package com.sonin.common.tool.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/2 13:31
 */
public class BeanUtils {

    /**
     * Bean => Bean
     *
     * @param src
     * @param targetClass
     * @throws Exception
     */
    public static <S, T> T bean2Bean(S src, Class<T> targetClass) throws Exception {
        // 判空
        if (src == null || targetClass == null) {
            return null;
        }
        // 获取Method[]
        Method[] srcMethods = src.getClass().getMethods();
        Method[] targetMethods = targetClass.getMethods();
        // 创建目标对象
        T target = (T) targetClass.newInstance();
        // 创建 fieldName => Method 缓存
        Map<String, Method> srcMap = new HashMap<>(srcMethods.length >> 1);
        // 缓存src
        for (Method srcMethod : srcMethods) {
            String srcMethodName = srcMethod.getName();
            if (srcMethodName.startsWith("get") && !"getClass".equals(srcMethodName)) {
                srcMap.put(srcMethodName.replaceFirst("get", ""), srcMethod);
            }
        }
        // 根据缓存的src创建target
        for (Method targetMethod : targetMethods) {
            String targetMethodName = targetMethod.getName();
            if (targetMethodName.startsWith("set")) {
                Method srcMethod = srcMap.get(targetMethodName.replaceFirst("set", ""));
                if (srcMethod == null) {
                    continue;
                }
                Object fieldVal = srcMethod.invoke(src);
                targetMethod.invoke(target, fieldVal);
            }
        }
        // 清理缓存
        srcMap.clear();
        // 返回目标对象
        return target;
    }

