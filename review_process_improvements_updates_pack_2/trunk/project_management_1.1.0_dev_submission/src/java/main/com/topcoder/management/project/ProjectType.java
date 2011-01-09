/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This class represents a project type from the persistence. Each project category must belong to a project type.
 * Project type are stored in 'project_type_lu' table, project category in 'project_category_lu'. A project type
 * instance contains id, name and description. This class is used in ProjectCategory class to specify the project type
 * of the project category. This class implements Serializable interface to support serialization.
 * </p>
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * <p>
 * Version 1.1 (End Of Project Analysis Release Assembly v1.0)
 * <ul>
 * <li>Expanded the class with new boolean 'generic' property. Added new constructor and respective accessor/mutator
 * methods.</li>
 * </ul>
 * <p>
 * Version 1.1
 * <ul>
 * <li>Added reviewSystemVersion variable. Represents the review system version.</li>
 * </ul>
 * </p>
 *
 * @author tuenm, iamajia, moonli, pvmagacho
 * @version 1.1
 * @since 1.0
 */
public class ProjectType implements Serializable {

    /**
     * Represents the id of this instance. Only values greater than zero is allowed. This variable is initialized in
     * the constructor and can be accessed in the corresponding getter/setter method.
     */
    private long id = 0;

    /**
     * Represents the name of this instance. Null or empty values are not allowed. This variable is initialized in the
     * constructor and can be accessed in the corresponding getter/setter method.
     */
    private String name = null;

    /**
     * Represents the description of this instance. Null value is not allowed. This variable is initialized in the
     * constructor and can be accessed in the corresponding getter/setter method.
     */
    private String description = null;

    /**
     * <p>
     * A <code>boolean</code> flag indicating whether this project type is generic or not. The generic project types
     * can not be used for project creation.
     * </p>
     *
     * @since 1.1
     */
    private boolean generic = false;

    /**
     * Represents the review system version. Cannot be negative. This variable is initialized in the constructor and
     * can be accessed in the corresponding getter/setter method.
     *
     * @since 1.1
     */
    private long reviewSystemVersion;

    /**
     * Create a new ProjectType instance with the given id and name. The two fields are required for a this instance
     * to be persisted.
     *
     * @param id The project type id.
     * @param name The project type name.
     * @param version the review system version.
     * @throws IllegalArgumentException If id is less than or equals to zero, or name is null or empty string.
     */
    public ProjectType(long id, String name, long version) {
        this(id, name, "", version);
    }

    /**
     * Create a new ProjectType instance with the given id, name and description. The two first fields are required
     * for a this instance to be persisted.
     *
     * @param id The project type id.
     * @param name The project type name.
     * @param description The project type description.
     * @param version the review system version.
     * @throws IllegalArgumentException If id is less than or equals to zero, or name is null or empty string, or
     *             description is null.
     */
    public ProjectType(long id, String name, String description, long version) {
        setId(id);
        setName(name);
        setDescription(description);
        setGeneric(false);
        setReviewSystemVersion(version);
    }

    /**
     * Create a new ProjectType instance with the given id, name, description and generic flag. The two first fields
     * are required for a this instance to be persisted.
     *
     * @param id The project type id.
     * @param name The project type name.
     * @param description The project type description.
     * @param generic <code>true</code> if this project type is generic; <code>false</code> otherwise.
     * @param version the review system version.
     * @throws IllegalArgumentException if id is less than or equals to zero, or name is null or empty string, or
     *             description is null.
     * @since 1.1
     */
    public ProjectType(long id, String name, String description, boolean generic, long version) {
        setId(id);
        setName(name);
        setDescription(description);
        setGeneric(generic);
        setReviewSystemVersion(version);
    }

    /**
     * Sets the id for this project type instance. Only positive values are allowed.
     *
     * @param id The id of this project type instance.
     * @throws IllegalArgumentException If project type id is less than or equals to zero.
     */
    public void setId(long id) {
        Helper.checkNumberPositive(id, "id");
        this.id = id;
    }

    /**
     * Gets the id of this project type instance.
     *
     * @return the id of this project type instance.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the name for this project type instance. Null or empty values are not allowed.
     *
     * @param name The name of this project type instance.
     * @throws IllegalArgumentException If project type name is null or empty string.
     */
    public void setName(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        this.name = name;
    }

    /**
     * Gets the name of this project type instance.
     *
     * @return the name of this project type instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description for this project type instance. Null value are not allowed.
     *
     * @param description The description of this project type instance.
     * @throws IllegalArgumentException If project type description is null.
     */
    public void setDescription(String description) {
        Helper.checkObjectNotNull(description, "description");
        this.description = description;
    }

    /**
     * Gets the description of this project type instance.
     *
     * @return the description of this project type instance.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Checks whether this project type is generic or not. The generic project types can not be used for project
     * creation.
     * </p>
     *
     * @return a <code>boolean</code> flag indicating whether this project type is generic or not.
     * @since 1.1
     */
    public boolean isGeneric() {
        return this.generic;
    }

    /**
     * <p>
     * Sets the flag indicating whether this project type is generic or not. The generic project types can not be used
     * for project creation.
     * </p>
     *
     * @param generic a <code>boolean</code> flag indicating whether this project type is generic or not.
     * @since 1.1
     */
    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

    /**
     * <p>
     * Gets the the review system version.
     * </p>
     *
     * @return the review system version.
     * @since 1.1
     */
    public long getReviewSystemVersion() {
        return reviewSystemVersion;
    }

    /**
     * <p>
     * Sets the review system version.
     * </p>
     *
     * @param reviewSystemVersion the review system version.
     * @since 1.1
     */
    public void setReviewSystemVersion(long reviewSystemVersion) {
        Helper.checkNumberPositive(reviewSystemVersion, "reviewSystemVersion");
        this.reviewSystemVersion = reviewSystemVersion;
    }
}
