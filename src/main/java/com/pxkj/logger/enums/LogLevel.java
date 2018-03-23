package com.pxkj.logger.enums;

public enum LogLevel {
    OFF(Integer.MAX_VALUE),ERROR(40000),WARN(30000),INFO(20000),DEBUG(10000),TRACE(5000),ALL(Integer.MIN_VALUE);
    private int levelInt;
    LogLevel(int levelInt){
        this.levelInt = levelInt;
    }
}
