package com.ibm.rms.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * 生成、创建、解析 JWT 的工具类
 *
 * @author 刘嘉宁
 * @date 2022-11-29 11:14:04
 */
public class JwtUtil {

    // 有效期
    public static final Long JWT_TTL = 30 * 60 * 1000L;// 30 * 60 * 1000  半个小时
    // 秘钥明文
    public static final String JWT_KEY = "liujianing";

    /**
     * 创建 JWT (没有过期时间的 token)
     *
     * @param subject token 中要存放的数据 (json 格式)
     * @return 紧凑的 URL 安全 JWT 字符串
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, UUIDUtil.getUUID()); // 不设置过期时间
        return builder.compact();
    }

    /**
     * 创建带有过期时间的 JWT
     *
     * @param subject   封装内容
     * @param ttlMillis 过期时间
     * @return 紧凑的 URL 安全 JWT 字符串
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, UUIDUtil.getUUID()); // 设置过期时间
        return builder.compact();
    }

    /**
     * 生成 JWT Builder
     *
     * @param subject   主题
     * @param ttlMillis 过期时间
     * @param uuid      唯一 id
     * @return JWT Builder
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)           //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("ljn")      // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用 HS256 对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * 生成没有过期时间的 JWT Builder
     *
     * @param subject   主题
     * @param uuid      唯一 id
     * @return JWT Builder
     */
    private static JwtBuilder getJwtBuilder(String subject, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        return Jwts.builder()
                .setId(uuid)           //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("ljn")      // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey); //使用 HS256 对称加密算法签名, 第二个参数为秘钥
    }

    /**
     * 创建自定义 id、带有过期时间的 JWT
     *
     * @param id        自定义 id
     * @param subject   封装内容
     * @param ttlMillis 过期时间
     * @return 紧凑的 URL 安全 JWT 字符串
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return 密钥
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析 JWT
     *
     * @param jwt JWT 字符串
     * @return Claims 类型
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
