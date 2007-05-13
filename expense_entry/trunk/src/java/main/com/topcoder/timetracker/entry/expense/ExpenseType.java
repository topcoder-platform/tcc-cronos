/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.common.TimeTrackerBean;

/**
 * <p>
 * Defines the information contained by an expense entry type.
 * </p>
 *
 * <p>
 * When creating an instance of this class the user has two options: 1) Use the default constructor and allow the GUID
 * Generator component to generate a unique id 2) Use the parameterized constructor and provide an id for the
 * <code>ExpenseType</code> instance; if the id already is contained by another type from the ExpenseTypes table, then
 * the newly created type will not be added to the ExpenseTypes table. Also the user should not populate the
 * creationDate and modificationDate fields, because if he does, the type will not be added to the database. These
 * fields will be handled automatically by the component (the current date will be used). When loading from the
 * persistence, all the fields will be properly populated.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is not thread safe as it is mutable.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseType extends TimeTrackerBean {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = 5273202174900863941L;

	/**
	 * Represents the active status of this expense type. Default is false.
	 */
    private boolean active = false;

    /**
     * Represetns the company id this expense entry type associated to. Default is -1. Can not be -1 once set.
     */
    private long companyId = -1;

    /**
     * Represetns the description of this expense entry type. Default is null. Can be null.
     */
    private String description;

    /**
     * <p>
     * Creates a new instance of <code>ExpenseType</code> class. The unique ID is generated when persisting.
     * </p>
     */
    public ExpenseType() {
    }

    /**
     * <p>
     * Creates a new instance of <code>ExpenseType</code> class. The unique ID is given.
     * </p>
     *
     * @param id the unique ID of this expense entry type.
     *
     * @throws IllegalArgumentException if <code>id</code> is not positive.
     */
    public ExpenseType(long id) {
        ExpenseEntryHelper.validatePositive(id, "id");
        super.setId(id);
    }

    /**
     * <p>
     * Gets the active flag.
     * </p>
     *
     * @return the active flag of expense entry type.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * <p>
     * Sets active flag to new value.
     * </p>
     *
     * @param active the active to set.
     */
    public void setActive(boolean active) {
        if (this.active != active) {
            this.setChanged(true);
        }

        this.active = active;
    }

    /**
     * <p>
     * Gets the company id this expense entry associated to.
     * </p>
     *
     * @return the company id.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the company id.
     * </p>
     *
     * @param companyId the company id to set.
     *
     * @throws IllegalArgumentException if <code>id</code> is not positive.
     */
    public void setCompanyId(long companyId) {
        ExpenseEntryHelper.validatePositive(companyId, "companyId");

        if (this.companyId != companyId) {
            this.setChanged(true);
        }

        this.companyId = companyId;
    }

    /**
     * <p>
     * Gets the description of this expense entry type. This could return any value including null.
     * </p>
     *
     * @return the description of this expense entry type.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Sets the description of this expense entry type. Can be any value, including null.
     * </p>
     *
     * @param description description to be set.
     */
    public void setDescription(String description) {
        if (this.description == null) {
            if (description != null) {
                this.setChanged(true);
            }
        } else if (!this.description.equals(description)) {
            this.setChanged(true);
        }

        this.description = description;
    }
}
