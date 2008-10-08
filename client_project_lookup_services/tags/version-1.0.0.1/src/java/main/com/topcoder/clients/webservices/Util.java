/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

import javax.ejb.SessionContext;

import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;

/**
 * <p>
 * Helper to be used inside the component.
 * </p>
 * <p>
 * This class is thread-safe since it's stateless.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Util {

    /**
     * <p>
     * Private constructor, prevents class from being instantiated.
     * </p>
     */
    private Util() {
    }

    /**
     * <p>
     * Checks passed object for being null and throws IllegalArgumentException if it is.
     * </p>
     *
     * @param data
     *            Object to check
     * @param name
     *            the object name to be used in the exception message
     * @throws IllegalArgumentException
     *             if passed data parameter is null
     */
    public static void checkNull(Object data, String name) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter [" + name + "] should not be null.");
        }
    }

    /**
     * <p>
     * Checks passed object for being null and throws IllegalArgumentException if it is.
     * </p>
     *
     * @param <T>
     *            The type of the passed object
     * @param data
     *            Object to check
     * @param name
     *            the object name to be used in the exception message
     * @return passed object
     * @throws IllegalArgumentException
     *             if passed data parameter is null
     */
    public static < T > T checkNullAndReturn(T data, String name) {
        checkNull(data, name);
        return data;
    }

    /**
     * <p>
     * Checks passed object for being null and throws and logs IllegalArgumentException if it is.
     * </p>
     *
     * @param log
     *            The <code>Log</code> to perform logging.
     * @param data
     *            Object to check
     * @param name
     *            the object name to be used in the exception message
     * @throws IllegalArgumentException
     *             if passed data parameter is null
     */
    public static void checkNullWithLog(Log log, Object data, String name) {
        if (data == null) {
            throw logException(log, new IllegalArgumentException("Parameter [" + name
                    + "] should not be null."));
        }
    }

    /**
     * <p>
     * Checks passed string for being null or empty and throws and logs IllegalArgumentException if it is.
     * </p>
     *
     * @param log
     *            The <code>Log</code> to perform logging.
     * @param data
     *            string to check
     * @param name
     *            the object name to be used in the exception message
     * @throws IllegalArgumentException
     *             if passed data parameter is null or empty string
     */
    public static void checkNullOrEmpty(Log log, String data, String name) {
        if (!isValidString(data)) {
            throw logException(log, new IllegalArgumentException("Parameter [" + name
                    + "] should not be null or empty."));
        }
    }

    /**
     * <p>
     * Checks passed number for being positive and throws and logs IllegalArgumentException if it is less or
     * equal to zero.
     * </p>
     *
     * @param log
     *            The <code>Log</code> to perform logging.
     * @param data
     *            number to check
     * @param name
     *            the number name to be used in the exception message
     * @throws IllegalArgumentException
     *             if passed number <= 0
     */
    public static void checkPositiveWithLog(Log log, long data, String name) {
        if (data <= 0) {
            throw logException(log, new IllegalArgumentException("Parameter [" + name
                    + "] should be positive but is [" + data + "]."));
        }
    }

    /**
     * <p>
     * Checks passed string for being null or empty. Returns true if string is not null and not empty, false
     * otherwise.
     * </p>
     *
     * @param data
     *            String to check
     * @return true if string is not null and not empty, false otherwise
     */
    public static boolean isValidString(String data) {
        return (data != null) && (data.trim().length() != 0);
    }

    /**
     * <p>
     * Creates Manager or UserMappingRetriever instance from the given file and namespace via ObjectFactory.
     * </p>
     *
     * @param fileResource
     *            the name of the configuration file
     * @param namespaceResource
     *            the namespace from which the configuration object will be created
     * @param tokenName
     *            the name of the property used to get the needed Manager instance via object factory
     * @return created Manager instance
     * @throws IOException
     *             if I/O error occurs while creating configuration object
     * @throws BaseException
     *             if any other error occurs while creating configuration object
     * @throws ClassCastException
     *             if passed tokenName represents not-string object in the configuration
     */
    public static Object createManagerFromConfiguration(String fileResource, String namespaceResource,
            String tokenName) throws IOException, BaseException {

        // Create ConfigurationFileManager from the given file
        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(fileResource);

        // Create ConfigurationObject using the given namespace (a wrapper above the needed configuration)
        ConfigurationObject configuration = configurationFileManager.getConfiguration(namespaceResource);

        // Extract child ConfigurationObject from the created configuration
        ConfigurationObject configurationObject = configuration.getChild(namespaceResource);
        if (configurationObject == null) {
            throw new BaseException("[" + namespaceResource + "] configuration couldn't be found.");
        }

        // Create ConfigurationObjectSpecificationFactory from the created configurationObject
        ConfigurationObjectSpecificationFactory configurationObjectSpecificationFactory =
            new ConfigurationObjectSpecificationFactory(configurationObject);

        // Create ObjectFactory from the created configurationObjectSpecificationFactory
        ObjectFactory objectFactory = new ObjectFactory(configurationObjectSpecificationFactory);

        // Get the needed token value from the created configurationObject
        String tokenValue = (String) configurationObject.getPropertyValue(tokenName);
        if (!isValidString(tokenValue)) {
            throw new BaseException("Parameter [" + tokenName + "] has invalid value: " + tokenValue);
        }

        // Return manager created via objectFactory with retrieved token value
        return objectFactory.createObject(tokenValue);
    }

    /**
     * <p>
     * Checks passed resource for being null and throws and logs IllegalStateException if it is.
     * </p>
     *
     * @param log
     *            The <code>Log</code> to perform logging.
     * @param resource
     *            resource object to be checked
     * @param name
     *            the object name to be used in the exception message
     * @throws IllegalStateException
     *             if passed object is null
     */
    public static void checkResource(Log log, Object resource, String name) {
        if (resource == null) {
            throw logException(log, new IllegalStateException("Resource [" + name + "] should not be null."));
        }
    }

    /**
     * <p>
     * Updates modifyDate and modifyUsername fields of the passed entity with current date and principal name
     * accordingly. If create flag is true updates createDate and createUsername in the same way.
     * </p>
     *
     * @param entity
     *            entity to be updated
     * @param sessionContext
     *            SessionContext from where to get caller principal
     * @param create
     *            true if entity is creating, false if operation performed is update or delete
     */
    public static void onEntityUpdate(AuditableEntity entity, SessionContext sessionContext, boolean create) {

        Date currentDate = new Date();
        Principal principal = sessionContext.getCallerPrincipal();
        entity.setModifyDate(currentDate);
        entity.setModifyUsername(principal.getName());

        if (create) {
            entity.setCreateDate(currentDate);
            entity.setCreateUsername(principal.getName());
        }
    }

    /**
     * <p>
     * Logs the entrance of a method. Returns entrance timestamp of this method.
     * </p>
     *
     * @param logger
     *            The <code>Log</code> to perform logging.
     * @param className
     *            The class name.
     * @param methodName
     *            The method to enter.
     * @param argumentName
     *            The name of the argument to log.
     * @param argumentInfo
     *            The information about method argument.
     * @param verbose
     *            The flag which indicates if entrance timestamp should be logged.
     * @return entrance timestamp of processed method
     */
    public static long logEnter(Log logger, String className, String methodName, String argumentName,
            String argumentInfo, boolean verbose) {
        long timestamp = System.currentTimeMillis();

        // Create log message
        StringBuilder format = new StringBuilder("Enter into method [{0}#{1}]");

        if (!verbose) {
            // Log only parameter name
            if (argumentName != null) {
                format.append(". Argument name: " + argumentName);
            }
        } else {
            // Log parameter value and entrance timestamp
            if (argumentInfo != null) {
                format.append(". Argument info: " + argumentInfo);
            }
            format.append(". Entrance timestamp: " + timestamp);
        }

        // Log it
        doLog(logger, Level.DEBUG, null, format.toString(), className, methodName);

        return timestamp;
    }

    /**
     * <p>
     * Logs the exit of a method.
     * </p>
     *
     * @param logger
     *            The <code>Log</code> to perform logging.
     * @param className
     *            The class name.
     * @param methodName
     *            The method to exit.
     * @param startTime
     *            The entrance timestamp of the finishing method.
     * @param returnValue
     *            The string representation of the return value of the method.
     * @param verbose
     *            The flag which indicates if the spent time and return value should be logged.
     */
    public static void logExit(Log logger, String className, String methodName, long startTime,
            String returnValue, boolean verbose) {

        // Create log message
        StringBuilder format = new StringBuilder("Exit from method [{0}#{1}]");
        if (verbose) {
            format.append(". Time spent: " + (System.currentTimeMillis() - startTime) + " ms");
            format.append(". Return value: " + returnValue);
        }

        // Log it
        doLog(logger, Level.DEBUG, null, format.toString(), className, methodName);
    }

    /**
     * <p>
     * Logs the exception thrown.
     * </p>
     *
     * @param <T>
     *            The exception type.
     * @param logger
     *            The <code>Log</code> to perform logging.
     * @param exception
     *            The exception thrown.
     * @return The logged exception.
     */
    public static < T extends Exception > T logException(Log logger, T exception) {
        if (exception != null) {
            doLog(logger, Level.WARN, exception, "{0}", exception.getMessage());
        }

        return exception;
    }

    /**
     * <p>
     * Returns explanation of the exception including exception class, method where exception occurred and
     * exception message.
     * </p>
     *
     * @param e
     *            exception to be described
     * @param methodName
     *            method where exception occurred
     * @return explanation string
     */
    public static String describeError(Exception e, String methodName) {
        return e.getClass().getSimpleName() + " occured while performing [" + methodName + "] operation: "
                + e.getMessage();
    }

    /**
     * <p>
     * Format the ClientStatus for log.
     * </p>
     *
     * @param status
     *            the ClientStatus to format
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return a String format of ClientStauts
     */
    public static String formatClientStatus(ClientStatus status, boolean verbose) {
        if (status == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");
        builder.append(" id=" + status.getId());
        builder.append(" name=" + status.getName());
        builder.append(" description=" + status.getDescription());
        builder.append(" isDeleted=" + status.isDeleted());
        builder.append("]");

        return builder.toString();
    }

    /**
     *<p>
     * Format the ProjectStatus for log.
     * </p>
     *
     * @param status
     *            the ProjectStatus to format
     * @param verbose
     *            passed parameter is formatted only if verbose is true. If it is false, null is returned
     * @return a String format of ProjectStatus
     */
    public static String formatProjectStatus(ProjectStatus status, boolean verbose) {
        if (status == null) {
            return "null";
        }
        if (!verbose) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        builder.append("[");
        builder.append(" id=" + status.getId());
        builder.append(" name=" + status.getName());
        builder.append(" description=" + status.getDescription());
        builder.append(" isDeleted=" + status.isDeleted());
        builder.append("]");

        return builder.toString();
    }

    /**
     * <p>
     * Logs the given exception and message with given level and given format.
     * </p>
     *
     * @param logger
     *            The <code>Log</code> to perform logging.
     * @param level
     *            The log <code>Level</code>.
     * @param exception
     *            The <code>Exception</code> to be logged.
     * @param format
     *            The format pattern.
     * @param messages
     *            The messages to be logged.
     */
    private static void doLog(Log logger, Level level, Exception exception, String format, Object... messages) {
        // isEnabled check minimizes the overhead by allowing the formatting to happen as late as possible
        if (logger != null && logger.isEnabled(level)) {
            logger.log(level, exception, format, messages);
        }
    }

}
