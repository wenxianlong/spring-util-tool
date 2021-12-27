package com.wxl.token.boot.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * jwt配置信息
 */
@Data
@Repository
public class JwtConfig {

    @Value("${jwt.base64.secret}")
    private String base64Secret;

    @Value("${jwt.refresh.base64.secret}")
    private String refreshBase64Secret;

    @Value("${jwt.expires.second}")
    private long expiresSecond;

    @Value("${jwt.refresh.expires.second}")
    private long refreshExpiresSecond;
}
