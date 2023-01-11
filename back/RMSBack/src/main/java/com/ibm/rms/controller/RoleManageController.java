package com.ibm.rms.controller;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.exception.RoleManageException;
import com.ibm.rms.pojo.dto.PageConditionDto;
import com.ibm.rms.service.RoleManageService;
import com.ibm.rms.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 角色管理控制层
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:10:16
 */
@RestController
@RequestMapping("/roleManage")
public class RoleManageController {

    @Autowired
    private RoleManageService roleService;

    /**
     * 获取所有角色列表
     *
     * @return 所有角色列表
     */
    @PostMapping("/getRoleList")
    public ResponseResult<Object> getRoleList() {
        List<Role> roleList = roleService.getRoleList();
        return new ResponseResult<>(200, "角色列表获取成功", roleList);
    }

    /**
     * 获取所有角色列表
     *
     * @return 所有角色列表
     */
    @PostMapping("/getRoleListByPageCondition")
    public ResponseResult<Object> getRoleListByPageCondition(@RequestBody PageConditionDto dto) {
        List<Role> roleList = roleService.getRoleListByPageCondition(dto);
        return new ResponseResult<>(200, "角色列表获取成功", roleList);
    }

    /**
     * 获取所有角色总量
     *
     * @return 所有角色总量
     */
    @PostMapping("/getRoleTotalSize")
    public ResponseResult<Long> getRoleTotalSize() {
        Long totalSize = roleService.getRoleTotalSize();
        return new ResponseResult<>(200, "角色总量获取成功", totalSize);
    }

    /**
     * 添加角色
     *
     * @param role 角色信息
     * @return 添加成功提示
     */
    @PostMapping("/addRole")
    public ResponseResult<Object> addRole(@RequestBody Role role) {
        roleService.addRole(role);
        return new ResponseResult<>(200, "角色添加成功");
    }

    /**
     * 修改角色对应的权限列表
     *
     * @param map 角色 id 和 权限 id 列表
     * @return 修改成功提示
     */
    @PostMapping("/editPermission")
    public ResponseResult<Object> editPermission(@RequestBody Map<String, Object> map) {
        String roleId;
        List<String> idList;
        try {
            roleId = (String) map.get("roleId");
            idList = (List<String>) map.get("idList");
        } catch (Exception e) {
            throw new RoleManageException(ErrorResultEnum.ROLE_PERMISSION_UPDATE_FAIL);
        }
        roleService.editPermission(roleId, idList);
        return new ResponseResult<>(200, "角色权限修改成功");
    }

    /**
     * 根据角色 id 获取角色信息
     *
     * @param map 角色 id
     * @return 对应角色信息
     */
    @PostMapping("/getRoleById")
    public ResponseResult<Object> getRoleById(@RequestBody Map<String, String> map) {
        Role role = roleService.getRoleById(map.get("id"));
        return new ResponseResult<>(200, "获取角色信息成功", role);
    }

    /**
     * 根据角色 id 修改角色
     *
     * @param role 角色 id 及角色数据内容
     * @return 成功提示
     */
    @PostMapping("/editRole")
    public ResponseResult<Object> editRole(@RequestBody Role role) {
        roleService.editRole(role);
        return new ResponseResult<>(200, "修改角色信息成功");
    }
}
