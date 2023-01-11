package com.ibm.rms.pojo.dto;

public class InterviewConditionDto {
    private Integer pageNo;
    private Integer pageSize;
    private String date;
    
    public InterviewConditionDto() {

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public InterviewConditionDto(Integer pageNo, Integer pageSize, String date) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.date = date;
	}

	@Override
	public String toString() {
		return "InterviewConditionDto [pageNo=" + pageNo + ", pageSize=" + pageSize + ", date=" + date + "]";
	}

    

}
