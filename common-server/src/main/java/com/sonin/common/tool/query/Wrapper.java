package com.sonin.common.tool.query;

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

/**
 * @author sonin
 * @date 2021/12/4 19:27
 */
public abstract class Wrapper implements IWrapper {

    /**
     * SQL拼接语句
     */
    Collection<Class> classes;

    Collection<String> conditions;

    Collection<String> selectedColumns;

    private String prefixSql;

    private QueryWrapper<?> queryWrapper;

    /**
     * 构造返回字段
     *
     * @return
     */
    String initColumns() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Class clazz : this.classes) {
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

    /**
     * 判断SQL注入
     *
     * @param sql
     * @throws Exception
     */
    private void sqlInject(String sql) throws Exception {
        Pattern pattern = Pattern.compile("\\b(and|exec|insert|select|drop|grant|alter|delete|update|count|chr|mid|master|truncate|char|declare|or)\\b|(\\*|;|\\+|')");
        Matcher matcher = pattern.matcher(sql.toLowerCase());
        if (matcher.find()) {
            throw new Exception("SQL注入: " + sql);
        }
    }

    /**
     * 构造完整SQL
     *
     * @return
     * @throws Exception
     */
    private String initSql() throws Exception {
        String suffixSql = this.queryWrapper.getCustomSqlSegment();
        Map<String, Object> paramNameValuePairs = this.queryWrapper.getParamNameValuePairs();
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

    public Wrapper select(Field... fields) {
        if (this.selectedColumns == null) {
            this.selectedColumns = new LinkedHashSet<>();
        }
        for (Field field : fields) {
            String className = field.getDeclaringClass().getSimpleName();
            String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
            String fieldName = field.getName();
            String column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
            String alias = tableName + DOT + column + SPACE + AS + SPACE + className + UNDERLINE + fieldName;
            this.selectedColumns.add(alias);
        }
        return this;
    }

    public Wrapper select(String... fields) {
        if (this.selectedColumns == null) {
            this.selectedColumns = new LinkedHashSet<>();
        }
        this.selectedColumns.addAll(Arrays.asList(fields));
        return this;
    }

    /**
     * 构造前缀SQL
     *
     * @return
     */
    abstract String initPrefixSql();

    public Wrapper where() {
        this.prefixSql = initPrefixSql();
        this.queryWrapper = new QueryWrapper<>();
        return this;
    }

    public Wrapper eq(boolean condition, String column, Object val) {
        this.queryWrapper.eq(condition, column, val);
        return this;
    }

    public Wrapper ne(boolean condition, String column, Object val) {
        this.queryWrapper.ne(condition, column, val);
        return this;
    }

    public Wrapper gt(boolean condition, String column, Object val) {
        this.queryWrapper.gt(condition, column, val);
        return this;
    }

    public Wrapper ge(boolean condition, String column, Object val) {
        this.queryWrapper.ge(condition, column, val);
        return this;
    }

    public Wrapper lt(boolean condition, String column, Object val) {
        this.queryWrapper.lt(condition, column, val);
        return this;
    }

    public Wrapper le(boolean condition, String column, Object val) {
        this.queryWrapper.le(condition, column, val);
        return this;
    }

    public Wrapper between(boolean condition, String column, Object val1, Object val2) {
        this.queryWrapper.between(condition, column, val1, val2);
        return this;
    }

    public Wrapper notBetween(boolean condition, String column, Object val1, Object val2) {
        this.queryWrapper.notBetween(condition, column, val1, val2);
        return this;
    }

    public Wrapper like(boolean condition, String column, Object val) {
        this.queryWrapper.like(condition, column, val);
        return this;
    }

    public Wrapper notLike(boolean condition, String column, Object val) {
        this.queryWrapper.notLike(condition, column, val);
        return this;
    }

    public Wrapper likeLeft(boolean condition, String column, Object val) {
        this.queryWrapper.likeLeft(condition, column, val);
        return this;
    }

    public Wrapper likeRight(boolean condition, String column, Object val) {
        this.queryWrapper.likeRight(condition, column, val);
        return this;
    }

    public Wrapper isNull(boolean condition, String column) {
        this.queryWrapper.isNull(condition, column);
        return this;
    }

    public Wrapper isNotNull(boolean condition, String column) {
        this.queryWrapper.isNotNull(condition, column);
        return this;
    }

    public Wrapper in(boolean condition, String column, Collection<?> coll) {
        this.queryWrapper.in(condition, column, coll);
        return this;
    }

    public Wrapper notIn(boolean condition, String column, Collection<?> coll) {
        this.queryWrapper.notIn(condition, column, coll);
        return this;
    }

    public Wrapper inSql(boolean condition, String column, String inValue) {
        this.queryWrapper.inSql(condition, column, inValue);
        return this;
    }

    public Wrapper notInSql(boolean condition, String column, String inValue) {
        this.queryWrapper.notInSql(condition, column, inValue);
        return this;
    }

    public Wrapper groupBy(boolean condition, String... columns) {
        this.queryWrapper.groupBy(condition, columns);
        return this;
    }

    public Wrapper having(boolean condition, String sqlHaving, Object... params) {
        this.queryWrapper.having(condition, sqlHaving, params);
        return this;
    }

    public Wrapper or(boolean condition) {
        this.queryWrapper.or(condition);
        return this;
    }

    public Wrapper apply(boolean condition, String applySql, Object... value) {
        this.queryWrapper.apply(condition, applySql, value);
        return this;
    }

    public Wrapper last(boolean condition, String lastSql) {
        this.queryWrapper.last(condition, lastSql);
        return this;
    }

    public Wrapper comment(boolean condition, String comment) {
        this.queryWrapper.comment(condition, comment);
        return this;
    }

    public Wrapper exists(boolean condition, String existsSql) {
        this.queryWrapper.exists(condition, existsSql);
        return this;
    }

    public Wrapper notExists(boolean condition, String notExistsSql) {
        this.queryWrapper.notExists(condition, notExistsSql);
        return this;
    }

    public Map<String, Object> queryWrapperForMap() {
        ICommonSqlService commonSqlService = CustomApplicationContext.getBean(ICommonSqlService.class);
        return commonSqlService.queryWrapperForMap(this.prefixSql, this.queryWrapper);
    }

    public Page<Map<String, Object>> queryWrapperForPage(Page<?> page) {
        ICommonSqlService commonSqlService = CustomApplicationContext.getBean(ICommonSqlService.class);
        return commonSqlService.queryWrapperForPage(page, this.prefixSql, this.queryWrapper);
    }

    public List<Map<String, Object>> queryWrapperForList() {
        ICommonSqlService commonSqlService = CustomApplicationContext.getBean(ICommonSqlService.class);
        return commonSqlService.queryWrapperForList(this.prefixSql, this.queryWrapper);
    }

    public Map<String, Object> queryDBForMap() throws Exception {
        JdbcTemplate jdbcTemplate = CustomApplicationContext.getBean(JdbcTemplate.class);
        return jdbcTemplate.queryForMap(initSql());
    }

    public Map<String, Object> queryDBForMap(String DBName) throws Exception {
        JdbcTemplate jdbcTemplate = (JdbcTemplate) CustomApplicationContext.getBean(DBName);
        return jdbcTemplate.queryForMap(initSql());
    }

    public List<Map<String, Object>> queryDBForList() throws Exception {
        JdbcTemplate jdbcTemplate = CustomApplicationContext.getBean(JdbcTemplate.class);
        return jdbcTemplate.queryForList(initSql());
    }

    public List<Map<String, Object>> queryDBForList(String DBName) throws Exception {
        JdbcTemplate jdbcTemplate = (JdbcTemplate) CustomApplicationContext.getBean(DBName);
        return jdbcTemplate.queryForList(initSql());
    }

}
