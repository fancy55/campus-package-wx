package com.cpack.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ApiModel(value="Report",description="举报")
public class Report {
    @ApiModelProperty(value="举报人身份证",name="reportIdCard",required=true)
    String reportIdCard;
    @ApiModelProperty(value="被举报人身份证",name="publishIdCard",required=true)
    String publishIdCard;
    @ApiModelProperty(value="举报订单",name="orderId",required=true)
    String orderId;
    @ApiModelProperty(value="举报内容",name="content",required=true)
    String content;
    @ApiModelProperty(value="受理状态",name="status",required=true,example = "1受理/-1未受理")
    String status;
    @ApiModelProperty(value="举报时间",name="date",required=true)
    String date;
}
