package com.sonin.common.tool.util;

import com.sonin.common.tool.annotation.BeanAnno;
import com.sonin.common.tool.service.IBeanConvertCallback;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/3 13:23
 * bean类型转换扩展
 */
public class BeanExtUtils {

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
        Class tmpSrcClass = src.getClass();
        // 遍历src对象
        while (tmpSrcClass != null && !"java.lang.Object".equals(tmpSrcClass.getName())) {
            Field[] srcFields = tmpSrcClass.getDeclaredFields();
            for (Field srcField : srcFields) {
                srcField.setAccessible(true);
                srcMap.put(srcField.getName(), srcField.get(src));
                BeanAnno beanAnno = srcField.getAnnotation(BeanAnno.class);
                if (beanAnno != null && !"".equals(beanAnno.targetFieldName())) {
                    annoMap.put(beanAnno.targetFieldName(), srcField.get(src));
                }
            }
            tmpSrcClass = tmpSrcClass.getSuperclass();
        }
        Class tmpTargetClass = targetClass;
        // 遍历target对象
        while (!"java.lang.Object".equals(tmpTargetClass.getName())) {
            Field[] targetFields = tmpTargetClass.getDeclaredFields();
            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                if (annoMap.get(targetField.getName()) != null) {
                    Object targetFieldVal = iBeanConvertCallback.doBeanConvert(targetField.getName(), annoMap.get(targetField.getName()));
                    targetField.set(target, targetFieldVal);
                } else {
                    targetField.set(target, srcMap.get(targetField.getName()));
                }
            }
            tmpTargetClass = tmpTargetClass.getSuperclass();
        }
        // 清理缓存
        srcMap.clear();
        annoMap.clear();
        // 返回目标对象
        return target;
    }
}
