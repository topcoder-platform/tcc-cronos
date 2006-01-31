/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryStatus;
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
 * Implements <code>ExpenseEntryPersistence</code> interface to provide functionality to manipulate
 * <code>ExpenseEntry</code> instance in database. The operations include adding, updating, deleting and retrieving.
 * The information in expense entry is used directly without any modifications. The database connection can be
 * either obtained from the DB connection factory using a connection producer name, or given directly. The expense
 * entries are stored in the database using the following DDL:
 * <pre>
 * CREATE TABLE ExpenseEntries (
 *        ExpenseEntriesID     integer NOT NULL,
 *        ExpenseTypesID       integer NOT NULL,
 *        ExpenseStatusesID    integer NOT NULL,
 *        Description          varchar(64) NOT NULL,
 *        EntryDate            datetime year to second NOT NULL,
 *        Amount               money NOT NULL,
 *        Billable             smallint NOT NULL,
 *        CreationDate         datetime year to second NOT NULL,
 *        CreationUser         varchar(64) NOT NULL,
 *        ModificationDate     datetime year to second NOT NULL,
 *        ModificationUser     varchar(64) NOT NULL,
 *        PRIMARY KEY (ExpenseEntriesID)
 * );
 * </pre>
 * </p>
 *
 * @author DanLazar, visualage
 * @version 1.0
 */
public class ExpenseEntryDbPersistence implements ExpenseEntryPersistence {
    /** Represents the prepared SQL statement to add an expense entry. */
    private static final String ADD_ENTRY_SQL = "INSERT INTO ExpenseEntries (ExpenseEntriesID, ExpenseTypesID, "
        + "ExpenseStatusesID, Description, EntryDate, Amount, Billable, CreationDate, CreationUser, ModificationDate, "
        + "ModificationUser) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    /** Represents the prepared SQL statement to update an expense entry. */
    private static final String UPDATE_ENTRY_SQL = "UPDATE ExpenseEntries SET ExpenseTypesID=?, ExpenseStatusesID=?, "
        + "Description=?, EntryDate=?, Amount=?, Billable=?, ModificationDate=?, ModificationUser=? "
        + "WHERE ExpenseEntriesID=?";

    /** Represents the prepared SQL statement to delete an expense entry. */
    private static final String DELETE_ENTRY_SQL = "DELETE FROM ExpenseEntries WHERE ExpenseEntriesID=?";

    /** Represents the prepared SQL statement to delete all expense entries. */
    private static final String DELETE_ALL_ENTRY_SQL = "DELETE FROM ExpenseEntries";

    /** Represents the prepared SQL statement to get an expense entry, including its entry type and status. */
    private static final String RETRIEVE_ENTRY_SQL = "SELECT E.ExpenseEntriesID, E.ExpenseTypesID, "
        + "E.ExpenseStatusesID, E.Description, E.EntryDate, E.Amount, E.Billable, E.CreationDate, E.CreationUser, "
        + "E.ModificationDate, E.ModificationUser, T.Description AS TypeDescription, "
        + "T.CreationDate AS TypeCreationDate, T.CreationUser AS TypeCreationUser, "
        + "T.ModificationDate AS TypeModificationDate, T.ModificationUser AS TypeModificationUser, "
        + "S.Description AS StatusDescription, S.CreationDate AS StatusCreationDate, "
        + "S.CreationUser AS StatusCreationUser, S.ModificationDate AS StatusModificationDate, "
        + "S.ModificationUser AS StatusModificationUser FROM ExpenseEntries AS E "
        + "LEFT OUTER JOIN ExpenseTypes AS T ON E.ExpenseTypesID = T.ExpenseTypesID "
        + "LEFT OUTER JOIN ExpenseStatuses AS S ON E.ExpenseStatusesID = S.ExpenseStatusesID "
        + "WHERE E.ExpenseEntriesID=?";

