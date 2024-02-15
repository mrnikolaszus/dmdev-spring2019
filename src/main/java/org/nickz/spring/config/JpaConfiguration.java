package org.nickz.spring.config;

import javax.annotation.PostConstruct;

import org.nickz.spring.config.condition.JpaCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Conditional(JpaCondition.class)
@Configuration
public class JpaConfiguration {

    @PostConstruct
    void init(){
        System.err.println("JPA CONFIG IS ENABLED");
    }
}
