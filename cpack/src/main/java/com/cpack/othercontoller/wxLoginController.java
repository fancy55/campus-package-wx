package com.cpack.othercontoller;

import com.cpack.utils.GsonUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description ="openid登录")
@RequestMapping("test")
public class wxLoginController {
    private String url = "https://api.weixin.qq.com/sns/jscode2session";
    private String APP_ID = "wxcb176af751aaade5";
    private String APP_SECRET = "5709704384cb0063a3721a67e3026b63";

    @RequestMapping(value="/getLogin", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public String GetLoginStatus(String code){
        String session = sendPostRequest(url,"appid="+APP_ID+"&secret="+APP_SECRET+"&js_code="+code+"&grant_type=authorization_code");
        Map<String, Object> sessionMap = GsonUtils.fromJson(session, Map.class);
        String openId = (String) sessionMap.get("openid");
        return openId;
    }

    public static String sendPostRequest(String url, String param){
        DataOutputStream dataOutputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (null != url && null != param) {
                URL postUrl = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) postUrl.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(param);
                dataOutputStream.flush();
                bufferedReader =
                        new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line;
                while (null != (line = bufferedReader.readLine())) {
                    stringBuilder.append(line);
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString();
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
        } finally {
            try {
                if (null != dataOutputStream) {
                    dataOutputStream.close();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (Exception e2) {
            }
        }
        return null;
    }

}
