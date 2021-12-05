package com.sonin.common.tool.javassist;

import javassist.*;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sonin
 * @date 2021/12/5 18:44
 */
public class Javassist {

    private static final Map<String, Class> cache = new ConcurrentHashMap<>();

    private String className;

    private String similarClassName;

    private Map<Class, String> fieldMap;

    Javassist() {
        fieldMap = new LinkedHashMap<>();
    }

    /**
     * 待创建的类
     *
     * @param className
     * @return
     */
    public Javassist className(String className) {
        this.className = className;
        return this;
    }

    /**
     * 新创建的类class继承extentClassName
     *
     * @param similarClassName
     * @return
     */
    public Javassist similarClassName(String similarClassName) {
        this.similarClassName = similarClassName;
        return this;
    }

    /**
     * 添加字段(会生成get/set方法)
     *
     * @param fieldType
     * @param fieldName
     * @return
     */
    public Javassist field(Class fieldType, String fieldName) {
        fieldMap.put(fieldType, fieldName);
        return this;
    }

    public Class buildClass() throws Exception {
        if (!cache.containsKey(className)) {
            synchronized (cache) {
                if (!cache.containsKey(className)) {
                    // 创建ClassPool
                    ClassPool classPool = ClassPool.getDefault();
                    // 生成类的名称
                    CtClass ctClass = classPool.makeClass(className);
                    for (Map.Entry<Class, String> item : fieldMap.entrySet()) {
                        initField(classPool, ctClass, item.getKey().getName(), item.getValue());
                    }
                    // 创建构造方法，指定了构造方法的参数类型和构造方法所属的类
                    CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
                    // 设置方法体
                    ctConstructor.setBody("{\n}");
                    ctClass.addConstructor(ctConstructor);
                    // 缓存生成的类
                    cache.put(className, ctClass.toClass());
                }
            }
        }
        return cache.get(className);
    }

    public Class similarClass() throws Exception {
        if (!cache.containsKey(similarClassName)) {
            this.buildClass();
        } else {
            if (!cache.containsKey(className)) {
                synchronized (cache) {
                    if (!cache.containsKey(className)) {
                        // 创建ClassPool
                        ClassPool classPool = ClassPool.getDefault();
                        // 生成类的名称
                        CtClass ctClass = classPool.makeClass(className);
                        Class extentClass = cache.get(similarClassName);
                        for (Field field : extentClass.getDeclaredFields()) {
                            initField(classPool, ctClass, field.getType().getName(), field.getName());
                        }
                        // 创建构造方法，指定了构造方法的参数类型和构造方法所属的类
                        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
                        // 设置方法体
                        ctConstructor.setBody("{\n}");
                        ctClass.addConstructor(ctConstructor);
                        // 缓存生成的类
                        cache.put(className, ctClass.toClass());
                    }
                }
            }
        }
        return cache.get(className);
    }

    private void initField(ClassPool classPool, CtClass ctClass, String fieldType, String fieldName) throws Exception {
        // 创建字段，指定了字段类型、字段名称、字段所属的类
        CtField ctField = new CtField(classPool.get(fieldType), fieldName, ctClass);
        // 指定该字段使用private修饰
        ctField.setModifiers(Modifier.PRIVATE);
        // 将字段添加到ctClass中
        ctClass.addField(ctField);
        // 设置ctClass字段的getter/setter方法
        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        ctClass.addMethod(CtNewMethod.getter(getMethodName, ctField));
        ctClass.addMethod(CtNewMethod.setter(setMethodName, ctField));
    }

}
