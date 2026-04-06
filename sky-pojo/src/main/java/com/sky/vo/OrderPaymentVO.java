package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentVO implements Serializable {

    private String nonceStr; // 隨機字符串
    private String paySign; // 簽名
    private String timeStamp; // 時間戳
    private String signType; // 簽名算法
    private String packageStr; // 統一下單接口返回的 prepay_id 參數值

}
