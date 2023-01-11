package com.ibm.rms.controller;

import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.pojo.DictionaryType;
import com.ibm.rms.pojo.dto.HistoryPageConditionDto;
import com.ibm.rms.pojo.vo.HistoryInterviewVo;
import com.ibm.rms.service.HistoryInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据字典类型管理功能控制层
 *
 * @author 耿晨
 * @date 2022-12-20 22:22:49
 */
@RestController
@RequestMapping("/history")
public class HistoryInterviewController {

    @Autowired
    private HistoryInterviewService historyInterviewService;

    /**
     * 根据分页和模糊查询数据获取数据字典类型表对应数据列表
     *
     * @param dto 页码、数据量、模糊查询值
     * @return 对应数据 List 列表
     */
    @PostMapping("/getHistoryInterviewByPageCondition")
    public ResponseResult<List<HistoryInterviewVo>> getHistoryListByPageCondition(@RequestBody HistoryPageConditionDto dto) {
        List<HistoryInterviewVo> historyList = historyInterviewService.getHistoryListByPageCondition(dto);
        return new ResponseResult<>(200, "获取历史面试列表成功", historyList);
    }

    

    /**
     * 获取当前数据字典类型总量
     *
     * @return 当前数据字典类型总量
     */
    @PostMapping("/getHistoryInterviewTotalSizeByCondition")
    public ResponseResult<Long> getHistoryTotalSizeByCondition(@RequestBody HistoryPageConditionDto dto) {
        Long totalSize = historyInterviewService.getHistoryTotalSizeByCondition(dto);
        return new ResponseResult<>(200, "获取历史面试总量成功", totalSize);
    }

 

    /**
     * 编辑数据字典类型数据
     *
     * @param dictionaryType 修改后内容
     * @return 修改成功提示
     */
    @PostMapping(value = "/revokeHistoryInterview")
    public ResponseResult<DictionaryType> editHistoryInterview(@RequestBody Map<String, String> map) {
        historyInterviewService.editHistoryInberview(map.get("id"));
        return new ResponseResult<>(200, "撤回成功");
    }

}
