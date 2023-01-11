package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.exception.AuthenticationException;
import com.ibm.rms.service.RedisService;
import com.ibm.rms.mapper.UserMapper;
import com.ibm.rms.pojo.User;
import com.ibm.rms.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;


/**
 * Redis 服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:07:18
 */
@Service
@PropertySource("classpath:constValue.properties")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCacheUtil redisCache;

    // Redis 存储用户的 key 默认前缀
    @Value("${REDIS_USER_PREFIX}")
    private String REDIS_USER_PREFIX;

    @Override
    public void flushUserById(String id) {
        // 查询用户信息
        User user = userMapper.selectByPrimaryKey(id);
        // 如果没有这个账户则抛出异常
        if (user == null) {
            throw new AuthenticationException(ErrorResultEnum.EMPTY_USER);
        }
        // 把用户信息覆盖 redis
        String redisKey = REDIS_USER_PREFIX + id;
        LoginUser loginUser = new LoginUser(user);
        redisCache.setCacheObject(redisKey, loginUser);
    }

}
