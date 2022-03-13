package com.iflytek.rule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.iflytek.rule.common.BaseMapper;
import com.iflytek.rule.entity.EdFolderMap;

public interface EdFolderMapMapper extends BaseMapper<EdFolderMap> {
	
	List<EdFolderMap> findEdFolderMapByCaseAndIsMain(@Param(value = "caseType") String caseType,@Param(value = "isMain") Integer isMain,@Param(value = "keyword") String keyword);
	
	@Select("select * from ed_dic_folder_mapping  ORDER BY id,sort")
	@ResultMap("BaseResultMap")
	List<EdFolderMap> findAllEdFolderMap();
	
	@Select("SELECT  IFNULL(MAX(sort),1)  FROM ed_dic_folder_mapping WHERE case_type=#{caseType}  AND maping_name=#{mapingName} AND is_main=#{isMain}")
	@ResultType(java.lang.Integer.class)
	int getMaxSort(@Param(value = "caseType") String caseType,@Param(value = "mapingName") String mapingName,@Param(value = "isMain") Integer isMain);
	
	
	@Select("SELECT * FROM ed_dic_folder_mapping WHERE case_type=#{caseType}  AND maping_name=#{mapingName} AND is_main=#{isMain} ORDER BY ID,SORT")
	@ResultMap("BaseResultMap")
	List<EdFolderMap> getAllFolderNames(@Param(value = "caseType") String caseType,@Param(value = "mapingName") String mapingName,@Param(value = "isMain") Integer isMain);
	
	@Select("DELETE FROM ed_dic_folder_mapping WHERE case_type=#{caseType}  AND maping_name=#{mapingName} AND is_main=#{isMain}")
	void deleteAllFolderNames(@Param(value = "caseType") String caseType,@Param(value = "mapingName") String mapingName,@Param(value = "isMain") Integer isMain);
	
	@Update("update ed_dic_folder_mapping set folder_name=null where case_type=#{caseType}  AND maping_name=#{mapingName} AND is_main=#{isMain}")
    void updateAllFolderNames(@Param(value = "caseType") String caseType,@Param(value = "mapingName") String mapingName,@Param(value = "isMain") Integer isMain);
}