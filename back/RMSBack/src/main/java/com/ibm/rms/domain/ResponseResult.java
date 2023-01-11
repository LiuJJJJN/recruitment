package com.ibm.rms.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.experimental.Accessors;

/**
 * 通用的返回结果对象
 *
 * @author 刘嘉宁
 * @date 2022-11-29 11:48:53
 */
@Accessors(chain = true) // 开启链式操作
@JsonInclude(JsonInclude.Include.NON_NULL) // 设置 JSON 中如果一个属性的值为 null 时, 就过滤掉这个属性
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 查询到的结果数据
     */
    private T data;

    public ResponseResult() {

    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(ErrorResultEnum errorResultEnum) {
        this.code = errorResultEnum.getCode();
        this.msg = errorResultEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
