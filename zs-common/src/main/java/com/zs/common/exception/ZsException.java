package com.zs.common.exception;

import com.zs.common.core.HttpEnum;
import lombok.Data;

@Data
public class ZsException extends RuntimeException{

    private Integer code;
    private String msg;

    public ZsException(Integer code, String msg, Exception e){
        super(e);
        this.code = code;
        this.msg = msg;
    }

    public ZsException(String msg){
        this.msg = msg;
    }

    public ZsException(HttpEnum httpEnum, Exception e){
        super(e);
        this.code = httpEnum.getCode();
        this.msg = httpEnum.getMsg();
    }
}
