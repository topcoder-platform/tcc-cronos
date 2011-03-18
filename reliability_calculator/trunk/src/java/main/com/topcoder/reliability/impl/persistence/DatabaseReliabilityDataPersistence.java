/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.reliability.Helper;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.impl.ReliabilityDataPersistence;
import com.topcoder.reliability.impl.ReliabilityDataPersistenceException;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is an implementation of ReliabilityDataPersistence that uses DB Connection Factory and JDBC to access
 * reliability specific data in the database persistence. It caches a Connection instance between open() and close()
 * calls.
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe. It's assumed that configure() method
 * will be called just once right after instantiation, before calling any business methods. saveUserReliabilityData()
 * and updateCurrentUserReliability() methods that can modify data in persistence use transactions.
 * </p>
 * @author saarixx, sparemax
 * @version 1.0
 */
public class DatabaseReliabilityDataPersistence implements ReliabilityDataPersistence {

    /**
     * <p>
     * Represents the question mark.
     * </p>
     */
    private static final String QUESTION_MARK = "?";

    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DatabaseReliabilityDataPersistence.class.getName();

    /**
     * <p>
     * Represents the child key 'dbConnectionFactoryConfig'.
     * </p>
     */
    private static final String KEY_DBCF_CONFIG = "dbConnectionFactoryConfig";

    /**
     * <p>
     * Represents the property key 'connectionName'.
     * </p>
     */
    private static final String KEY_CONN_NAME = "connectionName";

    /**
     * <p>
     * Represents the value used to adjust the project id.
     * </p>
     */
    private static final long PROJECT_ID_ADJUST = 111;

    /**
     * <p>
     * Represents the SQL string to query ids of users.
     * </p>
     */
    private static final String SQL_QUERY_USER_IDS =
            "SELECT DISTINCT pr.user_id FROM project_result pr, project_phase pp, project p, "
                    // The following four tables are joined for the consistency with the SQL_QUERY_PARTICIPATION_DATA
                    // query.
                    // Some legacy projects may contain bad or missing data and we need to make sure we don't return
                    // user IDs for which SQL_QUERY_PARTICIPATION_DATA would return no "reliable" project.
                    + " upload u, resource r, resource_info ri, submission s, component_inquiry ci "
                    + " WHERE pr.project_id = pp.project_id AND"
                    + " pp.phase_type_id = 2 AND" // "Submission" phase type
                    + " pp.scheduled_start_time >= ?"
                    + " AND pr.passed_review_ind = 1 AND pr.project_id = p.project_id AND p.project_category_id = ? AND"
                    + " p.project_id NOT IN (SELECT ce.contest_id FROM contest_eligibility ce WHERE ce.is_studio = 0) AND"
                    + " p.project_id IN (SELECT pi.project_id FROM project_info pi WHERE pi.project_info_type_id = 13 AND"
                    + " (pi.value = 'Yes' OR pi.value = 'yes')) AND "
                    + " r.project_id = p.project_id AND r.resource_role_id = 1 AND "
                    + " r.resource_id = ri.resource_id AND ri.resource_info_type_id = 1 AND ri.value = pr.user_id AND "
                    + " u.resource_id = r.resource_id AND u.upload_type_id = 1 AND u.upload_status_id = 1 AND "
                    + " s.upload_id = u.upload_id AND s.submission_type_id = 1 AND s.submission_status_id != 5 AND "
                    + " ci.project_id = pr.project_id AND ci.user_id = pr.user_id AND"
                    // retrieve only projects with statuses that should be included
                    + " p.project_status_id IN (%1$s)";

