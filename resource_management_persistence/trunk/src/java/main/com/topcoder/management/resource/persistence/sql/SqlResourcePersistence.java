/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistence;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * The SqlResourcePersistence class implements the ResourcePersistence interface, in order to persist to the database
 * structure given in the resource_management.sql script. This class does not cache a Connection to the database.
 * Instead a new Connection is used on every method call. Most methods in this class will just create and execute a
 * single PreparedStatement. However, some of the Resource related methods will need to execute several
 * PreparedStatements in order to accomplish the update/insertion/deletion of the Resource and its extended properties.
 * </p>
 *
 * <p>
 * As per the interface contract, auditing information is not passed explicitly as in the ResourceManager methods.
 * ResourceManager implementations are responsible for taking the parameters that are explicitly passed to them and
 * setting the corresponding auditing properties on the objects before they are passed to this interface.
 * </p>
 *
 * <p>
 * This class is immutable and thread-safe in the sense that multiple threads can not corrupt its internal data
 * structures. However, the results if used from multiple threads can be unpredictable as the database is changed from
 * different threads.
 * </p>
 *
 * <p>
 * This can equally well occur when the component is used on multiple machines or multiple instances are used, so this
 * is not a thread-safety issue but an application usage pattern issue.
 * </p>
 *
 * @author aubergineanode, Chenhong
 * @version 1.0
 */
public class SqlResourcePersistence implements ResourcePersistence {
    /**
     * <p>
     * The name of the connection producer to use when a connection to the database is retrieved from the
     * DBConnectionFactory.
     * </p>
     *
     * <p>
     * This field is immutable and can be null or non-null. When non-null, no restrictions are applied to the field.
     * When this field is null, the createConnection() method is used to get a connection.
     * </p>
     *
     * <p>
     * When it is non-null, the createConnection(String) method is used to get a connection.
     * </p>
     *
     * <p>
     * This field is not exposed by this class, and is used whenever a connection to the database is needed (i.e. in
     * every method).
     * </p>
     */
    private final String connectionName;

    /**
     * The connection factory to use when a connection to the database is needed.
     *
     * <p>
     * This field is immutable and must be non-null.
     * </p>
     *
     * <p>
     * This field is not exposed by this class and is used whenever a connection to the database is needed (i.e. in
     * every method).
     * </p>
     */
    private final DBConnectionFactory factory;

    /**
     * Creates a new SqlResourcePersistence with DBConnectionFactory instance.
     *
     * @param connectionFactory
     *            The connection factory to use for getting connections to the database.
     * @throws IllegalArgumentException
     *             If connectionFactory is null.
     */
    public SqlResourcePersistence(DBConnectionFactory connectionFactory) {
        this(connectionFactory, null);
    }

    /**
     * Creates a new SqlResourcePersistence with DBConnectionFactory instance and connctionName.
     *
     * @param connectionFactory
     *            The connection factory to be used for getting connections to the database.
     * @param connectionName
     *            The name of the connection to use. Can be null.
     * @throws IllegalArgumentException
     *             If connectionFactory is null.
     */
    public SqlResourcePersistence(DBConnectionFactory connectionFactory,
            String connectionName) {
        Util.checkNull(connectionFactory, "connectionFactory");

        factory = connectionFactory;
        this.connectionName = connectionName;
    }

