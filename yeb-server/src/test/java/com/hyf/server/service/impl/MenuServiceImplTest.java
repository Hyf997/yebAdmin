package com.hyf.server.service.impl;

import com.hyf.server.mapper.AdminMapper;
import com.hyf.server.mapper.MenuMapper;
import com.hyf.server.pojo.Admin;
import com.hyf.server.pojo.Menu;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author：洪yf
 * @date：2022/3/15
 */
@SpringBootTest
@MapperScan("com.hyf.server.mapper")
class MenuServiceImplTest {

    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Test
    void getMenusByAdminId() {

        List<Menu> menus = menuMapper.getMenusByAdminId(1);
        System.out.println(menus.get(0).getName());

        Admin admin = adminMapper.selectById(1);
        System.out.println(admin.getPassword());
    }

    @Test
    void getAllMenusWithRole() {
        List<Menu> allMenusWithRole = menuMapper.getAllMenusWithRole();
        for(Menu menu:allMenusWithRole){
            System.out.println(menu.getId());
        }
        System.out.println(allMenusWithRole.size());
    }

    @Test
    void getAllMenus() {
        List<Menu> allMenus = menuService.getAllMenus();
        allMenus.forEach(System.out::println);
    }
}