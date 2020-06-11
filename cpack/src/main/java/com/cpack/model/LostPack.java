package com.cpack.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("丢失的快递")
public class LostPack implements Serializable {
    String orderId;
    String date;
    String phone;
    String name;
}
