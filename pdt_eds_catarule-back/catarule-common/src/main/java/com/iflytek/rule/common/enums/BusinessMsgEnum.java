/* 
 *
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：MsgEnum.java
 * 
 * Description：
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0	  lli   2017年8月3日下午2:02:18	       Create	
 */
package com.iflytek.rule.common.enums;

/**
 * 业务异常提示信息枚举类
 * <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * @autho dgyu
 * @time 2021年11月1日 下午3:13:46
 */
public enum BusinessMsgEnum {
    /**  重复提交*/
    REPEATED_SUBMIT("101", "重复提交"),
    /**  失败任务重试  定时任务重复*/
    SCHEDULE_REPEATED_SUBMIT("114", "定时任务重复执行"),
    /** 参数为空异常 */
    PARMETER_NULL_EXCEPTION("102", "参数为空！"),
    /** 文件重名异常 */
    FILE_REPEAT_EXCEPTION("103", "名称已存在！"),
    /** 密码错误异常 */
    PWD_ERROR("104", "密码错误！"),
    USER_ERROR("105", "用户名错误！"),
    /** 正在进行图文识别 */
    OCR_PROCESSING("106", "正在进行图文识别"),
    /** 不存在未识别的图文 */
    NOT_EXIST_CONT("107", "尚无待编目的图片"),
    /** 编目结果为空 */
    NOT_CONT_RES("1071", "编目结果为空"),
    /** 存在未识别的图文 */
    EXIST_NOT_OCR_FILE("1072", "存在未识别的图文"),
    ROTATE_FAIL("106", "图片旋转失败！"),
    FOLDER_NOT_EXIST("107", "文件夹不存在！"),
    FILE_NOT_EXIST("108", "文件不存在！"),
    EDIT_DEFAULT_FAULT_ERROR("109", "未命名文件夹不可编辑！"),
    DOSSIER_NOT_EXIST("110", "收案材料不存在！"),
    ONLINE_FILE_CLOSE_IN("111", "当前案件网上材料已审核过！"),
    DOSSIER_NO_REPEAT("112", "收案号重复！"),
    PIC_EXCEPTION("113","图片下载为null"),
    /** 超时错误 */
    TIME_OUT_ERROR("499", "处理发生超时！"),
    /** 文件上传存储超时 */
    TIME_OUT_FDFS_ERROR("499", "文件上传存储超时！"),
    /** 文件上传存储超时 */
    TIME_OUT_OCR_ERROR("499", "图像识别超时！"),
    /** 500 : 发生异常 */
    UNEXPECTED_EXCEPTION("500", "系统发生异常，请联系管理员！"),
    /**任务未编目*/
    UNCATALOG_EXCEPTION("114","任务未编目！"),
    OCR_FAILED("300", "OCR识别失败"),
    ELLE_FAILED("399", "ELLE抽取失败"),

