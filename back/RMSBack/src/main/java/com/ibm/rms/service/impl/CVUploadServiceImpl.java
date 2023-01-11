package com.ibm.rms.service.impl;

import java.util.List;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.exception.CVReviewException;
import com.ibm.rms.exception.CVUploadException;
import com.ibm.rms.mapper.InterviewMapper;
import com.ibm.rms.mapper.ResumeMapper;
import com.ibm.rms.pojo.Interview;
import com.ibm.rms.pojo.dto.ResumeInterviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ibm.rms.service.CVUploadService;
import com.ibm.rms.mapper.CommentMapper;
import com.ibm.rms.mapper.DictionaryValueMapper;
import com.ibm.rms.pojo.Comment;
import com.ibm.rms.pojo.DictionaryValue;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CVUploadServiceImpl implements CVUploadService{
	@Autowired
    private CommentMapper commentMapper;
	
	@Autowired
	private DictionaryValueMapper dictionaryValueMapper;

	@Autowired
	private InterviewMapper interviewMapper;

	@Autowired
	private ResumeMapper resumeMapper;

	@Override
	public List<DictionaryValue> getEducationList() {
//		List<DictionaryValue> list = dictionaryValueMapper.selectByType("education");
//		if (list == null || list.size() == 0) {
//			throw new;
//		}
		return null;
	}

    @Override
	@Transactional(rollbackFor = CVUploadException.class)
    public String addResumeInterview(ResumeInterviewDto dto) {
		// 获取当前操作人
		LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 获取 UUID
		String resumeId = interviewMapper.selectUUID();

		// 格式化数据
		dto.setFilePath("/rms/" + dto.getFilePath());
		if (dto.getIntendedDept() == null || "".equals(dto.getIntendedDept())) {
			dto.setObey(null);
		}
		if (!"2".equals(dto.getSource())) {
			dto.setInternalRecommender(null);
		}

		// 添加简历表数据
		int result = resumeMapper.insertSelectiveByResumeInterviewDto(dto, resumeId, loginUser.getUser().getId());
		if (result != 1) {
			throw new CVUploadException(ErrorResultEnum.CV_UPLOAD_FAIL);
		}

		// 创建面试实例对象
		Interview interview = new Interview();
		interview.setId(interviewMapper.selectUUID());
		// 判断是否分配了 Reviewer
		if (dto.getReviewer() == null || "".equals(dto.getReviewer())) {
			interview.setStatus(1);
		} else {
			interview.setReviewer(dto.getReviewer());
			interview.setStatus(2);
		}
		interview.setResumeId(resumeId);
		interview.setCreater(loginUser.getUser().getId());

		// 添加面试表数据
		result = interviewMapper.insertSelective3CVUpload(interview);
		if (result != 1) {
			throw new CVUploadException(ErrorResultEnum.CV_UPLOAD_FAIL);
		}

		// 执行插入操作
        Comment comment = new Comment();
        comment.setCommentType(1);
        comment.setComment(dto.getComment());
        comment.setInterviewId(interview.getId());
        result = commentMapper.insertCommentSelective(loginUser.getUser().getId(), comment);
        // 如果插入失败则抛出异常回滚数据
        if (result != 1) {
            throw new CVUploadException(ErrorResultEnum.COMMENT_INSERT_FAIL);
        }
		// 返回面试 ID
		return interview.getId();
    }

}
