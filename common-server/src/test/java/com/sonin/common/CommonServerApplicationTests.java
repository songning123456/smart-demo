package com.sonin.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sonin.common.entity.EquipmentRepairAndChild;
import com.sonin.common.modules.common.mapper.CommonSqlMapper;
import com.sonin.common.modules.demo.entity.DemoA;
import com.sonin.common.modules.demo.entity.DemoB;
import com.sonin.common.modules.demo.entity.DemoC;
import com.sonin.common.modules.demo.entity.DemoD;
import com.sonin.common.tool.javassist.Javassist;
import com.sonin.common.tool.javassist.JavassistFactory;
import com.sonin.common.tool.pool.CustomThreadPool;
import com.sonin.common.tool.util.JoinSqlUtils;
import javassist.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

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

    @Test
    public void createClass() throws Exception {
        // 创建ClassPool
        ClassPool classPool = ClassPool.getDefault();
        // 生成类的名称JavassistTest
        CtClass ctClass = classPool.makeClass("JavassistTest");
        // 创建字段，指定了字段类型、字段名称、字段所属的类
        CtField ctField = new CtField(classPool.get("java.lang.String"), "prop", ctClass);
        // 指定该字段使用private修饰
        ctField.setModifiers(Modifier.PRIVATE);
        // 将prop字段添加到clazz中
        ctClass.addField(ctField);
        // 设置prop字段的getter/setter方法
        ctClass.addMethod(CtNewMethod.getter("getProp", ctField));
        ctClass.addMethod(CtNewMethod.setter("setProp", ctField));
        // 创建构造方法，指定了构造方法的参数类型和构造方法所属的类
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
        // 设置方法体
        ctConstructor.setBody("{\n}");
        ctClass.addConstructor(ctConstructor);
        // 加载clazz类
        Class<?> targetClass = ctClass.toClass();
    }

    @Test
    public void test() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new CustomThreadPool.Builder().corePoolSize(10).queueCapacity(10000).build();
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    Class testClass = JavassistFactory.create()
                            .className("Test")
                            .field(String.class, "test")
                            .buildClass();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();
        threadPoolExecutor.shutdown();
        System.out.println();
    }

    @Test
    public void test12() throws Exception {
        Class class1 = JavassistFactory.create()
                .className("Test1")
                .field(String.class, "prop")
                .buildClass();
        Class class2 = JavassistFactory.create()
                .className("Test2")
                .similarClassName(class1.getSimpleName())
                .similarClass();
        System.out.println();
    }

}
