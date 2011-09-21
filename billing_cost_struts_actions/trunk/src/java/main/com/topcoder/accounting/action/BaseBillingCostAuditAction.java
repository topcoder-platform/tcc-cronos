/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

/**
 * <p>
 * This is the base class for actions that are used to show auditing
 * information, including billing cost export history/details, and auditing
 * records. It simply has page number and page size properties that subclasses
 * can use to support pagination.
 * </p>
 * <p>
 * <strong> Thread Safety:</strong> This class is not thread-safe because it's
 * mutable. However, dedicated instance of struts action will be created by the
 * struts framework to process the user request, so the struts actions don't
 * need to be thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseBillingCostAuditAction extends BaseAction {
    /**
     * The page number of the search (for pagination). It has both getter and
     * setter. It must be non-negative. It does not need to be initialized when
     * the instance is created.
     */
    private int pageNumber;
    /**
     * The page size of the search (for pagination). It has both getter and
     * setter. It must be non-negative. It does not need to be initialized when
     * the instance is created.
     */
    private int pageSize;

    /**
     * This is the default constructor for the class.
     *
     */
    protected BaseBillingCostAuditAction() {
    }

    /**
     * Getter for the page number.
     *
     * @return the page number.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Getter for the page size.
     *
     * @return the page size.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter for the page number.
     *
     * @param pageNumber The page number of the search (for pagination).
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Setter for the page size.
     *
     * @param pageSize The page size of the search (for pagination).
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
