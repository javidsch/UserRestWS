package com.javidsh.userws.controller;

import com.javidsh.userws.dto.SexType;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.javidsh.userws.dto.UserDto;
import com.javidsh.userws.dto.UserDtoList;
import com.javidsh.userws.entity.User;
import com.javidsh.userws.entity.UserList;
import com.javidsh.userws.service.UserService;
import com.javidsh.userws.util.PaginationUtils;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * UserRestController.java
 * Purpose: RESTfull controller for User resource [UserDto.java].
 * Get UserDto objects as a parameter, converts these ones to the instances of User entity class. 
 * Calls service layer (business-logic) [ServiceImpl.java]
 * Converts entity instances to the instances of UserDto class.
 * Supports GET, POST, PUT, DELETE HTTP-methods.
 * 
 * @Exception handling: Controller advice [UserRestControllerAdvice.java]
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
@RestController
@RequestMapping("/userws")
public class UserRestController {

    public static final Logger logger = Logger.getLogger(UserRestController.class);

    @Autowired
    UserService userService;
    
    @Autowired
    ModelMapper modelMapper; 

    /**
     * Get UserDto list. Using pagination. 
     *
     * @param page Page number of results. Non-zero, positive integer.
     *             Not required. Default value is 1
     * @param size Users count per page. Non-zero, positive integer.
     *             Not required. Default value is ${usersPerPage}. Look at application.properties.
     * 
     * @return UserDtoList instance. 
     *         List of users and additional information about list and pagination.
     */    
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public UserDtoList getUsers(@RequestParam(value = "page", defaultValue = "1") long page,
                                @RequestParam(value = "size", defaultValue = "${usersPerPage}") int size) {
        logger.info("Get users by query-params: page=" + page + ", size = " + size);
        
        UserList userList = userService.getUsers(page, size);
        PaginationUtils pageUtils = userList.getPageUtils();
        
        //convert..
        List<UserDto> listOfUserDto = convertListToDto(userList.getUserList());
        
        listOfUserDto.forEach((user) -> {
            user.add(linkTo(methodOn(UserRestController.class).getUser(user.getUserId())).withSelfRel());
        });

        UserDtoList userDtoList = new UserDtoList(listOfUserDto, userList.getTotalUsers(), pageUtils.getLastPage(), listOfUserDto.size(), size);
        
        long firstPage = pageUtils.getFirstPage();
        long lastPage = pageUtils.getLastPage();
        if(firstPage != lastPage){
            userDtoList.add(linkTo(methodOn(UserRestController.class).getUsers(firstPage, size)).withRel("firstPage"));
            userDtoList.add(linkTo(methodOn(UserRestController.class).getUsers(lastPage, size)).withRel("lastPage"));
        }        
        
        if(pageUtils.hasPrevPage()){
            long prevPageIndex = pageUtils.getPrevIndex();
            userDtoList.add(linkTo(methodOn(UserRestController.class).getUsers(prevPageIndex, size)).withRel("prevPage"));
        }
        
        if(pageUtils.hasNextPage()){
            long nextPageIndex = pageUtils.getNextIndex();
            userDtoList.add(linkTo(methodOn(UserRestController.class).getUsers(nextPageIndex, size)).withRel("nextPage"));
        }                
        
        logger.debug("Get users by query-params. Done. ");
        return userDtoList;
    }

    /**
     * Get UserDto.
     *
     * @param userId User's unique ID.
     * 
     * @return UserDto instance.
     */       
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable(value = "id") long userId) {
        logger.info("Get user by id=" + userId);
        User user = userService.getUserById(userId);
        
        //convert..
        UserDto userDto = convertToDto(user);
        userDto.add(linkTo(methodOn(UserRestController.class).getUser(userDto.getUserId())).withSelfRel());
        
        logger.info("Get user Done. " + userId + ", " + userDto);
        return userDto;
    }

    /**
     * Add new UserDto.
     *
     * @param userDto New UserDto instance.
     * 
     * @return Permanent UserDta instance.
     */      
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Adding user. " + userDto);
        //convert...
        User user = convertToEntity(userDto);
        
        //Service
        user = userService.save(user);
        
        //convert..
        userDto = convertToDto(user);
        userDto.add(linkTo(methodOn(UserRestController.class).getUser(userDto.getUserId())).withSelfRel());
        
        logger.info("Adding user Done. " + userDto);
        return userDto;
    }

    /**
     * Update existence User entity.
     *
     * @param userId User's unique id.
     * @param userDto New UserDto instance.
     * 
     * @return Refreshed UserDto instance.
     */         
    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable(value = "id") long userId, @Valid @RequestBody UserDto userDto) {
        logger.info("Updating user by id=" + userId + ", " + userDto);
        //convert..
        User user = convertToEntity(userDto);
        user.setId(userId);
        
        //Service
        user = userService.update(userId, user);
        
        //convert..
        userDto = convertToDto(user);     
        userDto.add(linkTo(methodOn(UserRestController.class).getUser(userDto.getUserId())).withSelfRel());
        
        logger.info("Updating user Done. " + userId + ", " + userDto);
        return userDto;
    }

    /**
     * Delete existence UserDto.
     *
     * @param userId User's unique id.
     * 
     * @return Removed UserDto.
     */          
    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto deleteUser(@PathVariable(value = "id") long userId) {
        logger.info("Deletion user by id=" + userId);
        
        //Service
        User user = userService.delete(userId);
        
        //convert..
        UserDto userDto = convertToDto(user);
        userDto.add(linkTo(methodOn(UserRestController.class).getUser(userDto.getUserId())).withSelfRel());
        
        logger.info("Deletion user. Done. " + userDto);
        return userDto;
    }

    //Converting instances from User to UserDto
    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setUserId(user.getId());
        userDto.setSex(SexType.valueOf(user.getSex().toUpperCase()));
        return userDto;
    }    

    //Converting instances from List<User> to List<UserDto>
    private List<UserDto> convertListToDto(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        userList.forEach((user) -> {
            userDtoList.add(convertToDto(user));
        });
        
        return userDtoList;
    }    

    //Converting instances from UserDto to User
    private User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setSex(userDto.getSex().getKey());
        return user;
    }        
    
    //Converting instances from List<UserDto> to List<User>
    private List<User> convertListToEntity(List<UserDto> userDtoList) {
        List<User> userList = new ArrayList<>();
        userDtoList.forEach((userDto) -> {
            userList.add(convertToEntity(userDto));
        });
        
        return userList;
    }            
}
