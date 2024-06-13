package com.example.userms.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class UserAspect {

    @Pointcut("execution(* com.example.userms.Service.UserService.*(..))")
    public void userServiceMethods() {
        // Pointcut for methods in UserService
    }

    @Before("userServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Executing method: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "userServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method executed: {} with result: {}", joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(pointcut = "userServiceMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("Exception in method: {} with cause: {}", joinPoint.getSignature().toShortString(), error.getCause() != null ? error.getCause() : "NULL");
    }

    @After("userServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Method executed: {}", joinPoint.getSignature().toShortString());
    }
}
