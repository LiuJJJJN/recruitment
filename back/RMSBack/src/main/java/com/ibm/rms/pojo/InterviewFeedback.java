package com.ibm.rms.pojo;

public class InterviewFeedback {
    private String id;

    private Integer result;

    private String interviewId;

    private String interviewer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public String getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(String interviewer) {
        this.interviewer = interviewer;
    }

    @Override
    public String toString() {
        return "InterviewFeedback{" +
                "id='" + id + '\'' +
                ", result=" + result +
                ", interviewId='" + interviewId + '\'' +
                ", interviewer='" + interviewer + '\'' +
                '}';
    }

}