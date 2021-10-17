package com.sonin.common.config.yaml;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.util.StrUtil;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 读取application.yml件
 *
 * @author lixingwu
 */
public class YamlPropUtils {

    private LinkedHashMap prop;
    private static YamlPropUtils yamlPropUtils = new YamlPropUtils();

    /**
     * 私有构造，禁止直接创建
     */
    private YamlPropUtils() {
        BootYaml yaml = new BootYaml();
        yaml.setActive("spring.profiles.active");
        yaml.setInclude("spring.profiles.include");
        yaml.setPrefix("application");
        prop = yaml.loadAs("application.yml");
    }

    /**
     * 获取单例
     *
     * @return YmlPropUtils
     */
    public static YamlPropUtils getInstance() {
        if (yamlPropUtils == null) {
            yamlPropUtils = new YamlPropUtils();
        }
        return yamlPropUtils;
    }

    /**
     * 根据属性名读取值
     * 先去主配置查询，如果查询不到，就去启用配置查询
     *
     * @param name 名称
     */
    public Object getProperty(String name) {
        LinkedHashMap param = prop;
        List<String> split = StrSpliter.split(name, StrUtil.C_DOT, true, true);
        for (int i = 0; i < split.size(); i++) {
            if (i == split.size() - 1) {
                return param.get(split.get(i));
            }
            param = Convert.convert(LinkedHashMap.class, param.get(split.get(i)));
        }
        return null;
    }

    /**
     * 测试demo
     *
     * @param args
     */
    public static void main(String[] args) {
        Object property = YamlPropUtils.getInstance().getProperty("generate-code.port");
        System.out.println(property);
    }

}

