package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.exception.PermissionManageException;
import com.ibm.rms.mapper.PermissionMapper;
import com.ibm.rms.pojo.dto.PageConditionDto;
import com.ibm.rms.pojo.vo.PermissionVo;
import com.ibm.rms.service.PermissionManageService;
import com.ibm.rms.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限管理服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-07 21:28:28
 */
@Service
public class PermissionManageServiceImpl implements PermissionManageService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionVo> getPermissionVoList() {
        // 执行查询操作
        List<PermissionVo> permissionVoList = permissionMapper.selectAllPermissionVoList();
        // 如果查询失败则抛出异常
        if (permissionVoList == null) {
            throw new PermissionManageException(ErrorResultEnum.PERMISSION_MANAGE_SELECT_FAIL);
        }
        return permissionVoList;
    }

    @Override
    public List<String> getPermissionIdListByRoleId(String id) {
        // 执行查询操作
        List<String> idList = permissionMapper.selectAllByRoleId(id);
        // 如果查询失败则抛出异常
        if (idList == null) {
            throw new PermissionManageException(ErrorResultEnum.PERMISSION_MANAGE_SELECT_FAIL);
        }
        return idList;
    }

    @Override
    public List<Permission> getPermissionList() {
        // 执行查询操作
        List<Permission> permissionList = permissionMapper.selectAll();
        // 如果查询失败则抛出异常
        if (permissionList == null) {
            throw new PermissionManageException(ErrorResultEnum.PERMISSION_MANAGE_SELECT_FAIL);
        }
        return permissionList;
    }

    @Override
    public void addPermission(Permission permission) {
        // 执行添加操作
        int result = permissionMapper.insertSelective(permission);
        // 如果添加失败则抛出异常
        if (result == 0) {
            throw new PermissionManageException(ErrorResultEnum.PERMISSION_MANAGE_INSERT_FAIL);
        }
    }

    @Override
    public Permission getPermissionById(String id) {
        // 执行查询操作
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        // 如果查询失败则抛出异常
        if (permission == null) {
            throw new PermissionManageException(ErrorResultEnum.PERMISSION_MANAGE_SELECT_FAIL);
        }
        return permission;
    }

    @Override
    public void editPermission(Permission permission) {
        // 执行修改操作
        int result = permissionMapper.updateByPrimaryKeySelective(permission);
        // 如果修改失败则抛出异常
        if (result != 1) {
            throw new PermissionManageException(ErrorResultEnum.PERMISSION_MANAGE_UPDATE_FAIL);
        }
    }

    @Override
    public List<Permission> getPermissionListByPageCondition(PageConditionDto dto) {
        // 执行查询操作
        List<Permission> permissionList = permissionMapper.selectAllByPageCondition(dto);
        // 如果查询失败则抛出异常
        if (permissionList == null) {
            throw new PermissionManageException(ErrorResultEnum.PERMISSION_MANAGE_SELECT_FAIL);
        }
        return permissionList;
    }

    @Override
    public Long getPermissionTotalSize() {
        // 执行查询操作
        Long totalSize = permissionMapper.countPermissionTotalSize();
        return totalSize;
    }

    @Override
    public List<Permission> getPermissionPathList() {
        // 获取当前用户 ID
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = loginUser.getUser().getId();
        // 根据用户 ID 查询对应权限名与路径
        List<Permission> permissionList = permissionMapper.selectNamePathByUserId(id);
        // 如果查询失败则抛出异常
        if (permissionList == null) {
            throw new PermissionManageException(ErrorResultEnum.PERMISSION_PATH_SELECT_FAIL);
        }
        return permissionList;
    }

    @Override
    public List<Permission> getOverviewPathList() {
        // 获取当前用户 ID
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = loginUser.getUser().getId();
        // 根据用户 ID 查询对应权限名与路径
        List<Permission> permissionList = permissionMapper.getOverviewPathList(id);
        // 如果查询失败则抛出异常
        if (permissionList == null) {
            throw new PermissionManageException(ErrorResultEnum.PERMISSION_PATH_SELECT_FAIL);
        }
        return permissionList;
    }
}
