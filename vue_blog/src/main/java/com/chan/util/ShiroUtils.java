package com.chan.util;

import com.chan.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * shiro工具类
 */
public class ShiroUtils {

    /**
     * 获取账户信息
     */
    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
