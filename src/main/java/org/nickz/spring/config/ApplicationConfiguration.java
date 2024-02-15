package org.nickz.spring.config;

import org.nickz.spring.database.pool.ConnectionPool;
import org.nickz.spring.database.repository.CrudRepository;
import org.nickz.spring.database.repository.UserRepository;
import org.nickz.web.WebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

//@ImportResource("classpath:application.xml")
@Import(WebConfiguration.class)
@Configuration(proxyBeanMethods = true)
@ComponentScan(basePackages = "org.nickz.spring",
useDefaultFilters = false,
includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
})
public class ApplicationConfiguration {

    @Bean("pool2")
//    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ConnectionPool pool2(@Value("${db.username}") String username) {
        return new ConnectionPool(username, 20);
    }

    @Bean
    public ConnectionPool pool3() {
        return new ConnectionPool("test-pool", 25);
    }


    @Bean
    @Profile("prod|web")
    public UserRepository userRepository2(ConnectionPool pool2){
        return new UserRepository(pool2);
    }
    @Bean
    public UserRepository userRepository3(){
        return new UserRepository(pool3());
    }
}
