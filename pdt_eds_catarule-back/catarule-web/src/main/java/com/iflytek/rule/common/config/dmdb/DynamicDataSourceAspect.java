package com.iflytek.rule.common.config.dmdb;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * 自定义注解 + AOP的方式实现数据源动态切换。
 * Created by pure on 2018-05-06.
 */
@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {
    /**
     * 配置切点
     */
    @Pointcut("@annotation(com.iflytek.rule.common.config.dmdb.Ds)")
    public void ds() {
    }

    /**
     *用@Pointcut来注解一个切入方法
     */
    @Pointcut("execution(* com.iflytek.rule.mapper.*.*(..))")
    public void excude() {
    }


    @Before("excude()")
    public void beforeSwitchDS(JoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        try {
            if (targetMethod.isAnnotationPresent(Ds.class)) {
                Ds annotation = targetMethod.getAnnotation(Ds.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 切换数据源
        DataSourceContextHolder.setDataSourceLookupKey(dataSource);
    }

    @After("excude()")
    public void afterSwitchDS(JoinPoint point) {
        DataSourceContextHolder.clearDataSourceLookupKey();
    }
}
