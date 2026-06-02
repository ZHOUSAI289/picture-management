package com.gzlg.pojo.dto;

import lombok.Data;

/**
 * 登录请求DTO
 */
@Data
public class LoginDTO {
    private Integer jobNumber;
    private String password;
}
