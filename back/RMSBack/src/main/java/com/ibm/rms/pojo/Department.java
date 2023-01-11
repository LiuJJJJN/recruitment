package com.ibm.rms.pojo;

/**
 * 部门表实体类
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:05:15
 */
public class Department {
    private String id;

    private String name;

    private String company;

    private String comment;

    public Department() {

    }

    public Department(String id, String name, String company, String comment) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}