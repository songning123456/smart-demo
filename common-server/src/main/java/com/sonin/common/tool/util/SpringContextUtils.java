//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sonin.common.tool.util;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Lazy(false)
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public SpringContextUtils() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin() {
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader("Origin");
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static void setJdbcTemplateBean(String beanName, Class clazz, DataSource dataSource) {
        ConfigurableApplicationContext context = (ConfigurableApplicationContext)getApplicationContext();
        DefaultListableBeanFactory beanDefReg = (DefaultListableBeanFactory)context.getBeanFactory();
        BeanDefinitionBuilder beanDefBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        beanDefBuilder.addPropertyValue("dataSource", dataSource);
        beanDefBuilder.setScope("singleton");
        BeanDefinition beanDef = beanDefBuilder.getBeanDefinition();
        if (!beanDefReg.containsBeanDefinition(beanName)) {
            beanDefReg.registerBeanDefinition(beanName, beanDef);
        }
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
