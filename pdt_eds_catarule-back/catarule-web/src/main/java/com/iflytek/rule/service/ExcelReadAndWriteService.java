package com.iflytek.rule.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.iflytek.rule.common.ExcelCommonData;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ExcelReadAndWriteService {
	/**
	 *	构造excel表头相关信息
	 * @param sheetName
	 * @param fileName
	 * @param titles
	 * @return
	 */
	 ExcelCommonData populateExcelCommonData(String sheetName, String fileName, String[] titles);

	/**
	 * 导出
	 * @param response
	 * @param commonData
	 * @param edFolderMapList
	 * @throws Exception
	 */
	 void exportExcel(HttpServletResponse response, ExcelCommonData commonData, List<List<Object>> edFolderMapList) throws Exception;

	/**
	 * 写入标题到excel
	 * @param wb
	 * @param sheet
	 * @param titles
	 * @return
	 */
	 int writeTitlesToExcel(SXSSFWorkbook wb, Sheet sheet, List<String> titles);

	/**
	 * 写数据到excel
	 * @param wb
	 * @param sheet
	 * @param edFolderMapList
	 * @param rowIndex
	 */
	 void writeRowsToExcel(SXSSFWorkbook wb, Sheet sheet, List<List<Object>> edFolderMapList, int rowIndex);

	/**
	 * 获取execl数据
	 * @param file
	 * @return
	 */
	 Map<String, Object> getDataExcel(MultipartFile file);
}
