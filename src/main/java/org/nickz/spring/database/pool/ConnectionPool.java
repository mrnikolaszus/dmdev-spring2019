package org.nickz.spring.database.pool;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;


@Component(value ="pool1")
public class ConnectionPool  {
    private final String username;
    private final Integer poolSize;


    @Autowired
    public ConnectionPool(@Value("${db.username}") String username,
                          @Value("${db.pool.size}") Integer poolSize) {
        this.username = username;
        this.poolSize = poolSize;
        System.out.println("CONSTRUCTOR");
    }

   @PreDestroy
    public void  destroy(){
       System.out.println("POSTCONSTUCT");
    }
}
