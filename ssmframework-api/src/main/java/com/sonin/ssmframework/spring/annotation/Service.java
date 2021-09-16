package com.sonin.ssmframework.spring.annotation;

import java.lang.annotation.*;

/**
 * @author sonin
 * @date 2021/9/16 8:33
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {

    String value() default "";

}
