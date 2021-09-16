package com.sonin.ssmframework.springmvc.view;

import com.sonin.ssmframework.springmvc.entity.ModelAndView;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sonin
 * @date 2021/9/16 19:25
 */
public class ViewResolver {

    private String viewName;
    private File templateFile;

    public ViewResolver(String viewName, File templateFile) {
        this.viewName = viewName;
        this.templateFile = templateFile;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public File getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(File templateFile) {
        this.templateFile = templateFile;
    }

    public String processViews(ModelAndView modelAndView) throws Exception {

        RandomAccessFile randomAccessFile = new RandomAccessFile(this.templateFile, "r");
        StringBuilder sb = new StringBuilder();
        String strLine = "";
        while ((strLine = randomAccessFile.readLine()) != null) {
            Matcher m = matchLine(strLine);
            while (m.find()) {
                for (int i = 0; i < m.groupCount(); i++) {
                    String paraName = m.group(i);
                    paraName = paraName.replace("${", "").replace("}", "");
                    Object paraValue = modelAndView.getDataModel().get(paraName);
                    if (paraValue == null) {
                        continue;
                    }
                    strLine = strLine.replaceAll("\\$\\{" + paraName + "\\}", paraValue.toString());
                }

            }
            sb.append(strLine);
        }
        randomAccessFile.close();
        return sb.toString();
    }

    private Matcher matchLine(String strLine) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(strLine);
    }

}