    /** Represents the prepared SQL statement to get all expense entries, including their entry types and statuses. */
    private static final String RETRIEVE_ALL_ENTRY_SQL = "SELECT E.ExpenseEntriesID, E.ExpenseTypesID, "
        + "E.ExpenseStatusesID, E.Description, E.EntryDate, E.Amount, E.Billable, E.CreationDate, E.CreationUser, "
        + "E.ModificationDate, E.ModificationUser, T.Description AS TypeDescription, "
        + "T.CreationDate AS TypeCreationDate, T.CreationUser AS TypeCreationUser, "
        + "T.ModificationDate AS TypeModificationDate, T.ModificationUser AS TypeModificationUser, "
        + "S.Description AS StatusDescription, S.CreationDate AS StatusCreationDate, "
        + "S.CreationUser AS StatusCreationUser, S.ModificationDate AS StatusModificationDate, "
        + "S.ModificationUser AS StatusModificationUser FROM ExpenseEntries AS E "
        + "LEFT OUTER JOIN ExpenseTypes AS T ON E.ExpenseTypesID = T.ExpenseTypesID "
        + "LEFT OUTER JOIN ExpenseStatuses AS S ON E.ExpenseStatusesID = S.ExpenseStatusesID";

    /** Represents the column name for expense entry ID. */
    private static final String ID_COLUMN = "ExpenseEntriesID";

    /** Represents the column name for the expense type ID of the expense entry. */
    private static final String TYPE_ID_COLUMN = "ExpenseTypesID";

    /** Represents the column name for the expense status ID of the expense entry. */
    private static final String STATUS_ID_COLUMN = "ExpenseStatusesID";

    /** Represents the column name for expense entry description. */
    private static final String DESCRIPTION_COLUMN = "Description";

    /** Represents the column name for expense entry date. */
    private static final String DATE_COLUMN = "EntryDate";

    /** Represents the column name for expense entry amount. */
    private static final String AMOUNT_COLUMN = "Amount";

    /** Represents the column name for a flag indicating whether the expense entry is billable. */
    private static final String BILLABLE_COLUMN = "Billable";

    /** Represents the column name for expense entry creation date. */
    private static final String CREATION_DATE_COLUMN = "CreationDate";

    /** Represents the column name for expense entry creation user. */
    private static final String CREATION_USER_COLUMN = "CreationUser";

    /** Represents the column name for expense entry modification date. */
    private static final String MODIFICATION_DATE_COLUMN = "ModificationDate";

    /** Represents the column name for expense entry modification user. */
    private static final String MODIFICATION_USER_COLUMN = "ModificationUser";

    /** Represents the column name for expense entry type description. */
    private static final String TYPE_DESCRIPTION_COLUMN = "TypeDescription";

    /** Represents the column name for expense entry type creation date. */
    private static final String TYPE_CREATION_DATE_COLUMN = "TypeCreationDate";

    /** Represents the column name for expense entry type creation user. */
    private static final String TYPE_CREATION_USER_COLUMN = "TypeCreationUser";

    /** Represents the column name for expense entry type modification date. */
    private static final String TYPE_MODIFICATION_DATE_COLUMN = "TypeModificationDate";

    /** Represents the column name for expense entry type modification user. */
    private static final String TYPE_MODIFICATION_USER_COLUMN = "TypeModificationUser";

    /** Represents the column name for expense entry status description. */
    private static final String STATUS_DESCRIPTION_COLUMN = "StatusDescription";

    /** Represents the column name for expense entry status creation date. */
    private static final String STATUS_CREATION_DATE_COLUMN = "StatusCreationDate";

    /** Represents the column name for expense entry status creation user. */
    private static final String STATUS_CREATION_USER_COLUMN = "StatusCreationUser";

    /** Represents the column name for expense entry status modification date. */
    private static final String STATUS_MODIFICATION_DATE_COLUMN = "StatusModificationDate";

    /** Represents the column name for expense entry status modification user. */
    private static final String STATUS_MODIFICATION_USER_COLUMN = "StatusModificationUser";

    /** Represents the parameter index of ID in INSERT SQL statement. */
    private static final int INSERT_ID_INDEX = 1;

    /** Represents the parameter index of expense entry type ID in INSERT SQL statement. */
    private static final int INSERT_TYPEID_INDEX = 2;

    /** Represents the parameter index of expense entry status ID in INSERT SQL statement. */
    private static final int INSERT_STATUSID_INDEX = 3;

    /** Represents the parameter index of description in INSERT SQL statement. */
    private static final int INSERT_DESCRIPTION_INDEX = 4;

    /** Represents the parameter index of date in INSERT SQL statement. */
    private static final int INSERT_DATE_INDEX = 5;

