package com.wxl.token.utils;

import lombok.Data;

/**
 * Created  on 2016/07/04.
 *
 * @author zhangxiangyang
 */
@Data
public class FieldErrorVM {

    private final String objectName;

    private final String field;

    private final String message;

    private final String code;

    private final Object rejectValue;

}
