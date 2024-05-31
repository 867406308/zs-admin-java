package com.zs.common.core.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 86740
 */
@Data
public class Result<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;
    private long timestamp = System.currentTimeMillis();

    public Result() {

    }

    public Result(HttpEnum httpEnum) {
        this.code = httpEnum.getCode();
        this.msg = httpEnum.getMsg();
    }

    public Result(HttpEnum httpEnum, String msg) {
        this.code = httpEnum.getCode();
        this.msg = msg;
    }

    public Result(String msg) {
        this.code = HttpEnum.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
        this.data = null;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public Result(HttpEnum httpEnum, T data) {
        this.code = httpEnum.getCode();
        this.msg = httpEnum.getMsg();
        this.data = data;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result<T> ok() {
        return new Result<>(HttpEnum.OK);
    }

    public Result<T> ok(T data) {
        return new Result<>(HttpEnum.OK, data);
    }

    public Result<T> ok(HttpEnum httpEnum) {
        return new Result<>(httpEnum);
    }

    public Result<T> ok(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public Result<T> error() {
        return new Result<>(HttpEnum.INTERNAL_SERVER_ERROR);
    }

    public Result<T> error(T data) {
        return new Result<>(HttpEnum.INTERNAL_SERVER_ERROR, data);
    }

    public Result<T> error(String msg) {
        return new Result<>(msg);
    }

    public Result<T> error(HttpEnum httpEnum) {
        return new Result<>(httpEnum);
    }

    public Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    public Result<T> error(HttpEnum httpEnum, String msg) {
        return new Result<>(httpEnum, msg);
    }

    public Result<T> error(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }


}