    /** Represents the parameter index of amount of money in INSERT SQL statement. */
    private static final int INSERT_AMOUNT_INDEX = 6;

    /** Represents the parameter index of billable flag in INSERT SQL statement. */
    private static final int INSERT_BILLABLE_INDEX = 7;

    /** Represents the parameter index of creation date in INSERT SQL statement. */
    private static final int INSERT_CREATIONDATE_INDEX = 8;

    /** Represents the parameter index of creation user in INSERT SQL statement. */
    private static final int INSERT_CREATIONUSER_INDEX = 9;

    /** Represents the parameter index of modification date in INSERT SQL statement. */
    private static final int INSERT_MODIFICATIONDATE_INDEX = 10;

    /** Represents the parameter index of modification user in INSERT SQL statement. */
    private static final int INSERT_MODIFICATIONUSER_INDEX = 11;

    /** Represents the parameter index of task type ID in UPDATE SQL statement. */
    private static final int UPDATE_TYPEID_INDEX = 1;

    /** Represents the parameter index of time status ID in UPDATE SQL statement. */
    private static final int UPDATE_STATUSID_INDEX = 2;

    /** Represents the parameter index of description in UPDATE SQL statement. */
    private static final int UPDATE_DESCRIPTION_INDEX = 3;

    /** Represents the parameter index of date in UPDATE SQL statement. */
    private static final int UPDATE_DATE_INDEX = 4;

    /** Represents the parameter index of amount of money in UPDATE SQL statement. */
    private static final int UPDATE_AMOUNT_INDEX = 5;

    /** Represents the parameter index of billable flag in UPDATE SQL statement. */
    private static final int UPDATE_BILLABLE_INDEX = 6;

    /** Represents the parameter index of modification date in UPDATE SQL statement. */
    private static final int UPDATE_MODIFICATIONDATE_INDEX = 7;

    /** Represents the parameter index of modification user in UPDATE SQL statement. */
    private static final int UPDATE_MODIFICATIONUSER_INDEX = 8;

    /** Represents the parameter index of ID in UPDATE SQL statement. */
    private static final int UPDATE_ID_INDEX = 9;

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
     * Creates a new instance of <code>ExpenseEntryDbPersistence</code> class. The DB connection factory is created
     * from the given namespace.
     * </p>
     *
     * @param namespace the namespace to create DB connection factory.
     *
     * @throws NullPointerException if <code>namespace</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>namespace</code> is empty string.
     * @throws ConfigurationException if DB connection factory cannot be created.
     */
    public ExpenseEntryDbPersistence(String namespace) throws ConfigurationException {
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
     * Creates a new instance of <code>ExpenseEntryDbPersistence</code> class. The connection producer name used to
     * obtain database connection is given. The namespace used to create DB connection factory is also given.
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
    public ExpenseEntryDbPersistence(String connectionProducerName, String namespace)
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
     * Adds a new <code>ExpenseEntry</code> instance to the database.
     * </p>
     *
     * @param entry the expense entry to be added to the database.
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry is added;
     *         <code>false</code> otherwise.
     *
     * @throws NullPointerException if <code>entry</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when adding the expense entry to the persistence.
     */
    public boolean addEntry(ExpenseEntry entry) throws PersistenceException {
        PersistenceHelper.validateNotNull(entry, "entry");

        // Check if the ID already exists in database
        if (retrieveEntry(entry.getId()) != null) {
            return false;
        }

        createConnection(connectionProducerName);

        Date now = new Date(new java.util.Date().getTime());
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(ADD_ENTRY_SQL);

            // Set parameters
            statement.setInt(INSERT_ID_INDEX, entry.getId());
            statement.setInt(INSERT_TYPEID_INDEX, entry.getExpenseType().getId());
            statement.setInt(INSERT_STATUSID_INDEX, entry.getStatus().getId());
            statement.setString(INSERT_DESCRIPTION_INDEX, entry.getDescription());
            statement.setDate(INSERT_DATE_INDEX, new Date(entry.getDate().getTime()));
            statement.setBigDecimal(INSERT_AMOUNT_INDEX, entry.getAmount());
            statement.setShort(INSERT_BILLABLE_INDEX, entry.isBillable() ? (short) 1 : (short) 0);
            statement.setDate(INSERT_CREATIONDATE_INDEX, now);
            statement.setString(INSERT_CREATIONUSER_INDEX, entry.getCreationUser());
            statement.setDate(INSERT_MODIFICATIONDATE_INDEX, now);
            statement.setString(INSERT_MODIFICATIONUSER_INDEX, entry.getModificationUser());

            // Execute
            statement.executeUpdate();

            // Set property only after successful execution of the query
            entry.setCreationDate(now);
            entry.setModificationDate(now);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(statement);
        }

        return true;
    }

