/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * This class represents a project from the persistence. Each project must
 * belong to a project category and must have a status. Project also have contains some properties. Projects are
 * stored in 'project' table, project category in 'project_category_lu' table,
 * project status in 'project_status_lu' table. project
 * properties are stored in 'project_info' table. This class is used by
 * ProjectPersistence implementors to store projects in the persistence.
 * </p>
 * <p>
 * This class implements Serializable interface to support serialization.
 * </p>
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0
 */
public class Project extends AuditableObject implements Serializable {

    /**
     * Represents the id of this instance. Only values greater than or equal to
     * zero is allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private long id = 0;

    /**
     * Represents the project category of this instance. Null values are not
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private ProjectCategory projectCategory = null;

    /**
     * Represents the project status of this instance. Null values are not
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private ProjectStatus projectStatus = null;

    /**
     * Represents the properties of this instance. Null values are not allowed.
     * This variable is initialized in the constructor and can be accessed in
     * the corresponding getter/setter/getAll method. The map key is property
     * name and map value is property value. Key is string type and value is
     * Object type.
     */
    private Map properties = new HashMap();

    /**
     * <p>
     * Create a new Project instance with the given project type and project
     * status. These fields are required for a project to be created. Project id
     * will be zero which indicates that the project instance is not yet created
     * in the database.
     * </p>
     *
     * @param projectCategory
     *            The project category instance of this project.
     * @param projectStatus
     *            The project status instance of this project.
     * @throws IllegalArgumentException
     *             If any parameter is null.
     */
    public Project(ProjectCategory projectCategory, ProjectStatus projectStatus) {
        this(0, projectCategory, projectStatus);
    }

    /**
     * Create a new Project instance with the given project id, project type and
     * project status. This method is supposed to use by persistence
     * implementation to load project from the persistence when the project id
     * is already set.
     *
     * @param projectId
     *            The project id.
     * @param projectCategory
     *            The project category instance of this project.
     * @param projectStatus
     *            The project status instance of this project.
     * @throws IllegalArgumentException
     *             If id is less than zero, or any parameter is null.
     */
    public Project(long projectId, ProjectCategory projectCategory, ProjectStatus projectStatus) {
        this(projectId, projectCategory, projectStatus, new HashMap());
    }

    /**
     * Create a new Project instance with the given project id, project type id,
     * project status id and the associated properties. The input properties map
     * should contains String/Object as key/value pairs. This method is supposed
     * to use by persistence implementation to load project from the persistence
     * when the project id is already set.
     *
     * @param projectId
     *            The project id.
     * @param projectCategory
     *            The project category instance of this project.
     * @param projectStatus
     *            The project status instance of this project.
     * @param properties
     *            A map of properties of this project.
     * @throws IllegalArgumentException
     *             If id is less than zero, or any parameter is null. Or if in
     *             the 'properties' map, the key/value type is not
     *             String/Object, Or key is null or empty, Object is null.
     */
    public Project(long projectId, ProjectCategory projectCategory, ProjectStatus projectStatus, Map properties) {
        if (projectId < 0) {
            throw new IllegalArgumentException("id can not less than zero.");
        }
        this.id = projectId;
        setProjectCategory(projectCategory);
        setProjectStatus(projectStatus);
        Helper.checkObjectNotNull(properties, "properties");
        // check all entry in properties.
        for (Iterator iter = properties.entrySet().iterator(); iter.hasNext();) {
            Entry entry = (Entry) iter.next();
            if (!(entry.getKey() instanceof String)) {
                throw new IllegalArgumentException("properties contains some key that is not String.");
            }
            Helper.checkStringNotNullOrEmpty((String) entry.getKey(), "properties's key");
            Helper.checkObjectNotNull(entry.getValue(), "properties's value");
        }
        this.properties = new HashMap(properties);
    }

    /**
     * Sets the id for this project instance. Only positive values are allowed.
     *
     * @param id
     *            The id of this project instance.
     * @throws IllegalArgumentException
     *             If project id is less than or equals to zero.
     */
    public void setId(long id) {
        Helper.checkNumberPositive(id, "id");
        this.id = id;
    }

    /**
     * Gets the id of this project instance.
     *
     * @return the id of this project instance.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the project status for this project instance. Null value is not
     * allowed.
     *
     * @param projectStatus
     *            The project status instance to set.
     * @throws IllegalArgumentException
     *             If input is null.
     */
    public void setProjectStatus(ProjectStatus projectStatus) {
        Helper.checkObjectNotNull(projectStatus, "projectStatus");
        this.projectStatus = projectStatus;
    }

    /**
     * Gets the project status of this project instance.
     *
     * @return The project status of this project instance.
     */
    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    /**
     * Sets the project category for this project instance. Null value is not
     * allowed.
     *
     * @param projectCategory
     *            The project category instance to set.
     * @throws IllegalArgumentException
     *             If input is null.
     */
    public void setProjectCategory(ProjectCategory projectCategory) {
        Helper.checkObjectNotNull(projectCategory, "projectCategory");
        this.projectCategory = projectCategory;
    }

    /**
     * Gets the project category of this project instance.
     *
     * @return The project category of this project instance.
     */
    public ProjectCategory getProjectCategory() {
        return projectCategory;
    }

    /**
     * Sets a property for this project instance. If the property name already
     * exist, its value will be updated. Otherwise, the property will be added
     * to the project instance. If value parameter is null, the property will be
     * removed from the project instance. Value of the property will be saved as
     * string in the persistence using toString() method. Project property value
     * is stored in 'project_info' table, while its name is stored in
     * 'project_info_type_lu' table.
     *
     * @param name
     *            The property name.
     * @param value
     *            The property value.
     * @throws IllegalArgumentException
     *             If name is null or empty string.
     */
    public void setProperty(String name, Object value) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        if (value == null) {
            properties.remove(name);
        } else {
            properties.put(name, value);
        }
    }

    /**
     * Gets a property for this project instance. If the property name does not
     * exist in this instance, null value is returned.
     *
     * @return The property value, or null if the property does not exist.
     * @param name
     *            The property name.
     * @throws IllegalArgumentException
     *             If name is null or empty string.
     */
    public Object getProperty(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        return properties.get(name);
    }

    /**
     * Gets a map of property name/value pairs. If there is no property in this
     * project instance, an empty map is returned.
     *
     * @return A map of property name/value pairs. or an empty map if there is
     *         no property in this project instance.
     */
    public Map getAllProperties() {
        return new HashMap(properties);
    }
}
