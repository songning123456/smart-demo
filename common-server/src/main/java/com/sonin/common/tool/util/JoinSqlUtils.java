package com.sonin.common.tool.util;

import com.google.common.base.CaseFormat;
import com.sonin.common.tool.annotation.JoinSqlAnno;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
        Field[] fields = object.getClass().getDeclaredFields();
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
        Field[] fields = object.getClass().getDeclaredFields();
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
        Field[] fields = object.getClass().getDeclaredFields();
        if (fields.length == 0) {
            throw new Exception("Object NULL POINT EXCEPTION");
        }
        Map<Class, String> target2ForeignKeyMap = new HashMap<>(2);
        Map<Class, String> target2PrimaryKeyMap = new HashMap<>(2);
        String sql = "";
        StringBuilder aliasStrBuilder = new StringBuilder();
        String alias = "";
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            JoinSqlAnno joinSqlAnno = field.getAnnotation(JoinSqlAnno.class);
            if (joinSqlAnno == null) {
                continue;
            }
            field.setAccessible(true);
            Object subObj = field.get(object);
            field.setAccessible(false);
            if (subObj == null) {
                throw new Exception(field.getName() + " NULL POINT EXCEPTION");
            }
            target2ForeignKeyMap.put(joinSqlAnno.targetClass(), CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, joinSqlAnno.foreignKey()));
            target2PrimaryKeyMap.put(joinSqlAnno.targetClass(), field.getType().getSimpleName() + "_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, joinSqlAnno.primaryKey()));
            String className = subObj.getClass().getSimpleName();
            String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
            alias = aliasStrBuilder.toString().replaceFirst("_", "");
            if (i == 0) {
                sql = "select " + getColumns(subObj) + " from " + tableName + " as " + className;
            } else {
                sql = "select " + alias + ".*, " + getColumns(subObj) + " from " + tableName + " as " + className + " inner join (" + sql + ") as " + alias + " on " + className + "." + target2ForeignKeyMap.get(field.getType()) + " = " + alias + "." + target2PrimaryKeyMap.get(field.getType());
            }
            aliasStrBuilder.append("_").append(className);
        }
        alias = aliasStrBuilder.toString().replaceFirst("_", "");
        sql = "select " + alias + ".* from (" + sql + ") as " + alias + " where 1 = 1";
        target2ForeignKeyMap.clear();
        target2PrimaryKeyMap.clear();
        return sql;
    }

    /**
     * 获取
     * select DemoA.id as DemoA_id, DemoA.a_name as DemoA_AName from demo_a as DemoA
     * e.g: DemoA.id as DemoA_id, DemoA.a_name as DemoA_aName
     *
     * @param subObj
     * @return
     */
    private static String getColumns(Object subObj) {
        String className = subObj.getClass().getSimpleName();
        Field[] fields = subObj.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : fields) {
            // 匹配是否为静态常量,剔除实体类中serialVersionUID字段
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String fieldName = field.getName();
            String columnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
            stringBuilder.append(", ").append(className).append(".").append(columnName).append(" as ").append(className).append("_").append(fieldName);
        }
        return stringBuilder.toString().replaceFirst(", ", "");
    }

}
