package com.hyf.server.service.impl;

import com.hyf.server.mapper.EmployeeMapper;
import com.hyf.server.pojo.RespPageBean;
import com.hyf.server.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author：洪yf
 * @date：2022/4/4
 */
@SpringBootTest
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
}