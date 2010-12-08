/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.failuretests.mock;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ValidationException;
import com.topcoder.management.project.Project;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock implementation of ProjectManager.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {

    /**
     * Empty.
     *
     * @param project
     *            the project.
     * @param operator
     *            the operator.
     * @return null.
     */
    public void createProject(Project project, String operator) throws PersistenceException, ValidationException {

    }

    /**
     * Empty.
     *
     * @return null.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        return null;
    }

    /**
     * Empty.
     *
     * @return null.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return null;
    }

    /**
     * Empty.
     *
     * @return null.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return null;
    }

    /**
     * Empty.
     *
     * @return null.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return null;
    }

    /**
     * Empty.
     *
     * @param id
     *            the id.
     * @return null.
     * @throws PersistenceException
     *             always.
     */
    public Project getProject(long id) throws PersistenceException {
        throw new PersistenceException("");
    }

    /**
     * Empty.
     *
     * @param ids
     *            the ids.
     * @return null.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        return null;
    }

    /**
     * Empty.
     *
     * @param days
     *            the days.
     * @return null.
     */
    public Project[] getProjectsByCreateDate(int days) throws PersistenceException {
        return null;
    }

    /**
     * Empty.
     *
     * @param user
     *            the user.
     * @return null.
     */
    public Project[] getUserProjects(long user) throws PersistenceException {
        return null;
    }

    /**
     * Empty.
     *
     * @param filter
     *            the filter.
     *
     * @return null.
     */
    public Project[] searchProjects(Filter filter) throws PersistenceException {
        return null;
    }

    /**
     * Empty.
     *
     * @param project
     *            the project.
     * @param reason
     *            the reason.
     * @param operator
     *            the operator.
     */
    public void updateProject(Project project, String reason, String operator) throws PersistenceException,
        ValidationException {
    }
}
