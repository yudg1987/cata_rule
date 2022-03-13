package com.iflytek.rule.common.config.dmdb;


import java.lang.annotation.*;

/**
 * 自定义注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Ds {
    String value() default "db1";
}

