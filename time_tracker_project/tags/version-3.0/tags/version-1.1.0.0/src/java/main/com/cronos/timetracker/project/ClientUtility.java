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
 * The ClientUtility is useful to manage the clients through the persistence provided in the constructor. It can do the
 * following things:
 *
 * <ul>
 * <li>
 * add a client to the Clients table
 * </li>
 * <li>
 * delete a client from the Clients table
 * </li>
 * <li>
 * delete all the clients from the Clients table
 * </li>
 * <li>
 * retrieve a client from the Clients table
 * </li>
 * <li>
 * retrieve all the clients from the Clients table
 * </li>
 * <li>
 * update a client in the Clients table
 * </li>
 * <li>
 * add a project to a client
 * </li>
 * <li>
 * retrieve a certain project of a client
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
 * search clients with a given search filter
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
public class ClientUtility {
    /**
     * <p>
     * Represents an instance of TimeTrackerProjectPersistenceManager class. It will be used to retrieve the
     * persistence using the getPersistence() method. It will be initialized in the constructor. It cannot be null.
     * </p>
     */
    private ProjectPersistenceManager persistenceManager = null;

    /**
     * <p>
     * Represents the underlying persistence to manage the clients. It will be obtained by the the getPersistence()
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
    public ClientUtility(ProjectPersistenceManager persistenceManager) {
        if (persistenceManager == null) {
            throw new NullPointerException("persistenceManager is null");
        }
        this.persistenceManager = persistenceManager;
        persistence = persistenceManager.getPersistence();
    }

    /**
     * <p>
     * Adds a new client to the database. If the id of the client is used false will be returned.
     * </p>
     *
     * <p>
     * If the id of the given client is -1, an id will be generated using GUID Generator. This method also ensures that
     * all necessary fields are filled (not null) before passing it to the underlying persistence.
     * </p>
     *
     * @param client the client to add
     *
     * @return true if the client was added, false otherwise
     *
     * @throws NullPointerException if the client is null
     * @throws IllegalArgumentException if creationDate or modificationDate is not null
     * @throws InsufficientDataException if name, creationUser or modificationUser is null
     * @throws PersistenceException if something wrong happens while adding the client (such as SQL exception)
     */
    public boolean addClient(Client client) throws InsufficientDataException, PersistenceException {
        Util.checkAddClient(client);
        return persistence.addClient(client);
    }

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
    public boolean removeClient(int clientId) throws PersistenceException {
        return persistence.removeClient(clientId);
    }

    /**
     * <p>
     * Deletes all clients from the database.
     * </p>
     *
     * @throws PersistenceException if something wrong happens while removing the clients (such as SQL exception)
     */
    public void removeAllClients() throws PersistenceException {
        persistence.removeAllClients();
    }

    /**
     * <p>
     * Updates a client in the database. If the client does not exist in the database false will be returned.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null) before passing it to the underlying
     * persistence.
     * </p>
     *
     * @param client the client to update
     *
     * @return true if the client was updated, false otherwise
     *
     * @throws NullPointerException if the client is null
     * @throws InsufficientDataException if name, creationUser or modificationUser is null
     * @throws PersistenceException if something wrong happens while updating the client (such as SQL exception)
     */
    public boolean updateClient(Client client) throws InsufficientDataException, PersistenceException {
        Util.checkUpdateClient(client);
        return persistence.updateClient(client);
    }

