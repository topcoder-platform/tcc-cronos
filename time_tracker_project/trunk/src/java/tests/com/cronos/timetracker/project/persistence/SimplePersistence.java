/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.project.persistence;

import com.cronos.timetracker.project.Client;
import com.cronos.timetracker.project.Project;
import com.cronos.timetracker.project.ProjectManager;
import com.cronos.timetracker.project.ProjectWorker;
import com.cronos.timetracker.project.searchfilters.Filter;

import java.util.List;

/**
 * A simple implementation of the TimeTrackerProjectPersistence interface for
 * testing. It has an additional method which returns the name of the last
 * method being called. The argument(s) passed to the method can also be
 * retrieved by corresponding getter methods.
 *
 * @author colau, costty000
 * @author TCSDEVELOPER
 * @version 2.0
 *
 * @since 1.0
 */
public class SimplePersistence implements TimeTrackerProjectPersistence {
    /**
     * The client passed as the argument of the last method call.
     */
    private Client lastClient = null;

    /**
     * The project passed as the argument of the last method call.
     */
    private Project lastProject = null;

    /**
     * The project manager passed as the argument of the last method call.
     */
    private ProjectManager lastManager = null;

    /**
     * The project worker passed as the argument of the last method call.
     */
    private ProjectWorker lastWorker = null;

    /**
     * The client id passed as the argument of the last method call.
     */
    private int lastClientId = -1;

    /**
     * The project id passed as the argument of the last method call.
     */
    private int lastProjectId = -1;

    /**
     * The project manager id passed as the argument of the last method call.
     */
    private int lastManagerId = -1;

    /**
     * The project worker id passed as the argument of the last method call.
     */
    private int lastWorkerId = -1;

    /**
     * The entry id passed as the argument of the last method call.
     */
    private int lastEntryId = -1;

    /**
     * The user passed as the argument of the last method call.
     */
    private String lastUser = null;

    /**
     * The client ids passed as the argument of the last method call.
     */
    private int[] lastClientIds = null;

    /**
     * The project ids passed as the argument of the last method call.
     */
    private int[] lastProjectIds = null;

    /**
     * The worker ids passed as the argument of the last method call.
     */
    private int[] lastWorkerIds = null;

    /**
     * The entry ids passed as the argument of the last method call.
     */
    private int[] lastEntryIds = null;

    /**
     * The clients passed as the argument of the last method call.
     */
    private Client[] lastClients = null;

    /**
     * The projects passed as the argument of the last method call.
     */
    private Project[] lastProjects = null;

    /**
     * The workers passed as the argument of the last method call.
     */
    private ProjectWorker[] lastWorkers = null;

    /**
     * The atomic boolean passed as the argument of the last method call.
     */
    private boolean lastAtomic = false;

    /**
     * The filter passed as the argument of the last method call.
     */
    private Filter lastFilter = null;

    private int lastClientIdForCompany = -1;

    private int lastProjectIdForCompany = -1;

    private int lastTimeEntryId = -1;

    private int lastExpenseEntryId = -1;

    private int lastUserAccountId = -1;

    private Client lastClientForNameSeacrh = null;

    private boolean lastWasNewForClientNameSeacrh = false;

    /**
     * The name of the last method being called.
     */
    private String lastMethod = null;

    /**
     * Creates a new instance of SimplePersistence.
     *
     * @param dbNamespace
     *            the namespace of the DB Connection Factory configuration file
     */
    public SimplePersistence(String dbNamespace) {
    }

    /**
     * Implementation of the addClient() method.
     *
     * @param client
     *            the client to add
     *
     * @return false
     */
    public boolean addClient(Client client) {
        lastMethod = "addClient";
        lastClient = client;
        return false;
    }

    /**
     * Implementation of the addExpenseEntry() method.
     *
     * @param entryId
     *            the id of the expense entry
     * @param projectId
     *            the id of the project
     * @param user
     *            the user who created the expense entry for the project
     *
     * @return false
     */
    public boolean addExpenseEntry(int entryId, int projectId, String user) {
        lastMethod = "addExpenseEntry";
        lastEntryId = entryId;
        lastProjectId = projectId;
        lastUser = user;
        return false;
    }

