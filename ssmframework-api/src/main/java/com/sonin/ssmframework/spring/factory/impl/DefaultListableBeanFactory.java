package com.sonin.ssmframework.spring.factory.impl;

import com.sonin.ssmframework.spring.entity.BeanDefinition;
import com.sonin.ssmframework.spring.entity.BeanWrapper;
import com.sonin.ssmframework.spring.factory.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sonin
 * @date 2021/9/16 8:37
 * 用来模拟DefaultListableBeanFactory, 这就是IOC容器
 */
public class DefaultListableBeanFactory extends AbstractApplicationContext {

    /**
     * 用来存储BeanDefinition的定义
     */
    Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    /**
     * 用来存储实例化的单例Bean对象
     */
    Map<String, BeanWrapper> singleBeanInstanceMap = new ConcurrentHashMap<>();

    List<String> beanDefinitionNames = new ArrayList<>();

    @Override
    protected void refreshBeanFactory() {

    }

}
