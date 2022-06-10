package com.hyf.server.controller;

import com.hyf.server.pojo.Admin;
import com.hyf.server.pojo.AdminLoginParam;
import com.hyf.server.pojo.RespBean;
import com.hyf.server.service.IAdminRoleService;
import com.hyf.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author：洪yf
 * @date：2022/3/6
 *
 * 登录
 */

@Api(tags = "loginController")
@RestController
public class LoginController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登陆之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),
                adminLoginParam.getCode(),request);
    }
    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if(null==principal){
            return null;
        }
        String name = principal.getName();
     Admin admin = adminService.getAdminByUserName(name);
     admin.setPassword(null);
     admin.setRoles(adminService.getRoles(admin.getId()));
     return admin;
    }


    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logOut(){
        return RespBean.success("注销成功！");
    }

}
