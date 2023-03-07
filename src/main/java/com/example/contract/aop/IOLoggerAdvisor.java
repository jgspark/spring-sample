package com.example.contract.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class IOLoggerAdvisor {

    @Around("@annotation(com.example.contract.aop.IOLogger)")
    public Object processCustomAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        log.info("Method arguments: {}", Arrays.toString(args));
        Object result = joinPoint.proceed();
        log.info("Method return value: {}", result);
        return result;
    }
}
