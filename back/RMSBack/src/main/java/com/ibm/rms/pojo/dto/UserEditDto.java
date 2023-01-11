package com.ibm.rms.pojo.dto;

import java.util.List;

/**
 * 编辑用户表单数据
 *
 * @author 刘嘉宁
 * @date 2022-12-07 17:27:41
 */
public class UserEditDto {
    private String id;
    private String dept;
    private String email;
    private String name;
    private String password;
    private List<String> role;
    private Integer status;
    private String username;

    public UserEditDto() {

    }

    public UserEditDto(String id, String dept, String email, String name, String password, List<String> role, Integer status, String username) {
        this.id = id;
        this.dept = dept;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.status = status;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserEditDto{" +
                "id='" + id + '\'' +
                ", dept='" + dept + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", username='" + username + '\'' +
                '}';
    }

}
