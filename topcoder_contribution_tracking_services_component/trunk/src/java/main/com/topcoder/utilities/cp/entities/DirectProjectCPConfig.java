/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.entities;

import java.io.Serializable;

/**
 * <p>
 * This class is a container for information about the contribution points associated with the given TopCoder Direct
 * Project. It is a simple JavaBean (POJO) that provides getters and setters for all private attributes and performs
 * no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author vangavroche, sparemax
 * @version 1.0
 */
public class DirectProjectCPConfig implements Serializable {
    /**
     * The serial version ID.
     */
    private static final long serialVersionUID = 7186717720894440431L;

    /**
     * The ID of the direct project. Can be any value. Has getter and setter.
     */
    private long directProjectId;

    /**
     * The use contribution points flag. Can be any value. Has getter and setter.
     */
    private boolean useCP;

    /**
     * The available immediate budget. Can be any value. Has getter and setter.
     */
    private double availableImmediateBudget;

    /**
     * Creates an instance of DirectProjectCPConfig.
     */
    public DirectProjectCPConfig() {
        // Empty
    }

    /**
     * @return the directProjectId
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * @param directProjectId
     *            the directProjectId to set
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Gets the use contribution points flag.
     *
     * @return the use contribution points flag.
     */
    public boolean isUseCP() {
        return useCP;
    }

    /**
     * Sets the use contribution points flag.
     *
     * @param useCP
     *            the use contribution points flag.
     */
    public void setUseCP(boolean useCP) {
        this.useCP = useCP;
    }

    /**
     * Gets the available immediate budget.
     *
     * @return the available immediate budget.
     */
    public double getAvailableImmediateBudget() {
        return availableImmediateBudget;
    }

    /**
     * Sets the available immediate budget.
     *
     * @param availableImmediateBudget
     *            the available immediate budget.
     */
    public void setAvailableImmediateBudget(double availableImmediateBudget) {
        this.availableImmediateBudget = availableImmediateBudget;
    }

    /**
     * Converts the object to a string.
     *
     * @return the string.
     */
    @Override
    public String toString() {
        return new StringBuilder().append(DirectProjectCPConfig.class.getName()).append("{")
            .append("directProjectId").append(":").append(directProjectId).append(", ")
            .append("useCP").append(":").append(useCP).append(", ")
            .append("availableImmediateBudget").append(":").append(availableImmediateBudget).append("}").toString();
    }
}
