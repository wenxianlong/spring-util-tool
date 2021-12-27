package com.wxl.token.boot.exception;


public enum BizErrorCode implements BaseErrorCode {
    USER_UNAUTHORIZED(401, "未授权"),
    SYS_RATE_LIMIT_USER(10001, "用户请求限制"),
    SYS_RATE_LIMIT_IP(10002, "IP请求限制"),
    SYS_RATE_LIMIT_API(10003, "Api请求限制"),
    COMMON_FAILED(500, "接口返回失败");

    private int code;
    private String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    BizErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据code获取到msg
     *
     * @param code
     * @return
     */
    public static String getMsgByCode(int code) {
        for (BizErrorCode e : BizErrorCode.values()) {
            if (e.getCode() == code) {
                return e.getMsg();
            }
        }
        return null;
    }


}
