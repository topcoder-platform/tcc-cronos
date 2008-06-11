/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * Util class for dependency extractor component.
 * </p>
 * <p>
 * <b>Thread safety</b> This is a helper class and each method is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Utils {
    /**
     * <p>
     * Message format string for logging.
     * </p>
     */
    private static final String MESSAGE_FORMAT = "{0} : {1}";

    /**
     * <p>
     * Represents the entrance prefix.
     * </p>
     */
    private static final String ENTRANCE = "Enter method";

    /**
     * <p>
     * Represents the exit prefix.
     * </p>
     */
    private static final String EXIT = "Exit method";

    /**
     * <p>
     * Represents the info logging prefix.
     * </p>
     */
    private static final String INFO = "Info";

    /**
     * <p>
     * Represents the result message prefix.
     * </p>
     */
    private static final String WARNING = "Warning";

    /**
     * <p>
     * Represents the exception caught message prefix.
     * </p>
     */
    private static final String EXCEPTION = "Exception caught";

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private Utils() {
        // does nothing
    }

    /**
     * <p>
     * Checks to see if object is null.
     * </p>
     *
     * @param object object value
     * @param objectName object name
     *
     * @throws IllegalArgumentException if object is null
     */
    public static void checkNull(Object object, String objectName) {
        if (object == null) {
            throw new IllegalArgumentException(objectName + " should not be null.");
        }
    }

    /**
     * <p>
     * Checks if string value is null or empty.
     * </p>
     *
     * @param stringValue string value
     * @param stringName string name
     *
     * @throws IllegalArgumentException if string value is null or empty
     */
    public static void checkStringNullOrEmpty(String stringValue, String stringName) {
        checkNull(stringValue, stringName);

        if (stringValue.trim().length() == 0) {
            throw new IllegalArgumentException(stringName + " should not be empty.");
        }
    }

    /**
     * <p>
     * Checks the collection.
     * </p>
     *
     * @param collection to be checked
     * @param collectionName collection name
     * @param allowedEmpty if it is allowed to be empty
     *
     * @throws IllegalArgumentException if collection is null or it contains null element or empty if it is not allowed
     *             to be empty
     */
    public static void checkCollection(Collection<?> collection, String collectionName, boolean allowedEmpty) {
        checkNull(collection, collectionName);

        if (!allowedEmpty && collection.isEmpty()) {
            throw new IllegalArgumentException(collectionName + " can not be empty.");
        }

        for (Object element : collection) {
            checkNull(element, "any element in " + collectionName);
        }
    }

    /**
     * <p>
     * Sees if the string is null or empty.
     * </p>
     *
     * @param stringValue string value to be checked
     * @return true if the string is null or empty; false if it is not.
     */
    public static boolean isStringNullOrEmpty(String stringValue) {
        return stringValue == null || stringValue.trim().length() == 0;
    }

    /**
     * <p>
     * Logs for entrance into the method with {@link Level#INFO}.
     * </p>
     *
     * @param logger used to log the message
     * @param signature The signature of the method to be logged.
     */
    public static void logEnter(Log logger, String signature) {
        doLog(logger, Level.INFO, null, ENTRANCE, signature);
    }

    /**
     * <p>
     * Logs for exit from every public methods with {@link Level#INFO}.
     * </p>
     *
     * @param logger used to log the message
     * @param signature The signature of the method to be logged.
     */
    public static void logExit(Log logger, String signature) {
        doLog(logger, Level.INFO, null, EXIT, signature);
    }

    /**
     * <p>
     * Logs for general message with {@link Level#INFO}.
     * </p>
     *
     * @param logger used to log the message
     * @param message The message to be logged.
     */
    public static void logInfo(Log logger, String message) {
        doLog(logger, Level.INFO, null, INFO, message);
    }

    /**
     * <p>
     * Logs for given exception and warning message with {@link Level#WARN}.
     * </p>
     *
     * @param logger used to log the message
     * @param message warning message
     * @param e The exception caught to be logged.
     */
    public static void logWarningException(Log logger, String message, Throwable e) {
        doLog(logger, Level.WARN, e, WARNING, message);
    }

    /**
     * <p>
     * Logs the given exception with {@link Level#ERROR}.
     * </p>
     *
     * @param <T> exception type
     * @param logger used to log the message
     * @param e The exception caught to be logged.
     * @return logged exception
     */
    public static <T extends Exception> T logException(Log logger, T e) {
        doLog(logger, Level.ERROR, e, EXCEPTION, e.getMessage());
        return e;
    }

    /**
     * <p>
     * Logs the given exception and message with given level.
     * </p>
     *
     * <p>
     * If <code>logger</code> is null, this method does nothing.
     * </p>
     *
     * @param logger used to log the message
     * @param level The log level.
     * @param throwable A possibly null <code>Throwable</code> to be logged.
     * @param message1 Message object which will be logged.
     * @param message2 Message object which will be logged.
     */
    private static void doLog(Log logger, Level level, Throwable throwable, Object message1, Object message2) {
        // This minimizes the overhead by allowing the formatting to happen as late as possible
        if (logger != null && logger.isEnabled(level)) {
            logger.log(level, throwable, MESSAGE_FORMAT, message1, message2);
        }
    }

    /**
     * <p>
     * Close input stream safely after reading is finished.
     * </p>
     *
     * @param is input stream
     */
    public static void closeInputStreamSafely(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                // silently ignore it
            }
        }
    }

    /**
     * <p>
     * Close output stream safely after writing is finished.
     * </p>
     *
     * @param os output stream
     */
    public static void closeOutputStreamSafely(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                // silently ignore it
            }
        }
    }

    /**
     * <p>
     * Gets a required String value under specified key.
     * </p>
     *
     * @param config object
     * @param key to be used to get String value
     * @return String value. not null and empty
     *
     * @throws ComponentDependencyConfigurationException if any error occurs or it is not a String or String value is
     *             null or empty
     */
    public static String getRequiredStringValue(ConfigurationObject config, String key)
        throws ComponentDependencyConfigurationException {
        return getConfigStringValue(config, key, true);
    }

    /**
     * <p>
     * Gets a optional String value under specified key.
     * </p>
     *
     * @param config object
     * @param key to be used to get String value
     * @return String value. could be null if it doesn't exist.
     *
     * @throws ComponentDependencyConfigurationException if any error occurs or it is not a String or String value is
     *             null or empty
     */
    public static String getOptionalStringValue(ConfigurationObject config, String key)
        throws ComponentDependencyConfigurationException {
        return getConfigStringValue(config, key, false);
    }

    /**
     * <p>
     * Gets a configuration String value under specified key.
     * </p>
     *
     * @param config object
     * @param key to be used to get String value
     * @param isRequired is it required
     * @return String value. might be null if it is optional
     *
     * @throws ComponentDependencyConfigurationException if any error occurs or it is not a String or String value is
     *             null or empty
     */
    private static String getConfigStringValue(ConfigurationObject config, String key, boolean isRequired)
        throws ComponentDependencyConfigurationException {
        try {
            if (!config.containsProperty(key)) {
                if (!isRequired) {
                    return null;
                } else {
                    throw new ComponentDependencyConfigurationException("property value for '" + key
                        + "' should be provided.");
                }
            }
            Object value = config.getPropertyValue(key);
            if (value instanceof String) {
                configurationCheckRequiredNonEmptyStringValue((String) value, key);
                return (String) value;
            } else {
                throw new ComponentDependencyConfigurationException("property value for '" + key
                    + "' is not a String value.");
            }
        } catch (ConfigurationAccessException e) {
            throw new ComponentDependencyConfigurationException("error occurs while getting the property value : "
                + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Checks to see if string value is not null and also non-empty value.
     * </p>
     *
     * @param strValue string value to be checked
     * @param propertyName property name to be used to construct detailed error message
     *
     * @throws ComponentDependencyConfigurationException if any validation fails
     */
    private static void configurationCheckRequiredNonEmptyStringValue(String strValue, String propertyName)
        throws ComponentDependencyConfigurationException {
        if (strValue == null) {
            throw new ComponentDependencyConfigurationException(propertyName + " is missing in configuration.");
        }

        if (strValue.trim().equals("")) {
            throw new ComponentDependencyConfigurationException(propertyName + " should not be empty value.");
        }
    }

    /**
     * <p>
     * Gets required configuration object value from configuration.
     * </p>
     *
     * @param config object
     * @param key to get configuration object
     * @return required configuration object, not null
     *
     * @throws ComponentDependencyConfigurationException if any error occurs or returned configuration object is null
     */
    public static ConfigurationObject getRequiredConfigurationObject(ConfigurationObject config, String key)
        throws ComponentDependencyConfigurationException {
        try {
            ConfigurationObject configObject = config.getChild(key);
            configurationCheckRequiredValue(configObject, key);
            return configObject;
        } catch (ConfigurationAccessException e) {
            throw new ComponentDependencyConfigurationException(
                "error occurs while retrieve the child configuration : " + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * Checks to see if value is not null.
     * </p>
     *
     * @param value to be checked
     * @param propertyName property name to be used to construct detailed error message
     *
     * @throws ComponentDependencyConfigurationException if value is null
     */
    private static void configurationCheckRequiredValue(Object value, String propertyName)
        throws ComponentDependencyConfigurationException {
        if (value == null) {
            throw new ComponentDependencyConfigurationException(propertyName + " is missing in configuration.");
        }
    }

    /**
     * Creates an object from the given object factory config key with the given key, and casts it to the desired type.
     * All values under those keys will be checked as deemed to be required.
     *
     * @param <T> type of the created object to be casted
     * @param clazz class of the desired type
     * @param config full configuration object
     * @param objectFactoryConfigKey key to get ConfigurationObject for object factory
     * @param key to get the key value to create object from object factory
     * @return the object created, casted to the desired type
     *
     * @throws ComponentDependencyConfigurationException if failed to create the object or the object is not of the
     *             given type
     */
    public static <T> T createObject(Class<T> clazz, ConfigurationObject config, String objectFactoryConfigKey,
        String key) throws ComponentDependencyConfigurationException {
        return createObject(clazz, getObjectFactory(config, objectFactoryConfigKey),
            getRequiredStringValue(config, key));
    }

    /**
     * Creates an object from the given object factory with the given key, and casts it to the desired type. Note all
     * arguments should be valid.
     *
     * @param <T> type of the created object to be casted
     * @param clazz class of the desired type
     * @param objectFactory ObjectFactory instance to be created from
     * @param objectKey key value used to create the object through object factory
     *
     * @return the object created, casted to the desired type
     *
     * @throws ComponentDependencyConfigurationException if failed to create the object or the object is not of the
     *             given type
     */
    private static <T> T createObject(Class<T> clazz, ObjectFactory objectFactory, String objectKey)
        throws ComponentDependencyConfigurationException {
        Object object;
        try {
            object = objectFactory.createObject(objectKey);
        } catch (InvalidClassSpecificationException e) {
            throw new ComponentDependencyConfigurationException("error occurred during creating object with key \""
                + objectKey + "\" from ObjectFactory : " + e.getMessage(), e);
        }
        if (clazz.isInstance(object)) {
            return clazz.cast(object);
        } else {
            throw new ComponentDependencyConfigurationException("object created by key \"" + objectKey
                + "\" is not of type " + clazz.getName());
        }
    }

    /**
     * Gets a object factory instance from configuration using configuration object under configuration key.
     *
     * @param config configuration object
     * @param objectFactoryConfigKey configuration key
     * @return created object factory
     *
     * @throws ComponentDependencyConfigurationException if any error occurs in the process
     */
    private static ObjectFactory getObjectFactory(ConfigurationObject config, String objectFactoryConfigKey)
        throws ComponentDependencyConfigurationException {
        try {
            return new ObjectFactory(new ConfigurationObjectSpecificationFactory(getRequiredConfigurationObject(config,
                objectFactoryConfigKey)));
        } catch (IllegalReferenceException e) {
            // thrown by ConfigurationObjectSpecificationFactory(..)
            throw new ComponentDependencyConfigurationException(
                "ConfigurationObjectSpecificationFactory illegal reference exception  : " + e.toString(), e);
        } catch (SpecificationConfigurationException e) {
            // thrown by ConfigurationObjectSpecificationFactory(..)
            throw new ComponentDependencyConfigurationException(
                "ConfigurationObjectSpecificationFactory specification configuration exception : " + e.toString(), e);
        }
    }
}
