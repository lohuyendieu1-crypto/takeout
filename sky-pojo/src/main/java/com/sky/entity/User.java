package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 微信用戶唯一標識
    private String openid;

    // 姓名
    private String name;

    // 手機號
    private String phone;

    // 性別 0 女 1 男
    private String sex;

    // 身份證號
    private String idNumber;

    // 頭像
    private String avatar;

    // 注册時間
    private LocalDateTime createTime;
}
