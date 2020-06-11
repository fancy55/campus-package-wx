package com.cpack.controller;

import com.cpack.mapper.FeedbackClientMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "feedbackC")
@Api(description ="反馈")
public class FeedbackClientController {
    @Autowired
    private FeedbackClientMapper feedbackMapper;

    @PostMapping("insert")
    @ResponseBody
    @ApiOperation("提交反馈")
    private int insertFeedback(@Param("phone")String phone, @Param("content")String content){
        return feedbackMapper.insert(phone,content);
    }
}
