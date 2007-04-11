/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;

import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * Helper methods used to simplify access to the object factory and configuration manager components.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

class ConfigHelpers {
    /**
     * Private constructor to prevent instantiation.
     */
    private ConfigHelpers() {
    }

    /**
     * A helper method that retrieves an ID generator and rethrows any exceptions as
     * <code>ConfigurationException</code>.
     *
     * @param name the name of the ID generator
     * @return the ID generator
     * @throws ConfigurationException if an error occurs while retrieving the ID generator
     */
    static IDGenerator createIDGenerator(String name) throws ConfigurationException {
        try {
            return IDGeneratorFactory.getIDGenerator(name);
        } catch (IDGenerationException ex) {
            throw new ConfigurationException("error creating ID generator: " + ex.getMessage(), ex);
        }
    }

    /**
     * Creates an object using the specified namespace and key.
     *
     * @param specNamespace the specification namespace
     * @param key the object key
     * @return the newly created object
     * @throws ConfigurationException if a problem occurs while creating the object
     */
    static Object createObject(String specNamespace, String key) throws ConfigurationException {
        try {
            return new ObjectFactory(new ConfigManagerSpecificationFactory(specNamespace)).createObject(key);
        } catch (SpecificationConfigurationException ex) {
            throw new ConfigurationException("invalid specification namespace '" + specNamespace + "'", ex);
        } catch (IllegalReferenceException ex) {
            throw new ConfigurationException("illegal reference '" + key + "'", ex);
        } catch (InvalidClassSpecificationException ex) {
            throw new ConfigurationException("invalid class specification for '" + key + "'", ex);
        }
    }

    /**
     * Returns the value of the specified property.
     *
     * @param namespace the config namespace
     * @param prop the property to retrieve
     * @return the value of the specified property
     * @throws ConfigurationException if the namespace or property does not exist
     */
    static String getRequiredProperty(String namespace, String prop) throws ConfigurationException {
        String value = getOptionalProperty(namespace, prop);

        if (value == null) {
            throw new ConfigurationException("property '" + prop + "' does not exist in namespace '"
                                             + namespace + "'");
        }

        return value;
    }

    /**
     * Returns the value of the specified property or <code>null</code> if no such property exists.
     *
     * @param namespace the config namespace
     * @param prop the property to retrieve
     * @return the value of the specified property or <code>null</code> if no such property exists
     * @throws ConfigurationException if the namespace does not exist
     */
    static String getOptionalProperty(String namespace, String prop) throws ConfigurationException {
        ConfigManager manager = ConfigManager.getInstance();

        try {
            return manager.getString(namespace, prop);
        } catch (UnknownNamespaceException ex) {
            throw new ConfigurationException("namespace '" + namespace + "' does not exist");
        }
    }
}
