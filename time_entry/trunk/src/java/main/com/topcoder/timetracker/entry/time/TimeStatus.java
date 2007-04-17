/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.entry.time.db.Util;

/**
 * <p>
 * This is a bean that represents a <code>TimeStatus</code>, which represents a possible
 * state that a TimeEntry can have in the context of the Time Tracker system.
 * </p>
 *
 * <p>
 * Thread Safety: This class is not thread safe; Each thread is expected to work on it's own instance,
 * or this class should be used in a read-only manner for concurrent access.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class TimeStatus extends TimeTrackerBean {
    /**
     * <p>
     * This is a short String that describes the status.
     * </p>
     *
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     *
     * <p>
     * It can not be null or empty string after it is set.
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
        return description;
    }

    /**
     * <p>
     * Sets a short String that describes the status.
     * </p>
     *
     * <p>
     * Note, if the new value is not equal to the old value, then the changed flag will set to
     * <code>true</code>.
     * </p>
     *
     * @param description a short String that describes the status.
     *
     * @throws IllegalArgumentException if description is a null or empty String.
     */
    public void setDescription(String description) {
        Util.checkString(description, "description");

        if (!description.equals(this.description)) {
            this.description = description;
            setChanged(true);
        }
    }
}