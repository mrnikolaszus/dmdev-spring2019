package org.nickz.spring.service;

import lombok.RequiredArgsConstructor;
import org.nickz.spring.database.repository.CompanyRepository;
import org.nickz.spring.dto.CompanyReadDTO;
import org.nickz.spring.listenter.entity.AccessType;
import org.nickz.spring.listenter.entity.EntityEvent;
import org.nickz.spring.mapper.CompanyReadMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CompanyReadMapper companyReadMapper;



    public Optional<CompanyReadDTO> findById(Integer id){
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return companyReadMapper.map(entity);
                });
    }


    public List<CompanyReadDTO> findAll(){
        return companyRepository.findAll().stream()
                .map(companyReadMapper::map)
                .toList();
    }
}
