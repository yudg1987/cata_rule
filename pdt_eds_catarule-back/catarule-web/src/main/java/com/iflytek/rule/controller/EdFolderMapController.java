package com.iflytek.rule.controller;

import com.iflytek.rule.common.SuccessJsonResult;
import com.iflytek.rule.common.enums.VolumeEnum;
import com.iflytek.rule.common.exception.BusinessErrorException;
import com.iflytek.rule.entity.CatalogRule;
import com.iflytek.rule.entity.EdCaseType;
import com.iflytek.rule.entity.EdFolderMap;
import com.iflytek.rule.model.dto.CatalogRuleDTO;
import com.iflytek.rule.model.dto.CatalogRuleDataDTO;
import com.iflytek.rule.model.dto.CopyEdFolderToDTO;
import com.iflytek.rule.model.dto.EdFolderMapDTO;
import com.iflytek.rule.model.dto.UpdateCatalogRuleDTO;
import com.iflytek.rule.service.EdCaseTypeService;
import com.iflytek.rule.service.EdFolderMapService;

import io.swagger.annotations.ApiOperation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 归目规则库管理 <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * 
 * @autho dgyu
 * @time 2021年12月3日 下午5:28:42 */
@RestController
@RequestMapping(value = "/catalog/rule", produces = "application/json; charset=UTF-8")
public class EdFolderMapController {

	private final Logger	   logger = LoggerFactory.getLogger(getClass());
	@Resource
	private EdFolderMapService edFolderMapService;
	@Resource
	private EdCaseTypeService  edCaseTypeService;

	@PostMapping
	@ApiOperation(value = "添加归目规则：目录、证据映射，提供给道律使用", notes = "添加归目规则：目录、证据映射，提供给道律使用")
	public SuccessJsonResult<Integer> add(@RequestBody List<EdFolderMapDTO> datas) {
		logger.info("添加归目规则入参 edFolderMap:{}", JSON.toJSONString(datas, SerializerFeature.WriteNullStringAsEmpty));
		return edFolderMapService.addRule(datas);
	}

	@DeleteMapping("/{id}")
	public SuccessJsonResult<Void> delete(@PathVariable Integer id) {
		edFolderMapService.deleteById(id);
		return new SuccessJsonResult<Void>();
	}

	@PutMapping
	public SuccessJsonResult<Void> update(@RequestBody EdFolderMap edFolderMap) {
		edFolderMapService.update(edFolderMap);
		return new SuccessJsonResult<Void>();
	}

	@GetMapping("/{id}")
	public SuccessJsonResult<EdFolderMap> detail(@PathVariable Integer id) {
		EdFolderMap edFolderMap = edFolderMapService.findById(id);
		return new SuccessJsonResult<EdFolderMap>(edFolderMap);
	}

	@GetMapping
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SuccessJsonResult<PageInfo> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
		PageHelper.startPage(page, size);
		List<EdFolderMap> list = edFolderMapService.findAll();
		PageInfo pageInfo = new PageInfo(list);
		return new SuccessJsonResult<PageInfo>(pageInfo);
	}

	@ApiOperation(value = "获取全量卷", notes = "获取全量卷")
	@RequestMapping(value = "/getAllVolume", method = RequestMethod.POST)
	public SuccessJsonResult<List<JSONObject>> getAllVolume() throws BusinessErrorException {
		logger.info("getAllVolume start");
		List<JSONObject> list = new ArrayList<JSONObject>();
		Arrays.asList(VolumeEnum.values()).forEach(v -> {
			JSONObject o = new JSONObject();
			o.put("volumeType", v.getVolumeType());
			o.put("volumeName", v.getVolumeName());
			list.add(o);
		});
		return new SuccessJsonResult<>(list);
	}

	@ApiOperation(value = "获取全量案件类型", notes = "获取全量案件类型")
	@RequestMapping(value = "/getAllCaseType", method = RequestMethod.POST)
	public SuccessJsonResult<List<EdCaseType>> getAllCaseType() throws BusinessErrorException {
		logger.info("getAllCaseType start");
		return new SuccessJsonResult<>(this.edCaseTypeService.getAllCaseType());
	}

	@ApiOperation(value = "根据案件类型、卷获取材料目录", notes = "根据案件类型、卷获取材料目录")
	@PostMapping(value = "/getCatalog")
	public SuccessJsonResult<List<CatalogRule>> getCatalog(@RequestBody CatalogRuleDTO catalogRuleDTO) {
		logger.info("getCatalog start");
		return new SuccessJsonResult<List<CatalogRule>>(edFolderMapService.getCatalog(catalogRuleDTO));
	}

	@GetMapping("/exportRuleModel")
	@ApiOperation(value = "下载归目规则模板", notes = "下载归目规则模板")
	public void exportRuleModel(HttpServletResponse response) throws Exception {
		edFolderMapService.downLoadExcle(response);
	}

	@ApiOperation(value = "导出归目规则EXCEL", notes = "导出归目规则EXCEL")
	@RequestMapping(value = "/exportRule", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public void exportRule(HttpServletResponse response) throws Exception {
		long start = System.currentTimeMillis();
		logger.debug("导出归目规则EXCEL start");
		edFolderMapService.exportRule(response);
		logger.debug("导出归目规则EXCEL end！耗时：{}ms.", System.currentTimeMillis() - start);
	}

	@ApiOperation(value = "导入归目规则EXCEL", notes = "导入归目规则EXCEL")
	@RequestMapping(value = { "/importRule" }, method = RequestMethod.POST)
	public SuccessJsonResult<Integer> importRuleModel(@RequestParam("file") MultipartFile file) throws Exception {
		return this.edFolderMapService.importRuleModel(file);
	}

	@ApiOperation(value = "复用至/复用接口共用", notes = "复用至/复用接口共用")
	@PostMapping(value = "/copyTo")
	public SuccessJsonResult<Void> copyTo(@RequestBody CopyEdFolderToDTO copyEdFolderToDTO) {
		SuccessJsonResult<Void> result = new SuccessJsonResult<>();
		result.setMsg(edFolderMapService.copyTo(copyEdFolderToDTO));
		return result;
	}

	@ApiOperation(value = "编辑证据名称", notes = "编辑证据名称")
	@PutMapping("/edit")
	public SuccessJsonResult<CatalogRuleDataDTO> update(@RequestBody UpdateCatalogRuleDTO updateCatalogRuleDTO) throws Exception {
		return new SuccessJsonResult<CatalogRuleDataDTO>(edFolderMapService.updateEvidenceName(updateCatalogRuleDTO));
	}

	@ApiOperation(value = "根据案件类型、卷、目录名称获取证据详情", notes = "根据案件类型、卷、目录名称获取证据详情")
	@RequestMapping(value = { "/detail" }, method = RequestMethod.POST)
	public SuccessJsonResult<CatalogRuleDataDTO> detail(@RequestBody CatalogRuleDataDTO catalogRuleDataDTO) throws Exception {
		return new SuccessJsonResult<CatalogRuleDataDTO>(edFolderMapService.detail(catalogRuleDataDTO));
	}
}
