package com.javidsh.userws.exception;

/**
 * 
 * UserNotFoundException.java
 * Purpose: Custom exception class.
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
public class UserNotFoundException extends RuntimeException {
    String message;
    long userId;
    
    public UserNotFoundException(String message, long userId){
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    
}
