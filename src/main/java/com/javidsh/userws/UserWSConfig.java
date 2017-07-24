package com.javidsh.userws;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * UserWSConfig.java
 * Purpose: Using java-configuration to create ModelMapper bean.
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
@Configuration
public class UserWSConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }    
}
