package com.jy.activiti.dao.impl;

import com.jy.activiti.dao.BaseDao;
import com.jy.activiti.dao.DaoHelper;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDaoImpl<Entity> implements BaseDao<Entity> {

    @Autowired
    private DaoHelper daoHelper;

    public void save(Entity entity) {
        if (entity != null) {
            daoHelper.getCurrentSession().save(entity);
        }
    }

    public void update(Entity entity) {
        if (entity != null) {
            daoHelper.getCurrentSession().update(entity);
        }
    }

    public void deleteById(Class<Entity> clazz, Long id) {
        if (id != null) {
            Entity entity = queryById(clazz, id);
            if (entity != null) {
                daoHelper.getCurrentSession().delete(entity);
            }
        }
    }

    public Entity queryById(Class<Entity> clazz, Long id) {
        return daoHelper.getCurrentSession().get(clazz, id);
    }

    public Session getCurrentSession() {
        return daoHelper.getCurrentSession();
    }

    public void flushCurrentSession() {
        daoHelper.flushCurrentSession();
    }

    @Override
    public Query setHqlParam(Query query, String key, Object value) {
        return daoHelper.setHqlParam(query, key, value);
    }
}
