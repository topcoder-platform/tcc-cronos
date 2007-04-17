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
import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.RejectEmailDAO;
import com.topcoder.timetracker.rejectreason.RejectEmailDAOException;
import com.topcoder.timetracker.rejectreason.RejectEmailNotFoundException;

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
 * Database implementation of the RejectEmailDAO interface. It is capable of persisting and retrieving RejectEmail
 * information from the database. Create, Retrieve, Update, Delete and Enumerate (CRUDE) methods are provided. There
 * is also a search method that utilizes Filter classes from the Search Builder component.
 * </p>
 *
 * <p>
 * Sample configure of this class:
 * <pre>
 * &lt;Config name=&quot;com.topcoder.timetracker.rejectreason.informix.DbRejectEmailDAO&quot;&gt;
 *   &lt;Property name=&quot;search_bundle_manager_namespace&quot;&gt;
 *     &lt;Value&gt;com.topcoder.search.builder.SearchBundle&lt;/Value&gt;
 *   &lt;/Property&gt;
 *   &lt;Property name=&quot;search_bundle_name&quot;&gt;
 *     &lt;Value&gt;DbRejectEmailSearchBundle&lt;/Value&gt;
 *   &lt;/Property&gt;
 * &lt;/Config&gt;
 * </pre>
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> The class itself is thread safe because of immutability, but the thread safety issue of the
 * class extends to the back-end data store. Since the this DAO will be used by a Container Managed Transaction
 * session bean and all the transaction responsibility will be delegate to the container, there's no transaction
 * control here to make methods of it atomic.
 * </p>
 *
 * @author wangqing, TCSDEVELOPER
 * @version 3.2
 */
public class DbRejectEmailDAO implements RejectEmailDAO {
    /**
     * <p>
     * Represents the constant of application area for the Reject Email. It is used during auditing.
     * </p>
     */
    public static final ApplicationArea APPLICATION_AREA = ApplicationArea.TT_CONFIGURATION;

    /** The configuration namespace of RejectEmailDAO. */
    private static final String NAMESPACE = DbRejectEmailDAO.class.getName();

    /** The key to retrieve configuration namespace of SearchBundleManager through ConfigManager. */
    private static final String SEARCH_BUNDLE_MANAGER_NAMESPACE = "search_bundle_manager_namespace";

    /** The key to retrieve SearchBundle name. */
    private static final String SEARCH_BUNDLE_NAME = "search_bundle_name";

    /** The sql to insert record into reject_email table. */
    private static final String INSERT_REJECT_EMAIL_SQL = "INSERT into reject_email(reject_email_id, body, "
        + "creation_date, creation_user, modification_date, modification_user) VALUES (?, ?, ?, ?, ? ,?)";

    /** The sql to insert record into comp_rej_email table. */
    private static final String INSERT_COMP_REJ_EMAIL_SQL = "INSERT into comp_rej_email(company_id, reject_email_id, "
        + "creation_date, creation_user, modification_date, modification_user) VALUES (?, ?, ?, ?, ?, ?)";

    /** The sql to query reject email data with given reject email id. */
    private static final String QUERY_BY_ID_SQL = "SELECT comp_rej_email.company_id, reject_email.body, "
        + "reject_email.creation_date, reject_email.creation_user, reject_email.modification_date, "
        + "reject_email.modification_user FROM reject_email, comp_rej_email WHERE reject_email.reject_email_id = ? AND "
        + "comp_rej_email.reject_email_id = reject_email.reject_email_id";

    /** The sql to query reject email data. */
    private static final String QUERY_SQL = "SELECT reject_email.reject_email_id, comp_rej_email.company_id, "
        + "reject_email.body, reject_email.creation_date, reject_email.creation_user,reject_email.modification_date, "
        + "reject_email.modification_user FROM reject_email, comp_rej_email WHERE comp_rej_email.reject_email_id = "
        + "reject_email.reject_email_id";

    /** The sql to update record into reject_email table. */
    private static final String UPDATE_REJECT_EMAIL_SQL = "UPDATE reject_email SET reject_email.body = ?, "
        + "reject_email.modification_date = ?, reject_email.modification_user = ? WHERE reject_email.reject_email_id=?";

