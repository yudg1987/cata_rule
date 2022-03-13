package com.iflytek.rule.model.dto;

import java.util.List;

import lombok.Data;

/**
 * 复用至实体
 * <br>
 * 标题: <br>
 * 描述: <br>
 * 公司: www.iflytek.com<br>
 * @autho dgyu
 * @time 2021年9月3日 上午10:57:09
 */
@Data
public class CopyCatalogToDTO {
	/**
	 * 被复用的目录ID
	 */
   private String fromId;
   /**
    * 选中复用的目录ID集合
    */
   private List<String> toIds;
}
