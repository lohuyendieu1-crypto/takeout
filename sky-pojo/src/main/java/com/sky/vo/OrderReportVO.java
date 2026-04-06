package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportVO implements Serializable {

    // 日期，以逗號分隔，例如：2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    // 每日訂單數，以逗號分隔，例如：260,210,215
    private String orderCountList;

    // 每日有效訂單數，以逗號分隔，例如：20,21,10
    private String validOrderCountList;

    // 訂單總數
    private Integer totalOrderCount;

    // 有效訂單數
    private Integer validOrderCount;

    // 訂單完成率
    private Double orderCompletionRate;

}
