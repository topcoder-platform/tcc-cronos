/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This bean represents the set value of the mileage rate used to compute auto transportation expenses
 * Each company has a different mileage rate.
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
public class MileageRate extends TimeTrackerBean {

    /**
     * <p>
     * The id of the company with which this MileageRate is associated with.
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
     * The numeric value of the MileageRate. It may be null when the MileageRateob ject is initially constructed,.
     * </p>
     * <p>
     * Initialized In: setRate
     * </p>
     * <p>
     * Modified In: setRate
     * </p>
     * <p>
     * Accessed In: getRate
     * </p>
     *
     *
     */
    private double rate;

    /**
     * <p>
     * Default constructor.
     * </p>
     *
     */
    public MileageRate() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the id of the company with which this MileageRate is associated with.
     * </p>
     *
     *
     *
     * @return the id of the company with which this MileageRate is associated with.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which this MileageRate is associated with.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param companyId the id of the company with which this MileageRate is associated with.
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
     * Retrieves the mileage rate.
     * </p>
     *
     *
     *
     * @return the mileage rate.
     */
    public double getRate() {
        return rate;
    }

    /**
     * <p>
     * Sets the mileage rate.
     * </p>
     *
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param rate the mileage rate.
     */
    public void setRate(double rate) {
        Utils.checkPositive(rate, "rate");
        if (rate != this.rate) {
            this.rate = rate;
            setChanged(true);
        }
    }
}