    /**
     * <p>
     * Represents the SQL string to query user participation data.
     * </p>
     */
    private static final String SQL_QUERY_PARTICIPATION_DATA =
            "SELECT distinct pr.project_id, ci.create_time, "
                    + " s.submission_status_id, pp1.scheduled_start_time, pp1.phase_status_id, pp1.actual_end_time, "
                    + " pp2.actual_end_time, pp3.actual_end_time, pr.passed_review_ind, pr.final_score "
                    + " FROM project_result pr "
                    + " INNER JOIN project p ON pr.project_id = p.project_id and p.project_category_id = ? and p.project_status_id != 9 "
                    + " INNER JOIN component_inquiry ci ON ci.project_id = pr.project_id and ci.user_id = pr.user_id "
                    +
                    // "Submission" phase type
                    " INNER JOIN project_phase pp1 ON pr.project_id = pp1.project_id and pp1.phase_type_id = 2 "
                    + " AND pp1.scheduled_start_time >= ? "
                    + " INNER JOIN resource r ON r.project_id = p.project_id and r.resource_role_id = 1 "
                    +
                    // "External Reference ID" resource info value should match user ID
                    " INNER JOIN resource_info ri ON r.resource_id = ri.resource_id and ri.resource_info_type_id = 1 "
                    + " AND ri.value = pr.user_id "
                    +
                    // "Screening" phase type
                    " LEFT OUTER JOIN project_phase pp2 ON pr.project_id = pp2.project_id AND pp2.phase_type_id = 3 "
                    +
                    // "Appeals Response" phase type
                    " LEFT OUTER JOIN project_phase pp3 ON pr.project_id = pp3.project_id AND pp3.phase_type_id = 6 "
                    +
                    // User didn't submit or "Submission" upload type and "Active" upload status
                    " LEFT OUTER JOIN upload u ON pr.project_id = u.project_id AND u.resource_id = r.resource_id "
                    + " AND u.upload_type_id = 1 AND u.upload_status_id = 1 "
                    +
                    // User didn't submit or "Contest Submission" submission type and not "Deleted" submission status
                    " LEFT OUTER JOIN submission s ON s.upload_id = u.upload_id and s.submission_type_id = 1 "
                    + " AND s.submission_status_id != 5 "
                    + " WHERE "
                    + " pr.user_id = ? AND "
                    + " p.project_id NOT IN (SELECT ce.contest_id FROM contest_eligibility ce WHERE ce.is_studio = 0) AND "
                    + " p.project_id IN (SELECT pi.project_id FROM project_info pi WHERE pi.project_info_type_id = 13 "
                    + " AND (pi.value = 'Yes' or pi.value = 'yes')) AND "
                    +
                    // Either both the upload and submission should be joined or none of them.
                    // This check is needed to account for the very old projects for which the upload status
                    // was not set to "Deleted" for the re-uploaded submissions.
                    " ((s.submission_id IS NULL AND u.upload_id IS NULL) OR "
                    + "  (s.submission_id IS NOT NULL AND u.upload_id IS NOT NULL)) AND"
                    // retrieve only projects with statuses that should be included
                    + " p.project_status_id IN (%1$s)";

    /**
     * <p>
     * Represents the SQL string to delete reliability data for specified track.
     * </p>
     */
    private static final String SQL_DELETE_RELIABILITY_DATA = "DELETE FROM project_reliability WHERE project_id IN ("
    // delete all projects for given track(project_category_id)
            + " SELECT p.project_id FROM project p WHERE p.project_category_id = ?)";

    /**
     * <p>
     * Represents the SQL string to insert user reliability data.
     * </p>
     */
    private static final String SQL_INSERT_RELIABILITY_DATA =
            "INSERT INTO project_reliability (project_id, user_id,"
                    + " resolution_date, reliability_before_resolution, reliability_after_resolution, reliability_on_registration,"
                    + " reliable_ind) VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL string to update user reliability.
     * </p>
     */
    private static final String SQL_UPDATE_USER_RELIABILITY = "UPDATE user_reliability SET rating = ?"
            + " WHERE user_id = ? AND phase_id = ?";

    /**
     * <p>
     * Represents the SQL string to insert user reliability.
     * </p>
     */
    private static final String SQL_INSERT_USER_RELIABILITY =
            "INSERT INTO user_reliability (rating, user_id, phase_id)" + " VALUES (?, ?, ?)";

    /**
     * <p>
     * The database connection factory to be used by this class.
     * </p>
     * <p>
     * Is initialized in configure() and never changed after that. Cannot be null after initialization. Is used in
     * open().
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * The connection name to be passed to the connection factory when establishing a database connection. If null, the
     * default connection is used.
     * </p>
     * <p>
     * Is initialized in configure() and never changed after that. Cannot be empty after initialization. Is used in
     * open().
     * </p>
     */
    private String connectionName;

