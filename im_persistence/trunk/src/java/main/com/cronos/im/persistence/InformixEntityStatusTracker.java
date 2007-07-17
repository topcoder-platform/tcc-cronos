/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.EntityKey;
import com.topcoder.database.statustracker.EntityStatus;
import com.topcoder.database.statustracker.Status;

import com.topcoder.database.statustracker.persistence.EntityStatusTracker;
import com.topcoder.database.statustracker.persistence.RecordNotFoundException;
import com.topcoder.database.statustracker.persistence.StatusTrackerPersistenceException;

import com.topcoder.util.datavalidator.ObjectValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>An implementation of <code>EntityStatusTracker</code> that uses an Informix database as the backing
 * storage. Specifically, it uses the tables <i>xxx_status</i> and <i>xxx_status_history</i> (where <i>xxx</i> is
 * the entity type) to store the status information.
 *
 * <p>The following configuration parameters are supported.
 *
 * <ul>
 *   <li><strong>specification_factory_namespace</strong> (required): the namespace containing the object factory
 *      specifications</li>
 *   <li><strong>db_connection_factory_key</strong> (required): the object factory key used to instantiate the DB
 *     connection factory</li>
 *   <li><strong>connection_name</strong> (required): the name used to retrieve connections from the DB connection
 *     factory</li>
 *   <li><strong>validator_key</strong> (optional): the object factory key used to instantiate the object
 *     validator; if omitted, no validation is done</li>
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This implementation is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class InformixEntityStatusTracker extends AbstractPersistenceWithValidator implements EntityStatusTracker {
    /**
     * The namespace used by the {@link #InformixEntityStatusTracker default constructor} to configure a new
     * instance of this class.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.im.persistence.InformixEntityStatusTracker";

    /**
     * Constructs a new <code>InformixEntityStatusTracker</code> based on the specified configuration
     * namespace. See the class documentation for a description of the configuration parameters.
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws ConfigurationException if the namespace is missing or invalid or if a required property is missing
     *   or if a required object cannot be instantiated
     */
    public InformixEntityStatusTracker(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Constructs a new <code>InformixEntityStatusTracker</code> with the specified characteristics.
     *
     * @param connectionFactory the DB connection factory
     * @param connectionName the DB connection name
     * @param validator the <code>EntityKey</code> validator or <code>null</code> if no validation is required
     * @throws IllegalArgumentException if any argument except for <code>validator</code> is <code>null</code> or
     *   if <code>connectionName</code> is an empty string
     */
    public InformixEntityStatusTracker(DBConnectionFactory connectionFactory, String connectionName,
                                       ObjectValidator validator) {
        super(connectionFactory, connectionName, validator);
    }

    /**
     * Equivalent to {@link #InformixEntityStatusTracker(String) InformixEntityStatusTracker(DEFAULT_NAMESPACE}.
     *
     * @throws ConfigurationException if the namespace is missing or invalid or if a required property is missing
     *   or if a required object cannot be instantiated
     */
    public InformixEntityStatusTracker() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * Sets the current status of the specified entity to the specified status in the <i>XXX_status_history</i>
     * table. This also involves setting the end date of the previous status to the current date. Transaction
     * processing is used to ensure that partial updates are not performed. The legal values for the status exists
     * in the <i>XXX_status</i>, which is maintained outside of this component.
     *
     * @param key the entity whose status is being set
     * @param newStatus the new status
     * @param who the user performing the operation
     * @return the new status for the entity
     * @throws IllegalArgumentException if any argument is <code>null</code> or <code>who</code> is an empty string
     * @throws RecordNotFoundException if the entity type within the entity instance was not previously registered
     * @throws StatusTrackerPersistenceException if the entity type does not exist or the new status does not have
     *   a valid status ID, or a database error occurs while persisting the change
     * @throws EntityStatusValidationException if <code>key</code> fails validation
     */
    public EntityStatus setStatus(EntityKey key, Status newStatus, String who)
        throws StatusTrackerPersistenceException {
        ParameterHelpers.checkParameter(key, "entity key");
        checkValidStatus(key.getType(), newStatus);
        ParameterHelpers.checkParameter(who, "who");

        ObjectValidator validator = getValidator();

        if (validator != null && !validator.valid(key)) {
            throw new EntityStatusValidationException(validator.getMessage(key));
        }

        String type = key.getType().getName();
        String table = getStatusHistoryTable(type);

        Connection connection = getConnection();

        try {
            try {
                connection.setAutoCommit(false);

                long id = getPrimaryId(key);

                assertStatusExists(connection, type, newStatus);

                // make sure the tables exist for the specified type
                assertTypeExists(connection, type, false);

                // set the end date on the current status
                PreparedStatement statement =
                    connection.prepareStatement("UPDATE " + table + " SET create_date = CURRENT, modify_date = "
                                                + "CURRENT, end_date = CURRENT, create_user = USER, modify_user = "
                                                + "USER WHERE end_date IS NULL AND " + type + "_id = ?");

                try {
                    statement.setLong(1, id);
                    statement.executeUpdate();
                } finally {
                    closeStatement(statement);
                }

                // insert the new status with no end date
                statement =
                    connection.prepareStatement("INSERT INTO " + table + " (" + type + "_id, " + type + "_status_id, "
                                                + "start_date, create_date, modify_date, create_user, modify_user) "
                                                + "VALUES (?, ?, CURRENT, CURRENT, CURRENT, USER, USER)");

                try {
                    statement.setLong(1, id);
                    statement.setLong(2, newStatus.getId());

                    if (statement.executeUpdate() == 0) {
                        throw new StatusTrackerPersistenceException("failed to insert new status");
                    }

                } finally {
                    closeStatement(statement);
                }

                connection.commit();
            } catch (StatusTrackerPersistenceException ex) {
                rollbackConnection(connection);
                throw ex;
            } catch (SQLException ex) {
                rollbackConnection(connection);
                throw new StatusTrackerPersistenceException("SQL error: " + ex.getMessage(), ex);
            }

            return getCurrentStatus(connection, key);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Retrieves the current status of the specified entity key from the database. The current status will be the
     * only entry in the table with no <i>end_date</i>.
     *
     * @param key the entity key whose current status should be retrieved
     * @return the current entity status for the specified entity
     * @throws IllegalArgumentException if <code>key</code> is <code>null</code>
     * @throws RecordNotFoundException if the entity does not exist or has no status
     * @throws StatusTrackerPersistenceException if some other database error occurs
     */
    public EntityStatus getCurrentStatus(EntityKey key) throws StatusTrackerPersistenceException {
        ParameterHelpers.checkParameter(key, "entity key");

        Connection connection = getConnection();

        try {
            return getCurrentStatus(connection, key);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Retrieves the status history for the specified entity instance. The status history consists of all status
     * entries in ascending chronological order, with the last element being the current status.
     *
     * @param key the entity for which to retrieve the status
     * @return the status history in chronological order, with the current status being the last element
     * @throws IllegalArgumentException if <code>key</code> is <code>null</code>
     * @throws RecordNotFoundException if no history exists for the specified entity
     * @throws StatusTrackerPersistenceException if the entity instance is invalid or no history is found or a
     *   database error occurs
     */
    public EntityStatus[] getStatusHistory(EntityKey key) throws StatusTrackerPersistenceException {
        ParameterHelpers.checkParameter(key, "entity key");

        Connection connection = getConnection();

        String type = key.getType().getName();
        String statusTable = getStatusTable(type);
        String historyTable = getStatusHistoryTable(type);
        String statusID = getStatusIDColumn(type);

        try {
            try {
                PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM " + statusTable + ", " + historyTable + " WHERE "
                                                + statusTable + "." + statusID + " = " + historyTable + "."
                                                + statusID + " AND " + historyTable + "." + type
                                                + "_id = ? ORDER BY end_date ASC");

                try {
                    statement.setLong(1, getPrimaryId(key));

                    List ret = new ArrayList();

                    ResultSet results = statement.executeQuery();
                    // the current status will be the first result returned, but it needs to go at the end of the list
                    EntityStatus currentStatus = null;
                    try {
                        while (results.next()) {
                            EntityStatus status = createStatus(key, results, type);
                            if (currentStatus == null) {
                                currentStatus = status;
                            } else {
                                ret.add(status);
                            }
                        }

                        if (currentStatus != null) {
                            ret.add(currentStatus);
                        }

                        int size = ret.size();

                        if (size == 0) {
                            throw new RecordNotFoundException("no status history found");
                        }

                        return (EntityStatus[]) ret.toArray(new EntityStatus[size]);
                    } finally {
                        closeResults(results);
                    }

                } finally {
                    closeStatement(statement);
                }
            } catch (SQLException ex) {
                throw new StatusTrackerPersistenceException("error retrieving status history: " + ex.getMessage(), ex);
            }
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Retrieves all the current status entries of the specifies type whose IDs match an ID in the specified status
     * array.
     *
     * @param type the type of status to search for
     * @param statuses the statuses to search for
     * @return a (possibly empty) array of statuses matching the input criteria
     * @throws RecordNotFoundException if the entity type was not found in persistent storage
     * @throws IllegalArgumentException if either argument is <code>null</code> or <code>statuses</code> is
     *   <code>null</code> or contains entries that are not a valid status for the specified type
     * @throws StatusTrackerPersistenceException if the entity type is not found or another database error occurs
     */
    public EntityStatus[] findByStatus(Entity type, Status[] statuses) throws StatusTrackerPersistenceException {
        ParameterHelpers.checkParameter(type, "entity type");
        ParameterHelpers.checkParameter(statuses, "status array");
        // we'll check for null elements below, in checkValidStatus

        if (statuses.length == 0) {
            throw new IllegalArgumentException("status array is empty");
        }

        Connection connection = getConnection();

        String typeName = type.getName();
        String statusTable = getStatusTable(typeName);
        String historyTable = getStatusHistoryTable(typeName);
        String statusID = getStatusIDColumn(typeName);

        // build the query
        StringBuffer query = new StringBuffer();
        query.append("SELECT * FROM " + statusTable + ", " + historyTable + " WHERE " + statusTable + "." + statusID
                     + " = " + historyTable + "." + statusID + " AND end_date IS NULL AND "
                     + historyTable + "." + statusID + " IN (");

        // make sure the statuses are passed while we build the query
        for (int idx = 0; idx < statuses.length; ++idx) {
            Status status = statuses[idx];
            checkValidStatus(type, status);

            if (idx > 0) {
                query.append(", ");
            }

            query.append("?");
        }

        query.append(")");

        try {
            try {
                // make sure the tables exist
                assertTypeExists(connection, typeName, true);

                PreparedStatement statement = connection.prepareStatement(query.toString());

                try {
                    // statement.setLong(1, type.getId());
                    // populate the query params
                    for (int idx = 1; idx <= statuses.length; ++idx) {
                        statement.setLong(idx, statuses[idx - 1].getId());
                    }

                    List ret = new ArrayList();

                    // run the query and extract the results
                    ResultSet results = statement.executeQuery();
                    try {
                        while (results.next()) {
                            // creates EntityKey from result set
                            long entityId = results.getLong(typeName + "_id");

                            EntityKey key = new EntityKey(type, String.valueOf(entityId));

                            ret.add(createStatus(key, results, typeName));
                        }

                        return (EntityStatus[]) ret.toArray(new EntityStatus[ret.size()]);
                    } finally {
                        closeResults(results);
                    }

                } finally {
                    closeStatement(statement);
                }
            } catch (SQLException ex) {
                throw new StatusTrackerPersistenceException("error retrieving status: " + ex.getMessage(), ex);
            }
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Creates a serialized value suitable for initializing an entity key for the specified entity type.
     *
     * @param type the entity type
     * @return a serialized value
     */
    private static String createKeyValue(Entity type) {
        StringBuffer value = new StringBuffer();
        int len = type.getPrimaryKeyColumns().length;
        for (int idx = 0; idx < len; ++idx) {
            if (idx > 0) {
                value.append("|");
            }

            value.append(String.valueOf(idx));
        }

        return value.toString();
    }

    /**
     * Verifies that the specified status is not <code>null</code> and is a valid status for the specified type.
     *
     * @param type the entity type
     * @param status the status to check for validity
     * @throws IllegalArgumentException if <code>status</code> is <code>null</code> or is not a valid status for
     *   the specified type
     */
    private static void checkValidStatus(Entity type, Status status) {
        ParameterHelpers.checkParameter(status, "status");

        Status[] statuses = type.getValidStatuses();

        for (int idx = 0; idx < statuses.length; ++idx) {
            if (statuses[idx].equals(status)) {
                return;
            }
        }

        throw new IllegalArgumentException("invalid status: " + status);
    }

    /**
     * Helper method that creates a database connection and rethrows any exceptions as
     * <code>StatusTrackerPersistenceException</code>.
     *
     * @return a new database connection
     * @throws StatusTrackerPersistenceException if the connection cannot be created
     */
    private Connection getConnection() throws StatusTrackerPersistenceException {
        try {
            return getConnectionFactory().createConnection(getConnectionName());
        } catch (DBConnectionException ex) {
            throw new StatusTrackerPersistenceException("error creating connection: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the database connection and rethrows any exceptions as
     * <code>StatusTrackerPersistenceException</code>.
     *
     * @param connection the database connection to close
     * @throws StatusTrackerPersistenceException if a SQL error occurs
     */
    private static void closeConnection(Connection connection) throws StatusTrackerPersistenceException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new StatusTrackerPersistenceException("error closing connection: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the result set and rethrows any exceptions as <code>StatusTrackerPersistenceException</code>.
     *
     * @param results the result set to close
     * @throws StatusTrackerPersistenceException if a SQL error occurs
     */
    private static void closeResults(ResultSet results) throws StatusTrackerPersistenceException {
        try {
            results.close();
        } catch (SQLException ex) {
            throw new StatusTrackerPersistenceException("error closing result set: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the prepared statement and rethrows any exceptions as
     * <code>StatusTrackerPersistenceException</code>.
     *
     * @param statement the statement to close
     * @throws StatusTrackerPersistenceException if a SQL error occurs
     */
    private static void closeStatement(PreparedStatement statement) throws StatusTrackerPersistenceException {
        try {
            statement.close();
        } catch (SQLException ex) {
            throw new StatusTrackerPersistenceException("error closing prepared statement: " + ex.getMessage(), ex);
        }
    }

    /**
     * Creates an <code>EntityStatus</code> based on the results of a query.
     *
     * @param key the entity key
     * @param results the query results
     * @param type the entity type
     * @return a new entity status built from the result set
     * @throws SQLException if a database error occurs
     */
    private static EntityStatus createStatus(EntityKey key, ResultSet results, String type) throws SQLException {
        Status status = new Status(results.getLong(type + "_status_id"));
        status.setCreationDate(results.getDate("create_date"));
        status.setLastUpdatedByUserName(results.getString("create_user"));
        String description = results.getString("description");
        status.setLongDesc(description);
        status.setShortDesc(description);
        status.setName(results.getString("name"));
        status.setUpdateDate(results.getDate("modify_date"));

        return new EntityStatus(key, status, results.getDate("create_date"),
                                results.getString("modify_user"));
    }

    /**
     * Returns the status table associated with the specified table.
     *
     * @param type the entity type
     * @return the status table associated with the specified table
     */
    private static String getStatusTable(String type) {
        return type + "_status";
    }

    /**
     * Returns the status history table associated with the specified table.
     *
     * @param type the entity type
     * @return the status history table associated with the specified table
     */
    private static String getStatusHistoryTable(String type) {
        return type + "_status_history";
    }

    /**
     * Returns the status ID column associated with the specified table.
     *
     * @param type the entity type
     * @return the status ID column associated with the specified table
     */
    private static String getStatusIDColumn(String type) {
        return type + "_status_id";
    }

    /**
     * Verifies that the specified entity type exists in the database.
     *
     * @param connection the connection
     * @param type the entity type
     * @param exists whether to check for an instance of the type
     * @throws RecordNotFoundException if the type does not exist
     * @throws SQLException if a database error occurs
     */
    private void assertTypeExists(Connection connection, String type, boolean exists)
        throws SQLException, StatusTrackerPersistenceException, RecordNotFoundException {
        String table = getStatusTable(type);
        String historyTable = getStatusHistoryTable(type);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table);

        try {
            try {
                ResultSet results = statement.executeQuery();
                closeResults(results);
            } catch (SQLException ex) {
                throw new RecordNotFoundException("status table does not exist for '" + type + "'");
            }
        } finally {
            closeStatement(statement);
        }

        statement = connection.prepareStatement("SELECT * FROM " + historyTable);
        try {
            try {
                ResultSet results = statement.executeQuery();

                // make sure at least one instance of the type exists
//                if (exists && !results.next()) {
//                    throw new RecordNotFoundException("no status entry for '" + type + "'");
//                }

                closeResults(results);
            } catch (SQLException ex) {
                throw new RecordNotFoundException("status history table does not exist for '" + type + "'");
            }
        } finally {
            closeStatement(statement);
        }
    }

    private void assertStatusExists(Connection connection, String type, Status newStatus)
        throws RecordNotFoundException, SQLException, StatusTrackerPersistenceException {
        String table = getStatusTable(type);

        PreparedStatement statement =
            connection.prepareStatement("SELECT * FROM " + table + " WHERE " + type + "_status_id = ?");
        try {
            statement.setLong(1, newStatus.getId());

            ResultSet results = statement.executeQuery();
            try {
                if (!results.next()) {
                    throw new RecordNotFoundException("no status '" + newStatus + "' exists");
                }
            } finally {
                closeResults(results);
            }
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * A helper method that queries the current status of an entity type.
     *
     * @param connection the DB connection
     * @param key the entity type to query
     * @return the status of the specified entity type
     * @throws StatusTrackerPersistenceException if a database error occurs
     * @throws RecordNotFoundException if the status cannot be found
     */
    private EntityStatus getCurrentStatus(Connection connection, EntityKey key)
        throws StatusTrackerPersistenceException {
        String type = key.getType().getName();
        String statusTable = getStatusTable(type);
        String historyTable = getStatusHistoryTable(type);
        String statusID = getStatusIDColumn(type);

        try {
            PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM " + statusTable + ", " + historyTable + " WHERE "
                                            + "end_date IS NULL AND " + statusTable + "." + statusID + " = "
                                            + historyTable + "." + statusID + " AND " + historyTable + "." +
                                            type + "_id = ?");

            try {
                statement.setLong(1, getPrimaryId(key));

                ResultSet results = statement.executeQuery();

                try {
                    if (!results.next()) {
                        throw new RecordNotFoundException("no status found");
                    }

                    return createStatus(key, results, type);
                } finally {
                    closeResults(results);
                }
            } finally {
                closeStatement(statement);
            }
        } catch (SQLException ex) {
            throw new StatusTrackerPersistenceException("SQL error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Gets the primary id of the entity which represents by the given <code>EntityKey</code>.
     *
     * @param entityKey the EntityKey instance.
     * @return the id of the entity.
     */
    private long getPrimaryId(EntityKey entityKey) {
        String primaryKey = entityKey.getType().getPrimaryKeyColumns()[0];

        return ((Long) entityKey.getValues().get(primaryKey)).longValue();
    }
}
