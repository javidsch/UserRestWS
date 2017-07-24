package com.javidsh.userws.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDate;

/**
 * 
 * LocalDateSerializer.java
 * Purpose: Serializer LocalDate(Java 8) for Jackson/JSON.
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
public class LocalDateSerializer extends StdSerializer<LocalDate> {
    
    protected LocalDateSerializer(){
        super(LocalDate.class);
    }
    
    @Override
    public void serialize(LocalDate arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
        arg1.writeString(arg0.toString());
    }
}
