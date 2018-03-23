package com.pxkj.logger.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxkj.logger.enums.LogAppender;
import com.pxkj.logger.enums.LogLevel;
import com.pxkj.logger.enums.LogType;
import com.pxkj.logger.enums.OperateType;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

@Component
public class LogModel implements Serializable {
    private static final long serialVersionUID = 1647849519022722941L;

    private String className;
    private String method;
    private String sessionId;
    private String requestParameterMap;
    private String sessionParameterMap;
    private OperateType operateType;
    private Long startTime;
    private Long endTime;
    private Long interval;
    private String params;
    private LogType logType = LogType.SYSTEM;
    //private LogLevel logLevel = LogLevel.INFO;
    //@Transient
    private ObjectMapper objectMapper = new ObjectMapper();
    //public LogAppender[] logAppender = {LogAppender.CONSOLE};

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRequestParameterMap() {
        return requestParameterMap;
    }

    public void setRequestParameterMap(Map<String, String[]> requestParameterMap) throws JsonProcessingException {
        String writeValueAsString = objectMapper.writeValueAsString(requestParameterMap);
        this.requestParameterMap = writeValueAsString;
    }

    public String getSessionParameterMap() {
        return sessionParameterMap;
    }

    public void setSessionParameterMap(Map<String, Object> sessionParameterMap) throws JsonProcessingException {
        this.sessionParameterMap = objectMapper.writeValueAsString(sessionParameterMap);
    }



    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getInterval() {
        return this.endTime-this.startTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(Object[] params) throws JsonProcessingException {
        this.params = objectMapper.writeValueAsString(params);
    }

    public void setRequestParameterMap(String requestParameterMap) {
        this.requestParameterMap = requestParameterMap;
    }

    public void setSessionParameterMap(String sessionParameterMap) {
        this.sessionParameterMap = sessionParameterMap;
    }

    public OperateType getOperateType() {
        return operateType;
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }


    public void setParams(String params) {
        this.params = params;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

//    public LogAppender[] getLogAppender() {
//        return logAppender;
//    }
//
//    public void setLogAppender(LogAppender[] logAppender) {
//        this.logAppender = logAppender;
//    }
//
//    public LogLevel getLogLevel() {
//        return logLevel;
//    }
//
//    public void setLogLevel(LogLevel logLevel) {
//        this.logLevel = logLevel;
//    }

    @Override
    public String toString() {
        return "LogModel{" +
                "className='" + className + '\'' +
                ", method='" + method + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", requestParameterMap='" + requestParameterMap + '\'' +
                ", sessionParameterMap='" + sessionParameterMap + '\'' +
                ", operateType=" + operateType +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", interval=" + interval +
                ", params='" + params + '\'' +
                ", logType=" + logType +
                '}';
    }
}
