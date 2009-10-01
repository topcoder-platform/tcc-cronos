/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Simple DTO class for capacity retrieval
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author pulky
 * @version 1.0 (Cockpit Pipeline Release Assembly 2 - Capacity)
 * @since 1.0
 */
public class CapacityData implements Serializable {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 600295947647235401L;

    /**
     * Represents the date
     */
    private Date date;

    /**
     * Represents the number of scheduled contests
     */
    private int numScheduledContests;

    /**
     * Default constructor.
     */
    public CapacityData() {
    }

    /**
     * Constructor using fields
     *
     * @param date the date to set
     * @param numScheduledContests the number of scheduled contests to set
     */
    public CapacityData(Date date, int numScheduledContests) {
        super();
        this.date = date;
        this.numScheduledContests = numScheduledContests;
    }

    /**
     * Returns the date.
     *
     * @return the date.
     */
    public Date getDate() {
        return date;
    }

     /**
     * Updates the date with the specified value.
     *
     * @param date the date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the number of scheduled contests.
     *
     * @return the number of scheduled contests.
     */
    public int getNumScheduledContests() {
        return numScheduledContests;
    }

     /**
     * Updates the number of scheduled contests with the specified value.
     *
     * @param numScheduledContests the number of scheduled contests to set.
     */
    public void setNumScheduledContests(int numScheduledContests) {
        this.numScheduledContests = numScheduledContests;
    }
}
