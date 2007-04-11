/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This class holds the information about an expense entry status.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class ExpenseStatus extends TimeTrackerBean {

    /**
     * <p>
     * This variable represents the description of the status. It is set/initialized in the
     * dedictaed setter and can also be accessed through a dedicated getter.
     * </p>
     */
    private String description;

    /**
     * Empty constructor.
     */
    public ExpenseStatus() {
        // empty
    }

    /**
     * Create a new instance. Simply initialize the id field.
     *
     * @param id this value will be assigned to the id field
     */
    public ExpenseStatus(int id) {
        this.setId(id);
    }

    /**
     * <p>
     * Thi si a getter for the description variable currently set in this instance.This could return
     * any value including null.
     * </p>
     *
     * @return the current description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Setter for the description field. Can be any value, including null.
     * </p>
     *
     * @param description description of the type to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