    ALL_PAGE("115", "图片尚未完成编目，请点击自动编目"),
    PARTION_PAGE("116", "文件归目成功，图片尚未完成编目，请点击自动编目"),
    NO_PAGE_AND_FOLDER("117", "尚无待归目的文件"),
    EXIST_NO_CATALOG_FOLDER("118", "存在未归目的文件"),
    EXIST_FAIL_CATALOG_FOLDER("119", "存在归目失败的文件"),
    ONEKEYTOTREE_COMPLETED("0","文件归目完成"),
    FTP_PATH_NOFIND("201","FTP文件路径不存在"),
    DOSSIERNO_EXIT("301","当前材料号已存在"),
    SUPPORT_ONE_PDF("302","一次只能上传一份PDF卷宗！"),
    NOT_FINISH_CONT("303", "编目尚未完成"),
    FDFS_DELETE_WRONG("304", "fdfs删除文件失败"),
    NOFILE_DELETE("305", "该时间范围fdfs无文件可清除"),
    NO_OCR_FAILED("306", "不存在OCR识别失败的文件！"),
    FILE_FORMAT_ERROER("307", "请上传正确格式的文件！"),
    NOFILE_IN_DOSSIER_ERROER("308", "该任务下没有文件"),
    HOTWORD_SHOULDNOT_BENULL("401","新增热词不能为空！"),
    HOTWORD_ALEADLY_EXISTS("402","新增自定义热词已经存在！"),
    HOTWORD_NOT_EXISTS("403","自定义热词不存在！"),
    HOTWORD_REACH_MAXSIZE("405","您当前已添加50个热词，达到热词上限，请删除后再试！"),
    ARGUE_EXCEL_EMPTY("600","导出报表数据为空"),
    FOLDER_EXIST("700","文件夹已存在"),
    DEFAULT_FOLDER("701","默认目录不允许修改和删除"),
    FILE_EXIST("702","当前目录存在文件夹"),
    DEFAULT_FOLDER_ERROR("801","默认目录，可重命名，不能删除！"),
    FOLDER_NOT_EMPTY("703","存在非空目录"),
    NO_PIC_EXCEPTION("802","下载内容为空"),
    DOSSIER_ID_NULL("700","案件不存在"),
    DOSSIER_NAME_NULL("701","材料号不能为空"),
    NO_TARGET_SELECTED("901","未命中目标"),
    WORD_TRANSFER_ERROR("333","doc文件转换异常！！！"),
    FILE_EXPORT_ERROR("533","文件导出异常！！！"),
    FDFS_UPLOAD_FAIL("10001","FDFS上传文件失败！"),
    BASE64_TRANS_FAIL("10004","base64文件转换失败！"),
    FDFS_DOWNLOAD_FAIL("10003","FDFS下载文件失败！"),
    FDFS_TRACKER_SERVER_ISNULL("10002","tracker server is null ！"),

	// 接口限流提示
	SERVER_BUSY("503","系统繁忙，稍后重试！"),
    CATALOG_NOT_INIT("534","目录配置有误！！！"),
    // 材料号不存在
    DOSSIERNO_NOT_EXIST("334","材料号不存在！"),
    // 此材料正在处理中
    DOSSIERNO_IN_HANDING("335","此材料正在处理中！"),
    // 此材料创建失败
    DOSSIER_CREATE_ERROR("336","材料创建失败！"),
    DOSSIER_FILELIST_EMPTY("337","请求数据文件列表为空！"),
    EXPORT_CATALOG_EMPTY("600","目录数据为空"),
    NO_SEPARATE_DOSSIER("601","暂无可分册的卷宗!"),

    NO_FILE_CONVERTER_ERROR("602","暂无文件转换失败!"),

