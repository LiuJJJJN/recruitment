package com.ibm.rms.service;

import com.ibm.rms.pojo.dto.FeedbackDto;
import com.ibm.rms.pojo.vo.InterviewResumeVo;
import com.ibm.rms.pojo.vo.ResumeInterviewVo;

public interface FeedBackService {

	ResumeInterviewVo getInterviewResumeByInterviewId(String id);

    void addInterviewFeedback(FeedbackDto dto);
}
