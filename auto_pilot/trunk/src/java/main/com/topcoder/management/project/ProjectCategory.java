/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This class represents a project category from the persistence. Each project category must belong
 * to a project type. Project type are stored in 'project_type_lu' table, project category in
 * 'project_category_lu'. A project category instance contains id, name and description and a
 * reference to project type. This class is used in Project class to specify the project category of
 * a project. This class implements Serializable interface to support serialization.
 * </p>
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ProjectCategory implements Serializable {

    /**
     * Represents the id of this instance. Only values greater than zero is allowed. This variable
     * is initialized in the constructor and can be accessed in the corresponding getter/setter
     * method.
     */
    private long id = 0;

    /**
     * Represents the name of this instance. Null or empty values are not allowed. This variable is
     * initialized in the constructor and can be accessed in the corresponding getter/setter method.
     */
    private String name = null;

    /**
     * Represents the description of this instance. Null value is not allowed. This variable is
     * initialized in the constructor and can be accessed in the corresponding getter/setter method.
     */
    private String description = null;

    /**
     * The project type instance associated with this instance. Null value is not allowed. This
     * variable is initialized in the constructor and can be accessed in the corresponding
     * getter/setter method.
     */
    private ProjectType projectType = null;

    /**
     * Create a new ProjectCategory instance with the given id and name. The two fields are required
     * for a this instance to be persisted.
     * <p>
     * Implementation notes:
     * </p>
     * <li>Set the corresponding member variables.</li>
     * <li>description is set to empty string.</li>
     * @param id The project category id.
     * @param name The project category name.
     * @param projectType The project type of this instance.
     * @throws IllegalArgumentException If id is less than or equals to zero, any parameter is null
     *             or name is empty string.
     */
    public ProjectCategory(long id, String name, ProjectType projectType) {
        // your code here
        setId(id);
        setName(name);
        setPropectType(projectType);
    }

    /**
     * Create a new ProjectCategory instance with the given id, name and description. The two first
     * fields are required for a this instance to be persisted.
     * <p>
     * Implementation notes:
     * </p>
     * <li>Set the corresponding member variables.</li>
     * @param id The project category id.
     * @param name The project category name.
     * @param description The project category description.
     * @param projectType The project type of this instance.
     * @throws IllegalArgumentException If id is less than or equals to zero, any parameter is null
     *             or name is empty string.
     */
    public ProjectCategory(long id, String name, String description, ProjectType projectType) {
        // your code here
        this(id, name, projectType);
        setDescription(description);
    }

    /**
     * Sets the id for this project category instance. Only possitive values are allowed.
     * <p>
     * Implementation notes:
     * </p>
     * <li>Set the corresponding member variables.</li>
     * @param id The id of this project category instance.
     * @throws IllegalArgumentException If project category id is less than or equals to zero.
     */
    public void setId(long id) {
        // your code here
        this.id = id;
    }

    /**
     * Gets the id of this project category instance.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Return the corresponding member variables.</li>
     * </ul>
     * @return the id of this project category instance.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the name for this project category instance. Null or empty values are not allowed.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Set the corresponding member variables.</li>
     * </ul>
     * @param name The name of this project category instance.
     * @throws IllegalArgumentException If project category name is null or empty string.
     */
    public void setName(String name) {
        // your code here
        this.name = name;
    }

    /**
     * Gets the name of this project category instance.
     * <p>
     * Implementation notes:
     * </p>
     * <li>Return the corresponding member variables.</li>
     * @return the name of this project category instance.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the description for this project category instance. Null value are not allowed.
     * <p>
     * Implementation notes:
     * </p>
     * <li>Set the corresponding member variables.</li>
     * @param description The description of this project category instance.
     * @throws IllegalArgumentException If project category description is null.
     */
    public void setDescription(String description) {
        // your code here
        this.description = description;
    }

    /**
     * Gets the description of this project category instance.
     * <p>
     * Implementation notes:
     * </p>
     * <li>Return the corresponding member variables.</li>
     * @return the description of this project category instance.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the project type for this project category instance. Null value is not allowed.
     * <p>
     * Implementation notes:
     * </p>
     * <li>Set the corresponding member variables.</li>
     * @param projectType The project type instance to set.
     * @throws IllegalArgumentException If input is null.
     */
    public void setPropectType(ProjectType projectType) {
        // your code here
        this.projectType = projectType;
    }

    /**
     * Gets the project type of this project category instance.
     * <p>
     * Implementation notes:
     * </p>
     * <li>Return the corresponding member variables.</li>
     * @return The project type of this project category instance.
     */
    public ProjectType ProjectType() {
        return this.projectType;
    }
}
