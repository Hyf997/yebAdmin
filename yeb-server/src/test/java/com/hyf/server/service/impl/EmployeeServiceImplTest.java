package com.hyf.server.service.impl;

import com.hyf.server.mapper.EmployeeMapper;
import com.hyf.server.pojo.RespPageBean;
import com.hyf.server.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author：洪yf
 * @date：2022/4/4
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class EmployeeServiceImplTest {

    @Autowired
    IEmployeeService employeeService;
    @Test
    void maxWorkID() {
        System.out.println(employeeService.maxWorkID());
    }

    @Test
    void getEmployee() {
        System.out.println(employeeService.getEmployee(1));
    }

    @Test
    void getEmployeeWithSalary() {
        RespPageBean employeeWithSalary = employeeService.getEmployeeWithSalary(1, 10);
        System.out.println(employeeWithSalary.getData());
    }
    @Test
    void addEmp() {
        RespPageBean employeeWithSalary = employeeService.getEmployeeWithSalary(1, 10);
        System.out.println(employeeWithSalary.getData());
    }
}