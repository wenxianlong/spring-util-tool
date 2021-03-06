package com.wxl.token.controller;

import com.wxl.token.boot.web.ratelimit.annotation.RateLimit;
import com.wxl.token.boot.web.ratelimit.annotation.RateLimitRateConfig;
import com.wxl.token.boot.web.ratelimit.annotation.RateLimitType;
import com.wxl.token.boot.web.ratelimit.annotation.RateLimitTypeConfig;
import com.wxl.token.entity.LoginInfo;
import com.wxl.token.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.concurrent.TimeUnit;

/**
 * @author wenxianlong
 * @date 2021/12/27 7:51 下午
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @RateLimit({
            @RateLimitTypeConfig(value = RateLimitType.IP, rates = {
                    @RateLimitRateConfig(value = 10, time = TimeUnit.MINUTES)})
    })
    public LoginInfo login(@RequestParam(value = "loginName") @NotEmpty(message = "登录账号不能为空") String loginName,
                           @RequestParam(value = "password") @NotEmpty(message = "密码不能为空") String password) {
        return userService.login(loginName, password);
    }
}
