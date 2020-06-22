package com.chan.controller;

import com.chan.common.dto.LoginDTO;
import com.chan.common.lang.Result;
import com.chan.service.IUserService;
import com.chan.shiro.AccountProfile;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 账户控制层
 */
@RestController
public class AccountController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDTO loginDto, HttpServletResponse response) {
        Map<Object, Object> userInfo = userService.login(loginDto, response);
        if (userInfo.isEmpty()) {
            return Result.fail("密码不正确");
        }
        // 返回用户信息
        return Result.success(userInfo);
    }


    @RequiresAuthentication
    @PostMapping("/info")
    public Result getUserInfo(@RequestBody String token) {
        AccountProfile accountProfile = userService.getUserInfoByToken(token);
        return Result.success(accountProfile);
    }


    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        userService.logout();
        return Result.success();
    }

}
