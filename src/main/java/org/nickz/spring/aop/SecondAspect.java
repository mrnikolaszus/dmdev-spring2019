package org.nickz.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(2)
public class SecondAspect {

    @Around("org.nickz.spring.aop.FirstAspect.anyFindByIdServiceMethod() && target(service) && args(id)")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object service, Object id) throws Throwable {
        log.info("AROUND before -  invoked findById method in class {}, with id{}", service, id);

        try{
            Object result = joinPoint.proceed();
            log.info("AROUND after returning -invoked findById method in class {}, with result{}", service, result);
            return result;
        } catch (Throwable ex){
            log.info("AROUND After Throwing - invoked findById method in class {}, with exception {}: {}", service, ex.getClass(), ex.getMessage());
            throw ex;
        }
        finally {
            log.info("AROUND After(finally)-invoked findById method in class {}", service);
        }

    }
}
