/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * This a mock class implements ProjectPersistence.
 *
 * @author iamajia
 * @version 1.0
 */
public class MockProjectPersistence implements ProjectPersistence {
    /**
     * do nothing.
     *
     * @param ns The namespace to load configuration settings from.
     */
    public MockProjectPersistence(String ns) {
    }

    /**
     * simply validate the input.
     *
     * @param project The project instance to be created in the database.
     * @param operator The creation user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void createProject(Project project, String operator) throws PersistenceException {
        Helper.checkObjectNotNull(project, "project");
        Helper.checkStringNotNullOrEmpty(operator, "operator");
    }

    /**
     * simply validate the input.
     *
     * @param project The project instance to be updated into the database.
     * @param reason The update reason. It will be stored in the persistence for future references.
     * @param operator The modification user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void updateProject(Project project, String reason, String operator) throws PersistenceException {
        Helper.checkObjectNotNull(project, "project");
        Helper.checkStringNotNullOrEmpty(reason, "reason");
        Helper.checkStringNotNullOrEmpty(operator, "operator");
    }

    /**
     * simply validate the input.
     *
     * @return The project instance.
     * @param id The id of the project to be retrieved.
     * @throws IllegalArgumentException if the input id is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project getProject(long id) throws PersistenceException {
        Helper.checkNumberPositive(id, "id");
        return null;
    }

    /**
     * simply validate the input.
     *
     * @param ids The ids of the projects to be retrieved.
     * @return An array of project instances.
     * @throws IllegalArgumentException if ids empty or contain an id that is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        Helper.checkObjectNotNull(ids, "ids");
        if (ids.length == 0) {
            throw new IllegalArgumentException("ids can not be empty.");
        }
        for (int i = 0; i < ids.length; i++) {
            Helper.checkNumberPositive(ids[i], "ids[" + i + "]");
        }
        Project[] projects = new Project[ids.length];
        for (int i = 0; i < ids.length; i++) {
            projects[i] = new Project(ids[i], getAllProjectCategories()[0], getAllProjectStatuses()[0]);
        }
        return projects;
    }

    /**
     * return a ProjectType array, it is used in the test.
     *
     * @return An array of all project types in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return new ProjectType[] {new ProjectType(1, "type1", 1), new ProjectType(2, "type2", 1)};
    }

    /**
     * return a ProjectCategory array.
     *
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        return new ProjectCategory[] {new ProjectCategory(1, "category1", getAllProjectTypes()[0]),
            new ProjectCategory(2, "category2", getAllProjectTypes()[1])};
    }

    /**
     * return a ProjectStatus array, it is used in the test.
     *
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return new ProjectStatus[] {new ProjectStatus(1, "status1"), new ProjectStatus(2, "status2")};
    }

    /**
     * return a ProjectPropertyType array, it is used in the test.
     *
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return new ProjectPropertyType[] {new ProjectPropertyType(1, "propertyType1"),
            new ProjectPropertyType(2, "propertyType2")};
    }

    public Project[] getProjects(CustomResultSet resultSet) throws PersistenceException {
        throw new IllegalStateException("not implemented");
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence whose create date is within current - days.
     * </p>
     *
     * @param days last 'days'
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjectsByCreateDate(int days) throws PersistenceException {
        return null;
    }
}
