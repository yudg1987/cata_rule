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

/**
 * 成功返回json
 * 
 * @author lli
 *
 * @version 1.0
 * 
 */
public class SuccessJsonResult<T> extends JsonResult {
	
    public SuccessJsonResult() {
        super();
        this.code = "0";
        this.msg = "操作成功！";
    }

    public SuccessJsonResult(T data) {
        super();
        this.data = data;
        this.code = "0";
        this.msg = "操作成功！";
    }
    
    public SuccessJsonResult(T data, String msg) {
        super();
        this.data = data;
        this.code = "0";
        this.msg = msg;
    }

    private T data;

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    public void  setBusinessMsg(BusinessMsgEnum businessMsg){
        this.code = businessMsg.code();
        this.msg = businessMsg.msg();
    }
    
//    //江西返回结果
//    public void  setJXBusinessMsg(QueryClassifyResp resp){
//        this.code = resp.getCode();
//        this.msg = resp.getInfo();
//        this.data = (T) resp.getFileClassifyList();
//    }

    private int total;
    private int page;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private int pageSize;

}
