package com.iflytek.rule.entity;

import lombok.Data;

@Data
public class CatalogRule {

	/** 案件类型ID */
	private String	caseTypeCode;
	/** 案件类型ID */
	private Integer	caseTypeId;

	/** 案件类型名称 */
	private String	caseTypeName;

	/** 1：正卷 2：副卷 */
	private Integer	volumn;

	/** 正卷/副卷 */
	private String	volumnName;

	/** 目录名称 */
	private String	catalogName;

	/** 证据名称,以逗号(中文和英文)隔开 */
	private String	evidenceName;

	/** 顺序 */
	private Integer	ruleOrder;
}