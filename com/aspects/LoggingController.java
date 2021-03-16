package com.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingController {

    @Before("execution(String post*(..))")
    public void postLogging(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("логируется работа пост метода: " + methodSignature.getName());
    }

    @After("execution(public String view*(..))")
    public void getLogging(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("логируется работа гет метода: " + methodSignature.getName());
    }
}
