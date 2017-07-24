package com.javidsh.userws.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalDate;

/**
 * 
 * LocalDateDeserializer.java
 * Purpose: Deserializer LocalDate(Java 8) for Jackson/JSON.
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
public class LocalDateDeserializer extends StdDeserializer<LocalDate> {
    protected LocalDateDeserializer(){
        super(LocalDate.class);
    }
    
    @Override
    public LocalDate deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
        return LocalDate.parse(arg0.getText());
    }
}