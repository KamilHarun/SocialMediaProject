package com.example.postms.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class PostAspect {

    @Pointcut("execution(* com.example.postms.Service.impl.PostServiceImpl.*(..))")
    public void postServiceMethods() {
    }

    @Before("postServiceMethods()")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method called: {}", methodName);
    }
}
