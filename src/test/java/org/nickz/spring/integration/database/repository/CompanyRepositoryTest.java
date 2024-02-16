package org.nickz.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nickz.spring.database.entity.Company;
import org.nickz.spring.integration.annotation.IT;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor

class CompanyRepositoryTest {

    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;

    @Test

    void findById() {

        transactionTemplate.executeWithoutResult(tx ->{
            var company = entityManager.find(Company.class, 1);
            assertNotNull(company);
            Assertions.assertThat(company.getLocales()).hasSize(2);
        });


    }

//    @Test
//    @Transactional
//    @Commit
//    void save(){
//    var company = Company.builder()
//            .name("Apple")
//            .locales(Map.of(
//                    "ru", "Apple описание",
//                    "en", "Apple description"
//            ))
//            .build();
//    entityManager.persist(company);
//    assertNotNull(company.getId());
//    }
}