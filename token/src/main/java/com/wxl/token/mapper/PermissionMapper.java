package com.wxl.token.mapper;

import com.wxl.token.entity.Permission;

import java.util.List;

/**
 * Created  on 2021/12/27.
 *
 * @author wenxianlong
 */
public interface PermissionMapper {
    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    /**
     * 查询企业用户具有的权限值
     * @param userId 用户id
     * @return 权限值
     */
    List<String> selectPermsByUserId(String userId);
}
