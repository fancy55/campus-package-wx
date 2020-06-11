package com.cpack.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
@ApiModel("快递")
public class PublishPack implements Serializable {
    @ApiModelProperty(value="快递单号",name="orderId",required=true)
    String orderId;
    @ApiModelProperty(value="用户身份证",name="qIdCard",required=true)
    String qIdCard;
    @ApiModelProperty(value="取件人身份证",name="sIdCard",required =true)
    String sIdCard;
    @ApiModelProperty(value="送货地址",name="sPos",required=true)
    String sPos;
    @ApiModelProperty(value="用户电话号码",name="sPhone",required=true)
    String sPhone;
    @ApiModelProperty(value="取件人电话号码",name="qPhone",required = true)
    String qPhone;
    @ApiModelProperty(value="用户昵称",name="sNickname",required=true)
    String sNickname;
    @ApiModelProperty(value="取件人昵称",name="qNickname",required = true)
    String qNickname;
    @ApiModelProperty(value="价格",name="price",required=true)
    String price;
    @ApiModelProperty(value="描述",name="description",required=true)
    String description;
    @ApiModelProperty(value="分类",name="category",required=true,example = "0较轻/1轻/2重/3较重/4特别重")
    String category;
    @ApiModelProperty(value="发布时间",name="date",required=true)
    String date;
    @ApiModelProperty(value="用户头像",name="sPhoto",required = true)
    String sPhoto;
    @ApiModelProperty(value="快递状态",name="status",required = true,example = "1已代领/0未代领/-1失效")
    String status;
    @ApiModelProperty(value="取件地址",name="qPos",required=true)
    String qPos;
    @ApiModelProperty(value="用于验证的二维码",name="ewm",required=true)
    String ewm;
    @ApiModelProperty(value="用户限制领取的截止的二维码",name="endDate",required=true)
    String endDate;
}
