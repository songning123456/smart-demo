package com.sonin.ssmframework.springmvc.entity;

import java.util.Map;

/**
 * @author sonin
 * @date 2021/9/16 8:59
 */
public class ModelAndView {

    private String viewName;
    private Map<String, ?> dataModel;

    public ModelAndView(String viewName, Map<String, ?> dataModel) {
        this.viewName = viewName;
        this.dataModel = dataModel;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getDataModel() {
        return dataModel;
    }

    public void setDataModel(Map<String, ?> dataModel) {
        this.dataModel = dataModel;
    }
}
