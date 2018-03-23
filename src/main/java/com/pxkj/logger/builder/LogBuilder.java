package com.pxkj.logger.builder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.Appender;
import com.pxkj.logger.config.LogConfig;
import com.pxkj.logger.enums.LogAppender;
import com.pxkj.logger.enums.LogLevel;
import com.pxkj.logger.model.LogModel;
import com.pxkj.logger.util.SpringContextUtil;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class LogBuilder {
    private LogConfig logConfig;
    private String file = "loginfo.log";
    private static String LOGKEY;
    public final static String LOGFORMATTER = "%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level --- [%thread] %logger{50} : %msg%n";

    private Logger logger;

    public LogBuilder(){
        logConfig = SpringContextUtil.getBean("logConfig");
        logger = logConfig.getLoggerContext().getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
    }
    public LogBuilder(String className,String method){
        logConfig = SpringContextUtil.getBean("logConfig");
        LOGKEY = getLogKey(className, method);
        logger = logConfig.getLoggerContext().getLogger(LOGKEY);
    }
    private LogBuilder setFlie(String file){
        this.file = file;
        return this;
    }
    public static Logger getLogger(String className,String method){
        return new LogBuilder(className,method).start();
    }
    public static Logger getLogger(String className,String method,LogLevel logLevel){
        return new LogBuilder(className,method).setLogLevel(logLevel).start();
    }
    public static Logger getLogger(String className,String method,LogAppender[] logAppenders,LogLevel logLevel,String flie){
        return new LogBuilder(className,method).setLogLevel(logLevel).setFlie(flie).addAppenders(LOGKEY,logAppenders).start();
    }

    public static Logger getRootLogger(LogLevel logLevel,LogAppender[] logAppender){
        return new LogBuilder().setLogLevel(logLevel).addAppenders(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME,logAppender).start();
    }
    public static Logger getRootLogger(LogLevel logLevel,LogAppender[] logAppender,String file){
        return new LogBuilder().setLogLevel(logLevel).setFlie(file).addAppenders(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME,logAppender).start();
    }
    public static Logger getRootLogger(){
        return new LogBuilder().getRoot();
    }
    private Logger start(){
        logConfig.getLoggerContext().start();
        return this.logger;
    }
    private Logger getRoot(){
        return this.logConfig.getLoggerContext().getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
    }

    private LogBuilder addAppenders(String logKey,LogAppender[] logAppenders){
        Arrays.stream(logAppenders).forEach(logAppender -> {
            addAppender(logKey,logAppender);
        });
        return this;
    }
    /**
     * 动态设置某个类的日志级别
     */
    public LogBuilder setLogLevel(LogLevel logLevel) {
        if (null != logger && null != logLevel) {
            if (!logLevel.equals(logger.getLevel())){
                logger.setLevel(Level.toLevel(logLevel.toString()));
            }
        }
        return this;
    }
      /**
     * 设置appender
     */
      private LogBuilder addAppender(String logKey,LogAppender logAppender) {
        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
        patternLayoutEncoder.setPattern(LOGFORMATTER);
        patternLayoutEncoder.setContext(logConfig.getLoggerContext());
        Appender appender = null;
        switch (logAppender) {
            case FILE: {

                appender = logConfig.getFileAppender(logKey, file,patternLayoutEncoder);
                break;
            }
            case DB: {
                appender = logConfig.getDBAppender(logKey);
                break;
            }
            case CONSOLE:
                appender = logConfig.getConsoleAppender(logKey, patternLayoutEncoder);
                break;
        }
        appender.start();
        logger.addAppender(appender);
        return this;
    }
    private String getLogKey(String className,String method) {
        if (StringUtils.isEmpty(className) && StringUtils.isEmpty(method)) {
            return ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME;
        } else if (StringUtils.isEmpty(method)) {
            return className;
        } else {
            return className + "." + method;
        }
    }
}
