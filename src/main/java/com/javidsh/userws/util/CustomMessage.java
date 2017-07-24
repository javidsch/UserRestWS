package com.javidsh.userws.util;

import java.util.List;

/**
 * 
 * CustomMessage.java
 * Purpose: Exception-handling messaging class.
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
public class CustomMessage {
    String status;
    String message;
    List<String> details;

    public CustomMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public CustomMessage(String status, String message, List<String> details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }
    
    
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "CustomMessage{" + "status=" + status + ", message=" + message + ", details=" + details + '}';
    }
    
}
