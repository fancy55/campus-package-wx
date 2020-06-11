package com.cpack.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "photo.info")
@Data
@Component
public class PhotoConfig {
    String url;
    String path;
    String type;
}
