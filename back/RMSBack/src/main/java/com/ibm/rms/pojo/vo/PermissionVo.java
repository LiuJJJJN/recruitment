package com.ibm.rms.pojo.vo;

/**
 * 权限数据在角色管理页面的 vo 类
 *
 * @author 刘嘉宁
 * @date 2022-12-07 21:25:18
 */
public class PermissionVo {

    // 权限 id
    private String key;

    // 权限内容描述
    private String label;

    public PermissionVo() {

    }

    public PermissionVo(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "PermissionVo{" +
                "key='" + key + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

}
