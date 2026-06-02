package com.gzlg.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer jobNumber; //工号
    private String password;
    private String createTime; //创建时间
    private String token; //JWT令牌
}