    /** The sql to delete record from reject_email table. */
    private static final String DELETE_REJECT_EMAIL_SQL = "DELETE FROM reject_email WHERE "
        + "reject_email.reject_email_id = ?";

    /** The sql to delete record from comp_rej_email table. */
    private static final String DELETE_COMP_REJ_EMAIL_SQL = "DELETE FROM comp_rej_email WHERE "
        + "comp_rej_email.reject_email_id = ? AND comp_rej_email.company_id = ?";

    /** The reject_email table name. */
    private static final String REJECT_EMAIL = "reject_email";

    /** The comp_rej_email table name. */
    private static final String COMP_REJ_EMAIL = "comp_rej_email";

    /** The reject_email_id column name. */
    private static final String REJECT_EMAIL_ID = "reject_email_id";

    /** The company_id column name. */
    private static final String COMPANY_ID = "company_id";

    /** The body column name. */
    private static final String BODY = "body";

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
     * This is the connection factory where where connections to the data store will be retrieved. It may not be null.
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
     * pre-configured with an SQL Statement.
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
     * Creates a new instance of RejectEmailDAO with given parameters.
     * </p>
     *
     * @param connFactory the connection factory to use.
     * @param connName the connection name to use when retrieving a connection from the connection factory.
     * @param auditManager the AuditManager use to audit data changes
     * @param idGeneratorName the name of Id Generator to use.
     *
     * @throws IllegalArgumentException if any parameter is null or any string is empty.
     * @throws RejectEmailDAOException if error occurs while creating the RejectEmailDAO.
     */
    public DbRejectEmailDAO(DBConnectionFactory connFactory, String connName, AuditManager auditManager,
        String idGeneratorName) throws RejectEmailDAOException {
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
                throw new RejectEmailDAOException("Misses property '" + SEARCH_BUNDLE_MANAGER_NAMESPACE
                    + "' in configuration.");
            }

            String searchBundleName = cm.getString(NAMESPACE, SEARCH_BUNDLE_NAME);

            if ((searchBundleName == null) || (searchBundleName.trim().length() == 0)) {
                throw new RejectEmailDAOException("Misses property '" + SEARCH_BUNDLE_NAME + "' in configuration.");
            }

            // Create SearchBundle
            SearchBundleManager manager = new SearchBundleManager(managerNamaspace);
            searchBundle = manager.getSearchBundle(searchBundleName);

