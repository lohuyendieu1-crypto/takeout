package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cloudflare.r2")
@Data
public class AwsS3Properties {
    private String endpoint;
    private String bucketName;
    private String accessKey;
    private String secretKey;
    private String domain; // 新增這個，因為 R2 的公開訪問網址和上傳 Endpoint 不一樣
}