package com.chan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chan.common.dto.LoginDTO;
import com.chan.entity.User;
import com.chan.shiro.AccountProfile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chan
 * @since 2020-06-06
 */
public interface IUserService extends IService<User> {

    Map<Object, Object> login(LoginDTO loginDto, HttpServletResponse response);

    AccountProfile getUserInfoByToken(String token);

    void logout();
}
