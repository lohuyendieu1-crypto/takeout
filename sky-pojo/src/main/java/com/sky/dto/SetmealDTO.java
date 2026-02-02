package com.sky.dto;

import com.sky.entity.SetmealDish;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDTO implements Serializable {

    private Long id;

    // 分類id
    private Long categoryId;

    // 套餐名稱
    private String name;

    // 套餐價格
    private BigDecimal price;

    // 狀態 0:停用 1:啟用
    private Integer status;

    // 描述信息
    private String description;

    // 圖片
    private String image;

    // 套餐菜品關係
    private List<SetmealDish> setmealDishes = new ArrayList<>();

}
