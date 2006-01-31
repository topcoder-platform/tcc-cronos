/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Implements <code>ExpenseEntryTypeDbPersistence</code> interface to provide functionality to manipulate
 * <code>ExpenseEntryType</code> instance in database. The operations include adding, updating, deleting and
 * retrieving. The information in expense type is used directly without any modifications. The database connection
 * can be either obtained from the DB connection factory using a connection producer name, or given directly. The
 * expense entry statuses are stored in the database using the following DDL:
 * <pre>
 * CREATE TABLE ExpenseTypes (
 *        ExpenseTypesID       integer NOT NULL,
 *        Description          varchar(64) NOT NULL,
 *        CreationDate         datetime year to second NOT NULL,
 *        CreationUser         varchar(64) NOT NULL,
 *        ModificationDate     datetime year to second NOT NULL,
 *        ModificationUser     varchar(64) NOT NULL,
 *        PRIMARY KEY (ExpenseTypesID)
 * );
 * </pre>
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public class ExpenseEntryTypeDbPersistence implements ExpenseEntryTypePersistence {
    /** Represents the prepared SQL statement to add an expense type. */
    private static final String ADD_TYPE_SQL = "INSERT INTO ExpenseTypes (ExpenseTypesID, Description, "
        + "CreationDate, CreationUser, ModificationDate, ModificationUser) VALUES (?,?,?,?,?,?)";

    /** Represents the prepared SQL statement to update an expense type. */
    private static final String UPDATE_TYPE_SQL = "UPDATE ExpenseTypes SET Description=?, ModificationDate=?, "
        + "ModificationUser=? WHERE ExpenseTypesID=?";

    /** Represents the prepared SQL statement to delete an expense type. */
    private static final String DELETE_TYPE_SQL = "DELETE FROM ExpenseTypes WHERE ExpenseTypesID=?";

    /** Represents the prepared SQL statement to delete all expense types. */
    private static final String DELETE_ALL_TYPE_SQL = "DELETE FROM ExpenseTypes";

    /** Represents the prepared SQL statement to get an expense type. */
    private static final String RETRIEVE_TYPE_SQL = "SELECT * FROM ExpenseTypes WHERE ExpenseTypesID=?";

    /** Represents the prepared SQL statement to get all expense types. */
    private static final String RETRIEVE_ALL_TYPE_SQL = "SELECT * FROM ExpenseTypes";

    /** Represents the column name for description. */
    private static final String DESCRIPTION_COLUMN = "Description";

    /** Represents the column name for creation date. */
    private static final String CREATION_DATE_COLUMN = "CreationDate";

    /** Represents the column name for creation user. */
    private static final String CREATION_USER_COLUMN = "CreationUser";

    /** Represents the column name for modification date. */
    private static final String MODIFICATION_DATE_COLUMN = "ModificationDate";

    /** Represents the column name for modification user. */
    private static final String MODIFICATION_USER_COLUMN = "ModificationUser";

    /** Represents the column name for expense type ID. */
    private static final String ID_COLUMN = "ExpenseTypesID";

    /** Represents the parameter index of ID in INSERT SQL statement. */
    private static final int INSERT_ID_INDEX = 1;

    /** Represents the parameter index of description in INSERT SQL statement. */
    private static final int INSERT_DESCRIPTION_INDEX = 2;

    /** Represents the parameter index of creation date in INSERT SQL statement. */
    private static final int INSERT_CREATIONDATE_INDEX = 3;

    /** Represents the parameter index of creation user in INSERT SQL statement. */
    private static final int INSERT_CREATIONUSER_INDEX = 4;

    /** Represents the parameter index of modification date in INSERT SQL statement. */
    private static final int INSERT_MODIFICATIONDATE_INDEX = 5;

    /** Represents the parameter index of modification user in INSERT SQL statement. */
    private static final int INSERT_MODIFICATIONUSER_INDEX = 6;

    /** Represents the parameter index of description in UPDATE SQL statement. */
    private static final int UPDATE_DESCRIPTION_INDEX = 1;

    /** Represents the parameter index of modification date in UPDATE SQL statement. */
    private static final int UPDATE_MODIFICATIONDATE_INDEX = 2;

    /** Represents the parameter index of modification user in UPDATE SQL statement. */
    private static final int UPDATE_MODIFICATIONUSER_INDEX = 3;

    /** Represents the parameter index of ID in UPDATE SQL statement. */
    private static final int UPDATE_ID_INDEX = 4;

    /**
     * Represents the current connection used to access database. If it is <code>null</code>, when needed, the
     * connection is obtained from the DB connection factory using the connection producer name.
     */
    private Connection connection = null;

    /** Represents the connection producer name used to obtain a new database connection from DB connection factory. */
    private String connectionProducerName = null;

    /** Represents the namespace used to create DB connection factory. */
    private String namespace = null;

    /** Represents the DB connection factory instance used to create connections. */
    private DBConnectionFactory factory = null;

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryTypeDbPersistence</code> class. The DB connection factory is
     * created from the given namespace.
     * </p>
     *
     * @param namespace the namespace to create DB connection factory.
     *
     * @throws NullPointerException if <code>namespace</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>namespace</code> is empty string.
     * @throws ConfigurationException if DB connection factory cannot be created.
     */
    public ExpenseEntryTypeDbPersistence(String namespace) throws ConfigurationException {
        PersistenceHelper.validateNotNullOrEmpty(namespace, "namespace");

        this.namespace = namespace;

        try {
            factory = new DBConnectionFactoryImpl(this.namespace);
        } catch (DBConnectionException e) {
            throw new ConfigurationException("DB connection factory cannot be created.", e);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            throw new ConfigurationException("DB connection factory cannot be created.", e);
        }
    }

    /**
     * <p>
     * Creates a new instance of <code>ExpenseEntryTypeDbPersistence</code> class. The connection producer name used
     * to obtain database connection is given. The namespace used to create DB connection factory is also given.
     * </p>
     *
     * @param connectionProducerName the connection producer name to obtain database connection from DB connection
     *        factory.
     * @param namespace the namespace to create DB connection factory.
     *
     * @throws NullPointerException if <code>connectionProducerName</code> or <code>namespace</code> is
     *         <code>null</code>.
     * @throws IllegalArgumentException <code>connectionProducerName</code> or <code>namespace</code> is empty
     *         string.
     * @throws PersistenceException if <code>connectionProducerName</code> does not exist in DB connection factory.
     */
    public ExpenseEntryTypeDbPersistence(String connectionProducerName, String namespace)
        throws PersistenceException {
        PersistenceHelper.validateNotNullOrEmpty(connectionProducerName, "connectionProducerName");
        PersistenceHelper.validateNotNullOrEmpty(namespace, "namespace");

        this.namespace = namespace;
        this.connectionProducerName = connectionProducerName;

        try {
            factory = new DBConnectionFactoryImpl(namespace);
        } catch (DBConnectionException e) {
            throw new PersistenceException("DB connection factory cannot be created.", e);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            throw new PersistenceException("DB connection factory cannot be created.", e);
        }

        if (!((DBConnectionFactoryImpl) factory).contains(connectionProducerName)) {
            throw new PersistenceException("The connection producer is not available.");
        }
    }

    /**
     * <p>
     * Adds a new <code>ExpenseEntryType</code> instance to the database.
     * </p>
     *
     * @param type the expense entry type to be added to the database.
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry type is added;
     *         <code>false</code> otherwise.
     *
     * @throws NullPointerException if <code>type</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when adding the expense entry type.
     */
    public boolean addType(ExpenseEntryType type) throws PersistenceException {
        PersistenceHelper.validateNotNull(type, "type");

        // Check if the ID already exists in database
        if (retrieveType(type.getId()) != null) {
            return false;
        }

        createConnection(connectionProducerName);

        Date now = new Date(new java.util.Date().getTime());
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(ADD_TYPE_SQL);

            // Set parameters
            statement.setInt(INSERT_ID_INDEX, type.getId());
            statement.setString(INSERT_DESCRIPTION_INDEX, type.getDescription());
            statement.setDate(INSERT_CREATIONDATE_INDEX, now);
            statement.setString(INSERT_CREATIONUSER_INDEX, type.getCreationUser());
            statement.setDate(INSERT_MODIFICATIONDATE_INDEX, now);
            statement.setString(INSERT_MODIFICATIONUSER_INDEX, type.getModificationUser());

            // Execute
            statement.executeUpdate();

            // Set property only after successful execution of the query
            type.setCreationDate(now);
            type.setModificationDate(now);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(statement);
        }

        return true;
    }

    /**
     * <p>
     * Deletes an <code>ExpenseEntryType</code> instance with the given ID from the database.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be deleted.
     *
     * @return <code>true</code> if the expense entry type exists in database and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry type.
     */
    public boolean deleteType(int typeId) throws PersistenceException {
        createConnection(connectionProducerName);

        PreparedStatement statement = null;
        boolean success;

        try {
            statement = connection.prepareStatement(DELETE_TYPE_SQL);

            // Set parameters
            statement.setInt(1, typeId);

            // Execute
            success = (statement.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(statement);
        }

        return success;
    }

    /**
     * <p>
     * Deletes all <code>ExpenseEntryType</code> instances in the database.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry types.
     */
    public void deleteAllTypes() throws PersistenceException {
        createConnection(connectionProducerName);

        Statement statement = null;

        try {
            statement = connection.createStatement();

            statement.execute(DELETE_ALL_TYPE_SQL);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(statement);
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseEntryType</code> instance to the database.
     * </p>
     *
     * @param type the expense entry type to be updated in the database.
     *
     * @return <code>true</code> if the expense entry type exists in database and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws NullPointerException if <code>type</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when updating the expense entry type.
     */
    public boolean updateType(ExpenseEntryType type) throws PersistenceException {
        PersistenceHelper.validateNotNull(type, "type");

        createConnection(connectionProducerName);

        Date now = new Date(new java.util.Date().getTime());
        PreparedStatement statement = null;
        boolean success;

        try {
            statement = connection.prepareStatement(UPDATE_TYPE_SQL);

            // Set parameters
            statement.setString(UPDATE_DESCRIPTION_INDEX, type.getDescription());
            statement.setDate(UPDATE_MODIFICATIONDATE_INDEX, now);
            statement.setString(UPDATE_MODIFICATIONUSER_INDEX, type.getModificationUser());
            statement.setInt(UPDATE_ID_INDEX, type.getId());

            // Execute
            success = (statement.executeUpdate() > 0);

            // Set property only after successful execution of the query
            if (success) {
                type.setModificationDate(now);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(statement);
        }

        return success;
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseEntryType</code> instance with the given ID from the database.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be retrieved.
     *
     * @return an <code>ExpenseEntryType</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry type; or the value in
     *         persistence is invalid.
     */
    public ExpenseEntryType retrieveType(int typeId) throws PersistenceException {
        createConnection(connectionProducerName);

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExpenseEntryType type;

        try {
            statement = connection.prepareStatement(RETRIEVE_TYPE_SQL);

            // Set parameter
            statement.setInt(1, typeId);

            // Execute
            resultSet = statement.executeQuery();

            type = createExpenseEntryType(resultSet);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(resultSet);
            PersistenceHelper.close(statement);
        }

        return type;
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseEntryType</code> instances from the database.
     * </p>
     *
     * @return a list of <code>ExpenseEntryType</code> instances retrieved from the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry types; or the value in database
     *         is invalid.
     */
    public List retrieveAllTypes() throws PersistenceException {
        createConnection(connectionProducerName);

        Statement statement = null;
        ResultSet resultSet = null;
        ExpenseEntryType type;
        List list = new ArrayList();

        try {
            statement = connection.createStatement();

            // Execute
            resultSet = statement.executeQuery(RETRIEVE_ALL_TYPE_SQL);

            // For each record, create an expense entry type instance
            while ((type = createExpenseEntryType(resultSet)) != null) {
                list.add(type);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(resultSet);
            PersistenceHelper.close(statement);
        }

        return list;
    }

    /**
     * <p>
     * Sets the existing connection which is used to access database for future operations. The previous connection
     * is closed if it exists.
     * </p>
     *
     * @param connection the connection used to access database.
     *
     * @throws NullPointerException if <code>connection</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when closing the existing connection.
     */
    public void setConnection(Connection connection) throws PersistenceException {
        PersistenceHelper.validateNotNull(connection, "connection");

        closeConnection();

        this.connection = connection;
    }

    /**
     * <p>
     * Gets the existing connection. If there is no existing connection, <code>null</code> is returned.
     * </p>
     *
     * @return the existing connection if it is available; <code>null</code> otherwise.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * <p>
     * Initializes the existing connection which is used to access database for future operations. The previous
     * connection is closed if it exists. The new connection is created from DB connection factory according to the
     * given connection producer name.
     * </p>
     *
     * @param connectionProducerName the connection producer name to obtain a connection from DB connection factory.
     *
     * @throws NullPointerException if <code>connectionProducerName</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>connectionProducerName</code> is empty string.
     * @throws PersistenceException if error occurs when closing the previous connection; or error occurs when
     *         obtaining the new connection from DB connection factory.
     */
    public void initConnection(String connectionProducerName)
        throws PersistenceException {
        PersistenceHelper.validateNotNullOrEmpty(connectionProducerName, "connectionProducerName");

        closeConnection();

        createConnection(connectionProducerName);
    }

    /**
     * <p>
     * Creates a database connection using the DB connection factory and connection producer name if the current
     * connection is <code>null</code>. The connection field is set. If the current connection already exists, it
     * does nothing.
     * </p>
     *
     * @param connectionProducerName the DB connection producer name.
     *
     * @throws PersistenceException if the connection producer name is <code>null</code> or empty string; or the
     *         connection producer name does not exist in DB connection factory; or database connection cannot be
     *         created.
     */
    private void createConnection(String connectionProducerName)
        throws PersistenceException {
        if (connection != null) {
            return;
        }

        try {
            connection = factory.createConnection(connectionProducerName);
        } catch (DBConnectionException e) {
            throw new PersistenceException("Database connection cannot be created.", e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("The connection producer name cannot be empty string.", e);
        } catch (NullPointerException e) {
            throw new PersistenceException("The connection producer name cannot be null.", e);
        }
    }

    /**
     * <p>
     * Creates an expense entry type instance from the given result set. The next record in the result set is used to
     * create the instance. If there is no more record in the result set, <code>null</code> is returned.
     * </p>
     *
     * @param resultSet the result set used to create expense entry type instance.
     *
     * @return a new <code>ExpenseEntryType</code> instance created from the next record in the result set; or
     *         <code>null</code> if there is no more record.
     *
     * @throws SQLException if error occurs when accessing the result set.
     * @throws PersistenceException if the column value is <code>null</code> or empty string.
     */
    private ExpenseEntryType createExpenseEntryType(ResultSet resultSet)
        throws SQLException, PersistenceException {
        if (!resultSet.next()) {
            return null;
        }

        ExpenseEntryType type;

        try {
            // Set properties
            type = new ExpenseEntryType(resultSet.getInt(ID_COLUMN));
            type.setDescription(resultSet.getString(DESCRIPTION_COLUMN));
            type.setCreationDate(resultSet.getDate(CREATION_DATE_COLUMN));
            type.setCreationUser(resultSet.getString(CREATION_USER_COLUMN));
            type.setModificationDate(resultSet.getDate(MODIFICATION_DATE_COLUMN));
            type.setModificationUser(resultSet.getString(MODIFICATION_USER_COLUMN));
        } catch (NullPointerException e) {
            throw new PersistenceException("Column value cannot be null.", e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Column value cannot be empty string.", e);
        }

        return type;
    }

    /**
     * <p>
     * Closes the existing connection if available.
     * </p>
     *
     * @throws PersistenceException if error occurs when closing the connection.
     */
    public void closeConnection() throws PersistenceException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new PersistenceException("Error occurs when closing connection.", e);
            }

            connection = null;
        }
    }
}


