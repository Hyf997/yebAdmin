package com.hyf.server.controller;


import com.hyf.server.pojo.Admin;
import com.hyf.server.pojo.RespBean;
import com.hyf.server.pojo.Role;
import com.hyf.server.service.IAdminService;
import com.hyf.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyf
 * @since 2022-03-04
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;


    @ApiOperation(value = "获取所有操作员")
    @GetMapping(value = "/")
    public List<Admin> getAllAdmins(String keywords){

        return adminService.getAllAdmins(keywords);
    }

    @ApiOperation(value = "更新操作员")
    @PutMapping(value = "/")
    public RespBean updateAdmin( @RequestBody Admin admin){
        if (adminService.updateById(admin)){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/{id}")
    public RespBean DelAdmin(@PathVariable Integer id){
        if(adminService.removeById(id))
        {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败！");
    }


    @ApiOperation(value = "获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/role")
    public RespBean updateAdminRole(Integer adminId,Integer[] rids)
    {
        return adminService.updateAdminRole(adminId,rids);
    }



}
