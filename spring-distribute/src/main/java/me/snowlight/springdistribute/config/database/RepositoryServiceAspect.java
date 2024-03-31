package me.snowlight.springdistribute.config.database;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class RepositoryServiceAspect {

    @Pointcut("execution(public * me.snowlight.springdistribute.service..*.*(..))")
    private void repositoryService() {
    }

    @Around("repositoryService() && @within(sharding) && args(shardKey,..)")
    public Object handler(ProceedingJoinPoint pjp, Sharding sharding, long shardKey) throws Throwable {
        UserHolder.setSharding(sharding.target(), shardKey);

        Object returnVal = pjp.proceed();

        UserHolder.clearSharding();

        return returnVal;
    }
}