package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.exception.CVReviewException;
import com.ibm.rms.mapper.CommentMapper;
import com.ibm.rms.mapper.InterviewMapper;
import com.ibm.rms.mapper.ResumeMapper;
import com.ibm.rms.pojo.dto.CVReviewDto;
import com.ibm.rms.service.CVReviewService;
import com.ibm.rms.pojo.Comment;
import com.ibm.rms.pojo.Interview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 简历 Review 服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-18 16:44:39
 */
@Service
@PropertySource("classpath:constValue.properties")
public class CVReviewServiceImpl implements CVReviewService {

    @Autowired
    private ResumeMapper resumeMapper;

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private CommentMapper commentMapper;

    // 面试状态为等待面试的数据字典值 valueKey
    @Value("${WAIT_INTERVIEW_STATUS}")
    private Integer WAIT_INTERVIEW_STATUS;

    // 面试状态为等待 Review 的数据字典值 valueKey
    @Value("${WAIT_REVIEW_STATUS}")
    private Integer WAIT_REVIEW_STATUS;

    // 面试状态为简历 Review NG 的数据字典值 valueKey
    @Value("${REVIEW_NG_STATUS}")
    private Integer REVIEW_NG_STATUS;

    // 反馈为 OK 的数据字典值 valueKey
    @Value("${RESULT_OK}")
    private Integer RESULT_OK;

    // 反馈为 NG 的数据字典值 valueKey
    @Value("${RESULT_NG}")
    private Integer RESULT_NG;

    // 面试状态为未确定面试时间的数据字典值 valueKey
    @Value("${WAIT_ASSIGN_INTERVIEW_DATE_STATUS}")
    private Integer WAIT_ASSIGN_INTERVIEW_DATE_STATUS;

    // 面试状态为未分配 Interviewer 的数据字典值 valueKey
    @Value("${WAIT_ASSIGN_INTERVIEWER_STATUS}")
    private Integer WAIT_ASSIGN_INTERVIEWER_STATUS;

    // 备注类型为 Review 的数据字典值 valueKey
    @Value("${REVIEW_COMMENT}")
    private Integer REVIEW_COMMENT;

    @Override
    @Transactional(rollbackFor = CVReviewException.class)
    public void submitReviewResult(CVReviewDto dto) {
        // 获取当前登录用户 id
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = loginUser.getUser().getId();
        // 如果用户 id 获取失败则抛出异常, 前端重新登录
        if (id == null) {
            throw new CVReviewException(ErrorResultEnum.EMPTY_USER);
        }

        // 更新简历信息
        int result = resumeMapper.updateByPrimaryKeySelectiveReview(id, dto.getData());
        // 如果更新失败则抛出异常并回滚数据
        if (result != 1) {
            throw new CVReviewException(ErrorResultEnum.RESUME_UPDATE_FAIL);
        }

        // 查询面试信息
        Interview interview = interviewMapper.selectByPrimaryKey(dto.getInterviewId());
        // 判断是否重复 Review
        if (interview.getStatus() > WAIT_REVIEW_STATUS) {
            throw new CVReviewException(ErrorResultEnum.REPEAT_REVIEW_EXCEPTION);
        }
        Integer status = null;
        // 根据有没有分配面试时间、面试官判断 Review OK 后的面试 status
        if (Objects.equals(dto.getResult(), RESULT_OK)) {
            if (interview.getDate() == null) {
                status = WAIT_ASSIGN_INTERVIEW_DATE_STATUS;
            } else if (interview.getInterviewer1() == null) {
                status = WAIT_ASSIGN_INTERVIEWER_STATUS;
            } else {
                status = WAIT_INTERVIEW_STATUS;
            }
        }
        if (Objects.equals(dto.getResult(), RESULT_NG)) {
            status = REVIEW_NG_STATUS;
        }
        // 设置面试实体中 状态值
        interview.setStatus(status);

        // 更新面试信息
        int res = interviewMapper.updateStatusById(id, interview);
        // 如果更新失败则抛出异常回滚数据
        if (res != 1) {
            throw new CVReviewException(ErrorResultEnum.INTERVIEW_OVERVIEW_UPDATE_FAIL);
        }

        // 添加评论表数据
        if (Objects.equals(status, REVIEW_NG_STATUS)) {
            // 执行插入操作
            Comment comment = new Comment();
            comment.setCommentType(REVIEW_COMMENT);
            comment.setComment(dto.getComment());
            comment.setInterviewId(dto.getInterviewId());
            result = commentMapper.insertCommentSelective(id, comment);
            // 如果插入失败则抛出异常回滚数据
            if (result != 1) {
                throw new CVReviewException(ErrorResultEnum.COMMENT_INSERT_FAIL);
            }
        }
    }

}
