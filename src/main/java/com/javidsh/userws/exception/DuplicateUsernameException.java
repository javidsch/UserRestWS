package com.javidsh.userws.exception;

/**
 * 
 * UsernameExistsException.java
 * Purpose: Custom exception class.
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
public class DuplicateUsernameException extends RuntimeException {
    String message;
    String userName;    

    public DuplicateUsernameException(String message, String userName) {
        this.message = message;
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
