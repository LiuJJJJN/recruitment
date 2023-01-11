package com.ibm.rms.controller;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.exception.UserManageException;
import com.ibm.rms.pojo.dto.UserAddDto;
import com.ibm.rms.pojo.dto.UserEditDto;
import com.ibm.rms.pojo.dto.UserPageConditionDto;
import com.ibm.rms.pojo.vo.UserVo;
import com.ibm.rms.service.RedisService;
import com.ibm.rms.service.UserManageService;
import com.ibm.rms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 用户管理控制层
 *
 * @author 刘嘉宁
 * @date 2022-12-07 00:06:28
 */
@RestController
@RequestMapping("/userManage")
public class UserManageController {

    @Autowired
    private UserManageService userService;
    @Autowired
    private RedisService redisService;

    /**
     * 根据分页和模糊搜索值查询对应用户数据列表
     *
     * @param dto 分页和模糊查询内容
     * @return 对应用户数据列表
     */
    @PostMapping("/getUserListByPageCondition")
    public ResponseResult<Object> getUserListByPageCondition(@RequestBody UserPageConditionDto dto) {
        List<UserVo> userVoList = userService.getUserListByPageCondition(dto);
        return new ResponseResult<>(200, "用户列表获取成功", userVoList);
    }

    /**
     * 根据模糊搜索值查询对应用户数据总量
     *
     * @param dto 模糊查询内容
     * @return 对应用户数据列表
     */
    @PostMapping("/getUserTotalSizeByPageCondition")
    public ResponseResult<Object> getUserTotalSizeByPageCondition(@RequestBody UserPageConditionDto dto) {
        long totalSize = userService.getUserTotalSizeByPageCondition(dto);
        return new ResponseResult<>(200, "用户数据总量获取成功", totalSize);
    }

    /**
     * 修改用户状态
     *
     * @param map 用户 ID 和 状态的 valueKey
     * @return 成功提示
     */
    @PostMapping("/editUserStatus")
    public ResponseResult<Object> editUserStatus(@RequestBody Map<String, Object> map) {
        String id;
        Integer status;
        try {
            id = (String) map.get("id");
            status = (Integer) map.get("status");
        } catch (Exception e) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_UPDATE_FAIL);
        }
        // 更新数据库中的用户状态信息
        userService.editUserStatus(id, status);
        // 更新 Redis 中的用户信息
        redisService.flushUserById(id);
        return new ResponseResult<>(200, "修改用户状态成功, 如果用户在线其操作可能会受影响");
    }

    /**
     * 批量修改用户状态为 0
     *
     * @param idList 用户 ID 列表
     * @return 修改成功提示
     */
    @PostMapping("/editUserStatus0Batch")
    public ResponseResult<Object> editUserStatus0Batch(@RequestBody List<String> idList) {
        userService.editUserStatus0Batch(idList);
        return new ResponseResult<>(200, "批量修改成功");
    }

    /**
     * 批量修改用户状态为 1
     *
     * @param idList 用户 ID 列表
     * @return 修改成功提示
     */
    @PostMapping("/editUserStatus1Batch")
    public ResponseResult<Object> editUserStatus1Batch(@RequestBody List<String> idList) {
        userService.editUserStatus1Batch(idList);
        return new ResponseResult<>(200, "批量修改成功");
    }

    /**
     * 根据前端发送的用户数据添加用户
     *
     * @param dto 用户信息
     * @return 添加成功提示
     */
    @PostMapping("/addUser")
    public ResponseResult<Object> addUser(@RequestBody UserAddDto dto) {
        userService.addUser(dto);
        return new ResponseResult<>(200, "添加用户成功");
    }

    /**
     * 根据用户 id 获取对应用户及角色信息
     *
     * @param map 用户 ID
     * @return 对应用户及角色信息
     */
    @PostMapping("/getUserById")
    public ResponseResult<UserVo> getUserById(@RequestBody Map<String, String> map) {
        // 获取前端传值
        String id;
        try {
            id = map.get("id");
        } catch (Exception e) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_SELECT_FAIL);
        }
        // 执行查询操作
        UserVo userVo = userService.getUserById(id);
        return new ResponseResult<>(200, "获取用户数据成功", userVo);
    }

    /**
     * 根据用户 id 修改用户数据及角色列表
     *
     * @param dto 用户数据及角色列表
     * @return 成功提示
     */
    @PostMapping("/editUserById")
    public ResponseResult<UserVo> editUserById(@RequestBody UserEditDto dto) {
        userService.editUserById(dto);
        return new ResponseResult<>(200, "修改用户数据成功");
    }

    /**
     * 获取 Reviewer 列表
     *
     * @return Reviewer 列表
     */
    @PostMapping("/getReviewerList")
    public ResponseResult<List<UserVo>> getReviewerList() {
        List<UserVo> userList = userService.getReviewerList();
        return new ResponseResult<>(200, "获取用户列表成功", userList);
    }

    /**
     * 获取 面试官 列表
     *
     * @return 面试官 列表
     */
    @PostMapping("/getInterviewerList")
    public ResponseResult<List<UserVo>> getInterviewerList() {
        List<UserVo> userList = userService.getInterviewerList();
        return new ResponseResult<>(200, "获取用户列表成功", userList);
    }

    /**
     * 获取最闲 Reviewer
     *
     * @return 最闲 Reviewer
     */
    @PostMapping("/getReviewerAutoAssign")
    public ResponseResult<UserVo> getReviewerAutoAssign() {
        UserVo user = userService.getReviewerAutoAssign();
        return new ResponseResult<>(200, "获取最闲 Reviewer 成功", user);
    }

    /**
     * 修改密码
     *
     * @param user 密码
     * @return 成功提示
     */
    @PostMapping("/editPassword")
    public ResponseResult<UserVo> editPassword(@RequestBody User user) {
        userService.editPassword(user);
        return new ResponseResult<>(200, "密码修改成功");
    }

}
