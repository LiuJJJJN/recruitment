package com.ibm.rms.controller;

import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DemoController {

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('interviewManage:*:*')")
    public String test() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(loginUser.getUser().getId());
//        System.out.println(authentication.getDetails());
        System.out.println(authentication.getPrincipal()); // 获取当前登录用户实体类
        System.out.println(authentication.getName()); // 获取当前登录用户名
        System.out.println(authentication.getAuthorities()); //获取当前登录用户权限列表
//        System.out.println(authentication.getCredentials());

        return "test111111111111!";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test222222222222!";
    }

    @PostMapping("/cvUpload")
    public void cvUpload(MultipartFile file) {
        System.out.println(file);
    }

}
