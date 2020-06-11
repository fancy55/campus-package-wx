package com.cpack.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("反馈")
public class ProviderFeedback {
    @ApiModelProperty("电话号码")
    private String phone;
    @ApiModelProperty("反馈内容")
    private String content;
}
