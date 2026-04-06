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
public class UserReportVO implements Serializable {

    // 日期，以逗號分隔，例如：2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    // 用戶總量，以逗號分隔，例如：200,210,220
    private String totalUserList;

    // 新增用戶，以逗號分隔，例如：20,21,10
    private String newUserList;

}
