package com.sonin.common.tool.util;

import com.sonin.common.tool.annotation.BeanAnno;
import com.sonin.common.tool.service.IBeanConvertCallback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/3 13:23
 * bean类型转换扩展
 */
public class BeanExtUtils {

    /**
     * bean => bean 自定义实现值
     *
     * @param src
     * @param targetClass
     * @param iBeanConvertCallback
     * @param <S>
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <S, T> T bean2Bean(S src, Class<T> targetClass, IBeanConvertCallback iBeanConvertCallback) throws Exception {
        // 判空
        if (src == null || targetClass == null || iBeanConvertCallback == null) {
            return null;
        }
        // 创建目标对象
        T target = targetClass.newInstance();
        // 缓存
        Map<String, Object> srcMap = new HashMap<>();
        Map<String, Object> annoMap = new HashMap<>();
        // 遍历src对象属性
        Class tmpSrcClass = src.getClass();
        while (!"java.lang.Object".equals(tmpSrcClass.getName())) {
            Field[] srcFields = tmpSrcClass.getDeclaredFields();
            for (Field srcField : srcFields) {
                srcField.setAccessible(true);
                srcMap.put(srcField.getName(), srcField.get(src));
                beanAnnoFunc(src, srcField, annoMap);
            }
            tmpSrcClass = tmpSrcClass.getSuperclass();
        }
        // 遍历target对象属性
        Class tmpTargetClass = targetClass;
        while (!"java.lang.Object".equals(tmpTargetClass.getName())) {
            Field[] targetFields = tmpTargetClass.getDeclaredFields();
            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                setTargetFunc(targetField, target, srcMap, annoMap, iBeanConvertCallback);
            }
            tmpTargetClass = tmpTargetClass.getSuperclass();
        }
        // 清理缓存
        srcMap.clear();
        annoMap.clear();
        // 返回目标对象
        return target;
    }

    /**
     * beans => beans 自定义实现值
     *
     * @param srcList
     * @param targetClass
     * @param iBeanConvertCallback
     * @param <S>
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <S, T> List<T> beans2Beans(List<S> srcList, Class<T> targetClass, IBeanConvertCallback iBeanConvertCallback) throws Exception {
        // 判空
        if (srcList == null || targetClass == null) {
            return null;
        }
        // 判空
        if (srcList.isEmpty()) {
            return new ArrayList<>();
        }
        // 创建目标对象集合
        List<T> targetList = new ArrayList<>();
        // src、target的Field缓存
        List<Field> srcFieldList = new ArrayList<>();
        List<Field> targetFieldList = new ArrayList<>();
        for (S src : srcList) {
            // 创建目标对象
            T target = targetClass.newInstance();
            // 缓存
            Map<String, Object> srcMap = new HashMap<>();
            Map<String, Object> annoMap = new HashMap<>();
            if (srcFieldList.isEmpty() && targetFieldList.isEmpty()) {
                // 遍历src对象属性
                Class tmpSrcClass = src.getClass();
                while (!"java.lang.Object".equals(tmpSrcClass.getName())) {
                    Field[] srcFields = tmpSrcClass.getDeclaredFields();
                    for (Field srcField : srcFields) {
                        srcField.setAccessible(true);
                        srcFieldList.add(srcField);
                        srcMap.put(srcField.getName(), srcField.get(src));
                        beanAnnoFunc(src, srcField, annoMap);
                    }
                    tmpSrcClass = tmpSrcClass.getSuperclass();
                }
                // 遍历target对象属性
                Class tmpTargetClass = targetClass;
                while (!"java.lang.Object".equals(tmpTargetClass.getName())) {
                    Field[] targetFields = tmpTargetClass.getDeclaredFields();
                    for (Field targetField : targetFields) {
                        targetField.setAccessible(true);
                        targetFieldList.add(targetField);
                        setTargetFunc(targetField, target, srcMap, annoMap, iBeanConvertCallback);
                    }
                    tmpTargetClass = tmpTargetClass.getSuperclass();
                }
            } else {
                for (Field srcField : srcFieldList) {
                    srcMap.put(srcField.getName(), srcField.get(src));
                    beanAnnoFunc(src, srcField, annoMap);
                }
                for (Field targetField : targetFieldList) {
                    setTargetFunc(targetField, target, srcMap, annoMap, iBeanConvertCallback);
                }
            }
            // 清理缓存
            srcMap.clear();
            annoMap.clear();
            // 集合添加目标对象
            targetList.add(target);
        }
        // 清理缓存
        srcFieldList.clear();
        targetFieldList.clear();
        // 返回目标对象
        return targetList;
    }

    /**
     * 根据注解缓存annoMap
     *
     * @param src
     * @param srcField
     * @param annoMap
     * @param <S>
     * @throws Exception
     */
    private static <S> void beanAnnoFunc(S src, Field srcField, Map<String, Object> annoMap) throws Exception {
        BeanAnno beanAnno = srcField.getAnnotation(BeanAnno.class);
        if (beanAnno != null) {
            if (!"".equals(beanAnno.targetFieldName())) {
                annoMap.put(beanAnno.targetFieldName(), srcField.get(src));
            }
            if (beanAnno.targetFieldNames().length > 0) {
                for (String targetFieldName : beanAnno.targetFieldNames()) {
                    if (!"".equals(targetFieldName)) {
                        annoMap.put(targetFieldName, srcField.get(src));
                    }
                }
            }
        }
    }

    /**
     * 设置target的field值
     *
     * @param targetField
     * @param target
     * @param srcMap
     * @param annoMap
     * @param iBeanConvertCallback
     * @param <T>
     * @throws Exception
     */
    private static <T> void setTargetFunc(Field targetField, T target, Map<String, Object> srcMap, Map<String, Object> annoMap, IBeanConvertCallback iBeanConvertCallback) throws Exception {
        if (annoMap.get(targetField.getName()) != null) {
            Object targetFieldVal = iBeanConvertCallback.doBeanConvert(targetField.getName(), annoMap.get(targetField.getName()));
            targetField.set(target, targetFieldVal);
        } else {
            targetField.set(target, srcMap.get(targetField.getName()));
        }
    }

}
