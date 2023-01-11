package com.ibm.rms.pojo.dto;

import com.ibm.rms.pojo.InterviewFeedbackScore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class FeedbackDto {
    private String id;
    private Integer result;
    private String comment;
    private String internalDept;
    private String interviewId;
    private String interviewer;
    List<InterviewFeedbackScoreDto> abilityList;
    List<InterviewFeedbackScoreDto> confirmList;

    public FeedbackDto() {

    }

    public FeedbackDto(String id, Integer result, String comment, String internalDept, String interviewId, String interviewer, List<InterviewFeedbackScoreDto> abilityList, List<InterviewFeedbackScoreDto> confirmList) {
        this.id = id;
        this.result = result;
        this.comment = comment;
        this.internalDept = internalDept;
        this.interviewId = interviewId;
        this.interviewer = interviewer;
        this.abilityList = abilityList;
        this.confirmList = confirmList;
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInternalDept() {
        return internalDept;
    }

    public void setInternalDept(String internalDept) {
        this.internalDept = internalDept;
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

    public List<InterviewFeedbackScoreDto> getAbilityList() {
        return abilityList;
    }

    public void setAbilityList(List<InterviewFeedbackScoreDto> abilityList) {
        this.abilityList = abilityList;
    }

    public List<InterviewFeedbackScoreDto> getConfirmList() {
        return confirmList;
    }

    public void setConfirmList(List<InterviewFeedbackScoreDto> confirmList) {
        this.confirmList = confirmList;
    }

    @Override
    public String toString() {
        return "FeedbackDto{" +
                "id='" + id + '\'' +
                ", result=" + result +
                ", comment='" + comment + '\'' +
                ", internalDept='" + internalDept + '\'' +
                ", interviewId='" + interviewId + '\'' +
                ", interviewer='" + interviewer + '\'' +
                ", abilityList=" + abilityList +
                ", confirmList=" + confirmList +
                '}';
    }

}
