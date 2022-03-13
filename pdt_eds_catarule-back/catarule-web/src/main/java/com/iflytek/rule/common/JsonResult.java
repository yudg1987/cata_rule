/* 
 *
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：ErrorJsonResult.java
 * 
 * Description：
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0	  lli   2017年8月2日下午5:35:21	       Create	
 */
package com.iflytek.rule.common;


import com.iflytek.rule.common.enums.BusinessMsgEnum;
import com.iflytek.rule.common.exception.BusinessErrorException;

/**
 * 返回json基类
 * 
 * @author lli
 *
 * @version 1.0
 * 
 */
public class JsonResult {

    /**
     * 异常码
     */
    protected String code;

    /**
     * 异常信息
     */
    protected String msg;

    protected JsonResult() {

    }

    public JsonResult(BusinessErrorException ex) {
        this.code = ex.getCode();
        this.msg = ex.getMessage();
    }

    public JsonResult(BusinessMsgEnum msg) {
        this.code = msg.code();
        this.msg = msg.msg();
    }
    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
