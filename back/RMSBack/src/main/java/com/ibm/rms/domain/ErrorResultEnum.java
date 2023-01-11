package com.ibm.rms.domain;

/**
 * 异常信息枚举类
 *
 * @author 刘嘉宁
 * @date 2022-11-29 11:52:14
 */
public enum ErrorResultEnum {
    /**
     * 自己定义的异常信息，异常代码，异常提示信息
     * 41X 需要跳转到登录页面的错误
     * 42X 只需要提示的错误
     */

    PERMISSION_NOT_ENOUGH(411, "权限不足, 请勿尝试访问非账户权限内的内容"),
    EMPTY_PERMISSION(411, "权限错误, 未查询到对应权限信息, 请联系系统管理员"),
    LOGIN_USER_STATUS_LOCK(411, "登录失败, 账户已被锁定, 请联系管理员"),
    EMPTY_USER(412, "认证失败, 未查询到此用户"),
    AUTHENTICATION_USER_STATUS_LOCK(412, "认证失败, 账户已被锁定, 请联系管理员"),
    AUTHENTICATION_FAIL(412, "认证失败, 请检查密码是否正确, 请勿非法访问"),
    REDIS_EXCEPTION(413, "Redis 服务异常, 请联系管理员"),
    REDIS_TTL_END(414, "长时间未操作, 令牌已失效, 请重新登陆"),
    TOKEN_PARSE_FAIL(414, "token 不合法, 请重新登陆"),
    DIC_TYPE_SELECT_FAIL(421, "数据字典类型查询失败"),
    DIC_VALUE_SELECT_FAIL(421, "数据字典值查询失败"),
    DIC_TYPE_DELETE_FAIL(421, "数据字典类型删除失败"),
    DIC_VALUE_DELETE_FAIL(421, "数据字典值删除失败"),
    DIC_VALUE_BATCH_DELETE_FAIL(421, "数据字典值批量删除失败"),
    DIC_TYPE_DELETE_FAIL_CAUSE_VALUE(421, "数据字典类型删除失败, 请检查数据字典值表中是否存在对应 code 的数据"),
    DIC_TYPE_INSERT_FAIL(421, "数据字典类型添加失败"),
    DIC_VALUE_INSERT_FAIL(421, "数据字典值添加失败"),
    DIC_VALUE_INSERT_FAIL_CAUSE_KEY(421, "数据字典值添加失败, 请检查是否存在对应 code 的 value key "),
    DIC_TYPE_UPDATE_FAIL(421, "数据字典类型修改失败"),
    DIC_VALUE_UPDATE_FAIL(421, "数据字典值修改失败"),
    DIC_TYPE_UPDATE_FAIL_CAUSE_VALUE(421, "数据字典类型修改失败, 请检查数据字典值表中是否存在对应 code 的数据"),
    USER_MANAGE_SELECT_FAIL(422, "用户数据查询失败"),
    USER_MANAGE_UPDATE_FAIL(422, "用户数据更新失败"),
    USER_MANAGE_INSERT_FAIL(422, "用户添加失败"),
    DEPT_MANAGE_SELECT_FAIL(423, "部门数据查询失败"),
    ROLE_MANAGE_SELECT_FAIL(424, "角色数据查询失败"),
    ROLE_MANAGE_INSERT_FAIL(424, "角色添加失败"),
    ROLE_MANAGE_UPDATE_FAIL(424, "角色修改失败"),
    ROLE_PERMISSION_UPDATE_FAIL(424, "角色权限修改失败"),
    PERMISSION_MANAGE_SELECT_FAIL(424, "权限查询失败"),
    PERMISSION_PATH_SELECT_FAIL(424, "权限路径列表查询失败"),
    PERMISSION_MANAGE_INSERT_FAIL(424, "权限添加失败"),
    PERMISSION_MANAGE_UPDATE_FAIL(424, "权限修改失败"),
    INTERVIEW_OVERVIEW_SELECT_FAIL(425, "面试信息查询失败"),
    INTERVIEW_OVERVIEW_UPDATE_FAIL(425, "面试信息修改失败"),
    INTERVIEW_OVERVIEW_UPDATE_FILE_CAUSE_STATUS_COUNT(425, "请检查当前面试状态是否符合需求"),
    RESUME_SELECT_FAIL(425, "简历信息查询失败"),
    RESUME_UPDATE_FAIL(425, "简历更新失败"),
    REPEAT_REVIEW_EXCEPTION(425, "重复 Review 异常, 请检查当前面试状态"),
    COMMENT_INSERT_FAIL(425, "备注添加失败"),
    COMMENT_SELECT_FAIL(425, "评论查询失败"),
    WORK_BENCH_MISSIONS_EXCEPTION(426,"任务量获取失败"),
    HISTORY_INTERVIEW_SELECT_FAIL(426,"历史面试查询失败"),
    HISTORY_INTERVIEW_UPDATE_FAIL(426,"历史面试修改失败"),
    CV_UPLOAD_FAIL(427,"简历上传失败"),
    FEEDBACK_FAIL(427,"面试反馈失败"),
    PARAM_PARSE_EXCEPTION(428, "参数解析异常, 请联系管理员"),
    SQL_UNIQUE_EXCEPTION(429, "请检查是否存在重复的值"),
    ;
    private Integer code;
    private String msg;

    ErrorResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
