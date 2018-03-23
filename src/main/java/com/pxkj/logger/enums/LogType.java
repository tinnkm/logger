package com.pxkj.logger.enums;

public enum LogType {
    SERVICE("service"),SYSTEM("system");
    private String value;
    LogType(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
