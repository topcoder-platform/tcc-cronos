/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.ejb.EJBLocalHome;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * Helper class for this component.
 * </p>
 * <p>
 * Thread Safety : This class is immutable and so thread safe.
 * </p>
 *
 * @author biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public final class Util {
    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Util() {
        // empty
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @return the original argument
     *
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    public static Object checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }

        return arg;
    }

    /**
     * <p>
     * Checks whether the given String is null or empty.
     * </p>
     *
     * @param arg
     *            the String to check
     * @param name
     *            the name of the String argument to check
     * @return the original argument
     *
     * @throws IllegalArgumentException
     *             if the given string is null or empty
     */
    public static String checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        return arg;
    }

    /**
     * <p>
     * Checks whether the given <code>Map</code> instance contains each string in the given string array as a
     * key.
     * </p>
     *
     * @param columnNames
     *            the <code>Map</code> instance to check
     * @param keys
     *            each key in the string array is expected to be contained in the given <code>Map</code> instance
     *            as a key
     *
     * @throws IllegalArgumentException
     *             if the given map instance doesn't contains any key in the given string array
     */
    public static void checkMapForKeys(Map columnNames, String[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (!columnNames.containsKey(keys[i])) {
                throw new IllegalArgumentException("The given column names mapping doesn't contains key ["
                    + keys[i] + "}.");
            }
        }
    }

    /**
     * <p>
     * This method creates a <code>Filter</code> instance based on the given arguments.
     * </p>
     *
     * <p>
     * For {@link StringMatchType#EXACT_MATCH}, this method will create a <code>EqualToFilter</code> that is
     * used search for string that matches the provided value exactly. For {@link StringMatchType#STARTS_WITH},
     * this method will create a <code>LikeFilter</code> that is used to search for string that starts with the
     * provided value. For {@link StringMatchType#ENDS_WITH}, this method will create a <code>LikeFilter</code>
     * that is used to search for string that ends with the provided value. For {@link StringMatchType#SUBSTRING},
     * this method will create a <code>LikeFilter</code> that is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param matchType
     *            <code>StringMatchType</code> to determine which search type to be created
     * @param name
     *            the name of the column to search
     * @param value
     *            the value of the column to match based on the search type
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException
     *             if the given matchType is null
     */
    public static Filter createFilter(StringMatchType matchType, String name, String value) {
        checkNull(matchType, "matchType");

        if (matchType.equals(StringMatchType.EXACT_MATCH)) {
            return new EqualToFilter(name, value);
        } else {
            return new LikeFilter(name, matchType.getFilterPrefix() + value);
        }
    }

    /**
     * <p>
     * Creates ejb local object from the JNDI.
     * </p>
     *
     * @param namespace
     *            the given namespace
     * @param intendedLocalHomeClass
     *            the intended class of the EJBLocalHome
     * @param intendedLocalClass
     *            the intended class of EJBLocalObject
     *
     * @return the local object
     *
     * @throws ConfigurationException
     *             if there is any exception when creating the local object
     *
     * @since 3.2.1
     */
    public static Object createLocalObject(String namespace, Class intendedLocalHomeClass,
        Class intendedLocalClass) throws ConfigurationException {
        Util.checkString(namespace, "namespace");

        try {
            String contextName = getString(namespace, "contextName", false);
            String jndiName = getString(namespace, "localHomeName", true);

            Context jndiContext =
                (contextName == null) ? JNDIUtils.getDefaultContext() : JNDIUtils.getContext(contextName);

            Object ref = jndiContext.lookup(jndiName);
            EJBLocalHome home = (EJBLocalHome) PortableRemoteObject.narrow(ref, intendedLocalHomeClass);
            Method createMethod = home.getClass().getMethod("create", new Class[0]);
            Object result = createMethod.invoke(home, new Object[0]);

            if (!(intendedLocalClass.isInstance(result))) {
                throw new ConfigurationException("The created object is not instance of "
                    + intendedLocalClass.getName());
            }
            return result;
        } catch (NamingException e) {
            throw new ConfigurationException(
                "NamingException occurs when looking up the JNDI for the UserManagerLocalHome.", e);
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("Failed to access config manager.", e);
        } catch (SecurityException e) {
            throw new ConfigurationException("The public 'create' method can't be accessed", e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("This object doesn't have a public 'create' method", e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("The method 'create' is not valid", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("The method 'create' can't be accessed", e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationException("Can't create the object", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("Class cast exception in the narrow method", e);
        }
    }

    /**
     * <p>
     * Returns the value of the property in the given namespace.
     * </p>
     *
     * <p>
     * If the property is missing in the configuration, then if it is required, then
     * <code>ConfigurationException</code> will be thrown, otherwise <code>null</code> will be returned.
     * </p>
     *
     * @param namespace
     *            the namespace to get
     * @param propertyName
     *            the name of property
     * @param required
     *            whether this property is required
     *
     * @return the value of the property
     *
     * @throws ConfigurationException
     *             if fail to load the config values
     * @since 3.2.1
     */
    private static String getString(String namespace, String propertyName, boolean required)
        throws ConfigurationException {
        try {
            String property = ConfigManager.getInstance().getString(namespace, propertyName);

            // Empty property value is not allowed
            if ((property != null) && (property.trim().length() == 0)) {
                throw new ConfigurationException("Property " + propertyName + " is empty.");
            }

            // This property is missed
            if (required && (property == null)) {
                throw new ConfigurationException("Property " + propertyName + " is missing.");
            }

            return property;
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException(
                "UnknownNamespaceException occurs when accessing ConfigManager.", e);
        }
    }

    /**
     * <p>
     * This method checks the given beans and update their creation date and modification date based on whether the
     * operation is creation or modification.
     * </p>
     *
     * <p>
     * If the operation is creation, then <code>create</code> should be true, and both creation date and
     * modification date will be updated. If the operation is modification, then <code>create</code> should be
     * false, and only modification date will be updated.
     * </p>
     *
     * @param beans
     *            the beans array
     * @param create
     *            the flag to check whether it is creation or modification
     *
     * @throws IllegalArgumentException
     *             if beans is null or contains null bean
     * @since 3.2.1
     */
    public static void updateDates(TimeTrackerBean[] beans, boolean create) {
        Util.checkNull(beans, "users");

        Date now = new Date();

        for (int i = 0; i < beans.length; i++) {
            Util.checkNull(beans[i], "user in users");

            // if it is creation, both creation date
            // and modification date will be updated
            if (create) {
                beans[i].setCreationDate(now);
            }

            // if it is modification, only modification date will be updated
            beans[i].setModificationDate(now);
        }
    }

    /**
     * <p>
     * Creates duration date filter. Open end OR open start is supported if either the parameter is null, but not
     * both.
     * </p>
     *
     * @param from
     *            the start date, inclusive
     * @param to
     *            the end date, inclusive
     * @param name
     *            the name of the column
     *
     * @return the date filter
     * @throws IllegalArgumentException
     *             if to < from
     * @since 3.2.1
     */
    public static Filter createDateFilter(Date from, Date to, String name) {
        if (from == null) {
            if (to == null) {
                throw new IllegalArgumentException("At least one 'from' or 'to' need to be available");
            } else {
                return new LessThanOrEqualToFilter(name, to);
            }
        }

        if (to == null) {
            return new GreaterThanOrEqualToFilter(name, from);
        }

        if (to.before(from)) {
            throw new IllegalArgumentException(
                "the arguments are illegal, parameter from should be before to");
        }

        return new BetweenFilter(name, new Timestamp(to.getTime()), new Timestamp(from.getTime()));
    }

    /**
     * <p>
     * Creates new equal to filter for a boolean column.
     * </p>
     *
     * @param bool
     *            the given boolean
     * @param name
     *            the given column name
     * @return an equal to filter which filters column name that has value bool
     * @since 3.2.1
     */
    public static Filter createBooleanFilter(boolean bool, String name) {
        Integer value = bool ? new Integer(1) : new Integer(0);
        return new EqualToFilter(name, value);
    }

    /**
     * <p>
     * Creates new equal to filter for a long column.
     * </p>
     *
     * @param number
     *            the given number
     * @param paramName
     *            the name of the parameter (for error message)
     * @param name
     *            the given column name
     * @return an equal to filter which filters column name that has value number
     * @throws IllegalArgumentException
     *             if the number is &lt;= 0
     * @since 3.2.1
     */
    public static Filter createLongFilter(long number, String paramName, String name) {
        if (number <= 0) {
            throw new IllegalArgumentException(paramName + " should be a positive number");
        }

        return new EqualToFilter(name, new Long(number));
    }

    /**
     * <p>
     * Creates new equal to filter for a string column.
     * </p>
     *
     * @param string
     *            the given string
     * @param paramName
     *            the name of the parameter (for error message)
     * @param name
     *            the given column name
     * @return an equal to filter which filters column name that has value string
     * @throws IllegalArgumentException
     *             if the string is null or empty string
     * @since 3.2.1
     */
    public static Filter createStringFilter(String string, String paramName, String name) {
        checkString(string, "string");

        return new EqualToFilter(name, string);
    }

    /**
     * <p>
     * Converts the <code>BatchOperationException</code> to the first exception it wraps.
     * </p>
     *
     * @param e
     *            the given exception
     * @param msg
     *            the message that should be returned if error happens
     * @return the exception it wraps
     * @throws NullPointerException
     *             if exception is null
     * @since 3.2.1
     */
    public static DataAccessException convertBatchExceptionToSingleException(BatchOperationException e,
        String msg) {
        Throwable cause = e.getCauses()[0];
        if (cause instanceof DataAccessException) {
            return (DataAccessException) cause;
        } else {
            return new DataAccessException(msg, cause);
        }
    }

}