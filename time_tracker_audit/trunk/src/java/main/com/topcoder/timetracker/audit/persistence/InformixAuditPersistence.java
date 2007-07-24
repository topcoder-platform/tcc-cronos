/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditConfigurationException;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditPersistence;
import com.topcoder.timetracker.audit.AuditPersistenceException;
import com.topcoder.timetracker.audit.TimeTrackerAuditHelper;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * Represents the default Persistence plugin provided with this component, which uses an Informix Database for storage.
 * </p>
 *
 * <p>
 * This class supports all defined CRD operations defined in the interface. It stores a database connection factory and
 * connection name to provide access to a configurable database connection, as well as creating a default values
 * contain on construction, used when loaded audit details have missing information. It will use the id generator to
 * generate the unique id and the search bundle to retrieve the expected audit header records from the database. All
 * the errors generated in the database operations will be logged in ERROR level.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is threadsafe - its two database members are immutable after construction and the
 * insert/deletes are performed atomically per audit record, all rolled back when an error occurs. The defaultValues
 * member, while it can be changed, is not altered after construction, so is thread safe.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class InformixAuditPersistence implements AuditPersistence {
    /** Possible SQL statement used to obtain all audit headers from DB. */
    private static final String SQL_SELECT_HEADERS = "SELECT a.audit_id, a.entity_id, a.creation_date, "
        + "a.table_name, a.company_id, a.creation_user, a.action_type, a.client_id, a.project_id, "
        + "a.account_user_id, "
        + "(SELECT DISTINCT c.name FROM client c WHERE c.client_id = a.client_id) AS client_name, "
        + "(SELECT DISTINCT p.name FROM project p WHERE p.project_id = a.project_id) AS project_name, "
        + "a.app_area_id "
        + "FROM audit a";

    /** Possible SQL statement used to obtain all audit details from DB given their audit header id. */
    private static final String SQL_SELECT_DETAILS_FOR_HEADERS =
        "SELECT audit_id,audit_detail_id,old_value,new_value,column_name" +
        " FROM audit_detail WHERE audit_id IN";

    /** Represents the SQL statement used to insert a single audit header row into the database. */
    private static final String SQL_INSERT_AUDIT = "INSERT INTO audit "
        + "(audit_id, app_area_id, client_id, company_id, project_id, account_user_id, "
        + "entity_id, table_name, action_type, creation_date, creation_user) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /** Represents the SQL statement used to insert a single audit detail row into the database. */
    private static final String SQL_INSERT_DETAIL = "INSERT INTO audit_detail "
        + "(audit_detail_id, audit_id, old_value, new_value, column_name) " + "VALUES (?, ?, ?, ?, ?)";

    /** Represents the SQL statement used to remove a single audit row from the database. */
    private static final String SQL_DELETE_AUDIT = "DELETE FROM audit WHERE audit_id = ?";

    /** Represents the SQL statement used to remove all audit details of a given audit header from the database. */
    private static final String SQL_DELETE_DETAILS = "DELETE FROM audit_detail WHERE audit_id = ?";

    /**
     * <p>
     * Represents the property name to retrieve the logName value.
     * </p>
     */
    private static final String LOG_NAME_PROPERTY = "logName";

    /**
     * <p>
     * Represents the property name to retrieve the connectionName value.
     * </p>
     */
    private static final String CONNECTION_NAME_PROPERTY = "connectionName";

    /**
     * <p>
     * Represents the property name to retrieve the connectionFactoryNamespace value.
     * </p>
     */
    private static final String CONNECTION_FACTORY_NAMESPACE_PROPERTY = "connectionFactoryNamespace";

    /**
     * <p>
     * Represents the property name to retrieve the idGeneratorName value.
     * </p>
     */
    private static final String ID_GENERATOR_NAME_PROPERTY = "idGeneratorName";

    /**
     * <p>
     * Represents the property name to retrieve the searchBundleName value.
     * </p>
     */
    private static final String SEARCH_BUNDLE_NAME_PROPERTY = "searchBundleName";

    /**
     * <p>
     * Represents the property name to retrieve the searchBundleNamespace value.
     * </p>
     */
    private static final String SEARCH_BUNDLE_NAMESPACE_PROPERTY = "searchBundleNamespace";

    /**
     * <p>
     * Represents the name of the connection to obtain from the connection factory that is used for performing CRUD
     * operations.
     * </p>
     *
     * <p>
     * This is created in the constructor. This value is optional and will never change. If null, then the default
     * connection will be obtained.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the connection factory that is used for performing CRUD operations.
     * </p>
     *
     * <p>
     * It should be backed by a JNDI connection producer, which simply eases the obtaining of a connection from a
     * datasource via JNDI. This is created in the constructor, will not be null, and will never change.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the ID Generator instance used to obtain IDs for any Audit Header / Details to be added to the
     * database.
     * </p>
     *
     * <p>
     * This is used within the createAuditRecord method, where one ID is generated for the audit header, and one for
     * each detail it contains. It is set on construction through using the ID Generator Factory's static
     * getIDGenerator() method, using a required generator name read in from configuration. After construction this
     * member is immutable, and will not be null.
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * Represents the search bundle to be used to retrieve audits from persistence based on Search Builider Filters
     * passed to the searchAudit method.
     * </p>
     *
     * <p>
     * It is obtained from the SearchBundleManager on construction, using a bundle name from configuration. afterwards,
     * it is immutable and not null.
     * </p>
     */
    private final SearchBundle searchBundle;

    /**
     * <p>
     * Represents the Log used to log any problems in executing operations.
     * </p>
     *
     * <p>
     * Instantiated in the constructor. Won't be null, doesn't change.
     * </p>
     */
    private final Log log;

    /**
     * <p>
     * Creates the new instance of <code>InformixAuditPersistence</code> class from the given namespace. Within this
     * constructor, the (optional) logName is read and used to instantiate the log variable using LogFactory, the
     * (optional) connectionName is loaded, the connectionFactory is constructed, and the defaultValues member
     * initialised using its own namespace. In addition, the ID generator and search bundle members are obtained from
     * their factories using required config parameters.
     * </p>
     *
     * @param namespace configuration namespace.
     *
     * @throws IllegalArgumentException if namespace is null or empty String.
     * @throws AuditConfigurationException If there is an error initialising from configuration.
     */
    public InformixAuditPersistence(String namespace) throws AuditConfigurationException {
        TimeTrackerAuditHelper.validateString(namespace, "namespace");

        String logName = TimeTrackerAuditHelper.getStringPropertyValue(namespace, LOG_NAME_PROPERTY, false);
        this.connectionName = TimeTrackerAuditHelper.getStringPropertyValue(namespace, CONNECTION_NAME_PROPERTY, false);

        String connectionFactoryNamespace = TimeTrackerAuditHelper.getStringPropertyValue(namespace,
                CONNECTION_FACTORY_NAMESPACE_PROPERTY, true);
        String idGeneratorName = TimeTrackerAuditHelper.getStringPropertyValue(namespace, ID_GENERATOR_NAME_PROPERTY,
                true);
        String searchBundleName = TimeTrackerAuditHelper.getStringPropertyValue(namespace, SEARCH_BUNDLE_NAME_PROPERTY,
                true);
        String searchBundleNamespace = TimeTrackerAuditHelper.getStringPropertyValue(namespace,
                SEARCH_BUNDLE_NAMESPACE_PROPERTY, true);

        // create log instance
        log = (logName == null) ? LogManager.getLog() : LogManager.getLog(logName);

        if (log == null) {
            throw new AuditConfigurationException("Fails to create the Log from LogFactory.");
        }

        // create the connectionFactory
        try {
            this.connectionFactory = new DBConnectionFactoryImpl(connectionFactoryNamespace);
        } catch (ConfigurationException e) {
            throw new AuditConfigurationException("can not properly load the configuration for DBConnectionFactory", e);
        } catch (DBConnectionException e) {
            throw new AuditConfigurationException("can not properly get the connection from DBConnectionFactory", e);
        }

        // create the idGenerator
        try {
            this.idGenerator = IDGeneratorFactory.getIDGenerator(idGeneratorName);
            idGenerator.getNextID();
        } catch (IDGenerationException e) {
            throw new AuditConfigurationException("Fails to create the idGenerator from IDGeneratorFactory.", e);
        }

        // create the searchBundle
        try {
            this.searchBundle = new SearchBundleManager(searchBundleNamespace).getSearchBundle(searchBundleName);

            if (searchBundle == null) {
                throw new AuditConfigurationException("Fail to get the searchBundle since the bundle with name of "
                    + searchBundleName + " does not exist.");
            }
        } catch (SearchBuilderConfigurationException e) {
            throw new AuditConfigurationException("Fail to get the searchBundle.", e);
        }
    }

    /**
     * <p>
     * Persists information about a new audit record, including all of its contained 'details'.
     * </p>
     *
     * <p>
     * If the audit header cannot be persisted, applicationArea, tableName and entityId will be logged, and the
     * exception will be thrown then. If a detail is not persisted, the information is logged, the insertion
     * continues, and an exception is thrown at the end.
     * </p>
     *
     * @param record The new audit header record to add.
     *
     * @throws IllegalArgumentException if record is null, or if not null field is not set for given audit header, or
     *         the contained 'details'.
     * @throws AuditPersistenceException If there is any problem when try to insert the new audit record, including all
     *         of its contained 'details'.
     */
    public void createAuditRecord(AuditHeader record) throws AuditPersistenceException {
        TimeTrackerAuditHelper.validateNotNull(record, "record");

        if (record.getCreationDate() == null) {
            record.setCreationDate(new Timestamp(System.currentTimeMillis()));
        }

        // validate whether the not null field is not set.
        validateAuditHeader(record);

        // set the initial status of persisted as false
        record.setPersisted(false);

        AuditDetail[] details = record.getDetails();

        if (details != null) {
            for (int i = 0; i < details.length; i++) {
                details[i].setPersisted(false);
            }
        }

        Connection conn = null;

        try {
            conn = this.createConnection();

            // insert the Audit Header, exceptions will be directly thrown when any error happens
            long auditId = this.insertAuditHeader(conn, record);

            // insert the Audit Details
            this.insertAuditDetails(conn, auditId, record.getDetails());
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * Validates whether the not null field is not set for given audit header, or the contained
     * 'details'. This method does not check whether <code>creationDate</code> property of the
     * <code>auditHeader</code> was set, since this property will never be <code>null</code>.
     *
     * @param auditHeader the audit header to validate.
     *
     * @throws IllegalArgumentException if not null field is not set for given audit header, or the contained
     *         'details'.
     */
    private void validateAuditHeader(AuditHeader auditHeader) {
        TimeTrackerAuditHelper.validateNotNull(auditHeader.getApplicationArea(), "auditHeader#applicationArea");
        TimeTrackerAuditHelper.validateNotNull(auditHeader.getTableName(), "auditHeader#tableName");
        TimeTrackerAuditHelper.validateNotNull(auditHeader.getCreationUser(), "auditHeader#creationUser");

        AuditDetail[] details = auditHeader.getDetails();

        if (details != null) {
            for (int i = 0; i < details.length; i++) {
                TimeTrackerAuditHelper.validateNotNull(details[i], "auditHeader#" + i + "th AuditDetail");
                TimeTrackerAuditHelper.validateNotNull(details[i].getColumnName(),
                    "auditHeader#" + i + "th AuditDetail#columnName");
            }
        }
    }

    /**
     * <p>
     * Insert the AuditHeader into the audit table, all the fields are persisted into the corresponding columns. If
     * everything is ok, the new id will be set, and the persisted flag will also be set as true.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auditHeader the AuditHeader to persist.
     *
     * @return the audit id of the new record.
     *
     * @throws AuditPersistenceException If there is any problem when try to insert the AuditHeader instance.
     */
    private long insertAuditHeader(Connection conn, AuditHeader auditHeader) throws AuditPersistenceException {
        PreparedStatement statement = null;
        AuditPersistenceException auditPersistenceException = null;

        try {
            long auditId = this.getNextID();

            statement = conn.prepareStatement(SQL_INSERT_AUDIT);

            int index = 0;
            statement.setLong(++index, auditId);
            statement.setLong(++index, auditHeader.getApplicationArea().getId());
            if (auditHeader.getClientId() != -1) {
                statement.setLong(++index, auditHeader.getClientId());
            } else {
                statement.setNull(++index, Types.INTEGER);
            }
            if (auditHeader.getCompanyId() != -1) {
                statement.setLong(++index, auditHeader.getCompanyId());
            } else {
                statement.setNull(++index, Types.INTEGER);
            }
            if (auditHeader.getProjectId() != -1) {
                statement.setLong(++index, auditHeader.getProjectId());
            } else {
                statement.setNull(++index, Types.INTEGER);
            }
            if (auditHeader.getResourceId() != -1) {
                statement.setLong(++index, auditHeader.getResourceId());
            } else {
                statement.setNull(++index, Types.INTEGER);
            }
            statement.setLong(++index, auditHeader.getEntityId());
            statement.setString(++index, auditHeader.getTableName());
            statement.setInt(++index, auditHeader.getActionType());
            statement.setTimestamp(++index, auditHeader.getCreationDate());
            statement.setString(++index, auditHeader.getCreationUser());

            statement.executeUpdate();

            // everything is ok
            auditHeader.setId(auditId);
            auditHeader.setPersisted(true);

            return auditId;
        } catch (SQLException e) {
            auditPersistenceException = new AuditPersistenceException(
                "Something wrong in the database related operations.", e);
        } catch (AuditPersistenceException e) {
            auditPersistenceException = e;
        } finally {
            releaseResource(null, statement);
        }

        this.log.log(Level.ERROR, "Error happens when try to insert the AuditHeader instance: \n"
                + "applicationArea: [" + auditHeader.getApplicationArea() + "]\n"
                + "tableName: [" + auditHeader.getTableName() + "]\n"
                + "entityId: [" + auditHeader.getEntityId() + "]\n"
                + "Exceptions: "
                + TimeTrackerAuditHelper.getExceptionStaceTrace(auditPersistenceException) + "\n");
        throw auditPersistenceException;
    }

    /**
     * <p>
     * Insert the AuditDetail objects array into the audit_detail table, all the fields are persisted into the
     * corresponding columns. If everything is ok, the new id will be set, and the persisted flag will also be set as
     * true.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auditId the audit id of the new record.
     * @param auditDetails the AuditDetail objects array to persist.
     *
     * @throws AuditPersistenceException If there is any problem when try to insert the AuditDetail objects array.
     */
    private void insertAuditDetails(Connection conn, long auditId, AuditDetail[] auditDetails)
        throws AuditPersistenceException {
        if ((auditDetails == null) || (auditDetails.length == 0)) {
            return;
        }

        PreparedStatement statement = null;

        AuditPersistenceException auditPersistenceException = null;

        try {
            statement = conn.prepareStatement(SQL_INSERT_DETAIL);

            for (int i = 0; i < auditDetails.length; i++) {

                AuditPersistenceException exception = null;
                try {
                    long auditDetailId = this.getNextID();

                    statement.clearParameters();

                    int index = 0;
                    statement.setLong(++index, auditDetailId);
                    statement.setLong(++index, auditId);
                    statement.setString(++index, auditDetails[i].getOldValue());
                    statement.setString(++index, auditDetails[i].getNewValue());
                    statement.setString(++index, auditDetails[i].getColumnName());

                    statement.executeUpdate();

                    // everything is ok
                    auditDetails[i].setId(auditDetailId);
                    auditDetails[i].setPersisted(true);
                } catch (SQLException e) {
                    exception = new AuditPersistenceException("Something wrong in the database related operations.", e);
                } catch (AuditPersistenceException e) {
                    exception = e;
                }
                if (exception != null) {
                    this.log.log(Level.ERROR, "Error happens when try to insert the AuditDetail instance: \n"
                            + "columnName: [" + auditDetails[i].getColumnName() + "]\n"
                            + "newValue: [" + auditDetails[i].getNewValue() + "]\n"
                            + "oldValue: [" + auditDetails[i].getOldValue() + "]\n"
                            + "Exceptions: "
                            + TimeTrackerAuditHelper.getExceptionStaceTrace(exception) + "\n");
                    auditPersistenceException = exception;
                }
            }
        } catch (SQLException e) {
            this.log.log(Level.ERROR, "Error happens when try to insert the AuditDetail instances: \n"
                    + "auditId: [" + auditId + "]\n"
                    + "Exceptions: "
                    + TimeTrackerAuditHelper.getExceptionStaceTrace(e) + "\n");

            auditPersistenceException = new AuditPersistenceException("Failed to create the prepareStatement.", e);
        } finally {
            releaseResource(null, statement);
        }

        if (auditPersistenceException != null) {
            throw auditPersistenceException;
        }
    }

    /**
     * <p>
     * Searches through the audits and returns the ones which match a given set of criteria. If null, the filter is not
     * used and all the audit headers will be retrieved.
     * </p>
     *
     * @param filter The set of constraints which the header much match to be in the return array. Null indicates no
     *        restriction is to be placed.
     *
     * @return An array of <code>AuditHeader</code> objects that match the given filter. This array may be empty if no
     *         matches are found, but will never be null.
     *
     * @throws AuditPersistenceException If there are any problems searching the audits.
     */
    public AuditHeader[] searchAudit(Filter filter) throws AuditPersistenceException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = this.createConnection();

            List headersList = new ArrayList();

            if (filter == null) {
                // no filter, customer sql query will be used here
                ps = conn.prepareStatement(SQL_SELECT_HEADERS);
                rs = ps.executeQuery();

                while (rs.next()) {
                    headersList.add(this.loadAuditHeader(conn, rs));
                }
            } else {
                // using filter
                CustomResultSet customResultSet = (CustomResultSet) searchBundle.search(filter);

                while (customResultSet.next()) {
                    headersList.add(this.loadAuditHeader(conn, customResultSet));
                }

            }

            AuditHeader[] headers = (AuditHeader[]) headersList.toArray(new AuditHeader[headersList.size()]);

            loadDetails(conn, headers);
            return headers;
        } catch (SearchBuilderException e) {
            throw new AuditPersistenceException("Error occurs when searching audits from search builder.", e);
        } catch (SQLException e) {
            throw new AuditPersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseResource(rs, ps);
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Removes an audit header, and all of its details, from the database. If there is any problem when try to delete
     * the audit record, including all of its contained 'details', the exception will be logged, the delete operation
     * will continue and the exception will be thrown in the end.
     * </p>
     *
     * @param auditHeaderId The unique ID of the audit record to be removed
     *
     * @return Whether anything was removed from persistence or not. TRUE, If the audit header is there and deleted
     *         successfully. FALSE, If the audit header is not there.
     *
     * @throws AuditPersistenceException If there is any problem when try to delete the audit record, including all of
     *         its contained 'details'.
     */
    public boolean rollbackAuditRecord(long auditHeaderId) throws AuditPersistenceException {
        Connection conn = null;

        try {
            conn = this.createConnection();

            AuditPersistenceException auditPersistenceException = null;

            boolean changed = false;

            // delete the Audit Header, exceptions will be hold when any error happens,
            // and continue to delete its contained 'details'.
            try {
                changed = (this.delelteAuditDetails(conn, auditHeaderId) > 0) || changed;
            } catch (AuditPersistenceException e) {
                auditPersistenceException = e;
            }

            // delete the Audit Details
            try {
                changed = (this.deleteAuditHeader(conn, auditHeaderId) > 0) || changed;
            } catch (AuditPersistenceException e) {
                if (auditPersistenceException == null) {
                    auditPersistenceException = e;
                }
            }

            // only the first AuditPersistenceException will be thrown
            if (auditPersistenceException != null) {
                throw auditPersistenceException;
            }

            return changed;
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Delete the records from audit_detail table with given audit header id.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auditHeaderId the audit header id to delete.
     *
     * @return The effected records in the delete operation.
     *
     * @throws AuditPersistenceException If there is any problem when try to delete the audit details.
     */
    private int delelteAuditDetails(Connection conn, long auditHeaderId) throws AuditPersistenceException {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(SQL_DELETE_DETAILS);
            statement.setLong(1, auditHeaderId);

            return statement.executeUpdate();
        } catch (SQLException e) {
            this.log.log(Level.ERROR, "Error happens when try to delete the AuditDetail instances: \n"
                    + "auditId: [" + auditHeaderId + "]\n"
                    + "Exceptions: "
                    + TimeTrackerAuditHelper.getExceptionStaceTrace(e) + "\n");

            throw new AuditPersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Delete the record from audit table with given audit header id.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auditHeaderId the audit header id to delete.
     *
     * @return The effected records in the delete operation.
     *
     * @throws AuditPersistenceException If there is any problem when try to delete the audit.
     */
    private int deleteAuditHeader(Connection conn, long auditHeaderId) throws AuditPersistenceException {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(SQL_DELETE_AUDIT);
            statement.setLong(1, auditHeaderId);

            return statement.executeUpdate();
        } catch (SQLException e) {
            this.log.log(Level.ERROR, "Error happens when try to delete the AuditHeader instance: \n"
                    + "auditId: [" + auditHeaderId + "]\n"
                    + "Exceptions: "
                    + TimeTrackerAuditHelper.getExceptionStaceTrace(e) + "\n");

            throw new AuditPersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Utility method that reads in an audit header given a Custom Result Set pointing to its row in the database. The
     * audit's details will also be read from database.
     * </p>
     *
     * @param conn the connection to the database.
     * @param row The Custom ResultSet cursor at the row in the database result to retrieve the audit header from. This
     *        must be a valid ResultSet cursor, not null.
     *
     * @return An AuditHeader instance, complete with its AuditDetails, that the Custom ResultSet's current row
     *         contained.
     *
     * @throws AuditPersistenceException If there are any problems to load the audit header.
     */
    private AuditHeader loadAuditHeader(Connection conn, CustomResultSet row) throws AuditPersistenceException {
        AuditHeader header = new AuditHeader();

        try {
            header.setId(row.getLong("audit_id"));
            header.setEntityId(row.getLong("entity_id"));
            header.setCreationDate(row.getTimestamp("creation_date"));
            header.setTableName(row.getString("table_name"));
            header.setCreationUser(row.getString("creation_user"));
            header.setActionType(row.getInt("action_type"));
            if (row.getObject("company_id") != null) {
                header.setCompanyId(row.getLong("company_id"));
            }
            if (row.getObject("client_id") != null) {
                header.setClientId(row.getLong("client_id"));
            }
            if (row.getObject("project_id") != null) {
                header.setProjectId(row.getLong("project_id"));
            }
            if (row.getObject("account_user_id") != null) {
                header.setResourceId(row.getLong("account_user_id"));
            }
            if (row.getObject("client_name") != null) {
                header.setClientName(row.getString("client_name"));
            }
            if (row.getObject("project_name") != null) {
                header.setProjectName(row.getString("project_name"));
            }

            Object obj = ApplicationArea.getEnumByOrdinal(row.getInt("app_area_id") - 1, ApplicationArea.class);

            if (!(obj instanceof ApplicationArea)) {
                throw new AuditPersistenceException("Fails to retrieve the ApplicationArea from the customResultSet.");
            }

            header.setApplicationArea((ApplicationArea) obj);
        } catch (InvalidCursorStateException e) {
            throw new AuditPersistenceException("Fails to retrieve the data from the customResultSet.", e);
        }

//        header.setDetails(loadDetails(conn, header.getId()));
        header.setPersisted(true);

        return header;
    }

    /**
     * <p>
     * Utility method that reads in an audit header given a Result Set pointing to its row in the database. The audit's
     * details will also be read from database.
     * </p>
     *
     * @param conn the connection to the database.
     * @param row The ResultSet cursor at the row in the database result to retrieve the audit header from. This must
     *        be a valid ResultSet cursor, not null.
     *
     * @return An AuditHeader instance, complete with its AuditDetails, that the ResultSet's current row contained.
     *
     * @throws AuditPersistenceException If there are any problems to load the audit header.
     */
    private AuditHeader loadAuditHeader(Connection conn, ResultSet row) throws AuditPersistenceException {
        AuditHeader header = new AuditHeader();

        try {
            header.setId(row.getLong("audit_id"));
            header.setEntityId(row.getLong("entity_id"));
            header.setCreationDate(row.getTimestamp("creation_date"));
            header.setTableName(row.getString("table_name"));
            header.setCreationUser(row.getString("creation_user"));
            header.setActionType(row.getInt("action_type"));
            if (row.getObject("company_id") != null) {
                header.setCompanyId(row.getLong("company_id"));
            }
            if (row.getObject("client_id") != null) {
                header.setClientId(row.getLong("client_id"));
            }
            if (row.getObject("project_id") != null) {
                header.setProjectId(row.getLong("project_id"));
            }
            if (row.getObject("account_user_id") != null) {
                header.setResourceId(row.getLong("account_user_id"));
            }
            if (row.getObject("client_name") != null) {
                header.setClientName(row.getString("client_name"));
            }
            if (row.getObject("project_name") != null) {
                header.setProjectName(row.getString("project_name"));
            }

            Object obj = ApplicationArea.getEnumByOrdinal(row.getInt("app_area_id") - 1, ApplicationArea.class);

            if (!(obj instanceof ApplicationArea)) {
                throw new AuditPersistenceException("Fails to retrieve the ApplicationArea from the customResultSet.");
            }

            header.setApplicationArea((ApplicationArea) obj);
        } catch (SQLException e) {
            throw new AuditPersistenceException("Fails to retrieve the data from the ResultSet.", e);
        }

        header.setPersisted(true);

        return header;
    }

    /**
     * <p>
     * Reads in all the details attached to a given audit ID.
     * </p>
     *
     * @param conn the connection to the database.
     * @param auditId The Audit ID whose details are to be obtained.
     *
     * @return An array (non-null, possibly empty) of details matching the given audit ID.
     *
     * @throws AuditPersistenceException If there are any problems to load the audit details.
     */
    private static void loadDetails(Connection conn, AuditHeader[] headers) throws AuditPersistenceException {
        if (headers.length == 0) {
            return;
        }

        StringBuffer statement = new StringBuffer(SQL_SELECT_DETAILS_FOR_HEADERS);

        statement.append(" (");

        for (int i = 0; i < headers.length; ++i) {
            if (i != 0) {
                statement.append(",?");
            } else {
                statement.append('?');
            }
        }
        statement.append(')');

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(statement.toString());
            for (int i = 0; i < headers.length; ++i) {
                ps.setLong(i+1, headers[i].getId());
            }

            rs = ps.executeQuery();

            Map auditIdToDetails = new HashMap(); // key = audit ID, value = list of AuditDetail

            while (rs.next()) {
                AuditDetail detail = new AuditDetail();

                String oldValue = rs.getString("old_value");
                String newValue = rs.getString("new_value");

                detail.setId(rs.getLong("audit_detail_id"));
                detail.setOldValue((oldValue != null) ? oldValue : "");
                detail.setNewValue((newValue != null) ? newValue : "");
                detail.setColumnName(rs.getString("column_name"));
                detail.setPersisted(true);

                final Long auditId = new Long(rs.getLong("audit_id"));
                List details = (List) auditIdToDetails.get(auditId);

                if (details == null) {
                    details = new ArrayList();
                    auditIdToDetails.put(auditId, details);
                }

                details.add(detail);
            }

            for (int i = 0; i < headers.length; ++i) {
                final Long auditId = new Long(headers[i].getId());
                List details = (List) auditIdToDetails.get(auditId);

                if (details != null) {
                    headers[i].setDetails((AuditDetail[]) details.toArray(new AuditDetail[details.size()]));
                } else {
                    headers[i].setDetails(new AuditDetail[0]);
                }
            }
        } catch (SQLException e) {
            throw new AuditPersistenceException("Something wrong in the database related operations.", e);
        } finally {
            releaseResource(rs, ps);
        }
    }

    /**
     * <p>
     * Get database connection from the db connection factory.
     * </p>
     *
     * @return A database connection.
     *
     * @throws AuditPersistenceException If can't get connection.
     */
    private Connection createConnection() throws AuditPersistenceException {
        try {
            // create a DB connection
            return (connectionName == null) ? connectionFactory.createConnection()
                                            : connectionFactory.createConnection(connectionName);
        } catch (DBConnectionException e) {
            throw new AuditPersistenceException("Can't get the connection from database.", e);
        }
    }

    /**
     * <p>
     * Release the connection.
     * </p>
     *
     * @param conn the connection.
     */
    private void releaseConnection(Connection conn) {
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
    private static void releaseResource(ResultSet resultSet, Statement statement) {
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
     * Gets the next ID using the ID generator.
     * </p>
     *
     * @return the next generated unique ID.
     *
     * @throws AuditPersistenceException if a new ID cannot be generated.
     */
    private long getNextID() throws AuditPersistenceException {
        try {
            return this.idGenerator.getNextID();
        } catch (IDGenerationException e) {
            throw new AuditPersistenceException("Next id cannot be generated.", e);
        }
    }
}
