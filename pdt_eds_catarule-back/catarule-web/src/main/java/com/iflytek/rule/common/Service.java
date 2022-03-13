package com.iflytek.rule.common;

import java.io.Serializable;
import org.apache.ibatis.exceptions.TooManyResultsException;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 * 
 * @author lli
 *
 * @version 1.0
 * 
 * @param <T>
 */
public interface Service<T> {
    /**
     * 
     * @description 单个保存
     * @author lli
     * @create 2017年10月24日下午5:54:56
     * @version 1.0
     * @param model
     */
    void save(T model);

    /**
     * 
     * @description 批量保存
     * @author lli
     * @create 2017年10月24日下午5:55:24
     * @version 1.0
     * @param models
     */
    void save(List<T> models);

    /**
     * 
     * @description 通过主鍵刪除
     * @author lli
     * @create 2017年10月24日下午5:56:16
     * @version 1.0
     * @param id
     */
    void deleteById(Serializable id);

    /**
     * 
     * @description 批量刪除 eg：ids -> “1,2,3,4”
     * @author lli
     * @create 2017年10月24日下午5:56:29
     * @version 1.0
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 
     * @description 根据主键更新属性不为null的值
     * @author lli
     * @create 2017年10月24日下午5:56:42
     * @version 1.0
     * @param model
     */
    void update(T model);

    /**
     *
     * @description 根据主键更新属性为null的值
     * @author xiaoxiao
     * @create 2019年05月29日下午10:52:42
     * @version 1.0
     * @param model
     */
    void updateByPrimaryKey(T model);

    /**
     * 
     * @description 通过ID查找
     * @author lli
     * @create 2017年10月24日下午5:56:54
     * @version 1.0
     * @param id
     * @return
     */
    T findById(Serializable id);

    /**
     * 
     * @description 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     * @author lli
     * @create 2017年10月24日下午5:57:07
     * @version 1.0
     * @param fieldName
     * @param value
     * @return
     * @throws TooManyResultsException
     */
    T findBy(String fieldName, Object value) throws TooManyResultsException;

    /**
     * 
     * @description 通过多个ID查找//eg：ids -> “1,2,3,4”
     * @author lli
     * @create 2017年10月24日下午5:57:26
     * @version 1.0
     * @param ids
     * @return
     */
    List<T> findByIds(String ids);

    /**
     * 
     * @description 根据条件查找
     * @author lli
     * @create 2017年10月24日下午5:57:39
     * @version 1.0
     * @param condition
     * @return
     */
    List<T> findByCondition(Condition condition);
    /**
     * 
     * @description 获取所有
     * @author lli
     * @create 2017年10月24日下午5:57:55
     * @version 1.0
     * @return
     */
    List<T> findAll();
    /**
     * 
     * @description 根据条件获取
     * @author lli
     * @create 2017年10月24日下午5:58:05
     * @version 1.0
     * @param example
     * @return
     */
    List<T> selectByExample(Example example);

    /**
     * 根据条件更新
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(T record, Object example);
}
