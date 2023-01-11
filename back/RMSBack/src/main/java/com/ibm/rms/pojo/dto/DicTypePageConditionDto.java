package com.ibm.rms.pojo.dto;

public class DicTypePageConditionDto {
    private String typeCode;
    private Integer pageNo;
    private Integer pageSize;

    public DicTypePageConditionDto() {

    }

    public DicTypePageConditionDto(String typeCode, Integer pageNo, Integer pageSize) {
        this.typeCode = typeCode;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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

    @Override
    public String toString() {
        return "DicTypePageConditionDto{" +
                "typeCode='" + typeCode + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }

}
