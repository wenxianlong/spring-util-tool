package com.example.transaction.mapper;


import com.example.transaction.entity.Clazz;

/**
 * Created  on 2021/07/13.
 *
 * @author wenxianlong
 */
public interface ClazzMapper {
    int insert(Clazz record);

    int insertSelective(Clazz record);

    Clazz selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Clazz record);

    int updateByPrimaryKey(Clazz record);
}