    /**
     * <p>
     * The logger used by this class for logging errors and debug information.
     * </p>
     * <p>
     * Is initialized in the configure() method and never changed after that. If is null, logging is not performed. Is
     * used in all public business methods.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * The cached connection to the database.
     * </p>
     * <p>
     * Is null if connection is not yet opened or already closed. Is used in all public business methods.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the project statuses that should be included in SQL queries.
     * </p>
     * <p>
     * If null all statuses should be included.
     * </p>
     * <p>
     * Is initialized in the setIncludedProjectStatuses() method.
     * </p>
     */
    private List < Long > includedProjectStatuses;

    /**
     * <p>
     * Creates an instance of DatabaseReliabilityDataPersistence.
     * </p>
     */
    public DatabaseReliabilityDataPersistence() {
        // Empty
    }

    /**
     * <p>
     * Configures this instance with use of the given configuration object.
     * </p>
     * @param config the configuration object.
     * @throws IllegalArgumentException if config is <code>null</code>.
     * @throws ReliabilityCalculatorConfigurationException if some error occurred when initializing an instance using
     *             the given configuration.
     */
    public void configure(ConfigurationObject config) {
        Helper.checkNull(config, "config");
        // Get logger
        log = Helper.getLog(config);
        // Get configuration of DB Connection Factory
        ConfigurationObject dbConnectionFactoryConfig = Helper.getChildConfig(config, KEY_DBCF_CONFIG);
        try {
            // Create database connection factory using the extracted configuration
            dbConnectionFactory = new DBConnectionFactoryImpl(dbConnectionFactoryConfig);
        } catch (UnknownConnectionException e) {
            throw new ReliabilityCalculatorConfigurationException("Failed to create a database connection factory.", e);
        } catch (ConfigurationException e) {
            throw new ReliabilityCalculatorConfigurationException("Failed to create a database connection factory.", e);
        }
        // Get connection name
        connectionName = Helper.getProperty(config, KEY_CONN_NAME, false);
    }

    /**
     * <p>
     * Opens a connection to persistence.
     * </p>
     * @throws IllegalStateException if connection is already opened; if this persistence was not properly configured.
     * @throws ReliabilityDataPersistenceException if some error occurred when accessing the persistence.
     */
    public void open() throws ReliabilityDataPersistenceException {
        Date enterTimestamp = new Date();
        String signature = getSignature("open()");
        // Log method entry
        Helper.logEntrance(log, signature, null, null);
        try {
            Helper.checkState(dbConnectionFactory == null, "This persistence was not properly configured.");
            Helper.checkState(connection != null, "The connection is already opened.");
            try {
                // Create a connection
                connection =
                        (connectionName == null) ? dbConnectionFactory.createConnection() : dbConnectionFactory
                                .createConnection(connectionName);
                // Disable the auto commit mode
                connection.setAutoCommit(false);
            } catch (DBConnectionException e) {
                throw new ReliabilityDataPersistenceException("Failed to create a database connection.", e);
            } catch (SQLException e) {
                throw new ReliabilityDataPersistenceException("A database access error occurred.", e);
            }
            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (IllegalStateException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
        } catch (ReliabilityDataPersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e,
                    "ReliabilityDataPersistenceException is thrown when opening a connection to persistence.");
        }
    }

