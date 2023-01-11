package com.ibm.rms.service;

import com.ibm.rms.pojo.dto.DeptValuePageConditionDto;
import com.ibm.rms.pojo.Department;

import java.util.List;

/**
 * 部门管理服务层接口
 *
 * @author 宋月、刘嘉宁
 * @date 2022-12-07 01:51:50
 */
public interface DeptManageService {

    /**
     * 获取部门列表
     *
     * @return 部门列表
     */
    List<Department> getDeptList();

    /**
     * 根据分页和模糊查询数据获取部门列表
     *
     * @param dto 分页和模糊查询数据
     * @return 部门列表
     */
	List<Department> getDeptListByPageCondition(DeptValuePageConditionDto dto);

    /**
     * 根据模糊查询数据获取部门数据总量
     *
     * @param dto 模糊查询数据
     * @return 部门数据总量
     */
    Long getDeptListTotalSizeByCondition(DeptValuePageConditionDto dto);

}
