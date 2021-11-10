package com.sonin.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sonin.common.entity.EquipmentRepairAndChild;
import com.sonin.common.modules.common.mapper.CommonSqlMapper;
import com.sonin.common.modules.demo.entity.DemoA;
import com.sonin.common.modules.demo.entity.DemoD;
import com.sonin.common.tool.util.JoinSqlUtils;
import javassist.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author sonin
 * @date 2021/10/2 12:18
 */
@SpringBootTest
public class CommonServerApplicationTests {

    @Autowired
    private CommonSqlMapper commonSqlMapper;

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

    @Test
    public void testSqlInject() throws Exception {
        String sql = "select * from demo_a where id = '111'";
        JoinSqlUtils.checkSqlInject(sql);
        System.out.println("");
    }

    @Test
    public void testJoinSql() throws Exception {
        String sql = JoinSqlUtils.multiJoinSqlQuery(new EquipmentRepairAndChild());
        System.out.println(sql);
    }

    @Test
    public void testDeleteWrapper() {
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", "111");
        int res = commonSqlMapper.deleteWrapper("delete from demo_a", queryWrapper);
        System.out.println("");
    }

}
