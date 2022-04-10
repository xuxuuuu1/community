package com.xx.community.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {

    //sservice包下 第一个星号表示返回值 第二个星号表示所有类  第三个星号表示所有类的所有方法的 （..）表示所有参数
    @Pointcut("execution(* com.xx.community.service.*.* (..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("After");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
        System.out.println("After return");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("After throwing");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("before around");
        Object obj = joinPoint.proceed();
        System.out.println("After around");
        return obj;
    }
}
