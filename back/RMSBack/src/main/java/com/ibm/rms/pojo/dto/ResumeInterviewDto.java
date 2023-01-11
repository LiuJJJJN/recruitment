package com.ibm.rms.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ResumeInterviewDto {
    private String name;
    private String filePath;
    private String sex;
    @DateTimeFormat
    private Date birthday;
    private String email;
    private String tel;
    private String education;
    @DateTimeFormat
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
    private String intendedDept;
    private String obey;
    private String reviewer;
    private String comment;

    public ResumeInterviewDto() {

    }

    public ResumeInterviewDto(String name, String filePath, String sex, Date birthday, String email, String tel, String education, Date gradDate, String gradSchool, String major, String techAbility, String langAbility, String otherAbility, String workExperience, String selfAssessment, String source, String internalRecommender, String intendedDept, String obey, String reviewer, String comment) {
        this.name = name;
        this.filePath = filePath;
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
        this.intendedDept = intendedDept;
        this.obey = obey;
        this.reviewer = reviewer;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public String getIntendedDept() {
        return intendedDept;
    }

    public void setIntendedDept(String intendedDept) {
        this.intendedDept = intendedDept;
    }

    public String getObey() {
        return obey;
    }

    public void setObey(String obey) {
        this.obey = obey;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
