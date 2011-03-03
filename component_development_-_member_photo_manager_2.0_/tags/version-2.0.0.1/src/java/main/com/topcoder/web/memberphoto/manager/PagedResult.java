/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.manager;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * This class is a container for records from some page. Additionally it stores the total number of records on all
 * pages. It is a simple JavaBean (POJO) that provides getters and setters for all private attributes and performs no
 * argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 2.0
 * @since 2.0
 *
 * @param <T>
 *            the record type.
 */
@SuppressWarnings("serial")
public class PagedResult<T> implements Serializable {
    /**
     * <p>
     * The list of the records on the requested page.
     * </p>
     *
     * <p>
     * Can be any value. Can contain any values. Has getter and setter.
     * </p>
     */
    private List<T> records;

    /**
     * <p>
     * The total number of records on all pages.
     * </p>
     *
     * <p>
     * Can be any value. Has getter and setter.
     * </p>
     */
    private int totalRecords;

    /**
     * <p>
     * Creates an instance of PagedResult.
     * </p>
     */
    public PagedResult() {
        // Empty
    }

    /**
     * <p>
     * Retrieves the list of the records on the requested page.
     * </p>
     *
     * @return the list of the records on the requested page.
     */
    public List<T> getRecords() {
        return records;
    }

    /**
     * <p>
     * Sets the list of the records on the requested page.
     * </p>
     *
     * @param records
     *            the list of the records on the requested page.
     */
    public void setRecords(List<T> records) {
        this.records = records;
    }

    /**
     * <p>
     * Retrieves the total number of records on all pages.
     * </p>
     *
     * @return the total number of records on all pages.
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * <p>
     * Sets the total number of records on all pages.
     * </p>
     *
     * @param totalRecords
     *            the total number of records on all pages.
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
}
