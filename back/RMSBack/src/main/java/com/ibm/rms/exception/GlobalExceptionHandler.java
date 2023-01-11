package com.ibm.rms.exception;

import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.domain.ErrorResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * - @RestControllerAdvice 就是 @ControllerAdvice 和 @ResponseBody 的结合体
 * <br>
 * 所有的请求都会按照 filter -> interceptor -> controllerAdvice -> aspect -> controller 的顺序调用
 * 抛出异常时也会按照这样反向抛出, 所以在 filter 抛出的异常无法在此处做统一的异常处理
 * <br>
 * 可以通过实现 ErrorController 的方式解决, 待我后续研究...
 *
 * @author 刘嘉宁
 * @date 2022-11-15 23:56:11
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 日志输出
    Logger logger = LoggerFactory.getLogger("GlobalExceptionHandler");

    /*==================== 41X 认证 / 授权 引起的错误, 需要重新登录=======================*/
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseResult<Object> handler(AccessDeniedException e) {
        logger.error("权限不足 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = PermissionException.class)
    public ResponseResult<Object> handler(PermissionException e) {
        logger.error("权限异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationEntryPointException.class)
    public ResponseResult<Object> handler(AuthenticationEntryPointException e) {
        logger.error("认证失败 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseResult<Object> handler(AuthenticationException e) {
        logger.error("认证失败 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = RedisException.class)
    public ResponseResult<Object> handler(RedisException e) {
        logger.error("Redis 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    /*==================== 42X 不影响程序运行的错误, 在前端会提示=======================*/
    @ExceptionHandler(value = DictionaryException.class)
    public ResponseResult<Object> handler(DictionaryException e) {
        logger.error("数据字典 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseResult<Object> handler(SQLIntegrityConstraintViolationException e) {
        logger.error("MySQL: 数据完整性异常 ================== " + e.getMessage());
        return new ResponseResult<>(ErrorResultEnum.SQL_UNIQUE_EXCEPTION);
    }

    @ExceptionHandler(value = UserManageException.class)
    public ResponseResult<Object> handler(UserManageException e) {
        logger.error("用户管理 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = RoleManageException.class)
    public ResponseResult<Object> handler(RoleManageException e) {
        logger.error("角色管理 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = PermissionManageException.class)
    public ResponseResult<Object> handler(PermissionManageException e) {
        logger.error("权限管理 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = DeptManageException.class)
    public ResponseResult<Object> handler(DeptManageException e) {
        logger.error("部门管理 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = InterviewOverviewException.class)
    public ResponseResult<Object> handler(InterviewOverviewException e) {
        logger.error("面试一览 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = CVReviewException.class)
    public ResponseResult<Object> handler(CVReviewException e) {
        logger.error("简历 Review 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = ResumeException.class)
    public ResponseResult<Object> handler(ResumeException e) {
        logger.error("简历 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = CommentException.class)
    public ResponseResult<Object> handler(CommentException e) {
        logger.error("评论 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }
    
    @ExceptionHandler(value = WorkBenchException.class)
    public ResponseResult<Object> handler(WorkBenchException e) {
        logger.error("工作台 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = HistoryInterviewException.class)
    public ResponseResult<Object> handler(HistoryInterviewException e) {
        logger.error("历史面试 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = CVUploadException.class)
    public ResponseResult<Object> handler(CVUploadException e) {
        logger.error("简历上传 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = FeedbackException.class)
    public ResponseResult<Object> handler(FeedbackException e) {
        logger.error("面试反馈 相关异常 ================== " + e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

}
