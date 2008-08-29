/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.search.builder.filter.Filter;

/**
 * This interface defines functionality for managing project information, including CRUD functionality for projects and
 * statuses, searching for projects by name and a provided search filter, and getting projects by status, as well as
 * getting all projects for a provided client.
 *
 * Thread Safety: implementations of this interface should be thread safe.
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public interface ProjectManager {
    /**
     * Creates a new Project. After creation, a new ID will be generated for it.
     *
     * @param project
     *            the new Project to create, can not be null (salesTax should be greater than zero, description should
     *            be non-null String, name should be Non-null and non-empty string, isDeleted should be false)
     *
     * @param client
     *            the client of the project, it can't be null
     * @return the Project instance created, with new ID assigned.
     * @throws IllegalArgumentException
     *             if any argument is invalid (null, salesTax is not greater than zero, description is null,
     *             name is null or empty, or isDeleted is true, or client is null)
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer, or in ID generator
     *
     */
    public Project createProject(Project project, Client client) throws ProjectManagerException;

    /**
     * Updates a Project. Error will be thrown if the project to update does not exist in persistence.
     *
     *
     *
     * @param project
     *            the project to update, can not be null (salesTax should be greater than zero, description should be
     *            non-null String, name should be Non-null and non-empty string, isDeleted should be false)
     *
     * @return the updated Project.
     * @throws IllegalArgumentException
     *             if the project is invalid (null, salesTax is not greater than zero, description is null,
     *             name is null or empty, or isDeleted is true)
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     * @throws ProjectEntityNotFoundException
     *             if the project to be updated does not exist in persistence
     *
     */
    public Project updateProject(Project project) throws ProjectManagerException, ProjectEntityNotFoundException;

    /**
     * Deletes given Project.
     *
     * @param project
     *            the project to delete, can't be null, isDeleted should be false
     * @return the deleted project, with isDeleted set to true
     * @throws IllegalArgumentException
     *             if project is null or it's isDeleted is true
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     * @throws ProjectEntityNotFoundException
     *             if the project to be updated does not exist in persistence
     */
    public Project deleteProject(Project project) throws ProjectManagerException, ProjectEntityNotFoundException;

    /**
     * Gets the Project by its ID. All child project will be retrieved too.
     *
     * @param id
     *            id of the Project to retrieve, can't be negative
     * @return the Project with given ID, or null if none exists with given ID
     *
     * @throws IllegalArgumentException
     *             if id is negative
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Project retrieveProject(long id) throws ProjectManagerException;

    /**
     * Gets the projects for specified client, children projects will be retrieved. Empty list will be returned if none
     * if found.
     *
     *
     * @param client
     *            a Client whose projects will be retrieved, can't be null, id of client should not be negative
     *
     * @return a list of Project objects, can be empty
     * @throws IllegalArgumentException
     *             if argument client is invalid (client is null, or client's id is negative
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> retrieveProjectsForClient(Client client) throws ProjectManagerException;

    /**
     * Gets all projects, the child projects will be retrieved too. Empty list will be returned if none is found.
     *
     *
     * @return a list of Project objects, can be empty
     *
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> retrieveAllProjects() throws ProjectManagerException;

    /**
     * Sets the project status, and forward this update to the persistence.
     *
     *
     * @param project
     *            a Project object whose status will be set, (salesTax should be greater than zero, description should
     *            be non-null String, name should be Non-null and non-empty string, isDeleted should be false)
     * @param status
     *            the project status to set, can't be null (name should be non-null and non-empty string, description
     *            should be non-null and non-empty string, isDeleted should be false)
     *
     * @return the updated Project, with new status set
     *
     * @throws IllegalArgumentException
     *             if any argument is invalid
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     * @throws ProjectEntityNotFoundException
     *             if the project to be updated does not exist in persistence
     */
    public Project setProjectStatus(Project project, ProjectStatus status) throws ProjectEntityNotFoundException,
            ProjectManagerException;

    /**
     * Finds the Projects that match given name. Empty list will be returned if none is found.
     *
     *
     * @param name
     *            name of the Projects to search for, can't be null or empty
     * @return a list of Project objects that match given name, empty list if none is found
     * @throws IllegalArgumentException
     *             if the name is null or empty string
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> searchProjectsByName(String name) throws ProjectManagerException;

    /**
     * Finds the projects with specified filter. Empty list will be returned if none is found
     *
     *
     * @param filter
     *            the filter used to search for Projects, can't be null
     *
     * @return a list of Project objects that match the filter, empty list if none is found
     *
     * @throws IllegalArgumentException
     *             if the filter is null
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> searchProjects(Filter filter) throws ProjectManagerException;

    /**
     * Gets the ProjectStatus with given ID.
     *
     *
     * @param id
     *            ID of the ProjectStatus to retrieve, can't be negative
     * @return a ProjectStatus matching given ID, or null if none exists with given ID
     *
     * @throws IllegalArgumentException
     *             if id is negative
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public ProjectStatus getProjectStatus(long id) throws ProjectManagerException;

    /**
     * Gets all the project status. If none exists, empty list will be returned.
     *
     * @return a list of ProjectStatus objects, empty list if none exists
     *
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<ProjectStatus> getAllProjectStatuses() throws ProjectManagerException;

    /**
     * Gets all Projects that match given project status. Empty list will be returned if none if found.
     *
     * @param status
     *            a ProjectStatus, can't be null, id should not be negative
     * @return a list Project objects, empty list if none is found.
     *
     * @throws IllegalArgumentException
     *             if argument is invalid (status is null or status's id is negative
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Project> getProjectsForStatus(ProjectStatus status) throws ProjectManagerException;

    /**
     * Create a new ProjectStatus, a new id will be generated for it.
     *
     * @param status
     *            the project status to update, can not be null ( should have non-null and non-empty name, non-null and
     *            non-empty description and isDeleted false)
     *
     * @return the created ProjectStatus with new ID assigned.
     * @throws IllegalArgumentException
     *             if argument is invalid
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer, or in ID generator
     *
     */
    public ProjectStatus createProjectStatus(ProjectStatus status) throws ProjectManagerException;

    /**
     * Updates a ProjectStatus. Error will be thrown if the project status to update does not exist in persistence.
     *
     * @param status
     *            the project status to update, can not be null ( should have non-null and non-empty name, non-null and
     *            non-empty description and isDeleted false)
     * @return the update ProjectStatus instance
     * @throws IllegalArgumentException
     *             if the argument is invalid
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer,
     * @throws ProjectEntityNotFoundException
     *             if the ProjectStatus to be updated does not exist in persistence
     */
    public ProjectStatus updateProjectStatus(ProjectStatus status) throws ProjectManagerException,
            ProjectEntityNotFoundException;

    /**
     * Deletes given ProjectStatus.
     *
     * @param status
     *            the project status to delete, can't be null, isDeleted should be false
     *
     * @return the deleted project status, with isDeleted set to true
     * @throws IllegalArgumentException
     *             if project status is null or isDeleted is true
     * @throws ProjectManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     * @throws ProjectEntityNotFoundException
     *             if the ProjectStatus to be deleted does not exist in persistence
     */
    public ProjectStatus deleteProjectStatus(ProjectStatus status) throws ProjectManagerException,
            ProjectEntityNotFoundException;
}
