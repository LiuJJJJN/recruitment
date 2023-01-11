package com.ibm.rms.pojo;

import java.util.Date;

public class Resume {
    private String id;

    private String filePath;

    private String name;

    private Integer sex;

    private Date birthday;

    private String email;

    private String tel;

    private Integer education;

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

    private String creater;

    private Date creatDate;

    private String modifyer;

    private Date modifyDate;

    public Resume() {
    }

    public Resume(String id, String filePath, String name, Integer sex, Date birthday, String email, String tel,
                  Integer education, Date gradDate, String gradSchool, String major, Integer techAbility,
                  Integer langAbility, String otherAbility, String workExperience, String selfAssessment,
                  Integer source, String internalRecommender, String internalDept, Integer obey, String creater,
                  Date creatDate, String modifyer, Date modifyDate) {
        this.id = id;
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
        this.creater = creater;
        this.creatDate = creatDate;
        this.modifyer = modifyer;
        this.modifyDate = modifyDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public String getModifyer() {
        return modifyer;
    }

    public void setModifyer(String modifyer) {
        this.modifyer = modifyer;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id='" + id + '\'' +
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
                ", creater='" + creater + '\'' +
                ", creatDate=" + creatDate +
                ", modifyer='" + modifyer + '\'' +
                ", modifyDate=" + modifyDate +
                '}';
    }

}