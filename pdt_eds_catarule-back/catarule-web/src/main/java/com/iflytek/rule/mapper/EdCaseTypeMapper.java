package com.iflytek.rule.mapper;

import com.iflytek.rule.common.BaseMapper;
import com.iflytek.rule.entity.EdCaseType;

import java.util.List;
import java.util.Map;

public interface EdCaseTypeMapper extends BaseMapper<EdCaseType> {

	/**
	 *
	 * @param param
	 * @return
	 */
	List<EdCaseType> findInfoAll(Map<String, Object> param);

	/**
	 * 通过dossierId查找案件类型code
	 * @param dossierId
	 * @return
	 */
	String findCodeByDossierId(String dossierId);

	List<String> findThirdCaseType();
}