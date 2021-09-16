package com.sonin.ssmframework.spring.factory.impl;

import com.sonin.ssmframework.spring.annotation.*;
import com.sonin.ssmframework.spring.entity.BeanDefinition;
import com.sonin.ssmframework.spring.entity.BeanWrapper;
import com.sonin.ssmframework.spring.factory.BeanFactory;
import com.sonin.ssmframework.spring.reader.BeanDefinitionReader;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author sonin
 * @date 2021/9/16 8:47
 * 模拟XmlWebApplicationContext, 用来存储web程序的上下文
 */
public class WebApplicationContext extends DefaultListableBeanFactory implements BeanFactory {

    private String[] configLocations;
    private BeanDefinitionReader reader;

    /**
     * 用来保证注册式单例的容器s
     * key 为classname
     */
    private Map<String, Object> beanCacheMap = new HashMap<>();

    public WebApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    private void refresh() {
        //定位
        if (reader == null) {
            reader = new BeanDefinitionReader(configLocations[0]);
        }
        //加载
        List<String> beanDefinitionClassNames = reader.getRegisteredBeanDefinitionsClassName();
        //注册
        doRegister(beanDefinitionClassNames);
        //依赖注入
        doAutowired();
    }

    public String[] getBeanDefinitions() {
        return this.beanDefinitionMap.keySet().toArray(new String[0]);
    }

    private void doRegister(List<String> beanDefinitions) {
        try {
            for (String beanClassName : beanDefinitions) {

                Class<?> clazz = Class.forName(beanClassName);
                if (clazz.isInterface()) {
                    continue;
                }

                BeanDefinition beanDefinition = reader.doRegister(beanClassName);
                if (beanDefinition == null) {
                    continue;
                }
                this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
                this.beanDefinitionNames.add(beanDefinition.getFactoryBeanName());

                for (Class<?> i : clazz.getInterfaces()) {
                    this.beanDefinitionMap.put(i.getName(), beanDefinition);
                    this.beanDefinitionNames.add(i.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAutowired() {
        for (Map.Entry<String, BeanDefinition> entry : this.beanDefinitionMap.entrySet()) {
            // getBean 是DI的开始
            getBean(entry.getKey());
        }

        //对属性进行注入
        for (Map.Entry<String, BeanWrapper> entry : this.singleBeanInstanceMap.entrySet()) {
            //getBean 是DI的开始
            populateBean(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 遍历Bean下所有属性, 进行注入
     *
     * @param strBeanName
     * @param instance
     */
    private void populateBean(String strBeanName, BeanWrapper instance) {
        Object originalInstance = instance.getOriginalBean();
        Class<?> clazz = originalInstance.getClass();

        if (!(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class))) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field fd : fields) {
            if (!fd.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            Autowired autowired = fd.getAnnotation(Autowired.class);
            String strAutowireName = autowired.value().trim();
            if ("".equals(strAutowireName)) {
                strAutowireName = fd.getType().getName();
            }
            fd.setAccessible(true);

            try {
                fd.set(originalInstance, this.singleBeanInstanceMap.get(strAutowireName).getWrappedBean());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getBean(String beanName) {

        if (this.singleBeanInstanceMap.containsKey(beanName)) {
            return this.singleBeanInstanceMap.get(beanName);
        }

        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            return null;
        }

        Object beanInstance = initBean(beanDefinition);
        BeanWrapper beanWrapper = new BeanWrapper(beanInstance);
        this.singleBeanInstanceMap.put(beanName, beanWrapper);
        return beanWrapper;
    }

    private Object initBean(BeanDefinition beanDefinition) {
        Object instance;
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            if (beanCacheMap.containsKey(beanClassName)) {
                return beanCacheMap.get(beanClassName);
            }
            Class<?> clazz = (Class<?>) beanDefinition.getBeanClass();
            instance = clazz.newInstance();
            this.beanCacheMap.put(beanClassName, instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Properties getConfig() {
        return this.reader.getProperties();
    }
}
