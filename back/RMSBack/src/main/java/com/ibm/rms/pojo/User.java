package com.ibm.rms.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class User {
    private String id;

    private String username;

    @JSONField(serialize = false) // 不序列化到 Redis 中
    private String password;

    private String name;

    private Date signInDate;

    private String email;

    private Integer status;

    private String deptId;

    public User() {

    }

    public User(String id, String username, String password, String name, Date signInDate, String email, Integer status, String deptId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.signInDate = signInDate;
        this.email = email;
        this.status = status;
        this.deptId = deptId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", signInDate=" + signInDate +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", deptId='" + deptId + '\'' +
                '}';
    }
}
