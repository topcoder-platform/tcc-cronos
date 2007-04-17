/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.ejb;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientAuditException;
import com.topcoder.timetracker.client.ClientPersistenceException;
import com.topcoder.timetracker.client.ClientUtility;
import com.topcoder.timetracker.client.ConfigurationException;
import com.topcoder.timetracker.client.Depth;
import com.topcoder.timetracker.client.Helper;
import com.topcoder.timetracker.client.InvalidClientIDException;
import com.topcoder.timetracker.client.InvalidClientPropertyException;
import com.topcoder.timetracker.client.PropertyOperationException;
import com.topcoder.timetracker.project.Project;
import com.topcoder.util.config.ConfigManagerException;


/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application.  It is responsible for
 * looking up the local interface of the SessionBean for ClientUtility, and delegating any calls to the bean.
 * </p>
 *
 * <p>
 * Thread Safety: - This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientUtilityDelegate implements ClientUtility {
    /**
     * <p>
     * Represents key to retrieve location.
     * </p>
     */
    private static final String LOCATION_KEY = "LocalHomeName";

    /**
     * <p>
     * Represents key to retrieve context name.
     * </p>
     */
    private static final String CONTEXT_NAME_KEY = "ContextName";

    /**
     * <p>
     * This is the local interface for the ClientUtility business services. All business calls are delegated here.
     * </p>
     *
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     *
     * <p>
     * Accessed In: Not Accessed
     * </p>
     *
     * <p>
     * Modified In: Not modified after initialization
     * </p>
     *
     * <p>
     * Utilized In: All ClientUtility methods
     * </p>
     *
     * <p>
     * Valid Values: Non-null ClientUtilityLocal (after init)
     * </p>
     */
    private final ClientUtilityLocal local;

    /**
     * <p>
     * Default constructor.  It will initialize using configuration values from the namespace of the fully-qualified
     * class name of this class. (this.class.getName());
     * </p>
     *
     * @throws ConfigurationException if a problem occurs while constructing the instance.
     */
    public ClientUtilityDelegate() throws ConfigurationException {
        this(ClientUtilityDelegate.class.getName());
    }

    /**
     * <p>
     * Constructor that accepts configuration from the specified ConfigManager namespace.
     * </p>
     *
     * @param namespace The namespace to use.
     *
     * @throws IllegalArgumentException if namespace is null or an empty String.
     * @throws ConfigurationException if a problem occurs while constructing the instance.
     */
    public ClientUtilityDelegate(String namespace) throws ConfigurationException {
        Helper.checkString(namespace, "namespace");

        String location = Helper.getConfigString(namespace, LOCATION_KEY, true);
        String contextName = Helper.getConfigString(namespace, CONTEXT_NAME_KEY, false);

        try {
            Object object = null;

            if (contextName == null) {
                object = JNDIUtils.getDefaultContext().lookup(location);
            } else {
                object = JNDIUtils.getContext(contextName).lookup(location);
            }

            if (object == null) {
                throw new ConfigurationException("Home object is null.");
            }

            if (!(object instanceof ClientUtilityLocalHome)) {
                throw new ConfigurationException("The instance should be of type ClientUtilityLocalHome.");
            }
            this.local = ((ClientUtilityLocalHome) object).create();
        } catch (NamingException ne) {
            throw new ConfigurationException("Name exception.", ne);
        } catch (ConfigManagerException cme) {
            throw new ConfigurationException("Error relating to configuration manager.", cme);
        } catch (CreateException ce) {
            throw new ConfigurationException("Error creating the object.", ce);
        }
    }

    /**
     * <p>
     * Add the given Client.
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
            ClientAuditException {
        local.addClient(client, doAudit);
    }

    /**
     * <p>
     * Add the given Clients.
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
            ClientAuditException {
        local.addClients(clients, doAudit);
    }

    /**
     * <p>
     * Remove the Client with given id.
     * </p>
     *
     * @param id the id of the Client
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if the id is not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any error occurred
     */
    public void removeClient(long id, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException, PropertyOperationException {
        local.removeClient(id, doAudit);
    }

    /**
     * <p>
     * Remove the Clients with given ids.
     * </p>
     *
     * @param ids the non null, possible empty ids of the Clients
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if any id not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any operation with property failed
     */
    public void removeClients(long[] ids, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException, PropertyOperationException {
        local.removeClients(ids, doAudit);
    }

    /**
     * <p>
     * Retrieve the given Client.
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
    public Client retrieveClient(long id) throws ClientPersistenceException, PropertyOperationException {
        return local.retrieveClient(id);
    }

    /**
     * <p>
     * Retrieve the Clients with given ids.
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
    public Client[] retrieveClients(long[] ids) throws ClientPersistenceException, PropertyOperationException {
        return local.retrieveClients(ids);
    }

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
            PropertyOperationException {
        local.updateClient(client, doAudit);
    }

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
            PropertyOperationException {
        local.updateClients(clients, doAudit);
    }

    /**
     * <p>
     * Retrieve all the Clients.
     * </p>
     *
     * @return Non null, possible empty array containing all non null clients
     *
     * @throws ClientPersistenceException if it is thrown by the dao
     * @throws PropertyOperationException if it encounters exception when trying to get the properties
     */
    public Client[] getAllClients() throws ClientPersistenceException, PropertyOperationException {
        return local.getAllClients();
    }

    /**
     * <p>
     * Search the Clients with the given Filter and Depth.
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
        throws PropertyOperationException, ClientPersistenceException {
        return local.searchClient(filter, depth);
    }

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
        throws ClientPersistenceException {
        return local.getAllProjectsOfClient(clientId);
    }

    /**
     * <p>
     * Remove the project from the Client.
     * </p>
     *
     * @param client the id of client
     * @param projectId the id of project
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if any id not positive or null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void removeProjectFromClient(Client client, long projectId, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException {
        local.removeProjectFromClient(client, projectId, doAudit);
    }

    /**
     * <p>
     * Add the project to the client.
     * </p>
     *
     * @param client the id of the Client
     * @param project the non null project
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if project is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void addProjectToClient(Client client, Project project, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException {
        local.addProjectToClient(client, project, doAudit);
    }
}
