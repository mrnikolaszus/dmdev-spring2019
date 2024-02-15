package org.nickz.spring.database.repository;


import lombok.extern.slf4j.Slf4j;
import org.nickz.spring.bpp.Auditing;
import org.nickz.spring.bpp.InjectBean;
import org.nickz.spring.bpp.Transaction;
import org.nickz.spring.database.entity.Company;
import org.nickz.spring.database.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transaction
@Auditing

public class CompanyRepository implements CrudRepository<Integer, Company> {


    private final ConnectionPool pool1;

    private final List<ConnectionPool> pools;

    private final Integer poolSize;

    public CompanyRepository(ConnectionPool pool1,
                             List<ConnectionPool> pools,
                             @Value("${db.pool.size}") Integer poolSize) {
        this.pool1 = pool1;
        this.pools = pools;
        this.poolSize = poolSize;
    }

    @PostConstruct
    private void init(){
        log.error("init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        log.error("find by Id method");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity) {
        log.error("delete method");

    }

}
