package com.hyf.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyf.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
@Repository
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * create time: 2022/4/1
     * description:根据rid插入菜单
     * params:rid 角色id,mids
     * return:
     */
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
