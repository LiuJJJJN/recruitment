package com.ibm.rms.controller;

import java.util.Map;

import com.ibm.rms.pojo.dto.FeedbackDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.exception.CVReviewException;
import com.ibm.rms.pojo.Resume;
import com.ibm.rms.pojo.vo.InterviewResumeVo;
import com.ibm.rms.pojo.vo.ResumeInterviewVo;
import com.ibm.rms.service.CVReviewService;
import com.ibm.rms.service.FeedBackService;

/**
 * 面试反馈控制层
 *
 * @author 陈亚明
 * @date 2022-12-5 16:46:30
 */

@RestController
@RequestMapping("/feedBack")

public class FeedBackController {
    @Autowired
    private FeedBackService feedBackService;
	@PostMapping("/getInterviewResumeInformation")
	public ResponseResult<Object> getInterviewResumeInformation(@RequestBody Map<String, String> map) {
		String id = null;
        try {
            id = map.get("id");
        } catch (Exception e) {
           //throw new CVReviewException(ErrorResultEnum.PARAM_PARSE_EXCEPTION);
        }
        ResumeInterviewVo interviewResumeVo = feedBackService.getInterviewResumeByInterviewId(id);
		return new ResponseResult<>(200, "获取简历信息成功",interviewResumeVo);
		
	}

    @PostMapping("/addInterviewFeedback")
    public ResponseResult<Object> addInterviewFeedback(@RequestBody FeedbackDto dto) {
        feedBackService.addInterviewFeedback(dto);
        return new ResponseResult<>(200, "面试反馈成功");
    }

}
