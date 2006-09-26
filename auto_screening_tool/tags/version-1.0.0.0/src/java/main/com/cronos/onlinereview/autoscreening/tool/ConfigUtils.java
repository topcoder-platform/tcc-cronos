/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * This class contains utility methods to retrieved property values from the
 * configuration manager. This class is package-visible so that all the methods
 * are solely used from this package.
 * @author urtks
 * @version 1.0
 */
final class ConfigUtils {
    /**
     * Private constructor to prevent this class be initialized.
     */
    private ConfigUtils() {
    }

    /**
     * Gets the property object of the specified property name from the given
     * configuration namespace.
     * @param namespace
     *            the configuration namespace
     * @param propertyName
     *            the property name
     * @param required
     *            whether the property object is required
     * @return the Property instance
     * @throws ConfigurationException
     *             if the namespace does not exist, or the propertyName does not
     *             exist.
     */
    static Property getPropertyObject(String namespace, String propertyName, boolean required)
        throws ConfigurationException {

        // try to get the property object
        Property property;
        try {
            property = ConfigManager.getInstance().getPropertyObject(namespace, propertyName);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("Configuration namespace [" + namespace
                + "] does not exist.", e);
        }

        // property should not be null if required.
        if (property == null && required) {
            throw new ConfigurationException("Configuration property [" + propertyName
                + "] under namespace [" + namespace + "] does not exist.");
        }

        return property;
    }

    /**
     * Gets the property object of the specified sub property name under the
     * given configuration property object.
     * @param property
     *            the configuration property object
     * @param subPropertyName
     *            the sub property name
     * @param required
     *            whether the property object is required
     * @return the Property instance
     * @throws ConfigurationException
     *             if the sub property name does not exist.
     */
    static Property getPropertyObject(Property property, String subPropertyName, boolean required)
        throws ConfigurationException {

        // try to get the sub property object
        Property subProperty = property.getProperty(subPropertyName);

        // sub property should not be null if required.
        if (subProperty == null && required) {
            throw new ConfigurationException("The sub property [" + subPropertyName
                + "] under property [" + property.getName() + "] does not exist.");
        }

        return subProperty;
    }

    /**
     * Gets the String value of the specified property from the given
     * configuration namespace.
     * @param namespace
     *            the configuration namespace
     * @param propertyName
     *            the property name
     * @param required
     *            whether the property value is required
     * @return A String that represents the property value
     * @throws ConfigurationException
     *             if the namespace does not exist, or the property name does
     *             not exist when required is true, or the value is empty
     *             (trimmed).
     */
    static String getStringValue(String namespace, String propertyName, boolean required)
        throws ConfigurationException {
        ConfigManager cm = ConfigManager.getInstance();

        // try to get the value
        String value;
        try {
            value = cm.getString(namespace, propertyName);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("Configuration namespace [" + namespace
                + "] does not exist.", e);
        }

        // validate the value
        if (value == null) {
            if (required) {
                throw new ConfigurationException("Configuration property [" + propertyName
                    + "] under namespace [" + namespace + "] does not exist.");
            }
        } else if (value.trim().length() == 0) {
            throw new ConfigurationException("The value of configuration property [" + propertyName
                + "] under namespace [" + namespace + "] is empty (trimmed).");
        }

        return value;
    }

    /**
     * Gets the String value of the specified sub property under the given
     * configuration property object.
     * @param property
     *            the configuration property object
     * @param subPropertyName
     *            the sub property name
     * @param required
     *            whether the sub property value is required
     * @return A String that represents the sub property value
     * @throws ConfigurationException
     *             if the sub property does not exist when required is true, or
     *             the value is empty (trimmed).
     */
    static String getStringValue(Property property, String subPropertyName, boolean required)
        throws ConfigurationException {

        // try to get the value
        String value = property.getValue(subPropertyName);

        // validate the value
        if (value == null) {
            if (required) {
                throw new ConfigurationException("Sub property [" + subPropertyName
                    + "] under property [" + property.getName() + "] does not exist.");
            }
        } else if (value.trim().length() == 0) {
            throw new ConfigurationException("The value of sub property [" + subPropertyName
                + "] under property [" + property.getName() + "] is empty (trimmed).");
        }

        return value;
    }

    /**
     * Gets the String values of the specified sub property under the given
     * configuration property object.
     * @param property
     *            the configuration property object
     * @param subPropertyName
     *            the sub property name
     * @return An array of String that represents the sub property values
     * @throws ConfigurationException
     *             if the sub property does not exist, or the values contain
     *             empty strings (trimmed).
     */
    static String[] getStringValues(Property property, String subPropertyName)
        throws ConfigurationException {

        // try to get the value
        String[] values = property.getValues(subPropertyName);

        // values should never be null
        if (values == null) {
            throw new ConfigurationException("Sub property [" + subPropertyName
                + "] under property [" + property.getName() + "] does not exist.");
        }

        // values should never contain empty string (trimmed)
        for (int i = 0; i < values.length; ++i) {
            if (values[i].trim().length() == 0) {
                throw new ConfigurationException("Values[" + i + "] of sub property ["
                    + subPropertyName + "] under property [" + property.getName()
                    + "] is empty (trimmed).");
            }
        }

        return values;
    }

    /**
     * Gets the Long value of the specified sub property under the given
     * configuration property object.
     * @param property
     *            the configuration property object
     * @param subPropertyName
     *            the sub property name
     * @param required
     *            whether the sub property value is required
     * @return A Long that represents the sub property value
     * @throws ConfigurationException
     *             if the sub property does not exist when required is true, or
     *             the value is empty (trimmed) or unable to be converted to
     *             Long.
     */
    static Long getLongValue(Property property, String subPropertyName, boolean required)
        throws ConfigurationException {

        // try to get the string value of the sub property
        String value = getStringValue(property, subPropertyName, required);

        // try to convert the string value to long value
        try {
            return value == null ? null : Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Unable to convert the value [" + value
                + "] of sub property [" + subPropertyName + "] under property ["
                + property.getName() + "] to Long.", e);
        }
    }
}
