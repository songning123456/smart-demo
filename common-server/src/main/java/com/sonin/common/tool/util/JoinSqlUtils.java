package com.sonin.common.tool.util;

import com.sonin.common.tool.annotation.JoinSqlAnno;

import java.lang.reflect.Field;
import java.util.*;

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
            Object srcObject = field.get(object);
            field.setAccessible(false);
            // 设置唯一主键
            String uuid = UniqIdUtils.getInstance().getUniqID();
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
            Object srcObject = field.get(object);
            field.setAccessible(false);
            if (srcObject == null) {
                class2ObjMap.clear();
                throw new Exception(field.getName() + " NULL POINT EXCEPTION");
            }
            Field srcField = srcObject.getClass().getDeclaredField(joinSqlAnno.primaryKey());
            srcField.setAccessible(true);
            Object srcFieldVal = srcField.get(srcObject);
            srcField.setAccessible(false);
            if (srcFieldVal == null) {
                class2ObjMap.clear();
                throw new Exception(field.getName() + " Primary Key NULL POINT EXCEPTION");
            }
            Object targetObject = class2ObjMap.get(joinSqlAnno.targetClass());
            if (targetObject != null) {
                Field targetField = targetObject.getClass().getDeclaredField(joinSqlAnno.foreignKey());
                targetField.setAccessible(true);
                Object targetFieldVal = targetField.get(targetObject);
                targetField.setAccessible(false);
                if (targetFieldVal == null) {
                    class2ObjMap.clear();
                    throw new Exception(joinSqlAnno.targetClass().getName() + " NULL POINT EXCEPTION");
                }
                if (!srcFieldVal.equals(targetFieldVal)) {
                    class2ObjMap.clear();
                    throw new Exception(srcObject.getClass().getSimpleName() + " Primary Key != " + targetObject.getClass().getSimpleName() + " Foreign Key");
                }
            }
            class2ObjMap.put(field.getType(), srcObject);
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
        return "";
    }

    /**
     * 排序
     *
     * @param object
     * @return
     * @throws Exception
     */
    public static List<Object> sortFunc(Object object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        if (fields.length == 0) {
            throw new Exception("PLEASE INPUT OBJECT");
        }
        List<Class> classList = new LinkedList<>();
        Map<Class, Object> subObjMap = new HashMap<>(2);
        for (Field field : fields) {
            JoinSqlAnno joinSqlAnno = field.getAnnotation(JoinSqlAnno.class);
            if (joinSqlAnno == null) {
                continue;
            }
            if (classList.contains(field.getType())) {
                throw new Exception("DUPLICATE SUB OBJECT");
            }
            int index = classList.indexOf(joinSqlAnno.targetClass());
            if (index != -1) {
                classList.add(index, field.getType());
            } else {
                classList.add(field.getType());
            }
            field.setAccessible(true);
            if (field.get(object) == null) {
                field.set(object, field.getType().newInstance());
            }
            Object subObj = field.get(object);
            field.setAccessible(false);
            subObjMap.put(field.getType(), subObj);
        }
        List<Object> subObjList = new ArrayList<>();
        for (Class item : classList) {
            subObjList.add(subObjMap.get(item));
        }
        classList.clear();
        subObjMap.clear();
        return subObjList;
    }
}
