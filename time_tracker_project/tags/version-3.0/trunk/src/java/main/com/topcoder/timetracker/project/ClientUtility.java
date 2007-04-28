/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import java.util.List;

import com.topcoder.timetracker.project.persistence.BatchOperationException;
import com.topcoder.timetracker.project.persistence.PersistenceException;
import com.topcoder.timetracker.project.persistence.TimeTrackerProjectPersistence;
import com.topcoder.timetracker.project.searchfilters.Filter;

/**
 * <p>
 * The ClientUtility is useful to manage the clients through the persistence
 * provided in the constructor. It can do the following things:
 *
 * <ul>
 * <li> add a client to the Clients table </li>
 * <li> delete a client from the Clients table </li>
 * <li> delete all the clients from the Clients table </li>
 * <li> retrieve a client from the Clients table </li>
 * <li> retrieve all the clients from the Clients table </li>
 * <li> update a client in the Clients table </li>
 * <li> add a project to a client </li>
 * <li> retrieve a certain project of a client </li>
 * </ul>
 * </p>
 *
 * <p>
 * Since version 1.1 the following functionalities are added:
 *
 * <ul>
 * <li> process the above CRUD operations in a batch </li>
 * <li> search clients with a given search filter </li>
 * </ul>
 * </p> . The version 2.0 added verifications regarding to relation of clients
 * with a companies.
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
public class ClientUtility {
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
     * Represents the underlying persistence to manage the clients. It will be
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
    public ClientUtility(ProjectPersistenceManager persistenceManager) {
        if (persistenceManager == null) {
            throw new NullPointerException("persistenceManager is null");
        }
        this.persistenceManager = persistenceManager;
        persistence = persistenceManager.getPersistence();
    }

    /**
     * <p>
     * Adds a new Client instance to the database. If the argument is null
     * NullPointerException will be thrown. If the id of the argument is -1 then
     * an id for the client will be generated using GUID Generator component. If
     * the id is different than -1 then it means that the user(application) has
     * assigned an id for this client. Using the persistenceManager field this
     * method will call the addClient method from the persistence. If the id of
     * the client is contained by the Clients table false will be returned. If
     * the argument is null NullPointerException will be thrown. All the
     * projects contained by the client instance will be added to the
     * ClientProjects table. All the entries, workers, manager from each table
     * will be added to the coresponding tables. If anything wrong happens while
     * adding the client to the database, PersistenceException will be thrown.
     * If one of the fields: name, creationUser or modificationUser are null
     * InsufficientDataException will be thrown. As specified on the forum
     * creationDate and modificationDate will be handled automaticaly by the
     * component( the curent datetime will be used). These two fields should be
     * null when calling this method. If one of these two fields is not null
     * IllegalArgumentException will be thrown.
     *
     * Since version 2.0: If the client doesn't have set the companyId field
     * (has the value -1) then InsufficientDataException will be thrown.
     *
     * If the client has a valid companyId (not -1), and the client has projects
     * it is made a validation that all the projects must have the same
     * companyId set as the client. If exist at least only one project that has
     * a different companyId that the client has then a IllegalArgumentException
     * will be thrown.
     *
     * If the name of the client already exist in the database for the same
     * companyId that currect client has then a IllegalArgumentException will be
     * thrown.
     * </p>
     *
     * From version 2.0 If the client doesn't have set the companyId field (has
     * the value -1) then InsufficientDataException will be thrown.
     *
     * If the client has a valid companyId (not -1), and the client has projects
     * it is made a validation that all the projects must have the same
     * companyId set as the client. If exist at least only one project that has
     * a different companyId that the client has then a IllegalArgumentException
     * will be thrown.
     *
     * If the name of the client already exist in the database for the same
     * companyId that currect client has then a IllegalArgumentException will be
     * thrown.
     * </p>
     *
     *
     * @return true if the client has been added to the database; false
     *         otherwise
     * @param client
     *            the client to be added to the database
     * @throws NullPointerException -
     *             if the argument is null
     * @throws PersistenceException
     *             wraps a persistence implementation specific exception (such
     *             as SQL exception)
     * @throws InsufficientDataException -
     *             name, creationUser or modificationUser are null; the client
     *             doesn't have set the companyId field (has the value -1)
     * @throws IllegalArgumentException -
     *             if creationDate is not null and/or modificationDate is not
     *             null; If exist at least only one project (from the projects
     *             that this client has) that has a different companyId that the
     *             client has; If the name of the client already exist in the
     *             database for the same companyId that currect client has
     */
    public boolean addClient(Client client) throws InsufficientDataException,
            PersistenceException {
        Util.checkAddClient(client);
        // added in version 2.0
        Util.checkCompanyCorespondace(client, client.getProjects());
        // check to see if exist a company with specified id
        checkUniqueClientNameForCompany(client, true);
        return persistence.addClient(client);
    }

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
    public boolean removeClient(int clientId) throws PersistenceException {
        return persistence.removeClient(clientId);
    }

    /**
     * <p>
     * Deletes all clients from the database.
     * </p>
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the clients (such
     *             as SQL exception)
     */
    public void removeAllClients() throws PersistenceException {
        persistence.removeAllClients();
    }

    /**
     * <p>
     * Updates a client in the database. If the client does not exist in the
     * database false will be returned.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null)
     * before passing it to the underlying persistence.
     * </p>
     *
     * Since version 2.0: If the client doesn't have set the companyId field
     * (has the value -1) then InsufficientDataException will be thrown.
     *
     * If the client has a valid companyId (not -1), and the client has projects
     * it is made a validation that all the projects must have the same
     * companyId set as the client. If exist at least only one project that has
     * a different companyId that the client has then a IllegalArgumentException
     * will be thrown. The projects for this validation will be getted from
     * database not from the Client parameter since this projects will not be
     * saved among with the client.
     *
     * If the name of the client already exist in the database for the same
     * companyId that currect client has (if it is found a different client then
     * the current client with the same name) then a IllegalArgumentException
     * will be thrown.
     * </p>
     *
     *
     * @return true if the client was updated; false if a type with the id of
     *         the argument type does not exist in the database
     * @param client
     *            the client to be updated
     * @throws NullPointerException -
     *             if the argument is null
     * @throws PersistenceException
     *             wraps a persistence implementation specific exception (such
     *             as SQL exception)
     * @throws InsufficientDataException -
     *             if one of the fields name, creationUser or modificationUser
     *             are null; If the client doesn't have set the companyId field
     *             (has the value -1)
     * @throws IllegalArgumentException -
     *             If the client has a valid companyId (not -1), and the client
     *             has projects it is made a validation that all the projects
     *             must have the same companyId set as the client. If exist at
     *             least only one project that has a different companyId that
     *             the client has then a IllegalArgumentException will be
     *             thrown. The projects for this validation will be getted from
     *             database not from the Client parameter since this projects
     *             will not be saved among with the client.; If the name of the
     *             client already exist in the database for the same companyId
     *             that currect client has (for a different client then the
     *             current client) then a IllegalArgumentException will be
     *             thrown.
     */

    public boolean updateClient(Client client)
            throws InsufficientDataException, PersistenceException {
        Util.checkUpdateClient(client);
        // added in version 2.0
        Util.checkCompanyCorespondace(client, persistence
                .getAllClientProjects(client.getId()));
        // check to see if already exist a client with the same name, excluding
        // the current client
        checkUniqueClientNameForCompany(client, false);
        return persistence.updateClient(client);
    }

    /**
     * <p>
     * Adds a project to a client. If the project already has a client assigned
     * false will be returned.
     * </p>
     *
     * <p>
     * If the id of the given project is -1, an id will be generated using GUID
     * Generator. This method also ensures that all necessary fields are filled
     * (not null) before passing it to the underlying persistence.
     * </p>
     * Since version 2.0:
     * <p>
     * If existing client doesn't have the same companyId like the project has
     * then the IllegalArgumentException will be thrown. If the project given as
     * parameter is new then the companyId for project it is taken from
     * parameter. If the project given as parameter already exist in databases
     * then the companyId it is taken from the project existing in database.
     * </p>
     *
     *
     * @return true if the project was added, false otherwise
     * @param clientId
     *            the id of the client to which the project will be added
     * @param project
     *            the project that will be added to the client
     * @param user
     *            this value will be used to populate the creationUser and
     *            modificationUser columns
     * @throws IllegalArgumentException -
     *             if the user is the empty string; If existing client doesn't
     *             have the same companyId like the project has (If the project
     *             given as parameter is new then the companyId for project it
     *             is taken from parameter. If the project given as parameter
     *             already exist in databases then the companyId it is taken
     *             from the project existing in database).
     * @throws NullPointerException
     *             if project or user is null
     * @throws PersistenceException
     *             wraps a persistence implementation specific exception (such
     *             as SQL exception)
     * @throws InsufficientDataException -
     *             If all the fields except CreationDate and modificationDate,
     *             are not populated
     */
    public boolean addProjectToClient(int clientId, Project project, String user)
            throws InsufficientDataException, PersistenceException {
        Util.checkAddProject(project);
        int companyForClient = persistence.getCompanyIdForClient(clientId);
        int companyForProject = persistence.getCompanyIdForProject(project
                .getId());
        if (companyForProject == -1) {
            companyForProject = project.getCompanyId();
        }
        Util.checkCompanyCorespondace(companyForClient, companyForProject);
        return persistence.addProjectToClient(clientId, project, user);
    }

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
    public boolean removeProjectFromClient(int clientId, int projectId)
            throws PersistenceException {
        return persistence.removeProjectFromClient(clientId, projectId);
    }

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
    public Project getClientProject(int clientId, int projectId)
            throws PersistenceException {
        return persistence.getClientProject(clientId, projectId);
    }

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
    public List getAllClientProjects(int clientId) throws PersistenceException {
        return persistence.getAllClientProjects(clientId);
    }

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
    public Client getClient(int clientId) throws PersistenceException {
        return persistence.getClient(clientId);
    }

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
    public Client[] searchClients(Filter searchFilter)
            throws PersistenceException {
        return persistence.searchForClients(searchFilter);
    }

    /**
     * <p>
     * Adds the clients to the database. If the id of any client is used, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * <p>
     * If the id of any client is -1, an id will be generated using GUID
     * Generator. This method also ensures that all necessary fields are filled
     * (not null) before passing it to the underlying persistence.
     * </p>
     *
     * Since version 2.0: If one of the client from the array of clients doesn't
     * have set the companyId field (has the value -1) then
     * InsufficientDataException will be thrown.
     *
     * If one of the client from the array of clients has a valid companyId (not
     * -1), and that client has projects it is made a validation that all the
     * projects must have the same companyId set as the client. If exist at
     * least only one project that has a different companyId that the client has
     * then a IllegalArgumentException will be thrown.
     *
     * If the name for one of the client from the array of clients already exist
     * in the database for the same companyId that currect client has then a
     * IllegalArgumentException will be thrown.
     * </p>
     *
     * @param clients
     *            the array of Client instances to add to the database, should
     *            not be null, elements should not be null
     * @param atomic
     *            specifies if the operation should be atomic (all-or-nothing)
     * @throws IllegalArgumentException
     *             if "clients" array is null or zero-length, or invalid because
     *             of the other reasons; If one of the client from the array of
     *             clients has a valid companyId (not -1), and that client has
     *             projects it is made a validation that all the projects must
     *             have the same companyId set as the client. If exist at least
     *             only one project that has a different companyId that the
     *             client has then a IllegalArgumentException will be thrown; If
     *             the name for one of the client from the array of clients
     *             already exist in the database for the same companyId that
     *             currect client has then a IllegalArgumentException will be
     *             thrown.
     * @throws BatchOperationException
     *             if any error happens on the persistence level
     * @throws InsufficientDataException
     *             if one of the fields of any particular client: name,
     *             creationUser or modificationUser is null; If one of the
     *             client from the array of clients doesn't have set the
     *             companyId field (has the value -1) then
     *             InsufficientDataException will be thrown
     */
    public void addClients(Client[] clients, boolean atomic)
            throws InsufficientDataException, BatchOperationException {
        Util.checkArray(clients);
        for (int i = 0; i < clients.length; i++) {
            Util.checkAddClient(clients[i]);

            // added in version 2.0
            Util.checkCompanyCorespondace(clients[i], clients[i].getProjects());

            // check the uniqueness of names for every clients in the clients array
            try {
                checkUniqueClientNameForCompany(clients[i], true);
            } catch (PersistenceException ex) {
                throw new BatchOperationException(
                        "Error checking if the name is unique for client in a company",
                        new Throwable[] { ex });
            }
        }
        persistence.addClients(clients, atomic);
    }

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
    public void removeClients(int[] clientIds, boolean atomic)
            throws BatchOperationException {
        persistence.removeClients(clientIds, atomic);
    }

    /**
     * <p>
     * Updates the clients in the database. If any client does not exist in the
     * database, a BatchOperationException will be thrown.
     * </p>
     *
     * <p>
     * This method ensures that all necessary fields are filled (not null)
     * before passing it to the underlying persistence.
     * </p>
     *
     * Since version 2.0:
     * <p>
     * If one of the client from the array given as parameter doesn't have set
     * the companyId field (has the value -1) then InsufficientDataException
     * will be thrown.
     *
     * If one of the client from the array given as parameter has a valid
     * companyId (not -1), and that client has projects it is made a validation
     * that all the projects must have the same companyId set as the client. If
     * exist at least only one project that has a different companyId that the
     * client has then a IllegalArgumentException will be thrown. The projects
     * for this validation will be getted from database not from the Client
     * parameter since this projects will not be saved among with the client.
     *
     * If the name of one of the client from the array given as parameter
     * already exist in the database for the same companyId that currect client
     * has (if it is found a different client then the current client with the
     * same name) then a IllegalArgumentException will be thrown.
     * </p>
     *
     *
     *
     * @param clients
     *            the array of Clients to update, should not be null, elements
     *            should not be null
     * @param atomic
     *            specifies if the operation should be atomic (all-or-nothing)
     * @throws IllegalArgumentException
     *             if "clients" array is null or zero-length, or contains null
     *             elements; If one of the client from the array given as
     *             parameter has a valid companyId (not -1), and that client has
     *             projects it is made a validation that all the projects must
     *             have the same companyId set as the client. If exist at least
     *             only one project that has a different companyId that the
     *             client has then a IllegalArgumentException will be thrown.
     *             The projects for this validation will be getted from database
     *             not from the Client parameter since this projects will not be
     *             saved among with the client.; If the name of one of the
     *             client from the array given as parameter already exist in the
     *             database for the same companyId that currect client has (if
     *             it is found a different client then the current client with
     *             the same name) then a IllegalArgumentException will be
     *             thrown.
     * @throws BatchOperationException
     *             if any error happens on the persistence level
     * @throws InsufficientDataException
     *             if one of the fields of any particular client: name,
     *             creationUser or modificationUser is null; If one of the
     *             client from the array given as parameter doesn't have set the
     *             companyId field (has the value -1) then
     *             InsufficientDataException will be thrown
     * @throws PersistenceException
     *             if any error happens when checking the uniqueness of the clients
     *             based on the client name
     *
     * @see #updateClient(Client)
     * @since 1.1
     */
    public void updateClients(Client[] clients, boolean atomic)
            throws InsufficientDataException, BatchOperationException {
        Util.checkArray(clients);
        for (int i = 0; i < clients.length; i++) {
            Util.checkUpdateClient(clients[i]);
            // check the uniqueness of names for every clients in the clients array
            try {
                checkUniqueClientNameForCompany(clients[i], false);
            } catch (PersistenceException ex) {
                throw new BatchOperationException(
                        "Error checking to see if name is unique for client in a company",
                        new Throwable[] { ex });
            }
        }
        persistence.updateClients(clients, atomic);
    }

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
    public Client[] getClients(int[] clientIds, boolean atomic)
            throws BatchOperationException {
        return persistence.getClients(clientIds, atomic);
    }

    /**
     * Check if exists in the database a client with the same for the same
     * company as the client has
     *
     * @param client
     *            the client that is checked
     * @param isNew
     *            specify if the client already exists in the databaseor not
     * @throws PersistenceException
     *             if something is not ok in the persistance layer
     *
     * @since 2.0
     */
    private void checkUniqueClientNameForCompany(Client client, boolean isNew)
            throws PersistenceException {
        boolean existInDatabase = persistence.existClientWithNameForCompany(
                client, isNew);
        if (existInDatabase) {
            throw new IllegalArgumentException(
                    "Already exist a client with specified name");
        }
    }
}
