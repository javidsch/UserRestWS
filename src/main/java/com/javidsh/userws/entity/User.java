package com.javidsh.userws.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 
 * User.java
 * Purpose: Main entity class(JPA).
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;    
    
    @Column(name="user_name", unique = true, nullable = false, length = 16)
    private String userName;

    @Column(name="first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name="last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name="sex", nullable = false)
    private String sex;

    @Column(name="birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name="single", nullable = false)
    private Boolean single;
    
    @Column(name="family_members_count", nullable = false)
    private Integer familyMembersCount;    
    
    @Column(name="email", nullable = false)
    private String email;

    @Column(name="salary", nullable = false)
    private BigDecimal salary;
    
    @Column(nullable = false)
    @Version
    private Long version = 1L;
    
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }      

    public long getId() {
        return id;
    }

    public void setId(long userId) {
        this.id = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }    
    
    public Boolean getSingle() {
        return single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }

    public Integer getFamilyMembersCount() {
        return familyMembersCount;
    }

    public void setFamilyMembersCount(Integer familyMembersCount) {
        this.familyMembersCount = familyMembersCount;
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

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
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
        final User other = (User) obj;
        
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", sex=" + sex + ", birthDate=" + birthDate + ", single=" + single + ", familyMembersCount=" + familyMembersCount + ", email=" + email + ", salary=" + salary +  ", version=" + version + '}';
    }
}
