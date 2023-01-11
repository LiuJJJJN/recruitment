package com.ibm.rms.pojo.dto;

public class HistoryPageConditionDto {
    private String nameCode;
    private Integer pageNo;
    private Integer pageSize;

    public HistoryPageConditionDto() {

    }

	public String getNameCode() {
		return nameCode;
	}

	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
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

	public HistoryPageConditionDto(String nameCode, Integer pageNo, Integer pageSize) {
		super();
		this.nameCode = nameCode;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "HistoryPageConditionDto [nameCode=" + nameCode + ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}

   
}
