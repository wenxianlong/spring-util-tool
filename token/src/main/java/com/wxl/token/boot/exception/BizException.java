package com.wxl.token.boot.exception;

/**
 */
public class BizException extends RuntimeException {

    /*错误码*/
    private Integer code=-1;

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(BizErrorCode bizErrorCode) {
        super(bizErrorCode.getMsg());
        this.code=bizErrorCode.getCode();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BizException{" +
                "code=" + code +
                "message=" + getMessage() +
                '}';
    }
}
