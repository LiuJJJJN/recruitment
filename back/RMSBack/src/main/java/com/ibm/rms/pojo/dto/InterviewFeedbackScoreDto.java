package com.ibm.rms.pojo.dto;

public class InterviewFeedbackScoreDto {
    private String id;

    private Integer item;

    private Integer score;
    private String comment;

    private String interviewFeedbackId;

    public InterviewFeedbackScoreDto() {

    }

    public InterviewFeedbackScoreDto(String id, Integer item, Integer score, String comment, String interviewFeedbackId) {
        this.id = id;
        this.item = item;
        this.score = score;
        this.comment = comment;
        this.interviewFeedbackId = interviewFeedbackId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInterviewFeedbackId() {
        return interviewFeedbackId;
    }

    public void setInterviewFeedbackId(String interviewFeedbackId) {
        this.interviewFeedbackId = interviewFeedbackId;
    }

    @Override
    public String toString() {
        return "InterviewFeedbackScoreDto{" +
                "id='" + id + '\'' +
                ", item=" + item +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", interviewFeedbackId='" + interviewFeedbackId + '\'' +
                '}';
    }

}
