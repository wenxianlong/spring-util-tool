package com.example.transaction.service;

import com.example.transaction.entity.Clazz;
import com.example.transaction.mapper.ClazzMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wenxianlong
 * @date 2021/7/13 2:55 下午
 */
@Service
public class ClazzService {

    @Resource
    private ClazzMapper clazzMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean createClazz() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);
        System.out.println(1/0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean testDiffClassTwoMethodAllTransaction() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);

        return true;
    }

    public boolean testDiffClassTwoMethodMainTransaction() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean testDiffClassTwoMethodChildTransaction() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);
        return true;
    }




}
