package org.nickz.spring.service;

import org.nickz.spring.database.entity.Company;
import org.nickz.spring.database.repository.CompanyRepository;
import org.nickz.spring.database.repository.CrudRepository;
import org.nickz.spring.database.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;

    public UserService(UserRepository userRepository,
                       CrudRepository<Integer, Company> companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }
}
