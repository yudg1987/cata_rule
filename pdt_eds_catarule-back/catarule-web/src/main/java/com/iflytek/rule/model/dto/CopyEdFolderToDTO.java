package com.iflytek.rule.model.dto;

import java.util.List;

import lombok.Data;

/**
 * 复用至/复用实体
 * <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * @autho dgyu
 * @time 2021年12月6日 上午8:41:38
 */
@Data
public class CopyEdFolderToDTO {
	/**
	 * 案件类型编码
	 */
   private String caseTypeCode;
   /**
    * 正副卷
    */
   private Integer volumn;
   /**
    * 选中的目录名称
    */
   private String mapingName;
   /**
    * 选中复用的目录集合
    */
   private List<CopyEdFolderToDTO> toEdFolders;
}
