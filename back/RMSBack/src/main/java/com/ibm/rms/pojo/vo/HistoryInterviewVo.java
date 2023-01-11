package com.ibm.rms.pojo.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HistoryInterviewVo {
	private String id;
    
    private String name;
    
    private Integer status;
    
    private String statusCode;
    
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

	

	

	public HistoryInterviewVo() {
		
	}





	public String getId() {
		return id;
	}





	public void setId(String id) {
		this.id = id;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public Integer getStatus() {
		return status;
	}





	public void setStatus(Integer status) {
		this.status = status;
	}





	public String getStatusCode() {
		return statusCode;
	}





	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}





	public Date getDate() {
		return date;
	}





	public void setDate(Date date) {
		this.date = date;
	}





	public HistoryInterviewVo(String id, String name, Integer status, String statusCode, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.statusCode = statusCode;
		this.date = date;
	}





	@Override
	public String toString() {
		return "HistoryInterviewVo [id=" + id + ", name=" + name + ", status=" + status + ", statusCode=" + statusCode
				+ ", date=" + date + "]";
	}

	
	





	
    
    
}
