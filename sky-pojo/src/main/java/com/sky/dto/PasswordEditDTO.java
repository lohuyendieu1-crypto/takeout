package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordEditDTO implements Serializable {

    // 員工id
    private Long empId;

    // 舊密碼
    private String oldPassword;

    // 新密碼
    private String newPassword;

}
