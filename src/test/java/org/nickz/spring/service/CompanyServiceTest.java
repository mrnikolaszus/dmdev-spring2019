package org.nickz.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nickz.spring.database.entity.Company;
import org.nickz.spring.database.repository.CrudRepository;
import org.nickz.spring.dto.CompanyReadDTO;
import org.nickz.spring.listenter.entity.EntityEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.AssertionErrors;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    public static final Integer COMPANY_ID =1;
    @Mock
    private CrudRepository<Integer, Company> companyRepository;
    @Mock
    private UserService userService;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById() {

        Mockito.doReturn(Optional.of(new Company(COMPANY_ID)))
                .when(companyRepository).findById(COMPANY_ID);

        var result = companyService.findById(COMPANY_ID);

        Assertions.assertTrue(result.isPresent());

        var expected = new CompanyReadDTO(COMPANY_ID);
        result.ifPresent(actual -> assertEquals(expected, actual));

        Mockito.verify(eventPublisher).publishEvent(Mockito.any(EntityEvent.class));
        Mockito.verifyNoMoreInteractions(eventPublisher, userService);
    }


}