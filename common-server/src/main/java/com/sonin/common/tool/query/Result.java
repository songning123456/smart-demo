package com.sonin.common.tool.query;

import com.sonin.common.tool.callback.IBeanConvertCallback;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sonin
 * @date 2021/12/5 8:35
 */
public class Result implements IWrapper {

    private Map<String, String> callbackMap;

    private Collection<String> selectedColumns;

    public Result select(Field... fields) {
        if (this.selectedColumns == null) {
            this.selectedColumns = new LinkedHashSet<>();
        }
        for (Field field : fields) {
            this.selectedColumns.add(field.getDeclaringClass().getSimpleName() + UNDERLINE + field.getName());
        }
        return this;
    }

    public Result addCallback(Field srcField, Field targetField) {
        if (this.callbackMap == null) {
            this.callbackMap = new LinkedHashMap<>();
        }
        String srcClassName = srcField.getDeclaringClass().getSimpleName();
        String srcFieldName = srcField.getName();
        String targetClassName = targetField.getDeclaringClass().getSimpleName();
        String targetFieldName = targetField.getName();
        this.callbackMap.put(srcClassName + UNDERLINE + srcFieldName, targetClassName + UNDERLINE + targetFieldName);
        return this;
    }

    public Result addCallback(Field srcField, String targetField) {
        if (this.callbackMap == null) {
            this.callbackMap = new LinkedHashMap<>();
        }
        String srcClassName = srcField.getDeclaringClass().getSimpleName();
        String srcFieldName = srcField.getName();
        this.callbackMap.put(srcClassName + UNDERLINE + srcFieldName, targetField);
        return this;
    }

    public Result addCallback(String srcField, String targetField) {
        if (this.callbackMap == null) {
            this.callbackMap = new LinkedHashMap<>();
        }
        this.callbackMap.put(srcField, targetField);
        return this;
    }

    /**
     * src Map => target Map (前缀 + 回调)
     *
     * @param srcMap
     * @param iBeanConvertCallback
     * @return
     */
    public Map<String, Object> map2MapWithPrefix(Map<String, Object> srcMap, IBeanConvertCallback iBeanConvertCallback) {
        Map<String, Object> targetMap = new LinkedHashMap<>(2);
        for (Map.Entry<String, Object> item : srcMap.entrySet()) {
            String srcFieldName = item.getKey();
            if (this.selectedColumns != null && !this.selectedColumns.contains(srcFieldName)) {
                continue;
            }
            Object srcFieldVal = item.getValue();
            if (srcFieldVal instanceof Date) {
                srcFieldVal = dateFormat(EMPTY + srcFieldVal, "yyyy-MM-dd HH:mm:ss");
            }
            targetMap.put(srcFieldName, srcFieldVal);
            if (this.callbackMap != null && this.callbackMap.get(srcFieldName) != null) {
                Object callbackFieldVal = iBeanConvertCallback.doBeanConvert(this.callbackMap.get(srcFieldName), srcFieldVal);
                targetMap.put(this.callbackMap.get(srcFieldName), callbackFieldVal);
            }
        }
        return targetMap;
    }

    /**
     * src Maps => target Maps (前缀 + 回调)
     *
     * @param srcMapList
     * @param iBeanConvertCallback
     * @return
     */
    public List<Map<String, Object>> maps2MapsWithPrefix(List<Map<String, Object>> srcMapList, IBeanConvertCallback iBeanConvertCallback) {
        List<Map<String, Object>> targetMapList = new ArrayList<>();
        for (Map<String, Object> srcMap : srcMapList) {
            targetMapList.add(map2MapWithPrefix(srcMap, iBeanConvertCallback));
        }
        return targetMapList;
    }

    /**
     * src Map => target Map (无前缀 + 无回调)
     *
     * @param srcMap
     * @return
     */
    public Map<String, Object> map2MapWithoutPrefix(Map<String, Object> srcMap) {
        Map<String, Object> targetMap = new LinkedHashMap<>(2);
        for (Map.Entry<String, Object> item : srcMap.entrySet()) {
            String srcFieldName = item.getKey();
            if (this.selectedColumns != null && !this.selectedColumns.contains(srcFieldName)) {
                continue;
            }
            Object srcFieldVal = item.getValue();
            if (srcFieldVal instanceof Date) {
                srcFieldVal = dateFormat(EMPTY + srcFieldVal, "yyyy-MM-dd HH:mm:ss");
            }
            targetMap.put(splitByLowerUnderscore(srcFieldName), srcFieldVal);
        }
        return targetMap;
    }

