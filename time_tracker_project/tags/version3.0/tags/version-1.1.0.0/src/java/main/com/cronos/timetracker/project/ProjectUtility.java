/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project;

import com.cronos.timetracker.project.persistence.BatchOperationException;
import com.cronos.timetracker.project.persistence.PersistenceException;
import com.cronos.timetracker.project.persistence.TimeTrackerProjectPersistence;
import com.cronos.timetracker.project.searchfilters.Filter;

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
 * Since version 1.1 the following functionalities are added:
 *
 * <ul>
 * <li>
 * process the above CRUD operations in a batch
 * </li>
 * <li>
 * search projects with a given search filter
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * This class performs some check on the arguments before passing them to the underlying persistence and generates ids
 * for clients and projects if they are not specified.
 * </p>
 *
 * @author DanLazar, colau
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 *
 * @since 1.0
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
        throws PersistenceException {
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
        throws InsufficientDataException, PersistenceException {
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
        throws PersistenceException {
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
        throws PersistenceException {
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
        throws InsufficientDataException, PersistenceException {
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
        throws InsufficientDataException, PersistenceException {
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
        throws PersistenceException {
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
        throws PersistenceException {
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
        throws PersistenceException {
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
        throws PersistenceException {
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
        throws PersistenceException {
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
        throws PersistenceException {
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

    /**
     * <p>
     * Searches the projects from the database which matches the given search filter. If there are no such projects in
     * the database, an empty array will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @param searchFilter the search filter to match.
     *
     * @return the array of matching projects.
     *
     * @throws IllegalArgumentException if the searchFilter is null, or the searchFilter is not supported, or no column
     *         name is found for the column alias.
     * @throws PersistenceException if something wrong happens while searching the projects (such as SQL exception)
     *
     * @since 1.1
     */
    public Project[] searchProjects(Filter searchFilter)
        throws PersistenceException {
        return persistence.searchForProjects(searchFilter);
    }

    /**
     * <p>
     * Adds the projects to the persistence. If the id of any project is used, a BatchOperationException will be
     * thrown.
     * </p>
     *
     * <p>
     * If the id of any project is -1, an id will be generated using GUID Generator. This method also ensures that all
     * necessary fields are filled (not null) before passing it to the underlying persistence.
     * </p>
     *
     * @param projects the projects to add.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the projects is null or empty, or any of its elements is null.
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null.
     * @throws BatchOperationException if something wrong happens while adding the projects in a batch.
     *
     * @see #addProject(Project)
     * @since 1.1
     */
    public void addProjects(Project[] projects, boolean atomic)
        throws InsufficientDataException, BatchOperationException {
        Util.checkArray(projects);
        for (int i = 0; i < projects.length; i++) {
            Util.checkAddProject(projects[i]);
        }
        persistence.addProjects(projects, atomic);
    }

    /**
     * <p>
     * Deletes the projects from the database. If a project with any specified id does not exist, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param projectIds the ids of the projects to delete.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the projectIds is null or empty.
     * @throws BatchOperationException if something wrong happens while deleting the projects in a batch.
     *
     * @see #removeProject(int)
     * @since 1.1
     */
    public void removeProjects(int[] projectIds, boolean atomic)
        throws BatchOperationException {
        persistence.removeProjects(projectIds, atomic);
    }

    /**
     * <p>
     * Updates the projects in the database. If any project does not exist in the database, a BatchOperationException
     * will be thrown.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null) before passing it to the underlying
     * persistence.
     * </p>
     *
     * @param projects the projects to update.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the projects is null or empty, or any of its elements is null.
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null.
     * @throws BatchOperationException if something wrong happens while updating the projects in a batch.
     *
     * @see #updateProject(Project)
     * @since 1.1
     */
    public void updateProjects(Project[] projects, boolean atomic)
        throws InsufficientDataException, BatchOperationException {
        Util.checkArray(projects);
        for (int i = 0; i < projects.length; i++) {
            Util.checkUpdateProject(projects[i]);
        }
        persistence.updateProjects(projects, atomic);
    }

    /**
     * <p>
     * Retrieves the projects from the persistence. If any project does not exist, a BatchOperationException will be
     * thrown if it fails to retrieve at least one project (in atomic mode), or if it fails to retrieve all projects
     * (in non-atomic mode). When the operation is not atomic, and at least one project was successfully retrieved,
     * the returned array will contain nulls in the places where the projects cannot be retrieved.
     * </p>
     *
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @param projectIds the ids of the projects to retrieve.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @return the projects with the given ids.
     *
     * @throws IllegalArgumentException if the projectIds is null or empty.
     * @throws BatchOperationException if something wrong happens while retrieving the projects in a batch.
     *
     * @see #getProject(int)
     * @since 1.1
     */
    public Project[] getProjects(int[] projectIds, boolean atomic)
        throws BatchOperationException {
        return persistence.getProjects(projectIds, atomic);
    }

    /**
     * <p>
     * Assigns the project workers to the existing projects. If any project already has the worker assigned, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null) before passing it to the underlying
     * persistence.
     * </p>
     *
     * @param workers the project workers to assign.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the projectWorkers is null or empty, or any of its elements is null.
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null.
     * @throws BatchOperationException if something wrong happens while assigning the project workers in a batch.
     *
     * @see #addWorker(ProjectWorker)
     * @since 1.1
     */
    public void addWorkers(ProjectWorker[] workers, boolean atomic)
        throws InsufficientDataException, BatchOperationException {
        Util.checkArray(workers);
        for (int i = 0; i < workers.length; i++) {
            Util.checkAddWorker(workers[i]);
        }
        persistence.addWorkers(workers, atomic);
    }

    /**
     * <p>
     * Deletes the project workers from the database. This means that these workers will no longer be a worker for this
     * project. If the project does not have any of these workers assigned, a BatchOperationException will be thrown.
     * </p>
     *
     * @param workerIds the ids of the workers to delete.
     * @param projectId the id of the project.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the workerIds is null or empty.
     * @throws BatchOperationException if something wrong happens while deleting the project workers in a batch.
     *
     * @see #removeWorker(int, int)
     * @since 1.1
     */
    public void removeWorkers(int[] workerIds, int projectId, boolean atomic)
        throws BatchOperationException {
        persistence.removeWorkers(workerIds, projectId, atomic);
    }

    /**
     * <p>
     * Updates the workers in the database. If the workers were not assigned to the projects, a BatchOperationException
     * will be thrown.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null) before passing it to the underlying
     * persistence.
     * </p>
     *
     * @param workers the workers to update.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the projectWorkers is null or empty, or any of its elements is null.
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null.
     * @throws BatchOperationException if something wrong happens while updating the project workers in a batch.
     *
     * @see #updateWorker(ProjectWorker)
     * @since 1.1
     */
    public void updateWorkers(ProjectWorker[] workers, boolean atomic)
        throws InsufficientDataException, BatchOperationException {
        Util.checkArray(workers);
        for (int i = 0; i < workers.length; i++) {
            Util.checkUpdateWorker(workers[i]);
        }
        persistence.updateWorkers(workers, atomic);
    }

    /**
     * <p>
     * Retrieves the workers of a project. If the project does not have these workers assigned, a
     * BatchOperationException will be thrown if it fails to retrieve at least one worker (in atomic mode), or if it
     * fails to retrieve all workers (in non-atomic mode). When the operation is not atomic, and at least one worker
     * was successfully retrieved, the returned array will contain nulls in the places where the workers cannot be
     * retrieved.
     * </p>
     *
     * <p>
     * The fields of the ProjectWorker objects will be properly populated.
     * </p>
     *
     * @param workerIds the ids of the workers to retrieve.
     * @param projectId the id of the project.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @return the project workers with the given ids.
     *
     * @throws IllegalArgumentException if the workerIds is null or empty.
     * @throws BatchOperationException if something wrong happens while retrieving the project workers in a batch.
     *
     * @see #getWorker(int, int)
     * @since 1.1
     */
    public ProjectWorker[] getWorkers(int[] workerIds, int projectId, boolean atomic)
        throws BatchOperationException {
        return persistence.getWorkers(workerIds, projectId, atomic);
    }

    /**
     * <p>
     * Adds the time entries to an existing project. If the project already has any of these time entries assigned, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param entryIds the ids of the time entries to add.
     * @param projectId the id of the project.
     * @param user the user who created the time entries for the project.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the entryIds is null or empty, or the user is null or the empty string.
     * @throws BatchOperationException if something wrong happens while adding the time entries in a batch.
     *
     * @see #addTimeEntry(int, int, String)
     * @since 1.1
     */
    public void addTimeEntries(int[] entryIds, int projectId, String user, boolean atomic)
        throws BatchOperationException {
        persistence.addTimeEntries(entryIds, projectId, user, atomic);
    }

    /**
     * <p>
     * Deletes the time entries of a project from the database. If the project does not have any of these time entries
     * assigned, a BatchOperationException will be thrown.
     * </p>
     *
     * @param entryIds the ids of the time entries to delete.
     * @param projectId the id of the project.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the entryIds is null or empty.
     * @throws BatchOperationException if something wrong happens while deleting the time entries in a batch.
     *
     * @see #removeTimeEntry(int, int)
     * @since 1.1
     */
    public void removeTimeEntries(int[] entryIds, int projectId, boolean atomic)
        throws BatchOperationException {
        persistence.removeTimeEntries(entryIds, projectId, atomic);
    }

    /**
     * <p>
     * Adds the expense entries to an existing project. If the project already has any of these expense entries
     * assigned, a BatchOperationException will be thrown.
     * </p>
     *
     * @param entryIds the ids of the expense entries to add.
     * @param projectId the id of the project.
     * @param user the user who created the expense entries for the project.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the entryIds is null or empty, or the user is null or the empty string.
     * @throws BatchOperationException if something wrong happens while adding the expense entries in a batch.
     *
     * @see #addExpenseEntry(int, int, String)
     * @since 1.1
     */
    public void addExpenseEntries(int[] entryIds, int projectId, String user, boolean atomic)
        throws BatchOperationException {
        persistence.addExpenseEntries(entryIds, projectId, user, atomic);
    }

    /**
     * <p>
     * Deletes the expense entries of a project from the database. If the project does not have any of these expense
     * entries assigned, a BatchOperationException will be thrown.
     * </p>
     *
     * @param entryIds the ids of the expense entries to delete.
     * @param projectId the id of the project.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the entryIds is null or empty.
     * @throws BatchOperationException if something wrong happens while deleting the expense entries in a batch.
     *
     * @see #removeExpenseEntry(int, int)
     * @since 1.1
     */
    public void removeExpenseEntries(int[] entryIds, int projectId, boolean atomic)
        throws BatchOperationException {
        persistence.removeExpenseEntries(entryIds, projectId, atomic);
    }
}
