package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersConfirmDTO implements Serializable {

    private Long id;
    // 訂單狀態 1待付款 2待接單 3已接單 4派送中 5已完成 6 已取消 7退款
    private Integer status;

}
