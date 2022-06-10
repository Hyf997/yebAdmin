package com.hyf.server.controller;

import com.hyf.server.pojo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：洪yf
 * @date：2022/3/13
 */
@RestController
public class SwaggerController {

    @PostMapping("/home")
    public String  getTest()
    {
        return "登录成功";
    }
    @GetMapping("/employee/basic/hello")
    public String hello2(){
        return "/employee/basic/hello"; }
    @GetMapping("/employee/advanced/hello")
    public String hello3(){
        return "/employee/advanced/hello"; }


}