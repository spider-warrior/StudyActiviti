package com.jy.activiti.init;

import com.jy.activiti.config.AppConfig;
import com.jy.activiti.listener.AppStartupListener;
import com.jy.activiti.websocket.ServletWebsocketEndPoint;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.HashSet;
import java.util.Set;

public class AppInit implements WebApplicationInitializer, ServerApplicationConfig {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        /* Open Session In View */
        servletContext.addFilter("hibernateFilter", OpenSessionInViewFilter.class)
                .addMappingForUrlPatterns(null, false, "/*");

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppConfig.class);
        ctx.addApplicationListener(new AppStartupListener());
        servletContext.addListener(new ContextLoaderListener(ctx));

        /* Dispatcher Servlet */
        DispatcherServlet dispatcherServlet = new DispatcherServlet(ctx);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistration.Dynamic servlet = servletContext.addServlet(
                "dispatcherServlet", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("*.html");
        defaultServlet.addMapping("*.ico");

        /* Character Encoding Filter */
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter(
                "characterEncodingFilter", characterEncodingFilter);
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");

    }

    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> scanned) {
        Set<ServerEndpointConfig> result = new HashSet<>();
        result.add(ServerEndpointConfig.Builder.create(
                ServletWebsocketEndPoint.class,
                "/servlet/websocket/{relationId}/{username}").build());
        return result;
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
        // Deploy all WebSocket endpoints defined by annotations in the examples
        // web application. Filter out all others to avoid issues when running
        // tests on Gump
//        Set<Class<?>> results = new HashSet<>();
//        for (Class<?> clazz : scanned) {
//            if (clazz.getPackage().getName().startsWith("websocket.")) {
//                results.add(clazz);
//            }
//        }
//        return results;
        //do nothing
        return scanned;
    }

}