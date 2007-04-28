/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.topcoder.timetracker.project.persistence.BatchOperationException;
import com.topcoder.timetracker.project.persistence.PersistenceException;
import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;
import com.topcoder.timetracker.project.searchfilters.Filter;

/**
 * <p>
 * The ProjectUtility is useful to manage the projects through the persistence
 * provided in the constructor. It can do the following things:
 *
 * <ul>
 * <li> add a project to the Projects table </li>
 * <li> delete a project from the Projects table </li>
 * <li> delete all the clients from the Projects table </li>
 * <li> retrieve a project from the Projects table </li>
 * <li> retrieve all the projects from the Projects table </li>
 * <li> update a project in the Projects table </li>
 * <li> assign a client to a project </li>
 * <li> retrieve the client of a specified project </li>
 * <li> add/remove/update/retrieve workers </li>
 * <li> assign/remove/retrieve project manager </li>
 * <li> add/remove/retrieve time entries </li>
 * <li> add/remove/retrieve expense entries </li>
 * </ul>
 * </p>
 *
 * <p>
 * Since version 1.1 the following functionalities are added:
 *
 * <ul>
 * <li> process the above CRUD operations in a batch </li>
 * <li> search projects with a given search filter </li>
 * </ul>
 * </p>
 * <p>
 * The version 2.0 added verifications regarding to relation of projects with a
 * companies.
 * </p>
 *
 * <p>
 * This class performs some check on the arguments before passing them to the
 * underlying persistence and generates ids for clients and projects if they are
 * not specified.
 * </p>
 *
 * @author DanLazar, colau, costty000
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 *
 * @since 1.0
 */
public class ProjectUtility {
    /**
     * <p>
     * Represents an instance of TimeTrackerProjectPersistenceManager class. It
     * will be used to retrieve the persistence using the getPersistence()
     * method. It will be initialized in the constructor. It cannot be null.
     * </p>
     */
    private ProjectPersistenceManager persistenceManager = null;

    /**
     * <p>
     * Represents the underlying persistence to manage the projects. It will be
     * obtained by the the getPersistence() method of ProjectPersistenceManager.
     * It will be initialized in the constructor. It cannot be null.
     * </p>
     */
    private TimeTrackerProjectPersistence persistence = null;

    /**
     * <p>
     * Creates a new instance of this class with the persistence manager.
     * </p>
     *
     * @param persistenceManager
     *            the persistence manager used to retrieve the persistence
     *
     * @throws NullPointerException
     *             if the persistenceManager is null
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
     * Adds a new project to the persistence. If the id of the project is used
     * false will be returned.
     * </p>
     *
     * <p>
     * If the id of the given project is -1, an id will be generated using GUID
     * Generator. This method also ensures that all necessary fields are filled
     * (not null) before passing it to the underlying persistence.
     * </p>
     *
     * If the project doesn't have set the companyId field (has the value -1)
     * then InsufficientDataException will be thrown. If exist at least one
     * worker that has a different company Id that the project has then
     * IllegalArgumentException will be thrown (will be compared with the
     * companyId value from the User Account that is behind the worker). If
     * project manager has a different company Id that the project has then
     * IllegalArgumentException will be thrown (will be compared with the
     * companyId value from the User Account that is behind the project
     * manager). If exist at least one time entry that has a different company
     * Id that the project has then IllegalArgumentException will be thrown. If
     * exist at least one expense entry that has a different company Id that the
     * project has then IllegalArgumentException will be thrown.
     * </p>
     *
     *
     * @return true if the project has been added to the database; false
     *         otherwise
     * @param project
     *            the project to be added to the database
     * @throws PersistenceException
     *             wraps a persistence implementation specific exception (such
     *             as SQL exception)
     * @throws NullPointerException -
     *             if the argument is null
     * @throws InsufficientDataException -
     *             If one of the fields except creationDate and
     *             modificationDate, are null; If the project doesn't have set
     *             the companyId field (has the value -1)
     * @throws IllegalArgumentException -
     *             if creationDate is not null and/or modificationDate is not
     *             null; If exist at least one worker that has a different
     *             company Id that the project has then IllegalArgumentException
     *             will be thrown (will be compared with the companyId value
     *             from the User Account that is behind the worker). If project
     *             manager has a different company Id that the project has then
     *             IllegalArgumentException will be thrown (will be compared
     *             with the companyId value from the User Account that is behind
     *             the project manager). If exist at least one time entry that
     *             has a different company Id that the project has then
     *             IllegalArgumentException will be thrown. If exist at least
     *             one expense entry that has a different company Id that the
     *             project has then IllegalArgumentException will be thrown. if
     *             the project doesn't have a valid project manager id.
     */
    public boolean addProject(Project project)
            throws InsufficientDataException, PersistenceException {
        Util.checkAddProject(project);
        // added in version 2.0
        // check the company corespondace between project manager and project if
        // project manager id it is set or raise error if no project manager id
        // is set
        if (project.getManagerId() != -1) {
            checkCompanyCorespondaceForManager(project, project.getManagerId());
        }
        checkCompanyCorespondaceForWorkers(project, project.getWorkersIds());
        checkCompanyCorespondaceForTimeEntries(project, project
                .getTimeEntries());
        checkCompanyCorespondaceForExpenseEntries(project, project
                .getExpenseEntries());
        return persistence.addProject(project);
    }

