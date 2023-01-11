package com.ibm.rms.pojo;

public class InterviewFeedbackScore {
    private String id;

    private Integer item;

    private Integer score;

    private String interviewFeedbackId;

    public InterviewFeedbackScore() {
    }

    public InterviewFeedbackScore(String id, Integer item, Integer score, String interviewFeedbackId) {
        this.id = id;
        this.item = item;
        this.score = score;
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

    public String getInterviewFeedbackId() {
        return interviewFeedbackId;
    }

    public void setInterviewFeedbackId(String interviewFeedbackId) {
        this.interviewFeedbackId = interviewFeedbackId;
    }

    @Override
    public String toString() {
        return "InterviewFeedbackScore{" +
                "id='" + id + '\'' +
                ", item=" + item +
                ", score=" + score +
                ", interviewFeedbackId='" + interviewFeedbackId + '\'' +
                '}';
    }

}