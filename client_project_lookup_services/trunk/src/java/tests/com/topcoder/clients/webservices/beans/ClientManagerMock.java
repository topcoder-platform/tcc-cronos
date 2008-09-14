/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.clients.manager.ClientEntityNotFoundException;
import com.topcoder.clients.manager.ClientManager;
import com.topcoder.clients.manager.ClientManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Mock implementation of the ClientManager interface.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientManagerMock implements ClientManager {

    /**
     * <p>
     * If set to true, retrieveClient operation will return instance which is visible only for administrators.
     * </p>
     */
    private static boolean returnRestricted = false;

    /**
     * <p>
     * If set to true, operations will throw ClientManagerException.
     * </p>
     */
    private static boolean failOnOperation = false;

    /**
     * <p>
     * Setter for failOnOperation field.
     * </p>
     *
     * @param fail
     *            value to set
     */
    public static void setFail(boolean fail) {
        failOnOperation = fail;
    }

    /**
     * <p>
     * Setter for returnRestricted field.
     * </p>
     *
     * @param restricted
     *            value to set
     */
    public static void setReturnRestricted(boolean restricted) {
        returnRestricted = restricted;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param client
     *            not used
     * @return null
     * @throws ClientManagerException
     *             not used
     */
    public Client createClient(Client client) throws ClientManagerException {
        return null;
    }

    /**
     * <p>
     * Creates client status.
     * </p>
     *
     * @param status
     *            status to create
     * @return created status
     * @throws ClientManagerException
     *             if failOnOperation is true
     */
    public ClientStatus createClientStatus(ClientStatus status) throws ClientManagerException {
        checkFail();
        return status;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param client
     *            not used
     * @return null
     * @throws ClientEntityNotFoundException
     *             not used
     * @throws ClientManagerException
     *             nod used
     */
    public Client deleteClient(Client client) throws ClientEntityNotFoundException, ClientManagerException {
        return null;
    }

    /**
     * <p>
     * Deletes passed status.
     * </p>
     *
     * @param status
     *            status to delete
     * @return deleted status
     * @throws ClientManagerException
     *             if failOnOperation is true
     * @throws ClientEntityNotFoundException
     *             if failOnOperation is true
     */
    public ClientStatus deleteClientStatus(ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException {
        checkFail();
        return status;
    }

    /**
     * <p>
     * Returns all statuses.
     * </p>
     *
     * @return list of statuses
     * @throws ClientManagerException
     *             if failOnOperation is true
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
     * @param id
     *            id of needed status
     * @return status with passed id
     * @throws ClientManagerException
     *             if failOnOperation is true
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
     * @param status
     *            status which found clients will contain
     * @return clients for given status.
     * @throws ClientManagerException
     *             if failOnOperation is true
     */
    public List<Client> getClientsForStatus(ClientStatus status) throws ClientManagerException {
        return retrieveAllClients();
    }

    /**
     * <p>
     * Gets all clients.
     * </p>
     *
     * @return all clients
     * @throws ClientManagerException
     *             if failOnOperation is true
     */
    public List<Client> retrieveAllClients() throws ClientManagerException {
        checkFail();

        List<Client> result = (new UserMappingRetrieverMock()).getClientsForUserId(0);
        result.add(new Client());

        return result;
    }

    /**
     * <p>
     * Gets client for given id.
     * </p>
     *
     * @param id
     *            for which to get client
     * @return found client
     * @throws ClientManagerException
     *             if failOnOperation is true
     */
    public Client retrieveClient(long id) throws ClientManagerException {
        checkFail();

        List<Client> clients = (new UserMappingRetrieverMock()).getClientsForUserId(id);

        Client result;
        if (returnRestricted) {
            result = new Client();
        } else {
            result = clients.get(0);
        }
        result.setId(id);

        return result;
    }

    /**
     * <p>
     * Gets clients which match given filter.
     * </p>
     *
     * @param filter
     *            filter to use
     * @return clients for given filter
     * @throws ClientManagerException
     *             if failOnOperation is true
     */
    public List<Client> searchClients(Filter filter) throws ClientManagerException {
        return retrieveAllClients();
    }

    /**
     * <p>
     * Gets clients for given name.
     * </p>
     *
     * @param name
     *            name for which clients will be found
     * @return clients with given name
     * @throws ClientManagerException
     *             if failOnOperation is true
     */
    public List<Client> searchClientsByName(String name) throws ClientManagerException {
        return retrieveAllClients();
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param client
     *            not used
     * @param codeName
     *            not used
     * @return null
     * @throws ClientManagerException
     *             not used
     * @throws ClientEntityNotFoundException
     *             not used
     */
    public Client setClientCodeName(Client client, String codeName) throws ClientManagerException,
            ClientEntityNotFoundException {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param client
     *            not used
     * @param status
     *            not used
     * @return null
     * @throws ClientManagerException
     *             not used
     * @throws ClientEntityNotFoundException
     *             not used
     */
    public Client setClientStatus(Client client, ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException {
        return null;
    }

    /**
     * <p>
     * Not used.
     * </p>
     *
     * @param client
     *            not used
     * @return null
     * @throws ClientManagerException
     *             not used
     * @throws ClientEntityNotFoundException
     *             not used
     */
    public Client updateClient(Client client) throws ClientManagerException, ClientEntityNotFoundException {
        return null;
    }

    /**
     * <p>
     * Updates passed status.
     * </p>
     *
     * @param status
     *            status to update
     * @return updated status
     * @throws ClientManagerException
     *             if failOnOperation is true
     * @throws ClientEntityNotFoundException
     *             if failOnOperation is true
     */
    public ClientStatus updateClientStatus(ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException {
        checkFail();
        return status;
    }

    /**
     * <p>
     * Throws ClientManagerException if failOnOperation flag is true.
     * </p>
     *
     * @throws ClientManagerException
     *             if failOnOperation flag is true
     */
    private void checkFail() throws ClientManagerException {
        if (failOnOperation) {
            throw new ClientManagerException("Test exception", null, null);
        }
    }

}
