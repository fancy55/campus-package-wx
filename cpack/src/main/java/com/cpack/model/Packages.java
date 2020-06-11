package com.cpack.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ApiModel("快递")
public class Packages {
    @ApiModelProperty(value="快递单号",name="orderId",required=true)
    String orderId;
    @ApiModelProperty(value="扫描快递的日期",name="pDate",required=true)
    String pDate;
    @ApiModelProperty(value="快递上的电话号码",name="sPhone",required=true)
    String sPhone;
    @ApiModelProperty(value="用于验证的二维码",name="ewm",required=true)
    String ewm;
    @ApiModelProperty(value="取件码",name="qjm",required=true)
    String qjm;
    @ApiModelProperty(value="领取快递的日期",name="gDate",required=true)
    String gDate;
    @ApiModelProperty(value="签状态 用户手动",name="qStatus",required=true,example="已领1/-1未领")
    String qStatus;
    @ApiModelProperty(value = "收状态 扫描被领",name="sStatus",required=true,example = "1快递被领取取时/-1未被领取")
    String sStatus;
    @ApiModelProperty(value="收录快递站点人员身份证",name="zIdCard",required=true)
    String zIdCard;
    @ApiModelProperty(value = "收录快递站点人员手机号",name="zphone",required=true)
    String zphone;
    @ApiModelProperty(value="取件地址",name="qPos",required=true)
    String qPos;
    @ApiModelProperty(value="快递状态",name="status",required = true,example = "1已代领/0未代领/-1失效")
    String status;
//    @ApiModelProperty(value="真实取件人电话号码",name="rQPhone",required = true)
//    String rQPhone;
}
