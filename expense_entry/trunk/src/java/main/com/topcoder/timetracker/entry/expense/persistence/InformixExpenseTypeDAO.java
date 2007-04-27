/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;
import com.topcoder.timetracker.entry.expense.ExpenseType;
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
 * This class is a concrete implementation of the <code>ExpenseEntryTypeDAO</code> interface that uses an Informix
 * database as the data store for the persistence of <code>ExpenseType</code> instances. This implementation uses the
 * DB Connection Factory component to obtain a connection to the database, the GUID Generator component to create IDs
 * for new Expense Entry Types. This class provides two constructors that will use Config Manager and Object Factory
 * components to obtain instances of the connection factory. One constructor will take a namespace, and the second
 * will work with a default namespace. The expense entry types are stored in the database using the following DDL:
 * <pre>
 * CREATE TABLE expense_type (
 *     expense_type_id      integer NOT NULL,
 *     description          varchar(255),
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
 * <p>
 * <b>Thread Safety: </b>This class is immutable and thread-safe as long as the <code>ExpenseType</code> is no
 * manipulated by more than one thread.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class InformixExpenseTypeDAO implements ExpenseEntryTypeDAO {
    /** Represents the prepared SQL statement to add an expense type. */
    private static final String ADD_TYPE_SQL = "INSERT INTO expense_type(expense_type_id, description, "
        + "creation_date, creation_user, modification_date, modification_user, active) VALUES (?,?,?,?,?,?,?)";

    /** Represents the prepared SQL statement to add a comp expense type. */
    private static final String ADD_COMP_EXP_TYPE_SQL = "Insert into comp_exp_type (company_id, expense_type_id, "
        + "creation_date, creation_user, modification_date, modification_user) values(?,?,?,?,?,?)";

    /** Represents the prepared SQL statement to update an expense type. */
    private static final String UPDATE_TYPE_SQL = "UPDATE expense_type SET description=?, modification_date=?, "
        + "modification_user=?, active=? WHERE expense_type_id=?";

    /** Represents the prepared SQL statement to update a comp expense type. */
    private static final String UPDATE_COMP_EXP_TYPE_SQL = "UPDATE comp_exp_type SET company_id=?,"
        + " modification_date=?, modification_user=? WHERE expense_type_id=?";

    /** Represents the prepared SQL statement to delete an expense type. */
    private static final String DELETE_TYPE_SQL = "DELETE FROM expense_type WHERE expense_type_id=?";

    /** Represents the prepared SQL statement to delete a comp expense type. */
    private static final String DETELET_COMP_EXP_TYPE = "Delete From comp_exp_type where expense_type_id=?";

    /** Represents the prepared SQL statement to delete all expense types. */
    private static final String DELETE_ALL_TYPE_SQL = "DELETE FROM expense_type";

    /** Represents the prepared SQL statement to delete all comp expense type. */
    private static final String DETELET_ALL_COMP_EXP_TYPE = "Delete From comp_exp_type";

    /** Represents the prepared SQL statement to get an expense type. */
    private static final String RETRIEVE_TYPE_SQL = "SELECT expense_type.expense_type_id, expense_type.description,"
        + "expense_type.creation_date, expense_type.creation_user, expense_type.modification_date, "
        + "expense_type.modification_user, expense_type.active, comp_exp_type.company_id FROM expense_type "
        + ", comp_exp_type "
        + "WHERE comp_exp_type.expense_type_id = expense_type.expense_type_id and expense_type.expense_type_id=?";

    /** Represents the prepared SQL statement to check the existence of an expense type. */
    private static final String EXIST_TYPE_SQL = "SELECT 1 counts FROM expense_type WHERE expense_type_id=?";

    /** Represents the prepared SQL statement to get all expense types. */
    private static final String RETRIEVE_ALL_TYPE_SQL = "SELECT expense_type.expense_type_id, "
        + "expense_type.description, expense_type.creation_date, expense_type.creation_user, "
        + "expense_type.modification_date, expense_type.modification_user, expense_type.active, "
        + "comp_exp_type.company_id FROM expense_type "
        + ", comp_exp_type "
        + "WHERE expense_type.expense_type_id = comp_exp_type.expense_type_id ";

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

    /** Represents the column name for company id. */
    private static final String COMPANY_ID_COLUMN = "company_id";

    /** Represents the column name for active. */
    private static final String ACTIVE_COLUMN = "active";

    /** Represents the column name for expense type ID. */
    private static final String ID_COLUMN = "expense_type_id";

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
     * Creates a new instance of <code>InformixExpenseTypeDAO</code> class with default namespace to load the
     * connection name and DB connection factory.
     * </p>
     *
     * @throws ConfigurationException if any exception prevents creating the connection factory successfully.
     */
    public InformixExpenseTypeDAO() throws ConfigurationException {
        this(InformixExpenseTypeDAO.class.getName());
    }

    /**
     * <p>
     * Creates a new instance of <code>InformixExpenseTypeDAO</code> class with given namespace to load the connection
     * name and DB connection factory.
     * </p>
     *
     * @param namespace the given namespace to load the connection name and DB connection factory.
     *
     * @throws IllegalArgumentException if the argument is the empty string or if the argument is null.
     * @throws ConfigurationException if any exception prevents creating the connection factory successfully.
     */
    public InformixExpenseTypeDAO(String namespace) throws ConfigurationException {
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
     * Adds a new <code>ExpenseType</code> instance to the database. A new record will also be inserted into the
     * comp_exp_type table.
     * </p>
     *
     * <p>
     * If the id of the argument is not positive then an id for the entry will be generated using GUID Generator
     * component. If the id is positive then it means that the user(application) has assigned an id for this entry.
     * </p>
     *
     * <p>
     * Note: creationDate and modificationDate of the input type instance will be updated to the current datetime if
     * the new type is added into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param type the expense entry type to be added to the database.
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry type is added;
     *         <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if the argument is null, or if the creation date or modification date is not
     *         <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when adding the expense entry type.
     */
    public boolean addType(ExpenseType type) throws InsufficientDataException, PersistenceException {
        ExpenseEntryHelper.validateNotNull(type, "type");
        ExpenseEntryHelper.validateNewInfo(type, "type");
        ExpenseEntryHelper.validateExpenseTypeData(type);

        if (type.getId() <= 0) {
            type.setId(ExpenseEntryHelper.generateId());
        }

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            // Check if the ID already exists in database
            if (existType(conn, type.getId())) {
                return false;
            }

            return addType(conn, type) > 0;
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Adds a new <code>ExpenseType</code> instance to the database using the given connection. A new record will also
     * be inserted into the comp_exp_type table.
     * </p>
     *
     * <p>
     * Note: creationDate and modificationDate of the input type instance will be updated to the current datetime if
     * the new type is added into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param conn the give connection.
     * @param type the expense entry type to be added to the database.
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when adding the expense entry type.
     */
    private int addType(Connection conn, ExpenseType type) throws PersistenceException {
        PreparedStatement statement = null;

        Timestamp now = ExpenseEntryHelper.date2Timestamp(new Date());

        try {
            // insert into the table expense_type
            statement = conn.prepareStatement(ADD_TYPE_SQL);

            // Set parameters
            int index = 0;
            statement.setLong(++index, type.getId());
            statement.setString(++index, type.getDescription());
            statement.setTimestamp(++index, now);
            statement.setString(++index, type.getCreationUser());
            statement.setTimestamp(++index, now);
            statement.setString(++index, type.getModificationUser());
            statement.setShort(++index, (short) (type.getActive() ? 1 : 0));

            int ret = statement.executeUpdate();

            if (ret > 0) {
                statement.close();

                // insert into the table comp_exp_type
                statement = conn.prepareStatement(ADD_COMP_EXP_TYPE_SQL);
                index = 0;
                statement.setLong(++index, type.getCompanyId());
                statement.setLong(++index, type.getId());
                statement.setTimestamp(++index, now);
                statement.setString(++index, type.getCreationUser());
                statement.setTimestamp(++index, now);
                statement.setString(++index, type.getModificationUser());
                statement.executeUpdate();

                // Set property only after successful execution of the query
                type.setCreationDate(now);
                type.setModificationDate(now);
                type.setChanged(false);
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
     * Deletes an <code>ExpenseType</code> instance with the given ID from the database. The corresponding record will
     * also be deleted from the comp_exp_type table.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be deleted.
     *
     * @return <code>true</code> if the expense entry type exists in database and is deleted; <code>false</code>
     *         otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry type.
     */
    public boolean deleteType(long typeId) throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return deleteType(conn, typeId) > 0;
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes an <code>ExpenseType</code> instance with the given ID from the database using the given connection. The
     * corresponding record will also be deleted from the comp_exp_type table.
     * </p>
     *
     * @param conn the give connection.
     * @param typeId the ID of the expense entry type to be deleted.
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry type.
     */
    private int deleteType(Connection conn, long typeId) throws PersistenceException {
        PreparedStatement statement = null;

        try {
            // delete from the table comp_exp_type
            statement = conn.prepareStatement(DETELET_COMP_EXP_TYPE);
            statement.setLong(1, typeId);
            statement.executeUpdate();
            statement.close();

            // delete from the table expense_type
            statement = conn.prepareStatement(DELETE_TYPE_SQL);
            statement.setLong(1, typeId);

            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Deletes all <code>ExpenseType</code> instances in the database. The corresponding records will also be deleted
     * from the comp_exp_type table.
     * </p>
     *
     * @throws PersistenceException if error occurs when deleting the expense entry types.
     */
    public void deleteAllTypes() throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            deleteAllTypes(conn);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes all <code>ExpenseType</code> instances in the database using the given connection. The corresponding
     * records will also be deleted from the comp_exp_type table.
     * </p>
     *
     * @param conn the given connection.
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry types.
     */
    private int deleteAllTypes(Connection conn) throws PersistenceException {
        Statement statement = null;

        try {
            statement = conn.createStatement();
            statement.executeUpdate(DETELET_ALL_COMP_EXP_TYPE);

            return statement.executeUpdate(DELETE_ALL_TYPE_SQL);
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseType</code> instance to the database. The creation_date and creation_user will not be
     * updated. The corresponding record will also be updated in the comp_exp_type table.
     * </p>
     *
     * <p>
     * Note: modificationDate of the input type instance will be updated to the current datetime if the new type is
     * updated into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param type the expense entry type to be updated in the database.
     *
     * @return <code>true</code> if the expense entry type exists in database and is updated; <code>false</code>
     *         otherwise.
     *
     * @throws IllegalArgumentException if <code>type</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than description, creation date and modification date are not
     *         set.
     * @throws PersistenceException if error occurs when updating the expense entry type.
     */
    public boolean updateType(ExpenseType type) throws InsufficientDataException, PersistenceException {
        ExpenseEntryHelper.validateNotNull(type, "type");
        ExpenseEntryHelper.validateExpenseTypeData(type);

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return updateType(conn, type) > 0;
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseType</code> instance to the database using the given connection. The creation_date and
     * creation_user will not be updated. The corresponding record will also be updated in the comp_exp_type table.
     * </p>
     *
     * <p>
     * Note: modificationDate of the input type instance will be updated to the current datetime if the new type is
     * updated into the database.
     * </p>
     *
     * @param conn the given connection.
     * @param type the expense entry type to be updated in the database.
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when updating the expense entry type.
     */
    private int updateType(Connection conn, ExpenseType type) throws PersistenceException {
        PreparedStatement statement = null;

        Timestamp now = ExpenseEntryHelper.date2Timestamp(new Date());

        try {
            // update the expense_type table
            statement = conn.prepareStatement(UPDATE_TYPE_SQL);

            // Set parameters
            int index = 0;
            statement.setString(++index, type.getDescription());
            statement.setTimestamp(++index, now);
            statement.setString(++index, type.getModificationUser());
            statement.setShort(++index, (short) (type.getActive() ? 1 : 0));
            statement.setLong(++index, type.getId());

            int ret = statement.executeUpdate();

            if (ret > 0) {
                // update the comp_exp_type table
                statement.close();
                statement = conn.prepareStatement(UPDATE_COMP_EXP_TYPE_SQL);
                index = 0;
                statement.setLong(++index, type.getCompanyId());
                statement.setTimestamp(++index, now);
                statement.setString(++index, type.getModificationUser());
                statement.setLong(++index, type.getId());
                statement.executeUpdate();

                // Set property only after successful execution of the query
                type.setModificationDate(now);
                type.setChanged(false);
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
     * Retrieves an <code>ExpenseType</code> instance with the given ID from the database. The corresponding record
     * will also be retrieved from the comp_exp_type table.
     * </p>
     *
     * @param typeId the ID of the expense entry type to be retrieved.
     *
     * @return an <code>ExpenseType</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry type; or the value in persistence
     *         is invalid.
     */
    public ExpenseType retrieveType(long typeId) throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return retrieveType(conn, typeId);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseType</code> instance with the given ID from the database using given connection. The
     * corresponding record will also be retrieved from the comp_exp_type table.
     * </p>
     *
     * @param conn the given connection.
     * @param typeId the ID of the expense entry type to be retrieved.
     *
     * @return an <code>ExpenseType</code> instance with the given ID; or <code>null</code> if such instance
     *         cannot be found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry type; or the value in persistence
     *         is invalid.
     */
    private ExpenseType retrieveType(Connection conn, long typeId) throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(RETRIEVE_TYPE_SQL);

            // Set parameter
            statement.setLong(1, typeId);

            // Execute
            resultSet = statement.executeQuery();

            return createExpenseEntryType(resultSet);
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Checkes whether there is one record with the given type id in the persistence.
     * </p>
     *
     * @param conn the given connection.
     * @param typeId the given type id.
     *
     * @return whether there is one record with the given type id in the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry type.
     */
    private boolean existType(Connection conn, long typeId) throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(EXIST_TYPE_SQL);

            // Set parameter
            statement.setLong(1, typeId);

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
     * Creates an expense entry type instance from the given result set. The next record in the result set is used to
     * create the instance. If there is no more record in the result set, <code>null</code> is returned.
     * </p>
     *
     * @param resultSet the result set used to create expense entry type instance.
     *
     * @return a new <code>ExpenseType</code> instance created from the next record in the result set; or
     *         <code>null</code> if there is no more record.
     *
     * @throws SQLException if error occurs when accessing the result set.
     * @throws PersistenceException if the column value is <code>null</code> or empty string.
     */
    private ExpenseType createExpenseEntryType(ResultSet resultSet) throws SQLException, PersistenceException {
        if (!resultSet.next()) {
            return null;
        }

        ExpenseType type;

        try {
            // Set properties
            type = new ExpenseType(resultSet.getLong(ID_COLUMN));
            type.setDescription(resultSet.getString(DESCRIPTION_COLUMN));
            type.setCreationDate(resultSet.getDate(CREATION_DATE_COLUMN));
            type.setCreationUser(resultSet.getString(CREATION_USER_COLUMN));
            type.setModificationDate(resultSet.getDate(MODIFICATION_DATE_COLUMN));
            type.setModificationUser(resultSet.getString(MODIFICATION_USER_COLUMN));
            type.setCompanyId(resultSet.getLong(COMPANY_ID_COLUMN));
            type.setActive(resultSet.getShort(ACTIVE_COLUMN) != 0);
            type.setChanged(false);
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Column value is invalid.", e);
        }

        return type;
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseType</code> instances from the database. The corresponding records will also be
     * retrieved from the comp_exp_type table.
     * </p>
     *
     * @return an array of <code>ExpenseType</code> instances retrieved from the database, or an empty array if there
     *         is no record.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry types; or the value in database
     *         is invalid.
     */
    public ExpenseType[] retrieveAllTypes() throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return retrieveAllTypes(conn);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseType</code> instances from the database using the given connection. The corresponding
     * records will also be retrieved from the comp_exp_type table.
     * </p>
     *
     * @param conn the given connection.
     *
     * @return an array of <code>ExpenseType</code> instances retrieved from the database, or an empty array if there
     *         is no record.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry types; or the value in database
     *         is invalid.
     */
    private ExpenseType[] retrieveAllTypes(Connection conn) throws PersistenceException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.createStatement();

            // Execute
            resultSet = statement.executeQuery(RETRIEVE_ALL_TYPE_SQL);

            // For each record, create an expense entry type instance
            List list = new ArrayList();
            ExpenseType type;

            while ((type = createExpenseEntryType(resultSet)) != null) {
                list.add(type);
            }

            return (ExpenseType[]) list.toArray(new ExpenseType[list.size()]);
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(resultSet, statement);
        }
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
     */
    public ExpenseType[] searchEntries(Criteria criteria) throws PersistenceException {
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
     * Performs a search for expense types matching a given criteria using the given connection.
     * </p>
     *
     * @param conn the given connection.
     * @param criteria the criteria to be used in the search.
     *
     * @return the results of the search (can be empty if no matches found).
     *
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    private ExpenseType[] searchEntries(Connection conn, Criteria criteria) throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(RETRIEVE_ALL_TYPE_SQL + " AND " + criteria.getWhereClause());

            // Set parameter
            Object[] parameters = criteria.getParameters();

            for (int i = 0; i < parameters.length; ++i) {
                if (parameters[i] instanceof Date) {
                    parameters[i] = ExpenseEntryHelper.date2Timestamp((Date) parameters[i]);
                }

                statement.setObject(i + 1, parameters[i]);
            }

            resultSet = statement.executeQuery();

            // For each record, create an expense entry type instance
            List list = new ArrayList();
            ExpenseType type;

            while ((type = createExpenseEntryType(resultSet)) != null) {
                list.add(type);
            }

            return (ExpenseType[]) list.toArray(new ExpenseType[list.size()]);
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(resultSet, statement);
        }
    }
}
