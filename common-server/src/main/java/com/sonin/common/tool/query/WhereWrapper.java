package com.sonin.common.tool.query;

import com.google.common.base.CaseFormat;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * @author sonin
 * @date 2021/11/26 19:35
 * QueryWrapper条件e.g: DemoB_bName = xxx
 */
public class WhereWrapper implements Wrapper {

    private Collection<Class> classes;
    private Collection<String> conditions;
    private Collection<String> selectedColumns;

    private WhereWrapper() {
        classes = new LinkedHashSet<>();
    }

    private Collection<Class> getClasses() {
        return classes;
    }

    private Collection<String> getConditions() {
        return conditions;
    }

    private void setConditions(Collection<String> conditions) {
        this.conditions = conditions;
    }

    private Collection<String> getSelectedColumns() {
        return selectedColumns;
    }

    private void setSelectedColumns(Collection<String> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public static class Builder {

        private final WhereWrapper whereWrapper;

        public Builder() {
            whereWrapper = new WhereWrapper();
        }

        public Builder select(Field... fields) {
            if (whereWrapper.getSelectedColumns() == null) {
                whereWrapper.setSelectedColumns(new LinkedHashSet<>());
            }
            for (Field field : fields) {
                String className = field.getDeclaringClass().getSimpleName();
                String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
                String fieldName = field.getName();
                String column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
                String alias = tableName + DOT + column + SPACE + AS + SPACE + className + UNDERLINE + fieldName;
                whereWrapper.getSelectedColumns().add(alias);
            }
            return this;
        }

        public Builder select(String... fields) {
            if (whereWrapper.getSelectedColumns() == null) {
                whereWrapper.setSelectedColumns(new LinkedHashSet<>());
            }
            whereWrapper.getSelectedColumns().addAll(Arrays.asList(fields));
            return this;
        }

        public Builder from(Class... classes) {
            whereWrapper.getClasses().addAll(Arrays.asList(classes));
            return this;
        }

        public Builder and(Field leftField, Field rightField) {
            if (whereWrapper.getConditions() == null) {
                whereWrapper.setConditions(new LinkedHashSet<>());
            }
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
            // e.g: demo_a.id = demo_b.a_id
            whereWrapper.getConditions().add(leftTableName + DOT + leftTableFieldName + SPACE + EQUAL + SPACE + rightTableName + DOT + rightTableFieldName);
            return this;
        }

        public String build() {
            String sql = "select ${var0} from (select ${var1} from ${var2} where ${var3}) as ${var4}";
            String allClassName = whereWrapper.getClasses().stream().map(Class::getSimpleName).collect(Collectors.joining(UNDERLINE));
            sql = sql.replaceFirst("\\$\\{var0}", allClassName + DOT + ALL);
            if (whereWrapper.getSelectedColumns() != null && !whereWrapper.getSelectedColumns().isEmpty()) {
                String selectedColumns = String.join(COMMA + SPACE, whereWrapper.getSelectedColumns());
                sql = sql.replaceFirst("\\$\\{var1}", selectedColumns);
            } else {
                sql = sql.replaceFirst("\\$\\{var1}", getColumns());
            }
            String tables = whereWrapper.getClasses().stream().map(clazz -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName())).collect(Collectors.joining(COMMA + SPACE));
            sql = sql.replaceFirst("\\$\\{var2}", tables);
            if (whereWrapper.getConditions() != null && !whereWrapper.getConditions().isEmpty()) {
                sql = sql.replaceFirst("\\$\\{var3}", String.join(SPACE + AND + SPACE, whereWrapper.getConditions()));
            } else {
                sql = sql.replaceFirst(SPACE + WHERE + SPACE + "\\$\\{var3}", EMPTY);
            }
            return sql.replaceFirst("\\$\\{var4}", allClassName);
        }

        private String getColumns() {
            StringBuilder stringBuilder = new StringBuilder();
            for (Class clazz : whereWrapper.getClasses()) {
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
