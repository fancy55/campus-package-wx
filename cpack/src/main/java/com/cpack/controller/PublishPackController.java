package com.cpack.controller;

import com.cpack.mapper.PublishPackMapper;
import com.cpack.mapper.UserClientMapper;
import com.cpack.model.Packages;
import com.cpack.model.PublishPack;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(description ="发布快递")
@RequestMapping("publishPack")
public class PublishPackController {
    @Autowired
    UserClientMapper userClientMapper;

    @Autowired
    PublishPackMapper publishPackMapper;

    PublishPack[] publishPack;


    @PostMapping("publish")
    @ResponseBody
    @ApiOperation(value="发布快递",notes="有些数据不能为空，具体看数据库")
    public int publishPack(@RequestBody@ApiParam(name="发布快递",value="json格式",required=true) PublishPack publishPack){
        return publishPackMapper.alterPublishPack(publishPack);
    }

    @PostMapping("/receive/{msg}")
    @ResponseBody
    @ApiOperation("查询发布的快递 按订单查询/按发布人sIdCard查询/按发布人地址查询/按价格查询/ 按日期查询/")
    public PublishPack[] receivePack(@PathVariable String msg,
                                     @RequestParam @ApiParam(name="参数",value="非json格式",required=true)String para){
        switch (msg){
            case "order": publishPack = publishPackMapper.findById(para);
                if(publishPack.length > 1) return null;
            case "real": publishPack = publishPackMapper.findBySIdCard(para);break;
            case "qPos": publishPack = publishPackMapper.findByQPos(para);break;
            case "price": publishPack = publishPackMapper.findByPrice(para);break;
            case "date": publishPack = publishPackMapper.findByDate(para);break;
        }
        return publishPack;
    }

//    @PostMapping("receive/help")
//    @ResponseBody
//    @ApiOperation(value="确认代取快递 更新代取快递的信息",notes="返回0为普通用户，不具备代领资格")
//    public int receiveGetPack(@RequestBody@ApiParam(name="发布快递",value="json格式",required=true) PublishPack publishPack) {
//        if(!(userClientMapper.findById(publishPack.getQIdCard()).getStatus().equals("1")))return 0;
//        return publishPackMapper.updatePublishPack(publishPack.getQIdCard(), publishPack.getQPhone(), publishPack.getOrderId());
//    }

    @PostMapping("get/all/publish")
    @ResponseBody
    @ApiOperation("查询所有发布的快递")
    public PublishPack[] getAllPack() {
        return publishPackMapper.findAll();
    }

//    @GetMapping("get/qjm")
//    @ResponseBody
//    @ApiOperation(value="代领人获取取件码 二维码",notes="orderId+sIdCard")
//    public String getQJM(@RequestBody @ApiParam(name="发布快递",value="json格式",required=true) PublishPack publishPack){
//        PublishPack[] publishPacks = publishPackMapper.findById(publishPack.getOrderId());
//        if(publishPacks!=null && (publishPacks[0].getQIdCard() == null || (!publishPack.getQIdCard().equals(publishPacks[0].getQIdCard()))))
//            return "此代领快递还没有代领人，null代领人无法获取取件码";
//        Packages packages = publishPackMapper.findByOrderId(publishPack.getOrderId());
//        return packages.getQjm()+","+packages.getEwm();
//    }

//    @GetMapping("ewm/qjm")
//    @ResponseBody
//    @ApiOperation("根据单号获取快递二维码和取件码")
//    public String getEwmAndQJM(@RequestParam @ApiParam(name="发布快递",value="json格式",required=true) String orderId){
//        return publishPackMapper.findEwmAndQJMByOrderId(orderId);
//    }

    @GetMapping("get/help")
    @ResponseBody
    @ApiOperation("查询我代领的快递")
    public PublishPack[] getMyHelp(@RequestParam @ApiParam(name="领取人身份证",value="qIdCard",required=true) String qIdCard){
        return publishPackMapper.findByQIdCard(qIdCard);
    }

    @GetMapping("get/help/wait")
    @ResponseBody
    @ApiOperation("查询我代领的快递中未领取")
    public PublishPack[] getMyHelpWait(@RequestParam @ApiParam(name="领取人身份证",value="qIdCard",required=true) String qIdCard){
        return publishPackMapper.findByQIdCardStatus(qIdCard);
    }

    @GetMapping("get/publish")
    @ResponseBody
    @ApiOperation("查询我发布的快递")
    public PublishPack[] getMyPublish(@RequestParam  @ApiParam(name="发布人身份证",value="sIdCard",required=true) String sIdCard){
        return publishPackMapper.findBySIdCard(sIdCard);
    }

    @GetMapping("get/publish/wait")
    @ResponseBody
    @ApiOperation("查询我发布的快递中未领取")
    public Object getMyPublishWait(@RequestParam @ApiParam(name="发布人身份证",value="sIdCard",required=true) String sIdCard){
        return publishPackMapper.findBySIdCardStatus(sIdCard);
    }

//    @GetMapping("get/all")
//    @ResponseBody
//    @ApiOperation("获取我的所有快递")
//    public Packages[] getAllPack(@RequestParam @ApiParam(name="快递上的电话号码",value="sPhone",required=true)String sPhone){
//        return publishPackMapper.findAllBySPhone(sPhone);
//    }
//
    @PostMapping("no/use")
    @ResponseBody
    @ApiOperation("代领快递领取超过期限失效")
    public int noUse(@RequestParam @ApiParam(name="订单",value="orderId",required=true)String orderId){
        return publishPackMapper.upDateStatus(orderId);
    }
}
