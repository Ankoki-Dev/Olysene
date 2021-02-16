package com.ankoki.olysene.utils.annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Builder {
    //public <T> T type();
}
