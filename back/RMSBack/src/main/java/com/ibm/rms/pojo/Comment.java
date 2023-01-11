package com.ibm.rms.pojo;

import java.util.Date;

public class Comment {
    private String id;

    private Integer commentType;

    private String comment;

    private Date creatDate;

    private Date modifyDate;

    private String interviewId;

    private String creater;

    private String modifyer;

    public Comment() {

    }

    public Comment(String id, Integer commentType, String comment, Date creatDate, Date modifyDate, String interviewId,
                   String creater, String modifyer) {
        this.id = id;
        this.commentType = commentType;
        this.comment = comment;
        this.creatDate = creatDate;
        this.modifyDate = modifyDate;
        this.interviewId = interviewId;
        this.creater = creater;
        this.modifyer = modifyer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
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

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", commentType=" + commentType +
                ", comment='" + comment + '\'' +
                ", creatDate=" + creatDate +
                ", modifyDate=" + modifyDate +
                ", interviewId='" + interviewId + '\'' +
                ", creater='" + creater + '\'' +
                ", modifyer='" + modifyer + '\'' +
                '}';
    }

}