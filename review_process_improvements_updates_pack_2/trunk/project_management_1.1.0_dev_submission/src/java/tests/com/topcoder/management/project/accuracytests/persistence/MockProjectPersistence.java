/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests.persistence;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectPersistence;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * A mock class implements <code>ProjectPersistence</code> interface. I just record which implemented method was last
 * called, projects are not really persisted.
 *
 * @author skatou
 * @version 1.0
 */
public class MockProjectPersistence implements ProjectPersistence {
    /** Represents the name of implemented method last called. */
    private static String lastCalled = null;

    /**
     * Creates a new MockProjectPersistence instance with the given namespace.
     *
     * @param namespace ignore.
     */
    public MockProjectPersistence(String namespace) {
    }

    /**
     * Gets the name of implemented method last called.
     *
     * @return the name of the method last called.
     */
    public static String getLastCalled() {
        return lastCalled;
    }

    /**
     * Records this method is called.
     *
     * @param project parameter 1.
     * @param operator parameter 2.
     *
     * @throws PersistenceException never thrown.
     */
    public void createProject(Project project, String operator) throws PersistenceException {
        lastCalled = "createProject" + project + operator;
    }

    /**
     * Records this method is called.
     *
     * @param project parameter 1.
     * @param reason parameter 2.
     * @param operator parameter 3.
     *
     * @throws PersistenceException never thrown.
     */
    public void updateProject(Project project, String reason, String operator) throws PersistenceException {
        lastCalled = "updateProject" + project + reason + operator;
    }

    /**
     * Records this method is called.
     *
     * @param id parameter 1.
     *
     * @return null.
     *
     * @throws PersistenceException never thrown.
     */
    public Project getProject(long id) throws PersistenceException {
        lastCalled = "getProject" + id;

        return null;
    }

    /**
     * Records this method is called.
     *
     * @param ids parameter 1.
     *
     * @return null.
     *
     * @throws PersistenceException never thrown.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        lastCalled = "getProjects" + ids;

        ProjectType type = new ProjectType(1, "type", 1);
        ProjectCategory category = new ProjectCategory(1, "category", type);
        ProjectStatus status = new ProjectStatus(1, "status");
        Project[] projects = new Project[ids.length];

        for (int i = 0; i < projects.length; ++i) {
            projects[i] = new Project(ids[i], category, status, new HashMap());
        }

        return projects;
    }

    /**
     * Records this method is called.
     *
     * @return null.
     *
     * @throws PersistenceException never thrown.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        lastCalled = "getAllProjectTypes";

        return null;
    }

    /**
     * Records this method is called.
     *
     * @return null.
     *
     * @throws PersistenceException never thrown.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        lastCalled = "getAllProjectCategories";

        return null;
    }

    /**
     * Records this method is called.
     *
     * @return null.
     *
     * @throws PersistenceException never thrown.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        lastCalled = "getAllProjectStatuses";

        return null;
    }

    /**
     * Records this method is called.
     *
     * @return null.
     *
     * @throws PersistenceException never thrown.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        lastCalled = "getAllProjectPropertyTypes";

        return null;
    }

    /**
     * Get projects from sql result.
     *
     * @param result result set from sql query
     * @return An array with project found in persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjects(CustomResultSet result) throws PersistenceException {
        lastCalled = "getProjects";

        int size = result.getRecordCount();
        Project[] projects = new Project[size];

        for (int i = 0; i < size; i++) {
            result.absolute(i + 1);

            try {
                // create the ProjectStatus object
                ProjectStatus status = new ProjectStatus(result.getLong(2), result.getString(3));

                // create the ProjectType object
                ProjectType type = new ProjectType(result.getLong(6), result.getString(7), result.getInt(8));

                // create the ProjectCategory object
                ProjectCategory category = new ProjectCategory(result.getLong(4), result.getString(5), type);

                // create a new instance of ProjectType class
                projects[i] = new Project(result.getLong(1), category, status);

                // assign the audit information
                projects[i].setCreationUser(result.getString(9));
                projects[i].setCreationTimestamp(result.getDate(10));
                projects[i].setModificationUser(result.getString(11));
                projects[i].setModificationTimestamp(result.getDate(12));

                projects[i].setProperty("developer", "iamajia");
            } catch (InvalidCursorStateException e) {
                throw new PersistenceException(e.getMessage(), e);
            }
        }
        return projects;
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
