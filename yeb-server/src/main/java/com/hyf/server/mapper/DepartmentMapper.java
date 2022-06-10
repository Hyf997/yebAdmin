package com.hyf.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyf.server.pojo.Department;
import com.hyf.server.pojo.RespBean;
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
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * create time: 2022/4/3
     * description:获取所有部门
     * params:
     * return:
     */
    List<Department> getAllDepartment(Integer parentId);

    /**
     * create time: 2022/4/3
     * description:添加部门
     * params:
     * return:
     */
    void addDep(Department dep);

    /**
     * create time: 2022/4/3
     * description:根据id删除部门
     * params:
     * return:
     */
    RespBean deleteDep(Department department);
}
