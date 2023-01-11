package com.ibm.rms.service.impl;

import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.exception.RedisException;
import com.ibm.rms.pojo.dto.UserLoginDto;
import com.ibm.rms.service.LoginService;
import com.ibm.rms.utils.JwtUtil;
import com.ibm.rms.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 登录服务层实现类
 *
 * @author 刘嘉宁
 * @date 2022-11-12 15:28:12
 */
@Service
@PropertySource("classpath:constValue.properties")
public class LoginServiceImpl implements LoginService {

    // 注入 AuthenticationManager 进行用户认证
    @Autowired
    private AuthenticationManager authenticationManager;

    // 缓存服务
    @Autowired
    private RedisCacheUtil redisCache;

    // 设置 redis 过期时长
    @Value("${REDIS_TTL}")
    private Long REDIS_TTL;

    // Redis 存储用户的 key 默认前缀
    @Value("${REDIS_USER_PREFIX}")
    private String REDIS_USER_PREFIX;

    @Override
    public Map<String, String> login(UserLoginDto userVo) {
        // 将用户传来的用户名和密码封装成 authenticate 可识别的对象，并设置进 authenticationManager 中进行认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPassword());
        // 在这里调用 UserDetailServiceImpl 进行数据访问层的操作
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果认证没通过则给出提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("LoginServiceImpl:用户名或密码错误");
        }
        // 认证通过后，使用 userid 生成 jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入 redis
        String redisKey = REDIS_USER_PREFIX + userId;
        redisCache.setCacheObject(redisKey, loginUser);
        redisCache.expire(redisKey, REDIS_TTL);
        // 把 用户姓名、jwt 响应给前端
        HashMap<String, String> map = new HashMap<>();
        map.put("name", loginUser.getUser().getName());
        map.put("token", jwt);
        return map;
    }

    @Override
    public void logout() {
        // 获取到当前用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId();
        // 删除 Redis 中当前用户的信息
        boolean deleteFlag = redisCache.deleteObject(REDIS_USER_PREFIX + userId);
        // 删除失败时抛出异常
        if (!deleteFlag) {
            throw new RedisException(ErrorResultEnum.REDIS_EXCEPTION);
        }
    }

}
