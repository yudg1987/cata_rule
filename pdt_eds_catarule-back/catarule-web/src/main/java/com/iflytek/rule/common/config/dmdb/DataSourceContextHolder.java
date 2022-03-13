package com.iflytek.rule.common.config.dmdb;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by mhwang on 2018/11/14.
 */
public final class DataSourceContextHolder {
    public static final String DEFAULT_DS="db1";
    @SuppressWarnings("unchecked")
    private static final ThreadLocal<LinkedBlockingDeque<String>> LOOKUP_KEY_HOLDER = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new LinkedBlockingDeque();
        }
    };

    private DataSourceContextHolder() {
    }

    /**
     * 获得当前线程数据源
     *
     * @return 数据源名称
     */
    public static String getDataSourceLookupKey() {
        LinkedBlockingDeque<String> deque = LOOKUP_KEY_HOLDER.get();
        return deque.isEmpty() ? null : deque.pollFirst();
    }

    /**
     * 设置当前线程数据源
     */
    public static void setDataSourceLookupKey(String dataSourceLookupKey) {
        LOOKUP_KEY_HOLDER.get().addFirst(dataSourceLookupKey);
    }

    /**
     * 清空当前线程数据源
     * <p>
     * 如果当前线程是连续切换数据源
     * 只会移除掉当前线程的数据源名称
     * </p>
     */
    public static void clearDataSourceLookupKey() {
        LinkedBlockingDeque<String> deque = LOOKUP_KEY_HOLDER.get();
        if (deque.isEmpty()) {
            LOOKUP_KEY_HOLDER.remove();
        }
    }
}
