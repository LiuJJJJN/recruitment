package com.ibm.rms.mapper;

import com.ibm.rms.pojo.Role;
import com.ibm.rms.pojo.dto.PageConditionDto;

import java.util.List;

/**
 * 角色管理数据访问层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:19:27
 */
public interface RoleMapper {

    int deleteByPrimaryKey(String id);

    int insert(Role record);

    /**
     * 插入角色
     *
     * @param record 角色信息
     * @return 影响条数
     */
    int insertSelective(Role record);

    /**
     * 根据角色 id 查询角色信息
     *
     * @param id 角色 id
     * @return 对应角色信息
     */
    Role selectByPrimaryKey(String id);

    /**
     * 根据角色 id 修改角色信息
     *
     * @param record 角色 id 及角色信息
     * @return 影响条数
     */
    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 查询所有角色数据
     *
     * @return 角色数据列表
     */
    List<Role> selectAll();

    /**
     * 根据分页数据获取角色列表
     *
     * @param dto 分页数据
     * @return 角色列表
     */
    List<Role> selectAllByPageCondition(PageConditionDto dto);

    /**
     * 查询角色表数据总量
     *
     * @return 角色表数据总量
     */
    Long countRoleTotalSize();

}
