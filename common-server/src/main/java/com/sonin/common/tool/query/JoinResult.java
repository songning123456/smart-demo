package com.sonin.common.tool.query;

import com.sonin.common.tool.callback.IBeanConvertCallback;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sonin.common.tool.query.SqlConstant.*;

/**
 * @author sonin
 * @date 2021/11/21 8:50
 */
public class JoinResult {

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

        /**
         * src Map => target Map
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
         * src Map => target Map (回调)
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
         * src Maps => target Maps
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
         * src Maps => target Maps (回调)
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

    }
}
