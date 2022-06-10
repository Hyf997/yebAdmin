package com.hyf.server.service.impl;

import com.hyf.server.pojo.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author：洪yf
 * @date：2022/3/21
 */
@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    AdminServiceImpl adminService;
    @Test
    void getRoles() {
        List<Role> roles = adminService.getRoles(2);
        for (Role role:roles)
        {
            System.out.println(role.getName());
        }
    }
}