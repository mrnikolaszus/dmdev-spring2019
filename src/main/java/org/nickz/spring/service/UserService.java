package org.nickz.spring.service;

import lombok.RequiredArgsConstructor;
import org.nickz.spring.database.repository.CompanyRepository;
import org.nickz.spring.database.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;


}
