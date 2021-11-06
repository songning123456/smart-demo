package com.sonin.common;

import com.sonin.common.modules.demo.entity.DemoD;
import javassist.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

/**
 * @author sonin
 * @date 2021/10/2 12:18
 */
@SpringBootTest
public class CommonServerApplicationTests {

    @Test
    public void createField() throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get(DemoD.class.getName());
        CtClass fieldType = classPool.get(String.class.getName());
        // 创建field字段
        CtField ctField = new CtField(fieldType, "fieldName", ctClass);
        ctField.setModifiers(Modifier.PRIVATE);
        ctClass.addField(ctField);
        // 创建set方法
        CtMethod setCtMethod = new CtMethod(CtClass.voidType, "setFieldName", new CtClass[]{fieldType}, ctClass);
        setCtMethod.setModifiers(Modifier.PUBLIC);
        setCtMethod.setBody("{this.fieldName = $1;}");
        ctClass.addMethod(setCtMethod);
        // 创建get方法
        CtMethod getCtMethod = new CtMethod(fieldType, "getFieldName", new CtClass[]{}, ctClass);
        getCtMethod.setModifiers(Modifier.PUBLIC);
        getCtMethod.setBody("{return this.fieldName;}");
        ctClass.addMethod(getCtMethod);
        Loader classLoader = new Loader(classPool);
        Class NewDemoD = classLoader.loadClass(ctClass.getName());
        // 测试结果
        Object newDemoD = NewDemoD.newInstance();
        Method setMethod = newDemoD.getClass().getDeclaredMethod("setFieldName", String.class);
        setMethod.invoke(newDemoD, "123");
        Method getMethod = newDemoD.getClass().getDeclaredMethod("getFieldName");
        System.out.println(getMethod.invoke(newDemoD));
    }

}
