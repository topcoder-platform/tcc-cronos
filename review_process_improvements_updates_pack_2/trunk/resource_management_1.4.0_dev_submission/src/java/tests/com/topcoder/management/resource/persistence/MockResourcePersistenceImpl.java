/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This is a mocked-up implementation of interface ResourcePersistence.
 *
 * This class is for unit tests only.
 *
 * @author kinfkong, waits
 * @version 1.3
 * @since 1.0
 */
public class MockResourcePersistenceImpl implements ResourcePersistence {
    /**
     * <p>
     * Represents the sql for loading resource roles.
     * </p>
     */
    private static final String SQL_SELECT_RES_ROLE = "SELECT resource_role_id, phase_type_id, name, description,"
            + " create_user, create_date, modify_user, modify_date FROM resource_role_lu WHERE resource_role_id = ?";

    /**
     * <p>
     * Represents the sql for loading notification type.
     * </p>
     */
    private static final String SQL_SELECT_NOTIFICATION_TYPE = "SELECT notification_type_id, name, description,"
            + " create_user, create_date, modify_user, modify_date"
            + " FROM notification_type_lu WHERE notification_type_id = ?";

    /**
     * <p>
     * Represents the sql to get the external properties.
     * </p>
     */
    private static final String SQL_SELECT_EXT_PROPS = "SELECT resource_info.resource_id, "
            + "resource_info_type_lu.name, resource_info.value "
            + "FROM resource_info INNER JOIN resource_info_type_lu ON "
            + "(resource_info.resource_info_type_id = resource_info_type_lu.resource_info_type_id) "
            + "WHERE resource_id IN (";

    /**
     * <p>
     * Represents the sql for selecting resource submission.
     * </p>
     */
    private static final String SQL_SELECT_SUBMISSION = "SELECT submission_id FROM resource_submission "
            + "WHERE resource_id = ?";

