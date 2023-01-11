package com.ibm.rms.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ibm.rms.exception.PermissionException;
import com.ibm.rms.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDetails 的实现类
 * 代理了用户类、用户的权限列表
 * 在认证后会被序列化上传 Redis
 *
 * @author 刘嘉宁
 * @date 2022-11-29 11:49:54
 */
public class LoginUser implements UserDetails {

    // 代理用户实体
    private User user;
    // 代理用户对应的权限列表
    private List<String> permissions;
    // 存储 SpringSecurity 所需要的权限信息的集合
    @JSONField(serialize = false) // 不序列化到 Redis 中
    private List<GrantedAuthority> authorities;

    public LoginUser() {

    }

    public LoginUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(User user, List<String> permissions, List<GrantedAuthority> authorities) {
        this.user = user;
        this.permissions = permissions;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 获取权限信息
        if (authorities != null) {
            return authorities;
        }

        // 如果在查询用户对应的权限列表时出现错误(数据库权限设置错误), 抛出异常
        if (permissions == null || permissions.isEmpty() || permissions.get(0) == null) {
            throw new PermissionException(ErrorResultEnum.EMPTY_PERMISSION);
        }

        //把 permissions 中字符串类型的权限信息转换成 GrantedAuthority 对象存入 authorities 中
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return authorities;
    }

    @Override
    @JSONField(serialize = false) // 不序列化到 Redis 中
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "user=" + user +
                ", permissions=" + permissions +
                ", authorities=" + authorities +
                '}';
    }

}
