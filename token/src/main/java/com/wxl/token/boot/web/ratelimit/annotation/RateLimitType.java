package com.wxl.token.boot.web.ratelimit.annotation;

import com.wxl.token.boot.exception.BizErrorCode;
import com.wxl.token.boot.web.AuthorizationInterceptor;
import com.wxl.token.utils.IPUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public enum RateLimitType {

    /**
     * IP请求控制
     */
    IP(BizErrorCode.SYS_RATE_LIMIT_USER),

    /**
     * 用户请求控制
     */
    USER(BizErrorCode.SYS_RATE_LIMIT_IP),

    /**
     * api请求控制
     */
    PARAM(BizErrorCode.SYS_RATE_LIMIT_API);

    BizErrorCode bizErrorCode;

    RateLimitType(BizErrorCode bizErrorCode) {
        this.bizErrorCode = bizErrorCode;
    }

    public BizErrorCode getBizErrorCode() {
        return bizErrorCode;
    }

    public String getParamString(HttpServletRequest request) {
        switch (this) {
            case IP:
                return "i_" + IPUtils.getIpAddr(request);
            case USER:
                return "u_" + request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_KEY);
            case PARAM:
                return "p_" + getRequestParamString(request.getParameterMap());
            default:
                return "error";
        }
    }

    private String getRequestParamString(Map<String, String[]> parameters) {
        StringBuilder paramBuf = new StringBuilder();

        for (Map.Entry<String, String[]> e : parameters.entrySet()) {
            String key = e.getKey();
            String[] values = e.getValue();
            for (String value : values) {
                paramBuf.append(key).append(":").append(value);
                paramBuf.append("_");
            }
        }
        if (paramBuf.length() > 0 && paramBuf.charAt(paramBuf.length() - 1) == '_') {
            paramBuf.deleteCharAt(paramBuf.length() - 1);
        }
        return paramBuf.toString();
    }


}
