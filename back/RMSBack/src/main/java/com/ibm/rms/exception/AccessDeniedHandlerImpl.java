package com.ibm.rms.exception;

import com.alibaba.fastjson.JSON;
import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.domain.ErrorResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    Logger logger = LoggerFactory.getLogger("AccessDeniedHandler");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        logger.error("权限不足 ================== " + ErrorResultEnum.PERMISSION_NOT_ENOUGH.getMsg());

        ResponseResult<Object> result = new ResponseResult<>(ErrorResultEnum.PERMISSION_NOT_ENOUGH);
        String json = JSON.toJSONString(result);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(json);
        response.getWriter().flush();
//      WebUtils.renderString(response,json);

//        这个实现类是被 Filter 调用的, Filter 的执行优先级在异常捕获器之前, 所以此处无法使用异常捕获
//        throw new AccessDeniedException(ResultEnum.PERMISSION_NOT_ENOUGH);

    }
}
