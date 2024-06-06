package com.zs.common.core.exception;


import com.zs.common.core.core.HttpEnum;
import com.zs.common.core.core.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author 86740
 */
@RestControllerAdvice
public class ZsExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ZsExceptionHandler.class);


    /**
     * 自定义业务异常
     *
     * @return Result
     */
    @ExceptionHandler(ZsException.class)
    public Result<?> handleZsException(ZsException e) {
        logger.error(e.getMessage(), e);
        return new Result<>().error(e.getCode(), e.getMsg(), e.getMessage());
    }

    @ExceptionHandler(MyBatisSystemException.class)
    public Result<?> handleMyBatisException(MyBatisSystemException ex) {
        // 记录MyBatis-Plus的SQL异常信息到日志
        logger.error("MyBatis-Plus SQL Exception occurred", ex);
        logger.error(ex.getMessage(), ex);
        return new Result<>().error();
    }

    /**
     * 参数异常
     *
     * @param e a
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        logger.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();

        return new Result<>().error(HttpEnum.VALIDATE_ERROR, message);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        logger.error(e.getMessage(), e);
        String requestUri = request.getRequestURI();
        logger.error(requestUri);
        return new Result<>().error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return new Result<>().error();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        throw e;
    }

}