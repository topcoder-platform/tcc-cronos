/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.client;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This class holds the information of a client. No parameter checking is provided in this class,
 * since the class acts as a mock class for Time Tracker Report component. All the information is
 * reading from the database, so it is assumed to be valid.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class Client extends TimeTrackerBean {

    /**
     * <p>
     * Represents the name of this Client.
     * </p>
     */
    private String name = null;

    /**
     * <p>
     * Represents the the id of the company.
     * </p>
     */
    private long companyId = 0;

    /**
     * <p>
     * Constructs the Client.
     * </p>
     */
    public Client() {
        // empty
    }

    /**
     * <p>
     * Gets the Company ID.
     * </p>
     *
     * @return the company id of this client.
     */
    public long getCompanyId() {
        return this.companyId;
    }

    /**
     * <p>
     * Sets the company id.
     * </p>
     *
     * @param companyId the company id of this client.
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * <p>
     * Get the name.
     * </p>
     *
     * @return the name of the client.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Set the name of the client.
     * </p>
     *
     * @param name a name of client.
     */
    public void setName(String name) {
        this.name = name;
    }
}
