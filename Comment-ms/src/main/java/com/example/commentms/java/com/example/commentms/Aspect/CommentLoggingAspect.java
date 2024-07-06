package com.example.commentms.java.com.example.commentms.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class CommentLoggingAspect {

    @Pointcut("execution(* com.example.commentms.Service.Impl.CommentServiceImpl.*(..))")
    public void commentServiceMethods() {
        // Pointcut for methods in CommentServiceImpl
    }

    @Before("commentServiceMethods()")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        log.info("Method called: {} with arguments {}", methodName, methodArgs);
    }
}

