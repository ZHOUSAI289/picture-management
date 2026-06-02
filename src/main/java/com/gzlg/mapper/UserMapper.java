package com.gzlg.mapper;

import com.gzlg.pojo.dto.LoginDTO;
import com.gzlg.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名和密码查询用户
     * @param lo
     * @return
     */
    @Select("select * from user where job_number = #{jobNumber} and password = #{password}")
    User selectUser(LoginDTO lo);
}
