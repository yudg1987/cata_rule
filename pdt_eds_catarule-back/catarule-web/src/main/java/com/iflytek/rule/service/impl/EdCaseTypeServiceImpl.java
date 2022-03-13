package com.iflytek.rule.service.impl;


import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.iflytek.rule.common.AbstractService;
import com.iflytek.rule.entity.EdCaseType;
import com.iflytek.rule.mapper.EdCaseTypeMapper;
import com.iflytek.rule.service.EdCaseTypeService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

//import org.springframework.cloud.context.config.annotation.RefreshScope;


/**
 * Created by CodeGenerator on 2018/04/24.
 */
@Service
@Transactional
//@RefreshScope
public class EdCaseTypeServiceImpl extends AbstractService<EdCaseType> implements EdCaseTypeService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EdCaseTypeMapper edCaseTypeMapper;

    @Override
    public List<EdCaseType> findInfoAll(Map<String, Object> param) {
        // TODO Auto-generated method stub
        return edCaseTypeMapper.findInfoAll(param);
    }

    @Override
    public String findCodeByDossierId(String dossierId) {
        return edCaseTypeMapper.findCodeByDossierId(dossierId);
    }


    @Override
    public EdCaseType findCodeByThirdCode(String thirdCode) {
        Example example = new Example(EdCaseType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("thirdCode", thirdCode);
        List<EdCaseType> list = this.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public String findCaseNameByCode(String caseType) {
        String caseName = "";
        Example example = new Example(EdCaseType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", caseType);
        List<EdCaseType> list = this.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            caseName = list.get(0).getName();
        }
        return caseName;
    }

    @Override
    public List<String> findThirdCaseType() {
        return edCaseTypeMapper.findThirdCaseType();
    }

    @Override
    public List<EdCaseType> getAllCaseType() {
        Example example = new Example(EdCaseType.class);
        // 注意：排序使用的是列名
        example.setOrderByClause("id");
        return this.edCaseTypeMapper.selectByExample(example);
    }

    @Override
    public EdCaseType getCaseTypeByName(String name) {
        Example example = new Example(EdCaseType.class);
        example.createCriteria().andEqualTo("name", name);
        // 注意：排序使用的是列名
        example.setOrderByClause("id");
        List<EdCaseType> list = this.edCaseTypeMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public EdCaseType findByCode(String code) {
        Example example = new Example(EdCaseType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        List<EdCaseType> list = this.selectByExample(example);
        int size = CollectionUtils.size(list);
        if (size == 1) {
            return list.get(0);
        }
        else if (size > 1) {
            logger.warn("案件类型code重复, data: {}", JSON.toJSONString(list));
            return list.get(0);
        }
        return null;
    }

}
