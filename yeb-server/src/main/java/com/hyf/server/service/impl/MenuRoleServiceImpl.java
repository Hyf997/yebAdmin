package com.hyf.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.server.mapper.MenuRoleMapper;
import com.hyf.server.pojo.MenuRole;
import com.hyf.server.pojo.RespBean;
import com.hyf.server.service.IMenuRoleService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;
    /**
     * create time: 2022/4/1
     * description:更新角色菜单
     * params:rid
     * params:mids
     * return
     */
    @Override
    @Transactional
    public RespBean updateMenuRole(final Integer rid, final Integer[] mids) {
        //删除rid下所有菜单
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if(null == mids||0 == mids.length)
        {
            return RespBean.success("更新成功");
        }
        Integer integer = menuRoleMapper.insertRecord(rid, mids);

        if(integer == mids.length)
        {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");


    }
}
