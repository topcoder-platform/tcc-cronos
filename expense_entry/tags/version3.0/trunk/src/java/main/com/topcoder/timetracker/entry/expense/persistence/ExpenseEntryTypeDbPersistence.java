/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntryType;
import com.topcoder.timetracker.entry.expense.search.Criteria;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * Implements <code>ExpenseEntryTypeDbPersistence</code> interface to provide functionality to manipulate
 * <code>ExpenseEntryType</code> instance in database. The operations include adding, updating, deleting and
 * retrieving. The information in expense type is used directly without any modifications. The database connection can
 * be either obtained from the DB connection factory using a connection producer name, or given directly. The expense
 * entry statuses are stored in the database using the following DDL:
 * <pre>
 * CREATE TABLE expense_type (
 *     expense_type_id      integer NOT NULL,
 *     description          varchar(64) NOT NULL,
 *     active               smallint NOT NULL,
 *     creation_date        datetime year to second NOT NULL,
 *     creation_user        varchar(64) NOT NULL,
 *     modification_date    datetime year to second NOT NULL,
 *     modification_user    varchar(64) NOT NULL,
 *     PRIMARY KEY (expense_type_id)
 * );
 * </pre>
 * </p>
 *
 * @author DanLazar, visualage
 * @author arylio
 *
 * @version 2.0
 * @since 1.0
 */
public class ExpenseEntryTypeDbPersistence implements ExpenseEntryTypePersistence {
    /** Represents the prepared SQL statement to add an expense type. */
    private static final String ADD_TYPE_SQL = "INSERT INTO expense_type(expense_type_id, description, "
        + "creation_date, creation_user, modification_date, modification_user, active) VALUES (?,?,?,?,?,?,?)";

    /**
     * Represents the prepared SQL statement to add a comp expense type.
     *
     * @since 2.0
     */
    private static final String ADD_COMP_EXP_TYPE_SQL = "Insert into comp_exp_type (company_id, expense_type_id, " +
            "creation_date, creation_user, modification_date, modification_user) values(?,?,?,?,?,?)";

    /** Represents the prepared SQL statement to update an expense type. */
    private static final String UPDATE_TYPE_SQL = "UPDATE expense_type SET description=?, modification_date=?, "
        + "modification_user=?, active=? WHERE expense_type_id=?";


    /** Represents the prepared SQL statement to update a comp expense type. */
    private static final String UPDATE_COMP_EXP_TYPE_SQL = "UPDATE comp_exp_type SET company_id=?," +
        " modification_date=?, modification_user=? WHERE expense_type_id=?";

    /** Represents the prepared SQL statement to delete an expense type. */
    private static final String DELETE_TYPE_SQL = "DELETE FROM expense_type WHERE expense_type_id=?";

    /**
     * Represents the prepared SQL statement to delete a comp expense type.
     * @since 2.0
     */
    private static final String DETELET_COMP_EXP_TYPE = "Delete From comp_exp_type where expense_type_id=?";

    /** Represents the prepared SQL statement to delete all expense types. */
    private static final String DELETE_ALL_TYPE_SQL = "DELETE FROM expense_type";

    /**
     * Represents the prepared SQL statement to delete all comp expense type.
     * @since 2.0
     */
    private static final String DETELET_ALL_COMP_EXP_TYPE = "Delete From comp_exp_type";

    /** Represents the prepared SQL statement to get an expense type. */
    private static final String RETRIEVE_TYPE_SQL = "SELECT expense_type.expense_type_id, expense_type.description," +
        "expense_type.creation_date, expense_type.creation_user, expense_type.modification_date, " +
        "expense_type.modification_user, expense_type.active, comp_exp_type.company_id FROM expense_type " +
        "Left Join comp_exp_type on comp_exp_type.expense_type_id = expense_type.expense_type_id " +
        "WHERE expense_type.expense_type_id=?";

    /** Represents the prepared SQL statement to get all expense types. */
    private static final String RETRIEVE_ALL_TYPE_SQL = "SELECT expense_type.expense_type_id, " +
        "expense_type.description, expense_type.creation_date, expense_type.creation_user, " +
        "expense_type.modification_date, expense_type.modification_user, expense_type.active, " +
        "comp_exp_type.company_id FROM expense_type " +
        "Left Join comp_exp_type on expense_type.expense_type_id = comp_exp_type.expense_type_id ";

    /**
     * Represents the prepared SQL statement to get all expense entries, including their entry types and statuses.
     *
     * @since 2.0
     */
    private static final String RETRIEVE_ALL_ENTRY_SQL =
        "select expense_type.expense_type_id, expense_type.description, expense_type.active, " +
        "expense_type.creation_date, expense_type.creation_user, expense_type.modification_date, " +
        "expense_type.modification_user, comp_exp_type.company_id from expense_type " +
        "Left join comp_exp_type on comp_exp_type.expense_type_id = expense_type.expense_type_id";

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

