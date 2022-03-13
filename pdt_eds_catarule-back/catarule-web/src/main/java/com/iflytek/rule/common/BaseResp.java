package com.iflytek.rule.common;

/**
 * 基本返回（与智审系统交互协议）
 *
 */
public class BaseResp {
	/**
	 * 成功
	 */
	public final static String SUCCESS = "10";
	/**
	 * 文件识别错误
	 */
	public final static String RECOGNIZE_ERROR = "100";
	/**
	 * 系统错误 由于系统运行异常导致的错误
	 */
	public final static String SYSTEM_ERROR = "200";
	/**
	 * token错误
	 */
	public final static String SECRET_TOKEN_ERROR = "300";
	/**
	 * 参数错误
	 */
	public final static String PARAM_ERROR = "400";
	/**
	 * 其它异常
	 */
	public final static String OTHER_ERROR = "900";

	/**
	 * 设置默认值，防止异常直接返回成功
	 */
	private String code = "900";
	private String info = "未设置返回信息";
	/**
	 * 预留（暂不用）
	 */
	private Object attach;
	
	public BaseResp() {
        super();
        this.code = "10";
        this.info = "操作成功！";
    }
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Object getAttach() {
		return attach;
	}
	public void setAttach(Object attach) {
		this.attach = attach;
	}
	
}
