package com.ibm.rms.service.impl;

import java.util.Date;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.exception.InterviewOverviewException;
import com.ibm.rms.exception.UserManageException;
import com.ibm.rms.mapper.UserMapper;
import com.ibm.rms.mapper.UserRoleMapper;
import com.ibm.rms.pojo.dto.UserAddDto;
import com.ibm.rms.pojo.dto.UserEditDto;
import com.ibm.rms.pojo.dto.UserPageConditionDto;
import com.ibm.rms.pojo.vo.UserVo;
import com.ibm.rms.service.UserManageService;
import com.ibm.rms.pojo.User;
import com.ibm.rms.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:07:43
 */
@Service
@PropertySource("classpath:constValue.properties")
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 用户启用状态的代码, 添加新用户会默认使用此状态值
    @Value("${USER_STATUS_DEFAULT}")
    private Integer USER_STATUS_DEFAULT;

    // 用户锁定状态的代码
    @Value("${USER_STATUS_LOCK}")
    private Integer USER_STATUS_LOCK;

    // 角色为 reviewer 的角色表 name
    @Value("${REVIEWER_ROLE_NAME}")
    private String REVIEWER_ROLE_NAME;

    // 角色为 gl 的角色表 name
    @Value("${GL_ROLE_NAME}")
    private String GL_ROLE_NAME;

    @Override
    public List<UserVo> getUserListByPageCondition(UserPageConditionDto dto) {
        // 执行查询操作, 查询用户列表
        List<UserVo> userVoList = userMapper.selectAllByPageCondition(dto);
        // 如果查询失败则抛出异常
        if (userVoList == null) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_SELECT_FAIL);
        }
        return userVoList;
    }

    @Override
    public long getUserTotalSizeByPageCondition(UserPageConditionDto dto) {
        // 执行查询操作
        return userMapper.selectTotalSizeByPageCondition(dto);
    }

    @Override
    public void editUserStatus(String id, Integer status) {
        // 封装用户实体类
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        // 执行修改操作
        int result = userMapper.updateByPrimaryKeySelective(user);
        // 如果修改失败则抛出异常
        if (result != 1) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_UPDATE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = UserManageException.class)
    public void editUserStatus0Batch(List<String> idList) {
        for (String id : idList) {
            User user = new User();
            user.setId(id);
            user.setStatus(USER_STATUS_DEFAULT);
            // 执行修改操作
            int result = userMapper.updateByPrimaryKeySelective(user);
            // 如果修改失败则抛出异常
            if (result != 1) {
                throw new UserManageException(ErrorResultEnum.USER_MANAGE_UPDATE_FAIL);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = UserManageException.class)
    public void editUserStatus1Batch(List<String> idList) {
        for (String id : idList) {
            User user = new User();
            user.setId(id);
            user.setStatus(USER_STATUS_LOCK);
            // 执行修改操作
            int result = userMapper.updateByPrimaryKeySelective(user);
            // 如果修改失败则抛出异常
            if (result != 1) {
                throw new UserManageException(ErrorResultEnum.USER_MANAGE_UPDATE_FAIL);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = UserManageException.class)
    public void addUser(UserAddDto dto) {
        // 获取一个 UUID
        String userId = userMapper.selectUUID();
        // 创建用户实体类
        User user = new User();
        user.setId(userId);
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setSignInDate(new Date());
        user.setEmail(dto.getEmail());
        user.setStatus(USER_STATUS_DEFAULT);
        user.setDeptId(dto.getDept());
        // 插入用户数据
        int result = userMapper.insert(user);
        // 如果插入失败则抛出异常回滚
        if (result != 1) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_INSERT_FAIL);
        }
        for (String roelId : dto.getRole()) {
            // 创建用户角色实体类
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roelId);
            result = userRoleMapper.insert(userRole);
            // 如果插入失败则抛出异常回滚
            if (result != 1) {
                throw new UserManageException(ErrorResultEnum.USER_MANAGE_INSERT_FAIL);
            }
        }
    }

    @Override
    public UserVo getUserById(String id) {
        // 执行查询操作
        UserVo userVo = userMapper.selectUserVoById(id);
        // 如果查询失败则抛出异常
        if (userVo == null) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_SELECT_FAIL);
        }
        return userVo;
    }

    @Override
    @Transactional(rollbackFor = UserManageException.class)
    public void editUserById(UserEditDto dto) {
        // 加密密码
        if (dto.getPassword() != null && !"".equals(dto.getPassword())) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        // 更新用户表数据
        int result = userMapper.updateByIdSelective(dto);
        // 如果更新失败则抛出异常, 回滚数据
        if (result != 1) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_UPDATE_FAIL);
        }
        // 删除该用户对应用户角色表的数据
        result = userRoleMapper.deleteByUserId(dto.getId());
        // 如果删除失败则抛出异常, 回滚数据
        if (result == 0) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_UPDATE_FAIL);
        }
        // 添加用户角色表数据
        result = userRoleMapper.insertByUserIdAndRoleList(dto.getId(), dto.getRole());
        // 如果更新失败则抛出异常, 回滚数据
        if (result == 0) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_UPDATE_FAIL);
        }
    }

    @Override
    public List<UserVo> getReviewerList() {
        // 查询拥有 Reviewer 角色的用户列表
        List<UserVo> userVoList = userMapper.selectUserVoByRole(REVIEWER_ROLE_NAME, USER_STATUS_DEFAULT);
        // 如果查询失败则抛出异常
        if (userVoList == null) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_SELECT_FAIL);
        }
        return userVoList;
    }

    @Override
    public List<UserVo> getInterviewerList() {
        // 查询拥有 GL 角色的用户列表
        List<UserVo> userVoList = userMapper.selectUserVoByRole(GL_ROLE_NAME, USER_STATUS_DEFAULT);
        // 如果查询失败则抛出异常
        if (userVoList == null) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_SELECT_FAIL);
        }
        return userVoList;
    }

    @Override
    public UserVo getReviewerAutoAssign() {
        // 查询当前最闲且拥有 Reviewer 角色的用户
        UserVo user = userMapper.selectReviewerAutoAssign(REVIEWER_ROLE_NAME, USER_STATUS_DEFAULT);
        // 如果查询失败则抛出异常
        if (user == null) {
            throw new InterviewOverviewException(ErrorResultEnum.INTERVIEW_OVERVIEW_SELECT_FAIL);
        }
        return user;
    }

    @Override
    public void editPassword(User user) {
        // 获取到当前用户 ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId();
        user.setId(userId);
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 执行修改操作
        int result = userMapper.updateByPrimaryKeySelective(user);
        // 如果修改失败则抛出异常
        if (result != 1) {
            throw new UserManageException(ErrorResultEnum.USER_MANAGE_UPDATE_FAIL);
        }
    }

}
