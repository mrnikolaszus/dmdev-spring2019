package org.nickz.spring;

import org.nickz.spring.config.ApplicationConfiguration;
import org.nickz.spring.database.pool.ConnectionPool;
import org.nickz.spring.database.repository.CompanyRepository;
import org.nickz.spring.database.repository.CrudRepository;
import org.nickz.spring.database.repository.UserRepository;
import org.nickz.spring.ioc.Container;
import org.nickz.spring.service.CompanyService;
import org.nickz.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.register(ApplicationConfiguration.class);
            context.getEnvironment().setActiveProfiles("web", "prod");
            context.refresh();
            var connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);
            var companyService = context.getBean("companyService", CompanyService.class);
            System.out.println(companyService.findById(1));
        }

    }
}
