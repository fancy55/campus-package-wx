package com.cpack.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("反馈")
public class ClientFeedback implements Serializable {
    @ApiModelProperty("电话号码")
    private String phone;
    @ApiModelProperty("反馈内容")
    private String content;
}
