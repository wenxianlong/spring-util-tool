package com.example.transaction.controller;

import com.example.transaction.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 同类以及不同类方法调用的事务分析实例
 *
 * @author wenxianlong
 * @date 2021/7/13 2:55 下午
 */
@RestController
@RequestMapping(value = "/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * 测试 Transaction rolled back because it has been marked as rollback-only
     *
     * @return boolean
     */
    @GetMapping("/testTransactionRollbackOnly")
    public boolean testTransactionRollbackOnly() {
        return personService.testTransactionRollbackOnly();
    }

    /**
     * 测试同一个类中两个不同的方法，都有事务，方法抛异常,都回滚
     *
     * @return boolean
     */
    @GetMapping("/testSameClassTwoMethodAllTransaction")
    public boolean testSameClassTwoMethodAllTransaction() {
        return personService.testSameClassTwoMethodAllTransaction();
    }

    /**
     * 测试同一个类中两个不同的方法，主方法有事务，子方法没有事务，子方法抛异常,都回滚
     *
     * @return boolean
     */
    @GetMapping("/testSameClassTwoMethodMainTransaction")
    public boolean testSameClassTwoMethodMainTransaction() {
        return personService.testSameClassTwoMethodMainTransaction();
    }

    /**
     * 测试同一个类中两个不同的方法，主方法没有事务，子方法有事务，子方法抛异常,都不回滚
     * spring基于代理实现事务，主方法和子方法再同一个类中，没有代理，此时事务不生效，都不回滚
     *
     * @return boolean
     */
    @GetMapping("/testSameClassTwoMethodChildTransaction")
    public boolean testSameClassTwoMethodChildTransaction() {
        return personService.testSameClassTwoMethodChildTransaction();
    }

    /**
     * 测试不同类中两个不同的方法，两个方法都有事务，方法抛出异常，都回滚
     *
     * @return boolean
     */
    @GetMapping("/testDiffClassTwoMethodAllTransaction")
    public boolean testDiffClassTwoMethodAllTransaction() {
        return personService.testDiffClassTwoMethodAllTransaction();
    }

    /**
     * 测试不同类中两个不同的方法，主方法中有事务，子方法没有事务，抛出异常，都回滚
     *
     * @return boolean
     */
    @GetMapping("/testDiffClassTwoMethodMainTransaction")
    public boolean testDiffClassTwoMethodMainTransaction() {
        return personService.testDiffClassTwoMethodMainTransaction();
    }

    /**
     * 测试不同类中两个不同的方法，主方法中没有事务，子方法有事务，子方法抛出异常，子方法回滚，主方法不回滚，子方法回滚；
     * 主方法抛异常，子方法不抛异常，都不会滚
     *
     * @return boolean
     */
    @GetMapping("/testDiffClassTwoMethodChildTransaction")
    public boolean testDiffClassTwoMethodChildTransaction() {
        return personService.testDiffClassTwoMethodChildTransaction();
    }
}
