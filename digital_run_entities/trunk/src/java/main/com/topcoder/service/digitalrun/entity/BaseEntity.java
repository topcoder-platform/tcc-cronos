/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The <code>BaseEntity</code> entity. It holds the command attributes id, creation date, modification date, and
 * description for all the entities in this component.
 * </p>
 *
 * <p>
 * Thread Safety: It's mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseEntity implements Serializable {

    /**
     * Represents the id attribute of the BaseEntity entity. It should be positive after set.
     */
    private long id;

    /**
     * Represents the creation date attribute of the BaseEntity entity. It should be non-null after set.
     */
    private Date creationDate;

    /**
     * Represents the modification date attribute of the BaseEntity entity. It should be non-null after set.
     */
    private Date modificationDate;

    /**
     * Represents the description attribute of the BaseEntity entity. It should be non-null and non-empty after
     * set.
     */
    private String description;

    /**
     * Creates the instance. Empty constructor.
     */
    protected BaseEntity() {
        // empty
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id
     *
     * @throws IllegalArgumentException
     *             if the long argument is not positive.
     */
    public void setId(long id) {
        Helper.checkPositive(id, "id");
        this.id = id;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Set creation date.
     *
     * @param creationDate
     *            the creation date
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setCreationDate(Date creationDate) {
        Helper.checkNull(creationDate, "creationDate");
        this.creationDate = creationDate;
    }

    /**
     * Gets modification date.
     *
     * @return the modification date
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets modification date.
     *
     * @param modificationDate
     *            the modification date
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    public void setModificationDate(Date modificationDate) {
        Helper.checkNull(modificationDate, "modificationDate");
        this.modificationDate = modificationDate;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description
     *            the description
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code> or empty
     */
    public void setDescription(String description) {
        Helper.checkString(description, "description");
        this.description = description;
    }

    /**
     * Return <code>true</code> if the obj is not null, is the same type as this instance, and both of them have
     * id set(id>0), and the ids are equal; otherwise <code>false</code>.
     *
     * @param obj
     *            the object to check
     * @return true if the obj is not null, the same type as this instance, and both of them have id set(id>0), and
     *         the ids are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) obj;
            return id > 0 && id == entity.id;
        }
        return false;
    }

    /**
     * Returns the hash code. If id is set it will be used for calculating the hash code.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        if (id > 0) {
            return new Long(id).hashCode();
        } else {
            return super.hashCode();
        }
    }
}
