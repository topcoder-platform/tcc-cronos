/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.impl.ConfigurationException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * Utility class with several simple helper methods.
 * </p>
 * <p>
 * <strong>Thread safe:</strong> Class is stateless and thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public final class Helper {
    /**
     * Private constructor to prevent instantiation.
     */
    private Helper() {
    }

    /**
     * <p>
     * Checks object for null and throw IllegalArgumentException if it is.
     * </p>
     *
     * @param name of the object, to be used in messages.
     * @param data Object to check
     * @param <T> type of data
     * @return data parameter, implemented only for user's convenience
     * @throws IllegalArgumentException when data parameter is null
     */
    public static <T> T checkNull(String name, T data) {
        if (data == null) {
            throw new IllegalArgumentException(name
                    + " parameter not supposed to be null.");
        } else {
            return data;
        }
    }

    /**
     * <p>
     * Checks object for null and throw IllegalArgumentException if it is. In
     * case of error, attempts to log it.
     * </p>
     *
     * @param name of the object, to be used in messages.
     * @param data Object to check
     * @param log logger to persist error data
     * @param <T> type of data
     * @return data parameter, implemented only for user's convenience
     * @throws IllegalArgumentException when data parameter is null
     */
    public static <T> T checkNull(Log log, String name, T data) {
        try {
            return checkNull(name, data);
        } catch (IllegalArgumentException e) {
            logError(log, e);
            throw e;
        }
    }

    /**
     * <p>
     * Checks string for being null or empty.
     * </p>
     *
     * @param name of the string, to be used in messages.
     * @param data String to check
     * @throws IllegalArgumentException when data parameter is null or empty
     * @return the string checked.
     */
    public static String checkString(String name, String data) {
        checkNull(name, data);
        if (data.trim().length() == 0) {
            throw new IllegalArgumentException(name
                    + " parameter not supposed to be empty.");
        }
        return data;
    }

    /**
     * <p>
     * Checks string for being null or empty.
     * </p>
     *
     * @param name of the string, to be used in messages.
     * @param data String to check
     * @throws IllegalArgumentException when data parameter is null or empty
     * @return the string checked.
     */
    public static String checkStringNotEmpty(String name, String data) {
        if (data != null && data.trim().length() == 0) {
            throw new IllegalArgumentException(name
                    + " parameter not supposed to be empty.");
        }
        return data;
    }

    /**
     * <p>
     * Checks string for being null or empty. In case of error, attempts to log
     * it.
     * </p>
     *
     * @param log logger to persist error data
     * @param name of the string, to be used in messages.
     * @param data String to check
     * @throws IllegalArgumentException when data parameter is null or empty
     */
    public static void checkString(Log log, String name, String data) {
        try {
            checkString(name, data);
        } catch (IllegalArgumentException e) {
            logError(log, e);
            throw e;
        }
    }

    /**
     * <p>
     * Checks id whether is positive.
     * </p>
     *
     * @param log logger to persist error data
     * @param name the name of argument.
     * @param id to check
     * @throws IllegalArgumentException when id is not positive.
     */
    public static void checkId(Log log, String name, long id) {
        if (id <= 0) {
            throw logError(log, new IllegalArgumentException(name
                    + " should be positive."));
        }
    }

    /**
     * <p>
     * This method used to log enter in method.
     * </p>
     *
     * @param log logger to persist data
     * @param method name of the entered method
     */
    public static void logEnter(Log log, String method) {
        if (log != null) {
            log.log(Level.DEBUG, "Enter method: {0}.", method);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     *
     * @param log logger to persist data
     * @param method name of the leaved method
     */
    public static void logExit(Log log, String method) {
        if (log != null) {
            log.log(Level.DEBUG, "Leave method: {0}.", method);
        }
    }

    /**
     * <p>
     * This method used to log useful information.
     * </p>
     *
     * @param log logger to persist data
     * @param message to be logged
     *
     */
    public static void logInfo(Log log, String message) {
        if (log != null) {
            log.log(Level.INFO, message);
        }
    }

    /**
     * <p>
     * This method used to log arbitrary error. It will persist error's data and
     * throw exception back.
     * </p>
     *
     * @param log logger to persist data
     * @param error exception describing error
     * @param <T> type of Exception
     * @return the error
     */
    public static <T extends Exception> T logError(Log log, T error) {
        if (log != null) {
            log.log(Level.ERROR, error, "Error recognized: {0}.", error.getMessage());
        }
        return error;
    }

    /**
     * <p>
     * Get a String property from configuration object.
     * </p>
     *
     * @param config the configuration object.
     * @param key the key of the property
     * @param required if the property is required.
     * @return the value of the property as String.
     * @throws ConfigurationException if any error occurred.
     */
    public static String getStringProperty(ConfigurationObject config,
            String key, boolean required) throws ConfigurationException {
        String property;

        try {
            property = getProperty(config, key, required);
        } catch (ClassCastException e) {
            throw new ConfigurationException("The configuration property "
                    + key + " must be a String.", e);
        }

        if (property != null && property.trim().length() == 0) {
            throw new ConfigurationException("The configuration property "
                    + key + " is required and should not be empty.");
        }

        return property;
    }

    /**
     * <p>
     * Get a property from configuration object.
     * </p>
     *
     * @param config the configuration object.
     * @param key the key of the property
     * @return the value of the property as String.
     * @throws ConfigurationException if any error occurred.
     */
    public static String getProperty(ConfigurationObject config, String key) throws ConfigurationException {
        String property;
        try {
            property = (String) config.getPropertyValue(key);
        } catch (ConfigurationAccessException e) {
            throw new ConfigurationException("Cannot get configuration property "
                    + key + ". Access exception: " + e.getMessage(), e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("The configuration property "
                    + key + " must be a String.", e);
        }
        return property;
    }

    /**
     * <p>
     * Get a property from configuration object.
     * </p>
     *
     * @param config the configuration object.
     * @param key the key of the property
     * @param required if the property is required.
     * @return the value of the property as String.
     * @throws ConfigurationException if any error occurred.
     */
    private static String getProperty(ConfigurationObject config, String key,
            boolean required) throws ConfigurationException {
        String property = getProperty(config, key);

        if (required && (property == null)) {
            throw new ConfigurationException("The configuration property "
                    + key + " is required and should not be null or empty.");
        }

        return property;
    }

    /**
     * Creates object factory using config provided. If root has no config for
     * object factory, default constructor is used.
     *
     * @param factoryConfig configuration to build factory
     * @return created object factory.
     * @throws ConfigurationException if any error occurred.
     */
    public static ObjectFactory createFactory(ConfigurationObject factoryConfig) throws ConfigurationException {
        try {
            if (factoryConfig != null) {
                return new ObjectFactory(new ConfigurationObjectSpecificationFactory(factoryConfig));
            } else {
                throw new ConfigurationException("can't create object factory with given config ");
            }
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("Can't create specification factory from configuration.", e);
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("Can't create specification factory from configuration.", e);
        }

    }

    /**
     * Creates object using config provided.
     *
     * @param <T> Object returned
     * @param objectFactory the object factory.
     * @param configKey the configuration key
     * @param t the class to create.
     * @return created object factory.
     * @throws ConfigurationException if any error occurred.
     */

    @SuppressWarnings("unchecked")
    public static <T extends Object> T createObject(
            ObjectFactory objectFactory, String configKey, Class<?> t)
        throws ConfigurationException {
        try {
            return (T) objectFactory.createObject(configKey);
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException("InvalidClassSpecificationException occur", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("fail to cast the class.", e);
        }

    }

    /**
     * Fetches a child ConfigurationObject from passed one.
     *
     * @param config config to fetch child from
     * @param name name of the child
     * @return the config object.
     * @throws ConfigurationException if any error occurred.
     *
     */
    public static ConfigurationObject getChild(ConfigurationObject config,
            String name) throws ConfigurationException {
        try {
            ConfigurationObject child = config.getChild(name);
            if (child == null) {
                throw new ConfigurationException("can not to fetch child ["
                        + name + "] from configuration.");
            }
            return child;
        } catch (ConfigurationAccessException e) {
            throw new ConfigurationException("Unable to fetch child [" + name
                    + "] from configuration.", e);
        }
    }

    /**
     * get log from configuration.
     *
     * @param configuration to get log from
     * @return the log retrieved.
     * @throws ConfigurationException if fail to get log.
     */
    public static Log getLogger(ConfigurationObject configuration) throws ConfigurationException {
        checkNull("configuration", configuration);
        // get logger instance
        String loggerName = Helper.getStringProperty(configuration, "loggerName", false);
        return LogManager.getLog(loggerName);

    }

    /**
     * release database resource.
     *
     * @param conn database connection
     * @param stmt statement
     * @param rs result set
     */
    public static void releaseDBResource(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }

    }

    /**
     * <p>
     * get specific component list from dependencies.
     * </p>
     *
     * @param deps the dependencies.
     * @param category Dependency Category
     * @param type Dependency Type
     * @return the component in dependencies
     */
    public static List<Component> getComponentByTypeAndCategory(
            List<ComponentDependency> deps, DependencyCategory category,
            DependencyType type) {
        List<Component> components = new ArrayList<Component>();
        if (category == null && type == null) {
            for (ComponentDependency dep : deps) {
                components.add(dep.getComponent());
            }
        } else if (category != null && type == null) {
            for (ComponentDependency dep : deps) {
                if (dep.getCategory().equals(category)) {
                    components.add(dep.getComponent());
                }
            }
        } else if (category == null && type != null) {
            for (ComponentDependency dep : deps) {
                if (dep.getType().equals(type)) {
                    components.add(dep.getComponent());
                }
            }
        } else {
            for (ComponentDependency dep : deps) {
                if (dep.getType().equals(type)
                        && dep.getCategory().equals(category)) {
                    components.add(dep.getComponent());
                }
            }
        }

        // if version, license, url, description are null, set it to "NONE"
        resetNullValue(components);
        return components;
    }

    /**
     * if version, license, URL, description are null, set it to "NONE" in
     * report file.
     *
     * @param components to set
     */
    public static void setBackValue(List<Component> components) {
        for (Component component : components) {
            if (component.getVersion().equals("NONE")) {
                component.setVersion(null);
            }
            if (component.getLicense().equals("NONE")) {
                component.setLicense(null);
            }
            if (component.getUrl().equals("NONE")) {
                component.setUrl(null);
            }
            if (component.getDescription().equals("NONE")) {
                component.setDescription(null);
            }
        }
    }

    /**
     * set back to null value.
     *
     * @param components to set
     */
    public static void resetNullValue(List<Component> components) {
        for (Component component : components) {
            if (component.getVersion() == null
                    || component.getVersion().trim().length() == 0) {
                component.setVersion("NONE");
            }
            if (component.getLicense() == null
                    || component.getLicense().trim().length() == 0) {
                component.setLicense("NONE");
            }
            if (component.getUrl() == null
                    || component.getUrl().trim().length() == 0) {
                component.setUrl("NONE");
            }
            if (component.getDescription() == null
                    || component.getDescription().trim().length() == 0) {
                component.setDescription("NONE");
            }
        }
    }

    /**
     * <p>
     * Set the null value default to N/A.
     * </p>
     * @param str input string to convert
     * @return N/A if input is null, str if not null
     */
    public static String setDefaultNA(String str) {
        return str == null ? "N/A" : str;
    }
}
