package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 訂單概覽數據
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOverViewVO implements Serializable {
    // 待接單數量
    private Integer waitingOrders;

    // 待派送數量
    private Integer deliveredOrders;

    // 已完成數量
    private Integer completedOrders;

    // 已取消數量
    private Integer cancelledOrders;

    // 全部訂單
    private Integer allOrders;
}
