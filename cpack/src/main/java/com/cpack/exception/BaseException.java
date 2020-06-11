package com.cpack.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private Integer code;
    private String msg;


    public BaseException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
