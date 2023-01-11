package com.ibm.rms.mapper;

import com.ibm.rms.pojo.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限表数据访问层
 *
 * @author 刘嘉宁
 * @date 2022-12-07 01:15:26
 */
public interface RolePermissionMapper {

    int deleteByPrimaryKey(String id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RolePermission record);

    /**
     * 根据角色 id 删除其对应所有权限
     *
     * @param roleId 角色 id
     * @return 影响条数
     */
    Integer deleteByRoleId(String roleId);

    /**
     * 根据角色 id 和权限 id 列表插入对应数据
     *
     * @param roleId 角色 id
     * @param idList 权限 id 列表
     * @return 影响条数
     */
    Integer insertSelectiveList(@Param("roleId") String roleId, @Param("idList") List<String> idList);

}
