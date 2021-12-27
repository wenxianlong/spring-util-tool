package com.wxl.token.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created  on 2021/12/27.
 *
 * @author wenxianlong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 父级权限id
     */
    private Integer parentId;

    /**
     * 父节点路径，逗号分隔
     */
    private String parentIds;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限编码
     */
    private String perm;

    /**
     * 权限类型
     */
    private String type;

    /**
     * 权限层级
     */
    private Integer level;

    /**
     * 是否删除0否
     */
    private Integer delFlag;
}
