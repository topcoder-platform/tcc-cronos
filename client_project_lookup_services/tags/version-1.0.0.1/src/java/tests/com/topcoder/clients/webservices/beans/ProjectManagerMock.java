/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.manager.ProjectEntityNotFoundException;
import com.topcoder.clients.manager.ProjectManager;
import com.topcoder.clients.manager.ProjectManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Mock implementation of the ProjectManager interface.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectManagerMock implements ProjectManager {

    /**
     * <p>
     * If set to true, retrieveProject operation will return instance which is visible only for
     * administrators.
     * </p>
     */
    private static boolean returnRestricted = false;

    /**
     * <p>
     * If set to true, operations will throw ProjectManagerException.
     * </p>
     */
    private static boolean failOnOperation = false;

    /**
     * <p>
     * Setter for failOnOperation field.
     * </p>
     *
     * @param fail
     *            value to set
     */
    public static void setFail(boolean fail) {
        failOnOperation = fail;
    }

    /**
     * <p>
     * Setter for returnRestricted field.
     * </p>
     *
     * @param restricted
     *            value to set
     */
    public static void setReturnRestricted(boolean restricted) {
        returnRestricted = restricted;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param project
     *            not used
     * @param client
     *            not used
     * @return null
     * @throws ProjectManagerException
     *             not used
     */
    public Project createProject(Project project, Client client) throws ProjectManagerException {
        return null;
    }

    /**
     * <p>
     * Creates project status.
     * </p>
     *
     * @param status
     *            status to create
     * @return created status
     * @throws ProjectManagerException
     *             if failOnOperation is true
     */
    public ProjectStatus createProjectStatus(ProjectStatus status) throws ProjectManagerException {
        checkFail();
        return status;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param project
     *            not used
     * @return null
     * @throws ProjectEntityNotFoundException
     *             not used
     * @throws ProjectManagerException
     *             nod used
     */
    public Project deleteProject(Project project) throws ProjectManagerException,
            ProjectEntityNotFoundException {
        return null;
    }

    /**
     * <p>
     * Deletes passed status.
     * </p>
     *
     * @param status
     *            status to delete
     * @return deleted status
     * @throws ProjectManagerException
     *             if failOnOperation is true
     * @throws ProjectEntityNotFoundException
     *             if failOnOperation is true
     */
    public ProjectStatus deleteProjectStatus(ProjectStatus status) throws ProjectManagerException,
            ProjectEntityNotFoundException {
        checkFail();
        return status;
    }

    /**
     * <p>
     * Returns all statuses.
     * </p>
     *
     * @return list of statuses
     * @throws ProjectManagerException
     *             if failOnOperation is true
     */
    public List<ProjectStatus> getAllProjectStatuses() throws ProjectManagerException {
        checkFail();

        ProjectStatus p1 = new ProjectStatus();
        p1.setDescription("test1");
        ProjectStatus p2 = new ProjectStatus();
        p2.setDescription("test2");
        List<ProjectStatus> result = new ArrayList<ProjectStatus>();
        result.add(p1);
        result.add(p2);

        return result;
    }

    /**
     * <p>
     * Gets status by id.
     * </p>
     *
     * @param id
     *            id of needed status
     * @return status with passed id
     * @throws ProjectManagerException
     *             if failOnOperation is true
     */
    public ProjectStatus getProjectStatus(long id) throws ProjectManagerException {
        checkFail();

        ProjectStatus result = new ProjectStatus();
        result.setId(id);

        return result;
    }

    /**
     * <p>
     * Gets projects for given status.
     * </p>
     *
     * @param status
     *            status which found projects will contain
     * @return projects for given status.
     * @throws ProjectManagerException
     *             if failOnOperation is true
     */
    public List<Project> getProjectsForStatus(ProjectStatus status) throws ProjectManagerException {
        return retrieveAllProjects();
    }

    /**
     * <p>
     * Gets all projects.
     * </p>
     *
     * @return all projects
     * @throws ProjectManagerException
     *             if failOnOperation is true
     */
    public List<Project> retrieveAllProjects() throws ProjectManagerException {
        checkFail();

        List<Project> result = (new UserMappingRetrieverMock()).getProjectsForUserId(0);
        result.add(new Project());

        return result;
    }

    /**
     * <p>
     * Gets project for given id.
     * </p>
     *
     * @param id
     *            for which to get project
     * @return found project
     * @throws ProjectManagerException
     *             if failOnOperation is true
     */
    public Project retrieveProject(long id) throws ProjectManagerException {
        checkFail();

        List<Project> projects = (new UserMappingRetrieverMock()).getProjectsForUserId(id);

        Project result;
        if (returnRestricted) {
            result = new Project();
        } else {
            result = projects.get(0);
        }
        result.setId(id);

        return result;
    }

    /**
     * <p>
     * Gets all projects for passed client.
     * </p>
     *
     * @param client
     *            client which projects to find
     * @return all projects for passed client
     * @throws ProjectManagerException
     *             if failOnOperation is true
     */
    public List<Project> retrieveProjectsForClient(Client client) throws ProjectManagerException {
        return retrieveAllProjects();
    }

    /**
     * <p>
     * Gets projects which match given filter.
     * </p>
     *
     * @param filter
     *            filter to use
     * @return projects for given filter
     * @throws ProjectManagerException
     *             if failOnOperation is true
     */
    public List<Project> searchProjects(Filter filter) throws ProjectManagerException {
        return retrieveAllProjects();
    }

    /**
     * <p>
     * Gets projects for given name.
     * </p>
     *
     * @param name
     *            name for which projects will be found
     * @return projects with given name
     * @throws ProjectManagerException
     *             if failOnOperation is true
     */
    public List<Project> searchProjectsByName(String name) throws ProjectManagerException {
        return retrieveAllProjects();
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param project
     *            not used
     * @param status
     *            not used
     * @return null
     * @throws ProjectManagerException
     *             not used
     * @throws ProjectEntityNotFoundException
     *             not used
     */
    public Project setProjectStatus(Project project, ProjectStatus status)
            throws ProjectEntityNotFoundException, ProjectManagerException {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param project
     *            not used
     * @return null
     * @throws ProjectManagerException
     *             not used
     * @throws ProjectEntityNotFoundException
     *             not used
     */
    public Project updateProject(Project project) throws ProjectManagerException,
            ProjectEntityNotFoundException {
        return null;
    }

    /**
     * <p>
     * Updates passed status.
     * </p>
     *
     * @param status
     *            status to update
     * @return updated status
     * @throws ProjectManagerException
     *             if failOnOperation is true
     * @throws ProjectEntityNotFoundException
     *             if failOnOperation is true
     */
    public ProjectStatus updateProjectStatus(ProjectStatus status) throws ProjectManagerException,
            ProjectEntityNotFoundException {
        if (failOnOperation) {
            throw new ProjectManagerException("Test exception", null, status);
        }
        return status;
    }

    /**
     * <p>
     * Throws ProjectManagerException if failOnOperation flag is true.
     * </p>
     *
     * @throws ProjectManagerException
     *             if failOnOperation flag is true
     */
    private void checkFail() throws ProjectManagerException {
        if (failOnOperation) {
            throw new ProjectManagerException("Test exception", null, null);
        }
    }

}
