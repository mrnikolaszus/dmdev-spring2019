package org.nickz.spring.integration.service;


import org.nickz.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.nickz.spring.database.pool.ConnectionPool;
import org.nickz.spring.service.UserService;
import org.springframework.test.annotation.DirtiesContext;

@IT
@RequiredArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIT {

    private final UserService userService;
    private final ConnectionPool pool1;

    @Test
    void test(){
        System.err.println("TEST");
    } @Test
    void test2(){
        System.err.println("TEST2");
    }
}
