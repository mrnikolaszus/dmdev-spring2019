package org.nickz.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonPointcuts {

    // within - checks annotation on the class lvl (аннотация над классом)
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer(){

    }

    // within - checks class type name
    @Pointcut("within(org.nickz.spring.service.*Service)")
    public void isServiceLayer(){

    }
}
