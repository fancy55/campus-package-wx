package com.cpack.service;

import com.alibaba.fastjson.JSONObject;
import com.cpack.config.IdCardConfig;
import com.cpack.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IdCardService {

    @Autowired
    IdCardConfig idCardConfig;

    public String realNameAuthentication(String idCard, String realName) {
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + idCardConfig.getAppCode());
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("cardNo", idCard);
        querys.put("realName", realName);

        try {
            HttpResponse response = HttpUtils.doGet(idCardConfig.getHost(), idCardConfig.getPath(), idCardConfig.getMethod(), headers, querys);
            System.out.println(response.toString());
            //获取response的body
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "err";
    }


    public String authentication(String idCard, String realName){
        String res = realNameAuthentication(idCard, realName);
        JSONObject object = JSONObject.parseObject(res);
        JSONObject person = JSONObject.parseObject(object.get("result").toString());
        return person.get("isok").toString();
    }
}
