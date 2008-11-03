/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.rpc.ServiceException;

import com.atlassian.www._package.com_atlassian_confluence_rpc_soap_beans.RemotePage;
import com.atlassian.www.software.confluence.$Proxy77.ConfluenceServiceLocator;
import com.atlassian.www.software.confluence.$Proxy77.ConfluenceSoapService;
import com.topcoder.configuration.ConfigurationException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.confluence.ConfluenceAuthenticationFailedException;
import com.topcoder.confluence.ConfluenceConfigurationException;
import com.topcoder.confluence.ConfluenceConnectionException;
import com.topcoder.confluence.ConfluenceManager;
import com.topcoder.confluence.ConfluenceManagerException;
import com.topcoder.confluence.ConfluenceNotAuthorizedException;
import com.topcoder.confluence.Helper;
import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreatedAction;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.ConfluencePageType;
import com.topcoder.confluence.entities.Page;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This class represents the default implementation of ConfluenceManager interface. It provides operations to work
 * with Confluence, such as login, create and retrieve pages using different settings, log out. This class can be
 * configured via file-based runtime supplied configuration. It provides functionality to retry user calls in case
 * of failure.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * // note you need add the corresponding space into confluence first like the
 * // ConfluenceAssetType.COMPONENT_DESIGN etc.
 *
 * // ConfluenceAssetType.COMPONENT_DESIGN
 * // 1) DefaultConfluenceManager instance can be created
 * ConfluenceManager manager = new DefaultConfluenceManager(CONFLUENCE_URL, spaceLocations, templates);
 *
 * // 2) Log in and save authorization token
 * String token = manager.login(USER_NAME, PASSWORD);
 *
 * // 3) Assume user wants to create such page: component design page for the new component 'My New
 * // Component', and the component is Java Custom.
 * // should pass token
 * ConfluencePageCreationResult page1result =
 *     manager.createPage(token, &quot;My New Component&quot;, &quot;1&quot;, ConfluenceAssetType.COMPONENT_DESIGN,
 *         ConfluenceCatalog.JAVA, ComponentType.CUSTOM);
 *
 * // if the component is new, the call page1result.getActionTaken() will return
 * // //BASE_PAGE_AND_VERSION_CREATED.
 *
 * // 4) Different properties of the created page can be get
 * Page page1 = page1result.getPage();
 * String page1base = page1.getBasePageUrl();
 * String page1version = page1.getVersionUrl();
 * String content = page1.getContent();
 *
 * // 5) Assume user wants to wants add new version of such page: application specification page for the
 * // application 'My Favorite Application' from the .net catalog.
 * // should pass token
 * // should pass application code
 * ConfluencePageCreationResult page2result =
 *     manager.createPage(token, &quot;My Favorite Application&quot;, &quot;2&quot;,
 *          ConfluenceAssetType.APPLICATION_SPECIFICATION,ConfluenceCatalog.DOT_NET, APPLICATION_CODE_SPACE);
 *
 * // page2result.getActionTaken() should return BASE_PAGE_EXISTED_VERSION_CREATED.
 *
 * // 6) Assume user wants create the component development doc page with name 'My Component X' which will be
 * // .NET generic.
 * Page page3 = new Page();
 * page3.setAssetName(&quot;My Component X&quot;);
 * page3.setAssetType(ConfluenceAssetType.COMPONENT_DEVELOPMENT);
 * page3.setCatalog(ConfluenceCatalog.DOT_NET);
 * page3.setVersion(&quot;1&quot;);
 * page3.setComponentType(ComponentType.GENERIC);
 *
 * // save page
 * ConfluencePageCreationResult page3result = manager.createPage(token, page3);
 *
 * // 7) Now user wants to retrieve created page that he's just created
 * Page page3copy =
 *     manager.retrievePage(token, &quot;My Component X&quot;, &quot;1&quot;,
 *          ConfluenceAssetType.COMPONENT_DEVELOPMENT,ConfluenceCatalog.DOT_NET, ComponentType.GENERIC);
 *
 * // 8) Now user wants to retrieve the application specification for the 'My Favorite Application'
 * Page page2copy =
 *     manager.retrievePage(token, &quot;My Favorite Application&quot;, &quot;2&quot;,
 *          ConfluenceAssetType.APPLICATION_SPECIFICATION,ConfluenceCatalog.DOT_NET, APPLICATION_CODE_SPACE);
 *
 * // 9) Log out from Confluence
 * manager.logout(token);
 * </pre>
 * </p>
 * <p>
 * <b>Thread Safety:</b> it is thread-safe as the confluenceService is final.
 * </p>
 *
 * @author Margarita, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultConfluenceManager implements ConfluenceManager {

    /**
     * <p>
     * This field stores the default configuration namespace when using file-based configuration.
     * </p>
     */
    public static final String DEFAULT_CONFIGURATION_NAMESPACE =
        "com.topcoder.confluence.impl.DefaultConfluenceManager";

    /**
     * <p>
     * Use this pattern to replace the spaces with the plus('+').
     * </p>
     * <p>
     * NOTE: consecutive spaces only replaced with only one '+'.
     * </p>
     */
    private static final String SPACE_REPLACE_PLUS_PATTERN = "( )+";

    /**
     * <p>
     * Represents the sign to replace the spaces or used to construct the basePageName and fullPageName.
     * </p>
     */
    private static final String PLUS_SIGN = "+";

    /**
     * <p>
     * Replace this string value with applicationCode when asset type is APPLICATION_SPECIFICATION).
     * </p>
     */
    private static final String CODE_NAME = "$CODENAME$";

    /**
     * <p>
     * Represents the one partial token to decide whether the error message represent the page which does not
     * exist.
     * </p>
     */
    private static final String PAGE_NOT_EXIST_TOKEN1 = "does not exist";

    /**
     * <p>
     * Represents the other partial token to decide whether the error message represent the page which does not
     * exist.
     * </p>
     */
    private static final String PAGE_NOT_EXIST_TOKEN2 = "page";

    /**
     * <p>
     * Represents the default value of 'logName'.
     * </p>
     */
    private static final String DEFAULT_LOG_NAME = "com.topcoder.confluence.impl.DefaultConfluenceManager";

    /**
     * <p>
     * Represents the default value of 'maxRetriesNumber' field.
     * </p>
     */
    private static final int DEFAULT_MAX_RETRIES_NUMBER = 1;

    /**
     * <p>
     * Represents the default value of 'componentDesign'.
     * </p>
     */
    private static final String DEFAULL_COMPONENT_DESIGN_VALUE =
        "http://www.topcoder.com/wiki/display/docs/Design";

    /**
     * <p>
     * Represents the default value of 'componentDevelopment'.
     * </p>
     */
    private static final String DEFAULL_COMPONENT_DEVELOPMENT_VALUE =
        "http://www.topcoder.com/wiki/display/docs/Development";

    /**
     * <p>
     * Represents the default value of 'applicationSpecification'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_SPECIFICATION_VALUE =
        "http://www.topcoder.com/wiki/display/projects/$CODENAME$";

    /**
     * <p>
     * Represents the default value of 'applicationArchitecture'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_ARCHITECTURE_VALUE =
        "http://www.topcoder.com/wiki/display/docs/Architecture";

    /**
     * <p>
     * Represents the default value of 'applicationAssembly'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_ASSEMBLY_VALUE =
        "http://www.topcoder.com/wiki/display/docs/Assembly";

    /**
     * <p>
     * Represents the default value of 'applicationTesting'.
     * </p>
     */
    private static final String DEFAULL_APPLICATION_TESTING_VALUE =
        "http://www.topcoder.com/wiki/display/docs/Testing";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'confluenceUrl'.
     * </p>
     */
    private static final String CONFLUENCE_URL_CONFIG_KEY = "confluenceUrl";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'logName'.
     * </p>
     */
    private static final String LOG_NAME_CONFIG_KEY = "logName";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'maxRetriesNumber'.
     * </p>
     */
    private static final String MAX_RETRIES_NUMBER_CONFIG_KEY = "maxRetriesNumber";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'spaceLocationsMapping'.
     * </p>
     */
    private static final String SPACE_LOCATIONS_MAPPING_CONFIG_KEY = "spaceLocationsMapping";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'templatesMapping'.
     * </p>
     */
    private static final String TEMPLATES_MAPPING_CONFIG_KEY = "templatesMapping";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'componentDesign'.
     * </p>
     */
    private static final String COMPONENT_DESIGN_CONFIG_KEY = "componentDesign";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'componentDevelopment'.
     * </p>
     */
    private static final String COMPONENT_DEVELOPMENT_CONFIG_KEY = "componentDevelopment";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'applicationSpecification'.
     * </p>
     */
    private static final String APPLICATION_SPECIFICATION_CONFIG_KEY = "applicationSpecification";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'applicationArchitecture'.
     * </p>
     */
    private static final String APPLICATION_ARCHITECTURE_CONFIG_KEY = "applicationArchitecture";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'applicationAssembly'.
     * </p>
     */
    private static final String APPLICATION_ASSEMBLY_CONFIG_KEY = "applicationAssembly";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'applicationTesting'.
     * </p>
     */
    private static final String APPLICATION_TESTING_CONFIG_KEY = "applicationTesting";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'componentBasePage'.
     * </p>
     */
    private static final String COMPONENT_BASE_PAGE_CONFIG_KEY = "componentBasePage";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'componentVersionPage'.
     * </p>
     */
    private static final String COMPONENT_VERSION_PAGE_CONFIG_KEY = "componentVersionPage";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'applicationBasePage'.
     * </p>
     */
    private static final String APPLICATION_BASE_PAGE_CONFIG_KEY = "applicationBasePage";

    /**
     * <p>
     * Represent the configuration key in ConfigurationObject to retrieve the 'applicationVersionPage'.
     * </p>
     */
    private static final String APPLICATION_VERSION_PAGE_CONFIG_KEY = "applicationVersionPage";

    /**
     * <p>
     * This field stores url to Confluence service. It is used when creating ConfluenceSoapService instance.
     * </p>
     * <p>
     * Cannot be null or empty. should be valid url.
     * </p>
     * <p>
     * It is set in constructor and never changed after that.
     * </p>
     */
    private final String confluenceUrl;

    /**
     * <p>
     * This field stores mapping 'asset type' -> space key. When not configured, it has such default values:
     *
     * <pre>
     * COMPONENT_DESIGN -&gt; http://www.topcoder.com/wiki/display/docs/Design
     * COMPONENT_DEVELOPMENT -&gt; http://www.topcoder.com/wiki/display/docs/Development
     * APPLICATION_SPECIFICATION -&gt; http://www.topcoder.com/wiki/display/projects/$CODENAME$
     * APPLICATION_ARCHITECTURE -&gt; http://www.topcoder.com/wiki/display/docs/Architecture
     * APPLICATION_ASSEMBLY -&gt; http://www.topcoder.com/wiki/display/docs/Assembly
     * APPLICATION_TESTING -&gt; http://www.topcoder.com/wiki/display/docs/Testing
     * </pre>
     * </p>
     * <p>
     * Cannot be null. keys & values cannot be null. values cannot be empty and should be valid url.
     * </p>
     * <p>
     * It is set in constructor and never changed after that. It is used in methods createPage(...) and
     * retrievePage(...) to get space key for the page.
     * </p>
     */
    private final Map<ConfluenceAssetType, String> spaceLocations;

    /**
     * <p>
     * This field stores the mapping 'page type' -> template page path (in the Confluence).
     * </p>
     * <p>
     * Cannot be null. keys & values cannot be null. values cannot be empty and should be valid URLs.
     * </p>
     * <p>
     * This field is set in the constructor and never changed after that.
     * </p>
     */
    private final Map<ConfluencePageType, String> templates;

    /**
     * <p>
     * this field represents the Log instance.Each manager method called should be logged at the DEBUG level,
     * including the parameter information.If errors occur in any call, a message should be logged at the WARNING
     * level before the exception is re-thrown.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * It is set in constructor and never changed after that.
     * </p>
     */
    private final Log log;

    /**
     * <p>
     * This field represents the service object that will process calls to Confluence Soap Service.It is generated
     * from axis by corresponding WSDL file in confluence.
     * </p>
     * <p>
     * Cannot be null.
     * </p>
     * <p>
     * It is set to new value every time connection exception occurs. It is used in every method of this class to
     * make calls to Confluence service.
     * </p>
     */
    private ConfluenceSoapService confluenceService;

    /**
     *<p>
     * This field represents the max retries number. If not configured, defaults to 1.
     * </p>
     * <p>
     * Cannot be less than 1.
     * </p>
     * <p>
     * It is set in the constructor and never changed after that. It is used in getRetriesLeft method to determine
     * number of tries that can be made.
     * </p>
     */
    private final int maxRetriesNumber;

    /**
     * <p>
     * This is default constructor. It loads configuration from configuration file represented by
     * DEFAULT_CONFIGURATION_NAMESPACE.
     * </p>
     *
     * @throws ConfluenceConfigurationException
     *             if inner code threw an configurationException let it propagate
     * @throws ConfluenceManagerException
     *             if any exception occurs it should be wrapped into ConfluenceManagerException
     */
    public DefaultConfluenceManager() throws ConfluenceConfigurationException, ConfluenceManagerException {
        this(loadConfigFromFile(null, DEFAULT_CONFIGURATION_NAMESPACE, false));
    }

    /**
     * <p>
     * This constructor configures the class using file-based configuration from user-provided configuration
     * namespace.
     * </p>
     *
     * @param configurationNamespace
     *            the namespace to use. cannot be null or empty
     * @throws IllegalArgumentException
     *             if configurationNamespace is null or empty
     * @throws ConfluenceConfigurationException
     *             if inner code threw an ConfigurationException let it propagate
     * @throws ConfluenceManagerException
     *             if any exception occurs it should be wrapped into ConfluenceManagerException
     */
    public DefaultConfluenceManager(String configurationNamespace) throws ConfluenceConfigurationException,
        ConfluenceManagerException {
        this(loadConfigFromFile(null, configurationNamespace, false));
    }

    /**
     * <p>
     * This constructor configures the class using the programmatically supplied configuration. All exceptions
     * should be caught, wrapped into ConfluenceManagerException and re-thrown.
     * </p>
     *
     * @param configuration
     *            the configuration object. cannot be null. should be valid (match the schema given in CS 3.2.1)
     * @throws IllegalArgumentException
     *             if configuration is null or has invalid structure
     * @throws ConfluenceConfigurationException
     *             if ConfigurationException is caught it should be wrapped into ConfluenceConfigurationException
     *             and re-thrown
     * @throws ConfluenceManagerException
     *             if any exception occurs it should be wrapped into ConfluenceManagerException
     */
    public DefaultConfluenceManager(ConfigurationObject configuration) throws ConfluenceConfigurationException,
        ConfluenceManagerException {
        Helper.checkNull(configuration, "configuration");

        // get confluence url
        confluenceUrl = getRequiredProperyValue(configuration, CONFLUENCE_URL_CONFIG_KEY);
        if (confluenceUrl.trim().length() == 0) {
            throw new ConfluenceConfigurationException("confluenceUrl should not be empty.");
        }

        // get log
        log = LogManager.getLog(getOptionalPropertyValue(configuration, LOG_NAME_CONFIG_KEY, DEFAULT_LOG_NAME));

        // get max retries number

        int retriesNumber = DEFAULT_MAX_RETRIES_NUMBER;

        // get the retries number configuration
        String retriesConfig = getPropertyValue(configuration, MAX_RETRIES_NUMBER_CONFIG_KEY);
        if (retriesConfig != null) {
            try {
                retriesNumber = Integer.parseInt(retriesConfig);
            } catch (NumberFormatException e) {
                throw new ConfluenceConfigurationException("The property value should be parsed to integer.", e);
            }
        }

        if (retriesNumber < 1) {
            throw new ConfluenceConfigurationException("The max retries number should not be less than 1.");
        }
        maxRetriesNumber = retriesNumber;

        // get space locations
        ConfigurationObject spaces = getChild(configuration, SPACE_LOCATIONS_MAPPING_CONFIG_KEY);
        spaceLocations = new HashMap<ConfluenceAssetType, String>();
        spaceLocations.put(ConfluenceAssetType.COMPONENT_DESIGN, getOptionalPropertyValue(spaces,
            COMPONENT_DESIGN_CONFIG_KEY, DEFAULL_COMPONENT_DESIGN_VALUE));

        spaceLocations.put(ConfluenceAssetType.COMPONENT_DEVELOPMENT, getOptionalPropertyValue(spaces,
            COMPONENT_DEVELOPMENT_CONFIG_KEY, DEFAULL_COMPONENT_DEVELOPMENT_VALUE));
        spaceLocations.put(ConfluenceAssetType.APPLICATION_SPECIFICATION, getOptionalPropertyValue(spaces,
            APPLICATION_SPECIFICATION_CONFIG_KEY, DEFAULL_APPLICATION_SPECIFICATION_VALUE));
        spaceLocations.put(ConfluenceAssetType.APPLICATION_ARCHITECTURE, getOptionalPropertyValue(spaces,
            APPLICATION_ARCHITECTURE_CONFIG_KEY, DEFAULL_APPLICATION_ARCHITECTURE_VALUE));
        spaceLocations.put(ConfluenceAssetType.APPLICATION_ASSEMBLY, getOptionalPropertyValue(spaces,
            APPLICATION_ASSEMBLY_CONFIG_KEY, DEFAULL_APPLICATION_ASSEMBLY_VALUE));
        spaceLocations.put(ConfluenceAssetType.APPLICATION_TESTING, getOptionalPropertyValue(spaces,
            APPLICATION_TESTING_CONFIG_KEY, DEFAULL_APPLICATION_TESTING_VALUE));

        // get templates
        ConfigurationObject template = getChild(configuration, TEMPLATES_MAPPING_CONFIG_KEY);
        templates = new HashMap<ConfluencePageType, String>();
        templates.put(ConfluencePageType.COMPONENT_BASE_PAGE, getRequiredProperyValue(template,
            COMPONENT_BASE_PAGE_CONFIG_KEY));
        templates.put(ConfluencePageType.COMPONENT_VERSION_PAGE, getRequiredProperyValue(template,
            COMPONENT_VERSION_PAGE_CONFIG_KEY));
        templates.put(ConfluencePageType.APPLICATION_BASE_PAGE, getRequiredProperyValue(template,
            APPLICATION_BASE_PAGE_CONFIG_KEY));
        templates.put(ConfluencePageType.APPLICATION_VERSION_PAGE, getRequiredProperyValue(template,
            APPLICATION_VERSION_PAGE_CONFIG_KEY));

        // establish the confluence service
        establishConfluenceService();
    }

    /**
     * <p>
     * This constructor configures the class using file-based configuration from user-provided configuration file
     * and configuration namespace.
     * </p>
     *
     * @param configurationNamespace
     *            the filename with configuration. cannot be null or empty
     * @param configurationFileName
     *            the namespace to use. cannot be null or empty
     * @throws IllegalArgumentException
     *             if configurationFileName is null or empty, if configurationNamespace is null or empty
     * @throws ConfluenceConfigurationException
     *             if inner code threw an ConfigurationException let it propagate
     * @throws ConfluenceManagerException
     *             if any exception occurs it should be wrapped into ConfluenceManagerException
     */
    public DefaultConfluenceManager(String configurationFileName, String configurationNamespace)
        throws ConfluenceConfigurationException, ConfluenceManagerException {
        this(loadConfigFromFile(configurationFileName, configurationNamespace, true));
    }

    /**
     * <p>
     * This constructor creates the class with all provided parameters.
     * </p>
     *
     * @param spaceLocations
     *            the map containing entries 'asset type' -> space key. cannot be null. keys & values cannot be
     *            null. values cannot be empty and should be valid url
     * @param confluenceUrl
     *            the url to the confluence service. cannot be null or empty. should be valid url
     * @param templates
     *            the map containing entries 'page type' -> template file url. cannot be null. keys & values cannot
     *            be null. values cannot be empty and should be valid URLs
     * @throws IllegalArgumentException
     *             if confluenceUrl is null or empty, if spaceLocations is null or has null keys or values, if
     *             templates is null or has null keys or values
     * @throws ConfluenceManagerException
     *             if any exception occurs it should be wrapped into ConfluenceManagerException
     */
    public DefaultConfluenceManager(String confluenceUrl, Map<ConfluenceAssetType, String> spaceLocations,
        Map<ConfluencePageType, String> templates) throws ConfluenceManagerException {
        Helper.checkStringNullOrEmpty(confluenceUrl, "confluenceUrl");
        checkMap(spaceLocations, "spaceLocations");
        checkMap(templates, "templates");
        this.confluenceUrl = confluenceUrl;
        this.spaceLocations = spaceLocations;
        this.templates = templates;
        maxRetriesNumber = DEFAULT_MAX_RETRIES_NUMBER;
        log = LogManager.getLog(DEFAULT_LOG_NAME);
        establishConfluenceService();
    }

    /**
     * <p>
     * Establish a connection using the confluenceUrl.
     * </p>
     *
     * @throws ConfluenceManagerException
     *             if the confluenceUrl is invalid or any errors occur when establishing a connection
     */
    private void establishConfluenceService() throws ConfluenceManagerException {
        try {
            confluenceService = new ConfluenceServiceLocator().getConfluenceSoapService(new URL(confluenceUrl));
        } catch (MalformedURLException e) {
            throw new ConfluenceManagerException("URL is invalid.", e);
        } catch (ServiceException e) {
            throw new ConfluenceManagerException("Any errors occur when establishing a connection.", e);
        }
    }

    /**
     * <p>
     * Check whether the map is valid.
     * </p>
     *
     * @param map
     *            the map to check
     * @param mapName
     *            the name of the given map
     * @throws IllegalArgumentException
     *             if map is null or has null keys or values
     */
    private void checkMap(Map<?, String> map, String mapName) {
        Helper.checkNull(map, mapName);
        for (Entry<?, String> entry : map.entrySet()) {
            Helper.checkNull(entry.getKey(), "Any key in the map-" + mapName);
            Helper.checkStringNullOrEmpty(entry.getValue(), "Any value in the map-" + mapName);
        }
    }

    /**
     * <p>
     * Get the child configuration for the given configuration by key.
     * </p>
     *
     * @param configuration
     *            the parent configuration
     * @param key
     *            the key to retrieve
     * @return the child configuration retrieved from given configuration
     * @throws ConfluenceConfigurationException
     *             if inner code threw an ConfigurationException let it propagate or if miss the child for given
     *             name
     */
    private ConfigurationObject getChild(ConfigurationObject configuration, String key)
        throws ConfluenceConfigurationException {
        try {
            if (!configuration.containsChild(key)) {
                throw new ConfluenceConfigurationException("The child configuration named-" + key
                    + " should exist.");
            }
            return configuration.getChild(key);
        } catch (ConfigurationException e) {
            throw new ConfluenceConfigurationException("Any errors occur when getting the child configuration.", e);
        }
    }

    /**
     * <p>
     * Get the property value by given key.if the property does not exist return null.
     * </p>
     *
     * @param configuration
     *            the configuration to retrieve the property
     * @param key
     *            the key used to get property value
     * @return the property value or null if the property does not exist
     * @throws ConfluenceConfigurationException
     *             if any errors occur when getting property value or if the property value is not string type
     */
    private String getPropertyValue(ConfigurationObject configuration, String key)
        throws ConfluenceConfigurationException {
        try {
            if (!configuration.containsProperty(key)) {
                return null;
            }
            return (String) configuration.getPropertyValue(key);
        } catch (ConfigurationException e) {
            throw new ConfluenceConfigurationException("Any errors occur when try to retrieve the property value.");
        } catch (ClassCastException e) {
            throw new ConfluenceConfigurationException("The property value should be string type.");
        }
    }

    /**
     * <p>
     * Get required property value from the given configuration by the given key.
     * </p>
     *
     * @param configuration
     *            the configuration to retrieved
     * @param key
     *            the key to retrieve
     * @return the string property value
     * @throws ConfluenceConfigurationException
     *             if any errors occur when getting property value or if the property value is not string type or
     *             the required property does not exist
     */
    private String getRequiredProperyValue(ConfigurationObject configuration, String key)
        throws ConfluenceConfigurationException {

        String value = getPropertyValue(configuration, key);
        if (value == null) {
            throw new ConfluenceConfigurationException("The required property-" + key + " should be present.");
        }
        return value;
    }

    /**
     * <p>
     * Get optional property value from the given configuration by the given key.if the property is not present,
     * then return the default value.
     * </p>
     *
     * @param configuration
     *            the configuration to retrieved
     * @param key
     *            the key to retrieve
     * @param defaultValue
     *            the default value
     * @return the string property value
     * @throws ConfluenceConfigurationException
     *             if any errors occur when getting property value or if the property value is not string type
     */
    private String getOptionalPropertyValue(ConfigurationObject configuration, String key, String defaultValue)
        throws ConfluenceConfigurationException {

        String value = getPropertyValue(configuration, key);
        return value == null ? defaultValue : value;
    }

    /**
     * <p>
     * Log the method entry info.
     * </p>
     *
     * @param methodName
     *            the name of entering method
     * @param parameterNames
     *            the parameter names of method
     * @param parameterValues
     *            the parameter values of method
     */
    private void logMethodEntry(String methodName, String[] parameterNames, Object[] parameterValues) {
        log.log(Level.DEBUG, "Entering the method: " + methodName);
        StringBuffer parameterInfos = new StringBuffer("Parameters Info: ");
        for (int i = 0; i < parameterNames.length; i++) {
            String value = "";
            if (parameterValues[i] == null) {
                value = "null";
            } else if (parameterValues[i] instanceof String) {
                value = (String) parameterValues[i];
            } else {
                value = "instance of " + parameterValues[i].getClass().getName();
            }
            parameterInfos.append("parameter " + (i + 1) + ": parameterName is " + parameterNames[i]
                + "/parameterValue is " + value + "; ");
        }
        log.log(Level.DEBUG, parameterInfos.toString());
    }

    /**
     * <p>
     * Checks whether the given Object is null.And if null, log a message for warning level before throw IAE.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    private void checkAndLogNull(Object arg, String name) {
        if (arg == null) {
            log.log(Level.WARN, name + " should not be null.");
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * <p>
     * Checks whether the given Object is null or empty.And if null or empty, log a message for warning level
     * before throw IAE.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws IllegalArgumentException
     *             if the given Object is null or empty
     */
    private void checkAndLogStringNullOrEmpty(String arg, String name) {
        checkAndLogNull(arg, name);
        if (arg.trim().length() == 0) {
            log.log(Level.WARN, name + " should not be empty.");
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * <p>
     * Judge what exception would re-throw by given message.
     * </p>
     * <p>
     * Log the exception message before re-throw.
     * </p>
     *
     * @param message
     *            the given error message
     * @param e
     *            the given exception
     * @throws ConfluenceAuthenticationFailedException
     *             if AuthenticationFailedException was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     */
    private void throwExceptionByMessage(String message, Throwable e)
        throws ConfluenceAuthenticationFailedException, ConfluenceNotAuthorizedException,
        ConfluenceManagerException {

        if (message.contains("Connection refused")) {
            return;
        }

        if (message.lastIndexOf("failed") > message.indexOf("login")) {
            log.log(Level.WARN, "Failed to authenticated.");
            throw new ConfluenceAuthenticationFailedException("Failed to authenticated.", e);
        }

        if (message.contains("not authenticated") || message.contains("not allowed")) {
            log.log(Level.WARN, "Failed to authorized.");
            throw new ConfluenceNotAuthorizedException("Failed to authorized.", e);
        }

        log.log(Level.WARN, "Any errors occur when call the service methods.");
        throw new ConfluenceManagerException("Any errors occur when call the service methods.", e);
    }

    /**
     * <p>
     * This method is used to perform authorization in Confluence. Before making an API call, the caller is
     * expected to login with a user name and password, returning a token. That same token is then passed to the
     * subsequent calls into Confluence. This token is used to verify the user has access to the specific areas
     * being manipulated.
     * </p>
     *
     * @param password
     *            the user password. cannot be null or empty
     * @param userName
     *            the user name. cannot be null or empty
     * @return the authorization token. an empty string as the token to be treated as being the anonymous user
     * @throws IllegalArgumentException
     *             if userName is null or empty, if password is null or empty
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceAuthenticationFailedException
     *             if authentication failed
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     */
    public String login(String userName, String password) throws ConfluenceConnectionException,
        ConfluenceAuthenticationFailedException, ConfluenceManagerException {
        logMethodEntry("login(String userName, String password)", new String[] {"userName", "password"},
            new Object[] {userName, password});
        checkAndLogStringNullOrEmpty(userName, "userName");
        checkAndLogStringNullOrEmpty(password, "password");

        for (int retriesLeft = maxRetriesNumber; retriesLeft > 0; retriesLeft--) {
            try {
                // trying to make service call
                return confluenceService.login(userName, password);
            } catch (RemoteException e) {
                // try to re-establish the connection
                try {
                    establishConfluenceService();
                } catch (ConfluenceManagerException ex) {
                    // note this is surely connection exception
                    continue;
                }

                // re-throw the exception except for connection exception
                throwExceptionByMessage(e.getMessage(), e);
                // connection exception here
            }
        }

        log.log(Level.WARN, "connection to Confluence service failed.");
        throw new ConfluenceConnectionException("connection to Confluence service failed.");
    }

    /**
     * <p>
     * This methods logs the user out (removes the token from the list of logged in tokens).
     * </p>
     *
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @throws IllegalArgumentException
     *             if token is null.
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     */
    public void logout(String token) throws ConfluenceConnectionException, ConfluenceManagerException {
        logMethodEntry("logout(String token)", new String[] {"token"}, new Object[] {token});
        checkAndLogNull(token, "token");

        for (int retriesLeft = maxRetriesNumber; retriesLeft > 0; retriesLeft--) {
            try {
                // trying to make service call
                confluenceService.logout(token);
                return;
            } catch (RemoteException e) {
                // try to re-establish the connection
                try {
                    establishConfluenceService();
                } catch (ConfluenceManagerException ex) {
                    // note this is surely connection exception
                    continue;
                }

                // re-throw the exception except for connection exception
                throwExceptionByMessage(e.getMessage(), e);
                // connection exception here
            }
        }

        log.log(Level.WARN, "connection to Confluence service failed.");
        throw new ConfluenceConnectionException("connection to Confluence service failed.");
    }

    /**
     * <p>
     * Store the given page into corresponding space.
     * </p>
     *
     * @param token
     *            the token used to store
     * @param page
     *            the page to store
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    private void storePage(String token, RemotePage page) throws ConfluenceNotAuthorizedException,
        ConfluenceConnectionException, ConfluenceManagerException {
        // create the base page in the Confluence service
        for (int retriesLeft = maxRetriesNumber; retriesLeft > 0; retriesLeft--) {
            try {

                // get the current calendar
                Calendar calendar = Calendar.getInstance();

                // assign two required fields, one is created time,another iss modifed time
                page.setCreated(calendar);
                page.setModified(calendar);
                // trying to make service call
                confluenceService.storePage(token, page);
                return;
            } catch (RemoteException e) {
                // try to re-establish the connection
                try {
                    establishConfluenceService();
                } catch (ConfluenceManagerException ex) {
                    // note this is surely connection exception
                }

                // re-throw the exception except for connection exception
                throwExceptionByMessage(e.getMessage(), e);
                // connection exception here
            }
        }
        log.log(Level.WARN, "connection to Confluence service failed.");
        throw new ConfluenceConnectionException("connection to Confluence service failed.");
    }

    /**
     * <p>
     * Create a base page and store it in the corresponding space.
     * </p>
     *
     * @param token
     *            the token used to store the page
     * @param space
     *            the space used to stored in
     * @param basePageName
     *            the base page name of page
     * @param pageType
     *            the page type
     * @return the created page
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    private RemotePage createBasePage(String token, String space, String basePageName, ConfluencePageType pageType)
        throws ConfluenceNotAuthorizedException, ConfluenceConnectionException, ConfluenceManagerException {
        // create the base page
        RemotePage basePage = new RemotePage();

        // get the url to template file according to page type
        String templatePath = templates.get(pageType);

        // get the template page content
        RemotePage templatePage = getRemotePage(token, space, templatePath);
        String templateContent = templatePage == null ? "" : templatePage.getContent();

        // set retrieved content to the base page
        basePage.setContent(templateContent);

        // set base page properties
        basePage.setSpace(space);
        basePage.setTitle(basePageName);

        storePage(token, basePage);

        return basePage;
    }

    /**
     * <p>
     * Create a version page and store it in the corresponding space.
     * </p>
     *
     * @param token
     *            the token used to store the page
     * @param space
     *            the space used to stored in
     * @param fullPageName
     *            the full page name(base page name + version) of page
     * @param pageType
     *            the page type
     * @param version
     *            the version of the page
     * @return the RemotePage
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    private RemotePage createVersionPage(String token, String space, String fullPageName, String version,
        ConfluencePageType pageType) throws ConfluenceNotAuthorizedException, ConfluenceConnectionException,
        ConfluenceManagerException {

        // create the version page
        RemotePage versionPage = new RemotePage();

        // get the url to template file according to page type
        String templatePath = templates.get(pageType);

        // get the template page content
        RemotePage templatePage = getRemotePage(token, space, templatePath);
        String templateContent = templatePage == null ? "" : templatePage.getContent();

        // set retrieved content to the base page
        versionPage.setContent(templateContent);

        // set version page properties
        versionPage.setSpace(space);
        versionPage.setTitle(fullPageName);
        versionPage.setVersion(Integer.parseInt(version));

        // create the version page in the Confluence service
        storePage(token, versionPage);

        return versionPage;
    }

    /**
     * <p>
     * Decide the ConfluencePageCreatedAction type.
     * </p>
     *
     * @param basePageCreated
     *            indicated whether the base page is created
     * @param versionPageCreated
     *            indicated whether the version page is created
     * @return the ConfluencePageCreatedAction enum value
     */
    private ConfluencePageCreatedAction acquirePageCreatedAction(boolean basePageCreated,
        boolean versionPageCreated) {
        if (basePageCreated && versionPageCreated) {
            return ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_CREATED;
        }

        if ((!basePageCreated) && versionPageCreated) {
            return ConfluencePageCreatedAction.BASE_PAGE_EXISTED_VERSION_CREATED;
        }

        return ConfluencePageCreatedAction.BASE_PAGE_AND_VERSION_EXISTED;
    }

    /**
     * <p>
     * Get the page.if it does not exist, return null.
     * </p>
     *
     * @param token
     *            the token using to get the page
     * @param space
     *            the space using to get the page
     * @param pageName
     *            the pageName to get the page
     * @return the page retrieved from space
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    private RemotePage getRemotePage(String token, String space, String pageName)
        throws ConfluenceNotAuthorizedException, ConfluenceConnectionException, ConfluenceManagerException {
        for (int retriesLeft = maxRetriesNumber; retriesLeft > 0; retriesLeft--) {

            try {
                // trying to make service call
                return confluenceService.getPage(token, space, pageName);
            } catch (RemoteException e) {
                // try to re-establish the connection
                try {
                    establishConfluenceService();
                } catch (ConfluenceManagerException ex) {
                    // note this is surely connection exception
                    continue;
                }

                if (e.getMessage().contains(PAGE_NOT_EXIST_TOKEN1)
                    && e.getMessage().contains(PAGE_NOT_EXIST_TOKEN2)) {
                    return null;
                }

                // re-throw the exception except for connection exception
                throwExceptionByMessage(e.getMessage(), e);
                // connection exception here
            }
        }

        log.log(Level.WARN, "connection to Confluence service failed.");
        throw new ConfluenceConnectionException("connection to Confluence service failed.");
    }

    /**
     * <p>
     * Check whether the version is non-negative integer number or null or empty.
     * </p>
     *
     * @param version
     *            the version to check
     * @throws IllegalArgumentException
     *             if version is null or empty or if the version is not non-negative integer number
     */
    private void checkAndLogVersion(String version) {
        checkAndLogStringNullOrEmpty(version, "version");
        try {
            if (Integer.parseInt(version) < 0) {
                log.log(Level.WARN, "Version should be non-negative integer number.");
                throw new IllegalArgumentException("Version should be non-negative integer number.");
            }
        } catch (NumberFormatException e) {
            log.log(Level.WARN, "Version should be non-negative integer number.");
            throw new IllegalArgumentException("Version should be non-negative integer number.", e);
        }
    }

    /**
     * <p>
     * All space characters in pageName should be replaced with '+' and return the pageName after handled.
     * </p>
     * <p>
     * NOTE: consecutive spaces only replaced with only one '+'.
     * </p>
     *
     * @param pageName
     *            the pageName to handle
     * @return the pageName after handled
     */
    private String handlePageName(String pageName) {
        return pageName.replaceAll(SPACE_REPLACE_PLUS_PATTERN, PLUS_SIGN);
    }

    /**
     * <p>
     * Construct the component base page name.
     * </p>
     *
     * @param catalog
     *            the catalog used to construct the name
     * @param componentType
     *            the componentType used to construct the name
     * @param pageName
     *            the pageName used to construct the name
     * @return the component base page name
     */
    private String getComponentBasePageName(ConfluenceCatalog catalog, ComponentType componentType, String pageName) {
        // Construct the base page name
        return catalog.getStringName() + PLUS_SIGN + componentType.getStringName() + PLUS_SIGN + pageName;
    }

    /**
     * <p>
     * Construct the application base page name.
     * </p>
     *
     * @param applicationCode
     *            the applicationCode used to construct
     * @param pageName
     *            the pageName used to construct
     * @return the application base page name
     */
    private String getApplicationBasePageName(String applicationCode, String pageName) {
        return applicationCode + PLUS_SIGN + pageName;
    }

    /**
     * <p>
     * Construct the full page name.
     * </p>
     *
     * @param basePageName
     *            the base page name used to construct
     * @param version
     *            the version used to construct
     * @return the full page name
     */
    private String getFullPageName(String basePageName, String version) {
        return basePageName + PLUS_SIGN + version;
    }

    /**
     * <p>
     * Get the page space according to asset type and replace $CODENAME$ with applicationCode value when asset type
     * is APPLICATION_SPECIFICATION).
     * </p>
     *
     * @param assetType
     *            the asserType as the key to retrieve the space
     * @param applicationCode
     *            the applicationCode used to get the space
     * @return the application space
     */
    private String getApplicationSpace(ConfluenceAssetType assetType, String applicationCode) {
        String space = spaceLocations.get(assetType);

        return assetType == ConfluenceAssetType.APPLICATION_SPECIFICATION ? space.replace(CODE_NAME,
            applicationCode) : space;
    }

    /**
     * <p>
     * Get the page creation result.Retrieve the page first,if not exist then create the page.Next, we bind the
     * info to the result page and decide the ConfluencePageCreatedAction type.At last create and return the page
     * creation result with the ConfluencePageCreatedAction and page result.
     * </p>
     *
     * @param token
     *            the token to used get the page creation result
     * @param space
     *            the space used to get the related page
     * @param version
     *            the version used to get the related version page
     * @param assetType
     *            the assetType used to get the page
     * @param catalog
     *            the catalog
     * @param componentType
     *            the component type for component page creation result
     * @param basePageName
     *            the base page name
     * @param fullPageName
     *            the full page name namely version page name(base page name + version)
     * @param createdBasePageType
     *            the base page type if create a base page
     * @param createdVersionPageType
     *            the version page type if create a version page
     * @param applicationCode
     *            the applicationCode might used to get the application page creation result
     * @param pageName
     *            the page name
     * @return the ConfluencePageCreationResult
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    private ConfluencePageCreationResult getPageCreationResult(String token, String space, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, ComponentType componentType,
        String basePageName, String fullPageName, ConfluencePageType createdBasePageType,
        ConfluencePageType createdVersionPageType, String applicationCode, String pageName)
        throws ConfluenceNotAuthorizedException, ConfluenceConnectionException, ConfluenceManagerException {

        boolean basePageCreated = false;
        boolean versionPageCreated = false;

        // make the call to Confluence service to determine if base page exists

        RemotePage basePage = getRemotePage(token, space, basePageName);

        // if base page does not exist
        if (basePage == null) {
            basePage = createBasePage(token, space, basePageName, createdBasePageType);
            basePageCreated = true;
        }

        // make the call to Confluence service to determine if version page exists
        RemotePage versionPage = getRemotePage(token, space, fullPageName);

        // if version page does not exist
        if (versionPage == null) {
            // create the version page
            versionPage = createVersionPage(token, space, fullPageName, version, createdVersionPageType);
            versionPageCreated = true;
        }

        // create the Page object and set its values based on basePage and versionPage
        Page resultPage = new Page();

        if (basePage.getUrl() != null && basePage.getUrl().trim().length() != 0) {
            resultPage.setBasePageUrl(basePage.getUrl());
        }

        if (versionPage.getUrl() != null && versionPage.getUrl().trim().length() != 0) {
            resultPage.setVersionUrl(versionPage.getUrl());
        }

        resultPage.setAssetType(assetType);
        resultPage.setCatalog(catalog);
        resultPage.setVersion(version);
        if (applicationCode != null) {
            resultPage.setApplicationCode(applicationCode);
        }
        if (componentType != null) {
            resultPage.setComponentType(componentType);
        }
        resultPage.setAssetName(basePageName);

        if (basePage.getContent() != null) {
            resultPage.setContent(basePage.getContent());
        }

        // Create the result - ConfluencePageCreationResult object using resultPage and action.
        return new ConfluencePageCreationResult(resultPage, acquirePageCreatedAction(basePageCreated,
            versionPageCreated));
    }

    /**
     * <p>
     * This method is used to create page with given parameters. When creating a page, the user provides the base
     * component name and a version. If the base component already exists, a new version page is added (with its
     * contents filled from a template), but if the base component page doesn't already exist, it is first created
     * (with its contents filled from a template), and then the version page is added under it. This method should
     * be used to create pages that don't require application code (everything except Application Specification).
     * </p>
     *
     * @param componentType
     *            the type of the component (generic/custom). cannot be null
     * @param pageName
     *            the component name. cannot be null or empty. this string can have 2 formats - with spaces as
     *            delimiters ('My component') or with '+' as delimiters ('My+Component')
     * @param assetType
     *            the confluence asset type of the page being created. cannot be null
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @param catalog
     *            the catalog to represent (java/.net). cannot be null
     * @param version
     *            the version of the page that will be created. cannot be null or empty string. should be valid
     *            non-negative integer number when parsed
     * @return an object that stores result of this operation
     * @throws IllegalArgumentException
     *             if token is null, if pageName is null or empty, if version is null or empty, if assetType is
     *             null, if catalog is null, if componentType is null or if the version is not non-negative integer
     *             number string
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, ComponentType componentType)
        throws ConfluenceNotAuthorizedException, ConfluenceConnectionException, ConfluenceManagerException {
        logMethodEntry("createPage(String token, String pageName, String version,ConfluenceAssetType assetType,"
            + " ConfluenceCatalog catalog, ComponentType componentType)", new String[] {"token", "pageName",
            "version", "assetType", "catalog", "componentType"}, new Object[] {token, pageName, version,
            assetType, catalog, componentType});
        checkAndLogNull(token, "token");
        checkAndLogStringNullOrEmpty(pageName, "pageName");
        checkAndLogVersion(version);
        checkAndLogNull(assetType, "assetType");
        checkAndLogNull(catalog, "catalog");
        checkAndLogNull(componentType, "componentType");

        pageName = handlePageName(pageName);
        // Construct the basePageName and fullPageName
        String basePageName = getComponentBasePageName(catalog, componentType, pageName);
        String fullPageName = getFullPageName(basePageName, version);

        // get the page space according to asset type
        String space = spaceLocations.get(assetType);

        // get the page creation result
        return getPageCreationResult(token, space, version, assetType, catalog, componentType, basePageName,
            fullPageName, ConfluencePageType.COMPONENT_BASE_PAGE, ConfluencePageType.COMPONENT_VERSION_PAGE, null,
            pageName);
    }

    /**
     * <p>
     * This method is used to create page with given parameters. When creating a page, the user provides the base
     * component name and a version. If the base component already exists, a new version page is added (with its
     * contents filled from a template), but if the base component page doesn't already exist, it is first created
     * (with its contents filled from a template), and then the version page is added under it. This method should
     * be used to create pages that require application code (Application Specification).
     * </p>
     *
     * @param pageName
     *            the component name. cannot be null or empty. this string can have 2 formats - with spaces as
     *            delimiters ('My component') or with '+' as delimiters ('My+Component')
     * @param applicationCode
     *            the application code. cannot be null or empty
     * @param assetType
     *            the confluence asset type of the page being created. cannot be null
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @param catalog
     *            the catalog to represent (java/.net). cannot be null
     * @param version
     *            the version of the page that will be created. cannot be null or empty string. should be valid
     *            non-negative integer number when parsed
     * @return an object that stores result of this operation
     * @throws IllegalArgumentException
     *             if token is null, if pageName is null or empty, if version is null or empty, if assetType is
     *             null, if catalog is null, if applicationCode is null or empty or if the version is not
     *             non-negative integer number string
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    public ConfluencePageCreationResult createPage(String token, String pageName, String version,
        ConfluenceAssetType assetType, ConfluenceCatalog catalog, String applicationCode)
        throws ConfluenceNotAuthorizedException, ConfluenceConnectionException, ConfluenceManagerException {
        logMethodEntry("createPage(String token, String pageName, String version,"
            + " ConfluenceAssetType assetType, ConfluenceCatalog catalog, String applicationCode)", new String[] {
            "token", "pageName", "version", "assetType", "catalog", "applicationCode"}, new Object[] {token,
            pageName, version, assetType, catalog, applicationCode});
        checkAndLogNull(token, "token");
        checkAndLogStringNullOrEmpty(pageName, "pageName");
        checkAndLogVersion(version);
        checkAndLogNull(assetType, "assetType");
        checkAndLogNull(catalog, "catalog");
        checkAndLogStringNullOrEmpty(applicationCode, "applicationCode");

        pageName = handlePageName(pageName);
        String basePageName = getApplicationBasePageName(applicationCode, pageName);
        String fullPageName = getFullPageName(basePageName, version);

        // get the page space
        String space = getApplicationSpace(assetType, applicationCode);

        // get the page creation result
        return getPageCreationResult(token, space, version, assetType, catalog, null, basePageName, fullPageName,
            ConfluencePageType.APPLICATION_BASE_PAGE, ConfluencePageType.APPLICATION_VERSION_PAGE,
            applicationCode, pageName);
    }

    /**
     * <p>
     * This method is used to create page from the provided Page object, which already stores page parameters.
     * </p>
     *
     * @param page
     *            the Page object that stores parameters of the page being created. cannot be null. should have
     *            specified, at least, asset type, component type, confluence catalog, page name, version for
     *            components and additional field 'applicationCode' for
     *            assetType=ConfluenceAssetType.APPLICATION_SPECIFICATION
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @return an object that stores result of this operation
     * @throws IllegalArgumentException
     *             if page is null or at least one of asset type, component type, confluence catalog, page name,
     *             version for components and additional field 'applicationCode' for
     *             assetType=ConfluenceAssetType.APPLICATION_SPECIFICATION is not specified,or if the token is null
     *             or if the version in page is not non-negative integer number string
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    public ConfluencePageCreationResult createPage(String token, Page page)
        throws ConfluenceNotAuthorizedException, ConfluenceConnectionException, ConfluenceManagerException {
        logMethodEntry("createPage(String token, Page page)", new String[] {"token", "page"}, new Object[] {token,
            page});
        checkAndLogNull(token, "token");
        checkAndLogNull(page, "page");

        // leave the parameter checking for corresponding createXXX method
        String pageName = page.getAssetName();
        String version = page.getVersion();
        ConfluenceAssetType assetType = page.getAssetType();
        ConfluenceCatalog catalog = page.getCatalog();
        if (assetType == ConfluenceAssetType.APPLICATION_SPECIFICATION) {
            return createPage(token, pageName, version, assetType, catalog, page.getApplicationCode());
        }

        return createPage(token, pageName, version, assetType, catalog, page.getComponentType());
    }

    /**
     * <p>
     * Retrieve the page from space.
     * </p>
     *
     * @param token
     *            the token used to retrieve
     * @param space
     *            the space retrieved from
     * @param fullPageName
     *            the full page name
     * @param basePageName
     *            the base page name
     * @param pageName
     *            the page name
     * @param assetType
     *            the type of space
     * @param applicationCode
     *            the application code
     * @param componentType
     *            the component type
     * @param catalog
     *            the catalog type
     * @param version
     *            the version
     * @return the Page retrieved from space, if it does not exist return null
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    private Page retrievePage(String token, String space, String fullPageName, String basePageName,
        String pageName, ConfluenceAssetType assetType, String applicationCode, ComponentType componentType,
        ConfluenceCatalog catalog, String version) throws ConfluenceNotAuthorizedException,
        ConfluenceConnectionException, ConfluenceManagerException {

        // make the call to Confluence service to determine if page exists
        RemotePage page = getRemotePage(token, space, fullPageName);

        // if the page does not exist
        if (page == null) {
            return null;
        }

        // get the base page (we need this to get base page url)
        RemotePage basePage = getRemotePage(token, space, basePageName);

        // create resulting page
        Page result = new Page();
        // set resulting page properties according to remote page and method arguments
        result.setContent(page.getContent());
        result.setVersionUrl(page.getUrl());
        result.setBasePageUrl(basePage.getUrl());
        result.setAssetName(basePageName);
        result.setAssetType(assetType);
        if (applicationCode != null) {
            result.setApplicationCode(applicationCode);
        }

        if (componentType != null) {
            result.setComponentType(componentType);
        }
        result.setCatalog(catalog);
        result.setVersion(version);
        return result;
    }

    /**
     * <p>
     * This method is used to retrieve the page from the Confluence that does not need application code (everything
     * except Application Specification).
     * </p>
     *
     * @param componentType
     *            the type of the component (generic/custom). cannot be null
     * @param pageName
     *            the component name. cannot be null or empty. this string can have 2 formats - with spaces as
     *            delimiters ('My component') or with '+' as delimiters ('My+Component')
     * @param assetType
     *            the confluence asset type of the page being created. cannot be null
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @param catalog
     *            the catalog to represent (java/.net). cannot be null
     * @param version
     *            the version of the page that will be created. cannot be null or empty string. should be valid
     *            non-negative integer number when parsed
     * @return the Page object containing retrieved remote page parameters and content or return null if the page
     *         does not exist
     * @throws IllegalArgumentException
     *             if token is null, if pageName is null or empty, if version is null or empty, if assetType is
     *             null, if catalog is null, if componentType is null or if the version is not non-negative integer
     *             number string
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, ComponentType componentType) throws ConfluenceConnectionException,
        ConfluenceManagerException, ConfluenceNotAuthorizedException {

        logMethodEntry("retrievePage(String token, String pageName, String version"
            + ", ConfluenceAssetType assetType,  ConfluenceCatalog catalog, ComponentType componentType)",
            new String[] {"token", "pageName", "version", "assetType", "catalog", "componentType"}, new Object[] {
                token, pageName, version, assetType, catalog, componentType});
        checkAndLogNull(token, "token");
        checkAndLogStringNullOrEmpty(pageName, "pageName");
        checkAndLogVersion(version);
        checkAndLogNull(assetType, "assetType");
        checkAndLogNull(catalog, "catalog");
        checkAndLogNull(componentType, "componentType");

        pageName = handlePageName(pageName);
        String basePageName = getComponentBasePageName(catalog, componentType, pageName);
        String fullPageName = getFullPageName(basePageName, version);

        // get the page space
        String space = spaceLocations.get(assetType);

        return retrievePage(token, space, fullPageName, basePageName, fullPageName, assetType, null,
            componentType, catalog, version);
    }

    /**
     * <p>
     * This method is used to retrieve the page from the Confluence that needs application code (Application
     * Specification).
     * </p>
     *
     * @param pageName
     *            the component name. cannot be null or empty. this string can have 2 formats - with spaces as
     *            delimiters ('My component') or with '+' as delimiters ('My+Component')
     * @param applicationCode
     *            the application code. cannot be null or empty
     * @param assetType
     *            the confluence asset type of the page being created. cannot be null
     * @param token
     *            the authorization token obtained from login method. cannot be null, can be empty
     * @param catalog
     *            the catalog to represent (java/.net). cannot be null
     * @param version
     *            the version of the page that will be created. cannot be null or empty string. should be valid
     *            non-negative integer number when parsed
     * @return the Page object containing retrieved remote page parameters and content or return null if the page
     *         does not exist
     * @throws IllegalArgumentException
     *             if token is null, if pageName is null or empty, if version is null or empty, if assetType is
     *             null, if catalog is null, if applicationCode is null or empty or if the version is not
     *             non-negative integer number string
     * @throws ConfluenceConnectionException
     *             if connection wasn't established or was lost
     * @throws ConfluenceManagerException
     *             if any other exception was caught, wrap it and re-throw
     * @throws ConfluenceNotAuthorizedException
     *             if user is not logged in
     */
    public Page retrievePage(String token, String pageName, String version, ConfluenceAssetType assetType,
        ConfluenceCatalog catalog, String applicationCode) throws ConfluenceConnectionException,
        ConfluenceManagerException, ConfluenceNotAuthorizedException {
        logMethodEntry("retrievePage(String token, String pageName, String version"
            + ", ConfluenceAssetType assetType, ConfluenceCatalog catalog, String applicationCode)", new String[] {
            "token", "pageName", "version", "assetType", "catalog", "applicationCode"}, new Object[] {token,
            pageName, version, assetType, catalog, applicationCode});
        checkAndLogNull(token, "token");
        checkAndLogStringNullOrEmpty(pageName, "pageName");
        checkAndLogVersion(version);
        checkAndLogNull(assetType, "assetType");
        checkAndLogNull(catalog, "catalog");
        checkAndLogStringNullOrEmpty(applicationCode, "applicationCode");

        pageName = handlePageName(pageName);
        String basePageName = getApplicationBasePageName(applicationCode, pageName);
        String fullPageName = getFullPageName(basePageName, version);

        // get the page space
        String space = getApplicationSpace(assetType, applicationCode);

        // retrieve the page from space
        return retrievePage(token, space, fullPageName, basePageName, fullPageName, assetType, applicationCode,
            null, catalog, version);
    }

    /**
     * <p>
     * This function loads the configuration from given file having given namespace.
     * </p>
     *
     * @param configurationNamespace
     *            the namespace to use. cannot be null or empty
     * @param configurationFileName
     *            the filename with configuration if null means default file (ConfigurationManager.properties).
     *            cannot be empty
     * @param isConfigurationFileNameRequired
     *            Indicates whether the configurationFileName is required(means not null or empty.)
     * @return the loaded configuration object
     * @throws IllegalArgumentException
     *             if configurationFileName is null or empty when it is required or if configurationNamespace is
     *             null or empty
     * @throws ConfluenceManagerException
     *             if any exception occurs it should be wrapped into ConfluenceManagerException
     * @throws ConfluenceConfigurationException
     *             if ConfigurationException is caught it should be wrapped into ConfluenceConfigurationException
     *             and re-thrown of if no children for root configuration
     */
    private static ConfigurationObject loadConfigFromFile(String configurationFileName,
        String configurationNamespace, boolean isConfigurationFileNameRequired) throws ConfluenceManagerException {
        Helper.checkStringNullOrEmpty(configurationNamespace, "configurationNamespace");
        if (isConfigurationFileNameRequired) {
            Helper.checkStringNullOrEmpty(configurationFileName, "configurationFileName");
        }
        try {
            ConfigurationFileManager cm =
                configurationFileName == null ? new ConfigurationFileManager() : new ConfigurationFileManager(
                    configurationFileName);
            ConfigurationObject[] rootChildren = cm.getConfiguration(configurationNamespace).getAllChildren();
            if (rootChildren.length == 0) {
                throw new ConfluenceConfigurationException("No children for root configuration.");
            }
            return rootChildren[0];
        } catch (ConfigurationPersistenceException e) {
            throw new ConfluenceManagerException("Any errors occur when getting the configuration object.", e);
        } catch (ConfigurationException e) {
            throw new ConfluenceConfigurationException(
                "Any errors occur when getting the first child configuration object.", e);
        } catch (IOException e) {
            throw new ConfluenceManagerException("Any IO errors occur when parsing the configuration file.", e);
        }
    }
}