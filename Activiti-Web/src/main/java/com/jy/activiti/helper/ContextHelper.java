package com.jy.activiti.helper;

import org.activiti.engine.identity.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextHelper implements ApplicationContextAware {

    private static ApplicationContext context;

    private final ThreadLocal<User> requestThreadUser = new ThreadLocal<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String id) {
        return context.getBean(id);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }


    /**
     * 设置当前线程User
     */
    public void setCurrentUser(User user) {
        if (user == null) {
            return;
        }
        requestThreadUser.set(user);
    }

    /**
     * 获取当前线程User
     */
    public User getCurrentUser() {
        return requestThreadUser.get();
    }

    /**
     * 去除当前线程User
     */
    public void removeCurrentUser() {
        requestThreadUser.remove();
    }


}
