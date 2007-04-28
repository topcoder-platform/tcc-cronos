/*
 * TimeTrackerProjectPersistence.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project.persistence;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectWorker;

import java.util.List;


/**
 * <p>
 * This interface defines the contract for accessing the data associated with time tracker project. Client can choose
 * between alternative implementations to suit persistence migration. Currently, this interface defines all necessary
 * methods to interact with the database. They are aimed to an efficient database implementation (using INSERT,
 * SELECT, UPDATE and DELETE statements) but other storage technologies can be used just as well (such as XML).
 * </p>
 * 
 * <p>
 * Concrete implementations of this interface should provide two constructors:
 * 
 * <ul>
 * <li>
 * a constructor that accepts a String argument specifying the namespace of the DB Connection Factory configuration
 * file.
 * </li>
 * <li>
 * a constructor that accepts two String arguments specifying the namespace of the DB Connection Factory configuration
 * file, and the connection producer name.
 * </li>
 * </ul>
 * </p>
 *
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public interface TimeTrackerProjectPersistence {
    /**
     * <p>
     * Adds a new client to the database. If the id of the client is used false will be returned.
     * </p>
     *
     * @param client the client to add
     *
     * @return true if the client was added, false otherwise
     *
     * @throws NullPointerException if the client is null
     * @throws PersistenceException if something wrong happens while adding the client (such as SQL exception)
     */
    boolean addClient(Client client) throws PersistenceException;

    /**
     * <p>
     * Deletes a client from the database. If a client with the specified id does not exist false will be returned.
     * </p>
     *
     * @param clientId the id of the client
     *
     * @return true if the client was removed, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the client (such as SQL exception)
     */
    boolean removeClient(int clientId) throws PersistenceException;

    /**
     * <p>
     * Deletes all clients from the database.
     * </p>
     *
     * @throws PersistenceException if something wrong happens while removing the clients (such as SQL exception)
     */
    void removeAllClients() throws PersistenceException;

    /**
     * <p>
     * Updates a client in the database. If the client does not exist in the database false will be returned.
     * </p>
     *
     * @param client the client to update
     *
     * @return true if the client was updated, false otherwise
     *
     * @throws NullPointerException if the client is null
     * @throws PersistenceException if something wrong happens while updating the client (such as SQL exception)
     */
    boolean updateClient(Client client) throws PersistenceException;

    /**
     * <p>
     * Adds a project to a client. If the project already has a client assigned false will be returned.
     * </p>
     *
     * @param clientId the id of the client
     * @param project the project to add to the client
     * @param user the user who assigned the client for the project
     *
     * @return true if the project was added to the client, false otherwise
     *
     * @throws NullPointerException if the project or user is null
     * @throws IllegalArgumentException if the user is the empty string
     * @throws PersistenceException if something wrong happens while adding the project to the client (such as SQL
     *         exception)
     */
    boolean addProjectToClient(int clientId, Project project, String user)
        throws PersistenceException;

    /**
     * <p>
     * Retrieves the project with the specified clientId and projectId from the database. If there is no such project
     * associated with the client, null will be returned.
     * </p>
     * 
     * <p>
     * The fields of the Project object will be properly populated.
     * </p>
     *
     * @param clientId the id of the client
     * @param projectId the id of the project
     *
     * @return the project of the client with the specified clientId and projectId
     *
     * @throws PersistenceException if something wrong happens while getting the project of the client (such as SQL
     *         exception)
     */
    Project getClientProject(int clientId, int projectId)
        throws PersistenceException;

    /**
     * <p>
     * Retrieves all the projects of the client from the database. If there are no projects associated with the client,
     * an empty list will be returned.
     * </p>
     * 
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @param clientId the id of the client
     *
     * @return a list containing all the projects of the client
     *
     * @throws PersistenceException if something wrong happens while getting the projects of the client (such as SQL
     *         exception)
     */
    List getAllClientProjects(int clientId) throws PersistenceException;

    /**
     * <p>
     * Retrieves the client with the specified clientId from the database. If the client does not exist, null will be
     * returned.
     * </p>
     * 
     * <p>
     * The fields of the Client object will be properly populated.
     * </p>
     *
     * @param clientId the id of the client
     *
     * @return the client with the given id
     *
     * @throws PersistenceException if something wrong happens while getting the client (such as SQL exception)
     */
    Client getClient(int clientId) throws PersistenceException;

    /**
     * <p>
     * Retrieves all the clients from the database. If there are no clients in the database, an empty list will be
     * returned.
     * </p>
     * 
     * <p>
     * The fields of the Client objects will be properly populated.
     * </p>
     *
     * @return a list containing all the clients
     *
     * @throws PersistenceException if something wrong happens while getting the clients (such as SQL exception)
     */
    List getAllClients() throws PersistenceException;

    /**
     * <p>
     * Adds a new project to the persistence. If the id of the project is used false will be returned.
     * </p>
     *
     * @param project the project to add
     *
     * @return true if the project was added, false otherwise
     *
     * @throws NullPointerException if the project is null
     * @throws PersistenceException if something wrong happens while adding the project (such as SQL exception)
     */
    boolean addProject(Project project) throws PersistenceException;

    /**
     * <p>
     * Updates an project in the database. If the project does not exist in the database false will be returned.
     * </p>
     *
     * @param project the project to update
     *
     * @return true if the project was updated, false otherwise
     *
     * @throws NullPointerException if the project is null
     * @throws PersistenceException if something wrong happens while updating the project (such as SQL exception)
     */
    boolean updateProject(Project project) throws PersistenceException;

    /**
     * <p>
     * Deletes a project from the database. If a project with the specified id does not exist false will be returned.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return true if the project was removed, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the project (such as SQL exception)
     */
    boolean removeProject(int projectId) throws PersistenceException;

    /**
     * <p>
     * Deletes all projects from the database.
     * </p>
     *
     * @throws PersistenceException if something wrong happens while removing the projects (such as SQL exception)
     */
    void removeAllProjects() throws PersistenceException;

    /**
     * <p>
     * Assigns a client to an existing project. If the project already has a client assigned false will be returned.
     * </p>
     *
     * @param projectId the id of the project
     * @param clientId the id of the client
     * @param user the user who assigned the client for the project
     *
     * @return true if the client was assigned to the project, false otherwise
     *
     * @throws NullPointerException if the user is null
     * @throws IllegalArgumentException if the user is the empty string
     * @throws PersistenceException if something wrong happens while assigning the client (such as SQL exception)
     */
    boolean assignClient(int projectId, int clientId, String user)
        throws PersistenceException;

    /**
     * <p>
     * Retrieves the client of a project. If the project has no client assigned, null will be returned.
     * </p>
     * 
     * <p>
     * The fields of the Client object will be properly populated.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return the client of the project
     *
     * @throws PersistenceException if something wrong happens while getting the client of the project (such as SQL
     *         exception)
     */
    Client getProjectClient(int projectId) throws PersistenceException;

    /**
     * <p>
     * Assigns a project manager to an existing project. If the project already has a manager assigned false will be
     * returned.
     * </p>
     *
     * @param projectManager the project manager to assign
     *
     * @return true if the manager was assigned, false otherwise
     *
     * @throws NullPointerException if the projectManager is null
     * @throws PersistenceException if something wrong happens while assigning the project manager (such as SQL
     *         exception)
     */
    boolean assignProjectManager(ProjectManager projectManager)
        throws PersistenceException;

    /**
     * <p>
     * Retrieves the project manager of a project. If there is no project manager associated with the project, null
     * will be returned.
     * </p>
     * 
     * <p>
     * The fields of the ProjectManager object will be properly populated.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return the project manager of the project
     *
     * @throws PersistenceException if something wrong happens while getting the project manager (such as SQL
     *         exception)
     */
    ProjectManager getProjectManager(int projectId) throws PersistenceException;

    /**
     * <p>
     * Deletes a project manager from the database. This means that this manager will no longer be the project manager
     * of this project. If the project does not have the manager assigned, false will be returned.
     * </p>
     *
     * @param managerId the id of the manager
     * @param projectId the id of the project
     *
     * @return true if the manager was removed from the project, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the project manager (such as SQL
     *         exception)
     */
    boolean removeProjectManager(int managerId, int projectId)
        throws PersistenceException;

    /**
     * <p>
     * Assigns a project worker to an existing project. If the project already has this worker assigned false will be
     * returned.
     * </p>
     *
     * @param projectWorker the project worker to assign
     *
     * @return true if the worker was assigned to the project, false otherwise
     *
     * @throws NullPointerException if the projectWorker is null
     * @throws PersistenceException if something wrong happens while assigning the project worker (such as SQL
     *         exception)
     */
    boolean addWorker(ProjectWorker projectWorker) throws PersistenceException;

    /**
     * <p>
     * Updates a worker in the database. If the worker was not assigned to the project false will be returned.
     * </p>
     *
     * @param projectWorker the worker to update
     *
     * @return true if the worker was updated, false otherwise
     *
     * @throws NullPointerException if the projectWorker is null
     * @throws PersistenceException if something wrong happens while updating the project worker (such as SQL
     *         exception)
     */
    boolean updateWorker(ProjectWorker projectWorker) throws PersistenceException;

    /**
     * <p>
     * Deletes a project worker from the database. This means that this worker will no longer be a worker for this
     * project. If the project does not have the worker assigned, false will be returned.
     * </p>
     *
     * @param workerId the id of the worker
     * @param projectId the id of the project
     *
     * @return true if the worker was removed from the project, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the project worker (such as SQL
     *         exception)
     */
    boolean removeWorker(int workerId, int projectId)
        throws PersistenceException;

    /**
     * <p>
     * Deletes all the project workers from the database. This means that the project will have no workers. If the
     * project does not have any worker assigned, false will be returned.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return true if the workers were removed from the project, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the project workers (such as SQL
     *         exception)
     */
    boolean removeWorkers(int projectId) throws PersistenceException;

    /**
     * <p>
     * Retrieves a worker of a project. If the project does not have this worker assigned null will be returned.
     * </p>
     * 
     * <p>
     * The fields of the ProjectWorker object will be properly populated.
     * </p>
     *
     * @param workerId the id of the worker
     * @param projectId the id of the project
     *
     * @return the ProjectWorker of a project
     *
     * @throws PersistenceException if something wrong happens while getting the project worker (such as SQL exception)
     */
    ProjectWorker getWorker(int workerId, int projectId)
        throws PersistenceException;

    /**
     * <p>
     * Retrieves all the workers of a project. If there are no workers associated with a project, an empty list will be
     * returned.
     * </p>
     * 
     * <p>
     * The fields of the ProjectWorker objects will be properly populated.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return a list containing all the ProjectWorkers of a project
     *
     * @throws PersistenceException if something wrong happens while getting the project workers (such as SQL
     *         exception)
     */
    List getWorkers(int projectId) throws PersistenceException;

    /**
     * <p>
     * Adds a time entry to an existing project. If the project already has this time entry assigned false will be
     * returned.
     * </p>
     *
     * @param entryId the id of the time entry
     * @param projectId the id of the project
     * @param user the user who created the time entry for the project
     *
     * @return true if the time entry was added, false otherwise
     *
     * @throws NullPointerException if the user is null
     * @throws IllegalArgumentException if the user is the empty string
     * @throws PersistenceException if something wrong happens while adding the time entry (such as SQL exception)
     */
    boolean addTimeEntry(int entryId, int projectId, String user)
        throws PersistenceException;

    /**
     * <p>
     * Deletes a time entry of a project from the database. If the project does not have this time entry assigned false
     * will be returned.
     * </p>
     *
     * @param entryId the id of the time entry
     * @param projectId the id of the project
     *
     * @return true if the entry was removed, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the time entry (such as SQL exception)
     */
    boolean removeTimeEntry(int entryId, int projectId)
        throws PersistenceException;

    /**
     * <p>
     * Retrieves all the time entries of a project. If there are no time entries associated with a project, an empty
     * list will be returned.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return a list containing the ids of the time entries
     *
     * @throws PersistenceException if something wrong happens while getting the time entries (such as SQL exception)
     */
    List getTimeEntries(int projectId) throws PersistenceException;

    /**
     * <p>
     * Adds an expense entry to an existing project. If the project already has this expense entry assigned false will
     * be returned.
     * </p>
     *
     * @param entryId the id of the expense entry
     * @param projectId the id of the project
     * @param user the user who created the expense entry for the project
     *
     * @return true if the expense entry was added, false otherwise
     *
     * @throws NullPointerException if the user is null
     * @throws IllegalArgumentException if the user is the empty string
     * @throws PersistenceException if something wrong happens while adding the expense entry (such as SQL exception)
     */
    boolean addExpenseEntry(int entryId, int projectId, String user)
        throws PersistenceException;

    /**
     * <p>
     * Deletes an expense entry of a project from the database. If the project does not have this expense entry
     * assigned false will be returned.
     * </p>
     *
     * @param entryId the id of the expense entry
     * @param projectId the id of the project
     *
     * @return true if the entry was removed, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the expense entry (such as SQL exception)
     */
    boolean removeExpenseEntry(int entryId, int projectId)
        throws PersistenceException;

    /**
     * <p>
     * Retrieves all the expense entries of a project. If there are no expense entries associated with a project, an
     * empty list will be returned.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return a list containing the ids of the expense entries
     *
     * @throws PersistenceException if something wrong happens while getting the expense entries (such as SQL
     *         exception)
     */
    List getExpenseEntries(int projectId) throws PersistenceException;

    /**
     * <p>
     * Retrieves a project from the persistence. If the project does not exist, null will be returned.
     * </p>
     * 
     * <p>
     * The fields of the Project object will be properly populated.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return the project with the given id
     *
     * @throws PersistenceException if something wrong happens while getting the project (such as SQL exception)
     */
    Project getProject(int projectId) throws PersistenceException;

    /**
     * <p>
     * Retrieves all the projects from the database. If there are no projects in the database, an empty list will be
     * returned.
     * </p>
     * 
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @return a list containing all the projects
     *
     * @throws PersistenceException if something wrong happens while getting the projects (such as SQL exception)
     */
    List getAllProjects() throws PersistenceException;

    /**
     * <p>
     * Removes a project from a client. If the client was not assigned the project, false will be returned.
     * </p>
     *
     * @param clientId the id of the client
     * @param projectId the id of the project
     *
     * @return true if the project was removed from client, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the project from client (such as SQL
     *         exception)
     */
    boolean removeProjectFromClient(int clientId, int projectId)
        throws PersistenceException;

    /**
     * <p>
     * Closes the connection to the database. After this method is called, the instance should be discarded.
     * </p>
     *
     * @throws PersistenceException if something wrong happens while closing the connection (such as SQL exception)
     */
    void closeConnection() throws PersistenceException;
}
