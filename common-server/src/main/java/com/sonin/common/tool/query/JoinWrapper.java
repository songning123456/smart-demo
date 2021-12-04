package com.sonin.common.tool.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.sonin.common.modules.common.service.ICommonSqlService;
import com.sonin.common.tool.util.CustomApplicationContext;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author sonin
 * @date 2021/11/26 8:02
 * QueryWrapper条件e.g: demo_b.b_name = xxx
 */
public class JoinWrapper implements Wrapper {

    private Class from;
    private Collection<Class> classes;
    private Collection<String> conditions;
    private Collection<String> selectedColumns;
    private String prefixSql;
    private QueryWrapper<?> queryWrapper;

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

    private String getPrefixSql() {
        return prefixSql;
    }

    private QueryWrapper<?> getQueryWrapper() {
        return queryWrapper;
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

    private void setPrefixSql(String prefixSql) {
        this.prefixSql = prefixSql;
    }

    private void setQueryWrapper(QueryWrapper<?> queryWrapper) {
        this.queryWrapper = queryWrapper;
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

        public Builder where() {
            joinWrapper.setPrefixSql(build());
            joinWrapper.setQueryWrapper(new QueryWrapper<>());
            return this;
        }

        public Builder eq(boolean condition, String column, Object val) {
            joinWrapper.getQueryWrapper().eq(condition, column, val);
            return this;
        }

        public Builder ne(boolean condition, String column, Object val) {
            joinWrapper.getQueryWrapper().ne(condition, column, val);
            return this;
        }

        public Builder gt(boolean condition, String column, Object val) {
            joinWrapper.getQueryWrapper().gt(condition, column, val);
            return this;
        }

        public Builder ge(boolean condition, String column, Object val) {
            joinWrapper.getQueryWrapper().ge(condition, column, val);
            return this;
        }

        public Builder lt(boolean condition, String column, Object val) {
            joinWrapper.getQueryWrapper().lt(condition, column, val);
            return this;
        }

        public Builder le(boolean condition, String column, Object val) {
            joinWrapper.getQueryWrapper().le(condition, column, val);
            return this;
        }

        public Builder between(boolean condition, String column, Object val1, Object val2) {
            joinWrapper.getQueryWrapper().between(condition, column, val1, val2);
            return this;
        }

        public Builder notBetween(boolean condition, String column, Object val1, Object val2) {
            joinWrapper.getQueryWrapper().notBetween(condition, column, val1, val2);
            return this;
        }

        public Builder like(boolean condition, String column, Object val) {
            joinWrapper.getQueryWrapper().like(condition, column, val);
            return this;
        }

        public Builder notLike(boolean condition, String column, Object val) {
            joinWrapper.getQueryWrapper().notLike(condition, column, val);
            return this;
        }

        public Builder likeLeft(boolean condition, String column, Object val) {
            joinWrapper.getQueryWrapper().likeLeft(condition, column, val);
            return this;
        }

        public Builder likeRight(boolean condition, String column, Object val) {
            joinWrapper.getQueryWrapper().likeRight(condition, column, val);
            return this;
        }

        public Builder isNull(boolean condition, String column) {
            joinWrapper.getQueryWrapper().isNull(condition, column);
            return this;
        }

        public Builder isNotNull(boolean condition, String column) {
            joinWrapper.getQueryWrapper().isNotNull(condition, column);
            return this;
        }

        public Builder in(boolean condition, String column, Collection<?> coll) {
            joinWrapper.getQueryWrapper().in(condition, column, coll);
            return this;
        }

        public Builder notIn(boolean condition, String column, Collection<?> coll) {
            joinWrapper.getQueryWrapper().notIn(condition, column, coll);
            return this;
        }

        public Builder inSql(boolean condition, String column, String inValue) {
            joinWrapper.getQueryWrapper().inSql(condition, column, inValue);
            return this;
        }

        public Builder notInSql(boolean condition, String column, String inValue) {
            joinWrapper.getQueryWrapper().notInSql(condition, column, inValue);
            return this;
        }

        public Builder groupBy(boolean condition, String... columns) {
            joinWrapper.getQueryWrapper().groupBy(condition, columns);
            return this;
        }

        public Builder having(boolean condition, String sqlHaving, Object... params) {
            joinWrapper.getQueryWrapper().having(condition, sqlHaving, params);
            return this;
        }

        public Builder or(boolean condition) {
            joinWrapper.getQueryWrapper().or(condition);
            return this;
        }

        public Builder apply(boolean condition, String applySql, Object... value) {
            joinWrapper.getQueryWrapper().apply(condition, applySql, value);
            return this;
        }

        public Builder last(boolean condition, String lastSql) {
            joinWrapper.getQueryWrapper().last(condition, lastSql);
            return this;
        }

        public Builder comment(boolean condition, String comment) {
            joinWrapper.getQueryWrapper().comment(condition, comment);
            return this;
        }

        public Builder exists(boolean condition, String existsSql) {
            joinWrapper.getQueryWrapper().exists(condition, existsSql);
            return this;
        }

        public Builder notExists(boolean condition, String notExistsSql) {
            joinWrapper.getQueryWrapper().notExists(condition, notExistsSql);
            return this;
        }

        public Map<String, Object> queryWrapperForMap() {
            ICommonSqlService commonSqlService = CustomApplicationContext.getBean(ICommonSqlService.class);
            return commonSqlService.queryWrapperForMap(joinWrapper.getPrefixSql(), joinWrapper.getQueryWrapper());
        }

        public Page<Map<String, Object>> queryWrapperForPage(Page<?> page) {
            ICommonSqlService commonSqlService = CustomApplicationContext.getBean(ICommonSqlService.class);
            return commonSqlService.queryWrapperForPage(page, joinWrapper.getPrefixSql(), joinWrapper.getQueryWrapper());
        }

        public List<Map<String, Object>> queryWrapperForList() {
            ICommonSqlService commonSqlService = CustomApplicationContext.getBean(ICommonSqlService.class);
            return commonSqlService.queryWrapperForList(joinWrapper.getPrefixSql(), joinWrapper.getQueryWrapper());
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
