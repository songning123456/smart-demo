package com.sonin.common.tool.enums;

/**
 * @author sonin
 * @date 2021/10/19 14:35
 */
public enum JoinSqlQueryEnum {

    EQ(" and ${var0} = '${val0}'"),

    LIKE(" and ${var0} like CONCAT('%','${val0}','%')");

    private String sql;

    JoinSqlQueryEnum(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

}
