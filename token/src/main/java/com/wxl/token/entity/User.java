package com.wxl.token.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created  on 2021/12/27.
 *
 * @author wenxianlong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户名称
     */
    private String password;

    /**
     * 最后登陆错误时间
     */
    private LocalDateTime lastErrorDate;

    /**
     * 登陆错误次数
     */
    private Integer errorCount;

    /**
     * 状态（1-正常；2-冻结）
     */
    private Integer status;

    /**
     * 逻辑删除标识（1-隐藏；0-显示）
     */
    private Integer delFlag;
}
