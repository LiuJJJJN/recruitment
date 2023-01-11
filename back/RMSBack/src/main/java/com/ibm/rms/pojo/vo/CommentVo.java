package com.ibm.rms.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ibm.rms.pojo.User;

import java.util.Date;

public class CommentVo {

    private String id;

    private Integer commentType;

    private String comment;

    private String interviewId;

    private User creater;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatDate;

    public CommentVo() {

    }

    public CommentVo(String id, Integer commentType, String comment, String interviewId, User creater, Date creatDate) {
        this.id = id;
        this.commentType = commentType;
        this.comment = comment;
        this.interviewId = interviewId;
        this.creater = creater;
        this.creatDate = creatDate;
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

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    @Override
    public String toString() {
        return "CommentVo{" +
                "id='" + id + '\'' +
                ", commentType=" + commentType +
                ", comment='" + comment + '\'' +
                ", interviewId='" + interviewId + '\'' +
                ", creater=" + creater +
                ", creatDate=" + creatDate +
                '}';
    }

}
