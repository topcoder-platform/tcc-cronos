/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import java.util.Date;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This helper class contains the methods for this component to check the arguments
 * , get property value from Config Manager component, and create <code>ObjectFactory</code>.
 * </p>
 *
 * @author liuliquan
 * @version 3.1
 */
public final class Helper {

    /**
     * <p>
     * The max allowed length of a string in Informix DB(VARCHAR type) for this component.
     * </p>
     */
    private static final int MAX_LENGTH = 64;

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private Helper() {
        // does nothing
    }

    /**
     * <p>
     * Validates whether the given Object is not null.
     * If the given Object is null, <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @param obj The object to check
     * @param usage The usage what the object is intent to represent
     *
     * @throws IllegalArgumentException If the given Object(obj) is null
     */
    public static void validateNotNull(Object obj, String usage) {
        if (obj == null) {
            throw new IllegalArgumentException(usage + " should not be null.");
        }
    }

    /**
     * <p>
     * Validates whether the given <code>String</code> is not null and not empty(trimmed).
     * </p>
     *
     * @param str the String to check.
     * @param name the name what the String is intent to represent.
     *
     * @throws IllegalArgumentException If the given string is null or empty string (trimmed).
     */
    public static void validateString(String str, String name) {
        validateNotNull(str, name);

        if (str.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty (trimmed).");
        }
    }

    /**
     * <p>
     * Validates whether the given <code>String</code> is not null and not empty(trimmed), and whether the length
     * is not greater than 64.
     * </p>
     *
     * @param str the <code>String</code> to check.
     * @param usage the usage of the <code>String</code>.
     *
     * @throws IllegalArgumentException If the given <code>String</code> is null or empty string (trimmed), or with
     *         length greater than 64.
     */
    public static void validateStringWithMaxLength(String str, String usage) {
        validateString(str, usage);

        if (str.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(usage + " should not be with length greater than " + MAX_LENGTH);
        }
    }

