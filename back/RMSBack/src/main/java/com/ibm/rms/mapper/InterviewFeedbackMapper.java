package com.ibm.rms.mapper;

import com.ibm.rms.pojo.InterviewFeedback;

public interface InterviewFeedbackMapper {
    int deleteByPrimaryKey(String id);

    int insert(InterviewFeedback record);

    int insertSelective(InterviewFeedback record);

    InterviewFeedback selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InterviewFeedback record);

    int updateByPrimaryKey(InterviewFeedback record);
}