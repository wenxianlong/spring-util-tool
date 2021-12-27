package com.wxl.token.mapper;


import com.wxl.token.entity.User;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * Created  on 2021/12/27.
 *
 * @author wenxianlong
 */
public interface UserMapper {

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据登录名查询用户信息
     *
     * @param loginName 登录名
     * @return User
     */
    User getByLoginName(String loginName);

    /**
     * 根据用户id修改用户登录错误的次数
     *
     * @param id            用户id
     * @param errorCount    错误次数
     * @param lastErrorDate 登录时间
     */
    void updateErrorCount(@Param("id") Integer id, @Param("errorCount") int errorCount, @Param("lastErrorDate") LocalDateTime lastErrorDate);
}
