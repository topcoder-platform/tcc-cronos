/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.Project;


/**
 * <p>
 * This provides the interface by which callers may manipulate the Time Tracker Client entity..  It provides various
 * methods to retrieve and modify the client information.  And it provides method to allow whether audit on the
 * specified method.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> Implementations should be thread-safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public interface ClientUtility {
    /**
     * <p>
     * Add the given Client along with the associated projects, and associate the address manager and
     * contact manager.
     * </p>
     *
     * @param client non null client to be added
     * @param doAudit whether audit this action
     *
     * @throws IllegalArgumentException if the client is null
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws InvalidClientIDException if any exception occurs while generating ID
     * @throws InvalidClientPropertyException if the properties of given Client is invalid
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void addClient(Client client, boolean doAudit)
        throws ClientPersistenceException, InvalidClientIDException, InvalidClientPropertyException,
            ClientAuditException;

    /**
     * <p>
     * Add the given Clients along with the associated projects, and associate the address manager and
     * contact manager.
     * </p>
     *
     * @param clients non null, possible empty clients to be added
     * @param doAudit whether audit on this action
     *
     * @throws InvalidClientPropertyException if the properties of given Client is invalid
     * @throws IllegalArgumentException if the client is null or any client contained is null
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws InvalidClientIDException if any exception occurs while generating ID
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void addClients(Client[] clients, boolean doAudit)
        throws ClientPersistenceException, InvalidClientIDException, InvalidClientPropertyException,
            ClientAuditException;

    /**
     * <p>
     * Remove the Client with given id. Remove the associated projects and de-associate from the contact manager
     * and address manager.
     * </p>
     *
     * @param id the id of the Client
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if the id not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any error occurred
     */
    public void removeClient(long id, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException, PropertyOperationException;

    /**
     * <p>
     * Remove the Clients with given ids. Remove the associated projects and de-associate from the contact manager
     * and address manager.
     * </p>
     *
     * @param ids the non null, possible empty positive ids of the Clients
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if any id not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any operation with property failed
     */
    public void removeClients(long[] ids, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException, PropertyOperationException;

    /**
     * <p>
     * Retrieve the given Client. With project information, contact information, address information.
     * </p>
     *
     * @param id the id of the client to be retrieved
     *
     * @return the client with the given id, null if the client if not found
     *
     * @throws IllegalArgumentException if the id not positive
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws PropertyOperationException if it encounters exception when trying to get the properties
     */
    public Client retrieveClient(long id) throws ClientPersistenceException, PropertyOperationException;

    /**
     * <p>
     * Retrieve the Clients with given ids. Also retrieve the payment term information and Contact and
     * Address information.
     * </p>
     *
     * @param ids the non null, possible empty ids of the Clients
     *
     * @return the non null Clients with given id, the containing client may be null if not found
     *
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws IllegalArgumentException if any id not positive
     * @throws PropertyOperationException if it encounters exception when trying to get the properties
     */
    public Client[] retrieveClients(long[] ids) throws ClientPersistenceException, PropertyOperationException;

    /**
     * <p>
     * Update the given Client.
     * </p>
     *
     * @param client non null client to be updated
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if the client is null
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws InvalidClientPropertyException if the properties of given Client is invalid
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any operation with property failed
     */
    public void updateClient(Client client, boolean doAudit)
        throws ClientPersistenceException, InvalidClientPropertyException, ClientAuditException,
            PropertyOperationException;

    /**
     * <p>
     * Update the given Clients.
     * </p>
     *
     * @param clients non null, possible empty clients to be updated
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if any client is null
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws InvalidClientPropertyException if the properties of given Client is invalid
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any operation with property failed
     */
    public void updateClients(Client[] clients, boolean doAudit)
        throws ClientPersistenceException, InvalidClientPropertyException, ClientAuditException,
            PropertyOperationException;

    /**
     * <p>
     * Retrieve all the Clients. Set the contact, address, payment.
     * </p>
     *
     * @return Non null, possible empty array containing all non null clients
     *
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws PropertyOperationException if it encounters exception when trying to get the properties
     */
    public Client[] getAllClients() throws ClientPersistenceException, PropertyOperationException;

    /**
     * <p>
     * Search the Clients with the given Filter and Depth. Set the corresponding property such as payment contact.
     * </p>
     *
     * @param filter non null filter
     * @param depth non null depth
     *
     * @return non null, possible empty array containing all client with given condition
     *
     * @throws ClientPersistenceException if any exception occurs
     * @throws IllegalArgumentException if filter or depth is null
     * @throws PropertyOperationException if it encounters exception when trying to get the properties
     */
    public Client[] searchClient(Filter filter, Depth depth)
        throws PropertyOperationException, ClientPersistenceException;

    /**
     * <p>
     * Get all projects of the Client.
     * </p>
     *
     * @param clientId the id of the client
     *
     * @return the non null, possible empty projects of the client
     *
     * @throws IllegalArgumentException if client id not positive
     * @throws ClientPersistenceException if any exception occurs
     */
    public Project[] getAllProjectsOfClient(long clientId)
        throws ClientPersistenceException;

    /**
     * <p>
     * Remove the project from the Client.
     * </p>
     *
     * @param client the client
     * @param projectId the id of project
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if any id is not positive or arugment is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void removeProjectFromClient(Client client, long projectId, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException;

    /**
     * <p>
     * Add the project to the Client.
     * </p>
     *
     * @param client the Client
     * @param project the non null project
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if any argument is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void addProjectToClient(Client client, Project project, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException;
}
