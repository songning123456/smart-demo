package com.sonin.common.tool.util;

import com.sonin.common.tool.annotation.JoinSqlAnno;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/17 19:38
 */
public class JoinSqlUtils {

    /**
     * 设置关联表的primary key与foreign key
     *
     * @param object
     * @throws Exception
     */
    public static void setJoinSqlIdFunc(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<Class<?>, Object> class2ObjMap = new HashMap<>();
        for (int i = fields.length - 1; i >= 0; i--) {
            Field field = fields[i];
            JoinSqlAnno joinSqlAnno = field.getAnnotation(JoinSqlAnno.class);
            if (joinSqlAnno == null) {
                continue;
            }
            field.setAccessible(true);
            if (field.get(object) == null) {
                field.set(object, field.getType().newInstance());
            }
            // 设置唯一主键
            String uuid = UniqIdUtils.getInstance().getUniqID();
            Object srcObject = field.get(object);
            Field srcField = srcObject.getClass().getDeclaredField(joinSqlAnno.primaryKey());
            srcField.setAccessible(true);
            srcField.set(srcObject, uuid);
            srcField.setAccessible(false);
            Object targetObject = class2ObjMap.get(joinSqlAnno.targetClass());
            if (targetObject != null) {
                Field targetField = targetObject.getClass().getDeclaredField(joinSqlAnno.foreignKey());
                targetField.setAccessible(true);
                targetField.set(targetObject, uuid);
                targetField.setAccessible(false);
            }
            class2ObjMap.put(field.getType(), srcObject);
            field.setAccessible(false);
        }
        class2ObjMap.clear();
    }

    /**
     * 校验sql的 primary key ?= foreign key
     *
     * @param object
     */
    public static void checkSqlIdFunc(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<Class<?>, Object> class2ObjMap = new HashMap<>();
        for (int i = fields.length - 1; i >= 0; i--) {
            Field field = fields[i];
            JoinSqlAnno joinSqlAnno = field.getAnnotation(JoinSqlAnno.class);
            if (joinSqlAnno == null) {
                continue;
            }
            field.setAccessible(true);
            if (field.get(object) == null) {
                class2ObjMap.clear();
                field.setAccessible(false);
                throw new Exception(field.getName() + " NULL POINT EXCEPTION");
            }
            Object srcObject = field.get(object);
            Field srcField = srcObject.getClass().getDeclaredField(joinSqlAnno.primaryKey());
            srcField.setAccessible(true);
            if (srcField.get(srcObject) == null) {
                class2ObjMap.clear();
                srcField.setAccessible(false);
                field.setAccessible(false);
                throw new Exception(field.getName() + " Primary Key NULL POINT EXCEPTION");
            }
            Object targetObject = class2ObjMap.get(joinSqlAnno.targetClass());
            if (targetObject != null) {
                Field targetField = targetObject.getClass().getDeclaredField(joinSqlAnno.foreignKey());
                targetField.setAccessible(true);
                if (targetField.get(targetObject) == null) {
                    class2ObjMap.clear();
                    srcField.setAccessible(false);
                    targetField.setAccessible(false);
                    field.setAccessible(false);
                    throw new Exception(joinSqlAnno.targetClass().getName() + " NULL POINT EXCEPTION");
                }
                if (!srcField.get(srcObject).equals(targetField.get(targetObject))) {
                    class2ObjMap.clear();
                    srcField.setAccessible(false);
                    targetField.setAccessible(false);
                    field.setAccessible(false);
                    throw new Exception(srcObject.getClass().getSimpleName() + " Primary Key != " + targetObject.getClass().getSimpleName() + " Foreign Key");
                }
                targetField.setAccessible(false);
            }
            class2ObjMap.put(field.getType(), srcObject);
            srcField.setAccessible(false);
            field.setAccessible(false);
        }
        class2ObjMap.clear();
    }

    /**
     * 拼接inner join
     *
     * @param object
     * @return
     */
    public static String joinSqlQuery(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            throw new Exception("请输入查询对象");
        }
        String sql = "";
        String aliasObj = "";
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (i == 0) {
                field.setAccessible(true);
            } else {

            }
        }
        return "";
    }

}