    /**
     * <p>
     * Updates an project in the database. If the project does not exist in the
     * database false will be returned.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null)
     * before passing it to the underlying persistence.
     * </p>
     *
     * If the project doesn't have set the companyId field (has the value -1)
     * then InsufficientDataException will be thrown.
     *
     * @param project
     *            the project to update
     *
     * @return true if the project was updated, false otherwise
     *
     * @throws NullPointerException
     *             if the project is null
     * @throws InsufficientDataException
     *             if one of the fields except creationDate and
     *             modificationDate, are null; If the project doesn't have set
     *             the companyId field (has the value -1)
     * @throws PersistenceException
     *             if something wrong happens while updating the project (such
     *             as SQL exception)
     */
    public boolean updateProject(Project project)
            throws InsufficientDataException, PersistenceException {
        Util.checkUpdateProject(project);
        // added in version 2.0
        ProjectManager manager = persistence.getProjectManager(project.getId());
        if (manager != null) {
            checkCompanyCorespondaceForManager(project, manager.getManagerId());
        }
        checkCompanyCorespondaceForWorkers(project, project.getWorkersIds());
        List timeEntries = persistence.getTimeEntries(project.getId());
        if (timeEntries != null && timeEntries.size() > 0) {
            checkCompanyCorespondaceForTimeEntries(project, new HashSet(
                    timeEntries));
        }
        List expenseEntries = persistence.getExpenseEntries(project.getId());
        if (expenseEntries != null && expenseEntries.size() > 0) {
            checkCompanyCorespondaceForExpenseEntries(project, new HashSet(
                    expenseEntries));
        }
        return persistence.updateProject(project);
    }

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
    public boolean removeProject(int projectId) throws PersistenceException {
        return persistence.removeProject(projectId);
    }

    /**
     * <p>
     * Deletes all projects from the database.
     * </p>
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the projects (such
     *             as SQL exception)
     */
    public void removeAllProjects() throws PersistenceException {
        persistence.removeAllProjects();
    }

    /**
     * <p>
     * Assigns a client to an existing project. If the project already has a
     * client assigned false will be returned.
     * </p>
     *
     * If the project for projectId given as parameter has a different companyId
     * that the client has (the client for given clientId) then
     * IllegalArgumentException will be thrown.
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
     *             if the user is the empty string; If the project for projectId
     *             given as parameter has a different companyId that the client
     *             has (the client for given clientId)
     * @throws PersistenceException
     *             if something wrong happens while assigning the client (such
     *             as SQL exception)
     */
    public boolean assignClient(int projectId, int clientId, String user)
            throws PersistenceException {
        if (persistence.getCompanyIdForProject(projectId) != persistence
                .getCompanyIdForClient(clientId)) {
            throw new IllegalArgumentException(
                    "The client and the project must have the same companyId");
        }
        return persistence.assignClient(projectId, clientId, user);
    }

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
    public Client getProjectClient(int projectId) throws PersistenceException {
        return persistence.getProjectClient(projectId);
    }

