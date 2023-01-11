package com.ibm.rms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"com/ibm/rms/mapper"})
@EnableConfigurationProperties // 让 @ConfigurationProperties 注解生效
@EnableTransactionManagement // 开启事务注解
public class RecruitmentManageSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitmentManageSystemApplication.class, args);
    }

}
