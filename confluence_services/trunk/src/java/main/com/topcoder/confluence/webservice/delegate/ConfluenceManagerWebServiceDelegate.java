/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.delegate;

import java.io.IOException;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.confluence.ConfluenceAuthenticationFailedException;
import com.topcoder.confluence.ConfluenceConfigurationException;
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
import com.topcoder.confluence.webservice.ConfluenceManagementServiceConnectionException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceException;
import com.topcoder.confluence.webservice.ConfluenceManagementServiceNotAuthorizedException;
import com.topcoder.confluence.webservice.Helper;
import com.topcoder.confluence.webservice.client.ConfluenceManagementServiceClient;
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
 * A simple business delegate implementation of the ConfluenceManager that uses the
 * ConfluenceManagementServiceClient as the lookup service for the ConfluenceManagementService proxies to be used
 * to communicate with the Web Services. All methods are implemented and all methods just delegate to the proxy.
 * </p>
 * <p>
 * It uses the Configuration API and Persistence components to obtain configuration information. It uses the Object
 * Factory and its Configuration API-backed specification factory to instantiate the service client, and the Log
 * Manager to perform logging of activity and errors.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * // create a ConfluenceManagerWebServiceDelegate using the constructor with filename and namespace
 * ConfluenceManagerWebServiceDelegate confluenceManager =
 *     new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
 *          &quot;demo&quot;);
 *
 * // log in to Confluence and retrieve the needed token to perform the operations
 * String token = confluenceManager.login(&quot;user&quot;, &quot;password&quot;);
 * // if the user or password are not valid in Confluence, an appropriate exception will be thrown
 *
 * // create a new page in confluence with CompontType - CUSTOM
 * ConfluencePageCreationResult confluencePageCreationResult =
 *     confluenceManager.createPage(token, &quot;Erebus Token Web Services&quot;, &quot;1.0&quot;,
 *          ConfluenceAssetType.COMPONENT_DESIGN,ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
 *
 * // create a new page in confluence with application code
 * confluencePageCreationResult =
 *     confluenceManager.createPage(token, &quot;Erebus Payment Web Services&quot;, &quot;1.0&quot;,
 *         ConfluenceAssetType.COMPONENT_DESIGN, ConfluenceCatalog.JAVA, &quot;12345&quot;);
 *
 * // create another page in confluence with page
 * confluencePageCreationResult = confluenceManager.createPage(token, new Page());
 *
 * // retrieve a page with ComponentType
 * Page page =
 *     confluenceManager.retrievePage(token, &quot;Erebus Token Web Services&quot;, &quot;1.0&quot;,
 *         ConfluenceAssetType.COMPONENT_DESIGN, ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);
 *
 * // retrieve a page with applicationCode
 * page =
 *     confluenceManager.retrievePage(token, &quot;Erebus Token Web Services&quot;, &quot;1.0&quot;,
 *         ConfluenceAssetType.COMPONENT_DESIGN, ConfluenceCatalog.JAVA, &quot;12345&quot;);
 *
 * // log out from the Confluence:
 * confluenceManager.logout(token);
 * // if the token is not valid in Confluence, an appropriate exception will
 * // be thrown
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is immutable and effectively thread-safe, since although it deals with
 * non-thread-safe entities, these are not expected to be used by multiple threads.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class ConfluenceManagerWebServiceDelegate implements ConfluenceManager {

    /**
     * <p>
     * Represents the default configuration path to create the ConfigurationFileManager.
     * </p>
     * <p>
     * It will be used in the parameterless constructor.
     * </p>
     */
    public static final String DEFAULT_CONFIG_PATH =
        "com/topcoder/confluence/webservice/delegate/ConfluenceManagerWebServiceDelegate.properties";

    /**
     * <p>
     * Represents the default namespace to obtain the configuration object for the object factory.
     * </p>
     * <p>
     * It will be used in the parameterless constructor.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE =
        "com.topcoder.confluence.webservice.delegate.ConfluenceManagerWebServiceDelegate";

    /**
     * <p>
     * Represents the full qualified class name of <code>ConfluenceManagerWebServiceDelegate</code>.
     * </p>
     */
    private static final String CLASS_NAME =
        "com.topcoder.confluence.webservice.delegate.ConfluenceManagerWebServiceDelegate";

    /**
     * <p>
     * The configuration key is used to retrieve the log_name property.
     * </p>
     */
    private static final String LOG_NAME_CONFIG_KEY = "log_name";

    /**
     * <p>
     * The configuration key is used to retrieve the service_client_key property.
     * </p>
     */
    private static final String SERVICE_CLIENT_KEY_CONFIG_KEY = "service_client_key";

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
     * It is set in the constructor. It is used in all methods.
     * </p>
     * <p>
     * It will may be null to indicate no logging to take place .
     * </p>
     * <p>
     * Once set, it will not change.
     * </p>
     */
    private final Log log;

    /**
     * <p>
     * The client used to obtain the service instance to communicate with the Web Services.
     * </p>
     * <p>
     * It is set in the constructor. It is used in all methods.
     * </p>
     * <p>
     * It will not be null.
     * </p>
     * <p>
     * Once set, it will not change.
     * </p>
     */
    private final ConfluenceManagementServiceClient serviceClient;

    /**
     * <p>
     * Configures this instance from default configuration.
     * </p>
     *
     * @throws ConfluenceConfigurationException
     *             if can't create the file manager or extract the configuration from it, or if the configuration
     *             does not have required properties, or if there is an error while accessing these properties
     */
    public ConfluenceManagerWebServiceDelegate() throws ConfluenceConfigurationException {
        this(DEFAULT_CONFIG_PATH, DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Configures this instance from configuration.
     * </p>
     *
     * @param filename
     *            the path to the configuration file
     * @param namespace
     *            the namespace of the configuration to use
     * @throws IllegalArgumentException
     *             if either parameter is null/empty
     * @throws ConfluenceConfigurationException
     *             if can't create the file manager or extract the configuration from it, or if the configuration
     *             does not have required properties, or if there is an error while accessing these properties
     */
    public ConfluenceManagerWebServiceDelegate(String filename, String namespace)
        throws ConfluenceConfigurationException {
        Helper.checkStringNullOrEmpty(filename, "filename");
        Helper.checkStringNullOrEmpty(namespace, "namespace");

        try {

            // Create new instance of ConfigurationFileManager
            ConfigurationFileManager manager = new ConfigurationFileManager(filename);

            // Obtain the configuration object with name space
            ConfigurationObject root = manager.getConfiguration(namespace);

            // Get child configuration object to configure this delegate
            ConfigurationObject delegateConfObj = root.getChild(namespace);

            if (delegateConfObj == null) {
                throw new ConfluenceConfigurationException("Should contain child configuration object named "
                    + namespace + " in root because the 'default' configuration object should contain"
                    + " the required property named service_client_key.");
            }

            log = initializeLog(delegateConfObj);

            serviceClient = initializeServiceClient(delegateConfObj);
        } catch (ConfigurationPersistenceException e) {
            throw new ConfluenceConfigurationException(
                "Any errors occured when reading configurations from configuration files.", e);
        } catch (ConfigurationAccessException e) {
            throw new ConfluenceConfigurationException(
                "Any errors occured when getting properties or child configuration object from  configuration object.",
                e);
        } catch (IOException e) {
            throw new ConfluenceConfigurationException(
                "Any IO errors occured when configuring this service bean.", e);
        }
    }

    /**
     * <p>
     * Configures this instance from configuration.
     * </p>
     *
     * @param configurationObject
     * @throws IllegalArgumentException
     *             if configurationObject is null
     * @throws ConfluenceConfigurationException
     *             if can't create the file manager or extract the configuration from it, or if the configuration
     *             does not have required properties, or if there is an error while accessing these properties
     */
    public ConfluenceManagerWebServiceDelegate(ConfigurationObject configurationObject)
        throws ConfluenceConfigurationException {
        Helper.checkNull(configurationObject, "configurationObject");
        log = initializeLog(configurationObject);
        serviceClient = initializeServiceClient(configurationObject);
    }

    /**
     * <p>
     * Simply sets the fields with the passed namesake parameters.
     * </p>
     *
     * @param serviceClient
     *            the ConfluenceManagementServiceClient instance to use
     * @param log
     *            the Log to use to log activity
     * @throws IllegalArgumentException
     *             if serviceClient is null
     */
    public ConfluenceManagerWebServiceDelegate(ConfluenceManagementServiceClient serviceClient, Log log) {
        Helper.checkNull(serviceClient, "serviceClient");
        this.serviceClient = serviceClient;
        this.log = log;
    }

    /**
     * <p>
     * Get property value by key from configurationObject.
     * </p>
     *
     * @param key
     *            the key used to retrieve the property
     * @param configurationObject
     *            the configurationObject used to retrieve the property
     * @return the property value
     * @throws ConfluenceConfigurationException
     *             if any errors occurred when getting property value
     */
    private String getPropertyValue(String key, ConfigurationObject configurationObject)
        throws ConfluenceConfigurationException {
        try {
            return (String) configurationObject.getPropertyValue(key);
        } catch (ConfigurationAccessException e) {
            throw new ConfluenceConfigurationException(
                "Any error occured when getting properties or child configuration object from  configuration object.",
                e);
        } catch (ClassCastException e) {
            throw new ConfluenceConfigurationException("property value should be string type.", e);
        }
    }

    /**
     * <p>
     * Get the initialized log instance from configurationObject.
     * </p>
     *
     * @param configurationObject
     *            the configurationObject used to initialize log
     * @return the log instance or null if it is not provided
     * @throws ConfluenceConfigurationException
     *             if any errors occurred when creating the log instance
     */
    private Log initializeLog(ConfigurationObject configurationObject) throws ConfluenceConfigurationException {
        String logName = getPropertyValue(LOG_NAME_CONFIG_KEY, configurationObject);
        if (logName != null && logName.trim().length() != 0) {
            try {
                return LogManager.getLogWithExceptions(logName);
            } catch (LogException e) {
                throw new ConfluenceConfigurationException(
                    "Any errors occured when getting log instance from LogManager.", e);
            }
        }
        return null;
    }

    /**
     * <p>
     * Get the initialized <code>ConfluenceManagementServiceClient</code> instance.
     * </p>
     *
     * @param configurationObject
     *            the configurationObject used to initialize the <code>ConfluenceManagementServiceClient</code>
     * @return the initialized <code>ConfluenceManagementServiceClient</code> instance
     * @throws ConfluenceConfigurationException
     *             if any errors occurred when initialize the serviceClient
     */
    private ConfluenceManagementServiceClient initializeServiceClient(ConfigurationObject configurationObject)
        throws ConfluenceConfigurationException {
        try {
            ObjectFactory objectFactory =
                configurationObject.containsChild(SPEC_FACTORY_CONFIG_KEY) ? new ObjectFactory(
                    new ConfigurationObjectSpecificationFactory(configurationObject
                        .getChild(SPEC_FACTORY_CONFIG_KEY))) : new ObjectFactory();

            String serviceClientKeyValue = getPropertyValue(SERVICE_CLIENT_KEY_CONFIG_KEY, configurationObject);

            if (serviceClientKeyValue == null || serviceClientKeyValue.trim().length() == 0) {
                throw new ConfluenceConfigurationException("The required property named "
                    + SERVICE_CLIENT_KEY_CONFIG_KEY + " should be present and not empty.");
            }
            return (ConfluenceManagementServiceClient) objectFactory.createObject(serviceClientKeyValue);
        } catch (ClassCastException e) {
            throw new ConfluenceConfigurationException(
                "The created object from object factory should be ConfluenceManagementServiceClient type.", e);
        } catch (ConfigurationAccessException e) {
            throw new ConfluenceConfigurationException(
                "Any errors occured when getting properties or child configuration object from  configuration object.",
                e);
        } catch (SpecificationFactoryException e) {
            throw new ConfluenceConfigurationException(
                "Any errors occured when createing object using object factory.", e);
        } catch (InvalidClassSpecificationException e) {
            throw new ConfluenceConfigurationException(
                "The spceification is not valid and can't be used to create an object", e);
        }
    }

    /**
     * <p>
     * Log the exception thrown from all public methods and return it to public methods to propagating.
     * </p>
     *
     * @param e
     *            the underlying exception thrown from <code>ConfluenceManagementService</code>.
     * @param methodName
     *            the method which throw exception
     * @return the corresponding mapped exception to e and wraps e
     */
    private ConfluenceManagerException logConfluenceException(ConfluenceManagementServiceException e,
        String methodName) {

        if (e instanceof ConfluenceManagementServiceConnectionException) {
            String errorMsg = "Confluence connection error occured when calling method named-" + methodName + ".";
            ConfluenceConnectionException cce = new ConfluenceConnectionException(errorMsg, e);
            Helper.logException(errorMsg, methodName, cce, log);
            return cce;
        }

        if (e instanceof ConfluenceManagementServiceAuthenticationFailedException) {
            String errorMsg = "Confluence authentication failed when calling method named-" + methodName + ".";
            ConfluenceAuthenticationFailedException cafe =
                new ConfluenceAuthenticationFailedException(errorMsg, e);
            Helper.logException(errorMsg, methodName, cafe, log);
            return cafe;
        }

        if (e instanceof ConfluenceManagementServiceNotAuthorizedException) {
            String errorMsg = "Confluence authorization failed when calling method named-" + methodName + ".";
            ConfluenceNotAuthorizedException cnae = new ConfluenceNotAuthorizedException(errorMsg, e);
            Helper.logException(errorMsg, methodName, cnae, log);
            return cnae;
        }

        String errorMsg = "Any confluence manager error occured when calling method named-" + methodName + ".";
        ConfluenceManagerException cme = new ConfluenceManagerException(errorMsg, e);
        Helper.logException(errorMsg, methodName, cme, log);
        return cme;
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
     *         calls into Confluence to verify if the user has access to the specific areas being manipulated
     *         Should not be null but can be empty String represents anonymous user
     * @throws IllegalArgumentException
     *             if the userName or password are null or empty Strings
     * @throws ConfluenceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceAuthenticationFailedException
     *             if the user or password are invalid
     * @throws ConfluenceManagerException
     *             if any error occurs while performing this operation
     */
    public String login(String userName, String password) throws ConfluenceManagerException {

        String methodName = CLASS_NAME + ".login";

        // log method entry and input
        Helper.logLoginMethodEntryAndInput(CLASS_NAME, userName, password, log);

        Helper.checkStringNullOrEmptyAndLog(userName, "userName", methodName, log);
        Helper.checkStringNullOrEmptyAndLog(password, "password", methodName, log);

        try {
            String token = serviceClient.getConfluenceManagementServicePort().login(userName, password);

            // log exit and output
            Helper.logLoginOutputAndMethodExit(methodName, token, log);
            return token;
        } catch (ConfluenceManagementServiceException e) {
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
     * @throws ConfluenceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence.
     * @throws ConfluenceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated).
     * @throws ConfluenceManagerException
     *             if any error occurs while performing this operation.
     */
    public void logout(String token) throws ConfluenceManagerException {
        String methodName = CLASS_NAME + ".logout";

        // log method entry and input
        Helper.logLogoutMethodEntryAndInput(CLASS_NAME, token, log);

        Helper.checkNullAndLog(token, "token", methodName, log);

        try {
            serviceClient.getConfluenceManagementServicePort().logout(token);

            // log exit
            if (log != null) {
                log.log(Level.DEBUG, "[Exiting method {" + methodName + "}]");
            }
        } catch (ConfluenceManagementServiceException e) {
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
     *            the catalog to create a new page
     * @param componentType
     *            the componentType to create a new page
     * @return the confluence page creation result. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagerException
     *             if any error occurs while performing this operation
     */
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, ComponentType componentType)
        throws ConfluenceManagerException {
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
                serviceClient.getConfluenceManagementServicePort().createPage(token, pageName, version, assetType,
                    catalog, componentType);

            // log output and exit
            Helper.logConfluencePageCreationResultOutputAndMethodExit(methodName, result, log);
            return result;
        } catch (ConfluenceManagementServiceException e) {
            throw logConfluenceException(e, methodName);
        }
    }

    /**
     * <p>
     * Creates a confluence page with the given name, version, asset type, catalog, and application code Uses the
     * token to indicate it is logged in.
     * </p>
     *
     * @param pageName
     *            the name of the new page
     * @param applicationCode
     *            the application code
     * @param token
     *            the token to be used to create a Confluence page
     * @param catalog
     *            the confluence catalog to which the page is put
     * @param assetType
     *            the confluence asset type use to create a new page
     * @param version
     *            the version of the new page
     * @return the confluence page creation result. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagerException
     *             if any error occurs while performing this operation
     */
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, String applicationCode)
        throws ConfluenceManagerException {
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
                serviceClient.getConfluenceManagementServicePort().createPage(token, pageName, version, assetType,
                    catalog, applicationCode);

            // log output and exit
            Helper.logConfluencePageCreationResultOutputAndMethodExit(methodName, result, log);
            return result;
        } catch (ConfluenceManagementServiceException e) {
            throw logConfluenceException(e, methodName);
        }
    }

    /**
     * <p>
     * Creates a confluence page with the given info Uses the token to indicate it is logged in.
     * </p>
     *
     * @param page
     *            the page entity with the info to create
     * @param token
     *            the token to be used to create a Confluence page
     * @return the confluence page creation result. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagerException
     *             if any error occurs while performing this operation
     */
    public ConfluencePageCreationResult createPage(String token, Page page) throws ConfluenceManagerException {
        String methodName = CLASS_NAME + ".createPage";

        // log method entry and input
        Helper.logCreatePageWithPageMethodEntryAndInput(CLASS_NAME, token, page, log);

        Helper.checkNullAndLog(token, "token", methodName, log);
        Helper.checkNullAndLog(page, "page", methodName, log);

        try {
            ConfluencePageCreationResult result =
                serviceClient.getConfluenceManagementServicePort().createPage(token, page);

            // log output and exit
            Helper.logConfluencePageCreationResultOutputAndMethodExit(methodName, result, log);
            return result;
        } catch (ConfluenceManagementServiceException e) {
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
     * @throws ConfluenceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagerException
     *             if any error occurs while performing this operation
     */
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, ComponentType componentType) throws ConfluenceManagerException {
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
                serviceClient.getConfluenceManagementServicePort().retrievePage(token, pageName, version,
                    assetType, catalog, componentType);

            // log exit and output
            Helper.logPageOutputAndMethodExit(methodName, page, log);
            return page;
        } catch (ConfluenceManagementServiceException e) {
            throw logConfluenceException(e, methodName);
        }
    }

    /**
     * <p>
     * Retrieves a Confluence page with the given name, version, asset type, catalog, and application code Uses the
     * token to indicate it is logged in.
     * </p>
     *
     * @param pageName
     *            the name of the existing page
     * @param applicationCode
     *            the application code of the existing page
     * @param token
     *            the token to be used to retrieve a Confluence page
     * @param catalog
     *            the confluence catalog of the existing page
     * @param assetType
     *            the confluence asset type of the existing page
     * @param version
     *            the version of the existing page
     * @return the confluence page. Should not be null
     * @throws IllegalArgumentException
     *             if any argument is null or any String argument is empty except token
     * @throws ConfluenceConnectionException
     *             if an there is an issue while attempting to reestablish the connection to Confluence
     * @throws ConfluenceNotAuthorizedException
     *             if a user attempts an unauthorized call to Confluence (the token used to authenticate is invalid
     *             or the user does not have access to the specific areas being manipulated)
     * @throws ConfluenceManagerException
     *             if any error occurs while performing this operation
     */
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, String applicationCode) throws ConfluenceManagerException {
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
                serviceClient.getConfluenceManagementServicePort().retrievePage(token, pageName, version,
                    assetType, catalog, applicationCode);

            // log exit and output
            Helper.logPageOutputAndMethodExit(methodName, page, log);
            return page;
        } catch (ConfluenceManagementServiceException e) {
            throw logConfluenceException(e, methodName);
        }
    }
}