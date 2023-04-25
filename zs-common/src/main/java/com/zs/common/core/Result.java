package com.zs.common.core;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private Integer code;
    private String msg;
    private Object data;

    public Result(){

    }

    public Result(HttpEnum httpEnum){
        this.code = httpEnum.getCode();
        this.msg = httpEnum.getMsg();
    }

    public Result(Integer code, String msg){
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public Result(HttpEnum httpEnum, Object data){
        this.code = httpEnum.getCode();
        this.msg = httpEnum.getMsg();
        this.data = data;
    }

    public Result(Integer code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result ok(){
        return new Result(HttpEnum.OK);
    }

    public Result ok(Object data){
        return new Result(HttpEnum.OK,  data);
    }

    public Result ok(HttpEnum httpEnum){
        return new Result(httpEnum);
    }

    public Result ok(Integer code, String msg, Object data){
        return new Result(code, msg, data);
    }

    public Result error(){
        return new Result(HttpEnum.INTERNAL_SERVER_ERROR);
    }

    public Result error(Object data){
        return new Result(HttpEnum.INTERNAL_SERVER_ERROR,  data);
    }

    public Result error(HttpEnum httpEnum){
        return new Result(httpEnum);
    }

    public Result error(Integer code, String msg){
        return new Result(code, msg);
    }

    public Result error(HttpEnum httpEnum, String msg){
        return new Result(httpEnum, msg);
    }
    public Result error(Integer code, String msg, Object data){
        return new Result(code, msg, data);
    }


}
