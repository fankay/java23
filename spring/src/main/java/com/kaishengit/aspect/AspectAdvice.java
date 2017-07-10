package com.kaishengit.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Spring AOP 通知类
 * Created by fankay on 2017/7/10.
 */
@Component
@Aspect
public class AspectAdvice {

    @Pointcut("execution(* com.kaishengit.service..*.*(..))")
    public void pointcut(){}

   // @Before("pointcut()")
    public void beforeAdvice() {
        System.out.println("前置通知");
    }

    //@AfterReturning(pointcut = "pointcut()",returning = "result")
    public void afterReturningAdvice(Object result) {
        System.out.println("后置通知 :" + result);
    }

    //@AfterThrowing(pointcut = "pointcut()",throwing = "ex")
    public void afterThrowingAdvice(Exception ex) {
        System.out.println("异常通知 :" + ex.getMessage());
    }

    //@After("pointcut()")
    public void afterAdvice() {
        System.out.println("最终通知");
    }

    //@Around("pointcut()")
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
