package com.ibm.rms.service;


import java.util.List;
import java.util.Map;

import com.ibm.rms.pojo.dto.InterviewConditionDto;

/**
 * 工作台服务层
 *
 * @author 耿晨
 * @date 2022-12-3 11:46:30
 */
public interface WorkBenchService {
	/**
	 * 获取任务量
	 * @return  任务量列表，第一项是总任务量，第二项是用户的任务量，第三项是今天的任务量
	 */
	Map<String,Object> getInterviewStates(InterviewConditionDto dto);

}
