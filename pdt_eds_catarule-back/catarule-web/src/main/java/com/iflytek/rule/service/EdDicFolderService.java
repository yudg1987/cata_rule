package com.iflytek.rule.service;

import java.util.Map;


import com.iflytek.rule.common.Service;
import com.iflytek.rule.entity.EdDicFolder;

/** <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * 
 * @autho dgyu
 * @time 2021年12月6日 下午3:01:00 */
public interface EdDicFolderService extends Service<EdDicFolder> {
	
	Map<String,EdDicFolder> findDicsByCaseType(String caseType,String keyword);

}
