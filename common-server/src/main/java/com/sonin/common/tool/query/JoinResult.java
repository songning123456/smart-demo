package com.sonin.common.tool.query;

import com.sonin.common.tool.callback.IBeanConvertCallback;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sonin
 * @date 2021/11/21 8:50
 */
public class JoinResult implements Wrapper {

    private Map<String, String> callbackMap;

    private Collection<String> selectedColumns;

    private JoinResult() {
    }

    private Map<String, String> getCallbackMap() {
        return callbackMap;
    }

    private Collection<String> getSelectedColumns() {
        return selectedColumns;
    }

    private void setCallbackMap(Map<String, String> callbackMap) {
        this.callbackMap = callbackMap;
    }

    private void setSelectedColumns(Collection<String> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public static class Builder {

        private final JoinResult joinResult;

        public Builder() {
            joinResult = new JoinResult();
        }

        public Builder select(Field... fields) {
            if (joinResult.getSelectedColumns() == null) {
                joinResult.setSelectedColumns(new LinkedHashSet<>());
            }
            for (Field field : fields) {
                joinResult.getSelectedColumns().add(field.getDeclaringClass().getSimpleName() + UNDERLINE + field.getName());
            }
            return this;
        }

        public Builder addCallback(Field srcField, Field targetField) {
            if (joinResult.getCallbackMap() == null) {
                joinResult.setCallbackMap(new LinkedHashMap<>());
            }
            String srcClassName = srcField.getDeclaringClass().getSimpleName();
            String srcFieldName = srcField.getName();
            String targetClassName = targetField.getDeclaringClass().getSimpleName();
            String targetFieldName = targetField.getName();
            joinResult.getCallbackMap().put(srcClassName + UNDERLINE + srcFieldName, targetClassName + UNDERLINE + targetFieldName);
            return this;
        }

        public Builder addCallback(Field srcField, String targetField) {
            if (joinResult.getCallbackMap() == null) {
                joinResult.setCallbackMap(new LinkedHashMap<>());
            }
            String srcClassName = srcField.getDeclaringClass().getSimpleName();
            String srcFieldName = srcField.getName();
            joinResult.getCallbackMap().put(srcClassName + UNDERLINE + srcFieldName, targetField);
            return this;
        }

        public Builder addCallback(String srcField, String targetField) {
            if (joinResult.getCallbackMap() == null) {
                joinResult.setCallbackMap(new LinkedHashMap<>());
            }
            joinResult.getCallbackMap().put(srcField, targetField);
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
                if (joinResult.getSelectedColumns() != null && !joinResult.getSelectedColumns().contains(srcFieldName)) {
                    continue;
                }
                Object srcFieldVal = item.getValue();
                if (srcFieldVal instanceof Date) {
                    srcFieldVal = dateFormat(EMPTY + srcFieldVal, "yyyy-MM-dd HH:mm:ss");
                }
                targetMap.put(srcFieldName, srcFieldVal);
                if (joinResult.getCallbackMap() != null && joinResult.getCallbackMap().get(srcFieldName) != null) {
                    Object callbackFieldVal = iBeanConvertCallback.doBeanConvert(joinResult.getCallbackMap().get(srcFieldName), srcFieldVal);
                    targetMap.put(joinResult.getCallbackMap().get(srcFieldName), callbackFieldVal);
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
                if (joinResult.getSelectedColumns() != null && !joinResult.getSelectedColumns().contains(srcFieldName)) {
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
                if (joinResult.getSelectedColumns() != null && !joinResult.getSelectedColumns().contains(srcFieldName)) {
                    continue;
                }
                Object srcFieldVal = item.getValue();
                if (srcFieldVal instanceof Date) {
                    srcFieldVal = dateFormat(EMPTY + srcFieldVal, "yyyy-MM-dd HH:mm:ss");
                }
                targetMap.put(splitByLowerUnderscore(srcFieldName), srcFieldVal);
                if (joinResult.getCallbackMap() != null && joinResult.getCallbackMap().get(srcFieldName) != null) {
                    Object callbackFieldVal = iBeanConvertCallback.doBeanConvert(joinResult.getCallbackMap().get(srcFieldName), srcFieldVal);
                    targetMap.put(joinResult.getCallbackMap().get(srcFieldName), callbackFieldVal);
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
         * Maps => Beans
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
                        if (joinResult.getCallbackMap() != null && joinResult.getCallbackMap().containsValue(fieldName)) {
                            Object callbackFieldVal = iBeanConvertCallback.doBeanConvert(fieldName, map.get(getValByKey(joinResult.getCallbackMap(), fieldName)));
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

        private String getValByKey(Map<String, String> map, String val) {
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
}
