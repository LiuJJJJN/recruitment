package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.exception.RoleManageException;
import com.ibm.rms.mapper.RolePermissionMapper;
import com.ibm.rms.pojo.dto.PageConditionDto;
import com.ibm.rms.service.RoleManageService;
import com.ibm.rms.mapper.RoleMapper;
import com.ibm.rms.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-07 02:15:26
 */
@Service
@PropertySource("classpath:constValue.properties")
public class RoleManageServiceImpl implements RoleManageService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    // 数据库存储的角色默认前缀
    @Value("${ROLE_PREFIX}")
    private String ROLE_PREFIX;

    @Override
    public List<Role> getRoleList() {
        // 执行查询操作
        List<Role> roleList = roleMapper.selectAll();
        // 如果查询失败则抛出异常
        if (roleList == null) {
            throw new RoleManageException(ErrorResultEnum.ROLE_MANAGE_SELECT_FAIL);
        }
        return roleList;
    }

    @Override
    public void addRole(Role role) {
        // 设置角色名为 SpringSecurity 支持的格式
        role.setName(ROLE_PREFIX + role.getName());
        // 执行添加操作
        int result = roleMapper.insertSelective(role);
        // 添加失败时抛出异常
        if (result != 1) {
            throw new RoleManageException(ErrorResultEnum.ROLE_MANAGE_INSERT_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = RoleManageException.class)
    public void editPermission(String roleId, List<String> idList) {
        // 执行删除操作, 删除角色的全部权限
        Integer result = rolePermissionMapper.deleteByRoleId(roleId);
        if (result == null) {
            throw new RoleManageException(ErrorResultEnum.ROLE_PERMISSION_UPDATE_FAIL);
        }
        // 执行插入操作, 插入新选择的权限
        result = rolePermissionMapper.insertSelectiveList(roleId, idList);
        if (result == null) {
            throw new RoleManageException(ErrorResultEnum.ROLE_PERMISSION_UPDATE_FAIL);
        }
    }

    @Override
    public Role getRoleById(String id) {
        // 执行查询操作
        Role role = roleMapper.selectByPrimaryKey(id);
        // 如果查询失败则抛出异常
        if (role == null) {
            throw new RoleManageException(ErrorResultEnum.ROLE_MANAGE_SELECT_FAIL);
        }
        return role;
    }

    @Override
    public void editRole(Role role) {
        // 格式化角色名
        role.setName(ROLE_PREFIX + role.getName().toLowerCase());
        // 执行修改操作
        int result = roleMapper.updateByPrimaryKeySelective(role);
        // 如果修改失败则抛出异常
        if (result != 1) {
            throw new RoleManageException(ErrorResultEnum.ROLE_MANAGE_UPDATE_FAIL);
        }
    }

    @Override
    public List<Role> getRoleListByPageCondition(PageConditionDto dto) {
        // 执行查询操作
        List<Role> roleList = roleMapper.selectAllByPageCondition(dto);
        // 如果查询失败则抛出异常
        if (roleList == null) {
            throw new RoleManageException(ErrorResultEnum.ROLE_MANAGE_SELECT_FAIL);
        }
        return roleList;
    }

    @Override
    public Long getRoleTotalSize() {
        // 获取角色总量
        return roleMapper.countRoleTotalSize();
    }

}
