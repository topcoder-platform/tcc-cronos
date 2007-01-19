/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This bean represents the set value of a rate used to compute some sort of expenses
 * Each company has different rates.
 * </p>
 * <p>
 * Thread Safety: - This class is mutable, and not thread-safe. Multiple threads are advised to work with their own
 * instance.
 * </p>
 *
 * @author TheCois
 * @author TheCois
 * @version 2.0
 */
public class Rate extends TimeTrackerBean {

    /**
     * <p>
     * The id of the company with which this Rate is associated with.
     * </p>
     * <p>
     * Initialized In: setCompanyId
     * </p>
     * <p>
     * Modified In: setCompanyId
     * </p>
     * <p>
     * Accessed In: getCompanyId
     * </p>
     *
     *
     */
    private long companyId;

    /**
     * <p>
     * Represents the description of an entity.
     * </p>
     * <p>
     * If the size is actually longer than 64, then in the <code>create</code> and <code>update</code> methods in
     * all 3 <code>DAO</code> implementations will truncate this to 64.
     * </p>
     */
    private String description = null;

    /**
     * <p>
     * The numeric value of the Rate. It may be null when the Rate object is initially constructed,.
     * </p>
     * <p>
     * Initialized In: setValue
     * </p>
     * <p>
     * Modified In: setValue
     * </p>
     * <p>
     * Accessed In: getValue
     * </p>
     *
     *
     */
    private double value;

    /**
     * <p>
     * Default constructor.
     * </p>
     *
     */
    public Rate() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the id of the company with which this Rate is associated with.
     * </p>
     *
     *
     *
     * @return the id of the company with which this Rate is associated with.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which this Rate is associated.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param companyId the id of the company with which this Rate is associated with.
     * @throws IllegalArgumentException if the companyId is <=0.
     */
    public void setCompanyId(long companyId) {
        Utils.checkPositive(companyId, "companyId");
        if (this.companyId != companyId) {
            this.companyId = companyId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the description of this data object.
     * </p>
     *
     * @return the description of this data object.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Sets the description. Size schould be limited to length of 64 but is not enforced here.
     * </p>
     *
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Retrieves the value of the rate.
     * </p>
     *
     *
     *
     * @return the value of the rate.
     */
    public double getValue() {
        return value;
    }

    /**
     * <p>
     * Sets the value of the rate.
     * </p>
     *
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param rate the  rate.
     */
    public void setValue(double value) {
        Utils.checkPositive(value, "value");
        if (value != this.value) {
            this.value = value;
            setChanged(true);
        }
    }
}
