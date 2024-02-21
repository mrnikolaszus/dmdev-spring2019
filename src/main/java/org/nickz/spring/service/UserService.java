package org.nickz.spring.service;

import lombok.RequiredArgsConstructor;
import org.nickz.spring.database.entity.User;
import org.nickz.spring.database.repository.CompanyRepository;
import org.nickz.spring.database.repository.UserRepository;
import org.nickz.spring.dto.UserCreateEditDto;
import org.nickz.spring.dto.UserFilter;
import org.nickz.spring.dto.UserReadDto;
import org.nickz.spring.mapper.UserCreateEditMapper;
import org.nickz.spring.mapper.UserReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.OAEPParameterSpec;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;

    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable){
        Specification<User> spec = userSpecification(filter);
        Page<User> usersPage = userRepository.findAll(spec, pageable);
        return usersPage.map(userReadMapper::map);
    }

    public List<UserReadDto> findAll(){
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto){
        return Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();

    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto){
        return userRepository.findById(id)
                .map(entity -> userCreateEditMapper.map(userDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id){
        return userRepository.findById(id)
                .map(entity ->{
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Specification<User> userSpecification(UserFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.firstname() != null && !filter.firstname().isEmpty()) {
                String firstNamePattern = filter.firstname() + "%";
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstname")), firstNamePattern.toLowerCase()));
            }

            if (filter.lastname() != null && !filter.lastname().isEmpty()) {
                String lastNamePattern = filter.lastname() + "%";
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")), lastNamePattern.toLowerCase()));
            }

            if (filter.birthDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("birthDate"), filter.birthDate()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
