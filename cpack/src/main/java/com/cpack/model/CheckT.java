package com.cpack.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ApiModel(value="checkT",description="用户审核所需填写资料")
public class CheckT {
    @ApiModelProperty(value="身份证",name="idCard",required=true)
    String idCard;
    @ApiModelProperty(value="身份证手持头像面",name="file1",required=true)
    String file1;
    @ApiModelProperty(value="身份证手持正面",name="file2",required=true)
    String file2;
    @ApiModelProperty(value="学生证盖章面",name="file3",required=true)
    String file3;
    @ApiModelProperty(value="学生证盖戳面",name="file4",required=true)
    String file4;
    @ApiModelProperty(value="最新一次申请审核的日期",name="date",required=true)
    String date;
    @ApiModelProperty(value="通过时间",name="pdate",required=true)
    String pdate;
    @ApiModelProperty(value="申请状态",name="status",required=true,example="1通过/0正在审核中/-1未通过")
    String status;
    @ApiModelProperty(value="申请次数",name="cnt",required=true)
    String cnt;
}
