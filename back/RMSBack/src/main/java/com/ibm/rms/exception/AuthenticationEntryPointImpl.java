package com.ibm.rms.exception;

import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    Logger logger = LoggerFactory.getLogger("AuthenticationEntryPoint");

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        logger.error("认证失败 ================== " + ErrorResultEnum.AUTHENTICATION_FAIL.getMsg());

        ResponseResult<Object> result = new ResponseResult<>(ErrorResultEnum.AUTHENTICATION_FAIL);
        WebUtil.renderResult(response, result);

        // 这个实现类是被 Filter 调用的, Filter 的执行优先级在异常捕获器之前, 所以此处无法使用异常捕获
        //throw new AuthenticationEntryPointException(ResultEnum.AUTHENTICATION_FAIL.getMsg());
    }

}
