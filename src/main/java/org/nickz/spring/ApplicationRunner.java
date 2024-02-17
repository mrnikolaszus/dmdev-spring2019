package org.nickz.spring;

import org.nickz.spring.config.ApplicationConfiguration;
import org.nickz.spring.config.DatabaseProperties;
import org.nickz.spring.database.pool.ConnectionPool;
import org.nickz.spring.database.repository.CompanyRepository;
import org.nickz.spring.database.repository.UserRepository;
import org.nickz.spring.ioc.Container;
import org.nickz.spring.service.CompanyService;
import org.nickz.spring.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
        var definitionCount = context.getBeanDefinitionCount();
        System.out.println(definitionCount);
        System.out.println(context.getBean("pool1"));
        System.out.println(context.getBean(CompanyService.class));
    }
}
