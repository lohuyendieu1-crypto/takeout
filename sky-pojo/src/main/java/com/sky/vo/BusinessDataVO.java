package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 數據概覽
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDataVO implements Serializable {

    private Double turnover;// 營業額

    private Integer validOrderCount;// 有效訂單數

    private Double orderCompletionRate;// 訂單完成率

    private Double unitPrice;// 平均客單價

    private Integer newUsers;// 新增用戶數

}
