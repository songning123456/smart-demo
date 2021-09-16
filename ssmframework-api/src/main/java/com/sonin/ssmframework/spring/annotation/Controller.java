package com.sonin.ssmframework.spring.annotation;

import java.lang.annotation.*;

/**
 * @author sonin
 * @date 2021/9/16 8:31
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {

    String value() default "";

}
