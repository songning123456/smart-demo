package com.sonin.common.tool.query;

import com.google.common.base.CaseFormat;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * @author sonin
 * @date 2021/11/19 8:27
 */
public class JoinWrapper {

    private Collection<String> tables;
    private Collection<Class> classes;
    private Collection<String> conditions;
    private Collection<String> includeFields;

    private JoinWrapper() {
        tables = new LinkedHashSet<>();
        classes = new LinkedHashSet<>();
        conditions = new LinkedHashSet<>();
    }

    private Collection<String> getTables() {
        return tables;
    }

    private Collection<Class> getClasses() {
        return classes;
    }

    private Collection<String> getConditions() {
        return conditions;
    }

    private Collection<String> getIncludeFields() {
        return includeFields;
    }

    private void setTables(Collection<String> tables) {
        this.tables = tables;
    }

    private void setClasses(Collection<Class> classes) {
        this.classes = classes;
    }

    private void setConditions(Collection<String> conditions) {
        this.conditions = conditions;
    }

    private void setIncludeFields(Collection<String> includeFields) {
        this.includeFields = includeFields;
    }

    public static class Builder {

        private final JoinWrapper joinWrapper;

        public Builder() {
            joinWrapper = new JoinWrapper();
        }

        public Builder addClass(Class... classes) {
            for (Class clazz : classes) {
                String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
                joinWrapper.getTables().add(", " + tableName);
                joinWrapper.getClasses().add(clazz);
            }
            return this;
        }

        public Builder addCondition(Field leftField, Field rightField) {
            // e.g: DemoA
            String leftClassName = leftField.getDeclaringClass().getSimpleName();
            // e.g: demo_a
            String leftTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, leftClassName);
            // e.g: id
            String leftClassFieldName = leftField.getName();
            // e.g: id
            String leftTableFieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, leftClassFieldName);
            // e.g: DemoB
            String rightClassName = rightField.getDeclaringClass().getSimpleName();
            // e.g: demo_b
            String rightTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, rightClassName);
            // e.g: aId
            String rightClassFieldName = rightField.getName();
            // e.g: a_id
            String rightTableFieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, rightClassFieldName);
            // e.g: , demo_a.id = demo_b.a_id
            joinWrapper.getConditions().add(" and " + leftTableName + "." + leftTableFieldName + " = " + rightTableName + "." + rightTableFieldName);
            return this;
        }

        public Builder includeFields(Field... fields) {
            if (joinWrapper.getIncludeFields() == null) {
                joinWrapper.setIncludeFields(new LinkedHashSet<>());
            }
            for (Field field : fields) {
                joinWrapper.getIncludeFields().add(field.getDeclaringClass().getSimpleName() + "_" + field.getName());
            }
            return this;
        }

        public String build() {
            String allClassName = joinWrapper.getClasses().stream().map(Class::getSimpleName).collect(Collectors.joining("_"));
            String tables = String.join("", joinWrapper.getTables()).replaceFirst(",", "");
            String conditions = String.join("", joinWrapper.getConditions()).replaceFirst(" and", "");
            return "select " + allClassName + ".* from (select " + getColumns() + " from" + tables + " where" + conditions + ") as " + allClassName;
        }

        private String getColumns() {
            StringBuilder stringBuilder = new StringBuilder();
            for (Class clazz : joinWrapper.getClasses()) {
                String className = clazz.getSimpleName();
                String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    String classFieldName = field.getName();
                    String tableFieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, classFieldName);
                    String alias = className + "_" + classFieldName;
                    if (joinWrapper.getIncludeFields() != null && !joinWrapper.getIncludeFields().isEmpty()) {
                        if (joinWrapper.getIncludeFields().contains(alias)) {
                            stringBuilder.append(", ").append(tableName).append(".").append(tableFieldName).append(" as ").append(alias);
                        }
                    } else {
                        stringBuilder.append(", ").append(tableName).append(".").append(tableFieldName).append(" as ").append(alias);
                    }
                }
            }
            return stringBuilder.toString().replaceFirst(", ", "");
        }

    }

}
