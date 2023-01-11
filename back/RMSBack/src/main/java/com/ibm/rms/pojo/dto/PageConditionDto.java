package com.ibm.rms.pojo.dto;

/**
 * 分页 dto
 *
 * @author 刘嘉宁
 * @date 2022-12-17 18:25:08
 */
public class PageConditionDto {

    private Integer pageNo;

    private Integer pageSize;

    public PageConditionDto() {

    }

    public PageConditionDto(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
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
        return "pageConditionDto{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }

}
