package com.cpack.controller;

import com.cpack.mapper.PublishPackMapper;
import com.cpack.mapper.UserClientMapper;
import com.cpack.model.Packages;
import com.cpack.model.PublishPack;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "packC")
@Api(description ="快递")
public class PackagesClientController {

    @Autowired
    PublishPackMapper packagesMapper;

//    @Autowired
//    PublishPackMapper publishPackMapper;

    @Autowired
    UserClientMapper userClientMapper;

    @GetMapping("get/one")
    @ResponseBody
    @ApiOperation("根据订单号获取快递--快递单号号是唯一的")
    public Packages getByOrderId(@RequestParam @ApiParam(name="订单号",value="orderId",required=true)String orderId){
        return packagesMapper.findByOrderId(orderId);
    }

//    @GetMapping("get/all")
//    @ResponseBody
//    @ApiOperation("获取所有快递")
//    public Packages[] getAllPack(String sIdCard){
//        return packagesMapper.findAllBySIdCard(sIdCard);
//    }


    @PostMapping("update/status")
    @ResponseBody
    @ApiOperation("根据单号更新快递 签状态")
    public int updateQStatus(@RequestParam @ApiParam(name="订单号",value="orderId",required=true)String orderId){
        return packagesMapper.updateQStatus(orderId);
    }

    @GetMapping("ewm/qjm")
    @ResponseBody
    @ApiOperation("根据单号获取快递二维码和取件码")
    public String getEwmAndQJM(@RequestParam @ApiParam(name="发布快递",value="json格式",required=true) String orderId){
        return packagesMapper.findEwmAndQJMByOrderId(orderId);
    }

    @GetMapping("get/all")
    @ResponseBody
    @ApiOperation("获取我的所有快递")
    public Packages[] getAllPack(@RequestParam @ApiParam(name="快递上的电话号码",value="sPhone",required=true)String sPhone){
        return packagesMapper.findAllBySPhone(sPhone);
    }

    @GetMapping("get/all/wait")
    @ResponseBody
    @ApiOperation("获取我的所有待领取快递")
    public Packages[] getAllWait(@RequestParam @ApiParam(name="快递上的电话号码",value="sPhone",required=true)String sPhone){
        return packagesMapper.findAllBySPhoneWait(sPhone);
    }

    @PostMapping("receive/help")
    @ResponseBody
    @ApiOperation(value="确认代取快递 更新代取快递的信息",notes="返回0为普通用户，不具备代领资格")
    public int receiveGetPack(@RequestBody@ApiParam(name="发布快递",value="json格式",required=true) PublishPack publishPack) {
        if(userClientMapper.findById(publishPack.getQIdCard()).getStatus().equals("0"))
            return packagesMapper.updatePublishPack(publishPack.getQNickname(), publishPack.getQIdCard(), publishPack.getQPhone(), publishPack.getOrderId());
        return 0;
    }
}
