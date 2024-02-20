package org.nickz.spring.integration.service;


import org.assertj.core.api.Assertions;
import org.nickz.spring.database.entity.Role;
import org.nickz.spring.dto.UserCreateEditDto;
import org.nickz.spring.dto.UserReadDto;
import org.nickz.spring.integration.IntegrationTestBase;
import org.nickz.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.nickz.spring.database.pool.ConnectionPool;
import org.nickz.spring.service.UserService;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_1 = 1L;
    private static final Integer COMPANY_1 = 1;

    private final UserService userService;


    @Test
    void findAll(){
        List<UserReadDto> result = userService.findAll();
        Assertions.assertThat(result).hasSize(5);
    }

    @Test
    void findById(){
        Optional<UserReadDto> mayBeUser = userService.findById(USER_1);
        org.junit.jupiter.api.Assertions.assertTrue(mayBeUser.isPresent());
        mayBeUser.ifPresent(user -> org.junit.jupiter.api.Assertions.assertEquals("ivan@gmail.com", user.getUsername()));
    }

    @Test
    void create(){

        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                LocalDate.now(),
                "test",
                "test",
                Role.ADMIN,
                COMPANY_1
        );

        var actualResult = userService.create(userDto);
        org.junit.jupiter.api.Assertions.assertEquals(userDto.getUsername(), actualResult.getUsername());
        org.junit.jupiter.api.Assertions.assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        org.junit.jupiter.api.Assertions.assertEquals(userDto.getFirstname(), actualResult.getFirstname());
        org.junit.jupiter.api.Assertions.assertEquals(userDto.getLastname(), actualResult.getLastname());
        org.junit.jupiter.api.Assertions.assertEquals(userDto.getCompanyId(), actualResult.getCompany().id());
        org.junit.jupiter.api.Assertions.assertSame(userDto.getRole(), actualResult.getRole());
    }

    @Test
    void update(){
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                LocalDate.now(),
                "test",
                "test",
                Role.ADMIN,
                COMPANY_1
        );

        var actualResult = userService.update(USER_1, userDto);
        org.junit.jupiter.api.Assertions.assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user->{
            org.junit.jupiter.api.Assertions.assertEquals(userDto.getUsername(), user.getUsername());
            org.junit.jupiter.api.Assertions.assertEquals(userDto.getBirthDate(), user.getBirthDate());
            org.junit.jupiter.api.Assertions.assertEquals(userDto.getFirstname(), user.getFirstname());
            org.junit.jupiter.api.Assertions.assertEquals(userDto.getLastname(), user.getLastname());
            org.junit.jupiter.api.Assertions.assertEquals(userDto.getCompanyId(), user.getCompany().id());
            org.junit.jupiter.api.Assertions.assertSame(userDto.getRole(), user.getRole());

        });
    }


    @Test
    void delete(){
        org.junit.jupiter.api.Assertions.assertTrue(userService.delete(USER_1));
        org.junit.jupiter.api.Assertions.assertFalse(userService.delete(-124L));

    }

}
