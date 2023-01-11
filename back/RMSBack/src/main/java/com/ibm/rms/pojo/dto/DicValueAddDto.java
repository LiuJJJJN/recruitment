package com.ibm.rms.pojo.dto;

public class DicValueAddDto {
    private String typeId;
    private String valueKey;
    private String valueCode;
    private String ja;
    private String comment;

    public DicValueAddDto() {

    }

    public DicValueAddDto(String typeId, String valueKey, String valueCode, String ja, String comment) {
        this.typeId = typeId;
        this.valueKey = valueKey;
        this.valueCode = valueCode;
        this.ja = ja;
        this.comment = comment;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    public String getJa() {
        return ja;
    }

    public void setJa(String ja) {
        this.ja = ja;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "DicValueAddDto{" +
                "typeId='" + typeId + '\'' +
                ", valueKey='" + valueKey + '\'' +
                ", valueCode='" + valueCode + '\'' +
                ", ja='" + ja + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

}
