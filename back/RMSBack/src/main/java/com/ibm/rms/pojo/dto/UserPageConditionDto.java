package com.ibm.rms.pojo.dto;

/**
 * 用户表格数据查询分页和模糊查询数据
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:47:33
 */
public class UserPageConditionDto {

    private Integer pageNo;
    private Integer pageSize;
    private String username;
    private String name;
    private String company;
    private String dept;

    public UserPageConditionDto() {

    }

    public UserPageConditionDto(Integer pageNo, Integer pageSize, String username, String name, String company, String dept) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.username = username;
        this.name = name;
        this.company = company;
        this.dept = dept;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "UserPageConditionDto{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}
