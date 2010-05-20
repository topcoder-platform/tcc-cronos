/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>contest_property_lu</i>.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author tushak, cyberjag
 * @version 1.0
 */
public class ContestProperty implements Serializable {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 4361443144602502923L;

    /**
     * Represents the entity id.
     */
    private long propertyId;

    /**
     * Represents the description.
     */
    private String description;

    /**
     * Represents the name.
     */
    private String name;

    /**
     * Default constructor.
     */
    public ContestProperty() {
        // empty
    }

    /**
     * Returns the propertyId.
     *
     * @return the propertyId.
     */
    public long getPropertyId() {
        return propertyId;
    }

    /**
     * Updates the propertyId with the specified value.
     *
     * @param propertyId
     *            the propertyId to set.
     */
    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * Returns the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description with the specified value.
     *
     * @param description
     *            the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name with the specified value.
     *
     * @param name
     *            the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     *
     * @param obj
     *            the {@code Object} to compare to this one
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContestProperty) {
            return getPropertyId() == ((ContestProperty) obj).getPropertyId();
        }
        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's
     * {@link #equals(Object)}} method.
     *
     * @return a hash code for this {@code ContestProperty}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(propertyId);
    }
}
