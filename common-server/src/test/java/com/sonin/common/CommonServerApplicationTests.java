package com.sonin.common;

import com.sonin.common.modules.demo.entity.DemoD;
import javassist.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        String fieldType = String.class.getName();
        String fieldName = "testField";
        CtField ctField = new CtField(classPool.get(fieldType), fieldName, ctClass);
        ctField.setModifiers(Modifier.PRIVATE);
        ctClass.addField(ctField);
        Loader classLoader = new Loader(classPool);
        Class newClazz = classLoader.loadClass(ctClass.getName());
        System.out.println("");
    }

}
