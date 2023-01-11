package com.ibm.rms.mapper;

import com.ibm.rms.pojo.User;
import com.ibm.rms.pojo.dto.UserEditDto;
import com.ibm.rms.pojo.dto.UserPageConditionDto;
import com.ibm.rms.pojo.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据访问层接口
 *
 * @author 刘嘉宁 耿晨
 * @date 2022-11-12 15:28:37
 */
public interface UserMapper {

    int deleteByPrimaryKey(String id);

    /**
     *  根据用户id查询用户角色
     * @param 用户id
     * @return 查询到的角色列表
     */
    List<String> selectRoleById(String id);

    /**
     * 添加用户数据
     *
     * @param record 用户实体类
     * @return 影响条数
     */
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    /**
     * 根据用户 ID 修改用户数据
     *
     * @param record 用户实体类
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据分页和模糊搜索值查询对应数据列表
     *
     * @param dto 分页和模糊搜索搜索内容
     * @return 对应用户数据
     */
    List<UserVo> selectAllByPageCondition(UserPageConditionDto dto);

    /**
     * 根据模糊查询内容获取对应数据总量
     *
     * @param dto 模糊查询内容
     * @return 对应数据总量
     */
    long selectTotalSizeByPageCondition(UserPageConditionDto dto);

    /**
     * 获取一个 MySQL 生成的 UUID
     *
     * @return UUID
     */
    String selectUUID();

    /**
     * 根据用户 id 查询对应数据和对应角色列表
     *
     * @param id 用户 ID
     * @return 对应数据和对应角色列表
     */
    UserVo selectUserVoById(String id);

    /**
     * 根据用户 id 修改用户数据
     *
     * @param dto 用户 ID 及用户数据
     * @return 影响条数
     */
    int updateByIdSelective(UserEditDto dto);

    /**
     * 根据角色名查询对应的用户列表
     *
     * @param roleName   角色名
     * @param userStatus 用户状态值
     * @return 对应的用户列表
     */
    List<UserVo> selectUserVoByRole(@Param("roleName") String roleName, @Param("userStatus") Integer userStatus);

    /**
     * 获取某角色中当前任务量最少的用户
     *
     * @param roleName   角色名
     * @param userStatus 用户状态值
     * @return 用户 vo 实体类
     */
    UserVo selectReviewerAutoAssign(@Param("roleName") String roleName, @Param("userStatus") Integer userStatus);

    /**
     * 根据 username 查询对应用户
     *
     * @param username 用户名 username
     * @return 用户实例
     */
    User selectOneByUsername(String username);

    /**
     * 根据用户 id 查询对应权限列表
     *
     * @param id 用户 ID
     * @return 对应的权限列表
     */
    List<String> selectPermissionsById(String id);
}
