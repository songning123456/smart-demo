package com.sonin.ssmframework.spring.entity;

/**
 * @author sonin
 * @date 2021/9/16 8:22
 * 保存XML中Bean被解析后的基本信息
 */
public class BeanDefinition {

    /**
     * 全路径类名
     */
    private String beanClassName;
    /**
     * 首字母小写的被扫描的(简写)类
     */
    private String factoryBeanName;
    private Class<?> beanClass;

    public BeanDefinition(String beanClassName, String factoryBeanName) {
        this.beanClassName = beanClassName;
        this.factoryBeanName = factoryBeanName;

        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public Object getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

}
