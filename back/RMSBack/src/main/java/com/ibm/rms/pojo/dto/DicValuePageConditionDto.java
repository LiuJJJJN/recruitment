package com.ibm.rms.pojo.dto;

public class DicValuePageConditionDto {
    private Integer pageNo;
    private Integer pageSize;
    private String typeCode;
    private String valueCode;

    public DicValuePageConditionDto() {

    }

    public DicValuePageConditionDto(Integer pageNo, Integer pageSize, String typeCode, String valueCode) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.typeCode = typeCode;
        this.valueCode = valueCode;
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    @Override
    public String toString() {
        return "DicValuePageConditionDto{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", typeCode='" + typeCode + '\'' +
                ", valueCode='" + valueCode + '\'' +
                '}';
    }

}
