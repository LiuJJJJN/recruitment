package com.ibm.rms.controller;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.exception.CVReviewException;
import com.ibm.rms.pojo.dto.CVReviewDto;
import com.ibm.rms.service.CVReviewService;
import com.ibm.rms.service.ResumeService;
import com.ibm.rms.pojo.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 简历 Review 控制层
 *
 * @author 刘嘉宁
 * @date 2022-12-18 13:40:15
 */
@RestController
@RequestMapping("/cvReview")
public class CVReviewController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private CVReviewService cvReviewService;

    /**
     * 根据面试 ID 获取对应简历信息
     *
     * @param map 面试 ID
     * @return 对应简历信息
     */
    @PostMapping("/getResumeById")
    public ResponseResult<Object> getResumeById(@RequestBody Map<String, String> map) {
        String id = null;
        try {
            id = map.get("id");
        } catch (Exception e) {
            throw new CVReviewException(ErrorResultEnum.PARAM_PARSE_EXCEPTION);
        }
        Resume resume = resumeService.getResumeByInterviewId(id);
        return new ResponseResult<>(200, "获取简历信息成功", resume);
    }


    /**
     * 提交简历 Review 结果
     *
     * @param dto 面试 ID, 简历信息, Review 结果, 备注
     * @return 成功提示
     */
    @PostMapping("/submitReviewResult")
    public ResponseResult<Object> submitReviewResult(@RequestBody CVReviewDto dto) {
        cvReviewService.submitReviewResult(dto);
        return new ResponseResult<>(200, "简历 Review 成功");
    }

}
