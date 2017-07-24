package com.javidsh.userws.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.hateoas.ResourceSupport;

/**
 * 
 * UserDtoList.java
 * Purpose: POJO class to hold UserDto list [UserDto.java] and 
 * other additional information(total users count, total pages count etc) for pagination.
 * 
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
public class UserDtoList extends ResourceSupport {
    @JsonProperty
    private List<UserDto> content;
    private long totalUsers;
    private long totalPages;
    private int usersPerPage;
    private int maxUsersPerPage;

    @JsonCreator
    public UserDtoList() {
    }    
    
    @JsonCreator
    public UserDtoList(List<UserDto> content, long totalUsers, long totalPages, int usersPerPage, int maxUsersPerPage) {
        this.content = content;
        this.totalUsers = totalUsers;
        this.totalPages = totalPages;
        this.usersPerPage = usersPerPage;
        this.maxUsersPerPage = maxUsersPerPage;
    }

    public List<UserDto> getContent() {
        return content;
    }

    public void setContent(List<UserDto> content) {
        this.content = content;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public int getUsersPerPage() {
        return usersPerPage;
    }

    public void setUsersPerPage(int usersPerPage) {
        this.usersPerPage = usersPerPage;
    }

    public int getMaxUsersPerPage() {
        return maxUsersPerPage;
    }

    public void setMaxUsersPerPage(int maxUsersPerPage) {
        this.maxUsersPerPage = maxUsersPerPage;
    }

    @Override
    public String toString() {
        return "UserList{" + "content=" + content + ", totalUsers=" + totalUsers + ", totalPages=" + totalPages + ", usersPerPage=" + usersPerPage + ", maxUsersPerPage=" + maxUsersPerPage + '}';
    }
    
}
