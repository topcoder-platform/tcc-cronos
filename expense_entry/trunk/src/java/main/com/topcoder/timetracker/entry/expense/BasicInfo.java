/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

/**
 * <p>
 * Defines the common information of <code>ExpenseEntry</code>, <code>ExpenseEntryType</code> and
 * <code>ExpenseEntryStatus</code>. Though it has no abstract method, it is marked as abstract to avoid direct
 * instantiation. It is also serializable via object streams. The common information includes a unique ID.
 * </p>
 *
 * <p>
 * Bug fixed on 2006-4-22, modified by Xuchen. Bug Fix for TT-1976: "It is not possible to directly retrieve the
 * textual description of rejected reason from the  ExpenseEntryRejectReason object. This object is derived from the
 * CommonInfo class, instead of BasicInfo one, so it  has no getDescription/setDescription method."  Solution: Move
 * 'description' and its setter/getter methods from BasicInfo class to CommonInfo class.
 * </p>
 *
 * @author adic, TCSDEVELOPER
 * @author DanLazar, visualage
 * @author Xuchen
 * @version 2.0
 *
 * @since 1.1
 */
public abstract class BasicInfo extends CommonInfo {
    /**
     * Represents the unique ID of this type of information. If the value is -1, the ID should be generated when
     * persisted.
     */
    private int id = -1;

    /**
     * <p>
     * Creates a new instance of <code>BasicInfo</code> class. The unique ID is generated when persisting.
     * </p>
     */
    protected BasicInfo() {
    }

    /**
     * <p>
     * Creates a new instance of <code>BasicInfo</code> class. The unique ID of this instance is given.
     * </p>
     *
     * @param id the unique ID of this instance.
     */
    protected BasicInfo(int id) {
        ExpenseEntryHelper.validateId(id);

        this.id = id;
    }

    /**
     * <p>
     * Sets the unique ID of this instance.
     * </p>
     *
     * @param id the unique ID of this instance.
     */
    public void setId(int id) {
        ExpenseEntryHelper.validateId(id);

        this.id = id;
    }

    /**
     * <p>
     * Gets the unique ID of this instance.
     * </p>
     *
     * @return the unique ID of this instance.
     */
    public int getId() {
        return id;
    }
}
