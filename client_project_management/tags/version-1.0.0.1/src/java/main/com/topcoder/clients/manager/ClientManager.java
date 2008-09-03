/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager;

import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.search.builder.filter.Filter;

/**
 *
 * This interface defines functionality for managing client information, including CRUD functionality for clients and
 * ClientStatuses, searching for clients by name and a provided search filter, and getting clients by status,
 * as well as directly updating a client's status and code name.
 *
 * Thread Safety: implementations of this interface should be thread safe.
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public interface ClientManager {
    /**
     * Creates a new Client. After creation, a new ID will be generated for it.
     *
     * @param client
     *            the new Client to create, can not be null (should have startDate, endDate , and startDate should be
     *            before endDate; its name should be non-null and non-empty string and isDeleted should be false)
     *
     * @return the Client instance created, with new ID assigned
     * @throws IllegalArgumentException
     *             if the client is invalid (e.g. null, no startDate, endDate, and startDate is not before endDate,
     *             name is null or empty, isDeleted is true)
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer, or in ID generator
     */
    public Client createClient(Client client) throws ClientManagerException;

    /**
     * Updates a Client. Error will be thrown if the client to update does not exist in persistence.
     *
     * @param client
     *            the new Client to create, can not be null (should have startDate, endDate , and startDate should be
     *            before endDate; its name should be non-null and non-empty string and isDeleted should be false)
     *
     * @return the updated Client
     * @throws IllegalArgumentException
     *             if the client is invalid (e.g. null, no startDate, endDate, and startDate is not before endDate,
     *             name is null or empty, isDeleted is true)
     * @throws ClientEntityNotFoundException
     *             if the client to be updated does not exist in persistence
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Client updateClient(Client client) throws ClientManagerException, ClientEntityNotFoundException;

    /**
     * Deletes given Client.
     *
     *
     * @param client
     *            the client to delete, can't be null, isDeleted should be false
     * @return the deleted client, with isDeleted set to true
     * @throws IllegalArgumentException
     *             if client is null or it's isDeleted is true
     * @throws ClientEntityNotFoundException
     *             if the client to be updated does not exist in persistence
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Client deleteClient(Client client) throws ClientEntityNotFoundException, ClientManagerException;

    /**
     * Sets the code name of given client, and forward this update to the persistence.
     *
     *
     * @param codeName
     *            the code name to set, can't be null or empty
     * @param client
     *            the new Client to create, can not be null (should have startDate, endDate , and startDate should be
     *            before endDate; its name should be non-null and non-empty string and isDeleted should be false)
     *
     * @return the updated Client
     * @throws IllegalArgumentException
     *             if any argument is invalid (e.g. null, no startDate, endDate, and startDate is not before endDate,
     *             name is null or empty, isDeleted is true, or codeName is null or empty)
     * @throws ClientEntityNotFoundException
     *             if the client to be updated does not exist in persistence
     * @throws ClientManagerException
     *             f any error occurred during this operation, e.g. error in persistence layer
     */
    public Client setClientCodeName(Client client, String codeName) throws ClientManagerException,
            ClientEntityNotFoundException;

    /**
     * Sets the status of given client, and forward this update to the persistence.
     *
     *
     * @param status
     *            the client status to set, can't be null, (name non-null and non-empty, description non-null and
     *            non-empty and isDeleted false)
     * @param client
     *            the new Client to create, can not be null (should have startDate, endDate , and startDate should be
     *            before endDate; its name should be non-null and non-empty string and isDeleted should be false)
     *
     * @return the updated Client
     * @throws IllegalArgumentException
     *             if any argument is invalid (client is null, no startDate, endDate,
     *             and startDate is not before endDate,
     *             name is null or empty, isDeleted is true, or the status is null, status'name is null or empty,
     *             or status'description is null or empty, or status's isDeleted is true)
     * @throws ClientEntityNotFoundException
     *             if the client to be updated does not exist in persistence
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Client setClientStatus(Client client, ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException;

    /**
     * Gets the Client by its ID.
     *
     * @param id
     *            id of the Client to retrieve, can't be negative
     * @return the Client with given ID, null if none with given ID exists
     * @throws IllegalArgumentException
     *             if id is negative
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Client retrieveClient(long id) throws ClientManagerException;

    /**
     * Gets all the Clients. If none exists, empty list will be returned.
     *
     *
     * @return a list of Client objects, empty list if none client exists
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Client> retrieveAllClients() throws ClientManagerException;

    /**
     * Finds the Clients that match given name. Empty list will be returned if none is found.
     *
     * @param name
     *            name of the Clients to search, can't be null or empty
     * @return a list of Clients that match given name, empty list if none is found
     * @throws IllegalArgumentException
     *             if the name is null or empty
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Client> searchClientsByName(String name) throws ClientManagerException;

    /**
     * Finds the clients with specified filter. Empty list will be returned if none is found.
     *
     *
     * @param filter
     *            the filter used to search for Clients, can't be null
     *
     * @return a list of Clients that match the filter, empty list if none is found
     * @throws IllegalArgumentException
     *             if parameter filter is null
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Client> searchClients(Filter filter) throws ClientManagerException;

    /**
     * Gets the ClientStatus with given ID.
     *
     *
     * @param id
     *            ID of the ClientStatus to retrieve, can't be negative
     * @return a ClientStatus matching given ID, or null if none with given ID exists
     *
     * @throws IllegalArgumentException
     *             if id is negative
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public ClientStatus getClientStatus(long id) throws ClientManagerException;

    /**
     * Gets all the client status. If none exists, empty list will be returned.
     *
     *
     * @return a list of ClientStatus objects, empty list if none exists
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<ClientStatus> getAllClientStatuses() throws ClientManagerException;

    /**
     * Gets all Clients that match given client status. Empty list will be returned if none if found.
     *
     * @param status
     *            a ClientStatus instance, can't be null, id should not be negative
     *
     * @return a list Client objects, empty list if none is found.
     *
     * @throws IllegalArgumentException
     *             if clientStatus is null, or its id is negative
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Client> getClientsForStatus(ClientStatus status) throws ClientManagerException;

    /**
     * Create a new ClientStatus, a new id will be generated for it.
     *
     * @param status
     *            the client status to set, can't be null, (name non-null and non-empty, description non-null and
     *            non-empty and isDeleted false)
     *
     * @return the created ClientStatus with new ID assigned.
     * @throws IllegalArgumentException
     *             if argument is invalid (null, status'name is null or empty, status'description is null or empty)
     * @throws ClientManagerException
     *             f any error occurred during this operation, e.g. error in persistence layer, or in ID generator
     */
    public ClientStatus createClientStatus(ClientStatus status) throws ClientManagerException;

    /**
     * Updates a ClientStatus. Error will be thrown if the client status to update does not exist in persistence.
     *
     *
     * @param status
     *            the client status to set, can't be null, (name non-null and non-empty, description non-null and
     *            non-empty and isDeleted false)
     * @return the updated ClientStatus
     * @throws IllegalArgumentException
     *             if status is invalid. (null, status'name is null or empty, status'description is null or empty)
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     * @throws ClientEntityNotFoundException
     *             f the ClientStatus to be updated does not exist in persistence
     */
    public ClientStatus updateClientStatus(ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException;

    /**
     * Deletes given ClientStatus.
     *
     *
     * @param status
     *            the client status to delete, can't be null, isDeleted should be false
     * @return the deleted client status, with isDeleted set to true
     *
     * @throws IllegalArgumentException
     *             if client status is null or it's isDeleted is true
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     * @throws ClientEntityNotFoundException
     *             if the ClientStatus to be updated does not exist in persistence
     */
    public ClientStatus deleteClientStatus(ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException;
}
