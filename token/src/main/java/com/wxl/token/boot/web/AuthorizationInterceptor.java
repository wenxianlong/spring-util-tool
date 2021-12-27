package com.wxl.token.boot.web;

import com.wxl.token.boot.exception.BizErrorCode;
import com.wxl.token.boot.exception.BizException;
import com.wxl.token.boot.jwt.TokenManager;
import com.wxl.token.boot.web.ratelimit.annotation.RateLimit;
import com.wxl.token.boot.web.ratelimit.service.RateLimitAuthService;
import com.wxl.token.boot.web.user.annotation.AuthRequired;
import com.wxl.token.enums.CommonConstant;
import com.wxl.token.service.PermissionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 自定义拦截器，对请求进行身份验证
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    /**
     * 存放登录用户模型Key的Request Key
     */
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    public static final String REQUEST_CURRENT_USER_TYPE = "REQUEST_CURRENT_USER_TYPE";

    public final static String HEAD_PARAM_AUTHORIZATION = "authorization";

    @Autowired
    private RateLimitAuthService rateLimitAuthService;

    //管理身份验证操作的对象
    @Autowired
    private TokenManager manager;

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AuthRequired tag = null;
        if (handlerMethod.getBeanType().isAnnotationPresent(AuthRequired.class)) {
            tag = handlerMethod.getBeanType().getAnnotation(AuthRequired.class);
        }
        if (method.isAnnotationPresent(AuthRequired.class)) {
            tag = method.getAnnotation(AuthRequired.class);
        }

        // 无需登录
        if (tag == null || !tag.required()) {
            // 流量控制
            RateLimit rateLimit = null;
            if (method.isAnnotationPresent(RateLimit.class)) {
                rateLimit = method.getAnnotation(RateLimit.class);
            }
            // 用于判断内网外网（Nginx配置添加Header）
            if (rateLimit != null && (!"INNER".equals(request.getHeader("X-Engine-From")) || !rateLimit.internalIgnore())) {
                rateLimitAuthService.auth(request, rateLimit);
            }
            // 为了防止以恶意操作直接在REQUEST_CURRENT_KEY写入key，将其设为null
            request.setAttribute(REQUEST_CURRENT_KEY, null);
            return true;
        }

        // 登录权限认证
        String token = request.getHeader(HEAD_PARAM_AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            throw new BizException(BizErrorCode.USER_UNAUTHORIZED);
        }

        String key = manager.getKey(token);
        if (StringUtils.isBlank(key)) {
            throw new BizException(BizErrorCode.USER_UNAUTHORIZED);
        }

        String userType = key.split(":")[0];
        String userId = key.split(":")[1];
        request.setAttribute(REQUEST_CURRENT_USER_TYPE, userType);
        request.setAttribute(REQUEST_CURRENT_KEY, userId);
        String[] any = tag.anyPermission();
        String[] all = tag.allPermission();
        if (any.length == 0 && all.length == 0) {
            return true;
        }

        if (CommonConstant.USER_TYPE_PERSONAL.equals(userType)) {
            throw new BizException("未授权");
        }

        List<String> perms = permissionService.findPermsByUserId(userId);

        for (String perm : all) {
            if (!perms.contains(perm)) {
                throw new BizException("未授权");
            }
        }

        for (String perm : any) {
            if (perms.contains(perm)) {
                return true;
            }
        }
        throw new BizException("未授权");
    }
}