    /**
     * Implementation of the addProject() method.
     *
     * @param project
     *            the project to add
     *
     * @return false
     */
    public boolean addProject(Project project) {
        lastMethod = "addProject";
        lastProject = project;
        return false;
    }

    /**
     * Implementation of the addProjectToClient() method.
     *
     * @param clientId
     *            the id of the client
     * @param project
     *            the project to add to the client
     * @param user
     *            the user who assigned the client for the project
     *
     * @return false
     */
    public boolean addProjectToClient(int clientId, Project project, String user) {
        lastMethod = "addProjectToClient";
        lastClientId = clientId;
        lastProject = project;
        lastUser = user;
        return false;
    }

    /**
     * Implementation of the addTimeEntry() method.
     *
     * @param entryId
     *            the id of the time entry
     * @param projectId
     *            the id of the project
     * @param user
     *            the user who created the time entry for the project
     *
     * @return false
     */
    public boolean addTimeEntry(int entryId, int projectId, String user) {
        lastMethod = "addTimeEntry";
        lastEntryId = entryId;
        lastProjectId = projectId;
        lastUser = user;
        return false;
    }

    /**
     * Implementation of the addWorker() method.
     *
     * @param projectWorker
     *            the project worker to assign
     *
     * @return false
     */
    public boolean addWorker(ProjectWorker projectWorker) {
        lastMethod = "addWorker";
        lastWorker = projectWorker;
        return false;
    }

    /**
     * Implementation of the assignClient() method.
     *
     * @param projectId
     *            the id of the project
     * @param clientId
     *            the id of the client
     * @param user
     *            the user who assigned the client for the project
     *
     * @return false
     */
    public boolean assignClient(int projectId, int clientId, String user) {
        lastMethod = "assignClient";
        lastProjectId = projectId;
        lastClientId = clientId;
        lastUser = user;
        return false;
    }

    /**
     * Implementation of the assignProjectManager() method.
     *
     * @param projectManager
     *            the project manager to assign
     *
     * @return false
     */
    public boolean assignProjectManager(ProjectManager projectManager) {
        lastMethod = "assignProjectManager";
        lastManager = projectManager;
        return false;
    }

    /**
     * Implementation of the closeConnection() method.
     */
    public void closeConnection() {
        lastMethod = "closeConnection";
    }

    /**
     * Implementation of the getAllClientProjects() method.
     *
     * @param clientId
     *            the id of the client
     *
     * @return null
     */
    public List getAllClientProjects(int clientId) {
        lastMethod = "getAllClientProjects";
        lastClientId = clientId;
        return null;
    }

    /**
     * Implementation of the getAllClients() method.
     *
     * @return null
     */
    public List getAllClients() {
        lastMethod = "getAllClients";
        return null;
    }

    /**
     * Implementation of the getAllProjects() method.
     *
     * @return null
     */
    public List getAllProjects() {
        lastMethod = "getAllProjects";
        return null;
    }

    /**
     * Implementation of the getClient() method.
     *
     * @param clientId
     *            the id of the client
     *
     * @return null
     */
    public Client getClient(int clientId) {
        lastMethod = "getClient";
        lastClientId = clientId;
        return null;
    }

    /**
     * Implementation of the getClientProject() method.
     *
     * @param clientId
     *            the id of the client
     * @param projectId
     *            the id of the project
     *
     * @return null
     */
    public Project getClientProject(int clientId, int projectId) {
        lastMethod = "getClientProject";
        lastClientId = clientId;
        lastProjectId = projectId;
        return null;
    }

    /**
     * Implementation of the getExpenseEntries() method.
     *
     * @param projectId
     *            the id of the project
     *
     * @return null
     */
    public List getExpenseEntries(int projectId) {
        lastMethod = "getExpenseEntries";
        lastProjectId = projectId;
        return null;
    }

    /**
     * Implementation of the getProject() method.
     *
     * @param projectId
     *            the id of the project
     *
     * @return null
     */
    public Project getProject(int projectId) {
        lastMethod = "getProject";
        lastProjectId = projectId;
        return null;
    }

