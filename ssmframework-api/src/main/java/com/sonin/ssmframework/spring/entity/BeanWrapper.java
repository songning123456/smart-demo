package com.sonin.ssmframework.spring.entity;

/**
 * @author sonin
 * @date 2021/9/16 8:27
 * 包装实例化的bean以及被代理的bean
 */
public class BeanWrapper {

    private Object originalBean;
    private Object wrappedBean;

    public BeanWrapper(Object originalBean) {
        this.originalBean = originalBean;
        this.wrappedBean = originalBean;
    }

    public Object getOriginalBean() {
        return originalBean;
    }

    public void setOriginalBean(Object originalBean) {
        this.originalBean = originalBean;
    }

    public Object getWrappedBean() {
        return wrappedBean;
    }

    public void setWrappedBean(Object wrappedBean) {
        this.wrappedBean = wrappedBean;
    }
}
