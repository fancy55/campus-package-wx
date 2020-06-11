package com.cpack.controller;

import com.aliyuncs.CommonResponse;
import com.aliyuncs.exceptions.ClientException;
import com.cpack.mapper.PublishPackMapper;
import com.cpack.model.Packages;
import com.cpack.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/")
@Api(description ="手机号发送六位验证码")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    PublishPackMapper packagesMapper;

    @GetMapping("give/client")
    @ResponseBody
    @ApiOperation("生成对应取件码、将取件码发送给用户、录入系统")
    public CommonResponse cmsSend(String msg) throws ClientException {//@PathVariable

        Packages packages = packagesMapper.findByEwm(msg);
        if(packages == null) return smsService.send("15211019455", "手机号发送取件码时快递为空");

        //随机产生6位验证码
        StringBuffer code = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i<6; i++){
            int j = random.nextInt(10);
            code.append(j);
        }

        packagesMapper.buildQjm(code.toString(),packages.getOrderId());

        return smsService.send(packages.getSPhone(),code.toString());
    }

    @GetMapping("send/{phone}")
    @ResponseBody
    @ApiOperation("发短信")
    public CommonResponse cmsSend1(@PathVariable String phone) throws ClientException {//@PathVariable

        //随机产生6位验证码
        StringBuffer code = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i<6; i++){
            int j = random.nextInt(10);
            code.append(j);
        }

        return smsService.send(phone,code.toString());
    }
}