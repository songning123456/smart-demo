package com.sonin.common.tool.annotation;

import java.lang.annotation.*;

/**
 * @author sonin
 * @date 2021/10/17 13:23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface SqlAnno {

    String primaryKey() default "id";

    Class<?> targetClass() default Object.class;

    String foreignKey() default "";

}
