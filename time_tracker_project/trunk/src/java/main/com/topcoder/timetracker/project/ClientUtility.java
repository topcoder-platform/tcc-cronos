/*
 * ClientUtility.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project;

import com.topcoder.timetracker.project.persistence.PersistenceException;
import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;

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
 * This class performs some check on the arguments before passing them to the underlying persistence and generates ids
 * for clients and projects if they are not specified.
 * </p>
 *
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
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

        // if id is -1, generate an id for it
        if (client.getId() == -1) {
            client.setId(Util.getNextInt());
        }
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
     * @throws IllegalArgumentException if the user is the empty string, or creationDate or modificationDate is not null
     * @throws InsufficientDataException if any field except creationDate and modificationDate is null
     * @throws PersistenceException if something wrong happens while adding the project to the client (such as SQL
     *         exception)
     */
    public boolean addProjectToClient(int clientId, Project project, String user)
        throws InsufficientDataException, PersistenceException
    {
        Util.checkAddProject(project);
        Util.checkString(user);

        // if id is -1, generate an id for it
        if (project.getId() == -1) {
            project.setId(Util.getNextInt());
        }
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
        throws PersistenceException
    {
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
        throws PersistenceException
    {
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
}
