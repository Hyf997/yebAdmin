package com.hyf.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyf.server.pojo.Role;
import org.springframework.stereotype.Repository;

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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * create time: 2022/3/21
     * description:根据用户id查询角色列表
     * params:adminid
     * return:
     */
    List<Role> getRoles(Integer adminId);
}
