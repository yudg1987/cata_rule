package com.iflytek.rule.model.dto;

import lombok.Data;

@Data
public class CatalogRuleDTO {
	/**
	 * 案件类型ID
	 */
	private Integer caseTypeId;

	/**
	 * 案件类型Code
	 */
	private String caseTypeCode;
	/*
	 * 卷类型
	 */
	private Integer volumn;
	
	/*
	 * 卷关键词，支持模糊搜索
	 */
	private String keyword;

	public CatalogRuleDTO(Integer caseTypeId, Integer volumn) {
		super();
		this.caseTypeId = caseTypeId;
		this.volumn = volumn;
	}

	public CatalogRuleDTO() {
		super();
	}

	public CatalogRuleDTO(Integer caseTypeId, String caseTypeCode, Integer volumn, String keyword) {
		super();
		this.caseTypeId = caseTypeId;
		this.caseTypeCode = caseTypeCode;
		this.volumn = volumn;
		this.keyword = keyword;
	}

	public CatalogRuleDTO(String caseTypeCode, Integer volumn) {
		super();
		this.caseTypeCode = caseTypeCode;
		this.volumn = volumn;
	}

}
