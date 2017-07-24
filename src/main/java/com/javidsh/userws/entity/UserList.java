package com.javidsh.userws.entity;

import com.javidsh.userws.util.PaginationUtils;
import java.util.List;

/**
 * 
 * User.java
 * Purpose: POJO class to hold User list [User.java] and 
 * other additional information(total users count, total pages count etc) for pagination.
 * 
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
public class UserList {
    private List<User> userList;
    private PaginationUtils pageUtils;
    private long totalUsers;
    private long totalPages;
    private int usersPerPage;
    private int maxUsersPerPage;    

    public UserList(List<User> userList, PaginationUtils pageUtils, long totalUsers, long totalPages, int usersPerPage, int maxUsersPerPage) {
        this.userList = userList;
        this.pageUtils = pageUtils;
        this.totalUsers = totalUsers;
        this.totalPages = totalPages;
        this.usersPerPage = usersPerPage;
        this.maxUsersPerPage = maxUsersPerPage;
    }

   
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public PaginationUtils getPageUtils() {
        return pageUtils;
    }

    public void setPageUtils(PaginationUtils pageUtils) {
        this.pageUtils = pageUtils;
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
        return "UserList{" + "userList=" + userList + ", pageUtils=" + pageUtils + ", totalUsers=" + totalUsers + ", totalPages=" + totalPages + ", usersPerPage=" + usersPerPage + ", maxUsersPerPage=" + maxUsersPerPage + '}';
    }
    
}
