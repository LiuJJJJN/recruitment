package com.ibm.rms.pojo.vo;

import java.util.Date;
import java.util.List;

public class UserVo {
    private String id;

    private String username;

    private String name;

    private Date signInDate;

    private String company;

    private String email;

    private Integer status;

    private String dept;

    private List<String> roleList;

    public UserVo() {

    }

    public UserVo(String id, String username, String name, Date signInDate, String company, String email, Integer status, String dept, List<String> roleList) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.signInDate = signInDate;
        this.company = company;
        this.email = email;
        this.status = status;
        this.dept = dept;
        this.roleList = roleList;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", signInDate=" + signInDate +
                ", company='" + company + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", dept='" + dept + '\'' +
                ", roleList=" + roleList +
                '}';
    }

}
