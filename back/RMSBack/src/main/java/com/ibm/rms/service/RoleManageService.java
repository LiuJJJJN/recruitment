package com.ibm.rms.service;

import com.ibm.rms.pojo.dto.PageConditionDto;
import com.ibm.rms.pojo.Role;

import java.util.List;

/**
 * 角色管理服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:14:05
 */
public interface RoleManageService {

    /**
     * 获取所有角色列表
     *
     * @return 所有角色列表
     */
    List<Role> getRoleList();

    /**
     * 添加角色
     *
     * @param role 角色信息
     */
    void addRole(Role role);

    /**
     * 根据角色 id 覆盖其对应权限列表
     *
     * @param roleId 角色 ID
     * @param idList 权限 ID 列表
     */
    void editPermission(String roleId, List<String> idList);

    /**
     * 根据角色 id 获取角色信息
     *
     * @param id 角色 id
     * @return 角色信息
     */
    Role getRoleById(String id);

    /**
     * 根据角色 id 修改角色
     *
     * @param role 角色 id 及角色数据内容
     */
    void editRole(Role role);

    /**
     * 根据分页数据获取角色列表
     *
     * @param dto 分页数据
     * @return 角色列表
     */
    List<Role> getRoleListByPageCondition(PageConditionDto dto);

    /**
     * 获取角色总量
     *
     * @return 角色总量
     */
    Long getRoleTotalSize();
}
