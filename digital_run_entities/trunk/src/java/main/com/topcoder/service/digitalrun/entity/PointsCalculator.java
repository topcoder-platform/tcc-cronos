/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

/**
 * <p>
 * The <code>PointsCalculator</code> entity. It holds the attribute class name.
 * </p>
 *
 * <p>
 * Thread Safety: It's mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class PointsCalculator extends BaseEntity {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 8623404574995699395L;

    /**
     * Represents the class name attribute of the PointsCalculator entity. It should be non-null and non-empty
     * after set.
     */
    private String className;

    /**
     * Creates the instance. Empty constructor.
     */
    public PointsCalculator() {
        // empty
    }

    /**
     * Gets class name.
     *
     * @return the class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets class name.
     *
     * @param className
     *            the class name
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code> or empty
     */
    public void setClassName(String className) {
        Helper.checkString(className, "className");
        this.className = className;
    }
}
