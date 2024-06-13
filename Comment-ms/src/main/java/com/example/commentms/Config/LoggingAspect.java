package com.example.commentms.Config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.example.commentms.Service.Impl.CommentServiceImpl.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("Executing method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.example.commentms.Service.Impl.CommentServiceImpl.*(..))", returning = "result")
    public void logAfterReturningMethod(JoinPoint joinPoint, Object result) {
        log.info("Method executed successfully: {} with result = {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* com.example.commentms.Service.Impl.CommentServiceImpl.*(..))", throwing = "exception")
    public void logAfterThrowingMethod(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in method: {} with cause = {}", joinPoint.getSignature().getName(), exception.getCause() != null ? exception.getCause() : "NULL");
    }

    @Around("execution(* com.example.commentms.Service.Impl.CommentServiceImpl.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        log.info("Executed method: {} in {} ms", joinPoint.getSignature().getName(), executionTime);
        return proceed;
    }
}