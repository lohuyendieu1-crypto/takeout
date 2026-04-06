package com.sky.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderStatisticsVO implements Serializable {
    // 待接單數量
    private Integer toBeConfirmed;

    // 待派送數量
    private Integer confirmed;

    // 派送中數量
    private Integer deliveryInProgress;
}