    /**
     * <p>
     * Adds a project to a client. If the project already has a client assigned false will be returned.
     * </p>
     *
     * <p>
     * If the id of the given project is -1, an id will be generated using GUID Generator. This method also ensures
     * that all necessary fields are filled (not null) before passing it to the underlying persistence.
     * </p>
     *
     * @param clientId the id of the client
     * @param project the project to add to the client
     * @param user the user who assigned the client for the project
     *
     * @return true if the project was added to the client, false otherwise
     *
     * @throws NullPointerException if the project or user is null
     * @throws IllegalArgumentException if the user is the empty string, or creationDate or modificationDate is not
     *         null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     * @throws PersistenceException if something wrong happens while adding the project to the client (such as SQL
     *         exception)
     */
    public boolean addProjectToClient(int clientId, Project project, String user)
        throws InsufficientDataException, PersistenceException {
        Util.checkAddProject(project);
        return persistence.addProjectToClient(clientId, project, user);
    }

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
    public boolean removeProjectFromClient(int clientId, int projectId)
        throws PersistenceException {
        return persistence.removeProjectFromClient(clientId, projectId);
    }

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
    public Project getClientProject(int clientId, int projectId)
        throws PersistenceException {
        return persistence.getClientProject(clientId, projectId);
    }

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
    public List getAllClientProjects(int clientId) throws PersistenceException {
        return persistence.getAllClientProjects(clientId);
    }

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
    public Client getClient(int clientId) throws PersistenceException {
        return persistence.getClient(clientId);
    }

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
    public List getAllClients() throws PersistenceException {
        return persistence.getAllClients();
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
     * Searches the clients from the database which matches the given search filter. If there are no such clients in
     * the database, an empty array will be returned.
     * </p>
     *
     * <p>
     * The fields of the Client objects will be properly populated.
     * </p>
     *
     * @param searchFilter the search filter to match.
     *
     * @return the array of matching clients.
     *
     * @throws IllegalArgumentException if the searchFilter is null, or the searchFilter is not supported, or no column
     *         name is found for the column alias.
     * @throws PersistenceException if something wrong happens while searching the clients (such as SQL exception)
     *
     * @since 1.1
     */
    public Client[] searchClients(Filter searchFilter)
        throws PersistenceException {
        return persistence.searchForClients(searchFilter);
    }

    /**
     * <p>
     * Adds the clients to the database. If the id of any client is used, a BatchOperationException will be thrown.
     * </p>
     *
     * <p>
     * If the id of any client is -1, an id will be generated using GUID Generator. This method also ensures that all
     * necessary fields are filled (not null) before passing it to the underlying persistence.
     * </p>
     *
     * @param clients the clients to add.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the clients is null or empty, or any of its elements is null.
     * @throws InsufficientDataException if name, creationUser or modificationUser is null.
     * @throws BatchOperationException if something wrong happens while adding the clients in a batch.
     *
     * @see #addClient(Client)
     * @since 1.1
     */
    public void addClients(Client[] clients, boolean atomic)
        throws InsufficientDataException, BatchOperationException {
        Util.checkArray(clients);
        for (int i = 0; i < clients.length; i++) {
            Util.checkAddClient(clients[i]);
        }
        persistence.addClients(clients, atomic);
    }

    /**
     * <p>
     * Deletes the clients from the database. If a client with any specified id does not exist, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param clientIds the ids of clients to delete.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the clientIds is null or empty.
     * @throws BatchOperationException if something wrong happens while deleting the clients in a batch.
     *
     * @see #removeClient(int)
     * @since 1.1
     */
    public void removeClients(int[] clientIds, boolean atomic)
        throws BatchOperationException {
        persistence.removeClients(clientIds, atomic);
    }

    /**
     * <p>
     * Updates the clients in the database. If any client does not exist in the database, a BatchOperationException
     * will be thrown.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null) before passing it to the underlying
     * persistence.
     * </p>
     *
     * @param clients the clients to update.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException if the clients is null or empty, or any of its elements is null.
     * @throws InsufficientDataException if name, creationUser or modificationUser is null.
     * @throws BatchOperationException if something wrong happens while updating the clients in a batch.
     *
     * @see #updateClient(Client)
     * @since 1.1
     */
    public void updateClients(Client[] clients, boolean atomic)
        throws InsufficientDataException, BatchOperationException {
        Util.checkArray(clients);
        for (int i = 0; i < clients.length; i++) {
            Util.checkUpdateClient(clients[i]);
        }
        persistence.updateClients(clients, atomic);
    }

    /**
     * <p>
     * Retrieves the clients with the specified clientIds from the database. If any client does not exist, a
     * BatchOperationException will be thrown if it fails to retrieve at least one client (in atomic mode), or if it
     * fails to retrieve all clients (in non-atomic mode). When the operation is not atomic, and at least one client
     * was successfully retrieved, the returned array will contain nulls in the places where the clients cannot be
     * retrieved.
     * </p>
     *
     * <p>
     * The fields of the Client objects will be properly populated.
     * </p>
     *
     * @param clientIds the ids of the clients to retrieve.
     * @param atomic whether the operation should be atomic (all-or-nothing).
     *
     * @return the clients with the given ids.
     *
     * @throws IllegalArgumentException if the clientIds is null or empty.
     * @throws BatchOperationException if something wrong happens while retrieving the clients in a batch.
     *
     * @see #getClient(int)
     * @since 1.1
     */
    public Client[] getClients(int[] clientIds, boolean atomic)
        throws BatchOperationException {
        return persistence.getClients(clientIds, atomic);
    }
}
