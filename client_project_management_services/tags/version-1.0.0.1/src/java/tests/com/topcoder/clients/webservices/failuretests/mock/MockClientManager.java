/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.failuretests.mock;

import com.topcoder.clients.manager.ClientEntityNotFoundException;
import com.topcoder.clients.manager.ClientManager;
import com.topcoder.clients.manager.ClientManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;

import com.topcoder.search.builder.filter.Filter;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Mock implementation of the ClientManager interface.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class MockClientManager implements ClientManager {
    /**
     * <p>
     * If set to true, operations will throw ClientManagerException.
     * </p>
     */
    private static boolean throwCME = false;

    /**
     * <p>
     * If set to true, operations will throw IllegalArgumentException.
     * </p>
     */
    private static boolean throwIAE = false;

    /**
     * <p>
     * Setter for throwCME field.
     * </p>
     *
     * @param fail value to set
     */
    public static void throwCME(boolean fail) {
        throwCME = fail;
    }

    /**
     * <p>
     * Setter for throwIAE field.
     * </p>
     *
     * @param fail value to set
     */
    public static void throwIAE(boolean fail) {
        throwIAE = fail;
    }

    /**
     * <p>
     * Create the client.
     * </p>
     *
     * @param client client to create
     *
     * @return created client
     *
     * @throws ClientManagerException if throwCME is true
     */
    public Client createClient(Client client) throws ClientManagerException {
        checkFail();

        return client;
    }

    /**
     * <p>
     * Creates client status.
     * </p>
     *
     * @param status status to create
     *
     * @return created status
     *
     * @throws ClientManagerException if throwCME is true
     */
    public ClientStatus createClientStatus(ClientStatus status)
        throws ClientManagerException {
        checkFail();

        return status;
    }

    /**
     * <p>
     * Delete the client.
     * </p>
     *
     * @param client the client to be deleted.
     *
     * @return the deleted client
     *
     * @throws ClientEntityNotFoundException won't throw
     * @throws ClientManagerException if throwCME is true
     */
    public Client deleteClient(Client client) throws ClientEntityNotFoundException, ClientManagerException {
        checkFail();
        client.setDeleted(true);

        return client;
    }

    /**
     * <p>
     * Deletes passed status.
     * </p>
     *
     * @param status status to delete
     *
     * @return deleted status
     *
     * @throws ClientManagerException if throwCME is true
     * @throws ClientEntityNotFoundException won't throw
     */
    public ClientStatus deleteClientStatus(ClientStatus status)
        throws ClientManagerException, ClientEntityNotFoundException {
        checkFail();

        return status;
    }

    /**
     * <p>
     * Returns all statuses.
     * </p>
     *
     * @return list of statuses
     *
     * @throws ClientManagerException if failOnOperation is true
     */
    public List<ClientStatus> getAllClientStatuses() throws ClientManagerException {
        checkFail();

        ClientStatus s1 = new ClientStatus();
        s1.setDescription("test1");

        ClientStatus s2 = new ClientStatus();
        s2.setDescription("test2");

        List<ClientStatus> result = new ArrayList<ClientStatus>();
        result.add(s1);
        result.add(s2);

        return result;
    }

    /**
     * <p>
     * Gets status by id.
     * </p>
     *
     * @param id id of needed status
     *
     * @return status with passed id
     *
     * @throws ClientManagerException if failOnOperation is true
     */
    public ClientStatus getClientStatus(long id) throws ClientManagerException {
        checkFail();

        ClientStatus result = new ClientStatus();
        result.setId(id);

        return result;
    }

    /**
     * <p>
     * Gets clients for given status.
     * </p>
     *
     * @param status status which found clients will contain
     *
     * @return clients for given status.
     *
     * @throws ClientManagerException if failOnOperation is true
     */
    public List<Client> getClientsForStatus(ClientStatus status)
        throws ClientManagerException {
        return retrieveAllClients();
    }

    /**
     * <p>
     * Gets all clients.
     * </p>
     *
     * @return null
     *
     * @throws ClientManagerException won't throw
     */
    public List<Client> retrieveAllClients() throws ClientManagerException {
        return null;
    }

    /**
     * <p>
     * Gets client for given id.
     * </p>
     *
     * @param id for which to get client
     *
     * @return null
     *
     * @throws ClientManagerException won't be thrown
     */
    public Client retrieveClient(long id) throws ClientManagerException {
        return null;
    }

    /**
     * <p>
     * Gets clients which match given filter.
     * </p>
     *
     * @param filter filter to use
     *
     * @return clients for given filter
     *
     * @throws ClientManagerException if failOnOperation is true
     */
    public List<Client> searchClients(Filter filter) throws ClientManagerException {
        return retrieveAllClients();
    }

    /**
     * <p>
     * Gets clients for given name.
     * </p>
     *
     * @param name name for which clients will be found
     *
     * @return clients with given name
     *
     * @throws ClientManagerException if failOnOperation is true
     */
    public List<Client> searchClientsByName(String name)
        throws ClientManagerException {
        return retrieveAllClients();
    }

    /**
     * <p>
     * Set the client code name.
     * </p>
     *
     * @param client the client whose code name will be set
     * @param codeName the set name
     *
     * @return the client
     *
     * @throws ClientManagerException if throwCME is true
     * @throws ClientEntityNotFoundException won't throw
     */
    public Client setClientCodeName(Client client, String codeName)
        throws ClientManagerException, ClientEntityNotFoundException {
        checkFail();
        client.setCodeName(codeName);

        return client;
    }

    /**
     * <p>
     * Set the status of the client.
     * </p>
     *
     * @param client the client whose status will be set
     * @param status the status to be set
     *
     * @return the client
     *
     * @throws ClientManagerException if throwCME is true
     * @throws ClientEntityNotFoundException won't throw
     */
    public Client setClientStatus(Client client, ClientStatus status)
        throws ClientManagerException, ClientEntityNotFoundException {
        checkFail();
        client.setClientStatus(status);

        return client;
    }

    /**
     * <p>
     * Update the client.
     * </p>
     *
     * @param client the client to be updated.
     *
     * @return the updated client
     *
     * @throws ClientManagerException if throwCME is true
     * @throws ClientEntityNotFoundException won't throw
     */
    public Client updateClient(Client client) throws ClientManagerException, ClientEntityNotFoundException {
        checkFail();

        return client;
    }

    /**
     * <p>
     * Updates passed status.
     * </p>
     *
     * @param status status to update
     *
     * @return updated status
     *
     * @throws ClientManagerException if throwCME is true
     * @throws ClientEntityNotFoundException won't throw
     */
    public ClientStatus updateClientStatus(ClientStatus status)
        throws ClientManagerException, ClientEntityNotFoundException {
        checkFail();

        return status;
    }

    /**
     * <p>
     * Throws ClientManagerException or IllegalArgumentException if flag is true.
     * </p>
     *
     * @throws ClientManagerException if throwCME is true
     * @throws IllegalArgumentException if throwIAE is true
     */
    private void checkFail() throws ClientManagerException {
        if (throwCME) {
            throw new ClientManagerException("Test exception", null, null);
        }

        if (throwIAE) {
            throw new IllegalArgumentException("Test exception");
        }
    }
}
