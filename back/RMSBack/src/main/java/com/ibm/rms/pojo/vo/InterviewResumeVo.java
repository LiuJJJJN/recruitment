package com.ibm.rms.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 面试一览画面显示数据
 *
 * @author 刘嘉宁
 * @date 2022-12-11 03:15:45
 */
public class InterviewResumeVo {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    private Integer status;

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

    private Integer sex;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    private String email;

    private String tel;

    private Integer education;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gradDate;

    private String gradSchool;

    private String major;

    private Integer techAbility;

    private Integer langAbility;

    private String otherAbility;

    private String workExperience;

    private String selfAssessment;

    private Integer source;

    private String internalRecommender;

    private String internalDept;

    private Integer obey;

    public InterviewResumeVo() {

    }

    public InterviewResumeVo(String id, Date date, Integer status, String resumeId, UserVo interviewer1, UserVo interviewer2, UserVo interviewer3, UserVo interviewer4, UserVo reviewer, Date createDate, Date modifyDate, String filePath, String name, Integer sex, Date birthday, String email, String tel, Integer education, Date gradDate, String gradSchool, String major, Integer techAbility, Integer langAbility, String otherAbility, String workExperience, String selfAssessment, Integer source, String internalRecommender, String internalDept, Integer obey) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
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

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
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

    public Integer getTechAbility() {
        return techAbility;
    }

    public void setTechAbility(Integer techAbility) {
        this.techAbility = techAbility;
    }

    public Integer getLangAbility() {
        return langAbility;
    }

    public void setLangAbility(Integer langAbility) {
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

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
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

    public Integer getObey() {
        return obey;
    }

    public void setObey(Integer obey) {
        this.obey = obey;
    }

    @Override
    public String toString() {
        return "InterviewResumeVo{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", resumeId='" + resumeId + '\'' +
                ", interviewer1=" + interviewer1 +
                ", interviewer2=" + interviewer2 +
                ", interviewer3=" + interviewer3 +
                ", interviewer4=" + interviewer4 +
                ", reviewer=" + reviewer +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", filePath='" + filePath + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", education=" + education +
                ", gradDate=" + gradDate +
                ", gradSchool='" + gradSchool + '\'' +
                ", major='" + major + '\'' +
                ", techAbility=" + techAbility +
                ", langAbility=" + langAbility +
                ", otherAbility='" + otherAbility + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", selfAssessment='" + selfAssessment + '\'' +
                ", source=" + source +
                ", internalRecommender='" + internalRecommender + '\'' +
                ", internalDept='" + internalDept + '\'' +
                ", obey=" + obey +
                '}';
    }
}
