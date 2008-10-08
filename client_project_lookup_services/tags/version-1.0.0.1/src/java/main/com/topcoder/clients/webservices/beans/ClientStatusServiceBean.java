/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.io.IOException;

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
import com.topcoder.clients.manager.ClientManagerException;
import com.topcoder.clients.manager.ManagerException;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.webservices.ClientStatusService;
import com.topcoder.clients.webservices.ClientStatusServiceException;
import com.topcoder.clients.webservices.ClientStatusServiceLocal;
import com.topcoder.clients.webservices.ClientStatusServiceRemote;
import com.topcoder.clients.webservices.Util;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This class is a Stateless Session Bean endpoint realization of <code>ClientStatusService</code> web service
 * interface. This class has a default no-arg constructor. It uses <code>ClientManager</code> from Client
 * Project Management component by delegating to namesake methods to perform the operations. This class
 * implements the methods available for the <code>ClientStatusService</code> web service: create, update and
 * delete client status.
 * <p>
 * LOGGING: All the available web service operations performs logging using the Logging Wrapper component.
 * </p>
 * <p>
 * AUTHENTICATION AND AUTHORIZATION: All the available web service operations are under the "User" and "Admin"
 * security roles.
 * </p>
 * <p>
 * <b>Sample configuration:</b>
 *
 * <pre>
 * &lt;session&gt;
 *             &lt;ejb-name&gt;ClientStatusServiceBean&lt;/ejb-name&gt;
 *             &lt;remote&gt;com.topcoder.clients.webservices.ClientStatusServiceRemote&lt;/remote&gt;
 *             &lt;local&gt;com.topcoder.clients.webservices.ClientStatusServiceLocal&lt;/local&gt;
 *             &lt;ejb-class&gt;
 *                 com.topcoder.clients.webservices.beans.ClientStatusServiceBean&lt;/ejb-class&gt;
 *             &lt;session-type&gt;Stateless&lt;/session-type&gt;
 *             &lt;transaction-type&gt;Container&lt;/transaction-type&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The name of the log to use&lt;/description&gt;
 *                 &lt;env-entry-name&gt;logName&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;log_Name&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The client manager file to use&lt;/description&gt;
 *                 &lt;env-entry-name&gt;clientManagerFile&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;config.properties&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The client manager namespace to use&lt;/description&gt;
 *                 &lt;env-entry-name&gt;clientManagerNamespace&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;ClientStatusServiceBean&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *         &lt;/session&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <b>Sample usage:</b>
 *
 * <pre>
 * // we have the following WSDL document location:
 * String wsdlLocation = &quot;http://topcoder.com:8080/jaxws/clientProject/ClientStatusService/?wsdl&quot;;
 *
 * // create a ClientStatusServiceClient client:
 * ClientStatusServiceClient clientStatusServiceClient = new ClientStatusServiceClient(wsdlLocation);
 *
 * // get ClientStatusService endpoint:
 * ClientStatusService clientStatusService = clientStatusServiceClient.getClientStatusServicePort();
 *
 * ClientStatus clientStatus;
 * clientStatus = new ClientStatus();
 * clientStatus.setName(&quot;client status name&quot;);
 * clientStatus.setDescription(&quot;client status description&quot;);
 *
 * // create a clientStatus:
 * clientStatus = clientStatusService.createClientStatus(clientStatus);
 * // if the user is in &quot;User&quot; or &quot;Admin&quot; roles then the operation has been performed
 * // otherwise an AuthorizationFailedException has been thrown.
 *
 * // update a clientStatus:
 * clientStatus.setName(&quot;client status another name &quot;);
 * clientStatus.setDescription(&quot;client status another description&quot;);
 * clientStatus = clientStatusService.updateClientStatus(clientStatus);
 * // if the user is in &quot;User&quot; or &quot;Admin&quot; roles then the operation has been performed
 * // otherwise an AuthorizationFailedException has been thrown.
 *
 * // delete a clientStatus:
 * clientStatusService.deleteClientStatus(clientStatus);
 * // if the user is in &quot;User&quot; or &quot;Admin&quot; roles then the operation has been performed
 * //otherwise an AuthorizationFailedException has been thrown.
 * </pre>
 *
 * </p>
 * <p>
 * Thread safety: This class is technically mutable since the configuration properties (with @Resource) and
 * managers are set after construction, but the container will not initialize the properties more than once
 * for the session beans and the EJB3 container ensure the thread safety in this case.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@WebService(serviceName = "ClientStatusService",
        endpointInterface = "com.topcoder.clients.webservices.ClientStatusService",
        targetNamespace = "com.topcoder.clients.webservices.ClientStatusService")
