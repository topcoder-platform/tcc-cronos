/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.timetracker.common.TimeTrackerBean;


/**
 * <p>
 * Defines the information contained by an expense entry status.
 * </p>
 *
 * <p>
 * When creating an instance of this class the user has two options: 1) Use the default constructor and allow the GUID
 * Generator component to generate a unique id 2) Use the parameterized constructor and provide an id for the
 * <code>ExpenseStatus</code> instance; if the id already is contained by another status from the expense_status
 * table, then the newly created status will not be added to the expense_status table. Also the user should not
 * populate the creationDate and modificationDate fields, because if he does, the status will not be added to the
 * database. These fields will be handled automatically by the component (the current date will be used). When loading
 * from the persistence, all the fields will be properly populated.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is not thread safe as it is mutable.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseStatus extends TimeTrackerBean {
    /** Represetns the description of this expense entry status. Default is null. Can be null. */
    private String description;

    /**
     * <p>
     * Creates a new instance of <code>ExpenseStatus</code> class. The unique ID is generated when persisting.
     * </p>
     */
    public ExpenseStatus() {
    }

    /**
     * <p>
     * Creates a new instance of <code>ExpenseStatus</code> class. The unique ID is given.
     * </p>
     *
     * @param id the unique ID of this expense entry status.
     *
     * @throws IllegalArgumentException if <code>id</code> is not positive.
     */
    public ExpenseStatus(long id) {
        ExpenseEntryHelper.validatePositive(id, "id");
        super.setId(id);
    }

    /**
     * <p>
     * Get the description of this expense entry status. This could return any value including null.
     * </p>
     *
     * @return the description of this expense entry status.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Set the description of this expense entry status. Can be any value, including null.
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