    /**
     * <p>
     * Assigns a project manager to an existing project. If the project already
     * has a manager assigned false will be returned.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null)
     * before passing it to the underlying persistence.
     * </p>
     *
     * If the user account assigned to this project manager has a different
     * companyId that the companyId from the project assigned to given project
     * manager then the IllegalArgumentException will be thrown.
     *
     * @param projectManager
     *            the project manager to assign
     *
     * @return true if the manager was assigned, false otherwise
     *
     * @throws NullPointerException
     *             if the projectManager is null
     * @throws IllegalArgumentException
     *             if creationDate or modificationDate is not null If the user
     *             account assigned to this project manager has a different
     *             companyId that the companyId from the project assigned to
     *             given project manager
     * @throws InsufficientDataException
     *             if any field except creationDate and modificationDate is null
     * @throws PersistenceException
     *             if something wrong happens while assigning the project
     *             manager (such as SQL exception)
     */
    public boolean assignProjectManager(ProjectManager projectManager)
            throws InsufficientDataException, PersistenceException {
        Util.checkAddProjectManager(projectManager);
        if (persistence.getCompanyIdForUserAccount(projectManager
                .getManagerId()) != projectManager.getProject().getCompanyId()) {
            throw new IllegalArgumentException(
                    "The manager and the project must have the same companyId");
        }
        return persistence.assignProjectManager(projectManager);
    }

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
    public ProjectManager getProjectManager(int projectId)
            throws PersistenceException {
        return persistence.getProjectManager(projectId);
    }

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
    public boolean removeProjectManager(int managerId, int projectId)
            throws PersistenceException {
        return persistence.removeProjectManager(managerId, projectId);
    }

    /**
     * <p>
     * Assigns a project worker to an existing project. If the project already
     * has this worker assigned false will be returned.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null)
     * before passing it to the underlying persistence.
     * </p>
     *
     * If the user account assigned to this project worker has a different
     * companyId that the companyId from the project assigned to given project
     * worker then the IllegalArgumentException will be thrown.
     *
     * @param projectWorker
     *            the project worker to assign
     *
     * @return true if the worker was assigned to the project, false otherwise
     *
     * @throws NullPointerException
     *             if the projectWorker is null
     * @throws IllegalArgumentException
     *             if creationDate or modificationDate is not null If the user
     *             account assigned to this project worker has a different
     *             companyId that the companyId from the project assigned to
     *             given project worker
     * @throws InsufficientDataException
     *             if any field except creationDate and modificationDate is null
     * @throws PersistenceException
     *             if something wrong happens while assigning the project worker
     *             (such as SQL exception)
     */
    public boolean addWorker(ProjectWorker projectWorker)
            throws InsufficientDataException, PersistenceException {
        Util.checkAddWorker(projectWorker);
        if (persistence.getCompanyIdForUserAccount(projectWorker.getWorkerId()) != projectWorker
                .getProject().getCompanyId()) {
            throw new IllegalArgumentException(
                    "the project worker and the project assigned must have the same companyID");
        }
        return persistence.addWorker(projectWorker);
    }

    /**
     * <p>
     * Updates a worker in the database. If the worker was not assigned to the
     * project false will be returned.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null)
     * before passing it to the underlying persistence.
     * </p>
     *
     * @param projectWorker
     *            the worker to update
     *
     * @return true if the worker was updated, false otherwise
     *
     * @throws NullPointerException
     *             if the projectWorker is null
     * @throws InsufficientDataException
     *             if any field except creationDate and modificationDate is null
     * @throws PersistenceException
     *             if something wrong happens while updating the project worker
     *             (such as SQL exception)
     */
    public boolean updateWorker(ProjectWorker projectWorker)
            throws InsufficientDataException, PersistenceException {
        Util.checkUpdateWorker(projectWorker);
        return persistence.updateWorker(projectWorker);
    }

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
    public boolean removeWorker(int workerId, int projectId)
            throws PersistenceException {
        return persistence.removeWorker(workerId, projectId);
    }

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
    public boolean removeWorkers(int projectId) throws PersistenceException {
        return persistence.removeWorkers(projectId);
    }

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
    public ProjectWorker getWorker(int workerId, int projectId)
            throws PersistenceException {
        return persistence.getWorker(workerId, projectId);
    }

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
    public List getWorkers(int projectId) throws PersistenceException {
        return persistence.getWorkers(projectId);
    }

