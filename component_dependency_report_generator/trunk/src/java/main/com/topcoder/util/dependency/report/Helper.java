/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.report;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This is a helper class used to validate arguments and load configuration.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class Helper {

    /**
     * <p>
     * The semicolon used as separator.
     * </p>
     */
    public static final String SEPARATOR = ";";

    /**
     * <p>
     * The private constructor used to avoid creating instances.
     * </p>
     */
    private Helper() {
        // do nothing
    }

    /**
     * <p>
     * Checks if the given object value is null.
     * </p>
     *
     * @param <T> Generic type.
     * @param obj The object to check.
     * @param usage The usage of the object.
     *
     * @return The given object.
     *
     * @throws IllegalArgumentException If given object is null.
     */
    public static final < T > T checkNull(T obj, String usage) {
        ExceptionUtils.checkNull(obj, null, null, usage + " should not be null.");
        return obj;
    }

    /**
     * <p>
     * Checks if the given string is null or empty(trimmed).
     * </p>
     *
     * @param str The string to check.
     * @param usage The usage of the string.
     *
     * @return The given string.
     *
     * @throws IllegalArgumentException If given string is null or empty(trimmed).
     */
    public static final String checkNullOrEmpty(String str, String usage) {
        ExceptionUtils.checkNullOrEmpty(str, null, null, usage + " should not be null or empty(trimmed).");
        return str;
    }

    /**
     * <p>
     * Check the collection to be non-null, and does not contain null element.
     * </p>
     *
     * <p>
     * If the element is type of <code>String</code>, then any string element should not be empty.
     * </p>
     *
     * <p>
     * If given <code>allowEmpty</code> is false, then the collection should not be empty.
     * </p>
     *
     * <p>
     * Any duplicate element will be terminated.
     * </p>
     *
     * @param <T> Generic type.
     * @param collection The collection to check.
     * @param usage Represents the usage of the collection.
     * @param allowEmpty Indicates whether the collection can be empty.
     *
     * @return The shallow copy of given collection with only distinct elements.
     *
     * @throws IllegalArgumentException If the collection is null, or contains null element, or contains empty string,
     *         or if <code>allowEmpty</code> is false and the collection is empty.
     */
    @SuppressWarnings("unchecked")
    public static final < T > Collection < T > checkCollection(
        Collection < T > collection, String usage, boolean allowEmpty) {

        //Must not be null
        ExceptionUtils.checkNull(collection, null, null, usage + " should not be null.");

        //Check whether can be empty
        if (!allowEmpty && collection.size() == 0) {
            throw new IllegalArgumentException(usage + " should not be empty.");
        }

        //Use Set to terminate duplication. Use LinkedHashSet to preserve the order.
        Set < T > set = new LinkedHashSet();
        for (T obj : collection) {

            //Must not contain null element
            ExceptionUtils.checkNull(obj, null, null, usage + " should not contain null element.");

            //If the element is type of String, then it must no be empty
            if (obj instanceof String) {
                ExceptionUtils.checkNullOrEmpty((String) obj, null, null,
                    usage + " should not contain empty string.");
            }
            set.add(obj);
        }
        return set;
    }

    /**
     * <p>
     * Load boolean value from configuration. The value is expected to be either 'true' or 'false'(case sensitive).
     * </p>
     *
     * <p>
     * If the property is not defined, the given default value will be returned.
     * </p>
     *
     * @param configuration The configuration object.
     * @param propertyName The name of property to load its value.
     * @param defaultValue The default value to be used if the property is not defined.
     *
     * @return The boolean value. If property is missing, it will be default to <code>defaultValue</code>.
     *
     * @throws IllegalArgumentException If the given configuration object is null.
     *         Or if given property name is null or empty.
     * @throws DependencyReportConfigurationException If error occurs while accessing the configuration.
     *         Or if the property has multiple values.
     *         Or if the property is present but has null value.
     *         Or if the property is present but has empty(trimmed) value.
     *         Or if the property value is not type of <code>String</code>.
     *         Or if the property value is neither 'true' nor 'false'(case sensitive).
     *
     * @see #getStringProperty(ConfigurationObject, String, String)
     */
    public static final boolean getBooleanProperty(
        ConfigurationObject configuration, String propertyName, boolean defaultValue)
        throws DependencyReportConfigurationException {

        String property = getStringProperty(configuration, propertyName, String.valueOf(defaultValue));

        //Case sensitive
        if (Boolean.TRUE.toString().equals(property)) {
            return true;
        } else if (Boolean.FALSE.toString().equals(property)) {
            return false;
        }

        throw new DependencyReportConfigurationException("Property '"
            + propertyName + "' must have value either 'true' or 'false'(case sensitive). But is: " + property);
    }

    /**
     * <p>
     * Get <code>String</code> property value from <code>ConfigurationObject</code>.
     * </p>
     *
     * <p>
     * If the property is not defined, the given default value will be returned.
     * </p>
     *
     * @param co The <code>ConfigurationObject</code> to get property value from.
     * @param propertyName The name of the property.
     * @param defaultValue The default value to be used if the property is not defined.
     *
     * @return The <code>String</code> property value. May be null if optional.
     *
     * @throws IllegalArgumentException If the given configuration object is null.
     *         Or if given property name is null or empty.
     * @throws DependencyReportConfigurationException If error occurs while accessing the configuration.
     *         Or if the property has multiple values.
     *         Or if the property is present but has null value.
     *         Or if the property is present but has empty(trimmed) value.
     *         Or if the property value is not type of <code>String</code>.
     */
    public static final String getStringProperty(ConfigurationObject co, String propertyName, String defaultValue)
        throws DependencyReportConfigurationException {

        ExceptionUtils.checkNull(co, null, null, "The ConfigurationObject should not be null.");
        ExceptionUtils.checkNullOrEmpty(propertyName, null, null, "The property name should not be null or empty.");

        try {

            //Property is not present, return default value
            if (!co.containsProperty(propertyName)) {
                return defaultValue;
            }

            //Multiple values not allowed
            if (co.getPropertyValuesCount(propertyName) > 1) {
                throw new DependencyReportConfigurationException(
                    "Property '" + propertyName + "' should not have multiple values.");
            }

            //Property value must not be null
            Object value = co.getPropertyValue(propertyName);
            if (value == null) {
                throw new DependencyReportConfigurationException(
                        "Property '" + propertyName + "' is present and should not have null value.");
            }

            //Property value must be type of String
            if (!(value instanceof String)) {
                throw new DependencyReportConfigurationException(
                        "Property '" + propertyName + "' should have String value, but is: " + value);
            }

            String result = (String) value;

            //Property value must not be empty(trimmed)
            if (result.trim().length() == 0) {
                throw new DependencyReportConfigurationException(
                    propertyName + " is present and should not have empty(trimmed) value.");
            }

            return result;
        } catch (BaseException e) {
            throw e instanceof DependencyReportConfigurationException ? (DependencyReportConfigurationException) e
                : new DependencyReportConfigurationException("Error occurs while retrieving property value.", e);
        }
    }
}
