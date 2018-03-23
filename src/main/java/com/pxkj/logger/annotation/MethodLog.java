package com.pxkj.logger.annotation;

import com.pxkj.logger.enums.LogAppender;
import com.pxkj.logger.enums.LogLevel;
import com.pxkj.logger.enums.LogType;
import com.pxkj.logger.enums.OperateType;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
    OperateType operateType() default OperateType.SEARCH; // 操作类型：Add/Update/Delete/Search/Login等
    LogLevel logLevel() default LogLevel.INFO;
    LogType logType() default LogType.SERVICE;
    LogAppender[] logAppender() default LogAppender.CONSOLE;
}
