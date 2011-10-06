package com.topcoder.accounting.entities.dto;

import java.io.Serializable;
import java.lang.*;
import java.util.List;

/**
 * #### Purpose This class represents a cointainer for paged results This class provides a method that serializes the
 * entity contents into a JSON string. #### Thread Safety This class is mutable and not thread safe
 */
public class PagedResult<T> implements Serializable {
    /**
     * #### Purpose Represents the records in the result #### Usage it is managed with a getter and setter #### Legal
     * Values It may have any value #### Mutability It is fully mutable
     */
    private List<T> records;
    /**
     * #### Purpose Represents the totak number of records that the criteria used to generate it would return unpaged
     * #### Usage it is managed with a getter and setter #### Legal Values It may have any value #### Mutability It is
     * fully mutable
     */
    private int totalRecords;
    /**
     * #### Purpose Represents the 1-based page number, or -1 if no paging #### Usage it is managed with a getter and
     * setter #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private int pageNo;
    /**
     * #### Purpose Represents the size of each page #### Usage it is managed with a getter and setter #### Legal Values
     * It may have any value #### Mutability It is fully mutable
     */
    private int pageSize;
    /**
     * #### Purpose Represents the total number of pages #### Usage it is managed with a getter and setter #### Legal
     * Values It may have any value #### Mutability It is fully mutable
     */
    private int totalPages;

    /**
     * #### Purpose Empty constructor
     */
    public PagedResult() {
    }

    /**
     * <p>
     * Getter method for the records.
     * </p>
     * @return the records
     */
    public List<T> getRecords() {
        return records;
    }

    /**
     * <p>
     * Setter method for the records.
     * </p>
     * @param records the records to set
     */
    public void setRecords(List<T> records) {
        this.records = records;
    }

    /**
     * <p>
     * Getter method for the totalRecords.
     * </p>
     * @return the totalRecords
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * <p>
     * Setter method for the totalRecords.
     * </p>
     * @param totalRecords the totalRecords to set
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * <p>
     * Getter method for the pageNo.
     * </p>
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * <p>
     * Setter method for the pageNo.
     * </p>
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * <p>
     * Getter method for the pageSize.
     * </p>
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * <p>
     * Setter method for the pageSize.
     * </p>
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * <p>
     * Getter method for the totalPages.
     * </p>
     * @return the totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * <p>
     * Setter method for the totalPages.
     * </p>
     * @param totalPages the totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * #### Purpose Provides the JSON representation of the contents of this entity. #### Parameters return - the JSON
     * representation of the contents of this entity. #### Implementation Notes See CS 1.3.2 for details
     * @param Return
     * @return
     */
    public String toJSONString() {
        throw new UnsupportedOperationException("Not implemented in mock.");
    }
}
