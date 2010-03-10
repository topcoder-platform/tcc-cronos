/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax;


/**
 * <p>
 * A java been class used to test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Project {
    /**
     * <p>
     * The name of the project.
     * </p>
     */
    private String name;
    /**
     * <p>
     * The description of the project.
     * </p>
     */
    private String description;

    /**
     * <p>
     * The id of the project.
     * </p>
     */
    private int id;

    /**
     * <p>
     * The default constructor.
     * </p>
     */
    public Project() {

    }

    /**
     * <p>
     * Set the id.
     * </p>
     *
     * @param id
     *            to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * <p>
     * get the id.
     * </p>
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * <p>
     * Set the name.
     * </p>
     *
     * @param name
     *            to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Get the Id.
     * </p>
     *
     * @return the id
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Set the description.
     * </p>
     *
     * @param description
     *            to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Get the description.
     * </p>
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
