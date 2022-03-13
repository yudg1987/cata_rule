package com.iflytek.rule.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.iflytek.rule.common.BaseMapper;
import com.iflytek.rule.entity.EdDicFolder;

public interface EdDicFolderMapper extends BaseMapper<EdDicFolder> {
    /**
     * 查询以新建文件夹开头的目录
     *
     * @param newName
     * @return
     */
    @Select("select count(1) from ed_folder_opt where name like concat(#{newName},'%') and dossier_id=#{dossierId}")
    Integer findNewDicFolderByName(@Param(value = "newName") String newName, @Param(value = "dossierId") Integer dossierId);

    /**
     * 根据id删除edDicFolder
     *
     * @param id
     */
    @Delete("delete from ed_dic_folder where id = #{id}")
    void deleteDicFolderById(@Param(value = "id") Integer id);

    /**
     * 根据parentCode查询parent_code为parentCode的目录
     *
     * @param parentCode
     * @return
     */
    @Select("select * from ed_dic_folder where parent_code = #{parentCode}")
    @ResultMap("BaseResultMap")
    List<EdDicFolder> findChildNodeByParentCode(@Param(value = "parentCode") String parentCode);

    /**
     * 根据code查询左侧树目录
     *
     * @param name
     * @return
     */
    @Select("select * from ed_dic_folder where name=#{name}")
    @ResultMap("BaseResultMap")
    List<EdDicFolder> findDicFolderByCode(@Param(value = "name") String name);

    /**
     * 根据name 查询左侧树列表
     *
     * @param name
     * @return
     */
    @Select("select * from ed_dic_folder where name = #{name}")
    @ResultMap("BaseResultMap")
    List<EdDicFolder> findDicFolderByName(@Param(value = "name") String name);

    /**
     * 根据id查询左侧树目录信息
     *
     * @param id
     * @return
     */
    @Select("select * from ed_dic_folder where id = #{id}")
    @ResultMap("BaseResultMap")
    EdDicFolder findDicFolderById(@Param(value = "id") Integer id);

    /**
     * 根据parent_code和level以及caseType查询sort最大值
     *
     * @param level
     * @param caseType
     * @param parentCode
     * @return
     */
    @Select("select ifnull(max(sort),0)+1 sort  from (select ifnull(max(sort),0) sort from ed_dic_folder where case_type=#{caseType} and level=#{level} and parent_code=#{parentCode} "
            + "union all select ifnull(max(sort),0)  sort from ed_folder_opt where dossier_id=#{dossierId} and level=#{level} and parent_code=#{parentCode} )a")
    int findSortBylevelAndParentCode(@Param(value = "caseType") String caseType, @Param(value = "level") int level,
                                     @Param(value = "parentCode") String parentCode, @Param(value = "dossierId") int dossierId);

    @Delete("delete from ed_dic_folder where code = #{code}")
    void deleteFolderByCode(String code);


    /**
     * 通过案件类型查询目录
     *
     * @param caseType
     * @return
     */
    @Select(value = "select * from ed_dic_folder where case_type = #{caseType} order by level,sort,code asc")
    @ResultMap("BaseResultMap")
    List<EdDicFolder> findByCaseType(String caseType);

    @Select("select code from ed_dic_folder where case_type=#{caseType} and code like 'default-%' limit 1")
    String findDefaultCode(String caseType);

    @Select("select code from ed_dic_folder where case_type=#{caseType} and name=#{catalogName} ORDER BY code limit 1")
    String findCodeByName(@Param(value = "caseType") String caseType, @Param(value = "catalogName") String catalogName);

    @Select("select * from ed_dic_folder where case_type=#{caseType} and name in #{catalogNames} ORDER BY code")
    List<EdDicFolder> findByNames(@Param("caseType") String caseType,
            @Param("catalogNames") Collection<String> catalogNames);

    /**
     * 查找最大排序值
     *
     * @param level
     * @param caseType
     * @return
     */
    @Select("select ifnull(max(sort),0) from ed_dic_folder where case_type=#{caseType} and level=#{level}")
    int findSortBylevel(@Param(value = "level") int level, @Param(value = "caseType") String caseType);

    /**
     * 修改节点名
     *
     * @param code
     * @param name
     * @param caseType
     */
    @Update("update ed_dic_folder set name=#{name} where code=#{code} and case_type=#{caseType}")
    void updateTree(@Param(value = "code") String code, @Param(value = "name") String name, @Param(value = "caseType") String caseType);

    @Select(value = {"select code from ed_dic_folder a,ed_dic_folder_mapping b where a.name = b.maping_name "
            + "and a.case_type=#{caseType} and b.case_type = #{caseType} and b.folder_name <> '' "
            + "and (b.folder_name like concat('%',#{folderName},'%') or #{folderName} like concat('%',b.folder_name,'%')) limit 1"})
    String findMatchCodeByName(@Param(value = "folderName") String folderName, @Param(value = "caseType") String caseType);

    @Select("select code from ed_dic_folder where name = #{folderName} and case_type = #{caseType}")
    String findParentCodeByDicNameAndCaseType(@Param(value = "folderName") String folderName, @Param(value = "caseType") String caseType);

    List<Integer> findIds(String code);

    @Select("select IFNULL(max(sort),1) from ed_dic_folder where case_type=#{caseType} and level=#{level} and parent_code=#{parentCode}")
    int findMaxSort(@Param(value = "caseType") String caseType, @Param(value = "level") int level,
                    @Param(value = "parentCode") String parentCode);

    @Update("update ed_dic_folder set name=#{name} where code=#{code}")
    void updateNameByCode(@Param(value = "code") String code, @Param(value = "name") String name);

    @Select("select code from ed_dic_folder where case_type=(select code from ed_case_type where third_code=#{caseType} limit 1) and code like 'default-%' ")
    String findDefaultByThirdCaseType(String caseType);
    
    //20211206归目规则管理功能新增
    List<EdDicFolder> findDicsByCaseType(@Param(value = "caseType")String caseType,@Param(value = "keyword")String keyword);
    
    @Select("SELECT * FROM ed_dic_folder  WHERE case_type=#{caseType} and level=1 and (parent_code is null or parent_code='') ORDER BY sort limit 1")
    @ResultMap("BaseResultMap")
    EdDicFolder findParentDicsByCaseType(@Param(value = "caseType")String caseType);
    
    @Select("select IFNULL(max(sort),1) from ed_dic_folder ")
    int findMaxSortFromAll();
}