    /**
     * <p>
     * Check whether the given <code>String</code> is not null and not empty(trimmed), and whether the length
     * is not greater than 64.
     * </p>
     *
     * @param str the String to check.
     *
     * @return false If the given string is null or empty string (trimmed), or with length greater than 64.
     *         true otherwise.
     */
    public static boolean checkStringWithMaxLength(String str) {
        try {
            validateStringWithMaxLength(str, "String to check");
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Check whether the given id is positive.
     * </p>
     *
     * @param id The id to validate.
     *
     * @return true If the given id is positive; false otherwise.
     */
    public static boolean checkPositive(long id) {
        if (id <= 0) {
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Validate the given id is positive.
     * </p>
     *
     * @param id The id to validate.
     * @param name What the id represents.
     *
     * @throws IllegalArgumentException If the given id is not positive.
     */
    public static void validatePositive(long id, String name) {
        if (!checkPositive(id)) {
            throw new IllegalArgumentException(name + " should be positive, but is '" + id + "'.");
        }
    }

    /**
     * <p>
     * Validate the given id is positive.
     * </p>
     *
     * @param id The id to validate.
     * @param name What the id represents.
     *
     * @throws IllegalArgumentException If the given id is not positive.
     */
    public static void validatePositiveOrZero(long id, String name) {
        if (id < 0) {
            throw new IllegalArgumentException(name + " should not be negative, but is '" + id + "'.");
        }
    }

    /**
     * <p>
     * Validate the given ids array is not null and contains positive values.
     * </p>
     *
     * @param ids The ids array to be validated.
     * @param usage The usage of ids array.
     *
     * @return True if the given ids array is not empty; False otherwise.
     *
     * @throws IllegalArgumentException If given ids array is null or contains non-positive values.
     */
    public static boolean validateIdsArray(long[] ids, String usage) {
        validateNotNull(ids, usage);
        for (int i = 0; i < ids.length; i++) {
            validatePositive(ids[i], usage);
        }
        return ids.length > 0;
    }

    /**
     * <p>
     * Validate recent days.
     * </p>
     *
     * @param recentDays The recent days to validate.
     *
     * @throws IllegalArgumentException If recentDays is not positive and not -1.
     */
    public static void validateRecentDays(int recentDays) {
        if (recentDays != -1 && !checkPositive(recentDays)) {
            throw new IllegalArgumentException("The recent days must be positive or equal -1, but is '"
                    + recentDays + "'.");
        }
    }

    /**
     * <p>
     * Return the value of the property in the given namespace.
     * </p>
     *
     * <p>
     * If the given property is missing or with an empty(trimmed) value, then it will cause an
     * <code>CommonManagerConfigurationException</code>.
     * </p>
     *
     * @param namespace the namespace to get the property value from.
     * @param propertyName the name of property
     *
     * @return the value of the property, will never be null or empty(trimmed).
     *
     * @throws IllegalArgumentException if the given parameter is null or empty(trimmed).
     * @throws CommonManagerConfigurationException if fail to load the config values.
     */
    public static String getPropertyValue(String namespace, String propertyName)
        throws CommonManagerConfigurationException {
        validateString(namespace, "namespace");
        validateString(propertyName, "propertyName");
        try {
            String property = ConfigManager.getInstance().getString(namespace, propertyName);

            // Property is missed
            if (property == null) {
                throw new CommonManagerConfigurationException("Missed property '" + propertyName + "'.");
            }

            // Empty property value is not allowed
            if ((property.trim().length() == 0)) {
                throw new CommonManagerConfigurationException(
                    "The value for property '" + propertyName + "' is empty.");
            }

            return property;
        } catch (UnknownNamespaceException e) {
            throw new CommonManagerConfigurationException(
                "The namespace '" + namespace + "' is unknown.", e);
        }
    }

    /**
     * <p>
     * Instantiate an <code>ObjectFactory</code>.
     * </p>
     *
     * @param namespace The namespace which contains the <em>objectFactoryNS</em> property.
     * @param objectFactoryNS The name of the property which gives the namespace of
     *        <code>ConfigManagerSpecificationFactory</code>.
     *
     * @return instance of <code>ObjectFactory</code> created.
     *
     * @throws IllegalArgumentException if the given parameter is null or empty(trimmed).
     * @throws CommonManagerConfigurationException If fail to load the config values or error occurs
     *         while creating <code>ObjectFactory</code>.
     */
    public static ObjectFactory getObjectFactory(String namespace, String objectFactoryNS)
        throws CommonManagerConfigurationException {
        String specNamespace = null;
        try {
            specNamespace = getPropertyValue(namespace, objectFactoryNS);
            ConfigManagerSpecificationFactory cmsf = new ConfigManagerSpecificationFactory(specNamespace);
            return new ObjectFactory(cmsf);
        } catch (SpecificationConfigurationException e) {
            throw new CommonManagerConfigurationException(
                    "The namespace '" + specNamespace + "' of ObjectFactory contains invalid configuration.", e);
        } catch (IllegalReferenceException e) {
            throw new CommonManagerConfigurationException(
                    "The namespace '" + specNamespace + "' of ObjectFactory contains invalid configuration.", e);
        }
    }

    /**
     * <p>
     * Validate given <code>PaymentTerm</code>.
     * </p>
     *
     * <p>
     *  <strong>Usage:</strong>
     *  <ul>
     *   <li>For <code>PaymentTerm</code>s to be created through <code>SimpleCommonManager</code>, the id and the
     *   modification user does not need be validated.</li>
     *   <li>For <code>PaymentTerm</code>s to be created through <code>DatabasePaymentTermDAO</code>, the
     *   modification user does need be validated, but the id does not need be validated.</li>
     *   <li>For <code>PaymentTerm</code>s to be updated through <code>SimpleCommonManager</code> or
     *   <code>DatabasePaymentTermDAO</code>, the id and the modification user does need be validated.</li>
     *  </ul>
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  <ul>
     *   <li>The given <code>PaymentTerm</code> must be not null;</li>
     *   <li>Its term must be positive.</li>
     *   <li>Its description must be non-null, non-empty and with length &lt;= 64.</li>
     *   <li>Its creation user must be non-null, non-empty and with length &lt;= 64.</li>
     *   <li>Its id must be positive if <em>validateId</em> is true; Otherwise its id will not be validated.</li>
     *   <li>Its modification user must be non-null, non-empty and with length &lt;= 64 if
     *   <em>validateModificationUser</em> is true; Otherwise its modification user will not be validated.</li>
     *  </ul>
     * </p>
     *
     * @param paymentTerm The <code>PaymentTerm</code> to validate.
     * @param usage The usage of the given <code>PaymentTerm</code>.
     * @param validateId Indicate whether to validate the id of given <code>PaymentTerm</code>.
     * @param validateModificationUser Indicate whether to validate the modification user of given
     *                     <code>PaymentTerm</code>.
     *
     * @throws IllegalArgumentException if the given <code>PaymentTerm</code> is invalid.
     */
    public static void validatePaymentTerm(PaymentTerm paymentTerm, String usage,
            boolean validateId, boolean validateModificationUser) {
        validateNotNull(paymentTerm, usage);
        validatePositiveOrZero(paymentTerm.getTerm(), "The term of " + usage);
        if (validateId) {
            validatePositive(paymentTerm.getId(), "The id of " + usage);
        }

        if (validateModificationUser) {
            validateStringWithMaxLength(paymentTerm.getModificationUser(), "The modification user of " + usage);
        }
        validateStringWithMaxLength(paymentTerm.getDescription(), "The description of " + usage);
        validateStringWithMaxLength(paymentTerm.getCreationUser(), "The creation user of " + usage);
    }

    /**
     * <p>
     * Validate given before date does not exceed after date.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  <ul>
     *   <li>If any date is null, IAE is thrown;</li>
     *   <li>If before &gt; after, IAE is thrown;</li>
     *   <li>If <em>condition</em> is true and before != after, IAE is thrown;</li>
     *   <li>If <em>condition</em> is false and before == after, IAE is thrown;</li>
     *  </ul>
     * </p>
     *
     * @param before The before date to validate.
     * @param after The after date to validate.
     * @param condition Indicate whether before should equal after.
     * @param beforeUsage The usage of the before date.
     * @param afterUsage The usage of the after date.
     *
     * @throws IllegalArgumentException if validation failed.
     */
    public static void validateNotExceed(Date before, Date after, Boolean condition,
            String beforeUsage, String afterUsage) {
        validateNotNull(before, beforeUsage);
        validateNotNull(after, afterUsage);

        if (after.before(before)) {
            throw new IllegalArgumentException(beforeUsage + " must not exceed " + afterUsage + ", but: ["
                + before + "] > [" + after + "].");
        }
/*        if (condition == null) {
            return;
        }
        if (condition.booleanValue() && (!before.equals(after))) {
            throw new IllegalArgumentException(beforeUsage + " must equal " + afterUsage + ", but: ["
                    + before + "] != [" + after + "].");
        }
        if ((!condition.booleanValue()) && before.equals(after)) {
            throw new IllegalArgumentException(beforeUsage + " must not equal " + afterUsage + ", but: ["
                    + before + "] == [" + after + "].");
        }*/
    }
}
