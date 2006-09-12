/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import com.topcoder.search.builder.filter.Filter;

/**
 * Mock implementation of ProjectManager.
 *
 * @author assistant
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {

    /**
     * Create project.
     * @param project to create
     * @param operator used
     */
    public void createProject(Project project, String operator) {
    }

    /**
     * Get all project categories.
     * @return all project categories.
     */
    public ProjectCategory[] getAllProjectCategories() {
        return null;
    }

    /**
     * Get all project statuses.
     * @return all project statuses.
     */
    public ProjectStatus[] getAllProjectStatuses() {
        return null;
    }

    /**
     * Get project by id.
     * @param id the id
     * @return the project
     */
    public Project getProject(long id) {
        Project project = new MockProject(
                new ProjectCategory(1, "name", new ProjectType(1, "Java")),
                new ProjectStatus(1, "Open"));
        project.setProperty("Public", "Yes");
        return project;
    }

    /**
     * Get projects for a user.
     * @param user id
     * @return all the projects
     */
    public Project[] getUserProjects(long user) {
        return null;
    }

    /**
     * Search projects.
     * @param filter the filter
     * @return the projects
     */
    public Project[] searchProjects(Filter filter) {
        return null;
    }

    /**
     * Update project.
     * @param project the project
     * @param operator the operator
     */
    public void updateProject(Project project, String operator) {
    }

    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] getProjects(long[] arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateProject(Project arg0, String arg1, String arg2) throws PersistenceException, ValidationException {
        // TODO Auto-generated method stub
        
    }

}
