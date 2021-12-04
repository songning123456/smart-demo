package com.sonin.common.tool.query2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.sonin.common.modules.common.service.ICommonSqlService;
import com.sonin.common.tool.util.CustomApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private String prefixSql;
    private QueryWrapper<?> queryWrapper;

    private WhereWrapper() {
        classes = new LinkedHashSet<>();
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

        public Builder where() {
            whereWrapper.setPrefixSql(build());
            whereWrapper.setQueryWrapper(new QueryWrapper<>());
            return this;
        }

        public Builder eq(boolean condition, String column, Object val) {
            whereWrapper.getQueryWrapper().eq(condition, column, val);
            return this;
        }

        public Builder ne(boolean condition, String column, Object val) {
            whereWrapper.getQueryWrapper().ne(condition, column, val);
            return this;
        }

        public Builder gt(boolean condition, String column, Object val) {
            whereWrapper.getQueryWrapper().gt(condition, column, val);
            return this;
        }

        public Builder ge(boolean condition, String column, Object val) {
            whereWrapper.getQueryWrapper().ge(condition, column, val);
            return this;
        }

        public Builder lt(boolean condition, String column, Object val) {
            whereWrapper.getQueryWrapper().lt(condition, column, val);
            return this;
        }

        public Builder le(boolean condition, String column, Object val) {
            whereWrapper.getQueryWrapper().le(condition, column, val);
            return this;
        }

        public Builder between(boolean condition, String column, Object val1, Object val2) {
            whereWrapper.getQueryWrapper().between(condition, column, val1, val2);
            return this;
        }

        public Builder notBetween(boolean condition, String column, Object val1, Object val2) {
            whereWrapper.getQueryWrapper().notBetween(condition, column, val1, val2);
            return this;
        }

        public Builder like(boolean condition, String column, Object val) {
            whereWrapper.getQueryWrapper().like(condition, column, val);
            return this;
        }

        public Builder notLike(boolean condition, String column, Object val) {
            whereWrapper.getQueryWrapper().notLike(condition, column, val);
            return this;
        }

        public Builder likeLeft(boolean condition, String column, Object val) {
            whereWrapper.getQueryWrapper().likeLeft(condition, column, val);
            return this;
        }

        public Builder likeRight(boolean condition, String column, Object val) {
            whereWrapper.getQueryWrapper().likeRight(condition, column, val);
            return this;
        }

        public Builder isNull(boolean condition, String column) {
            whereWrapper.getQueryWrapper().isNull(condition, column);
            return this;
        }

        public Builder isNotNull(boolean condition, String column) {
            whereWrapper.getQueryWrapper().isNotNull(condition, column);
            return this;
        }

        public Builder in(boolean condition, String column, Collection<?> coll) {
            whereWrapper.getQueryWrapper().in(condition, column, coll);
            return this;
        }

        public Builder notIn(boolean condition, String column, Collection<?> coll) {
            whereWrapper.getQueryWrapper().notIn(condition, column, coll);
            return this;
        }

        public Builder inSql(boolean condition, String column, String inValue) {
            whereWrapper.getQueryWrapper().inSql(condition, column, inValue);
            return this;
        }

        public Builder notInSql(boolean condition, String column, String inValue) {
            whereWrapper.getQueryWrapper().notInSql(condition, column, inValue);
            return this;
        }

        public Builder groupBy(boolean condition, String... columns) {
            whereWrapper.getQueryWrapper().groupBy(condition, columns);
            return this;
        }

        public Builder having(boolean condition, String sqlHaving, Object... params) {
            whereWrapper.getQueryWrapper().having(condition, sqlHaving, params);
            return this;
        }

        public Builder or(boolean condition) {
            whereWrapper.getQueryWrapper().or(condition);
            return this;
        }

        public Builder apply(boolean condition, String applySql, Object... value) {
            whereWrapper.getQueryWrapper().apply(condition, applySql, value);
            return this;
        }

        public Builder last(boolean condition, String lastSql) {
            whereWrapper.getQueryWrapper().last(condition, lastSql);
            return this;
        }

        public Builder comment(boolean condition, String comment) {
            whereWrapper.getQueryWrapper().comment(condition, comment);
            return this;
        }

        public Builder exists(boolean condition, String existsSql) {
            whereWrapper.getQueryWrapper().exists(condition, existsSql);
            return this;
        }

        public Builder notExists(boolean condition, String notExistsSql) {
            whereWrapper.getQueryWrapper().notExists(condition, notExistsSql);
            return this;
        }

        public Map<String, Object> queryWrapperForMap() {
            ICommonSqlService commonSqlService = CustomApplicationContext.getBean(ICommonSqlService.class);
            return commonSqlService.queryWrapperForMap(whereWrapper.getPrefixSql(), whereWrapper.getQueryWrapper());
        }

        public Page<Map<String, Object>> queryWrapperForPage(Page<?> page) {
            ICommonSqlService commonSqlService = CustomApplicationContext.getBean(ICommonSqlService.class);
            return commonSqlService.queryWrapperForPage(page, whereWrapper.getPrefixSql(), whereWrapper.getQueryWrapper());
        }

        public List<Map<String, Object>> queryWrapperForList() {
            ICommonSqlService commonSqlService = CustomApplicationContext.getBean(ICommonSqlService.class);
            return commonSqlService.queryWrapperForList(whereWrapper.getPrefixSql(), whereWrapper.getQueryWrapper());
        }

        public Map<String, Object> queryDBForMap(String DBName) throws Exception {
            JdbcTemplate jdbcTemplate;
            if (DBName == null || "".equals(DBName)) {
                jdbcTemplate = CustomApplicationContext.getBean(JdbcTemplate.class);
            } else {
                jdbcTemplate = (JdbcTemplate) CustomApplicationContext.getBean(DBName);
            }
            return jdbcTemplate.queryForMap(createSql());
        }

        public List<Map<String, Object>> queryDBForList(String DBName) throws Exception {
            JdbcTemplate jdbcTemplate;
            if (DBName == null || "".equals(DBName)) {
                jdbcTemplate = CustomApplicationContext.getBean(JdbcTemplate.class);
            } else {
                jdbcTemplate = (JdbcTemplate) CustomApplicationContext.getBean(DBName);
            }
            return jdbcTemplate.queryForList(createSql());
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

        private void sqlInject(String sql) throws Exception {
            Pattern pattern = Pattern.compile("\\b(and|exec|insert|select|drop|grant|alter|delete|update|count|chr|mid|master|truncate|char|declare|or)\\b|(\\*|;|\\+|')");
            Matcher matcher = pattern.matcher(sql.toLowerCase());
            if (matcher.find()) {
                throw new Exception("SQL注入: " + sql);
            }
        }

        private String createSql() throws Exception {
            String prefixSql = whereWrapper.getPrefixSql();
            String suffixSql = whereWrapper.getQueryWrapper().getCustomSqlSegment();
            Map<String, Object> paramNameValuePairs = whereWrapper.getQueryWrapper().getParamNameValuePairs();
            for (Map.Entry<String, Object> item : paramNameValuePairs.entrySet()) {
                Object value = item.getValue();
                sqlInject("" + value);
                if (value instanceof String) {
                    value = "'" + value + "'";
                }
                suffixSql = suffixSql.replaceFirst("#\\{ew\\.paramNameValuePairs\\." + item.getKey() + "}", "" + value);
            }
            return prefixSql + SPACE + suffixSql;
        }

    }

}
