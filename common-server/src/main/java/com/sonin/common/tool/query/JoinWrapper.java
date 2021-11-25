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
    private Collection<String> selectedColumns;

    private String sql;

    private JoinWrapper() {
        tables = new LinkedHashSet<>();
        classes = new LinkedHashSet<>();
        conditions = new LinkedHashSet<>();
        sql = "select ${var0} from (select ${var1} from ${var2} where ${var3}) as ${var4}";
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

    private Collection<String> getSelectedColumns() {
        return selectedColumns;
    }

    private String getSql() {
        return sql;
    }

    private void setIncludeFields(Collection<String> includeFields) {
        this.includeFields = includeFields;
    }

    private void setSelectedColumns(Collection<String> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public static class Builder {

        private final JoinWrapper joinWrapper;

        private String COMMA_SPACE = ", ";

        private String SPACE_AND_SPACE = " and ";

        private String DOT = ".";

        private String UNDERLINE = "_";

        private String SPACE_AS_SPACE = " as ";

        private String SPACE_EQUAL_SPACE = " = ";

        private String DOT_ALL = ".*";

        private String EMPTY = "";

        public Builder() {
            joinWrapper = new JoinWrapper();
        }

        public Builder addClass(Class... classes) {
            for (Class clazz : classes) {
                String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
                joinWrapper.getTables().add(COMMA_SPACE + tableName);
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
            joinWrapper.getConditions().add(SPACE_AND_SPACE + leftTableName + DOT + leftTableFieldName + SPACE_EQUAL_SPACE + rightTableName + DOT + rightTableFieldName);
            return this;
        }

        public Builder includeFields(Field... fields) {
            if (joinWrapper.getIncludeFields() == null) {
                joinWrapper.setIncludeFields(new LinkedHashSet<>());
            }
            for (Field field : fields) {
                joinWrapper.getIncludeFields().add(field.getDeclaringClass().getSimpleName() + UNDERLINE + field.getName());
            }
            return this;
        }

        public Builder select(String... columns) {
            if (joinWrapper.getSelectedColumns() == null) {
                joinWrapper.setSelectedColumns(new LinkedHashSet<>());
            }
            for (String column : columns) {
                joinWrapper.getSelectedColumns().add(COMMA_SPACE + column);
            }
            return this;
        }

        public String build() {
            String allClassName = joinWrapper.getClasses().stream().map(Class::getSimpleName).collect(Collectors.joining(UNDERLINE));
            String tables = String.join(EMPTY, joinWrapper.getTables()).replaceFirst(COMMA_SPACE, EMPTY);
            String conditions = String.join(EMPTY, joinWrapper.getConditions()).replaceFirst(SPACE_AND_SPACE, EMPTY);
            String sql = joinWrapper.getSql();
            if (joinWrapper.getSelectedColumns() != null && !joinWrapper.getSelectedColumns().isEmpty()) {
                String selectedColumns = String.join(EMPTY, joinWrapper.getSelectedColumns()).replaceFirst(COMMA_SPACE, EMPTY);
                sql = sql.replaceFirst("\\$\\{var0}", selectedColumns);
            } else {
                sql = sql.replaceFirst("\\$\\{var0}", allClassName + DOT_ALL);
            }
            return sql.replaceFirst("\\$\\{var1}", getColumns()).replaceFirst("\\$\\{var2}", tables).replaceFirst("\\$\\{var3}", conditions).replaceFirst("\\$\\{var4}", allClassName);
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
                    String alias = className + UNDERLINE + classFieldName;
                    if (joinWrapper.getIncludeFields() != null && !joinWrapper.getIncludeFields().isEmpty()) {
                        if (joinWrapper.getIncludeFields().contains(alias)) {
                            stringBuilder.append(COMMA_SPACE).append(tableName).append(DOT).append(tableFieldName).append(SPACE_AS_SPACE).append(alias);
                        }
                    } else {
                        stringBuilder.append(COMMA_SPACE).append(tableName).append(DOT).append(tableFieldName).append(SPACE_AS_SPACE).append(alias);
                    }
                }
            }
            return stringBuilder.toString().replaceFirst(COMMA_SPACE, EMPTY);
        }

    }

}
