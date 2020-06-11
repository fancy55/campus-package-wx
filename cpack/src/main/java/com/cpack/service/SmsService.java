package com.cpack.service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.exceptions.ClientException;
import com.cpack.utils.SendUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsService {
    static final Logger LOGGER = LoggerFactory.getLogger(SendUtils.class);

    @Autowired
    private SendUtils sendUtils;

    //发送验证码
    public CommonResponse send(String phone, String code) throws ClientException {
        CommonResponse response = sendUtils.send(phone,code);
        if (response != null && response.getHttpStatus() == 200){
            String data = response.getData();
            response.setData("code:"+code+",date:"+data);
            Map map = JSON.parseObject(data, Map.class);
            LOGGER.info("短信发送状态:{}" + phone + "=====" + code + "=====" + map.get("Message"));
        }
        //以下为Api的测试代码，不做理会即可
//        boolean success = response.getHttpResponse().isSuccess();  //true
//        int status = response.getHttpResponse().getStatus(); //200
//        int httpStatus = response.getHttpStatus(); //200
        return response;
    }

}