    /**
     * <p>
     * Deletes an <code>ExpenseEntry</code> instance with the given ID from the database.
     * </p>
     *
     * @param entryId the ID of the expense entry to be deleted.
     *
     * @return <code>true</code> if the expense entry exists in database and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry.
     */
    public boolean deleteEntry(int entryId) throws PersistenceException {
        createConnection(connectionProducerName);

        PreparedStatement statement = null;
        boolean success;

        try {
            statement = connection.prepareStatement(DELETE_ENTRY_SQL);

            // Set parameters
            statement.setInt(1, entryId);

            // Execute
            success = (statement.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } catch (NullPointerException e) {
            throw new PersistenceException("Status or type is null.", e);
        } finally {
            PersistenceHelper.close(statement);
        }

        return success;
    }

    /**
     * <p>
     * Deletes all <code>ExpenseEntry</code> instances in the database.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entries.
     */
    public void deleteAllEntries() throws PersistenceException {
        createConnection(connectionProducerName);

        Statement statement = null;

        try {
            statement = connection.createStatement();

            statement.execute(DELETE_ALL_ENTRY_SQL);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(statement);
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseEntry</code> instance to the database.
     * </p>
     *
     * @param entry the expense entry to be updated in the database.
     *
     * @return <code>true</code> if the expense entry exists in database and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws NullPointerException if <code>entry</code> is <code>null</code>.
     * @throws PersistenceException if error occurs when updating the expense entry.
     */
    public boolean updateEntry(ExpenseEntry entry) throws PersistenceException {
        PersistenceHelper.validateNotNull(entry, "entry");

        createConnection(connectionProducerName);

        Date now = new Date(new java.util.Date().getTime());
        PreparedStatement statement = null;
        boolean success;

        try {
            statement = connection.prepareStatement(UPDATE_ENTRY_SQL);

            // Set parameters
            statement.setInt(UPDATE_TYPEID_INDEX, entry.getExpenseType().getId());
            statement.setInt(UPDATE_STATUSID_INDEX, entry.getStatus().getId());
            statement.setString(UPDATE_DESCRIPTION_INDEX, entry.getDescription());
            statement.setDate(UPDATE_DATE_INDEX, new Date(entry.getDate().getTime()));
            statement.setBigDecimal(UPDATE_AMOUNT_INDEX, entry.getAmount());
            statement.setShort(UPDATE_BILLABLE_INDEX, entry.isBillable() ? (short) 1 : (short) 0);
            statement.setDate(UPDATE_MODIFICATIONDATE_INDEX, now);
            statement.setString(UPDATE_MODIFICATIONUSER_INDEX, entry.getModificationUser());
            statement.setInt(UPDATE_ID_INDEX, entry.getId());

            // Execute
            success = (statement.executeUpdate() > 0);

            // Set property only after successful execution of the query
            if (success) {
                entry.setModificationDate(now);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } catch (NullPointerException e) {
            throw new PersistenceException("Status or type is null.", e);
        } finally {
            PersistenceHelper.close(statement);
        }

        return success;
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseEntry</code> instance with the given ID from the database. The related
     * <code>ExpenseEntryType</code> and <code>ExpenseEntryStatus</code> instances are also retrieved.
     * </p>
     *
     * @param entryId the ID of the expense entry to be retrieved.
     *
     * @return an <code>ExpenseEntry</code> instance with the given ID; or <code>null</code> if such instance cannot
     *         be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry; or the value in database is
     *         invalid.
     */
    public ExpenseEntry retrieveEntry(int entryId) throws PersistenceException {
        createConnection(connectionProducerName);

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExpenseEntry entry;

        try {
            statement = connection.prepareStatement(RETRIEVE_ENTRY_SQL);

            // Set parameter
            statement.setInt(1, entryId);

            // Execute
            resultSet = statement.executeQuery();

            entry = createExpenseEntry(resultSet);
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(resultSet);
            PersistenceHelper.close(statement);
        }

        return entry;
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseEntry</code> instances from the database. The related <code>ExpenseEntryType</code>
     * and <code>ExpenseEntryStatus</code> instances are also retrieved.
     * </p>
     *
     * @return a list of <code>ExpenseEntry</code> instances retrieved from the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entries; or the value in database is
     *         invalid.
     */
    public List retrieveAllEntries() throws PersistenceException {
        createConnection(connectionProducerName);

        Statement statement = null;
        ResultSet resultSet = null;
        ExpenseEntry entry;
        List list = new ArrayList();

        try {
            statement = connection.createStatement();

            // Execute
            resultSet = statement.executeQuery(RETRIEVE_ALL_ENTRY_SQL);

            // For each record, create an expense entry status instance
            while ((entry = createExpenseEntry(resultSet)) != null) {
                list.add(entry);
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
     * to create the instance. If there is no more record in the result set, <code>null</code> is returned. The
     * entry type and entry status instances are also created from the result set.
     * </p>
     *
     * @param resultSet the result set used to create expense entry status instance.
     *
     * @return a new <code>ExpenseEntryStatus</code> instance created from the next record in the result set; or
     *         <code>null</code> if there is no more record.
     *
     * @throws SQLException if error occurs when accessing the result set.
     * @throws PersistenceException if the flag for billable is neither 0 nor 1; or the column value is
     *         <code>null</code>, empty string or invalid value.
     */
    private ExpenseEntry createExpenseEntry(ResultSet resultSet)
        throws SQLException, PersistenceException {
        if (!resultSet.next()) {
            return null;
        }

        ExpenseEntry entry;

        try {
            // Set properties
            entry = new ExpenseEntry(resultSet.getInt(ID_COLUMN));
            entry.setDescription(resultSet.getString(DESCRIPTION_COLUMN));
            entry.setCreationDate(resultSet.getDate(CREATION_DATE_COLUMN));
            entry.setCreationUser(resultSet.getString(CREATION_USER_COLUMN));
            entry.setModificationDate(resultSet.getDate(MODIFICATION_DATE_COLUMN));
            entry.setModificationUser(resultSet.getString(MODIFICATION_USER_COLUMN));
            entry.setAmount(resultSet.getBigDecimal(AMOUNT_COLUMN));
            entry.setDate(resultSet.getDate(DATE_COLUMN));

            // Set billable
            switch (resultSet.getShort(BILLABLE_COLUMN)) {
            case 0:
                entry.setBillable(false);

                break;

            case 1:
                entry.setBillable(true);

                break;

            default:
                throw new PersistenceException("Billable column is neither 0 nor 1.");
            }

            // Create type and status
            ExpenseEntryStatus status = new ExpenseEntryStatus(resultSet.getInt(STATUS_ID_COLUMN));
            ExpenseEntryType type = new ExpenseEntryType(resultSet.getInt(TYPE_ID_COLUMN));

            status.setDescription(resultSet.getString(STATUS_DESCRIPTION_COLUMN));
            status.setCreationDate(resultSet.getDate(STATUS_CREATION_DATE_COLUMN));
            status.setCreationUser(resultSet.getString(STATUS_CREATION_USER_COLUMN));
            status.setModificationDate(resultSet.getDate(STATUS_MODIFICATION_DATE_COLUMN));
            status.setModificationUser(resultSet.getString(STATUS_MODIFICATION_USER_COLUMN));

            type.setDescription(resultSet.getString(TYPE_DESCRIPTION_COLUMN));
            type.setCreationDate(resultSet.getDate(TYPE_CREATION_DATE_COLUMN));
            type.setCreationUser(resultSet.getString(TYPE_CREATION_USER_COLUMN));
            type.setModificationDate(resultSet.getDate(TYPE_MODIFICATION_DATE_COLUMN));
            type.setModificationUser(resultSet.getString(TYPE_MODIFICATION_USER_COLUMN));

            // Set type and status
            entry.setExpenseType(type);
            entry.setStatus(status);
        } catch (NullPointerException e) {
            throw new PersistenceException("Column value cannot be null.", e);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Column value cannot be empty string.", e);
        }

        return entry;
    }
}