    /**
     * <p>
     * Retrieves the IDs of users who have reliability rating in the specified project category.
     * </p>
     * @param startDate the start date when the reliability started counting in the specified project category
     *            (corresponds to submission phase start date).
     * @param projectCategoryId the ID of the project category.
     * @return the retrieved IDs of users with reliability (not <code>null</code>, doesn't contain <code>null</code>).
     * @throws IllegalArgumentException if projectCategoryId is not positive or startDate is <code>null</code>.
     * @throws IllegalStateException if persistence connection is not opened; if this persistence was not properly
     *             configured; if includedProjectStatuses is not set properly
     * @throws ReliabilityDataPersistenceException if some error occurred when accessing the persistence.
     */
    public List < Long > getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)
        throws ReliabilityDataPersistenceException {
        Date enterTimestamp = new Date();
        String signature = getSignature("getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)");
        // Log method entry
        Helper.logEntrance(log, signature, new String[] {"projectCategoryId", "startDate"},
                new Object[] {projectCategoryId, startDate});
        try {
            Helper.checkPositive(projectCategoryId, "projectCategoryId");
            Helper.checkNull(startDate, "startDate");
            Helper.checkState(dbConnectionFactory == null, "This persistence was not properly configured.");
            Helper.checkState(connection == null, "The connection is not opened.");
            // check included project statuses state
            Helper.checkState(includedProjectStatuses == null, "The included project statuses are not set.");
            // populate sql with included project statuses values
            String sql = String.format(SQL_QUERY_USER_IDS, getPlaceholderList(includedProjectStatuses.size()));
            // Create a prepared statement for this SELECT query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            List < Long > result;
            int index = 1;
            try {
                // Set reliability calculation start date to the prepared statement
                preparedStatement.setTimestamp(index++, new Timestamp(startDate.getTime()));
                // Set project category ID to the prepared statement
                preparedStatement.setLong(index++, projectCategoryId);
                // populate statement with included project statuses ids
                populateStatementWithIncludedProjectStatuses(preparedStatement, index);
                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();
                // Create a list for result
                result = new ArrayList < Long >();
                while (resultSet.next()) {
                    // Add user ID to the result list
                    result.add(resultSet.getLong(1));
                }
            } finally {
                // Close the prepared statement
                closeStatement(signature, preparedStatement);
            }
            // Log method exit
            Helper.logExit(log, signature, new Object[] {result}, enterTimestamp);
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (IllegalStateException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
        } catch (SQLException e) {
            // Log exception
            throw Helper.logException(log, signature, new ReliabilityDataPersistenceException(
                    "A database access error occurred.", e),
                    "ReliabilityDataPersistenceException is thrown when retrieving the IDs of users.");
        }
    }

    /**
     * <p>
     * Populates given statement parameters from given index with included project statuses.
     * </p>
     * @param preparedStatement the prepared statement to be populated with included project statuses
     * @param index the parameter index
     * @throws SQLException if any error occurs while setting parameters
     */
    private void populateStatementWithIncludedProjectStatuses(PreparedStatement preparedStatement, int index)
        throws SQLException {
        for (long projectStatus : includedProjectStatuses) {
            preparedStatement.setLong(index++, projectStatus);
        }
    }

    /**
     * <p>
     * Retrieves information about all projects that can count toward reliability for some specific user. Elements in
     * the result list must be sorted by the resolution date chronologically.
     * </p>
     * @param projectCategoryId the ID of the project category.
     * @param userId the ID of the user.
     * @param startDate the start date when the reliability started counting in the specified project category
     *            (corresponds to submission phase start date).
     * @return the retrieved user participation data (not <code>null</code>, doesn't contain <code>null</code>).
     * @throws IllegalArgumentException if userId or projectCategoryId is not positive, or startDate is
     *             <code>null</code>.
     * @throws IllegalStateException if persistence connection is not opened; if this persistence was not properly
     *             configured; if includedProjectStatuses is not set properly
     * @throws ReliabilityDataPersistenceException if some error occurred when accessing the persistence.
     */
    public List < UserProjectParticipationData > getUserParticipationData(long userId, long projectCategoryId,
            Date startDate) throws ReliabilityDataPersistenceException {
        Date enterTimestamp = new Date();
        String signature =
                getSignature("getUserParticipationData(long userId, long projectCategoryId," + " Date startDate)");
        // Log method entry
        Helper.logEntrance(log, signature, new String[] {"userId", "projectCategoryId", "startDate"},
                new Object[] {userId, projectCategoryId, startDate});
        try {
            Helper.checkPositive(userId, "userId");
            Helper.checkPositive(projectCategoryId, "projectCategoryId");
            Helper.checkNull(startDate, "startDate");
            Helper.checkState(dbConnectionFactory == null, "This persistence was not properly configured.");
            Helper.checkState(connection == null, "The connection is not opened.");
            // check included project statuses state
            Helper.checkState(includedProjectStatuses == null, "The included project statuses are not set.");
            // populate sql with included project statuses values
            String sql =
                    String.format(SQL_QUERY_PARTICIPATION_DATA, getPlaceholderList(includedProjectStatuses.size()));
            // Create a prepared statement for this SELECT query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            List < UserProjectParticipationData > result;
            try {
                int columnIndex = 1;
                // Set project category ID to the prepared statement
                preparedStatement.setLong(columnIndex++, projectCategoryId);
                // Set reliability calculation start date to the prepared statement
                preparedStatement.setTimestamp(columnIndex++, new Timestamp(startDate.getTime()));
                // Set user ID to the prepared statement
                preparedStatement.setLong(columnIndex++, userId);
                // populate statement with included project statuses ids
                populateStatementWithIncludedProjectStatuses(preparedStatement, columnIndex);
                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();
                // Create a list for result
                result = new ArrayList < UserProjectParticipationData >();
                while (resultSet.next()) {
                    // Add user project participation data to the result list
                    result.add(getUserProjectParticipationData(userId, resultSet));
                }
            } finally {
                // Close the prepared statement
                closeStatement(signature, preparedStatement);
            }
            // Log method exit
            Helper.logExit(log, signature, new Object[] {result}, enterTimestamp);
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (IllegalStateException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
        } catch (SQLException e) {
            // Log exception
            throw Helper.logException(log, signature, new ReliabilityDataPersistenceException(
                    "A database access error occurred.", e),
                    "ReliabilityDataPersistenceException is thrown when retrieving the IDs of users.");
        }
    }

    /**
     * <p>
     * Saves the provided user reliability data in persistence. The provided list must contain information about all
     * projects that affected user's reliability.
     * </p>
     * @param projects the list with reliability details for each project that affected user's reliability.
     * @throws IllegalArgumentException if projects is <code>null</code>/empty or contains <code>null</code>, or for
     *             any element of projects: (resolutionDate is <code>null</code>) or any 2 elements in the list have
     *             different userId properties.
     * @throws IllegalStateException if persistence connection is not opened; if this persistence was not properly
     *             configured.
     * @throws ReliabilityDataPersistenceException if some error occurred when accessing the persistence.
     */
    public void saveUserReliabilityData(List < UserProjectReliabilityData > projects)
        throws ReliabilityDataPersistenceException {
        Date enterTimestamp = new Date();
        String signature = getSignature("saveUserReliabilityData(List<UserProjectReliabilityData> projects)");
        // Log method entry
        Helper.logEntrance(log, signature, new String[] {"projects"}, new Object[] {projects});
        try {
            // Check argument
            checkProjects(projects);
            Helper.checkState(dbConnectionFactory == null, "This persistence was not properly configured.");
            Helper.checkState(connection == null, "The connection is not opened.");
            try {
                // Create a prepared statement for this INSERT statement
                PreparedStatement insertStatement = connection.prepareStatement(SQL_INSERT_RELIABILITY_DATA);
                long userId = projects.get(0).getUserId();
                for (UserProjectReliabilityData project : projects) {
                    int columnIndex = 1;
                    // Set project ID to the prepared statement
                    insertStatement.setLong(columnIndex++, project.getProjectId());
                    // Set user ID to the prepared statement
                    insertStatement.setLong(columnIndex++, userId);
                    // Set resolution date to the prepared statement
                    insertStatement.setTimestamp(columnIndex++, new Timestamp(project.getResolutionDate().getTime()));
                    // Set reliability before resolution to the prepared statement
                    setDouble(insertStatement, columnIndex++, project.getReliabilityBeforeResolution());
                    // Set reliability after resolution to the prepared statement
                    insertStatement.setDouble(columnIndex++, project.getReliabilityAfterResolution());
                    // Set reliability on registration to the prepared statement
                    setDouble(insertStatement, columnIndex++, project.getReliabilityOnRegistration());
                    // Set reliable flag to the prepared statement
                    insertStatement.setInt(columnIndex, project.isReliable() ? 1 : 0);
                    // Add insert data to the batch:
                    insertStatement.addBatch();
                }
                // Execute the INSERT statement batch
                insertStatement.executeBatch();
                // Commit the changes
                connection.commit();
            } catch (SQLException e) {
                for (UserProjectReliabilityData project: projects) {
                    System.err.println(project.getProjectId());
                }
                // Roll back
                rollback(signature);
                throw new ReliabilityDataPersistenceException("A database access error occurred.", e);
            }
            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (IllegalStateException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
        } catch (ReliabilityDataPersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e,
                    "ReliabilityDataPersistenceException is thrown when updating the current user reliability.");
        }
    }

    /**
     * <p>
     * Updates the current user reliability for the specified project category.
     * </p>
     * @param projectCategoryId the ID of the project category.
     * @param userId the ID of the user.
     * @param reliability the new reliability.
     * @throws IllegalArgumentException if userId or projectCategoryId is not positive, or reliability is not in the
     *             range [0, 1].
     * @throws IllegalStateException if persistence connection is not opened; if this persistence was not properly
     *             configured.
     * @throws ReliabilityDataPersistenceException if some error occurred when accessing the persistence.
     */
    public void updateCurrentUserReliability(long userId, long projectCategoryId, double reliability)
        throws ReliabilityDataPersistenceException {
        Date enterTimestamp = new Date();
        String signature =
                getSignature("updateCurrentUserReliability(long userId, long projectCategoryId,"
                        + " double reliability)");
        // Log method entry
        Helper.logEntrance(log, signature, new String[] {"userId", "projectCategoryId", "reliability"},
                new Object[] {userId, projectCategoryId, reliability});
        try {
            Helper.checkPositive(userId, "userId");
            Helper.checkPositive(projectCategoryId, "projectCategoryId");
            if ((reliability < 0) || (reliability > 1)) {
                throw new IllegalArgumentException("'reliability' should be in the range [0, 1].");
            }
            Helper.checkState(dbConnectionFactory == null, "This persistence was not properly configured.");
            Helper.checkState(connection == null, "The connection is not opened.");
            Object[] parameters = new Object[] {reliability, userId, projectCategoryId + PROJECT_ID_ADJUST};
            try {
                // Execute the UPDATE statement
                int updatedNum = executeUpdate(signature, SQL_UPDATE_USER_RELIABILITY, parameters);
                if (updatedNum == 0) {
                    // Execute the INSERT statement
                    executeUpdate(signature, SQL_INSERT_USER_RELIABILITY, parameters);
                }
                // Commit the changes
                connection.commit();
            } catch (SQLException e) {
                // Roll back
                rollback(signature);
                throw new ReliabilityDataPersistenceException("A database access error occurred.", e);
            }
            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (IllegalStateException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
        } catch (ReliabilityDataPersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e,
                    "ReliabilityDataPersistenceException is thrown when updating the current user reliability.");
        }
    }

    /**
     * <p>
     * Closes the connection to persistence.
     * </p>
     * @throws IllegalStateException if connection is already closed; if this persistence was not properly configured.
     */
    public void close() {
        Date enterTimestamp = new Date();
        String signature = getSignature("close");
        // Log method entry
        Helper.logEntrance(log, signature, null, null);
        try {
            Helper.checkState(dbConnectionFactory == null, "This persistence was not properly configured.");
            Helper.checkState(connection == null, "The connection is already closed.");
            try {
                // Close the connection
                connection.close();
            } catch (SQLException e) {
                // Log exception
                Helper.logException(log, signature, e,
                        "A database access error occurred when closing the connection to persistence (will be ignored).");
                // Ignore
            }
            connection = null;
            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (IllegalStateException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
        }
    }

    /**
     * <p>
     * Reads a UserProjectParticipationData instance from the result set.
     * </p>
     * @param userId the user id.
     * @param resultSet the result set.
     * @return the UserProjectParticipationData instance.
     * @throws SQLException if any error occurs.
     */
    private static UserProjectParticipationData getUserProjectParticipationData(long userId, ResultSet resultSet)
        throws SQLException {
        // Create new user project participation data instance
        UserProjectParticipationData projectParticipationData = new UserProjectParticipationData();
        // Set user ID to the participation data:
        projectParticipationData.setUserId(userId);
        int columnIndex = 1;
        // Set project ID to the participation data
        projectParticipationData.setProjectId(resultSet.getLong(columnIndex++));
        // Set user registration date to the participation data
        projectParticipationData.setUserRegistrationDate(resultSet.getDate(columnIndex++));
        // Get submission status ID
        long submissionStatusId = resultSet.getLong(columnIndex++);
        // Set submission status ID to the participation data
        projectParticipationData.setSubmissionStatusId(resultSet.wasNull() ? null : submissionStatusId);
        // Set project start date to the participation data
        projectParticipationData.setProjectStartDate(resultSet.getDate(columnIndex++));
        // Set submission phase status ID to the participation data
        projectParticipationData.setSubmissionPhaseStatusId(resultSet.getLong(columnIndex++));
        // Set submission phase end time to the participation data
        projectParticipationData.setSubmissionPhaseEnd(resultSet.getDate(columnIndex++));
        // Set screening phase end time to the participation data
        projectParticipationData.setScreeningPhaseEnd(resultSet.getDate(columnIndex++));
        // Set appeals response phase end time to the participation data
        projectParticipationData.setAppealsResponsePhaseEnd(resultSet.getDate(columnIndex++));
        // Set passed review flag to the participation data
        projectParticipationData.setPassedReview(resultSet.getBoolean(columnIndex++));
        // Get final user score
        double userScore = resultSet.getDouble(columnIndex);
        // Set final user score to the participation data
        projectParticipationData.setUserScore(resultSet.wasNull() ? null : userScore);
        return projectParticipationData;
    }

    /**
     * <p>
     * Sets the double value.
     * </p>
     * @param ps the prepared statement.
     * @param index the index.
     * @param value the double value.
     * @throws SQLException if any error occurs.
     */
    private static void setDouble(PreparedStatement ps, int index, Double value) throws SQLException {
        if (value == null) {
            ps.setNull(index, Types.DOUBLE);
        } else {
            ps.setDouble(index, value);
        }
    }

    /**
     * <p>
     * Checks the projects argument.
     * </p>
     * @param projects the projects.
     * @throws IllegalArgumentException if projects is <code>null</code>/empty or contains <code>null</code>, or for
     *             any element of projects: (resolutionDate is <code>null</code>) or any 2 elements in the list have
     *             different userId properties.
     */
    private static void checkProjects(List < UserProjectReliabilityData > projects) {
        // Check argument
        Helper.checkNull(projects, "projects");
        if (projects.isEmpty()) {
            throw new IllegalArgumentException("'projects' should not be empty.");
        }
        int projectsSize = projects.size();
        long userId = -1;
        for (int i = 0; i < projectsSize; i++) {
            UserProjectReliabilityData project = projects.get(i);
            Helper.checkNull(project, "element of projects");
            Helper.checkNull(project.getResolutionDate(), "project.getResolutionDate()");
            if (i == 0) {
                // The first user id
                userId = project.getUserId();
            } else if (project.getUserId() != userId) {
                throw new IllegalArgumentException("'projects' should not have different userId.");
            }
        }
    }

    /**
     * <p>
     * Rolls back the current transaction.
     * </p>
     * @param signature the method signature.
     */
    private void rollback(String signature) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            // Log exception
            Helper.logException(log, signature, e,
                    "A database access error occurred when rolling back (will be ignored).");
            // Ignore
        }
    }

    /**
     * <p>
     * Executes the SQL statement.
     * </p>
     * @param signature the method signature.
     * @param sql the SQL string.
     * @param parameters the parameter values.
     * @return the affected row count.
     * @throws SQLException if a database access error occurs.
     */
    private int executeUpdate(String signature, String sql, Object[] parameters) throws SQLException {
        // Create a prepared statement for this statement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            for (int i = 0; i < parameters.length; i++) {
                // Set parameter to the prepared statement
                preparedStatement.setObject(i + 1, parameters[i]);
            }
            // Execute the statement
            return preparedStatement.executeUpdate();
        } finally {
            // Close the prepared statement
            closeStatement(signature, preparedStatement);
        }
    }

    /**
     * <p>
     * Closes the given Statement object.
     * </p>
     * @param signature the method signature.
     * @param statement the Statement object.
     */
    private void closeStatement(String signature, Statement statement) {
        if (statement != null) {
            try {
                // Close the statement
                statement.close();
            } catch (SQLException e) {
                // Log exception
                Helper.logException(log, signature, e,
                        "A database access error occurred when closing the statement (will be ignored).");
                // Ignore
            }
        }
    }

    /**
     * <p>
     * Constructs a placeholder list of SQL string parameters.
     * </p>
     * @param number the number of parameters.
     * @return a placeholder list.
     */
    private static String getPlaceholderList(int number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number; i++) {
            if (i != 0) {
                // Append a comma
                sb.append(Helper.COMMA);
            }
            sb.append(QUESTION_MARK);
        }
        return sb.toString();
    }

    /**
     * <p>
     * Gets the signature for given method for logging.
     * </p>
     * @param method the method name.
     * @return the signature for given method.
     */
    private static String getSignature(String method) {
        return CLASS_NAME + Helper.DOT + method;
    }

    /**
     * <p>
     * Sets project statuses that should be included in {@link #getIdsOfUsersWithReliability(long, Date)} and
     * {@link #getUserParticipationData(long, long, Date)} queries.
     * </p>
     * @param includedProjectStatuses the list of project statuses to be included in
     *            {@link #getIdsOfUsersWithReliability(long, Date)} and
     *            {@link #getUserParticipationData(long, long, Date)} queries.
     * @throws IllegalArgumentException if given list is null
     */
    public void setIncludedProjectStatuses(List < Long > includedProjectStatuses) {
        Date enterTimestamp = new Date();
        String signature = getSignature("setIncludedProjectStatuses(List<Long> includedProjectStatuses)");
        // Log method entry
        Helper.logEntrance(log, signature, new String[] {"includedProjectStatuses"},
                new Object[] {includedProjectStatuses});
        Helper.checkNull(includedProjectStatuses, "includedProjectStatuses");
        this.includedProjectStatuses = includedProjectStatuses;
        // Log method exit
        Helper.logExit(log, signature, null, enterTimestamp);
    }

    /**
     * <p>
     * Deletes all reliability data from persistence in given track.
     * </p>
     * @param projectCategoryId the track for which all reliability data should be removed
     * @throws IllegalArgumentException if projectCategoryId is not positive
     * @throws IllegalStateException if persistence connection is not opened; if this persistence was not properly
     *             configured (is not thrown by implementation that don't require any configuration parameters).
     * @throws ReliabilityDataPersistenceException if some error occurred when accessing the persistence.
     */
    public void deleteReliabilityData(long projectCategoryId) throws ReliabilityDataPersistenceException {
        Date enterTimestamp = new Date();
        String signature = getSignature("deleteReliabilityData(long projectCategoryId");
        // Log method entry
        Helper.logEntrance(log, signature, new String[] {"projectCategoryId"}, new Object[] {projectCategoryId});
        try {
            Helper.checkPositive(projectCategoryId, "projectCategoryId");
            Helper.checkState(dbConnectionFactory == null, "This persistence was not properly configured.");
            Helper.checkState(connection == null, "The connection is not opened.");
            Object[] parameters = new Object[] {projectCategoryId};
            try {
                // Execute the UPDATE statement
                executeUpdate(signature, SQL_DELETE_RELIABILITY_DATA, parameters);
                // Commit the changes
                connection.commit();
            } catch (SQLException e) {
                // Roll back
                rollback(signature);
                throw new ReliabilityDataPersistenceException("A database access error occurred.", e);
            }
            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (IllegalStateException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
        } catch (ReliabilityDataPersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e,
                    "ReliabilityDataPersistenceException is thrown when updating the current user reliability.");
        }
    }
}
