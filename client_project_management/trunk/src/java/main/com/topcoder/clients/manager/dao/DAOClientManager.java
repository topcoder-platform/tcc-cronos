/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.dao;

import java.util.List;

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.dao.ClientStatusDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ejb3.ClientDAOBean;
import com.topcoder.clients.dao.ejb3.ClientStatusDAOBean;
import com.topcoder.clients.manager.ClientEntityNotFoundException;
import com.topcoder.clients.manager.ClientManager;
import com.topcoder.clients.manager.ClientManagerException;
import com.topcoder.clients.manager.ManagerConfigurationException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * This is class is an implementation of ClientManager interface.
 *
 * <p>
 * It provides convenient methods to manage Client and ClientStatus entities. All operations will eventually delegate
 * to the underlying ClientDAO and ClientStatusDAO appropriately.
 * </p>
 *
 * <p>
 * It uses IDGenerator to generate new IDs for entities, and has a logger used to log method entry/exit and all
 * exceptions. It can be configured via ConfigurationObject directly, as well as configuration file compatible with
 * the format defined in Configuration Persistence component.
 * </p>
 *
 * <p>
 * For checking whether the entity already exists in persistence, we call retrieveClient(id), if non-null entity is
 * returned, then the entity exists. This will affect the performance a bit, but we can not get the correct result by
 * just checking the ID.
 * </p>
 *
 * <p>
 * <strong>Usage Sample:</strong>
 * <pre>
 *      // the configuration for DAOClientManager
 *      ConfigurationObject obj = new DefaultConfigurationObject(&quot;root&quot;);
 *      obj.setPropertyValue(&quot;id_generator_name&quot;, &quot;client_id&quot;);
 *      obj.setPropertyValue(&quot;logger_name&quot;, &quot;System.out&quot;);
 *
 *      ConfigurationObject child = new DefaultConfigurationObject(&quot;object_factory_configuration&quot;);
 *
 *      obj.addChild(child);
 *      obj.setPropertyValue(&quot;client_dao&quot;, &quot;com.topcoder.clients.manager.MockClientDAO&quot;);
 *      obj.setPropertyValue(&quot;client_status_dao&quot;,
 *       &quot;com.topcoder.clients.manager.MockClientStatusDAO&quot;);
 *
 *      // create an instance of DAOClientManager by default
 *      DAOClientManager manager = new DAOClientManager();
 *
 *      // create an instance of DAOClientManager with ConfigurationObject
 *      manager = new DAOClientManager(obj);
 *
 *      // create an instance of DAOClientManager with configuration file
 *      String configFile = &quot;test_files/daoclient.properties&quot;;
 *      manager = new DAOClientManager(configFile, &quot;daoClient&quot;);
 *
 *      // retrieve a client with id
 *      Client client = manager.retrieveClient(1);
 *
 *      // retrieve all clients
 *      List&lt;Client&gt; clients = manager.retrieveAllClients();
 *
 *      // search client for specified name
 *      clients = manager.searchClientsByName(&quot;clent-1&quot;);
 *
 *      // search clients with Company-1, create a filter for this
 *      EqualToFilter filter = new EqualToFilter(&quot;Company-1&quot;, new Boolean(false));
 *
 *      clients = manager.searchClients(filter);
 *
 *      // set new code name to Client
 *      manager.setClientCodeName(client, &quot;new code name&quot;);
 *
 *      // get client status with id
 *      ClientStatus status = manager.getClientStatus(3);
 *
 *      // get clients with status
 *      clients = manager.getClientsForStatus(status);
 *
 *      // set new status to client
 *      manager.setClientStatus(client, status);
 *
 *      // update the client
 *      client.setSalesTax(100.121);
 *      manager.updateClient(client);
 *      // deletes the client
 *      manager.deleteClient(client);
 *      // create new client to persist.
 *      Client newClient = new Client();
 *      newClient.setStartDate(new Date(System.currentTimeMillis() - 1000000000L));
 *      newClient.setEndDate(new Date());
 *      newClient.setSalesTax(10.0);
 *      newClient.setName(&quot;newClient&quot;);
 *
 *      // create a new client
 *      manager.createClient(newClient);
 *
 *      // updates the status
 *      status.setName(&quot;pending&quot;);
 *      // deletes the status
 *      manager.deleteClientStatus(status);
 *      ClientStatus newStatus = new ClientStatus();
 *      newStatus.setName(&quot;newStatus&quot;);
 *      newStatus.setDescription(&quot;des&quot;);
 *      newStatus.setDeleted(false);
 *      // create a new Client status
 *      manager.createClientStatus(newStatus);
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread-safe. All fields do not change after construction. Inner ClientDAO,
 * ClientStatusDAO, IDGenerator and Log are effectively thread-safe too.
 * </p>
 *
 *
 * @author moonli, Chenhong
 * @version 1.0
 */
