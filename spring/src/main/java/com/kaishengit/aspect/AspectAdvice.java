package com.kaishengit.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Spring AOP 通知类
 * Created by fankay on 2017/7/10.
 */
public class AspectAdvice {

    public void beforeAdvice() {
        System.out.println("前置通知");
    }

    public void afterReturningAdvice(Object result) {
        System.out.println("后置通知 :" + result);
    }

    public void afterThrowingAdvice(Exception ex) {
        System.out.println("异常通知 :" + ex.getMessage());
    }

    public void afterAdvice() {
        System.out.println("最终通知");
    }

    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            System.out.println("around before");
            Object result = proceedingJoinPoint.proceed(); //代表目标对象方法的执行
            System.out.println("around after returning");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("around throwing");
        } finally {
            System.out.println("around after");
        }
    }

}
