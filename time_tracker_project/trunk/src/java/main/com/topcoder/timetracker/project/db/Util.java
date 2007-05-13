/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
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
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.StringMatchType;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * Helper class for this component.
 * </p>
 *
 * <p>
 * Thread Safety : This class is immutable and so thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
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
     * @param arg the argument to check
     * @param name the name of the argument to check
     * @return the original argument
     *
     * @throws IllegalArgumentException if the given Object is null
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
     * @param arg the String to check
     * @param name the name of the String argument to check
     * @return the original argument
     *
     * @throws IllegalArgumentException if the given string is null or empty
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
     * This method checks the given users and update their creation date and modification date based
     * on whether the operation is creation or modification.
     * </p>
     *
     * <p>
     * If the operation is creation, then <code>create</code> should be true, and both creation date
     * and modification date will be updated.
     * If the operation is modification, then <code>create</code> should be false, and only
     * modification date will be updated.
     * </p>
     *
     * @param beans the beans array
     * @param name the name to use
     * @param create the flag to check whether it is creation or modification
     *
     * @throws IllegalArgumentException if users is null or contains null user
     */
    public static void updateTimeTrackerBeanDates(TimeTrackerBean[] beans, String name, boolean create) {
        Util.checkNull(beans, name);

        Date now = new Date();

        for (int i = 0; i < beans.length; i++) {
            Util.checkNull(beans[i], "element in " + name);

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
     * This method creates the ejb local home object using the configuration setting.
     * </p>
     *
     * <p>
     * <b>contextName</b> and <b>jndi_home</b> are the keys in the configuration file.
     * </p>
     *
     * @param namespace the namespace to read configuration information
     * @param localHomeClass the expected class of the ejb local home
     * @return the ejb local home object
     *
     * @throws ConfigurationException if any exception occurs
     */
    public static Object createEJBLocalHome(String namespace, Class localHomeClass) throws ConfigurationException {
        Util.checkString(namespace, "namespace");

        try {
            String contextName = getString(namespace, "contextName", false);
            String jndiName = getString(namespace, "jndi_home", true);

            Context jndiContext = (contextName == null) ? JNDIUtils.getDefaultContext()
                : JNDIUtils.getContext(contextName);

            Object ref = jndiContext.lookup(jndiName);
            return PortableRemoteObject.narrow(ref, localHomeClass);
        } catch (ConfigManagerException e) {
            throw new ConfigurationException("Failed to access config manager.", e);
        } catch (NamingException e) {
            throw new ConfigurationException(
                "NamingException occurs when looking up the JNDI for the UserManagerLocalHome.", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException(
                "ClassCastException occurs when narrowing the object reference retrieved from JNDI.", e);
        }
    }

    /**
     * <p>
     * This method checks the given id.
     * </p>
     *
     * <p>
     * If the given id is zero or negative, <code>IllegalArgumentException</code> will be thrown.
     * </p>
     *
     * @param idValue the id value to check
     * @param name the name of the id
     *
     * @throws IllegalArgumentException if the given idValue is negative or zero
     */
    public static void checkIdValue(long idValue, String name) {
        if (idValue <= 0) {
            throw new IllegalArgumentException("The given " + name + " id is negative or zero.");
        }
    }

    /**
     * <p>
     * Checks whether the given <code>Map</code> instance contains each string in the given string
     * array as a key.
     * </p>
     *
     * @param columnNames the <code>Map</code> instance to check
     * @param keys each key in the string array is expected to be contained in the given <code>Map</code>
     * instance as a key
     *
     * @throws IllegalArgumentException if the given map instance doesn't contains any key in the given
     * string array
     */
    static void checkMapForKeys(Map columnNames, String[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (!columnNames.containsKey(keys[i])) {
                throw new IllegalArgumentException("The given column names mapping doesn't contains key [" + keys[i]
                    + "}.");
            }
        }
    }

    /**
     * <p>
     * This method creates a <code>Filter</code> instance based on the given arguments.
     * </p>
     *
     * <p>
     * For {@link StringMatchType#EXACT_MATCH}, this method will create a <code>EqualToFilter</code>
     * that is used search for string that matches the provided value exactly.
     * For {@link StringMatchType#STARTS_WITH}, this method will create a <code>LikeFilter</code>
     * that is used to search for string that starts with the provided value.
     * For {@link StringMatchType#ENDS_WITH}, this method will create a <code>LikeFilter</code>
     * that is used to search for string that ends with the provided value.
     * For {@link StringMatchType#SUBSTRING}, this method will create a <code>LikeFilter</code>
     * that is used to search for strings that contains the provided value.
     * </p>
     *
     * @param matchType <code>StringMatchType</code> to determine which search type to be created
     * @param name the name of the column to search
     * @param value the value of the column to match based on the search type
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the given matchType is null
     */
    static Filter createFilter(StringMatchType matchType, String name, String value) {
        checkNull(matchType, "matchType");

        if (matchType.equals(StringMatchType.EXACT_MATCH)) {
            return new EqualToFilter(name, value);
        } else {
            return new LikeFilter(name, matchType.getFilterPrefix() + value);
        }
    }

    /**
     * <p>
     * This method creates a filter for the given column name, the start and end ranges.
     * </p>
     *
     * <p>
     * The given <code>rangeStart</code> and <code>rangeEnd</code> cannot both be null.
     * </p>
     *
     * <p>
     * If the <code>rangeStart</code> is null, then the filter should search the records with the
     * column value less than or equals to <code>rangeEnd</code>.
     * </p>
     *
     * <p>
     * If the <code>rangeEnd</code> is null, then the filter should search the records with the
     * column value larger than or equals to <code>rangeStart</code>.
     * </p>
     *
     * <p>
     * If they are both not null, then the filter should search the records with the column value between
     * the given <code>rangeStart</code> and <code>rangeEnd</code>.
     * </p>
     *
     * @param columnName the name of the column to search
     * @param rangeStart The lower bound of the range criterion.
     * @param rangeEnd The upper bound of the range criterion.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException the range specified is invalid (e.g. rangeStart &gt; rangeEnd),
     * or if both arguments are null.
     */
    static Filter createRangeFilter(String columnName, Comparable rangeStart, Comparable rangeEnd) {
        if (rangeStart == null && rangeEnd == null) {
            throw new IllegalArgumentException("Both range start and end are null.");
        }

        if (rangeStart == null && rangeEnd != null) {
            return new LessThanOrEqualToFilter(columnName, rangeEnd);
        }

        if (rangeStart != null && rangeEnd == null) {
            return new GreaterThanOrEqualToFilter(columnName, rangeStart);
        }

        if (rangeStart.compareTo(rangeEnd) > 0) {
            throw new IllegalArgumentException("The given range start is larger than the range end.");
        }

        return new BetweenFilter(columnName, rangeEnd, rangeStart);
    }

    /**
     * <p>
     * This method creates a <code>AuditDetail</code> instance based on the give parameters.
     * </p>
     *
     * @param columnName the column name
     * @param oldValue the old column value
     * @param newValue the new column value
     *
     * @return the <code>AuditDetail</code> instance based on the give parameters.
     */
    static AuditDetail createAuditDetail(String columnName, String oldValue, String newValue) {
        AuditDetail detail = new AuditDetail();
        detail.setColumnName(columnName);
        detail.setOldValue(oldValue);
        detail.setNewValue(newValue);
        detail.setPersisted(false);

        return detail;
    }

    /**
     * <p>
     * This function is used to execute a update sql expression in database persistence.
     * </p>
     *
     * @param connection the connection instance for database operation
     * @param sql the update sql expression
     * @param params the parameters for executing update in database
     * @return the number of rows affects by the current operation
     *
     * @throws SQLException when exception occurs during the database operation
     */
    static int executeUpdate(Connection connection, String sql, List params) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            fillPreparedStatement(pstmt, params);

            return pstmt.executeUpdate();
        } finally {
            // release database resource
            closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This function is used to execute a update sql expression in database persistence
     * in a batch operation.
     * </p>
     *
     * @param connection the connection instance for database operation
     * @param sql the update sql expression
     * @param params the parameters for executing update in database
     * @return the number of rows affects by the current operation
     *
     * @throws SQLException when exception occurs during the database operation
     * @throws DataAccessException if any update count for each command in the batch
     * is not equals to one
     */
    static int[] executeBatchUpdate(Connection connection, String sql, List[] params) throws SQLException,
        DataAccessException {
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                fillPreparedStatement(pstmt, params[i]);

                pstmt.addBatch();
            }

            int[] updateCounts = pstmt.executeBatch();

            for (int i = 0; i < updateCounts.length; i++) {
                if (updateCounts[i] != 1) {
                    throw new DataAccessException("Failed to update the database.");
                }
            }

            return updateCounts;
        } finally {
            // release database resource
            closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This methods fill the <code>PreparedStatement</code> instance with the given parameters.
     * </p>
     *
     * @param pstmt the <code>PreparedStatement</code> instance to fill data
     * @param params the parameter list to fill the <code>PreparedStatement</code> instance
     *
     * @throws SQLException if a database access error occurs
     */
    static void fillPreparedStatement(PreparedStatement pstmt, List params) throws SQLException {
        // set up all the necessary parameters for executing.
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                Object obj = params.get(i);
                if (obj instanceof SqlType) {
                    pstmt.setNull(i + 1, ((SqlType) obj).getType());
                } else {
                    setElement(i + 1, obj, pstmt);
                }
            }
        }
    }

    /**
     * <p>
     * Sets the actual value to replace the corresponding question mark.
     * </p>
     *
     * @param order the sequence number of question mark in sql expression
     * @param parameter the actual value to replace the corresponding question mark.
     * @param ps PreparedStatement instance to execute the sql expression
     *
     * @throws SQLException when exception occurs during the database operation
     */
    private static void setElement(int order, Object parameter, PreparedStatement ps) throws SQLException {
        // replace the question mark in sql with real value
        if (parameter instanceof String) {
            ps.setString(order, (String) parameter);
        } else if (parameter instanceof Long) {
            ps.setLong(order, ((Long) parameter).longValue());
        } else if (parameter instanceof Double) {
            ps.setDouble(order, ((Double) parameter).doubleValue());
        } else if (parameter instanceof Date) {
            ps.setTimestamp(order, new Timestamp(((Date) parameter).getTime()));
        } else {
            ps.setObject(order, parameter);
        }
    }

    /**
     * <p>
     * Closes the given Statement instance, SQLException will be ignored.
     * </p>
     *
     * @param statement the given Statement instance to close.
     */
    static void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given Connection instance, SQLException will be ignored.
     * </p>
     *
     * @param con the given Connection instance to close.
     */
    static void closeConnection(Connection con) {
        try {
            if ((con != null) && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given ResultSet instance, SQLException will be ignored.
     * </p>
     *
     * @param rs the given ResultSet instance to close.
     */
    static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Returns the value of the property in the given namespace.
     * </p>
     *
     * <p>
     * If the property is missing in the configuration, then if it is required, then
     * <code>ConfigurationException</code> will be thrown, otherwise <code>null</code>
     * will be returned.
     * </p>
     *
     * @param namespace the namespace to get
     * @param propertyName the name of property
     * @param required whether this property is required
     *
     * @return the value of the property
     *
     * @throws ConfigurationExeption if fail to load the config values
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
            throw new ConfigurationException("UnknownNamespaceException occurs when accessing ConfigManager.", e);
        }
    }

    /**
     * <p>
     * Creates an object from the given namespace and the class type.
     * </p>
     *
     * <p>
     * The type of the object to create is given as well, it is for validation purpose.
     * </p>
     *
     * @param namespace the namespace to get the configuration value
     * @param type the class type of the object to create, it is for validation purpose
     * @return the object created from the given object factory and the key
     *
     * @throws CreateException if fail to create the object or the
     * created object is an instance of the given type
     */
    public static Object createObject(String namespace, Class type) throws CreateException {
        String ofNamespace = null;
        String key = null;
        try {
            ofNamespace = getString(namespace, "of_namespace", true);
            key = getString(namespace, "impl_key", true);

            ObjectFactory of = new ObjectFactory(new ConfigManagerSpecificationFactory(ofNamespace));
            Object value = of.createObject(key);

            // verify the class type of the created object
            if (!type.isInstance(value)) {
                throw new CreateException("The configured object for key [" + key + "] is not an instance of "
                    + type.getName());
            }

            return value;
        } catch (InvalidClassSpecificationException e) {
            e.printStackTrace();
            throw new CreateException("InvalidClassSpecificationException occurs while creating object with key ["
                + key + "] using object factory, caused by " + e.getMessage());
        } catch (SpecificationConfigurationException e) {
            throw new CreateException("SpecificationConfigurationException occurs "
                + "while creating ObjectFactory instance using namespace " + ofNamespace + ", caused by "
                + e.getMessage());
        } catch (IllegalReferenceException e) {
            throw new CreateException("IllegalReferenceException occurs "
                + "while creating ObjectFactory instance using namespace " + ofNamespace + ", caused by "
                + e.getMessage());
        } catch (ConfigurationException e) {
            throw new CreateException("ConfigurationException occurs while loading the config value, caused by "
                + e.getMessage());
        }
    }

    /**
     * <p>
     * This method builds a IN clause according to the given id array.
     * </p>
     *
     * @param columnName the column name
     * @param ids the id array
     * @return the IN clause according to the given column name and id array
     */
    static String buildInClause(String columnName, long[] ids) {
        StringBuffer inClause = new StringBuffer();
        inClause.append(columnName);
        inClause.append(" IN (");
        for (int i = 0; i < ids.length; i++) {
            if (i != 0) {
                inClause.append(", ");
            }
            inClause.append(ids[i]);
        }
        inClause.append(")");

        return inClause.toString();
    }

    /**
     * <p>
     * This method resets the given time tracker beans to unchanged states.
     * </p>
     *
     * @param beans the time tracker beans for the batch operation
     * @param causes the inner causes of the batch operation
     */
    static void resetBeanChangedStates(TimeTrackerBean[] beans) {
        for (int i = 0; i < beans.length; i++) {
            beans[i].setChanged(false);
        }
    }

}