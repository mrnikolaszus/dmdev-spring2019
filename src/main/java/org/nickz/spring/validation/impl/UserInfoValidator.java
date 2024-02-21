package org.nickz.spring.validation.impl;

import liquibase.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.nickz.spring.database.repository.CompanyRepository;
import org.nickz.spring.dto.UserCreateEditDto;
import org.nickz.spring.validation.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto> {

    private final CompanyRepository companyRepository;
    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value.getFirstname()) || StringUtils.hasText(value.getLastname());
    }
}
