package com.jy.activiti.service;

public interface BaseService<Entity> {

    /**
     * save
     */
    void save(Entity entity);

    /**
     * update
     */
    void update(Entity entity);

    /**
     * delte by ID
     */
    void deleteById(Class<Entity> clazz, Long id);

    /**
     * query by ID
     */
    Entity queryById(Class<Entity> clazz, Long id);
}
