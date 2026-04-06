package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeePageQueryDTO implements Serializable {

    // 員工姓名
    private String name;

    // 頁碼
    private int page;

    // 每頁顯示紀錄數
    private int pageSize;

}
