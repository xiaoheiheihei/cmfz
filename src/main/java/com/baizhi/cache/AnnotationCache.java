package com.baizhi.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Aspect
@Configuration
public class AnnotationCache {
    @Autowired
    RedisTemplate redisTemplate;

    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed = null;
        StringBuffer sb = new StringBuffer();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg.toString());
        }
        String s = sb.toString();
        HashOperations hashOperations = redisTemplate.opsForHash();
        Boolean aBoolean = hashOperations.hasKey(className, s);
        System.out.println(aBoolean);
        if (aBoolean) {
            proceed = hashOperations.get(className, s);
        } else {
            proceed = proceedingJoinPoint.proceed();
            hashOperations.put(className, s, proceed);
        }
        return proceed;
    }

    @After("@annotation(com.baizhi.annotation.DelCache)")
    public void After(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(className);
    }
}
