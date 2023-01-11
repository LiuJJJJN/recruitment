package com.ibm.rms.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ibm.rms.pojo.User;

import java.util.Date;

/**
 * 面试一览画面显示数据
 *
 * @author 刘嘉宁
 * @date 2022-12-11 03:15:45
 */
public class InterviewResumeExcelVo {

    @ExcelProperty("面试 ID")
    private String id;

    @ExcelProperty("面试日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    @ExcelProperty("面试状态")
    private Integer status;

    @ExcelProperty("简历 ID")
    private String resumeId;

    @ExcelProperty("面试官1")
    private String interviewer1;

    @ExcelProperty("面试官2")
    private String interviewer2;

    @ExcelProperty("面试官3")
    private String interviewer3;

    @ExcelProperty("面试官4")
    private String interviewer4;

    @ExcelProperty("Reviewer")
    private String reviewer;

    @ExcelProperty("创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createDate;

    @ExcelProperty("简历路径")
    private String filePath;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private Integer sex;

    @ExcelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("电话")
    private String tel;

    @ExcelProperty("学历")
    private Integer education;

    @ExcelProperty("毕业日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gradDate;

    @ExcelProperty("毕业学校")
    private String gradSchool;

    @ExcelProperty("专业")
    private String major;

    @ExcelProperty("技术能力")
    private Integer techAbility;

    @ExcelProperty("语言能力")
    private Integer langAbility;

    @ExcelProperty("其它能力")
    private String otherAbility;

    @ExcelProperty("工作经验")
    private String workExperience;

    @ExcelProperty("自我介绍")
    private String selfAssessment;

    @ExcelProperty("简历来源")
    private Integer source;

    @ExcelProperty("内推人")
    private String internalRecommender;

    @ExcelProperty("意向部门")
    private String internalDept;

    @ExcelProperty("是否服从")
    private Integer obey;

    public InterviewResumeExcelVo() {

    }

    public InterviewResumeExcelVo(String id, Date date, Integer status, String resumeId, String interviewer1,
                                  String interviewer2, String interviewer3, String interviewer4, String reviewer,
                                  String createDate, String filePath, String name, Integer sex, Date birthday,
                                  String email, String tel, Integer education, Date gradDate, String gradSchool,
                                  String major, Integer techAbility, Integer langAbility, String otherAbility,
                                  String workExperience, String selfAssessment, Integer source,
                                  String internalRecommender, String internalDept, Integer obey) {
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

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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
        return "InterviewResumeExcelVo{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", resumeId='" + resumeId + '\'' +
                ", interviewer1='" + interviewer1 + '\'' +
                ", interviewer2='" + interviewer2 + '\'' +
                ", interviewer3='" + interviewer3 + '\'' +
                ", interviewer4='" + interviewer4 + '\'' +
                ", reviewer='" + reviewer + '\'' +
                ", createDate='" + createDate + '\'' +
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
