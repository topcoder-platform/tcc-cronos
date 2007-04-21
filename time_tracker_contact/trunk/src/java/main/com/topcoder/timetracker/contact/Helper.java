/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;


/**
 * <p>
 * This is an utility class help to validate arguments for this component.
 * It also helps get property value, <code>ObjectFactory</code>, implementation of <code>AddressDAO</code>
 * and <code>ContactDAO</code> from given namespace.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class Helper {

    /**
     * <p>
     * The count of milliseconds within one second.
     * </p>
     */
    public static final int MILLISECOND = 1000;

    /**
     * <p>
     * The prefix of the JNDI name of the environment entries bound in EJB.
     * </p>
     */
    private static final String JNDI_PREFIX = "java:comp/env/";

    /**
     * <p>
     * The fixed environment entry referenced by <code>ContactBean</code> and <code>AddressBean</code> which gives
     * the namespace for <code>ObjectFactory</code> in order to create <code>InformixAddressDAO</code> and
     * <code>InformixContactDAO</code>. Required.
     * </p>
     */
    private static final String SPE_NAMESPACE = "SpecificationNamespace";

    /**
     * <p>
     * The fixed environment entry referenced by <code>ContactBean</code> which gives the key of
     * <code>InformixContactDAO</code> with <code>ObjectFactory</code>. Required.
     * </p>
     */
    private static final String CONTACTDAO_KEY = "ContactDAOKey";

    /**
     * <p>
     * The fixed environment entry referenced by <code>AddressBean</code> which gives the key of
     * <code>InformixAddressDAO</code> with <code>ObjectFactory</code>. Required.
     * </p>
     */
    private static final String ADDRESSDAO_KEY = "AddressDAOKey";

    /**
     * <p>
     * Only if two objects have same reference (== operator returns true), then they are considered to
     * be duplicate, otherwise they are not duplicate(even same hashCode and equals() returns true).
     * This field will be passed into the constructor of {@link TreeMap#TreeMap(Comparator)} to terminate
     * the same reference.
     * </p>
     */
    private static final Comparator COMPARATOR = new Comparator() {
        public int compare(Object a, Object b) {
            if (a == b) {
                //same object reference, return 0
                return 0;
            }
            //otherwise always return 1
            return 1;
        }
    };

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
     * Check whether the given id is positive.
     * </p>
     *
     * @param id The id to validate.
     *
     * @return true If the given id is positive; false otherwise.
     */
    private static boolean checkPositive(long id) {
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
     * @throws IllegalArgumentException - If the given id is not positive.
     */
    public static void validatePositiveWithIAE(long id, String name) {
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
     * @throws InvalidPropertyException If the given id is not positive.
     */
    public static void validatePositiveWithIPE(long id, String name)
        throws InvalidPropertyException {
        if (!checkPositive(id)) {
            throw new InvalidPropertyException(name + " should be positive, but is '" + id + "'.");
        }
    }

    /**
     * <p>
     * Check whether the given <code>Object</code> is not null.
     * If the given <code>Object</code> is null, false is returned.
     * </p>
     *
     * @param object The <code>Object</code> to check.
     *
     * @return false If given object is null; true otherwise.
     */
    private static boolean checkNotNull(Object object) {
        if (object == null) {
            return false;
        }

        return true;
    }

    /**
     * <p>
     * Validates the given <code>Object</code> is not null.
     * If the given <code>Object</code> is null, <code>IllegalArgumentException</code> is thrown.
     * </p>
     *
     * @param object The <code>Object</code> to validate.
     * @param usage The usage what the object is intent to represent.
     *
     * @throws IllegalArgumentException - If the given object is null.
     */
    public static void validateNotNullWithIAE(Object object, String usage) {
        if (!checkNotNull(object)) {
            throw new IllegalArgumentException(usage + " should not be null.");
        }
    }

    /**
     * <p>
     * Validates the given <code>Object</code> is not null.
     * If the given <code>Object</code> is null, <code>InvalidPropertyException</code> is thrown.
     * </p>
     *
     * @param object The <code>Object</code> to validate.
     * @param usage The usage what the object is intent to represent.
     *
     * @throws InvalidPropertyException - If the given object is null.
     */
    public static void validateNotNullWithIPE(Object object, String usage)
        throws InvalidPropertyException {
        if (!checkNotNull(object)) {
            throw new InvalidPropertyException(usage + " should not be null.");
        }
    }

    /**
     * <p>
     * Validate given array of long value is not null and does not contain non-positive member.
     * </p>
     *
     * @param ids The array of long ids to validate.
     * @param usage The usage what the array is intent to represent.
     *
     * @return true If given array is empty; False otherwise.
     *
     * @throws IllegalArgumentException - If given array of long value is null or contains
     *         non-positive member.
     */
    public static boolean validateIdsArrayWithIAE(long[] ids, String usage) {
        validateNotNullWithIAE(ids, usage);

        for (int i = 0; i < ids.length; i++) {
            validatePositiveWithIAE(ids[i], usage + " at index " + i);
        }

        return ids.length == 0;
    }

    /**
     * <p>
     * Validate given array of <code>Object</code> is not null and does not contain null member.
     * This method will return a list contains the objects after removing duplication(objects with same reference)
     * if any.
     * </p>
     *
     * @param objects The array of <code>Object</code> to validate.
     * @param usage The usage what the array is intent to represent.
     *
     * @return List contains the objects after removing duplication(objects with same reference) if any.
     *
     * @throws IllegalArgumentException - If given array is null or contains null member.
     */
    public static List validateObjectsArrayWithIAE(Object[] objects, String usage) {
        validateNotNullWithIAE(objects, usage);

        TreeMap map = new TreeMap(COMPARATOR);
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            validateNotNullWithIAE(object, usage + " at index " + i);
            map.put(object, null);
        }

        return new ArrayList(map.keySet());
    }

    /**
     * <p>
     * Validate given from and to date range are not both null.
     * </p>
     *
     * <p>
     * If they are both not null, then the from date must not exceed the end date with precision of second.
     * </p>
     *
     * @param from The from date range
     * @param to The to date range
     * @param usage The usage of the date range
     *
     * @throws IllegalArgumentException If both ends of date range are null
     */
    public static void validateDatesRange(Date from, Date to, String usage) {
        if (from == null && to == null) {
            throw new IllegalArgumentException("Both ends of date range for " + usage + " are null");
        }

        //Informix type DATETIME YEAR TO SECOND, precision is second
        if ((from != null) && (to != null)) {
            if (from.getTime() / MILLISECOND > to.getTime() / MILLISECOND) {
                throw new IllegalArgumentException("The range specified is invalid, start Date exceeds end Date: "
                    + from + " > " + to);
            }
        }
    }
    /**
     * <p>
     * Validate given before date does not exceed after date.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  <ul>
     *   <li>If any date is null, <code>InvalidPropertyException</code> is thrown;</li>
     *   <li>If before &gt; after, <code>InvalidPropertyException</code> is thrown;</li>
     *  </ul>
     * </p>
     *
     * @param before The before date to validate.
     * @param after The after date to validate.
     * @param beforeUsage The usage of the before date.
     * @param afterUsage The usage of the after date.
     *
     * @throws InvalidPropertyException if validation failed.
     */
    public static void validateNotExceed(Date before, Date after,
        String beforeUsage, String afterUsage) throws InvalidPropertyException {
        validateNotNullWithIPE(before, beforeUsage);
        validateNotNullWithIPE(after, afterUsage);

        if (before.after(after)) {
            throw new InvalidPropertyException(beforeUsage
                + " must not exceed " + afterUsage + ", but: [" + before + "] >= [" + after + "].");
        }
    }

    /**
     * <p>Check whether the given <code>String</code> is not null and not empty(trimmed).</p>
     *
     * @param string The <code>String</code> to check.
     *
     * @return false If the given string is null or empty string (trimmed); True otherwise.
     */
    private static boolean checkString(String string) {
        if ((string == null) || (string.trim().length() == 0)) {
            return false;
        }

        return true;
    }

    /**
     * <p>
     * Validates whether the given <code>String</code> is not null and not empty(trimmed).
     * </p>
     *
     * @param string The <code>String</code> to validated.
     * @param usage The usage what the String is intent to represent.
     *
     * @throws IllegalArgumentException - If the given string is null or empty string (trimmed).
     */
    public static void validateStringWithIAE(String string, String usage) {
        validateNotNullWithIAE(string, usage);

        if (string.trim().length() == 0) {
            throw new IllegalArgumentException(usage + " should not be empty (trimmed).");
        }
    }

    /**
     * <p>Returns the value of the given property in the given namespace.</p>
     *  <p><strong>Note:</strong>
     *  <ul>
     *      <li>If <em>required</em> is true, and the given property is missing or with an
     *      empty(trimmed) value, then it will cause an
     *      <code>ConfigurationException</code>.</li>
     *      <li>If <em>required</em> is false, and the given property is missing or with an
     *      empty(trimmed) value, then this method will return null.</li>
     *  </ul>
     *  </p>
     *
     * @param namespace The namespace to get the property value from.
     * @param propertyName The name of property.
     * @param required Indicates whether the property is required.
     *
     * @return the value of the property. May be null if this property is not required. But will never be
     *         null or empty(trimmed) if this property is required.
     *
     * @throws IllegalArgumentException - If the given parameter is null or empty(trimmed).
     * @throws ConfigurationException - If fail to load the config values.
     */
    public static String getPropertyValue(String namespace,
        String propertyName, boolean required) throws ConfigurationException {
        validateStringWithIAE(namespace, "namespace");
        validateStringWithIAE(propertyName, "propertyName");

        try {
            String property = ConfigManager.getInstance()
                                           .getString(namespace, propertyName);

            if (required) {
                validateStringWithIAE(property, "The value for property '" + propertyName + "'");
            } else if (!checkString(property)) {
                return null;
            }

            return property.trim();
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("The namespace '" + namespace + "' is unknown.", e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Required property '" + propertyName + "' is missing.", e);
        }
    }

    /**
     * <p>
     * Instantiate an <code>ObjectFactory</code> with given specification namespace.
     * </p>
     *
     * @param specNamespace The specification namespace of <code>ConfigManagerSpecificationFactory</code>.
     *
     * @return An instance of <code>ObjectFactory</code> created.
     *
     * @throws ConfigurationException - If fail to load the config values or error occurs while creating
     *         <code>ObjectFactory</code>.
     */
    public static ObjectFactory getObjectFactory(String specNamespace)
        throws ConfigurationException {
        try {
            ConfigManagerSpecificationFactory cmsf = new ConfigManagerSpecificationFactory(specNamespace);

            return new ObjectFactory(cmsf);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("The namespace of ObjectFactory is illegal",
                e);
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException(
                "Can not create ObjectFactory from given namespace: " + specNamespace, e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException(
                "Can not create ObjectFactory from given namespace: " + specNamespace, e);
        }
    }

    /**
     * <p>
     * Used by <code>ejbCreate()</code> methods of <code>ContactBean</code> and <code>AddressBean</code> to get
     * DAO from <code>ObjectFactory</code>. The specification namespace and key of DAO are retrieved by looking
     * up EJB referenced environment entry.
     * </p>
     *
     * <p>
     * The EJB deployment descriptors of both <code>ContactBean</code> and <code>AddressBean</code> should contain
     * an environment entry <b>"SpecificationNamespace"</b> which gives the specification namespace of
     * <code>ObjectFactory</code>. And:
     *  <ul>
     *   <li>
     *   The EJB deployment descriptor of <code>ContactBean</code> should contain an environment entry
     *   <b>"ContaceDAOKey"</b> which gives the key of <code>ContactDAO</code> within <code>ObjectFactory</code>.
     *   </li>
     *   <li>
     *   The EJB deployment descriptor of <code>AddressBean</code> should contain an environment entry
     *   <b>"AddressDAOKey"</b> which gives the key of <code>AddressDAO</code> within <code>ObjectFactory</code>.
     *   </li>
     *  </ul>
     * </p>
     *
     * @param address Indicates whether to get an instance of <code>AddressDAO</code> or <code>ContactDAO</code>.
     *
     * @return An instance of <code>AddressDAO</code> if given boolean value is true; Otherwise return an instance
     *         of <code>ContactDAO</code> if given boolean value is false.
     *
     * @throws CreateException - If error occurs while looking up environment entry or creating DAO through
     *         <code>ObjectFactory</code>.
     */
    public static Object getDAO(boolean address) throws CreateException {

        try {
            String specNamespace = null;
            Context initCtx = new InitialContext();
            specNamespace = (String) initCtx.lookup(JNDI_PREFIX + SPE_NAMESPACE);

            String daoKey = (String) initCtx.lookup(JNDI_PREFIX + (address ? ADDRESSDAO_KEY : CONTACTDAO_KEY));
            ObjectFactory of = getObjectFactory(specNamespace);
            Object dao = of.createObject(daoKey);

            if (address) {
                return (AddressDAO) dao;
            } else {
                return (ContactDAO) dao;
            }
        } catch (NamingException ne) {
            throw new CreateException(
                "Error occurs while looking up environment entry: " + ne.getMessage());
        } catch (ConfigurationException ce) {
            throw new CreateException(
                "Error occurs while creating ObjectFactory: " + ce.getMessage());
        } catch (IllegalArgumentException iae) {
            throw new CreateException("Error occurs while creating DAO: " + iae.getMessage());
        } catch (InvalidClassSpecificationException icse) {
            throw new CreateException("Error occurs while creating DAO: " + icse.getMessage());
        } catch (ClassCastException cce) {
            throw new CreateException("Error occurs while casting object to DAO: " + cce.getMessage());
        }
    }
}
