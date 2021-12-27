package com.wxl.token.entity;

import lombok.Data;

/**
 * @author wenxianlong
 * @date 2021/12/27 7:53 下午
 */
@Data
public class LoginInfo {

    private String token;

    private User user;
}
