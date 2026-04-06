package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 訂單明細
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 名稱
    private String name;

    // 訂單id
    private Long orderId;

    // 菜品id
    private Long dishId;

    // 套餐id
    private Long setmealId;

    // 口味
    private String dishFlavor;

    // 數量
    private Integer number;

    // 金額
    private BigDecimal amount;

    // 圖片
    private String image;
}