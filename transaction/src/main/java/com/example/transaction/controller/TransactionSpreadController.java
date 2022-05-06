package com.example.transaction.controller;

import com.example.transaction.service.PersonTransactionSpreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事务传播分析实例
 *
 * @author wenxianlong
 * @date 2021/7/13 2:55 下午
 */
@RestController
@RequestMapping(value = "/api/transaction/spread")
public class TransactionSpreadController {

    @Autowired
    private PersonTransactionSpreedService personService;

    /**
     * 测试required传播机制，
     * 支持当前事务，如果没有事务，则创建一个新的事务，这是spring默认的事务传播机制
     * <p>
     * 主方法添加required机制，主方法会创建一个事务，主方法就在事务中运行
     * 如果子方法再不同的类中，主方法或者子方法报错，事务回滚；
     * 如果子方法和主方法再同一个类中，主方法或者子方法报错，事务回滚；
     *
     * @return boolean
     */
    @GetMapping("/testRequired")
    public boolean testRequired() {
        personService.testRequired();
        return true;
    }

    /**
     * 测试RequiresNew传播机制，
     * 创建一个新事物执行，如果当前存在事务，则挂起当前事务，重新创建一个新的事务
     * <p>
     * 一、不同类
     * 1、主方法存在事务，子方法上存在requires_new事务，如果子方法报错则全部回滚；
     * <p>
     * 1.如果A中捕获B的异常，并没有继续向上抛异常，则B先回滚，A再正常提交；
     * 2.如果A未捕获B的异常，默认则会将B的异常向上抛，则B先回滚，A再回滚
     * <p>
     * 如果子方法不报错，主方法报错，则子方法事务正常提交，主方法回滚；
     * 2、主方法不存在事务，子方法存在requires_new事务，如果子方法报错，子方法回滚，主方法不会滚；
     * 如果主方法不报错，则子方法不回滚，主方法也不回滚；
     * 二、同一类
     * 如果主方法存在RequiresNew事务，主方法以新的事物方式运行，如果主方法报错回滚，子方法报错，也回滚;
     *
     * @return boolean
     */
    @GetMapping("/testRequiresNew")
    public boolean testRequiresNew() {
        personService.testRequiresNew();
        return true;
    }

    /**
     * 测试Supports传播机制，
     * 支持当前事务，如果不存在事务则以非事务方式执行，如果存在事务则加入当前事务
     * <p>
     * 一、不同类
     * 1、主方法存在required事务，子方法上存在Supports事务，如果主方法或者子方法报错则全部回滚；
     * 2、主方法存在requires_new事务，子方法存在Supports事务，主方法和子方法都不影响，都不回滚；
     * 二、同一类
     * 如果主方法存在Supports事务，主方法子方法报错都不会滚;
     *
     * @return boolean
     */
    @GetMapping("/testSupport")
    public boolean testSupport() {
        personService.testSupport();
        return true;
    }

    /**
     * 测试NOT_SUPPORTED传播机制，
     * 不支持当前事务，存在事务挂起当前事务，始终以非事务方式执行
     * 一、不同类
     * 1、主方法有事务，子方法上有NOT_SUPPORTED，则子方法不支持事务，子方法以非事务的方式运行，报错不回滚
     *
     * @return boolean
     */
    @GetMapping("/testNoSupport")
    public boolean testNoSupport() {
        personService.testNoSupport();
        return true;
    }

    /**
     * 测试Never传播机制，
     * 不支持当前事务，如果当前事务存在，抛出异常
     * <p>
     * 一、不同类
     * 1、主方法存在required,或者requires_new事务，如果子方法是Never传播机制，则抛出异常Existing transaction found for transaction marked with propagation 'never'
     * 2、主方法存在support事务，如果子方法是Never传播机制，本身主方法是没有事务的，此时子方法不抛出异常，以无事务的方式执行
     *
     * @return boolean
     */
    @GetMapping("/testNever")
    public boolean testNever() {
        personService.testNever();
        return true;
    }

    /**
     * 测试Mandatory传播机制，
     * 支持当前事务，如果当前事务不存在，则抛出异常
     * <p>
     * 一、不同类
     * 1、主方法不存在事务，主方法报错，不会滚，子方法如果是Mandatory，则报错， No existing transaction found for transaction marked with propagation 'mandatory'；
     * 1、主方法存在required,或者requires_new事务，子方法上存在Mandatory事务，如果主方法或者子方法报错则全部回滚；
     * 2、主方法存在Supports事务，子方法存在Supports事务，主方法都不回滚，子方法报No existing transaction found for transaction marked with propagation 'mandatory'；
     * 二、同一类
     * 如果主方法存在required,或者requires_new事务，主方法子方法报错都回滚;
     * 如果主方法存在Supports事务，主方法不会滚，子方法事务不生效;
     *
     * @return boolean
     */
    @GetMapping("/testMandatory")
    public boolean testMandatory() {
        personService.testMandatory();
        return true;
    }

    /**
     * 测试Nested传播机制，
     * 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作
     * <p>
     * 一、不同类
     * 定义serviceA.methodA()以PROPAGATION_REQUIRED修饰；
     * 定义serviceB.methodB()以Nested
     * 异常状态	       |         PROPAGATION_REQUIRES_NEW（两个独立事务）|	PROPAGATION_NESTED(B的事务嵌套在A的事务中)	| PROPAGATION_REQUIRED(同一个事务)
     * methodA抛异常,methodB正常	| A回滚，B正常提交	 | A与B一起回滚	|A与B一起回滚
     * methodA正常, methodB抛异常	|1.如果A中捕获B的异常，并没有继续向上抛异常，则B先回滚，A再正常提交； 2.如果A未捕获B的异常，默认则会将B的异常向上抛，则B先回滚，A再回滚	| B先回滚，A再正常提交	| A与B一起回滚
     * methodA抛异常, methodB抛异常	|B先回滚，A再回滚	|A与B一起回滚	| A与B一起回滚
     * methodA正常 methodB正常	|B先提交，A再提交	|A与B一起提交	| A与B一起提交

     *
     * @return boolean
     */
    @GetMapping("/testNested")
    public boolean testNested() {
        personService.testNested();
        return true;
    }
}
