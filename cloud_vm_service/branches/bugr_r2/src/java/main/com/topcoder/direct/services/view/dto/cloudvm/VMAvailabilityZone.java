/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

/**
 * This class represents the VM availability zone.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class VMAvailabilityZone extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 9438501481250834L;

    /**
     * Represents the availability zone name. It has getter & setter. It can be any value.
     */
    private String name;

    /**
     * Empty constructor.
     */
    public VMAvailabilityZone() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param name value to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

