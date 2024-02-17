package org.nickz.spring.config;

import org.nickz.spring.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
@EnableEnversRepositories(basePackageClasses = ApplicationRunner.class)
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware(){
        // securityContext
        return () -> Optional.of("nickz");

    }

}