    /**
     * Represents the column name for company id.
     * @since 2.0
     */
    private static final String COMPANY_ID_COLUMN = "company_id";

    /**
     * Represents the column name for active.
     * @since 2.0
     */
    private static final String ACTIVE_COLUMN = "active";

    /** Represents the column name for expense type ID. */
    private static final String ID_COLUMN = "expense_type_id";

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

    /** Represents the parameter index of active in INSERT SQL statement.
     * @since 2.0
     */
    private static final int INSERT_ACTIVE_INDEX = 7;

    /** Represents the parameter index of description in UPDATE SQL statement. */
    private static final int UPDATE_DESCRIPTION_INDEX = 1;

    /** Represents the parameter index of modification date in UPDATE SQL statement. */
    private static final int UPDATE_MODIFICATIONDATE_INDEX = 2;

    /** Represents the parameter index of modification user in UPDATE SQL statement. */
    private static final int UPDATE_MODIFICATIONUSER_INDEX = 3;

    /**
     * Represents the parameter index of active in UPDATE SQL statement.
     * @since 2.0
     */
    private static final int UPDATE_ACTIVE_INDEX = 4;

    /** Represents the parameter index of ID in UPDATE SQL statement. */
    private static final int UPDATE_ID_INDEX = 5;

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
     * Creates a new instance of <code>ExpenseEntryTypeDbPersistence</code> class. The DB connection factory is created
     * from the given namespace.
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
     * Creates a new instance of <code>ExpenseEntryTypeDbPersistence</code> class. The connection producer name used to
     * obtain database connection is given. The namespace used to create DB connection factory is also given.
     * </p>
     *
     * @param connectionProducerName the connection producer name to obtain database connection from DB connection
     *        factory.
     * @param namespace the namespace to create DB connection factory.
     *
     * @throws NullPointerException if <code>connectionProducerName</code> or <code>namespace</code> is
     *         <code>null</code>.
     * @throws IllegalArgumentException <code>connectionProducerName</code> or <code>namespace</code> is empty string.
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
            startTransaction();
            statement = connection.prepareStatement(ADD_TYPE_SQL);

            // Set parameters
            statement.setInt(INSERT_ID_INDEX, type.getId());
            statement.setString(INSERT_DESCRIPTION_INDEX, type.getDescription());
            statement.setDate(INSERT_CREATIONDATE_INDEX, now);
            statement.setString(INSERT_CREATIONUSER_INDEX, type.getCreationUser());
            statement.setDate(INSERT_MODIFICATIONDATE_INDEX, now);
            statement.setString(INSERT_MODIFICATIONUSER_INDEX, type.getModificationUser());
            statement.setShort(INSERT_ACTIVE_INDEX, (short) (type.isActive() ? 1 : 0));
            statement.executeUpdate();
            statement.close();

            // Insert into comp_exp_type (company_id, expense_type_id, " +
            // "creation_date, creation_user, modification_date, modification_user) values(?,?,?,?,?,?)";
            statement = connection.prepareStatement(ADD_COMP_EXP_TYPE_SQL);
            int index = 0;
            statement.setInt(++index, type.getCompanyId());
            statement.setInt(++index, type.getId());
            statement.setDate(++index, now);
            statement.setString(++index, type.getCreationUser());
            statement.setDate(++index, now);
            statement.setString(++index, type.getModificationUser());
            statement.executeUpdate();

