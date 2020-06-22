package com.chan.controller;


import com.chan.common.lang.Result;
import com.chan.entity.User;
import com.chan.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  用户控制层
 * </p>
 *
 * @author chan
 * @since 2020-06-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequiresAuthentication
    @GetMapping("/index")
    public Object index() {
        User user = userService.getById(1L);
        return Result.success(user);
    }

    @PostMapping("/save")
    public Object save(@Validated @RequestBody User user) {
        return Result.success(user);
    }

}
