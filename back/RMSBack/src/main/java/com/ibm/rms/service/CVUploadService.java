package com.ibm.rms.service;

import java.util.List;

import com.ibm.rms.pojo.DictionaryValue;
import com.ibm.rms.pojo.dto.ResumeInterviewDto;

public interface CVUploadService {

	List<DictionaryValue> getEducationList();

    /**
     * 简历数据保存
     *
     * @param dto 简历数据
     * @return 面试 ID
     */
    String addResumeInterview(ResumeInterviewDto dto);
}
