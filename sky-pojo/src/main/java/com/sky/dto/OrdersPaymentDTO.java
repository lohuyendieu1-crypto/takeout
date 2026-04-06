package com.sky.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrdersPaymentDTO implements Serializable {
    // 訂單號
    private String orderNumber;

    // 付款方式
    private Integer payMethod;

}
