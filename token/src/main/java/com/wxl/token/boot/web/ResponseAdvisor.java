package com.wxl.token.boot.web;

import com.alibaba.fastjson.JSON;
import com.wxl.token.utils.Result;
import com.wxl.token.utils.ResultVM;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created  on 2016/07/04.
 *
 * @author zhangxiangyang
 */
@RestControllerAdvice
public class ResponseAdvisor implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String returnTypeClassName = returnType.getParameterType().getName();
        Class clazz = null;
        try {
            clazz = Thread.currentThread().getContextClassLoader().loadClass(returnTypeClassName);
        } catch (ClassNotFoundException e) {
            // ignore exception
        }
        if (clazz == null || clazz == HttpEntity.class) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (body instanceof ResultVM) {
            return body;
        } else if (body instanceof Result) {
            return body;
        } else {
            if (body instanceof String) {
                return JSON.toJSONString(ResultVM.ok(body));
            }
            return ResultVM.ok(body);
        }
    }
}
