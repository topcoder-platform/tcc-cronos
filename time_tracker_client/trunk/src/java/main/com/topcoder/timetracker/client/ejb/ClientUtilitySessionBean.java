/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.ejb;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.client.Client;
import com.topcoder.timetracker.client.ClientAuditException;
import com.topcoder.timetracker.client.ClientPersistenceException;
import com.topcoder.timetracker.client.ClientUtility;
import com.topcoder.timetracker.client.ConfigurationException;
import com.topcoder.timetracker.client.Depth;
import com.topcoder.timetracker.client.InvalidClientIDException;
import com.topcoder.timetracker.client.InvalidClientPropertyException;
import com.topcoder.timetracker.client.PropertyOperationException;
import com.topcoder.timetracker.project.Project;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business services to manage Time Tracker Clients within the
 * Time Tracker Application. It contains the same methods as ClientUtility, and delegates to an instance of
 * ClientUtility.
 * </p>
 *
 * <p>
 * The instance of ClientUtility to use will be retrieved from the ClientUtilityFactory class.
 * </p>
 *
 * <p>
 * Thread Safety: The TaskTypeManager interface implementations are required to be thread-safe, and so this stateless
 * session bean is thread-safe also.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ClientUtilitySessionBean implements ClientUtility, SessionBean {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -3923948000487887987L;

	/**
     * <p>
     * This is the instance of SessionContext that was provided by the EJB container.  It is stored and made available
     * to subclasses.  It is also used to rollback a transaction in the case of an exception.
     * </p>
     *
     * <p>
     * Initial Value: Null
     * </p>
     *
     * <p>
     * Accessed In: getSessionContext();
     * </p>
     *
     * <p>
     * Modified In: setSessionContext
     * </p>
     *
     * <p>
     * Utilized In: All ClientUtility methods of this class.
     * </p>
     *
     * <p>
     * Valid Values: sessionContext objects (possibly null)
     * </p>
     */
    private SessionContext context;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ClientUtilitySessionBean() {
        // empty
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
        try {
            ClientUtilityFactory.getClientUtility().addClient(client, doAudit);
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (InvalidClientIDException ice) {
            context.setRollbackOnly();
            throw ice;
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (InvalidClientPropertyException icpe) {
            context.setRollbackOnly();
            throw icpe;
        } catch (ClientAuditException cae) {
            context.setRollbackOnly();
            throw cae;
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        }
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
        try {
            ClientUtilityFactory.getClientUtility().addClients(clients, doAudit);
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (InvalidClientIDException ice) {
            context.setRollbackOnly();
            throw ice;
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (InvalidClientPropertyException icpe) {
            context.setRollbackOnly();
            throw icpe;
        } catch (ClientAuditException cae) {
            context.setRollbackOnly();
            throw cae;
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        }
    }

    /**
     * <p>
     * Remove the Client with given id.
     * </p>
     *
     * @param id the id of the Client
     * @param doAudit whether audit on this action
     *
     * @throws IllegalArgumentException if the id not positive
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     * @throws PropertyOperationException if any operation with property failed
     */
    public void removeClient(long id, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException, PropertyOperationException {
        try {
            ClientUtilityFactory.getClientUtility().removeClient(id, doAudit);
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (ClientAuditException cae) {
            context.setRollbackOnly();
            throw cae;
        } catch (PropertyOperationException poe) {
            context.setRollbackOnly();
            throw poe;
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        }
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
        try {
            ClientUtilityFactory.getClientUtility().removeClients(ids, doAudit);
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (ClientAuditException cae) {
            context.setRollbackOnly();
            throw cae;
        } catch (PropertyOperationException poe) {
            context.setRollbackOnly();
            throw poe;
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        }
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
        try {
            return ClientUtilityFactory.getClientUtility().retrieveClient(id);
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (PropertyOperationException poe) {
            context.setRollbackOnly();
            throw poe;
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        }
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
        try {
            return ClientUtilityFactory.getClientUtility().retrieveClients(ids);
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (PropertyOperationException poe) {
            context.setRollbackOnly();
            throw poe;
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        }
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
        try {
            ClientUtilityFactory.getClientUtility().updateClient(client, doAudit);
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (ClientAuditException cae) {
            context.setRollbackOnly();
            throw cae;
        } catch (PropertyOperationException poe) {
            context.setRollbackOnly();
            throw poe;
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        } catch (InvalidClientPropertyException ice) {
            context.setRollbackOnly();
            throw ice;
        }
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
        try {
            ClientUtilityFactory.getClientUtility().updateClients(clients, doAudit);
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (ClientAuditException cae) {
            context.setRollbackOnly();
            throw cae;
        } catch (PropertyOperationException poe) {
            context.setRollbackOnly();
            throw poe;
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        } catch (InvalidClientPropertyException ice) {
            context.setRollbackOnly();
            throw ice;
        }
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
        try {
            return ClientUtilityFactory.getClientUtility().getAllClients();
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (PropertyOperationException poe) {
            context.setRollbackOnly();
            throw poe;
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        }
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
        try {
            return ClientUtilityFactory.getClientUtility().searchClient(filter, depth);
        } catch (PropertyOperationException poe) {
            context.setRollbackOnly();
            throw poe;
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        }
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
        try {
            return ClientUtilityFactory.getClientUtility().getAllProjectsOfClient(clientId);
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        }
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
     * @throws IllegalArgumentException if any id not positive or instance is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void removeProjectFromClient(Client client, long projectId, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException {
        try {
            ClientUtilityFactory.getClientUtility().removeProjectFromClient(client, projectId, doAudit);
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (ClientAuditException cae) {
            context.setRollbackOnly();
            throw cae;
        }
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
     * @throws IllegalArgumentException if project is null or cleint is null
     * @throws ClientPersistenceException if any exception occurs
     * @throws ClientAuditException if it encounters exception when audit
     */
    public void addProjectToClient(Client client, Project project, boolean doAudit)
        throws ClientPersistenceException, ClientAuditException {
        try {
            ClientUtilityFactory.getClientUtility().addProjectToClient(client, project, doAudit);
        } catch (ClientPersistenceException cpe) {
            context.setRollbackOnly();
            throw cpe;
        } catch (IllegalArgumentException iae) {
            context.setRollbackOnly();
            throw iae;
        } catch (ConfigurationException ce) {
            context.setRollbackOnly();
            throw new ClientPersistenceException("Error get the utility.", ce);
        } catch (ClientAuditException cae) {
            context.setRollbackOnly();
            throw cae;
        }
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbCreate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbActivate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbPassivate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbRemove() {
        // empty
    }

    /**
     * <p>
     * Sets the SessionContext to use for this session.  This method is included to comply with the SessionBean
     * interface.
     * </p>
     *
     * @param ctx The SessionContext to use.
     */
    public void setSessionContext(SessionContext ctx) {
        this.context = ctx;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the session context if necessary.
     * </p>
     *
     * @return The session context provided by the EJB container.
     */
    protected SessionContext getSessionContext() {
        return context;
    }
}
