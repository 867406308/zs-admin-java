package com.zs.framework.aspect;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.zs.common.annotation.Log;
import com.zs.common.annotation.LoginLog;
import com.zs.common.core.Result;
import com.zs.common.model.params.SysLogErrorAddParams;
import com.zs.common.model.params.SysLogLoginAddParams;
import com.zs.common.model.params.SysLogOperationAddParams;
import com.zs.common.utils.IpUtils;
import com.zs.framework.security.utils.SecurityUtil;
import com.zs.framework.service.ILogErrorAspectService;
import com.zs.framework.service.ILogLoginAspectService;
import com.zs.framework.service.ILogOperationAspectService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86740
 */
@Aspect
@Component
public class LogAspect {

    long beginTime;
    @Resource
    private ILogLoginAspectService iLogLoginAspectService;
    @Resource
    private ILogOperationAspectService iLogOperationAspectService;
    @Resource
    private ILogErrorAspectService iLogErrorAspectService;

    /**
     * 登录日志切入点
     */
    @Pointcut("@annotation(com.zs.common.annotation.LoginLog)")
    public void loginPointCut() {
    }

    /** 操作日志切入点 */
    @Pointcut("@annotation(com.zs.common.annotation.Log)")
    public void logOperationPointCut() {
    }

    @Before("logOperationPointCut()")
    public void before(){
        beginTime = System.currentTimeMillis();
    }

    /**
     * 记录操作日志
     */
    @AfterReturning(value = "logOperationPointCut()", returning = "obj")
    public void afterReturning(JoinPoint point, Object obj) {
        saveLogOperation(point, obj);
    }



    /**
     * 记录异常日志
     * 异常通知，可传递异常对象
     * 目标方法抛出异常后
     * 常用场景：处理异常、回滚事务
     */
    @AfterThrowing(value = "logOperationPointCut()", throwing = "e")
    public void afterThrowing(JoinPoint point, Exception e) {
        saveLogError(point,e);
    }


    /**
     * 记录登录日志
     * 环绕通知
     * 用于目标方法的整个调用流程
     *
     */
    @Around("loginPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            Object object = proceedingJoinPoint.proceed();
            saveLoginLog(proceedingJoinPoint);
            return object;
    }


    /** 获取HttpServletRequest */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /** 获取请求体参数 */
    private String getRequestBody(JoinPoint point) {
        Object[] args = point.getArgs();
        return JSONUtil.toJsonStr(args[0]);
    }
    void saveLoginLog(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] objects = proceedingJoinPoint.getArgs();
        Throwable failureReason = null;
        if (objects[2] instanceof BadCredentialsException) {
            failureReason = (BadCredentialsException) objects[2];
        }

        HttpServletRequest request = getRequest();
        String username = (String) request.getAttribute("username");
        String ipAddr = IpUtils.getIpAddr(request);
        String userAgentString = request.getHeader(HttpHeaders.USER_AGENT);

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LoginLog annotation = method.getAnnotation(LoginLog.class);

        Map<String, Object> cityInfo = IpUtils.getCityInfo(ipAddr);
        String city = Objects.requireNonNull(cityInfo).get("province").toString();
        UserAgent userAgent = UserAgentUtil.parse(userAgentString);
        String browser = userAgent.getBrowser().toString();
        String os = userAgent.getOs().toString();

        SysLogLoginAddParams sysLogLoginAddParams = createLogLoginParams(username, ipAddr, city, userAgentString, annotation, failureReason, browser, os);

        iLogLoginAspectService.save(sysLogLoginAddParams);
    }


    /** 操作日志 */
    public void saveLogOperation(JoinPoint point, Object obj) {
        HttpServletRequest request = getRequest();
        String params = getRequestBody(point);

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Log annotation = method.getAnnotation(Log.class);
        Result result = (Result) obj;

        SysLogOperationAddParams addParams = createLogOperationParams(annotation, request, params, result);

        iLogOperationAspectService.save(addParams);
    }

    /** 错误日志 */
    public void saveLogError(JoinPoint point, Exception e){
        HttpServletRequest request = getRequest();
        String params = getRequestBody(point);

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Log annotation = method.getAnnotation(Log.class);

        SysLogErrorAddParams sysLogErrorAddParams = createLogErrorParams(annotation, request, params, e);

        iLogErrorAspectService.save(sysLogErrorAddParams);
    }

    /** 创建操作日志参数 */
    private SysLogOperationAddParams createLogOperationParams(Log annotation, HttpServletRequest request, String params, Result result) {
        SysLogOperationAddParams addParams = new SysLogOperationAddParams();
        addParams.setUsername(SecurityUtil.getUserInfo().getSysUser().getUsername());
        addParams.setModule(annotation.module());
        addParams.setIpAddress(IpUtils.getIpAddr(request));
        addParams.setOperationType(annotation.type().toString());
        addParams.setOperationDescription(annotation.description());
        addParams.setRequestMethod(request.getMethod());
        addParams.setRequestPath(request.getRequestURI());
        addParams.setRequestParams(params);
        addParams.setResponseStatusCode(result.getCode());
        addParams.setResponseMessage(result.getMsg());
        addParams.setOperationDuration((int) (System.currentTimeMillis() - beginTime));
        addParams.setCreateTime(DateUtil.now());
        return addParams;
    }

    /** 创建异常日志参数 */
    private SysLogErrorAddParams createLogErrorParams(Log annotation, HttpServletRequest request, String params, Exception e) {
        SysLogErrorAddParams addParams = new SysLogErrorAddParams();
        addParams.setUsername(SecurityUtil.getUserInfo().getSysUser().getUsername());
        addParams.setModule(annotation.module());
        addParams.setIpAddress(IpUtils.getIpAddr(request));
        addParams.setExceptionType(e.getClass().getName());
        addParams.setExceptionMessage(e.getCause().getMessage());
        addParams.setRequestMethod(request.getMethod());
        addParams.setRequestPath(request.getRequestURI());
        addParams.setRequestParams(params);
        addParams.setCreateTime(DateUtil.now());
        return addParams;
    }


    /** 创建登录日志参数 */
    private SysLogLoginAddParams createLogLoginParams(String username, String ipAddr, String city, String userAgentString, LoginLog annotation, Throwable failureReason, String browser, String os) {
        SysLogLoginAddParams sysLogLoginAddParams = new SysLogLoginAddParams();
        sysLogLoginAddParams.setUsername(username);
        sysLogLoginAddParams.setLoginTime(DateUtil.now());
        sysLogLoginAddParams.setIpAddress(ipAddr);
        sysLogLoginAddParams.setCity(city);
        sysLogLoginAddParams.setUserAgent(userAgentString);
        sysLogLoginAddParams.setLoginStatus(annotation.value());
        sysLogLoginAddParams.setFailureReason(failureReason != null ? failureReason.getMessage() : null);
        sysLogLoginAddParams.setLoginMethod(1);
        sysLogLoginAddParams.setLoginSource(null);
        sysLogLoginAddParams.setBrowser(browser);
        sysLogLoginAddParams.setOs(os);
        return sysLogLoginAddParams;
    }


}
