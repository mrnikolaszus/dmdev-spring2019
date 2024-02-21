package org.nickz.spring.database.repository;

import lombok.RequiredArgsConstructor;
import org.nickz.spring.database.entity.Role;
import org.nickz.spring.database.entity.User;
import org.nickz.spring.dto.PersonalInfo;
import org.nickz.spring.dto.UserFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private static final String FIND_BY_COMPANY_AND_ROLE = """
            SELECT
                firstname,
                lastname,
                birth_date
            FROM users
            WHERE company_id = ?
                AND role = ?
            """;

    private static final String UPDATE_COMPANY_AND_ROLE = """
            UPDATE users
            SET company_id = ?,
                role = ?
            WHERE id = ?
            """;

    private static final String UPDATE_COMPANY_AND_ROLE_NAMED = """
            UPDATE users
            SET company_id = :companyId,
                role = :role
            WHERE id = :id
            """;

    private final EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        // Фильтр по имени
        if (filter.firstname() != null && !filter.firstname().isEmpty()) {
            // Для примера, предположим, что filter.getFirstname() может содержать как полное имя, так и первую букву имени.
            // Поиск по первой букве или части имени
            String firstNamePattern = filter.firstname() + "%";
            predicates.add(cb.like(cb.lower(user.get("firstname")), firstNamePattern.toLowerCase()));
        }

        // Фильтр по фамилии
        if (filter.lastname() != null && !filter.lastname().isEmpty()) {
            String lastNamePattern = filter.lastname() + "%";
            predicates.add(cb.like(cb.lower(user.get("lastname")), lastNamePattern.toLowerCase()));
        }

        // Фильтр по дате рождения
        if (filter.birthDate() != null) {
            predicates.add(cb.equal(user.get("birthDate"), filter.birthDate()));
        }

        criteria.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(criteria).getResultList();
    }
    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE, (rs, rowNum) -> new PersonalInfo(
        rs.getString("firstname"),
        rs.getString("lastname"),
        rs.getDate("birth_date").toLocalDate()
                ), companyId, role.name());
    }

    @Override
    public void updateCompanyAndRole(List<User> users) {
        var args = users.stream()
                .map(user -> new Object[]{user.getCompany().getId(),
                        user.getRole().name(),
                        user.getId()}).toList();
        jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args);

    }

    @Override
    public void updateCompanyAndRoleNamed(List<User> users) {
        var args = users.stream()
                .map(user -> Map.of(
                        "companyId", user.getCompany().getId(),
                        "role", user.getRole().name(),
                        "id", user.getId()
                )).map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED, args);
    }
}
