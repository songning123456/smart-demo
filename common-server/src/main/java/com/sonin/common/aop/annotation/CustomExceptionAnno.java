package com.sonin.common.aop.annotation;

import java.lang.annotation.*;

/**
 * @author sonin
 * @date 2021/9/23 19:08
 * 对controller层统一实现try...catch...拦截
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface CustomExceptionAnno {

    String description() default "";

}
