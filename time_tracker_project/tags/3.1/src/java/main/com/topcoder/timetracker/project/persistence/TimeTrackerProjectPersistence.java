/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.persistence;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.searchfilters.Filter;

import java.util.List;

/**
 * <p>
 * This interface defines the contract for accessing the data associated with
 * time tracker project. Client can choose between alternative implementations
 * to suit persistence migration. Currently, this interface defines all
 * necessary methods to interact with the database. They are aimed to an
 * efficient database implementation (using INSERT, SELECT, UPDATE and DELETE
 * statements) but other storage technologies can be used just as well (such as
 * XML).
 * </p>
 *
 * <p>
 * Concrete implementations of this interface should provide three constructors:
 *
 * <ul>
 * <li> a constructor that accepts a String argument specifying the namespace of
 * the DB Connection Factory configuration file. </li>
 * <li> a constructor that accepts two String arguments specifying the namespace
 * of the DB Connection Factory configuration file, and the connection producer
 * name. </li>
 * <li> a constructor that accepts four String arguments specifying the
 * namespace of the DB Connection Factory configuration file, the connection
 * producer name, project search utility namespace and client search utility
 * namespace. </li>
 * </ul>
 * </p>
 *
 * <p>
 * Since version 1.1 it supports batch processing of the client/project CRUD
 * operations, and client/project search functionality. The atomicity can be
 * specified for those batch operations. If it is true, the successful
 * operations are rolled back when one operation in a batch fails, and a
 * BatchOperationException will be thrown. If it is false, the operation
 * continues for every element before throwing the BatchOperationException.
 * </p>
 *
 *
 * Version 2.0 added posibility to obtain companyId for a specific id for time
 * tracker entities: (for client, project, user_account, time entry and expense
 * entry)
 *
 * @author DanLazar, colau, costty000
 * @author real_vg, TCSDEVELOPER
 * @version 2.0
 *
 * @since 1.0
 */
public interface TimeTrackerProjectPersistence {
    /**
     * <p>
     * Adds a new client to the database. If the id of the client is used false
     * will be returned.
     * </p>
     *
     * @param client
     *            the client to add
     *
     * @return true if the client was added, false otherwise
     *
     * @throws NullPointerException
     *             if the client is null
     * @throws PersistenceException
     *             if something wrong happens while adding the client (such as
     *             SQL exception)
     */
    boolean addClient(Client client) throws PersistenceException;

    /**
     * <p>
     * Deletes a client from the database. If a client with the specified id
     * does not exist false will be returned.
     * </p>
     *
     * @param clientId
     *            the id of the client
     *
     * @return true if the client was removed, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the client (such as
     *             SQL exception)
     */
    boolean removeClient(int clientId) throws PersistenceException;

    /**
     * <p>
     * Deletes all clients from the database.
     * </p>
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the clients (such
     *             as SQL exception)
     */
    void removeAllClients() throws PersistenceException;

    /**
     * <p>
     * Updates a client in the database. If the client does not exist in the
     * database false will be returned.
     * </p>
     *
     * @param client
     *            the client to update
     *
     * @return true if the client was updated, false otherwise
     *
     * @throws NullPointerException
     *             if the client is null
     * @throws PersistenceException
     *             if something wrong happens while updating the client (such as
     *             SQL exception)
     */
    boolean updateClient(Client client) throws PersistenceException;

    /**
     * <p>
     * Adds a project to a client. If the project already has a client assigned
     * false will be returned.
     * </p>
     *
     * @param clientId
     *            the id of the client
     * @param project
     *            the project to add to the client
     * @param user
     *            the user who assigned the client for the project
     *
     * @return true if the project was added to the client, false otherwise
     *
     * @throws NullPointerException
     *             if the project or user is null
     * @throws IllegalArgumentException
     *             if the user is the empty string
     * @throws PersistenceException
     *             if something wrong happens while adding the project to the
     *             client (such as SQL exception)
     */
    boolean addProjectToClient(int clientId, Project project, String user)
            throws PersistenceException;