@Stateless(name = ClientStatusService.BEAN_NAME)
@Local(ClientStatusServiceLocal.class)
@Remote(ClientStatusServiceRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@DeclareRoles({Roles.USER, Roles.ADMIN })
@RolesAllowed({Roles.USER, Roles.ADMIN })
public class ClientStatusServiceBean implements ClientStatusServiceLocal, ClientStatusServiceRemote,
        ClientStatusService {
    /**
     * <p>
     * The name of this class.
     * </p>
     */
    private static final String CLASS_NAME = ClientStatusServiceBean.class.getSimpleName();

    /**
     * <p>
     * Represents the client manager instance. Initialized in the #initialize() method with a not null value
     * and never changed afterwards. Accessed by the methods that delegates to client manager. Will not change
     * after initialization. Valid values: can not be null after this bean is constructed. REQUIRED.
     * </p>
     */
    private ClientManager clientManager;

    /**
     * <p>
     * Represents the client manager file. Injected by the EJB container with non null and not empty value and
     * never changed afterwards. Accessed by the #initialize() method. Will not change after injection. Valid
     * values: can not be null or empty String after injected when this bean is instantiated. REQUIRED.
     * </p>
     */
    @Resource(name = "clientManagerFile")
    private String clientManagerFile;

    /**
     * <p>
     * Represents the client manager namespace. Injected by the EJB container with non null and not empty
     * value and never changed afterwards. Accessed by the #initialize() method. Will not change after
     * injection. Valid values: can not be null or empty String after injected when this bean is instantiated.
     * REQUIRED.
     * </p>
     */
    @Resource(name = "clientManagerNamespace")
    private String clientManagerNamespace;

    /**
     * <p>
     * Represents the SessionContext injected by the EJB container automatically and it is used to retrieve
     * the Principal needed for user authentication. It is initialized during deploying with not null value
     * using @Resource annotation (we do not check this because depends on configuration of EJB, but if it
     * does not correspond to the given configuration container will report about deployment error). Accessed
     * by the available web service operations to authenticate the users that performs the operations. Will
     * not change after deploying (we do not check this because depends on configuration of EJB, but if it
     * does not correspond to the given configuration container will report about deployment error). Valid
     * values: can not be null after injected when this bean is instantiated.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the log name used to initialize the log. Injected by the EJB container and never changed
     * afterwards. Accessed by the #initialize() method. Will not change after injection. Valid values: can be
     * null or empty String. OPTIONAL.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * <p>
     * This field represents the 'log' instance of the ClientStatusServiceBean. It will be used to perform the
     * logging operations. Contains an instance of Log interface. It can be enabled (not null) or disabled
     * (null). It is default to the default value of this data type when it is not assigned. Initialized in
     * setLog(..) method or initialize() method. After the bean is constructed, the log will have a valid
     * value set in the initialize() method - it is turned ON. Accessed thru corresponding getLog/setLog
     * methods. It is mutable. Valid values: can be any value. OPTIONAL. Used in all CRUD operations to log
     * the method details or exceptions.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * This field represents the 'verboseLogging' property of the ClientStatusServiceBean. The control flag
     * defining whether the detailed logging actions should be performed (true value), or standard logging
     * (only exceptions) should be supported (false value). It is default to 'false' when it is not assigned.
     * Initialized in setVerboseLogging(..) method. Accessed thru corresponding
     * getVerboseLogging/setVerboseLogging methods. It is mutable. Valid values: there are no restrictions at
     * this moment. It can take any value. OPTIONAL. Used in all CRUD operations to determine the type of the
     * log.
     * </p>
     */
    private boolean verboseLogging = false;

    /**
     * <p>
     * Default no-arg constructor. Constructs a new 'ClientStatusServiceBean' instance.
     * </p>
     */
    public ClientStatusServiceBean() {
        // Do nothing
    }

    /**
     * <p>
     * Initializes the client manager and log and makes sure the initialization of this session bean is OK.
     * This method is called after this bean is constructed by the EJB container.
     * </p>
     *
     * @throws ClientStatusServiceBeanConfigurationException
     *             if the required String fields are null or empty, if needed configurations are missing or
     *             invalid (invalid means null here), if Object Factory cannot initialize the needed
     *             dependencies
     */
    @PostConstruct
    protected void initialize() {

        // Check required resources
        checkValidString(clientManagerFile, "clientManagerFile");
        checkValidString(clientManagerNamespace, "clientManagerNamespace");

        // Initialize logging
        if (Util.isValidString(logName)) {
            log = LogManager.getLog(logName);
        } else {
            log = LogManager.getLog();
        }

        // Initialize clientManager
        try {
            clientManager = (ClientManager) Util.createManagerFromConfiguration(clientManagerFile,
                    clientManagerNamespace, "client_manager_token");
        } catch (IOException e) {
            throw new ClientStatusServiceBeanConfigurationException("I/O error occured while initializing "
                    + this.getClass(), e);
        } catch (BaseException e) {
            throw new ClientStatusServiceBeanConfigurationException("Error occured while initializing "
                    + this.getClass(), e);
        } catch (ClassCastException e) {
            throw new ClientStatusServiceBeanConfigurationException(
                    "ClassCastException occured while initializing " + this.getClass(), e);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the creation of the given client status in the persistence.
     * </p>
     *
     * @param status
     *            the client status that should be created. Should not be null.
     * @return the client status created in the persistence.
     * @throws IllegalArgumentException
     *             if the client status is null.
     * @throws IllegalStateException
     *             if the clientManager is not set (or is set to a null value).
     * @throws ClientStatusServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public ClientStatus createClientStatus(ClientStatus status) throws ClientStatusServiceException {

        String methodName = "createClientStatus";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "status", Util.formatClientStatus(status,
                verboseLogging), verboseLogging);
        ClientStatus result = null;

        try {
            Util.checkNullWithLog(log, status, "status");
            Util.checkResource(log, clientManager, "clientManager");

            // Update entity
            Util.onEntityUpdate(status, sessionContext, true);

            // Delegate to manager
            return (result = clientManager.createClientStatus(status));

        } catch (ClientManagerException e) {
            throw new ClientStatusServiceException(Util.describeError(e, methodName), Util.logException(log,
                    e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, Util.formatClientStatus(result,
                    verboseLogging), verboseLogging);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the update of the given client status in the persistence.
     * </p>
     *
     * @param status
     *            the client status that should be updated. Should not be null.
     * @return the client status updated in the persistence.
     * @throws IllegalArgumentException
     *             if the client status is null.
     * @throws IllegalStateException
     *             if the clientManager is not set (or is set to a null value).
     * @throws ClientStatusServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public ClientStatus updateClientStatus(ClientStatus status) throws ClientStatusServiceException {

        String methodName = "updateClientStatus";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "status", Util.formatClientStatus(status,
                verboseLogging), verboseLogging);
        ClientStatus result = null;

        try {
            Util.checkNullWithLog(log, status, "status");
            Util.checkResource(log, clientManager, "clientManager");

            // Update entity
            Util.onEntityUpdate(status, sessionContext, false);

            // Delegate to manager
            return (result = clientManager.updateClientStatus(status));

        } catch (ManagerException e) {
            throw new ClientStatusServiceException(Util.describeError(e, methodName), Util.logException(log,
                    e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, Util.formatClientStatus(result,
                    verboseLogging), verboseLogging);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the deletion of the given client status from the persistence.
     * </p>
     *
     * @param status
     *            the client status that should be deleted. Should not be null.
     * @return the client status deleted from the persistence.
     * @throws IllegalArgumentException
     *             if the client status is null.
     * @throws IllegalStateException
     *             if the clientManager is not set (or is set to a null value).
     * @throws ClientStatusServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public ClientStatus deleteClientStatus(ClientStatus status) throws ClientStatusServiceException {

        String methodName = "deleteClientStatus";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "status", Util.formatClientStatus(status,
                verboseLogging), verboseLogging);
        ClientStatus result = null;

        try {
            Util.checkNullWithLog(log, status, "status");
            Util.checkResource(log, clientManager, "clientManager");

            // Update entity
            Util.onEntityUpdate(status, sessionContext, false);

            // Delegate to manager
            return (result = clientManager.deleteClientStatus(status));

        } catch (ManagerException e) {
            throw new ClientStatusServiceException(Util.describeError(e, methodName), Util.logException(log,
                    e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, Util.formatClientStatus(result,
                    verboseLogging), verboseLogging);
        }
    }

    /**
     * <p>
     * Getter for 'verboseLogging' property. Please refer to the related field for more information.
     * </p>
     *
     * @return the value of the 'verboseLogging' property. It can be any value.
     */
    public boolean isVerboseLogging() {
        return verboseLogging;
    }

    /**
     * <p>
     * Setter for 'verboseLogging' property. Please refer to the related field for more information.
     * </p>
     *
     * @param verboseLogging
     *            the new value of the control flag defining whether the detailed logging actions should be
     *            performed (true value), or standard logging (only exceptions) should be supported (false
     *            value).
     */
    public void setVerboseLogging(boolean verboseLogging) {
        this.verboseLogging = verboseLogging;
    }

    /**
     * <p>
     * Getter for name of 'log' property. Please refer to the related field for more information.
     * </p>
     *
     * @return the value of the 'log' property. It can be any value.
     */
    public Log getLog() {
        return log;
    }

    /**
     * <p>
     * Setter for 'log' property. Please refer to the related field for more information.
     * </p>
     *
     * @param log
     *            the new log to set for log property. It cab be any value.
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * <p>
     * Checks passed string for being null or empty and throws ClientStatusServiceBeanConfigurationException
     * if it is.
     * </p>
     *
     * @param data
     *            String to check
     * @param name
     *            the data name to be used in the exception message
     * @throws ClientStatusServiceBeanConfigurationException
     *             if passed string is null or empty
     */
    private void checkValidString(String data, String name) {
        if (!Util.isValidString(data)) {
            throw new ClientStatusServiceBeanConfigurationException("Parameter [" + name + "] is invalid: "
                    + data);
        }
    }
}
