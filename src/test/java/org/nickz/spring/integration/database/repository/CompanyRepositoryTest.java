package org.nickz.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nickz.spring.database.entity.Company;
import org.nickz.spring.database.entity.User;
import org.nickz.spring.database.repository.CompanyRepository;
import org.nickz.spring.dto.UserFilter;
import org.nickz.spring.integration.IntegrationTestBase;
import org.nickz.spring.integration.annotation.IT;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor

class CompanyRepositoryTest extends IntegrationTestBase {

    private static final Integer APPLE_ID = 4;
    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;
    private final CompanyRepository companyRepository;



    @Test
    void checkFindByQueries(){
        companyRepository.findByName("google");
        companyRepository.findByNameContainingIgnoreCase("a");
    }

    @Test
    void delete(){
        var maybeCompany = companyRepository.findById(APPLE_ID);
        assertTrue(maybeCompany.isPresent());
        maybeCompany.ifPresent(companyRepository::delete);
        entityManager.flush();
        assertTrue(companyRepository.findById(APPLE_ID).isEmpty());
    }

    @Test
    void findById() {

        transactionTemplate.executeWithoutResult(tx ->{
            var company = entityManager.find(Company.class, 1);
            assertNotNull(company);
            Assertions.assertThat(company.getLocales()).hasSize(2);
        }


        );

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