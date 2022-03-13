package com.iflytek.rule.service.impl;

import com.iflytek.rule.common.ExcelCommonData;
import com.iflytek.rule.service.ExcelReadAndWriteService;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@RefreshScope
public class ExcelReadAndWriteServiceImpl implements ExcelReadAndWriteService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public ExcelCommonData populateExcelCommonData(String sheetName,String fileName,String[] titles) {
		ExcelCommonData commonData = new ExcelCommonData();
        commonData.setSheetName(sheetName);
        commonData.setFileName(fileName);
        List<String> titleList = new ArrayList<String>();
        for(int i=0;i<titles.length;i++) {
        	titleList.add(i, titles[i]);
        }
        commonData.setTitles(titleList);
        return commonData;
	}
	@Override
	public void exportExcel(HttpServletResponse response, ExcelCommonData commonData, List<List<Object>> edFolderMapList) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
		response.setContentType("application/vnd.ms-excel;charset=UTF-8"); 
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(commonData.getFileName(), "utf-8"));
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        try {
            String sheetName = commonData.getSheetName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet(sheetName);
            int rowIndex = 0;
            if(commonData.getTitles().size()>0) {
            	rowIndex = writeTitlesToExcel(wb, sheet, commonData.getTitles());
            }          
            writeRowsToExcel(wb, sheet, edFolderMapList, rowIndex);
            OutputStream out = response.getOutputStream();  
            try {  
                wb.write(out);// 将数据写出去  
            } catch (Exception e) {
                logger.error("导出数据错发生错误"+e.toString()+System.currentTimeMillis());
            } finally {  
                out.close();  
            }    
            
	   } finally {
//	       wb.close();
	   }
    }
	
	@Override
	public int writeTitlesToExcel(SXSSFWorkbook wb, Sheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex = 0;
        Font titleFont = wb.createFont();
//        titleFont.setBoldweight();
        titleFont.setColor(Font.COLOR_NORMAL); // 将字体设置为
        titleFont.setFontHeightInPoints((short) 10); // 将字体大小设置为18px
        titleFont.setFontName("微软雅黑");
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFont(titleFont);

        Row titleRow = sheet.createRow(rowIndex);
        colIndex = 0;
        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }
        rowIndex++;       
        return rowIndex;
    }
	@Override
	public void writeRowsToExcel(SXSSFWorkbook wb, Sheet sheet, List<List<Object>> edFolderMapList, int rowIndex) {
        int colIndex = 0;
        for (List<Object> rowData : edFolderMapList) {
            Row dataRow = sheet.createRow(rowIndex);
            colIndex = 0;
            for (Object cellData : rowData) {           	
                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                	if(cellData instanceof Integer) {
//                		cell.setCellType(CellType.NUMERIC);
                		cell.setCellValue(((Integer) cellData).intValue());
                	}else if(cellData instanceof String) {
//                		cell.setCellType(CellType.STRING);
                		cell.setCellValue(cellData.toString());
                	}
                } else {
                    cell.setCellValue("");
                }
                colIndex++;
            }
            rowIndex++;
        } 
    }
	@Override
	public Map<String, Object> getDataExcel(MultipartFile file) {
		Map<String, Object> detailMap = new HashMap<String, Object>();
		InputStream is = null;
		List<List<Object>> rowList = null;
		XSSFWorkbook wb = null;
		try {
			is = file.getInputStream();
			wb = new XSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(0);
			if (sheet == null) {
				detailMap.put("code", "-1");
				detailMap.put("msg", "Excel中Sheet无效");
				return detailMap;
			}
			int totalRows = sheet.getPhysicalNumberOfRows();
			// 总列数
			int totalCells = 0;
			// 得到Excel的列数(前提是有行数)，从第二行算起
			if (totalRows >= 2 && sheet.getRow(0) != null) {
				totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
				rowList = new ArrayList<List<Object>>();
				for (int r = 1; r <= totalRows - 1; r++) {
					if (totalCells != 5) {
						detailMap.put("code", "-3");
						detailMap.put("msg", "第" + (r + 1) + "行数据不是5列!");
						return detailMap;
					}
					Row row = sheet.getRow(r);
					if (row == null) {
						break;
					}
					List<Object> objectList = new ArrayList<Object>();
					for (int c = 0; c < totalCells && c<5; c++) {
						Object cellValue = null;
						Cell cell = row.getCell(c);
						if (null == cell) {
							objectList.add(c, "");
							continue;
						}
						int type = cell.getCellType();
						if (type == 0) {
							cellValue = cell.getNumericCellValue();
						}
						else if (type == 1) {
							cellValue = cell.getStringCellValue();
						}
						objectList.add(c, cellValue);
					}
					rowList.add(r - 1, objectList);
				}
				detailMap.put("code", "0");
				detailMap.put("msg", "Excel中数据获取成功");
				detailMap.put("rowList", rowList);
			}
			else {
				detailMap.put("code", "-2");
				detailMap.put("msg", "Excel中没有数据");
			}
		}
		catch (IOException e) {
			detailMap.put("code", "-1");
			detailMap.put("msg", "Excel中数据获取失败");
			logger.error("上传数据错发生错误" + e.toString() + System.currentTimeMillis());
		}
		finally {
			try {
				is.close();
			}
			catch (IOException e) {
				detailMap.put("code", "-1");
				detailMap.put("msg", "关闭文件流失败");
				logger.error("关闭文件流失败" + e.toString() + System.currentTimeMillis());
			}
		}
		return detailMap;
	}	
}
