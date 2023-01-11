package com.ibm.rms.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 更新面试信息 DTO 类
 *
 * @author 刘嘉宁
 * @date 2022-12-17 15:40:27
 */
public class InterviewEditDto {
    private String id;

    private Date date;

    private String interviewer1;

    private String interviewer2;

    private String interviewer3;

    private String interviewer4;

    private String reviewer;

    private Date modifyDate;

    public InterviewEditDto() {

    }

    public InterviewEditDto(String id, Date date, String interviewer1, String interviewer2, String interviewer3,
                            String interviewer4, String reviewer, Date modifyDate) {
        this.id = id;
        this.date = date;
        this.interviewer1 = interviewer1;
        this.interviewer2 = interviewer2;
        this.interviewer3 = interviewer3;
        this.interviewer4 = interviewer4;
        this.reviewer = reviewer;
        this.modifyDate = modifyDate;
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

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "InterviewEditDto{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", interviewer1='" + interviewer1 + '\'' +
                ", interviewer2='" + interviewer2 + '\'' +
                ", interviewer3='" + interviewer3 + '\'' +
                ", interviewer4='" + interviewer4 + '\'' +
                ", reviewer='" + reviewer + '\'' +
                ", modifyDate=" + modifyDate +
                '}';
    }

}
