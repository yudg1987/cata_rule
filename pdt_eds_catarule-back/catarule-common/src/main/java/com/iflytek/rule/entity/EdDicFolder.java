package com.iflytek.rule.entity;

import javax.persistence.*;

@Table(name = "ed_dic_folder")
public class EdDicFolder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 目录名称
	 */
	private String name;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 层级
	 */
	private Integer level;

	/**
	 * 父编码
	 */
	@Column(name = "parent_code")
	private String parentCode;

	@Column(name = "case_type")
	private String caseType;

	/**
	 * 收案ID
	 */
	@Column(name = "dossier_id")
	private Integer dossierId;
	private String code;

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
	 * 获取目录名称
	 *
	 * @return name - 目录名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置目录名称
	 *
	 * @param name
	 *            目录名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取排序
	 *
	 * @return sort - 排序
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * 设置排序
	 *
	 * @param sort
	 *            排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 获取层级
	 *
	 * @return level - 层级
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * 设置层级
	 *
	 * @param level
	 *            层级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 获取父编码
	 *
	 * @return parent_code - 父编码
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * 设置父编码
	 *
	 * @param parentCode
	 *            父编码
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	/**
	 * @return case_type
	 */
	public String getCaseType() {
		return caseType;
	}

	/**
	 * @param caseType
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	/**
	 * 获取收案ID
	 *
	 * @return dossier_id - 收案ID
	 */
	public Integer getDossierId() {
		return dossierId;
	}

	/**
	 * 设置收案ID
	 *
	 * @param dossierId
	 *            收案ID
	 */
	public void setDossierId(Integer dossierId) {
		this.dossierId = dossierId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public EdDicFolder() {
		super();
	}

	public EdDicFolder(String name, Integer sort, Integer level, String parentCode, String caseType, Integer dossierId, String code) {
		super();
		this.name = name;
		this.sort = sort;
		this.level = level;
		this.parentCode = parentCode;
		this.caseType = caseType;
		this.dossierId = dossierId;
		this.code = code;
	}

}