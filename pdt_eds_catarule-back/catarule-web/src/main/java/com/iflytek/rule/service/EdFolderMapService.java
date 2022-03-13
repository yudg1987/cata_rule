package com.iflytek.rule.service;
import com.iflytek.rule.entity.CatalogRule;
import com.iflytek.rule.entity.EdFolderMap;
import com.iflytek.rule.model.dto.CatalogRuleDTO;
import com.iflytek.rule.model.dto.CatalogRuleDataDTO;
import com.iflytek.rule.model.dto.CopyEdFolderToDTO;
import com.iflytek.rule.model.dto.EdFolderMapDTO;
import com.iflytek.rule.model.dto.UpdateCatalogRuleDTO;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.iflytek.rule.common.Service;
import com.iflytek.rule.common.SuccessJsonResult;


/**
 * 
 * <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * @autho dgyu
 * @time 2021年12月3日 下午5:34:34
 */
public interface EdFolderMapService extends Service<EdFolderMap> {
	
	public List<CatalogRule> getCatalog(CatalogRuleDTO catalogRuleDTO);
	
	public void downLoadExcle(HttpServletResponse response) throws Exception ;
	
	/** 导入归目规则EXCEL
	 * 
	 * @param file
	 * @return */
	SuccessJsonResult<Integer> importRuleModel(MultipartFile file);
	/** 导出归目规则 <br>
	 * 适用场景: <br>
	 * 调用方式: <br>
	 * 业务逻辑说明<br>
	 *
	 * @param response
	 * @return
	 * @throws Exception
	 * @autho dgyu
	 * @time 2021年8月13日 上午10:00:41 */
	void exportRule(HttpServletResponse response) throws Exception;
	/**
	 * 更新证据名称
	 * <br>
	 * 适用场景:	<br>
	 * 调用方式:	<br>
	 * 业务逻辑说明<br>
	 *
	 * @param updateCatalogRuleDTO
	 * @return
	 * @autho dgyu
	 * @time 2021年8月13日 上午11:42:57
	 */
	CatalogRuleDataDTO updateEvidenceName(UpdateCatalogRuleDTO updateCatalogRuleDTO) throws Exception;
	
	String copyTo(CopyEdFolderToDTO copyEdFolderToDTO);
	
	CatalogRuleDataDTO detail(CatalogRuleDataDTO catalogRuleDataDTO);
	
	/** 添加归目规则，提供道律
	 * 
	 * @param file
	 * @return */
	SuccessJsonResult<Integer> addRule(List<EdFolderMapDTO> datas);

}
