package com.ibm.rms.service;

import com.ibm.rms.pojo.dto.InterviewEditDto;
import com.ibm.rms.pojo.dto.ResumeInterviewDto;
import com.ibm.rms.pojo.vo.InterviewResumeVo;

import java.util.List;

/**
 * 邮件发送服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-21 12:11:03
 */
public interface MailService {

    /**
     * 更新面试信息发送邮件
     *
     * @param dto 面试信息
     */
    void mailSend4editInterviewInfo(InterviewEditDto dto);

    /**
     * 批量自动分配 Reviewer发送邮件
     *
     * @param voList 面试列表
     */
    void mailSend4editReviewerAutoAssignByIdList(List<InterviewResumeVo> voList);

    /**
     * 上传简历发送邮件
     *
     * @param dto 简历信息
     * @param id 面试 ID
     */
    void mailSend4addResumeInterview(ResumeInterviewDto dto, String id);
}
