/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * Configuration utility class.
 *
 * @author enefem21
 * @version 1.0
 */
public final class ConfigUtil {

    /**
     * Private constructor to avoid an instantiation of this utility class.
     */
    private ConfigUtil() {
        // nothing to do
    }

    /**
     * Retrieves the required property from configuration manager.
     *
     * @param namespace
     *            the given namespace from where the property will be retrieved
     * @param propertyName
     *            the given property name that has to be retrieved
     *
     * @return the value of the propertyName
     *
     * @throws InvoiceConfigurationException
     *             if the namespace is unknown or the property is not available or an empty string
     */
    public static String getRequiredProperty(String namespace, String propertyName)
        throws InvoiceConfigurationException {
        return getProperty(namespace, propertyName, true);
    }

    /**
     * Retrieves the optional property from configuration manager.
     *
     * @param namespace
     *            the given namespace from where the property will be retrieved
     * @param propertyName
     *            the given property name that has to be retrieved
     *
     * @return the value of the propertyName
     *
     * @throws InvoiceConfigurationException
     *             if the namespace is unknown or the property is not available or an empty string
     */
    public static String getOptionalProperty(String namespace, String propertyName)
        throws InvoiceConfigurationException {
        return getProperty(namespace, propertyName, false);
    }

    /**
     * Retrieves the property from configuration manager.
     *
     * @param namespace
     *            the given namespace from where the property will be retrieved
     * @param propertyName
     *            the given property name that has to be retrieved
     * @param required
     *            whether the property s required or not
     *
     * @return the value of the propertyName
     *
     * @throws InvoiceConfigurationException
     *             if the namespace is unknown or the property is an empty string or the property is required but
     *             missing
     */
    private static String getProperty(String namespace, String propertyName, boolean required)
        throws InvoiceConfigurationException {
        try {
            String propertyValue = ConfigManager.getInstance().getString(namespace, propertyName);

            if (required && ArgumentCheckUtil.isNull(propertyValue)) {
                throw new InvoiceConfigurationException(propertyName
                    + " is a required configuration property but missing");
            }

            if (ArgumentCheckUtil.isEmptyString(propertyValue)) {
                throw new InvoiceConfigurationException(propertyName + " should not be an empty string");
            }

            return propertyValue;
        } catch (UnknownNamespaceException e) {
            throw new InvoiceConfigurationException("There is no namespace [" + namespace + "]", e);
        }
    }

    /**
     * Creates object factory from given namespace.
     *
     * @param objectFactoryNamespace
     *            the given namespace of the object factory
     *
     * @return the object factory
     *
     * @throws InvoiceConfigurationException
     *             if the namespace is not available or is not a valid namespace for object factory
     */
    public static ObjectFactory createObjectFactory(String objectFactoryNamespace)
        throws InvoiceConfigurationException {
        try {
            return new ObjectFactory(new ConfigManagerSpecificationFactory(objectFactoryNamespace));

        } catch (SpecificationConfigurationException e) {
            throw new InvoiceConfigurationException("Configuration error when creating the object factory", e);
        } catch (IllegalReferenceException e) {
            throw new InvoiceConfigurationException("Configuration error when creating the object factory", e);
        }
    }

    /**
     * Creates object from object factory and makes sure that the object is what is intended.
     *
     * @param objectFactory
     *            the given object factory from where the object will created from
     * @param namespace
     *            the given namespace from where the property will be retrieved
     * @param propertyName
     *            the given property name that has to be retrieved
     * @param intendedClass
     *            the intended class
     *
     * @return the object
     *
     * @throws InvoiceConfigurationException
     *             if the property retrieval is failed, the object is not what is intended or there is no entry
     *             with objectKey in the object factory
     */
    public static Object createObject(ObjectFactory objectFactory, String namespace, String propertyName,
        Class intendedClass) throws InvoiceConfigurationException {
        String objectKey = getRequiredProperty(namespace, propertyName);

        try {
            Object temp = objectFactory.createObject(objectKey);

            if (intendedClass.isInstance(temp)) {
                return temp;
            } else {
                throw new InvoiceConfigurationException("The object created with key [" + objectKey
                    + "] is not the right class " + intendedClass.getName());
            }
        } catch (InvalidClassSpecificationException e) {
            throw new InvoiceConfigurationException("There is no entry in object factory for key [" + objectKey
                + "]", e);
        }
    }
}
