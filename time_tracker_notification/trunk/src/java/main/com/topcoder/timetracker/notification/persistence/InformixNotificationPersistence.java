/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification.persistence;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.database.DatabaseSearchStrategy;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.notification.Helper;
import com.topcoder.timetracker.notification.Notification;
import com.topcoder.timetracker.notification.NotificationConfigurationException;
import com.topcoder.timetracker.notification.NotificationFilterFactory;
import com.topcoder.timetracker.notification.NotificationPersistence;
import com.topcoder.timetracker.notification.NotificationPersistenceException;

import com.topcoder.util.datavalidator.ObjectValidator;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * InformixNotificationPersistence provides the functionality of managing the Notification entity in the Informix
 * database.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread safe as long as the inner SearchBundle is not modified externally.
 * </p>
 *
 * @author ShindouHikaru, kzhu
 * @version 3.2
 */
public class InformixNotificationPersistence implements NotificationPersistence {

	/**
     * <p>
     * Represents the SQL command to select the notification of specified id.
     * </p>
     */
    private static final String SQL_SELECT_NOTIFICATION = "select notification_id, company_id, from_address,"
        + " subject, message, last_time_sent, next_time_send, status, scheduleId, creation_user,"
        + " creation_date, modification_user, modification_date from notification where notification_id = ?";

    /**
     * <p>
     * Represents the SQL command to select the client ids related to specified notification.
     * </p>
     */
    private static final String SQL_SELECT_CLIENTS = "select client_id from notify_clients where notification_id = ?";

    /**
     * <p>
     * Represents the SQL command to select the resource ids related to specified notification.
     * </p>
     */
    private static final String SQL_SELECT_RESOURCES = "select user_account_id from notify_resources"
        + " where notification_id = ?";

    /**
     * <p>
     * Represents the SQL command to select the project ids related to specified notification.
     * </p>
     */
    private static final String SQL_SELECT_PROJECTS = "select project_id from notify_projects"
        + " where notification_id = ?";

    /**
     * <p>
     * Represents the SQL command to select all the notification.
     * </p>
     */
    private static final String SQL_SELECT_ALL_NOTIFICATION = "select notification_id, company_id, from_address,"
        + " subject, message, last_time_sent, next_time_send, status, scheduleId, creation_user,"
        + " creation_date, modification_user, modification_date from notification";

    /**
     * <p>
     * Represents SQL command to select all clients.
     * </p>
     */
    private static final String SQL_SELECT_ALL_CLIENTS = "select creation_user, creation_date, modification_user,"
        + " modification_date, notification_id, client_id from notify_clients where notification_id = ?";

    /**
     * <p>
     * Represents SQL command to select all the projects.
     * </p>
     */
    private static final String SQL_SELECT_ALL_PROJECTS = "select creation_user, creation_date, modification_user,"
        + " modification_date, notification_id, project_id from notify_projects where notification_id = ?";

    /**
     * <p>
     * Represents SQl command to select all the resources.
     * </p>
     */
    private static final String SQL_SELECT_ALL_RESOURCES = "select creation_user, creation_date, modification_user,"
        + " modification_date, notification_id, user_account_id from notify_resources where notification_id = ?";

    /**
     * <p>
     * Represents the SQL command to insert the notification to the database.
     * </p>
     */
    private static final String SQL_INSERT_NOTIFICATION = "insert into notification (notification_id,"
        + " company_id, from_address, subject, message, last_time_sent, next_time_send, status, scheduleId,"
        + " creation_user, creation_date, modification_user, modification_date) values (?, ?, ?, ?, ?, ?,"
        + " ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL command to insert the notification clients to the database.
     * </p>
     */
    private static final String SQL_INSERT_CLIENTS = "insert into notify_clients (creation_user,"
        + " creation_date, modification_user, modification_date, notification_id, client_id) values"
        + " (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL command to insert the notification projects to the database.
     * </p>
     */
    private static final String SQL_INSERT_PROJECTS = "insert into notify_projects (creation_user,"
        + " creation_date, modification_user, modification_date, notification_id, project_id) values"
        + " (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL command to insert the notification resources to the database.
     * </p>
     */
    private static final String SQL_INSERT_RESOURCES = "insert into notify_resources (creation_user,"
        + " creation_date, modification_user, modification_date, notification_id, user_account_id) values"
        + " (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL command to delete from the notify_clients table.
     * </p>
     */
    private static final String SQL_DELETE_CLIENTS = "delete from notify_clients where notification_id = ?";

    /**
     * <p>
     * Represents the SQL command to delete from the notify_projects table.
     * </p>
     */
    private static final String SQL_DELETE_PROJECTS = "delete from notify_projects where notification_id = ?";

    /**
     * <p>
     * Represents the SQL command to delete from the notify_resources table.
     * </p>
     */
    private static final String SQL_DELETE_RESOURCES = "delete from notify_resources where notification_id = ?";

    /**
     * <p>
     * Represents the SQL command to delete from the notification table.
     * </p>
     */
    private static final String SQL_DELETE_NOTIFICATION = "delete from notification where notification_id = ?";

    /**
     * <p>
     * Represents the SQL command to update the notification.
     * </p>
     */
    private static final String SQL_UPDATE_NOTIFICATION = "update notification set company_id = ?,"
        + " from_address = ?, subject = ?, message = ?, last_time_sent = ?, next_time_send = ?,"
        + " status = ?, scheduleId = ?, creation_user = ?, creation_date = ?, modification_user = ?,"
        + "modification_date = ? where notification_id = ?";
    /**
     * <p>
     * Type indicator for the association table. Indicate the client type.
     * </p>
     */
    private static final int TYPE_NOTIFY_CLIENTS = 0;

    /**
     * <p>
     * Type indicator for the association table. Indicate the project type.
     * </p>
     */
    private static final int TYPE_NOTIFY_PROJECTS = 1;

