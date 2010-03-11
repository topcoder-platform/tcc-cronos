/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

/**
 * <p>
 * The Department entity used in the unit tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Department {

    /**
     * The department name. Accessed and modified using the namesake getter/setter.
     */
    private String name;

    /**
     * Getter for the department name.
     *
     * @return the department name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the department name.
     *
     * @param name the department name
     */
    public void setName(String name) {
        this.name = name;
    }
}
