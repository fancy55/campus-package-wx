package com.cpack.mcontroller;

import com.cpack.mapper.CheckTMapper;
import com.cpack.mapper.ReportMapper;
import com.cpack.model.CheckT;
import com.cpack.model.Report;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(description ="超级管理员审核")
@RequestMapping("checkT")
public class CheckTController {
    @Autowired
    CheckTMapper checkTMapper;
    @Autowired
    ReportMapper reportMapper;

    @GetMapping("get/all/certification")
    @ApiOperation("查看所有的申请")
    @ResponseBody
    public CheckT[] getCheck(){
        return checkTMapper.getAll();
    }

    @GetMapping("get/ing/certification")
    @ApiOperation("查看正在的申请")
    @ResponseBody
    public CheckT[] getCheckING(){
        return checkTMapper.getStatus0();
    }

    @GetMapping("get/ed/certification")
    @ApiOperation("查看通过的申请")
    @ResponseBody
    public CheckT[] getCheckED(){
        return checkTMapper.getStatus1();
    }

    @GetMapping("get/sb/report")
    @ApiOperation("查看某人举报")
    @ResponseBody
    public Report[] getReport(@RequestParam @ApiParam(name="reportIdCard",value="用户身份证",required=true)String reportIdCard){
        return checkTMapper.getMy(reportIdCard);
    }

    @PostMapping("pass/check")
    @ApiOperation("根据身份证通过审核")
    @ResponseBody
    public int passCheck(@RequestParam @ApiParam(name="pDate",value="通过日期",required=true)String pDate,
                         @RequestParam  @ApiParam(name="idCard",value="用户身份证",required=true)String idCard){
        return checkTMapper.passCheck(pDate,idCard);
    }

    @PostMapping("not/pass/check")
    @ApiOperation("根据身份证审核失败")
    @ResponseBody
    public int NotPassCheck(@RequestParam @ApiParam(name="pDate",value="通过日期",required=true)String pDate,
                            @RequestParam @ApiParam(name="idCard",value="用户身份证",required=true)String idCard){
        return checkTMapper.notPassCheck(pDate,idCard);
    }

    @ResponseBody
    @GetMapping("get/all/report")
    @ApiOperation("查看所有举报")
    public Report[] getAllReport(){
        return reportMapper.findAll();
    }


    @ResponseBody
    @PostMapping("deal/report")
    @ApiOperation("处理举报")
    public int dealReport(@RequestBody@ApiParam(name="举报",value="json格式,reportId+date+orderId",required=true) Report report){
        return reportMapper.dealReport(report.getReportIdCard(),report.getDate(),report.getOrderId());
    }
}
