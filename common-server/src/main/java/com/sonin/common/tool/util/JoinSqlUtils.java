package com.sonin.common.tool.util;

import com.google.common.base.CaseFormat;
import com.sonin.common.tool.annotation.JoinSqlAnno;
import com.sonin.common.tool.annotation.JoinSqlQueryAnno;
import com.sonin.common.tool.callback.IBeanConvertCallback;
import com.sonin.common.tool.enums.JoinSqlQueryEnum;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sonin
 * @date 2021/10/17 19:38
 * M multi表示多层对象，S single表示一层对象，T表示任意
 */
public class JoinSqlUtils {

    /**
     * 设置关联表的primary key与foreign key
     *
     * @param object
     * @throws Exception
     */
    public static <M> void setJoinSqlIdFunc(M object) throws Exception {
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
            Object srcObj = field.get(object);
            field.setAccessible(false);
            // 设置唯一主键
            String uuid = UniqIdUtils.getInstance().getUniqID();
            Field srcField = srcObj.getClass().getDeclaredField(joinSqlAnno.primaryKey());
            srcField.setAccessible(true);
            srcField.set(srcObj, uuid);
            srcField.setAccessible(false);
            Object targetObj = class2ObjMap.get(joinSqlAnno.targetClass());
            if (targetObj != null) {
                Field targetField = targetObj.getClass().getDeclaredField(joinSqlAnno.foreignKey());
                targetField.setAccessible(true);
                targetField.set(targetObj, uuid);
                targetField.setAccessible(false);
            }
            class2ObjMap.put(field.getType(), srcObj);
        }
        class2ObjMap.clear();
    }

    /**
     * 校验sql的 primary key ?= foreign key
     *
     * @param object
     */
    public static <M> void checkSqlIdFunc(M object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        Map<Class<?>, Object> class2ObjMap = new HashMap<>();
        for (int i = fields.length - 1; i >= 0; i--) {
            Field field = fields[i];
            JoinSqlAnno joinSqlAnno = field.getAnnotation(JoinSqlAnno.class);
            if (joinSqlAnno == null) {
                continue;
            }
            field.setAccessible(true);
            Object srcObj = field.get(object);
            field.setAccessible(false);
            if (srcObj == null) {
                class2ObjMap.clear();
                throw new Exception(field.getName() + " NULL POINT EXCEPTION");
            }
            Field srcField = srcObj.getClass().getDeclaredField(joinSqlAnno.primaryKey());
            srcField.setAccessible(true);
            Object srcFieldVal = srcField.get(srcObj);
            srcField.setAccessible(false);
            if (srcFieldVal == null) {
                class2ObjMap.clear();
                throw new Exception(field.getName() + " Primary Key NULL POINT EXCEPTION");
            }
            Object targetObj = class2ObjMap.get(joinSqlAnno.targetClass());
            if (targetObj != null) {
                Field targetField = targetObj.getClass().getDeclaredField(joinSqlAnno.foreignKey());
                targetField.setAccessible(true);
                Object targetFieldVal = targetField.get(targetObj);
                targetField.setAccessible(false);
                if (targetFieldVal == null) {
                    class2ObjMap.clear();
                    throw new Exception(joinSqlAnno.targetClass().getName() + " NULL POINT EXCEPTION");
                }
                if (!srcFieldVal.equals(targetFieldVal)) {
                    class2ObjMap.clear();
                    throw new Exception(srcObj.getClass().getSimpleName() + " Primary Key != " + targetObj.getClass().getSimpleName() + " Foreign Key");
                }
            }
            class2ObjMap.put(field.getType(), srcObj);
        }
        class2ObjMap.clear();
    }

    /**
     * Object种包含多个subObj，1对1拼接根据条件查询：inner join
     *
     * @param object
     * @return
     */
    public static <M> String multiJoinSqlQuery(M object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        if (fields.length == 0) {
            throw new Exception(object.getClass().getSimpleName() + " NULL POINT EXCEPTION");
        }
        Map<Class, String> target2ForeignKeyMap = new HashMap<>(2);
        Map<Class, String> target2PrimaryKeyMap = new HashMap<>(2);
        String sql = "";
        StringBuilder aliasStrBuilder = new StringBuilder();
        String alias;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            JoinSqlAnno joinSqlAnno = field.getAnnotation(JoinSqlAnno.class);
            if (joinSqlAnno == null) {
                continue;
            }
            target2ForeignKeyMap.put(joinSqlAnno.targetClass(), CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, joinSqlAnno.foreignKey()));
            target2PrimaryKeyMap.put(joinSqlAnno.targetClass(), field.getType().getSimpleName() + "_" + joinSqlAnno.primaryKey());
            String className = field.getType().getSimpleName();
            String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
            alias = aliasStrBuilder.toString().replaceFirst("_", "");
            if (i == 0) {
                sql = "select " + getColumns(field.getType()) + " from " + tableName + " as " + className;
            } else {
                sql = "select " + alias + ".*, " + getColumns(field.getType()) + " from " + tableName + " as " + className + " inner join (" + sql + ") as " + alias + " on " + className + "." + target2ForeignKeyMap.get(field.getType()) + " = " + alias + "." + target2PrimaryKeyMap.get(field.getType());
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
     * 不带 where 1 = 1
     *
     * @param object
     * @param <M>
     * @return
     * @throws Exception
     */
    public static <M> String multiJoinSqlQueryWithoutWhere(M object) throws Exception {
        String joinSql = multiJoinSqlQuery(object);
        joinSql = joinSql.replace(" where 1 = 1", "");
        return joinSql;
    }

    /**
     * Object中包含多个subObj，1对1拼接inner join 并 查询
     *
     * @param object
     * @return
     * @throws Exception
     */
    public static <M> String multiJoinSqlTermQuery(M object) throws Exception {
        String joinSql = multiJoinSqlQuery(object);
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder termSqlStrBuilder = new StringBuilder();
        for (Field field : fields) {
            JoinSqlAnno joinSqlAnno = field.getAnnotation(JoinSqlAnno.class);
            if (joinSqlAnno == null) {
                continue;
            }
            field.setAccessible(true);
            Object subObj = field.get(object);
            if (subObj == null) {
                subObj = field.getType().newInstance();
            }
            field.setAccessible(false);
            Field[] subFields = field.getType().getDeclaredFields();
            for (Field subField : subFields) {
                subField.setAccessible(true);
                Object subFieldVal = subField.get(subObj);
                subField.setAccessible(false);
                JoinSqlQueryAnno joinSqlQueryAnno = subField.getAnnotation(JoinSqlQueryAnno.class);
                if (joinSqlQueryAnno == null || !joinSqlQueryAnno.isUsed() || subFieldVal == null || "".equals(subFieldVal)) {
                    continue;
                }
                checkSqlInject(subFieldVal + "");
                JoinSqlQueryEnum joinSqlQueryEnum = joinSqlQueryAnno.joinSqlQueryEnum();
                String sqlTemplate = joinSqlQueryEnum.getSql();
                sqlTemplate = sqlTemplate.replace("${var0}", subObj.getClass().getSimpleName() + "_" + subField.getName());
                sqlTemplate = sqlTemplate.replace("${val0}", subFieldVal + "");
                termSqlStrBuilder.append(sqlTemplate);
            }
        }
        return joinSql + termSqlStrBuilder.toString();
    }

    /**
     * 多重对象 类型转换 maps => beans
     *
     * @param mapList
     * @param clazz
     * @param <M>
     * @return
     * @throws Exception
     */
    public static <M> List<M> multiMaps2Beans(List<Map<String, Object>> mapList, Class<M> clazz) throws Exception {
        List<M> list = new ArrayList<>();
        Map<String, Object> class2ObjMap = new LinkedHashMap<>();
        for (Map<String, Object> map : mapList) {
            M object = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Object subObj = field.getType().newInstance();
                field.setAccessible(true);
                field.set(object, subObj);
                field.setAccessible(false);
                class2ObjMap.put(field.getType().getSimpleName(), subObj);
            }
            for (Map.Entry<String, Object> item : map.entrySet()) {
                String key = item.getKey();
                Object val = item.getValue();
                String subClassName = key.split("_")[0];
                String subFieldName = key.split("_")[1];
                Object subObj = class2ObjMap.get(subClassName);
                if (subObj == null) {
                    continue;
                }
                Field subField = subObj.getClass().getDeclaredField(subFieldName);
                subField.setAccessible(true);
                subField.set(subObj, val);
                subField.setAccessible(false);
            }
            list.add(object);
        }
        class2ObjMap.clear();
        return list;
    }

    /**
     * 多个Object relation查询: inner join
     *
     * @param object
     * @param <S>
     * @return
     * @throws Exception
     */
    public static <S> String singleJoinSqlQuery(S object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        if (fields.length == 0) {
            throw new Exception(object.getClass().getSimpleName() + " NULL POINT EXCEPTION");
        }
        String className = object.getClass().getSimpleName();
        String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
        String sql = "select " + getColumns(object.getClass()) + " from " + tableName + " as " + className;
        StringBuilder aliasStrBuilder = new StringBuilder();
        aliasStrBuilder.append("_").append(className);
        String alias;
        for (Field field : fields) {
            JoinSqlAnno joinSqlAnno = field.getAnnotation(JoinSqlAnno.class);
            if (joinSqlAnno == null) {
                continue;
            }
            className = joinSqlAnno.targetClass().getSimpleName();
            tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, joinSqlAnno.targetClass().getSimpleName());
            alias = aliasStrBuilder.toString().replaceFirst("_", "");
            sql = "select " + alias + ".*, " + getColumns(joinSqlAnno.targetClass()) + " from " + tableName + " as " + className + " inner join (" + sql + ") as " + alias + " on " + className + "." + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, joinSqlAnno.foreignKey()) + " = " + alias + "." + object.getClass().getSimpleName() + "_" + joinSqlAnno.primaryKey();
            aliasStrBuilder.append("_").append(className);
        }
        alias = aliasStrBuilder.toString().replaceFirst("_", "");
        sql = "select " + alias + ".* from (" + sql + ") as " + alias + " where 1 = 1";
        // 起别名时，在类上加注解使用原始的对象
        if (object.getClass().getAnnotation(JoinSqlAnno.class) != null && object.getClass().getAnnotation(JoinSqlAnno.class).targetClass() != Object.class) {
            String srcClassName = object.getClass().getSimpleName();
            String targetClassName = object.getClass().getAnnotation(JoinSqlAnno.class).targetClass().getSimpleName();
            sql = sql.replaceAll(srcClassName, targetClassName);
            sql = sql.replaceAll(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, srcClassName), CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, targetClassName));
        }
        return sql;
    }

    /**
     * 不带 where 1 = 1
     *
     * @param object
     * @param <S>
     * @return
     * @throws Exception
     */
    public static <S> String singleJoinSqlQueryWithoutWhere(S object) throws Exception {
        String joinSql = singleJoinSqlQuery(object);
        joinSql = joinSql.replace(" where 1 = 1", "");
        return joinSql;
    }

    /**
     * 多个Object relation根据条件查询: inner join
     *
     * @param sObject: xxxRelation
     * @return
     * @throws Exception
     */
    public static <S> String singleJoinSqlTermQuery(S sObject, Object... otherObjs) throws Exception {
        String joinSql = singleJoinSqlQuery(sObject);
        StringBuilder termSqlStrBuilder = new StringBuilder();
        List<Object> objects = new ArrayList<>();
        Collections.addAll(objects, sObject, otherObjs);
        for (Object subObj : objects) {
            Field[] subFields = subObj.getClass().getDeclaredFields();
            for (Field subField : subFields) {
                subField.setAccessible(true);
                Object subFieldVal = subField.get(subObj);
                subField.setAccessible(false);
                JoinSqlQueryAnno joinSqlQueryAnno = subField.getAnnotation(JoinSqlQueryAnno.class);
                if (joinSqlQueryAnno == null || !joinSqlQueryAnno.isUsed() || subFieldVal == null || "".equals(subFieldVal)) {
                    continue;
                }
                checkSqlInject(subFieldVal + "");
                JoinSqlQueryEnum joinSqlQueryEnum = joinSqlQueryAnno.joinSqlQueryEnum();
                String sqlTemplate = joinSqlQueryEnum.getSql();
                sqlTemplate = sqlTemplate.replace("${var0}", subObj.getClass().getSimpleName() + "_" + subField.getName());
                sqlTemplate = sqlTemplate.replace("${val0}", subFieldVal + "");
                termSqlStrBuilder.append(sqlTemplate);
            }
        }
        return joinSql + termSqlStrBuilder.toString();
    }

    /**
     * 单重对象 类型转换 maps => beans
     *
     * @param mapList
     * @param clazz
     * @param <S>
     * @return
     * @throws Exception
     */
    public static <S> List<S> singleMaps2Beans(List<Map<String, Object>> mapList, Class<S> clazz) throws Exception {
        List<S> list = new ArrayList<>();
        Map<String, Field> fieldMap = new HashMap<>(2);
        Class tmpClass = clazz;
        while (tmpClass != null && !"java.lang.Object".equals(tmpClass.getName())) {
            Field[] fields = tmpClass.getDeclaredFields();
            for (Field field : fields) {
                fieldMap.put(field.getName(), field);
            }
            tmpClass = tmpClass.getSuperclass();
        }
        for (Map<String, Object> map : mapList) {
            S object = clazz.newInstance();
            for (Map.Entry<String, Object> item : map.entrySet()) {
                String key = item.getKey();
                Object val = item.getValue();
                String fieldName = splitByLowerUnderscore(key);
                Field field = fieldMap.get(fieldName);
                if (field == null) {
                    continue;
                }
                field.setAccessible(true);
                field.set(object, val);
                field.setAccessible(false);
            }
            list.add(object);
        }
        fieldMap.clear();
        return list;
    }

    /**
     * map => map 删掉前缀Entity
     *
     * @param map
     * @return
     */
    public static Map<String, Object> map2MapWithoutPrefix(Map<String, Object> map) {
        Map<String, Object> withoutPrefixMap = new LinkedHashMap<>(2);
        for (Map.Entry<String, Object> item : map.entrySet()) {
            String srcFieldName = item.getKey();
            Object srcFieldVal = item.getValue();
            if (srcFieldVal instanceof Date) {
                srcFieldVal = dateFormat("" + srcFieldVal, "yyyy-MM-dd HH:mm:ss");
            }
            withoutPrefixMap.put(splitByLowerUnderscore(srcFieldName), srcFieldVal);
        }
        return withoutPrefixMap;
    }

    /**
     * map => map 删掉前缀Entity(回调)
     *
     * @param map
     * @return
     */
    public static Map<String, Object> map2MapWithoutPrefix(Map<String, Object> map, Collection<String> includeFieldNames, Map<String, String> callbackMap, IBeanConvertCallback iBeanConvertCallback) {
        Map<String, Object> withoutPrefixMap = new LinkedHashMap<>(2);
        for (Map.Entry<String, Object> item : map.entrySet()) {
            String srcFieldName = item.getKey();
            if (includeFieldNames != null && !includeFieldNames.contains(srcFieldName)) {
                continue;
            }
            Object srcFieldVal = item.getValue();
            if (srcFieldVal instanceof Date) {
                srcFieldVal = dateFormat("" + srcFieldVal, "yyyy-MM-dd HH:mm:ss");
            }
            withoutPrefixMap.put(splitByLowerUnderscore(srcFieldName), srcFieldVal);
            String targetFieldName = callbackMap.get(srcFieldName);
            if (targetFieldName != null) {
                Object targetFieldVal = iBeanConvertCallback.doBeanConvert(targetFieldName, srcFieldVal);
                withoutPrefixMap.put(splitByLowerUnderscore(targetFieldName), targetFieldVal);
            }
        }
        return withoutPrefixMap;
    }

    private static String splitByLowerUnderscore(String srcFieldName) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] srcFieldNames = srcFieldName.split("_");
        int i = srcFieldNames.length > 1 ? 1 : 0;
        while (i < srcFieldNames.length) {
            stringBuilder.append("_").append(srcFieldNames[i]);
            i++;
        }
        return stringBuilder.toString().replaceFirst("_", "");
    }

    /**
     * maps => maps 删掉前缀Entity
     *
     * @param mapList
     * @return
     */
    public static List<Map<String, Object>> maps2MapsWithoutPrefix(List<Map<String, Object>> mapList) {
        List<Map<String, Object>> withoutPrefixMapList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            withoutPrefixMapList.add(map2MapWithoutPrefix(map));
        }
        return withoutPrefixMapList;
    }

    /**
     * maps => maps 删掉前缀Entity(回调)
     *
     * @param mapList
     * @return
     */
    public static List<Map<String, Object>> maps2MapsWithoutPrefix(List<Map<String, Object>> mapList, Set<String> includeMap, Map<String, String> callbackMap, IBeanConvertCallback iBeanConvertCallback) {
        List<Map<String, Object>> withoutPrefixMapList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            withoutPrefixMapList.add(map2MapWithoutPrefix(map, includeMap, callbackMap, iBeanConvertCallback));
        }
        return withoutPrefixMapList;
    }

    /**
     * 获取
     * select DemoA.id as DemoA_id, DemoA.a_name as DemoA_AName from demo_a as DemoA
     * e.g: DemoA.id as DemoA_id, DemoA.a_name as DemoA_aName
     *
     * @param subClass
     * @return
     */
    private static <T> String getColumns(Class<T> subClass) {
        String className = subClass.getSimpleName();
        Field[] fields = subClass.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : fields) {
            JoinSqlQueryAnno joinSqlQueryAnno = field.getAnnotation(JoinSqlQueryAnno.class);
            // 注解存在 且 isUsed=false 则过滤
            if (joinSqlQueryAnno != null && !joinSqlQueryAnno.isUsed()) {
                continue;
            }
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

    private static String dateFormat(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date _date = null;
        try {
            _date = simpleDateFormat.parse(date);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }
        return simpleDateFormat.format(_date);
    }

    /**
     * 检测是否SQL注入
     *
     * @param sql
     */
    public static void checkSqlInject(String sql) throws Exception {
        Pattern pattern = Pattern.compile("\\b(and|exec|insert|select|drop|grant|alter|delete|update|count|chr|mid|master|truncate|char|declare|or)\\b|(\\*|;|\\+|'|%)");
        Matcher matcher = pattern.matcher(sql.toLowerCase());
        if (matcher.find()) {
            throw new Exception("SQL注入: " + sql);
        }
    }

}