    /**
     * Implementation of the getProjectClient() method.
     *
     * @param projectId
     *            the id of the project
     *
     * @return null
     */
    public Client getProjectClient(int projectId) {
        lastMethod = "getProjectClient";
        lastProjectId = projectId;
        return null;
    }

    /**
     * Implementation of the getProjectManager() method.
     *
     * @param projectId
     *            the id of the project
     *
     * @return null
     */
    public ProjectManager getProjectManager(int projectId) {
        lastMethod = "getProjectManager";
        lastProjectId = projectId;
        return null;
    }

    /**
     * Implementation of the getTimeEntries() method.
     *
     * @param projectId
     *            the id of the project
     *
     * @return null
     */
    public List getTimeEntries(int projectId) {
        lastMethod = "getTimeEntries";
        lastProjectId = projectId;
        return null;
    }

    /**
     * Implementation of the getWorker() method.
     *
     * @param workerId
     *            the id of the worker
     * @param projectId
     *            the id of the project
     *
     * @return null
     */
    public ProjectWorker getWorker(int workerId, int projectId) {
        lastMethod = "getWorker";
        lastWorkerId = workerId;
        lastProjectId = projectId;
        return null;
    }

    /**
     * Implementation of the getWorkers() method.
     *
     * @param projectId
     *            the id of the project
     *
     * @return null
     */
    public List getWorkers(int projectId) {
        lastMethod = "getWorkers";
        lastProjectId = projectId;
        return null;
    }

    /**
     * Implementation of the removeAllClients() method.
     */
    public void removeAllClients() {
        lastMethod = "removeAllClients";
    }

    /**
     * Implementation of the removeAllProjects() method.
     */
    public void removeAllProjects() {
        lastMethod = "removeAllProjects";
    }

    /**
     * Implementation of the removeClient() method.
     *
     * @param clientId
     *            the id of the client
     *
     * @return false
     */
    public boolean removeClient(int clientId) {
        lastMethod = "removeClient";
        lastClientId = clientId;
        return false;
    }

    /**
     * Implementation of the removeExpenseEntry() method.
     *
     * @param entryId
     *            the id of the expense entry
     * @param projectId
     *            the id of the project
     *
     * @return false
     */
    public boolean removeExpenseEntry(int entryId, int projectId) {
        lastMethod = "removeExpenseEntry";
        lastEntryId = entryId;
        lastProjectId = projectId;
        return false;
    }

    /**
     * Implementation of the removeProject() method.
     *
     * @param projectId
     *            the id of the project
     *
     * @return false
     */
    public boolean removeProject(int projectId) {
        lastMethod = "removeProject";
        lastProjectId = projectId;
        return false;
    }

    /**
     * Implementation of the removeProjectFromClient() method.
     *
     * @param clientId
     *            the id of the client
     * @param projectId
     *            the id of the project
     *
     * @return false
     */
    public boolean removeProjectFromClient(int clientId, int projectId) {
        lastMethod = "removeProjectFromClient";
        lastClientId = clientId;
        lastProjectId = projectId;
        return false;
    }

    /**
     * Implementation of the removeProjectManager() method.
     *
     * @param managerId
     *            the id of the manager
     * @param projectId
     *            the id of the project
     *
     * @return false
     */
    public boolean removeProjectManager(int managerId, int projectId) {
        lastMethod = "removeProjectManager";
        lastManagerId = managerId;
        lastProjectId = projectId;
        return false;
    }

    /**
     * Implementation of the removeTimeEntry() method.
     *
     * @param entryId
     *            the id of the time entry
     * @param projectId
     *            the id of the project
     *
     * @return false
     */
    public boolean removeTimeEntry(int entryId, int projectId) {
        lastMethod = "removeTimeEntry";
        lastEntryId = entryId;
        lastProjectId = projectId;
        return false;
    }

