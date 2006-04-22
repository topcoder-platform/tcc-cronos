/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense;

import java.io.Serializable;

import java.util.Date;


/**
 * <p>
 * Defines the common information of <code>ExpenseEntry</code>, <code>ExpenseEntryType</code>,
 * <code>ExpenseEntryStatus</code> and <code>ExpenseEntryRejectReason</code>. Though it has no abstract method, it is
 * marked as abstract to avoid direct instantiation. It is also serializable via object streams. The common
 * information includes the creation date and user, the last modification date and user, and a description of the
 * information.
 * </p>
 * 
 * <p>
 * Changes in 1.1: id and description have been moved to BasicInfo. That's because
 * <code>ExpenseEntryRejectReason</code> doesn't have them.
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
 * @version 1.1
 *
 * @since 1.0
 */
public abstract class CommonInfo implements Serializable {
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
     * Represents the last modiciation user of this information. If the information is newly persisted, the value is
     * the same as creation user.
     */
    private String modificationUser = null;

    /** Represents the description of this information. */
    private String description = null;

    /**
     * <p>
     * Creates a new instance of <code>CommonInfo</code> class.
     * </p>
     */
    protected CommonInfo() {
        // do nothing here
    }

    /**
     * <p>
     * Sets the creation date of this instance. This method should be used by persistence classes. User should not use
     * it directly.
     * </p>
     *
     * @param creationDate the creation date of this instance.
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
     */
    public void setModificationUser(String modificationUser) {
        ExpenseEntryHelper.validateNotNullOrEmpty(modificationUser, "modificationUser");

        this.modificationUser = modificationUser;
    }

    /**
     * <p>
     * Sets the description of this instance.
     * </p>
     *
     * @param description the description of this instance.
     */
    public void setDescription(String description) {
        ExpenseEntryHelper.validateNotNullOrEmpty(description, "description");

        this.description = description;
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
