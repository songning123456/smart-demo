package com.sonin.common.tool.query;

import com.google.common.base.CaseFormat;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import static com.sonin.common.tool.query.SqlConstant.*;

/**
 * @author sonin
 * @date 2021/11/26 8:02
 * QueryWrapper条件e.g: demo_b.b_name = xxx
 */
public class JoinWrapper {

    private Class from;
    private Collection<Class> classes;
    private Collection<String> conditions;
    private Collection<String> selectedColumns;

    private JoinWrapper() {
        classes = new LinkedHashSet<>();
    }

    private Class getFrom() {
        return from;
    }

    private Collection<Class> getClasses() {
        return classes;
    }

    private Collection<String> getConditions() {
        return conditions;
    }

    private Collection<String> getSelectedColumns() {
        return selectedColumns;
    }

    private void setFrom(Class from) {
        this.from = from;
    }

    private void setConditions(Collection<String> conditions) {
        this.conditions = conditions;
    }

    private void setSelectedColumns(Collection<String> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public static class Builder {

        private final JoinWrapper joinWrapper;

        public Builder() {
            this.joinWrapper = new JoinWrapper();
        }

        public Builder select(Field... fields) {
            if (joinWrapper.getSelectedColumns() == null) {
                joinWrapper.setSelectedColumns(new LinkedHashSet<>());
            }
            for (Field field : fields) {
                String className = field.getDeclaringClass().getSimpleName();
                String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
                String fieldName = field.getName();
                String column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
                String alias = tableName + DOT + column + SPACE + AS + SPACE + className + UNDERLINE + fieldName;
                joinWrapper.getSelectedColumns().add(alias);
            }
            return this;
        }

        public Builder select(String... fields) {
            if (joinWrapper.getSelectedColumns() == null) {
                joinWrapper.setSelectedColumns(new LinkedHashSet<>());
            }
            joinWrapper.getSelectedColumns().addAll(Arrays.asList(fields));
            return this;
        }

        public Builder from(Class clazz) {
            joinWrapper.setFrom(clazz);
            joinWrapper.getClasses().add(clazz);
            return this;
        }

        public Builder innerJoin(Class clazz, Field leftField, Field rightField) {
            if (joinWrapper.getConditions() == null) {
                joinWrapper.setConditions(new LinkedHashSet<>());
            }
            joinWrapper.getClasses().add(leftField.getDeclaringClass());
            joinWrapper.getClasses().add(rightField.getDeclaringClass());
            // e.g: demo_b
            String fromTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
            // e.g: demo_b
            String leftTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, leftField.getDeclaringClass().getSimpleName());
            // e.g: a_id
            String leftColumn = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, leftField.getName());
            // e.g: demo_a
            String rightTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, rightField.getDeclaringClass().getSimpleName());
            // e.g: id
            String rightColumn = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, rightField.getName());
            // e.g: inner join demo_b on demo_b.a_id = demo_a.id
            joinWrapper.getConditions().add(INNER_JOIN + SPACE + fromTableName + SPACE + ON + SPACE + leftTableName + DOT + leftColumn + SPACE + EQUAL + SPACE + rightTableName + DOT + rightColumn);
            return this;
        }

        public Builder leftJoin(Class clazz, Field leftField, Field rightField) {
            if (joinWrapper.getConditions() == null) {
                joinWrapper.setConditions(new LinkedHashSet<>());
            }
            joinWrapper.getClasses().add(leftField.getDeclaringClass());
            joinWrapper.getClasses().add(rightField.getDeclaringClass());
            // e.g: demo_b
            String fromTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
            // e.g: demo_b
            String leftTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, leftField.getDeclaringClass().getSimpleName());
            // e.g: a_id
            String leftColumn = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, leftField.getName());
            // e.g: demo_a
            String rightTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, rightField.getDeclaringClass().getSimpleName());
            // e.g: id
            String rightColumn = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, rightField.getName());
            // e.g: left join demo_b on demo_b.a_id = demo_a.id
            joinWrapper.getConditions().add(LEFT_JOIN + SPACE + fromTableName + SPACE + ON + SPACE + leftTableName + DOT + leftColumn + SPACE + EQUAL + SPACE + rightTableName + DOT + rightColumn);
            return this;
        }

        public Builder rightJoin(Class clazz, Field leftField, Field rightField) {
            if (joinWrapper.getConditions() == null) {
                joinWrapper.setConditions(new LinkedHashSet<>());
            }
            joinWrapper.getClasses().add(leftField.getDeclaringClass());
            joinWrapper.getClasses().add(rightField.getDeclaringClass());
            // e.g: demo_b
            String fromTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
            // e.g: demo_b
            String leftTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, leftField.getDeclaringClass().getSimpleName());
            // e.g: a_id
            String leftColumn = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, leftField.getName());
            // e.g: demo_a
            String rightTableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, rightField.getDeclaringClass().getSimpleName());
            // e.g: id
            String rightColumn = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, rightField.getName());
            // e.g: right join demo_b on demo_b.a_id = demo_a.id
            joinWrapper.getConditions().add(RIGHT_JOIN + SPACE + fromTableName + SPACE + ON + SPACE + leftTableName + DOT + leftColumn + SPACE + EQUAL + SPACE + rightTableName + DOT + rightColumn);
            return this;
        }

        public String build() {
            StringBuilder stringBuilder = new StringBuilder(SELECT + SPACE);
            if (joinWrapper.getSelectedColumns() != null && !joinWrapper.getSelectedColumns().isEmpty()) {
                String selectedColumns = String.join(COMMA + SPACE, joinWrapper.getSelectedColumns());
                stringBuilder.append(selectedColumns);
            } else {
                stringBuilder.append(getColumns());
            }
            stringBuilder.append(SPACE).append(FROM).append(SPACE).append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, joinWrapper.getFrom().getSimpleName()));
            if (joinWrapper.getConditions() != null && !joinWrapper.getConditions().isEmpty()) {
                stringBuilder.append(SPACE).append(String.join(SPACE, joinWrapper.getConditions()));
            }
            return stringBuilder.toString();
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
                    stringBuilder.append(COMMA).append(SPACE).append(tableName).append(DOT).append(tableFieldName).append(SPACE).append(AS).append(SPACE).append(alias);
                }
            }
            return stringBuilder.toString().replaceFirst(COMMA + SPACE, EMPTY);
        }

    }
}
