package com.wxl.token.boot.web.ratelimit.service;

import com.wxl.token.boot.exception.BizException;
import com.wxl.token.boot.web.ratelimit.annotation.RateLimit;
import com.wxl.token.boot.web.ratelimit.annotation.RateLimitRateConfig;
import com.wxl.token.boot.web.ratelimit.annotation.RateLimitTypeConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimitAuthService {
    private static final String RATE_KEY_PREFIX = "rate_";
    @Autowired
    private RedisTemplate redisTemplate;

    public void auth(HttpServletRequest request, RateLimit rateLimit) throws BizException {
        RateLimitTypeConfig[] configs = rateLimit.value();

        Calendar calendar = Calendar.getInstance();
        for (RateLimitTypeConfig config : configs) {
            for (RateLimitRateConfig rateConfig : config.rates()) {
                Long count = incrCount(rateConfig.time(), calendar, config.value().getParamString(request));
                if (count > rateConfig.value()) {
                    throw new BizException(config.value().getBizErrorCode());
                }
            }
        }
    }

    private long incrCount(TimeUnit time, Calendar calendar, String params) {
        String key = RATE_KEY_PREFIX + StringUtils.substring(time.name(), 0, 1).toLowerCase() + "_" + getCurrentTimeNum(calendar, time) + "_" + params;
        Long result =redisTemplate.opsForValue().increment(key) ;
        if (result != null && result <= 2) {
            redisTemplate.expire(key,(int)(time.toSeconds(1) + 10),TimeUnit.SECONDS);
        }
        return result != null ? result : 0;
    }


    public int getCurrentTimeNum(TimeUnit timeUnit) {
        return getCurrentTimeNum(Calendar.getInstance(), timeUnit);
    }

    public int getCurrentTimeNum(Calendar calendar, TimeUnit timeUnit) {
        int result;
        switch (timeUnit) {
            case DAYS:
                result = calendar.get(Calendar.DAY_OF_YEAR);
                break;
            case HOURS:
                result = calendar.get(Calendar.HOUR_OF_DAY);
                break;
            case MINUTES:
                result = calendar.get(Calendar.MINUTE);
                break;
            case SECONDS:
                result = calendar.get(Calendar.SECOND);
                break;
            case MILLISECONDS:
                result = calendar.get(Calendar.MILLISECOND);
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }
}
