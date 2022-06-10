package com.hyf.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.server.mapper.DepartmentMapper;
import com.hyf.server.pojo.Department;
import com.hyf.server.pojo.RespBean;
import com.hyf.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * create time: 2022/4/3
     * description:获取所有部门
     * params:
     * return:
     */
    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment(-1);
    }

    /**
     * create time: 2022/4/3
     * description:添加部门实现方法
     * params:
     * return:
     */
    @Override
    public RespBean addDep(final Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
        System.out.println(dep.getResult());
        if(1==dep.getResult()){
            return RespBean.success("添加成功",dep);
        }
        return RespBean.error("添加失败");

    }

    /**
     * create time: 2022/4/3
     * description:根据id删除部门
     * params:
     * return:
     */
    @Override
    public RespBean deleteDep(final Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDep(department);
        if(-2==department.getResult()){
            return RespBean.error("该部门下还有子部门，删除失败");
        }
        if(-1 == department.getResult()){
            return RespBean.error("该部门下还有员工，删除失败");
        }
        if(1 == department.getResult())
        {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
