package com.javidsh.userws.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 
 * SexType.java
 * Purpose: Enum-class for gender type(male, female).
 * 
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
public enum SexType {
    MALE("male"), FEMALE("female");
    
    private String key;
    
    SexType(String key){
        this.key = key;
    }
    
    @JsonCreator
    public static SexType fromString(String key) {
        return key == null
                    ? null
                    : SexType.valueOf(key.toUpperCase());
    }
    
    @JsonValue
    public String getKey() {
        return key;
    }
}
