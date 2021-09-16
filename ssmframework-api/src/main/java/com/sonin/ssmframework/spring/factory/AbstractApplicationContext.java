package com.sonin.ssmframework.spring.factory;

/**
 * @author sonin
 * @date 2021/9/16 8:36
 */
public abstract class AbstractApplicationContext {

    protected void onRefresh() {
    }

    protected abstract void refreshBeanFactory();

}
