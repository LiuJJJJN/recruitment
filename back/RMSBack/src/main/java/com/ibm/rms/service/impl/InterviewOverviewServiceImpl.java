package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.exception.InterviewOverviewException;
import com.ibm.rms.mapper.InterviewMapper;
import com.ibm.rms.pojo.dto.InterviewEditDto;
import com.ibm.rms.pojo.dto.InterviewOverviewPageConditionDto;
import com.ibm.rms.pojo.dto.StatusEditBatchDto;
import com.ibm.rms.pojo.vo.InterviewResumeExcelVo;
import com.ibm.rms.pojo.vo.InterviewResumeVo;
import com.ibm.rms.service.InterviewOverviewService;
import com.ibm.rms.pojo.Interview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 面试一览服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-11 03:27:56
 */
@Service
@PropertySource("classpath:constValue.properties")
public class InterviewOverviewServiceImpl implements InterviewOverviewService {

    @Autowired
    private InterviewMapper interviewMapper;

    // 面试状态的数据字典类型 code
    @Value("${IV_STATUS}")
    private String IV_STATUS;

    // 用户启用状态的代码, 添加新用户会默认使用此状态值
    @Value("${USER_STATUS_DEFAULT}")
    private Integer USER_STATUS_DEFAULT;

    // 面试状态为等待分配 Reviewer 的数据字典值 valueKey
    @Value("${WAIT_ASSIGN_REVIEWER_STATUS}")
    private String WAIT_ASSIGN_REVIEWER_STATUS;

    // 面试状态为未分配 Interviewer 的数据字典值 valueKey
    @Value("${WAIT_ASSIGN_INTERVIEWER_STATUS}")
    private String WAIT_ASSIGN_INTERVIEWER_STATUS;

    // 面试状态为未确定面试时间的数据字典值 valueKey
    @Value("${WAIT_ASSIGN_INTERVIEW_DATE_STATUS}")
    private String WAIT_ASSIGN_INTERVIEW_DATE_STATUS;

    // 角色为 reviewer 的角色表 name
    @Value("${REVIEWER_ROLE_NAME}")
    private String REVIEWER_ROLE_NAME;

    // 面试状态为等待 Review 的数据字典值 valueKey
    @Value("${WAIT_REVIEW_STATUS}")
    private String WAIT_REVIEW_STATUS;

    // 面试状态为等待面试的数据字典值 valueKey
    @Value("${WAIT_INTERVIEW_STATUS}")
    private String WAIT_INTERVIEW_STATUS;

    @Override
    public List<InterviewResumeVo> getInterviewOverviewListByPageCondition(InterviewOverviewPageConditionDto dto) {
        // 执行查询操作
        List<InterviewResumeVo> interviewResumeVoList = interviewMapper.selectAllByPageCondition(dto, IV_STATUS);
        // 如果查询失败则抛出异常
        if (interviewResumeVoList == null) {
            throw new InterviewOverviewException(ErrorResultEnum.INTERVIEW_OVERVIEW_SELECT_FAIL);
        }
        return interviewResumeVoList;
    }

    @Override
    public Long getInterviewOverviewTotalSize(InterviewOverviewPageConditionDto dto) {
        // 执行查询操作
        Long totalSize = interviewMapper.selectInterviewOverviewTotalSize(dto);
        // 如果查询失败则抛出异常
        if (totalSize == null) {
            throw new InterviewOverviewException(ErrorResultEnum.INTERVIEW_OVERVIEW_SELECT_FAIL);
        }
        return totalSize;
    }

    @Override
    public List<InterviewResumeExcelVo> getInterviewOverviewListByIdList(List<String> idList) {
        // 执行查询操作
        List<InterviewResumeExcelVo> interviewResumeVoList = interviewMapper.selectAllByIdList(idList);
        // 如果查询失败则抛出异常
        if (interviewResumeVoList == null) {
            throw new InterviewOverviewException(ErrorResultEnum.INTERVIEW_OVERVIEW_SELECT_FAIL);
        }
        return interviewResumeVoList;
    }

    @Override
    @Transactional(rollbackFor = InterviewOverviewException.class)
    public void editStatusBatchByStatusEditBatchDto(StatusEditBatchDto dto) {
        // 获取当前用户 ID
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = loginUser.getUser().getId();
        // 执行修改操作
        for (InterviewResumeVo vo : dto.getTargetList()) {
            int result = interviewMapper.updateStatusByPrimary(vo, dto.getInterviewStatus(), id);
            // 如果查询失败则抛出异常, 进行回滚
            if (result != 1) {
                throw new InterviewOverviewException(ErrorResultEnum.INTERVIEW_OVERVIEW_UPDATE_FAIL);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = InterviewOverviewException.class)
    public void autoAssignReviewer(List<InterviewResumeVo> voList) {
        // 获取当前用户 ID
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = loginUser.getUser().getId();
        // 执行自动分配 Reviewer
        for (InterviewResumeVo vo : voList) {
            // 判断是否需要自动调节面试状态
            if (!WAIT_ASSIGN_REVIEWER_STATUS.equals(vo.getStatus().toString())) {
                // 如果当前状态不是等待分配 Reviewer 则不修改状态
                WAIT_REVIEW_STATUS = null;
            }
            int result = interviewMapper.updateReviewerAutoAssign(
                    vo.getId(),
                    vo.getModifyDate(),
                    REVIEWER_ROLE_NAME,
                    WAIT_REVIEW_STATUS,
                    id,
                    USER_STATUS_DEFAULT);
            if (result != 1) {
                throw new InterviewOverviewException(ErrorResultEnum.INTERVIEW_OVERVIEW_UPDATE_FAIL);
            }
        }
    }

    @Override
    public Interview getInterviewById(String id) {
        // 执行查询操作
        Interview interview = interviewMapper.selectByPrimaryKey(id);
        // 如果查询失败则抛出异常
        if (interview == null) {
            throw new InterviewOverviewException(ErrorResultEnum.INTERVIEW_OVERVIEW_SELECT_FAIL);
        }
        return interview;
    }

    @Override
    public void editInterviewInfo(InterviewEditDto dto) {
        // 获取当前用户 ID
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = loginUser.getUser().getId();
        // 分析修改后的面试状态
        String status = null;
        if (dto.getReviewer() == null || "".equals(dto.getReviewer())) {
            // 如果没选择 Reviewer 当前状态应为: 未分配 Reviewer
            status = WAIT_ASSIGN_REVIEWER_STATUS;
        } else if (dto.getInterviewer1() == null || "".equals(dto.getInterviewer1())) {
            // 如果没选择 Interviewer 当前状态应为: 等待 Review
            status = WAIT_REVIEW_STATUS;
        } else if (dto.getDate() == null) {
            // 如果没选择 Date 当前状态应为: 未确定面试时间
            status = WAIT_ASSIGN_INTERVIEW_DATE_STATUS;
        } else {
            // 当前状态应为: 等待面试
            status = WAIT_INTERVIEW_STATUS;
        }
        // 执行更新操作
        int result = interviewMapper.updateByDtoSelective(dto, id, status);
        // 如果更新失败则抛出异常
        if (result != 1) {
            throw new InterviewOverviewException(ErrorResultEnum.INTERVIEW_OVERVIEW_UPDATE_FAIL);
        }
    }

}
