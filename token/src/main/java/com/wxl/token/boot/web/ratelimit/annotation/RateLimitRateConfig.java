package com.wxl.token.boot.web.ratelimit.annotation;

import java.util.concurrent.TimeUnit;

public @interface RateLimitRateConfig {
    /**
     * 单位时间最大请求数
     */
    int value();

    TimeUnit time() default TimeUnit.HOURS;
}
