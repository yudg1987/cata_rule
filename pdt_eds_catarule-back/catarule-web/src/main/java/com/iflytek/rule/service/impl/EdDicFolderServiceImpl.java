package com.iflytek.rule.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iflytek.rule.common.AbstractService;
import com.iflytek.rule.entity.EdDicFolder;
import com.iflytek.rule.mapper.EdDicFolderMapper;
import com.iflytek.rule.service.EdDicFolderService;

/** <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * 
 * @autho dgyu
 * @time 2021年12月6日 下午2:58:25 */
@Service
public class EdDicFolderServiceImpl extends AbstractService<EdDicFolder> implements EdDicFolderService {

	protected final Logger	  logger = LoggerFactory.getLogger(getClass());
	@Resource
	private EdDicFolderMapper edDicFolderMapper;
	@Override
	public Map<String, EdDicFolder> findDicsByCaseType(String caseType,String keyword) {
		LinkedHashMap<String, EdDicFolder> map = new LinkedHashMap<>();
		List<EdDicFolder> listMaps = this.edDicFolderMapper.findDicsByCaseType(caseType,keyword);
		if (CollectionUtils.isEmpty(listMaps)) {
			return map;
		}
		listMaps.forEach(edDicFolder -> {
			map.put(edDicFolder.getName(), edDicFolder);
		});
		return map;
	}

}
