package com.wxl.token.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created  on 2016/07/04.
 *
 * @author zhangxiangyang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ResultVM<T> {
    private Boolean success;
    private T data;
    private ErrorVM error;

    public static final <M> ResultVM<M> ok(M m) {
        return new ResultVM(true, m, null);
    }

    public static final <M> ResultVM<M> ok() {
        return new ResultVM(true, null, null);
    }

    public static final <M> ResultVM<M> error(ErrorVM error) {
        return new ResultVM(false, null, error);
    }

}
