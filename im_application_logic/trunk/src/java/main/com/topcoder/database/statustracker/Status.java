/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.database.statustracker;

import java.util.Date;

/**
 * <p>
 * This class represents the status of an entity instance in the database.
 * </p>
 *
 * <p>
 * A status can be Shared by many entity instances, but that relationship is managed by the EntityStatus class.
 * </p>
 *
 * <p>
 * The corresponding table defined for this class is <b>status</b>.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is not thread-safe. This was done because it is anticipated that status
 * objects will be <i>written</i> only once or twice in their lifetimes, and almost never modified, but
 * <i>read</i> many many times. To improve performance, therefore, this class was not made thread-safe.
 * </p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 */
public class Status {
    /**
     * <p>
     * This variable represents the unique id of this Status object.
     * </p>
     *
     * <p>
     * It is set in the constructor only and will never be negative.
     * </p>
     */
    private final long id;

    /**
     * <p>
     * This variable represents the name of this status.
     * </p>
     *
     * <p>
     * It is set by setName and accessed by getName.
     * May be null if not set; if set, will never be empty.
     * </p>
     */
    private String name;

    /**
     * <p>
     * This variable represents the short description of this status.
     * </p>
     *
     * <p>
     * It is set by setShortDesc and accessed by getShortDesc.
     * May be null if not set. If set, will never be empty.
     * </p>
     */
    private String shortDesc;

    /**
     * <p>
     * This variable represents the long description of this status.
     * </p>
     *
     * <p>
     * It is set by setLongDesc and accessed by getLongDesc.
     * May be null if not set. If set, will never be empty.
     * </p>
     */
    private String longDesc;

    /**
     * <p>
     * This variable represents the date this status was first saved to persistent storage.
     * </p>
     *
     * <p>
     * It is set by the constructor or setCreationDate and accessed by getCreationDate.
     * Will never be null.
     * </p>
     */
    private Date creationDate;

    /**
     * <p>
     * This variable represents the date and time this record was last written to persistent storage.
     * </p>
     *
     * <P>
     * It is set by the constructor or setUpdateDate and accessed by getUpdateDate().
     * Will never be null.
     * </p>
     */
    private Date updateDate;

    /**
     * <p>
     * This variable represents the username who last modified (or created) this Status type.
     * </p>
     *
     * <p>
     * Accessed by getLastUpdatedByJserName) and set by setLastUpdatedByUserName(String).
     * May be null if not set yet, If set, will never be empty.
     * </p>
     */
    private String lastUpdatedByUserName;

    /**
     * <p>
     * Instantiate this Status with the given ID. All of the other attributes are left null,
     * except for creationDate and updateDate, which default to now.
     * </p>
     *
     * @param id the unique ID of this Status
     *
     * @throws IllegalArgumentException if id is negative.
     */
    public Status(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("The id parameter is negative.");
        }

        this.id = id;
        creationDate = new Date();
        updateDate = new Date();
    }

    /**
     * <p>
     * Return the date this record was first inserted into persistent storage
     * </p>
     *
     * <p>
     * The default value is the creation date of this status instance, so null will never be returned.
     * </p>
     *
     * @return Returns the creation Date.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * <p>
     * Set the creation date of this record.
     * </p>
     *
     * @param creationDate The date this record was first saved into persistent storage.
     *
     * @throws IllegalArgumentException if creationDate is null.
     */
    public void setCreationDate(Date creationDate) {
        Util.checkNull(creationDate, "creationDate");
        this.creationDate = creationDate;
    }

    /**
     * <p>
     * Returns the username of the user who last updated this record, or null if not set.
     * </p>
     *
     * <p>
     * Note, empty string (trimmed) will never be returned.
     * </p>
     *
     * @return Returns the last updated username
     */
    public String getLastUpdatedByUserName() {
        return lastUpdatedByUserName;
    }

    /**
     * <P>
     * Set the username of the user who last updated this record in persistent storage.
     * </p>
     *
     * @param userName The last updated username
     *
     * @throws IllegalArgumentException if userName is null or empty after trim.
     */
    public void setLastUpdatedByUserName(String userName) {
        Util.checkString(userName, "userName");
        this.lastUpdatedByUserName = userName;
    }

    /**
     * <p>
     * Returns the long description. May be null.
     * </p>
     *
     * @return Returns the long description.
     */
    public String getLongDesc() {
        return longDesc;
    }

    /**
     * <p>
     * Set the long description of this Status.
     * </p>
     *
     * @param longDesc The long description of this status
     *
     * @throws IllegalArgumentException if longDesc is null or empty after trim.
     */
    public void setLongDesc(String longDesc) {
        Util.checkString(longDesc, "longDesc");
        this.longDesc = longDesc;
    }

    /**
     * <p>
     * Returns the name of this status, or null if not set.
     * </p>
     *
     * @return Returns the name of this status as set by setName
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Set the name of this status.
     * </p>
     *
     * @param name The name to set.
     *
     * @throws IllegalArgumentException if name is null or empty after trim.
     */
    public void setName(String name) {
        Util.checkString(name, "name");
        this.name = name;
    }

    /**
     * <p>
     * Returns the short description. May be null.
     * </p>
     *
     * @return Returns the short description.
     */
    public String getShortDesc() {
        return shortDesc;
    }

    /**
     * <p>
     * Set the short description of this Status.
     * </p>
     *
     * @param shortDesc The short description of this status
     *
     * @throws IllegalArgumentException if shortDesc is null or empty after trim.
     */
    public void setShortDesc(String shortDesc) {
        Util.checkString(shortDesc, "shortDesc");
        this.shortDesc = shortDesc;
    }

    /**
     * <p>
     * Return the date this record was last updated in persistent storage.
     * </p>
     *
     * <p>
     * The default value is the creation date of this status instance, so null will never be returned.
     * </p>
     *
     * @return Returns the last updated Date.
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>
     * Set the update date of this record.
     * </p>
     *
     * @param updateDate The date this record was last updated in persistent storage.
     *
     * @throws IllegalArgumentException if updateDate is null, or if it is before the creationDate.
     */
    public void setUpdateDate(Date updateDate) {
        Util.checkNull(updateDate, "updateDate");
        // updateDate should after creationDate
        if (updateDate.before(creationDate)) {
            throw new IllegalArgumentException("The update date is before create date, which is invalid.");
        }
        this.updateDate = updateDate;
    }

    /**
     * <p>
     * Return the status id, it will never be negative.
     * </p>
     *
     * @return the status id. Will never be negative.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * This method implements the equals method, to be able to compare Status objects for
     * uniqueness by their ids.
     * </p>
     *
     * @param obj the other object to compare for equality
     * @return true if 'obj' is an Status with the same id as this Status, false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        // compare the class name instead of using the instanceof operator
        if (obj.getClass().getName().equals(this.getClass().getName())) {
            return ((Status) obj).id == id;
        }

        return false;
    }

    /**
     * <p>
     * This method implements the hashCode method, to allow this class to be easily used in hash
     * tables, etc.
     * </p>
     *
     * @return the id of this Status as its hash code.
     */
    public int hashCode() {
        return (int) id;
    }
}
