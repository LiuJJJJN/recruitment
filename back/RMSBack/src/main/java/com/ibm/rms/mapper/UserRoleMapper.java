package com.ibm.rms.mapper;

import com.ibm.rms.pojo.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色表数据访问层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:55:38
 */
public interface UserRoleMapper {

    int deleteByPrimaryKey(String id);

    /**
     * 插入用户角色数据
     *
     * @param record 用户角色实体类
     * @return 影响条数
     */
    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserRole record);

    /**
     * 根据 userId 删除对应数据
     *
     * @param id 用户 ID
     * @return 影响条数
     */
    int deleteByUserId(String id);

    /**
     * 根据用户 id 和角色 id 列表添加用户角色表数据
     *
     * @param id 用户 ID
     * @param role 角色 ID 列表
     * @return 影响条数
     */
    int insertByUserIdAndRoleList(@Param("userId") String id, @Param("roleIdList") List<String> role);

}
