package org.nickz.spring.service;

import lombok.RequiredArgsConstructor;
import org.nickz.spring.database.entity.Company;
import org.nickz.spring.database.repository.CrudRepository;
import org.nickz.spring.dto.CompanyReadDTO;
import org.nickz.spring.listenter.entity.AccessType;
import org.nickz.spring.listenter.entity.EntityEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CrudRepository<Integer, Company> companyRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;


    public Optional<CompanyReadDTO> findById(Integer id){
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new CompanyReadDTO(entity.id());
                });
    }
}