    /**
     * List<S> => List<T>
     *
     * @param srcList
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <S, T> List<T> beans2Beans(List<S> srcList, Class<T> targetClass) throws Exception {
        // 判空
        if (srcList == null || targetClass == null) {
            return null;
        }
        // 判空
        if (srcList.isEmpty()) {
            return new ArrayList<>();
        }
        // 获取Method[]
        Method[] srcMethods = srcList.get(0).getClass().getMethods();
        Method[] targetMethods = targetClass.getMethods();
        // 创建目标对象
        List<T> targetList = new ArrayList<>();
        // 创建 fieldName => Method 缓存
        Map<String, Method> srcMap = new HashMap<>(srcMethods.length >> 1);
        Map<String, Method> targetMap = new HashMap<>(targetMethods.length >> 1);
        // 遍历
        for (S src : srcList) {
            T target = targetClass.newInstance();
            if (srcMap.isEmpty() && targetMap.isEmpty()) {
                for (Method srcMethod : srcMethods) {
                    String srcMethodName = srcMethod.getName();
                    if (srcMethodName.startsWith("get") && !"getClass".equals(srcMethodName)) {
                        srcMap.put(srcMethodName.replaceFirst("get", ""), srcMethod);
                    }
                }
                for (Method targetMethod : targetMethods) {
                    String targetMethodName = targetMethod.getName();
                    if (targetMethodName.startsWith("set")) {
                        Method srcMethod = srcMap.get(targetMethodName.replaceFirst("set", ""));
                        if (srcMethod == null) {
                            continue;
                        }
                        Object fieldVal = srcMethod.invoke(src);
                        targetMethod.invoke(target, fieldVal);
                        targetMap.put(targetMethodName.replaceFirst("set", ""), targetMethod);
                    }
                }
            } else {
                for (Map.Entry<String, Method> item : targetMap.entrySet()) {
                    String fieldName = item.getKey();
                    Method srcMethod = srcMap.get(fieldName);
                    if (srcMethod == null) {
                        continue;
                    }
                    Object fieldVal = srcMethod.invoke(src);
                    Method targetMethod = targetMap.get(fieldName);
                    targetMethod.invoke(target, fieldVal);
                }
            }
            targetList.add(target);
        }
        // 清理缓存
        srcMap.clear();
        targetMap.clear();
        // 返回目标对象
        return targetList;
    }

    /**
     * Map => Bean
     *
     * @param map
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> targetClass) throws Exception {
        // 判空
        if (map == null || targetClass == null) {
            return null;
        }
        // 判空
        if (map.isEmpty()) {
            return targetClass.newInstance();
        }
        // 构建对象
        T target = targetClass.newInstance();
        // 获取所有方法
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                // 截取属性名
                String fieldName = method.getName();
                fieldName = fieldName.toLowerCase().charAt(3) + fieldName.substring(4);
                if (map.containsKey(fieldName)) {
                    method.invoke(target, map.get(fieldName));
                }
            }
        }
        return target;
    }

    /**
     * Maps => Beans
     *
     * @param mapList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> maps2Beans(List<Map<String, Object>> mapList, Class<T> targetClass) throws Exception {
        // 判空
        if (mapList == null || targetClass == null) {
            return null;
        }
        // 判空
        if (mapList.isEmpty()) {
            return new ArrayList<>();
        }
        // 构建对象集合
        List<T> targetList = new ArrayList<>();
        // fieldName:method 缓存
        Map<String, Method> methodMap = new HashMap<>();
        // 遍历存储
        for (Map<String, Object> map : mapList) {
            T target = targetClass.newInstance();
            if (methodMap.isEmpty()) {
                Method[] methods = targetClass.getMethods();
                for (Method method : methods) {
                    if (method.getName().startsWith("set")) {
                        // 截取属性名
                        String fieldName = method.getName();
                        fieldName = fieldName.toLowerCase().charAt(3) + fieldName.substring(4);
                        if (map.containsKey(fieldName)) {
                            method.invoke(target, map.get(fieldName));
                            methodMap.put(fieldName, method);
                        }
                    }
                }
            } else {
                for (Map.Entry<String, Method> item : methodMap.entrySet()) {
                    String fieldName = item.getKey();
                    Method method = item.getValue();
                    method.invoke(target, map.get(fieldName));
                }
            }
            targetList.add(target);
        }
        // 清理缓存
        methodMap.clear();
        return targetList;
    }

    /**
     * Bean => Map
     *
     * @param src
     * @param <S>
     * @return
     */
    public static <S> Map<String, Object> bean2Map(S src) throws Exception {
        // 判空
        if (src == null) {
            return new HashMap<>();
        }
        Map<String, Object> map = new HashMap<>();
        Class srcClass = src.getClass();
        while (srcClass != null && !"java.lang.Object".equals(srcClass.getName())) {
            Field[] srcFields = srcClass.getDeclaredFields();
            for (Field srcField : srcFields) {
                srcField.setAccessible(true);
                map.put(srcField.getName(), srcField.get(src));
            }
            srcClass = srcClass.getSuperclass();
        }
        return map;
    }

    /**
     * Beans => Maps
     *
     * @param srcList
     * @param <S>
     * @return
     */
    public static <S> List<Map<String, Object>> beans2Maps(List<S> srcList) throws Exception {
        // 判空
        if (srcList == null) {
            return null;
        }
        // 判空
        if (srcList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        // 字段属性缓存
        List<Field> fieldList = new ArrayList<>();
        // 遍历存储
        for (S src : srcList) {
            Map<String, Object> map = new HashMap<>();
            if (fieldList.isEmpty()) {
                Class srcClass = src.getClass();
                while (srcClass != null && !"java.lang.Object".equals(srcClass.getName())) {
                    Field[] srcFields = srcClass.getDeclaredFields();
                    for (Field srcField : srcFields) {
                        srcField.setAccessible(true);
                        map.put(srcField.getName(), srcField.get(src));
                        fieldList.add(srcField);
                    }
                    srcClass = srcClass.getSuperclass();
                }
            } else {
                for (Field srcField : fieldList) {
                    map.put(srcField.getName(), srcField.get(src));
                }
            }
            mapList.add(map);
        }
        // 清理缓存
        fieldList.clear();
        return mapList;
    }

}
