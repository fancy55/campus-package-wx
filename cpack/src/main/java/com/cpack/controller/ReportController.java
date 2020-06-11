package com.cpack.controller;

import com.cpack.mapper.ReportMapper;
import com.cpack.model.Report;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("report")
@Api(description ="举报")
public class ReportController {
    @Autowired
    ReportMapper reportMapper;

    @PostMapping("post")
    @ApiOperation("提交举报")
    @ResponseBody
    public int reportOthers(@RequestBody@ApiParam(name="举报",value="json格式",required=true) Report report){
        return reportMapper.insert(report);
    }


}
