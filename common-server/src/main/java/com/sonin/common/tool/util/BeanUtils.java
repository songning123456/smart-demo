package com.sonin.common.tool.util;

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
     * Java Bean与Bean之间的转换
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
            T target = (T) targetClass.newInstance();
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

}
