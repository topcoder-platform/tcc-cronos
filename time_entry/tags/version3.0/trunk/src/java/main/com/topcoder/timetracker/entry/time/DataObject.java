/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

/**
 * <p>
 * Base data object component. This class is concrete and provides the common primaryId member with accessor methods.
 * All entities will extend this class and define additional members as needed.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public abstract class DataObject {
    /**
     * <p>
     * Represents the primary Id of this entity. Initial values is 0.
     * </p>
     */
    private int primaryId = 0;

    /**
     * <p>
     * Gets the primaryId.
     * </p>
     *
     * @return The primary Id
     */
    public int getPrimaryId() {
        return this.primaryId;
    }

    /**
     * <p>
     * Sets the primaryId. No restrictions are placed on the value.
     * </p>
     *
     * @param id The primary Id
     */
    public void setPrimaryId(int id) {
        this.primaryId = id;
    }
}
