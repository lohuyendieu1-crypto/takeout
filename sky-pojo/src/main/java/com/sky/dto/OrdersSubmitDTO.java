package com.sky.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersSubmitDTO implements Serializable {
    // 地址簿id
    private Long addressBookId;
    // 付款方式
    private int payMethod;
    // 備注
    private String remark;
    // 預計送達時間
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;
    // 配送狀態  1立即送出  0選擇具體時間
    private Integer deliveryStatus;
    // 餐具數量
    private Integer tablewareNumber;
    // 餐具數量狀態  1按餐量提供  0選擇具體數量
    private Integer tablewareStatus;
    // 打包費
    private Integer packAmount;
    // 總金額
    private BigDecimal amount;
}