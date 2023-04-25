package com.zs.common.exception;

import com.zs.common.core.HttpEnum;
import com.zs.common.core.Result;
import com.zs.common.exception.ZsException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(1)
public class ZsExceptionHandler {


    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex){
        return new Result().error();
    }

    /**
     * 自定义业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(ZsException.class)
    public Result handleZsException(ZsException e){
        return new Result().error(e.getCode(), e.getMsg(), e.getMessage());
    }

    /**
     * 参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e)
    {
        String message = e.getAllErrors().get(0).getDefaultMessage();

        return new Result().error(HttpEnum.VALIDATE_ERROR, message);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        return new Result().error(e.getMessage());
    }




}
