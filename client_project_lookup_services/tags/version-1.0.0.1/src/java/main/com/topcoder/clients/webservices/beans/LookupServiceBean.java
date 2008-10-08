/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
import com.topcoder.clients.manager.CompanyManager;
import com.topcoder.clients.manager.CompanyManagerException;
import com.topcoder.clients.manager.ProjectManager;
import com.topcoder.clients.manager.ProjectManagerException;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.LookupService;
import com.topcoder.clients.webservices.LookupServiceException;
import com.topcoder.clients.webservices.LookupServiceLocal;
import com.topcoder.clients.webservices.LookupServiceRemote;
import com.topcoder.clients.webservices.Util;
import com.topcoder.clients.webservices.usermapping.UserMappingRetrievalException;
import com.topcoder.clients.webservices.usermapping.UserMappingRetriever;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This class is a Stateless Session Bean endpoint realization of <code>LookupService</code> web service
 * interface. This class has a default no-arg constructor. It uses <code>CompanyManager</code>,
 * <code>ClientManager</code> and <code>ProjectManager</code> from Client Project Management component by
 * delegating to namesake methods to perform the operations. This class implements the methods available for
 * the <code>LookupService</code> web service: retrieve client, project, company, client status and project
 * status by id, retrieve all clients, projects, companies, client statuses and project statuses, search all
 * clients, projects, companies by name, search all clients, projects, companies by filter, retrieve clients
 * for status, retrieve projects for client, retrieve projects for status, retrieve projects for company,
 * retrieve clients for company.
 * <p>
 * LOGGING: All the available web service operations performs logging using the Logging Wrapper component.
 * </p>
 * <p>
 * AUTHENTICATION AND AUTHORIZATION: All the available web service operations performs the authentication and
 * authorization of the users that performs the operations using JBoss Login Module component and then
 * delegates to the Client Project Management component which performs the needed operations.
 * </p>
 * <p>
 * <b>Sample configuration:</b>
 *
 * <pre>
 * &lt;session&gt;
 *             &lt;ejb-name&gt;LookupServiceBean&lt;/ejb-name&gt;
 *             &lt;remote&gt;com.topcoder.clients.webservices.LookupServiceRemote&lt;/remote&gt;
 *             &lt;local&gt;com.topcoder.clients.webservices.LookupServiceLocal&lt;/local&gt;
 *             &lt;ejb-class&gt;com.topcoder.clients.webservices.beans.LookupServiceBean&lt;/ejb-class&gt;
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
 *                 &lt;description&gt;The project manager file to use&lt;/description&gt;
 *                 &lt;env-entry-name&gt;projectManagerFile&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;config.properties&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The company manager file to use&lt;/description&gt;
 *                 &lt;env-entry-name&gt;companyManagerFile&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;config.properties&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The client manager namespace to use&lt;/description&gt;
 *                 &lt;env-entry-name&gt;clientManagerNamespace&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;LookupServiceBean&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The project manager namespace to use&lt;/description&gt;
 *                 &lt;env-entry-name&gt;projectManagerNamespace&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;LookupServiceBean&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The company manager namespace to use&lt;/description&gt;
 *                 &lt;env-entry-name&gt;companyManagerNamespace&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;LookupServiceBean&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The user mapping retriever file to use&lt;/description&gt;
 *                 &lt;env-entry-name&gt;userMappingRetrieverFile&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;LookupServiceBean&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The user mapping retriever namespace to use&lt;/description&gt;
 *                 &lt;env-entry-name&gt;userMappingRetrieverNamespace&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;LookupServiceBean&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The name of the administrator role&lt;/description&gt;
 *                 &lt;env-entry-name&gt;administratorRole&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;Admin&lt;/env-entry-value&gt;
 *             &lt;/env-entry&gt;
 *             &lt;env-entry&gt;
 *                 &lt;description&gt;The name of the client and project user role&lt;/description&gt;
 *                 &lt;env-entry-name&gt;clientAndProjectUserRole&lt;/env-entry-name&gt;
 *                 &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *                 &lt;env-entry-value&gt;Client and Projects User&lt;/env-entry-value&gt;
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
 * String wsdlLocation =
 * &quot;http://topcoder.com:8080/jaxws/clientProject/LookupService/?wsdl&quot;;
 *
 * // create a LookupServiceClient client:
 * LookupServiceClient lookupServiceClient =
 * new LookupServiceClient (wsdlLocation);
 *
 * // get LookupService endpoint:
 * LookupService lookupService=
 * lookupServiceClient.getLookupServicePort();
 *
 * // we assume that there is already a filter provided by the application:
 * Filter filter=...;
 *
 * Client client;
 * List&lt;Client&gt; clients;
 *
 * ClientStatus clientStatus;
 * clientStatus = new ClientStatus();
 * clientStatus.setName(&quot;client status name&quot;);
 * clientStatus.setDescription(&quot;client status description&quot;);
 *
 * // retrieve client with the id 111222333:
 * client = lookupService.retrieveClient(111222333);
 * // if the user is in &quot;User&quot; or &quot;Admin&quot; roles then the operation has been performed
 * //otherwise an AuthorizationFailedException has been thrown.
 *
 * // retrieve all clients:
 * clients = lookupService.retrieveAllClients();
 * // if the user is in &quot;User&quot; or &quot;Admin&quot; roles then the operation has been performed
 * //otherwise an AuthorizationFailedException has been thrown.
 *
 * // search clients with the name 'TopCoder':
 * clients = lookupService.searchClientsByName(&quot;TopCoder&quot;);
 * // if the user is in &quot;User&quot; or &quot;Admin&quot; roles then the operation has been performed
 * //otherwise an AuthorizationFailedException has been thrown.
 *
 * // search clients with the given filter:
 * clients = lookupService.searchClients (filter);
 * // if the user is in &quot;User&quot; or &quot;Admin&quot; roles then the operation has been performed
 * // otherwise an AuthorizationFailedException has been thrown.
 *
 * // get clients with the given client status:
 * clients = lookupService.getClientsForStatus(clientStatus);
 * // if the user is in &quot;User&quot; or &quot;Admin&quot; roles then the operation has been performed
 * // otherwise an AuthorizationFailedException has been thrown.
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
@WebService(serviceName = "LookupService",
        endpointInterface = "com.topcoder.clients.webservices.LookupService",
        targetNamespace = "com.topcoder.clients.webservices.LookupService")
