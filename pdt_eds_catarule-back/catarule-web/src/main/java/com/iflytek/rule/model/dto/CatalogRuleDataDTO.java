package com.iflytek.rule.model.dto;

import java.util.Map;

import lombok.Data;

@Data
public class CatalogRuleDataDTO {

	/** 1：正卷 2：副卷 */
	private Integer				volumn;

	/** 案件类型ID */
	private String				caseTypeCode;

	/** 目录名称 */
	private String				mapingName;

	/** 证据名称,以逗号(中文和英文)隔开 */
	private String				folderName;

	/** 顺序 */
	private Integer				ruleOrder;
	/** 冲突提示 */
	private Map<String, String>	conflictTip;
	public CatalogRuleDataDTO(Integer volumn, String caseTypeCode, String mapingName, String folderName, Integer ruleOrder, Map<String, String> conflictTip) {
		super();
		this.volumn = volumn;
		this.caseTypeCode = caseTypeCode;
		this.mapingName = mapingName;
		this.folderName = folderName;
		this.ruleOrder = ruleOrder;
		this.conflictTip = conflictTip;
	}
}
