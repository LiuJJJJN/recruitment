package com.ibm.rms.mapper;

import com.ibm.rms.pojo.Department;
import com.ibm.rms.pojo.dto.DeptValuePageConditionDto;

import java.util.List;

/**
 * 部门管理数据访问层接口
 *
 * @author 宋月、刘嘉宁
 * @date 2022-12-07 01:51:50
 */
public interface DepartmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    /**
     * 查询部门表所有数据列表
     *
     * @return 部门表所有数据列表
     */
    List<Department> selectAll();

    /**
     * 根据分页和模糊查询数据获取部门列表
     *
     * @param dto 分页和模糊查询数据
     * @return 部门列表
     */
    List<Department> selectAllByPageCondition(DeptValuePageConditionDto dto);

    /**
     * 根据模糊查询数据获取部门数据总量
     *
     * @param dto 模糊查询数据
     * @return 部门数据总量
     */
    Long countDeptListTotalSizeByCondition(DeptValuePageConditionDto dto);
}
