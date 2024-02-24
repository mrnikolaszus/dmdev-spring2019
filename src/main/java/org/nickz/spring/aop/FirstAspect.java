package org.nickz.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.mapping.Join;
import org.nickz.spring.validation.UserInfo;
import org.springframework.core.annotation.Order;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Aspect
@Component
@Order(1)
public class FirstAspect {


    //this - checks AOP proxy class type
    //this - checks target object class type
    @Pointcut("this(org.springframework.data.repository.Repository)")
//    @Pointcut("target(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer(){
    }

    // @annotation - checks annotation on the method lvl (аннотация над методом)
    //              переиспользование IsControllerLayer
    @Pointcut("org.nickz.spring.aop.CommonPointcuts.isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping(){
    }

    //args -checks methods param types   первым идет параметр Model model, а далее: ,.. - любое количество параметров
    //              ,*,* - два допоолнительных парметра
    @Pointcut("org.nickz.spring.aop.CommonPointcuts.isControllerLayer() && args(org.springframework.ui.Model),..")
    public void hasModelParam(){
    }

    //@args - checks annotation on the param type   ---  далее: ,.. - любое количество параметров
    //              ,*,* - два допоолнительных парметра

    @Pointcut("org.nickz.spring.aop.CommonPointcuts.isControllerLayer() && @args(org.nickz.spring.validation.UserInfo,..)")
    public void hasUserInfoParamAnnotation(){
    }


    // bean - checks bean name
    @Pointcut("bean(*Service)")
    public void isServiceLayerBean(){
    }

    // execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
    @Pointcut("execution(public * org.nickz.spring.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod(){

    }

    @Before(value = "anyFindByIdServiceMethod()" +
            " && args(id)" +
            " && target(service)" +
            "&& this(serviceProxy)" +
            "&& @within(transactional)", argNames = "joinPoint,id,service,serviceProxy,transactional")
//    @Before("execution(public * org.nickz.spring.service.*Service.findById(*))")
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional){
        log.info("BEFORE invoked findById method in class {}, with id{}", service, id);
    }

    @AfterReturning(value = "anyFindByIdServiceMethod()" +
            "&& target(service)",
            returning = "result")
    public void addLoggingAfterReturning(Object result, Object service){
        log.info("AfterReturning -invoked findById method in class {}, with result{}", service, result);
    }

    @AfterThrowing(value = "anyFindByIdServiceMethod()" +
            "&& target(service)",
            throwing = "ex")
    public void addLoggingAfterThrowing(Throwable ex, Object service){
        log.info("AfterThrowing -invoked findById method in class {}, with exception {}: {}", service, ex.getClass(), ex.getMessage());
    }

    @After("anyFindByIdServiceMethod() && target(service)")
    public void addLoggingAfterFinally(Object service){
        log.info("After(finally)-invoked findById method in class {}", service);
    }


}
