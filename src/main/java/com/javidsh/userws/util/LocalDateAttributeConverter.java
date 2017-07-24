package com.javidsh.userws.util;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * LocalDateAttributeConverter.java
 * Purpose: Converter for new LocalDate and Date types.
 * JPA is not support for LocalDate(Java 8)
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {
	
    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
    	return (locDate == null ? null : Date.valueOf(locDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
    	return (sqlDate == null ? null : sqlDate.toLocalDate());
    }
}
