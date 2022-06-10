package com.hyf.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.server.pojo.Department;
import com.hyf.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * create time: 2022/4/3
     * description:获取所有部门
     * params:
     * return:
     */
    List<Department> getAllDepartment();

    /**
     * create time: 2022/4/3
     * description:添加部门
     * params:
     * return:
     */
    RespBean addDep(Department dep);

    /**
     * create time: 2022/4/3
     * description:根据id删除部门
     * params:
     * return:
     */
    RespBean deleteDep(Integer id);
}
