package com.wxl.token.utils;

import com.wxl.token.boot.exception.BaseErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 *   接口返回数据格式
 * @author scott
 * @email jeecgos@163.com
 * @date  2019年1月19日
 */
@Data
public class Result<T> implements Serializable {

	public final static int CODE_SUCCESS=200;
	public final static int CODE_FAILED=500;


	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	private Integer code = 0;

	/**
	 * 返回数据对象 data
	 */
	private T result;

	/**
	 * 时间戳
	 */
	private long timestamp = System.currentTimeMillis();

	public Result() {

	}

	public Result<T> error500(String message) {
		this.message = message;
		this.code = CODE_FAILED;
		this.success = false;
		return this;
	}

	public Result<T> success(String message) {
		this.message = message;
		this.code = CODE_SUCCESS;
		this.success = true;
		return this;
	}


	public static Result ok() {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(CODE_SUCCESS);
		r.setMessage("成功");
		return r;
	}

	public static Result<Object> okWithMsg(String msg) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(CODE_SUCCESS);
		r.setMessage(msg);
		return r;
	}

	public static Result ok(Object data) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(CODE_SUCCESS);
		r.setResult(data);
		return r;
	}
	public static Result error(Object data) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(false);
		r.setCode(CODE_FAILED);
		r.setResult(data);
		return r;
	}

	public static Result error(String msg) {
		return error(CODE_FAILED, msg);
	}

    public static Result error(BaseErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static Result<Object> error() {
        return error(CODE_FAILED, "操作失败");
    }

	public static Result<Object> error(int code, String msg) {
		Result<Object> r = new Result<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}

	/**
	 * 无权限访问返回结果
	 */
	public static Result<Object> noauth(String msg) {
		return error(510, msg);
	}

	public static Result apiError(String msg) {
		return error(CODE_SUCCESS, msg);
	}

	public static <D> Result<D> okWithData(D data) {
		Result<D> result = new Result<>();
		result.setSuccess(true);
		result.setCode(CODE_SUCCESS);
		result.setResult(data);
		return result;
	}
}
