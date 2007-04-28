/*
 * ProjectUtility.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.persistence.PersistenceException;
import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;

import java.util.List;


/**
 * <p>
 * The ProjectUtility is useful to manage the projects through the persistence provided in the constructor. It can do
 * the following things:
 *
 * <ul>
 * <li>
 * add a project to the Projects table
 * </li>
 * <li>
 * delete a project from the Projects table
 * </li>
 * <li>
 * delete all the clients from the Projects table
 * </li>
 * <li>
 * retrieve a project from the Projects table
 * </li>
 * <li>
 * retrieve all the projects from the Projects table
 * </li>
 * <li>
 * update a project in the Projects table
 * </li>
 * <li>
 * assign a client to a project
 * </li>
 * <li>
 * retrieve the client of a specified project
 * </li>
 * <li>
 * add/remove/update/retrieve workers
 * </li>
 * <li>
 * assign/remove/retrieve project manager
 * </li>
 * <li>
 * add/remove/retrieve time entries
 * </li>
 * <li>
 * add/remove/retrieve expense entries
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * This class performs some check on the arguments before passing them to the underlying persistence and generates ids
 * for clients and projects if they are not specified.
 * </p>
 *
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public class ProjectUtility {
    /**
     * <p>
     * Represents an instance of TimeTrackerProjectPersistenceManager class. It will be used to retrieve the
     * persistence using the getPersistence() method. It will be initialized in the constructor. It cannot be null.
     * </p>
     */
    private ProjectPersistenceManager persistenceManager = null;

    /**
     * <p>
     * Represents the underlying persistence to manage the projects. It will be obtained by the the getPersistence()
     * method of ProjectPersistenceManager. It will be initialized in the constructor. It cannot be null.
     * </p>
     */
    private TimeTrackerProjectPersistence persistence = null;

    /**
     * <p>
     * Creates a new instance of this class with the persistence manager.
     * </p>
     *
     * @param persistenceManager the persistence manager used to retrieve the persistence
     *
     * @throws NullPointerException if the persistenceManager is null
     */
    public ProjectUtility(ProjectPersistenceManager persistenceManager) {
        if (persistenceManager == null) {
            throw new NullPointerException("persistenceManager is null");
        }
        this.persistenceManager = persistenceManager;
        persistence = persistenceManager.getPersistence();
    }

    /**
     * <p>
     * Adds a new project to the persistence. If the id of the project is used false will be returned.
     * </p>
     *
     * <p>
     * If the id of the given project is -1, an id will be generated using GUID Generator. This method also ensures
     * that all necessary fields are filled (not null) before passing it to the underlying persistence.
     * </p>
     *
     * @param project the project to add
     *
     * @return true if the project was added, false otherwise
     *
     * @throws NullPointerException if the project is null
     * @throws IllegalArgumentException if creationDate or modificationDate is not null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     * @throws PersistenceException if something wrong happens while adding the project (such as SQL exception)
     */
    public boolean addProject(Project project) throws InsufficientDataException, PersistenceException {
        Util.checkAddProject(project);

        // if id is -1, generate an id for it
        if (project.getId() == -1) {
            project.setId(Util.getNextInt());
        }
        return persistence.addProject(project);
    }

    /**
     * <p>
     * Updates an project in the database. If the project does not exist in the database false will be returned.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null) before passing it to the underlying
     * persistence.
     * </p>
     *
     * @param project the project to update
     *
     * @return true if the project was updated, false otherwise
     *
     * @throws NullPointerException if the project is null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     * @throws PersistenceException if something wrong happens while updating the project (such as SQL exception)
     */
    public boolean updateProject(Project project) throws InsufficientDataException, PersistenceException {
        Util.checkUpdateProject(project);
        return persistence.updateProject(project);
    }

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
    public boolean removeProject(int projectId) throws PersistenceException {
        return persistence.removeProject(projectId);
    }

    /**
     * <p>
     * Deletes all projects from the database.
     * </p>
     *
     * @throws PersistenceException if something wrong happens while removing the projects (such as SQL exception)
     */
    public void removeAllProjects() throws PersistenceException {
        persistence.removeAllProjects();
    }

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
    public boolean assignClient(int projectId, int clientId, String user)
        throws PersistenceException
    {
        Util.checkString(user);
        return persistence.assignClient(projectId, clientId, user);
    }

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
    public Client getProjectClient(int projectId) throws PersistenceException {
        return persistence.getProjectClient(projectId);
    }

    /**
     * <p>
     * Assigns a project manager to an existing project. If the project already has a manager assigned false will be
     * returned.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null) before passing it to the underlying
     * persistence.
     * </p>
     *
     * @param projectManager the project manager to assign
     *
     * @return true if the manager was assigned, false otherwise
     *
     * @throws NullPointerException if the projectManager is null
     * @throws IllegalArgumentException if creationDate or modificationDate is not null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     * @throws PersistenceException if something wrong happens while assigning the project manager (such as SQL
     *         exception)
     */
    public boolean assignProjectManager(ProjectManager projectManager)
        throws InsufficientDataException, PersistenceException
    {
        Util.checkAddProjectManager(projectManager);
        return persistence.assignProjectManager(projectManager);
    }

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
    public ProjectManager getProjectManager(int projectId)
        throws PersistenceException
    {
        return persistence.getProjectManager(projectId);
    }

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
    public boolean removeProjectManager(int managerId, int projectId)
        throws PersistenceException
    {
        return persistence.removeProjectManager(managerId, projectId);
    }

    /**
     * <p>
     * Assigns a project worker to an existing project. If the project already has this worker assigned false will be
     * returned.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null) before passing it to the underlying
     * persistence.
     * </p>
     *
     * @param projectWorker the project worker to assign
     *
     * @return true if the worker was assigned to the project, false otherwise
     *
     * @throws NullPointerException if the projectWorker is null
     * @throws IllegalArgumentException if creationDate or modificationDate is not null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     * @throws PersistenceException if something wrong happens while assigning the project worker (such as SQL
     *         exception)
     */
    public boolean addWorker(ProjectWorker projectWorker)
        throws InsufficientDataException, PersistenceException
    {
        Util.checkAddWorker(projectWorker);
        return persistence.addWorker(projectWorker);
    }

    /**
     * <p>
     * Updates a worker in the database. If the worker was not assigned to the project false will be returned.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null) before passing it to the underlying
     * persistence.
     * </p>
     *
     * @param projectWorker the worker to update
     *
     * @return true if the worker was updated, false otherwise
     *
     * @throws NullPointerException if the projectWorker is null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     * @throws PersistenceException if something wrong happens while updating the project worker (such as SQL
     *         exception)
     */
    public boolean updateWorker(ProjectWorker projectWorker)
        throws InsufficientDataException, PersistenceException
    {
        Util.checkUpdateWorker(projectWorker);
        return persistence.updateWorker(projectWorker);
    }

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
    public boolean removeWorker(int workerId, int projectId)
        throws PersistenceException
    {
        return persistence.removeWorker(workerId, projectId);
    }

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
    public boolean removeWorkers(int projectId) throws PersistenceException {
        return persistence.removeWorkers(projectId);
    }

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
    public ProjectWorker getWorker(int workerId, int projectId)
        throws PersistenceException
    {
        return persistence.getWorker(workerId, projectId);
    }

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
    public List getWorkers(int projectId) throws PersistenceException {
        return persistence.getWorkers(projectId);
    }

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
    public boolean addTimeEntry(int entryId, int projectId, String user)
        throws PersistenceException
    {
        Util.checkString(user);
        return persistence.addTimeEntry(entryId, projectId, user);
    }

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
    public boolean removeTimeEntry(int entryId, int projectId)
        throws PersistenceException
    {
        return persistence.removeTimeEntry(entryId, projectId);
    }

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
    public List getTimeEntries(int projectId) throws PersistenceException {
        return persistence.getTimeEntries(projectId);
    }

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
    public boolean addExpenseEntry(int entryId, int projectId, String user)
        throws PersistenceException
    {
        Util.checkString(user);
        return persistence.addExpenseEntry(entryId, projectId, user);
    }

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
    public boolean removeExpenseEntry(int entryId, int projectId)
        throws PersistenceException
    {
        return persistence.removeExpenseEntry(entryId, projectId);
    }

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
    public List getExpenseEntries(int projectId) throws PersistenceException {
        return persistence.getExpenseEntries(projectId);
    }

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
    public Project getProject(int projectId) throws PersistenceException {
        return persistence.getProject(projectId);
    }

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
    public List getAllProjects() throws PersistenceException {
        return persistence.getAllProjects();
    }

    /**
     * Getter for the persistence manager used by this instance.
     *
     * @return the persistence manager
     */
    public ProjectPersistenceManager getPersistenceManager() {
        return persistenceManager;
    }
}
