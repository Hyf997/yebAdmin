package com.hyf.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyf.server.pojo.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * create time: 2022/4/4
     * description:获取所有员工（分页）
     * params:
     * return:
     */
     IPage getEmployeeByPage(Page<Employee> page,
                             @Param("employee") Employee employee,
                             @Param("beginDateScope") LocalDate[] beginDateScope);

     /**
      * create time: 2022/4/5
      * description:查询员工
      * params:
      * return:
      */
    List<Employee> getEmployee(Integer id);

    /**
     * create time: 2022/6/9
     * description:获取所有员工账套
     * params: page
     * return:
     */
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
