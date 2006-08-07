/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.project.phases;

/**
 * <p>
 * This class represents the phase type. A phase type consists of a numeric identifier and a name.
 * This class is serializable.
 * </p>
 * <p>
 * Thread Safety: This class is mutable. so it's not thread safe.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 2.0
 */
public class PhaseType implements java.io.Serializable {

    /**
     * <p>
     * Represents the phase type id. Initialized in the constructor and could be accessed via getter
     * and setter method. The value could not be negative .
     * </p>
     */
    private long id = 0;

    /**
     * <p>
     * Represents the phase type name. Initialized in the constructor and could be accessed via
     * getter and setter method. The value could not be null .
     * </p>
     */
    private String name = null;

    /**
     * The constructor with the phase type id and name.
     * @param id the phase type id
     * @param name the phase type name
     * @throws IllegalArgumentException if id is negative or name is null
     */
    public PhaseType(long id, String name) {
        // your code here
        setId(id);
        setName(name);
    }

    /**
     * Gets the phase type id.
     * @return the phase type id
     */
    public long getId() {
        // your code here
        return this.id;
    }

    /**
     * Sets the phase type id.
     * @param id the phase type id
     * @throws IllegalArgumentException if id is negative
     */
    public void setId(long id) {
        // your code here
        this.id = id;
    }

    /**
     * Gets the phase type name.
     * @return the phase type name.
     */
    public String getName() {
        // your code here
        return this.name;
    }

    /**
     * Sets the phase type name.
     * @param name the phase type name.
     * @throws IllegalArgumentException if name is null
     */
    public void setName(String name) {
        // your code here
        this.name = name;
    }
}
