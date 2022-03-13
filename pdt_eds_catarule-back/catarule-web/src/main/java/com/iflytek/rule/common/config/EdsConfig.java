package com.iflytek.rule.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "eds", ignoreUnknownFields = true)
@Component
public class EdsConfig {
	/**
	 * 是否编目
	 */
	private Integer isCont;
	/**
	 * 是否归目
	 */
	private Integer isOneKeyToCatalog;
	/**
	 * 是否语义抽取
	 */
	private Integer isElle;
    /**
     * ocr地址 ip+端口
     */
    private String ocrIp;

    /**
     * 自动编目地址
     */
    private String contUrl;
    
    /**
     * 归目地址
     */
    private String toCatalogUrl;

    /**
     * 地址
     */
    private String evsUrl;

    /**
     * 文件访问地址
     */
    private static String fileUrl;

    /**
     * 图片访问地址
     */
    private String ocrUrl;

    /**
     * ftp文件上传地址
     */
    private String ftpIp;
    private Integer ftpPort;
    private String ftpPath;
    private String ftpUser;
    private String ftpPwd;
    /**
	 * 左侧树传给阅卷模块
	 */
	private String treeToEvidence;
    /**
     * 地区版本
     */
    private static String version;
    /**
     * 上海监狱局pdf存放位置
     */
    private String prisonStorePath;

    /**
     * 判断任务中断时间
     */
    private long interrupTime;
    
    private String titleSupplement;

    /**
     * 图像识别超时时间设定
     */
    private int timeout;
    private int dossierSeparateNum;
    private static  int computeSemblanceOn;

    private int archiveDept;

    private String caseType;

    private String cookie;

    private int imageHeightAndWidth;

    private int redisLockTimeout;

    //证据名字要过滤的特殊字符
    private static String nameFilterAll;
    private static String nameFilterBeginEnd;
    /**
     * OCR重新识别次数
     */
	private int retryOcrTimes;
	/**
	 * OCR重新识别间隔（ms）
	 */
	private Long retryOcrInterval;


    /**
     * 询问/讯问笔录是否自动排序
     */
    private int isAutoSort;
    
    private String point;

    public static String getNameFilterAll() {
        return nameFilterAll;
    }

    public void setNameFilterAll(String nameFilterAll) {
        this.nameFilterAll = nameFilterAll;
    }

    public static String getNameFilterBeginEnd() {
        return nameFilterBeginEnd;
    }

    public void setNameFilterBeginEnd(String nameFilterBeginEnd) {
        this.nameFilterBeginEnd = nameFilterBeginEnd;
    }

    public int getIsAutoSort() {
        return isAutoSort;
    }

    public void setIsAutoSort(int isAutoSort) {
        this.isAutoSort = isAutoSort;
    }

    public int getArchiveDept() {
        return archiveDept;
    }

    public void setArchiveDept(int archiveDept) {
        this.archiveDept = archiveDept;
    }

    public  static  int getComputeSemblanceOn() {
        return computeSemblanceOn;
    }

    public void setComputeSemblanceOn(int computeSemblanceOn) {
        this.computeSemblanceOn = computeSemblanceOn;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getOcrMethod() {
        return ocrMethod;
    }

    public void setOcrMethod(String ocrMethod) {
        this.ocrMethod = ocrMethod;
    }

    private String ocrMethod;


    public long getInterrupTime() {
        return interrupTime;
    }

    public void setInterrupTime(long interrupTime) {
        this.interrupTime = interrupTime;
    }

    public Integer getIsCont() {
		return isCont;
	}

	public void setIsCont(Integer isCont) {
		this.isCont = isCont;
	}

	public Integer getIsOneKeyToCatalog() {
		return isOneKeyToCatalog;
	}

	public void setIsOneKeyToCatalog(Integer isOneKeyToCatalog) {
		this.isOneKeyToCatalog = isOneKeyToCatalog;
	}

	public Integer getIsElle() {
		return isElle;
	}

	public void setIsElle(Integer isElle) {
		this.isElle = isElle;
	}

	public String getOcrIp() {
        return ocrIp;
    }

    public void setOcrIp(String ocrIp) {
        this.ocrIp = ocrIp;
    }

    public String getContUrl() {
        return contUrl;
    }

    public void setContUrl(String contUrl) {
        this.contUrl = contUrl;
    }

    public String getToCatalogUrl() {
		return toCatalogUrl;
	}

	public void setToCatalogUrl(String toCatalogUrl) {
		this.toCatalogUrl = toCatalogUrl;
	}

	public String getEvsUrl() {
        return evsUrl;
    }

    public void setEvsUrl(String evsUrl) {
        this.evsUrl = evsUrl;
    }

    public static String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getOcrUrl() {
        return ocrUrl;
    }

    public void setOcrUrl(String ocrUrl) {
        this.ocrUrl = ocrUrl;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPwd() {
        return ftpPwd;
    }

    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public static String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getTreeToEvidence() {
		return "http://" + treeToEvidence;
	}

	public void setTreeToEvidence(String treeToEvidence) {
		this.treeToEvidence = treeToEvidence;
	}

	public String getPrisonStorePath() {
		return prisonStorePath;
	}

	public void setPrisonStorePath(String prisonStorePath) {
		this.prisonStorePath = prisonStorePath;
	}

	public String getTitleSupplement() {
		return titleSupplement;
	}

	public void setTitleSupplement(String titleSupplement) {
		this.titleSupplement = titleSupplement;
	}

    public int getDossierSeparateNum() {
        return dossierSeparateNum;
    }

    public void setDossierSeparateNum(int dossierSeparateNum) {
        this.dossierSeparateNum = dossierSeparateNum;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public int getImageHeightAndWidth() {
        return imageHeightAndWidth;
    }

    public void setImageHeightAndWidth(int imageHeightAndWidth) {
        this.imageHeightAndWidth = imageHeightAndWidth;
    }

    public int getRedisLockTimeout() {
        return redisLockTimeout;
    }

    public void setRedisLockTimeout(int redisLockTimeout) {
        this.redisLockTimeout = redisLockTimeout;
    }

	public int getRetryOcrTimes() {
		return retryOcrTimes;
	}

	public void setRetryOcrTimes(int retryOcrTimes) {
		this.retryOcrTimes = retryOcrTimes;
	}

	public Long getRetryOcrInterval() {
		return retryOcrInterval;
	}

	public void setRetryOcrInterval(Long retryOcrInterval) {
		this.retryOcrInterval = retryOcrInterval;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
}
