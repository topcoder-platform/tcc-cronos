/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.messaging;

/**
 * <p>
 * This abstract class holds the id for concrete classes which extend the this abstract class. It
 * has a setter and a getter for the id field.
 * </p>
 * <p>
 * Thread safe: This class is not thread safe because it is mutable.
 * </p>
 * 
 * @author DanLazar, yqw
 * @version 2.0
 */
public abstract class BasicEntityData {
    /**
     * Represents the id. It will be initialized in the parameterized constructor. It has a setter
     * and a getter. It can be any value but <0 means that it is not set.
     */
    private long id;

    /**
     * Default constructor. Set the id to -1 meaning that it is not set.
     */
    protected BasicEntityData() {
        this.id = -1;
    }

    /**
     * Creates a new instance with the id.
     * 
     * 
     * @param id
     *            the id to set.
     * @throws IllegalArgumentException
     *             if id < 0.
     */
    protected BasicEntityData(long id) {
        if (id < 0) {
            throw new IllegalArgumentException(
                    "The id should not be negative. id=" + id);
        }

        this.id = id;
    }

    /**
     * Getter for the id of this entity data.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the id of this entity data.
     * 
     * @param id
     *            the id of this entity data.
     */
    public void setId(long id) {
        if (id < 0) {
            throw new IllegalArgumentException(
                    "The id to set should not be negative. id="+id);
        }
        this.id = id;
    }
}
