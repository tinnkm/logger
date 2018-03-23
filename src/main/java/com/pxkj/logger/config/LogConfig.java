package com.pxkj.logger.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.db.DataSourceConnectionSource;
import ch.qos.logback.core.db.DriverManagerConnectionSource;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import com.pxkj.logger.enums.LogAppender;
import com.pxkj.logger.enums.LogLevel;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.LocalDate;

/**
 * 自定义日志配置
 */
@Configuration
public class LogConfig {
    private final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init(){
        loggerContext.stop();
        loggerContext.reset();
    }

    public Appender getConsoleAppender(String logKey, PatternLayoutEncoder patternLayoutEncoder) {
        Appender appender = new ConsoleAppender<ILoggingEvent>();
        ConsoleAppender<ILoggingEvent> consoleAppender = (ConsoleAppender<ILoggingEvent>) appender;
        consoleAppender.setEncoder(patternLayoutEncoder);
        // 需要先启动格式化才能实现输出否则不起作用
        patternLayoutEncoder.start();
        consoleAppender.setContext(loggerContext);
        appender.setName(logKey+".console");
        return appender;
    }

    public Appender getDBAppender(String logKey) {
        Appender appender =new DBAppender();
        DBAppender dbAppender = (DBAppender) appender;
        DataSourceConnectionSource dataSourceConnectionSource = new DataSourceConnectionSource();
        dataSourceConnectionSource.setContext(loggerContext);
        dataSourceConnectionSource.setDataSource(dataSource);
        dataSourceConnectionSource.start();
        dbAppender.setConnectionSource(dataSourceConnectionSource);
        appender.setName(logKey+".db");
        appender.setContext(loggerContext);
        return appender;
    }

    public Appender getFileAppender(String logKey,String file, PatternLayoutEncoder patternLayoutEncoder) {
        Appender appender =new RollingFileAppender<ILoggingEvent>();
        RollingFileAppender<ILoggingEvent> fileAppender = (RollingFileAppender<ILoggingEvent>) appender;
        fileAppender.setContext(loggerContext);
        fileAppender.setFile(file);
        fileAppender.setEncoder(patternLayoutEncoder);
        // 需要先启动格式化才能实现输出否则不起作用
        patternLayoutEncoder.start();
        SizeAndTimeBasedRollingPolicy sizeAndTimeBasedRollingPolicy = new SizeAndTimeBasedRollingPolicy();
        sizeAndTimeBasedRollingPolicy.setMaxFileSize(FileSize.valueOf("30MB"));
        sizeAndTimeBasedRollingPolicy.setFileNamePattern("reduce.%d{yyyy-MM-dd}.%i.log.zip");
        sizeAndTimeBasedRollingPolicy.setContext(loggerContext);
        sizeAndTimeBasedRollingPolicy.setParent(fileAppender);
        sizeAndTimeBasedRollingPolicy.start();
        fileAppender.setRollingPolicy(sizeAndTimeBasedRollingPolicy);
        appender.setName(logKey+".file");
        return appender;
    }

    public LoggerContext getLoggerContext() {
        return loggerContext;
    }
}