    DOSSIER_NO_NULL_ERROR("1101","收案号不能为空!"),
    CASE_TYPE_NULL_ERROR("1102","案件类型不能为空!"),
    CATALOG_TYPE_NULL_ERROR("1103","归目方式不能为空!"),
    DOSSIER_NO_NOT_EXSIT("1104","收案号不存在!"),
    CASE_TYPE_NOT_EXSIT("1105","案件类型不存在!"),
    CASE_TYPE_SAME_ERROR("1106","转换的案件类型与原案件类型一样!"),
    CATALOG_TYPE_NOT_EXSIT("1107","归目方式不存在!"),
    CLEAN_SEPARATE_AND_CATALOG_ERROR("1108","清除分卷和归目关系失败!"),
    DOSSIER_SEPARATE_INSERT_ERROR("1109","案件分册创建失败!"),
    DOSSIER_SEPARATE_DELETE_ERROR("1110","案件分册删除失败!"),
    FOLDER_OPT_INSERT_ERROR("1111","案件目录创建失败!"),
    FOLDER_OPT_DELETE_ERROR("1112","案件目录删除失败!"),
    CASE_TYPE_CHANGE_ERROR("1113","案件类型转换失败!"),
    RULES_EXIST("300","该条规则已存在！"),
    TYPE_MATCH_ERROR("301","拖拽类型不匹配！"),
    //前端做了全局拦截，注意不要跟该code有重复
    EXIT_NOT_EDIT_FILE("9999","存在不可编辑的文件!"),
    FILE_TYPE_NULL_ERROR("1201","文件类型不能为空!"),
    FILE_TYPE_NOT_EXSIT("1202","文件类型不存在!"),
    FILE_ID_NULL_ERROR("1203","文件Id不能为空!"),
    FOLDER_ID_NULL_ERROR("1204","文件夹Id不能为空!"),
    NO_SEPARATE_CATALOG("1205", "未找到配置对应的主分卷目录"),
    SAVE_ERROR("1206", "保存失败"),
    FAIL_GET_COVER("1210", "接口请求获取封面信息失败"),
    FAIL_SAVE_COVER("1207", "接口保存封面信息失败"),
    FAIL_CREATE_DOC("1208", "制作封面word失败"),
    FAIL_EXPORT_COVER("1209", "导出封面失败"),
    FAIL_CHANGE_MODEL("1211", "模式转换失败"),
    SAME_MODEL_CHANGE("1212", "不支持同种模式转换！"),
    MASTER_DOSSIERNO_NULL_ERROR("1213", "主案id不能为空！"),
    MASTER_DOSSIERNO_NULL_ERROR2("1213", "主案不存在！"),
    SUB_DOSSIERNO_NULL_ERROR("1214", "子案id不能为空！"),
    SPLIT_DOSSIERNO_NULL_ERROR("1214", "分案id不能为空！"),
    SPLIT_DOSSIERNO_NULL_ERROR2("1214", "分案不存在！"),
    GET_USER_INFO_FAIL("1215", "获取用户信息失败！"),
    AIO_EVI_SORT_IS_NULL("1216", "一体机数据证据顺序不能为空！"),
    AIO_EVI_NAME_IS_NULL("1217", "一体机数据证据名称不能为空！"),
    AIO_IMG_SORT_IS_NULL("1218", "一体机数据图片顺序不能为空！"),
    AIO_IMG_PATH_IS_NULL("1219", "图片路径不能为空！"),
    AIO_IMG_PID_IS_NULL("1220", "一体机数据图片PID不能为空！"),
    AIO_IMG_NAME_IS_NULL("1221", "一体机数据图片名称不能为空！"),
    ID_LIST_IS_NULL("1222", "参数ID列表为空！"),
    EVI_ID_IS_NULL("1223", "证据ID不能为空！"),
    ATT_LIST_IS_NULL("1224", "图片列表不能为空！"),
    CALL_BACK_IS_NULL("1225", "回调地址不能为空！"),
    ATT_ID_IS_NULL("1226", "图片ID不能为空！"),
    SUB_CASE_LIST_IS_EMPTY("1227", "子案列表为空！"),
    AUTO_CALL_BACK_NULL("1228", "是否自动回调标识不能为空！"),
    IS_ADD_TYPE_ERROR("1229", "isAdd处理类型标识错误！"),
    USER_ID_IS_NULL("1230", "用户id不能为空！"),
    FILE_PATH_IS_NULL("1231", "文件路径不能为空！"),
    IS_ADD_IS_NULL("1232", "是否覆盖不能为空！"),
    FILE_ID_IS_NULL("1233", "文件id不能为空！"),
    FILE_TYPE_IS_NULL("1234", "文件类型不能为空！"),

    PAGE_SET_ERROR("309","设置页码参数错误！"),

    REQUEST_BODY_IS_BLANK("2000", "请求体为空"),
    DOSSIER_HANDLE_TIME_IS_FUTURE("2010", "案件处理日期不能早于当前日期"),
    
    EVIDENCE_THAN_TWOHUNDRED("2011", "证据名称不能大于200个！"),
    CATALOG_RULE_NOT_EXISTS("2012", "归目规则不存在！"),
    //EVIDENCE_IS_NULL("2013", "新的证据为空！"),

    ;
    /**
     * 消息码
     */
    private String code;
    /**
     * 消息内容
     */
    private String msg;

    private BusinessMsgEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return code;
    }

    public String msg() {
        return msg;
    }

}
