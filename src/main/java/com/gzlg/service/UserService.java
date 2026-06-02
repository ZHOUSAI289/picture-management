package com.gzlg.service;

import com.gzlg.pojo.dto.LoginDTO;
import com.gzlg.pojo.entity.User;
import com.gzlg.pojo.vo.LoginVO;
import org.slf4j.Logger;

public interface UserService {
    /**
     * 登录
     * @param lo
     * @return
     */
    User login(LoginDTO lo);
}
