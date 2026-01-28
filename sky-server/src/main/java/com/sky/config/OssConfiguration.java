package com.sky.config;

import com.sky.properties.AwsS3Properties;
import com.sky.utils.AwsS3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置類，用於創建 AwsS3Util 對象
 */
@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AwsS3Util awsS3Util(AwsS3Properties awsS3Properties) {
        log.info("開始創建雲存儲文件上傳工具類對象：{}", awsS3Properties);

        // 這裡就是把 Properties 裡的變量，傳給 Util 的構造方法
        return new AwsS3Util(
                awsS3Properties.getEndpoint(),
                awsS3Properties.getAccessKey(),
                awsS3Properties.getSecretKey(),
                awsS3Properties.getBucketName(),
                awsS3Properties.getDomain()
        );
    }
}