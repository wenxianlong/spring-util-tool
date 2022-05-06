package com.example.transaction.service;

import com.example.transaction.entity.Person;
import com.example.transaction.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wenxianlong
 * @date 2021/7/13 2:46 下午
 */
@Service
public class PersonTransactionSpreedService {

    @Resource
    private PersonMapper personMapper;

    @Autowired
    private ClazzTransactionSpreedService clazzService;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean testRequired() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        testRequired1();
        System.out.println(1 / 0);
        //clazzService.createClazz();
        return true;
    }

    public boolean testRequired1() {
        Person person = new Person();
        person.setId(3);
        person.setName("bbb33");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        return true;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public boolean testRequiresNew() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        testRequiresNew1();
        //clazzService.testRequiresNew();
        return true;
    }

    public boolean testRequiresNew1() {
        Person person = new Person();
        person.setId(3);
        person.setName("bbb33");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        System.out.println(1 / 0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean testSupport() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        //testSupport1();
        clazzService.testSupport();
        return true;
    }

    public boolean testSupport1() {
        Person person = new Person();
        person.setId(3);
        person.setName("bbb33");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        System.out.println(1 / 0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public boolean testMandatory() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        //testMandatory1();
        clazzService.testMandatory();
        return true;
    }

    public boolean testMandatory1() {
        Person person = new Person();
        person.setId(3);
        person.setName("bbb33");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        System.out.println(1 / 0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public boolean testNoSupport() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        //testMandatory1();
        clazzService.testNoSupport();
        return true;
    }

    public boolean testNoSupport1() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        //testMandatory1();
        clazzService.testMandatory();
        return true;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public boolean testNever() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        //testMandatory1();
        clazzService.testNever();
        return true;
    }

    public boolean testNever1() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        //testMandatory1();
        clazzService.testMandatory();
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean testNested() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        //testMandatory1();
        clazzService.testNever();
        return true;
    }

    public boolean testNested1() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        //testMandatory1();
        clazzService.testMandatory();
        return true;
    }

}
