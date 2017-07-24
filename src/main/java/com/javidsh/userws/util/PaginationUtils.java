package com.javidsh.userws.util;

/**
 * 
 * PaginationUtils.java
 * Purpose: Helper class for calculating next page, previous page, last page etc.
 * Arguments: totalCount - total count of users, 
 *            size - users count per page,
 *            page - current page
 * 
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
public class PaginationUtils {

    private final long page;
    private final int size;
    private final long totalCount;

    public PaginationUtils(long totalCount, long page, int size) {
        this.page = page;
        this.totalCount = totalCount;
        this.size = size;
    }

    public boolean checkValidity() {
        return checkValidity(page);
    }    
    
    private boolean checkValidity(long page) {
        long k = (page - 1) * size + 1;
        return  k <= totalCount && k >= 1;
    }

    public boolean hasPrevPage() {
        return checkValidity(page - 1);
    }

    public boolean hasNextPage() {
        return checkValidity(page + 1);
    }

    public long getPrevIndex() {
        return page > 1 ? page - 1 : 1;
    }

    public long getNextIndex() {
        return page + 1;
    }

    public int getFirstPage() {
        return 1;
    }

    public long getLastPage() {
        if (!checkValidity(page)){
            return 1;
        }
        
        long nextPage = (totalCount / size);
        int remainder = (int)(totalCount - nextPage * size);
        remainder = (remainder % size);
        nextPage += (remainder == 0) ? 0 : 1;

        return nextPage;
    }
}
