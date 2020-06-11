package com.cpack.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.card")
public class IdCardConfig {
    String appSecret;
    String appCode;
    String host;
    String path;
    String method;
}
