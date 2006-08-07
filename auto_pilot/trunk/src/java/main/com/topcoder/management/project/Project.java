/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class represents a project from the persistence. Each project must belong to a project
 * category and must have a status. Project also associated with a set of scorecards and have
 * contains some propeties. Projects are stored in 'project' table, project category in
 * 'project_category_lu' table, project status in 'project_status_lu' table. Association between a
 * project and scorecards are stored in 'project_scorecard' table, and project properties are stored
 * in 'project_info' table. This class is used by ProjectPersistence implementors to store projects
 * in the persistence.
 * </p>
 * <p>
 * This class implements Serializable interface to support serialization.
 * </p>
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class Project extends AuditableObject implements Serializable {

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
     * Represents the project category of this instance. Null values are not allowed. This variable
     * is initialized in the constructor and can be accessed in the corresponding getter/setter
     * method.
     */
    private ProjectCategory projectCategory = null;

    /**
     * Represents the project status of this instance. Null values are not allowed. This variable is
     * initialized in the constructor and can be accessed in the corresponding getter/setter method.
     */
    private ProjectStatus projectStatus = null;

    /**
     * Represents the properties of this instance. Null values are not allowed. This variable is
     * initialized in the constructor and can be accessed in the corresponding getter/setter/getAll
     * method. The map key is property name and map value is property value. Key is string type and
     * value is Object type.
     */
    private Map properties = null;


    /**
     * Represents the properties of this instance. Null values are not allowed. This variable is
     * initialized in the constructor and can be accessed in the corresponding getter/setter/getAll
     * method. The map key is scorecard assigment id and the value is the scorecard id. Key and
     * value are Long type. Call setScorecard() method with null value for scorecard id will remove
     * the entry from the map.
     */
    private Map scorecards = null;

    /**
     * <p>
     * Create a new Project instance with the given project type and project status. These fields
     * are required for a project to be created. Project id will be zero which indicates that the
     * project instance is not yet created in the database.
     * </p>
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Set the corresponding member variables.</li>
     * <li>properties map is initialized to an empty map</li>
     * <li>scorecards map is initialized to an empty map</li>
     * </ul>
     * <p>
     * </p>
     * @param projectCategory The project category instance of this project.
     * @param projectStatus The project status instance of this project.
     * @throws IllegalArgumentException If any parameter is null.
     */
    public Project(ProjectCategory projectCategory, ProjectStatus projectStatus) {
        // your code here
        this.properties = new HashMap();
        this.scorecards = new HashMap();
        this.projectCategory = projectCategory;
        this.projectStatus = projectStatus;
    }

    /**
     * Create a new Project instance with the given project id, project type and project status.
     * This method is supposed to use by persistence implementation to load project from the
     * persistence when the project id is already set.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Set the corresponding member variables.</li>
     * <li>properties map is initialized to an empty map</li>
     * <li>scorecards map is initialized to an empty map</li>
     * </ul>
     * @param projectId The project id.
     * @param projectCategory The project category instance of this project.
     * @param projectStatus The project status instance of this project.
     * @throws IllegalArgumentException If id is less than zero, or any parameter is null.
     */
    public Project(long projectId, ProjectCategory projectCategory, ProjectStatus projectStatus) {
        // your code here
        this(projectCategory, projectStatus);
        this.id = projectId;
    }

    /**
     * Create a new Project instance with the given project id, project type id, project status id
     * and the associated properties. The input properties map should contains String/Object as
     * key/value pairs. This method is supposed to use by persistence implementation to load project
     * from the persistence when the project id is already set.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Check the type of the input map, throw IllegalArgumentException if they're invalid.</li>
     * <li>Empty maps is allowed</li>
     * <li>Set the corresponding member variables.</li>
     * </ul>
     * @param projectId The project id.
     * @param projectCategory The project category instance of this project.
     * @param projectStatus The project status instance of this project.
     * @param properties A map of properties of this project.
     * @throws IllegalArgumentException If id is less than zero, or any parameter is null. Or the
     *             map is empty. Or if in the 'properties' map, the key/value type is not
     *             String/Object.
     */
    public Project(long projectId, ProjectCategory projectCategory, ProjectStatus projectStatus,
        Map properties) {
        // your code here
        this(projectId, projectCategory, projectStatus);
        this.properties = new HashMap(properties);
    }

    /**
     * Sets the id for this project instance. Only possitive values are allowed.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Set the corresponding member variables.</li>
     * </ul>
     * @param id The id of this project instance.
     * @throws IllegalArgumentException If project id is less than or equals to zero.
     */
    public void setId(long id) {
        // your code here
        this.id = id;
    }

    /**
     * Gets the id of this project instance.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Return the corresponding member variables.</li>
     * </ul>
     * @return the id of this project instance.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the project status for this project instance. Null value is not allowed.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Set the corresponding member variables.</li>
     * </ul>
     * @param projectStatus The project status instance to set.
     * @throws IllegalArgumentException If input is null.
     */
    public void setProjectStatus(ProjectStatus projectStatus) {
        // your code here
        this.projectStatus = projectStatus;
    }

    /**
     * Gets the project status of this project instance.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Return the corresponding member variables.</li>
     * </ul>
     * @return The project status of this project instance.
     */
    public ProjectStatus getProjectStatus() {
        return this.projectStatus;
    }

    /**
     * Sets the project category for this project instance. Null value is not allowed.
     * <p>
     * @param projectCategory The project category instance to set.
     * @throws IllegalArgumentException If input is null.
     */
    public void setProjectCategory(ProjectCategory projectCategory) {
        // your code here
        this.projectCategory = projectCategory;
    }

    /**
     * Gets the project category of this project instance.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Return the corresponding member variables.</li>
     * </ul>
     * @return The project category of this project instance.
     */
    public ProjectCategory getProjectCategory() {
        return this.projectCategory;
    }

    /**
     * Sets a property for this project instance. If the property name already exist, its value will
     * be updated. Otherwise, the property will be added to the project instance. If value parameter
     * is null, the property will be removed from the project instance. Value of the property will
     * be saved as string in the persistence using toString() method. Project property value is
     * stored in 'project_info' table, while its name is stored in 'project_info_type_lu' table.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Store the name/value paramters to the 'properties' map.</li>
     * <li>If key does not exist, add as new entry, if key exist, update its value.</li>
     * <li>If value is null and key exist, remove the entry from the map</li>
     * </ul>
     * @param name The property name.
     * @param value The property value.
     * @throws IllegalArgumentException If name is null or empty string.
     */
    public void setProperty(String name, Object value) {
        // your code here
        this.properties.put(name, value);
    }

    /**
     * Gets a property for this project instance. If the property name does not exist in this
     * instance, null value is returned.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Lookup the 'properties' map to find the key with the input value, if found, return map's
     * value. Else return null</li>
     * </ul>
     * @return The property value, or null if the property does not exist.
     * @param name The property name.
     */
    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    /**
     * Gets a map of property name/value pairs. If there is no property in this project instance, an
     * empty map is returned.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>return a copy of the 'properties' map</li>
     * </ul>
     * @return A map of property name/value pairs. or an empty map if there is no property in this
     *         project instance.
     */
    public Map getAllProperties() {
        return new HashMap(this.properties);
    }
}
