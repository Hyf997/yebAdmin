package com.hyf.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.server.pojo.Employee;
import com.hyf.server.pojo.RespBean;
import com.hyf.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * create time: 2022/4/4
     * description:获取所有员工（分页）
     * params:
     * return:
     */
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * create time: 2022/4/4
     * description:获取工号id
     * params:
     * return:
     */
    RespBean maxWorkID();


    /**
     * create time: 2022/4/4
     * description:添加员工
     * params:
     * return:
     */
    RespBean addEmp(Employee employee);

    /**
     * create time: 2022/4/5
     * description:查询员工数据
     * params:
     * return:
     */
    List<Employee> getEmployee(Integer id);

    /**
     * create time: 2022/6/9
     * description:获取所有工资账套
     * params:
     * return:
     * @return
     */
    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);

}
