package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {

    // 主鍵
    private Long id;

    // 類型 1 菜品分類 2 套餐分類
    private Integer type;

    // 分類名稱
    private String name;

    // 排序
    private Integer sort;

}