    /**
     * Implementation of the removeWorker() method.
     *
     * @param workerId
     *            the id of the worker
     * @param projectId
     *            the id of the project
     *
     * @return false
     */
    public boolean removeWorker(int workerId, int projectId) {
        lastMethod = "removeWorker";
        lastWorkerId = workerId;
        lastProjectId = projectId;
        return false;
    }

    /**
     * Implementation of the removeWorkers() method.
     *
     * @param projectId
     *            the id of the project
     *
     * @return false
     */
    public boolean removeWorkers(int projectId) {
        lastMethod = "removeWorkers";
        lastProjectId = projectId;
        return false;
    }

    /**
     * Implementation of the updateClient() method.
     *
     * @param client
     *            the client to update
     *
     * @return false
     */
    public boolean updateClient(Client client) {
        lastMethod = "updateClient";
        lastClient = client;
        return false;
    }

    /**
     * Implementation of the updateProject() method.
     *
     * @param project
     *            the project to update
     *
     * @return false
     */
    public boolean updateProject(Project project) {
        lastMethod = "updateProject";
        lastProject = project;
        return false;
    }

    /**
     * Implementation of the updateWorker() method.
     *
     * @param projectWorker
     *            the worker to update
     *
     * @return false
     */
    public boolean updateWorker(ProjectWorker projectWorker) {
        lastMethod = "updateWorker";
        lastWorker = projectWorker;
        return false;
    }

    /**
     * Returns the client passed as the argument of the last method call.
     *
     * @return the client passed as the argument of the last method call
     */
    public Client getLastClient() {
        return lastClient;
    }

    /**
     * Returns the project passed as the argument of the last method call.
     *
     * @return the project passed as the argument of the last method call
     */
    public Project getLastProject() {
        return lastProject;
    }

    /**
     * Returns the project manager passed as the argument of the last method
     * call.
     *
     * @return the project manager passed as the argument of the last method
     *         call
     */
    public ProjectManager getLastManager() {
        return lastManager;
    }

    /**
     * Returns the project worker passed as the argument of the last method
     * call.
     *
     * @return the project worker passed as the argument of the last method call
     */
    public ProjectWorker getLastWorker() {
        return lastWorker;
    }

    /**
     * Returns the client id passed as the argument of the last method call.
     *
     * @return the client id passed as the argument of the last method call
     */
    public int getLastClientId() {
        return lastClientId;
    }

    /**
     * Returns the project id passed as the argument of the last method call.
     *
     * @return the project id passed as the argument of the last method call
     */
    public int getLastProjectId() {
        return lastProjectId;
    }

    /**
     * Returns the project manager id passed as the argument of the last method
     * call.
     *
     * @return the project manager id passed as the argument of the last method
     *         call
     */
    public int getLastManagerId() {
        return lastManagerId;
    }

    /**
     * Returns the project worker id passed as the argument of the last method
     * call.
     *
     * @return the project worker id passed as the argument of the last method
     *         call
     */
    public int getLastWorkerId() {
        return lastWorkerId;
    }

    /**
     * Returns the entry id passed as the argument of the last method call.
     *
     * @return the entry id passed as the argument of the last method call
     */
    public int getLastEntryId() {
        return lastEntryId;
    }

    /**
     * Returns the user passed as the argument of the last method call.
     *
     * @return the user passed as the argument of the last method call
     */
    public String getLastUser() {
        return lastUser;
    }

    /**
     * Returns the client ids passed as the argument of the last method call.
     *
     * @return the client ids passed as the argument of the last method call
     */
    public int[] getLastClientIds() {
        return lastClientIds;
    }

    /**
     * Returns the project ids passed as the argument of the last method call.
     *
     * @return the project ids passed as the argument of the last method call
     */
    public int[] getLastProjectIds() {
        return lastProjectIds;
    }

    /**
     * Returns the worker ids passed as the argument of the last method call.
     *
     * @return the worker ids passed as the argument of the last method call
     */
    public int[] getLastWorkerIds() {
        return lastWorkerIds;
    }

    /**
     * Returns the entry ids passed as the argument of the last method call.
     *
     * @return the entry ids passed as the argument of the last method call
     */
    public int[] getLastEntryIds() {
        return lastEntryIds;
    }

