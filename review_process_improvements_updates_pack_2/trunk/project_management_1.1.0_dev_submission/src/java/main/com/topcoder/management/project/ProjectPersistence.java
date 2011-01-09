/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * This interface defines the contract that any project persistence must implement. The implementation classes will be
 * used by ProjectManagerImpl to perform project persistence operations. The implementation classes should have a
 * constructor that receives a namespace string parameter so that they're exchangeable with each other by changing
 * configuration settings in the manager.
 *
 * <p>
 * <b>Thread safety</b>: The implementations of this interface do not have to be thread safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0.1
 */
public interface ProjectPersistence {
    /**
     * Create the project in the database using the given project instance. The project information is stored to
     * 'project' table, while its properties are stored in 'project_info' table. The project's associating scorecards
     * are stored in 'project_scorecard' table. For the project, its properties and associating scorecards, the
     * operator parameter is used as the creation/modification user and the creation date and modification date will
     * be the current date time when the project is created.
     *
     * @param project The project instance to be created in the database.
     * @param operator The creation user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void createProject(Project project, String operator) throws PersistenceException;

    /**
     * Update the given project instance into the database. The project information is stored to 'project' table,
     * while its properties are stored in 'project_info' table. The project's associating scorecards are stored in
     * 'project_scorecard' table. All related items in these tables will be updated. If items are removed from the
     * project, they will be deleted from the persistence. Likewise, if new items are added, they will be created in
     * the persistence. For the project, its properties and associating scorecards, the operator parameter is used as
     * the modification user and the modification date will be the current date time when the project is updated. An
     * update reason is provided with this method. Update reason will be stored in the persistence for future
     * references.
     *
     * @param project The project instance to be updated into the database.
     * @param reason The update reason. It will be stored in the persistence for future references.
     * @param operator The modification user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void updateProject(Project project, String reason, String operator) throws PersistenceException;

    /**
     * Retrieves the project instance from the persistence given its id. The project instances is retrieved with its
     * properties.
     *
     * @return The project instance.
     * @param id The id of the project to be retrieved.
     * @throws IllegalArgumentException if the input id is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project getProject(long id) throws PersistenceException;

    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their ids. The project instances are
     * retrieved with their properties.
     * </p>
     *
     * @param ids The ids of the projects to be retrieved.
     * @return An array of project instances.
     * @throws IllegalArgumentException if ids is null or empty or contain an id that is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException;

    /**
     * <p>
     * Retrieves an array of project instance from the persistence whose create date is within current - days.
     * </p>
     *
     * @param days last 'days'
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjectsByCreateDate(int days) throws PersistenceException;

    /**
     * Gets an array of all project types in the persistence. The project types are stored in 'project_type_lu' table.
     *
     * @return An array of all project types in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException;

    /**
     * Gets an array of all project categories in the persistence. The project categories are stored in
     * 'project_category_lu' table.
     *
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException;

    /**
     * Gets an array of all project statuses in the persistence. The project statuses are stored in
     * 'project_status_lu' table.
     *
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException;

    /**
     * Gets an array of all project property type in the persistence. The project property types are stored in
     * 'project_info_type_lu' table.
     *
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException;

    /**
     * Build {@link Project} directly from the {@link CustomResultSet}.
     *
     * @param resultSet a {@link CustomResultSet} containing the data for build the {@link Project} instances.
     * @return an array of {@link Project}
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjects(CustomResultSet resultSet) throws PersistenceException;
}
