/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.confluence.ConfluenceAuthenticationFailedException;
import com.topcoder.confluence.ConfluenceConnectionException;
import com.topcoder.confluence.ConfluenceManager;
import com.topcoder.confluence.ConfluenceManagerException;
import com.topcoder.confluence.ConfluenceNotAuthorizedException;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceAuthenticationFailedException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceConfigurationException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceConnectionException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceLocal;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceNotAuthorizedException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceRemote;
import com.topcoder.confluence.webservice.Helper;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.SpecificationFactoryException;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;

/**
 * <p>
 * The stateless session bean that implements all operations in the ConfluenceManagementService to expose the
 * ConfluenceManager methods as web services. All its methods are exposed, and all methods are just delegated to.
 * </p>
 * <p>
 * It uses the Configuration API and Persistence components to obtain configuration information. It uses the Object
 * Factory and its Configuration API-backed specification factory to instantiate the ConfluenceManager, and the Log
 * Manager to perform logging of activity and errors.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This bean is mutable and not thread-safe as it deals with non-thread-safe entities.
 * However, in the context of being used in a container, it is thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@WebService(endpointInterface = "com.topcoder.confluence.webservice.ConfluenceManagementService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfluenceManagementServiceBean implements ConfluenceManagementServiceLocal,
    ConfluenceManagementServiceRemote {

    /**
     * <p>
     * Represents the default configuration path to create the ConfigurationFileManager.
     * </p>
     * <p>
     * It would be used in the initialize method if the corresponding ConfluenceManagerFile variable is not
     * injected.
     * </p>
     */
    public static final String DEFAULT_CONFIG_PATH =
        "com/topcoder/confluence/webservice/bean/ConfluenceManagementServiceBean.properties";

    /**
     * <p>
     * Represents the default namespace to obtain the configuration object for the object factory.
     * </p>
     * <p>
     * It would be used in the initialize method if the corresponding ConfluenceManagerNamespace variable is not
     * injected.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE =
        "com.topcoder.confluence.webservice.bean.ConfluenceManagementServiceBean";

    /**
     * <p>
     * Represents the full qualified class name of <code>ConfluenceManagementServiceBean</code>.
     * </p>
     */
    private static final String CLASS_NAME =
        "com.topcoder.confluence.webservice.bean.ConfluenceManagementServiceBean";

    /**
     * <p>
     * The configuration key is used to retrieve the log_name property.
     * </p>
     */
    private static final String LOG_NAME_CONFIG_KEY = "log_name";

    /**
     * <p>
     * The configuration key is used to retrieve the confluence_manager_key property.
     * </p>
     */
    private static final String CONFLUENCE_MANAGER_KEY_CONFIG_KEY = "confluence_manager_key";

    /**
     * <p>
     * The configuration key is used to retrieve the object factory specification configuration.
     * </p>
     */
    private static final String SPEC_FACTORY_CONFIG_KEY = "spec_factory_config";

    /**
     * <p>
     * It will be used to log all activity.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in all business methods.
     * </p>
     * <p>
     * It will may be null after initialization to indicate no logging to take place.
     * </p>
     * <p>
     * Once set, it will not change.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * The instance of the ConfluenceManager used to perform all actual Confluence business operations.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in all business methods.
     * </p>
     * <p>
     * It will not be null after initialization.
     * </p>
     * <p>
     * Once set, it will not change.
     * </p>
     */
    private ConfluenceManager confluenceManager;

    /**
     * <p>
     * Represents the configuration path to create the ConfigurationFileManager that will be used for the
     * configuration of this bean and the Confluence manager it will use.
     * </p>
     * <p>
     * It is injected into the bean by the container. It is used in the initialize method.
     * </p>
     * <p>
     * Can be any value.
     * </p>
     * <p>
     * Once set, it will not change.
     * </p>
     */
    @Resource(name = "confluenceManagerFile")
    private String confluenceManagerFile;

    /**
     * <p>
     * Represents the namespace to create the ConfigurationFileManager that will be used for the configuration of
     * this bean and the Confluence manager it will use.
     * </p>
     * <p>
     * It is injected into the bean by the container. It is used in the initialize method.
     * </p>
     * <p>
     * Can be any value.
     * </p>
     * <p>
     * Once set, it will not change.
     * </p>
     */
    @Resource(name = "confluenceManagerNamespace")
    private String confluenceManagerNamespace;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ConfluenceManagementServiceBean() {
        // does nothing
    }

    /**
     * <p>
     * Initializes this bean for use.
     * </p>
     *
     * @throws ConfluenceManagementServiceConfigurationException
     *             if can't create the file manager or extract the configuration from it, or if the configuration
     *             does not have required properties, or if there is an error while accessing these properties
     */
    @PostConstruct
    protected void initialize() {

        try {
            // get the fileName to retrieve the configuration file
            String fileName = confluenceManagerFile == null ? DEFAULT_CONFIG_PATH : confluenceManagerFile;

            // get the namespace to retrieve the configuration and child configuration object
            String namespace = confluenceManagerNamespace == null ? DEFAULT_NAMESPACE : confluenceManagerNamespace;

            if (fileName.trim().length() == 0 || namespace.trim().length() == 0) {
                throw new ConfluenceManagementServiceConfigurationException(
                    "confluenceManagerFile and confluenceManagerNamespace should not be empty.");
            }

            // Create new instance of ConfigurationFileManager
            ConfigurationFileManager manager = new ConfigurationFileManager(fileName);

            // Obtain the root configuration object using given namespace or DEFAULT_NAMESPACE
            ConfigurationObject root = manager.getConfiguration(namespace);

            // Obtain the child configuration object using to configure this ServiceBean
            ConfigurationObject beanConfig = root.getChild(namespace);

            if (beanConfig == null) {
                throw new ConfluenceManagementServiceConfigurationException(
                    "Should contain configuration object named " + namespace + " in root"
                        + " because the child configuration object should contain"
                        + " the required property named confluence_manager_key.");
            }

            ObjectFactory objectFactory =
                beanConfig.containsChild(SPEC_FACTORY_CONFIG_KEY) ? new ObjectFactory(
                    new ConfigurationObjectSpecificationFactory(beanConfig.getChild(SPEC_FACTORY_CONFIG_KEY)))
                    : new ObjectFactory();

            String logName = (String) beanConfig.getPropertyValue(LOG_NAME_CONFIG_KEY);

            if (logName != null && logName.trim().length() != 0) {
                log = LogManager.getLogWithExceptions(logName);
            }

            String confluenceManagerKey = (String) beanConfig.getPropertyValue(CONFLUENCE_MANAGER_KEY_CONFIG_KEY);

            if (confluenceManagerKey == null || confluenceManagerKey.trim().length() == 0) {
                throw new ConfluenceManagementServiceConfigurationException("The required property named"
                    + CONFLUENCE_MANAGER_KEY_CONFIG_KEY + " should be present and not empty.");
            }

            confluenceManager = (ConfluenceManager) objectFactory.createObject(confluenceManagerKey);
        } catch (InvalidClassSpecificationException e) {
            throw new ConfluenceManagementServiceConfigurationException(
                "The spceification is not valid and can't be used to create an object", e);
        } catch (ConfigurationPersistenceException e) {
            throw new ConfluenceManagementServiceConfigurationException(
                "Any errors occured when reading configurations from configuration files.", e);
        } catch (ConfigurationAccessException e) {
            throw new ConfluenceManagementServiceConfigurationException(
                "Any errors occured when getting properties or child configuration object from  configuration object.",
                e);
        } catch (SpecificationFactoryException e) {
            throw new ConfluenceManagementServiceConfigurationException(
                "Any errors occured when createing object using object factory.", e);
        } catch (LogException e) {
            throw new ConfluenceManagementServiceConfigurationException(
                "Any errors occured when getting log instance from LogManager.", e);
        } catch (IOException e) {
            throw new ConfluenceManagementServiceConfigurationException(
                "Any IO errors occured when configuring this service bean.", e);
        } catch (ClassCastException e) {
            throw new ConfluenceManagementServiceConfigurationException(
                "The object created from object factory should be ConfluenceManager type.", e);
        }
    }

    /**
     * <p>
     * Log the exception thrown from all public methods and return it to public methods to propagating.
     * </p>
     *
     * @param e
     *            the underlying exception thrown from <code>ConfluenceManager</code>.
     * @param methodName
     *            the method which throw exception
     * @return the corresponding mapped exception to e
     */
    private ConfluenceManagementServiceException logConfluenceException(ConfluenceManagerException e,
        String methodName) {

        if (e instanceof ConfluenceConnectionException) {
            String errorMsg = "Confluence connection error occured when calling method named-" + methodName + ".";
            ConfluenceManagementServiceConnectionException cmsce =
                new ConfluenceManagementServiceConnectionException(errorMsg);
            Helper.logException(errorMsg, methodName, cmsce, log);
            return cmsce;
        }

        if (e instanceof ConfluenceAuthenticationFailedException) {
            String errorMsg = "Confluence authentication failed when calling method named-" + methodName + ".";
            ConfluenceManagementServiceAuthenticationFailedException cmsafe =
                new ConfluenceManagementServiceAuthenticationFailedException(errorMsg);
            Helper.logException(errorMsg, methodName, cmsafe, log);
            return cmsafe;
        }

        if (e instanceof ConfluenceNotAuthorizedException) {
            String errorMsg = "Confluence authorization failed when calling method named-" + methodName + ".";
            ConfluenceManagementServiceNotAuthorizedException cmsnae =
                new ConfluenceManagementServiceNotAuthorizedException(errorMsg);
            Helper.logException(errorMsg, methodName, cmsnae, log);
            return cmsnae;
        }

        String errorMsg = "Any confluence manager error occured when calling method named-" + methodName + ".";
        ConfluenceManagementServiceException cmse = new ConfluenceManagementServiceException(errorMsg);
        Helper.logException(errorMsg, methodName, cmse, log);
        return cmse;
    }

    /**
     * <p>
     * performs the login of the given user with the given password to Confluence.
     * </p>
     *
     * @param userName
     *            the user name that should be logged in Confluence. Should not be null or empty String
     * @param password
     *            the password for the given user name that should be logged in Confluence. Should not be null or
     *            empty String
     * @return the created token for the given userName and password. This token is then passed to the subsequent
     *         calls into Confluence to verify if the user has access to the specific areas being manipulated.
     *         Should not be null but can be empty String represents anonymous user
     * @throws IllegalArgumentException
     *             if the userName or password are null or empty Strings
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceAuthenticationFailedException
     *             if the user or password are invalid
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String login(String userName, String password) throws ConfluenceManagementServiceException {

        String methodName = CLASS_NAME + ".login";

        // log method entry and input
        Helper.logLoginMethodEntryAndInput(CLASS_NAME, userName, password, log);

        Helper.checkStringNullOrEmptyAndLog(userName, "userName", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(password, "password", methodName, log);

        try {
            String token = confluenceManager.login(userName, password);

            // log exit and output
            Helper.logLoginOutputAndMethodExit(methodName, token, log);
            return token;
        } catch (ConfluenceManagerException e) {
            throw logConfluenceException(e, methodName);
        }
    }

    /**
     * <p>
     * performs the log out from the Confluence using the given token.
     * </p>
     *
     * @param token
     *            the token to be used to log out from the Confluence. This token is created by
     *            login(userName,password):String method and is used to verify if the user has access to the
     *            specific areas being manipulated. Should not be null but can be empty String represents anonymous
     *            user
     * @throws IllegalArgumentException
     *             if the token is null
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void logout(String token) throws ConfluenceManagementServiceException {
        String methodName = CLASS_NAME + ".logout";

        // log method entry and input
        Helper.logLogoutMethodEntryAndInput(CLASS_NAME, token, log);

        Helper.checkNullAndLog(token, "token", methodName, log);

        try {
            confluenceManager.logout(token);

            // log exit
            if (log != null) {
                log.log(Level.DEBUG, "[Exiting method {" + methodName + "}]");
            }
        } catch (ConfluenceManagerException e) {
            throw logConfluenceException(e, methodName);
        }
    }

    /**
     * <p>
     * Creates a confluence page with the given name, version and asset type. Uses the token to indicate it is
     * logged in.
     * </p>
     *
     * @param pageName
     *            the name of the new page
     * @param token
     *            the token to be used to create a Confluence page
     * @param assetType
     *            the confluence asset type use to create a new page
     * @param version
     *            the version of the new page
     * @param catalog
     *            the confluence catalog to create a new page
     * @param componentType
     *            the confluence componentType to create a new page
     * @return the confluence page creation result. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, ComponentType componentType)
        throws ConfluenceManagementServiceException {
        String methodName = CLASS_NAME + ".createPage";

        // log method entry and input
        Helper.logCreatePageOrRetrievePageWithComponentTypeMethodEntryAndInput(methodName, token, pageName,
            version, assetType, catalog, componentType, log);

        Helper.checkNullAndLog(token, "token", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(pageName, "pageName", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(version, "version", methodName, log);
        Helper.checkNullAndLog(assetType, "assetType", methodName, log);
        Helper.checkNullAndLog(catalog, "catalog", methodName, log);
        Helper.checkNullAndLog(componentType, "componentType", methodName, log);

        try {
            ConfluencePageCreationResult result =
                confluenceManager.createPage(token, pageName, version, assetType, catalog, componentType);

            // log output and exit
            Helper.logConfluencePageCreationResultOutputAndMethodExit(methodName, result, log);
            return result;
        } catch (ConfluenceManagerException e) {
            throw logConfluenceException(e, methodName);
        }
    }

    /**
     * <p>
     * Creates a confluence page with the given name, version, asset type, catalog, and application code Uses the
     * token to indicate it is logged in.
     * </p>
     *
     * @param token
     *            the token to be used to create a Confluence page
     * @param pageName
     *            the name of the new page
     * @param version
     *            the version of the new page
     * @param assetType
     *            the confluence asset type use to create a new page
     * @param catalog
     *            the confluence catalog to which the page is put
     * @param applicationCode
     *            the application code
     * @return the confluence page creation result. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence.
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated).
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, String applicationCode)
        throws ConfluenceManagementServiceException {
        String methodName = CLASS_NAME + ".createPage";

        // log method entry and input
        Helper.logCreatePageOrRetrievePageWithApplicationCodeMethodEntryAndInput(methodName, token, pageName,
            version, assetType, catalog, applicationCode, log);

        Helper.checkNullAndLog(token, "token", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(pageName, "pageName", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(version, "version", methodName, log);
        Helper.checkNullAndLog(assetType, "assetType", methodName, log);
        Helper.checkNullAndLog(catalog, "catalog", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(applicationCode, "applicationCode", methodName, log);

        try {
            ConfluencePageCreationResult result =
                confluenceManager.createPage(token, pageName, version, assetType, catalog, applicationCode);

            // log output and exit
            Helper.logConfluencePageCreationResultOutputAndMethodExit(methodName, result, log);
            return result;
        } catch (ConfluenceManagerException e) {
            throw logConfluenceException(e, methodName);
        }
    }

    /**
     * <p>
     * Creates a confluence page with the given info Uses the token to indicate it is logged in.
     * </p>
     *
     * @param token
     *            the token to be used to create a Confluence page
     * @param page
     *            the page entity with the info to create
     * @return the confluence page creation result. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ConfluencePageCreationResult createPage(String token, Page page)
        throws ConfluenceManagementServiceException {

        String methodName = CLASS_NAME + ".createPage";

        // log method entry and input
        Helper.logCreatePageWithPageMethodEntryAndInput(CLASS_NAME, token, page, log);

        Helper.checkNullAndLog(token, "token", methodName, log);
        Helper.checkNullAndLog(page, "page", methodName, log);

        try {
            ConfluencePageCreationResult result = confluenceManager.createPage(token, page);

            // log output and exit
            Helper.logConfluencePageCreationResultOutputAndMethodExit(methodName, result, log);
            return result;
        } catch (ConfluenceManagerException e) {
            throw logConfluenceException(e, methodName);
        }
    }

    /**
     * <p>
     * Retrieves a Confluence page with the given name, version and asset type. Uses the token to indicate it is
     * logged in.
     * </p>
     *
     * @param token
     *            the token to be used to retrieve a Confluence page
     * @param pageName
     *            the name of the existing page
     * @param version
     *            the version of the existing page
     * @param assetType
     *            the confluence asset type of the existing page
     * @param catalog
     *            the confluence catalog of the existing page
     * @param componentType
     *            the confluence componentType of the existing page
     * @return the confluence page. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, ComponentType componentType) throws ConfluenceManagementServiceException {
        String methodName = CLASS_NAME + ".retrievePage";

        // log method entry and input
        Helper.logCreatePageOrRetrievePageWithComponentTypeMethodEntryAndInput(methodName, token, pageName,
            version, assetType, catalog, componentType, log);

        Helper.checkNullAndLog(token, "token", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(pageName, "pageName", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(version, "version", methodName, log);
        Helper.checkNullAndLog(assetType, "assetType", methodName, log);
        Helper.checkNullAndLog(catalog, "catalog", methodName, log);
        Helper.checkNullAndLog(componentType, "componentType", methodName, log);

        try {
            Page page =
                confluenceManager.retrievePage(token, pageName, version, assetType, catalog, componentType);

            // log exit and output
            Helper.logPageOutputAndMethodExit(methodName, page, log);
            return page;
        } catch (ConfluenceManagerException e) {
            throw logConfluenceException(e, methodName);
        }
    }

    /**
     * <p>
     * Retrieves a Confluence page with the given name, version, asset type, catalog, and application code Uses the
     * token to indicate it is logged in.
     * </p>
     *
     * @param token
     *            the token to be used to retrieve a Confluence page
     * @param pageName
     *            the name of the existing page
     * @param version
     *            the version of the existing page
     * @param assetType
     *            the confluence asset type of the existing page
     * @param catalog
     *            the confluence catalog of the existing page
     * @param applicationCode
     *            the application code of the existing page
     * @return the confluence page. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceManagementServiceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceManagementServiceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagementServiceException
     *             if any error occurs while performing this operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, String applicationCode) throws ConfluenceManagementServiceException {

        String methodName = CLASS_NAME + ".retrievePage";

        // log method entry and input
        Helper.logCreatePageOrRetrievePageWithApplicationCodeMethodEntryAndInput(methodName, token, pageName,
            version, assetType, catalog, applicationCode, log);

        Helper.checkNullAndLog(token, "token", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(pageName, "pageName", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(version, "version", methodName, log);
        Helper.checkNullAndLog(assetType, "assetType", methodName, log);
        Helper.checkNullAndLog(catalog, "catalog", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(applicationCode, "applicationCode", methodName, log);

        try {
            Page page =
                confluenceManager.retrievePage(token, pageName, version, assetType, catalog, applicationCode);

            // log exit and output
            Helper.logPageOutputAndMethodExit(methodName, page, log);
            return page;
        } catch (ConfluenceManagerException e) {
            throw logConfluenceException(e, methodName);
        }
    }
}