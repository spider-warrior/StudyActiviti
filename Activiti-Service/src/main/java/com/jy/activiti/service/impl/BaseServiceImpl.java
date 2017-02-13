package com.jy.activiti.service.impl;

import com.jy.activiti.dao.impl.BaseDaoImpl;
import com.jy.activiti.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(rollbackFor = Exception.class)
@Service
public class BaseServiceImpl<Entity> implements BaseService<Entity> {

    @Resource
    private BaseDaoImpl<Entity> baseDaoImpl;

    public void save(Entity entity) {
        baseDaoImpl.save(entity);
    }

    public void update(Entity entity) {
        baseDaoImpl.update(entity);
    }

    public void deleteById(Class<Entity> clazz, Long id) {
        baseDaoImpl.deleteById(clazz, id);
    }

    public Entity queryById(Class<Entity> clazz, Long id) {
        return baseDaoImpl.queryById(clazz, id);
    }
}