    /**
     * <p>
     * Type indicator for the association table. Indicate the resource type.
     * </p>
     */
    private static final int TYPE_NOTIFY_RESOURCES = 2;

    /**
     * <p>
     * Search context for the search bundle.
     * </p>
     */
    private static final String SEARCH_CONTEXT = "SELECT DISTINCT notification.notification_id FROM notification"
        + " INNER JOIN Notify_resources ON notification.notification_id=Notify_resources.notification_id INNER JOIN"
        + " notify_projects ON notification.notification_id= notify_projects.notification_id INNER JOIN"
        + " notify_clients ON notification.notification_id=notify_clients.notification_id WHERE ";

    /**
     * <p>
     * Represents the database search strategy namespace.
     * </p>
     */
    private static final String SEARCH_STRATEGE_NAMESPACE = "com.topcoder.timetracker.notification"
        + ".persistence.searchstrategy";

    /**
     * <p>
     * The DBConnectionFactory is used to create the named connection.
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and immutable after set
     * </p>
     */
    private final DBConnectionFactory dbFactory;

    /**
     * <p>
     * The connection name used to create the named connection.
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and non-empty and immutable after set.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * The searchBundle is used to retrieve CustomResultSet against the passed in Filter. It can be created by
     * SearchBundleManager and then passed to this class.
     * </p>
     * <p>
     * This variable is set in the constructor, non-null and immutable after set.
     * </p>
     */
    private final SearchBundle searchBundle;

    /**
     * <p>
     * The IDGenerator used to generate the notification id.
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and immutable after set.
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * The auditManager is used to audit the modification to the notification table in the updateNotification method.
     * </p>
     *
     * <p>
     * It's set in the constructor, non-null and immutable after set.
     * </p>
     */
    private final AuditManager auditManager;

    /**
     * <p>
     * This is the filter factory that is used to create Search Filters for searching the data store for Time Tracker
     * Notifications using this implementation.
     * </p>
     *
     * <p>
     * It will be initialized in the constructor with the parameters depending on the search query used by the
     * developer.
     * </p>
     *
     * <p>
     * Initial Value: Initialized in Constructor - The values to provide to the map are dependent on the query used.
     * For more clarification, see the Algorithm Section of the CS.
     * </p>
     *
     * <p>
     * Accessed In: getNotificationFilterFactory
     * </p>
     *
     * <p>
     * Modified In: Not Modified.
     * </p>
     *
     * <p>
     * Utilized In: The filters it produces are used in searchNotifications
     * </p>
     *
     * <p>
     * Valid Values: Non-null NotificationFilterFactory object.
     * </p>
     */
    private final NotificationFilterFactory filterFactory;

    /**
     * Create the instance with given argument.
     *
     * @param dbFactory the db connection factory
     * @param connName the connection name
     * @param sb the search bundle
     * @param idGeneratorName the id generator name
     * @param am the audit manager
     *
     * @throws NotificationConfigurationException if any error occurred
     * @throws IllegalArgumentExceptin if argument is null or string argument is empty
     */
    public InformixNotificationPersistence(DBConnectionFactory dbFactory, String connName, SearchBundle sb,
        String idGeneratorName, AuditManager am) throws NotificationConfigurationException {
        Helper.checkNull(dbFactory, "dbFactory");
        Helper.checkString(connName, "connName");
        Helper.checkNull(sb, "sb");
        Helper.checkString(idGeneratorName, "idGeneratorName");
        Helper.checkNull(am, "am");

        this.filterFactory = new NotificationFilterFactory(buildFilterNames());
        this.dbFactory = dbFactory;
        this.connectionName = connName;
        this.searchBundle = sb;
        this.auditManager = am;

        try {
            this.idGenerator = IDGeneratorFactory.getIDGenerator(idGeneratorName);
        } catch (IDGenerationException e) {
            throw new NotificationConfigurationException("An error occurs while retrieving ID sequence configuration.",
                e);
        }
    }

    /**
     * Create the instance with given argument.
     *
     * @param dbFactory the db connection factory
     * @param connName the connection name
     * @param idGeneratorName the id generator name
     * @param am the audit manager
     *
     * @throws NotificationConfigurationException if any error occurred
     * @throws IllegalArgumentExceptin if argument is null or string argument is empty
     */
    public InformixNotificationPersistence(DBConnectionFactory dbFactory, String connName, String idGeneratorName,
        AuditManager am) throws NotificationConfigurationException {
        this(dbFactory, connName, createSearchBundle(), idGeneratorName, am);
    }

