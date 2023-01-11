package com.ibm.rms.service;

/**
 * Redis 服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:06:54
 */
public interface RedisService {

    /**
     * 根据 id 更新 Redis 中用户信息
     *
     * @param id 用户 id
     */
    void flushUserById(String id);
}
