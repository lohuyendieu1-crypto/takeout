package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 類型: 1菜品分類 2套餐分類
    private Integer type;

    // 分類名稱
    private String name;

    // 順序
    private Integer sort;

    // 分類狀態 0表示禁用 1表示啟用
    private Integer status;

    // 創建時間
    private LocalDateTime createTime;

    // 更新時間
    private LocalDateTime updateTime;

    // 創建人
    private Long createUser;

    // 修改人
    private Long updateUser;
}
