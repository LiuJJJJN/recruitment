package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.exception.FeedbackException;
import com.ibm.rms.mapper.CommentMapper;
import com.ibm.rms.mapper.InterviewFeedbackMapper;
import com.ibm.rms.pojo.Comment;
import com.ibm.rms.pojo.InterviewFeedback;
import com.ibm.rms.pojo.dto.FeedbackDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ibm.rms.mapper.InterviewMapper;
import com.ibm.rms.pojo.vo.InterviewResumeVo;
import com.ibm.rms.pojo.vo.ResumeInterviewVo;
import com.ibm.rms.service.FeedBackService;

@Service
public class FeedBackServiceImpl implements FeedBackService {
	@Autowired
	private InterviewMapper interviewMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private InterviewFeedbackMapper interviewFeedbackMapper;

	@Override
	public ResumeInterviewVo getInterviewResumeByInterviewId(String id) {
		ResumeInterviewVo interviewResumeVo = interviewMapper.selectInterviewResumeByInterviewId(id);
		return interviewResumeVo;
	}

    @Override
    public void addInterviewFeedback(FeedbackDto dto) {
		String feedbackId = interviewMapper.selectUUID();
		LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 插入面试反馈表数据
		InterviewFeedback interviewFeedback = new InterviewFeedback();
		interviewFeedback.setId(feedbackId);
		interviewFeedback.setResult(dto.getResult());
		interviewFeedback.setInterviewId(dto.getInterviewId());
		interviewFeedback.setInterviewer(loginUser.getUser().getId());
		int result = interviewFeedbackMapper.insertSelective(interviewFeedback);
		if (result != 1) {
			throw new FeedbackException(ErrorResultEnum.FEEDBACK_FAIL);
		}
		// 插入面试反馈得分表(能力)数据
		// todo
		// 插入面试反馈得分表(确认事项)数据
		// todo
		// 插入备注表数据
		if (dto.getComment() != null && !"".equals(dto.getComment())) {
			Comment comment = new Comment();
			comment.setCommentType(3);
			comment.setComment(dto.getComment());
			comment.setInterviewId(dto.getInterviewId());
			result = commentMapper.insertCommentSelective(loginUser.getUser().getId(), comment);
			if (result != 1) {
				throw new FeedbackException(ErrorResultEnum.FEEDBACK_FAIL);
			}
		}

    }

}
