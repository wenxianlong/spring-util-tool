package com.example.transaction.mapper;

import com.example.transaction.entity.Person;

/**
 * Created  on 2021/07/13.
 *
 * @author wenxianlong
 */
public interface PersonMapper {
    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}
