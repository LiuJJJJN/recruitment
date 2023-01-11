package com.ibm.rms.pojo.dto;

import com.ibm.rms.pojo.Resume;

public class CVReviewDto {

    String interviewId;

    Resume data;

    Integer result;

    String comment;

    public CVReviewDto() {

    }

    public CVReviewDto(String interviewId, Resume data, Integer result, String comment) {
        this.interviewId = interviewId;
        this.data = data;
        this.result = result;
        this.comment = comment;
    }

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public Resume getData() {
        return data;
    }

    public void setData(Resume data) {
        this.data = data;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CVReviewDto{" +
                "interviewId='" + interviewId + '\'' +
                ", data=" + data +
                ", result=" + result +
                ", comment='" + comment + '\'' +
                '}';
    }

}
