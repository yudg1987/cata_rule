package com.iflytek.rule.common.config.dmdb;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by mhwang on 2018/11/14.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceLookupKey();
    }

}