    /**
     * Returns the clients passed as the argument of the last method call.
     *
     * @return the clients passed as the argument of the last method call
     */
    public Client[] getLastClients() {
        return lastClients;
    }

    /**
     * Returns the projects passed as the argument of the last method call.
     *
     * @return the projects passed as the argument of the last method call
     */
    public Project[] getLastProjects() {
        return lastProjects;
    }

    /**
     * Returns the workers passed as the argument of the last method call.
     *
     * @return the workers passed as the argument of the last method call
     */
    public ProjectWorker[] getLastWorkers() {
        return lastWorkers;
    }

    /**
     * Returns the atomic boolean passed as the argument of the last method
     * call.
     *
     * @return the atomic boolean passed as the argument of the last method call
     */
    public boolean getLastAtomic() {
        return lastAtomic;
    }

    /**
     * Returns the filter passed as the argument of the last method call.
     *
     * @return the filter passed as the argument of the last method call
     */
    public Filter getLastFilter() {
        return lastFilter;
    }

    /**
     * Returns the name of the last method being called.
     *
     * @return the name of the last method being called
     */
    public String getLastMethod() {
        return lastMethod;
    }

    /**
     * Implementation of the updateClients() method.
     *
     * @param clients
     *            the clients to update.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void updateClients(Client[] clients, boolean atomic) {
        lastMethod = "updateClients";
        lastClients = clients;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the addClients() method.
     *
     * @param clients
     *            the clients to add.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void addClients(Client[] clients, boolean atomic) {
        lastMethod = "addClients";
        lastClients = clients;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the updateProjects() method.
     *
     * @param projects
     *            the projects to update.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void updateProjects(Project[] projects, boolean atomic) {
        lastMethod = "updateProjects";
        lastProjects = projects;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the addProjects() method.
     *
     * @param projects
     *            the projects to add.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void addProjects(Project[] projects, boolean atomic) {
        lastMethod = "addProjects";
        lastProjects = projects;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the searchForProjects() method.
     *
     * @param searchFilter
     *            the search filter to match.
     *
     * @return always null.
     */
    public Project[] searchForProjects(Filter searchFilter) {
        lastMethod = "searchForProjects";
        lastFilter = searchFilter;
        return null;
    }

    /**
     * Implementation of the searchForClients() method.
     *
     * @param searchFilter
     *            the search filter to match.
     *
     * @return always null.
     */
    public Client[] searchForClients(Filter searchFilter) {
        lastMethod = "searchForClients";
        lastFilter = searchFilter;
        return null;
    }

    /**
     * Implementation of the addExpenseEntries() method.
     *
     * @param entryIds
     *            the ids of the expense entries to add.
     * @param projectId
     *            the id of the project.
     * @param user
     *            the user who created the expense entries for the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void addExpenseEntries(int[] entryIds, int projectId, String user,
            boolean atomic) {
        lastMethod = "addExpenseEntries";
        lastEntryIds = entryIds;
        lastProjectId = projectId;
        lastUser = user;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the addTimeEntries() method.
     *
     * @param entryIds
     *            the ids of the time entries to add.
     * @param projectId
     *            the id of the project.
     * @param user
     *            the user who created the time entries for the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void addTimeEntries(int[] entryIds, int projectId, String user,
            boolean atomic) {
        lastMethod = "addTimeEntries";
        lastEntryIds = entryIds;
        lastProjectId = projectId;
        lastUser = user;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the getClients() method.
     *
     * @param clientIds
     *            the ids of the clients to retrieve.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @return always null.
     */
    public Client[] getClients(int[] clientIds, boolean atomic) {
        lastMethod = "getClients";
        lastClientIds = clientIds;
        lastAtomic = atomic;
        return null;
    }

    /**
     * Implementation of the getProjects() method.
     *
     * @param projectIds
     *            the ids of the projects to retrieve.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @return always null.
     */
    public Project[] getProjects(int[] projectIds, boolean atomic) {
        lastMethod = "getProjects";
        lastProjectIds = projectIds;
        lastAtomic = atomic;
        return null;
    }

