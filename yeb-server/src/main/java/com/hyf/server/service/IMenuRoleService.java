package com.hyf.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.server.pojo.MenuRole;
import com.hyf.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * create time: 2022/4/1
     * description:更新角色菜单
     * params:rid
     * params:mids
     * return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
