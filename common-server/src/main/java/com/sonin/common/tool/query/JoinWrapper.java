package com.sonin.common.tool.query;

import com.google.common.base.CaseFormat;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;

/**
 * @author sonin
 * @date 2021/12/4 19:31
 */
public class JoinWrapper extends Wrapper {

    private Class from;

    public JoinWrapper() {
        this.classes = new LinkedHashSet<>();
    }

    public JoinWrapper from(Class clazz) {
        this.from = clazz;
        this.classes.add(clazz);
        return this;
    }

    public JoinWrapper innerJoin(Class clazz, Field leftField, Field rightField) {
        if (this.conditions == null) {
            this.conditions = new LinkedHashSet<>();
        }
        this.classes.add(leftField.getDeclaringClass());
        this.classes.add(rightField.getDeclaringClass());
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
        this.conditions.add(INNER_JOIN + SPACE + fromTableName + SPACE + ON + SPACE + leftTableName + DOT + leftColumn + SPACE + EQUAL + SPACE + rightTableName + DOT + rightColumn);
        return this;
    }

    public JoinWrapper leftJoin(Class clazz, Field leftField, Field rightField) {
        if (this.conditions == null) {
            this.conditions = new LinkedHashSet<>();
        }
        this.classes.add(leftField.getDeclaringClass());
        this.classes.add(rightField.getDeclaringClass());
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
        this.conditions.add(LEFT_JOIN + SPACE + fromTableName + SPACE + ON + SPACE + leftTableName + DOT + leftColumn + SPACE + EQUAL + SPACE + rightTableName + DOT + rightColumn);
        return this;
    }

    public JoinWrapper rightJoin(Class clazz, Field leftField, Field rightField) {
        if (this.conditions == null) {
            this.conditions = new LinkedHashSet<>();
        }
        this.classes.add(leftField.getDeclaringClass());
        this.classes.add(rightField.getDeclaringClass());
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
        this.conditions.add(RIGHT_JOIN + SPACE + fromTableName + SPACE + ON + SPACE + leftTableName + DOT + leftColumn + SPACE + EQUAL + SPACE + rightTableName + DOT + rightColumn);
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder(SELECT + SPACE);
        if (this.selectedColumns != null && !this.selectedColumns.isEmpty()) {
            String selectedColumns = String.join(COMMA + SPACE, this.selectedColumns);
            stringBuilder.append(selectedColumns);
        } else {
            stringBuilder.append(getColumns());
        }
        stringBuilder.append(SPACE).append(FROM).append(SPACE).append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.from.getSimpleName()));
        if (this.conditions != null && !this.conditions.isEmpty()) {
            stringBuilder.append(SPACE).append(String.join(SPACE, this.conditions));
        }
        return stringBuilder.toString();
    }

}
