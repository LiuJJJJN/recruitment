package com.ibm.rms.pojo.vo;

public class DictionaryValueVo {
    private String id;
    private String typeCode;
    private Integer valueKey;

    private String valueCode;

    private String comment;

    public DictionaryValueVo() {

    }

    public DictionaryValueVo(String id, String typeCode, Integer valueKey, String valueCode, String comment) {
        this.id = id;
        this.typeCode = typeCode;
        this.valueKey = valueKey;
        this.valueCode = valueCode;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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

    @Override
    public String toString() {
        return "DictionaryValueVo{" +
                "id='" + id + '\'' +
                ", typeCode='" + typeCode + '\'' +
                ", valueKey=" + valueKey +
                ", valueCode='" + valueCode + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

}
