/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.entry.base;

import com.topcoder.timetracker.common.TimeTrackerBean;

import java.util.Date;

/**
 * <p>
 * This class holds the information of a base entry. No parameter checking is provided in this
 * class, since the class acts as a mock class for Time Tracker Report component. All the
 * information is reading from the database, so it is assumed to be valid.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public abstract class BaseEntry extends TimeTrackerBean {

    /**
     * <p>
     * This is a date/time representation of this entry (i.e. when it is valid/entered).
     * </p>
     *
     * <p>
     * Initial Value: null
     * </p>
     * <p>
     * Accessed In: getDate()
     * </p>
     * <p>
     * Modified In: setDate()
     * </p>
     * <p>
     * Valid Values: null during initialization, and not-null once it is set.
     * </p>
     */
    private Date date;

    /**
     * <p>
     * This is a description of this entry.
     * </p>
     *
     * <p>
     * Initial Value: null
     * </p>
     * <p>
     * Accessed In: getDescription()
     * </p>
     * <p>
     * Modified In: setDescription()
     * </p>
     * <p>
     * Valid Values: null during initialization, and not-null and not-empty string once it is set.
     * </p>
     */
    private String description;

    /**
     * <p>
     * This is a unique company id that identifies which company this belongs to.
     * </p>
     *
     * <p>
     * Initial Value: 0
     * </p>
     * <p>
     * Accessed In: getCompanyId()
     * </p>
     * <p>
     * Modified In: setCompanyId()
     * </p>
     * <p>
     * Valid Values: 0 when initialization, any value after that (for Time Tracker, it is assumed to
     * be >0)
     * </p>
     */
    private long companyId = 0;

    /**
     * Construct a new <code>BaseEntry</code>.
     */
    protected BaseEntry() {
        // empty
    }

    /**
     * <p>
     * Retrieves the id of the company which this entry belongs to.
     * </p>
     *
     * @return The identifier that tell which company this entry belongs to.
     */
    public long getCompanyId() {
        return this.companyId;
    }

    /**
     * <p>
     * Sets the identifier of the company which this entry belongs to.
     * </p>
     *
     * @param companyId The identifier of the company which this entry belongs to.
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * <p>
     * Returns the date representation of this entry (i.e. when it is valid/entered).
     * </p>
     *
     * @return the date representation of this entry.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * <p>
     * Set the date for this entry.
     * </p>
     *
     * @param date The date which is used to set to the date of the entry.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * <p>
     * Retrieves the description of this entry.
     * </p>
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Sets the description of this entry.
     * </p>
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}