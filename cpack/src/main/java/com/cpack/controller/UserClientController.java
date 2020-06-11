package com.cpack.controller;

import com.alibaba.fastjson.JSONArray;
import com.cpack.mapper.CheckTMapper;
import com.cpack.mapper.PublishPackMapper;
import com.cpack.mapper.UserClientMapper;
import com.cpack.model.CheckT;
import com.cpack.model.Report;
import com.cpack.model.UserClient;
import com.cpack.service.IdCardService;
import com.cpack.utils.UploadPhotosUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "student")
@Api(description = "学生用户")
public class UserClientController {
    @Autowired
    private UserClientMapper userMapper;
    @Autowired
    UploadPhotosUtils uploadPhotosUtils;
    @Autowired
    PublishPackMapper publishPackMapper;
    @Autowired
    CheckTMapper checkTMapper;
    @Autowired
    IdCardService idCardService;

    Map<Integer, String> m = new HashMap<>();
    List<Object> l = new ArrayList<>();

    @PostMapping("login")
    @ResponseBody
    @ApiOperation(value="登录",notes="成功：返回用户个人信息/失败：返回空")
    public UserClient login(@RequestParam @ApiParam(name="phone",value="用户电话号码",required=true)String phone,
                            @RequestParam @ApiParam(name="password",value="用户密码",required=true)String password) {
        return userMapper.login(phone,password);
    }

//    @PostMapping("register/photo")
//    @ResponseBody
//    @ApiOperation("注册")
//    public String upLoadImage(@ApiParam(name="用户头像",value="MultipartFile文件",required=true)MultipartFile file){
//        if(file!=null) {
//            m = uploadPhotosUtils.getImageURL(file);
//            if (!(m.get(0) == null || m.get(0).equals("err")))
//                user.setPhoto(m.get(0));
//        }
//        else return "头像上传失败";
//    }

    @PostMapping("register")
    @ResponseBody
    @ApiOperation("注册")
    public String Register(@RequestBody @ApiParam(name="用户对象不含头像",value="json格式",required=true)UserClient user) {
        if(user.getPhone() == null)return "注册手机号不能为空";
        if(userMapper.findById(user.getIdCard()) != null || userMapper.findByPhone(user.getPhone()) != null)return "不能重复注册";
        if(!idCardService.authentication(user.getIdCard(), user.getName()).equals("true"))
            return "没有通过身份证实名认证";
        user.setPhoto("http://47.104.191.228:8089/photo/logo1.png");
        userMapper.register(user);
        return "注册成功";
    }

    @GetMapping("getInfo")
    @ResponseBody
    @ApiOperation("根据idCard查询用户信息")
    public UserClient getUserInfo(@RequestParam @ApiParam(name="idCard",value="用户身份证",required=true)String idCard){
        return userMapper.findById(idCard);
    }

