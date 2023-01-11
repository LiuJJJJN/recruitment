package com.ibm.rms.service.impl;

import com.ibm.rms.mapper.DepartmentMapper;
import com.ibm.rms.pojo.dto.DeptValuePageConditionDto;
import com.ibm.rms.service.DeptManageService;
import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.exception.DeptManageException;
import com.ibm.rms.pojo.Department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门管理服务层接口实现类
 *
 * @author 宋月、刘嘉宁
 * @date 2022-12-07 01:51:50
 */
@Service
public class DeptManageServiceImpl implements DeptManageService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getDeptList() {
        List<Department> departmentList = departmentMapper.selectAll();
        if (departmentList == null) {
            throw new DeptManageException(ErrorResultEnum.DEPT_MANAGE_SELECT_FAIL);
        }
        return departmentList;

    }

	@Override
	public List<Department> getDeptListByPageCondition(DeptValuePageConditionDto dto) {
        List<Department> departmentList = departmentMapper.selectAllByPageCondition(dto);
        if (departmentList == null) {
            throw new DeptManageException(ErrorResultEnum.DEPT_MANAGE_SELECT_FAIL);
        }
        return departmentList;
	}

    @Override
    public Long getDeptListTotalSizeByCondition(DeptValuePageConditionDto dto) {
        return departmentMapper.countDeptListTotalSizeByCondition(dto);
    }

}
