package com.cpack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(description ="简单页面展示")
public class OtherPageController {

    @GetMapping("/login")
    @ApiOperation("当用户已经进入小程序，但是还没有完善个人信息时则在功能界面显示该页面")
    public String notLogin(){
        return "NotLogin";
    }

    @GetMapping("/err")
    @ApiOperation("出现错误则可显示该页面")
    public String error(){
        return "Error";
    }
}
