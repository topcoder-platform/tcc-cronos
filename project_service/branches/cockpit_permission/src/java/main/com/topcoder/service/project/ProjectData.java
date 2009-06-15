/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.Serializable;

/**
 * <p>
 * This data object serves to communicate between project data between the bean and its client. It also serves as a base
 * class for the Project class.
 * </p>
 * <p>
 * We simply define three properties and getter/setters for these properties. Note that this is a dumb DTO - no
 * validation is done.
 * </p>
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe as it has mutable state.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
public class ProjectData implements Serializable {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 9053041638081689882L;

    /**
     * <p>
     * Represents the ID of this project.
     * </p>
     * <p>
     * It uses <code>Long</code> type instead of <code>long</code> type to allow for null values to be set before
     * entity creation in persistence. This variable is mutable and is retrieved by the {@link #getProjectId()} method
     * and set by the {@link #setProjectId(Long)} method. It is initialized to null, and may be set to ANY value.
     * </p>
     */
    private Long projectId;

    /**
     * <p>
     * Represents the name of this project.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the {@link #getName()} method and set by the
     * {@link #setName(String)} method. It is initialized to null, and may be set to ANY value, including null/empty
     * string.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Represents the description of this project.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the getDescription method and set by the setDescription method. It
     * is initialized to null, and may be set to ANY value, including null/empty string.
     * </p>
     */
    private String description;

    /**
     * <p>
     * Constructs a <code>ProjectData</code> instance.
     * </p>
     */
    public ProjectData() {
    }

    /**
     * <p>
     * Gets the ID of the project.
     * </p>
     *
     * @return The ID of the project.
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets the ID of this project.
     * </p>
     *
     * @param projectId
     *            The desired ID of this project. ANY value.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the name of the project.
     * </p>
     *
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name of this project.
     * </p>
     *
     * @param name
     *            The desired name of this project. ANY value.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Gets the description of the project.
     * </p>
     *
     * @return The description of the project.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the description of this project.
     * </p>
     *
     * @param description
     *            The desired description of this project. ANY value.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
