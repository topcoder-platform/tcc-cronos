/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;

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
 * Implements <code>ExpenseEntryStatusPersistence</code> interface to provide functionality to manipulate
 * <code>ExpenseEntryStatus</code> instance in database. The operations include adding, updating, deleting and
 * retrieving. The information in expense status is used directly without any modifications. The database connection
 * can be either obtained from the DB connection factory using a connection producer name, or given directly. The
 * expense entry statuses are stored in the database using the following DDL:
 * <pre>
 * CREATE TABLE ExpenseStatuses (
 *        ExpenseStatusesID    integer NOT NULL,
 *        Description          varchar(20) NOT NULL,
 *        CreationDate         datetime year to second NOT NULL,
 *        CreationUser         varchar(64) NOT NULL,
 *        ModificationDate     datetime year to second NOT NULL,
 *        ModificationUser     varchar(64) NOT NULL,
 *        PRIMARY KEY (ExpenseStatusesID)
 * );
 * </pre>
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public class ExpenseEntryStatusDbPersistence implements ExpenseEntryStatusPersistence {
    /** Represents the prepared SQL statement to add an expense status. */
    private static final String ADD_STATUS_SQL = "INSERT INTO ExpenseStatuses (ExpenseStatusesID, Description, "
        + "CreationDate, CreationUser, ModificationDate, ModificationUser) VALUES (?,?,?,?,?,?)";

    /** Represents the prepared SQL statement to update an expense status. */
    private static final String UPDATE_STATUS_SQL = "UPDATE ExpenseStatuses SET Description=?, ModificationDate=?, "
        + "ModificationUser=? WHERE ExpenseStatusesID=?";

    /** Represents the prepared SQL statement to delete an expense status. */
    private static final String DELETE_STATUS_SQL = "DELETE FROM ExpenseStatuses WHERE ExpenseStatusesID=?";

    /** Represents the prepared SQL statement to delete all expense statuses. */
    private static final String DELETE_ALL_STATUS_SQL = "DELETE FROM ExpenseStatuses";

    /** Represents the prepared SQL statement to get an expense status. */
    private static final String RETRIEVE_STATUS_SQL = "SELECT * FROM ExpenseStatuses WHERE ExpenseStatusesID=?";

    /** Represents the prepared SQL statement to get all expense statuses. */
    private static final String RETRIEVE_ALL_STATUS_SQL = "SELECT * FROM ExpenseStatuses";

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

    /** Represents the column name for expense status ID. */
    private static final String ID_COLUMN = "ExpenseStatusesID";

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
     * Creates a new instance of <code>ExpenseEntryStatusDbPersistence</code> class. The DB connection factory is
     * created from the given namespace.
     * </p>
     *
     * @param namespace the namespace to create DB connection factory.
     *
     * @throws NullPointerException if <code>namespace</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>namespace</code> is empty string.
     * @throws ConfigurationException if DB connection factory cannot be created.
     */
    public ExpenseEntryStatusDbPersistence(String namespace) throws ConfigurationException {
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
     * Creates a new instance of <code>ExpenseEntryStatusDbPersistence</code> class. The connection producer name
     * used to obtain database connection is given. The namespace used to create DB connection factory is also
     * given.
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
    public ExpenseEntryStatusDbPersistence(String connectionProducerName, String namespace)
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
     * Adds a new <code>ExpenseEntryStatus</code> instance to the database.
     * </p>
     *
     * @param status the expense entry status to be added to the database.
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry status is added;
     *         <code>false</code> otherwise.
     *
     * @throws NullPointerException if <code>status</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when adding the expense entry status.
     */
    public boolean addStatus(ExpenseEntryStatus status)
        throws PersistenceException {
        PersistenceHelper.validateNotNull(status, "status");

        // Check if the ID already exists in database
        if (retrieveStatus(status.getId()) != null) {
            return false;
        }

        createConnection(connectionProducerName);

        Date now = new Date(new java.util.Date().getTime());
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(ADD_STATUS_SQL);

            // Set parameters
            statement.setInt(INSERT_ID_INDEX, status.getId());
            statement.setString(INSERT_DESCRIPTION_INDEX, status.getDescription());
            statement.setDate(INSERT_CREATIONDATE_INDEX, now);
            statement.setString(INSERT_CREATIONUSER_INDEX, status.getCreationUser());
            statement.setDate(INSERT_MODIFICATIONDATE_INDEX, now);
            statement.setString(INSERT_MODIFICATIONUSER_INDEX, status.getModificationUser());

            // Execute
            statement.executeUpdate();

            // Set property only after successful execution of the query
            status.setCreationDate(now);
            status.setModificationDate(now);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(statement);
        }

        return true;
    }

    /**
     * <p>
     * Deletes an <code>ExpenseEntryStatus</code> instance with the given ID from the database.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be deleted.
     *
     * @return <code>true</code> if the expense entry status exists in database and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry status.
     */
    public boolean deleteStatus(int statusId) throws PersistenceException {
        createConnection(connectionProducerName);

        PreparedStatement statement = null;
        boolean success;

        try {
            statement = connection.prepareStatement(DELETE_STATUS_SQL);

            // Set parameters
            statement.setInt(1, statusId);

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
     * Deletes all <code>ExpenseEntryStatus</code> instances in the database.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry statuses.
     */
    public void deleteAllStatuses() throws PersistenceException {
        createConnection(connectionProducerName);

        Statement statement = null;

        try {
            statement = connection.createStatement();

            statement.execute(DELETE_ALL_STATUS_SQL);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(statement);
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseEntryStatus</code> instance to the database.
     * </p>
     *
     * @param status the expense entry status to be updated in the database.
     *
     * @return <code>true</code> if the expense entry status exists in database and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws NullPointerException if <code>status</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when updating the expense entry status.
     */
    public boolean updateStatus(ExpenseEntryStatus status)
        throws PersistenceException {
        PersistenceHelper.validateNotNull(status, "status");

        createConnection(connectionProducerName);

        Date now = new Date(new java.util.Date().getTime());
        PreparedStatement statement = null;
        boolean success;

        try {
            statement = connection.prepareStatement(UPDATE_STATUS_SQL);

            // Set parameters
            statement.setString(UPDATE_DESCRIPTION_INDEX, status.getDescription());
            statement.setDate(UPDATE_MODIFICATIONDATE_INDEX, now);
            statement.setString(UPDATE_MODIFICATIONUSER_INDEX, status.getModificationUser());
            statement.setInt(UPDATE_ID_INDEX, status.getId());

            // Execute
            success = (statement.executeUpdate() > 0);

            // Set property only after successful execution of the query
            if (success) {
                status.setModificationDate(now);
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
     * Retrieves an <code>ExpenseEntryStatus</code> instance with the given ID from the database.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be retrieved.
     *
     * @return an <code>ExpenseEntryStatus</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry status; or the value in
     *         database is invalid.
     */
    public ExpenseEntryStatus retrieveStatus(int statusId)
        throws PersistenceException {
        createConnection(connectionProducerName);

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExpenseEntryStatus status;

        try {
            statement = connection.prepareStatement(RETRIEVE_STATUS_SQL);

            // Set parameter
            statement.setInt(1, statusId);

            // Execute
            resultSet = statement.executeQuery();

            status = createExpenseEntryStatus(resultSet);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(resultSet);
            PersistenceHelper.close(statement);
        }

        return status;
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseEntryStatus</code> instances from the database.
     * </p>
     *
     * @return a list of <code>ExpenseEntryStatus</code> instances retrieved from the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry statuses; or the value in
     *         database is invalid.
     */
    public List retrieveAllStatuses() throws PersistenceException {
        createConnection(connectionProducerName);

        Statement statement = null;
        ResultSet resultSet = null;
        ExpenseEntryStatus status;
        List list = new ArrayList();

        try {
            statement = connection.createStatement();

            // Execute
            resultSet = statement.executeQuery(RETRIEVE_ALL_STATUS_SQL);

            // For each record, create an expense entry status instance
            while ((status = createExpenseEntryStatus(resultSet)) != null) {
                list.add(status);
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
     * Creates an expense entry status instance from the given result set. The next record in the result set is used
     * to create the instance. If there is no more record in the result set, <code>null</code> is returned.
     * </p>
     *
     * @param resultSet the result set used to create expense entry status instance.
     *
     * @return a new <code>ExpenseEntryStatus</code> instance created from the next record in the result set; or
     *         <code>null</code> if there is no more record.
     *
     * @throws SQLException if error occurs when accessing the result set.
     * @throws PersistenceException if the column value is <code>null</code> or empty string.
     */
    private ExpenseEntryStatus createExpenseEntryStatus(ResultSet resultSet)
        throws SQLException, PersistenceException {
        if (!resultSet.next()) {
            return null;
        }

        ExpenseEntryStatus status;

        try {
            // Set properties
            status = new ExpenseEntryStatus(resultSet.getInt(ID_COLUMN));
            status.setDescription(resultSet.getString(DESCRIPTION_COLUMN));
            status.setCreationDate(resultSet.getDate(CREATION_DATE_COLUMN));
            status.setCreationUser(resultSet.getString(CREATION_USER_COLUMN));
            status.setModificationDate(resultSet.getDate(MODIFICATION_DATE_COLUMN));
            status.setModificationUser(resultSet.getString(MODIFICATION_USER_COLUMN));
        } catch (NullPointerException e) {
            throw new PersistenceException("Column value cannot be null.", e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Column value cannot be empty string.", e);
        }

        return status;
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


