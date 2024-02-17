package org.nickz.spring.database.repository;

import org.nickz.spring.database.entity.User;
import org.nickz.spring.dto.UserFilter;

import java.util.List;

public interface FilterUserRepository {
    List<User> findAllByFilter(UserFilter filter);
}