    /**
     * <p>
     * Adds a time entry to an existing project. If the project already has this
     * time entry assigned false will be returned.
     * </p>
     *
     * If the project for projectId given as parameter has a different companyId
     * that the companyId that the time entry has (the time entry for the given
     * entryId) then IllegalArgumentException will be thrown.
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
     *             if the user is the empty string if the project for projectId
     *             given as parameter has a different companyId that the
     *             companyId that the time entry has (the time entry given
     *             entryId)
     * @throws PersistenceException
     *             if something wrong happens while adding the time entry (such
     *             as SQL exception)
     */
    public boolean addTimeEntry(int entryId, int projectId, String user)
            throws PersistenceException {
        if (persistence.getCompanyIdForTimeEntry(entryId) != persistence
                .getCompanyIdForProject(projectId)) {
            throw new IllegalArgumentException(
                    "the time entry and the project must have the same company id");
        }
        return persistence.addTimeEntry(entryId, projectId, user);
    }

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
    public boolean removeTimeEntry(int entryId, int projectId)
            throws PersistenceException {
        return persistence.removeTimeEntry(entryId, projectId);
    }

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
    public List getTimeEntries(int projectId) throws PersistenceException {
        return persistence.getTimeEntries(projectId);
    }

    /**
     * <p>
     * Adds an expense entry to an existing project. If the project already has
     * this expense entry assigned false will be returned.
     * </p>
     *
     * If the project for projectId given as parameter has a different companyId
     * that the companyId that the expense entry has (the expense entry for the
     * given entryId) then IllegalArgumentException will be thrown.
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
     *             if the user is the empty string if the project for projectId
     *             given as parameter has a different companyId that the
     *             companyId that the expense entry has (the expense entry for
     *             the given entryId)
     * @throws PersistenceException
     *             if something wrong happens while adding the expense entry
     *             (such as SQL exception)
     */
    public boolean addExpenseEntry(int entryId, int projectId, String user)
            throws PersistenceException {
        if (persistence.getCompanyIdForExpenseEntry(entryId) != persistence
                .getCompanyIdForProject(projectId)) {
            throw new IllegalArgumentException(
                    "The expense entry and the project must have the same company id");
        }
        return persistence.addExpenseEntry(entryId, projectId, user);
    }

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
    public boolean removeExpenseEntry(int entryId, int projectId)
            throws PersistenceException {
        return persistence.removeExpenseEntry(entryId, projectId);
    }

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
    public List getExpenseEntries(int projectId) throws PersistenceException {
        return persistence.getExpenseEntries(projectId);
    }

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
    public Project getProject(int projectId) throws PersistenceException {
        return persistence.getProject(projectId);
    }

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
    public Project[] searchProjects(Filter searchFilter)
            throws PersistenceException {
        return persistence.searchForProjects(searchFilter);
    }

