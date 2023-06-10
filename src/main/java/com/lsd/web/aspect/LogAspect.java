package com.lsd.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("within(com.lsd.web.controller.*)")
    public void logAspectApi(){}
    @Pointcut("within(com.lsd.web.service.*)")
    public void logAspectService(){}

    @Around("logAspectApi() || logAspectService()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();
        final Object proceed = joinPoint.proceed();
        final long executeTime = System.currentTimeMillis() - start;
        LOGGER.info("[EXECUTE] {}.{} executed in {} ms", joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName(), executeTime);
        return proceed;
    }
}