    /**
     * <p>
     * Adds the given Resource to the persistence store. The resource must not already exist (by id) in the persistence
     * store.
     * </p>
     *
     * @param resource The resource to add to the persistence store
     *
     */
    public void addResource(Resource resource) {
        Connection con = null;

        try {
            con = getConnection();

            String sql = "INSERT INTO resource"
                    + " (resource_id, resource_role_id, project_id, create_user, create_date, modify_user, modify_date)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?)";

            // System.out.println(resource.getId());
            doSQLUpdate(con, sql, new Object[] {new Long(resource.getId()),
                new Long(resource.getResourceRole().getId()), resource.getProject(), resource.getCreationUser(),
                resource.getCreationTimestamp(), resource.getModificationUser(),
                resource.getModificationTimestamp()});
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Deletes the given Resource (by id) in the persistence store. The Resource must already be present in the
     * persistence store, otherwise nothing is done.
     * </p>
     *
     * @param resource The resource to remove
     */
    public void deleteResource(Resource resource) {
        Connection con = null;

        try {
            con = getConnection();
            doSQLUpdate(con, "DELETE FROM resource_info WHERE resource_id = ?",
                    new Object[] {new Long(resource.getId())});
            doSQLUpdate(con, "DELETE FROM resource_submission WHERE resource_id =?", new Object[] {new Long(resource
                    .getId())});

            String sql = "DELETE FROM resource WHERE resource_id = ?";
            doSQLUpdate(con, sql, new Object[] {new Long(resource.getId())});
            closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Updates the given Resource in the persistence store with its currently set information. The Resource must already
     * be present in the persistence store. The operator information should already have been put in the modification
     * date / modification user properties of the Resource.
     * </p>
     *
     * @param resource The resource to update
     *
     */
    public void updateResource(Resource resource) {
        Connection con = null;

        try {
            con = getConnection();

            String sql = "UPDATE resource SET modify_user = ? WHERE resource_id = ?";
            doSQLUpdate(con, sql, new Object[] {resource.getModificationUser(), new Long(resource.getId())});
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Loads the resource from the persistence with the given id. Returns null if there is no resource for the given id.
     * </p>
     *
     * @param resourceId The id of the Resource to load
     *
     * @return The loaded Resource
     *
     */
    public Resource loadResource(long resourceId) {
        // mock implementation
        return new Resource(resourceId);
    }

    /**
     * <p>
     * Adds a notification to the persistence store. A notification type with the given ID must already exist in the
     * persistence store, as must a project.
     * </p>
     *
     * @param user The user id to add as a notification
     * @param project The project the notification is related to
     * @param notificationType The id of the notification type
     * @param operator The operator making the change
     *
     */
    public void addNotification(long user, long project, long notificationType, String operator) {
        Connection con = null;

        try {
            con = getConnection();

            String sql = "INSERT INTO notification (project_id, external_ref_id, "
                    + " notification_type_id, create_user, create_date, modify_user, modify_date)"
                    + " VALUES(?, ?, ?, ?, CURRENT, ?, CURRENT)";

            doSQLUpdate(con, sql, new Object[] {new Long(project), new Long(user), new Long(notificationType),
                operator, operator});
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Removes a notification from the persistence store. The given notification tuple identifier (user, project, and
     * notificationType) should already exists in the persistence store, otherwise nothing will be done.
     * </p>
     *
     * @param user The user id of the notification to remove
     * @param project The project id of the notification to remove
     * @param notificationType The notification type id of the notification to remove
     * @param operator The operator making the change
     *
     */
    public void removeNotification(long user, long project, long notificationType, String operator) {
        Connection con = null;

        try {
            con = getConnection();

            String sql = "DELETE FROM notification WHERE project_id = ? AND"
                    + " notification_type_id = ? AND external_ref_id = ?";
            doSQLUpdate(con, sql, new Object[] {new Long(project), new Long(notificationType), new Long(user),});
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Load the Notification for the given "id" triple from the persistence store. Returns null if no entry in the
     * persistence has the given user, project, and notificationType.
     * </p>
     *
     * @return The loaded notification
     * @param user The id of the user
     * @param project The id of the project
     * @param notificationType The id of the notificationType
     *
     */
    public Notification loadNotification(long user, long project, long notificationType) {
        Notification notification = new Notification(project, new NotificationType(notificationType), user);

        return notification;
    }

    /**
     * <p>
     * Adds a notification type to the persistence store. The id of the notification type must already be assigned to
     * the NotificationType object passed to this method, and not already exist in the persistence source.
     * </p>
     *
     * @param notificationType The notification type to add
     *
     */
    public void addNotificationType(NotificationType notificationType) {
        Connection con = null;

        try {
            con = getConnection();

            String sql = "INSERT INTO notification_type_lu"
                    + " (notification_type_id, name, description, create_user, create_date, modify_user, modify_date)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?)";
            doSQLUpdate(con, sql, new Object[] {new Long(notificationType.getId()), notificationType.getName(),
                    notificationType.getDescription(), notificationType.getCreationUser(),
                    notificationType.getCreationTimestamp(), notificationType.getModificationUser(),
                    notificationType.getModificationTimestamp()});
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Removes a notification type from the persistence (by id) store. If no notification type exists with the id of the
     * notification type, nothing is done.
     * </p>
     *
     * @param notificationType The notification type to delete
     *
     */
    public void deleteNotificationType(NotificationType notificationType) {
        Connection con = null;

        try {
            con = getConnection();

            String sql = "DELETE FROM notification_type_lu WHERE notification_type_id = ?";
            doSQLUpdate(con, sql, new Object[] {new Long(notificationType.getId())});
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * updateNotificationType: Updates the notification type in the persistence store. The notification type (by id)
     * must exist in the persistence store.
     * </p>
     *
     * @param notificationType The notification type to update
     *
     * @throws UnsupportedOperationException always
     */
    public void updateNotificationType(NotificationType notificationType) {
        Connection con = null;

        try {
            con = getConnection();

            String sql = "UPDATE notification_type_lu SET modify_user = ? WHERE notification_type_id = ?";
            doSQLUpdate(con, sql, new Object[] {notificationType.getModificationUser(),
                new Long(notificationType.getId())});
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Loads the notification type from the persistence with the given id. Returns null if there is no notification type
     * with the given id.
     * </p>
     *
     * @param notificationTypeId The id of the notification type to load
     *
     * @return The loaded notification type
     *
     * @throws UnsupportedOperationException always
     */
    public NotificationType loadNotificationType(long notificationTypeId) {
        throw new UnsupportedOperationException("This method is not supported yet.");
    }

    /**
     * <p>
     * Adds a resource role to the persistence store. The id of the resource role must already be assigned to the
     * notificationType object passed to this method, and not already exist in the persistence source.
     * </p>
     *
     * @param resourceRole The resource role to add
     *
     * @throws UnsupportedOperationException always
     */
    public void addResourceRole(ResourceRole resourceRole) {
        Connection con = null;

        try {
            con = getConnection();

            String sql = "INSERT INTO resource_role_lu" + " (resource_role_id, phase_type_id, name, description,"
                    + " create_user, create_date, modify_user, modify_date)" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            doSQLUpdate(con, sql, new Object[] {new Long(resourceRole.getId()), resourceRole.getPhaseType(),
                    resourceRole.getName(), resourceRole.getDescription(), resourceRole.getCreationUser(),
                    resourceRole.getCreationTimestamp(), resourceRole.getModificationUser(),
                    resourceRole.getModificationTimestamp()});
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Removes a resource role from the persistence store. If no resource role exists with the given id, nothing is
     * done.
     * </p>
     *
     * @param resourceRole The notification type to delete.
     *
     */
    public void deleteResourceRole(ResourceRole resourceRole) {
        Connection con = null;

        try {
            con = getConnection();

            String sql = "DELETE FROM resource_role_lu WHERE resource_role_id = ?";
            doSQLUpdate(con, sql, new Object[] {new Long(resourceRole.getId())});
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Updates the resource role in the persistence store. The resource role (by id) must exist in the persistence
     * store.
     * </p>
     *
     * @param resourceRole The resource role to update
     *
     */
    public void updateResourceRole(ResourceRole resourceRole) {
        Connection con = null;

        try {
            con = getConnection();

            String sql = "UPDATE resource_role_lu SET modify_user = ? WHERE resource_role_id = ?";
            doSQLUpdate(con, sql, new Object[] {resourceRole.getModificationUser(), new Long(resourceRole.getId())});
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Loads the resource role from the persistence with the given id. Returns null if there is no resource role with
     * the given id.
     * </p>
     *
     * @param resourceRoleId The id of the resource role to load
     * @return The loaded resource role
     * @throws ResourcePersistenceException If there is an error loading the resource role
     */
    public ResourceRole loadResourceRole(long resourceRoleId) throws ResourcePersistenceException {
        Connection connection = getConnection();

        try {
            return loadResourceRole(resourceRoleId, connection);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Loads the resource role from the persistence with the given id. Returns <code>null</code> if there is no resource
     * role with the given id.
     * </p>
     *
     * @return The loaded resource role
     * @param resourceRoleId The id of the resource role to load
     *
     * @throws ResourcePersistenceException If there is an error loading the resource role
     */
    private ResourceRole loadResourceRole(long resourceRoleId, Connection connection)
            throws ResourcePersistenceException {
        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SQL_SELECT_RES_ROLE);
            statement.setLong(1, resourceRoleId);

            rs = statement.executeQuery();

            if (rs.next()) {
                return constructResourceRole(rs);
            }
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new ResourcePersistenceException("Failed to load ResourceRole instance.", e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
        }

        return null;
    }

    /**
     * Constructs the <code>ResourceRole</code> instance from the given <code>ResultSet</code> instance.
     *
     * @param rs the ResultSet instance
     * @return the <code>ResourceRole</code> instance
     * @throws SQLException if failed to get the <code>ResourceRole</code> instance from the <code>ResultSet</code>.
     */
    private ResourceRole constructResourceRole(ResultSet rs) throws SQLException {
        ResourceRole role = new ResourceRole();
        int index = 3;
        role.setId(rs.getLong(1));

        if (rs.getObject(2) == null) {
            role.setPhaseType(null);
        } else {
            role.setPhaseType(new Long(rs.getLong(2)));
        }

        role.setName(rs.getString(index++));
        role.setDescription(rs.getString(index++));
        role.setCreationUser(rs.getString(index++));
        role.setCreationTimestamp(rs.getTimestamp(index++));
        role.setModificationUser(rs.getString(index++));
        role.setModificationTimestamp(rs.getTimestamp(index++));

        return role;
    }

    /**
     * <p>
     * Loads the resources from the persistence with the given ids. May return a zero-length array. This method is
     * designed to keep the amount of SQL queries to a minimum when searching resources.
     * </p>
     *
     * @param resourceIds The ids of resources to load
     * @return Theloaded resources
     *
     */
    public Resource[] loadResources(long[] resourceIds) {
        Resource[] resources = new Resource[resourceIds.length];

        for (int i = 0; i < resources.length; i++) {
            resources[i] = new Resource(resourceIds[i]);
        }

        return resources;
    }

    /**
     * <p>
     * loadNotificationTypes: Loads the notification types from the persistence with the given ids. May return a
     * 0-length array.
     * </p>
     *
     * @param notificationTypeIds The ids of notification types to load
     * @return The loaded notification types
     * @throws UnsupportedOperationException always
     */
    public NotificationType[] loadNotificationTypes(long[] notificationTypeIds) {
        NotificationType[] notificationTypes = new NotificationType[notificationTypeIds.length];

        for (int i = 0; i < notificationTypeIds.length; i++) {
            notificationTypes[i] = new NotificationType(notificationTypeIds[i]);
        }

        return notificationTypes;
    }

    /**
     * <p>
     * loadResourceRoles: Loads the resource roles from the persistence with the given ids. May return a 0-length array.
     * </p>
     *
     * @param resourceRoleIds The ids of resource roles to load
     *
     * @return The loaded resource roles
     *
     */
    public ResourceRole[] loadResourceRoles(long[] resourceRoleIds) {
        ResourceRole[] resourceRoles = new ResourceRole[resourceRoleIds.length];

        for (int i = 0; i < resourceRoles.length; i++) {
            resourceRoles[i] = new ResourceRole(resourceRoleIds[i]);
        }

        return resourceRoles;
    }

    /**
     * <p>
     * Load the Notifications for the given "id" triples from the persistence store. May return a 0-length array
     * </p>
     *
     * @param userIds The ids of the users
     * @param projectId The ids of the projects
     * @param notificationTypes The ids of the notification types
     *
     * @return The loaded notifications
     *
     */
    public Notification[] loadNotifications(long[] userIds, long[] projectId, long[] notificationTypes) {
        int n = userIds.length;
        Notification[] notifications = new Notification[n];

        for (int i = 0; i < n; i++) {
            notifications[i] = loadNotification(userIds[i], projectId[i], notificationTypes[i]);
        }

        return notifications;
    }

    /**
     * Does the update operation to the database.
     *
     * @param con the connection
     * @param sql the sql statement to execute
     * @param args the arguments to be set into the database
     */
    private void doSQLUpdate(Connection con, String sql, Object[] args) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                setElement(ps, i + 1, args[i]);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            // ignore
            // e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
    }

    /**
     * Sets the element to the prepared statement with given index.
     *
     * @param ps the prepared statement
     * @param index the index to set
     * @param obj the value of the argument
     *
     * @throws SQLException if any error occurs
     */
    private void setElement(PreparedStatement ps, int index, Object obj) throws SQLException {
        if (obj instanceof String) {
            ps.setString(index, (String) obj);
        } else if (obj instanceof Integer) {
            ps.setInt(index, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            ps.setLong(index, ((Long) obj).longValue());
        } else if (obj instanceof Timestamp) {
            ps.setTimestamp(index, (Timestamp) obj);
        } else if (obj instanceof Date) {
            ps.setDate(index, new java.sql.Date(((Date) obj).getTime()));
        } else if (obj == null) {
            ps.setNull(index, java.sql.Types.INTEGER);
        } else {
            throw new IllegalArgumentException("The element type is not supported yet.");
        }
    }

    /**
     * Closes a prepared statement silently.
     *
     * @param ps the prepared statement to close
     */
    private void closeStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes a connection silently.
     *
     * @param con the connection to close
     */
    private void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Returns a connection.
     *
     * @return the connection
     *
     */
    private Connection getConnection() {
        Connection con = null;

        try {
            String dbNamespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
            DBConnectionFactory factory = new DBConnectionFactoryImpl(dbNamespace);
            con = factory.createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    /**
     * Constructs the <code>NotificationType</code> instance from the given <code>CustomResultSet</code> instance.
     *
     * @param rs the <code>CustomResultSet</code> instance
     * @return NotifcationType instance.
     * @throws InvalidCursorStateException if failed to load the notificationType instance from the database.
     */
    private NotificationType constructNotificationType(CustomResultSet rs) throws InvalidCursorStateException {
        NotificationType type = new NotificationType();
        type.setId(rs.getLong("notification_type_id"));
        type.setName(rs.getString("name"));
        type.setDescription(rs.getString("description"));
        type.setCreationUser(rs.getString("create_user"));
        type.setCreationTimestamp(rs.getTimestamp("create_date"));
        type.setModificationUser(rs.getString("modify_user"));
        type.setModificationTimestamp(rs.getTimestamp("modify_date"));

        return type;
    }

    /**
     * Loads the notification types from the result of the SELECT operation. This method may return a zero-length array.
     * It is designed to keep the amount of SQL queries to a minimum when searching notification types.
     *
     * @param resultSet The result of the SELECT operation. This result should have the following columns:
     *            <ul>
     *            <li>notification_type_id</li>
     *            <li>name</li>
     *            <li>description</li>
     *            <li>as create_user</li>
     *            <li>as create_date</li>
     *            <li>as modify_user</li>
     *            <li>as modify_date</li>
     *            </ul>
     * @return The loaded notification types
     * @throws IllegalArgumentException If any id is &lt;= 0 or the array is null
     * @throws ResourcePersistenceException If there is an error loading from persistence
     */
    public NotificationType[] loadNotificationTypes(CustomResultSet resultSet) throws ResourcePersistenceException {
        if (resultSet.getRecordCount() == 0) {
            return new NotificationType[0];
        }

        try {
            List list = new ArrayList();

            while (resultSet.next()) {
                list.add(constructNotificationType(resultSet));
            }

            return (NotificationType[]) list.toArray(new NotificationType[list.size()]);
        } catch (InvalidCursorStateException icse) {
            throw new ResourcePersistenceException("Failed to load NotificationType instances.", icse);
        }
    }

    /**
     * Loads the <code>NotificationType</code> instance with notificationTypeId in the database.
     *
     * @param connection the connection to database.
     * @param notificationTypeId the notificationTypeId to load
     * @return NotificationType instance if exists, <code>null</code> otherwise.
     * @throws SQLException if failed to load the <code>NotificationType</code> instance.
     */
    private NotificationType loadNotificationType(Connection connection, long notificationTypeId) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        int index = 1;

        try {
            statement = connection.prepareStatement(SQL_SELECT_NOTIFICATION_TYPE);
            statement.setInt(1, (int) notificationTypeId);

            rs = statement.executeQuery();

            if (rs.next()) {
                NotificationType type = new NotificationType();
                type.setId(rs.getInt(index++));
                type.setName(rs.getString(index++));
                type.setDescription(rs.getString(index++));
                type.setCreationUser(rs.getString(index++));
                type.setCreationTimestamp(rs.getTimestamp(index++));
                type.setModificationUser(rs.getString(index++));
                type.setModificationTimestamp(rs.getTimestamp(index));

                return type;
            }
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
        }

        return null;
    }

    /**
     *
     * Construct the Notification instance from the ResultSet instance.
     *
     * @param connection the connection to the db.
     * @param rs the ResultSet instance
     * @return the constructed Notification instance.
     * @throws ResourcePersistenceException if failed to get the Notification instance from database.
     */
    private Notification consructNotification(Connection connection, CustomResultSet rs)
            throws ResourcePersistenceException {
        try {
            NotificationType type = loadNotificationType(connection, rs.getLong("notification_type_id"));
            Notification notification = new Notification(rs.getLong("project_id"), type, rs.getLong("external_ref_id"));

            notification.setCreationUser(rs.getString("create_user"));
            notification.setCreationTimestamp(rs.getTimestamp("create_date"));
            notification.setModificationUser(rs.getString("modify_user"));
            notification.setModificationTimestamp(rs.getTimestamp("modify_date"));

            return notification;
        } catch (SQLException e) {
            throw new ResourcePersistenceException("Failed to construct Notification instance.", e);
        } catch (InvalidCursorStateException icse) {
            throw new ResourcePersistenceException("Failed to construct Notification instance.", icse);
        }
    }

    /**
     * Load Notifications from the result of the SELECT operation. May return an empty array.
     *
     * @return The loaded notifications
     * @param resultSet The result of the SELECT operation.
     * @throws IllegalArgumentException If the three arrays don't all have the same number of elements (or any array is
     *             null) or all three arrays do not have the same length, any id is <= 0
     * @throws ResourcePersistenceException If there is an error loading from the persistence
     */
    public Notification[] loadNotifications(CustomResultSet resultSet) throws ResourcePersistenceException {
        if (resultSet.getRecordCount() == 0) {
            return new Notification[0];
        }

        Connection connection = getConnection();

        try {
            List notifications = new ArrayList();

            while (resultSet.next()) {
                notifications.add(consructNotification(connection, resultSet));
            }

            return (Notification[]) notifications.toArray(new Notification[notifications.size()]);
        } catch (ResourcePersistenceException rpe) {
            throw rpe;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Construct the ResourceRole instance from the given ResultSet instance.
     *
     * @param rs the ResultSet instance
     * @return the ResourceRole instance
     * @throws InvalidCursorStateException if any error occurs
     */
    private ResourceRole constructResourceRole(CustomResultSet rs) throws InvalidCursorStateException {
        ResourceRole role = new ResourceRole();

        role.setId(rs.getLong("resource_role_id"));

        if (rs.getObject("phase_type_id") == null) {
            role.setPhaseType(null);
        } else {
            role.setPhaseType(new Long(rs.getLong("phase_type_id")));
        }

        role.setName(rs.getString("name"));
        role.setDescription(rs.getString("description"));
        role.setCreationUser(rs.getString("create_user"));
        role.setCreationTimestamp(rs.getTimestamp("create_date"));
        role.setModificationUser(rs.getString("modify_user"));
        role.setModificationTimestamp(rs.getTimestamp("modify_date"));

        return role;
    }

    /**
     * Loads the resource roles from the result of the SELECT operation. May return an empty array.
     *
     * @return The loaded resource roles
     * @param resultSet The result of the SELECT operation.
     * @throws IllegalArgumentException If any id is <= 0 or the array is null
     * @throws ResourcePersistenceException If there is an error loading from persistence
     */
    public ResourceRole[] loadResourceRoles(CustomResultSet resultSet) throws ResourcePersistenceException {
        if (resultSet.getRecordCount() == 0) {
            return new ResourceRole[0];
        }

        try {
            List roles = new ArrayList();

            while (resultSet.next()) {
                roles.add(constructResourceRole(resultSet));
            }

            return (ResourceRole[]) roles.toArray(new ResourceRole[roles.size()]);
        } catch (InvalidCursorStateException icse) {
            throw new ResourcePersistenceException("Failed to load nResourceRole instance.", icse);
        }
    }
    /**
     * Loads the resources from the result of the SELECT operation. This method may return a
     * zero-length array. It is designed to keep the amount of SQL queries to a minimum when
     * searching resources.
     *
     * @param resultSet
     *            The result of the SELECT operation. This result should have the following columns:
     *            <ul>
     *            <li>resource.resource_id (as resource_id)</li>
     *            <li>resource_role_id</li>
     *            <li>project_id</li>
     *            <li>project_phase_id</li>
     *            <li>submission_id</li>
     *            <li>resource.create_user (as create_user)</li>
     *            <li>resource.create_date (as create_date)</li>
     *            <li>resource.modify_user (as modify_user)</li>
     *            <li>resource.modify_date (as modify_date)</li>
     *            </ul>
     * @return The loaded resources
     * @throws IllegalArgumentException
     *             If any id is &lt;= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading the Resources
     */
    public Resource[] loadResources(CustomResultSet resultSet) throws ResourcePersistenceException {
        if (resultSet.getRecordCount() == 0) {
            return new Resource[0];
        }

        Connection connection = getConnection();

        try {
            List list = new ArrayList();

            while (resultSet.next()) {
                list.add(constructResource(resultSet, connection));
            }

            Resource[] resources = (Resource[]) list.toArray(new Resource[list.size()]);
            long[] resourceIds = new long[resources.length];

            for (int i = 0; i < resources.length; ++i) {
                resourceIds[i] = resources[i].getId();
            }

            // select all the external properties once and add the matching properties into resource instances
            Map map = getAllExternalProperties(connection, resourceIds);

            for (int i = 0; i < resources.length; ++i) {
                Resource resource = resources[i];
                Map properties = (Map) map.get(new Long(resource.getId()));

                if (properties == null) {
                    continue;
                }

                for (Iterator iter = properties.entrySet().iterator(); iter.hasNext();) {
                    Map.Entry entry = (Map.Entry) iter.next();

                    resource.setProperty(entry.getKey().toString(), entry.getValue().toString());
                }
            }

            return resources;
        } catch (ResourcePersistenceException rpe) {
            throw rpe;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Gets all the external properties with one select sql query. Here a <code>HashMap</code> structure is used. The
     * key is an <code>Integer</code> of resourceId and the value is another map which contains the key/value of
     * external properties.
     *
     * @param connection the connection to database
     * @param resourceIds the resourceIds for retrieving external properties
     * @return a <code>Map</code> contained all external properties.
     * @throws ResourcePersistenceException if failed to select all external properties at once.
     */
    private Map getAllExternalProperties(Connection connection, long[] resourceIds)
        throws ResourcePersistenceException {
        // Map from resource id to a Map containing the properties of the resource.
        Map resourcesProperties = new HashMap();

        PreparedStatement statement = null;
        ResultSet rs = null;

        int index;

        try {
            statement = connection.prepareStatement(buildQueryWithIds(SQL_SELECT_EXT_PROPS, resourceIds));

            rs = statement.executeQuery();

            while (rs.next()) {
                index = 1;

                Long resourceId = new Long(rs.getLong(index++));

                String key = rs.getString(index++);
                String value = rs.getString(index);

                Map properties = (Map) resourcesProperties.get(resourceId);

                if (properties == null) {
                    properties = new HashMap();
                    resourcesProperties.put(resourceId, properties);
                }

                properties.put(key, value);
            }

            return resourcesProperties;
        } catch (SQLException e) {
            throw new ResourcePersistenceException("ERROR.", e);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
        }
    }

    /**
     * Builds a select sql query with an argument contains many long values. The structure of the result string looks
     * like this: ... in ( id, id, id, id...).
     *
     * @param baseQuery the sql query
     * @param ids the ids for select sql query
     * @return the result string
     */
    private String buildQueryWithIds(String baseQuery, long[] ids) {
        StringBuffer buffer = new StringBuffer(baseQuery);

        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                buffer.append(',');
            }

            buffer.append(ids[i]);
        }

        buffer.append(')');

        return buffer.toString();
    }

    /**
     * Construct a Resource instance from given CustomResultSet instance.
     *
     * @param rs the CustomResultSet instance
     * @return The Resource instance
     * @throws ResourcePersistenceException if failed to construct the Resource instance.
     */
    private Resource constructResource(CustomResultSet rs, Connection connection) throws ResourcePersistenceException {
        try {
            Resource resource = new Resource();

            resource.setId(rs.getLong("resource_id"));

            resource.setSubmissions(getSubmissionEntry(connection, resource));

            return resource;
        } catch (Exception e) {
            throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", e);
        }
    }

    /**
     * Gets the submission entry array for <code>Resource</code> instance.
     *
     * @param connection the connection to database.
     * @param resource the Resource instance
     * @return The Long array which contains the submission(s)
     * @throws SQLException if failed to get the submission entry for <code>Resource</code> instance.
     */
    private Long[] getSubmissionEntry(Connection connection, Resource resource) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        List submissions = new ArrayList();

        try {
            statement = connection.prepareStatement(SQL_SELECT_SUBMISSION);
            statement.setLong(1, resource.getId());

            rs = statement.executeQuery();

            while (rs.next()) {
                submissions.add(new Long(rs.getLong(1)));
            }

            return (Long[]) submissions.toArray(new Long[submissions.size()]);
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
        }
    }

    /**
     * <p>
     * Closes the given ResultSet.
     * </p>
     *
     * @param rs the given ResultSet instance to close.
     */
    private static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // ignore.
        }
    }
}
