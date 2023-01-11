package com.ibm.rms.service;

import com.ibm.rms.pojo.dto.CVReviewDto;

/**
 * 面试 Review 服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-18 16:44:00
 */
public interface CVReviewService {

    /**
     * 提交简历 Review 结果
     *
     * @param dto 面试 ID, 简历信息, Review 结果, 备注
     */
    void submitReviewResult(CVReviewDto dto);

}
