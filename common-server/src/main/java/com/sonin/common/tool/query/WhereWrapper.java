package com.sonin.common.tool.query;

import com.google.common.base.CaseFormat;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * @author sonin
 * @date 2021/12/4 19:37
 */
public class WhereWrapper extends Wrapper {

    public WhereWrapper() {
        this.classes = new LinkedHashSet<>();
    }

    public WhereWrapper from(Class... classes) {
        this.classes.addAll(Arrays.asList(classes));
        return this;
    }

    public WhereWrapper and(Field leftField, Field rightField) {
        if (this.conditions == null) {
            this.conditions = new LinkedHashSet<>();
        }
        this.classes.add(leftField.getDeclaringClass());
        this.classes.add(rightField.getDeclaringClass());
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
        this.conditions.add(leftTableName + DOT + leftTableFieldName + SPACE + EQUAL + SPACE + rightTableName + DOT + rightTableFieldName);
        return this;
    }

    @Override
    public String build() {
        String sql = "select ${var0} from (select ${var1} from ${var2} where ${var3}) as ${var4}";
        String allClassName = this.classes.stream().map(Class::getSimpleName).collect(Collectors.joining(UNDERLINE));
        sql = sql.replaceFirst("\\$\\{var0}", allClassName + DOT + ALL);
        if (this.selectedColumns != null && !this.selectedColumns.isEmpty()) {
            String selectedColumns = String.join(COMMA + SPACE, this.selectedColumns);
            sql = sql.replaceFirst("\\$\\{var1}", selectedColumns);
        } else {
            sql = sql.replaceFirst("\\$\\{var1}", getColumns());
        }
        String tables = this.classes.stream().map(clazz -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName())).collect(Collectors.joining(COMMA + SPACE));
        sql = sql.replaceFirst("\\$\\{var2}", tables);
        if (this.conditions != null && !this.conditions.isEmpty()) {
            sql = sql.replaceFirst("\\$\\{var3}", String.join(SPACE + AND + SPACE, this.conditions));
        } else {
            sql = sql.replaceFirst(SPACE + WHERE + SPACE + "\\$\\{var3}", EMPTY);
        }
        return sql.replaceFirst("\\$\\{var4}", allClassName);
    }

}
