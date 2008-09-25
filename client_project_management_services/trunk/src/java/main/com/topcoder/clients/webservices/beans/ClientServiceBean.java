/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.topcoder.clients.manager.ClientManager;
import com.topcoder.clients.manager.ManagerException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.webservices.ClientProjectManagementServicesException;
import com.topcoder.clients.webservices.ClientService;
import com.topcoder.clients.webservices.ClientServiceException;
import com.topcoder.clients.webservices.ClientServiceLocal;
import com.topcoder.clients.webservices.ClientServiceRemote;
import com.topcoder.clients.webservices.usermapping.UserMappingRetriever;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Log;

/**
 * <p>
 *  This class is a Stateless Session Bean endpoint realization of ClientService web service interface.
 *  This class has a default no-arg constructor. It uses ClientManager from
 *  Client Project Management component by delegating to namesake methods to perform the operations.
 *  This class implements the methods available for the ClientService web service:
 *  create, update and delete client, set code name for client and set client status for client.
 * </P>
 *
 * <P>
 *  <strong>Logging:</strong>
 *  All the available web service operations performs logging using the Logging Wrapper component.
 * </P>
 *
 * <P>
 *  <strong>Authentication and Authorization:</strong>
 *  All the available web service operations performs
 *  the authentication and authorization of the users that performs the operations
 *  using JBoss Login Module component and then delegates to the Client Project Management
 *  component which performs the needed operations.
 * </P>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This class is technically mutable since the configuration
 *  properties and manager are set after construction,
 *  but the container will not initialize the properties more than once
 *  or the session beans and the EJB3 container ensure the thread safety in this case.
 * </p>
 *
 * <p>
 *  Below is example configuration in ejb-jar.xml for ClientServiceBean.
 *  <pre>
 *  &lt;ejb-jar version="3.0" xmlns="http://java.sun.com/xml/ns/javaee"
 *  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/ejb-jar_3_0.xsd"&gt;
 *  &lt;enterprise-beans&gt;
 *      &lt;session&gt;
 *          &lt;ejb-name&gt;ClientServiceBean&lt;/ejb-name&gt;
 *          &lt;remote&gt;com.topcoder.clients.webservices.ClientServiceRemote&lt;/remote&gt;
 *          &lt;local&gt;com.topcoder.clients.webservices.ClientServiceLocal&lt;/local&gt;
 *          &lt;ejb-class&gt;com.topcoder.clients.webservices.beans.ClientServiceBean&lt;/ejb-class&gt;
 *          &lt;session-type&gt;Stateless&lt;/session-type&gt;
 *          &lt;transaction-type&gt;Container&lt;/transaction-type&gt;
 *
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;clientManagerFile&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;
 *                  /Applications/Developer_Suite/Service/jboss-4.2.3.GA/server/default/
 *                  conf_tcs/client_project_management_services/bean.properties
 *              &lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;clientManagerNamespace&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;clientManagerNamespace&lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;userMappingRetrieverFile&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;
 *                  /Applications/Developer_Suite/Service/jboss-4.2.3.GA/server/default/
 *                  conf_tcs/client_project_management_services/bean.properties
 *              &lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;userMappingRetrieverNamespace&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;userMappingRetrieverNamespace&lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;logName&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;System.out&lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;administratorRole&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;Admin&lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *          &lt;env-entry&gt;
 *              &lt;env-entry-name&gt;clientAndProjectUserRole&lt;/env-entry-name&gt;
 *              &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *              &lt;env-entry-value&gt;developer&lt;/env-entry-value&gt;
 *          &lt;/env-entry&gt;
 *
 *          &lt;persistence-context-ref&gt;
 *              &lt;persistence-context-ref-name&gt;persistenceUnit&lt;/persistence-context-ref-name&gt;
 *              &lt;persistence-unit-name&gt;persistenceUnit&lt;/persistence-unit-name&gt;
 *              &lt;persistence-context-type&gt;Transaction&lt;/persistence-context-type&gt;
 *          &lt;/persistence-context-ref&gt;
 *      &lt;/session&gt;
 *  &lt;/enterprise-beans&gt;
 *
 *  &lt;!-- define security roles permissions --&gt;
 *  &lt;assembly-descriptor&gt;
 *      &lt;security-role&gt;
 *          &lt;role-name&gt;Admin&lt;/role-name&gt;
 *      &lt;/security-role&gt;
 *      &lt;method-permission&gt;
 *          &lt;role-name&gt;Admin&lt;/role-name&gt;
 *          &lt;method&gt;
 *              &lt;ejb-name&gt;ClientServiceBean&lt;/ejb-name&gt;
 *              &lt;method-name&gt;*&lt;/method-name&gt;
 *          &lt;/method&gt;
 *      &lt;/method-permission&gt;
 *  &lt;/assembly-descriptor&gt;
 *  &lt;/ejb-jar&gt;
 *  </pre>
 *  <pre>
 *      This service bean can be used like below example:
 *
 *      Properties env = ....;
 *      env.setProperty(Context.SECURITY_PRINCIPAL, username);
 *      env.setProperty(Context.SECURITY_CREDENTIALS, password);
 *      InitialContext ctx = new InitialContext(env);
 *
 *      // Assume, this bean is deployed in EAR package.
 *      ClientService service = (ClientService) ctx.lookup(EAR_NAME + "/" + ClientServiceRemote.JNDI_NAME);
 *
 *      // Create Client
 *      service.createClient(client);
 *
 *      // Update Client
 *      service.updateClient(client);
 *
 *      // Delete Client
 *      service.deleteClient(client);
 *  </pre>
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@WebService(name = "ClientService",
            serviceName = "ClientService",
            targetNamespace = "com.topcoder.clients.webservices.ClientService",
            endpointInterface = "com.topcoder.clients.webservices.ClientService")
@Stateless(name = ClientService.BEAN_NAME)
@Local(ClientServiceLocal.class)
@Remote(ClientServiceRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@DeclareRoles({ Roles.USER, Roles.ADMIN })
@RolesAllowed({ Roles.USER, Roles.ADMIN })
public class ClientServiceBean implements ClientService, ClientServiceRemote, ClientServiceLocal {

    /**
     * Represents token for user mapping.
     */
    private static final String USER_MAPPING_TOKEN = "user_mapping_retriever_token";

    /**
     * Represents token for client manager.
     */
    private static final String CLIENT_MANAGER_TOKEN = "client_manager_token";

    /**
     * Represents the client manager instance.
     *
     * Cannot be null after this bean is constructed.
     * REQUIRED.
     */
    private ClientManager clientManager;

    /**
     *  Represents the user mapping retriever instance.
     *  Cannot be null after this bean is constructed.
     *  REQUIRED.
     */
    private UserMappingRetriever userMappingRetriever;

    /**
     * Represents the client manager file.
     * Cannot be null or empty String after injected when this bean is instantiated.
     * REQUIRED.
     */
    @Resource(name = "clientManagerFile")
    private String clientManagerFile;

    /**
     *  Represents the client manager namespace.
     *  Cannot be null or empty String after injected when this bean is instantiated.
     *  REQUIRED.
     */
    @Resource(name = "clientManagerNamespace")
    private String clientManagerNamespace;

    /**
     *  Represents the user mapping retriever file.
     *  Cannot be null or empty String after injected when this bean is instantiated.
     *  REQUIRED.
     */
    @Resource(name = "userMappingRetrieverFile")
    private String userMappingRetrieverFile;

    /**
     * Represents the user mapping retriever namespace.
     *
     * Cannot be null or empty String after injected when this bean is instantiated.
     * REQUIRED.
     */
    @Resource(name = "userMappingRetrieverNamespace")
    private String userMappingRetrieverNamespace;

    /**
     * Represents the SessionContext injected by the EJB container automatically
     * and it is used to retrieve the Principal needed for user authentication.
     *
     * Cannot be null after injected when this bean is instantiated.
     */
    @Resource
    private SessionContext sessionContext;

    /**
     *  Represents the administrator role used to check if the user is administrator.
     *  Can be null or empty String.
     *  OPTIONAL.
     */
    @Resource(name = "administratorRole")
    private String administratorRole;

    /**
     *  Represents the client and project user role used to
     *  check if the user is a  client and project user.
     *  Can be null or empty String.
     *  OPTIONAL.
     */
    @Resource(name = "clientAndProjectUserRole")
    private String clientAndProjectUserRole;

    /**
     *  Represents the log name used to initialize the log.
     *  Can be null or empty String.
     *  OPTIONAL.
     */
    @Resource(name = "logName")
    private String logName;

    /**
     *  This field represents the 'log' instance of the ClientServiceBean.
     *  It will be used to perform the logging operations.
     *  Contains an instance of Log interface.
     *  It can be enabled (not null) or disabled (null).
     *  Can be any value.
     *  OPTIONAL.
     */
    private Log log;

    /**
     *  This field represents the 'verboseLogging' property of the ClientServiceBean.
     *  The control flag defining whether the detailed logging actions should be
     *  performed (true value), or standard logging (only exceptions)
     *  should be supported (false value).
     *
     *  There are no restrictions at this moment.
     *  It can take any value.
     *  OPTIONAL.
     */
    private boolean verboseLogging = false;

    /**
     *  Default no-arg constructor.
     */
    public ClientServiceBean() {
        // do - nothing.
    }

    /**
     *  Initialize the clientManager, mapping retreiver and
     *  log and makes sure the initialization of this session bean is ok.
     *  This method is called after this bean is constructed by the EJB container.
     *
     *  @throws ClientServiceBeanConfigurationException
     *          if the required String fields are null or empty,
     *          if needed configurations are missing or invalid (invalid means null here),
     *          if Object Factory cannot initialize the needed dependencies.
     *          Also this exception is thrown if both ad if both administratorRole
     *          and clientAndProjectUserRole are null or empty strings.
     */
    @PostConstruct
    protected void initialize() {
        // create logger.
        log = BeanUtils.createLogger(logName);
        try {
            // create client manager instance.
            clientManager = BeanUtils.createManager(ClientManager.class,
                    clientManagerFile, clientManagerNamespace, CLIENT_MANAGER_TOKEN);
            // create user mapping retriever.
            userMappingRetriever = BeanUtils.createManager(UserMappingRetriever.class,
                    userMappingRetrieverFile, userMappingRetrieverNamespace, USER_MAPPING_TOKEN);
        } catch (ServiceBeanConfigurationException e) {
            throw new ClientServiceBeanConfigurationException("Fail to initialize "
                    + "'clientManager' or 'usermappingRetriever'.", e.getCause());
        }
    }

    /**
     *  Defines the operation that performs the creation of the given client in the persistence.
     *
     *  @param  client
     *      the client that should be created.
     *      Should not be null.
     *  @return the client created in the persistence.
     *
     *  @throws IllegalArgumentException
     *          if the client is null.
     *  @throws IllegalStateException
     *          if the clientManager or userMappingRetriever
     *          are not set (or it is set to a null value).
     *  @throws AuthorizationFailedException
     *          if the user is not authorized to perform the operation.
     *  @throws ClientServiceException
     *          if any error occurs while performing this operation.
     */
    @WebMethod
    public Client createClient(Client client)
        throws AuthorizationFailedException, ClientServiceException {
        String method = this.getClass().getName() + "#createClient(Client client)";
        Client retClient = null;
        Date enterTime = null;

        try {
            // log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{client});

            // Perform authentication for current user.
            BeanUtils.validateCaller(method, sessionContext, administratorRole, clientAndProjectUserRole,
                    client, log, false, userMappingRetriever);

            // Performing create operation for Client.
            retClient = BeanUtils.createOperation(BeanUtils.getCurrentUser(sessionContext),
                    client, null, clientManager, null, null);
        } catch (ClientProjectManagementServicesException e) {
            throw BeanUtils.logException(log, new ClientServiceException("Fail create Client.", e.getCause()));
        } catch (AuthorizationFailedException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit method.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retClient);
        }

        return retClient;
    }

    /**
     *  Defines the operation that performs the update of the given client in the persistence.
     *
     *  @param  client
     *      the client that should be updated.
     *      Should not be null.
     *  @return the client updated in the persistence.
     *
     *  @throws IllegalArgumentException
     *      if the client is null.
     *  @throws IllegalStateException
     *      if the clientManager or userMappingRetriever
     *      are not set (or it is set to a null value).
     *  @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     *  @throws ClientServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Client updateClient(Client client)
        throws AuthorizationFailedException, ClientServiceException {
        String method = this.getClass().getName() + "#updateClient(Client client)";
        Client retClient = null;
        Date enterTime = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{client});

            // Perform authentication for current user.
            BeanUtils.validateCaller(method, sessionContext, administratorRole, clientAndProjectUserRole,
                    client, log, true, userMappingRetriever);

            // Perform update operation for Client.
            retClient = BeanUtils.updateOperation(BeanUtils.getCurrentUser(sessionContext),
                    client, clientManager, null, null);
        } catch (ClientProjectManagementServicesException e) {
            throw BeanUtils.logException(log, new ClientServiceException("Fail update Client.", e.getCause()));
        } catch (AuthorizationFailedException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit method.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retClient);
        }

        return retClient;
    }

    /**
     *  Defines the operation that performs the deletion of the given client from the persistence.
     *
     *  @param  client
     *      the client that should be deleted.
     *      Should not be null.
     *  @return the client deleted from the persistence.
     *
     *  @throws IllegalArgumentException
     *      if the client is null.
     *  @throws IllegalStateException
     *      if the clientManager or userMappingRetriever
     *      are not set (or it is set to a null value).
     *  @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     *  @throws ClientServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Client deleteClient(Client client)
        throws AuthorizationFailedException, ClientServiceException {
        String method = this.getClass().getName() + "#deleteClient(Client client)";
        Client retClient = null;
        Date enterTime = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{client});

            // Perform authentication for current user.
            BeanUtils.validateCaller(method, sessionContext, administratorRole, clientAndProjectUserRole,
                    client, log, true, userMappingRetriever);

            // Perform delete operation.
            retClient = BeanUtils.deleteOperation(BeanUtils.getCurrentUser(sessionContext),
                    client, clientManager, null, null);
        } catch (ClientProjectManagementServicesException e) {
            throw BeanUtils.logException(log, new ClientServiceException("Fail delete Client.", e.getCause()));
        } catch (AuthorizationFailedException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit method.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retClient);
        }

        return retClient;
    }

    /**
     *  Defines the operation that performs the set of the
     *  codeName for the given client in the persistence.
     *
     *  @param  client
     *      the client for that should be set the codeName.
     *      Should not be null.
     *  @param  name
     *      the codeName that should be set for the client.
     *      Should not be null or empty String.
     *  @return the client for that the codeName was set in the persistence.
     *
     *  @throws IllegalArgumentException
     *      if the client or codeName is null or codeName is empty String.
     *  @throws IllegalStateException
     *      if the clientManager or userMappingRetriever are not set (or it is set to a null value).
     *  @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     *  @throws ClientServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Client setClientCodeName(Client client, String name)
        throws AuthorizationFailedException, ClientServiceException {
        String method = this.getClass().getName() + "#setClientCodeName(Client client, String name)";
        Date enterTime = null;
        Client retClient = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{client, name});

            // Perform authentication for current user.
            BeanUtils.validateCaller(method, sessionContext, administratorRole, clientAndProjectUserRole,
                    client, log, true, userMappingRetriever);

            // Validation for required property.
            BeanUtils.checkState("ClientManager", clientManager);
            ExceptionUtils.checkNull(client, null, null, "Argument [client] cannot be null.");
            ExceptionUtils.checkNullOrEmpty(name, null, null, "Argument [name] cannot be null or empty string.");

            client.setModifyUsername(BeanUtils.getCurrentUser(sessionContext));
            client.setModifyDate(enterTime);

            // Set codename into client.
            retClient = setClientCodeNameOrStatus(client, name, null, true);
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (AuthorizationFailedException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            // Log exit code.
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retClient);
        }

        return retClient;
    }

    /**
     *  Defines the operation that performs the set of
     *  the client status for the given client in the persistence.
     *
     *  @param client
     *      the client for that should be set the client status.
     *      Should not be null.
     *  @param status
     *      the client status that should be set for the client.
     *      Should not be null.
     *  @return the client for that the client status was set in the persistence.
     *
     *  @throws IllegalArgumentException
     *      if the client or client status is null.
     *  @throws IllegalStateException
     *      if the clientManager or userMappingRetriever
     *      are not set (or it is set to a null value).
     *  @throws AuthorizationFailedException
     *      if the user is not authorized to perform the operation.
     *  @throws ClientServiceException
     *      if any error occurs while performing this operation.
     */
    @WebMethod
    public Client setClientStatus(Client client, ClientStatus status)
        throws AuthorizationFailedException, ClientServiceException {

        String method = this.getClass().getName() + "#setClientStatus(Client client, ClientStatus status)";
        Date enterTime = null;
        Client retClient = null;

        try {
            // Log entrance.
            enterTime = BeanUtils.logEnter(log, verboseLogging, method, new Object[]{client, status});

            // Perform authentication for current user.
            BeanUtils.validateCaller(method, sessionContext, administratorRole, clientAndProjectUserRole,
                    client, log, true, userMappingRetriever);

            // Validation for required property.
            BeanUtils.checkState("ClientManager", clientManager);
            ExceptionUtils.checkNull(client, null, null, "Argument [client] cannot be null.");
            ExceptionUtils.checkNull(status, null, null, "Argument [status] cannot be null or empty string.");

            client.setModifyUsername(BeanUtils.getCurrentUser(sessionContext));
            client.setModifyDate(enterTime);

            // Set status into client.
            retClient = setClientCodeNameOrStatus(client, null, status, false);
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, e);
        } catch (AuthorizationFailedException e) {
            throw BeanUtils.logException(log, e);
        } catch (IllegalStateException e) {
            throw BeanUtils.logException(log, e);
        } finally {
            BeanUtils.logExit(log, verboseLogging, method, enterTime, retClient);
        }

        return retClient;
    }

    /**
     * Getter for 'verboseLogging' property.
     *
     * @return the value of the 'verboseLogging' property.
     *      It can be any value.
     */
    public boolean isVerboseLogging() {
        return verboseLogging;
    }

    /**
     * Setter for 'verboseLogging' property.
     *
     * @param verboseLogging
     *      the new value of the control flag defining whether the detailed logging actions
     *      should be performed (true value), or standard logging (only exceptions)
     *      should be supported (false value).
     */
    public void setVerboseLogging(boolean verboseLogging) {
        this.verboseLogging = verboseLogging;
    }

    /**
     * Getter for name of 'log' property.
     *
     * @return the value of the 'log' property.
     *      It can be any value.
     */
    public Log getLog() {
        return log;
    }

    /**
     * Setter for 'log' property.
     *
     * @param log the new log to set for log property.
     *      It can be any value.
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * Performing operation to setup ClientCodeName or ClientStatus.
     *
     * @param client
     *      The client instance.
     * @param name
     *      The code name for Client.
     * @param status
     *      The client status instance.
     * @param isSetCodeName
     *      Whether this operation is setCodeName into Client.
     *
     * @return Client instance.
     *
     * @throws ClientServiceException if any failure occurs.
     */
    private Client setClientCodeNameOrStatus(Client client, String name, ClientStatus status, boolean isSetCodeName)
        throws ClientServiceException {

        try {
            return (isSetCodeName) ? clientManager.setClientCodeName(client, name)
                    : clientManager.setClientStatus(client, status);
        } catch (IllegalArgumentException e) {
            throw BeanUtils.logException(log, new ClientServiceException("Received invalid argument.", e));
        } catch (ManagerException e) {
            throw BeanUtils.logException(log, new ClientServiceException("Fail perform operation.", e));
        }
    }
}
