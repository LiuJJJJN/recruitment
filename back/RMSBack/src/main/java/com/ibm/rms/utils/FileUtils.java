package com.ibm.rms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件工具类
 *
 * @author 刘嘉宁
 * @date 2023-01-05 18:28:41
 */
public class FileUtils {

    /**
     * 生成带有时间前缀的文件名
     *
     * @param originalName 原文件名
     * @return 带有时间前缀的文件名 "xxxxxxx-原文件名"
     */
    public static String formatName(String originalName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss-");
        String str = simpleDateFormat.format(new Date());
        return str + originalName;
    }

}
