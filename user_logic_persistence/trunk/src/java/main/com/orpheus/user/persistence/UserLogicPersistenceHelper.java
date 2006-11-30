/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import java.sql.Connection;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * A helper class which contains common operations performed by various parts of
 * the User Logic Persistence component. This helper class is public, because
 * the operations which it provides are used across all the component packages.
 * </p>
 * <p>
 * <b>Note:</b> This class should is not meant to be used by code outside the
 * User Logic Persistence component. Since only the User Logic Persistence
 * component uses this class, no argument validation is performed (except in
 * the {@link #assertArgumentNotNull(Object, String)} and
 * {@link #assertArgumentNotNullOrBlank(String, String)} methods, of course).
 * </p>
 * <p>
 * <b>Thread-safety:</b><br>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public final class UserLogicPersistenceHelper {

    /**
     * <p>
     * This constructor is private to prevent this class from being
     * instantiated.
     * </p>
     */
    private UserLogicPersistenceHelper() {
        // Empty constructor.
    }

    /**
     * <p>
     * Checks if the given argument is <code>null</code>. If it is, an
     * <code>IllegalArgumentException</code> is thrown with a message
     * encompassing the given description.
     * </p>
     *
     * @param argument the argument to check for <code>null</code>
     * @param description a description of the argument being checked
     */
    public static void assertArgumentNotNull(Object argument, String description) {
        if (argument == null) {
            throw new IllegalArgumentException("The " + description + " cannot be null");
        }
    }

    /**
     * <p>
     * Checks if the given string argument is <code>null</code> or a blank
     * string. If it is either <code>null</code> or a blank string, an
     * <code>IllegalArgumentException</code> is thrown with a message
     * encompassing the given description.
     * </p>
     *
     * @param argument the argument to check for <code>null</code> or a blank
     *        string
     * @param description a description of the argument being checked
     */
    public static void assertArgumentNotNullOrBlank(String argument, String description) {
        assertArgumentNotNull(argument, description);
        if (argument.trim().length() == 0) {
            throw new IllegalArgumentException("The " + description + " cannot be a blank string");
        }
    }

    /**
     * <p>
     * Creates an object using the Object Factory component, and returns it.
     * </p>
     * <p>
     * The "specNamespace" property is read from the given configuration
     * namespace. The value of this property refers to the
     * ConfigManagerSpecificationFactory namespace containing the object
     * specification. The <code>ObjectFactory</code> is instantiated with this
     * value. The specified object key property is then read, whose value is
     * passed to the <code>ObjectFactory</code> in order to create the object.
     * The type of the created object is checked to make sure it is the same as
     * the expected type. If it is not of the expected type, an
     * <code>ObjectInstantiationException</code> is thrown.
     * </p>
     *
     * @param namespace the Object Factory configuration namespace containing
     *        the object specification
     * @param objectKeyConfigProperty the name of the property representing the
     *        object key
     * @param expectedType the expected type of the object to create
     * @return the created object
     * @throws ObjectInstantiationException if creating the object fails
     */
    public static Object createObject(String namespace, String objectKeyConfigProperty, Class expectedType)
            throws ObjectInstantiationException {
        // Create the ObjectFactory using the "specNamespace" property value.
        ObjectFactory objFactory = null;
        try {
            String specNamespace = getConfigProperty(namespace, "specNamespace", true);
            objFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(specNamespace));
        } catch (IllegalReferenceException e) {
            throw new ObjectInstantiationException("Error instantiating ObjectFactory with namespace, " + namespace,
                                                   e);
        } catch (SpecificationConfigurationException e) {
            throw new ObjectInstantiationException("Error instantiating ObjectFactory with namespace, " + namespace,
                                                   e);
        }

        // Get the object key.
        String objectKey = getConfigProperty(namespace, objectKeyConfigProperty, true);

        // Create the object with the object key.
        Object obj = null;
        try {
            obj = objFactory.createObject(objectKey);
        } catch (InvalidClassSpecificationException e) {
            throw new ObjectInstantiationException("Could not create the " + objectKey + " object", e);
        }

        // Check that the object is of the expected type.
        if (!expectedType.isInstance(obj)) {
            throw new ObjectInstantiationException("Expected a " + expectedType.getName()
                    + " object to be created, but got a " + obj.getClass().getName() + " object instead");
        }

        return obj;
    }

    /**
     * <p>
     * Reads and returns the (trimmed) value of the specified property from the
     * given configuration namespace. If the <code>required</code> argument is
     * <code>true</code>, and the property does not exist in the
     * configuration, or if it has an empty value, an
     * <code>ObjectInstantiationException</code> is thrown. Otherwise,
     * <code>null</code> is returned.
     * </p>
     *
     * @param namespace the configuration namespace from which to read the
     *        property
     * @param property the property to read
     * @param required whether the property is required
     * @return the (trimmed) value of the property, or <code>null</code> if
     *         the property is optional and could not be found
     * @throws ObjectInstantiationException if reading the configuration
     *         property fails, or if the property does not exist in the
     *         namespace or has an empty value when the <code>required</code>
     *         argument is <code>true</code>
     */
    public static String getConfigProperty(String namespace, String property, boolean required)
            throws ObjectInstantiationException {
        // Get the property value.
        String value = null;
        try {
            value = ConfigManager.getInstance().getString(namespace, property);
        } catch (ConfigManagerException e) {
            throw new ObjectInstantiationException("Unable to read the " + property + " configuration property in the "
                                                   + namespace + " namespace", e);
        }

        if (value != null) {
            // Trim the value.
            value = value.trim();

            // If the property is required and the value is empty, throw an
            // exception.
            if (required && value.length() == 0) {
                throw new ObjectInstantiationException("The required " + property + " configuration property cannot "
                                                       + "have an empty value");
            }
        } else if (required) {
            // If the property was not found and it is required, thrown an
            // exception.
            throw new ObjectInstantiationException("The required " + property + " configuration property was not found "
                                                   + "in the " + namespace + " namespace");
        }

        return value;
    }

    /**
     * <p>
     * Creates a database connection with the specified connection name using
     * the given connection factory. If the specified connection name is
     * <code>null</code>, the default database connection is created.
     * </p>
     *
     * @param connFactory the connection factory to use to create the database
     *        connection
     * @param connName the name of the database connection to create
     * @return the database connection with the specified name, or the default
     *         database connection if the specified connection name is
     *         <code>null</code>
     * @throws PersistenceException if creating the database connection fails
     */
    public static Connection createDBConnection(DBConnectionFactory connFactory, String connName)
            throws PersistenceException {
        // Create the default DB connection.
        if (connName == null) {
            try {
                return connFactory.createConnection();
            } catch (DBConnectionException e) {
                throw new PersistenceException("Could not create default database connection", e);
            }
        }

        // Create the DB connection with the configured name.
        try {
            return connFactory.createConnection(connName);
        } catch (DBConnectionException e) {
            throw new PersistenceException("Could not create database connection with name, " + connName, e);
        }
    }

}
