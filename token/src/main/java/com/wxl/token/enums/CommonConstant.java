package com.wxl.token.enums;

/**
 * @author wenxianlong
 * @date 2021/12/27 8:04 下午
 */
public interface CommonConstant {

    /**
     * 用户类型 1个人用户 2企业用户 3后台用户
     */
    String USER_TYPE_PERSONAL = "1";
    String USER_TYPE_COMPANY = "2";
    String USER_TYPE_ADMIN = "3";

    /**
     * 是否用户已被冻结 1(解冻)正常 2冻结
     */
    Integer USER_UNFREEZE = 1;
    Integer USER_FREEZE = 2;

    /**
     * 登录用户权限缓存前缀
     */
    String REST_PERMISSION_PREFIX = "rest_login_user:permission:";

    /**
     * 登录用户令牌缓存KEY前缀
     */
    int TOKEN_EXPIRE_TIME = 3600; //3600秒即是一小时
}