public class DAOClientManager extends AbstractDAOManager implements ClientManager {

    /**
     * Represents the name of this class. It will be used when do the entrance log and exist log.
     */
    private static final String CLASSNAME = DAOClientManager.class.getName();

    /**
     * Represents the object_factory_configuration property key when retrieving child ConfigurationObject to create
     * ObjectFactory instance.
     */
    private static final String OBJECT_FACTORY_CONFIGURATION = "object_factory_configuration";

    /**
     * Represents the client_dao property key.
     */
    private static final String CLIENT_DAO = "client_dao";

    /**
     * Represents the client_status_dao property key.
     */
    private static final String CLIENT_STATUS_DAO = "client_status_dao";

    /**
     * Represents the a instance of ClientDAO, it provides the persistence APIs for managing Client entities.
     * <p>
     * It's set in the constructor, and do not change afterwards, can't be null. It's used to
     * create/update/delete/search Client entities.
     * </p>
     */
    private final ClientDAO clientDAO;

    /**
     * Represents the a instance of ClientStatusDAO, it provides the persistence APIs for managing ClientStatus
     * entities.
     *
     * <p>
     * It's set in the constructor, and do not change afterwards, can't be null. It's used to
     * create/update/delete/search ClientStatus entities.
     * </p>
     */
    private final ClientStatusDAO clientStatusDAO;

    /**
     * Constructs an instance of this class by default.
     *
     * @throws ManagerConfigurationException
     *             if error occurred when creating IDGenerator instance
     */
    public DAOClientManager() throws ManagerConfigurationException {
        clientDAO = new ClientDAOBean();

        clientStatusDAO = new ClientStatusDAOBean();
    }

    /**
     * Constructs an instance of this class with given configuration object.
     *
     * @param configuration
     *            the ConfigurationObject used to configure this class, can't be null
     *
     * @throws IllegalArgumentException
     *             if configuration is null
     * @throws ManagerConfigurationException
     *             if error occurred when configuring this class, e.g. required configuration value is missing,
     *             or error occurred in object factory
     *
     */
    public DAOClientManager(ConfigurationObject configuration) throws ManagerConfigurationException {
        super(configuration);

        ConfigurationObject objFactoryConfig = Helper.getChildConfigurationObject(configuration,
                OBJECT_FACTORY_CONFIGURATION);

        String clientDAOValue = Helper.getConfigurationParameter(configuration, CLIENT_DAO, true);
        String clientStatusDAOValue = Helper.getConfigurationParameter(configuration, CLIENT_STATUS_DAO, true);

        // create ObjectFactory
        ObjectFactory factory = Helper.createObjectFactory(objFactoryConfig);

        // create ClientDAO instance
        clientDAO = (ClientDAO) Helper.createObject(factory, clientDAOValue, ClientDAO.class);

        // create ClientStatusDAO instance
        clientStatusDAO = (ClientStatusDAO) Helper.createObject(factory, clientStatusDAOValue, ClientStatusDAO.class);
    }

    /**
     * Constructs an instance of this class with configuration in a specified file.
     *
     * @param namespace
     *            namespace of the configuration for this class, can't be null or empty
     * @param filename
     *            path of the configuration file, it can be null, but can't be empty
     * @throws IllegalArgumentException
     *             if filename is empty or namespace is null or empty
     * @throws ManagerConfigurationException
     *             if error occurred when configuring this class, e.g. required configuration value is missing,
     *             or error occurred in object factory
     */
    public DAOClientManager(String filename, String namespace) throws ManagerConfigurationException {
        this(getConfiguration(filename, namespace, DAOClientManager.class.getName()));
    }

