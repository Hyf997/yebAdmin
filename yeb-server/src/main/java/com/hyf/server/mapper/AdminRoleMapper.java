package com.hyf.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyf.server.pojo.AdminRole;
import com.hyf.server.pojo.RespBean;
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
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * create time: 2022/4/4
     * description:更新操作员角色
     * params:
     * return:
     */
    Integer updateAdminRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
