package integration.service;

import integration.service.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.nickz.spring.ApplicationRunner;
import org.nickz.spring.config.DatabaseProperties;
import org.nickz.spring.dto.CompanyReadDTO;
import org.nickz.spring.listenter.entity.EntityEvent;
import org.nickz.spring.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;


import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
@RequiredArgsConstructor
public class CompanyServiceIT {

    private final CompanyService companyService;

    private final DatabaseProperties databaseProperties;
    public static final Integer COMPANY_ID =1;
    @Test
    void findById(){

        var result = companyService.findById(COMPANY_ID);

        Assertions.assertTrue(result.isPresent());

        var expected = new CompanyReadDTO(COMPANY_ID);
        result.ifPresent(actual -> assertEquals(expected, actual));

    }

}
