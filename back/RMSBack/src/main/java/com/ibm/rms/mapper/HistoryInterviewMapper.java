package com.ibm.rms.mapper;

import com.ibm.rms.pojo.dto.HistoryPageConditionDto;
import com.ibm.rms.pojo.vo.HistoryInterviewVo;

import java.util.List;

/**
 * 数据字典类型管理数据访问层接口
 *
 * @author 耿晨
 * @date 2022-12-20 00:02:18
 */
public interface HistoryInterviewMapper {



    /**
     * 添加对应数据
     *
     * @param record id 及要添加的数据
     * @return 影响条数
     */
    int insertNewInterview(String id,String userId);

    /**
     * 根据 id 修改对应数据
     *
     * @param record id 及要修改的数据
     * @return 影响条数
     */
    int updateInterviewStatusToHoldOn(String id);

    /**
     * 根据分页数据和模糊查询数据查询对应数据列表
     *
     * @param dto 页码、数据量、模糊查询值
     * @return 数据列表
     */
    List<HistoryInterviewVo> selectAllByPageCondition(HistoryPageConditionDto dto);

    /**
     * 获取当前数据字典类型表的数据总量
     *
     * @return 数据总量
     */
    Long selectTotalSizeByCondition(HistoryPageConditionDto dto);

   

}
