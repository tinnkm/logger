package com.pxkj.logger.enums;

public enum OperateType {
    ADD("add"),UPDATE("update"),DELETE("delete"),SEARCH("search"),LOGIN("login"),LOGOUT("logout"), SYSTEM("system");
    private String value;
    OperateType(String value){
        this.value = value;
    }
}
