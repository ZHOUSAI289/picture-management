package com.gzlg.mapper;

import com.gzlg.pojo.dto.LoginDTO;
import com.gzlg.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
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

    /**
     * 根据工号查询用户
     * @param jobNumber
     * @return
     */
    @Select("select * from user where job_number = #{jobNumber}")
    User selectByJobNumber(Integer jobNumber);

    /**
     * 新增用户
     * @param user
     */
    @Insert("insert into user (name, job_number, password, create_time) values (#{name}, #{jobNumber}, #{password}, #{createTime})")
    void insert(User user);

    /**
     * 根据姓名查询数据库是否还有管理员名字
     * @param name
     * @return
     */
    @Select("select * from user where name = #{name}")
    User selectByName(String name);
}
