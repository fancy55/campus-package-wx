package com.cpack.pcontroller;

import com.cpack.mapper.PublishPackMapper;
import com.cpack.model.Packages;
import com.cpack.model.PublishPack;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "providerPack")
@Api(description ="站点对快递的操作")
public class PackagesController {
    @Autowired
    PublishPackMapper packagesMapper;

    @PostMapping(value = "post")
    @ResponseBody
    @ApiOperation("通过此接口将快递信息保存到数据库、同时发给用户、且当用户收到快递的二维码路由之后")
    public int PostPack( @RequestBody @ApiParam(name="快递信息录入系统",value="json格式",required=true) Packages packages){
                        // @RequestParam  @ApiParam(name="领取快递人的电话号码",value="phone",required=true)String phone
        //EWMController ewm = new EWMController();
        if(packagesMapper.findByOrderId(packages.getOrderId()) != null)return -1;
        packages.setEwm(EWMController.getURCodeImage(packages.getSPhone(),packages.getOrderId()));//二维码的url存储到数据库，一旦收状态和签状态符合则失效
        return packagesMapper.insertPackages(packages);
    }

    @GetMapping("pDate")
    @ResponseBody
    @ApiOperation("根据录入日期获取包裹信息")
    public Packages[] SelectByDate(@RequestParam @ApiParam(name="扫描录入日期",value="pDate",required=true)String pDate){
        return packagesMapper.findByPDate(pDate);
    }

    @PostMapping("check")
    @ResponseBody
    @ApiOperation("验证 扫描用户提供的二维码时 是否与数据库中信息符合")
    public String checkInfo(@RequestParam @ApiParam(name="电话号码",value="phone",required=true) String phone,
                            @RequestParam @ApiParam(name="快递单号",value="orderId",required=true) String orderId,
                            @RequestParam @ApiParam(name="取件时间",value="pDate",required=true) String pDate,
                            @RequestParam @ApiParam(name="uuid",value="uuid",required=true)String uuid){
        PublishPack packages = packagesMapper.findAllByOrderId(orderId);
        String _ewm = packages.getEwm();
        String[] infos = _ewm.split("/");
        String[] info = infos[infos.length-1].split("-");
        String _uuid = "";
        for(int i = 2;i < info.length; i++){
            if(i == info.length-1)
                _uuid = _uuid + info[i];
            else _uuid = _uuid + info[i] + "-";
        }
        _uuid = _uuid.substring(0,_uuid.length()-4);
//        System.out.println("info[0]:"+info[0]+",-------_uuid:"+_uuid);

        if(info[1].equals(orderId) && uuid.equals(_uuid)){
            if(info[0].equals(phone)){
                //自己
                packagesMapper.updateSStatus(pDate, orderId);
            }else if(phone.equals(packages.getQPhone())){
                packagesMapper.updateSStatusWithStatus(pDate, orderId);
            }
            else return "验证失败";
            return "验证成功";
        }
        return "验证失败";
    }

    @GetMapping("get/one")
    @ResponseBody
    @ApiOperation("根据订单号获取快递--快递单号号是唯一的")
    public Packages getByOrderId(@RequestParam @ApiParam(name="订单号",value="orderId",required=true)String orderId){
        return packagesMapper.findByOrderId(orderId);
    }
}
