package com.sonin.ssmframework.spring.annotation;

import java.lang.annotation.*;

/**
 * @author sonin
 * @date 2021/9/16 8:32
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value() default "";

}