            endTransaction(true);
            // Set property only after successful execution of the query
            type.setCreationDate(now);
            type.setModificationDate(now);
        } catch (SQLException e) {
            endTransaction(false);
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
            startTransaction();
            statement = connection.prepareStatement(DETELET_COMP_EXP_TYPE);
            statement.setInt(1, typeId);
            success = (statement.executeUpdate() > 0);
            if (success) {
                statement.close();
                statement = connection.prepareStatement(DELETE_TYPE_SQL);
                statement.setInt(1, typeId);
                success = (statement.executeUpdate() > 0);
            }
            endTransaction(success);
        } catch (SQLException e) {
            endTransaction(false);
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
            startTransaction();
            statement = connection.createStatement();
            statement.execute(DETELET_ALL_COMP_EXP_TYPE);
            statement.execute(DELETE_ALL_TYPE_SQL);
            endTransaction(true);
        } catch (SQLException e) {
            endTransaction(false);
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
            startTransaction();
            statement = connection.prepareStatement(UPDATE_TYPE_SQL);
            statement.setString(UPDATE_DESCRIPTION_INDEX, type.getDescription());
            statement.setDate(UPDATE_MODIFICATIONDATE_INDEX, now);
            statement.setString(UPDATE_MODIFICATIONUSER_INDEX, type.getModificationUser());
            statement.setShort(UPDATE_ACTIVE_INDEX, (short) (type.isActive() ? 1 : 0));
            statement.setInt(UPDATE_ID_INDEX, type.getId());
            success = (statement.executeUpdate() > 0);

            if (success) {
                statement.close();
                statement = connection.prepareStatement(UPDATE_COMP_EXP_TYPE_SQL);
                //UPDATE comp_exp_type SET company_id=?, modification_date=?, "
                //        + "modification_user=? WHERE expense_type_id=?";
                int index = 0;
                statement.setInt(++index, type.getCompanyId());
                statement.setDate(++index, now);
                statement.setString(++index, type.getModificationUser());
                statement.setInt(++index, type.getId());
                success = (statement.executeUpdate() > 0);
            }
            // Set property only after successful execution of the query
            if (success) {
                type.setModificationDate(now);
            }
            endTransaction(success);
        } catch (SQLException e) {
            endTransaction(false);
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
     * @throws PersistenceException if error occurs when retrieving the expense entry type; or the value in persistence
     *         is invalid.
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
     * Performs a search for expense types matching a given criteria. The criteria is abstracted using the <code>
     * Criteria</code> interface. The <code>Criteria</code> implementations cover the basic SQL filtering abilities
     * (=, like, between, or, and, not). The result of the search is an array with the matched expense types. It is
     * empty if no matches found (but it can't be <code>null</code> or contain <code>null</code>) elements.
     * </p>
     *
     * @param criteria the criteria to be used in the search.
     *
     * @return the results of the search (can be empty if no matches found).
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     *
     * @since 2.0
     */
    public ExpenseEntryType[] searchEntries(Criteria criteria) throws PersistenceException {
        // argument validation
        if (criteria == null) {
            throw new IllegalArgumentException("criteria should not be null.");
        }

        List ret = new ArrayList();

        createConnection(connectionProducerName);

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExpenseEntryType entryType;

        try {
            statement = connection.prepareStatement(RETRIEVE_ALL_ENTRY_SQL + " where " + criteria.getWhereClause());

            // Set parameter
            Object[] parameters = criteria.getParameters();

            for (int i = 0; i < parameters.length; ++i) {
                if (parameters[i] instanceof java.util.Date) {
                    parameters[i] = new Timestamp(((java.util.Date) parameters[i]).getTime());
                }

                statement.setObject(i + 1, parameters[i]);
            }

            // Execute
            resultSet = statement.executeQuery();

            // For each record, create an expense entry status instance
            while ((entryType = createExpenseEntryType(resultSet)) != null) {
                ret.add(entryType);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Accessing database fails.", e);
        } finally {
            PersistenceHelper.close(resultSet);
            PersistenceHelper.close(statement);
        }

        return (ExpenseEntryType[]) ret.toArray(new ExpenseEntryType[ret.size()]);
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
     * Sets the existing connection which is used to access database for future operations. The previous connection is
     * closed if it exists.
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
    public void initConnection(String connectionProducerName) throws PersistenceException {
        PersistenceHelper.validateNotNullOrEmpty(connectionProducerName, "connectionProducerName");

        closeConnection();

        createConnection(connectionProducerName);
    }

    /**
     * <p>
     * Creates a database connection using the DB connection factory and connection producer name if the current
     * connection is <code>null</code>. The connection field is set. If the current connection already exists, it does
     * nothing.
     * </p>
     *
     * @param connectionProducerName the DB connection producer name.
     *
     * @throws PersistenceException if the connection producer name is <code>null</code> or empty string; or the
     *         connection producer name does not exist in DB connection factory; or database connection cannot be
     *         created.
     */
    private void createConnection(String connectionProducerName) throws PersistenceException {
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
    private ExpenseEntryType createExpenseEntryType(ResultSet resultSet) throws SQLException, PersistenceException {
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
            type.setCompanyId(resultSet.getInt(COMPANY_ID_COLUMN));
            type.setActive(!(resultSet.getShort(ACTIVE_COLUMN) == 0));
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

    /**
     * <p>
     * Start transaction.
     * </p>
     *
     * @throws SQLException the exception thrown by connection.
     *
     */
    private void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    /**
     * <p>
     * Stop transaction.
     * </p>
     *
     * @param isCommited True for commited, false for roll back.
     */
    private void endTransaction(boolean isCommited) {
        try {
            if (isCommited) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            // ignore it.
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                // ignore it.
            }
        }
    }
}
