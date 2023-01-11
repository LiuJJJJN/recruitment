package com.ibm.rms.service;


import com.ibm.rms.pojo.dto.UserLoginDto;

import java.util.Map;

/**
 * 登录服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-11-12 15:28:07
 */
public interface LoginService {

    /**
     * 根据用户名密码进行登录, 生成 token、存入 Redis、响应 token 数据
     *
     * @param userLoginDto 登录请求携带的用户名、密码等数据
     * @return 登录成功时返回用户对应的用户名及 token
     */
    Map<String, String> login(UserLoginDto userLoginDto);

    /**
     * 退出登录, 删除 redis 中数据
     */
    void logout();
}
