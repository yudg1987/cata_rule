package com.iflytek.rule.common;

public class Constants {
	
	private Constants() {

    }

    /**
     * http请求前缀
     */
    public static final String HTTP = "http://";

    /**
     * 定时任务已更新
     */
    public static final String TASK_UPDATE_VALUE = "task_update_value";

    /**
     * 请求参数类型：页码0，文件1，全部2，未处理3,已处理4,归目失败11
     */
    public interface RequestType{
        /**
         * 页码
         */
    	int PAGE = 0;
        /**
         * 文件
         */
    	int FILE = 1;
        /**
         * 目录
         */
    	int CATALOG = 2;
        /**
         * 待整理
         */
    	int UNPROCESS = 3;
        /**
         * 已整理
         */
    	int PROCESS = 4;
        /**
         * 回收站
         */
        int RECYCLEBIN = 5;
        /**
         * 卷
         */
        int DOSSIER = 6;
        /**
         * 归目失败
         */
        int CATALOG_FAIL = 11;

        /**
         * 第三方待入库 公安
         */
        int IS_THIRD_GA = 7;

        /**
         * 第三方待入库 检查院
         */
        int IS_THIRD_JCY = 8;

        /**
         * 第三方待入库 法院
         */
        int IS_THIRD_FY = 9;

        /**
         * 第三方待入库 法院
         */
        int SINGLE_FOLDER = 10;

        /**
         * 未识别材料查询（大卷宗分页）
         */
        int UNKONW_FILE = 12;

        /**
         * 空白页查询(大卷宗分页)
         */
        int BLANK_FILE = 14;

        /**
         * 归目失败(大卷宗分页)
         */
        int CATA_FAIL_PAGING = 15;

        /**
         * 回传失败
         */
        int BACK_FAIL_PAGING = 16;
    }
    /**
     * 文件夹是否有更新状态
     *
     */
    public interface FolderUpdate {
        /**
         * 无更新
         */
        int NO = 0;
        /**
         * 有更新
         */
        int YES = 1;
    }

    /**
     * 自动编目未成功的目录名
     */
    public static final String UNKNOWN = "unknown";
    /**
     * 自动编目服务成功返回码
     */
    public static final String SUCCESS = "0";

    /**
     * 扫描文件格式
     */
    public static final String SCAN_PIC_TYPE = "jpg";
    public static final int CatalogStatus_SUCESS =3;
    public static final int CatalogStatus_FAIL =4;

    /** 印章类型*/
    public static final String OCR_SEAL_TYPE = "SEAL";
    /** 打印体*/
    public static final String OCR_MACHINEPRINT_TYPE = "MACHINEPRINT";
    /** 手写体*/
    public static final String OCR_HANDWRITE_TYPE = "HANDWRITE";

    public interface FileCanEdit {
        /**
         * 可编辑
         */
        int YES = 0;
        /**
         * 不可编辑
         */
        int NO = 1;
    }

    public interface DupEvidenceOperType {

        /**
         * 保存
         */
        int SAVE = 1;
        /**
         * 误报
         */
        int MIS_REPORT = 2;
        /**
         * 删除
         */
        int DELETE = 3;
    }

    public interface FileIsNew {
        /**
         * 新证据
         */
        int YES = 1;
        /**
         * 老证据
         */
        int NO = 0;
    }

    public interface FileType {
        /**
         * 新证据
         */
        int PIC = 1;
        /**
         * 老证据
         */
        int FOLDER = 2;
    }


    public final static String DEFAULT_EDVIDENCE_NAME="卷内文件目录";
    public final static String DEFAULT_COVER_NAME="卷宗封面";

    public interface BackStatus {
        /**
         * 回传成功
         */
        int BACK_SUCCESS = 0;
        /**
         * 回传失败
         */
        int BACK_FAIL = 1;
    }

}
