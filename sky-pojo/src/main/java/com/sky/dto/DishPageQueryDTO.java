package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DishPageQueryDTO implements Serializable {

    private int page;

    private int pageSize;

    private String name;

    // 分頁id
    private Integer categoryId;

    // 狀態 0表示禁用 1表示啟用
    private Integer status;

}
