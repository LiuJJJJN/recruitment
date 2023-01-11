package com.ibm.rms.mapper;

import com.ibm.rms.pojo.Permission;
import com.ibm.rms.pojo.dto.PageConditionDto;
import com.ibm.rms.pojo.vo.PermissionVo;

import java.util.List;

/**
 * 权限管理数据访问层
 *
 * @author 刘嘉宁
 * @date 2022-12-07 20:28:28
 */
public interface PermissionMapper {

    int deleteByPrimaryKey(String id);

    int insert(Permission record);

    /**
     * 根据权限实体添加权限数据
     *
     * @param record 权限实体信息
     * @return 影响条数
     */
    int insertSelective(Permission record);

    /**
     * 根据 id 查询对应数据
     *
     * @param id 权限 ID
     * @return 对应数据
     */
    Permission selectByPrimaryKey(String id);

    /**
     * 根据权限 id 修改权限数据
     *
     * @param record 权限 ID 和新权限数据
     * @return 影响条数
     */
    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    /**
     * 查询所有权限列表的简要数据
     *
     * @return 权限 vo 类列表
     */
    List<PermissionVo> selectAllPermissionVoList();

    /**
     * 根据角色 id 查询对应权限 id 列表
     *
     * @param id 角色 id
     * @return 对应权限 id 列表
     */
    List<String> selectAllByRoleId(String id);

    /**
     * 查询所有权限数据列表
     *
     * @return 所有权限数据列表
     */
    List<Permission> selectAll();

    /**
     * 根据分页数据获取权限列表
     *
     * @param dto 分页数据
     * @return 权限列表
     */
    List<Permission> selectAllByPageCondition(PageConditionDto dto);

    /**
     * 获取权限数据总量
     *
     * @return 权限数据总量
     */
    Long countPermissionTotalSize();

    /**
     * 根据用户 ID 查询对应权限名与路径
     *
     * @param id 用户 ID
     * @return 对应权限名和路径列表
     */
    List<Permission> selectNamePathByUserId(String id);

    /**
     * 根据当前登录账户获取对应动作路径列表
     *
     * @param id 用户 ID
     * @return 对应动作路径列表
     */
    List<Permission> getOverviewPathList(String id);

}