package com.ibm.rms.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.exception.InterviewOverviewException;
import com.ibm.rms.pojo.dto.InterviewEditDto;
import com.ibm.rms.pojo.dto.InterviewOverviewPageConditionDto;
import com.ibm.rms.pojo.dto.StatusEditBatchDto;
import com.ibm.rms.pojo.vo.InterviewResumeExcelVo;
import com.ibm.rms.pojo.vo.InterviewResumeVo;
import com.ibm.rms.service.InterviewOverviewService;
import com.ibm.rms.service.MailService;
import com.ibm.rms.pojo.Interview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 面试一览画面控制层
 *
 * @author 刘嘉宁
 * @date 2022-12-11 03:01:17
 */
@RestController
@RequestMapping("/interviewOverview")
public class InterviewOverviewController {

    @Autowired
    private InterviewOverviewService interviewOverviewService;

    @Autowired
    private MailService mailService;

    /**
     * 根据分页和模糊查询数据获取对应面试及简历信息数据列表
     *
     * @param dto 分页和模糊查询数据
     * @return 对应面试及简历信息数据列表
     */
    @PostMapping("/getInterviewOverviewListByPageCondition")
    public ResponseResult<List<InterviewResumeVo>> getInterviewOverviewListByPageCondition(
            @RequestBody InterviewOverviewPageConditionDto dto) {
        List<InterviewResumeVo> interviewResumeVoList =
                interviewOverviewService.getInterviewOverviewListByPageCondition(dto);
        return new ResponseResult<>(200, "获取面试一览数据列表成功", interviewResumeVoList);

    }

    /**
     * 根据分页和模糊查询数据获取对应面试及简历信息数据总量
     *
     * @param dto 分页和模糊查询数据
     * @return 对应面试及简历信息数据总量
     */
    @PostMapping("/getInterviewOverviewTotalSize")
    public ResponseResult<Long> getInterviewOverviewTotalSize(@RequestBody InterviewOverviewPageConditionDto dto) {
        Long totalSize = interviewOverviewService.getInterviewOverviewTotalSize(dto);
        return new ResponseResult<>(200, "获取面试一览数据总量成功", totalSize);
    }

    /**
     * 根据 id 列表获取对应的数据并生成 Excel 文件返回
     *
     * @param idList 面试 ID 列表
     */
    @PostMapping("/getExcelByIdList")
    public void getExcelByIdList(HttpServletResponse response, @RequestBody List<String> idList) throws IOException {
        List<InterviewResumeExcelVo> interviewResumeExcelVoList =
                interviewOverviewService.getInterviewOverviewListByIdList(idList);

        // 这里注意 有同学反应使用 swagger 会导致各种问题，请直接用浏览器或者用 postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        EasyExcel.write(response.getOutputStream(), InterviewResumeExcelVo.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("数据")
                .doWrite(interviewResumeExcelVoList);
    }

    /**
     * 根据 id 列表批量修改其状态值
     *
     * @param dto 面试信息列表及状态值
     */
    @PostMapping("/editStatusBatch")
    public ResponseResult<Object> editStatusBatch(@RequestBody StatusEditBatchDto dto) {
        interviewOverviewService.editStatusBatchByStatusEditBatchDto(dto);
        return new ResponseResult<>(200, "更新状态成功");
    }

    /**
     * 批量自动分配 Reviewer
     *
     * @param voList 面试列表
     */
    @PostMapping("/editReviewerAutoAssignByIdList")
    public ResponseResult<Object> editReviewerAutoAssignByIdList(@RequestBody List<InterviewResumeVo> voList) {
        interviewOverviewService.autoAssignReviewer(voList);
        mailService.mailSend4editReviewerAutoAssignByIdList(voList);
        return new ResponseResult<>(200, "自动分配 Reviewer 成功");
    }

    /**
     * 根据 id 获取面试信息
     *
     * @param map 面试 ID
     * @return 面试信息
     */
    @PostMapping("/getInterviewById")
    public ResponseResult<Interview> getInterviewById(@RequestBody Map<String, String> map) {
        // 获取前端传来的 id
        String id = null;
        try {
            id = map.get("id");
        } catch (Exception e) {
            throw new InterviewOverviewException(ErrorResultEnum.INTERVIEW_OVERVIEW_SELECT_FAIL);
        }
        // 调用服务层
        Interview interview = interviewOverviewService.getInterviewById(id);
        return new ResponseResult<>(200, "获取面试信息成功", interview);
    }

    /**
     * 更新面试信息
     *
     * @param dto 面试信息
     * @return 更新成功提示
     */
    @PostMapping("/editInterviewInfo")
    public ResponseResult<Interview> editInterviewInfo(@RequestBody InterviewEditDto dto) {
        interviewOverviewService.editInterviewInfo(dto);
        mailService.mailSend4editInterviewInfo(dto);
        return new ResponseResult<>(200, "更新面试信息成功");
    }

}
