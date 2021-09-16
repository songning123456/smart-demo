package com.sonin.ssmframework.spring.annotation;

import java.lang.annotation.*;

/**
 * @author sonin
 * @date 2021/9/16 8:32
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    String value() default "";

}
