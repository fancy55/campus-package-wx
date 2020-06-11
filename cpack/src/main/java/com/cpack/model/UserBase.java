package com.cpack.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class UserBase implements Serializable {
    @ApiModelProperty(value="用户真实姓名",name="name",required=true)
    String name;
    @ApiModelProperty(value="用户昵称",name="nickname",required=true)
    String nickname;
    @ApiModelProperty(value="电话号码",name="phone",required=true)
    String phone;
    @ApiModelProperty(value="身份证",name="idCard",required=true)
    String idCard;
    @ApiModelProperty(value="性别",name="sex",required=true,example="男/女")
    String sex;
    @ApiModelProperty(value="年龄",name="age",required=true)
    String age;
    @ApiModelProperty(value="注册日期",name="idCard",required=true)
    String date;
    @ApiModelProperty(value="密码",name="password",required=true)
    String password;
    @ApiModelProperty(value="头像",name="photo",required=true)
    String photo;
    @ApiModelProperty(value="地址",name="address",required=true)
    String address;
    @ApiModelProperty(value="账号状态",name="state",required=true,example="1代领功能/-1封号/0普通用户，无法代领/2站点")
    String status;
}
