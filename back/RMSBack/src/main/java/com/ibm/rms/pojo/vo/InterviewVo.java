package com.ibm.rms.pojo.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InterviewVo {
    private String id;
    
    private String name;
    
    private String message;
    
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

    private Integer status;

    private Date creatDate;

    private Date modifyDate;

    private String resumeId;

    private String interviewer1;

	private String interviewer2;

    private String interviewer3;

    private String interviewer4;

    private String creater;

    private String modifyer;

    private String reviewer;

    public InterviewVo() {
    }

  
	public InterviewVo(String id, String name, String message, Date date, Integer status, Date creatDate,
			Date modifyDate, String resumeId, String interviewer1, String interviewer2, String interviewer3,
			String interviewer4, String creater, String modifyer, String reviewer) {
		super();
		this.id = id;
		this.name = name;
		this.message = message;
		this.date = date;
		this.status = status;
		this.creatDate = creatDate;
		this.modifyDate = modifyDate;
		this.resumeId = resumeId;
		this.interviewer1 = interviewer1;
		this.interviewer2 = interviewer2;
		this.interviewer3 = interviewer3;
		this.interviewer4 = interviewer4;
		this.creater = creater;
		this.modifyer = modifyer;
		this.reviewer = reviewer;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getIntervieweeName() {
  		return name;
  	}

  	public void setIntervieweeName(String intervieweeName) {
  		name = intervieweeName;
  	}
  	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getResumeId() {
        return resumeId;
    }

    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }

    public String getInterviewer1() {
        return interviewer1;
    }

    public void setInterviewer1(String interviewer1) {
        this.interviewer1 = interviewer1;
    }

    public String getInterviewer2() {
        return interviewer2;
    }

    public void setInterviewer2(String interviewer2) {
        this.interviewer2 = interviewer2;
    }

    public String getInterviewer3() {
        return interviewer3;
    }

    public void setInterviewer3(String interviewer3) {
        this.interviewer3 = interviewer3;
    }

    public String getInterviewer4() {
        return interviewer4;
    }

    public void setInterviewer4(String interviewer4) {
        this.interviewer4 = interviewer4;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getModifyer() {
        return modifyer;
    }

    public void setModifyer(String modifyer) {
        this.modifyer = modifyer;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }


	@Override
	public String toString() {
		return "InterviewVo [id=" + id + ", name=" + name + ", message=" + message + ", date=" + date + ", status="
				+ status + ", creatDate=" + creatDate + ", modifyDate=" + modifyDate + ", resumeId=" + resumeId
				+ ", interviewer1=" + interviewer1 + ", interviewer2=" + interviewer2 + ", interviewer3=" + interviewer3
				+ ", interviewer4=" + interviewer4 + ", creater=" + creater + ", modifyer=" + modifyer + ", reviewer="
				+ reviewer + "]";
	}



	

   

}
