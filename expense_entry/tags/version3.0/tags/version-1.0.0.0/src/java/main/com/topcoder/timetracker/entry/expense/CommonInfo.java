/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import java.io.Serializable;

import java.util.Date;

/**
 * <p>
 * Defines the common information of <code>ExpenseEntry</code>, <code>ExpenseEntryType</code> and
 * <code>ExpenseEntryStatus</code>. Though it has no abstract method, it is marked as abstract to avoid direct
 * instantiation. It is also serializable via object streams. The common information includes a unique ID, a description
 * of the information, the creation date and user, the last modification date and user.
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public abstract class CommonInfo implements Serializable {
    /**
     * Represents the unique ID of this type of information. If the value is -1, the ID should be generated when
     * persisted.
     */
    private int id = -1;

    /** Represents the description of this information. */
    private String description = null;

    /** Represents the creation date of this information. */
    private Date creationDate = null;

    /** Represents the creation user of this information. */
    private String creationUser = null;

    /**
     * Represents the last modification date of this information. If the information is newly persisted, the value is
     * the same as creation date.
     */
    private Date modificationDate = null;

    /**
     * Represents the last modiciation user of this information. If the information is newly persisted, the value is the
     * same as creation user.
     */
    private String modificationUser = null;

    /**
     * <p>
     * Creates a new instance of <code>CommonInfo</code> class. The unique ID is generated when persisting.
     * </p>
     */
    protected CommonInfo() {
    }

    /**
     * <p>
     * Creates a new instance of <code>CommonInfo</code> class. The unique ID of this instance is given.
     * </p>
     *
     * @param id the unique ID of this instance.
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    protected CommonInfo(int id) {
        ExpenseEntryHelper.validateId(id);

        this.id = id;
    }

    /**
     * <p>
     * Sets the unique ID of this instance.
     * </p>
     *
     * @param id the unique ID of this instance.
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
     * @throws NullPointerException if <code>description</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>description</code> is empty string.
     */
    public void setDescription(String description) {
        ExpenseEntryHelper.validateNotNullOrEmpty(description, "description");

        this.description = description;
    }

    /**
     * <p>
     * Sets the creation date of this instance. This method should be used by persistence classes. User should not use
     * it directly.
     * </p>
     *
     * @param creationDate the creation date of this instance.
     * @throws NullPointerException if <code>creationDate</code> is <code>null</code>.
     */
    public void setCreationDate(Date creationDate) {
        ExpenseEntryHelper.validateNotNull(creationDate, "creationDate");

        this.creationDate = creationDate;
    }

    /**
     * <p>
     * Sets the creation user of this instance.
     * </p>
     *
     * @param creationUser the creation user of this instance.
     * @throws NullPointerException if <code>creationUser</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>creationUser</code> is empty string.
     */
    public void setCreationUser(String creationUser) {
        ExpenseEntryHelper.validateNotNullOrEmpty(creationUser, "creationUser");

        this.creationUser = creationUser;
    }

    /**
     * <p>
     * Sets the last modification date of this instance. This method should be used by persistence classes. User should
     * not use it directly.
     * </p>
     *
     * @param modificationDate the last modification date of this instance.
     * @throws NullPointerException if <code>modificationDate</code> is <code>null</code>.
     */
    public void setModificationDate(Date modificationDate) {
        ExpenseEntryHelper.validateNotNull(modificationDate, "modificationDate");

        this.modificationDate = modificationDate;
    }

    /**
     * <p>
     * Sets the last modification user of this instance.
     * </p>
     *
     * @param modificationUser the last modification user of this instance.
     * @throws NullPointerException if <code>modificationUser</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>modificationUser</code> is empty string.
     */
    public void setModificationUser(String modificationUser) {
        ExpenseEntryHelper.validateNotNullOrEmpty(modificationUser, "modificationUser");

        this.modificationUser = modificationUser;
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

    /**
     * <p>
     * Gets the creation date of this instance.
     * </p>
     *
     * @return the creation date of this instance.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * <p>
     * Gets the creation user of this instance.
     * </p>
     *
     * @return the creation user of this instance.
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * <p>
     * Gets the last modification date of this instance.
     * </p>
     *
     * @return the last modification date of this instance.
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * <p>
     * Gets the last modification user of this instance.
     * </p>
     *
     * @return the last modification user of this instance.
     */
    public String getModificationUser() {
        return modificationUser;
    }
}






