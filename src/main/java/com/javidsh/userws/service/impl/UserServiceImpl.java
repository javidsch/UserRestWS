package com.javidsh.userws.service.impl;

import com.javidsh.userws.entity.User;
import com.javidsh.userws.service.UserService;
import com.javidsh.userws.util.PaginationUtils;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javidsh.userws.dao.UserDao;
import com.javidsh.userws.entity.UserList;
import com.javidsh.userws.exception.InvalidParamsException;
import com.javidsh.userws.exception.NoUserFoundException;
import com.javidsh.userws.exception.UserNotFoundException;
import com.javidsh.userws.exception.DuplicateUsernameException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 
 * UserServiceImpl.java
 * Purpose: Main business logics for User resource[User.java].
 * Get User objects as a parameter, execute business logic 
 * and returns entities to Rest Controller[UserRestController].
 * Calls DAO-layer [UserDao.java]
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
@Service
public class UserServiceImpl implements UserService {

    public static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    /**
     * Get User entity list. Using pagination. 
     *
     * @param page Page number of results. Non-zero, positive integer.
     * @param size Users count per page. Non-zero, positive integer.
     * 
     * @return UserList instance. List of users and additional information list and pagination.
     */
    @Override
    public UserList getUsers(long page, int size) {
        logger.debug("Get user-list. page=" + page + ", size=" + size);

        //Validation: 'page' and 'size' params
        if (page <= 0 || size <= 0) {
            throw new InvalidParamsException();
        }

        //Get total count of users
        long totalCount = userDao.count();
        logger.debug("Total users count: " + totalCount);

        PaginationUtils pageUtils = new PaginationUtils(totalCount, page, size);
        //Check: Are page and size valid combination
        boolean checkPageValidity = pageUtils.checkValidity();
        if (!checkPageValidity) {
            throw new NoUserFoundException();
        }

        //Get user-list by 'page/size' params
        Page<User> pageUsers = userDao.findAll(new PageRequest((int) page - 1, size));
        List<User> users = pageUsers.getContent();

        UserList userList = new UserList(users, pageUtils, totalCount, pageUtils.getLastPage(), users.size(), size);
        logger.debug("User-list size: " + users.size());
        return userList;
    }

    /**
     * Get concrete User entity.
     *
     * @param userId User's unique ID.
     * 
     * @return User entity instance.
     */    
    @Override
    public User getUserById(long userId) {
        logger.debug("Get user by id=" + userId);

        //Check: is there a user by id
        User user = userDao.getById(userId);
        if (user == null) {
            throw new UserNotFoundException("Get user failed.", userId);
        }

        logger.debug("Get user by id=" + userId + " DONE. " + user);
        return user;
    }

    /**
     * Save new User entity.
     *
     * @param user New User entity instance.
     * 
     * @throws DuplicateUsernameException when trying to use already existence userName
     * 
     * @return After saving returns permanent User instance.
     */        
    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public User save(User user) {
        logger.debug("Save user. " + user);

        //Check: is userName already exist
        String userName = user.getUserName();
        User userFromDb = userDao.getByUserName(userName);
        if (userFromDb != null) {
            throw new DuplicateUsernameException("Save failed.", userName);
        }

        user = userDao.save(user);

        logger.debug("Save user. DONE. " + user);
        return user;
    }

    /**
     * Update existence User entity.
     *
     * @param userId User's unique id.
     * @param user New User entity instance.
     * 
     * @throws UserNotFoundException if user is not found by userId
     * @throws DuplicateUsernameException when trying to use already existence userName
     * 
     * @return After updating returns refreshed User instance.
     */      
    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public User update(Long userId, User user) {
        logger.info("Update user. " + user);

        //Check: is there a user by id
        User userFromDb = userDao.getById(userId);
        if (userFromDb == null) {
            throw new UserNotFoundException("Update failed.", userId);
        }
        Long version = userFromDb.getVersion();
        logger.debug("User current version=" + version);

        //Check: is userName already exist
        String userName = user.getUserName();
        userFromDb = userDao.getByUserName(userName);
        if (userFromDb != null && userFromDb.getId() != user.getId()) {
            throw new DuplicateUsernameException("Update failed.", userName);
        }

        user.setId(userId);
        user.setVersion(version);
        user = userDao.saveAndFlush(user);

        logger.debug("Update user DONE. " + user);
        return user;
    }

    /**
     * Delete existence User entity.
     *
     * @param userId User's unique id.
     * 
     * @throws UserNotFoundException if user is not found by userId
     * 
     * @return After deletion returns removed User instance.
     */          
    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public User delete(Long userId) {
        logger.debug("Delete user by id = " + userId);

        //Check: is there a user by id
        User user = userDao.getById(userId);
        if (user == null) {
            throw new UserNotFoundException("Deletion failed.", userId);
        }

        userDao.delete(user);

        logger.debug("Delete user. DONE. " + user);
        return user;
    }

}
