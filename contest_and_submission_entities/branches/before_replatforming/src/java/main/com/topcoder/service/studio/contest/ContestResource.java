/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;

/**
 * <p>
 * Represents the entity class for db table <i>resource</i>.
 * </p>
 * <p>
 * It holds the attributes resource id, and name.
 * </p>
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 * @since 1.2
 */
public class ContestResource implements Serializable {
    /**
     * <p>
     * Generated serial version id.
     * </p>
     */
    private static final long serialVersionUID = 2503160622673566702L;

    /**
     * <p>
     * Represents the resource id.
     * </p>
     */
    private Long resourceId;

    /**
     * <p>
     * Represents the name.
     * </p>
     */
    private String name;

    /**
     * Empty constructor.
     */
    public ContestResource() {
    }

    /**
     * <p>
     * Returns the resource id.
     * </p>
     *
     * @return the resource id.
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * <p>
     * Updates the resource id.
     * </p>
     *
     * @param resourceId
     *            the resource id
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * <p>
     * Returns the name.
     * </p>
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Updates the name.
     * </p>
     *
     * @param name
     *            the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Compares this object with the passed object for equality. Only the entity id will be compared.
     * </p>
     *
     * @param obj
     *            the obj to compare to this one
     * @return true if this object is equal to the other, false otherwise
     */
    @Override
    public synchronized boolean equals(Object obj) {
        if (obj instanceof ContestResource) {
            return getResourceId() == ((ContestResource) obj).getResourceId();
        }
        return false;
    }

    /**
     * <p>
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's {@link #equals(Object)}}
     * method.
     * </p>
     *
     * @return a hash code for this entity
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(resourceId);
    }
}
