package org.nickz.spring.mapper;

import org.nickz.spring.database.entity.Company;
import org.nickz.spring.dto.CompanyReadDTO;
import org.springframework.stereotype.Component;

@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDTO> {

    @Override
    public CompanyReadDTO map(Company object) {
        return new CompanyReadDTO(
                object.getId(),
                object.getName()
        );
    }
}
