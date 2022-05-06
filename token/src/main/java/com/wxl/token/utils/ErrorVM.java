package com.wxl.token.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created  on 2016/07/04.
 *
 * @author zhangxiangyang
 *
 * <p>
 * 统一错误格式:
 * {
 * "error":{
 * "path":"/test"
 * "exception":"com.example.demo.boot.exception.NoJwtTokenException",
 * "message": "jwt信息错误",
 * "code":3000,
 * "occurTime":"2012-12-12 12:30:10"
 * },
 * "success":false
 * }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonPropertyOrder(alphabetic = true, value = {"path", "code", "message", "exception", "occurTime"})
public class ErrorVM {

    /**
     * 当前请求路径
     */
    private String path;
    /**
     * 异常类型
     */
    private String exception;
    /**
     * 业务错误消息:来自异常
     */
    private String message;
    /**
     * 业务错误码
     */
    private Integer code;
    /**
     * 当前时间
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime occurTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private List<FieldErrorVM> fieldErrors;

}
