package com.ibm.rms.pojo;

public class DictionaryType {
    private String id;

    private String typeCode;

    private String comment;

    public DictionaryType() {
    }

    public DictionaryType(String id, String typeCode, String comment) {
        this.id = id;
        this.typeCode = typeCode;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "DictionaryType{" +
                "id='" + id + '\'' +
                ", code='" + typeCode + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

}