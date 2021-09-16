package com.sonin.ssmframework.spring.reader;

import com.sonin.ssmframework.spring.entity.BeanDefinition;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author sonin
 * @date 2021/9/16 8:50
 */
public class BeanDefinitionReader {

    private Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    private List<String> registeredBeanDefinitionsClassName = new ArrayList<>();


    public BeanDefinitionReader(String... locations) {

        //定位
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:", ""));
        //加载
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //注册 (获取要扫描的路径 strPackage = com.sn.springlean.userapi)
        String strPackage = properties.get("scanPackage").toString();
        // List<String> registeredBeanDefinitionsClassName记录下配置文件中Bean包下所有的类(全路径名称)
        doScannerBeanClassNames(strPackage);

    }

    private static String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public BeanDefinition doRegister(String beanClassName) {
        if (this.registeredBeanDefinitionsClassName.contains(beanClassName)) {
            return new BeanDefinition(beanClassName, lowerFirstCase(beanClassName.substring(beanClassName.lastIndexOf(".") + 1)));
        }
        return null;
    }

    /**
     * 扫描指定包下所有的类
     *
     * @param packageName
     */
    private void doScannerBeanClassNames(String packageName) {

        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        assert url != null;
        File classDir = new File(url.getFile());
        for (File fileT : Objects.requireNonNull(classDir.listFiles())) {
            if (fileT.isDirectory()) {
                doScannerBeanClassNames(packageName + "." + fileT.getName());
            } else {
                registeredBeanDefinitionsClassName.add(packageName + "." + fileT.getName().replace(".class", ""));
            }
        }
    }

    public List<String> getRegisteredBeanDefinitionsClassName() {
        return registeredBeanDefinitionsClassName;
    }

}
