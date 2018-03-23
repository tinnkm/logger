package com.pxkj.logger.aspect;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pxkj.logger.builder.LogBuilder;
import com.pxkj.logger.enums.LogType;
import com.pxkj.logger.enums.OperateType;
import com.pxkj.logger.exception.ControllerException;
import com.pxkj.logger.model.LogModel;
import org.aspectj.apache.bcel.classfile.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.ThrowsAdvice;
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
public class ExceptionAspect{

    @Pointcut("execution(* com.pxkj..*(..))")
    private void exception(){}
    @AfterThrowing(pointcut = "exception()")
    public void controllerException(JoinPoint joinPoint) throws JsonProcessingException {
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
        LogModel logModel = new LogModel();
        logModel.setClassName(typeName);
        logModel.setMethod(name);
        logModel.setSessionId(sessionId);
        logModel.setRequestParameterMap(parameterMap);
        logModel.setSessionParameterMap(map);
        logModel.setOperateType(OperateType.SYSTEM);
        logModel.setParams(args);
        logModel.setLogType(LogType.SYSTEM);
        Logger rootLogger = LogBuilder.getRootLogger();
        rootLogger.error(logModel.toString());
    }
}
