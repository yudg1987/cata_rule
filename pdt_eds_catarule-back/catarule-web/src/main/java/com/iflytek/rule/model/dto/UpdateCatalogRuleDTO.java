package com.iflytek.rule.model.dto;

import lombok.Data;

@Data
public class UpdateCatalogRuleDTO {
	/*
	 * 案件类型编目
	 */
	private String	caseTypeCode;
	/*
	 * 正副卷 1正卷 0副卷
	 */
	private Integer	volumn;
	/**
	 * 目录名称
	 */
	private String mapingName;
	/** 证据名称 以逗号分割 */
	private String	evidenceName;

}
