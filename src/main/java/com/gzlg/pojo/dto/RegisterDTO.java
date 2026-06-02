package com.gzlg.pojo.dto;

import lombok.Data;

/**
 * 注册请求DTO
 */
@Data
public class RegisterDTO {
    private String name;
    private Integer jobNumber;
    private String password;
}
