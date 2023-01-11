package com.ibm.rms.pojo.vo;

/**
 * 数据字典值的 vo 类，在数据字典值表中添加 isChecked 属性，默认为 false
 *
 * @author 刘嘉宁
 * @date 2022-12-11 02:03:56
 */
public class DicValueCheckableVo {
    private String id;

    private Integer valueKey;

    private String valueCode;

    private String comment;

    private Boolean isChecked = false;

    public DicValueCheckableVo() {

    }

    public DicValueCheckableVo(String id, Integer valueKey, String valueCode, String comment, Boolean isChecked) {
        this.id = id;
        this.valueKey = valueKey;
        this.valueCode = valueCode;
        this.comment = comment;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getValueKey() {
        return valueKey;
    }

    public void setValueKey(Integer valueKey) {
        this.valueKey = valueKey;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "DicValueCheckableVo{" +
                "id='" + id + '\'' +
                ", valueKey=" + valueKey +
                ", valueCode='" + valueCode + '\'' +
                ", comment='" + comment + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

}
