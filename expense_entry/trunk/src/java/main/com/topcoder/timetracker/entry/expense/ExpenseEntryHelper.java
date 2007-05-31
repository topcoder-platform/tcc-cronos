/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.naming.jndiutility.JNDIUtils;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.timetracker.rejectreason.RejectReason;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.generator.guid.UUIDType;
import com.topcoder.util.generator.guid.UUIDUtility;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;

import javax.naming.Context;
import javax.naming.NamingException;


/**
 * <p>
 * Defines utilities for this component. Since this class will be accessed via multi-packages, this class is declared
 * in public scope.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public final class ExpenseEntryHelper {
    /** Represents the base of a hex number. */
    private static final int HEX_BASE = 16;

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryHelper</code> class. This private constructor prevents the creation
     * of a new instance.
     * </p>
     */
    private ExpenseEntryHelper() {
    }

    /**
     * <p>
     * Validates the value of a variable with type <code>long</code>. The value should be positive.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @throws IllegalArgumentException if <code>value</code> is not positive.
     */
    public static void validatePositive(long value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " should be positive.");
        }
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
     * Validates the array of a object. The size of the object not be less than the given min length.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     * @param minLength the min length of the collection.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code>, or any element is
     *         <code>null</code>, its length is less than the given min length.
     */
    public static void validateObjectArray(Object[] value, String name, int minLength) {
        validateNotNull(value, name);

        if (value.length < minLength) {
            throw new IllegalArgumentException(name + "'s lenth should be at least " + minLength + ".");
        }

        for (int i = 0; i < value.length; i++) {
            validateNotNull(value[i], name + " " + i + "th element.");
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
     * @throws ConfigurationException if the namespace doesn't exist or the parameter doesn't exist if it is required
     *         to get, or the parameter value is an empty string.
     */
    public static String getStringPropertyValue(String namespace, String name, boolean required)
        throws ConfigurationException {
        try {
            String value = ConfigManager.getInstance().getString(namespace, name);

            if ((value == null)) {
                if (required) {
                    throw new ConfigurationException("The required parameter " + name + " in namespace " + namespace
                        + " doesn't exist.");
                }

                return null;
            }

            if (value.trim().length() == 0) {
                throw new ConfigurationException("The parameter value of " + name + " in namespace " + namespace
                    + " is an empty string.");
            }

            return value;
        } catch (UnknownNamespaceException une) {
            if (required) {
                throw new ConfigurationException("The namespace with the name of " + namespace
                    + " doesn't exist.", une);
            }

            return null;
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
     * @throws ConfigurationException if any error happens when creating.
     */
    public static ObjectFactory createObjectFactory(String namespace) throws ConfigurationException {
        try {
            return new ObjectFactory(new ConfigManagerSpecificationFactory(namespace));
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("Can't create the ObjectFactory.", e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("Can't create the ObjectFactory.", e);
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
     * @throws ConfigurationException if it fails to create the object or the object is not of expected type.
     */
    public static Object createObject(ObjectFactory objectFactory, String key, Class expected)
        throws ConfigurationException {
        try {
            Object object = objectFactory.createObject(key);

            if (!expected.isInstance(object)) {
                throw new ConfigurationException("The object is not instanceof " + expected.getName());
            }

            return object;
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException("The " + expected.getName() + " instance "
                + "can't be created successfully.", e);
        }
    }

    /**
     * <p>
     * Converts a {@link java.util.Date} to a {@link java.sql.Timestamp}.
     * </p>
     *
     * @param date the date to convert.
     *
     * @return a {@link java.sql.Timestamp} instance.
     */
    public static Timestamp date2Timestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * <p>
     * Get database connection from the db connection factory. If the connection is null or empty string, the default
     * connection will be retrieved.
     * </p>
     *
     * @param connectionFactory the db connection factory.
     * @param connectionName the connection name.
     *
     * @return A database connection.
     *
     * @throws PersistenceException If can't get connection.
     */
    public static Connection createConnection(DBConnectionFactory connectionFactory, String connectionName)
        throws PersistenceException {
        try {
            // create a DB connection
            return ((connectionName == null) || (connectionName.trim().length() == 0))
                ? connectionFactory.createConnection() : connectionFactory.createConnection(connectionName);
        } catch (DBConnectionException e) {
            throw new PersistenceException("Can't get the connection from database.", e);
        }
    }

    /**
     * <p>
     * Release the connection.
     * </p>
     *
     * @param conn the connection.
     */
    public static void releaseConnection(Connection conn) {
        try {
            if ((conn != null) && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            // ignore it
        }
    }

    /**
     * <p>
     * Releases the JDBC resources.
     * </p>
     *
     * @param resultSet the resultSet set to be closed.
     * @param statement the SQL statement to be closed.
     */
    public static void releaseResource(ResultSet resultSet, Statement statement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException se) {
                // ignore
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException se) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Validates the value of a <code>TimeTrackerBean</code> variable. The creation date and modification date of the
     * variable should be <code>null</code>.
     * </p>
     *
     * @param value the value of a <code>TimeTrackerBean</code> variable.
     * @param name the name of a <code>TimeTrackerBean</code> variable.
     *
     * @throws IllegalArgumentException if creation date or modification date of the given variable is not
     *         <code>null</code>.
     */
    public static void validateNewInfo(TimeTrackerBean value, String name) {
        if ((value.getCreationDate() != null) || (value.getModificationDate() != null)) {
            throw new IllegalArgumentException("The creation date or modification date of " + name + " is not null.");
        }
    }

    /**
     * <p>
     * Validates the data of an <code>ExpenseEntry</code> instance. All properties except invoice, description,
     * creation date and modification date should not be <code>null</code>.
     * </p>
     *
     * @param value the <code>ExpenseEntry</code> instance to validate.
     *
     * @throws InsufficientDataException if any property other than invoice, description, creation date and
     *         modification date is <code>null</code>.
     * @throws IllegalArgumentException if the ID of invoice, reject reason, expense type or expense status is not
     *         positive, or the company id is not matched.
     */
    public static void validateExpenseEntryData(ExpenseEntry value) throws InsufficientDataException {
        if (value.getCreationUser() == null) {
            throw new InsufficientDataException("Creation user is missing.");
        }

        if (value.getModificationUser() == null) {
            throw new InsufficientDataException("Modiciation user is missing.");
        }

        if (value.getAmount() == null) {
            throw new InsufficientDataException("Expense amount is missing.");
        }

        if (value.getDate() == null) {
            throw new InsufficientDataException("Expense date is missing.");
        }

        if (value.getExpenseType() == null) {
            throw new InsufficientDataException("Expense type is missing.");
        }

        if (value.getStatus() == null) {
            throw new InsufficientDataException("Expense status is missing.");
        }

        if (value.getCompanyId() <= 0) {
            throw new IllegalArgumentException("The ID of company should be positive.");
        }
        
        if (value.getClientId() <= 0) {
            throw new IllegalArgumentException("The ID of client should be positive.");
        }
        
        if (value.getProjectId() <= 0) {
            throw new IllegalArgumentException("The ID of project should be positive.");
        }

        if (value.getExpenseType().getId() <= 0) {
            throw new IllegalArgumentException("The ID of expense type shoule be positive.");
        }

        if (value.getExpenseType().getCompanyId() != value.getCompanyId()) {
            throw new IllegalArgumentException(
                "The company ID of expense type does not match the company id of the entry.");
        }

        if (value.getStatus().getId() <= 0) {
            throw new IllegalArgumentException("The ID of expense status shoule be positive.");
        }

        if (value.getInvoiceId() != -1 && value.getInvoiceId() <= 0) {
            throw new IllegalArgumentException("The ID of invoice is wrong: " + value.getInvoiceId() + ".");
        }

        Map rejectReasons = value.getRejectReasons();

        if (rejectReasons != null) {
            for (Iterator iter = rejectReasons.values().iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (!(obj instanceof RejectReason)) {
                    throw new IllegalArgumentException("The reject reason's type should be correct.");
                }

                RejectReason rejectReason = (RejectReason) obj;

                if (rejectReason.getId() <= 0) {
                    throw new IllegalArgumentException("The ID of reject reason shoule be positive.");
                }

                if (rejectReason.getCompanyId() != value.getCompanyId()) {
                    throw new IllegalArgumentException(
                        "The company ID of reject reason does not match the company id of the entry.");
                }
            }
        }
    }

    /**
     * <p>
     * Validates the data of an <code>ExpenseStatus</code> instance. All properties except description, creation date
     * and modification date should not be <code>null</code>.
     * </p>
     *
     * @param value the <code>ExpenseStatus</code> instance to validate.
     *
     * @throws InsufficientDataException if any property other than description, creation date and modification date is
     *         <code>null</code>.
     */
    public static void validateExpenseStatusData(ExpenseStatus value) throws InsufficientDataException {
        if (value.getCreationUser() == null) {
            throw new InsufficientDataException("Creation user is missing.");
        }

        if (value.getModificationUser() == null) {
            throw new InsufficientDataException("Modiciation user is missing.");
        }
    }

    /**
     * <p>
     * Validates the data of an <code>ExpenseType</code> instance. All properties except description, creation date and
     * modification date should not be <code>null</code>.
     * </p>
     *
     * @param value the <code>ExpenseType</code> instance to validate.
     *
     * @throws InsufficientDataException if any property other than description, creation date and modification date is
     *         <code>null</code>.
     */
    public static void validateExpenseTypeData(ExpenseType value) throws InsufficientDataException {
        if (value.getCreationUser() == null) {
            throw new InsufficientDataException("Creation user is missing.");
        }

        if (value.getModificationUser() == null) {
            throw new InsufficientDataException("Modiciation user is missing.");
        }

        if (value.getCompanyId() <= 0) {
            throw new InsufficientDataException("Company id is missing.");
        }
    }

    /**
     * <p>
     * Generates a random ID for expense entry, expense entry status, or expense entry type. The generated ID cannot be
     * non-positive.
     * </p>
     *
     * @return a random ID, which is positive.
     */
    public static long generateId() {
        int id;

        do {
            // Integer is not enough, since the generated ID is an unsigned integer.
            id = (int) Long.parseLong(UUIDUtility.getNextUUID(UUIDType.TYPEINT32).toString(), HEX_BASE);
        } while (id <= 0);

        return id;
    }

    /**
     * <p>
     * The dao is instantiated in this method from the object factory with the specified SpecificationNamespace and
     * DAOKey value from the environmental entry. The jndi context name for the JNDI Utility is defined in the given
     * namespace.
     * </p>
     *
     * @param namespace the namespace which holds the property value of jndi context name.
     * @param jndiContextProperty the property name for the jndi context name.
     * @param ofNamespaceProperty the property name for the object factory namespace.
     * @param daoKeyProperty the property name for the dao key.
     * @param expected the expected class.
     *
     * @return the created dao instance.
     *
     * @throws CreateException If any error occurs while instantiating.
     */
    public static Object createDAO(String namespace, String jndiContextProperty, String ofNamespaceProperty,
        String daoKeyProperty, Class expected) throws CreateException {
        try {
            String contextName = ExpenseEntryHelper.getStringPropertyValue(namespace, jndiContextProperty, false);
            Context context = (contextName == null) ? JNDIUtils.getDefaultContext() : JNDIUtils.getContext(contextName);

            context = (Context) context.lookup("java:comp/env");

            // obtain the namespace used for ConfigManagerSpecificationFactory to use with ObjectFactory
            String ofNamespace = (String) context.lookup(ofNamespaceProperty);

            // obtain the key for DAO
            String daoKey = (String) context.lookup(daoKeyProperty);

            // create the ExpenseEntryDAO object
            ObjectFactory objectFactory = ExpenseEntryHelper.createObjectFactory(ofNamespace);

            return ExpenseEntryHelper.createObject(objectFactory, daoKey, expected);
        } catch (ConfigurationException e) {
            throw new CreateException("Fails to get the property value from the config manager. Exception: "
                + ExpenseEntryHelper.getExceptionStaceTrace(e));
        } catch (ConfigManagerException e) {
            throw new CreateException("Fails to get the context. Exception: "
                + ExpenseEntryHelper.getExceptionStaceTrace(e));
        } catch (NamingException e) {
            throw new CreateException("Fails to look up the property value. Exception: "
                + ExpenseEntryHelper.getExceptionStaceTrace(e));
        }
    }
}
