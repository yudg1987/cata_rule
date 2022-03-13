package com.iflytek.rule.service;
import java.util.List;
import java.util.Map;

import com.iflytek.rule.common.Service;
import com.iflytek.rule.entity.EdCaseType;


/**
 * Created by CodeGenerator on 2018/04/24.
 */
public interface EdCaseTypeService extends Service<EdCaseType> {

    /**
     * 获取所有
     * @param param
     * @return
     */
    List<EdCaseType> findInfoAll(Map<String, Object> param);

    /**
     * 根据dossierId查询
     * @param dossierId
     * @return
     */
    String findCodeByDossierId(String dossierId);

    /**
     * <p>
     * 三方案件类型是否存在
     * </p>
     *
     * @author xiaoxiao
     * @version 1.0
     * @since 2019/8/26 16:16
     **/
    EdCaseType findCodeByThirdCode(String thirdCode);

    /**
     * 通过caseType查找案件类型名称
     * @param caseType
     * @return
     */
    String findCaseNameByCode(String caseType);

    List<String> findThirdCaseType();
    
    List<EdCaseType>  getAllCaseType();
    
    
    EdCaseType  getCaseTypeByName(String name);

    EdCaseType findByCode(String code);
}
