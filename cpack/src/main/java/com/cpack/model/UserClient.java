package com.cpack.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Validated
@ApiModel(value="UserClient",description="用户个人信息")
public class UserClient extends UserBase implements Serializable {
    @ApiModelProperty(value="学院",name="comName",required=true)
    String college;
    @ApiModelProperty(value="年级",name="comName",required=true)
    String grade;
    @ApiModelProperty(value="专业",name="comName",required=true)
    String major;
}
