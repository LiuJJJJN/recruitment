package com.ibm.rms.service;

import com.ibm.rms.pojo.dto.InterviewEditDto;
import com.ibm.rms.pojo.dto.InterviewOverviewPageConditionDto;
import com.ibm.rms.pojo.dto.StatusEditBatchDto;
import com.ibm.rms.pojo.vo.InterviewResumeExcelVo;
import com.ibm.rms.pojo.vo.InterviewResumeVo;
import com.ibm.rms.pojo.Interview;

import java.util.List;

/**
 * 面试一览服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-11 03:09:15
 */
public interface InterviewOverviewService {

    /**
     * 根据分页和模糊查询数据获取对应面试及简历信息数据列表
     *
     * @param dto 分页和模糊查询数据
     * @return 对应面试及简历信息数据列表
     */
    List<InterviewResumeVo> getInterviewOverviewListByPageCondition(InterviewOverviewPageConditionDto dto);

    /**
     * 根据分页和模糊查询数据获取对应面试及简历信息数据总量
     *
     * @param dto 分页和模糊查询数据
     * @return 对应面试及简历信息数据总量
     */
    Long getInterviewOverviewTotalSize(InterviewOverviewPageConditionDto dto);

    /**
     * 根据 id 列表获取对应的数据列表
     *
     * @param idList 面试 ID 列表
     * @return 对应的数据列表
     */
    List<InterviewResumeExcelVo> getInterviewOverviewListByIdList(List<String> idList);

    /**
     *根据根据 id 列表批量修改其状态值
     *
     * @param dto 面试信息列表及状态值
     */
    void editStatusBatchByStatusEditBatchDto(StatusEditBatchDto dto);

    /**
     * 根据 id 列表自动分配 Reviewer
     *
     * @param voList ID 列表
     */
    void autoAssignReviewer(List<InterviewResumeVo> voList);

    /**
     * 根据 id 获取面试信息
     *
     * @param id 面试 ID
     * @return 面试信息
     */
    Interview getInterviewById(String id);

    /**
     * 修改面试信息
     *
     * @param dto 面试信息
     */
    void editInterviewInfo(InterviewEditDto dto);
}
