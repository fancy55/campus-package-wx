package com.cpack.model;

import com.cpack.model.UserBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Validated
@ApiModel(value="UserProvider",description="站点员工个人信息")
public class UserProvider extends UserBase implements Serializable{
    @ApiModelProperty(value="公司名字",name="comName",required=true,example="菜鸟/妈妈/韵达/申通/...")
    String comName;
}
