package com.ibm.rms.controller;

import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.pojo.dto.PageConditionDto;
import com.ibm.rms.pojo.vo.PermissionVo;
import com.ibm.rms.service.PermissionManageService;
import com.ibm.rms.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 权限管理控制层
 *
 * @author 刘嘉宁
 * @date 2022-12-07 20:44:58
 */

@RestController
@RequestMapping("/permissionManage")
public class PermissionManageController {

    @Autowired
    private PermissionManageService permissionService;

    /**
     * 获取权限基础数据: id、权限描述
     *
     * @return 权限基础数据 vo 类列表
     */
    @PostMapping("/getPermissionVoList")
    public ResponseResult<List<PermissionVo>> getPermissionVoList() {
        List<PermissionVo> permissionVoList = permissionService.getPermissionVoList();
        return new ResponseResult<>(200, "获取权限内容列表成功", permissionVoList);
    }

    /**
     * 根据角色 id 获取对应权限 id 列表
     *
     * @param map 角色 id
     * @return 对应权限 id 列表
     */
    @PostMapping("/getPermissionIdListByRoleId")
    public ResponseResult<List<String>> getPermissionIdListByRoleId(@RequestBody Map<String, String> map) {
        List<String> permissionIdList = permissionService.getPermissionIdListByRoleId(map.get("id"));
        return new ResponseResult<>(200, "获取权限ID列表成功", permissionIdList);
    }

    /**
     * 获取权限数据列表
     *
     * @return 权限数据列表
     */
    @PostMapping("/getPermissionList")
    public ResponseResult<List<Permission>> getPermissionList() {
        List<Permission> permissionList = permissionService.getPermissionList();
        return new ResponseResult<>(200, "获取权限列表成功", permissionList);
    }

    /**
     * 获取权限数据总量
     *
     * @return 权限数据总量
     */
    @PostMapping("/getPermissionTotalSize")
    public ResponseResult<Long> getPermissionTotalSize() {
        Long totalSize = permissionService.getPermissionTotalSize();
        return new ResponseResult<>(200, "获取权限总量成功", totalSize);
    }

    /**
     * 获取权限数据列表
     *
     * @return 权限数据列表
     */
    @PostMapping("/getPermissionListByPageCondition")
    public ResponseResult<List<Permission>> getPermissionListByPageCondition(@RequestBody PageConditionDto dto) {
        List<Permission> permissionList = permissionService.getPermissionListByPageCondition(dto);
        return new ResponseResult<>(200, "获取权限列表成功", permissionList);
    }

    /**
     * 添加权限
     *
     * @param permission 新的权限信息
     * @return 添加成功提示
     */
    @PostMapping("/addPermission")
    public ResponseResult<Object> addPermission(@RequestBody Permission permission) {
        permissionService.addPermission(permission);
        return new ResponseResult<>(200, "添加权限成功");
    }

    /**
     * 根据权限 ID 查询权限
     *
     * @param map 权限 ID
     * @return 对应权限
     */
    @PostMapping("/getPermissionById")
    public ResponseResult<Permission> getPermissionById(@RequestBody Map<String, String> map) {
        Permission permission = permissionService.getPermissionById(map.get("id"));
        return new ResponseResult<>(200, "权限查询成功", permission);
    }

    /**
     * 根据权限 id 修改权限数据
     *
     * @param permission 权限 ID 和新权限数据
     * @return 修改成功提示
     */
    @PostMapping("/editPermission")
    public ResponseResult<Permission> editPermission(@RequestBody Permission permission) {
        permissionService.editPermission(permission);
        return new ResponseResult<>(200, "修改权限成功");
    }

    /**
     * 根据当前登录账户获取对应权限路径列表
     *
     * @return 对应权限路径列表
     */
    @PostMapping("/getPermissionPathList")
    public ResponseResult<List<Permission>> getPermissionPathList() {
        List<Permission> permissionList = permissionService.getPermissionPathList();
        return new ResponseResult<>(200, "获取权限路径列表成功", permissionList);
    }

    /**
     * 根据当前登录账户获取对应动作路径列表
     *
     * @return 对应动作路径列表
     */
    @PostMapping("/getOverviewPathList")
    public ResponseResult<List<Permission>> getOverviewPathList() {
        List<Permission> permissionList = permissionService.getOverviewPathList();
        return new ResponseResult<>(200, "获取权限路径列表成功", permissionList);
    }

}
