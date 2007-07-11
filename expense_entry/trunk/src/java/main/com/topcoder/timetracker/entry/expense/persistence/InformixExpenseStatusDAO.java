/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;
import com.topcoder.timetracker.entry.expense.ExpenseStatus;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;

import com.topcoder.util.objectfactory.ObjectFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * This class is a concrete implementation of the <code>ExpenseEntryStatusDAO</code> interface that uses an Informix
 * database as the data store for the persistence of <code>ExpenseStatus</code> instances. This implementation uses
 * the DB Connection Factory component to obtain a connection to the database, the GUID Generator component to create
 * IDs for new Expense Entry Statuses. This class provides two constructors that will use Config Manager and Object
 * Factory components to obtain instances of the connection factory. One constructor will take a namespace, and the
 * second will work with a default namespace. The expense entry statuses are stored in the database using the
 * following DDL:
 * <pre>
 * CREATE TABLE expense_status (
 *     expense_status_id    integer NOT NULL,
 *     description          varchar(255),
 *     creation_date        datetime year to second NOT NULL,
 *     creation_user        varchar(64) NOT NULL,
 *     modification_date    datetime year to second NOT NULL,
 *     modification_user    varchar(64) NOT NULL,
 *     PRIMARY KEY (expense_status_id)
 * );
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is immutable and thread-safe as long as the <code>ExpenseStatus</code> is no
 * manipulated by more than one thread.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class InformixExpenseStatusDAO implements ExpenseEntryStatusDAO {
    /** Represents the prepared SQL statement to add an expense status. */
    private static final String ADD_STATUS_SQL = "INSERT INTO expense_status(expense_status_id, description, "
        + "creation_date, creation_user, modification_date, modification_user) VALUES (?,?,?,?,?,?)";

    /** Represents the prepared SQL statement to update an expense status. */
    private static final String UPDATE_STATUS_SQL = "UPDATE expense_status SET description=?, modification_date=?, "
        + "modification_user=? WHERE expense_status_id=?";

    /** Represents the prepared SQL statement to delete an expense status. */
    private static final String DELETE_STATUS_SQL = "DELETE FROM expense_status WHERE expense_status_id=?";

    /** Represents the prepared SQL statement to delete all expense statuses. */
    private static final String DELETE_ALL_STATUS_SQL = "DELETE FROM expense_status";

    /** Represents the prepared SQL statement to get an expense status. */
    private static final String RETRIEVE_STATUS_SQL = "SELECT * FROM expense_status WHERE expense_status_id=?";

    /** Represents the prepared SQL statement to check the existence of an expense status. */
    private static final String EXIST_TYPE_SQL = "SELECT 1 counts FROM expense_status WHERE expense_status_id=?";

    /** Represents the prepared SQL statement to get all expense statuses. */
    private static final String RETRIEVE_ALL_STATUS_SQL = "SELECT * FROM expense_status";

    /** Represents the column name for description. */
    private static final String DESCRIPTION_COLUMN = "description";

    /** Represents the column name for creation date. */
    private static final String CREATION_DATE_COLUMN = "creation_date";

    /** Represents the column name for creation user. */
    private static final String CREATION_USER_COLUMN = "creation_user";

    /** Represents the column name for modification date. */
    private static final String MODIFICATION_DATE_COLUMN = "modification_date";

    /** Represents the column name for modification user. */
    private static final String MODIFICATION_USER_COLUMN = "modification_user";

    /** Represents the column name for expense status ID. */
    private static final String ID_COLUMN = "expense_status_id";

    /** Represents the property name to retrieve the connection_name value. */
    private static final String CONNECTION_NAME_PROPERTY = "connection_name";

    /** Represents the property name to retrieve the specification_namespace value. */
    private static final String SPECIFICATION_NAMESPACE_PROPERTY = "specification_namespace";

    /** Represents the property name to retrieve the db_connection_factory_key value. */
    private static final String DB_CONNECTION_FACTORY_KEY_PROPERTY = "db_connection_factory_key";

    /**
     * <p>
     * Represents the connection producer name used to obtain a new database connection from DB connection factory.
     * </p>
     *
     * <p>
     * This variable is set in constructor. It is possible null, possible empty(trim'd) which means the default
     * connection name will be used.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the DB connection factory instance used to create connections.
     * </p>
     *
     * <p>
     * This variable is set in constructor. It is immutable and never be null.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Creates a new instance of <code>InformixExpenseStatusDAO</code> class with default namespace to load the
     * connection name and DB connection factory.
     * </p>
     *
     * @throws ConfigurationException if any exception prevents creating the connection factory successfully.
     */
    public InformixExpenseStatusDAO() throws ConfigurationException {
        this(InformixExpenseStatusDAO.class.getName());
    }

    /**
     * <p>
     * Creates a new instance of <code>InformixExpenseStatusDAO</code> class with given namespace to load the
     * connection name and DB connection factory.
     * </p>
     *
     * @param namespace the given namespace to load the connection name and DB connection factory.
     *
     * @throws IllegalArgumentException if the argument is the empty string or if the argument is null.
     * @throws ConfigurationException if any exception prevents creating the connection factory successfully.
     */
    public InformixExpenseStatusDAO(String namespace) throws ConfigurationException {
        ExpenseEntryHelper.validateString(namespace, "namespace");

        String specificationNamespace = ExpenseEntryHelper.getStringPropertyValue(namespace,
                SPECIFICATION_NAMESPACE_PROPERTY, true);
        String dbConnectionFactoryKey = ExpenseEntryHelper.getStringPropertyValue(namespace,
                DB_CONNECTION_FACTORY_KEY_PROPERTY, true);

        // get the connection name
        this.connectionName = ExpenseEntryHelper.getStringPropertyValue(namespace, CONNECTION_NAME_PROPERTY, false);

        // create the connection factory
        ObjectFactory objectFactory = ExpenseEntryHelper.createObjectFactory(specificationNamespace);
        this.connectionFactory = (DBConnectionFactory) ExpenseEntryHelper.createObject(objectFactory,
                dbConnectionFactoryKey, DBConnectionFactory.class);
    }

    /**
     * <p>
     * Adds a new <code>ExpenseStatus</code> instance to the database.
     * </p>
     *
     * <p>
     * If the id of the argument is not positive then an id for the entry will be generated using GUID Generator
     * component. If the id is positive then it means that the user(application) has assigned an id for this entry.
     * </p>
     *
     * <p>
     * Note: creationDate and modificationDate of the input status instance will be updated to the current datetime if
     * the new status is added into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param status the status that will be added to the database
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry status is added;
     *         <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if the argument is null, or if the creation date or modification date is not
     *         <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when adding the expense entry status.
     */
    public boolean addStatus(ExpenseStatus status) throws InsufficientDataException, PersistenceException {
        ExpenseEntryHelper.validateNotNull(status, "status");
        ExpenseEntryHelper.validateNewInfo(status, "status");
        ExpenseEntryHelper.validateExpenseStatusData(status);

        if (status.getId() <= 0) {
            status.setId(ExpenseEntryHelper.generateId());
        }

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            // Check if the ID already exists in database
            if (existStatus(conn, status.getId())) {
                return false;
            }

            return insertStatus(conn, status) > 0;
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Adds a new <code>ExpenseStatus</code> instance to the database using the given connection.
     * </p>
     *
     * <p>
     * Note: creationDate and modificationDate of the input status instance will be updated to the current datetime if
     * the new status is added into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param conn the give connection.
     * @param status the status that will be added to the database
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when adding the expense entry status.
     */
    private int insertStatus(Connection conn, ExpenseStatus status) throws PersistenceException {
        PreparedStatement statement = null;

        Timestamp now = ExpenseEntryHelper.date2Timestamp(new Date());

        try {
            statement = conn.prepareStatement(ADD_STATUS_SQL);

            // Set parameters
            int index = 0;
            statement.setLong(++index, status.getId());
            statement.setString(++index, status.getDescription());
            statement.setTimestamp(++index, now);
            statement.setString(++index, status.getCreationUser());
            statement.setTimestamp(++index, now);
            statement.setString(++index, status.getModificationUser());

            // Execute
            int ret = statement.executeUpdate();

            if (ret > 0) {
                // Set property only after successful execution of the query.
                status.setCreationDate(now);
                status.setModificationDate(now);
                status.setChanged(false);
            }

            return ret;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Deletes an <code>ExpenseStatus</code> instance with the given ID from the database.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be deleted.
     *
     * @return <code>true</code> if the expense entry status exists in database and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry status.
     */
    public boolean deleteStatus(long statusId) throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return deleteStatus(conn, statusId) > 0;
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes an <code>ExpenseStatus</code> instance with the given ID from the database using the given connection.
     * </p>
     *
     * @param conn the given connection.
     * @param statusId the ID of the expense entry status to be deleted.
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry status.
     */
    private int deleteStatus(Connection conn, long statusId) throws PersistenceException {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(DELETE_STATUS_SQL);

            // Set parameters
            statement.setLong(1, statusId);

            // Execute
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Deletes all <code>ExpenseStatus</code> instances in the database.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry statuses.
     */
    public void deleteAllStatuses() throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            deleteAllStatuses(conn);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes all <code>ExpenseStatus</code> instances in the database.
     * </p>
     *
     * @param conn the given connection.
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry statuses.
     */
    private int deleteAllStatuses(Connection conn) throws PersistenceException {
        Statement statement = null;

        try {
            statement = conn.createStatement();

            return statement.executeUpdate(DELETE_ALL_STATUS_SQL);
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseStatus</code> instance to the database. The creation_date and creation_user will not be
     * updated.
     * </p>
     *
     * <p>
     * Note: modificationDate of the input status instance will be updated to the current datetime if the new status is
     * updated into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param status the expense entry status to be updated in the database.
     *
     * @return <code>true</code> if the expense entry status exists in database and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws IllegalArgumentException if <code>status</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when updating the expense entry status.
     */
    public boolean updateStatus(ExpenseStatus status) throws InsufficientDataException, PersistenceException {
        ExpenseEntryHelper.validateNotNull(status, "status");
        ExpenseEntryHelper.validateExpenseStatusData(status);

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return updateStatus(conn, status) > 0;
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseStatus</code> instance to the database using the given connection. The creation_date and
     * creation_user will not be updated.
     * </p>
     *
     * <p>
     * Note: modificationDate of the input status instance will be updated to the current datetime if the new status is
     * updated into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param conn the given connection.
     * @param status the expense entry status to be updated in the database.
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when updating the expense entry status.
     */
    private int updateStatus(Connection conn, ExpenseStatus status) throws PersistenceException {
        PreparedStatement statement = null;

        Timestamp now = ExpenseEntryHelper.date2Timestamp(new Date());

        try {
            statement = conn.prepareStatement(UPDATE_STATUS_SQL);

            // Set parameters
            int index = 0;
            statement.setString(++index, status.getDescription());
            statement.setTimestamp(++index, now);
            statement.setString(++index, status.getModificationUser());
            statement.setLong(++index, status.getId());

            // Execute
            int ret = statement.executeUpdate();

            // Set property only after successful execution of the query
            if (ret > 0) {
                status.setModificationDate(now);
                status.setChanged(false);
            }

            return ret;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseStatus</code> instance with the given ID from the database.
     * </p>
     *
     * @param statusId the ID of the expense entry status to be retrieved.
     *
     * @return an <code>ExpenseStatus</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry status; or the value in database
     *         is invalid.
     */
    public ExpenseStatus retrieveStatus(long statusId) throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return retrieveStatus(conn, statusId);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseStatus</code> instance with the given ID from the database using given connection.
     * </p>
     *
     * @param conn the given connection.
     * @param statusId the ID of the expense entry status to be retrieved.
     *
     * @return an <code>ExpenseStatus</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry status; or the value in database
     *         is invalid.
     */
    private ExpenseStatus retrieveStatus(Connection conn, long statusId) throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(RETRIEVE_STATUS_SQL);

            // Set parameter
            statement.setLong(1, statusId);

            // Execute
            resultSet = statement.executeQuery();

            return createExpenseEntryStatus(resultSet);
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Checkes whether there is one record with the given status id in the persistence.
     * </p>
     *
     * @param conn the given connection.
     * @param statusId the given status id.
     *
     * @return whether there is one record with the given status id in the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry status.
     */
    private boolean existStatus(Connection conn, long statusId) throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(EXIST_TYPE_SQL);

            // Set parameter
            statement.setLong(1, statusId);

            // Execute
            resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Creates an expense entry status instance from the given result set. The next record in the result set is used to
     * create the instance. If there is no more record in the result set, <code>null</code> is returned.
     * </p>
     *
     * @param resultSet the result set used to create expense entry status instance.
     *
     * @return a new <code>ExpenseStatus</code> instance created from the next record in the result set; or
     *         <code>null</code> if there is no more record.
     *
     * @throws SQLException if error occurs when accessing the result set.
     * @throws PersistenceException if the column value is <code>null</code> or empty string.
     */
    private ExpenseStatus createExpenseEntryStatus(ResultSet resultSet) throws SQLException, PersistenceException {
        if (!resultSet.next()) {
            return null;
        }

        ExpenseStatus status;

        try {
            // Set properties
            status = new ExpenseStatus(resultSet.getLong(ID_COLUMN));
            status.setDescription(resultSet.getString(DESCRIPTION_COLUMN));
            status.setCreationDate(resultSet.getTimestamp(CREATION_DATE_COLUMN));
            status.setCreationUser(resultSet.getString(CREATION_USER_COLUMN));
            status.setModificationDate(resultSet.getTimestamp(MODIFICATION_DATE_COLUMN));
            status.setModificationUser(resultSet.getString(MODIFICATION_USER_COLUMN));
            status.setChanged(false);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Column value is invalid.", e);
        }

        return status;
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseStatus</code> instances from the database.
     * </p>
     *
     * @return an array of <code>ExpenseStatus</code> instances retrieved from the database, or an empty array if
     *         there is no record.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry statuses; or the value in
     *         database is invalid.
     */
    public ExpenseStatus[] retrieveAllStatuses() throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return retrieveAllStatuses(conn);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseStatus</code> instances from the database using the given connection.
     * </p>
     *
     * @param conn the given connection.
     *
     * @return an array of <code>ExpenseStatus</code> instances retrieved from the database, or an empty array if
     *         there is no record.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry statuses; or the value in
     *         database is invalid.
     */
    private ExpenseStatus[] retrieveAllStatuses(Connection conn) throws PersistenceException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.createStatement();

            // Execute
            resultSet = statement.executeQuery(RETRIEVE_ALL_STATUS_SQL);

            // For each record, create an expense entry status instance
            List list = new ArrayList();
            ExpenseStatus status;

            while ((status = createExpenseEntryStatus(resultSet)) != null) {
                if (status.getId() > 0) {
                    list.add(status);
                }
            }

            return (ExpenseStatus[]) list.toArray(new ExpenseStatus[list.size()]);
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Performs a search for expense status matching a given criteria. The criteria is abstracted using the <code>
     * Criteria</code> interface. The <code>Criteria</code> implementations cover the basic SQL filtering abilities
     * (=, like, between, or, and, not). The result of the search is an array with the matched expense status. It is
     * empty if no matches found (but it can't be <code>null</code> or contain <code>null</code>) elements.
     * </p>
     *
     * @param criteria the criteria to be used in the search.
     *
     * @return the results of the search (can be empty if no matches found).
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public ExpenseStatus[] searchEntries(Criteria criteria) throws PersistenceException {
        ExpenseEntryHelper.validateNotNull(criteria, "criteria");

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return searchEntries(conn, criteria);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Performs a search for expense status matching a given criteria using the given connection.
     * </p>
     *
     * @param conn the given connection.
     * @param criteria the criteria to be used in the search.
     *
     * @return the results of the search (can be empty if no matches found).
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    private ExpenseStatus[] searchEntries(Connection conn, Criteria criteria) throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(RETRIEVE_ALL_STATUS_SQL + " Where " + criteria.getWhereClause());

            // Set parameter
            Object[] parameters = criteria.getParameters();

            for (int i = 0; i < parameters.length; ++i) {
                if (parameters[i] instanceof Date) {
                    parameters[i] = ExpenseEntryHelper.date2Timestamp((Date) parameters[i]);
                }

                statement.setObject(i + 1, parameters[i]);
            }

            resultSet = statement.executeQuery();

            // For each record, create an expense entry status instance
            List list = new ArrayList();
            ExpenseStatus status;

            while ((status = createExpenseEntryStatus(resultSet)) != null) {
                if (status.getId() > 0) {
                    list.add(status);
                }
            }

            return (ExpenseStatus[]) list.toArray(new ExpenseStatus[list.size()]);
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(resultSet, statement);
        }
    }
}
