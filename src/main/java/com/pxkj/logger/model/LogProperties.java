package com.pxkj.logger.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("system.log")
public class LogProperties {
    private boolean enable;
    private String logLevel;
    private String[] LogAppender;
    private String path;
    private String logName;

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String[] getLogAppender() {
        return LogAppender;
    }

    public void setLogAppender(String[] logAppender) {
        LogAppender = logAppender;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
