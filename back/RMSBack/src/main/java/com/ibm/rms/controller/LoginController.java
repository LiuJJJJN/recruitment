package com.ibm.rms.controller;

import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.pojo.dto.UserLoginDto;
import com.ibm.rms.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 登录控制层
 *
 * @author 刘嘉宁
 * @date 2022-11-29 11:46:30
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录操作
     *
     * @param userVo 用户输入的数据
     * @return 登录成功提示
     */
    @PostMapping("/login")
    public ResponseResult<Map<String, String>> login(@RequestBody UserLoginDto userVo) {
        Map<String, String> map = loginService.login(userVo);
        return new ResponseResult<>(200, "登录成功", map);
    }

    /**
     * 登出操作
     *
     * @return 登出成功提示
     */
    @PostMapping("/logout")
    public ResponseResult<Object> logout() {
        loginService.logout();
        return new ResponseResult<>(200, "退出登录成功");
    }

}
