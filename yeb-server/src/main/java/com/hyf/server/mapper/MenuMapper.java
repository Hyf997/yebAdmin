package com.hyf.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyf.server.pojo.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    ArrayList<Menu> getMenusByAdminId(Integer id);

    /**
     * create time: 2022/3/21
     * description: 通过角色获取菜单列表
     * params:
     * return:
     */
    List<Menu> getAllMenusWithRole();

    List<Menu> getAllMenus();
}
