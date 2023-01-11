package com.ibm.rms.service.impl;

import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.exception.AccessDeniedException;
import com.ibm.rms.exception.AuthenticationException;
import com.ibm.rms.mapper.UserMapper;
import com.ibm.rms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 登录加载用户数据核心服务层
 *
 * @author 刘嘉宁
 * @date 2022-11-12 15:28:07
 */
@Service
@PropertySource("classpath:constValue.properties")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    // 用户锁定状态的代码
    @Value("${USER_STATUS_LOCK}")
    private Integer USER_STATUS_LOCK;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user = userMapper.selectOneByUsername(username);
        // 如果没有这个账户则抛出异常
        if (user == null) {
            throw new AuthenticationException(ErrorResultEnum.EMPTY_USER);
        }
        // 如果账户被锁
        if (Objects.equals(user.getStatus(), USER_STATUS_LOCK)) {
            throw new AccessDeniedException(ErrorResultEnum.LOGIN_USER_STATUS_LOCK);
        }
        // 查询权限信息
        List<String> permissions = userMapper.selectPermissionsById(user.getId());

        //把用户封装成 UserDetail 返回
        return new LoginUser(user, permissions);
    }

}
