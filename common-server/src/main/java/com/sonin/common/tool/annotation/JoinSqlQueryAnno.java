package com.sonin.common.tool.annotation;

import com.sonin.common.tool.enums.JoinSqlQueryEnum;

import java.lang.annotation.*;

/**
 * @author sonin
 * @date 2021/10/19 14:34
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JoinSqlQueryAnno {

    JoinSqlQueryEnum joinSqlQueryEnum() default JoinSqlQueryEnum.EQ;

    boolean isUsed() default true;

}
