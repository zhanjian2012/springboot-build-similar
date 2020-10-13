package com.springboot.similar.annoation.springmvc;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostMapping {
    String value() default "";
}
