package org.nickz.spring.dto;

import lombok.Value;
import org.nickz.spring.database.entity.Role;

import java.time.LocalDate;

@Value
public class UserReadDto {
    Long id;
    String username;
    LocalDate birthDate;
    String firstname;
    String lastname;
    Role role;
    CompanyReadDTO company;
}
