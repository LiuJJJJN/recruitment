package com.ibm.rms.controller;

import com.ibm.rms.pojo.dto.DeptValuePageConditionDto;
import com.ibm.rms.service.DeptManageService;
import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门管理控制层
 *
 * @author 宋月、刘嘉宁
 * @date 2022-12-07 01:51:30
 */
@RestController
@RequestMapping("/deptManage")
public class DeptManageController {

    @Autowired
    private DeptManageService deptManageService;

    /**
     * 获取部门列表
     *
     * @return 部门列表
     */
    @PostMapping("/getDeptList")
    public ResponseResult<Object> getDeptList() {
        List<Department> departmentList = deptManageService.getDeptList();
        return new ResponseResult<>(200, "部门列表获取成功", departmentList);
    }

    /**
     * 根据分页和模糊查询数据获取部门列表
     *
     * @param dto 分页和模糊查询数据
     * @return 部门列表
     */
    @PostMapping("/getDeptListByPageCondition")
    public ResponseResult<Object> getDeptListByPageCondition(@RequestBody DeptValuePageConditionDto dto){
       List<Department> voList = deptManageService.getDeptListByPageCondition(dto);
       return new ResponseResult<>(200, "获取部门列表成功", voList);
    }

    /**
     * 根据模糊查询数据获取部门数据总量
     *
     * @param dto 模糊查询数据
     * @return 部门数据总量
     */
    @PostMapping("/getDeptListTotalSizeByCondition")
    public ResponseResult<Object> getDeptListTotalSizeByCondition(@RequestBody DeptValuePageConditionDto dto){
       Long totalSize = deptManageService.getDeptListTotalSizeByCondition(dto);
       return new ResponseResult<>(200, "获取部门数据总量成功", totalSize);
    }

}
