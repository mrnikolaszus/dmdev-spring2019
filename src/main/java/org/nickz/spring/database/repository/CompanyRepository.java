package org.nickz.spring.database.repository;

import org.nickz.spring.database.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Integer> {

//    @Query(name = "Company.findByName")
    @Query("select c from Company c " +
            "join fetch c.locales cl " +
            "where c.name = :name2")
    Optional<Company> findByName(@Param("name2") String name);

    List<Company> findByNameContainingIgnoreCase(String fragment);


//    Optional<Company> findById(Integer id);
//
//
//    void delete(Company entity);

}