    /**
     * <p>
     * Adds the projects to the persistence. If the id of any project is used, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * <p>
     * If the id of any project is -1, an id will be generated using GUID
     * Generator. This method also ensures that all necessary fields are filled
     * (not null) before passing it to the underlying persistence.
     * </p>
     *
     * <p>
     * If exist at least one project that doesn't have set the companyId field
     * (has the value -1) then InsufficientDataException will be thrown. For all
     * the projects if exist at least one worker that has a different company Id
     * that the current project has then IllegalArgumentException will be thrown
     * (will be compared with the companyId value from the User Account that is
     * behind the worker). For all the projects if project manager has a
     * different company Id that the current project has then
     * IllegalArgumentException will be thrown (will be compared with the
     * companyId value from the User Account that is behind the project
     * manager). For all the projects if exist at least one time entry that has
     * a different company Id that the current project has then
     * IllegalArgumentException will be thrown. For all the projects if exist at
     * least one expense entry that has a different company Id that the current
     * project has then IllegalArgumentException will be thrown.
     * </p>
     *
     *
     *
     * @param projects
     *            the array of Projects to be added, should not be null,
     *            elements should not be null
     * @param atomic
     *            specifies if the operation should be atomic (all-or-nothing)
     * @throws BatchOperationException
     *             if any error happens on the persistence level
     * @throws IllegalArgumentException
     *             if "projects" array is null or zero-length, or illegal for
     *             other reasons; For all the projects if exist at least one
     *             worker that has a different company Id that the current
     *             project has then IllegalArgumentException will be thrown
     *             (will be compared with the companyId value from the User
     *             Account that is behind the worker). For all the projects if
     *             project manager has a different company Id that the current
     *             project has then IllegalArgumentException will be thrown
     *             (will be compared with the companyId value from the User
     *             Account that is behind the project manager). For all the
     *             projects if exist at least one time entry that has a
     *             different company Id that the current project has then
     *             IllegalArgumentException will be thrown. For all the projects
     *             if exist at least one expense entry that has a different
     *             company Id that the current project has then
     *             IllegalArgumentException will be thrown.
     * @throws InsufficientDataException
     *             if one of the fields except creationDate and modificationDate
     *             of any particular project , is null; If exist at least one
     *             project that doesn't have set the companyId field (has the
     *             value -1)
     *
     * @see #addProject(Project)
     * @since 1.1
     */
    public void addProjects(Project[] projects, boolean atomic)
            throws InsufficientDataException, BatchOperationException {
        Util.checkArray(projects);
        for (int i = 0; i < projects.length; i++) {
            Util.checkAddProject(projects[i]);
            try {
                // check the company corespondace between project manager and
                // project if project manager id it is set or raise error if no
                // project manager id is set
                if (projects[i].getManagerId() != -1) {
                    checkCompanyCorespondaceForManager(projects[i], projects[i]
                            .getManagerId());
                }
                // check company corespondace for current project and assigned
                // workers
                checkCompanyCorespondaceForWorkers(projects[i], projects[i]
                        .getWorkersIds());
                // check company corespondace for current project and assigned
                // time entries
                checkCompanyCorespondaceForTimeEntries(projects[i], projects[i]
                        .getTimeEntries());
                // check company corespondace for current project and assigned
                // expense entries
                checkCompanyCorespondaceForExpenseEntries(projects[i],
                        projects[i].getExpenseEntries());
            } catch (PersistenceException ex) {
                throw new BatchOperationException(
                        "Error when performing the check for company corespondace for projects in the given list",
                        new Throwable[] { ex });
            }
        }
        persistence.addProjects(projects, atomic);
    }

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
    public void removeProjects(int[] projectIds, boolean atomic)
            throws BatchOperationException {
        persistence.removeProjects(projectIds, atomic);
    }

    /**
     * <p>
     * Updates the projects in the database. If any project does not exist in
     * the database, a BatchOperationException will be thrown.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null)
     * before passing it to the underlying persistence.
     * </p>
     *
     * <p>
     * If exist at least one project that doesn't have the companyId field with
     * value -1 (not seted) the InsufficientDataException will be thrown.
     * </p>
     *
     * @param projects
     *            the array of Projects to be updated, should not be null,
     *            elements should not be null
     * @param atomic
     *            specifies if the operation should be atomic (all-or-nothing)
     * @throws IllegalArgumentException
     *             if "projects" array is null or zero-length, or illegal
     *             because of the other reasons
     * @throws BatchOperationException
     *             if any error happens on the persistence level
     * @throws InsufficientDataException -
     *             If exist at least one project that doesn't have the companyId
     *             field with value -1 (not seted)
     *
     * @see #updateProject(Project)
     * @since 1.1
     */
    public void updateProjects(Project[] projects, boolean atomic)
            throws InsufficientDataException, BatchOperationException {
        Util.checkArray(projects);
        for (int i = 0; i < projects.length; i++) {
            Util.checkUpdateProject(projects[i]);
            try {
                // check the company corespondace between the current project
                // and assigned project manager
                ProjectManager manager = persistence
                        .getProjectManager(projects[i].getId());
                if (manager != null) {
                    checkCompanyCorespondaceForManager(projects[i], manager
                            .getManagerId());
                }
                // check the company corespondace between the current project
                // and assigned workers
                checkCompanyCorespondaceForWorkers(projects[i], projects[i]
                        .getWorkersIds());
                // check the company corespondace between the current project
                // and assigned time entries
                List timeEntries = persistence.getTimeEntries(projects[i]
                        .getId());
                if (timeEntries != null && timeEntries.size() > 0) {
                    checkCompanyCorespondaceForTimeEntries(projects[i],
                            new HashSet(timeEntries));
                }
                // check the company corespondace between the current project
                // and assigned expense entries
                List expenseEntries = persistence.getExpenseEntries(projects[i]
                        .getId());
                if (expenseEntries != null && expenseEntries.size() > 0) {
                    checkCompanyCorespondaceForExpenseEntries(projects[i],
                            new HashSet(expenseEntries));
                }
            } catch (PersistenceException ex) {
                throw new BatchOperationException(
                        "Error when performing the check for company corespondace for projects in the given list",
                        new Throwable[] { ex });
            }
        }
        persistence.updateProjects(projects, atomic);
    }

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
    public Project[] getProjects(int[] projectIds, boolean atomic)
            throws BatchOperationException {
        return persistence.getProjects(projectIds, atomic);
    }

