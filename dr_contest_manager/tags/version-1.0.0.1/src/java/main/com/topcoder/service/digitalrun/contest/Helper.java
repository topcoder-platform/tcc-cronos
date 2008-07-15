/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import java.text.MessageFormat;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * The helper class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Helper {

    /**
     * <p>
     * The "unit_name" ENC entry.
     * </p>
     */
    public static final String UNIT_NAME = "unit_name";

    /**
     * <p>
     * The "config_file_name" ENC entry.
     * </p>
     */
    public static final String CONFIG_FILE_NAME = "config_file_name";

    /**
     * <p>
     * The "config_namespace" ENC entry.
     * </p>
     */
    public static final String CONFIG_NAMESPACE = "config_namespace";

    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * <p>
     * Get a child with given name.
     * </p>
     *
     * @param configuration <code>ConfigurationObject</code> to get child from
     * @param childName name of the child
     *
     * @return the child.
     *
     * @throws IllegalArgumentException if the given parameter is null or empty.
     * @throws BaseException if error occurs while accessing the configuration.
     *         Or if the child does not exist.
     */
    public static ConfigurationObject getChild(ConfigurationObject configuration, String childName)
        throws BaseException {

        ExceptionUtils.checkNull(configuration, null, null, "The ConfigurationObject should not be null.");
        ExceptionUtils.checkNullOrEmpty(childName, null, null, "The child name should not be null or empty.");

        ConfigurationObject child = configuration.getChild(childName);

        if (child == null) {
            throw new BaseException("The child '" + childName + "' does not exist under : "
                    + configuration.getName());
        }

        return child;
    }

    /**
     * <p>
     * Get configuration property value.
     * </p>
     *
     * @param co <code>ConfigurationObject</code> to get value from.
     * @param propertyName The name of property.
     * @param required Whether the property is required.
     *
     * @return The configuration property value. May be null if not required.
     *
     * @throws IllegalArgumentException If given configuration object is null, or if given property name is null
     *         or empty.
     * @throws BaseException If error occurs while accessing configuration or if the property value is invalid
     *         (e.g. has multiple values, value is not type of <code>String</code>, value is present but is null
     *         or empty).
     */
    public static String getConfigString(ConfigurationObject co, String propertyName, boolean required)
        throws BaseException {
        ExceptionUtils.checkNull(co, null, null, "ConfigurationObject should not be null.");
        ExceptionUtils.checkNullOrEmpty(propertyName, null, null, "Property name should not be null or empty.");

        int count = co.getPropertyValuesCount(propertyName);

        if (count < 1) {
            // Not required, return null
            if (!required) {
                return null;
            }
            // Required, missing
            throw new BaseException("Property '" + propertyName + "' is missing.");
        }

        // Multiple values not allowed
        if (count > 1) {
            throw new BaseException("Property '" + propertyName + "' should have at most one value.");
        }

        // Should be non-null and non-empty string if present
        Object result = co.getPropertyValue(propertyName);
        if (!(result instanceof String) || ((String) result).trim().length() == 0) {
            throw new BaseException(
                "Property '" + propertyName + "' is present and should have non-null, non-empty string value.");
        }

        return (String) result;
    }

    /**
     * <p>
     * Retrieve the value of ENC entry from JNDI context.
     * </p>
     *
     * @param sessionContext The session context.
     * @param name The name of the ENC entry to look up.
     *
     * @return The value of ENC entry.
     *
     * @throws ContestManagerConfigurationException If given session context is null.
     *             Or if the required ENC entry is missing, or if any ENC entry value is not <code>String</code> type,
     *             or if any ENC entry value is empty.
     */
    public static String lookupENC(SessionContext sessionContext, String name) {

        try {
            ExceptionUtils.checkNull(sessionContext, null, null, "SessionContext has not been injected.");

            String value = (String) sessionContext.lookup(name);

            ExceptionUtils.checkNullOrEmpty(value, null, null, MessageFormat.format(
                "The {0} ENC entry is required to be non-null and non-empty.", name));

            return value;
        } catch (ClassCastException e) {
            throw new ContestManagerConfigurationException(
                MessageFormat.format("The {0} ENC entry is not type of String.", name), e);
        } catch (IllegalArgumentException e) {
            // SessionContext.lookup() may also throws IllegalArgumentException if ENC does not exist
            // See http://java.sun.com/javaee/5/docs/api/javax/ejb/EJBContext.html#lookup(java.lang.String)
            throw new ContestManagerConfigurationException(
                MessageFormat.format("The {0} ENC entry is not properly configured.", name), e);
        }
    }

    /**
     * <p>
     * Obtain the <code>EntityManager</code> from the JNDI ENC via the session context.
     * </p>
     *
     * @param sessionContext The session context.
     * @param unitName The persistence unit name.
     *
     * @return The <code>EntityManager</code> obtained.
     *
     * @throws PersistenceException If given session context is null, or we can't obtain the manager instance.
     */
    public static EntityManager getEntityManager(SessionContext sessionContext, String unitName)
        throws PersistenceException {

        try {
            ExceptionUtils.checkNull(sessionContext, null, null, "SessionContext has not been injected.");

            EntityManager em = (EntityManager) sessionContext.lookup(unitName);

            if (em == null) {
                throw new PersistenceException("Null object bound on JNDI name: " + unitName);
            }

            return em;
        } catch (ClassCastException e) {
            throw new PersistenceException(MessageFormat.format(
                "Object bound on JNDI name {0} is not EntityManager", unitName), e);
        } catch (IllegalArgumentException e) {
            // SessionContext.lookup() may also throws IllegalArgumentException if ENC does not exist
            // See http://java.sun.com/javaee/5/docs/api/javax/ejb/EJBContext.html#lookup(java.lang.String)
            throw new PersistenceException(MessageFormat.format(
                "EntityManager is not bound on JNDI name {0}", unitName), e);
        }
    }

    /**
     * <p>
     * Logs the entrance of a method.
     * </p>
     *
     * @param logger The <code>Log</code> to perform logging.
     * @param clazzName The class name.
     * @param methodName The method to enter.
     */
    public static void logEnter(Log logger, String clazzName, String methodName) {
        doLog(logger, Level.INFO, null, "Enter into method [{0}#{1}]", clazzName, methodName);
    }

    /**
     * <p>
     * Logs the exit of a method.
     * </p>
     *
     * @param logger The <code>Log</code> to perform logging.
     * @param clazzName The class name.
     * @param methodName The method to exit.
     */
    public static void logExit(Log logger, String clazzName, String methodName) {
        doLog(logger, Level.INFO, null, "Exit from method [{0}#{1}]", clazzName, methodName);
    }

    /**
     * <p>
     * Logs the exception thrown.
     * </p>
     *
     * @param <T> The exception type.
     * @param logger The <code>Log</code> to perform logging.
     * @param exception The exception thrown.
     *
     * @return The logged exception.
     */
    public static < T extends Exception > T logException(Log logger, T exception) {
        if (exception != null) {
            doLog(logger, Level.ERROR, exception, "{0}", exception.getMessage());
        }

        return exception;
    }

    /**
     * <p>
     * Logs the given exception and message with given level and given format.
     * </p>
     *
     * @param logger The <code>Log</code> to perform logging.
     * @param level The log <code>Level</code>.
     * @param exception The <code>Exception</code> to be logged.
     * @param format The format pattern.
     * @param messages The messages to be logged.
     */
    private static void doLog(Log logger, Level level, Exception exception, String format, Object... messages) {
        // This minimizes the overhead by allowing the formatting to happen as late as possible
        // (certainly after a check to see if logging is enabled).
        // See Logging Wrapper 2.0 CS 4.3.4 for details
        if (logger != null && logger.isEnabled(level)) {
            logger.log(level, exception, format, messages);
        }
    }
}
