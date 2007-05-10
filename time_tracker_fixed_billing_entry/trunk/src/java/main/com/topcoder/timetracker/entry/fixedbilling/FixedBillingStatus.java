/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This is a bean that represents a FixedBillingEntryStatus, which represents a possible state that a
 * <code>FixedBillingEntry</code> can have in the context of the Time Tracker system.
 * </p>
 *
 * <p>
 * Thread Safety: This class is not thread safe; Each thread is expected to work on it's own instance, or this class
 * should be used in a read-only manner for concurrent access.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class FixedBillingStatus extends TimeTrackerBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
	private static final long serialVersionUID = 2617426129556930894L;

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
    public FixedBillingStatus() {
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
     * @param description a short String that describes the status.
     *
     * @throws IllegalArgumentException if description is a null or empty String.
     */
    public void setDescription(String description) {
        Helper.checkNullOrEmpty("description", description);

        if ((this.description == null) || !this.description.equals(description)) {
            setChanged(true);
        }

        this.description = description;
    }
}
