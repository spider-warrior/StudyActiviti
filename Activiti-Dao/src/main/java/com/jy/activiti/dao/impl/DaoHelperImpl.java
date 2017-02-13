package com.jy.activiti.dao.impl;

import com.jy.activiti.dao.DaoHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Calendar;

@Repository
public class DaoHelperImpl implements DaoHelper {

    private final Logger logger = LogManager.getLogger(DaoHelperImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void flushCurrentSession() {
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public Query setHqlParam(Query query, String key, Object value) {

        if (query != null) {
            if (value == null) {
                query.setParameter(key, value);
            } else if (value instanceof String) {
                query.setParameter(key, value, StringType.INSTANCE);
            } else if (value instanceof Byte) {
                query.setParameter(key, value, ByteType.INSTANCE);
            } else if (value instanceof Short) {
                query.setParameter(key, value, ShortType.INSTANCE);
            } else if (value instanceof Character) {
                query.setParameter(key, value, CharacterType.INSTANCE);
            } else if (value instanceof Integer) {
                query.setParameter(key, value, IntegerType.INSTANCE);
            } else if (value instanceof Float) {
                query.setParameter(key, value, FloatType.INSTANCE);
            } else if (value instanceof Double) {
                query.setParameter(key, value, DoubleType.INSTANCE);
            } else if (value instanceof Long) {
                query.setParameter(key, value, LongType.INSTANCE);
            } else if (value instanceof Boolean) {
                query.setParameter(key, value, BooleanType.INSTANCE);
            } else if (value instanceof java.util.Date) {
                query.setParameter(key, value, DateType.INSTANCE);
            } else if (value instanceof Calendar) {
                query.setParameter(key, value, CalendarType.INSTANCE);
            } else if (value instanceof BigDecimal) {
                query.setParameter(key, value, BigDecimalType.INSTANCE);
            } else {
                logger.warn("meet a unexpected param type: " + value.getClass() + ", value: " + value.toString());
                query.setParameter(key, value);
            }
        }
        return query;
    }
}