    /**
     * <p>
     * Assigns the project workers to the existing projects. If any project
     * already has the worker assigned, a BatchOperationException will be
     * thrown.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null)
     * before passing it to the underlying persistence.
     * </p>
     * <p>
     * If the user account assigned to this every project worker has a different
     * companyId that the companyId from the project assigned to current project
     * worker then the IllegalArgumentException will be thrown.
     * </p>
     *
     * @param workers
     *            the array of ProjectWorkers to be added, should not be null,
     *            elements should not be null
     * @param atomic
     *            specifies if the operation should be atomic (all-or-nothing)
     * @throws InsufficientDataException
     *             if any of the ProjectWorker instances doesn't have all the
     *             fields populated, except creationDate throw
     *             InsufficientDataException
     * @throws BatchOperatonException
     *             if any error happens on the persistence level
     * @throws IllegalArgumentException
     *             if "workers" array is null or zero-length, or any of its
     *             elements is null; If the user account assigned to this every
     *             project worker has a different companyId that the companyId
     *             from the project assigned to current project worker
     *
     * @see #addWorker(ProjectWorker)
     * @since 1.1
     */
    public void addWorkers(ProjectWorker[] workers, boolean atomic)
            throws InsufficientDataException, BatchOperationException {
        Util.checkArray(workers);
        for (int i = 0; i < workers.length; i++) {
            Util.checkAddWorker(workers[i]);
            try {
                if (persistence.getCompanyIdForUserAccount(workers[i]
                        .getWorkerId()) != workers[i].getProject()
                        .getCompanyId()) {
                    throw new IllegalArgumentException(
                            "the project worker and the project assigned must have the same companyID");
                }
            } catch (PersistenceException e) {
                throw new BatchOperationException(
                        "Error when verify the company corespondace between worker and assigned project",
                        new Throwable[] { e });
            }
        }
        persistence.addWorkers(workers, atomic);
    }

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
    public void removeWorkers(int[] workerIds, int projectId, boolean atomic)
            throws BatchOperationException {
        persistence.removeWorkers(workerIds, projectId, atomic);
    }

    /**
     * <p>
     * Updates the workers in the database. If the workers were not assigned to
     * the projects, a BatchOperationException will be thrown.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null)
     * before passing it to the underlying persistence.
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
     * @throws InsufficientDataException
     *             if any field except creationDate and modificationDate is
     *             null.
     * @throws BatchOperationException
     *             if something wrong happens while updating the project workers
     *             in a batch.
     *
     * @see #updateWorker(ProjectWorker)
     * @since 1.1
     */
    public void updateWorkers(ProjectWorker[] workers, boolean atomic)
            throws InsufficientDataException, BatchOperationException {
        Util.checkArray(workers);
        for (int i = 0; i < workers.length; i++) {
            Util.checkUpdateWorker(workers[i]);
            try {
                if (persistence.getCompanyIdForUserAccount(workers[i]
                        .getWorkerId()) != workers[i].getProject()
                        .getCompanyId()) {
                    throw new IllegalArgumentException(
                            "the project worker and the project assigned must have the same companyID");
                }
            } catch (PersistenceException e) {
                throw new BatchOperationException(
                        "Error when verify the company corespondace between worker and assigned project",
                        new Throwable[] { e });
            }
        }
        persistence.updateWorkers(workers, atomic);
    }

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
    public ProjectWorker[] getWorkers(int[] workerIds, int projectId,
            boolean atomic) throws BatchOperationException {
        return persistence.getWorkers(workerIds, projectId, atomic);
    }

