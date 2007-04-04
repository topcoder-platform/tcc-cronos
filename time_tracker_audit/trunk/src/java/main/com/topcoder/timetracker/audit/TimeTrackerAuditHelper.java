/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;


/**
 * <p>
 * Defines utilities for this component. Since this class will be accessed via multi-packages, this class is declared
 * in public scope.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public final class TimeTrackerAuditHelper {
    /**
     * Private constructor to prevent this class be instantiated.
     */
    private TimeTrackerAuditHelper() {
        // empty
    }

    /**
     * <p>
     * Validates the value of a variable with type <code>Object</code>. The value cannot be <code>null</code>.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code>.
     */
    public static void validateNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a string variable. The value cannot be <code>null</code> or empty string.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code> or is empty string.
     */
    public static void validateString(String value, String name) {
        validateNotNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " cannot be empty string.");
        }
    }

    /**
     * <p>
     * Create an object factory using given namespace.
     * </p>
     *
     * @param namespace the given namespace to create the object factory.
     *
     * @return the object factory created.
     *
     * @throws AuditConfigurationException if any error happens when creating.
     */
    public static ObjectFactory createObjectFactory(String namespace) throws AuditConfigurationException {
        try {
            return new ObjectFactory(new ConfigManagerSpecificationFactory(namespace));
        } catch (SpecificationConfigurationException e) {
            throw new AuditConfigurationException("Can't create the ObjectFactory.", e);
        } catch (IllegalReferenceException e) {
            throw new AuditConfigurationException("Can't create the ObjectFactory.", e);
        }
    }

    /**
     * <p>
     * Create an object with the given objectFactory and classname.
     * </p>
     *
     * @param objectFactory the objectFactory to create the object.
     * @param key the key to fetch the object from objectFactory.
     * @param expected the expected type of the object to create.
     *
     * @return the created object.
     *
     * @throws AuditConfigurationException if it fails to create the object or the object is not of expected type.
     */
    public static Object createObject(ObjectFactory objectFactory, String key, Class expected)
        throws AuditConfigurationException {
        try {
            Object object = objectFactory.createObject(key);

            if (!expected.isInstance(object)) {
                throw new AuditConfigurationException("The object is not instanceof " + expected.getName());
            }

            return object;
        } catch (InvalidClassSpecificationException e) {
            throw new AuditConfigurationException("The " + expected.getName() + " instance "
                + "can't be created successfully.", e);
        }
    }

    /**
     * <p>
     * Get the string property value in <code>ConfigManager</code> with specified namespace and name.
     * </p>
     *
     * @param namespace the namespace of the config string property value .
     * @param name the name of the config string property value.
     * @param required whether the property value is required to get.
     *
     * @return the config string property value in <code>ConfigManager</code>.
     *
     * @throws AuditConfigurationException if the namespace doesn't exist, or the parameter doesn't exist if it is
     *         required to get, or the parameter value is an empty string.
     */
    public static String getStringPropertyValue(String namespace, String name, boolean required)
        throws AuditConfigurationException {
        try {
            String value = ConfigManager.getInstance().getString(namespace, name);

            if ((value == null)) {
                if (required) {
                    throw new AuditConfigurationException("The required parameter " + name + " in namespace "
                        + namespace + " doesn't exist.");
                }

                return null;
            }

            if (required && (value.trim().length() == 0)) {
                throw new AuditConfigurationException("The parameter value of " + name + " in namespace " + namespace
                    + " is an empty string.");
            }

            return value;
        } catch (UnknownNamespaceException une) {
            throw new AuditConfigurationException("The namespace with the name of " + namespace + " doesn't exist.",
                une);
        }
    }

    /**
     * <p>
     * Gets the content of stack trace of the exception.
     * </p>
     *
     * @param e the exception to get the content of statck trace.
     *
     * @return the content of stack trace of the exception.
     */
    public static String getExceptionStaceTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        String message = sw.toString();
        pw.close();

        return message;
    }
}
