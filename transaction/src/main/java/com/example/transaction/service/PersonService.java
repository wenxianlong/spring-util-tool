package com.example.transaction.service;

import com.example.transaction.entity.Person;
import com.example.transaction.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wenxianlong
 * @date 2021/7/13 2:46 下午
 */
@Service
public class PersonService {

    @Resource
    private PersonMapper personMapper;

    @Autowired
    private ClazzService clazzService;

    /**
     * Transaction rolled back because it has been marked as rollback-only
     * <p>
     * 在createClazz返回的时候，transaction被设置为rollback-only了，但是testTransactionRollbackOnly正常消化掉，
     * 没有继续向外抛。那么addRoles结束的时候，transaction会执commit操作，但是transaction已经被设置为rollback-only了
     * <p>
     * 在spring中，在事务方法中调用多个事务方法时，spring将会把这些事务合二为一。当整个方法中每个子方法没报错时，
     * 整个方法执行完才提交事务（大家可以使用debug测试），如果某个子方法有异常，spring将该事务标志为rollback only。
     * 如果这个子方法没有将异常往上整个方法抛出或整个方法未往上抛出，那么改异常就不会触发事务进行回滚，事务就会在整个方法执行完后就会提交，
     * 这时就会造成Transaction rolled back because it has been marked as rollback-only的异常，
     * 就如上面代码中未抛throw e 一样。如果我们往上抛了改异常，spring就会获取异常，并执行回滚。
     *
     * 数据都回滚
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean testTransactionRollbackOnly() {
        Person person = new Person();
        person.setId(1);
        person.setName("aaa");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        try {
            clazzService.createClazz();
        } catch (Exception e) {
            throw  e; //如果子方法或者本方法往上抛异常则不会报rollback-only
            //System.out.println("吃掉子方法的异常，此时报rollback-only");
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean testSameClassTwoMethodAllTransaction() {
        Person person = new Person();
        person.setId(1);
        person.setName("aaa");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        testSameClassTwoMethodAllTransaction1();
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean testSameClassTwoMethodAllTransaction1() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        System.out.println(1/0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean testSameClassTwoMethodMainTransaction() {
        Person person = new Person();
        person.setId(1);
        person.setName("aaa");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        testSameClassTwoMethodMainTransaction1();
        return true;
    }

    public boolean testSameClassTwoMethodMainTransaction1() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        System.out.println(1/0);
        return true;
    }

    public boolean testSameClassTwoMethodChildTransaction() {
        Person person = new Person();
        person.setId(1);
        person.setName("aaa");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        testSameClassTwoMethodChildTransaction1();
        System.out.println(1/0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean testSameClassTwoMethodChildTransaction1() {
        Person person = new Person();
        person.setId(2);
        person.setName("bbb");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        return true;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean testDiffClassTwoMethodAllTransaction() {
        Person person = new Person();
        person.setId(1);
        person.setName("aaa");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        clazzService.testDiffClassTwoMethodAllTransaction();
        System.out.println(1/0);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean testDiffClassTwoMethodMainTransaction() {
        Person person = new Person();
        person.setId(1);
        person.setName("aaa");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        clazzService.testDiffClassTwoMethodMainTransaction();
        System.out.println(1/0);
        return true;
    }

    public boolean testDiffClassTwoMethodChildTransaction() {
        Person person = new Person();
        person.setId(1);
        person.setName("aaa");
        person.setAge(24);
        person.setClazzId(1);
        personMapper.insert(person);
        clazzService.testDiffClassTwoMethodChildTransaction();
        System.out.println(1/0);
        return true;
    }

}