    /**
     * Creates a new Client. After creation, a new ID will be generated for it.
     *
     * @param client
     *            the new Client to create, can not be null (should have startDate, endDate , and startDate should be
     *            before endDate; its name should be non-null and non-empty string and isDeleted should be false)
     *
     * @return the Client instance created, with new ID assigned
     * @throws IllegalArgumentException
     *             if the argument is invalid (null client, the startDate or endDate is null or endDate is not after
     *             startDate, name is null or empty string, isDeleted is true)
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer, or in ID generator
     */
    public Client createClient(Client client) throws ClientManagerException {
        String method = "createClient(Client client)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"client"}, new String[] {Helper
                .formatClient(client)});

        try {
            Helper.validateClient(client);

            // Generate a new id for this client
            client.setId(getIDGenerator().getNextID());

            // save the client
            Client newClient = (Client) clientDAO.save(client);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClient(newClient));

            return newClient;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (IDGenerationException e) {
            throw wrapInnerException(method, "Fail to generate id for client, cause by " + e.getMessage(), e, client,
                    null);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to create the client, cause by " + e.getMessage(), e, client, null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to create the client, cause by " + e.getMessage(), e, client, null);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to create the client, cause by " + cce.getMessage(), cce, client,
                    null);
        }
    }

    /**
     * Wrap inner exception such as DAOException , DAOConfigurationException or IDGenerationException to
     * ClientManagerException and return the ClientManagerException created.
     *
     * @param method
     *            the method name for log
     * @param message
     *            the error message
     * @param e
     *            the inner cause
     * @param client
     *            the Client instance to create ClientManagerException, can be null
     * @param status
     *            the ClientStatus instance to create ClientManagerException, can be null
     *
     * @return ClientManagerException the created ClientManagerException
     */
    private ClientManagerException wrapInnerException(String method, String message, Exception e, Client client,
            ClientStatus status) {
        ClientManagerException clientManagerException = new ClientManagerException(message, e, client, status);

        // log the ClientManagerException
        Helper.logException(getLog(), CLASSNAME, method, clientManagerException);

        return clientManagerException;
    }

    /**
     * Updates a Client. ClientEntityNotFoundException will be thrown if the client to update does not
     * exist in persistence.
     *
     *
     * @param client
     *            the new Client to create, can not be null (should have startDate, endDate , and startDate should be
     *            before endDate; its name should be non-null and non-empty string and isDeleted should be false)
     *
     * @return the updated Client
     *
     * @throws IllegalArgumentException
     *             if the argument is invalid (e.g. client is null, client do not have startDate, endDate or startDate
     *             is not before endDate...)
     * @throws ClientEntityNotFoundException
     *             if the client to be updated does not exist in persistence
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Client updateClient(Client client) throws ClientManagerException, ClientEntityNotFoundException {
        String method = "updateClient(Client client)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"client"}, new String[] {Helper
                .formatClient(client)});

        try {
            Helper.validateClient(client);

            // check if the client exists in persistence layer
            checkClientExisted(client, method);

            // save the client
            Client newClient = (Client) clientDAO.save(client);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClient(newClient));

            return newClient;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to update the client, cause by " + e.getMessage(), e, client, null);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to update the client, cause by " + e.getMessage(), e, client, null);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to update the client, cause by " + cce.getMessage(), cce, client,
                    null);
        }
    }

    /**
     * Check if the given Client exists in the persistence.
     *
     * @param client
     *            the client to check
     * @param method
     *            the method name used when logging
     * @throws ClientEntityNotFoundException
     *             if the client to be updated does not exist in persistence
     * @throws DAOException
     *             if any other exceptions except IllegalArgumentException and EntityNotFoundException occurs while
     *             performing retrieveById function
     */
    private void checkClientExisted(Client client, String method) throws ClientEntityNotFoundException, DAOException {
        // check if the client exists in persistence
        try {
            if (clientDAO.retrieveById(client.getId()) == null) {
                ClientEntityNotFoundException clientEntityNotFoundException = new ClientEntityNotFoundException(
                        "The client entity does not exist in persistence with id=" + client.getId());

                Helper.logException(getLog(), CLASSNAME, method, clientEntityNotFoundException);

                // throw ClientEntityNotFoundException
                throw clientEntityNotFoundException;
            }
        } catch (IllegalArgumentException iae) {
            // if the id is <= 0, IAE will be thrown
            ClientEntityNotFoundException clientEntityNotFoundException = new ClientEntityNotFoundException(
                    "The id should not <= 0, refer to " + iae.getMessage(), iae);

            Helper.logException(getLog(), CLASSNAME, method, clientEntityNotFoundException);

            throw clientEntityNotFoundException;
        } catch (EntityNotFoundException e) {
            throw wrapEntityNotFoundException(method, "The client with id=" + client.getId()
                    + " does not exist in the persistence", e);
        }
    }

    /**
     * Deletes given Client.
     *
     * @param client
     *            the client to delete, can't be null, isDeleted should be false
     *
     * @return the deleted client, with isDeleted set to true
     * @throws IllegalArgumentException
     *             if client is null or it's isDeleted is true
     * @throws ClientEntityNotFoundException
     *             if the client to be updated does not exist in persistence
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Client deleteClient(Client client) throws ClientEntityNotFoundException, ClientManagerException {
        String method = "deleteClient(Client client)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"client"}, new String[] {Helper
                .formatClient(client)});

        try {
            Helper.checkNull(client, "client");
            if (client.isDeleted()) {
                throw new IllegalArgumentException("The isDelete of client should not be true.");
            }

            // delete the client.
            clientDAO.delete(client);

            // set deleted to true
            client.setDeleted(true);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClient(client));
            return client;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to delete the given client, cause by " + e.getMessage(), e, client,
                    null);
        } catch (EntityNotFoundException e) {
            throw wrapEntityNotFoundException(method, "The client entity with id= " + client.getId()
                    + " does not exist.", e);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to delete the given client, cause by " + e.getMessage(), e, client,
                    null);
        }
    }

    /**
     * Sets the code name of given client, and forward this update to the persistence.
     *
     *
     * @param client
     *            the new Client to create, can not be null (should have startDate, endDate , and startDate should be
     *            before endDate; its name should be non-null and non-empty string and isDeleted should be false)
     * @param codeName
     *            the code name to set, can't be null or empty
     * @return the updated Client with new codeName set
     * @throws IllegalArgumentException
     *             if any argument is invalid (e.ge, codeName is null or empty, client is not valid for update)
     * @throws ClientEntityNotFoundException
     *             if the client to be updated does not exist in persistence
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Client setClientCodeName(Client client, String codeName) throws ClientManagerException,
            ClientEntityNotFoundException {
        String method = "setClientCodeName(Client client, String codeName)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"client", "codeName"}, new String[] {
                Helper.formatClient(client), codeName});

        try {
            Helper.validateClient(client);
            Helper.checkString(codeName, "codeName");
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        }

        // set the codeName.
        client.setCodeName(codeName);

        // update the client.
        Client updateClient = updateClient(client);

        Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClient(updateClient));
        return updateClient;
    }

    /**
     * Sets the status of given client, and forward this update to the persistence.
     *
     *
     * @param status
     *            the client status to set, can't be null ( should have non-null and non-empty name, non-null and
     *            non-empty description and isDeleted should be false)
     * @param client
     *            the new Client to create, can not be null (should have startDate, endDate , and startDate should be
     *            before endDate; its name should be non-null and non-empty string and isDeleted should be false)
     * @return the updated Client with new ClientStatus set
     * @throws IllegalArgumentException
     *             if any argument is invalid (for client, it should be not null, it should have startDate, endDate
     *             and startDate should be before endDate; It should have non-null and non-empty name and
     *             isDeleted false. For status, it must have non-null and non-empty name and non-null and non-empty
     *             description and isDeleted attribute should be false)
     * @throws ClientEntityNotFoundException
     *             if the client to be updated does not exist in persistence
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Client setClientStatus(Client client, ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException {
        String method = "setClientStatus(Client client, ClientStatus status)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"client", "status"}, new String[] {
                Helper.formatClient(client), Helper.formatClientStatus(status)});

        try {
            Helper.validateClient(client);
            Helper.validateClientStatus(status);
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        }

        // set the ClientStatus;
        client.setClientStatus(status);

        // update the client
        Client updateClient = updateClient(client);

        Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClient(updateClient));
        return updateClient;
    }

    /**
     * Gets the Client by given id.
     *
     *
     * @param id
     *            id of the Client to retrieve, can't be negative
     * @return the Client with given id, or null if none exists for given id
     * @throws IllegalArgumentException
     *             if id is negative
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public Client retrieveClient(long id) throws ClientManagerException {
        String method = "retrieveClient(long id)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"id"}, new String[] {String.valueOf(id)});

        try {
            Helper.checkId(id);

            Client client = (Client) clientDAO.retrieveById(id);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClient(client));

            return client;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to retrieve the client by id=" + id + " cause by " + e.getMessage(),
                    e, null, null);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to retrieve the client by id=" + id + " cause by "
                    + cce.getMessage(), cce, null, null);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to retrieve the client by id=" + id + " cause by " + e.getMessage(),
                    e, null, null);
        }
    }

    /**
     * Gets all the Clients. If none exists, empty list will be returned.
     *
     * @return a list of Client objects, empty list if none client exists
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Client> retrieveAllClients() throws ClientManagerException {
        String method = "retrieveAllClients";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[0], new String[0]);

        try {
            List<Client> clients = clientDAO.retrieveAll();

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClientList(clients));
            return clients;
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to retrieve all clients, cause by " + e.getMessage(), e, null, null);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to retrieve all clients, cause by " + e.getMessage(), e, null, null);
        }

    }

    /**
     * Finds the Clients that match given name. Empty list will be returned if none is found.
     *
     * @param name
     *            name of the Clients to search, can't be null or empty
     *
     * @return a list of Clients that match given name, empty list if none is found
     * @throws IllegalArgumentException
     *             if name is null or empty
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Client> searchClientsByName(String name) throws ClientManagerException {
        String method = "searchClientsByName(String name)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"name"}, new String[] {name});

        try {
            Helper.checkString(name, "name");

            List<Client> clients = clientDAO.searchByName(name);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClientList(clients));
            return clients;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to search clients by name=" + name + ", cause by " + e.getMessage(),
                    e, null, null);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to search clients by name=" + name + ", cause by " + e.getMessage(),
                    e, null, null);
        }

    }

    /**
     * Finds the clients with specified filter. Empty list will be returned if none is found
     *
     * @param filter
     *            the filter used to search for Clients, can't be null
     *
     * @return a list of Clients that match the filter, empty list if none is found
     * @throws IllegalArgumentException
     *             if the filter is null
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Client> searchClients(Filter filter) throws ClientManagerException {
        String method = "searchClients(Filter filter)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"filter"}, new String[] {String
                .valueOf(filter)});

        try {
            Helper.checkNull(filter, "filter");

            List<Client> clients = clientDAO.search(filter);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClientList(clients));
            return clients;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to search clients by filter, cause by " + e.getMessage(), e, null,
                    null);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to search clients by filter, cause by " + e.getMessage(), e, null,
                    null);
        }
    }

    /**
     * Gets the ClientStatus with given ID.
     *
     *
     * @param id
     *            id of the ClientStatus to retrieve, can't be negative
     * @return a ClientStatus matching given ID, or null if none exists with given id
     * @throws IllegalArgumentException
     *             if id is negative
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public ClientStatus getClientStatus(long id) throws ClientManagerException {
        String method = "getClientStatus(long id)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"id"}, new String[] {String.valueOf(id)});

        try {
            Helper.checkId(id);

            ClientStatus status = (ClientStatus) clientStatusDAO.retrieveById(id);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClientStatus(status));
            return status;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to get the ClientStatus by id =" + id + " cause by "
                    + e.getMessage(), e, null, null);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to get the ClientStatus by id =" + id + " cause by "
                    + e.getMessage(), e, null, null);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to get the ClientStatus by id =" + id + " cause by "
                    + cce.getMessage(), cce, null, null);
        }
    }

    /**
     * Gets all the client status. If none exists, empty list will be returned.
     *
     * @return a list of ClientStatus objects, empty list if none exists
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<ClientStatus> getAllClientStatuses() throws ClientManagerException {
        String method = "getAllClientStatuses";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {}, new String[] {});

        try {
            List<ClientStatus> statuses = clientStatusDAO.retrieveAll();

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClientStatusList(statuses));
            return statuses;
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to get all ClientStatus, cause by "
                    + e.getMessage(), e, null, null);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to get all ClientStatus, cause by "
                    + e.getMessage(), e, null, null);
        }
    }

    /**
     * Gets all Clients that match given client status. Empty list will be returned if none if found.
     *
     *
     * @param status
     *            a ClientStatus, can't be null, id should not be negative
     * @return a list Client objects, empty list if none is found.
     * @throws IllegalArgumentException
     *             if param status is null or its id is negative
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     */
    public List<Client> getClientsForStatus(ClientStatus status) throws ClientManagerException {
        String method = "getClientsForStatus(ClientStatus status)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"clientStatus"}, new String[] {Helper
                .formatClientStatus(status) });

        try {
            Helper.validateClientStatus(status);

            List<Client> clients = clientStatusDAO.getClientsWithStatus(status);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClientList(clients));
            return clients;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to get clients by ClientStatus, cause by " + e.getMessage(), e,
                    null, status);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to get clients by ClientStatus, cause by " + e.getMessage(), e,
                    null, status);
        }

    }

    /**
     * Create a new ClientStatus, a new id will be generated for it.
     *
     * @param status
     *            the client status to set, can't be null ( should have non-null and non-empty name, non-null and
     *            non-empty description and isDeleted should be false)
     *
     * @return the created ClientStatus with new ID assigned.
     * @throws IllegalArgumentException
     *             if argument is invalid (status is null or its name is null or empty, or
     *             its description is null or empty, or its isDeleted is true)
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer, or in ID generator
     */
    public ClientStatus createClientStatus(ClientStatus status) throws ClientManagerException {
        String method = "createClientStatus(ClientStatus status)";

        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"clientStatus"}, new String[] {Helper
                .formatClientStatus(status)});

        try {
            Helper.validateClientStatus(status);

            // Generate a new id for the ClientStatus
            status.setId(getIDGenerator().getNextID());
            // save the ClientStatus
            ClientStatus newStatus = (ClientStatus) clientStatusDAO.save(status);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClientStatus(newStatus));
            return newStatus;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (IDGenerationException e) {
            throw wrapInnerException(method, "Fail to generate id for ClientStatus, cause by " + e.getMessage(), e,
                    null, status);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to create ClientStatus, cause by " + e.getMessage(),
                    e, null, status);
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to create ClientStatus, cause by " + e.getMessage(),
                    e, null, status);
        }
    }

    /**
     * Updates a ClientStatus. Error will be thrown if the client status to update does not exist in persistence.
     *
     * @param status
     *            the client status to set, can't be null ( should have non-null and non-empty name, non-null and
     *            non-empty description and isDeleted should be false)
     *
     * @return the updated ClientStatus
     * @throws IllegalArgumentException
     *             if the argument is invalid (status is null or its name is null or empty, or
     *             its description is null or empty, or its isDeleted is true)
     * @throws ClientManagerException
     *             if any error occurred during this operation, e.g. error in persistence layer
     * @throws ClientEntityNotFoundException
     *             if the ClientStatus to be updated does not exist in persistence
     */
    public ClientStatus updateClientStatus(ClientStatus status) throws ClientManagerException,
        ClientEntityNotFoundException {
        String method = "updateClientStatus(ClientStatus status)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"clientStatus"}, new String[] {Helper
                .formatClientStatus(status)});

        try {
            Helper.validateClientStatus(status);
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        }

        try {
            // check if the ClientStatus exists in persistence
            checkClientStatusExisted(status, method);

            // save the ClientStatus
            ClientStatus newStatus = (ClientStatus) clientStatusDAO.save(status);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClientStatus(newStatus));

            return newStatus;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to update the ClientStatus, cause by " + e.getMessage(), e, null,
                    status);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to update the ClientStatus, cause by " + e.getMessage(), e, null,
                    status);
        } catch (ClassCastException cce) {
            throw wrapInnerException(method, "Fail to update the ClientStatus, cause by " + cce.getMessage(), cce,
                    null, status);
        }

    }

    /**
     * Check if the ClientStatus with given id exists.
     *
     * @param clientStatus
     *            the ClientStatus instance
     * @param method
     *            the method name used when logging
     * @throws ClientEntityNotFoundException
     *             if the ClientStatus to be updated does not exist in persistence
     * @throws DAOException
     *             if any other exception except IllegalArgumentException and EntityNotFoundException occur while
     *             performing clientStatusDAO.retrieveById(id) function
     */
    private void checkClientStatusExisted(ClientStatus clientStatus, String method)
        throws ClientEntityNotFoundException, DAOException {
        // check if the ClientStatus exists in persistence
        try {
            if (clientStatusDAO.retrieveById(clientStatus.getId()) == null) {
                ClientEntityNotFoundException clientEntityNotFoundException = new ClientEntityNotFoundException(
                        "The clientStatus entity does not exist in persistence with id=" + clientStatus.getId());

                Helper.logException(getLog(), CLASSNAME, method, clientEntityNotFoundException);

                // throw ClientEntityNotFoundException
                throw clientEntityNotFoundException;
            }
        } catch (IllegalArgumentException iae) {
            // if the id is <= 0, IAE will be thrown
            ClientEntityNotFoundException clientEntityNotFoundException = new ClientEntityNotFoundException(
                    "The id should not <= 0, refer to detail:" + iae.getMessage(), iae);

            Helper.logException(getLog(), CLASSNAME, method, clientEntityNotFoundException);

            throw clientEntityNotFoundException;
        } catch (EntityNotFoundException e) {
            throw wrapEntityNotFoundException(method, "The ClientStatus with id=" + clientStatus.getId()
                    + " does not exist in the persistence", e);
        }
    }

    /**
     * Wrap EntityNotFoundException to ClientEntityNotFoundException and return.
     *
     * @param method
     *            the method name for log
     * @param message
     *            the error message to create ClientEntityNotFoundException instance
     * @param cause
     *            the inner cause to create ClientEntityNotFoundException instance
     * @return ClientEntityNotFoundException created
     */
    private ClientEntityNotFoundException wrapEntityNotFoundException(String method, String message, Exception cause) {
        // create ClientEntityNotFoundException with given message and inner cause
        ClientEntityNotFoundException exception = new ClientEntityNotFoundException(message, cause);

        Helper.logException(getLog(), CLASSNAME, method, exception);

        return exception;
    }

    /**
     * Deletes given ClientStatus.
     *
     * @param status
     *            the client status to delete, can't be null, isDeleted should be false
     * @return the deleted client status, with isDeleted set to true
     * @throws IllegalArgumentException
     *             if client status is null or it's isDeleted is true
     * @throws ClientManagerException
     *             if the ClientStatus to be deleted does not exist in persistence
     * @throws ClientEntityNotFoundException
     *             if the ClientStatus to be deleted does not exist in persistence
     */
    public ClientStatus deleteClientStatus(ClientStatus status) throws ClientManagerException,
            ClientEntityNotFoundException {
        String method = "deleteClientStatus(ClientStatus status)";
        Helper.logEntranceInfo(getLog(), CLASSNAME, method, new String[] {"status"}, new String[] {Helper
                .formatClientStatus(status)});

        try {
            // valid parameter status
            Helper.checkNull(status, "status");
            if (status.isDeleted()) {
                throw new IllegalArgumentException("The isDelete of ClientStatus should not be true.");
            }

            // delete the ClientStatus
            clientStatusDAO.delete(status);

            // set delete to true
            status.setDeleted(true);

            Helper.logExitInfo(getLog(), CLASSNAME, method, Helper.formatClientStatus(status));

            return status;
        } catch (IllegalArgumentException iae) {
            Helper.logException(getLog(), CLASSNAME, method, iae);
            throw iae;
        } catch (DAOConfigurationException e) {
            throw wrapInnerException(method, "Fail to delete the ClientStatus, cause by " + e.getMessage(), e, null,
                    status);
        } catch (EntityNotFoundException e) {
            throw wrapEntityNotFoundException(method,
                    "The ClientStatus entity to delete does not exist in persistence", e);
        } catch (DAOException e) {
            throw wrapInnerException(method, "Fail to delete the ClientStatus, cause by " + e.getMessage(), e, null,
                    status);
        }
    }
}
