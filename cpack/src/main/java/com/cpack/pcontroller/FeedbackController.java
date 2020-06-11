package com.cpack.pcontroller;

import com.cpack.mapper.pmapper.FeedbackMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "feedback")
@Api(description ="反馈")
public class FeedbackController {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @PostMapping("/insert")
    @ApiOperation("非json格式即？k=xxx&k=xxx传递，且反馈成功后传回信息插在响应的header中")
    private void insertFeedback(@Param("phone")String phone, @Param("content")String content, HttpServletResponse response){
        feedbackMapper.insert(phone,content);
        response.addHeader("hasFeedback","true");
        System.out.println("收到"+phone+"反馈");
    }
}
