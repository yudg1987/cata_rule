/*
 *
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved.
 *
 * FileName：GlobalExceptionHandler.java
 *
 * Description：
 *
 * History：
 * Version   Author      Date            Operation
 * 1.0	  lli   2017年8月2日下午5:28:59	       Create
 */
package com.iflytek.rule.common.handler;

import com.iflytek.rule.common.JsonResult;
import com.iflytek.rule.common.enums.BusinessMsgEnum;
import com.iflytek.rule.common.exception.BusinessErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局统一异常处理
 *
 * @author lli
 * @version 1.0
 */
@ControllerAdvice(basePackages = "com.iflytek.rule.controller")
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param ex
     * @return
     * @description 先拦截业务异常，返回业务异常信息
     * @author lli
     * @create 2017年8月2日下午5:36:48
     * @version 1.0
     */
    @ExceptionHandler(BusinessErrorException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public JsonResult handleBusinessError(BusinessErrorException ex) {
        logger.error("业务异常：{}", ex.getMessage());
        return new JsonResult(ex);
    }

    /**
     * 不合法的请求格式异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JsonResult handleTypeMismatchException(TypeMismatchException ex) {
        logger.error("不合法的请求格式", ex);
        return new JsonResult("400", "不合法的请求格式");
    }

    /**
     * HTTP参数不可读
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JsonResult handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        logger.error("请求参数不可读", ex);
        return new JsonResult("400", "请求参数不可读");
    }

    /**
     * 请求参数验证异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JsonResult handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();

        boolean first = false;
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            if (first) {
                sb.append(";");
            }
            sb.append(fieldError.getDefaultMessage());
            first = true;
        }
        logger.error("请求参数验证异常,{}", sb.toString());
        return new JsonResult("400", sb.toString());
    }

    /**
     * @param ex
     * @return
     * @description 系统异常 预期以外异常
     * @author lli
     * @create 2017年8月3日下午1:58:12
     * @version 1.0
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public JsonResult handleUnexpectedServer(Exception ex) {
        logger.error("系统异常：", ex);
        return new JsonResult(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
    }
}
