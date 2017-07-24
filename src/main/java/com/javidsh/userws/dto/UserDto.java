package com.javidsh.userws.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.javidsh.userws.util.LocalDateDeserializer;
import com.javidsh.userws.util.LocalDateSerializer;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import org.springframework.hateoas.ResourceSupport;

/**
 * 
 * UserDto.java
 * Purpose: Main DTO class. Converting from/to Jackson JSON.
 * 
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 * 
 * ValidationMessages.properties - Stored validation messages in /src/main/resource/
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends ResourceSupport {

    private long userId;

    @NotNull
    @Size(min = 3, max = 15, message = "{invalid_username}")
    private String userName;

    @NotNull
    @Size(min = 3, max = 15, message = "{invalid_firstname}")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 15, message = "{invalid_lastname}")
    private String lastName;

    @NotNull
    private SexType sex;

    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "{invalid_email}")
    private String email;

    @NotNull
    @Min(value = 1000, message = "{invalid_salary}")
    private BigDecimal salary;
    
    @NotNull
    @JsonFormat(pattern = "dd::MM::yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    @NotNull
    private Boolean single;
    
    @NotNull
    @Range(min = 0, max = 20, message = "{invalid_family_member_count}")
    Integer familyMembersCount;
    
    
    @JsonCreator
    public UserDto() {
        userId = 0;
    }
    
    public UserDto(UserDto user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.sex = user.getSex();
        this.email = user.getEmail();
        this.salary = user.getSalary();
        this.birthDate = user.getBirthDate();
        this.familyMembersCount = user.getFamilyMembersCount();
        this.single = user.isSingle();        
    }    

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName.toLowerCase();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SexType getSex() {
        return sex;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }    
    
    public int getFamilyMembersCount() {
        return familyMembersCount;
    }

    public void setFamilyMembersCount(int familyMembersCount) {
        this.familyMembersCount = familyMembersCount;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }    
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final UserDto other = (UserDto) obj;

        return this.userId == other.userId;
    }

    @Override
    public String toString() {
        return "UserDto{" + "userId=" + userId + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", sex=" + sex + ", email=" + email + ", salary=" + salary + ", birthDate=" + birthDate + ", single=" + single + ", familyMembersCount=" + familyMembersCount + '}';
    }
}
