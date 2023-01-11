package com.ibm.rms.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 使用 FastJson 的 Redis 序列化器
 *
 * @author 刘嘉宁
 * @date 2022-11-29 11:15:14
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    // 默认编码格式
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    // 代理类 clazz
    private Class<T> clazz;

    static {
        // fastjson 1.2.83 版本已解决远程代码执行漏洞
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * 序列化
     *
     * @param t 序列化内容
     * @return 序列化字符串
     * @throws SerializationException 序列化异常
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    /**
     * 反序列化
     *
     * @param bytes 字符串
     * @return 实体类
     * @throws SerializationException 序列化异常
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return JSON.parseObject(str, clazz);
    }

    /**
     * 获取 JavaType
     *
     * @param clazz 反射对象
     * @return JavaType
     */
    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }

}
