package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * C端用户登錄
 */
@Data
public class UserLoginDTO implements Serializable {

    private String code;

}
