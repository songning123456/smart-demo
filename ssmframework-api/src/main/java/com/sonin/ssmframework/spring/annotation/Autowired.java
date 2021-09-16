package com.sonin.ssmframework.spring.annotation;

import java.lang.annotation.*;

/**
 * @author sonin
 * @date 2021/9/16 8:30
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    String value() default "";

}
