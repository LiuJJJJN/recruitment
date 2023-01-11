package com.ibm.rms.service;

import com.ibm.rms.pojo.dto.UserAddDto;
import com.ibm.rms.pojo.dto.UserEditDto;
import com.ibm.rms.pojo.dto.UserPageConditionDto;
import com.ibm.rms.pojo.vo.UserVo;
import com.ibm.rms.pojo.User;

import java.util.List;

/**
 * 用户管理服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:07:43
 */
public interface UserManageService {

    /**
     * 根据分页和模糊搜索数据查询用户数据列表
     *
     * @param dto 分页和模糊搜索数据
     * @return 对应的用户数据列表
     */
    List<UserVo> getUserListByPageCondition(UserPageConditionDto dto);

    /**
     * 根据模糊搜索值查询对应用户数据总量
     *
     * @param dto 模糊查询内容
     * @return 对应用户数据列表
     */
    long getUserTotalSizeByPageCondition(UserPageConditionDto dto);

    /**
     * 修改用户状态
     *
     * @param id 用户 ID
     * @param status 新状态
     */
    void editUserStatus(String id, Integer status);

    /**
     * 批量修改用户状态为 0
     *
     * @param idList 用户 ID 列表
     */
    void editUserStatus0Batch(List<String> idList);

    /**
     * 批量修改用户状态为 1
     *
     * @param idList 用户 ID 列表
     */
    void editUserStatus1Batch(List<String> idList);

    /**
     * 根据前端发送的用户数据添加用户
     *
     * @param dto 用户信息
     */
    void addUser(UserAddDto dto);

    /**
     * 根据用户 id 获取对应用户及角色信息
     *
     * @param id 用户 ID
     * @return 对应用户及角色信息
     */
    UserVo getUserById(String id);

    /**
     * 根据用户 id 修改用户数据及角色列表
     *
     * @param dto 用户数据及角色列表
     */
    void editUserById(UserEditDto dto);

    /**
     * 获取角色含有 Reviewer 的用户列表
     *
     * @return 角色含有 Reviewer 的用户列表
     */
    List<UserVo> getReviewerList();

    /**
     * 获取角色含有 GL 的用户列表
     *
     * @return 角色含有 GL 的用户列表
     */
    List<UserVo> getInterviewerList();

    /**
     * 获取最闲 Reviewer
     *
     * @return 最闲 Reviewer
     */
    UserVo getReviewerAutoAssign();

    /**
     * 修改当前登录用户密码
     *
     * @param user 密码
     */
    void editPassword(User user);
}
