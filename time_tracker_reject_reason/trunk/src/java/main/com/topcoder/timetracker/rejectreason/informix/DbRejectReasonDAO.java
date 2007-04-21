/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.informix;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonDAO;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;
import com.topcoder.timetracker.rejectreason.RejectReasonNotFoundException;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Pluggable database implementation of the RejectReasonDAO interface. It is capable of persisting and retrieving
 * RejectReason information from the database. Create, Retrieve, Update, Delete and Enumerate (CRUDE) methods are
 * provided. There is also a search method that utilizes Filter classes from the Search Builder component.
 * </p>
 *
 * <p>
 * Sample configure of this class:
 * <pre>
 * &lt;Config name=&quot;com.topcoder.timetracker.rejectreason.informix.DbRejectReasonDAO&quot;&gt;
 *   &lt;Property name=&quot;search_bundle_manager_namespace&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.SearchBundle&lt;/Value&gt;
 *   &lt;/Property&gt;
 *   &lt;Property name=&quot;search_bundle_name&quot;&gt;
 *     &lt;Value&gt;DbRejectReasonSearchBundle&lt;/Value&gt;
 *   &lt;/Property&gt;
 * &lt;/Config&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> The class itself is thread safe because of immutability, but the thread safety issue of the
 * class extends to the back-end data store. - Since the this DAO will be used by a Container Managed Transaction
 * session bean and all the transaction responsibility will be delegate to the container, there's no transaction
 * control here to make methods of it atomic.
 * </p>
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class DbRejectReasonDAO implements RejectReasonDAO {
    /**
     * <p>
     * Represents the constant of application area for the Reject Reason. It is used during auditing.
     * </p>
     */
    public static final ApplicationArea APPLICATION_AREA = ApplicationArea.TT_CONFIGURATION;

    /** The configuration namespace of this DAO. */
    private static final String NAMESPACE = DbRejectReasonDAO.class.getName();

    /** The key to retrieve configuration namespace of SearchBundleManager through ConfigManager. */
    private static final String SEARCH_BUNDLE_MANAGER_NAMESPACE = "search_bundle_manager_namespace";

    /** The key to retrieve SearchBundle name. */
    private static final String SEARCH_BUNDLE_NAME = "search_bundle_name";

    /** The sql to insert record into reject_reason table. */
    private static final String INSERT_REJECT_REASON_SQL = "INSERT into reject_reason(reject_reason_id, description, "
        + "active, creation_date, creation_user, modification_date, modification_user) VALUES (?, ?, ?, ?, ? ,?, ?)";

    /** The sql to insert record into comp_rej_reason table. */
    private static final String INSERT_COMP_REJ_REASON_SQL = "INSERT into comp_rej_reason(company_id, "
        + "reject_reason_id, creation_date, creation_user, modification_date, modification_user) VALUES "
        + "(?, ?, ?, ?, ?, ?)";

    /** The sql to query reject reason data with given reject reason id. */
    private static final String QUERY_BY_ID_SQL = "SELECT comp_rej_reason.company_id, reject_reason.description, "
        + "reject_reason.active, reject_reason.creation_date, reject_reason.creation_user, "
        + "reject_reason.modification_date, reject_reason.modification_user FROM reject_reason, comp_rej_reason WHERE "
        + "reject_reason.reject_reason_id = ? AND comp_rej_reason.reject_reason_id = reject_reason.reject_reason_id";

    /** The sql to query reject reason data. */
    private static final String QUERY_SQL = "SELECT reject_reason.reject_reason_id, comp_rej_reason.company_id, "
        + "reject_reason.description, reject_reason.active, reject_reason.creation_date, reject_reason.creation_user, "
        + "reject_reason.modification_date, reject_reason.modification_user FROM reject_reason, comp_rej_reason WHERE "
        + "comp_rej_reason.reject_reason_id = reject_reason.reject_reason_id";

    /** The sql to update record into reject_reason table. */
    private static final String UPDATE_REJECT_REASON_SQL = "UPDATE reject_reason SET reject_reason.description = ?, "
        + "reject_reason.active = ?, reject_reason.modification_date = ?, reject_reason.modification_user = ? WHERE "
        + "reject_reason.reject_reason_id = ?";

    /** The sql to delete record from reject_reason table. */
    private static final String DELETE_REJECT_REASON_SQL = "DELETE FROM reject_reason WHERE "
        + "reject_reason.reject_reason_id = ?";

    /** The sql to delete record from comp_rej_reason table. */
    private static final String DELETE_COMP_REJ_REASON_SQL = "DELETE FROM comp_rej_reason WHERE "
        + "comp_rej_reason.reject_reason_id = ? AND comp_rej_reason.company_id = ?";

    /** The reject_reason table name. */
    private static final String REJECT_REASON = "reject_reason";

    /** The comp_rej_reason table name. */
    private static final String COMP_REJ_REASON = "comp_rej_reason";

    /** The reject_reason_id column name. */
    private static final String REJECT_REASON_ID = "reject_reason_id";

    /** The company_id column name. */
    private static final String COMPANY_ID = "company_id";

    /** The description column name. */
    private static final String DESCRIPTION = "description";

    /** The active column name. */
    private static final String ACTIVE = "active";

    /** The creation_date column name. */
    private static final String CREATION_DATE = "creation_date";

    /** The creation_user column name. */
    private static final String CREATION_USER = "creation_user";

    /** The modification_date column name. */
    private static final String MODIFICATION_DATE = "modification_date";

    /** The modification_user column name. */
    private static final String MODIFICATION_USER = "modification_user";

    /**
     * <p>
     * This is the DataSource where connections to the data store will be retrieved. It may not be null.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * This is the connection name which will be used to retrieve a database connection from the connection factory. It
     * may not be null or empty.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * An instance of the id generator that is used to generate ids for the entities.
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * This is the search bundle that is used to perform the searches based on any provided filters. It is
     * pre-configured with an SQL Statement, and is coupled with another pre-configured Search Builder filter that is
     * used to tie the separate tables together.
     * </p>
     */
    private final SearchBundle searchBundle;

    /**
     * <p>
     * An AuditManager from the Time Tracker Audit component that is used to perform audits upon a database change.
     * </p>
     */
    private final AuditManager auditManager;

    /**
     * <p>
     * Creates a new instance of RejectReasonDAO with given parameters.
     * </p>
     *
     * @param connFactory the connection factory to use.
     * @param connName the connection name to use when retrieving a connection from the connection factory.
     * @param auditManager the AuditManager use to audit data changes
     * @param idGeneratorName the name of Id Generator to use.
     *
     * @throws IllegalArgumentException if any parameter is null or any string is empty.
     * @throws RejectReasonDAOException if error occurs while creating the RejectReasonDAO.
     */
    public DbRejectReasonDAO(DBConnectionFactory connFactory, String connName, AuditManager auditManager,
        String idGeneratorName) throws RejectReasonDAOException {
        if (connFactory == null) {
            throw new IllegalArgumentException("The connFactory is null.");
        }

        if (connName == null) {
            throw new IllegalArgumentException("The connName is null.");
        }

        if (connName.trim().length() == 0) {
            throw new IllegalArgumentException("The connName is empty.");
        }

        if (idGeneratorName == null) {
            throw new IllegalArgumentException("The idGeneratorName is null.");
        }

        if (idGeneratorName.trim().length() == 0) {
            throw new IllegalArgumentException("The idGeneratorName is empty.");
        }

        if (auditManager == null) {
            throw new IllegalArgumentException("The auditManager is null.");
        }

        connectionFactory = connFactory;
        connectionName = connName;
        this.auditManager = auditManager;

        try {
            // Get id generator with given name
            idGenerator = IDGeneratorFactory.getIDGenerator(idGeneratorName);

            // Get SearchBundleManager namespace and SearchBundle name through ConfigManager
            ConfigManager cm = ConfigManager.getInstance();
            String managerNamaspace = cm.getString(NAMESPACE, SEARCH_BUNDLE_MANAGER_NAMESPACE);

            if ((managerNamaspace == null) || (managerNamaspace.trim().length() == 0)) {
                throw new RejectReasonDAOException("Misses property '" + SEARCH_BUNDLE_MANAGER_NAMESPACE
                    + "' in configuration.");
            }

            String searchBundleName = cm.getString(NAMESPACE, SEARCH_BUNDLE_NAME);

            if ((searchBundleName == null) || (searchBundleName.trim().length() == 0)) {
                throw new RejectReasonDAOException("Misses property '" + SEARCH_BUNDLE_NAME + "' in configuration.");
            }

            // Create SearchBundle
            SearchBundleManager manager = new SearchBundleManager(managerNamaspace);
            searchBundle = manager.getSearchBundle(searchBundleName);

            if (searchBundle == null) {
                throw new RejectReasonDAOException("No SearchBundle with name '" + searchBundleName
                    + "' is configured.");
            }
        } catch (IDGenerationException e) {
            throw new RejectReasonDAOException("Error occurred while creating IDGenerator from the given name.", e);
        } catch (SearchBuilderConfigurationException e) {
            throw new RejectReasonDAOException("Error occurred while retrieving search bundle.", e);
        } catch (UnknownNamespaceException e) {
            throw new RejectReasonDAOException("The configuration namespace '" + NAMESPACE + "' is unknown.", e);
        }
    }

    /**
     * <p>
     * Creates a data store entry for the given Reject Reason. An id is automatically generated by the DAO and assigned
     * to the reason. The RejectReason is also considered to have been created by the specified username. If the
     * argument isAudit is true, insert the corresponding audit record in data store.
     * </p>
     *
     * @param rejectReason the RejectReason to be persistent in the data store
     * @param username the username of the user responsible for creating the RejectReason entry within the data store.
     * @param isAudit indicates audit or not.
     *
     * @return the same rejectReason Object, with an assigned id, creationDate, modificationDate, creationUser and
     *         modificationUser assigned appropriately (none null).
     *
     * @throws IllegalArgumentEException if the rejectReason or username is null, or if username is an empty String, or
     *         if the company id of the reject reason is not set.
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     */
    public RejectReason createRejectReason(RejectReason rejectReason, String username, boolean isAudit)
        throws RejectReasonDAOException {
        if (rejectReason == null) {
            throw new IllegalArgumentException("The rejectReason is null.");
        }

        if (username == null) {
            throw new IllegalArgumentException("The username is null.");
        }

        if (username.trim().length() == 0) {
            throw new IllegalArgumentException("The username is empty.");
        }

        if (rejectReason.getCompanyId() <= 0) {
            throw new IllegalArgumentException("The company id of rejectReason must be set first.");
        }

        if (rejectReason.getDescription() == null) {
            throw new IllegalArgumentException("The description of rejectReason must be set first.");
        }

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Set RejectReason data to persist
            if (rejectReason.getId() <= 0) {
                rejectReason.setId(idGenerator.getNextID());
            }

            Date now = new Date(new java.util.Date().getTime());
            rejectReason.setCreationDate(now);
            rejectReason.setCreationUser(username);
            rejectReason.setModificationDate(now);
            rejectReason.setModificationUser(username);

            // Get connection
            connection = connectionFactory.createConnection(connectionName);

            // Persist the reject reason data into reject_reason table
            statement = connection.prepareStatement(INSERT_REJECT_REASON_SQL);
            statement.setLong(1, rejectReason.getId());
            statement.setString(2, rejectReason.getDescription());
            statement.setBoolean(3, rejectReason.getActive());
            statement.setDate(4, now);
            statement.setString(5, username);
            statement.setDate(6, now);
            statement.setString(7, username);
            statement.executeUpdate();
            statement.close();

            // Persist relationship into comp_rej_reason table
            statement = connection.prepareStatement(INSERT_COMP_REJ_REASON_SQL);
            statement.setLong(1, rejectReason.getCompanyId());
            statement.setLong(2, rejectReason.getId());
            statement.setDate(3, now);
            statement.setString(4, username);
            statement.setDate(5, now);
            statement.setString(6, username);
            statement.executeUpdate();
            statement.close();

            // Set un-changed flag
            rejectReason.setChanged(false);

            // Do audit
            if (isAudit) {
                AuditDetail[] details = new AuditDetail[6];

                // Common details for both tables
                details[0] = DAOHelper.createAuditDetail(REJECT_REASON_ID, null, "" + rejectReason.getId());
                details[1] = DAOHelper.createAuditDetail(CREATION_DATE, null, now.toString());
                details[2] = DAOHelper.createAuditDetail(CREATION_USER, null, username);
                details[3] = DAOHelper.createAuditDetail(MODIFICATION_DATE, null, now.toString());
                details[4] = DAOHelper.createAuditDetail(MODIFICATION_USER, null, username);

                // Audit for comp_rej_reason table
                details[5] = DAOHelper.createAuditDetail(COMPANY_ID, null, "" + rejectReason.getCompanyId());
                auditManager.createAuditRecord(DAOHelper.createAdutiHeader(AuditType.INSERT, COMP_REJ_REASON,
                        rejectReason.getId(), rejectReason.getCompanyId(), details, APPLICATION_AREA, now, username));

                // Audit for reject_reason table
                details[5] = DAOHelper.createAuditDetail(DESCRIPTION, null, rejectReason.getDescription());

                AuditDetail[] details2 = new AuditDetail[7];
                details2[0] = DAOHelper.createAuditDetail(ACTIVE, null, "" + rejectReason.getActive());
                System.arraycopy(details, 0, details2, 1, 6);
                auditManager.createAuditRecord(DAOHelper.createAdutiHeader(AuditType.INSERT, REJECT_REASON,
                        rejectReason.getId(), rejectReason.getCompanyId(), details2, APPLICATION_AREA, now, username));
            }
        } catch (SQLException e) {
            throw new RejectReasonDAOException("Error occurred while accessing database.", e);
        } catch (IDGenerationException e) {
            throw new RejectReasonDAOException("Failed to generate id for the reject reason.", e);
        } catch (AuditManagerException e) {
            throw new RejectReasonDAOException("Error occurred while doing audit.", e);
        } catch (DBConnectionException e) {
            throw new RejectReasonDAOException("Failed to get database connection.", e);
        } finally {
            DAOHelper.releaseResources(null, statement, connection);
        }

        return rejectReason;
    }

    /**
     * <p>
     * Retrieves a RejectReason from the data store with the provided id. If no RejectReason with that id exists, then
     * a null is returned.
     * </p>
     *
     * @param id the id of the RejectReason to retrieve from the data store.
     *
     * @return the retrieved RejectReason object or null if there is no corresponding RejectReason.
     *
     * @throws IllegalArgumentException if id is less than or equals to zero.
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     */
    public RejectReason retrieveRejectReason(long id) throws RejectReasonDAOException {
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be positive.");
        }

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            // Execute the query sql to retrieve reject reason data
            connection = connectionFactory.createConnection(connectionName);

            statement = connection.prepareStatement(QUERY_BY_ID_SQL);
            statement.setLong(1, id);

            rs = statement.executeQuery();

            // If no data retrieved, return null
            if (!rs.next()) {
                return null;
            }

            // Read data from result set and re-construct a RejectReason
            return createRejectReason(id, rs.getLong(1), rs.getString(2), rs.getShort(3), rs.getDate(4),
                rs.getString(5), rs.getDate(6), rs.getString(7));
        } catch (SQLException e) {
            throw new RejectReasonDAOException("Error occurred while retrieving RejectReason.", e);
        } catch (DBConnectionException e) {
            throw new RejectReasonDAOException("Failed to get database connection.", e);
        } finally {
            DAOHelper.releaseResources(rs, statement, connection);
        }
    }

    /**
     * <p>
     * Updates the given RejectReason in the data store. The RejectReason is considered to have been modified by the
     * specified username. If the argument isAudit is true, insert the corresponding audit record in data store.
     * </p>
     *
     * @param rejectReason the RejectReason entity to modify.
     * @param username the username of the user responsible for performing the update.
     * @param isAudit indicates audit or not.
     *
     * @throws IllegalArgumentEException if the rejectReason or username is null, or if username is an empty String.
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     * @throws RejectReasonNotFoundException if the RejectReason to update was not found in the data store.
     */
    public void updateRejectReason(RejectReason rejectReason, String username, boolean isAudit)
        throws RejectReasonDAOException {
        if (rejectReason == null) {
            throw new IllegalArgumentException("The rejectReason is null.");
        }

        if (username == null) {
            throw new IllegalArgumentException("The username is null.");
        }

        if (username.trim().length() == 0) {
            throw new IllegalArgumentException("The username is empty.");
        }

        if (rejectReason.getDescription() == null) {
            throw new IllegalArgumentException("The description of rejectReason must be set first.");
        }

        if (rejectReason.getId() <= 0) {
            throw new RejectReasonNotFoundException("Unable to find rejectReason in persistence to update.");
        }

        if (!rejectReason.isChanged()) {
            // No modification, just return
            return;
        }

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Retrieve the old values
            RejectReason oldReason = retrieveRejectReason(rejectReason.getId());

            if (oldReason == null) {
                throw new RejectReasonNotFoundException("Unable to find rejectReason in persistence to update.");
            }

            // Set modification date and user
            Date now = new Date(new java.util.Date().getTime());
            rejectReason.setModificationDate(now);
            rejectReason.setModificationUser(username);

            connection = connectionFactory.createConnection(connectionName);

            // Update the reject reason data into reject_reason table
            statement = connection.prepareStatement(UPDATE_REJECT_REASON_SQL);
            statement.setString(1, rejectReason.getDescription());
            statement.setBoolean(2, rejectReason.getActive());
            statement.setDate(3, now);
            statement.setString(4, username);
            statement.setLong(5, rejectReason.getId());
            statement.executeUpdate();
            statement.close();

            // Set un-changed flag
            rejectReason.setChanged(false);

            // Do audit
            if (isAudit) {
                // Audit for reject_reason table, only audit changed columns
                List details = new ArrayList();

                details.add(DAOHelper.createAuditDetail(MODIFICATION_DATE, oldReason.getModificationDate().toString(),
                        now.toString()));

                if (!oldReason.getModificationUser().equals(username)) {
                    details.add(DAOHelper.createAuditDetail(MODIFICATION_USER, oldReason.getModificationUser(),
                        username));
                }

                if (!oldReason.getDescription().equals(rejectReason.getDescription())) {
                    details.add(DAOHelper.createAuditDetail(DESCRIPTION, oldReason.getDescription(),
                            rejectReason.getDescription()));
                }

                if (oldReason.getActive() != rejectReason.getActive()) {
                    details.add(DAOHelper.createAuditDetail(ACTIVE, "" + oldReason.getActive(),
                            "" + rejectReason.getActive()));
                }

                auditManager.createAuditRecord(DAOHelper.createAdutiHeader(AuditType.UPDATE, REJECT_REASON,
                        rejectReason.getId(), rejectReason.getCompanyId(),
                        (AuditDetail[]) details.toArray(new AuditDetail[details.size()]), APPLICATION_AREA, now,
                        username));
            }
        } catch (SQLException e) {
            throw new RejectReasonDAOException("Error occurred while accessing database.", e);
        } catch (AuditManagerException e) {
            throw new RejectReasonDAOException("Error occurred while doing audit.", e);
        } catch (DBConnectionException e) {
            throw new RejectReasonDAOException("Failed to get database connection.", e);
        } finally {
            DAOHelper.releaseResources(null, statement, connection);
        }
    }

    /**
     * <p>
     * Removes the specified RejectReason from the data store. If the argument isAudit is true, insert the
     * corresponding audit record in data store.
     * </p>
     *
     * @param rejectReason the rejectReason to delete.
     * @param username the username of the user responsible for performing the deletion.
     * @param isAudit indicates audit or not.
     *
     * @throws IllegalArgumentException if the rejectReason is null.
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     * @throws RejectReasonNotFoundException if the RejectReason to delete was not found in the data store.
     */
    public void deleteRejectReason(RejectReason rejectReason, String username, boolean isAudit)
        throws RejectReasonDAOException {
        if (rejectReason == null) {
            throw new IllegalArgumentException("The rejectReason is null.");
        }

        if (username == null) {
            throw new IllegalArgumentException("The username is null.");
        }

        if (username.trim().length() == 0) {
            throw new IllegalArgumentException("The username is empty.");
        }

        if (rejectReason.getId() <= 0) {
            throw new RejectReasonNotFoundException("Unable to find rejectReason in persistence to delete.");
        }

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Retrieve the old values
            RejectReason oldReason = retrieveRejectReason(rejectReason.getId());

            if (oldReason == null) {
                throw new RejectReasonNotFoundException("Unable to find rejectReason in persistence to delete.");
            }

            connection = connectionFactory.createConnection(connectionName);

            // Delete relationship from comp_rej_reason table
            statement = connection.prepareStatement(DELETE_COMP_REJ_REASON_SQL);
            statement.setLong(1, rejectReason.getId());
            statement.setLong(2, rejectReason.getCompanyId());
            statement.executeUpdate();
            statement.close();

            // Delete the reject reason data from reject_reason table
            statement = connection.prepareStatement(DELETE_REJECT_REASON_SQL);
            statement.setLong(1, rejectReason.getId());
            statement.executeUpdate();
            statement.close();

            // Do audit
            if (isAudit) {
                Date now = new Date(new java.util.Date().getTime());
                AuditDetail[] details = new AuditDetail[6];

                // Common details for both tables
                details[0] = DAOHelper.createAuditDetail(REJECT_REASON_ID, "" + oldReason.getId(), null);
                details[1] = DAOHelper.createAuditDetail(CREATION_DATE, oldReason.getCreationDate().toString(), null);
                details[2] = DAOHelper.createAuditDetail(CREATION_USER, oldReason.getCreationUser(), null);
                details[3] = DAOHelper.createAuditDetail(MODIFICATION_DATE, oldReason.getModificationDate().toString(),
                        null);
                details[4] = DAOHelper.createAuditDetail(MODIFICATION_USER, oldReason.getModificationUser(), null);

                // Audit for comp_rej_reason table
                details[5] = DAOHelper.createAuditDetail(COMPANY_ID, null, "" + rejectReason.getCompanyId());
                auditManager.createAuditRecord(DAOHelper.createAdutiHeader(AuditType.DELETE, COMP_REJ_REASON,
                        oldReason.getId(), oldReason.getCompanyId(), details, APPLICATION_AREA, now, username));

                // Audit for reject_reason table
                details[5] = DAOHelper.createAuditDetail("description", oldReason.getDescription(), null);

                AuditDetail[] details2 = new AuditDetail[7];
                details2[0] = DAOHelper.createAuditDetail(ACTIVE, "" + oldReason.getActive(), null);
                System.arraycopy(details, 0, details2, 1, 6);
                auditManager.createAuditRecord(DAOHelper.createAdutiHeader(AuditType.DELETE, REJECT_REASON,
                        oldReason.getId(), oldReason.getCompanyId(), details2, APPLICATION_AREA, now, username));
            }
        } catch (SQLException e) {
            throw new RejectReasonDAOException("Error occurred while accessing database.", e);
        } catch (AuditManagerException e) {
            throw new RejectReasonDAOException("Error occurred while doing audit.", e);
        } catch (DBConnectionException e) {
            throw new RejectReasonDAOException("Failed to get database connection.", e);
        } finally {
            DAOHelper.releaseResources(null, statement, connection);
        }
    }

    /**
     * <p>
     * Enumerates all the RejectReasons that are present within the data store. If no record found an empty array will
     * be return.
     * </p>
     *
     * @return a list of all the RejectReasons within the data store.
     *
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     */
    public RejectReason[] listRejectReasons() throws RejectReasonDAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            // The reject reason list
            List reasons = new ArrayList();

            // Execute the query sql to retrieve reject reason
            connection = connectionFactory.createConnection(connectionName);

            statement = connection.prepareStatement(QUERY_SQL);
            rs = statement.executeQuery();

            // Read data from result set and re-construct all RejectReasons
            while (rs.next()) {
                reasons.add(createRejectReason(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getShort(4),
                        rs.getDate(5), rs.getString(6), rs.getDate(7), rs.getString(8)));
            }

            // Convert the result list to an array and return
            return (RejectReason[]) reasons.toArray(new RejectReason[reasons.size()]);
        } catch (SQLException e) {
            throw new RejectReasonDAOException("Error occurred while retrieving RejectReason.", e);
        } catch (DBConnectionException e) {
            throw new RejectReasonDAOException("Failed to get database connection.", e);
        } finally {
            DAOHelper.releaseResources(rs, statement, connection);
        }
    }

    /**
     * <p>
     * Returns a list of all the RejectReasons within the datastore that satisfy the filters that are provided. If no
     * record found an empty array will be return. The filters are defined using classes from the Search Builder
     * component.
     * </p>
     *
     * @param filter the filter that is used as criterion to facilitate the search..
     *
     * @return a list of RejectReasons that satisfy the search criterion.
     *
     * @throws IllegalArgumentException if the filter is null.
     * @throws RejectReasonDAOException if a problem occurs while accessing the data store.
     */
    public RejectReason[] searchRejectReasons(Filter filter)
        throws RejectReasonDAOException {
        if (filter == null) {
            throw new IllegalArgumentException("The filter is null.");
        }

        try {
            // The reject reason list
            List reasons = new ArrayList();

            // Search reject reason
            CustomResultSet rs = (CustomResultSet) searchBundle.search(filter);

            // Read data from result set and re-construct all RejectReasons
            while (rs.next()) {
                reasons.add(createRejectReason(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getShort(4),
                        rs.getDate(5), rs.getString(6), rs.getDate(7), rs.getString(8)));
            }

            // Convert the result list to an array and return
            return (RejectReason[]) reasons.toArray(new RejectReason[reasons.size()]);
        } catch (SearchBuilderException e) {
            throw new RejectReasonDAOException("Error occurred while searching RejectReason.", e);
        } catch (ClassCastException e) {
            throw new RejectReasonDAOException("Error occurred while getting data from result set.", e);
        } catch (InvalidCursorStateException e) {
            throw new RejectReasonDAOException("Error occurred while getting data from result set.", e);
        }
    }

    /**
     * Re-constructs the RejectReason with the given data.
     *
     * @param id the id of the reject reason.
     * @param companyId the companyId of the reject reason.
     * @param description the body of the reject reason.
     * @param active the active status of the reject reason.
     * @param creationDate the creation date of the reject reason.
     * @param creationUser the creation user of the reject reason.
     * @param modificationDate the modification date of the reject reason.
     * @param modificationUser the modification user of the reject reason.
     *
     * @return the RejectReason object constructed with the given data.
     */
    private RejectReason createRejectReason(long id, long companyId, String description, short active,
        Date creationDate, String creationUser, Date modificationDate, String modificationUser) {
        RejectReason rejectReason = new RejectReason();
        rejectReason.setId(id);
        rejectReason.setCompanyId(companyId);
        rejectReason.setDescription(description);

        if (active == 0) {
            rejectReason.setActive(false);
        } else {
            rejectReason.setActive(true);
        }

        rejectReason.setCreationDate(creationDate);
        rejectReason.setCreationUser(creationUser);
        rejectReason.setModificationDate(modificationDate);
        rejectReason.setModificationUser(modificationUser);

        // Retrieved reject reason is not changed
        rejectReason.setChanged(false);

        return rejectReason;
    }
}