    /**
     * Implementation of the getWorkers() method.
     *
     * @param workerIds
     *            the ids of the workers to retrieve.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @return always null.
     */
    public ProjectWorker[] getWorkers(int[] workerIds, int projectId,
            boolean atomic) {
        lastMethod = "getWorkers";
        lastWorkerIds = workerIds;
        lastProjectId = projectId;
        lastAtomic = atomic;
        return null;
    }

    /**
     * Implementation of the removeClients() method.
     *
     * @param clientIds
     *            the ids of clients to delete.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void removeClients(int[] clientIds, boolean atomic) {
        lastMethod = "removeClients";
        lastClientIds = clientIds;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the removeExpenseEntries() method.
     *
     * @param entryIds
     *            the ids of the expense entries to delete.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void removeExpenseEntries(int[] entryIds, int projectId,
            boolean atomic) {
        lastMethod = "removeExpenseEntries";
        lastEntryIds = entryIds;
        lastProjectId = projectId;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the removeProjects() method.
     *
     * @param projectIds
     *            the ids of the projects to delete.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void removeProjects(int[] projectIds, boolean atomic) {
        lastMethod = "removeProjects";
        lastProjectIds = projectIds;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the removeTimeEntries() method.
     *
     * @param entryIds
     *            the ids of the time entries to delete.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void removeTimeEntries(int[] entryIds, int projectId, boolean atomic) {
        lastMethod = "removeTimeEntries";
        lastEntryIds = entryIds;
        lastProjectId = projectId;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the removeWorkers() method.
     *
     * @param workerIds
     *            the ids of the workers to delete.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void removeWorkers(int[] workerIds, int projectId, boolean atomic) {
        lastMethod = "removeWorkers";
        lastWorkerIds = workerIds;
        lastProjectId = projectId;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the updateWorkers() method.
     *
     * @param workers
     *            the workers to update.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void updateWorkers(ProjectWorker[] workers, boolean atomic) {
        lastMethod = "updateWorkers";
        lastWorkers = workers;
        lastAtomic = atomic;
    }

    /**
     * Implementation of the addWorkers() method.
     *
     * @param workers
     *            the project workers to assign.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     */
    public void addWorkers(ProjectWorker[] workers, boolean atomic) {
        lastMethod = "addWorkers";
        lastWorkers = workers;
        lastAtomic = atomic;
    }

    /**
     * Implementation for getCompanyIdForClient
     */
    public int getCompanyIdForClient(int clientId) throws PersistenceException {
        lastMethod = "getCompanyIdForClient";
        lastClientIdForCompany = clientId;
        return 1;
    }

    /**
     * Implementation for getCompanyIdForProject
     */
    public int getCompanyIdForProject(int projectId)
            throws PersistenceException {
        lastMethod = "getCompanyIdForProject";
        lastProjectIdForCompany = projectId;
        return 1;
    }

    /**
     * Implementation for getCompanyIdForTimeEntry
     */
    public int getCompanyIdForTimeEntry(int timeEntryId)
            throws PersistenceException {
        lastMethod = "getCompanyIdForTimeEntry";
        lastTimeEntryId = timeEntryId;
        return 1;
    }

    /**
     * Implementation for getCompanyIdForExpenseEntry
     */
    public int getCompanyIdForExpenseEntry(int expenseEntryId)
            throws PersistenceException {
        lastMethod = "getCompanyIdForExpenseEntry";
        lastExpenseEntryId = expenseEntryId;
        return 1;
    }

    /**
     * Implementation for getCompanyIdForUserAccount
     */
    public int getCompanyIdForUserAccount(int userAccountId)
            throws PersistenceException {
        lastMethod = "getCompanyIdForUserAccount";
        lastUserAccountId = userAccountId;
        return 1;
    }

    /**
     * Implementation for existClientWithNameForCompany
     */
    public boolean existClientWithNameForCompany(Client client, boolean isNew)
            throws PersistenceException {
        lastMethod = "existClientWithNameForCompany";
        lastClientForNameSeacrh = client;
        lastWasNewForClientNameSeacrh = isNew;
        return false;
    }
}
