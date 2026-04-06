package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地址簿
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 用戶id
    private Long userId;

    // 收貨人
    private String consignee;

    // 手機號
    private String phone;

    // 性別 0 女 1 男
    private String sex;

    // 省級區劃編號
    private String provinceCode;

    // 省級名稱
    private String provinceName;

    // 市級區劃編號
    private String cityCode;

    // 市級名稱
    private String cityName;

    // 區級區劃編號
    private String districtCode;

    // 區級名稱
    private String districtName;

    // 詳細地址
    private String detail;

    // 標簽
    private String label;

    // 是否默認 0否 1是
    private Integer isDefault;
}
