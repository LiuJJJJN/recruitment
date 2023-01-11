package com.ibm.rms.pojo.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResumeInterviewVo {
	private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    private String status;

    private String resumeId;

    private UserVo interviewer1;

    private UserVo interviewer2;

    private UserVo interviewer3;

    private UserVo interviewer4;

    private UserVo reviewer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyDate;

    private String filePath;

    private String name;

    private String sex;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    private String email;

    private String tel;

    private String education;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gradDate;

    private String gradSchool;

    private String major;

    private String techAbility;

    private String langAbility;

    private String otherAbility;

    private String workExperience;

    private String selfAssessment;

    private String source;

    private String internalRecommender;

    private String internalDept;

    private String obey;

	public ResumeInterviewVo() {
		super();
	}

	public ResumeInterviewVo(String id, Date date, String status, String resumeId, UserVo interviewer1,
			UserVo interviewer2, UserVo interviewer3, UserVo interviewer4, UserVo reviewer, Date createDate,
			Date modifyDate, String filePath, String name, String sex, Date birthday, String email, String tel,
			String education, Date gradDate, String gradSchool, String major, String techAbility, String langAbility,
			String otherAbility, String workExperience, String selfAssessment, String source,
			String internalRecommender, String internalDept, String obey) {
		super();
		this.id = id;
		this.date = date;
		this.status = status;
		this.resumeId = resumeId;
		this.interviewer1 = interviewer1;
		this.interviewer2 = interviewer2;
		this.interviewer3 = interviewer3;
		this.interviewer4 = interviewer4;
		this.reviewer = reviewer;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.filePath = filePath;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.email = email;
		this.tel = tel;
		this.education = education;
		this.gradDate = gradDate;
		this.gradSchool = gradSchool;
		this.major = major;
		this.techAbility = techAbility;
		this.langAbility = langAbility;
		this.otherAbility = otherAbility;
		this.workExperience = workExperience;
		this.selfAssessment = selfAssessment;
		this.source = source;
		this.internalRecommender = internalRecommender;
		this.internalDept = internalDept;
		this.obey = obey;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResumeId() {
		return resumeId;
	}

	public void setResumeId(String resumeId) {
		this.resumeId = resumeId;
	}

	public UserVo getInterviewer1() {
		return interviewer1;
	}

	public void setInterviewer1(UserVo interviewer1) {
		this.interviewer1 = interviewer1;
	}

	public UserVo getInterviewer2() {
		return interviewer2;
	}

	public void setInterviewer2(UserVo interviewer2) {
		this.interviewer2 = interviewer2;
	}

	public UserVo getInterviewer3() {
		return interviewer3;
	}

	public void setInterviewer3(UserVo interviewer3) {
		this.interviewer3 = interviewer3;
	}

	public UserVo getInterviewer4() {
		return interviewer4;
	}

	public void setInterviewer4(UserVo interviewer4) {
		this.interviewer4 = interviewer4;
	}

	public UserVo getReviewer() {
		return reviewer;
	}

	public void setReviewer(UserVo reviewer) {
		this.reviewer = reviewer;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public Date getGradDate() {
		return gradDate;
	}

	public void setGradDate(Date gradDate) {
		this.gradDate = gradDate;
	}

	public String getGradSchool() {
		return gradSchool;
	}

	public void setGradSchool(String gradSchool) {
		this.gradSchool = gradSchool;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getTechAbility() {
		return techAbility;
	}

	public void setTechAbility(String techAbility) {
		this.techAbility = techAbility;
	}

	public String getLangAbility() {
		return langAbility;
	}

	public void setLangAbility(String langAbility) {
		this.langAbility = langAbility;
	}

	public String getOtherAbility() {
		return otherAbility;
	}

	public void setOtherAbility(String otherAbility) {
		this.otherAbility = otherAbility;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getSelfAssessment() {
		return selfAssessment;
	}

	public void setSelfAssessment(String selfAssessment) {
		this.selfAssessment = selfAssessment;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getInternalRecommender() {
		return internalRecommender;
	}

	public void setInternalRecommender(String internalRecommender) {
		this.internalRecommender = internalRecommender;
	}

	public String getInternalDept() {
		return internalDept;
	}

	public void setInternalDept(String internalDept) {
		this.internalDept = internalDept;
	}

	public String getObey() {
		return obey;
	}

	public void setObey(String obey) {
		this.obey = obey;
	}
    
}
