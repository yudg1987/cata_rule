package com.iflytek.rule.model.dto;

import com.iflytek.rule.entity.EdFolderMap;

public class EdFolderMapDTO extends EdFolderMap {

	public EdFolderMapDTO(String mapingName, String folderName, Integer isMain, String caseType, String caseTypeName) {
		super(mapingName, folderName, isMain, caseType);
		this.caseTypeName = caseTypeName;
	}

	private String caseTypeName;

	public String getCaseTypeName() {
		return caseTypeName;
	}

	public void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = caseTypeName;
	}

}
