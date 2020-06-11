package com.cpack.pcontroller;

import com.cpack.mapper.PublishPackMapper;
import com.cpack.model.UserProvider;
import com.cpack.mapper.pmapper.UserProviderMapper;
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
@RequestMapping(value = "provider")
@Api(description = "快递站点端")
public class UserProviderController {
    @Autowired
    private UserProviderMapper userProviderMapper;
    @Autowired
    UploadPhotosUtils uploadPhotosUtils;
    @Autowired
    PublishPackMapper publishPackMapper;
    @Autowired
    IdCardService idCardService;

    Map<Integer, String> m = new HashMap<>();
    List<Object> l = new ArrayList<>();

    @PostMapping("login")
    @ResponseBody
    @ApiOperation(value="登录",notes="成功：返回用户个人信息/失败：返回空")
    public UserProvider login(@RequestParam @ApiParam(name="phone",value="用户电话号码",required=true)String phone,
                              @RequestParam  @ApiParam(name="password",value="用户密码",required=true)String password) {
        return userProviderMapper.login(phone,password);
    }

    @PostMapping("register")
    @ResponseBody
    @ApiOperation("注册")
    public String Register(@RequestBody  @ApiParam(name="用户对象不含头像",value="json格式",required=true)UserProvider user) {
//        m = uploadPhotosUtils.getImageURL(file);
//        if(!(m.get(0) == null || m.get(0).equals("err")))

        if(user.getPhone() == null)return "注册手机号不能为空";
        if(userProviderMapper.findById(user.getIdCard()) != null || userProviderMapper.findByPhone(user.getPhone()) != null)return "不能重复注册";
        if(!idCardService.authentication(user.getIdCard(), user.getName()).equals("true"))
            return "没有通过身份证实名认证";
        user.setPhoto("http://47.104.191.228:8089/photo/IMG_0417.jpg");
        userProviderMapper.register(user);
        return "注册成功";
    }

    @GetMapping("getInfo")
    @ResponseBody
    @ApiOperation("根据idCard查询用户信息")
    public UserProvider getUserInfo(@RequestParam @ApiParam(name="身份证",value="idCard",required=true)String idCard){
        return userProviderMapper.findById(idCard);
    }

    @PostMapping("updateInfo")
    @ResponseBody
    @ApiOperation("根据idCard修改信息 除头像外")
    public String alterByIdCard(@RequestBody @ApiParam(name="用户对象",value="json格式",required=true)UserProvider user){
        UserProvider user1 = userProviderMapper.findById(user.getIdCard());
        if(user1 == null)return "不存在此用户";
        UserProvider user2 = user1;
        if(user.getPhone() != null)user1.setPhone(user.getPhone());
        if(user.getAddress() != null)user1.setAddress(user.getAddress());
        if(user.getNickname() != null)user1.setNickname(user.getNickname());
        if(user.getSex() != null)user1.setSex(user.getSex());
        if(user.getAge() != null)user1.setAge(user.getAge());
        if(user.getPassword() != null)user1.setPassword(user.getPassword());
        if(user.getComName() != null)user1.setComName(user.getComName());
        try{
            userProviderMapper.deleteByIdCard(user1.getIdCard());
            try{
                userProviderMapper.register(user1);
            }catch (Exception e){
                e.printStackTrace();
                userProviderMapper.register(user2);
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
        UserProvider user1 = userProviderMapper.findById(idCard);
        if(user1 == null)return "数据库无此用户";
        if(!(m.get(0) == null || m.get(0).equals("err"))) {
            user1.setPhoto(m.get(0));
            userProviderMapper.deleteByIdCard(idCard);
            userProviderMapper.register(user1);
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
        UserProvider user1 = userProviderMapper.findByPhone(phone);
        if(user1 != null ) {
            user1.setPassword(password);
            userProviderMapper.deleteByIdCard(user1.getIdCard());
            userProviderMapper.register(user1);
            return "修改成功";
        }
        return "忘记为空 或者手机号错误";
    }

    @GetMapping("{type}")
    @ApiOperation("核实账号状态 通过idCard或phone")
    @ResponseBody
    public String getStatus(@PathVariable String type){
        if(type.equals("phone"))return userProviderMapper.findByPhone(type).getStatus();
        if(type.equals("idCard"))return userProviderMapper.findById(type).getStatus();
        return "传参err";
    }


}
