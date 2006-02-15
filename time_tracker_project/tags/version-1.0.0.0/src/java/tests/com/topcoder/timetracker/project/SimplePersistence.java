/*
 * SimplePersistence.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;

import java.util.List;


/**
 * A simple implementation of the TimeTrackerProjectPersistence interface for testing. It has an additional method
 * which returns the name of the last method being called. The argument(s) passed to the method can also be retrieved
 * by corresponding getter methods.
 *
 * @author TCSDEVELOPER
 * @version 1.0
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
     * The name of the last method being called.
     */
    private String lastMethod = null;

    /**
     * Creates a new instance of SimplePersistence.
     */
    public SimplePersistence() {
    }

    /**
     * Implementation of the addClient() method.
     *
     * @param client the client to add
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
     * @param entryId the id of the expense entry
     * @param projectId the id of the project
     * @param user the user who created the expense entry for the project
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
     * @param project the project to add
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
     * @param clientId the id of the client
     * @param project the project to add to the client
     * @param user the user who assigned the client for the project
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
     * @param entryId the id of the time entry
     * @param projectId the id of the project
     * @param user the user who created the time entry for the project
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
     * @param projectWorker the project worker to assign
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
     * @param projectId the id of the project
     * @param clientId the id of the client
     * @param user the user who assigned the client for the project
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
     * @param projectManager the project manager to assign
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
     * @param clientId the id of the client
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
     * @param clientId the id of the client
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
     * @param clientId the id of the client
     * @param projectId the id of the project
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
     * @param projectId the id of the project
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
     * @param projectId the id of the project
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
     * @param projectId the id of the project
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
     * @param projectId the id of the project
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
     * @param projectId the id of the project
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
     * @param workerId the id of the worker
     * @param projectId the id of the project
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
     * @param projectId the id of the project
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
     * @param clientId the id of the client
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
     * @param entryId the id of the expense entry
     * @param projectId the id of the project
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
     * @param projectId the id of the project
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
     * @param clientId the id of the client
     * @param projectId the id of the project
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
     * @param managerId the id of the manager
     * @param projectId the id of the project
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
     * @param entryId the id of the time entry
     * @param projectId the id of the project
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
     * @param workerId the id of the worker
     * @param projectId the id of the project
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
     * @param projectId the id of the project
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
     * @param client the client to update
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
     * @param project the project to update
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
     * @param projectWorker the worker to update
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
     * Returns the project manager passed as the argument of the last method call.
     *
     * @return the project manager passed as the argument of the last method call
     */
    public ProjectManager getLastManager() {
        return lastManager;
    }

    /**
     * Returns the project worker passed as the argument of the last method call.
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
     * Returns the project manager id passed as the argument of the last method call.
     *
     * @return the project manager id passed as the argument of the last method call
     */
    public int getLastManagerId() {
        return lastManagerId;
    }

    /**
     * Returns the project worker id passed as the argument of the last method call.
     *
     * @return the project worker id passed as the argument of the last method call
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
     * Returns the name of the last method being called.
     *
     * @return the name of the last method being called
     */
    public String getLastMethod() {
        return lastMethod;
    }
}
