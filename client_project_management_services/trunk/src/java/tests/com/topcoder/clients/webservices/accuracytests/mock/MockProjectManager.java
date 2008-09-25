/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.accuracytests.mock;

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
public class MockProjectManager implements ProjectManager {

    /**
     * <p>
     * If set to true, operations will throw ProjectManagerException.
     * </p>
     */
    private static boolean throwPME = false;
    /**
     * <p>
     * If set to true, operations will throw IllegalArgumentException.
     * </p>
     */
    private static boolean throwIAE = false;

    /**
     * <p>
     * Setter for throwPME field.
     * </p>
     *
     * @param fail
     *            value to set
     */
    public static void throwPME(boolean fail) {
        throwPME = fail;
    }

    /**
     * <p>
     * Setter for throwIAE field.
     * </p>
     *
     * @param fail
     *            value to set
     */
    public static void throwIAE(boolean fail) {
        throwIAE = fail;
    }

    /**
     * <p>
     * Create the project.
     * </p>
     *
     * @param project
     *            the project to create
     * @param client
     *            used to create the project
     * @return the created project
     * @throws ProjectManagerException
     *             if throwPME is true
     */
    public Project createProject(Project project, Client client) throws ProjectManagerException {
        Project p = new Project();
        p.setName("AAAA");
        p.setParentProjectId(1);
        return p;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param status
     *            not used
     * @return null
     * @throws ProjectManagerException
     *             not used
     */
    public ProjectStatus createProjectStatus(ProjectStatus status) throws ProjectManagerException {

        return null;
    }

    /**
     * <p>
     * Delete the project.
     * </p>
     *
     * @param project
     *            the project to delete
     * @return the deleted project
     * @throws ProjectManagerException
     *             if throwPME is true
     * @throws ProjectEntityNotFoundException
     *              won't throw
     */
    public Project deleteProject(Project project) throws ProjectManagerException,
            ProjectEntityNotFoundException {
        Project p = new Project();
        p.setName("CCCC");
        p.setDeleted(true);
        p.setParentProjectId(1);
        return p;
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
    public List < ProjectStatus > getAllProjectStatuses() throws ProjectManagerException {
        checkFail();

        ProjectStatus p1 = new ProjectStatus();
        p1.setDescription("test1");
        ProjectStatus p2 = new ProjectStatus();
        p2.setDescription("test2");
        List < ProjectStatus > result = new ArrayList < ProjectStatus >();
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
    public List < Project > getProjectsForStatus(ProjectStatus status) throws ProjectManagerException {
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
    public List < Project > retrieveAllProjects() throws ProjectManagerException {
        return null;
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
        return null;
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
    public List < Project > retrieveProjectsForClient(Client client) throws ProjectManagerException {
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
    public List < Project > searchProjects(Filter filter) throws ProjectManagerException {
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
    public List < Project > searchProjectsByName(String name) throws ProjectManagerException {
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
        Project p = new Project();
        p.setName("EEEE");
        p.setParentProjectId(1);
        return p;
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
        Project p = new Project();
        p.setName("DDDD");
        p.setParentProjectId(1);
        return p;
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
        if (throwPME) {
            throw new ProjectManagerException("Test exception", null, status);
        }
        return status;
    }

    /**
     * <p>
     * Throws ProjectManagerException if throwPME flag is true.
     * Throws IllegalArgumentException if throwIAE flag is true.
     * </p>
     *
     * @throws ProjectManagerException
     *             if throwPME flag is true
     * @throws IllegalArgumentException
     *             if throwIAE is true
     */
    private void checkFail() throws ProjectManagerException {
        if (throwPME) {
            throw new ProjectManagerException("Test exception", null, null);
        }
        if (throwIAE) {
            throw new IllegalArgumentException("Test exception");
        }
    }

}
