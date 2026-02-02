package com.sky.vo;

import com.sky.entity.SetmealDish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealVO implements Serializable {

    private Long id;

    // 分類 id
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

    // 更新時間
    private LocalDateTime updateTime;

    // 分類名稱
    private String categoryName;

    // 套餐和菜品的關聯關係
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}
