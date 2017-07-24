package com.javidsh.userws.dao;

import com.javidsh.userws.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * UserDao.java
 * Purpose: Abstraction and implementation of the DAO layer(Spring DATA JPA).
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
@Repository
public interface UserDao  extends JpaRepository<User, Long>{
    
    User getById(Long Id);

    User getByUserName(String userName);
    
}