@Stateless(name = LookupService.BEAN_NAME)
@Local(LookupServiceLocal.class)
@Remote(LookupServiceRemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@DeclareRoles({Roles.USER, Roles.ADMIN })
@RolesAllowed({Roles.USER, Roles.ADMIN })
public class LookupServiceBean implements LookupServiceLocal, LookupServiceRemote, LookupService {
    /**
     * <p>
     * The name of this class.
     * </p>
     */
    private static final String CLASS_NAME = LookupServiceBean.class.getSimpleName();

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
     * Represents the project manager instance. Initialized in the #initialize() method with a not null value
     * and never changed afterwards. Accessed by the methods that delegates to project manager. Will not
     * change after initialization. Valid values: can not be null after this bean is constructed. REQUIRED.
     * </p>
     */
    private ProjectManager projectManager;

    /**
     * <p>
     * Represents the company manager instance. Initialized in the #initialize() method with a not null value
     * and never changed afterwards. Accessed by the methods that delegates to company manager. Will not
     * change after initialization. Valid values: can not be null after this bean is constructed. REQUIRED.
     * </p>
     */
    private CompanyManager companyManager;

    /**
     * <p>
     * Represents the user mapping retriever instance. Initialized in the #initialize() method with a not null
     * value and never changed afterwards. Accessed by the methods that calls the user mapping retriever. Will
     * not change after initialization. Valid values: can not be null after this bean is constructed.
     * REQUIRED.
     * </p>
     */
    private UserMappingRetriever userMappingRetriever;

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
     * Represents the project manager file. Injected by the EJB container with non null and not empty value
     * and never changed afterwards. Accessed by the #initialize() method. Will not change after injection.
     * Valid values: can not be null or empty String after injected when this bean is instantiated. REQUIRED.
     * </p>
     */
    @Resource(name = "projectManagerFile")
    private String projectManagerFile;

    /**
     * <p>
     * Represents the project manager namespace. Injected by the EJB container with non null and not empty
     * value and never changed afterwards. Accessed by the #initialize() method. Will not change after
     * injection. Valid values: can not be null or empty String after injected when this bean is instantiated.
     * REQUIRED.
     * </p>
     */
    @Resource(name = "projectManagerNamespace")
    private String projectManagerNamespace;

    /**
     * <p>
     * Represents the company manager file. Injected by the EJB container with possible null value but not
     * empty String value and never changed afterwards. Accessed by the #initialize() method. Will not change
     * after injection. Valid values: can be null but not empty String after injected when this bean is
     * instantiated.
     * </p>
     */
    @Resource(name = "companyManagerFile")
    private String companyManagerFile;

    /**
     * <p>
     * Represents the company manager namespace. Injected by the EJB container with non null and not empty
     * value and never changed afterwards. Accessed by the #initialize() method. Will not change after
     * injection. Valid values: can not be null or empty String after injected when this bean is instantiated.
     * </p>
     */
    @Resource(name = "companyManagerNamespace")
    private String companyManagerNamespace;

    /**
     * <p>
     * Represents the user mapping retriever file. Injected by the EJB container with non null and not empty
     * value and never changed afterwards. Accessed by the #initialize() method. Will not change after
     * injection. Valid values: can not be null or empty String after injected when this bean is instantiated.
     * REQUIRED.
     * </p>
     */
    @Resource(name = "userMappingRetrieverFile")
    private String userMappingRetrieverFile;

    /**
     * <p>
     * Represents the user mapping retriever namespace. Injected by the EJB container with non null and not
     * empty value and never changed afterwards. Accessed by the #initialize() method. Will not change after
     * injection. Valid values: can not be null or empty String after injected when this bean is instantiated.
     * REQUIRED.
     * </p>
     */
    @Resource(name = "userMappingRetrieverNamespace")
    private String userMappingRetrieverNamespace;

    /**
     * <p>
     * Represents the administrator role used to check if the user is administrator. Injected by the EJB
     * container and never changed afterwards. Accessed by all the available web service operations to
     * authenticate the users that performs the operations. Will not change after injection. Valid values: can
     * be null or empty String. OPTIONAL.
     * </p>
     */
    @Resource(name = "administratorRole")
    private String administratorRole;

    /**
     * <p>
     * Represents the client and project user role used to check if the user is a client and project user.
     * Injected by the EJB container and never changed afterwards. Accessed by all the available web service
     * operations to authenticate the client and project users that performs the operations. Will not change
     * after injection. Valid values: can be null or empty String. OPTIONAL.
     * </p>
     */
    @Resource(name = "clientAndProjectUserRole")
    private String clientAndProjectUserRole;

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
     * This field represents the 'log' instance of the LookupServiceBean. It will be used to perform the
     * logging operations. Contains an instance of Log interface. It can be enabled (not null) or disabled
     * (null). It is default to the default value of this data type when it is not assigned. Initialized in
     * setLog(..) method or initialize() method. After the bean is constructed, the log will have a valid
     * value set in the initialize() method - it is turned ON. Accessed thru corresponding getLog/setLog
     * methods. It is mutable. Valid values: can be any value. OPTIONAL. Used in all lookup operations to log
     * the method details or exceptions.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * This field represents the 'verboseLogging' property of the LookupServiceBean. The control flag defining
     * whether the detailed logging actions should be performed (true value), or standard logging (only
     * exceptions) should be supported (false value). It is default to 'false' when it is not assigned.
     * Initialized in setVerboseLogging(..) method. Accessed thru corresponding
     * getVerboseLogging/setVerboseLogging methods. It is mutable. Valid values: there are no restrictions at
     * this moment. It can take any value. OPTIONAL. Used in all CRUD operations to determine the type of the
     * log.
     * </p>
     */
    private boolean verboseLogging = false;

    /**
     * <p>
     * Default no-arg constructor. Constructs a new 'LookupServiceBean' instance.
     * </p>
     */
    public LookupServiceBean() {
        // Do nothing
    }

    /**
     * <p>
     * Initialize the project manager, client manager, company manager, user mapping retreiver and log and
     * makes sure the initialization of this session bean is ok. This method is called after this bean is
     * constructed by the EJB container.
     * </p>
     *
     * @throws LookupServiceBeanConfigurationException
     *             if the required String fields are null or empty, if needed configurations are missing or
     *             invalid (invalid means null here), if Object Factory cannot initialize the needed
     *             dependencies.
     */
    @PostConstruct
    protected void initialize() {

        // Check required resources
        checkValidString(clientManagerFile, "clientManagerFile");
        checkValidString(clientManagerNamespace, "clientManagerNamespace");
        checkValidString(companyManagerFile, "companyManagerFile");
        checkValidString(companyManagerNamespace, "companyManagerNamespace");
        checkValidString(projectManagerFile, "projectManagerFile");
        checkValidString(projectManagerNamespace, "projectManagerNamespace");
        checkValidString(userMappingRetrieverFile, "userMappingRetrieverFile");
        checkValidString(userMappingRetrieverNamespace, "userMappingRetrieverNamespace");

        // Check roles
        if (!Util.isValidString(administratorRole)) {
            administratorRole = null;
        }
        if (!Util.isValidString(clientAndProjectUserRole)) {
            clientAndProjectUserRole = null;
        }
        if ((administratorRole == null) && (clientAndProjectUserRole == null)) {
            throw new LookupServiceBeanConfigurationException(
                    "At least one of {administratorRole, clientAndProjectUserRole} resources should be set.");
        }

        // Initialize logging
        if (Util.isValidString(logName)) {
            log = LogManager.getLog(logName);
        } else {
            log = LogManager.getLog();
        }

        // Initialize managers and userMappingRetriever
        try {
            clientManager = (ClientManager) Util.createManagerFromConfiguration(clientManagerFile,
                    clientManagerNamespace, "client_manager_token");
            companyManager = (CompanyManager) Util.createManagerFromConfiguration(companyManagerFile,
                    companyManagerNamespace, "company_manager_token");
            projectManager = (ProjectManager) Util.createManagerFromConfiguration(projectManagerFile,
                    projectManagerNamespace, "project_manager_token");
            userMappingRetriever = (UserMappingRetriever) Util.createManagerFromConfiguration(
                    userMappingRetrieverFile, userMappingRetrieverNamespace, "user_mapping_retriever_token");
        } catch (IOException e) {
            throw new LookupServiceBeanConfigurationException("I/O error occured while initializing "
                    + this.getClass(), e);
        } catch (BaseException e) {
            throw new LookupServiceBeanConfigurationException("Error occured while initializing "
                    + this.getClass(), e);
        } catch (ClassCastException e) {
            throw new LookupServiceBeanConfigurationException(
                    "ClassCastException occured while initializing " + this.getClass(), e);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of an client using the given id from the persistence.
     * </p>
     *
     * @param id
     *            the identifier of the client that should be retrieved. Should be positive.
     * @return the client with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     * @throws IllegalStateException
     *             if the clientManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public Client retrieveClient(long id) throws LookupServiceException, AuthorizationFailedException {

        String methodName = "retrieveClient";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "id", Long.toString(id), verboseLogging);
        Client result = null;

        try {
            Util.checkPositiveWithLog(log, id, "id");
            Util.checkResource(log, clientManager, "clientManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            Client uncheckedClient = clientManager.retrieveClient(id);

            // Authorization check
            return (result = checkClient(uncheckedClient, isAdmin));

        } catch (ClientManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatClient(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all clients from the persistence. If nothing is
     * found, return an empty list.
     * </p>
     *
     * @return the list of clients found in the persistence. If nothing is found, return an empty list.
     * @throws IllegalStateException
     *             if the clientManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Client > retrieveAllClients() throws LookupServiceException, AuthorizationFailedException {

        String methodName = "retrieveAllClients";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, null, null, verboseLogging);
        List < Client > result = null;

        try {
            Util.checkResource(log, clientManager, "clientManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Client > uncheckedClients = clientManager.retrieveAllClients();

            // Authorization check
            return (result = checkClients(uncheckedClients, isAdmin));

        } catch (ClientManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatClientList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all clients that have the given name in the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param name
     *            the name of the client that should be retrieved. Should be not empty and not null.
     * @return the list with clients with the given name retrieved from the persistence. If nothing is found,
     *         return an empty list.
     * @throws IllegalArgumentException
     *             if the name is null or empty string.
     * @throws IllegalStateException
     *             if the clientManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Client > searchClientsByName(String name) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "searchClientsByName";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "name", name, verboseLogging);
        List < Client > result = null;

        try {
            Util.checkNullOrEmpty(log, name, "name");
            Util.checkResource(log, clientManager, "clientManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Client > uncheckedClients = clientManager.searchClientsByName(name);

            // Authorization check
            return (result = checkClients(uncheckedClients, isAdmin));

        } catch (ClientManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatClientList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all clients that match the given filter in the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param filter
     *            the filter that should be used to search the matched clients. Should not be null.
     * @return the list with clients that match the given filter retrieved from the persistence. If nothing is
     *         found, return an empty list.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws IllegalStateException
     *             if the clientManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Client > searchClients(Filter filter) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "searchClients";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "filter", formatFilter(filter,
                verboseLogging), verboseLogging);
        List < Client > result = null;

        try {
            Util.checkNullWithLog(log, filter, "filter");
            Util.checkResource(log, clientManager, "clientManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Client > uncheckedClients = clientManager.searchClients(filter);

            // Authorization check
            return (result = checkClients(uncheckedClients, isAdmin));

        } catch (ClientManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatClientList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of an client status using the given id from the
     * persistence.
     * </p>
     *
     * @param id
     *            the identifier of the client status that should be retrieved. Should be positive.
     * @return the client status with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     * @throws IllegalStateException
     *             if the clientManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public ClientStatus getClientStatus(long id) throws LookupServiceException, AuthorizationFailedException {

        String methodName = "getClientStatus";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "id", Long.toString(id), verboseLogging);
        ClientStatus result = null;

        try {
            Util.checkPositiveWithLog(log, id, "id");
            Util.checkResource(log, clientManager, "clientManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            authenticationCheck(methodName);

            // Delegate to manager
            return (result = clientManager.getClientStatus(id));

        } catch (ClientManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, Util.formatClientStatus(result,
                    verboseLogging), verboseLogging);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all client statuses from the persistence. If
     * nothing is found, return an empty list.
     * </p>
     *
     * @return the list of client statuses found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws IllegalStateException
     *             if the clientManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < ClientStatus > getAllClientStatuses() throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "getAllClientStatuses";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, null, null, verboseLogging);
        List < ClientStatus > result = null;

        try {
            Util.checkResource(log, clientManager, "clientManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            authenticationCheck(methodName);

            // Delegate to manager
            return (result = clientManager.getAllClientStatuses());

        } catch (ClientManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatClientStatusList(result,
                    verboseLogging), verboseLogging);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with clients with the given client status
     * from the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param status
     *            the given client status to retrieve it's clients. Should not be null.
     * @return the list of clients for the given client status found in the persistence. If nothing is found,
     *         return an empty list.
     * @throws IllegalArgumentException
     *             if the client status is null.
     * @throws IllegalStateException
     *             if the clientManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Client > getClientsForStatus(ClientStatus status) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "getClientsForStatus";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "status", Util.formatClientStatus(status,
                verboseLogging), verboseLogging);
        List < Client > result = null;

        try {
            Util.checkNullWithLog(log, status, "status");
            Util.checkResource(log, clientManager, "clientManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Client > uncheckedClients = clientManager.getClientsForStatus(status);

            // Authorization check
            return (result = checkClients(uncheckedClients, isAdmin));

        } catch (ClientManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatClientList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of an project using the given id from the persistence.
     * </p>
     *
     * @param id
     *            the identifier of the project that should be retrieved. Should be positive.
     * @return the project with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     * @throws IllegalStateException
     *             if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public Project retrieveProject(long id) throws LookupServiceException, AuthorizationFailedException {

        String methodName = "retrieveProject";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "id", Long.toString(id), verboseLogging);
        Project result = null;

        try {
            Util.checkPositiveWithLog(log, id, "id");
            Util.checkResource(log, projectManager, "projectManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            Project uncheckedProject = projectManager.retrieveProject(id);

            // Authorization check
            return (result = checkProject(uncheckedProject, isAdmin));

        } catch (ProjectManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatProject(result, verboseLogging),
                    verboseLogging);
        }

    }

    /**
     * <p>
     * Performs the retrieval of the list with projects with the given client from the persistence. If nothing
     * is found, return an empty list.
     * </p>
     *
     * @param client
     *            the given client to retrieve it's projects. Should not be null.
     * @return the list of projects for the given client found in the persistence. If nothing is found, return
     *         an empty list.
     * @throws IllegalArgumentException
     *             if the client is null.
     * @throws IllegalStateException
     *             if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Project > retrieveProjectsForClient(Client client) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "retrieveProjectsForClient";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "client", formatClient(client,
                verboseLogging), verboseLogging);
        List < Project > result = null;

        try {
            Util.checkNullWithLog(log, client, "client");
            Util.checkResource(log, projectManager, "projectManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Project > uncheckedProjects = projectManager.retrieveProjectsForClient(client);

            // Authorization check
            return (result = checkProjects(uncheckedProjects, isAdmin));

        } catch (ProjectManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatProjectList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of all projects from the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @return the list of projects found in the persistence. If nothing is found, return an empty list.
     * @throws IllegalStateException
     *             if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Project > retrieveAllProjects() throws LookupServiceException, AuthorizationFailedException {

        String methodName = "retrieveAllProjects";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, null, null, verboseLogging);
        List < Project > result = null;

        try {
            Util.checkResource(log, projectManager, "projectManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Project > uncheckedProjects = projectManager.retrieveAllProjects();

            // Authorization check
            return (result = checkProjects(uncheckedProjects, isAdmin));

        } catch (ProjectManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatProjectList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of all projects that have the given name in the persistence. If nothing is
     * found, return an empty list.
     * </p>
     *
     * @param name
     *            the name of the project that should be retrieved. Should be not empty and not null.
     * @return the list with projects with the given name retrieved from the persistence. If nothing is found,
     *         return an empty list.
     * @throws IllegalArgumentException
     *             if the name is null or empty string.
     * @throws IllegalStateException
     *             if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Project > searchProjectsByName(String name) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "searchProjectsByName";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "name", name, verboseLogging);
        List < Project > result = null;

        try {
            Util.checkNullOrEmpty(log, name, "name");
            Util.checkResource(log, projectManager, "projectManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Project > uncheckedProjects = projectManager.searchProjectsByName(name);

            // Authorization check
            return (result = checkProjects(uncheckedProjects, isAdmin));

        } catch (ProjectManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatProjectList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of all projects that match the given filter in the persistence. If nothing is
     * found, return an empty list.
     * </p>
     *
     * @param filter
     *            the filter that should be used to search the matched projects. Should not be null.
     * @return the list with projects that match the given filter retrieved from the persistence. If nothing
     *         is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws IllegalStateException
     *             if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Project > searchProjects(Filter filter) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "searchProjects";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "filter", formatFilter(filter,
                verboseLogging), verboseLogging);
        List < Project > result = null;

        try {
            Util.checkNullWithLog(log, filter, "filter");
            Util.checkResource(log, projectManager, "projectManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Project > uncheckedProjects = projectManager.searchProjects(filter);

            // Authorization check
            return (result = checkProjects(uncheckedProjects, isAdmin));

        } catch (ProjectManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatProjectList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of an project status using the given id from the persistence.
     * </p>
     *
     * @param id
     *            the identifier of the project status that should be retrieved. Should be positive.
     * @return the project status with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     * @throws IllegalStateException
     *             if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public ProjectStatus getProjectStatus(long id) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "getProjectStatus";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "id", Long.toString(id), verboseLogging);
        ProjectStatus result = null;

        try {
            Util.checkPositiveWithLog(log, id, "id");
            Util.checkResource(log, projectManager, "projectManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            authenticationCheck(methodName);

            // Delegate to manager
            return (result = projectManager.getProjectStatus(id));

        } catch (ProjectManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, Util.formatProjectStatus(result,
                    verboseLogging), verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of all project statuses from the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @return the list of project statuses found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws IllegalStateException
     *             if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < ProjectStatus > getAllProjectStatuses() throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "getAllProjectStatuses";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, null, null, verboseLogging);
        List < ProjectStatus > result = null;

        try {
            Util.checkResource(log, projectManager, "projectManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            authenticationCheck(methodName);

            // Delegate to manager
            return (result = projectManager.getAllProjectStatuses());

        } catch (ProjectManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatProjectStatusList(result,
                    verboseLogging), verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of the list with projects with the given project status from the persistence. If
     * nothing is found, return an empty list.
     * </p>
     *
     * @param status
     *            the given project status to retrieve it's projects. Should not be null.
     * @return the list of projects for the given project status found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws IllegalArgumentException
     *             if the project status is null.
     * @throws IllegalStateException
     *             if the projectManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Project > getProjectsForStatus(ProjectStatus status) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "getProjectsForStatus";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "status", Util.formatProjectStatus(
                status, verboseLogging), verboseLogging);
        List < Project > result = null;

        try {
            Util.checkNullWithLog(log, status, "status");
            Util.checkResource(log, projectManager, "projectManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Project > uncheckedProjects = projectManager.getProjectsForStatus(status);

            // Authorization check
            return (result = checkProjects(uncheckedProjects, isAdmin));

        } catch (ProjectManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatProjectList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of an project using the given id from the persistence.
     * </p>
     *
     * @param id
     *            the identifier of the company that should be retrieved. Should be positive.
     * @return the company with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *             if the id &lt;= 0.
     * @throws IllegalStateException
     *             if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public Company retrieveCompany(long id) throws LookupServiceException, AuthorizationFailedException {

        String methodName = "retrieveCompany";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "id", Long.toString(id), verboseLogging);
        Company result = null;

        try {
            Util.checkPositiveWithLog(log, id, "id");
            Util.checkResource(log, companyManager, "companyManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            authenticationCheck(methodName);

            // Delegate to manager
            return (result = companyManager.retrieveCompany(id));

        } catch (CompanyManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatCompany(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of all companies from the persistence. If nothing is found, return an empty
     * list.
     * </p>
     *
     * @return the list of companies found in the persistence. If nothing is found, return an empty list.
     * @throws IllegalStateException
     *             if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Company > retrieveAllCompanies() throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "retrieveAllCompanies";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, null, null, verboseLogging);
        List < Company > result = null;

        try {
            Util.checkResource(log, companyManager, "companyManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            authenticationCheck(methodName);

            // Delegate to manager
            return (result = companyManager.retrieveAllCompanies());

        } catch (CompanyManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatCompanyList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of all companies that have the given name in the persistence. If nothing is
     * found, return an empty list.
     * </p>
     *
     * @param name
     *            the name of the company that should be retrieved. Should be not empty and not null.
     * @return the list with companies with the given name retrieved from the persistence. If nothing is
     *         found, return an empty list.
     * @throws IllegalArgumentException
     *             if the name is null or empty string.
     * @throws IllegalStateException
     *             if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Company > searchCompaniesByName(String name) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "searchCompaniesByName";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "name", name, verboseLogging);
        List < Company > result = null;

        try {
            Util.checkNullOrEmpty(log, name, "name");
            Util.checkResource(log, companyManager, "companyManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            authenticationCheck(methodName);

            // Delegate to manager
            return (result = companyManager.searchCompaniesByName(name));

        } catch (CompanyManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatCompanyList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of all companies that match the given filter in the persistence. If nothing is
     * found, return an empty list.
     * </p>
     *
     * @param filter
     *            the filter that should be used to search the matched companies. Should not be null.
     * @return the list with companies that match the given filter retrieved from the persistence. If nothing
     *         is found, return an empty list.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws IllegalStateException
     *             if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Company > searchCompanies(Filter filter) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "searchCompanies";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "filter", formatFilter(filter,
                verboseLogging), verboseLogging);
        List < Company > result = null;

        try {
            Util.checkNullWithLog(log, filter, "filter");
            Util.checkResource(log, companyManager, "companyManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            authenticationCheck(methodName);

            // Delegate to manager
            return (result = companyManager.searchCompanies(filter));

        } catch (CompanyManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatCompanyList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of the list with clients for the given company from the persistence. If nothing
     * is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's clients. Should not be null.
     * @return the list of clients for the given company found in the persistence. If nothing is found, return
     *         an empty list.
     * @throws IllegalArgumentException
     *             if the company is null.
     * @throws IllegalStateException
     *             if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Client > getClientsForCompany(Company company) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "getClientsForCompany";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "company", formatCompany(company,
                verboseLogging), verboseLogging);
        List < Client > result = null;

        try {
            Util.checkNullWithLog(log, company, "company");
            Util.checkResource(log, companyManager, "companyManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Client > uncheckedClients = companyManager.getClientsForCompany(company);

            // Authorization check
            return (result = checkClients(uncheckedClients, isAdmin));

        } catch (CompanyManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatClientList(result, verboseLogging),
                    verboseLogging);
        }
    }

    /**
     * <p>
     * Performs the retrieval of the list with projects for the given company from the persistence. If nothing
     * is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's projects. Should not be null.
     * @return the list of projects for the given company found in the persistence. If nothing is found,
     *         return an empty list.
     * @throws IllegalArgumentException
     *             if the company is null.
     * @throws IllegalStateException
     *             if the companyManager or userMappingRetriever are not set (or are set to a null value).
     * @throws AuthorizationFailedException
     *             if the user is not authorized to perform the operation.
     * @throws LookupServiceException
     *             if any error occurs while performing this operation.
     */
    @WebMethod
    public List < Project > getProjectsForCompany(Company company) throws LookupServiceException,
            AuthorizationFailedException {

        String methodName = "getProjectsForCompany";
        long startTime = Util.logEnter(log, CLASS_NAME, methodName, "company", formatCompany(company,
                verboseLogging), verboseLogging);
        List < Project > result = null;

        try {
            Util.checkNullWithLog(log, company, "company");
            Util.checkResource(log, companyManager, "companyManager");
            Util.checkResource(log, userMappingRetriever, "userMappingRetriever");

            // Authentication check
            boolean isAdmin = authenticationCheck(methodName);

            // Delegate to manager
            List < Project > uncheckedProjects = companyManager.getProjectsForCompany(company);

            // Authorization check
            return (result = checkProjects(uncheckedProjects, isAdmin));

        } catch (CompanyManagerException e) {
            throw new LookupServiceException(Util.describeError(e, methodName), Util.logException(log, e));
        } finally {
            Util.logExit(log, CLASS_NAME, methodName, startTime, formatProjectList(result, verboseLogging),
                    verboseLogging);
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
     * Returns user ID obtained from the caller principal of the SessionContext.
     * </p>
     *
     * @return user ID of the caller
     * @throws AuthorizationFailedException
     *             if caller principal isn't of UserProfilePrincipal type
     */
    private long getUserId() throws AuthorizationFailedException {

        // Obtain the user profile principal
        Principal principal = sessionContext.getCallerPrincipal();
        if (!(principal instanceof UserProfilePrincipal)) {
            throw new AuthorizationFailedException(
                    "Caller principal should be of the UserProfilePrincipal type. "
                            + "Perhaps JBoss Login Module isn't configured properly.");
        }
        UserProfilePrincipal userProfile = (UserProfilePrincipal) principal;

        // Obtain and return the user ID
        return userProfile.getUserId();
    }

    /**
     * <p>
     * Checks if the caller is authenticated as administratorRole or clientAndProjectUserRole, throws
     * AuthorizationFailedException if it isn't. Returns whether the caller is an administrator.
     * </p>
     *
     * @param methodName
     *            the method in which this check is performed
     * @return whether the caller is an administrator
     * @throws AuthorizationFailedException
     *             if user isn't authenticated
     */
    private boolean authenticationCheck(String methodName) throws AuthorizationFailedException {

        // Check whether the caller is an administrator
        if ((administratorRole != null) && sessionContext.isCallerInRole(administratorRole)) {
            // Indicates that caller is an administrator
            return true;
        }

        // Check whether the caller has clientAndProjectUserRole role
        if ((clientAndProjectUserRole != null) && sessionContext.isCallerInRole(clientAndProjectUserRole)) {
            // Indicates that caller isn't an administrator
            return false;
        }

        // If user isn't authenticated
        throw Util.logException(log, new AuthorizationFailedException("User isn't authenticated to perform ["
                + methodName + "] operation."));
    }

    /**
     * <p>
     * Checks if caller from SessionContext is allowed to perform operations with passed Client.
     * </p>
     *
     * @param client
     *            the Client to check
     * @param isAdmin
     *            indicates whether the user is an administrator. If it is - no authentication should be
     *            performed
     * @return passed Client
     * @throws AuthorizationFailedException
     *             if user is restricted to perform operations with passed Client
     * @throws LookupServiceException
     *             if error occurs during mapping retrieval
     */
    private Client checkClient(Client client, boolean isAdmin) throws AuthorizationFailedException,
            LookupServiceException {

        // If user is an administrator no authentication should be performed
        if (isAdmin) {
            return client;
        }

        List < Client > clientsForUserId;
        try {
            clientsForUserId = userMappingRetriever.getClientsForUserId(getUserId());
        } catch (UserMappingRetrievalException e) {
            throw Util.logException(log, new LookupServiceException(
                    "Error occured during mapping retrieval.", e));
        }
        if (!contains(clientsForUserId, client)) {
            throw Util.logException(log, new AuthorizationFailedException(
                    "User is restricted to perform operations with [" + formatClient(client, true)
                            + "] client."));
        }

        return client;
    }

    /**
     * <p>
     * Checks if caller from SessionContext is allowed to perform operations with passed Clients and return
     * only allowed ones.
     * </p>
     *
     * @param clients
     *            the Clients list to check
     * @param isAdmin
     *            indicates whether the user is an administrator. If it is - no authentication should be
     *            performed
     * @return modified Clients list which contains only objects visible to the caller
     * @throws LookupServiceException
     *             if error occurs during mapping retrieval
     * @throws AuthorizationFailedException
     *             if caller principal isn't of UserProfilePrincipal type
     */
    private List < Client > checkClients(List < Client > clients, boolean isAdmin)
            throws LookupServiceException, AuthorizationFailedException {

        // If user is an administrator no authentication should be performed
        if (isAdmin) {
            return clients;
        }

        List < Client > validClients = new ArrayList < Client >();
        List < Client > clientsForUserId;
        try {
            clientsForUserId = userMappingRetriever.getClientsForUserId(getUserId());
        } catch (UserMappingRetrievalException e) {
            throw Util.logException(log, new LookupServiceException(
                    "Error occured during mapping retrieval.", e));
        }
        for (Client client : clients) {
            if (contains(clientsForUserId, client)) {
                validClients.add(client);
            }
        }

        return validClients;
    }

    /**
     * <p>
     * Checks if caller from SessionContext is allowed to perform operations with passed Project.
     * </p>
     *
     * @param project
     *            the Project to check
     * @param isAdmin
     *            indicates whether the user is an administrator. If it is - no authentication should be
     *            performed
     * @return passed Project
     * @throws AuthorizationFailedException
     *             if user is restricted to perform operations with passed Project
     * @throws LookupServiceException
     *             if error occurs during mapping retrieval
     */
    private Project checkProject(Project project, boolean isAdmin) throws AuthorizationFailedException,
            LookupServiceException {

        // If user is an administrator no authentication should be performed
        if (isAdmin) {
            return project;
        }

        List < Project > projectsForUserId;
        try {
            projectsForUserId = userMappingRetriever.getProjectsForUserId(getUserId());
        } catch (UserMappingRetrievalException e) {
            throw Util.logException(log, new LookupServiceException(
                    "Error occured during mapping retrieval.", e));
        }
        if (!contains(projectsForUserId, project)) {
            throw Util.logException(log, new AuthorizationFailedException(
                    "User is restricted to perform operations with [" + formatProject(project, true)
                            + "] project."));
        }

        return project;
    }

    /**
     * <p>
     * Checks if caller from SessionContext is allowed to perform operations with passed Projects and return
     * only allowed ones.
     * </p>
     *
     * @param projects
     *            the Projects list to check
     * @param isAdmin
     *            indicates whether the user is an administrator. If it is - no authentication should be
     *            performed
     * @return modified Projects list which contains only objects visible to the caller
     * @throws LookupServiceException
     *             if error occurs during mapping retrieval
     * @throws AuthorizationFailedException
     *             if caller principal isn't of UserProfilePrincipal type
     */
    private List < Project > checkProjects(List < Project > projects, boolean isAdmin)
            throws LookupServiceException, AuthorizationFailedException {

        // If user is an administrator no authentication should be performed
        if (isAdmin) {
            return projects;
        }

        List < Project > validProjects = new ArrayList < Project >();
        List < Project > projectsForUserId;
        try {
            projectsForUserId = userMappingRetriever.getProjectsForUserId(getUserId());
        } catch (UserMappingRetrievalException e) {
            throw Util.logException(log, new LookupServiceException(
                    "Error occured during mapping retrieval.", e));
        }

        for (Project project : projects) {
            if (contains(projectsForUserId, project)) {
                validProjects.add(project);
            }
        }

        return validProjects;
    }

    /**
     * <p>
     * Checks passed string for being null or empty and throws LookupServiceBeanConfigurationException if it
     * is.
     * </p>
     *
     * @param data
     *            String to check
     * @param name
     *            the data name to be used in the exception message
     * @throws LookupServiceBeanConfigurationException
     *             if passed string is null or empty
     */
    private void checkValidString(String data, String name) {
        if (!Util.isValidString(data)) {
            throw new LookupServiceBeanConfigurationException("Parameter [" + name + "] is invalid: " + data);
        }
    }

    /**
     * <p>
     * Format the client for log.
     * </p>
     *
     * @param client
     *            the Client entity to format
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return a String format of Client
     */
    private String formatClient(Client client, boolean verbose) {
        if (client == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(" id=" + client.getId());
        builder.append(" name=" + client.getName());
        builder.append(" codeName=" + client.getCodeName());
        builder.append(" salesTax=" + client.getSalesTax());
        builder.append(" isDeleted=" + client.isDeleted());

        // format the company attribute
        builder.append(" company=" + formatCompany(client.getCompany(), true));

        // format the ClientStatus attribute
        builder.append(" clientStatus=" + Util.formatClientStatus(client.getClientStatus(), true));

        builder.append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Format a Company for log.
     * </p>
     *
     * @param company
     *            the company to format
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return String format of Company
     */
    private String formatCompany(Company company, boolean verbose) {
        if (company == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");
        builder.append(" id=" + company.getId());
        builder.append(" name=" + company.getName());
        builder.append(" passcode=" + company.getPasscode());
        builder.append(" isDeleted=" + company.isDeleted());

        builder.append("]");
        return builder.toString();
    }

    /**
     * <p>
     * Format a list of Client entities for log.
     * </p>
     *
     * @param clients
     *            the list of client to format
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return a String format contain all clients main information
     */
    private String formatClientList(List < Client > clients, boolean verbose) {
        if (clients == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (Client client : clients) {
            builder.append(formatClient(client, true));
            builder.append(" ");
        }
        if (clients.size() > 0) {
            // Delete trailing space
            builder.deleteCharAt(builder.length() - 1);
        }

        builder.append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Format a list of ClientStatus entities for log.
     * </p>
     *
     * @param clientStatuses
     *            the list of ClientStatus to format
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return a String format contain all ClientStatus's main information
     */
    private String formatClientStatusList(List < ClientStatus > clientStatuses, boolean verbose) {
        if (clientStatuses == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (ClientStatus status : clientStatuses) {
            builder.append(Util.formatClientStatus(status, true));
            builder.append(" ");
        }

        // Delete trailing space
        builder.deleteCharAt(builder.length() - 1).append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Format a list of Company for log.
     * </p>
     *
     * @param companies
     *            the list of company instances to be formated.
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return String format of a list of Company
     */
    private String formatCompanyList(List < Company > companies, boolean verbose) {
        if (companies == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (Company company : companies) {
            builder.append(formatCompany(company, true));
            builder.append(" ");
        }
        if (companies.size() > 0) {
            // Delete trailing space
            builder.deleteCharAt(builder.length() - 1);
        }

        builder.append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Format the Project entity for log.
     * </p>
     *
     * @param project
     *            the project entity to format
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return the String format of Project entity , containing main information
     */
    private String formatProject(Project project, boolean verbose) {
        if (project == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(" id=" + project.getId());
        builder.append(" name=" + project.getName());
        builder.append(" description=" + project.getDescription());
        builder.append(" salesTax=" + project.getSalesTax());

        // log the client.
        builder.append(" client=" + formatClient(project.getClient(), true));

        // log the company
        builder.append(" company=" + formatCompany(project.getCompany(), true));

        // log the child projects
        builder.append(" childProjects=" + formatProjectList(project.getChildProjects(), true));

        builder.append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Format a list of Project for log.
     * </p>
     *
     * @param projects
     *            the list of Project to be formated
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return String format of Project
     */
    private String formatProjectList(List < Project > projects, boolean verbose) {
        if (projects == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (Project p : projects) {
            builder.append(formatProject(p, true));
            builder.append(" ");
        }
        if (projects.size() > 0) {
            // Delete trailing space
            builder.deleteCharAt(builder.length() - 1);
        }

        builder.append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Format a list of ProjectStatus entities for log.
     * </p>
     *
     * @param statuses
     *            the list of ProjectStatus to format
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return a String format contain all ProjectStatus's main information
     */
    private String formatProjectStatusList(List < ProjectStatus > statuses, boolean verbose) {
        if (statuses == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");

        for (ProjectStatus status : statuses) {
            builder.append(Util.formatProjectStatus(status, true));
            builder.append(" ");
        }

        // Delete trailing space
        builder.deleteCharAt(builder.length() - 1).append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Format a Filter object for log.
     * </p>
     *
     * @param filter
     *            the filter to format
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return a String format of filter
     */
    private String formatFilter(Filter filter, boolean verbose) {
        if (filter == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        return filter.toString();
    }

    /**
     * <p>
     * Checks whether list contains passed entity by comparing item's IDs.
     * </p>
     *
     * @param <T>
     *            the type of the entities
     * @param list
     *            the list that could contain passed entity
     * @param item
     *            entity to check
     * @return true if list contatins passed entity, false otherwise
     */
    private < T extends AuditableEntity > boolean contains(List < T > list, T item) {
        for (T listItem : list) {
            if (listItem.getId() == item.getId()) {
                return true;
            }
        }
        return false;
    }
}
