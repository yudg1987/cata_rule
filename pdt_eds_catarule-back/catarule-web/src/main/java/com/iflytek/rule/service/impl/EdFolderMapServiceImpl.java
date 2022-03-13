package com.iflytek.rule.service.impl;

import com.iflytek.rule.mapper.EdCaseTypeMapper;
import com.iflytek.rule.mapper.EdDicFolderMapper;
import com.iflytek.rule.mapper.EdFolderMapMapper;
import com.iflytek.rule.model.dto.CatalogRuleDTO;
import com.iflytek.rule.model.dto.CatalogRuleDataDTO;
import com.iflytek.rule.model.dto.CopyEdFolderToDTO;
import com.iflytek.rule.model.dto.EdFolderMapDTO;
import com.iflytek.rule.model.dto.UpdateCatalogRuleDTO;
import com.iflytek.rule.entity.CatalogRule;
import com.iflytek.rule.entity.EdCaseType;
import com.iflytek.rule.entity.EdDicFolder;
import com.iflytek.rule.entity.EdFolderMap;
import com.iflytek.rule.service.EdCaseTypeService;
import com.iflytek.rule.service.EdDicFolderService;
import com.iflytek.rule.service.EdFolderMapService;
import com.iflytek.rule.service.ExcelReadAndWriteService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iflytek.rule.common.AbstractService;
import com.iflytek.rule.common.SuccessJsonResult;
import com.iflytek.rule.common.enums.BusinessMsgEnum;
import com.iflytek.rule.common.enums.VolumeEnum;
import com.iflytek.rule.common.exception.BusinessErrorException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/** <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * 
 * @autho dgyu
 * @time 2021年12月3日 下午5:33:07 */
@Service
@Transactional
public class EdFolderMapServiceImpl extends AbstractService<EdFolderMap> implements EdFolderMapService {

