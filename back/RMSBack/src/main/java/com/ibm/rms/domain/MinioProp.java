package com.ibm.rms.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * minio 属性值
 *
 * @author 刘嘉宁
 * @date 2022-12-13 14:32:30
 */
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProp {
    //连接url
    private String endpoint;
    //公钥
    private String accesskey;
    //私钥
    private  String secretkwy;

    public MinioProp() {

    }

    public MinioProp(String endpoint, String accesskey, String secretkwy) {
        this.endpoint = endpoint;
        this.accesskey = accesskey;
        this.secretkwy = secretkwy;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getSecretkwy() {
        return secretkwy;
    }

    public void setSecretkwy(String secretkwy) {
        this.secretkwy = secretkwy;
    }

    @Override
    public String toString() {
        return "MinioProp{" +
                "endpoint='" + endpoint + '\'' +
                ", accesskey='" + accesskey + '\'' +
                ", secretkwy='" + secretkwy + '\'' +
                '}';
    }

}

