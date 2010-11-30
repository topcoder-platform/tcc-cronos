/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.SpecificationFactoryException;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;

/**
 * <p>
 * Helper class for the component. It provides useful common methods for all the classes in this component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public final class Helper {
    /**
     * <p>
     * Represents the dot.
     * </p>
     */
    public static final String DOT = ".";

    /**
     * <p>
     * Represents the comma.
     * </p>
     */
    public static final String COMMA = ", ";

    /**
     * <p>
     * Represents the property key 'loggerName'.
     * </p>
     */
    private static final String KEY_LOGGER_NAME = "loggerName";

    /**
     * <p>
     * Represents the child key 'objectFactoryConfig'.
     * </p>
     */
    private static final String KEY_OF_CONFIG = "objectFactoryConfig";

    /**
     * <p>
     * Represents the entrance message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the method signature.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_ENTRANCE = "Entering method [%1$s].";

    /**
     * <p>
     * Represents the exit message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the method signature.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_EXIT = "Exiting method [%1$s], time spent in the method: %2$s milliseconds.";

    /**
     * <p>
     * Represents the error message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the method signature.</li>
     * <li>the error message.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_ERROR = "Error in method [%1$s], details: %2$s";

    /**
     * <p>
     * Prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if the value of the variable is <code>null</code>.
     */
    public static void checkNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException("'" + name + "' should not be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value should be positive.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if value is not positive.
     */
    public static void checkPositive(long value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException("'" + name + "' should be positive.");
        }
    }

    /**
     * <p>
     * Validates the value of a list. The value can not be <code>null</code> or contains <code>null</code>.
     * </p>
     *
     * @param <T>
     *            the element type.
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if value is <code>null</code> or contains <code>null</code>.
     */
    public static <T> void checkList(List<T> value, String name) {
        Helper.checkNull(value, name);

        for (T obj : value) {
            if (obj == null) {
                throw new IllegalArgumentException("'" + name + "' should not contain null element.");
            }
        }
    }

    /**
     * <p>
     * Checks state of object.
     * </p>
     *
     * @param isInvalid
     *            the state of object.
     * @param message
     *            the error message.
     *
     * @throws IllegalStateException
     *             if isInvalid is <code>true</code>.
     */
    public static void checkState(boolean isInvalid, String message) {
        if (isInvalid) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * <p>
     * Gets a Log instance with the configuration object.
     * </p>
     *
     * @param config
     *            the configuration object.
     *
     *@return the Log instance or <code>null</code> if the logger name is not specified.
     *
     * @throws ReliabilityCalculatorConfigurationException
     *             if 'loggerName' is not a string, an empty string or some error occurred.
     */
    public static Log getLog(ConfigurationObject config) {
        String loggerName = getProperty(config, KEY_LOGGER_NAME, false);

        return (loggerName == null) ? null : LogManager.getLog(loggerName);
    }

    /**
     * <p>
     * Gets a child ConfigurationObject from given configuration object.
     * </p>
     *
     * @param config
     *            the given configuration object.
     * @param key
     *            the child name.
     *
     * @return the retrieved child ConfigurationObject.
     *
     * @throws ReliabilityCalculatorConfigurationException
     *             if the child ConfigurationObject is missing or some error occurred.
     */
    public static ConfigurationObject getChildConfig(ConfigurationObject config, String key) {
        try {
            ConfigurationObject child = config.getChild(key);

            if (child == null) {
                throw new ReliabilityCalculatorConfigurationException("The child '" + key + "' of '"
                    + config.getName() + "' is required.");
            }

            return child;
        } catch (ConfigurationAccessException e) {
            throw new ReliabilityCalculatorConfigurationException(
                "An error occurred while accessing the configuration.", e);
        }
    }

    /**
     * <p>
     * Gets a property value from given configuration object.
     * </p>
     *
     * @param config
     *            the given configuration object.
     * @param key
     *            the property key.
     * @param required
     *            the flag indicates whether the property is required.
     *
     * @return the retrieved property value or <code>null</code> if the optional property is not present.
     *
     * @throws ReliabilityCalculatorConfigurationException
     *             if the property is missing when required is <code>true</code>, not a string, an empty string or
     *             some error occurred.
     */
    public static String getProperty(ConfigurationObject config, String key, boolean required) {
        try {
            if (!config.containsProperty(key)) {
                if (required) {
                    throw new ReliabilityCalculatorConfigurationException("The property '" + key + "' is required.");
                }

                // Return the default value
                return null;
            }

            Object valueObj = config.getPropertyValue(key);

            if (!(valueObj instanceof String)) {
                throw new ReliabilityCalculatorConfigurationException("The property '" + key
                    + "' should be a String.");
            }

            String valueStr = ((String) valueObj).trim();

            if (valueStr.length() == 0) {
                // The value is empty
                throw new ReliabilityCalculatorConfigurationException("The property '" + key + "' can not be empty.");
            }

            return valueStr;
        } catch (ConfigurationAccessException e) {
            throw new ReliabilityCalculatorConfigurationException(
                "An error occurred while accessing the configuration.", e);
        }
    }

    /**
     * <p>
     * Gets property values from given configuration object.
     * </p>
     *
     * @param config
     *            the given configuration object.
     * @param key
     *            the property key.
     *
     * @return the retrieved property values.
     *
     * @throws ReliabilityCalculatorConfigurationException
     *             if the property is missing, not a String[], empty or some error occurred.
     */
    public static String[] getPropertyValues(ConfigurationObject config, String key) {
        try {
            if (!config.containsProperty(key)) {
                throw new ReliabilityCalculatorConfigurationException("The property '" + key + "' is required.");
            }

            Object[] valuesObj = config.getPropertyValues(key);
            int valuesSize = valuesObj.length;

            if (valuesSize == 0) {
                // The value is empty
                throw new ReliabilityCalculatorConfigurationException("The property '" + key + "' can not be empty.");
            }

            String[] valuesStr = new String[valuesSize];

            for (int i = 0; i < valuesSize; i++) {
                Object value = valuesObj[i];
                if (!(value instanceof String)) {
                    throw new ReliabilityCalculatorConfigurationException("The property '" + key
                        + "' should be a String[].");
                }

                valuesStr[i] = (String) value;
            }

            return valuesStr;
        } catch (ConfigurationAccessException e) {
            throw new ReliabilityCalculatorConfigurationException(
                "An error occurred while accessing the configuration.", e);
        }
    }

    /**
     * <p>
     * Gets property values from given configuration object.
     * </p>
     *
     * @param config
     *            the given configuration object.
     * @param key
     *            the property key.
     *
     * @return the retrieved property values.
     *
     * @throws ReliabilityCalculatorConfigurationException
     *             if the property is missing, not a long[] containing positive values, empty or some error occurred.
     */
    public static long[] getPropertyPositiveValues(ConfigurationObject config, String key) {
        String[] valuesStr = getPropertyValues(config, key);

        int valuesSize = valuesStr.length;
        long[] values = new long[valuesSize];
        for (int i = 0; i < valuesSize; i++) {
            String valueStr = valuesStr[i];
            try {
                // Parse as a long
                values[i] = Long.parseLong(valueStr);

                if (values[i] <= 0) {
                    throw new ReliabilityCalculatorConfigurationException("The property '" + key
                        + "' should contain positive long only.");
                }
            } catch (NumberFormatException e) {
                throw new ReliabilityCalculatorConfigurationException("The property '" + key
                    + "' should contain positive long only.", e);
            }
        }

        return values;
    }

    /**
     * <p>
     * Creates an instance of ObjectFactory.
     * </p>
     *
     * @param config
     *            the configuration object.
     *
     * @return the created ObjectFactory instance.
     */
    public static ObjectFactory getObjectFactory(ConfigurationObject config) {
        try {
            // Create object factory
            return new ObjectFactory(new ConfigurationObjectSpecificationFactory(Helper.getChildConfig(config,
                KEY_OF_CONFIG)));
        } catch (SpecificationFactoryException e) {
            throw new ReliabilityCalculatorConfigurationException(
                "Failed to create an instance of ConfigurationObjectSpecificationFactory.", e);
        }
    }

    /**
     * <p>
     * Creates an object with given key.
     * </p>
     *
     * @param <T>
     *            the object type.
     * @param targetType
     *            the target type.
     * @param objectFactory
     *            the <code>ObjectFactory</code> instance which is used to create object.
     * @param config
     *            the configuration object.
     * @param objKey
     *            the key used to retrieve the object key.
     * @param configKey
     *            the key used to retrieve configuration object for the created object.
     *
     * @return the created object.
     *
     * @throws ReliabilityCalculatorConfigurationException
     *             if any error occurs when creating object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Configurable> T createObject(Class<?> targetType, ObjectFactory objectFactory,
        ConfigurationObject config, String objKey, String configKey) {
        T result = (T) createObject(targetType, objectFactory, config, objKey);

        // Configure the created object
        result.configure(Helper.getChildConfig(config, configKey));

        return result;
    }

    /**
     * <p>
     * Creates an object with given key.
     * </p>
     *
     * @param <T>
     *            the object type.
     * @param targetType
     *            the target type.
     * @param objectFactory
     *            the <code>ObjectFactory</code> instance which is used to create object.
     * @param config
     *            the configuration object.
     * @param objKey
     *            the key used to retrieve the object key.
     *
     * @return the created object.
     *
     * @throws ReliabilityCalculatorConfigurationException
     *             if any error occurs when creating object.
     */
    @SuppressWarnings("unchecked")
    public static <T> T createObject(Class<?> targetType, ObjectFactory objectFactory, ConfigurationObject config,
        String objKey) {
        String key = Helper.getProperty(config, objKey, true);

        try {
            Object obj = objectFactory.createObject(key);

            if (!targetType.isAssignableFrom(obj.getClass())) {
                throw new ReliabilityCalculatorConfigurationException("The created object is not of correct type.");
            }

            return (T) obj;
        } catch (InvalidClassSpecificationException e) {
            throw new ReliabilityCalculatorConfigurationException(
                "An error occurs when creating object with the given key '" + key + "'.", e);
        }
    }

    /**
     * <p>
     * Logs for entrance into public method and parameters at <code>DEBUG</code> level.
     * </p>
     *
     * <p>
     * <em>NOTE:</em> Logging is NOT performed if log is <code>null</code>.
     * </p>
     *
     * @param log
     *            the logger.
     * @param signature
     *            the signature of the method to log.
     * @param paramNames
     *            the names of parameters to log.
     * @param paramValues
     *            the values of parameters to log.
     */
    public static void logEntrance(Log log, String signature, String[] paramNames, Object[] paramValues) {
        if (log == null) {
            // No logging
            return;
        }

        log.log(Level.DEBUG, String.format(MESSAGE_ENTRANCE, signature));

        if (paramNames != null) {
            // Log parameters
            StringBuilder sb = new StringBuilder("Input parameters[");
            int paramNamesLen = paramNames.length;
            for (int i = 0; i < paramNamesLen; i++) {
                if (i != 0) {
                    // Append a comma
                    sb.append(COMMA);
                }
                sb.append(paramNames[i]).append(":").append(paramValues[i]);
            }
            sb.append("]");

            log.log(Level.DEBUG, sb.toString());
        }
    }

    /**
     * <p>
     * Logs for exit from public method and return value at <code>DEBUG</code> level.
     * </p>
     *
     * <p>
     * <em>NOTE:</em> Logging is NOT performed if log is <code>null</code>.
     * </p>
     *
     * @param log
     *            the logger.
     * @param signature
     *            the signature of the method to log.
     * @param value
     *            the return value to log.
     * @param enterTimestamp
     *            the timestamp while entering the method.
     */
    public static void logExit(Log log, String signature, Object[] value, Date enterTimestamp) {
        if (log == null) {
            // No logging
            return;
        }

        log.log(Level.DEBUG, String.format(MESSAGE_EXIT, signature, System.currentTimeMillis()
            - enterTimestamp.getTime()));

        if (value != null) {
            // Log return value
            log.log(Level.DEBUG, "Output parameter: " + value[0]);
        }
    }

    /**
     * <p>
     * Logs the given exception and message at <code>ERROR</code> level.
     * </p>
     *
     * <p>
     * <em>NOTE:</em> Logging is NOT performed if log is <code>null</code>.
     * </p>
     *
     * @param <T>
     *            the exception type.
     * @param log
     *            the logger.
     * @param signature
     *            the signature of the method to log.
     * @param e
     *            the exception to log.
     * @param message
     *            the message to log.
     *
     * @return the passed in exception.
     */
    public static <T extends Throwable> T logException(Log log, String signature, T e, String message) {
        if (log != null) {
            // Log exception at ERROR level
            log.log(Level.ERROR, String.format(MESSAGE_ERROR, signature, message) + getStackTrace(e));

            if (e instanceof BaseRuntimeException) {
                ((BaseRuntimeException) e).setLogged(true);
            } else if (e instanceof BaseCriticalException) {
                ((BaseCriticalException) e).setLogged(true);
            }
        }

        return e;
    }

    /**
     * <p>
     * Returns the exception stack trace string.
     * </p>
     *
     * @param cause
     *            the exception to be recorded.
     *
     * @return the exception stack trace string.
     */
    private static String getStackTrace(Throwable cause) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);

        // Print a new line
        ps.println();
        cause.printStackTrace(ps);

        return out.toString();
    }
}
