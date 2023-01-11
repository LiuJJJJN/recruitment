package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.exception.ResumeException;
import com.ibm.rms.mapper.ResumeMapper;
import com.ibm.rms.service.ResumeService;
import com.ibm.rms.pojo.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 简历服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-18 13:39:28
 */
@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public Resume getResumeByInterviewId(String interviewId) {
        // 根据面试 ID 查询简历
        Resume resume = resumeMapper.selectByInterviewId(interviewId);
        // 如果查询失败则抛出异常
        if (resume == null) {
            throw new ResumeException(ErrorResultEnum.RESUME_SELECT_FAIL);
        }
        return resume;
    }
}
