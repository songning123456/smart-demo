package com.sonin.common.tool.util;

import com.alibaba.fastjson.JSON;

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
    public static <S, T> T bean2Bean(S src, Class<?> targetClass) throws Exception {
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
    public static <T> T map2Bean(Map map, Class<T> targetClass) throws Exception {
        // 判空
        if (map == null || targetClass == null) {
            return null;
        }
        // 判空
        if (map.isEmpty()) {
            return targetClass.newInstance();
        }
        return JSON.parseObject(JSON.toJSONString(map), targetClass);
    }

    /**
     * Maps => Beans
     *
     * @param mapList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> maps2Beans(List<Map> mapList, Class<T> targetClass) {
        // 判空
        if (mapList == null || targetClass == null) {
            return null;
        }
        // 判空
        if (mapList.isEmpty()) {
            return new ArrayList<>();
        }
        List<T> targetList = new ArrayList<>();
        mapList.forEach(map -> targetList.add(JSON.parseObject(JSON.toJSONString(map), targetClass)));
        return targetList;
    }

    /**
     * Bean => Map
     *
     * @param src
     * @param <S>
     * @return
     */
    public static <S> Map bean2Map(S src) {
        // 判空
        if (src == null) {
            return new HashMap();
        }
        return JSON.parseObject(JSON.toJSONString(src), Map.class);
    }

    /**
     * Beans => Maps
     *
     * @param srcList
     * @param <S>
     * @return
     */
    public static <S> List<Map> beans2Maps(List<S> srcList) {
        // 判空
        if (srcList == null) {
            return null;
        }
        // 判空
        if (srcList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map> mapList = new ArrayList<>();
        srcList.forEach(src -> mapList.add(JSON.parseObject(JSON.toJSONString(src), Map.class)));
        return mapList;
    }

}
