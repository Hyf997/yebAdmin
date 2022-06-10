package com.hyf.server.controller.converter;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hyf.server.pojo.Employee;
import com.hyf.server.pojo.RespBean;
import com.hyf.server.pojo.RespPageBean;
import com.hyf.server.pojo.Salary;
import com.hyf.server.service.IEmployeeService;
import com.hyf.server.service.ISalaryService;
import com.hyf.server.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author：洪yf
 * @date：2022/6/9
 * 员工账套
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {
    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有员工账套")
    @GetMapping("/")
    public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeWithSalary(currentPage,size);
    }

    @ApiOperation(value = "更新员工账套")
    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid, Integer sid) {
        if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId",
                sid).eq("id", eid))) {
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
}