    /**
     * <p>
     * Retrieves the project with the specified clientId and projectId from the
     * database. If there is no such project associated with the client, null
     * will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project object will be properly populated.
     * </p>
     *
     * @param clientId
     *            the id of the client
     * @param projectId
     *            the id of the project
     *
     * @return the project of the client with the specified clientId and
     *         projectId
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project of the
     *             client (such as SQL exception)
     */
    Project getClientProject(int clientId, int projectId)
            throws PersistenceException;

    /**
     * <p>
     * Retrieves all the projects of the client from the database. If there are
     * no projects associated with the client, an empty list will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @param clientId
     *            the id of the client
     *
     * @return a list containing all the projects of the client
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the projects of the
     *             client (such as SQL exception)
     */
    List getAllClientProjects(int clientId) throws PersistenceException;

    /**
     * <p>
     * Retrieves the client with the specified clientId from the database. If
     * the client does not exist, null will be returned.
     * </p>
     *
     * <p>
     * The fields of the Client object will be properly populated.
     * </p>
     *
     * @param clientId
     *            the id of the client
     *
     * @return the client with the given id
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the client (such as
     *             SQL exception)
     */
    Client getClient(int clientId) throws PersistenceException;

    /**
     * <p>
     * Retrieves all the clients from the database. If there are no clients in
     * the database, an empty list will be returned.
     * </p>
     *
     * <p>
     * The fields of the Client objects will be properly populated.
     * </p>
     *
     * @return a list containing all the clients
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the clients (such as
     *             SQL exception)
     */
    List getAllClients() throws PersistenceException;

    /**
     * <p>
     * Adds a new project to the persistence. If the id of the project is used
     * false will be returned.
     * </p>
     *
     * @param project
     *            the project to add
     *
     * @return true if the project was added, false otherwise
     *
     * @throws NullPointerException
     *             if the project is null
     * @throws PersistenceException
     *             if something wrong happens while adding the project (such as
     *             SQL exception)
     */
    boolean addProject(Project project) throws PersistenceException;

    /**
     * <p>
     * Updates an project in the database. If the project does not exist in the
     * database false will be returned.
     * </p>
     *
     * @param project
     *            the project to update
     *
     * @return true if the project was updated, false otherwise
     *
     * @throws NullPointerException
     *             if the project is null
     * @throws PersistenceException
     *             if something wrong happens while updating the project (such
     *             as SQL exception)
     */
    boolean updateProject(Project project) throws PersistenceException;

    /**
     * <p>
     * Deletes a project from the database. If a project with the specified id
     * does not exist false will be returned.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return true if the project was removed, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project (such
     *             as SQL exception)
     */
    boolean removeProject(int projectId) throws PersistenceException;

    /**
     * <p>
     * Deletes all projects from the database.
     * </p>
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the projects (such
     *             as SQL exception)
     */
    void removeAllProjects() throws PersistenceException;

    /**
     * <p>
     * Assigns a client to an existing project. If the project already has a
     * client assigned false will be returned.
     * </p>
     *
     * @param projectId
     *            the id of the project
     * @param clientId
     *            the id of the client
     * @param user
     *            the user who assigned the client for the project
     *
     * @return true if the client was assigned to the project, false otherwise
     *
     * @throws NullPointerException
     *             if the user is null
     * @throws IllegalArgumentException
     *             if the user is the empty string
     * @throws PersistenceException
     *             if something wrong happens while assigning the client (such
     *             as SQL exception)
     */
    boolean assignClient(int projectId, int clientId, String user)
            throws PersistenceException;

    /**
     * <p>
     * Retrieves the client of a project. If the project has no client assigned,
     * null will be returned.
     * </p>
     *
     * <p>
     * The fields of the Client object will be properly populated.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return the client of the project
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the client of the
     *             project (such as SQL exception)
     */
    Client getProjectClient(int projectId) throws PersistenceException;

    /**
     * <p>
     * Assigns a project manager to an existing project. If the project already
     * has a manager assigned false will be returned.
     * </p>
     *
     * @param projectManager
     *            the project manager to assign
     *
     * @return true if the manager was assigned, false otherwise
     *
     * @throws NullPointerException
     *             if the projectManager is null
     * @throws PersistenceException
     *             if something wrong happens while assigning the project
     *             manager (such as SQL exception)
     */
    boolean assignProjectManager(ProjectManager projectManager)
            throws PersistenceException;

