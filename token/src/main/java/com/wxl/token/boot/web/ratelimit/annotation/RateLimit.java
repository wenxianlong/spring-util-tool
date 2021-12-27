package com.wxl.token.boot.web.ratelimit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimit {

    boolean internalIgnore() default true; //内网默认不拦截

    RateLimitTypeConfig[] value();

}
