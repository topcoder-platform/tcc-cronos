/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.entry.time;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This class holds the information of a time entry status. No parameter checking is provided in
 * this class, since the class acts as a mock class for Time Tracker Report component. All the
 * information is reading from the database, so it is assumed to be valid.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class TimeStatus extends TimeTrackerBean {

    /**
     * <p>
     * This is a short String that describes the status.
     * </p>
     */
    private String description;

    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public TimeStatus() {
        // empty
    }

    /**
     * <p>
     * Gets a short String that describes the status.
     * </p>
     *
     * @return a short String that describes the status.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Sets a short String that describes the status.
     * </p>
     *
     * @param description a short String that describes the status.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