            if (searchBundle == null) {
                throw new RejectEmailDAOException("No SearchBundle with name '" + searchBundleName
                    + "' is configured.");
            }
        } catch (IDGenerationException e) {
            throw new RejectEmailDAOException("Error occurred while creating IDGenerator from the given name.", e);
        } catch (SearchBuilderConfigurationException e) {
            throw new RejectEmailDAOException("Error occurred while retrieving search bundle.", e);
        } catch (UnknownNamespaceException e) {
            throw new RejectEmailDAOException("The configuration namespace '" + NAMESPACE + "' is unknown.", e);
        }
    }

    /**
     * <p>
     * Creates a data store entry for the given Reject Email. An id is automatically generated by the DAO and assigned
     * to the Email. The RejectEmail is also considered to have been created by the specified username. If the
     * argument isAudit is true, insert the corresponding audit record in data store.
     * </p>
     *
     * @param rejectEmail the rejectEmail to create within the data store.
     * @param username the username of the user responsible for creating the RejectEmail entry within the data store.
     * @param isAudit indicates audit or not.
     *
     * @return the same rejectEmail Object, with an assigned id, creationDate, modificationDate, creationUser and
     *         modificationUser assigned appropriately.
     *
     * @throws IllegalArgumentException if the rejectEmail or username is null, or if username is an empty String, or
     *         the company id or body of the passed in reject email is not set.
     * @throws RejectEmailDAOException if a problem occurs while accessing the data store.
     */
    public RejectEmail createRejectEmail(RejectEmail rejectEmail, String username, boolean isAudit)
        throws RejectEmailDAOException {
        if (rejectEmail == null) {
            throw new IllegalArgumentException("The rejectEmail is null.");
        }

        if (username == null) {
            throw new IllegalArgumentException("The username is null.");
        }

        if (username.trim().length() == 0) {
            throw new IllegalArgumentException("The username is empty.");
        }

        if (rejectEmail.getCompanyId() <= 0) {
            throw new IllegalArgumentException("The company id of rejectEmail must be set first.");
        }

        if (rejectEmail.getBody() == null) {
            throw new IllegalArgumentException("The body of rejectEmail must be set first.");
        }

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Set RejectEmail data to persist
            if (rejectEmail.getId() <= 0) {
                rejectEmail.setId(idGenerator.getNextID());
            }

            Date now = new Date(new java.util.Date().getTime());
            rejectEmail.setCreationDate(now);
            rejectEmail.setCreationUser(username);
            rejectEmail.setModificationDate(now);
            rejectEmail.setModificationUser(username);

            // Get connection
            connection = connectionFactory.createConnection(connectionName);

            // Persist the reject email data into reject_email table
            statement = connection.prepareStatement(INSERT_REJECT_EMAIL_SQL);

            int index = 1;
            statement.setLong(index++, rejectEmail.getId());
            statement.setString(index++, rejectEmail.getBody());
            statement.setDate(index++, now);
            statement.setString(index++, username);
            statement.setDate(index++, now);
            statement.setString(index, username);
            statement.executeUpdate();
            statement.close();

            // Persist relationship into comp_rej_email table
            statement = connection.prepareStatement(INSERT_COMP_REJ_EMAIL_SQL);
            index = 1;
            statement.setLong(index++, rejectEmail.getCompanyId());
            statement.setLong(index++, rejectEmail.getId());
            statement.setDate(index++, now);
            statement.setString(index++, username);
            statement.setDate(index++, now);
            statement.setString(index, username);
            statement.executeUpdate();
            statement.close();

            // Set un-changed flag
            rejectEmail.setChanged(false);

            // Do audit
            if (isAudit) {
                AuditDetail[] details = new AuditDetail[6];

                // Common details for both tables
                index = 0;
                details[index++] = DAOHelper.createAuditDetail(REJECT_EMAIL_ID, null, "" + rejectEmail.getId());
                details[index++] = DAOHelper.createAuditDetail(CREATION_DATE, null, now.toString());
                details[index++] = DAOHelper.createAuditDetail(CREATION_USER, null, username);
                details[index++] = DAOHelper.createAuditDetail(MODIFICATION_DATE, null, now.toString());
                details[index++] = DAOHelper.createAuditDetail(MODIFICATION_USER, null, username);

                // Audit for reject_email table
                details[index] = DAOHelper.createAuditDetail(BODY, null, rejectEmail.getBody());
                auditManager.createAuditRecord(DAOHelper.createAdutiHeader(AuditType.INSERT, REJECT_EMAIL,
                        rejectEmail.getId(), rejectEmail.getCompanyId(), details, APPLICATION_AREA, now, username));

                // Audit for comp_rej_email table
                details[index] = DAOHelper.createAuditDetail(COMPANY_ID, null, "" + rejectEmail.getCompanyId());
                auditManager.createAuditRecord(DAOHelper.createAdutiHeader(AuditType.INSERT, COMP_REJ_EMAIL,
                        rejectEmail.getId(), rejectEmail.getCompanyId(), details, APPLICATION_AREA, now, username));
            }
        } catch (SQLException e) {
            throw new RejectEmailDAOException("Error occurred while accessing database.", e);
        } catch (IDGenerationException e) {
            throw new RejectEmailDAOException("Failed to generate id for the reject email.", e);
        } catch (AuditManagerException e) {
            throw new RejectEmailDAOException("Error occurred while doing audit.", e);
        } catch (DBConnectionException e) {
            throw new RejectEmailDAOException("Failed to get database connection.", e);
        } finally {
            DAOHelper.releaseResources(null, statement, connection);
        }

        return rejectEmail;
    }

    /**
     * <p>
     * Retrieves a RejectEmail from the data store with the provided id. If no RejectEmail with that id exists, then a
     * null is returned.
     * </p>
     *
     * @param id the id of the RejectEmail to retrieve from the data store.
     *
     * @return the retrieved RejectEmail object or null if there is no corresponding RejectEmail.
     *
     * @throws IllegalArgumentException if id is less than or equals to zero.
     * @throws RejectEmailDAOException if a problem occurs while accessing the data store.
     */
    public RejectEmail retrieveRejectEmail(long id) throws RejectEmailDAOException {
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be positive.");
        }

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            // Execute the query sql to retrieve reject email data
            connection = connectionFactory.createConnection(connectionName);

            statement = connection.prepareStatement(QUERY_BY_ID_SQL);
            statement.setLong(1, id);

            rs = statement.executeQuery();

            // If no data retrieved, return null
            if (!rs.next()) {
                return null;
            }

            // Read data from result set and re-construct a RejectEmail
            return createRejectEmail(id, rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getDate(5),
                rs.getString(6));
        } catch (SQLException e) {
            throw new RejectEmailDAOException("Error occurred while retrieving RejectEmail.", e);
        } catch (DBConnectionException e) {
            throw new RejectEmailDAOException("Failed to get database connection.", e);
        } finally {
            DAOHelper.releaseResources(rs, statement, connection);
        }
    }

    /**
     * <p>
     * Updates the given RejectEmail in the data store. The RejectEmail is considered to have been modified by the
     * specified username. If the argument isAudit is true, insert the corresponding audit record in data store.
     * </p>
     *
     * @param rejectEmail the RejectEmail entity to modify.
     * @param username the username of the user responsible for performing the update.
     * @param isAudit indicates audit or not.
     *
     * @throws IllegalArgumentException if the rejectEmail or username is null, or if username is an empty String, or
     *         if the body of rejectEmail is not set.
     * @throws RejectEmailDAOException if a problem occurs while accessing the data store.
     * @throws RejectEmailNotFoundException if the RejectEmail to update was not found in the data store.
     */
    public void updateRejectEmail(RejectEmail rejectEmail, String username, boolean isAudit)
        throws RejectEmailDAOException {
        if (rejectEmail == null) {
            throw new IllegalArgumentException("The rejectEmail is null.");
        }

        if (username == null) {
            throw new IllegalArgumentException("The username is null.");
        }

        if (username.trim().length() == 0) {
            throw new IllegalArgumentException("The username is empty.");
        }

        if (rejectEmail.getBody() == null) {
            throw new IllegalArgumentException("The body of rejectEmail must be set first.");
        }

        if (rejectEmail.getId() <= 0) {
            throw new RejectEmailNotFoundException("Unable to find rejectEmail in persistence to update.");
        }

        if (!rejectEmail.isChanged()) {
            // No modification, just return
            return;
        }

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Retrieve the old values
            RejectEmail oldEmail = retrieveRejectEmail(rejectEmail.getId());

            if (oldEmail == null) {
                throw new RejectEmailNotFoundException("Unable to find rejectEmail in persistence to update.");
            }

            // Set modification date and user
            Date now = new Date(new java.util.Date().getTime());
            rejectEmail.setModificationDate(now);
            rejectEmail.setModificationUser(username);

            // Get connection
            connection = connectionFactory.createConnection(connectionName);

            // Update the reject email data into reject_email table
            statement = connection.prepareStatement(UPDATE_REJECT_EMAIL_SQL);
            int index = 1;
            statement.setString(index++, rejectEmail.getBody());
            statement.setDate(index++, now);
            statement.setString(index++, username);
            statement.setLong(index, rejectEmail.getId());
            statement.executeUpdate();
            statement.close();

            // Set un-changed flag
            rejectEmail.setChanged(false);

            // Do audit
            if (isAudit) {
                // Audit for reject_email table, only audit changed columns
                List details = new ArrayList();

                details.add(DAOHelper.createAuditDetail(MODIFICATION_DATE, oldEmail.getModificationDate().toString(),
                        now.toString()));

                if (!oldEmail.getModificationUser().equals(username)) {
                    details.add(DAOHelper.createAuditDetail(MODIFICATION_USER, oldEmail.getModificationUser(),
                        username));
                }

                if (!oldEmail.getBody().equals(rejectEmail.getBody())) {
                    details.add(DAOHelper.createAuditDetail(BODY, oldEmail.getBody(), rejectEmail.getBody()));
                }

                auditManager.createAuditRecord(DAOHelper.createAdutiHeader(AuditType.UPDATE, REJECT_EMAIL,
                        rejectEmail.getId(), rejectEmail.getCompanyId(),
                        (AuditDetail[]) details.toArray(new AuditDetail[details.size()]), APPLICATION_AREA, now,
                        username));
            }
        } catch (SQLException e) {
            throw new RejectEmailDAOException("Error occurred while accessing database.", e);
        } catch (AuditManagerException e) {
            throw new RejectEmailDAOException("Error occurred while doing audit.", e);
        } catch (DBConnectionException e) {
            throw new RejectEmailDAOException("Failed to get database connection.", e);
        } finally {
            DAOHelper.releaseResources(null, statement, connection);
        }
    }

    /**
     * <p>
     * Removes the specified RejectEmail from the data store. If the argument isAudit is true, insert the corresponding
     * audit record in data store.
     * </p>
     *
     * @param rejectEmail the rejectEmail to delete.
     * @param username the username of the user responsible for performing the deletion.
     * @param isAudit indicates audit or not.
     *
     * @throws IllegalArgumentException if the rejectEmail is null.
     * @throws RejectEmailDAOException if a problem occurs while accessing the data store.
     * @throws RejectEmailNotFoundException if the RejectEmail to delete was not found in the data store.
     */
    public void deleteRejectEmail(RejectEmail rejectEmail, String username, boolean isAudit)
        throws RejectEmailDAOException {
        if (rejectEmail == null) {
            throw new IllegalArgumentException("The rejectEmail is null.");
        }

        if (username == null) {
            throw new IllegalArgumentException("The username is null.");
        }

        if (username.trim().length() == 0) {
            throw new IllegalArgumentException("The username is empty.");
        }

        if (rejectEmail.getId() <= 0) {
            throw new RejectEmailNotFoundException("Unable to find rejectEmail in persistence to delete.");
        }

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Retrieve the old values
            RejectEmail oldEmail = retrieveRejectEmail(rejectEmail.getId());

            if (oldEmail == null) {
                throw new RejectEmailNotFoundException("Unable to find rejectEmail in persistence to delete.");
            }

            // Get connection
            connection = connectionFactory.createConnection(connectionName);

            // Delete relationship from comp_rej_email table
            statement = connection.prepareStatement(DELETE_COMP_REJ_EMAIL_SQL);
            statement.setLong(1, rejectEmail.getId());
            statement.setLong(2, rejectEmail.getCompanyId());
            statement.executeUpdate();
            statement.close();

            // Delete the reject email data from reject_email table
            statement = connection.prepareStatement(DELETE_REJECT_EMAIL_SQL);
            statement.setLong(1, rejectEmail.getId());
            statement.executeUpdate();
            statement.close();

            // Do audit
            if (isAudit) {
                Date now = new Date(new java.util.Date().getTime());
                AuditDetail[] details = new AuditDetail[6];

                // Common details for both tables
                int index = 0;
                details[index++] = DAOHelper.createAuditDetail(REJECT_EMAIL_ID, "" + oldEmail.getId(), null);
                details[index++] = DAOHelper.createAuditDetail(CREATION_DATE, oldEmail.getCreationDate().toString(),
                    null);
                details[index++] = DAOHelper.createAuditDetail(CREATION_USER, oldEmail.getCreationUser(), null);
                details[index++] = DAOHelper.createAuditDetail(MODIFICATION_DATE,
                    oldEmail.getModificationDate().toString(),
                        null);
                details[index++] = DAOHelper.createAuditDetail(MODIFICATION_USER, oldEmail.getModificationUser(), null);

                // Audit for comp_rej_email table
                details[index] = DAOHelper.createAuditDetail(COMPANY_ID, null, "" + rejectEmail.getCompanyId());
                auditManager.createAuditRecord(DAOHelper.createAdutiHeader(AuditType.DELETE, COMP_REJ_EMAIL,
                        rejectEmail.getId(), rejectEmail.getCompanyId(), details, APPLICATION_AREA, now, username));

                // Audit for reject_email table
                details[index] = DAOHelper.createAuditDetail(BODY, oldEmail.getBody(), null);
                auditManager.createAuditRecord(DAOHelper.createAdutiHeader(AuditType.DELETE, REJECT_EMAIL,
                        rejectEmail.getId(), rejectEmail.getCompanyId(), details, APPLICATION_AREA, now, username));
            }
        } catch (SQLException e) {
            throw new RejectEmailDAOException("Error occurred while accessing database.", e);
        } catch (AuditManagerException e) {
            throw new RejectEmailDAOException("Error occurred while doing audit.", e);
        } catch (DBConnectionException e) {
            throw new RejectEmailDAOException("Failed to get database connection.", e);
        } finally {
            DAOHelper.releaseResources(null, statement, connection);
        }
    }

    /**
     * <p>
     * Enumerates all the RejectEmails that are present within the data store. If no record found an empty array will
     * be return.
     * </p>
     *
     * @return a list of all the RejectEmails within the data store.
     *
     * @throws RejectEmailDAOException if a problem occurs while accessing the datastore.
     */
    public RejectEmail[] listRejectEmails() throws RejectEmailDAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            // The reject email list
            List emails = new ArrayList();

            // Execute the query sql to retrieve reject email
            connection = connectionFactory.createConnection(connectionName);

            statement = connection.prepareStatement(QUERY_SQL);
            rs = statement.executeQuery();

            // Read data from result set and re-construct all RejectEmails
            while (rs.next()) {
                emails.add(createRejectEmail(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getDate(4),
                        rs.getString(5), rs.getDate(6), rs.getString(7)));
            }

            // Convert the result list to an array and return
            return (RejectEmail[]) emails.toArray(new RejectEmail[emails.size()]);
        } catch (SQLException e) {
            throw new RejectEmailDAOException("Error occurred while retrieving RejectEmail.", e);
        } catch (DBConnectionException e) {
            throw new RejectEmailDAOException("Failed to get database connection.", e);
        } finally {
            DAOHelper.releaseResources(rs, statement, connection);
        }
    }

    /**
     * <p>
     * Returns a list of all the RejectEmails within the data store that satisfy the filters that are provided. If no
     * record found an empty array will be return. The filters are defined using classes from the Search Builder
     * component.
     * </p>
     *
     * @param filter the filter that is used as criterion to facilitate the search.
     *
     * @return a list of RejectEmails that satisfy the search criterion.
     *
     * @throws IllegalArgumentException if the filter is null.
     * @throws RejectEmailDAOException if a problem occurs while accessing the datastore.
     */
    public RejectEmail[] searchRejectEmails(Filter filter)
        throws RejectEmailDAOException {
        if (filter == null) {
            throw new IllegalArgumentException("The filter is null.");
        }

        try {
            // The reject email list
            List emails = new ArrayList();

            // Search reject email
            CustomResultSet rs = (CustomResultSet) searchBundle.search(filter);

            // Read data from result set and re-construct all RejectEmails
            while (rs.next()) {
                emails.add(createRejectEmail(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getDate(4),
                        rs.getString(5), rs.getDate(6), rs.getString(7)));
            }

            // Convert the result list to an array and return
            return (RejectEmail[]) emails.toArray(new RejectEmail[emails.size()]);
        } catch (SearchBuilderException e) {
            throw new RejectEmailDAOException("Error occurred while searching RejectEmail.", e);
        } catch (ClassCastException e) {
            throw new RejectEmailDAOException("Error occurred while getting data from result set.", e);
        } catch (InvalidCursorStateException e) {
            throw new RejectEmailDAOException("Error occurred while getting data from result set.", e);
        }
    }

    /**
     * Re-constructs the RejectEmail with the given data.
     *
     * @param id the id of the reject email.
     * @param companyId the companyId of the reject email.
     * @param body the body of the reject email.
     * @param creationDate the creation date of the reject email.
     * @param creationUser the creation user of the reject email.
     * @param modificationDate the modification date of the reject email.
     * @param modificationUser the modification user of the reject email.
     *
     * @return the RejectEmail object constructed with the given data.
     */
    private RejectEmail createRejectEmail(long id, long companyId, String body, Date creationDate, String creationUser,
        Date modificationDate, String modificationUser) {
        RejectEmail rejectEmail = new RejectEmail();
        rejectEmail.setId(id);
        rejectEmail.setCompanyId(companyId);
        rejectEmail.setBody(body);
        rejectEmail.setCreationDate(creationDate);
        rejectEmail.setCreationUser(creationUser);
        rejectEmail.setModificationDate(modificationDate);
        rejectEmail.setModificationUser(modificationUser);

        return rejectEmail;
    }
}
