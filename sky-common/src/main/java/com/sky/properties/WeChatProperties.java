package com.sky.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.wechat")
@Data
public class WeChatProperties {

    private String appid;  // 小程序的 appid
    private String secret; // 小程序的密鑰
    private String mchid; // 商户號
    private String mchSerialNo; // 商户 API 證書的證書序列號
    private String privateKeyFilePath; // 商户私鑰文件
    private String apiV3Key; // 證書解密的密鑰
    private String weChatPayCertFilePath; // 平台證書
    private String notifyUrl; // 支付成功的回調地址
    private String refundNotifyUrl; // 退款成功的回調地址

}