    @PostMapping("updateInfo")
    @ResponseBody
    @ApiOperation("根据idCard修改信息 可修改phone[注意再发验证码进行验证]、password【记得二次验证】、address、college、major、nickName、grade、sex、age")
    public String alterByIdCard(@RequestBody @ApiParam(name="用户对象",value="json格式",required=true)UserClient user){
        UserClient user1 = userMapper.findById(user.getIdCard());
        if(user1 == null)return "不存在此用户";
        UserClient user2 = user1;
        if(user.getPhone() != null)user1.setPhone(user.getPhone());
        if(user.getAddress() != null)user1.setAddress(user.getAddress());
        if(user.getCollege() != null)user1.setCollege(user.getCollege());
        if(user.getMajor() != null)user1.setMajor(user.getMajor());
        if(user.getNickname() != null)user1.setNickname(user.getNickname());
        if(user.getGrade() != null)user1.setGrade(user.getGrade());
        if(user.getSex() != null)user1.setSex(user.getSex());
        if(user.getAge() != null)user1.setAge(user.getAge());
        if(user.getPassword() != null)user1.setPassword(user.getPassword());
        try{
            userMapper.deleteByIdCard(user1.getIdCard());
            try{
                userMapper.register(user1);
            }catch (Exception e){
                e.printStackTrace();
                userMapper.register(user2);
                return "db 修改用户信息时出错";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "db 修改用户信息时删除出错";
        }
        return "信息修改成功";
    }

    @ResponseBody
    @PostMapping("updatePhoto")
    @ApiOperation("修改头像")
    public String alterPhoto(@ApiParam(name="用户头像",value="file文件",required=true) MultipartFile file,
                             @RequestParam @ApiParam(name="身份证",value="idCard",required=true)String idCard){
        m = uploadPhotosUtils.getImageURL(file);
        UserClient user1 = userMapper.findById(idCard);
        if(user1 == null)return "数据库无此用户";
        if(!(m.get(0) == null || m.get(0).equals("err"))) {
            user1.setPhoto(m.get(0));
            userMapper.deleteByIdCard(idCard);
            userMapper.register(user1);
            publishPackMapper.alterPhoto(user1.getPhoto(), user1.getIdCard());
        }
        else return "头像上传出错";
        return "头像上传成功";
    }

    @PostMapping("forget")
    @ApiOperation("忘记密码 phone+新password")
    @ResponseBody
    public String forget(@RequestParam @ApiParam(name="phone",value="用户电话号码",required=true)String phone,
                         @RequestParam @ApiParam(name="password",value="用户密码",required=true)String password){
        UserClient user1 = userMapper.findByPhone(phone);
        if(user1 != null ) {
            user1.setPassword(password);
            userMapper.deleteByIdCard(user1.getIdCard());
            userMapper.register(user1);
            return "修改成功";
        }
        return "忘记为空 或者手机号错误";
    }

    @GetMapping("{type}")
    @ApiOperation("核实账号状态 通过idCard或phone")
    @ResponseBody
    public String getStatus(@PathVariable String type){
        if(type.equals("phone"))return userMapper.findByPhone(type).getStatus();
        if(type.equals("idCard"))return userMapper.findById(type).getStatus();
        return "传参err";
    }

    @PostMapping("post/certification")
    @ApiOperation("提交审核的资料")
    @ResponseBody
    public String postCheck(MultipartFile file, int size, String msg){
        CheckT checkT = JSONArray.parseObject(msg, CheckT.class);
        Map<Integer, String> map1 = uploadPhotosUtils.getImageURL(file);
        if (!(map1.get(0) == null || map1.get(0).equals("err"))) {
            switch (size) {
                case 4:
                    checkTMapper.alterf2(map1.get(0),checkT.getIdCard());
                    break;
                case 3:
                    checkTMapper.alterf3(map1.get(0),checkT.getIdCard());
                    break;
                case 2:
                    checkTMapper.alterf4(map1.get(0),checkT.getIdCard());
                    break;
                case 1:
                    checkT.setFile1(map1.get(0));
                    break;
                default:
                    return "上传图片张数错误，应>=1，<=4";
            }
        }
        if(size == 1) {
            if(checkTMapper.getMy(checkT.getIdCard())!=null)checkTMapper.alterf1(map1.get(0),checkT.getDate(),checkT.getIdCard());
            else checkTMapper.postCheck(checkT.getIdCard(),checkT.getFile1(),checkT.getDate());
            return "第"+size+"图片发布成功";
        }
        else if(size>1 && size <= 3){
            return "第"+size+"图片发布成功";
        }
        else if(size == 4)return "提交申请成功";
        return "size err";
    }

    @GetMapping("get/my/certification")
    @ApiOperation("查看我申请代领的审核状态")
    @ResponseBody
    public String getCheck(@RequestParam @ApiParam(name="idCard",value="用户身份证",required=true)String idCard){
        return checkTMapper.getMy1(idCard);
    }

}
