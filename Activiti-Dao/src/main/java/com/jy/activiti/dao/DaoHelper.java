package com.jy.activiti.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

public interface DaoHelper {
    /**
     * 获取当前Session
     */
    Session getCurrentSession();

    /**
     * 设置HQL参数
     */
    Query setHqlParam(Query query, String key, Object value);

    /**
     * flush session
     */
    void flushCurrentSession();
}
