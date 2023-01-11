package com.ibm.rms.pojo;

public class Permission {
    private String id;

    private String name;

    private String code;

    private String path;

    private Integer isMenu;

    private Integer orderBy;

    public Permission() {
    }

    public Permission(String id, String name, String code, String path, Integer isMenu, Integer orderBy) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.path = path;
        this.isMenu = isMenu;
        this.orderBy = orderBy;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", path='" + path + '\'' +
                ", isMenu=" + isMenu +
                ", orderBy=" + orderBy +
                '}';
    }

}
