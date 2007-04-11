/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * This class holds the information about an expense entry type.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class ExpenseType extends TimeTrackerBean {

    /**
     * the active flag of this expense entry type.
     */
    private boolean active;

    /**
     * the company id this expense entry type associated with.
     */
    private int companyId;

    /**
     * <p>
     * This variable represents the description of the status. It is set/initialized in the
     * dedictaed setter and can also be accessed through a dedicated getter.
     * </p>
     */
    private String description;

    /**
     * Create a new instance. Empty constructor.
     */
    public ExpenseType() {
        // empty
    }

    /**
     * Create a new instance. Simply initialize the id field.
     *
     * @param id this value will be assigned to the id field
     */
    public ExpenseType(int id) {
        this.setId(id);
    }

    /**
     * gets the active flag.
     *
     * @return the active flag of expense entry type
     */
    public boolean getActive() {
        return this.active;
    }

    /**
     * set active to new value.
     *
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * gets the company id.
     *
     * @return the company id
     */
    public int getCompanyId() {
        return this.companyId;
    }

    /**
     * set company id to new value.
     *
     * @param companyId the company id to set
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    /**
     * <p>
     * Thi is a getter for the description variable currently set in this instance. This could
     * return any value including null.
     * </p>
     *
     * @return currently set description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Setter for the description field. Can be any value, including null.
     * </p>
     *
     * @param description description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