    /**
     * <p>
     * Adds the time entries to an existing project. If the project already has
     * any of these time entries assigned, a BatchOperationException will be
     * thrown.
     * </p>
     *
     * <p>
     * If the project for projectId given as parameter has a different companyId
     * that al least one of the companyId that the time entries has (the time
     * entries for the given entryIds) then IllegalArgumentException will be
     * thrown.
     * </p>
     *
     * @param entryIds
     *            the array of ids of the time entries to be added to the
     *            project, should not be null or zero-length
     * @param projectId
     *            the id of Project, the time entries should be added to
     * @param user
     *            the name of user, which performs an operation, this value will
     *            be used to populate the creationUser and modificationUser
     *            fields, should not be null or empty string
     * @param atomic
     *            specifies if the operation should be atomic (all-or-nothing)
     * @throws IllegalArgumentException
     *             if any of the parameters is null, or "user" parameter is an
     *             empty string; If the project for projectId given as parameter
     *             has a different companyId that al least one of the companyId
     *             that the time entries has (the time entries for the given
     *             entryIds)
     * @throws BatchOperationException
     *             if any error happens on the persistence level
     *
     * @see #addTimeEntry(int, int, String)
     * @since 1.1
     */
    public void addTimeEntries(int[] entryIds, int projectId, String user,
            boolean atomic) throws BatchOperationException {
        if (entryIds != null && entryIds.length > 0) {
            try {
                int companyIdForProject = persistence
                        .getCompanyIdForProject(projectId);
                for (int i = 0; i < entryIds.length; i++) {
                    if (companyIdForProject != persistence
                            .getCompanyIdForTimeEntry(entryIds[i])) {
                        throw new IllegalArgumentException(
                                "Time entry associated with a project must have the same "
                                        + "CompanyId as the project does.");
                    }
                }
            } catch (PersistenceException e) {
                throw new BatchOperationException(
                        "Error when verify the company corespondace between project "
                                + "and time entries", new Throwable[] { e });
            }
        }
        persistence.addTimeEntries(entryIds, projectId, user, atomic);
    }

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
    public void removeTimeEntries(int[] entryIds, int projectId, boolean atomic)
            throws BatchOperationException {
        persistence.removeTimeEntries(entryIds, projectId, atomic);
    }

    /**
     * <p>
     * Adds the expense entries to an existing project. If the project already
     * has any of these expense entries assigned, a BatchOperationException will
     * be thrown.
     * </p>
     * <p>
     * If the project for projectId given as parameter has a different companyId
     * that at least one of the companyId that the expense entries has (the
     * expense entries for the given entryIds) then IllegalArgumentException
     * will be thrown.
     * </p>
     *
     * @param entryIds
     *            the array of ids of the expense entries to be added to the
     *            project, should not be null or zero-length
     * @param projectId
     *            the id of Project, the expense entries should be added to
     * @param user
     *            the name of user, which performs an operation, this value will
     *            be used to populate the creationUser and modificationUser
     *            fields, should not be null or empty string
     * @param atomic
     *            specifies if the operation should be atomic (all-or-nothing)
     * @throws IllegalArgumentException
     *             if any of the parameters is null, or "user" parameter is an
     *             empty string; If the project for projectId given as parameter
     *             has a different companyId that at least one of the companyId
     *             that the expense entries has (the expense entries for the
     *             given entryIds)
     * @throws BatchOperationException
     *             if any error happens on the persistence level
     *
     * @see #addExpenseEntry(int, int, String)
     * @since 1.1
     */
    public void addExpenseEntries(int[] entryIds, int projectId, String user,
            boolean atomic) throws BatchOperationException {
        if (entryIds != null && entryIds.length > 0) {
            try {
                int companyIdForProject = persistence
                        .getCompanyIdForProject(projectId);
                for (int i = 0; i < entryIds.length; i++) {
                    if (companyIdForProject != persistence
                            .getCompanyIdForExpenseEntry(entryIds[i])) {
                        throw new IllegalArgumentException(
                                "The expense entries associated with a project "
                                        + "must have the same CompanyId as the project does.");
                    }
                }
            } catch (PersistenceException e) {
                throw new BatchOperationException(
                        "Error when verify the company corespondace between "
                                + "project and expense entries",
                        new Throwable[] { e });
            }
        }
        persistence.addExpenseEntries(entryIds, projectId, user, atomic);
    }

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
    public void removeExpenseEntries(int[] entryIds, int projectId,
            boolean atomic) throws BatchOperationException {
        persistence.removeExpenseEntries(entryIds, projectId, atomic);
    }

