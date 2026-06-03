package com.gzlg.pojo.vo;

import lombok.Data;

/**
 * 登录视图对象VO
 */
@Data
public class LoginVO {
    private Integer id;
    private Integer jobNumber;
    private String name;
    private String token;
    private String role; // 角色：admin/user
}
