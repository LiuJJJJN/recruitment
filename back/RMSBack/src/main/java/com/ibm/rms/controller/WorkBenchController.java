package com.ibm.rms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.pojo.dto.InterviewConditionDto;
import com.ibm.rms.service.WorkBenchService;


/**
 * 工作台控制层
 *
 * @author 耿晨
 * @date 2022-12-3 11:46:30
 */
@RestController
@RequestMapping("/workbench")
public class WorkBenchController {

	@Autowired
	private WorkBenchService workBenchService;
	
	/**
	 * 获取任务量
	 * @param dto 分页数据和日期
	 * @return 任务量和获取成功提示
	 */
	@PostMapping("/getInterviewStates")
	public ResponseResult<Map<String,Object>> getInterviewStates(@RequestBody InterviewConditionDto dto){
		Map<String,Object> missions=workBenchService.getInterviewStates(dto);
		return new  ResponseResult<>(200,"获取任务量成功",missions);
	}
	


}
