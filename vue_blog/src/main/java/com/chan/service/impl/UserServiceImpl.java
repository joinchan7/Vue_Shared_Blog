package com.chan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chan.common.dto.LoginDTO;
import com.chan.entity.User;
import com.chan.mapper.UserMapper;
import com.chan.service.IUserService;
import com.chan.shiro.AccountProfile;
import com.chan.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chan
 * @since 2020-06-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Map<Object, Object> login(LoginDTO loginDto, HttpServletResponse response) {
        User user = super.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");

        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return null;
        }

        String jwt = jwtUtils.generateToken(user.getId());
        // 放入 header 便于后续延期
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map();
    }

    @Override
    public AccountProfile getUserInfoByToken(String token) {
        String userId = jwtUtils.getSubjectByToken(token);
        User user = super.getById(userId);
        if (null == user) {
            throw new UnknownAccountException("账户不存在");
        }
        if (user.getStatus() == -1) {
            throw new LockedAccountException("账户被锁定");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);
        return profile;
    }

    @Override
    public void logout() {
        SecurityUtils.getSubject().logout();
    }
}
