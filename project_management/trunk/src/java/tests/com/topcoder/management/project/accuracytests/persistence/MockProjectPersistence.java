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
    public void createProject(Project project, String operator)
        throws PersistenceException {
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
    public void updateProject(Project project, String reason, String operator)
        throws PersistenceException {
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

        ProjectType type = new ProjectType(1, "type");
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
    public ProjectCategory[] getAllProjectCategories()
        throws PersistenceException {
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
    public ProjectPropertyType[] getAllProjectPropertyTypes()
        throws PersistenceException {
        lastCalled = "getAllProjectPropertyTypes";

        return null;
    }
}
