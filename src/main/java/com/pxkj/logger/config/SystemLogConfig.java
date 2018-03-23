package com.pxkj.logger.config;

import com.pxkj.logger.builder.LogBuilder;
import com.pxkj.logger.enums.LogAppender;
import com.pxkj.logger.enums.LogLevel;
import com.pxkj.logger.model.LogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnProperty(value = "system.log.enable")
public class SystemLogConfig implements ApplicationRunner {

    @Autowired
    private LogProperties logProperties;

    public void getRootLogger(){
        List logAppenders = new ArrayList();
        Arrays.stream(logProperties.getLogAppender()).forEach(logAppender -> {
            logAppenders.add(LogAppender.valueOf(logAppender));
        });
        LogBuilder.getRootLogger( LogLevel.valueOf(logProperties.getLogLevel()), (LogAppender[]) logAppenders.toArray(new LogAppender[0]),logProperties.getPath()+logProperties.getLogName());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getRootLogger();
    }
}
