package com.pack.Utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSink;
import org.apache.http.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Component
public class OkHttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    @Autowired
    private static OkHttpClient okHttpClient;

    /**
     * get
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static  String get(String url, Map<String, String> queries) {
        String responseBody = "";
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 put error >> ex = {}", e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */
    public static String post(String url, Map<String, String> params, HttpServletResponse response) {
        String responseBody = "";
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
                System.out.println("okhttp post ---------key:"+key+",value:"+params.get(key));
            }
        }

        //使用Gson 添加 依赖 compile 'com.google.code.gson:gson:2.8.1'
        Gson gson = new Gson();
        //使用Gson将对象转换为json字符串
//        String json = gson.toJson(params);
        System.out.println(url+gson.toJson(params));

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , gson.toJson(params));   //JSON.toJSONString(map)


        System.out.println(requestBody);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)        //builder.build()
                .build();
//        Response response = null;
        try {
//            response = okHttpClient.newCall(request).execute();
//            int status = response.code();
//            if (response.isSuccessful()) {
//                return response.body().string();
//            }
//            String code = response.getHeader("msg");
//            if(code.equals("success"))return "success";
//            else if(code.equals("fail"))return "fail";
//            else return "other fail";
            return "success";
        } catch (Exception e) {
            logger.error("okhttp3 post error >> ex = {}", e.getMessage());
            e.printStackTrace();
        }
//        finally {
//            if (response != null) {
//                response.close();
//            }
//        }
        return responseBody;
    }

    /**
     * get
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static String getForHeader(String url, Map<String, String> queries) {
        String responseBody = "";
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder()
                .addHeader("key", "value")
                .url(sb.toString())
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 put error >> ex = {}", e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static String postJsonParams(String url, String jsonParams) {
        String responseBody = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 post error >> ex = {}", e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    /**
     * Post请求发送xml数据....
     * 参数一：请求Url
     * 参数二：请求的xmlString
     * 参数三：请求回调
     */
    public static String postXmlParams(String url, String xml) {
        String responseBody = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 post error >> ex = {}", e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }


    public static void postToCPack(String url){//, Map<String, String> params
        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("bookid",String.valueOf(mBook.bookid))
//                .add("bookName",mBookNameEdit.getText().toString()).build();
        //使用Gson 添加 依赖 compile 'com.google.code.gson:gson:2.8.1'
        Gson gson = new Gson();
        //使用Gson将对象转换为json字符串
//        String json = gson.toJson(params);
//        System.out.println(url+gson.toJson(params));


        //MediaType  设置Content-Type 标头中包含的媒体类型值
//        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
//                , gson.toJson(params));   //JSON.toJSONString(map)

        final Request request = new Request.Builder()
                .url(url)
                //.post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("fail" + "<<<<e="+e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    String d = response.body().string();
                    System.out.println("success" + "<<<<d="+d);
                }
                //System.out.println("success" + "<<<<d="+response.toString());
            }
        });
    }


    public void GetToCPack(String url){
        // 首先需要创建一个OkHttpClient对象用于Http请求, 可以改成全局型
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        // 创建一个request对象
        Request request = new Request.Builder().url(url).build();
        // 执行和回调
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                System.out.println("fail" + "<<<<e="+e);
            }

            public void onResponse(Call call, Response response)
                    throws IOException {
                String str = response.body().string();
                System.out.println("OkHttp的get()请求方式" + str);
            }
        });
    }
}
