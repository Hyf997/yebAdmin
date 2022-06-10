package com.hyf.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyf.server.pojo.Admin;
import org.apache.ibatis.annotations.Param;
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
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * create time: 2022/4/3
     * description:获取所有的操作员
     * params:
     * return:
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);
}
