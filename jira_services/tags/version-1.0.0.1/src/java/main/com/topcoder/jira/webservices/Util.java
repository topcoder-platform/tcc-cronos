/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;
import com.topcoder.jira.managers.JiraManagerConfigurationException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.io.IOException;
import java.util.Arrays;

/**
 * Utility class with several simple helper methods.
 *
 * Class is stateless and thread-safe.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Util {
    /**
     * Private constructor to prevent instantiation.
     */
    private Util() {
    }

    /**
     * <p>Checks object for null and throw IllegalArgumentException if it is.</p>
     *
     * @param name of the object, to be used in messages.
     * @param data Object to check
     * @param <T> type of data
     * @return data parameter, implemented only for user's convenience
     * @throws IllegalArgumentException when data parameter is null
     */
    public static <T> T checkNull(String name, T data) {
        if (data == null) {
            throw new IllegalArgumentException(name + " parameter not supposed to be null.");
        } else {
            return data;
        }
    }

    /**
     * <p>Checks object for null and throw IllegalArgumentException if it is. In case of error, attempts to log it.</p>
     *
     * @param name of the object, to be used in messages.
     * @param data Object to check
     * @param log  logger to persist error data
     * @param <T> type of data
     * @return data parameter, implemented only for user's convenience
     * @throws IllegalArgumentException when data parameter is null
     */
    public static <T> T checkNull(Log log, String name, T data) {
        try {
            return Util.checkNull(name, data);
        } catch (IllegalArgumentException e) {
            Util.logError(log, e);
            throw e;
        }
    }

    /**
     * <p>Checks string for being null or empty.</p>
     *
     * @param name of the string, to be used in messages.
     * @param data String to check
     * @throws IllegalArgumentException when data parameter is null or empty
     */
    public static void checkString(String name, String data) {
        checkNull(name, data);
        if (data.trim().length() == 0) {
            throw new IllegalArgumentException(name + " parameter not supposed to be empty.");
        }
    }

    /**
     * <p>Checks string for being null or empty. In case of error, attempts to log it.</p>
     *
     * @param log  logger to persist error data
     * @param name of the string, to be used in messages.
     * @param data String to check
     * @throws IllegalArgumentException when data parameter is null or empty
     */
    public static void checkString(Log log, String name, String data) {
        try {
            Util.checkString(name, data);
        } catch (IllegalArgumentException e) {
            Util.logError(log, e);
            throw e;
        }
    }

    /**
     * <p>This method used to log enter in method with arbitrary number of parameters.</p>
     *
     * @param log    logger to persist data
     * @param method name of the entered method
     * @param params parameters used to invoke method
     */
    public static void logEnter(Log log, String method, Object... params) {
        if (log != null) {
            log.log(Level.DEBUG, "Enter method {0} with parameters {1}.", method, Arrays.deepToString(params),
                    System.currentTimeMillis());
        }
    }

    /**
     * <p>This method used to log leave of method. It will persist method name and result.</p>
     *
     * @param log    logger to persist data
     * @param method name of the leaved method
     * @param result value returned from the method
     */
    public static void logExit(Log log, String method, Object result) {
        if (log != null) {
            log.log(Level.DEBUG, "Leave method {0} with return value {1}.", method, result);
        }
    }

    /**
     * <p>This method used to log leave of method. It will persist method name.</p>
     *
     * @param log    logger to persist data
     * @param method name of the leaved method
     */
    public static void logExit(Log log, String method) {
        if (log != null) {
            log.log(Level.DEBUG, "Leave method {0}.", method);
        }
    }

    /**
     * <p>This method used to log arbitrary error. It will persist error's data and throw exception back.</p>
     *
     * @param log   logger to persist data
     * @param error exception describing error
     */
    public static void logError(Log log, Exception error) {
        if (log != null) {
            log.log(Level.WARN, error, "Error recognized: {0}.", error.getMessage());
        }
    }

    /**
     * Reads configuration from file using ConfigurationFileManager.
     *
     * @param file      root configuration file
     * @param namespace specific namespace to load configuration
     * @return retrieved configuration
     * @throws JiraManagerConfigurationException if any error occurs while reading configuration
     */
    public static ConfigurationObject readConfiguration(String file, String namespace) {
        try {
            ConfigurationFileManager manager = new ConfigurationFileManager(file);
            return manager.getConfiguration(namespace);
        } catch (IOException e) {
            throw new JiraManagerConfigurationException(
                    "Unable to create ConfigurationFileManager from file [" + file + "].", e);
        } catch (NamespaceConflictException e) {
            throw new JiraManagerConfigurationException(
                    "Unable to create ConfigurationFileManager from file [" + file + "].", e);
        } catch (UnrecognizedNamespaceException e) {
            throw new JiraManagerConfigurationException(
                    "No [" + namespace + "] found in the configuration file [" + file + "].", e);
        } catch (ConfigurationParserException e) {
            throw new JiraManagerConfigurationException(
                    "Unable to create ConfigurationFileManager from file [" + file + "].", e);
        } catch (UnrecognizedFileTypeException e) {
            throw new JiraManagerConfigurationException(
                    "Unable to create ConfigurationFileManager from file [" + file + "].", e);
        }
    }

    /**
     * Fetches a child ConfigurationObject from passed one.
     *
     * @param config config to fetch child from
     * @param name   name of the child
     * @return child configuration object
     * @throws JiraManagerConfigurationException if any error occurs while retrieving the child
     */
    public static ConfigurationObject getChild(ConfigurationObject config, String name) {
        try {
            return config.getChild(name);
        } catch (ConfigurationAccessException e) {
            throw new JiraManagerConfigurationException("Unable to fetch child [" + name + "] from configuration.", e);
        }
    }

    /**
     * <p>Retrieves string property from configuration.</p>
     *
     * @param config   configuration to retrieve property from
     * @param key      string identifying property
     * @param required whether this property is required (can't be null) or optional
     * @return property found, possibly null, never empty string
     * @throws JiraManagerConfigurationException
     *          if property can't be retrieved for some reason
     */
    public static String getProperty(ConfigurationObject config, String key, boolean required) {
        try {
            Object value = config.getPropertyValue(key);

            // check for null/empty value
            if (value == null || (value instanceof String && value.toString().trim().length() == 0)) {
                if (required) {
                    throw new JiraManagerConfigurationException(
                            "Required configuration property [" + key + "] is missed.");
                } else {
                    return null;
                }
            }

            if (!(value instanceof String)) {
                // not a string
                throw new JiraManagerConfigurationException("Property [" + key + "] is not string in configuration.");
            }

            return (String) value;
        } catch (ConfigurationAccessException e) {
            throw new JiraManagerConfigurationException("Unable to fetch property [" + key + "] from configuration.",
                    e);
        }
    }

    /**
     * Creates object factory using config provided. If root has no config for object factory, default constructor is
     * used.
     *
     * @param configRoot configuration to build factory
     * @return created object factory
     * @throws JiraManagerConfigurationException
     *          if factory can't be created for some reason
     */
    public static ObjectFactory createFactory(ConfigurationObject configRoot) {
        try {
            ConfigurationObject factoryConfig = getChild(configRoot, "spec_factory_config");

            if (factoryConfig != null) {
                return new ObjectFactory(new ConfigurationObjectSpecificationFactory(factoryConfig));
            } else {
                return new ObjectFactory();
            }
        } catch (IllegalReferenceException e) {
            throw new JiraManagerConfigurationException("Can't create specification factory from configuration.", e);
        } catch (SpecificationConfigurationException e) {
            throw new JiraManagerConfigurationException("Can't create specification factory from configuration.", e);
        }
    }

    /**
     * <p>Creates object from object factory using key passed.</p>
     *
     * @param factory      object factory to create instance
     * @param key          key to create instance
     * @param expectedType expected type of instance, exception thrown if not match
     * @param <T>          class we expect
     * @return created instance
     * @throws JiraManagerConfigurationException
     *          if instance can't be created for some reason
     */
    public static <T> T createObject(ObjectFactory factory, String key, Class<T> expectedType) {
        try {
            Object value = factory.createObject(key);
            if (!expectedType.isInstance(value)) {
                throw new JiraManagerConfigurationException("Object created with key [" + key
                        + "] supposed to have type " + expectedType.getName() + ", but have "
                        + value.getClass().getName() + ".");
            }
            return (T) value;
        } catch (InvalidClassSpecificationException e) {
            throw new JiraManagerConfigurationException("Unable to create new object with key [" + key + "].", e);
        }
    }
}
