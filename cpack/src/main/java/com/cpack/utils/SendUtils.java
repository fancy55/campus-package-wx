package com.cpack.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.cpack.config.SmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(SmsConfig.class)
public class SendUtils {
    @Autowired
    private  SmsConfig smsConfig;

    //发送短信的方法
    public CommonResponse send(String phone, String code)throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        DefaultProfile profile = DefaultProfile.getProfile("default",smsConfig.getAccessKeyId() , smsConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        //封装请求对象
        CommonRequest request = new CommonRequest();
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setAction("SendSms");
        request.setVersion("2017-05-25");
        request.setMethod(MethodType.POST);
        //确定发送的电话号码和 验证码
        request.putQueryParameter("PhoneNumbers", phone);
        //这里也得注意，对应的值是Json格式的字符串，不然就是一杯茶一根烟，一个bug玩一天
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        //确定是的模版和签名
        request.putQueryParameter("SignName", smsConfig.getSignName());
        request.putQueryParameter("TemplateCode", smsConfig.getTemplateCode());
        //发起调用
        try {
            CommonResponse response = client.getCommonResponse(request);
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