    /**
     * <p>
     * Retrieves the project manager of a project. If there is no project
     * manager associated with the project, null will be returned.
     * </p>
     *
     * <p>
     * The fields of the ProjectManager object will be properly populated.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return the project manager of the project
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project manager
     *             (such as SQL exception)
     */
    ProjectManager getProjectManager(int projectId) throws PersistenceException;

    /**
     * <p>
     * Deletes a project manager from the database. This means that this manager
     * will no longer be the project manager of this project. If the project
     * does not have the manager assigned, false will be returned.
     * </p>
     *
     * @param managerId
     *            the id of the manager
     * @param projectId
     *            the id of the project
     *
     * @return true if the manager was removed from the project, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project manager
     *             (such as SQL exception)
     */
    boolean removeProjectManager(int managerId, int projectId)
            throws PersistenceException;

    /**
     * <p>
     * Assigns a project worker to an existing project. If the project already
     * has this worker assigned false will be returned.
     * </p>
     *
     * @param projectWorker
     *            the project worker to assign
     *
     * @return true if the worker was assigned to the project, false otherwise
     *
     * @throws NullPointerException
     *             if the projectWorker is null
     * @throws PersistenceException
     *             if something wrong happens while assigning the project worker
     *             (such as SQL exception)
     */
    boolean addWorker(ProjectWorker projectWorker) throws PersistenceException;

    /**
     * <p>
     * Updates a worker in the database. If the worker was not assigned to the
     * project false will be returned.
     * </p>
     *
     * @param projectWorker
     *            the worker to update
     *
     * @return true if the worker was updated, false otherwise
     *
     * @throws NullPointerException
     *             if the projectWorker is null
     * @throws PersistenceException
     *             if something wrong happens while updating the project worker
     *             (such as SQL exception)
     */
    boolean updateWorker(ProjectWorker projectWorker)
            throws PersistenceException;

    /**
     * <p>
     * Deletes a project worker from the database. This means that this worker
     * will no longer be a worker for this project. If the project does not have
     * the worker assigned, false will be returned.
     * </p>
     *
     * @param workerId
     *            the id of the worker
     * @param projectId
     *            the id of the project
     *
     * @return true if the worker was removed from the project, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project worker
     *             (such as SQL exception)
     */
    boolean removeWorker(int workerId, int projectId)
            throws PersistenceException;

    /**
     * <p>
     * Deletes all the project workers from the database. This means that the
     * project will have no workers. If the project does not have any worker
     * assigned, false will be returned.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return true if the workers were removed from the project, false
     *         otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project workers
     *             (such as SQL exception)
     */
    boolean removeWorkers(int projectId) throws PersistenceException;

    /**
     * <p>
     * Retrieves a worker of a project. If the project does not have this worker
     * assigned null will be returned.
     * </p>
     *
     * <p>
     * The fields of the ProjectWorker object will be properly populated.
     * </p>
     *
     * @param workerId
     *            the id of the worker
     * @param projectId
     *            the id of the project
     *
     * @return the ProjectWorker of a project
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project worker
     *             (such as SQL exception)
     */
    ProjectWorker getWorker(int workerId, int projectId)
            throws PersistenceException;

    /**
     * <p>
     * Retrieves all the workers of a project. If there are no workers
     * associated with a project, an empty list will be returned.
     * </p>
     *
     * <p>
     * The fields of the ProjectWorker objects will be properly populated.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return a list containing all the ProjectWorkers of a project
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project workers
     *             (such as SQL exception)
     */
    List getWorkers(int projectId) throws PersistenceException;

    /**
     * <p>
     * Adds a time entry to an existing project. If the project already has this
     * time entry assigned false will be returned.
     * </p>
     *
     * @param entryId
     *            the id of the time entry
     * @param projectId
     *            the id of the project
     * @param user
     *            the user who created the time entry for the project
     *
     * @return true if the time entry was added, false otherwise
     *
     * @throws NullPointerException
     *             if the user is null
     * @throws IllegalArgumentException
     *             if the user is the empty string
     * @throws PersistenceException
     *             if something wrong happens while adding the time entry (such
     *             as SQL exception)
     */
    boolean addTimeEntry(int entryId, int projectId, String user)
            throws PersistenceException;

