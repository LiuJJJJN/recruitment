package com.ibm.rms.service;

import com.ibm.rms.pojo.Resume;

/**
 * 简历服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-18 13:39:28
 */
public interface ResumeService {

    /**
     * 根据面试 ID 获取简历信息
     *
     * @param interviewId 面试 ID
     * @return 简历信息
     */
    Resume getResumeByInterviewId(String interviewId);
}
