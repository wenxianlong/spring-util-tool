package com.example.transaction.service;

import com.example.transaction.entity.Clazz;
import com.example.transaction.mapper.ClazzMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wenxianlong
 * @date 2021/7/13 2:55 下午
 */
@Service
public class ClazzTransactionSpreedService {

    @Resource
    private ClazzMapper clazzMapper;

    public boolean createClazz() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);
        System.out.println(1 / 0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public boolean testRequiresNew() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);

        return true;
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public boolean testSupport() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);
        System.out.println(1 / 0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public boolean testMandatory() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);
        System.out.println(1 / 0);
        return true;
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public boolean testNoSupport() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);
        System.out.println(1 / 0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NEVER)
    public boolean testNever() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);
        System.out.println(1 / 0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public boolean testNested() {
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("bbb");
        clazzMapper.insert(clazz);
        System.out.println(1 / 0);
        return true;
    }
}
