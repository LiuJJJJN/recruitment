package com.ibm.rms.mapper;

import com.ibm.rms.pojo.InterviewFeedbackScore;

public interface InterviewFeedbackScoreMapper {
    int deleteByPrimaryKey(String id);

    int insert(InterviewFeedbackScore record);

    int insertSelective(InterviewFeedbackScore record);

    InterviewFeedbackScore selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InterviewFeedbackScore record);

    int updateByPrimaryKey(InterviewFeedbackScore record);
}