    /**
     * Verify if the project and the assigned manager has the same companyId
     *
     * @param project
     *            the project that must be checked
     * @param managerId
     *            the id of the project manager
     * @throws PersistenceException
     *
     * @since 2.0
     */
    public void checkCompanyCorespondaceForManager(Project project,
            int managerId) throws PersistenceException {

        if (project.getCompanyId() != persistence
                .getCompanyIdForUserAccount(managerId)) {
            throw new IllegalArgumentException(
                    "the project and coresponding project manager must have the same company id");
        }
    }

    /**
     * Verify if the project and the assigned workers has the same companyId
     *
     * @param project
     *            the project that must be checked
     * @param workersIds
     *            the Set with all the worker ids for current project
     * @throws PersistenceException -
     *             if something is not ok in the persistence layer
     *
     * @since 2.0
     */
    public void checkCompanyCorespondaceForWorkers(Project project,
            Set workersIds) throws PersistenceException {
        if (workersIds != null && workersIds.size() > 0) {
            for (Iterator iter = workersIds.iterator(); iter.hasNext();) {
                Integer userId = (Integer) iter.next();
                if (project.getCompanyId() != persistence
                        .getCompanyIdForUserAccount(userId.intValue())) {
                    throw new IllegalArgumentException(
                            "the project and all the workers assigned must have the same company id");
                }

            }
        }
    }

    /**
     * Verify if the project and the assigned time entries has the same
     * companyId
     *
     * @param project
     *            the project that must be checked
     * @param timeEntries
     *            the Set with all the timeEntry ids for current project
     * @throws PersistenceException -
     *             if something is not ok in the persistence layer
     *
     * @since 2.0
     */
    public void checkCompanyCorespondaceForTimeEntries(Project project,
            Set timeEntries) throws PersistenceException {
        if (timeEntries != null && timeEntries.size() > 0) {
            for (Iterator iter = timeEntries.iterator(); iter.hasNext();) {
                Integer timeEntryId = (Integer) iter.next();
                if (project.getCompanyId() != persistence
                        .getCompanyIdForTimeEntry(timeEntryId.intValue())) {
                    throw new IllegalArgumentException(
                            "the project and all the time entries assigned must have the same company id");
                }

            }
        }
    }

    /**
     * Verify if the project and the assigned expense entries has the same
     * companyId
     *
     * @param project
     *            the project that must be checked
     * @param expenseEntries
     *            the Set with all the expenseEntry ids for current project
     * @throws PersistenceException -
     *             if something is not ok in the persistence layer
     *
     * @since 2.0
     */
    public void checkCompanyCorespondaceForExpenseEntries(Project project,
            Set expenseEntries) throws PersistenceException {
        if (expenseEntries != null && expenseEntries.size() > 0) {
            for (Iterator iter = expenseEntries.iterator(); iter.hasNext();) {
                Integer expenseEntryId = (Integer) iter.next();
                if (project.getCompanyId() != persistence
                        .getCompanyIdForExpenseEntry(expenseEntryId.intValue())) {
                    throw new IllegalArgumentException(
                            "the project and all the time entries assigned must have the same company id");
                }

            }
        }
    }
}
