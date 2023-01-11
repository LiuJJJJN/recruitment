package com.ibm.rms.pojo.dto;

import java.util.List;

/**
 * 添加用户表单数据
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:48:29
 */
public class UserAddDto {
    private String username;
    private String name;
    private String password;
    private String dept;
    private String email;
    private Integer status;
    private List<String> role;

    public UserAddDto() {

    }

    public UserAddDto(String username, String name, String password, String dept, String email, Integer status, List<String> role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.dept = dept;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserAddDto{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", dept='" + dept + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", role=" + role +
                '}';
    }

}
