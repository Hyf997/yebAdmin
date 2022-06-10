package com.hyf.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.server.pojo.Admin;
import com.hyf.server.pojo.RespBean;
import com.hyf.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
public interface IAdminService extends IService<Admin> {

    /**
     * create time: 2022/3/13
     * description:登陆之后返回token
     * params:
     * return:
     */
    RespBean login(String username, String password, final String code, HttpServletRequest request);

    /**
     * create time: 2022/3/13
     * description:根据用户名获取用户
     * params:
     * return:
     */
    Admin getAdminByUserName(String username);

    /**
     * create time: 2022/3/21
     * description:根据用户id查询角色列表
     * params:
     * return:
     */
    List<Role> getRoles(Integer adminId);

    /**
     * create time: 2022/4/3
     * description:获取所有的操作员
     * params:
     * return:
     */
    List<Admin> getAllAdmins(String keywords);

    /**
     * create time: 2022/4/4
     * description:更新操作员角色
     * params:
     * return:
     */
    RespBean updateAdminRole(Integer adminId, Integer[] rids);
}
