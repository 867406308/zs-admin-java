package com.zs.common.core.exception;


import com.zs.common.core.core.HttpEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ZsException extends RuntimeException {

    private Integer code;
    private String msg;


    public ZsException(Integer code, String msg, Exception e) {
        super(e);
        this.code = code;
        this.msg = msg;
    }

    public ZsException(String msg) {
        this.msg = msg;
    }


    public ZsException(@NotNull HttpEnum httpEnum, Exception e) {
        super(e);
        this.code = httpEnum.getCode();
        this.msg = httpEnum.getMsg();
    }

    public ZsException(HttpEnum httpEnum) {
        this.code = httpEnum.getCode();
        this.msg = httpEnum.getMsg();
    }
}
