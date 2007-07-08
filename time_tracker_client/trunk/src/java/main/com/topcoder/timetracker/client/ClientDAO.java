/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.Project;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;


/**
 * <p>
 * This interface specifies the contract for implementations of a Client DAOs. A ClientDAO is responsible for accessing
 * the database.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> Implementations of this interface should be thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public interface ClientDAO {
    /**
     * <p>
     * Add the given Client.
     * </p>
     *
     * @param client non null client to be added
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the client is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void addClient(Client client, boolean audit)
        throws ClientPersistenceException, ClientAuditException;

    /**
     * <p>
     * Add the given Clients into the database.
     * </p>
     *
     * @param clients non null, possible empty array containing non null contact to be added
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the array is null or containing null client
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void addClients(Client[] clients, boolean audit)
        throws ClientPersistenceException, ClientAuditException;

    /**
     * <p>
     * Retrieve the given Client.
     * </p>
     *
     * @param id the id of the Client
     *
     * @return the Client with given id, null if not found
     *
     * @throws IllegalArgumentException if the id not positive
     * @throws ClientPersistenceException if any exception occurs
     */
    public Client retrieveClient(long id) throws ClientPersistenceException;

    /**
     * <p>
     * Retrieve the Clients with given ids.
     * </p>
     *
     * @param ids non null, possible empty array containing id of client to be removed
     *
     * @return non null, possible empty array containing possible null clients
     *
     * @throws IllegalArgumentException if the array is null or any id is not positive
     * @throws ClientPersistenceException if any exception occurs
     */
    public Client[] retrieveClients(long[] ids) throws ClientPersistenceException;

    /**
     * <p>
     * Remove the Client with given id.
     * </p>
     *
     * @param id the id of the Client
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the id not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void removeClient(long id, boolean audit) throws ClientPersistenceException, ClientAuditException;

    /**
     * <p>
     * Remove the Clients with given ids.
     * </p>
     *
     * @param ids non null, possible empty array containing id of client to be removed
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the array is null or any not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void removeClients(long[] ids, boolean audit)
        throws ClientPersistenceException, ClientAuditException;

    /**
     * <p>
     * Update the given Client.
     * </p>
     *
     * @param client non null client to be updated
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the client is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void updateClient(Client client, boolean audit)
        throws ClientPersistenceException, ClientAuditException;

    /**
     * <p>
     * Update the given Clients.
     * </p>
     *
     * @param clients non null, possible empty array containing non null contact
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if the array is null or containing null client
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void updateClients(Client[] clients, boolean audit)
        throws ClientPersistenceException, ClientAuditException;

    /**
     * <p>
     * Retrieve all the Clients.
     * </p>
     *
     * @return non null, possible empty array containing all the clients
     *
     * @throws ClientPersistenceException if any exception occurs
     */
    public Client[] getAllClients() throws ClientPersistenceException;

    /**
     * <p>
     * Search the Clients with the given Filter and Depth.
     * </p>
     *
     * @param filter non null filter
     * @param depth non null depth
     *
     * @return non null result set
     *
     * @throws ClientPersistenceException if any exception occurs
     * @throws IllegalArgumentException if filter or depth is null
     */
    public CustomResultSet searchClient(Filter filter, Depth depth)
        throws ClientPersistenceException;

    /**
     * <p>
     * Add the project to the Client.
     * </p>
     *
     * @param client the id of the Client
     * @param project the non null project
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if project is null or the clientId not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void addProjectToClient(Client client, Project project, boolean audit)
        throws ClientPersistenceException, ClientAuditException;

    /**
     * <p>
     * Remove the project from the Client.
     * </p>
     *
     * @param client the id of client
     * @param projectId the id of project
     * @param audit whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any id not positive of instance null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if any error when audit
     */
    public void removeProjectFromClient(Client client, long projectId, boolean audit)
        throws ClientPersistenceException, ClientAuditException;

    /**
     * <p>
     * Get all ids of projects of the Client.
     * </p>
     *
     * @param clientId the id of the client
     *
     * @return the non null, possible empty array containing all project ids of the client
     *
     * @throws IllegalArgumentException if client id not positive
     * @throws ClientPersistenceException if any exception occurs
     */
    public long[] getAllProjectIDsOfClient(long clientId)
        throws ClientPersistenceException;

    /**
     * Gets all projects for the client specified by ID. Only the IDs/Names of the resulting
     * projects will be retrieved.
     * 
     * @return the non null, possibly empty array containing all projects of the client.
     * @param clientId
     *            the ID of the client.
     * @throws IllegalArgumentException
     *             if client ID not positive.
     * @throws ClientPersistenceException
     *             if any exception occurs.
     */
    public Project[] getProjectIDsNamesForClient(long clientId) throws ClientPersistenceException;
}
