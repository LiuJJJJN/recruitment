package com.ibm.rms.utils;

import java.util.UUID;

/**
 * UUID 生成工具类
 *
 * @author 刘嘉宁
 * @date 2022-11-29 11:28:44
 */
public class UUIDUtil {

    /**
     * 生成 UUID
     * @return 去掉 - 的 32 位 UUID 字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
