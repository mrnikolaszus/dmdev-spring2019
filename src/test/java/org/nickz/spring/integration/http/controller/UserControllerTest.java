package org.nickz.spring.integration.http.controller;

import lombok.RequiredArgsConstructor;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nickz.spring.database.entity.Role;
import org.nickz.spring.dto.UserCreateEditDto;
import org.nickz.spring.integration.IntegrationTestBase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @BeforeEach
    void init(){
//        List<GrantedAuthority> roles = Arrays.asList(Role.ADMIN, Role.USER);
//        var testUser = new User("test@gmail.com", "test", roles);
//        var testingAuthenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), roles);
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(testingAuthenticationToken);
//        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("user/users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
    }

    @Test
    void create() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .param(UserCreateEditDto.Fields.username, "test@gmail.com")
                        .param(UserCreateEditDto.Fields.firstname, "Test")
                        .param(UserCreateEditDto.Fields.lastname, "TestTest")
                        .param(UserCreateEditDto.Fields.role, "ADMIN")
                        .param(UserCreateEditDto.Fields.companyId, "1")
                        .param(UserCreateEditDto.Fields.birthDate, "2000-01-01")

                ).andExpectAll(MockMvcResultMatchers.status().is3xxRedirection(),
                MockMvcResultMatchers.redirectedUrlPattern("/users/{\\d+}"));



    }

}