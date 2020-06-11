package com.cpack.utils;

import com.cpack.config.PhotoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class UploadPhotosUtils {
    Map<Integer, String> map = new HashMap<>();

    @Autowired
    PhotoConfig photoConfig;

    //上传图片并返回访问路由 至少一张，最多六张
 /*   public Map<Integer, String> getPhotoURL(@RequestParam("file") MultipartFile[] files, String phone){
        if(files == null){
            map.put(0,"null");
            return map;
        }
        int cnt = 0;
        for(MultipartFile mf: files){
            String uuid = UUID.randomUUID().toString();
            try {
                File f = new File(photoConfig.getPath()+phone.substring(0,3)+"-"+phone.substring(7,11)+"-"+uuid+photoConfig.getType());
                mf.transferTo(f); //把 MultipartFile 转换成 File // getOriginalFilename()得到原来的文件名在客户机的文件系统名称
                map.put(cnt++, photoConfig.getUrl() +phone.substring(0,3)+"-"+phone.substring(7,11)+"-"+uuid+photoConfig.getType());
                if(cnt >= 6)break;
            }catch (Exception e){
                e.printStackTrace();
                map.put(0,"err");
            }

        }
        return map;
    }*/

    public Map<Integer, String> getImageURL(@RequestParam("file") MultipartFile mf){
        String uuid = UUID.randomUUID().toString();
        try {
            File f = new File(photoConfig.getPath()+uuid+photoConfig.getType());
            mf.transferTo(f); //把 MultipartFile 转换成 File ,getOriginalFilename(); //得到原来的文件名在客户机的文件系统名称
            map.put(0, photoConfig.getUrl()+uuid+photoConfig.getType());
        }catch (Exception e){
            e.printStackTrace();
            map.put(0,"err");
        }
        return map;
    }
}