    /**
     * <p>
     * Adds the given Resource to the persistence store.
     * </p>
     *
     * <p>
     * The resource must not already exist (by id) in the persistence store.
     * </p>
     *
     * <p>
     * Any SqlException or DBConnectionException should be wrapped in a ResourcePersistenceException.
     * </p>
     *
     * @param resource
     *            The resource to add to the persistence store
     * @throws IllegalArgumentException
     *             If resource is null or its id is UNSET_ID or its ResourceRole is null or its creation/modification
     *             user/date is null
     * @throws ResourcePersistenceException
     *             If there is a failure to persist the change
     *             <p>
     *             or a Resource with the id is already in the persistence.
     *             </p>
     */
    public void addResource(Resource resource)
            throws ResourcePersistenceException {
        // argument checking.
        Util.checkResource(resource, false);

        Connection conn = createConnection();
        try {
            conn.setAutoCommit(false);
            insertResource(conn, resource);

            // if the submission is not null, persist the submission.
            if (resource.getSubmission() != null) {
                insertSubmission(conn, resource);
            }

            // persist properties.
            Map map = resource.getAllProperties();

            for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Entry) iter.next();

                Integer resourceInfoTypeId = getResourceInfoTypeId(conn, entry
                        .getKey().toString());

                // if resource_info_type_id is found
                if (resourceInfoTypeId != null) {
                    insertResourceInfo(conn, resource, resourceInfoTypeId
                            .intValue(), entry.getValue().toString());
                }
            }
            conn.commit();
        } catch (SQLException ex) {
            Util.rollback(conn);
            throw new ResourcePersistenceException(
                    "Unable to insert resource.", ex);
        } finally {
            Util.setAutocommit(conn, true);
            Util.closeConnection(conn);
        }
    }

    /**
     * insert the Resource instance into the database.
     *
     * @param connection
     *            the connection to database
     * @param resource
     *            the Resource instance to be persist.
     * @throws SQLException
     *             if failed to persiste Resource.
     * @throws ResourcePersistenceException
     *             Resource with the id is already in the persistence.
     *
     */
    private void insertResource(Connection connection, Resource resource)
            throws SQLException, ResourcePersistenceException {
        PreparedStatement statement = null;
        String query = "INSERT INTO resource (resource_id, resource_role_id, project_id, project_phase_id, create_user,"
                + " create_date, modify_user, modify_date)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(query);

            statement.setLong(1, resource.getId());
            statement.setLong(2, resource.getResourceRole().getId());

            statement.setObject(3, resource.getProject());
            statement.setObject(4, resource.getPhase());

            statement.setString(5, resource.getCreationUser());
            statement.setTimestamp(6, Util.dateToTimestamp(resource
                    .getCreationTimestamp()));
            statement.setString(7, resource.getModificationUser());
            statement.setTimestamp(8, Util.dateToTimestamp(resource
                    .getModificationTimestamp()));

            if (statement.executeUpdate() != 1) {
                throw new ResourcePersistenceException(
                        "Resource with the id is already in the persistence");
            }

        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * insert the submission information into database.
     *
     * @param connection
     *            the connection to database
     *
     * @param resource
     *            the Resource instance to persist
     * @throws SQLException
     *             if failed to persist submission.
     */
    private void insertSubmission(Connection connection, Resource resource)
            throws SQLException {
        PreparedStatement statement = null;
        String query = "INSERT INTO resource_submission"
                + "(resource_id, submission_id, create_user, create_date, modify_user, modify_date)"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, resource.getId());
            statement.setObject(2, resource.getSubmission());

            statement.setString(3, resource.getCreationUser());
            statement.setTimestamp(4, Util.dateToTimestamp(resource
                    .getCreationTimestamp()));
            statement.setString(5, resource.getModificationUser());
            statement.setTimestamp(6, Util.dateToTimestamp(resource
                    .getModificationTimestamp()));

            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Look up table resource_info_type_lu for resource_info_type_id.
     *
     * @param connection
     *            the connection to database
     * @param name
     *            the name to look up in table.
     * @return Integer if exist, null if not
     * @throws SQLException
     *             if failed to look up resource_info_type_lu table for resource_info_type_id
     */
    private Integer getResourceInfoTypeId(Connection connection, String name)
            throws SQLException {
        String query = "SELECT resource_info_type_id FROM resource_info_type_lu WHERE name = ?";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            rs = statement.executeQuery();
            if (rs.next()) {
                return new Integer(rs.getInt(1));
            }
            return null;
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
        }
    }

    /**
     * Insert the properties of Resource into table resource_info.
     *
     * @param connection
     *            the connection to database
     * @param resource
     *            the Resource instance to be persisted.
     * @param resourceinfotypeid
     *            the resource_info_type_id
     * @param value
     *            the property value to be persisted
     * @throws SQLException
     *             if failed to persist resource_info
     */
    private void insertResourceInfo(Connection connection, Resource resource,
            int resourceinfotypeid, String value) throws SQLException {

        String query = "INSERT INTO resource_info"
                + "(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, resource.getId());
            statement.setInt(2, resourceinfotypeid);
            statement.setString(3, value);
            statement.setString(4, resource.getCreationUser());
            statement.setTimestamp(5, Util.dateToTimestamp(resource
                    .getCreationTimestamp()));
            statement.setString(6, resource.getModificationUser());
            statement.setTimestamp(7, Util.dateToTimestamp(resource
                    .getModificationTimestamp()));

            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * <p>
     * Deletes the given Resource (by id) in the persistence store. The Resource must already be present in the
     * persistence store, otherwise nothing is done.
     * </p>
     *
     * <p>
     * Any SqlException or DBConnectionException should be wrapped in a ResourcePersistenceException.
     * </p>
     *
     * @param resource
     *            The resource to remove
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resource is null or its id is UNSET_ID or its ResourceRole is null
     * @throws ResourcePersistenceException
     *             If there is a failure to persist the change.
     */
    public void deleteResource(Resource resource)
            throws ResourcePersistenceException {
        Util.checkResource(resource, true);

        Connection connection = createConnection();
        try {
            connection.setAutoCommit(false);

            deleteResourceInfos(connection, resource.getId());
            deleteFromResourceSubmission(connection, resource.getId());
            deleteFromResource(connection, resource.getId());

            connection.commit();
        } catch (SQLException ex) {
            Util.rollback(connection);
            throw new ResourcePersistenceException("Fail to delete resource",
                    ex);
        } finally {
            Util.setAutocommit(connection, true);
            Util.closeConnection(connection);
        }
    }

    /**
     * Delete the resource_info properties from table resource_info with the given resource id.
     *
     * @param connection
     *            the connection to database
     * @param resourceId
     *            the value of resource id in table resource_info
     * @throws SQLException
     *             if failed to delete
     */
    private void deleteResourceInfos(Connection connection, long resourceId)
            throws SQLException {
        String query = "DELETE FROM resource_info WHERE resource_id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, resourceId);
            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Delete the submission from resource_submission table.
     *
     * @param connection
     *            the connection to database
     * @param id
     *            the submission id to be deleted.
     * @throws SQLException
     *             if failed to delete the submission.
     */
    private void deleteFromResourceSubmission(Connection connection, long id)
            throws SQLException {
        String query = "DELETE FROM resource_submission WHERE resource_id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Delete the resource from resource table.
     *
     * @param connection
     *            the connection to database
     * @param id
     *            the resource id to be deleted.
     * @throws SQLException
     *             if failed to delete the resource
     */
    private void deleteFromResource(Connection connection, long id)
            throws SQLException {
        String query = "DELETE FROM resource WHERE resource_id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * <p>
     * Updates the given Resource in the database with its currently set information. The Resource must already be
     * present in the persistence store.
     * </p>
     *
     * <p>
     * The operator information should already have been put in the modification date/modification user properties of
     * the Resource. Any SqlException or DBConnectionException should be wrapped in a ResourcePersistenceException.
     * </p>
     *
     * @param resource
     *            The resource to update
     *
     * @throws IllegalArgumentException
     *             If resource is null or its id is UNSET_ID or its ResourceRole is null or its modification user/date
     *             is null
     * @throws ResourcePersistenceException
     *             If there is a failure to persist the Resource, or the Resource is not already in the persistence.
     */
    public void updateResource(Resource resource)
            throws ResourcePersistenceException {
        Util.checkResource(resource, false);

        Connection connection = createConnection();
        try {
            connection.setAutoCommit(false);

            // Update the resource table.
            updateResourceTable(connection, resource);

            // Update the resource_submission table accordingly.
            Integer previousSubmission = getSubmissionEntry(connection,
                    resource);
            if (previousSubmission == null && resource.getSubmission() != null) {
                // insert submission.
                insertSubmission(connection, resource);
            } else if (previousSubmission != null
                    && resource.getSubmission() == null) {
                // remove submission.
                deleteFromResourceSubmission(connection, resource.getId());

            } else if (previousSubmission != null
                    && resource.getSubmission() != null
                    && previousSubmission.longValue() != resource
                            .getSubmission().longValue()) {
                // update submission.
                updateResourceSubmission(connection, resource);
            }

            // updating the extended properties
            Map previousProperties = selectExternalProperties(connection,
                    new Resource(resource.getId())).getAllProperties();

            for (Iterator iter = resource.getAllProperties().entrySet()
                    .iterator(); iter.hasNext();) {
                Map.Entry entry = (Entry) iter.next();

                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                Object oldValue = previousProperties.get(key);
                if (value.equals(oldValue)) {
                    // there is previous entry, but same current entry, do not hit the db.
                    previousProperties.remove(key);

                } else if (oldValue == null) {
                    // no previous entry, but current entry has, insert the current entry.
                    Integer resourceInfoTypeId = getResourceInfoTypeId(
                            connection, key);

                    // if resource_info_type_id is found
                    if (resourceInfoTypeId != null) {
                        insertResourceInfo(connection, resource,
                                resourceInfoTypeId.intValue(), value);
                    }

                } else if (previousProperties.get(key) != null) {
                    // there is previous entry , but different from current entry, do an update.
                    Integer resourceInfoTypeId = getResourceInfoTypeId(
                            connection, key);
                    if (resourceInfoTypeId != null) {
                        updateResourceInfo(connection, resource.getId(),
                                resourceInfoTypeId.intValue(), value);
                    }
                    previousProperties.remove(key);
                }
            }

            // Up to now, what are left in the previousProperties are
            // properties which do not exist in the current properties list, we need to remove them from db.
            for (Iterator iter = previousProperties.entrySet().iterator(); iter
                    .hasNext();) {
                Map.Entry entry = (Entry) iter.next();

                Integer resourceInfoTypeId = getResourceInfoTypeId(connection,
                        entry.getKey().toString());
                if (resourceInfoTypeId != null) {
                    removeResourceInfo(connection, resource.getId(),
                            resourceInfoTypeId.intValue());
                }
            }

            connection.commit();
        } catch (SQLException ex) {
            Util.rollback(connection);
            throw new ResourcePersistenceException("Fail to update resource",
                    ex);
        } finally {
            Util.setAutocommit(connection, true);
            Util.closeConnection(connection);
        }
    }

    /**
     * Update the resource table with Resource instance.
     *
     * @param connection
     *            the connection to database.
     * @param resource
     *            the Resource instance to update
     * @throws SQLException
     *             if failed to update Resource instance.
     * @throws ResourcePersistenceException
     *             the Resource is not already in the persistence.
     */
    private void updateResourceTable(Connection connection, Resource resource)
            throws SQLException, ResourcePersistenceException {

        String query = "UPDATE resource SET resource_role_id = ?, project_id = ?, project_phase_id = ?,"
                + " modify_user = ?, modify_date = ? WHERE resource_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, resource.getResourceRole().getId());
            statement.setObject(2, resource.getProject());
            statement.setObject(3, resource.getPhase());
            statement.setString(4, resource.getModificationUser());
            statement.setTimestamp(5, Util.dateToTimestamp(resource
                    .getModificationTimestamp()));
            statement.setLong(6, resource.getId());

            if (statement.executeUpdate() != 1) {
                throw new ResourcePersistenceException(
                        "The resource id is not already in the database.");
            }
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * get the submission entry for Resource instance.
     *
     * @param connection
     *            the connection to database.
     * @param resource
     *            the Resource instance
     * @return Integer if exists, otherwise null
     * @throws SQLException
     *             if failed to get the submission entry for Resource instance.
     */
    private Integer getSubmissionEntry(Connection connection, Resource resource)
            throws SQLException {
        String query = "SELECT submission_id FROM resource_submission WHERE resource_id = ?";
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, resource.getId());

            rs = statement.executeQuery();

            if (rs.next()) {
                return new Integer(rs.getInt(1));
            }

            return null;
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
        }
    }

    /**
     * Update the submission of the Resource instance.
     *
     * @param connection
     *            the connection to database
     * @param resource
     *            the Resource instance
     * @throws SQLException
     *             if failed to update resource submission
     */
    private void updateResourceSubmission(Connection connection,
            Resource resource) throws SQLException {

        String query = "UPDATE resource_submission SET submission_id = ?, modify_user = ?, modify_date = ?"
                + " WHERE resource_id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);

            statement.setObject(1, resource.getSubmission());
            statement.setString(2, resource.getModificationUser());
            statement.setTimestamp(3, Util.dateToTimestamp(resource
                    .getModificationTimestamp()));
            statement.setLong(4, resource.getId());
            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Update the resource_info table with the given connection, resource_id and resource_info_type_id.
     *
     * @param connection
     *            the connection to the database.
     * @param resourceId
     *            the resource id.
     * @param resourceTypeInfoId
     *            the resource type info id.
     * @param value
     *            the value.
     * @throws SQLException
     *             if database operation fails for some reasons.
     */
    private void updateResourceInfo(Connection connection, long resourceId,
            int resourceTypeInfoId, String value) throws SQLException {
        String query = "UPDATE resource_info SET value = ? WHERE resource_id = ? AND resource_info_type_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, value);
            statement.setLong(2, resourceId);
            statement.setLong(3, resourceTypeInfoId);

            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Remove resource info with the given connection, resource_id and resourceInfo type id.
     *
     * @param connection
     *            the connection to the database.
     * @param resourceId
     *            the resource id.
     * @param resourceInfoTypeId
     *            the resource info type id.
     * @throws SQLException
     *             if database operation fails for some reasons.
     */
    private void removeResourceInfo(Connection connection, long resourceId,
            int resourceInfoTypeId) throws SQLException {
        String query = "DELETE FROM resource_info WHERE resource_id = ? and resource_info_type_id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, resourceId);
            statement.setInt(2, resourceInfoTypeId);

            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * <p>
     * Loads the resource from the persistence with the given id.
     * </p>
     *
     * <p>
     * Returns null if there is no resource for the given id.
     * </p>
     *
     *
     * @return The loaded Resource
     * @param resourceId
     *            The id of the Resource to load
     * @throws IllegalArgumentException
     *             If resourceId is not greater than 0
     * @throws ResourcePersistenceException
     *             If there is an error loading the Resource
     */
    public Resource loadResource(long resourceId)
            throws ResourcePersistenceException {
        Util.checkPositiveValue(resourceId, "resourceId");

        Connection connection = createConnection();

        String query = "SELECT resource.resource_id, resource_role_id, project_id, project_phase_id, submission_id,"
                + " resource.create_user, resource.create_date, resource.modify_user, resource.modify_date"
                + " FROM resource LEFT OUTER JOIN resource_submission"
                + " ON  resource.resource_id = resource_submission.resource_id WHERE resource.resource_id = ?";

        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, resourceId);

            rs = statement.executeQuery();

            if (rs.next()) {
                Resource resource = constructResource(rs);
                // select all external properties for the resource instance.
                return selectExternalProperties(connection, resource);
            }
            return null;
        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to load resource instance.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
            Util.closeConnection(connection);
        }
    }

    /**
     * Select all external properties for Resource, and set them into resource and returned.
     *
     * @param connection
     *            the connection to database
     * @param resource
     *            the resource instance
     * @return the resource instance with all external properties set.
     * @throws ResourcePersistenceException
     *             if failed to select all external properties for this resource instance.
     */
    private Resource selectExternalProperties(Connection connection,
            Resource resource) throws ResourcePersistenceException {

        String query = "SELECT resource_info_type_lu.name, resource_info.value"
                + " FROM resource_info, resource_info_type_lu  "
                + " WHERE resource_info.resource_info_type_id = resource_info_type_lu.resource_info_type_id "
                + " AND resource_id = ?";

        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, resource.getId());

            rs = statement.executeQuery();

            while (rs.next()) {
                resource.setProperty(rs.getString(1), rs.getString(2));
            }
            return resource;
        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to select external properties for resource.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
        }
    }

    /**
     * <p>
     * Adds a notification to the persistence store. A notification type with the given ID must already exist in the
     * persistence store, as must a project.
     * </p>
     *
     * @param user
     *            The user id to add as a notification
     * @param project
     *            The project the notification is related to
     * @param notificationType
     *            The id of the notification type
     * @param operator
     *            The operator making the change
     * @throws IllegalArgumentException
     *             If user, project, or notificationType is <= 0 or operator is null.
     * @throws ResourcePersistenceException
     *             If there is an error making the change in the persistence store
     */
    public void addNotification(long user, long project, long notificationType,
            String operator) throws ResourcePersistenceException {

        Util.checkPositiveValue(user, "user");
        Util.checkPositiveValue(project, "project");
        Util.checkPositiveValue(notificationType, "notificationType");
        Util.checkNull(operator, "operator");

        String query = "INSERT INTO notification "
                + "(project_id, external_ref_id, notification_type_id, create_user, "
                + "create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection connection = createConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);

            statement.setLong(1, project);
            statement.setLong(2, user);
            statement.setLong(3, notificationType);
            statement.setString(4, operator);

            Timestamp time = new Timestamp(System.currentTimeMillis());
            statement.setTimestamp(5, time);
            statement.setString(6, operator);
            statement.setTimestamp(7, time);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to insert notification for reason:"
                            + e.getMessage(), e);
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Removes a notification from the persistence store.
     * </p>
     *
     * <p>
     * The given notification tuple identifier (user, project, and notificationType) should already exists in the
     * persistence store, otherwise nothing will be done. Note that in this implementation the operator is not used.
     * </p>
     *
     * @param user
     *            The user id of the notification to remove
     * @param project
     *            The project id of the notification to remove
     * @param notificationType
     *            The notification type id of the notification to remove
     * @param operator
     *            The operator making the change
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If user, project, or notificationType is <= 0 or if operator is null
     * @throws ResourcePersistenceException
     *             If there is an error making the change in the persistence store
     */
    public void removeNotification(long user, long project,
            long notificationType, String operator)
            throws ResourcePersistenceException {
        Util.checkPositiveValue(user, "user");
        Util.checkPositiveValue(project, "project");
        Util.checkPositiveValue(notificationType, "notificationType");
        Util.checkNull(operator, "operator");

        String query = "DELETE FROM notification WHERE project_id = ? AND external_ref_id = ? "
                + "AND notification_type_id = ?";

        PreparedStatement statement = null;
        Connection connection = createConnection();
        try {

            statement = connection.prepareStatement(query);
            statement.setLong(1, project);
            statement.setLong(2, user);
            statement.setLong(3, notificationType);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to remove notification.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Load the Notification for the given "id" triple from the persistence store. Returns null if no entry in the
     * persistence has the given user, project, and notificationType.
     * </p>
     *
     * @return The loaded notification
     * @param user
     *            The id of the user
     * @param project
     *            The id of the project
     * @param notificationType
     *            The id of the notificationType
     *
     * @throws IllegalArgumentException
     *             If user, project, or notificationType is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error making the change in the persistence store
     */
    public Notification loadNotification(long user, long project,
            long notificationType) throws ResourcePersistenceException {
        Util.checkPositiveValue(user, "user");
        Util.checkPositiveValue(project, "project");
        Util.checkPositiveValue(notificationType, "notificationType");

        Connection connection = createConnection();

        ResultSet rs = null;
        PreparedStatement statement = null;

        String query = "SELECT project_id, external_ref_id, notification_type_id,"
                + " create_user, create_date, modify_user, modify_date"
                + " FROM notification WHERE project_id = ? AND external_ref_id = ? AND notification_type_id = ?";

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, project);
            statement.setLong(2, user);
            statement.setLong(3, notificationType);

            rs = statement.executeQuery();

            if (rs.next()) {
                return consructNotification(connection, rs);
            }

            return null;
        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to load the notification.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Adds a notification type to the persistence store. The id of the notification type must already be assigned to
     * the NotificationType object passed to this method, and not already exist in the persistence source.
     * </p>
     *
     * @param notificationType
     *            The notification type to add.
     *
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is NotificationType.UNSET_ID or its name/description is null or
     *             its creation/modification user/date are null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void addNotificationType(NotificationType notificationType)
            throws ResourcePersistenceException {
        Util.checkNotificationType(notificationType, false);

        String query = "INSERT INTO notification_type_lu(notification_type_id, name, description, create_user, "
                + "create_date, modify_user, modify_date) VALUES(?, ?, ?, ?, ?, ?, ?)";

        Connection connection = createConnection();

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, notificationType.getId());
            statement.setString(2, notificationType.getName());
            statement.setString(3, notificationType.getDescription());
            statement.setString(4, notificationType.getCreationUser());

            statement.setTimestamp(5, Util.dateToTimestamp(notificationType
                    .getCreationTimestamp()));
            statement.setString(6, notificationType.getModificationUser());
            statement.setTimestamp(7, Util.dateToTimestamp(notificationType
                    .getModificationTimestamp()));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to add the notificationType instance.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Removes a notification type from the persistence (by id) store. If no notification type exists with the id of the
     * notification type, nothing is done.
     * </p>
     *
     * @param notificationType
     *            The notification type to delete.
     *
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is UNSET_ID.
     * @throws ResourcePersistenceException
     *             If there is an error deleting the notification type in the persistence
     */
    public void deleteNotificationType(NotificationType notificationType)
            throws ResourcePersistenceException {
        Util.checkNotificationType(notificationType, true);

        Connection connection = createConnection();
        try {
            deleteNotificationType(connection, notificationType);
        } catch (SQLException ex) {
            throw new ResourcePersistenceException(
                    "Failed to delete the NotificationType instance.", ex);
        } finally {
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Updates the notification type in the persistence store. The notification type (by id) must exist in the
     * persistence store.
     * </p>
     *
     * @param notificationType
     *            The notification type to update
     *
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is UNSET_ID or its name/description is null or its modification
     *             user/date is null
     * @throws ResourcePersistenceException
     *             if there is an error updating the notification type in the persistence
     */
    public void updateNotificationType(NotificationType notificationType)
            throws ResourcePersistenceException {
        Util.checkNotificationType(notificationType, false);

        Connection connection = createConnection();
        try {
            updateNotificationType(connection, notificationType);
        } catch (SQLException ex) {
            throw new ResourcePersistenceException(
                    "Failed to update the notificationType instance.", ex);
        } finally {
            Util.closeConnection(connection);
        }
    }

    /**
     * Update the NotificationType instance in the database.
     *
     * @param connection
     *            the connection to database.
     * @param type
     *            the NotificationType instance to be updated.
     * @throws SQLException
     *             if failed to update the notificationType instance.
     */
    private void updateNotificationType(Connection connection,
            NotificationType type) throws SQLException {

        String query = "UPDATE notification_type_lu SET name = ?, description = ?, modify_user = ?, modify_date = ? "
                + "WHERE notification_type_id = ?";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, type.getName());
            statement.setString(2, type.getDescription());
            statement.setString(3, type.getModificationUser());
            statement.setTimestamp(4, Util.dateToTimestamp(type
                    .getModificationTimestamp()));
            statement.setLong(5, type.getId());

            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Delete NotificationType instance.
     *
     * @param connection
     *            the connection to database.
     * @param type
     *            the notificationType instance to be deleted
     * @throws SQLException
     *             if failed to delete the notificationtype instance.
     */
    private void deleteNotificationType(Connection connection,
            NotificationType type) throws SQLException {
        String query = "DELETE FROM notification_type_lu WHERE notification_type_id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);

            statement.setLong(1, type.getId());

            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * <p>
     * Loads the notification type from the persistence with the given id. Returns null if there is no notification type
     * with the given id.
     * </p>
     *
     * @return The loaded notification type
     * @param notificationTypeId
     *            The id of the notification type to load
     *
     * @throws IllegalArgumentException
     *             If notificationTypeId is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading the notification type
     */
    public NotificationType loadNotificationType(long notificationTypeId)
            throws ResourcePersistenceException {
        Util.checkPositiveValue(notificationTypeId, "notificationTypeId");

        Connection connection = createConnection();
        try {
            return loadNotificationType(connection, notificationTypeId);
        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Fail to load notification type.", e);
        } finally {
            Util.closeConnection(connection);
        }
    }

    /**
     * Load the NotificationType instance with notificationTypeId in the database.
     *
     * @param connection
     *            the connection to database.
     * @param notificationTypeId
     *            the notificationTypeId to load
     * @return NotificationType instance if exists, null otherwise.
     * @throws SQLException
     *             if failed to load the NotificationType instance.
     */
    private NotificationType loadNotificationType(Connection connection,
            long notificationTypeId) throws SQLException {
        String query = "SELECT notification_type_id, name, description, create_user, "
                + "create_date, modify_user, modify_date"
                + " FROM notification_type_lu WHERE notification_type_id = ?";

        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, (int) notificationTypeId);

            rs = statement.executeQuery();

            if (rs.next()) {
                NotificationType type = new NotificationType();
                type.setId(rs.getInt(1));
                type.setName(rs.getString(2));
                type.setDescription(rs.getString(3));
                type.setCreationUser(rs.getString(4));
                type.setCreationTimestamp(rs.getTimestamp(5));
                type.setModificationUser(rs.getString(6));
                type.setModificationTimestamp(rs.getTimestamp(7));

                return type;
            }
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
        }

        return null;
    }

    /**
     * <p>
     * Adds a resource role to the persistence store. The id of the resource role must already be assigned to the
     * notificationType object passed to this method, and not already exist in the persistence source.
     * </p>
     *
     * @param resourceRole
     *            The resource role to add.
     *
     * @throws IllegalArgumentException
     *             If resourceRole is null or its id is UNSET_ID or its name/description is null or its
     *             creation/modification date/user is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void addResourceRole(ResourceRole resourceRole)
            throws ResourcePersistenceException {
        Util.checkResourceRole(resourceRole, false);

        String query = "INSERT INTO resource_role_lu(resource_role_id, name, "
                + "description, phase_type_id, create_user, create_date, modify_user, modify_date)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = createConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);

            statement.setLong(1, resourceRole.getId());
            statement.setString(2, resourceRole.getName());
            statement.setString(3, resourceRole.getDescription());
            statement.setObject(4, resourceRole.getPhaseType());

            statement.setString(5, resourceRole.getCreationUser());
            statement.setTimestamp(6, Util.dateToTimestamp(resourceRole
                    .getCreationTimestamp()));
            statement.setString(7, resourceRole.getModificationUser());
            statement.setTimestamp(8, Util.dateToTimestamp(resourceRole
                    .getModificationTimestamp()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to add the ResourceRole instance.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Removes a resource role from the persistence store. If no resource role exists with the given id, nothing is
     * done.
     * </p>
     *
     * @param resourceRole
     *            The notification type to delete.
     *
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is UNSET_ID.
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void deleteResourceRole(ResourceRole resourceRole)
            throws ResourcePersistenceException {
        Util.checkResourceRole(resourceRole, true);

        Connection connection = createConnection();
        String query = "DELETE FROM resource_role_lu WHERE resource_role_id = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, resourceRole.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to add the ResourceRole instance.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Updates the resource role in the persistence store. The resource role (by id) must exist in the persistence
     * store.
     * </p>
     *
     * @param resourceRole
     *            The resource role to update
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If resourceRole is null or its id is UNSET_ID or its name/description is null or its modification
     *             user/date is null
     * @throws ResourcePersistenceException
     *             if there is an error updating the persistence
     */
    public void updateResourceRole(ResourceRole resourceRole)
            throws ResourcePersistenceException {
        Util.checkResourceRole(resourceRole, false);

        Connection connection = createConnection();

        String query = "UPDATE resource_role_lu SET phase_type_id = ?, name = ?, "
                + "description = ?, modify_user = ?, modify_date = ? WHERE resource_role_id = ?";

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);

            statement.setObject(1, resourceRole.getPhaseType());
            statement.setString(2, resourceRole.getName());
            statement.setString(3, resourceRole.getDescription());
            statement.setString(4, resourceRole.getModificationUser());
            statement.setTimestamp(5, Util.dateToTimestamp(resourceRole
                    .getModificationTimestamp()));
            statement.setLong(6, resourceRole.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to update the ResourceRole instance.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Loads the resource role from the persistence with the given id. Returns null if there is no resource role with
     * the given id.
     * </p>
     *
     * @return The loaded resource role
     * @param resourceRoleId
     *            The id of the resource role to load
     *
     * @throws IllegalArgumentException
     *             If resourceRoleId is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading the resource role
     */
    public ResourceRole loadResourceRole(long resourceRoleId)
            throws ResourcePersistenceException {
        Util.checkPositiveValue(resourceRoleId, "resourceRoleId");

        String query = "SELECT resource_role_id, phase_type_id, name, description, create_user,"
                + " create_date, modify_user, modify_date"
                + " FROM resource_role_lu WHERE resource_role_id = ?";

        Connection connection = createConnection();
        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, resourceRoleId);

            rs = statement.executeQuery();

            if (rs.next()) {
                return constructResourceRole(rs);
            }

        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to load ResourceRole instance.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
            Util.closeConnection(connection);
        }

        return null;
    }

    /**
     * <p>
     * Loads the resources from the persistence with the given ids. May return a 0-length array.
     * </p>
     *
     * @param resourceIds
     *            The ids of resources to load
     * @return The loaded resources
     * @throws IllegalArgumentException
     *             If any id is <= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading the Resources
     */
    public Resource[] loadResources(long[] resourceIds)
            throws ResourcePersistenceException {
        Util.checkLongArray(resourceIds, "resourceIds");

        if (resourceIds.length == 0) {
            return new Resource[0];
        }

        // select all resource once.
        String query = "SELECT resource.resource_id, resource_role_id, project_id, project_phase_id, submission_id,"
                + " resource.create_user, resource.create_date, resource.modify_user, resource.modify_date"
                + " FROM resource LEFT OUTER JOIN resource_submission"
                + " ON resource.resource_id = resource_submission.resource_id WHERE resource.resource_id IN (";

        Connection connection = createConnection();

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.prepareStatement(buildQueryWithIds(query,
                    resourceIds));
            rs = statement.executeQuery();

            List list = new ArrayList();

            while (rs.next()) {
                list.add(constructResource(rs));
            }

            Resource[] resources = (Resource[]) list.toArray(new Resource[list
                    .size()]);

            // select all the external properties once and add the matching properties into resource instances.
            Map map = getAllExternalProperties(connection, resourceIds);

            for (int i = 0; i < resources.length; i++) {
                Resource resource = resources[i];
                Map properties = (Map) map.get(new Long(resource.getId()));

                if (properties == null) {
                    continue;
                }

                for (Iterator iter = properties.entrySet().iterator(); iter
                        .hasNext();) {
                    Map.Entry entry = (Entry) iter.next();

                    resource.setProperty(entry.getKey().toString(), entry
                            .getValue().toString());
                }
            }

            return resources;

        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to load all the resources.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
            Util.closeConnection(connection);
        }
    }

    /**
     * Construct a Resource instance from given ResultSet instance.
     *
     * @param rs
     *            the ResultSet instance
     * @return The Resource instance
     * @throws ResourcePersistenceException
     *             if failed to construct the Resource instance.
     */
    private Resource constructResource(ResultSet rs)
            throws ResourcePersistenceException {
        try {
            Resource resource = new Resource();

            resource.setId(rs.getLong(1));
            ResourceRole role = this.loadResourceRole(rs.getLong(2));

            resource.setResourceRole(role);

            if (rs.getObject(3) == null) {
                resource.setProject(null);
            } else {
                resource.setProject(new Long(rs.getLong(3)));
            }

            if (rs.getObject(4) == null) {
                resource.setPhase(null);
            } else {
                resource.setPhase(new Long(rs.getLong(4)));
            }

            if (rs.getObject(5) == null) {
                resource.setSubmission(null);
            } else {
                resource.setSubmission(new Long(rs.getLong(5)));
            }

            resource.setCreationUser(rs.getString(6));
            resource.setCreationTimestamp(rs.getTimestamp(7));
            resource.setModificationUser(rs.getString(8));
            resource.setModificationTimestamp(rs.getTimestamp(9));

            return resource;
        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to load the Resource from ResultSet.", e);
        }
    }

    /**
     * Build a select sql query with an argument contains many long values. The structure of the result string looks
     * like this: ... in ( id, id, id, id...).
     *
     * @param baseQuery
     *            the sql query
     * @param ids
     *            the ids for select sql query
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
     * Get all the external properties with one select sql query. Here a HashMap structure is used. The key is an
     * Integer of resourceId and the value is another map which contains the key/value of external properties.
     *
     * @param connection
     *            the connection to database
     * @param resourceIds
     *            the resourceIds for retrieving external properties
     * @return a Map contained all external properties.
     * @throws ResourcePersistenceException
     *             if failed to select all external properties at once.
     */
    private Map getAllExternalProperties(Connection connection,
            long[] resourceIds) throws ResourcePersistenceException {

        // Map from resource id to a Map containing the properties of the resource.
        Map resourcesProperties = new HashMap();

        // this sql query will select all internal properties once.
        String query = "SELECT resource_info.resource_id, resource_info_type_lu.name, resource_info.value"
                + " FROM resource_info, resource_info_type_lu ON"
                + " WHERE resource_info.resource_info_type_id = "
                + " resource_info_type_lu.resource_info_type_id "
                + " AND resource_info.resource_id IN (";

        PreparedStatement statement = null;

        ResultSet rs = null;

        try {
            statement = connection.prepareStatement(buildQueryWithIds(query,
                    resourceIds));

            rs = statement.executeQuery();

            while (rs.next()) {
                Long resourceId = new Long(rs.getLong(1));

                String key = rs.getString(2);
                String value = rs.getString(3);

                Map properties = (Map) resourcesProperties.get(resourceId);
                if (properties == null) {
                    properties = new HashMap();
                    resourcesProperties.put(resourceId, properties);
                }
                properties.put(key, value);
            }
            return resourcesProperties;
        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed for getting all external properties for all resourceIds.",
                    e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);
        }
    }

    /**
     * <p>
     * Loads the notification types from the persistence with the given ids. May return a 0-length array.
     * </p>
     *
     * @param notificationTypeIds
     *            The ids of notification types to load
     * @return The loaded notification types
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException
     *             If any id is <= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading from persistence
     */
    public NotificationType[] loadNotificationTypes(long[] notificationTypeIds)
            throws ResourcePersistenceException {
        Util.checkLongArray(notificationTypeIds, "notificationTypeIds");

        if (notificationTypeIds.length == 0) {
            return new NotificationType[0];
        }

        String query = "SELECT notification_type_id, name, description, create_user,"
                + " create_date, modify_user, modify_date"
                + " FROM notification_type_lu WHERE notification_type_id  IN (";

        Connection connection = createConnection();
        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(buildQueryWithIds(query,
                    notificationTypeIds));

            rs = statement.executeQuery();

            List list = new ArrayList();

            while (rs.next()) {
                list.add(constructNotificationType(rs));
            }

            return (NotificationType[]) list.toArray(new NotificationType[list
                    .size()]);

        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to load NotificationTypes instances.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
            Util.closeConnection(connection);
        }

    }

    /**
     * Consruct the NotificationType instance from the given ResultSet instance.
     *
     * @param rs
     *            the ResultSet instance
     * @return NotifcationType instance.
     * @throws SQLException
     *             if failed to load the notificationType instance from the database.
     */
    private NotificationType constructNotificationType(ResultSet rs)
            throws SQLException {
        NotificationType type = new NotificationType();
        type.setId(rs.getLong(1));
        type.setName(rs.getString(2));
        type.setDescription(rs.getString(3));
        type.setCreationUser(rs.getString(4));
        type.setCreationTimestamp(rs.getTimestamp(5));
        type.setModificationUser(rs.getString(6));
        type.setModificationTimestamp(rs.getTimestamp(7));
        return type;
    }

    /**
     * <p>
     * Loads the resource roles from the persistence with the given ids. May return a 0-length array.
     * </p>
     *
     * @param resourceRoleIds
     *            The ids of resource roles to load
     * @return The loaded resource roles
     *
     * @throws IllegalArgumentException
     *             If any id is <= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading from persistence
     */
    public ResourceRole[] loadResourceRoles(long[] resourceRoleIds)
            throws ResourcePersistenceException {
        Util.checkLongArray(resourceRoleIds, "resourceRoleIds");

        if (resourceRoleIds.length == 0) {
            return new ResourceRole[0];
        }

        String baseQuery = "SELECT resource_role_id, phase_type_id, name, description,"
                + " create_user, create_date, modify_user, modify_date"
                + " FROM resource_role_lu " + "WHERE resource_role_id IN (";

        Connection connection = createConnection();

        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(buildQueryWithIds(
                    baseQuery, resourceRoleIds));

            rs = statement.executeQuery();

            List roles = new ArrayList();

            while (rs.next()) {
                roles.add(constructResourceRole(rs));
            }

            return (ResourceRole[]) roles
                    .toArray(new ResourceRole[roles.size()]);

        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to load nResourceRole instance.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
            Util.closeConnection(connection);
        }
    }

    /**
     * Construct the ResourceRole instance from the given ResultSet instance.
     *
     * @param rs
     *            the ResultSet instance
     * @return the ResourceRole instance
     * @throws SQLException
     *             if failed to get the ResourceRole instance from the ResultSet.
     */
    private ResourceRole constructResourceRole(ResultSet rs)
            throws SQLException {
        ResourceRole role = new ResourceRole();

        role.setId(rs.getLong(1));

        if (rs.getObject(2) == null) {
            role.setPhaseType(null);
        } else {
            role.setPhaseType(new Long(rs.getLong(2)));
        }

        role.setName(rs.getString(3));
        role.setDescription(rs.getString(4));
        role.setCreationUser(rs.getString(5));
        role.setCreationTimestamp(rs.getTimestamp(6));
        role.setModificationUser(rs.getString(7));
        role.setModificationTimestamp(rs.getTimestamp(8));

        return role;
    }

    /**
     * <p>
     * Load the Notifications for the given "id" triples from the persistence store. May return a 0-length array.
     * </p>
     *
     * @param userIds
     *            The ids of the users
     * @param projectIds
     *            The ids of the projects
     * @param notificationTypes
     *            The ids of the notification types
     * @return The loaded notifications
     *
     * @throws IllegalArgumentException
     *             If the three arrays don't all have the same number of elements (or any array is null) or all three
     *             arrays do not have the same length, any id is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading from the persistence
     */
    public Notification[] loadNotifications(long[] userIds, long[] projectIds,
            long[] notificationTypes) throws ResourcePersistenceException {
        Util.checkLongArray(userIds, "userIds");
        Util.checkLongArray(projectIds, "projectIds");
        Util.checkLongArray(notificationTypes, "notificationTypes");

        if (userIds.length != projectIds.length
                || projectIds.length != notificationTypes.length) {
            throw new IllegalArgumentException(
                    "All three arrays do not have the same length.");
        }

        if (userIds.length == 0) {
            return new Notification[0];
        }

        String baseQuery = "SELECT project_id, external_ref_id, notification_type_id, create_user, "
                + "create_date, modify_user, modify_date FROM notification WHERE ";

        // construct the sql query.
        StringBuffer buffer = new StringBuffer(baseQuery);

        for (int i = 0; i < userIds.length; i++) {
            if (i > 0) {
                buffer.append(" OR ");
            }

            buffer.append('(');
            buffer.append("project_id = " + projectIds[i]);
            buffer.append(" AND external_ref_id = " + userIds[i]);
            buffer
                    .append(" AND notification_type_id = "
                            + notificationTypes[i]);
            buffer.append(')');
        }
        // end of constructing sql query.

        Connection connection = createConnection();

        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(buffer.toString());

            rs = statement.executeQuery();

            List list = new ArrayList();

            while (rs.next()) {
                list.add(consructNotification(connection, rs));
            }

            return (Notification[]) list.toArray(new Notification[list.size()]);

        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to load Notification instances.", e);
        } finally {
            Util.closeStatement(statement);
            Util.closeResultSet(rs);
            Util.closeConnection(connection);
        }
    }

    /**
     *
     * Construct the Notification instance from the ResultSet instance.
     *
     * @param connection
     *            the connection to the db.
     * @param rs
     *            the ResultSet instance
     * @return the constructed Notification instance.
     * @throws ResourcePersistenceException
     *             if failed to get the Notification instance from database.
     */
    private Notification consructNotification(Connection connection,
            ResultSet rs) throws ResourcePersistenceException {
        try {
            NotificationType type = loadNotificationType(connection, rs
                    .getLong(3));

            Notification notification = new Notification(rs.getLong(1), type,
                    rs.getLong(2));

            notification.setCreationUser(rs.getString(4));
            notification.setCreationTimestamp(rs.getTimestamp(5));
            notification.setModificationUser(rs.getString(6));
            notification.setModificationTimestamp(rs.getTimestamp(7));

            return notification;

        } catch (SQLException e) {
            throw new ResourcePersistenceException(
                    "Failed to construct Notification instance.", e);
        }
    }

    /**
     * Create a jdbc Connection to the db.
     *
     * @return Connection to the database
     * @throws ResourcePersistenceException
     *             if failed to create connection.
     */
    private Connection createConnection() throws ResourcePersistenceException {
        Connection connection = null;
        try {
            if (connectionName == null) {
                connection = factory.createConnection();
            } else {
                connection = factory.createConnection(connectionName);
            }
            return connection;
        } catch (DBConnectionException de) {
            throw new ResourcePersistenceException(
                    "Failed to create connection.", de);
        }
    }
}