    /**
     * src Maps => target Maps (无前缀 + 无回调)
     *
     * @param srcMapList
     * @return
     */
    public List<Map<String, Object>> maps2MapsWithoutPrefix(List<Map<String, Object>> srcMapList) {
        List<Map<String, Object>> targetMapList = new ArrayList<>();
        for (Map<String, Object> srcMap : srcMapList) {
            targetMapList.add(map2MapWithoutPrefix(srcMap));
        }
        return targetMapList;
    }

    /**
     * src Map => target Map (无前缀 + 回调)
     *
     * @param srcMap
     * @param iBeanConvertCallback
     * @return
     */
    public Map<String, Object> map2MapWithoutPrefix(Map<String, Object> srcMap, IBeanConvertCallback iBeanConvertCallback) {
        Map<String, Object> targetMap = new LinkedHashMap<>(2);
        for (Map.Entry<String, Object> item : srcMap.entrySet()) {
            String srcFieldName = item.getKey();
            if (this.selectedColumns != null && !this.selectedColumns.contains(srcFieldName)) {
                continue;
            }
            Object srcFieldVal = item.getValue();
            if (srcFieldVal instanceof Date) {
                srcFieldVal = dateFormat(EMPTY + srcFieldVal, "yyyy-MM-dd HH:mm:ss");
            }
            targetMap.put(splitByLowerUnderscore(srcFieldName), srcFieldVal);
            if (this.callbackMap != null && this.callbackMap.get(srcFieldName) != null) {
                Object callbackFieldVal = iBeanConvertCallback.doBeanConvert(this.callbackMap.get(srcFieldName), srcFieldVal);
                targetMap.put(this.callbackMap.get(srcFieldName), callbackFieldVal);
            }
        }
        return targetMap;
    }

    /**
     * src Maps => target Maps (无前缀 + 回调)
     *
     * @param srcMapList
     * @param iBeanConvertCallback
     * @return
     */
    public List<Map<String, Object>> maps2MapsWithoutPrefix(List<Map<String, Object>> srcMapList, IBeanConvertCallback iBeanConvertCallback) {
        List<Map<String, Object>> targetMapList = new ArrayList<>();
        for (Map<String, Object> srcMap : srcMapList) {
            targetMapList.add(map2MapWithoutPrefix(srcMap, iBeanConvertCallback));
        }
        return targetMapList;
    }

    /**
     * Maps => Beans (无回调)
     *
     * @param mapList
     * @param targetClass
     * @param <T>
     * @return
     */
    public <T> List<T> maps2Beans(List<Map<String, Object>> mapList, Class<T> targetClass) throws Exception {
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
        // 遍历存储
        for (Map<String, Object> map : mapList) {
            T target = targetClass.newInstance();
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
            targetList.add(target);
        }
        return targetList;
    }

    /**
     * Maps => Beans (回调)
     *
     * @param mapList
     * @param targetClass
     * @param <T>
     * @return
     */
    public <T> List<T> maps2Beans(List<Map<String, Object>> mapList, Class<T> targetClass, IBeanConvertCallback iBeanConvertCallback) throws Exception {
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
        // 遍历存储
        for (Map<String, Object> map : mapList) {
            T target = targetClass.newInstance();
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("set")) {
                    // 截取属性名
                    String fieldName = method.getName();
                    fieldName = fieldName.toLowerCase().charAt(3) + fieldName.substring(4);
                    if (map.containsKey(fieldName)) {
                        method.invoke(target, map.get(fieldName));
                    }
                    // 补充回调
                    if (this.callbackMap != null && this.callbackMap.containsValue(fieldName)) {
                        Object callbackFieldVal = iBeanConvertCallback.doBeanConvert(fieldName, map.get(getKeyByVal(this.callbackMap, fieldName)));
                        method.invoke(target, callbackFieldVal);
                    }
                }
            }
            targetList.add(target);
        }
        return targetList;
    }

    /**
     * e.g: DemoA_aName => aName
     *
     * @param srcFieldName
     * @return
     */
    private String splitByLowerUnderscore(String srcFieldName) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] srcFieldNames = srcFieldName.split(UNDERLINE);
        int i = srcFieldNames.length > 1 ? 1 : 0;
        while (i < srcFieldNames.length) {
            stringBuilder.append(UNDERLINE).append(srcFieldNames[i]);
            i++;
        }
        return stringBuilder.toString().replaceFirst(UNDERLINE, EMPTY);
    }

    /**
     * Date => String
     *
     * @param date
     * @param format
     * @return
     */
    private String dateFormat(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date _date = null;
        try {
            _date = simpleDateFormat.parse(date);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }
        return simpleDateFormat.format(_date);
    }

    private String getKeyByVal(Map<String, String> map, String val) {
        String key = "";
        for (Map.Entry<String, String> item : map.entrySet()) {
            if (val.equals(item.getValue())) {
                key = item.getKey();
                break;
            }
        }
        return key;
    }

}
