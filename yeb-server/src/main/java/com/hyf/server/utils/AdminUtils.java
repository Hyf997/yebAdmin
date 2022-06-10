package com.hyf.server.utils;

import com.hyf.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author：洪yf
 * @date：2022/4/3
 * 操作员工具类
 */
public class AdminUtils {
    /**
     * create time: 2022/4/3
     * description:获取当前登录操作员
     * params:
     * return:
     */
    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
