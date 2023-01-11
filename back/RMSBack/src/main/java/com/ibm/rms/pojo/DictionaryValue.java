package com.ibm.rms.pojo;

public class DictionaryValue {
    private String id;

    private Integer valueKey;

    private String valueCode;

    private String comment;

    private String typeId;

    public DictionaryValue() {

    }

    public DictionaryValue(String id, Integer valueKey, String valueCode, String comment, String typeId) {
        this.id = id;
        this.valueKey = valueKey;
        this.valueCode = valueCode;
        this.comment = comment;
        this.typeId = typeId;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "DictionaryValue{" +
                "id='" + id + '\'' +
                ", valueKey=" + valueKey +
                ", valueCode='" + valueCode + '\'' +
                ", comment='" + comment + '\'' +
                ", typeId='" + typeId + '\'' +
                '}';
    }

}
