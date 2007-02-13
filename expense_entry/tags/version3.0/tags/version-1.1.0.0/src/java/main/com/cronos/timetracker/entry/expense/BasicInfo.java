/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

/**
 * <p>
 * Defines the common information of <code>ExpenseEntry</code>, <code>ExpenseEntryType</code> and
 * <code>ExpenseEntryStatus</code>. Though it has no abstract method, it is marked as abstract to avoid direct
 * instantiation. It is also serializable via object streams. The common information includes a unique ID, a
 * description of the information.
 * </p>
 *
 * @author adic, TCSDEVELOPER
 * @author DanLazar, visualage
 * @version 1.1
 *
 * @since 1.1
 */
public abstract class BasicInfo extends CommonInfo {
    /**
     * Represents the unique ID of this type of information. If the value is -1, the ID should be generated when
     * persisted.
     */
    private int id = -1;

    /** Represents the description of this information. */
    private String description = null;

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
     *
     * @throws IllegalArgumentException if <code>id</code> is -1.
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
     *
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    public void setId(int id) {
        ExpenseEntryHelper.validateId(id);

        this.id = id;
    }

    /**
     * <p>
     * Sets the description of this instance.
     * </p>
     *
     * @param description the description of this instance.
     *
     * @throws NullPointerException if <code>description</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>description</code> is empty string.
     */
    public void setDescription(String description) {
        ExpenseEntryHelper.validateNotNullOrEmpty(description, "description");

        this.description = description;
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

    /**
     * <p>
     * Gets the description of this instance.
     * </p>
     *
     * @return the description of this instance.
     */
    public String getDescription() {
        return description;
    }
}
