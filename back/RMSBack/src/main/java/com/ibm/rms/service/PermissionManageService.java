package com.ibm.rms.service;

import com.ibm.rms.pojo.dto.PageConditionDto;
import com.ibm.rms.pojo.vo.PermissionVo;
import com.ibm.rms.pojo.Permission;

import java.util.List;

/**
 * 权限管理服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-07 21:27:42
 */
public interface PermissionManageService {

    /**
     * 获取权限基础数据: id、权限描述
     *
     * @return 权限基础数据 vo 类列表
     */
    List<PermissionVo> getPermissionVoList();

    /**
     *根据角色 id 获取对应权限 id 列表
     *
     * @param id 角色 id
     * @return 对应权限 id 列表
     */
    List<String> getPermissionIdListByRoleId(String id);

    /**
     * 获取权限数据列表
     *
     * @return 权限数据列表
     */
    List<Permission> getPermissionList();

    /**
     * 添加权限
     *
     * @param permission 新的权限信息
     */
    void addPermission(Permission permission);

    /**
     * 根据 id 查询权限信息
     *
     * @param id 权限 ID
     * @return 对应权限信息
     */
    Permission getPermissionById(String id);

    /**
     * 根据权限 id 修改权限数据
     *
     * @param permission 权限 ID 和新权限数据
     */
    void editPermission(Permission permission);

    /**
     * 根据分页数据获取权限列表
     *
     * @param dto 分页数据
     * @return 权限列表
     */
    List<Permission> getPermissionListByPageCondition(PageConditionDto dto);

    /**
     * 获取权限数据总量
     *
     * @return 权限数据总量
     */
    Long getPermissionTotalSize();

    /**
     * 根据当前登录账户获取对应权限路径列表
     *
     * @return 对应权限路径列表
     */
    List<Permission> getPermissionPathList();

    /**
     * 根据当前登录账户获取对应动作路径列表
     *
     * @return 对应动作路径列表
     */
    List<Permission> getOverviewPathList();
}
