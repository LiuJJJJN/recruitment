package com.ibm.rms.utils;

import com.alibaba.fastjson.JSON;
import com.ibm.rms.domain.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应封装工具类
 *
 * @author 刘嘉宁
 * @date 2022-11-17 03:09:46
 */
public class WebUtil {

    public static <T> void renderResult(HttpServletResponse response, ResponseResult<T> result) throws IOException {
        String json = JSON.toJSONString(result);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(json);
        response.getWriter().flush();
    }

}
