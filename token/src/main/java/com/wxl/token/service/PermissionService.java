package com.wxl.token.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wxl.token.boot.exception.BizException;
import com.wxl.token.enums.CommonConstant;
import com.wxl.token.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 查询企业用户具有的权限值
     * @param userId 用户id
     * @return 权限值
     */
    public List<String> findPermsByUserId(String userId) {
        String json = stringRedisTemplate.opsForValue().get(CommonConstant.REST_PERMISSION_PREFIX + userId);
        List<String> perms;
        if (json == null) {
            perms = permissionMapper.selectPermsByUserId(userId);
            try {
                json = objectMapper.writeValueAsString(perms);
            } catch (JsonProcessingException e) {
                throw new BizException(e.getMessage());
            }
            stringRedisTemplate.opsForValue().set(CommonConstant.REST_PERMISSION_PREFIX + userId, json,
                    CommonConstant.TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        } else {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, String.class);
            try {
                perms = objectMapper.readValue(json, type);
            } catch (IOException e) {
                throw new BizException(e.getMessage());
            }
        }
        return perms;
    }
}
