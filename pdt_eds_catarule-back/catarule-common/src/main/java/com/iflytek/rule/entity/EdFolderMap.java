package com.iflytek.rule.entity;

import javax.persistence.*;

@Table(name = "ed_dic_folder_mapping")
public class EdFolderMap {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 抽象标题
     */
    @Column(name = "maping_name")
    private String mapingName;

    /**
     * 文件夹名称
     */
    @Column(name = "folder_name")
    private String folderName;

    /**
     * 是否为正卷规则（1:是；0:否），0表示为副卷规则
     */
    @Column(name = "is_main")
    private Integer isMain;

    /**
     * 案件类型
     */
    @Column(name = "case_type")
    private String caseType;

    /**
     * 映射到同一目录规则下的顺序
     */
    private Integer sort;
	/**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取抽象标题
     *
     * @return maping_name - 抽象标题
     */
    public String getMapingName() {
        return mapingName;
    }

    /**
     * 设置抽象标题
     *
     * @param mapingName 抽象标题
     */
    public void setMapingName(String mapingName) {
        this.mapingName = mapingName;
    }

    /**
     * 获取文件夹名称
     *
     * @return folder_name - 文件夹名称
     */
    public String getFolderName() {
        return folderName;
    }

    /**
     * 设置文件夹名称
     *
     * @param folderName 文件夹名称
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    /**
     * 获取是否为正卷规则（1:是；0:否），0表示为副卷规则
     *
     * @return is_main - 是否为正卷规则（1:是；0:否），0表示为副卷规则
     */
    public Integer getIsMain() {
        return isMain;
    }

    /**
     * 设置是否为正卷规则（1:是；0:否），0表示为副卷规则
     *
     * @param isMain 是否为正卷规则（1:是；0:否），0表示为副卷规则
     */
    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    /**
     * 获取案件类型
     *
     * @return case_type - 案件类型
     */
    public String getCaseType() {
        return caseType;
    }

    /**
     * 设置案件类型
     *
     * @param caseType 案件类型
     */
    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    /**
     * 获取映射到同一目录规则下的顺序
     *
     * @return sort - 映射到同一目录规则下的顺序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置映射到同一目录规则下的顺序
     *
     * @param sort 映射到同一目录规则下的顺序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

	public EdFolderMap(String mapingName, String folderName, Integer isMain, String caseType) {
		super();
		this.mapingName = mapingName;
		this.folderName = folderName;
		this.isMain = isMain;
		this.caseType = caseType;
	}

	public EdFolderMap(String mapingName, String folderName, Integer isMain, String caseType, Integer sort) {
		super();
		this.mapingName = mapingName;
		this.folderName = folderName;
		this.isMain = isMain;
		this.caseType = caseType;
		this.sort = sort;
	}

	public EdFolderMap() {
		super();
	}

	public EdFolderMap(Integer id, String mapingName, String folderName, Integer isMain, String caseType, Integer sort) {
		super();
		this.id = id;
		this.mapingName = mapingName;
		this.folderName = folderName;
		this.isMain = isMain;
		this.caseType = caseType;
		this.sort = sort;
	}
}