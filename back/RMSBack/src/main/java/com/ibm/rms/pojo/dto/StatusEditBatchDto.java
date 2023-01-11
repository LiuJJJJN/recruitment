package com.ibm.rms.pojo.dto;

import com.ibm.rms.pojo.vo.InterviewResumeVo;

import java.util.List;

/**
 * 批量修改面试状态时用到的 dto 类
 *
 * @author 刘嘉宁
 * @date 2022-12-16 10:57:33
 */
public class StatusEditBatchDto {

    // 面试表 id 列表
    private List<InterviewResumeVo> targetList;

    // 状态 valueKey
    private Integer interviewStatus;

    public StatusEditBatchDto() {

    }

    public StatusEditBatchDto(List<InterviewResumeVo> targetList, Integer interviewStatus) {
        this.targetList = targetList;
        this.interviewStatus = interviewStatus;
    }

    public List<InterviewResumeVo> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<InterviewResumeVo> targetList) {
        this.targetList = targetList;
    }

    public Integer getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(Integer interviewStatus) {
        this.interviewStatus = interviewStatus;
    }

    @Override
    public String toString() {
        return "StatusEditBatchDto{" +
                "targetList=" + targetList +
                ", interviewStatus=" + interviewStatus +
                '}';
    }

}
