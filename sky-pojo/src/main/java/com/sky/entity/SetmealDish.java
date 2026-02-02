package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 套餐菜品關係
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 套餐id
    private Long setmealId;

    // 菜品id
    private Long dishId;

    // 菜品名稱 （冗余字段）
    private String name;

    // 菜品原價
    private BigDecimal price;

    // 份數
    private Integer copies;
}
