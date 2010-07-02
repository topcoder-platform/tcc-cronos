/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

/**
 * <p>
 * This entity represents an Design competition.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DesignCompetition extends Competition {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 3483940144907276525L;

    /**
     * <p>
     * Represents the competition name.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Creates a <code>DesignCompetition</code>.
     * </p>
     *
     * @param name
     *            the competition name.
     */
    public DesignCompetition(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Returns the competition name.
     * </p>
     *
     * @return the competition name.
     */
    public String getName() {
        return name;
    }
}
