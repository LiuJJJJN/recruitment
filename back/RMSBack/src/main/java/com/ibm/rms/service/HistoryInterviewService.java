package com.ibm.rms.service;

import com.ibm.rms.pojo.dto.HistoryPageConditionDto;
import com.ibm.rms.pojo.vo.HistoryInterviewVo;


import java.util.List;

/**
 * 数据字典类型管理数据服务层接口
 *
 * @author 耿晨
 * @date 2022-12-20 16:02:18
 */
public interface HistoryInterviewService {
    /**
     * 根据分页和模糊查询数据获取对应数据列表
     *
     * @param dto 页码、数据量、模糊查询值
     * @return 数据列表
     */
    List<HistoryInterviewVo> getHistoryListByPageCondition(HistoryPageConditionDto dto);

    /**
     * 获取当前数据字典类型表数据总量
     *
     * @return 总量
     */
    Long getHistoryTotalSizeByCondition(HistoryPageConditionDto dto);

  
    /**
     * 根据 id 修改数据字典类型表数据
     *
     * @param dictionaryType 修改内容
     */
    void editHistoryInberview(String id);

}
