/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This class represents a project property type from the persistence. Each
 * project property must associated with a project property type. Project
 * property type are stored in 'project_info_type_lu' table, project property in
 * 'project_info' table. A project property type instance contains id, name and
 * description. This class is used in ProjectManager#getAllProjectPropertyTypes
 * method to return project property types from the persistence. This class
 * implements Serializable interface to support serialization.
 * </p>
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0
 */
public class ProjectPropertyType implements Serializable {


    /**
     * <p>
     * Represents the "Review Cost" project property key
     * </p>
     *
     */
    public static final String REVIEW_COSTS_PROJECT_PROPERTY_KEY = "Review Cost";


    /**
     * <p>
     * Represents the "Review Cost" project property key
     * </p>
     *
     */
    public static final String SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY = "Spec Review Cost";


    /**
     * Private constant specifying project type info's component id key.
     *
     */
    public static final String COMPONENT_ID_PROJECT_PROPERTY_KEY = "Component ID";

    /**
     * Private constant specifying project type info's version id key.
     *
     */
    public static final String VERSION_ID_PROJECT_PROPERTY_KEY = "Version ID";

    /**
     * Private constant specifying project type info's version id key.
     *
     */
    public static final String EXTERNAL_REFERENCE_PROJECT_PROPERTY_KEY = "External Reference ID";

    /**
     * Private constant specifying project type info's forum id key.
     *
     */
    public static final String DEVELOPER_FORUM_ID_PROJECT_PROPERTY_KEY = "Developer Forum ID";

    /**
     * Private constant specifying project type info's SVN Module key.
     *
     */
    public static final String SVN_MODULE_PROJECT_PROPERTY_KEY = "SVN Module";

    /**
     * Private constant specifying project type info's Notes key.
     *
     */
    public static final String NOTES_PROJECT_PROPERTY_KEY = "Notes";

    /**
     * Private constant specifying project type info's project name key.
     *
     */
    public static final String PROJECT_NAME_PROJECT_PROPERTY_KEY = "Project Name";

    /**
     * <p>
     * Represents the "Autopilot option" project property key
     * </p>
     */
    public static final String AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY = "Autopilot Option";

        /**
     * Private constant specifying project type info's project name key.
     *
     */
    public static final String PROJECT_VERSION_PROJECT_PROPERTY_KEY = "Project Version";

        /**
     * Private constant specifying project type info's billing project key.
     *
     * @since 1.3.3
     */
    public static final String BILLING_PROJECT_PROJECT_PROPERTY_KEY = "Billing Project";

    /**
     * Represents the id of this instance. Only values greater than zero is
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private long id = 0;

    /**
     * Represents the name of this instance. Null or empty values are not
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private String name = null;

    /**
     * Represents the description of this instance. Null value is not allowed.
     * This variable is initialized in the constructor and can be accessed in
     * the corresponding getter/setter method.
     */
    private String description = null;

    /**
     * Create a new ProjectPropertyType instance with the given id and name. The
     * two fields are required for a this instance to be persisted.
     *
     * @param id
     *            The project property type id.
     * @param name
     *            The project property type name.
     * @throws IllegalArgumentException
     *             If id is less than or equals to zero, or name is null or
     *             empty string.
     */
    public ProjectPropertyType(long id, String name) {
        this(id, name, "");
    }

    /**
     * Create a new ProjectPropertyType instance with the given id, name and
     * description. The two first fields are required for a this instance to be
     * persisted.
     *
     * @param id
     *            The project property type id.
     * @param name
     *            The project property type name.
     * @param description
     *            The project property type description.
     * @throws IllegalArgumentException
     *             If any id is less than or equals to zero, or name is null or
     *             empty string, or description is null.
     */
    public ProjectPropertyType(long id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    /**
     * Sets the id for this project property type instance. Only positive
     * values are allowed.
     *
     * @param id
     *            The id of this project property type instance.
     * @throws IllegalArgumentException
     *             If project property type id is less than or equals to zero.
     */
    public void setId(long id) {
        Helper.checkNumberPositive(id, "id");
        this.id = id;
    }

    /**
     * Gets the id of this project property type instance.
     *
     * @return the id of this project property type instance.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the name for this project property type instance. Null or empty
     * values are not allowed.
     *
     * @param name
     *            The name of this project property type instance.
     * @throws IllegalArgumentException
     *             If project property type name is null or empty string.
     */
    public void setName(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        this.name = name;
    }

    /**
     * Gets the name of this project property type instance.
     *
     * @return the name of this project property type instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description for this project property type instance. Null value
     * are not allowed.
     *
     * @param description
     *            The description of this project property type instance.
     * @throws IllegalArgumentException
     *             If project property type description is null.
     */
    public void setDescription(String description) {
        Helper.checkObjectNotNull(description, description);
        this.description = description;
    }

    /**
     * Gets the description of this project property type instance.
     *
     * @return the description of this project property type instance.
     */
    public String getDescription() {
        return description;
    }
}