    /**
     * <p>
     * Deletes a time entry of a project from the database. If the project does
     * not have this time entry assigned false will be returned.
     * </p>
     *
     * @param entryId
     *            the id of the time entry
     * @param projectId
     *            the id of the project
     *
     * @return true if the entry was removed, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the time entry
     *             (such as SQL exception)
     */
    boolean removeTimeEntry(int entryId, int projectId)
            throws PersistenceException;

    /**
     * <p>
     * Retrieves all the time entries of a project. If there are no time entries
     * associated with a project, an empty list will be returned.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return a list containing the ids of the time entries
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the time entries
     *             (such as SQL exception)
     */
    List getTimeEntries(int projectId) throws PersistenceException;

    /**
     * <p>
     * Adds an expense entry to an existing project. If the project already has
     * this expense entry assigned false will be returned.
     * </p>
     *
     * @param entryId
     *            the id of the expense entry
     * @param projectId
     *            the id of the project
     * @param user
     *            the user who created the expense entry for the project
     *
     * @return true if the expense entry was added, false otherwise
     *
     * @throws NullPointerException
     *             if the user is null
     * @throws IllegalArgumentException
     *             if the user is the empty string
     * @throws PersistenceException
     *             if something wrong happens while adding the expense entry
     *             (such as SQL exception)
     */
    boolean addExpenseEntry(int entryId, int projectId, String user)
            throws PersistenceException;

    /**
     * <p>
     * Deletes an expense entry of a project from the database. If the project
     * does not have this expense entry assigned false will be returned.
     * </p>
     *
     * @param entryId
     *            the id of the expense entry
     * @param projectId
     *            the id of the project
     *
     * @return true if the entry was removed, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the expense entry
     *             (such as SQL exception)
     */
    boolean removeExpenseEntry(int entryId, int projectId)
            throws PersistenceException;

    /**
     * <p>
     * Retrieves all the expense entries of a project. If there are no expense
     * entries associated with a project, an empty list will be returned.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return a list containing the ids of the expense entries
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the expense entries
     *             (such as SQL exception)
     */
    List getExpenseEntries(int projectId) throws PersistenceException;

    /**
     * <p>
     * Retrieves a project from the persistence. If the project does not exist,
     * null will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project object will be properly populated.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return the project with the given id
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project (such as
     *             SQL exception)
     */
    Project getProject(int projectId) throws PersistenceException;

    /**
     * <p>
     * Retrieves all the projects from the database. If there are no projects in
     * the database, an empty list will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @return a list containing all the projects
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the projects (such
     *             as SQL exception)
     */
    List getAllProjects() throws PersistenceException;

    /**
     * <p>
     * Removes a project from a client. If the client was not assigned the
     * project, false will be returned.
     * </p>
     *
     * @param clientId
     *            the id of the client
     * @param projectId
     *            the id of the project
     *
     * @return true if the project was removed from client, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project from
     *             client (such as SQL exception)
     */
    boolean removeProjectFromClient(int clientId, int projectId)
            throws PersistenceException;

    /**
     * <p>
     * Closes the connection to the database. After this method is called, the
     * instance should be discarded.
     * </p>
     *
     * @throws PersistenceException
     *             if something wrong happens while closing the connection (such
     *             as SQL exception)
     */
    void closeConnection() throws PersistenceException;

    /**
     * <p>
     * Searches the projects from the database which matches the given search
     * filter. If there are no such projects in the database, an empty array
     * will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @param searchFilter
     *            the search filter to match.
     *
     * @return the array of matching projects.
     *
     * @throws IllegalArgumentException
     *             if the searchFilter is null, or the searchFilter is not
     *             supported, or no column name is found for the column alias.
     * @throws PersistenceException
     *             if something wrong happens while searching the projects (such
     *             as SQL exception)
     *
     * @since 1.1
     */
    Project[] searchForProjects(Filter searchFilter)
            throws PersistenceException;

    /**
     * <p>
     * Searches the clients from the database which matches the given search
     * filter. If there are no such clients in the database, an empty array will
     * be returned.
     * </p>
     *
     * <p>
     * The fields of the Client objects will be properly populated.
     * </p>
     *
     * @param searchFilter
     *            the search filter to match.
     *
     * @return the array of matching clients.
     *
     * @throws IllegalArgumentException
     *             if the searchFilter is null, or the searchFilter is not
     *             supported, or no column name is found for the column alias.
     * @throws PersistenceException
     *             if something wrong happens while searching the clients (such
     *             as SQL exception)
     *
     * @since 1.1
     */
    Client[] searchForClients(Filter searchFilter) throws PersistenceException;

