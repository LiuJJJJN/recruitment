package com.ibm.rms.filter;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.pojo.User;
import com.ibm.rms.utils.JwtUtil;
import com.ibm.rms.utils.RedisCacheUtil;
import com.ibm.rms.utils.WebUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 除 SpringSecurity 配置的登录接口外，其余请求全部进入此过滤器
 *
 * @author 刘嘉宁
 * @date 2022-11-29 11:54:38
 */
@Component
@PropertySource("classpath:constValue.properties")
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    // 设置 Redis 最小过期时间, 低于这个时长应续期
    @Value("${REDIS_MIN_TTL}")
    private Long REDIS_MIN_TTL;

    // 设置 Redis 续期时长
    @Value("${REDIS_TTL}")
    private Long REDIS_TTL;

    // 用户锁定状态的代码
    @Value("${USER_STATUS_LOCK}")
    private Integer USER_STATUS_LOCK;

    // Redis 存储用户的 key 默认前缀
    @Value("${REDIS_USER_PREFIX}")
    private String REDIS_USER_PREFIX;

    // 请求头中的 token 的 key
    @Value("${TOKEN_NAME}")
    private String TOKEN_NAME;

    /**
     * 解析请求头中的 token 得到用户 ID、根据用户 ID 在 Redis 中获取到用户信息保存到 SpringSecurity 提供的作用域中
     *
     * @param request     请求作用域
     * @param response    响应作用域
     * @param filterChain 过滤器链
     * @throws ServletException doFilter 异常
     * @throws IOException      doFilter 异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 获取请求头中的 token
        String token = request.getHeader(TOKEN_NAME);
        if (!StringUtils.hasText(token)) {
            //如果不存在 token 则放行(如果不是登录接口会报用户未认证错误)
            filterChain.doFilter(request, response);
            return;
        }
        // 解析 token 获取到用户 ID
        Claims claims;
        String userid;
        try {
            claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            // 如果解析 token 失败
            ResponseResult<Object> result = new ResponseResult<>(ErrorResultEnum.TOKEN_PARSE_FAIL);
            WebUtil.renderResult(response, result);
            return;
        }

        // 使用用户 ID 从 redis 中获取用户信息
        String redisKey = REDIS_USER_PREFIX + userid;
        LoginUser loginUser = redisCacheUtil.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            // 如果 redis 中没有此用户
            ResponseResult<Object> result = new ResponseResult<>(ErrorResultEnum.REDIS_TTL_END);
            WebUtil.renderResult(response, result);
            return;
        }

        // 判断用户是否被锁
        User user = loginUser.getUser();
        if (Objects.equals(user.getStatus(), USER_STATUS_LOCK)) {
            ResponseResult<Object> result = new ResponseResult<>(ErrorResultEnum.AUTHENTICATION_USER_STATUS_LOCK);
            WebUtil.renderResult(response, result);
            return;
        }

        // 此时可以证明 用户登录操作 是有效的, 开始 token 续期
        long keyDate = redisCacheUtil.getExpire(redisKey);
        if (keyDate <= REDIS_MIN_TTL) {
            logger.info("此时正在为 " + loginUser.getUsername() + " 续期");
            redisCacheUtil.expire(redisKey, REDIS_TTL);
        }

        // 将用户信息和用户的权限信息封装存入 SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }

}