    /**
     * Get the notification by notification id.
     *
     * @param notificationId the notification id
     *
     * @return the notification with given id
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification getNotification(long notificationId)
        throws NotificationPersistenceException {
        Connection conn = null;

        try {
            // create a DB connection
            conn = createConnection();

            // get the notification through the connection and also retrieve the association
            return getNotificationInside(conn, notificationId, true);
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * Create the notification.
     *
     * @param notification the instance to create
     * @param audit the audit flag
     *
     * @throws NotificationPersistenceException if any error occurred
     * @throws IllegalArgumentExceptin if argument is null
     */
    public void createNotification(Notification notification, boolean audit)
        throws NotificationPersistenceException {
        // check for null argument
        Helper.checkNull(notification, "notification");

        // make sure the notification is valid
        checkNotification(notification);

        // get the notification id from the id generator
        try {
            notification.setId(idGenerator.getNextID());
        } catch (IDGenerationException idge) {
            throw new NotificationPersistenceException("Error generating the id.", idge);
        } catch (IllegalArgumentException iae) {
            throw new NotificationPersistenceException("Error get the id, the id generator has wrong configuration.",
                iae);
        }

        Connection conn = null;

        try {
            // get the DB connection
            conn = createConnection();

            // check if the notification exist
            checkNotificationExist(conn, notification.getId());

            // insert the notification to the database
            insertNotificationInside(conn, notification);

            // insert the association to the notify_clients, notify_projects, notify_resources
            insertAssociation(conn, notification, audit, notification.getCreationUser(), TYPE_NOTIFY_CLIENTS);
            insertAssociation(conn, notification, audit, notification.getCreationUser(), TYPE_NOTIFY_PROJECTS);
            insertAssociation(conn, notification, audit, notification.getCreationUser(), TYPE_NOTIFY_RESOURCES);

            if (audit) {
                // audit the notification
                auditNotification(null, notification);
            }
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * Update the notification If audit is true, the audit functionality is enabled.
     *
     * @param notification the bean to update
     * @param audit the audit flag.
     *
     * @throws NotificationPersistenceException if any error occurred
     * @throws IllegalArgumentExceptin if argument is null
     */
    public void updateNotification(Notification notification, boolean audit)
        throws NotificationPersistenceException {
        Helper.checkNull(notification, "notification");

        // check if the notification is valid
        checkNotification(notification);

        Notification old = null;

        Connection conn = null;

        try {
            // create the DB connection
            conn = createConnection();

            if (audit) {
                // if audit is enable, get the old notification
                old = getNotificationInside(conn, notification.getId(), false);
            }

            // delete from the association
            deleteAssociation(conn, notification, audit, TYPE_NOTIFY_CLIENTS);
            deleteAssociation(conn, notification, audit, TYPE_NOTIFY_PROJECTS);
            deleteAssociation(conn, notification, audit, TYPE_NOTIFY_RESOURCES);

            // update the notification
            updateNotificationInside(conn, notification);

            // insert the association to the database
            insertAssociation(conn, notification, audit, notification.getModificationUser(), TYPE_NOTIFY_CLIENTS);
            insertAssociation(conn, notification, audit, notification.getModificationUser(), TYPE_NOTIFY_PROJECTS);
            insertAssociation(conn, notification, audit, notification.getModificationUser(), TYPE_NOTIFY_RESOURCES);

            if (audit) {
                // audit the notification
                auditNotification(old, notification);
            }
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * Delete the notification by id. if the id does not exist, do nothing.
     *
     * @param notificationId the notification id
     * @param audit the audit flag
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public void deleteNotification(long notificationId, boolean audit)
        throws NotificationPersistenceException {
        Connection conn = null;
        Notification old = null;

        try {
            // create the DB connection
            conn = createConnection();

            // get the old notification
            old = getNotificationInside(conn, notificationId, false);

            if (old != null) {
                // if old is not null, delete the association
                deleteAssociation(conn, old, audit, TYPE_NOTIFY_CLIENTS);
                deleteAssociation(conn, old, audit, TYPE_NOTIFY_PROJECTS);
                deleteAssociation(conn, old, audit, TYPE_NOTIFY_RESOURCES);

                // delete the notification
                deleteNotificationInside(conn, old.getId());

                if (audit) {
                    // audit the change
                    auditNotification(old, null);
                }
            }
        } finally {
            releaseConnection(conn);
        }
    }

    /**
     * Get all the notifications in the database.
     *
     * @return all the notifications
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    public Notification[] getAllNotifications() throws NotificationPersistenceException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            // create the DB connection
            conn = createConnection();

            // prepare the statement
            stmt = conn.prepareStatement(SQL_SELECT_ALL_NOTIFICATION);

            // execute the query
            result = stmt.executeQuery();

            List list = new ArrayList();

            while (result.next()) {
                // get the notification entity
                Notification notification = retrieveNotification(result);

                // get the notification association
                notification.setToClients(getAssociation(conn, notification.getId(), TYPE_NOTIFY_CLIENTS));
                notification.setToProjects(getAssociation(conn, notification.getId(), TYPE_NOTIFY_PROJECTS));
                notification.setToResources(getAssociation(conn, notification.getId(), TYPE_NOTIFY_RESOURCES));

                list.add(notification);
            }

            // convert from List to Array
            return (Notification[]) list.toArray(new Notification[list.size()]);
        } catch (SQLException e) {
            throw new NotificationPersistenceException("Error retrieving notification.", e);
        } finally {
            releaseStatement(stmt, result);
            releaseConnection(conn);
        }
    }

    /**
     * Search the matching notifications against the given filter. If none found, return empty array Note that the
     * default filter field is the column name.
     *
     * @param filter the search filter
     *
     * @return all the matching notifications
     *
     * @throws NotificationPersistenceException if any error occurred
     * @throws IllegalArgumentExceptin if argument is null
     */
    public Notification[] searchNotifications(Filter filter)
        throws NotificationPersistenceException {
        Helper.checkNull(filter, "filter");

        try {
            CustomResultSet rs = (CustomResultSet) searchBundle.search(filter);

            List list = new ArrayList();

            while (rs.next()) {
                list.add(getNotification(rs.getLong(1)));
            }

            return (Notification[]) list.toArray(new Notification[list.size()]);
        } catch (SearchBuilderException sbe) {
            throw new NotificationPersistenceException("Error occurred when retrieve data by search bundle.", sbe);
        } catch (InvalidCursorStateException icse) {
            throw new NotificationPersistenceException("Error occurred while retrieving data from result set.", icse);
        }
    }

    /**
     * <p>
     * Retrieves the NotificationFilterFactory that is capable of creating SearchFilters to use when searching for
     * Notifications.  This is used to conveniently specify the search criteria that should be used.  The filters
     * returned by the given factory should be used with this instance's searchNotifications method.
     * </p>
     *
     * @return the NotificationFilterFactory that is capable of creating SearchFilters to use when searching for Time
     *         Tracker notifications.
     */
    public NotificationFilterFactory getNotificationFilterFactory() {
        return filterFactory;
    }

    /**
     * <p>
     * Get database connection from the db connection factory. It will set the connection to auto commit as required.
     * </p>
     *
     * @param autoCommit auto commit flag
     *
     * @return A database connection.
     *
     * @throws NotificationPersistenceException If can't get connection or fails to set the auto commit value.
     */
    private Connection createConnection()
        throws NotificationPersistenceException {
        try {
            // create a DB connection
            Connection conn = dbFactory.createConnection(connectionName);

            return conn;
        } catch (DBConnectionException dbce) {
            throw new NotificationPersistenceException("Can't get the connection from database.", dbce);
        }
    }

    /**
     * <p>
     * Release the connection. If not success, rollback the transaction.
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
     * Release the statement and result set.
     * </p>
     *
     * @param stmt the statement to release
     * @param result the result to release
     */
    private void releaseStatement(Statement stmt, ResultSet result) {
        try {
            if (result != null) {
                result.close();
            }

            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            // ignore it
        }
    }

    /**
     * Get association such as notify_clients, notify_projects, notify_resources from the database.
     *
     * @param conn the Connection used to get the data
     * @param notificationId the association's notification id
     * @param type the type of the association
     *
     * @return the array of the association
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    private long[] getAssociation(Connection conn, long notificationId, int type)
        throws NotificationPersistenceException {
        ResultSet result = null;
        PreparedStatement stmt = null;

        String SQLCommand = getSQLSelectCommandByType(type);

        try {
            // prepare the statement
            stmt = conn.prepareStatement(SQLCommand);

            stmt.setLong(1, notificationId);

            // do the query
            result = stmt.executeQuery();

            return retrieveArray(result);
        } catch (SQLException sqle) {
            throw new NotificationPersistenceException("Error when retrieve information from database "
                + getTableNameByType(type), sqle);
        } finally {
            releaseStatement(stmt, result);
        }
    }

    /**
     * <p>
     * A private helper method to retrieve all the long ids from a given ResultSet. SQLException will be thrown to
     * upper method to deal with.
     * </p>
     *
     * @param result the given result
     *
     * @return the retrieved array of long ids
     *
     * @throws SQLException if any error occurs with the sql executing.
     */
    private long[] retrieveArray(ResultSet result) throws SQLException {
        // Retrieve all the buddies from this result set.
        List list = new ArrayList();

        while (result.next()) {
            list.add(new Long(result.getLong(1)));
        }

        // Store it into an array
        long[] arr = new long[list.size()];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = ((Long) list.get(i)).longValue();
        }

        return arr;
    }

    /**
     * Check the notification, make sure the NON-NULL fields is set.
     *
     * @param notification the notification to be check
     *
     * @throws NotificationPersistenceException if notification contains null value for non-null fields.
     */
    private void checkNotification(Notification notification)
        throws NotificationPersistenceException {
        // from_address should not be null.
        if (notification.getFromAddress() == null) {
            throw new NotificationPersistenceException("Notification with null from address is illegal");
        }

        // subject should not be null.
        if (notification.getSubject() == null) {
            throw new NotificationPersistenceException("Notification with null subject is illegal.");
        }

        // message should not be null.
        if (notification.getMessage() == null) {
            throw new NotificationPersistenceException("Notification with null message is illegal.");
        }

        // if modification date should not be null.
        if (notification.getModificationDate() == null) {
            throw new NotificationPersistenceException("Notification with null modification date.");
        }

        if (notification.getModificationUser() == null) {
            throw new NotificationPersistenceException("Notification with null modification user.");
        }

        // if create user is null.
        if (notification.getCreationUser() == null) {
            throw new NotificationPersistenceException("Notification with null creation user.");
        }

        // if create date is null.
        if (notification.getCreationDate() == null) {
            throw new NotificationPersistenceException("Notification with null create date.");
        }
    }

    /**
     * Get the notification from the specified connection with notification id. If flag is set, get the association
     * too, if flag is not set, does not get the association.
     *
     * @param conn the Connection used to get the notification
     * @param notificationId the notification id
     * @param flag whether the association should be get
     *
     * @return return the notification
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    private Notification getNotificationInside(Connection conn, long notificationId, boolean flag)
        throws NotificationPersistenceException {
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            stmt = conn.prepareStatement(SQL_SELECT_NOTIFICATION);

            stmt.setLong(1, notificationId);

            result = stmt.executeQuery();

            while (result.next()) {
                Notification notification = retrieveNotification(result);

                if (flag) {
                    notification.setToClients(getAssociation(conn, notification.getId(), TYPE_NOTIFY_CLIENTS));
                    notification.setToProjects(getAssociation(conn, notification.getId(), TYPE_NOTIFY_PROJECTS));
                    notification.setToResources(getAssociation(conn, notification.getId(), TYPE_NOTIFY_RESOURCES));
                }

                return notification;
            }

            return null;
        } catch (SQLException sqle) {
            throw new NotificationPersistenceException("Error getting message.", sqle);
        } finally {
            releaseStatement(stmt, result);
        }
    }

    /**
     * Insert the notification to the database, this implementation will not insert the client, project and resource.
     *
     * @param conn the connection to the database
     * @param notification the notification to insert
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    private void insertNotificationInside(Connection conn, Notification notification)
        throws NotificationPersistenceException {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(SQL_INSERT_NOTIFICATION);

            stmt.setLong(1, notification.getId());
            stmt.setLong(2, notification.getCompanyId());
            stmt.setString(3, notification.getFromAddress());
            stmt.setString(4, notification.getSubject());
            stmt.setString(5, notification.getMessage());

            if (notification.getLastTimeSent() == null) {
                stmt.setDate(6, new java.sql.Date(notification.getLastTimeSent().getTime()));
            } else {
                stmt.setNull(6, java.sql.Types.DATE);
            }

            if (notification.getNextTimeToSend() == null) {
                stmt.setDate(7, new java.sql.Date(notification.getNextTimeToSend().getTime()));
            } else {
                stmt.setNull(7, java.sql.Types.DATE);
            }

            stmt.setInt(8, notification.isActive() ? 1 : 0);
            stmt.setLong(9, notification.getScheduleId());
            stmt.setString(10, notification.getCreationUser());
            stmt.setDate(11, new java.sql.Date(notification.getCreationDate().getTime()));
            stmt.setString(12, notification.getModificationUser());
            stmt.setDate(13, new java.sql.Date(notification.getModificationDate().getTime()));

            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new NotificationPersistenceException("Error inserting record to notification.", sqle);
        } finally {
            releaseStatement(stmt, null);
        }
    }

    /**
     * Get the SQL command by type.
     *
     * @param type indicate the type of the select, three types is allowed for notify clients, notify projects and
     *        notify resources.
     *
     * @return return the SQL select command
     */
    private String getSQLSelectCommandByType(int type) {
        if (type == TYPE_NOTIFY_CLIENTS) {
            return SQL_SELECT_CLIENTS;
        } else if (type == TYPE_NOTIFY_PROJECTS) {
            return SQL_SELECT_PROJECTS;
        } else if (type == TYPE_NOTIFY_RESOURCES) {
            return SQL_SELECT_RESOURCES;
        } else {
            // never reach here
            return null;
        }
    }

    /**
     * Get the SQL all command by type.
     *
     * @param type indicate the type of the select, three types is allowed for notify clients, notify projects and
     *        notify resources.
     *
     * @return return the SQL select all command
     */
    private String getSQLSelectAllCommandByType(int type) {
        if (type == TYPE_NOTIFY_CLIENTS) {
            return SQL_SELECT_ALL_CLIENTS;
        } else if (type == TYPE_NOTIFY_PROJECTS) {
            return SQL_SELECT_ALL_PROJECTS;
        } else if (type == TYPE_NOTIFY_RESOURCES) {
            return SQL_SELECT_ALL_RESOURCES;
        } else {
            // never reach here
            return null;
        }
    }

    /**
     * Get the SQL insert command by type.
     *
     * @param type indicate the type of the select, three types is allowed for notify clients, notify projects and
     *        notify resources.
     *
     * @return return the SQL insert command
     */
    private String getSQLInsertCommandByType(int type) {
        if (type == TYPE_NOTIFY_CLIENTS) {
            return SQL_INSERT_CLIENTS;
        } else if (type == TYPE_NOTIFY_PROJECTS) {
            return SQL_INSERT_PROJECTS;
        } else if (type == TYPE_NOTIFY_RESOURCES) {
            return SQL_INSERT_RESOURCES;
        } else {
            // never reach here
            return null;
        }
    }

    /**
     * Get the SQL delete command by type.
     *
     * @param type indicate the type of the select, three types is allowed for notify clients, notify projects and
     *        notify resources.
     *
     * @return return the SQL delete command
     */
    private String getSQLDeleteCommandByType(int type) {
        if (type == TYPE_NOTIFY_CLIENTS) {
            return SQL_DELETE_CLIENTS;
        } else if (type == TYPE_NOTIFY_PROJECTS) {
            return SQL_DELETE_PROJECTS;
        } else if (type == TYPE_NOTIFY_RESOURCES) {
            return SQL_DELETE_RESOURCES;
        } else {
            // never reach here
            return null;
        }
    }

    /**
     * Get the id list by type.
     *
     * @param notification DOCUMENT ME!
     * @param type indicate the type of the select, three types is allowed for notify clients, notify projects and
     *        notify resources.
     *
     * @return return the id list
     */
    private long[] getIdsByType(Notification notification, int type) {
        if (type == TYPE_NOTIFY_CLIENTS) {
            return notification.getToClients();
        } else if (type == TYPE_NOTIFY_PROJECTS) {
            return notification.getToProjects();
        } else if (type == TYPE_NOTIFY_RESOURCES) {
            return notification.getToResources();
        } else {
            // never reach here
            return null;
        }
    }

    /**
     * Get the column name by type.
     *
     * @param type indicate the type of the select, three types is allowed for notify clients, notify projects and
     *        notify resources.
     *
     * @return return the column name
     */
    private String getColumnNameByType(int type) {
        if (type == TYPE_NOTIFY_CLIENTS) {
            return "client_id";
        } else if (type == TYPE_NOTIFY_PROJECTS) {
            return "project_id";
        } else if (type == TYPE_NOTIFY_RESOURCES) {
            return "user_account_id";
        } else {
            // never reach here
            return null;
        }
    }

    /**
     * Get the table name
     *
     * @param type indicate the type of the select, three types is allowed for notify clients, notify projects and
     *        notify resources.
     *
     * @return return the table name
     */
    private String getTableNameByType(int type) {
        if (type == TYPE_NOTIFY_CLIENTS) {
            return "notify_clients";
        } else if (type == TYPE_NOTIFY_PROJECTS) {
            return "notify_projects";
        } else if (type == TYPE_NOTIFY_RESOURCES) {
            return "notify_resources";
        } else {
            // never reach here
            return null;
        }
    }

    /**
     * Insert to the association table.
     *
     * @param conn the Connection to the database
     * @param notification the notification
     * @param audit the audit flag
     * @param user the user for the audit
     * @param type the type of the association
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    private void insertAssociation(Connection conn, Notification notification, boolean audit, String user, int type)
        throws NotificationPersistenceException {
        PreparedStatement stmt = null;

        String sqlCommand = getSQLInsertCommandByType(type);
        long[] ids = getIdsByType(notification, type);
        String columnName = getColumnNameByType(type);
        String tableName = getTableNameByType(type);

        try {
            stmt = conn.prepareStatement(sqlCommand);

            long notificationId = notification.getId();

            Date current = new Date();

            for (int i = 0; i < ids.length; i++) {
                // set the fields of the statement.
                // for associations, the creation user and modification user is the same.
                stmt.setString(1, user);
                stmt.setDate(2, new java.sql.Date(current.getTime()));
                stmt.setString(3, user);
                stmt.setDate(4, new java.sql.Date(current.getTime()));
                stmt.setLong(5, notificationId);
                stmt.setLong(6, ids[i]);
                stmt.addBatch();

                if (audit) {
                    AuditDetail[] details = new AuditDetail[6];
                    details[0] = buildAuditDetail("creation_user", null, user);
                    details[1] = buildAuditDetail("creation_date", null, current.toString());
                    details[2] = buildAuditDetail("modification_user", null, user);
                    details[3] = buildAuditDetail("modification_date", null, current.toString());
                    details[4] = buildAuditDetail("notification_id", null, (new Long(notificationId)).toString());
                    details[5] = buildAuditDetail(columnName, null, (new Long(ids[i])).toString());

                    AuditHeader header = buildAuditHeader(notificationId, tableName, notification.getCompanyId(),
                            AuditType.INSERT, user);

                    auditManager.createAuditRecord(header);
                }
            }

            stmt.executeBatch();
        } catch (SQLException sqle) {
            throw new NotificationPersistenceException("Error inserting to" + tableName, sqle);
        } catch (AuditManagerException ame) {
            throw new NotificationPersistenceException("Error while creating audit.", ame);
        } finally {
            releaseStatement(stmt, null);
        }
    }

    /**
     * Delete from the association table.
     *
     * @param conn the Connection to the database
     * @param notification the notification
     * @param audit the audit flag
     * @param type the type of the association
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    private void deleteAssociation(Connection conn, Notification notification, boolean audit, int type)
        throws NotificationPersistenceException {
        PreparedStatement stmt = null;
        ResultSet result = null;
        String sqlCommand = getSQLDeleteCommandByType(type);
        String columnName = getColumnNameByType(type);
        String tableName = getTableNameByType(type);

        if (audit) {
            try {
                stmt = conn.prepareStatement(getSQLSelectAllCommandByType(type));

                stmt.setLong(1, notification.getId());

                result = stmt.executeQuery();

                while (result.next()) {
                    AuditDetail[] details = new AuditDetail[6];

                    details[0] = buildAuditDetail("creation_user", result.getString("creation_user"), null);
                    details[1] = buildAuditDetail("creation_date", result.getDate("creation_date").toString(), null);
                    details[2] = buildAuditDetail("modification_user", result.getString("modification_user"), null);
                    details[3] = buildAuditDetail("modification_date", result.getDate("modification_date").toString(),
                            null);
                    details[4] = buildAuditDetail("notification_id",
                            (new Long(result.getLong("notification_id"))).toString(), null);
                    details[5] = buildAuditDetail(columnName, (new Long(result.getLong(columnName))).toString(), null);

                    AuditHeader header = buildAuditHeader(notification.getId(), tableName, notification.getCompanyId(),
                            AuditType.DELETE, notification.getModificationUser());

                    auditManager.createAuditRecord(header);
                }
            } catch (SQLException sqle) {
                throw new NotificationPersistenceException("Error getting information from" + tableName, sqle);
            } catch (AuditManagerException ame) {
                throw new NotificationPersistenceException("Error while creating audit.", ame);
            } finally {
                releaseStatement(stmt, result);
            }
        }

        // do the delete
        try {
            stmt = conn.prepareStatement(sqlCommand);

            stmt.setLong(1, notification.getId());

            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new NotificationPersistenceException("Error inserting to" + tableName, sqle);
        } finally {
            releaseStatement(stmt, null);
        }
    }

    /**
     * Build the filter names map for the construction of the filter factory.
     *
     * @return filter names map
     */
    private Map buildFilterNames() {
        Map filterNames = new HashMap();

        filterNames.put("PROJECT_ID_NAME", "notification_projects.project_id");
        filterNames.put("COMPANY_ID_NAME", "notification.company_id");
        filterNames.put("CLIENT_ID_NAME", "notification_clients.client_id");
        filterNames.put("RESOURCE_ID_NAME", "notification_resources.notification_id");
        filterNames.put("ACTIVE_NAME", "notification.status");
        filterNames.put("LAST_SENT_NAME", "notification.last_time_sent");
        filterNames.put("NEXT_SEND_NAME", "notification.next_time_send");
        filterNames.put("FROM_LINE_NAME", "notification.from_line");
        filterNames.put("MESSAGE_NAME", "notification.message");
        filterNames.put("SUBJECT_NAME", "notification.subject");
        filterNames.put("CREATION_USER_NAME", "notification.creation_user");
        filterNames.put("MODIFICATION_USER_NAME", "notification.modification_user");
        filterNames.put("CREATION_DATE_NAME", "notification.creation_date");
        filterNames.put("MODIFICATION_DATE_NAME", "notification.modification_date");

        return filterNames;
    }

    /**
     * Retrieve notification from the result set, does not retrieve the association information
     *
     * @param result the result set used to retrieve the data
     *
     * @return the retrieved database
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    private Notification retrieveNotification(ResultSet result)
        throws NotificationPersistenceException {
        Notification notification = new Notification();

        try {
            notification.setId(result.getLong("notification_id"));
            notification.setCompnayId(result.getLong("company_id"));
            notification.setFromAddress(result.getString("from_address"));
            notification.setSubject(result.getString("subject"));
            notification.setMessage(result.getString("message"));

            Date lastTimeSent = result.getDate("last_time_sent");
            if (lastTimeSent != null) {
                notification.setLastTimeSent(lastTimeSent);
            }

            Date nextTimeSend = result.getDate("next_time_send");
            if (nextTimeSend != null) {
                notification.setNextTimeToSend(nextTimeSend);
            }
            notification.setActive((result.getInt("status") != 0) ? true : false);
            notification.setScheduleId(result.getLong("scheduleId"));
            notification.setCreationUser(result.getString("creation_user"));
            notification.setCreationDate(result.getDate("creation_date"));
            notification.setModificationUser(result.getString("modification_user"));
            notification.setModificationDate(result.getDate("creation_date"));

            return notification;
        } catch (SQLException sqle) {
            throw new NotificationPersistenceException("Error when get notification information.", sqle);
        }
    }

    /**
     * Delete the notification from the database.
     *
     * @param conn the Connection to the database
     * @param notificationId the notification id to delete
     *
     * @throws NotificationPersistenceException if any error occurred
     */
    private void deleteNotificationInside(Connection conn, long notificationId)
        throws NotificationPersistenceException {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(SQL_DELETE_NOTIFICATION);
            stmt.setLong(1, notificationId);
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new NotificationPersistenceException("Error occurred when deleting.", sqle);
        }
    }

    /**
     * Build the audit detail from the column name, old value and new value.
     *
     * @param columnName the column name for the audit detail
     * @param oldValue the old value of the column
     * @param newValue the new value for the column
     *
     * @return the AuditDetail
     */
    private AuditDetail buildAuditDetail(String columnName, String oldValue, String newValue) {
        AuditDetail detail = new AuditDetail();

        detail.setColumnName(columnName);
        detail.setOldValue(oldValue);
        detail.setNewValue(newValue);

        return detail;
    }

    /**
     * Build the auditHeder with the provided information
     *
     * @param entityId the entity id for the header
     * @param tableName the table name for the header
     * @param companyId the company id for the header
     * @param actionType the action type for the header
     * @param creationUser the creation user of the header
     *
     * @return return the audit header
     */
    private AuditHeader buildAuditHeader(long entityId, String tableName, long companyId, int actionType,
        String creationUser) {
        AuditHeader header = new AuditHeader();

        header.setEntityId(entityId);
        header.setTableName(tableName);
        header.setCompanyId(companyId);
        header.setActionType(actionType);
        header.setApplicationArea(ApplicationArea.TT_NOTIFICATION);
        header.setCreationUser(creationUser);

        return header;
    }

    /**
     * Check if the notification with the provided id exist in the database, if exist throw exception.
     *
     * @param conn the Connection to the database
     * @param notificationId the notification id
     *
     * @throws NotificationPersistenceException if the id exist or any error occurred
     */
    private void checkNotificationExist(Connection conn, long notificationId)
        throws NotificationPersistenceException {
        PreparedStatement stmt = null;
        ResultSet result = null;

        try {
            stmt = conn.prepareStatement(SQL_SELECT_NOTIFICATION);

            stmt.setLong(1, notificationId);

            result = stmt.executeQuery();

            while (result.next()) {
                throw new NotificationPersistenceException("Notification with the specified id already exist.");
            }
        } catch (SQLException sqle) {
            throw new NotificationPersistenceException("Error retrieve notification from database.", sqle);
        } finally {
            releaseStatement(stmt, result);
        }
    }

    /**
     * Audit the notification with the old value and new value
     *
     * @param oldNotification the old value of the notification
     * @param newNotification the new value of the notification
     * @throws NotificationPersistenceException if any error occurred when auditing
     */
    private void auditNotification(Notification oldNotification, Notification newNotification)
        throws NotificationPersistenceException {
        // create the audit details
        // the notification table has 13 columns.
        AuditDetail[] details = new AuditDetail[13];

        details[0] = buildAuditDetail("notification_id",
                (oldNotification == null) ? null : (new Long(oldNotification.getId())).toString(),
                (newNotification == null) ? null : (new Long(newNotification.getId())).toString());
        details[1] = buildAuditDetail("company_id",
                (oldNotification == null) ? null : (new Long(oldNotification.getCompanyId())).toString(),
                (newNotification == null) ? null : (new Long(newNotification.getCompanyId())).toString());
        details[2] = buildAuditDetail("fromAddress",
                (oldNotification == null) ? null : oldNotification.getFromAddress(),
                (newNotification == null) ? null : newNotification.getFromAddress());
        details[3] = buildAuditDetail("subject", (oldNotification == null) ? null : oldNotification.getSubject(),
                (newNotification == null) ? null : newNotification.getSubject());
        details[4] = buildAuditDetail("message", (oldNotification == null) ? null : oldNotification.getMessage(),
                (newNotification == null) ? null : newNotification.getMessage());
        details[5] = buildAuditDetail("last_time_sent",
                (oldNotification == null) ? null : (oldNotification.getLastTimeSent() == null) ?
                        null : oldNotification.getLastTimeSent().toString(),
                (newNotification == null) ? null : newNotification.getLastTimeSent() == null ?
                        null : newNotification.getLastTimeSent().toString());
        details[6] = buildAuditDetail("next_time_send",
                (oldNotification == null) ? null : (oldNotification.getNextTimeToSend() == null) ?
                        null : oldNotification.getNextTimeToSend().toString(),
                        (newNotification == null) ? null : newNotification.getNextTimeToSend() == null ?
                                null : newNotification.getNextTimeToSend().toString());
        details[7] = buildAuditDetail("status",
                (oldNotification == null) ? null : (oldNotification.isActive() ? "1" : "0"),
                (newNotification == null) ? null : (newNotification.isActive() ? "1" : "0"));
        details[8] = buildAuditDetail("schedule_id",
                (oldNotification == null) ? null : (new Long(oldNotification.getScheduleId())).toString(),
                (newNotification == null) ? null : (new Long(newNotification.getScheduleId())).toString());
        details[9] = buildAuditDetail("creation_user",
                (oldNotification == null) ? null : oldNotification.getCreationUser(),
                (newNotification == null) ? null : newNotification.getCreationUser());
        details[10] = buildAuditDetail("creation_date",
                (oldNotification == null) ? null : oldNotification.getCreationDate().toString(),
                (newNotification == null) ? null : newNotification.getCreationDate().toString());
        details[11] = buildAuditDetail("modification_user",
                (oldNotification == null) ? null : oldNotification.getModificationUser(),
                (newNotification == null) ? null : newNotification.getModificationUser());
        details[12] = buildAuditDetail("modification_date",
                (oldNotification == null) ? null : oldNotification.getModificationDate().toString(),
                (newNotification == null) ? null : newNotification.getModificationDate().toString());

        long entityId = -1;
        long companyId = -1;
        int actionType = 0;
        String creationUser = null;

        if (oldNotification != null && newNotification != null) {
            actionType = AuditType.UPDATE;
            creationUser = newNotification.getModificationUser();
            entityId = newNotification.getId();
            companyId = newNotification.getCompanyId();
        } else if(oldNotification == null) {
            actionType = AuditType.INSERT;
            creationUser = newNotification.getCreationUser();
            entityId = newNotification.getId();
            companyId = newNotification.getCompanyId();
        } else if(newNotification == null){
            actionType = AuditType.DELETE;
            creationUser = oldNotification.getModificationUser();
            entityId = oldNotification.getId();
            companyId = oldNotification.getCompanyId();
        }

        AuditHeader header = buildAuditHeader(entityId, "notification", companyId, actionType, creationUser);

        header.setDetails(details);
        try {
            auditManager.createAuditRecord(header);
        } catch (AuditManagerException ame) {
            throw new NotificationPersistenceException("Error auditing.", ame);
        }
    }

    /**
     * <p>
     * Create the search bundle used for search through filter.
     * </p>
     *
     * @return the SearchBundle
     * @throws NotificationConfigurationException if any error happened when create the search bundle
     */
    private static SearchBundle createSearchBundle() throws NotificationConfigurationException {
        DatabaseSearchStrategy strategy = null;

        try {
            strategy = new DatabaseSearchStrategy(SEARCH_STRATEGE_NAMESPACE);
        } catch (SearchBuilderConfigurationException e) {
            throw new NotificationConfigurationException("Error while build search strategy.", e);
        }

        Map fields = new HashMap();

        /**
         * <p>Always true validate.</p>
         * @author kzhu
         * @version 3.2
         */
        final class AlwaysTrueValidator implements ObjectValidator {
            /**
             * <p> Default Constructor.</p>
             *
             */
            public AlwaysTrueValidator() {
            }

            /**
             * <p> Always returns null, since this validate considers any object to be valid. </p>
             *
             * @return null always.
             * @param obj The object to validate (this parameter is ignored)
             */
            public String getMessage(Object obj) {
                return null;
            }

            /**
             * <p>Always returns true, since this validate considers any object to be valid.</p>
             *
             * @return True always.
             * @param obj The object to validate (this parameter is ignored)
             */
            public boolean valid(Object obj) {
                return true;
            }
        }

        fields.put("notification.notification_id", new AlwaysTrueValidator());
        fields.put("notification.company_id", new AlwaysTrueValidator());
        fields.put("notify_projects.project_id", new AlwaysTrueValidator());
        fields.put("notify_clients.client_id", new AlwaysTrueValidator());
        fields.put("notify_resources.notification_id", new AlwaysTrueValidator());
        fields.put("notification.status", new AlwaysTrueValidator());
        fields.put("notification.last_time_sent", new AlwaysTrueValidator());
        fields.put("notification.next_time_send", new AlwaysTrueValidator());
        fields.put("notification.from_address", new AlwaysTrueValidator());
        fields.put("notification.message", new AlwaysTrueValidator());
        fields.put("notification.subject", new AlwaysTrueValidator());
        fields.put("notification.scheduleId", new AlwaysTrueValidator());
        fields.put("notification.creation_user", new AlwaysTrueValidator());
        fields.put("notification.creation_date", new AlwaysTrueValidator());
        fields.put("notification.modification_user", new AlwaysTrueValidator());
        fields.put("notification.modification_date", new AlwaysTrueValidator());

        SearchBundle sb = new SearchBundle("name", fields, SEARCH_CONTEXT, strategy);

        return sb;
    }

    /**
     * Update the notification.
     *
     * @param conn the DB connection
     * @param notification the notification
     * @throws NotificationPersistenceException if any error occurred
     */
    private void updateNotificationInside(Connection conn, Notification notification)
        throws NotificationPersistenceException {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(SQL_UPDATE_NOTIFICATION);

            stmt.setLong(1, notification.getCompanyId());
            stmt.setString(2, notification.getFromAddress());
            stmt.setString(3, notification.getSubject());
            stmt.setString(4, notification.getMessage());

            if (notification.getLastTimeSent() != null) {
                stmt.setDate(5, new java.sql.Date(notification.getLastTimeSent().getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }
            if (notification.getNextTimeToSend() != null) {
                stmt.setDate(6, new java.sql.Date(notification.getNextTimeToSend().getTime()));
            } else {
                stmt.setNull(6, java.sql.Types.DATE);
            }

            stmt.setInt(7, notification.isActive() ? 1 : 0);
            stmt.setLong(8, notification.getScheduleId());
            stmt.setString(9, notification.getCreationUser());
            stmt.setDate(10, new java.sql.Date(notification.getCreationDate().getTime()));
            stmt.setString(11, notification.getModificationUser());
            stmt.setDate(12, new java.sql.Date(notification.getModificationDate().getTime()));
            stmt.setLong(13, notification.getId());

            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new NotificationPersistenceException("Error updating the record.", sqle);
        } finally {
            releaseStatement(stmt, null);
        }
    }
}