    /**
     * <p>
     * Adds the clients to the database. If the id of any client is used, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param clients
     *            the clients to add.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the clients is null or empty, or any of its elements is
     *             null.
     * @throws BatchOperationException
     *             if something wrong happens while adding the clients in a
     *             batch.
     *
     * @see #addClient(Client)
     * @since 1.1
     */
    void addClients(Client[] clients, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Deletes the clients from the database. If a client with any specified id
     * does not exist, a BatchOperationException will be thrown.
     * </p>
     *
     * @param clientIds
     *            the ids of clients to delete.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the clientIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while deleting the clients in a
     *             batch.
     *
     * @see #removeClient(int)
     * @since 1.1
     */
    void removeClients(int[] clientIds, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Updates the clients in the database. If any client does not exist in the
     * database, a BatchOperationException will be thrown.
     * </p>
     *
     * @param clients
     *            the clients to update.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the clients is null or empty, or any of its elements is
     *             null.
     * @throws BatchOperationException
     *             if something wrong happens while updating the clients in a
     *             batch.
     *
     * @see #updateClient(Client)
     * @since 1.1
     */
    void updateClients(Client[] clients, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Retrieves the clients with the specified clientIds from the database. If
     * any client does not exist, a BatchOperationException will be thrown if it
     * fails to retrieve at least one client (in atomic mode), or if it fails to
     * retrieve all clients (in non-atomic mode). When the operation is not
     * atomic, and at least one client was successfully retrieved, the returned
     * array will contain nulls in the places where the clients cannot be
     * retrieved.
     * </p>
     *
     * <p>
     * The fields of the Client objects will be properly populated.
     * </p>
     *
     * @param clientIds
     *            the ids of the clients to retrieve.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @return the clients with the given ids.
     *
     * @throws IllegalArgumentException
     *             if the clientIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while retrieving the clients in a
     *             batch.
     *
     * @see #getClient(int)
     * @since 1.1
     */
    Client[] getClients(int[] clientIds, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Adds the projects to the persistence. If the id of any project is used, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param projects
     *            the projects to add.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the projects is null or empty, or any of its elements is
     *             null.
     * @throws BatchOperationException
     *             if something wrong happens while adding the projects in a
     *             batch.
     *
     * @see #addProject(Project)
     * @since 1.1
     */
    void addProjects(Project[] projects, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Deletes the projects from the database. If a project with any specified
     * id does not exist, a BatchOperationException will be thrown.
     * </p>
     *
     * @param projectIds
     *            the ids of the projects to delete.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the projectIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while deleting the projects in a
     *             batch.
     *
     * @see #removeProject(int)
     * @since 1.1
     */
    void removeProjects(int[] projectIds, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Updates the projects in the database. If any project does not exist in
     * the database, a BatchOperationException will be thrown.
     * </p>
     *
     * @param projects
     *            the projects to update.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the projects is null or empty, or any of its elements is
     *             null.
     * @throws BatchOperationException
     *             if something wrong happens while updating the projects in a
     *             batch.
     *
     * @see #updateProject(Project)
     * @since 1.1
     */
    void updateProjects(Project[] projects, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Retrieves the projects from the persistence. If any project does not
     * exist, a BatchOperationException will be thrown if it fails to retrieve
     * at least one project (in atomic mode), or if it fails to retrieve all
     * projects (in non-atomic mode). When the operation is not atomic, and at
     * least one project was successfully retrieved, the returned array will
     * contain nulls in the places where the projects cannot be retrieved.
     * </p>
     *
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @param projectIds
     *            the ids of the projects to retrieve.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @return the projects with the given ids.
     *
     * @throws IllegalArgumentException
     *             if the projectIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while retrieving the projects in a
     *             batch.
     *
     * @see #getProject(int)
     * @since 1.1
     */
    Project[] getProjects(int[] projectIds, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Assigns the project workers to the existing projects. If any project
     * already has the worker assigned, a BatchOperationException will be
     * thrown.
     * </p>
     *
     * @param workers
     *            the project workers to assign.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the projectWorkers is null or empty, or any of its
     *             elements is null.
     * @throws BatchOperationException
     *             if something wrong happens while assigning the project
     *             workers in a batch.
     *
     * @see #addWorker(ProjectWorker)
     * @since 1.1
     */
    void addWorkers(ProjectWorker[] workers, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Deletes the project workers from the database. This means that these
     * workers will no longer be a worker for this project. If the project does
     * not have any of these workers assigned, a BatchOperationException will be
     * thrown.
     * </p>
     *
     * @param workerIds
     *            the ids of the workers to delete.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the workerIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while deleting the project workers
     *             in a batch.
     *
     * @see #removeWorker(int, int)
     * @since 1.1
     */
    void removeWorkers(int[] workerIds, int projectId, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Updates the workers in the database. If the workers were not assigned to
     * the projects, a BatchOperationException will be thrown.
     * </p>
     *
     * @param workers
     *            the workers to update.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the projectWorkers is null or empty, or any of its
     *             elements is null.
     * @throws BatchOperationException
     *             if something wrong happens while updating the project workers
     *             in a batch.
     *
     * @see #updateWorker(ProjectWorker)
     * @since 1.1
     */
    void updateWorkers(ProjectWorker[] workers, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Retrieves the workers of a project. If the project does not have these
     * workers assigned, a BatchOperationException will be thrown if it fails to
     * retrieve at least one worker (in atomic mode), or if it fails to retrieve
     * all workers (in non-atomic mode). When the operation is not atomic, and
     * at least one worker was successfully retrieved, the returned array will
     * contain nulls in the places where the workers cannot be retrieved.
     * </p>
     *
     * <p>
     * The fields of the ProjectWorker objects will be properly populated.
     * </p>
     *
     * @param workerIds
     *            the ids of the workers to retrieve.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @return the project workers with the given ids.
     *
     * @throws IllegalArgumentException
     *             if the workerIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while retrieving the project
     *             workers in a batch.
     *
     * @see #getWorker(int, int)
     * @since 1.1
     */
    ProjectWorker[] getWorkers(int[] workerIds, int projectId, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Adds the time entries to an existing project. If the project already has
     * any of these time entries assigned, a BatchOperationException will be
     * thrown.
     * </p>
     *
     * @param entryIds
     *            the ids of the time entries to add.
     * @param projectId
     *            the id of the project.
     * @param user
     *            the user who created the time entries for the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the entryIds is null or empty, or the user is null or the
     *             empty string.
     * @throws BatchOperationException
     *             if something wrong happens while adding the time entries in a
     *             batch.
     *
     * @see #addTimeEntry(int, int, String)
     * @since 1.1
     */
    void addTimeEntries(int[] entryIds, int projectId, String user,
            boolean atomic) throws BatchOperationException;

    /**
     * <p>
     * Deletes the time entries of a project from the database. If the project
     * does not have any of these time entries assigned, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param entryIds
     *            the ids of the time entries to delete.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the entryIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while deleting the time entries in
     *             a batch.
     *
     * @see #removeTimeEntry(int, int)
     * @since 1.1
     */
    void removeTimeEntries(int[] entryIds, int projectId, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Adds the expense entries to an existing project. If the project already
     * has any of these expense entries assigned, a BatchOperationException will
     * be thrown.
     * </p>
     *
     * @param entryIds
     *            the ids of the expense entries to add.
     * @param projectId
     *            the id of the project.
     * @param user
     *            the user who created the expense entries for the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the entryIds is null or empty, or the user is null or the
     *             empty string.
     * @throws BatchOperationException
     *             if something wrong happens while adding the expense entries
     *             in a batch.
     *
     * @see #addExpenseEntry(int, int, String)
     * @since 1.1
     */
    void addExpenseEntries(int[] entryIds, int projectId, String user,
            boolean atomic) throws BatchOperationException;

    /**
     * <p>
     * Deletes the expense entries of a project from the database. If the
     * project does not have any of these expense entries assigned, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param entryIds
     *            the ids of the expense entries to delete.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the entryIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while deleting the expense entries
     *             in a batch.
     *
     * @see #removeExpenseEntry(int, int)
     * @since 1.1
     */
    void removeExpenseEntries(int[] entryIds, int projectId, boolean atomic)
            throws BatchOperationException;

    /**
     * <p>
     * Return the CompanyId coresponding to the client given as parameter. Will
     * return the id of the company if it is found a client with the given id or
     * -1 if no client is found for the given clientId If the clientId is -1 the
     * method throws IllegalArgumentException. If any errors happend the method
     * throws PersistenceException.
     * </p>
     *
     * @return the id of the company if it is found a client with the given id
     *         or -1 if no client is found for the given clientId
     * @param clientId
     *            the id of the client
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the clientId received as parameter is -1.
     *
     * @since 2.0
     */
    int getCompanyIdForClient(int clientId) throws PersistenceException;

    /**
     * <p>
     * Return the CompanyId coresponding to the client given as parameter. Will
     * return the id of the company if it is found a project with the given id
     * or -1 if no project is found for the given projectId If the projectId is
     * -1 the method throws IllegalArgumentException. If any errors happend the
     * method throws PersistenceException.
     * </p>
     *
     *
     * @return the id of the company if it is found a project with the given id
     *         or -1 if no project is found for the given projectId
     * @param projectId
     *            the id of the project
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the projectId received as parameter is -1.
     *
     * @since 2.0
     */
    int getCompanyIdForProject(int projectId) throws PersistenceException;

    /**
     * <p>
     * Return the CompanyId coresponding to the timeEntry given as parameter.
     * Will return the id of the company if it is found a timeEntry with the
     * given id or -1 if no timeEntry is found for the given timeEntryId If the
     * timeEntryId is -1 the method throws IllegalArgumentException. If any
     * errors happend the method throws PersistenceException.
     * </p>
     *
     *
     * @return the id of the company if it is found a timeEntry with the given
     *         id or -1 if no timeEntry is found for the given timeEntryId
     * @param timeEntryId
     *            the id of the timeEntry
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the timeEntryId received as parameter is -1.
     *
     * @since 2.0
     */
    int getCompanyIdForTimeEntry(int timeEntryId) throws PersistenceException;

    /**
     * <p>
     * Return the CompanyId coresponding to the expenseEntry given as parameter.
     * Will return the id of the company if it is found a expenseEntry with the
     * given id or -1 if no expenseEntry is found for the given expenseEntryId
     * If the expenseEntryId is -1 the method throws IllegalArgumentException.
     * If any errors happend the method throws PersistenceException.
     * </p>
     *
     *
     * @return the id of the company if it is found a expenseEntry with the
     *         given id or -1 if no expenseEntry is found for the given
     *         expenseEntryId
     * @param expenseEntryId
     *            the id of the expenseEntry
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the expenseEntryId received as parameter is -1.
     *
     * @since 2.0
     */
    int getCompanyIdForExpenseEntry(int expenseEntryId)
            throws PersistenceException;

    /**
     * <p>
     * Return the CompanyId coresponding to the userAccount given as parameter.
     * Will return the id of the company if it is found a userAccount with the
     * given id or -1 if no userAccount is found for the given userAccountId If
     * the userAccountId is -1 the method throws IllegalArgumentException. If
     * any errors happend the method throws PersistenceException.
     * </p>
     *
     *
     * @return the id of the company if it is found a userAccount with the given
     *         id or -1 if no userAccount is found for the given userAccountId
     * @param userAccountId
     *            the id of the userAccount
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the userAccountId received as parameter is -1.
     *
     * @since 2.0
     */
    int getCompanyIdForUserAccount(int userAccountId)
            throws PersistenceException;

    /**
     * <p>
     * Search a client with specific values (name, companyId). Return true if
     * found at least one or false if no client mach the criteria. The isNew
     * parameter specify if, on searching, will pass or not the clientId from
     * the given client.
     * </p>
     *
     *
     * @return true if exist a client with the name given as parameter, assigned
     *         to a company given as parameter
     * @param client
     *            the client that it is checked
     * @param isNew
     *            specify if the client is new (specify if, on searching, will
     *            pass or not the clientId)
     * @throws PersistenceException -
     *             if any error happens
     */
    boolean existClientWithNameForCompany(
            com.topcoder.timetracker.project.Client client, boolean isNew)
            throws PersistenceException;

}
