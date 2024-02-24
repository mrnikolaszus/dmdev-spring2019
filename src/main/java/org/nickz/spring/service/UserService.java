package org.nickz.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.spec.OAEPParameterSpec;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final ImageService imageService;

//    @PostFilter("filterObject.role.name().equals('AdDMIN')")
//    @PostFilter("@companyService.findById(filterObject.company.id()).isPresent()")
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

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto){
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userCreateEditMapper.map(dto);
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();

    }

    public Optional<byte[]> findAvatar(Long id){
        return  userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()){
            imageService.upload(image.getOriginalFilename(), image.getInputStream());

        }
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto){
        return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(userDto.getImage());
                    return userCreateEditMapper.map(userDto, entity);
                })
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user:" + username));
    }
}
