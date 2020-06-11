package com.cpack.pcontroller;


import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.pack.Utils.OkHttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/")
@Api(description ="将用户信息封装成二维码  你们不需要调用此接口")
public class EWMController {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    public static final String TYPE = "png";
    public static final String EWM_URL = "http://www.qlybit.xyz:8089/ewm/";
//    public static final String EWM_URL = "http://47.104.191.228:8082/ewm/";
//    public static final String SEND_CLIENT_URL = "http://localhost:8085/send?msg=";
    public static final String SEND_CLIENT_URL = "http://47.104.191.228:8085/give/client?msg=";
    static final Logger LOGGER = LoggerFactory.getLogger(EWMController.class);

    @Autowired
    private static OkHttpUtils okHttpCli;

    //1、二维码信息存储到数据库【取件时微信小程序验证】   /ewm?content=xxx&orderId=xxx
    //2、发送给用户,将用户的签状态变为yes
    @GetMapping("ewm")
    @ApiOperation("返回二维码路由")
    public static String getURCodeImage(String phone, String orderId) {

            //定义二维码配置，hashMap
            HashMap hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.MARGIN, 2);

            UUID uuid = UUID.randomUUID();
            String returnMsg = "";

            Map<String, String> _ewm = new HashMap<>();
            _ewm.put("content", phone);
            _ewm.put("orderId", orderId);
            _ewm.put("valueId", uuid.toString());

            Date date = new Date();
            System.out.println(date);

            try {
                BitMatrix bitMatrix = new MultiFormatWriter()
                        .encode(String.valueOf(_ewm), BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
                //String path = ClassUtils.getDefaultClassLoader().getResource("static/photo").getPath();
//                            File f = new File("src/main/resources/static/photo/"+orderId+"-"+ uuid+".png");
                File f = new File("/root/vr/ewm/" +phone+"-"+ orderId + "-" + uuid + ".png");
                Path file = f.toPath();
                MatrixToImageWriter.writeToPath(bitMatrix, TYPE, file);
                String final_url = EWM_URL+phone+"-" + orderId + "-" + uuid + ".png";
                //            System.out.println("URL："+final_url);

                ///2、发送给用户
                Map<String, String> msg = new HashMap<>();
                msg.put("ewmMsg", final_url);

                Gson gson = new Gson();
                //使用Gson将对象转换为json字符串
//        String json = gson.toJson(params);
//                System.out.println(url+gson.toJson(params));

//                String url = SEND_CLIENT_URL+gson.toJson(msg);
//                System.out.println("发给用户："+url);
                String url = SEND_CLIENT_URL+final_url;
                System.out.println(url);

//                okHttpCli.GetToCPack(url);
                okHttpCli.postToCPack(url);   //SEND_CLIENT_URL, msg(发送给用户的url, key[])
//                String message = okHttpCli.post(SEND_CLIENT_URL, msg, response);   //(发送给用户的url, key[])
                System.out.println("after send EWM to Client Server"  + "----------------");
                //////////////////////

                return final_url;
            } catch (Exception e) {
                e.printStackTrace();
                returnMsg = e.getMessage();
                LOGGER.info("二维码出错");
            }

            return "二维码出错：" + returnMsg;

    }

//    @ResponseBody
    public void rtPostObject(){
        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://47.xxx.xxx.96/register/checkEmail";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", "844072586@qq.com");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( SEND_CLIENT_URL, request , String.class );
        System.out.println(response.getBody());
    }

}
