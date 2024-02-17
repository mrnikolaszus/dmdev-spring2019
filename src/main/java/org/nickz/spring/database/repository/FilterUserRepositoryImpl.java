package org.nickz.spring.database.repository;

import lombok.RequiredArgsConstructor;
import org.nickz.spring.database.entity.User;
import org.nickz.spring.dto.UserFilter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;
    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);

        var user = criteria.from(User.class);
        criteria.select(user);

        List<Predicate> predicates = new ArrayList<>();
        if(filter.firstname() != null){
            predicates.add(cb.like(user.get("firstname"), filter.firstname()));
        }

        if(filter.firstname() != null){
            predicates.add(cb.like(user.get("lastname"), filter.lastname()));
        }

        if(filter.firstname() != null){
            predicates.add(cb.lessThan(user.get("birthDate"), filter.birthDate()));
        }

        criteria.where(predicates.toArray(Predicate[]::new));


        return entityManager.createQuery(criteria).getResultList();
    }
}
