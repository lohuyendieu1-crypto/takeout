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
public class SalesTop10ReportVO implements Serializable {

    // 商品名稱列表，以逗號分隔，例如：魚香肉絲,宮保鶏丁,水煮魚
    private String nameList;

    // 銷量列表，以逗號分隔，例如：260,215,200
    private String numberList;

}
