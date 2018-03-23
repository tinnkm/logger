package com.pxkj.logger.aspect;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pxkj.logger.annotation.MethodLog;
import com.pxkj.logger.builder.LogBuilder;
import com.pxkj.logger.config.LogConfig;
import com.pxkj.logger.enums.LogAppender;
import com.pxkj.logger.enums.LogLevel;
import com.pxkj.logger.model.LogModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;


@Component
@Aspect
public class MethodLogAspect {
    @Autowired
    private LogModel logModel;

    private LogLevel logLevel;
    private LogAppender[] logAppenders;
    @Pointcut("@annotation(com.pxkj.logger.annotation.MethodLog)")
    public void methodPointCut(){}

    @Before("methodPointCut()&&@annotation(methodLog)")
    public void before(JoinPoint joinPoint,MethodLog methodLog) throws JsonProcessingException {
        long begin = System.currentTimeMillis();
        // 参数列表
        Object[] args = joinPoint.getArgs();
        // 获取签名
        Signature signature = joinPoint.getSignature();
        // 获取调用的方法名
        String name = signature.getName();
        // 获取类
        String typeName = signature.getDeclaringTypeName();
        // 获取request的钩子
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        // 获取request
        HttpServletRequest request = (HttpServletRequest)requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        // 获取session
        HttpSession httpSession = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        // 参数列表
        Map<String, String[]> parameterMap = request.getParameterMap();
        // sessionId
        String sessionId = httpSession.getId();
        Map map = Collections.EMPTY_MAP;
        Enumeration<String> attributeNames = httpSession.getAttributeNames();
        if (attributeNames.hasMoreElements()) {
            String element = attributeNames.nextElement();
            map.put(element,httpSession.getAttribute(element));
        }
        logModel.setClassName(typeName);
        logModel.setMethod(name);
        logModel.setSessionId(sessionId);
        logModel.setRequestParameterMap(parameterMap);
        logModel.setSessionParameterMap(map);
        logModel.setStartTime(begin);
        logModel.setOperateType(methodLog.operateType());
        logModel.setParams(args);
        logModel.setLogType(methodLog.logType());
//        logModel.setInterval(logModel.getEndTime() - logModel.getStartTime());
        logAppenders = methodLog.logAppender();
        logLevel = methodLog.logLevel();
    }
    @After("methodPointCut()")
    public void after(){
        logModel.setEndTime(System.currentTimeMillis());
        Logger logger = LogBuilder.getLogger(logModel.getClassName(),logModel.getMethod(),logAppenders,logLevel,"service.log");
        log(logger,logLevel);
    }

    private void log(Logger logger,LogLevel logLevel){
        switch (logLevel){
            case INFO:
                logger.info(logModel.toString());
                break;
            case WARN:
                logger.warn(logModel.toString());
                break;
            case DEBUG:
                logger.debug(logModel.toString());
                break;
            case ERROR:
                logger.error(logModel.toString());
                break;
            case TRACE:
                logger.trace(logModel.toString());
                break;
        }
    }
}