	protected final Logger			 logger	= LoggerFactory.getLogger(getClass());
	@Resource
	private EdFolderMapMapper		 edDicFolderMappingMapper;
	@Resource
	private EdDicFolderMapper		 edDicFolderMapper;
	@Resource
	private EdDicFolderService		 edDicFolderService;
	@Resource
	private EdCaseTypeMapper		 edCaseTypeMapper;
	@Resource
	private EdCaseTypeService		 edCaseTypeService;
	@Autowired
	private ExcelReadAndWriteService excelReadAndWriteService;
	@Autowired
	private Executor				 requestExecutor;
	@Override
	public List<CatalogRule> getCatalog(CatalogRuleDTO catalogRuleDTO) {
		List<CatalogRule> rules = new ArrayList<CatalogRule>();
		String caseType = catalogRuleDTO.getCaseTypeCode();
		Integer caseTypeId = catalogRuleDTO.getCaseTypeId();
		Integer volumn = catalogRuleDTO.getVolumn();
		String keyword = catalogRuleDTO.getKeyword();
		EdCaseType edCaseType = null;
		Map<String, EdDicFolder> edDicFoldermap = null;
		if (null != caseTypeId) {
			edCaseType = this.edCaseTypeMapper.selectByPrimaryKey(caseTypeId);
			if (null == edCaseType) {
				logger.error("案件类型ID不存在 caseTypeId:{}", caseTypeId);
				return rules;
			}
			caseType = edCaseType.getCode();
			// 查询所有的目录
			edDicFoldermap = this.edDicFolderService.findDicsByCaseType(caseType, keyword);
		}
		else if (!StringUtils.isEmpty(caseType)) {
			edCaseType = this.edCaseTypeService.findByCode(caseType);
			if (null == edCaseType) {
				logger.error("案件类型编码不存在 caseType:{}", caseType);
				return rules;
			}
			caseTypeId = edCaseType.getId();
			edDicFoldermap = this.edDicFolderService.findDicsByCaseType(caseType, keyword);
		}
		if (MapUtils.isEmpty(edDicFoldermap)) {
			logger.error("案件类型编目下caseType:{}不存在目录", caseType);
			return rules;
		}
		for (String key : edDicFoldermap.keySet()) {
			CatalogRule catalogRule = new CatalogRule();
			EdDicFolder edDicFolder = edDicFoldermap.get(key);
			catalogRule.setCaseTypeId(caseTypeId);
			catalogRule.setCaseTypeName(null == edCaseType ? "" : edCaseType.getName());
			catalogRule.setCatalogName(key);
			catalogRule.setRuleOrder(edDicFolder.getSort());
			catalogRule.setVolumn(volumn);
			catalogRule.setVolumnName(VolumeEnum.getVolumeType(volumn).getVolumeName());
			catalogRule.setCaseTypeCode(caseType);
			rules.add(catalogRule);
		}
		// 查询所有的已有证据的目录
		List<EdFolderMap> list = this.edDicFolderMappingMapper.findEdFolderMapByCaseAndIsMain(caseType, volumn, keyword);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("案件类型、卷下无证据数据，只返回目录caseType:{},volumn:{}", caseType, volumn);
			return rules;
		}
		// 按照目录名称mappingName分组 得到每个目录内证据名称
		Map<String, Collection<EdFolderMap>> map = list.stream().collect(Collectors.groupingBy(EdFolderMap::getMapingName, LinkedHashMap::new, Collectors.toCollection(ArrayList::new)));
		rules.forEach(rule -> {
			String catalogName = rule.getCatalogName();
			Collection<EdFolderMap> enidenceNameList = map.get(catalogName);
			if (CollectionUtils.isNotEmpty(enidenceNameList)) {
				// 目录下存在证据
				enidenceNameList = enidenceNameList.stream().filter(v -> !StringUtils.isEmpty(v.getFolderName())).collect(Collectors.toList());
				if (CollectionUtils.isNotEmpty(enidenceNameList)) {
					String evidenceNames = enidenceNameList.stream().map(EdFolderMap::getFolderName).collect(Collectors.joining(","));
					rule.setEvidenceName(evidenceNames);
				}
			}
		});
		return rules;
	}

	@Override
	public void downLoadExcle(HttpServletResponse response) throws Exception {
		logger.info("downLoadExcleModel start");
		InputStream in = getRuleModel();
		// 设置文件头
		String fileName = "诉讼卷宗材料目录模板.xlsx";
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
		byte[] b = IOUtils.toByteArray(in);
		response.getOutputStream().write(b);
		IOUtils.closeQuietly(in);
	}

	// 不带数据的模板
	private InputStream getRuleModel() throws Exception {
		ClassPathResource defaultFldmlb = new ClassPathResource("directory/catalogRule.xlsx");
		InputStream in = defaultFldmlb.getInputStream();
		return in;
	}

	@Override
	public SuccessJsonResult<Integer> importRuleModel(MultipartFile file) {
		SuccessJsonResult<Integer> result = new SuccessJsonResult<>();
		if (null == file) {
			result.setMsg("导入文件失败");
			result.setCode("-1");
			return result;
		}
		if (judegExcelEdition(file.getOriginalFilename())) {
			result.setMsg("只允许上传excel表格");
			result.setCode("-2");
			return result;
		}
		// 抽取数据
		Map<String, Object> detailMap = excelReadAndWriteService.getDataExcel(file);
		if (MapUtils.isEmpty(detailMap)) {
			result.setMsg("excel中归目规则读取失败");
			result.setCode("-3");
			return result;
		}
		if (null == detailMap.get("rowList") || !detailMap.containsKey("rowList")) {
			logger.warn("excel中归目规则数据读取失败,{}", detailMap.get("msg"));
			return new SuccessJsonResult<Integer>(0);
		}
		if (null != detailMap.get("code") && !"".equals((String) detailMap.get("code")) && "0".equals((String) detailMap.get("code"))) {
			List<List<Object>> allDataList = (List<List<Object>>) detailMap.get("rowList");
			if (allDataList.get(0).size() != 5) {
				result.setMsg("excel中应为5列，分别是案件类型、案件类型编码、正/副卷、目录名称、证据名称");
				result.setCode("-4");
				return result;
			}
			// 写入数据库
			logger.info("导入的数据size:{}:", allDataList.size());
			if (CollectionUtils.isEmpty(allDataList)) {
				result.setMsg("EXCEL文件中无数据");
				result.setCode("-5");
				return result;
			}
			logger.debug("EXCEL中导入的数据:" + JSON.toJSONString(allDataList));
			List<EdFolderMapDTO> datas = convertDatas(allDataList);
			if (CollectionUtils.isEmpty(datas)) {
				result.setMsg("EXCEL文件中数据为空，不满足要求被舍弃了！");
				result.setCode("-6");
				return result;
			}
			try {
				groupAndbatchByAsync(datas);
			}
			catch (Exception e) {
				logger.error("批量异步处理入库操作异常", e);
				result.setMsg("批量异步处理入库操作异常");
				result.setCode("-7");
				return result;
			}
		}
		else {
			result.setMsg((String) detailMap.get("msg"));
			result.setCode((String) detailMap.get("code"));
			return result;
		}
		return result;
	}

	private void groupAndbatchByAsync(List<EdFolderMapDTO> datas) throws Exception {
		// 按照案件类型编码、正副卷、目录名称分组 得到同一组证据
		Map<String, List<EdFolderMapDTO>> map = datas.stream().collect(Collectors.groupingBy(o -> {
			StringBuffer bf = new StringBuffer();
			bf.append(o.getCaseType()).append("_").append(o.getIsMain()).append("_").append(o.getMapingName());
			return bf.toString();
		}, LinkedHashMap::new, Collectors.toCollection(ArrayList::new)));
		// 批量异步处理入库操作
		batchByAsync(map);
	}

	private void batchByAsync(Map<String, List<EdFolderMapDTO>> map) throws Exception {
		final List<EdFolderMap> validRules = new ArrayList<>();
		int size = map.size();
		final CountDownLatch countDownLatch = new CountDownLatch(size);
		for (String key : map.keySet()) {
			final String keyI = key;
			final List<EdFolderMapDTO> enidenceList = map.get(keyI);
			requestExecutor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						String caseTypeCode = keyI.split("_")[0];
						String isMain = keyI.split("_")[1];
						Integer volumn = Integer.parseInt(isMain);
						String mappingName = keyI.split("_")[2];
						int maxSort = edDicFolderMappingMapper.getMaxSort(caseTypeCode, mappingName, volumn);
						int[] maxSortArray = new int[1];
						maxSortArray[0] = maxSort;
						// 证据名称集合allList
						List<String> allList = new ArrayList<>();
						enidenceList.forEach(v -> {
							String foldName = v.getFolderName();
							List<String> list = Arrays.asList(foldName.split(","));
							allList.addAll(list);
						});
						String caseTypeName = enidenceList.get(0).getCaseTypeName();
						// 创建ed_dic_folder 对应案件类型
						EdDicFolder edDicFolder = edDicFolderMapper.findParentDicsByCaseType(caseTypeCode);
						if (null == edDicFolder) {
							Integer maxSortInAll = edDicFolderMapper.findMaxSortFromAll();
							edDicFolder = new EdDicFolder(caseTypeName, maxSortInAll + 1, 1, "", caseTypeCode, null, caseTypeCode + "0000");
							edDicFolderService.save(edDicFolder);
						}

						Map<String, EdDicFolder> allMappingNames = edDicFolderService.findDicsByCaseType(caseTypeCode, null);
						// 创建ed_dic_folder 对应目录
						if (null == allMappingNames || !allMappingNames.containsKey(mappingName) || null == allMappingNames.get(mappingName)) {
							Integer num = null == allMappingNames ? 1 : CollectionUtils.size(allMappingNames) + 1;
							Integer sort = edDicFolder.getSort() + num;
							String code = caseTypeCode + String.format("%04d", num);
							EdDicFolder entity = new EdDicFolder(mappingName, sort, 2, edDicFolder.getCode(), caseTypeCode, null, code);
							edDicFolderService.save(entity);
						}
						// 证据名称去重
						LinkedHashSet<String> allEvidenceList = new LinkedHashSet<>(allList);
						// 查询该案件类型下，同目录，同卷已存在的证据
						List<EdFolderMap> list = edDicFolderMappingMapper.getAllFolderNames(caseTypeCode, mappingName, volumn);
						if (CollectionUtils.isNotEmpty(list)) {
							List<String> existFolderNames = list.stream().map(EdFolderMap::getFolderName).collect(Collectors.toList());
							allEvidenceList.removeAll(existFolderNames);
							// 剩下的就是需要增加的证据名称
							if (CollectionUtils.isNotEmpty(allEvidenceList)) {
								allEvidenceList.forEach(folderName -> {
									validRules.add(new EdFolderMap(mappingName, folderName, volumn, caseTypeCode, ++maxSortArray[0]));
								});
							}
						}
						else {// 不存在 exce中所有的证据全部添加进来
							allEvidenceList.forEach(folderName -> {
								validRules.add(new EdFolderMap(mappingName, folderName, volumn, caseTypeCode, ++maxSortArray[0]));
							});
						}
					}
					catch (Exception e) {
						logger.error("批量处理batchByAsync异常", e);
					}
					finally {
						countDownLatch.countDown();
					}
				}
			});
		}
		countDownLatch.await();
		logger.debug("归目规则数据重新组装后:" + JSON.toJSONString(validRules));
		if (CollectionUtils.isNotEmpty(validRules)) {
			this.save(validRules);
		}

	}

	/** 筛选不符合案件类型要求的数据、转换成Bean <br>
	 * 适用场景: <br>
	 * 调用方式: <br>
	 * 业务逻辑说明<br>
	 *
	 * @param allDataList
	 * @return
	 * @autho dgyu
	 * @time 2021年12月4日 下午7:54:20 */
	private List<EdFolderMapDTO> convertDatas(List<List<Object>> allDataList) {
		List<EdFolderMapDTO> list = new ArrayList<>();
		if (CollectionUtils.isEmpty(allDataList)) {
			return list;
		}
		int row = 2;
		for (List<Object> objectList : allDataList) {
			row++;
			String mapingName = null == objectList.get(3) ? null : objectList.get(3).toString();
			if (StringUtils.isEmpty(mapingName)) {
				logger.error("导入的excel表格中第{}行目录名称为空，舍弃,objectList:{}", row - 1, objectList.toString());
				continue;
			}
			String folderName = null == objectList.get(4) ? null : objectList.get(4).toString().replaceAll("，", ",");
			/*
			 * if (StringUtils.isEmpty(folderName)) { logger.error("导入的excel表格中第{}行证据名称为空，舍弃,objectList:{}", row - 1, objectList.toString()); continue; }
			 */
			String caseTypeName = null == objectList.get(0) ? null : (String) objectList.get(0);
			String caseTypeCode = null == objectList.get(1) ? null : (String) objectList.get(1);
			String volumeName = null == objectList.get(2) ? VolumeEnum.ZHENGJUAN.getVolumeName() : objectList.get(2).toString();
			VolumeEnum volumeEnum = VolumeEnum.getVolumeType(volumeName);
			if (null == volumeEnum) {
				logger.error("导入的excel表格中第{}行卷非【正卷、复卷】，舍弃,objectList:{}", row - 1, objectList.toString());
				continue;
			}
			// 案件类型以表里为准
			if (!StringUtils.isEmpty(caseTypeCode)) {
				EdCaseType edCaseType = this.edCaseTypeService.findByCode(caseTypeCode);
				if (null != edCaseType) {
					caseTypeName = edCaseType.getName();
				}
				else if (null == edCaseType && !StringUtils.isEmpty(caseTypeName)) {// 新创建案件类型
					this.edCaseTypeService.save(new EdCaseType(caseTypeCode, caseTypeName, caseTypeCode));

				}
				else {
					logger.error("导入的excel表格中第{}行案件类型为空、案件类型编码数据库不存在记录，舍弃,objectList:{}", row - 1, objectList.toString());
					continue;
				}
			}
			else if (!StringUtils.isEmpty(caseTypeName) && StringUtils.isEmpty(caseTypeCode)) {
				EdCaseType edCaseType = this.edCaseTypeService.getCaseTypeByName(caseTypeName);
				if (null != edCaseType) {
					caseTypeCode = edCaseType.getCode();
				}
			}
			else {
				logger.error("导入的excel表格中第{}行案件类型、案件类型编码都为空，舍弃,objectList:{}", row - 1, objectList.toString());
				continue;
			}
			Integer isMain = volumeEnum.getVolumeType();
			list.add(new EdFolderMapDTO(mapingName, folderName, isMain, caseTypeCode, caseTypeName));
		}
		return list;
	}

	@Override
	public void exportRule(HttpServletResponse response) throws Exception {
		logger.info("exportRule start ");
		// 获取所有案件类型
		List<EdCaseType> caseTypes = edCaseTypeService.getAllCaseType();
		if (CollectionUtils.isEmpty(caseTypes)) {
			logger.error("案件类型表数据为空！");
			return;
		}
		final List<CatalogRule> allRules = new ArrayList<CatalogRule>();
		int size = CollectionUtils.size(caseTypes);
		final CountDownLatch countDownLatch = new CountDownLatch(size);
		for (EdCaseType caseType : caseTypes) {
			final EdCaseType caseTypeI = caseType;
			requestExecutor.execute(new Runnable() {

				@Override
				public void run() {
					try {

						List<CatalogRule> zhengjuanRules = getCatalog(new CatalogRuleDTO(caseTypeI.getCode(), VolumeEnum.ZHENGJUAN.getVolumeType()));
						if (CollectionUtils.isNotEmpty(zhengjuanRules)) {
							zhengjuanRules.forEach(v -> {
								v.setCaseTypeId(caseTypeI.getId());
								v.setCaseTypeCode(caseTypeI.getCode());
								v.setCaseTypeName(caseTypeI.getName());
							});
							allRules.addAll(zhengjuanRules);
						}
						/*
						 * List<CatalogRule> fujuanRules = getCatalog(new CatalogRuleDTO(caseTypeI.getCode(), VolumeEnum.FUJUAN.getVolumeType())); if
						 * (CollectionUtils.isNotEmpty(fujuanRules)) { fujuanRules.forEach(v -> { v.setCaseTypeId(caseTypeI.getId());
						 * v.setCaseTypeCode(caseTypeI.getCode()); v.setCaseTypeName(caseTypeI.getName()); }); allRules.addAll(fujuanRules); }
						 */

					}
					catch (Exception e) {
						logger.error("批量查询目录规则异常", e);
					}
					finally {
						countDownLatch.countDown();
					}
				}
			});
		}
		countDownLatch.await();
		if (CollectionUtils.isEmpty(allRules)) {
			logger.error("归目规则为空！");
			return;
		}
		logger.info("查询归目目录、证据完成,size:{}", CollectionUtils.size(allRules));
		// 按照案件类型id 目录顺序 升序排序
		List<CatalogRule> sortedAllRules = allRules.stream().filter(rule -> null != rule).sorted(Comparator.comparing(CatalogRule::getCaseTypeId).thenComparing(CatalogRule::getRuleOrder))
		        .collect(Collectors.toList());
		// 读取EXCEL模板
		InputStream in = getRuleModel();
		// 复制模板 写入数据
		File tempModel = copyModel(in);
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(tempModel));
		Sheet sheet = wb.getSheetAt(0);
		if (sheet == null) {
			logger.error("Excel中Sheet无效");
			return;
		}
		writeData2Excel(wb, sheet, sortedAllRules);
		// 设置文件头
		response.setContentType("application/octet-stream");
		String excelName = "归目规则-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx";
		response.addHeader("Content-Disposition", "attachment;fileName=" + new String(excelName.getBytes("UTF-8"), "ISO-8859-1"));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.write(bos);
		byte[] bytes = bos.toByteArray();
		response.getOutputStream().write(bytes);
		IOUtils.closeQuietly(in);
		IOUtils.closeQuietly(bos);
		logger.info("Excel中数据导出成功");
	}

	@Override
	public CatalogRuleDataDTO updateEvidenceName(UpdateCatalogRuleDTO updateCatalogRuleDTO) throws Exception {
		logger.info("updateEvidenceName start updateCatalogRuleDTO:{}", JSON.toJSONString(updateCatalogRuleDTO));
		String caseTypeCode = updateCatalogRuleDTO.getCaseTypeCode();
		Integer isMain = updateCatalogRuleDTO.getVolumn();
		String mapingName = updateCatalogRuleDTO.getMapingName();
		String newevidenceName = updateCatalogRuleDTO.getEvidenceName();
		if (StringUtils.isEmpty(newevidenceName)) {
			logger.warn("证据为空,newevidenceName{}", newevidenceName);
			this.edDicFolderMappingMapper.updateAllFolderNames(caseTypeCode, mapingName, isMain);
			return new CatalogRuleDataDTO(isMain, caseTypeCode, mapingName, null, null, null);
		}
		List<String> newevidenceNameList = Lists.newArrayList(newevidenceName.replaceAll("，", ",").split(","));
		if (newevidenceNameList.size() > 200) {
			throw new BusinessErrorException(BusinessMsgEnum.EVIDENCE_THAN_TWOHUNDRED);
		}
		/*
		 * List<EdFolderMap> rules = this.edDicFolderMappingMapper.getAllFolderNames(caseTypeCode, mapingName, isMain); if (CollectionUtils.isEmpty(rules)) {
		 * logger.info("归目规则不存在,caseTypeCode:{},mapingName:{},isMain:{}", caseTypeCode, mapingName, isMain); throw new
		 * BusinessErrorException(BusinessMsgEnum.CATALOG_RULE_NOT_EXISTS); }
		 */
		// 对证据进行去重并切保证原顺序
		LinkedHashSet<String> newset = new LinkedHashSet<>(newevidenceNameList.stream().filter(item -> !StringUtils.isEmpty(item)).collect(Collectors.toList()));
		// 删除原有的证据
		this.edDicFolderMappingMapper.deleteAllFolderNames(caseTypeCode, mapingName, isMain);
		List<EdFolderMap> newRules = new ArrayList<>();
		Integer[] sortArray = new Integer[1];
		sortArray[0] = 1;
		newset.forEach(folderName -> {
			newRules.add(new EdFolderMap(mapingName, folderName, isMain, caseTypeCode, sortArray[0]));
			sortArray[0] = sortArray[0] + 1;
		});
		// 批量保存新的归目规则
		this.save(newRules);
		String newFolderNames = newset.stream().map(String::valueOf).collect(Collectors.joining(","));
		CatalogRuleDataDTO catalogRuleDataDTO = new CatalogRuleDataDTO(isMain, caseTypeCode, mapingName, newFolderNames, null, null);
		return checkConflict(catalogRuleDataDTO, newset);
	}

	private CatalogRuleDataDTO checkConflict(CatalogRuleDataDTO oldCatalogRule, Set<String> set) {
		// 查询同案件类型、卷下，不同目录 所有edFolderMaping记录 做冲突校验提示
		String caseTypeCode = oldCatalogRule.getCaseTypeCode();
		Integer isMain = oldCatalogRule.getVolumn();
		String mapingName = oldCatalogRule.getMapingName();
		// 获取所有的ed_dic_folder顺序
		Map<String, EdDicFolder> edDicFolderMap = this.edDicFolderService.findDicsByCaseType(caseTypeCode, null);
		if (MapUtils.isEmpty(edDicFolderMap)) {
			logger.info("案件类型编目：{}归目规则在ed_dic_older中无配置,不做冲突校验提示,mapingName:{},isMain:{}", caseTypeCode, mapingName, isMain);
			return oldCatalogRule;
		}
		EdDicFolder edDicFolder = edDicFolderMap.get(mapingName);
		Integer selfRuleOrder = null == edDicFolder ? Integer.MAX_VALUE : edDicFolder.getSort();
		oldCatalogRule.setRuleOrder(selfRuleOrder);
		Example example = new Example(EdFolderMap.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("caseType", caseTypeCode).andEqualTo("isMain", isMain).andNotEqualTo("mapingName", mapingName);
		example.setOrderByClause("id");
		example.setOrderByClause("sort");
		final List<EdFolderMap> otherList = edDicFolderMappingMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(otherList)) {
			logger.info("归目规则只有一个,不做冲突校验提示,caseTypeCode:{},mapingName:{},isMain:{}", caseTypeCode, mapingName, isMain);
			return oldCatalogRule;
		}
		// 按照目录名称mappingName分组 得到每个目录内证据名称
		Map<String, List<EdFolderMap>> sameMapingNamemap = otherList.stream().collect(Collectors.groupingBy(EdFolderMap::getMapingName, LinkedHashMap::new, Collectors.toCollection(ArrayList::new)));
		final List<CatalogRuleDataDTO> listFilter = new ArrayList<>();
		for (String key : sameMapingNamemap.keySet()) {
			// 同个目录下归目规则集合
			List<EdFolderMap> otherEdFolderMaps = sameMapingNamemap.get(key);
			otherEdFolderMaps = otherEdFolderMaps.stream().filter(v -> !StringUtils.isEmpty(v.getFolderName())).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(otherEdFolderMaps)) {
				String folderNames = otherEdFolderMaps.stream().map(EdFolderMap::getFolderName).collect(Collectors.joining(","));
				edDicFolder = edDicFolderMap.get(key);
				Integer ruleOrder = null == edDicFolder ? Integer.MAX_VALUE : edDicFolder.getSort();
				listFilter.add(new CatalogRuleDataDTO(isMain, caseTypeCode, key, folderNames, ruleOrder, null));
			}

		}
		if (CollectionUtils.isEmpty(listFilter)) {
			logger.info("同类型案件类型、卷下的其他归目规则证据为空,不做冲突校验提示,caseTypeCode:{},mapingName:{},isMain:{}", oldCatalogRule.getCaseTypeCode(), oldCatalogRule.getMapingName(), oldCatalogRule.getVolumn());
			return oldCatalogRule;
		}
		// 新增的证据冲突校验
		Map<String, List<CatalogRuleDataDTO>> conflictTipMap = new HashMap<>();
		for (String evidenceKey : set) {
			for (int i = 0; i < listFilter.size(); i++) {
				CatalogRuleDataDTO catalogRule = listFilter.get(i);
				List<String> evidences = Arrays.asList(catalogRule.getFolderName().split(","));
				if (evidences.contains(evidenceKey)) {
					if (conflictTipMap.containsKey(evidenceKey)) {
						List<CatalogRuleDataDTO> tips = conflictTipMap.get(evidenceKey);
						tips.add(catalogRule);
						conflictTipMap.put(evidenceKey, tips);
					}
					else {
						conflictTipMap.put(evidenceKey, Lists.newArrayList(catalogRule));
					}
				}
			}
		}
		if (MapUtils.isNotEmpty(conflictTipMap)) {
			Set<Map.Entry<String, List<CatalogRuleDataDTO>>> entries = conflictTipMap.entrySet();
			Map<String, String> map = new HashMap<>();
			for (Map.Entry<String, List<CatalogRuleDataDTO>> entry : entries) {
				String key = entry.getKey();
				List<CatalogRuleDataDTO> value = entry.getValue();
				String str = "和%s目录下的证据重复，以%s证据目录进行归目";
				CatalogRuleDataDTO firstCatalogRule = value.get(0);
				String catalogName = value.stream().map(CatalogRuleDataDTO::getMapingName).collect(Collectors.joining("、"));
				if (selfRuleOrder < firstCatalogRule.getRuleOrder()) { // 顺序在前的以自身为准
					str = String.format(str, catalogName, mapingName);
				}
				else {// 顺序在后的以前置为准
					str = String.format(str, catalogName, firstCatalogRule.getMapingName());
				}
				map.put(key, str);
			}
			oldCatalogRule.setConflictTip(map);
		}
		return oldCatalogRule;
	}

	@Override
	public String copyTo(CopyEdFolderToDTO copyEdFolderToDTO) {
		logger.info("copyTo start copyEdFolderToDTO:{}", JSON.toJSONString(copyEdFolderToDTO));
		List<CopyEdFolderToDTO> toEdFolders = copyEdFolderToDTO.getToEdFolders();
		if (CollectionUtils.isEmpty(toEdFolders)) {
			return "请选择复用的目录！";
		}
		String caseTypeCode = copyEdFolderToDTO.getCaseTypeCode();
		String mapingName = copyEdFolderToDTO.getMapingName();
		Integer isMain = copyEdFolderToDTO.getVolumn();
		List<EdFolderMap> folders = this.edDicFolderMappingMapper.getAllFolderNames(caseTypeCode, mapingName, isMain);
		if (CollectionUtils.isEmpty(folders)) {
			return "被复用的归目规则不存在！";
		}
		List<String> evidenceNameList = folders.stream().filter(v -> !StringUtils.isEmpty(v.getFolderName())).map(EdFolderMap::getFolderName).collect(Collectors.toList());
		if (StringUtils.isEmpty(evidenceNameList)) {
			return "被复用的归目规则下的证据为空！";
		}
		// 证据做去重
		LinkedHashSet<String> hashSet = new LinkedHashSet<>(evidenceNameList);
		List<String> listWithoutDuplicates = new ArrayList<>(hashSet);
		final List<EdFolderMap> insertEnties = new ArrayList<>();
		toEdFolders.forEach(toEdFolder -> {
			String toCaseTypeCode = toEdFolder.getCaseTypeCode();
			String tomapingName = toEdFolder.getMapingName();
			Integer toIsMain = copyEdFolderToDTO.getVolumn();
			List<EdFolderMap> toFolders = this.edDicFolderMappingMapper.getAllFolderNames(toCaseTypeCode, tomapingName, toIsMain);
			if (CollectionUtils.isNotEmpty(folders)) {
				Integer[] sortArray = new Integer[1];
				sortArray[0] = 1;
				List<String> toEvidenceNameList = toFolders.stream().filter(v -> !StringUtils.isEmpty(v.getFolderName())).map(EdFolderMap::getFolderName).collect(Collectors.toList());
				this.edDicFolderMappingMapper.deleteAllFolderNames(toCaseTypeCode, tomapingName, toIsMain);
				if (StringUtils.isEmpty(toEvidenceNameList)) {
					logger.info("被复用的归目规则下的证据为空,做批量覆盖操作,toCaseTypeCode:{},tomapingName:{},toIsMain:{}", toCaseTypeCode, tomapingName, toIsMain);
					listWithoutDuplicates.forEach(v -> {
						EdFolderMap edFolderMap = new EdFolderMap(tomapingName, v, toIsMain, toCaseTypeCode, sortArray[0]);
						sortArray[0] = sortArray[0] + 1;
						insertEnties.add(edFolderMap);
					});
				}
				else {// 去重追加
					logger.info("被复用的归目规则下的证据不为空,做去重追加操作,toCaseTypeCode:{},tomapingName:{},toIsMain:{}", toCaseTypeCode, tomapingName, toIsMain);
					listWithoutDuplicates.forEach(item -> {
						if (toEvidenceNameList.contains(item)) {
							toEvidenceNameList.remove(item);
							toEvidenceNameList.add(item);
						}
						else {
							toEvidenceNameList.add(item);
						}
					});
					// 去重
					List<String> newListWithoutDuplicates = new ArrayList<>(new LinkedHashSet<>(toEvidenceNameList));
					newListWithoutDuplicates.forEach(v -> {
						EdFolderMap edFolderMap = new EdFolderMap(tomapingName, v, toIsMain, toCaseTypeCode, sortArray[0]);
						sortArray[0] = sortArray[0] + 1;
						insertEnties.add(edFolderMap);
					});
				}
			}
		});
		logger.info("新增的归目规则insertEnties.size:{}", insertEnties.size());
		if (CollectionUtils.isNotEmpty(insertEnties)) {
			this.save(insertEnties);
		}
		return "归目规则已复用成功！";

	}

	private void writeData2Excel(XSSFWorkbook workbook, Sheet sheet, List<CatalogRule> list) {
		// 创建字体对象
		Font ztFont = workbook.createFont();
		ztFont.setColor(Font.COLOR_NORMAL); // 将字体设置为
		ztFont.setFontHeightInPoints((short) 10); // 将字体大小设置为10px
		ztFont.setFontName("微软雅黑"); // 将“微软雅黑”字体应用到当前单元格上
		// 创建单元格样式对象
		XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setFont(ztFont);
		workbook.setSheetName(0, "归目规则");

		Row row = sheet.getRow(1);
		if (row == null) {
			row = sheet.createRow(1);
		}
		Cell cell = row.getCell(0);
		if (cell == null) {
			cell = row.createCell(0);
		}
		for (int i = 0; i < list.size(); i++) {
			CatalogRule vo = list.get(i);
			Row other = sheet.createRow(i + 1);
			createRowAndCell(vo.getCaseTypeName(), other, cell, 0, style);
			createRowAndCell(vo.getCaseTypeCode(), other, cell, 1, style);
			createRowAndCell(vo.getVolumnName(), other, cell, 2, style);
			createRowAndCell(vo.getCatalogName(), other, cell, 3, style);
			createRowAndCell(vo.getEvidenceName(), other, cell, 4, style);
			logger.debug("已经导出数据:{}条", (i + 1));
		}
	}

	/** 根据当前row行，来创建index标记的列数,并赋值数据 */
	private void createRowAndCell(Object obj, Row row, Cell cell, int index, XSSFCellStyle alignStyle) {
		cell = row.getCell(index);
		if (cell == null) {
			cell = row.createCell(index);
			cell.setCellStyle(alignStyle);
		}
		if (obj != null) {
			if (obj instanceof Double) {
				cell.setCellValue((Double) obj);
			}
			else if (obj instanceof String) {
				cell.setCellValue((String) obj);
			}
			else if (obj instanceof Boolean) {
				cell.setCellValue((Boolean) obj);
			}
			else if (obj instanceof Long) {
				cell.setCellValue((Long) obj);
			}
			else if (obj instanceof Integer) {
				cell.setCellValue((Integer) obj);
			}
			else if (obj instanceof Float) {
				cell.setCellValue((Float) obj);
			}
			else if (obj instanceof Short) {
				cell.setCellValue((Short) obj);
			}
			else {
				cell.setCellValue(obj + "");
			}
		}
		else
			cell.setCellValue("");
		cell.setCellStyle(alignStyle);
	}

	/** 复制EXCEL模板，方便后续规则数据写入 <br>
	 * 适用场景: <br>
	 * 调用方式: <br>
	 * 业务逻辑说明<br>
	 *
	 * @param input
	 * @return
	 * @throws Exception
	 * @autho dgyu
	 * @time 2021年8月13日 上午10:19:58 */
	private File copyModel(InputStream input) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		Path file = Files.createTempFile(UUID.randomUUID().toString(), ".xlsx");
		FileUtils.writeByteArrayToFile(file.toFile(), output.toByteArray());
		output.close();
		// 应用退出时删除文件, 兜底
		file.toFile().deleteOnExit();
		return file.toFile();
	}

	/** 判断上传的excel文件版本（xls为2003，xlsx为2017）
	 * 
	 * @param fileName
	 *        文件路径
	 * @return excel2007及以上版本返回true，excel2007以下版本返回false */
	private static boolean judegExcelEdition(String fileName) {
		if (fileName.matches("^.+\\.(?i)(xls)$") || fileName.matches("^.+\\.(?i)(xlsx)$")) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public CatalogRuleDataDTO detail(CatalogRuleDataDTO catalogRuleDataDTO) {
		// 查询同案件类型、卷下，不同目录 所有edFolderMaping记录 做冲突校验提示
		String caseTypeCode = catalogRuleDataDTO.getCaseTypeCode();
		Integer isMain = catalogRuleDataDTO.getVolumn();
		String mapingName = catalogRuleDataDTO.getMapingName();
		// 获取目录下所有的证据
		List<EdFolderMap> edFolderMapList = this.edDicFolderMappingMapper.getAllFolderNames(caseTypeCode, mapingName, isMain);
		if (CollectionUtils.isEmpty(edFolderMapList)) {
			logger.info("目录下证据无记录，不做冲突校验caseTypeCode:{},isMain:{},mapingName:{}", caseTypeCode, isMain, mapingName);
			return catalogRuleDataDTO;
		}
		List<String> list = edFolderMapList.stream().filter(v -> !StringUtils.isEmpty(v.getFolderName())).map(EdFolderMap::getFolderName).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(edFolderMapList)) {
			logger.info("目录下证据为空，不做冲突校验caseTypeCode:{},isMain:{},mapingName:{}", caseTypeCode, isMain, mapingName);
			return catalogRuleDataDTO;
		}
		LinkedHashSet<String> newset = new LinkedHashSet<>(list);
		catalogRuleDataDTO.setFolderName(newset.stream().collect(Collectors.joining(",")));
		checkConflict(catalogRuleDataDTO, newset);
		return catalogRuleDataDTO;
	}

	@Override
	public SuccessJsonResult<Integer> addRule(List<EdFolderMapDTO> datas) {
		SuccessJsonResult<Integer> result = new SuccessJsonResult<>();
		try {
			groupAndbatchByAsync(datas);
		}
		catch (Exception e) {
			logger.error("添加归目规则失败！", e);
			result.setMsg("添加归目规则失败");
			result.setCode("-1");
			return result;
		}
		return new SuccessJsonResult<Integer>(0);
	}

}
