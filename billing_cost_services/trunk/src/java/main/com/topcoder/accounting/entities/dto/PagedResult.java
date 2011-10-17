/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.entities.dto;

import java.io.Serializable;
import java.util.List;

import com.topcoder.accounting.entities.JsonPrintable;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 * This class represents a container for paged results. <br>
 * This class provides a method that serializes the entity contents into a JSON string.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @param <T> the result type
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class PagedResult<T> implements JsonPrintable, Serializable {
    /**
     * Represents the records in the result. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private List<T> records;

    /**
     * Represents the total number of records that the criteria used to generate It would return unpaged. It is
     * managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private int totalRecords;

    /**
     * Represents the 1-based page number, or -1 if no paging. It is managed with a getter and setter. It may have
     * any value. It is fully mutable.
     */
    private int pageNo;

    /**
     * Represents the size of each page. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private int pageSize;

    /**
     * Represents the total number of pages. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private int totalPages;

    /**
     * Empty constructor.
     */
    public PagedResult() {
        // Empty
    }

    /**
     * <p>
     * Getter method for records, simply return the namesake instance variable.
     * </p>
     *
     * @return the records
     */
    public List<T> getRecords() {
        return records;
    }

    /**
     * <p>
     * Setter method for records, simply assign the value to the instance variable.
     * </p>
     *
     * @param records
     *            the records to set
     */
    public void setRecords(List<T> records) {
        this.records = records;
    }

    /**
     * <p>
     * Getter method for totalRecords, simply return the namesake instance variable.
     * </p>
     *
     * @return the totalRecords
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * <p>
     * Setter method for totalRecords, simply assign the value to the instance variable.
     * </p>
     *
     * @param totalRecords
     *            the totalRecords to set
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * <p>
     * Getter method for pageNo, simply return the namesake instance variable.
     * </p>
     *
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * <p>
     * Setter method for pageNo, simply assign the value to the instance variable.
     * </p>
     *
     * @param pageNo
     *            the pageNo to set
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * <p>
     * Getter method for pageSize, simply return the namesake instance variable.
     * </p>
     *
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * <p>
     * Setter method for pageSize, simply assign the value to the instance variable.
     * </p>
     *
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * <p>
     * Getter method for totalPages, simply return the namesake instance variable.
     * </p>
     *
     * @return the totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * <p>
     * Setter method for totalPages, simply assign the value to the instance variable.
     * </p>
     *
     * @param totalPages
     *            the totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Provides the JSON representation of the contents of this entity.
     *
     * @return the JSON representation of the contents of this entity.
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        if (records != null) {
            JSONArray jsonArray = new JSONArray();
            if (!records.isEmpty()) {
                if (records.get(0) instanceof JsonPrintable) {
                    for (T record : records) {
                        jsonArray.addString(((JsonPrintable) record).toJSONString());
                    }
                } else {
                    for (T record : records) {
                        jsonArray.addString(record.toString());
                    }
                }
            }
            jsonObject.setArray("records", jsonArray);
        } else {
            jsonObject.setNull("records");
        }
        jsonObject.setInt("totalRecords", totalRecords);
        jsonObject.setInt("pageNo", pageNo);
        jsonObject.setInt("pageSize", pageSize);
        jsonObject.setInt("totalPages", totalPages);
        return jsonObject.toJSONString();
    }
}
