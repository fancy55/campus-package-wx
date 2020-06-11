package com.cpack.controller;

import com.cpack.mapper.LostPackMapper;
import com.cpack.model.LostPack;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lost")
@Api(description ="丢失快递")
public class LostPackController {

    @Autowired
    LostPackMapper lostPackMapper;

    @PostMapping("post")
    @ResponseBody
    @ApiOperation(("提交丢失快递"))
    public int insertLost(@RequestBody@ApiParam(name="丢失",value="json格式",required=true) LostPack lostPack){
        return lostPackMapper.insertLostPack(lostPack);
    }


    @PostMapping("get/all")
    @ResponseBody
    @ApiOperation(("得到所有丢失快递"))
    public LostPack[] getLost(){
        return lostPackMapper.find();
    }
}
