package com.hyf.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
public interface IMenuService extends IService<Menu> {

    /**
     * create time: 2022/3/15
     * description:根据用户ID 查询菜单列表
     * params:
     * return:
     */
    List<Menu> getMenusByAdminId();

    /**
     * 通过角色获取菜单列表
     * @return
     */
    List<Menu> getAllMenusWithRole();

    //查询所有菜单
    List<Menu> getAllMenus();
}
