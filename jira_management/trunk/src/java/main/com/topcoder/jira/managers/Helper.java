/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.managers;

import java.io.IOException;
import java.util.Map;

import com.atlassian.jira.rpc.exception.RemoteException;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationPersistenceException;
import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraManagerException;
import com.topcoder.jira.JiraNotAuthorizedException;
import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.JiraProjectRetrievalResult;
import com.topcoder.jira.JiraProjectValidationException;
import com.topcoder.jira.JiraSecurityTokenExpiredException;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;

/**
 * This class defines common utility methods used in this component.    // TODO: The Helper class is unnecessary. Please move all helper methods into DefaultJiraManager and make them private.
 * <p>
 * Thread-safety. This class is immutable and thread-safe.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {            // TODO: The Helper class needs to be tested.
    /**
     * Key used to retrieve scheme name associated with generic component from configuration.
     */
    private static final String GENERIC_TOKEN_KEY = "generic_token";

    /**
     * Key used to retrieve scheme name associated with custom component from configuration.
     */
    private static final String CUSTOM_TOKEN_KEY = "custom_token";

    /**
     * Key used to retrieve scheme name associated with generic component from configuration.       // TODO: "application_token" should be associated with applications
     */
    private static final String APPLICATION_TOKEN_KEY = "application_token";

    /**
     * This class couldn't be instantiated.
     */
    private Helper() {
    }

    /**
     * Utility method used to get string representation of the object.
     * <p>
     * Used for logging.
     *
     * @param obj object to log
     * @return string representation of the passed object.
     */
    static String toString(Object obj) {
        if (obj instanceof JiraProject) {
            JiraProject project = (JiraProject) obj;
            return "project(id=" + project.getId() + ", key=" + project.getKey() + ", name="
                + project.getName() + ")";
        }
        if (obj instanceof JiraVersion) {
            JiraVersion version = (JiraVersion) obj;
            return "version(id=" + version.getId() + ", name=" + version.getName() + ")";
        }
        if (obj instanceof JiraProjectCreationResult) {
            JiraProjectCreationResult result = (JiraProjectCreationResult) obj;
            return "{" + result.getActionTaken() + ", " + toString(result.getProject()) + ", "
                + toString(result.getVersion()) + "}";
        }
        if (obj instanceof JiraProjectRetrievalResult) {
            JiraProjectRetrievalResult result = (JiraProjectRetrievalResult) obj;
            return "{" + result.getRetrievalResult() + ", " + toString(result.getProject()) + ", "
                + toString(result.getVersion()) + "}";
        }

        return obj == null ? "null" : obj.toString();
    }

    /**
     * Returns a child configuration object with the given name.
     *
     * @param configurationObject configuration object to get child from
     * @param name name of the child
     * @return child configuration object
     * @throws JiraManagerConfigurationException if error occurs while retrieving the child.
     */
    static ConfigurationObject retrieveChild(ConfigurationObject configurationObject, String name) {
        ConfigurationObject child;
        // get child by name
        try {
            child = configurationObject.getChild(name);
        } catch (ConfigurationAccessException e) {
            throw new JiraManagerConfigurationException("Unable to retrieve child with name '" + name + "'.",
                e);
        }
        return child;
    }

    /**
     * Returns string property value for the given key.
     *
     * @param configurationObject configuration object to get property from
     * @param name name of the property
     * @param canBeNullOrEmpty flag that indicates whether the value can be null or empty string
     * @return property value for the given key. If value is empty string then null will be
     *         returned.
     * @throws JiraManagerConfigurationException if error occurs while retrieving the value; if
     *         value is null or empty and canBeNullOrEmpty flag is false; if value is not a String
     *         instance.
     */
    static String retrievePropertyValue(ConfigurationObject configurationObject, String name,
        boolean canBeNullOrEmpty) {
        Object value;

        // get value
        try {
            if (configurationObject.getPropertyValuesCount(name) > 1) {
                throw new JiraManagerConfigurationException("Property '" + name
                    + "' couldn't have more than one value.");
            }
            value = configurationObject.getPropertyValue(name);
        } catch (ConfigurationAccessException e) {
            throw new JiraManagerConfigurationException("Unable to retrieve property with name '" + name
                + "'.", e);

        }

        // make sure that value is a String instance
        String stringValue;
        try {
            stringValue = (String) value;
        } catch (ClassCastException e) {
            throw new JiraManagerConfigurationException("Value of the property must be String object.", e);
        }

        // check whether value is null or empty
        if ((stringValue == null || stringValue.trim().length() == 0) && !canBeNullOrEmpty) {
            throw new JiraManagerConfigurationException("Property '" + name + "' couldn't be null or empty.");
        }

        return stringValue;
    }

    /**
     * Reads mapping from configuration.
     *
     * @param configurationObject configuration object to get data from
     * @param map map to read data in
     * @param namespace child configuration object with data for concrete mapping
     * @param defaultMap default mapping to use if child configuration doesn't exist
     * @throws JiraManagerConfigurationException if configuration data invalid.
     */
    static void readMapping(ConfigurationObject configurationObject, Map<ComponentType, String> map,
        String namespace, Map<ComponentType, String> defaultMap) {
        // get configuration namespace
        ConfigurationObject child = retrieveChild(configurationObject, namespace);
        if (child == null) {
            map.putAll(defaultMap);
            return;
        }

        // read mappings
        map.put(ComponentType.APPLICATION, retrievePropertyValue(child, APPLICATION_TOKEN_KEY, false));
        map.put(ComponentType.CUSTOM_COMPONENT, retrievePropertyValue(child, CUSTOM_TOKEN_KEY, false));
        map.put(ComponentType.GENERIC_COMPONENT, retrievePropertyValue(child, GENERIC_TOKEN_KEY, false));
    }

    /**
     * Wraps given remote exception into the corresponding <code>JiraManagerException</code>
     * instance.
     * <p>
     * Methods checks fault string for 'RemoteAuthenticationException', 'RemoteValidationException'
     * or 'RemotePermissionException' substring. If one of these strings is found then corresponding
     * <code>JiraSecurityTokenExpiredException</code>,
     * <code>JiraProjectValidationException</code> or <code>JiraNotAuthorizedException</code> is
     * thrown. If none were found then exception is wrapped into the
     * <code>JiraManagerException</code>
     *
     * @param e caught exception
     * @return JiraManagerException wrapper around the passed exception.
     */
    static JiraManagerException parseRemoteException(RemoteException e) {
        // we have to parse message
        if (e.getFaultString().contains("RemoteAuthenticationException")) {
            return new JiraSecurityTokenExpiredException("Authentication token is invalid.", e);
        }
        if (e.getFaultString().contains("RemoteValidationException")) {
            return new JiraProjectValidationException("Validation error occurred.", e);
        }
        if (e.getFaultString().contains("RemotePermissionException")) {
            return new JiraNotAuthorizedException("User doesn't have permissions to perform operation.", e);
        }
        return new JiraManagerException("Unable to perform operation due to general remote exception", e);
    }

    /**
     * Creates configuration object from the given file and namespace.
     *
     * @param filename name of the configuration file. Can not be null or empty string.
     * @param namespace namespace. Can not be null or empty string.
     * @return configuration object representing given namespace from the given file.
     * @throws IllegalArgumentException if one of the arguments is null or empty.
     * @throws JiraManagerConfigurationException if configuration data are invalid.
     */
    static ConfigurationObject getConfigurationObject(String filename, String namespace) {
        checkString(filename, "filename");
        checkString(namespace, "namespace");
        try {
            return new ConfigurationFileManager(filename).getConfiguration(namespace);
        } catch (ConfigurationPersistenceException e) {
            throw new JiraManagerConfigurationException("Configuration data are invalid.", e);
        } catch (IOException e) {
            throw new JiraManagerConfigurationException("I/O error occurred while reading configuration.", e);
        }
    }

    /**
     * Checks whether this map contains mappings for all component types.
     *
     * @param map the map to check
     * @param paramName the actual parameter name of the argument being checked
     * @throws IllegalArgumentException if map is not null and doesn't contain mapping for every
     *         component type; if map is not null and contains mapping not only for component types;
     *         if map values are null or empty string.
     */
    static void checkMap(Map<ComponentType, String> map, String paramName) {
        // ignore null map
        if (map == null) {
            return;
        }
        // check size
        if (map.size() != ComponentType.values().length) {
            throw new IllegalArgumentException("Map " + paramName
                + " doesn't contain mapping for every component type.");
        }
        // check keys and values
        for (ComponentType type : ComponentType.values()) {
            String value = map.get(type);
            if (value == null) {
                throw new IllegalArgumentException("Map " + paramName + " couldn't contain null values.");
            }
            if (value.trim().length() == 0) {
                throw new IllegalArgumentException("Map " + paramName + " couldn't contain empty values.");
            }
        }
    }

    /**
     * Checks whether the given object is null.
     *
     * @param obj the object to check
     * @param paramName the actual parameter name of the argument being checked
     * @throws IllegalArgumentException if object is null.
     */
    static void checkNotNull(Object obj, String paramName) {
        if (obj == null) {
            throw new IllegalArgumentException("Argument " + paramName + " cannot be null.");
        }
    }

    /**
     * Checks whether the given string is null or empty.
     *
     * @param str the string to check
     * @param paramName the actual parameter name of the argument being checked
     * @throws IllegalArgumentException if argument is null or empty.
     */
    static void checkString(String str, String paramName) {
        checkNotNull(str, paramName);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("Argument " + paramName + " must be non-empty string.");
        }
    }
}
