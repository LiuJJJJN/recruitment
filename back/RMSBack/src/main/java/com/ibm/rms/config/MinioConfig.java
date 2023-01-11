package com.ibm.rms.config;

import com.ibm.rms.domain.MinioProp;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio核心配置类
 * 通过注入 MinIO 服务器的相关配置信息, 得到 MinioClient 对象, 我们上传文件依赖此对象
 *
 * @author 刘嘉宁
 * @date 2022-12-13 14:33:11
 */
@Configuration
public class MinioConfig {

    @Autowired
    private MinioProp prop;

    /**
     * 获取 MinioClient
     * @return MinioClient
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(prop.getEndpoint()).
                credentials(prop.getAccesskey(),prop.getSecretkwy()).build();
    }

}
