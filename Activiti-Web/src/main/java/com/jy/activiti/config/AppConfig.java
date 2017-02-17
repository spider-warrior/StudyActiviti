package com.jy.activiti.config;

import com.jy.activiti.interceptor.LoginInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(
        basePackages = "com.jy.activiti"
)
@PropertySource({"classpath:props/db.properties",
        "classpath:props/hibernate.properties"})
public class AppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private LoginInterceptor loginInterceptor;

    /* 数据模型package */
    private static String[] MODEL_PACKAGES = {
            "com.jy.activiti.entity"
    };
    /**
     * 静态文件支持
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor);
    }

    /**
     * 为文件上传配置CommonsMultiPartResolver
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(2097152); //上传文件为20MB
        return resolver;
    }

    /**
     * 数据源
     */
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(environment.getProperty("db.driver.class"));
        dataSource.setJdbcUrl(environment.getProperty("db.url"));
        dataSource.setUser(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));
        dataSource.setInitialPoolSize(environment.getProperty("db.initialPoolSize", Integer.class));
        dataSource.setMinPoolSize(environment.getProperty("db.minPoolSize", Integer.class));
        dataSource.setMaxPoolSize(environment.getProperty("db.maxPoolSize", Integer.class));
        dataSource.setMaxIdleTime(environment.getProperty("db.maxIdleTime", Integer.class));
        dataSource.setMaxConnectionAge(environment.getProperty("db.maxConnectionAge", Integer.class));
        dataSource.setIdleConnectionTestPeriod(environment.getProperty("db.idleConnectionTestPeriod", Integer.class));
        return dataSource;
    }

    /**
     * 数据连接会话
     */

    @Bean
    @Autowired
    public SessionFactory sessionFactory(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan(MODEL_PACKAGES);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

    /**
     * Hibernate ORM 配置
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    /**
     * 事物管理器
     */
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    @Autowired
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource, HibernateTransactionManager transactionManager) {
        SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
        springProcessEngineConfiguration.setDataSource(dataSource);
        springProcessEngineConfiguration.setTransactionManager(transactionManager);
        springProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
        springProcessEngineConfiguration.setJobExecutorActivate(Boolean.FALSE);
        springProcessEngineConfiguration.setDeploymentResources(new Resource[]{new ClassPathResource("bpmn/hello.bpmn20.xml")});
        return springProcessEngineConfiguration;
    }

    @Bean
    @Autowired
    public ProcessEngineFactoryBean processEngineFactoryBean(SpringProcessEngineConfiguration processEngineConfiguration) {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration);
        return processEngineFactoryBean;
    }

    @Bean
    public RepositoryService repositoryService() {
        return processEngine.getRepositoryService();
    }
    @Bean
    public RuntimeService RuntimeService() {
        return processEngine.getRuntimeService();
    }
    @Bean
    public TaskService taskService() {
        return processEngine.getTaskService();
    }
    @Bean
    public HistoryService historyService() {
        return processEngine.getHistoryService();
    }
    @Bean
    public ManagementService managementService() {
        return processEngine.getManagementService();
    }
    @Bean
    public IdentityService identityService() {
        return processEngine.getIdentityService();
    }

}
