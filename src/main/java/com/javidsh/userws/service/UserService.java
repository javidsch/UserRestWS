package com.javidsh.userws.service;

import com.javidsh.userws.entity.User;
import com.javidsh.userws.entity.UserList;

/**
 * 
 * UserService.java
 * Purpose: Abstraction of service-layer.
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
public interface UserService {
    
    UserList getUsers(long page, int size);

    User getUserById(long userId);

    User save(User user);

    User update(Long userId, User user);

    User delete(Long userId);
}
