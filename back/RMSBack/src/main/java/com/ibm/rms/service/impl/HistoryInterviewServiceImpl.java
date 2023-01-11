package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.exception.DictionaryException;
import com.ibm.rms.exception.HistoryInterviewException;
import com.ibm.rms.mapper.HistoryInterviewMapper;
import com.ibm.rms.pojo.dto.HistoryPageConditionDto;
import com.ibm.rms.pojo.vo.HistoryInterviewVo;
import com.ibm.rms.service.HistoryInterviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典类型管理数据服务层接口实现类
 *
 * @author 耿晨
 * @date 2022-12-20 16:02:18
 */
@Service
public class HistoryInterviewServiceImpl implements HistoryInterviewService {

    @Autowired
    private HistoryInterviewMapper historyInterviewMapper;

   

    @Override
    public List<HistoryInterviewVo> getHistoryListByPageCondition(HistoryPageConditionDto dto) {
        // 执行查询操作
        List<HistoryInterviewVo> historyList = historyInterviewMapper.selectAllByPageCondition(dto);
        // 如果查询出来的数据为 null (空数据应为 [] ) 抛出异常
        if (historyList == null) {
            throw new HistoryInterviewException(ErrorResultEnum.HISTORY_INTERVIEW_SELECT_FAIL);
        }
        return historyList;
    }

    @Override
    public Long getHistoryTotalSizeByCondition(HistoryPageConditionDto dto) {
        // 执行查询操作
        return historyInterviewMapper.selectTotalSizeByCondition(dto);
    }


    @Override
    public void editHistoryInberview(String id) {
    	 // 吧原本的面试状态改成挂起
        int result = historyInterviewMapper.updateInterviewStatusToHoldOn(id);
        // 如果修改失败则抛出异常
        if (result != 1) {
            throw new HistoryInterviewException(ErrorResultEnum.HISTORY_INTERVIEW_UPDATE_FAIL);
        }
        // 获取到当前用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId();
        // 添加新的用户信息
        result = historyInterviewMapper.insertNewInterview(id,userId);
        // 如果添加失败则抛出异常
        if (result != 1) {
            throw new HistoryInterviewException(ErrorResultEnum.HISTORY_INTERVIEW_UPDATE_FAIL);
        }
    }

 

	


}
