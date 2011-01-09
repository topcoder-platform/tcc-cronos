/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsConfigurationException;
import com.topcoder.management.resource.ReviewerStatisticsPersistenceException;
import com.topcoder.management.resource.SideBySideStatistics;
import com.topcoder.management.resource.StatisticsType;
import com.topcoder.management.resource.persistence.ReviewerStatisticsPersistence;
import com.topcoder.management.resource.persistence.logging.LogMessage;
import com.topcoder.management.resource.persistence.sql.Helper.DataType;
import com.topcoder.util.cache.Cache;
import com.topcoder.util.cache.CacheException;
import com.topcoder.util.cache.CacheInstantiationException;
import com.topcoder.util.cache.SimpleCache;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is the base class for informix reviewer statistics persistence implementations. It implements CRUD
 * operations on ReviewerStatistics entities. DBConnectionFactory is used to create database connections.
 * </p>
 *
 * <p>
 * This abstract class does not manage the connection itself. It contains three abstract methods which should be
 * implemented by concrete subclass to manage the connection:
 * <ul>
 * <li><code>openConnection()</code> is used to acquire and open the connection.</li>
 * <li><code>closeConnection()</code> is used to dispose the connection if the queries are executed successfully.</li>
 * <li><code>closeConnectionOnError()</code> is used to handle the error if the SQL operation fails.</li>
 * </ul>
 * The transaction handling logic should be implemented in subclasses while the <code>Statement</code>s and
 * <code>ResultSet</code>s are handled in this abstract class.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: this class is immutable an thread safe.
 * </p>
 *
 * @author moonli, pvmagacho
 * @version 1.4
 * @since 1.4
 */
public abstract class AbstractInformixReviewerStatisticsPersistence implements ReviewerStatisticsPersistence {
    /**
     * Represents the prefix of cache key for an array of history ReviewerStatistics entities. Value of the key is an
     * array of ReviewStatistics objects. E.g. if reviewer id is 22, the cache key would be "history_22";
     */
    public static final String HISTORY_PREFIX = "history_";

    /**
     * Represents the prefix of cache key for an array of average ReviewerStatistics entities. Value of key is an
     * array of ReviewStatistics objects. E.g. if review id is 32, cache key would be "average_32".
     */
    public static final String AVERAGE_PREFIX = "average_";

    /**
     * Represents the prefix of cache key for single ReviewerStatistics entity. Value of the key is a single
     * ReviewStatistics object. E.g. if id is 111, cache key would be "single_111".
     */
    public static final String SINGLE_PREFIX = "single_";

    /**
     * Represents the name of connection name parameter in configuration.
     */
    private static final String CONNECTION_NAME_PARAMETER = "ConnectionName";

    /**
     * Represents the name of connection factory namespace parameter in configuration.
     */
    private static final String CONNECTION_FACTORY_NAMESPACE_PARAMETER = "DBConnFactoryNamespace";

    /**
     * Represents the name of the simple cache in configuration.
     */
    private static final String CACHE_NAMESAPCE_PARAMETER = "CacheNamespace";

    /**
     * Represents the name of review statistics id sequence name parameter in configuration.
     */
    private static final String REVIEW_STATISTICS_ID_SEQUENCE_NAME_PARAMETER = "ReviewerStatisticsIDSequenceName";

    /**
     * Represents the sql to retrieve review statistics data from average persistence.
     */
    private static final String QUERY_SINGLE_REVIEW_STATISTICS_AVERAGE_SQL = "SELECT id, accuracy, coverage,"
        + " timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points,"
        + " project_id, competition_type_id , create_user, create_date, modify_user, modify_date from"
        + " average_review_statistics where id=?";

    /**
     * Represents the sql to retrieve review statistics data from history persistence.
     */
    private static final String QUERY_SINGLE_REVIEW_STATISTICS_HISTORY_SQL = "SELECT id, accuracy, coverage,"
        + " timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points,"
        + " project_id, competition_type_id , create_user, create_date, modify_user, modify_date from"
        + " history_statistics where id=?";

    /**
     * Represents the data types to retrieve review statistics data from average/history persistence.
     */
    private static final DataType[] QUERY_SINGLE_REVIEW_STATISTICS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE,
        Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.INTEGER_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE};

    /**
     * Represents the sql to retrieve review statistics data from average persistence.
     */
    private static final String QUERY_REVIEW_STATISTICS_AVERAGE_SQL = "SELECT id, accuracy, coverage,"
        + " timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points,"
        + " project_id, competition_type_id , create_user, create_date, modify_user, modify_date FROM"
        + " average_review_statistics WHERE reviewer_id=?";

    /**
     * Represents the sql to retrieve review statistics data from history persistence.
     */
    private static final String QUERY_REVIEW_STATISTICS_HISTORY_SQL = "SELECT id, accuracy, coverage,"
        + " timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points,"
        + " project_id, competition_type_id , create_user, create_date, modify_user, modify_date FROM"
        + " history_statistics WHERE reviewer_id=?";

    /**
     * Represents the sql to retrieve review statistics data from average persistence with reviewer id and competition
     * type id.
     */
    private static final String QUERY_REVIEW_STATISTICS_COMPETITION_DATE_SQL = "SELECT id, accuracy, coverage,"
        + " timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points,"
        + " project_id, competition_type_id , create_user, create_date, modify_user, modify_date FROM"
        + " average_review_statistics WHERE reviewer_id=? AND competition_type_id=?";

    /**
     * Represents the data types to retrieve review statistics data from average/history persistence.
     */
    private static final DataType[] QUERY_REVIEW_STATISTICS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE,
        Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.INTEGER_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE};

    /**
     * Represents the sql statement to populate side by side statistics.
     */
    private static final String QUERY_REVIEW_STATISTICS_SIDEBYSIDE_SQL = "SELECT a.id, a.accuracy, a.coverage,"
        + " a.timeline_reliability, a.total_evaluation_coefficient, a.reviewer_id, a.eligibility_points,"
        + " a.project_id, a.competition_type_id , a.create_user, a.create_date, a.modify_user, a.modify_date,"
        + " b.id, b.accuracy, b.coverage,"
        + " b.timeline_reliability, b.total_evaluation_coefficient, b.reviewer_id, b.eligibility_points,"
        + " b.project_id, b.competition_type_id , b.create_user, b.create_date, b.modify_user, b.modify_date FROM"
        + " history_statistics a, history_statistics b WHERE a.competition_type_id=?"
        + " AND a.project_id=b.project_id AND a.reviewer_id=? AND b.reviewer_id=?";

    /**
     * Represents the data types to retrieve review statistics data to side by side statistics.
     */
    private static final DataType[] QUERY_REVIEW_STATISTICS_SIDEBYSIDE_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE,
        Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.INTEGER_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE,
        Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE,
        Helper.INTEGER_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE};

    /**
     * Represents the sql statement to retrieve reviewer id for average review statistics.
     */
    private static final String QUERY_REVIEWER_ID_AVERAGE_SQL = "SELECT distinct reviewer_id FROM "
        + "average_review_statistics WHERE id=?";

    /**
     * Represents the sql statement to retrieve reviewer id for history review statistics.
     */
    private static final String QUERY_REVIEWER_ID_HISTORY_SQL = "SELECT reviewer_id FROM history_statistics WHERE id=?";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query
     * reviewer id for review statistics.
     */
    private static final DataType QUERY_REVIEWER_ID_COLUMN_TYPES = Helper.LONG_TYPE;

    /**
     * Represents the sql to insert review statistics data into average persistence.
     */
    private static final String CREATE_REVIEW_STATISTICS_AVERAGE_SQL = "INSERT INTO average_review_statistics (id,"
        + " accuracy, coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points,"
        + " project_id, competition_type_id , create_user, create_date, modify_user, modify_date)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Represents the sql to insert review statistics data into history persistence.
     */
    private static final String CREATE_REVIEW_STATISTICS_HISTORY_SQL = "INSERT INTO history_statistics (id, accuracy,"
        + " coverage, timeline_reliability, total_evaluation_coefficient, reviewer_id, eligibility_points, project_id,"
        + " competition_type_id , create_user, create_date, modify_user, modify_date)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Represents the sql to update review statistics data into average persistence.
     */
    private static final String UPDATE_REVIEW_STATISTICS_AVERAGE_SQL = "update average_review_statistics set "
        + " accuracy=?, coverage=?,timeline_reliability=?,total_evaluation_coefficient=?,reviewer_id=?,"
        + " eligibility_points=?,project_id=?,competition_type_id=?,create_user=?,create_date=?,modify_user=?,"
        + " modify_date=? where id=?";

    /**
     * Represents the sql to update review statistics data into history persistence.
     */
    private static final String UPDATE_REVIEW_STATISTICS_HISTORY_SQL = "update history_statistics set "
        + " accuracy=?, coverage=?,timeline_reliability=?,total_evaluation_coefficient=?,reviewer_id=?,"
        + " eligibility_points=?,project_id=?,competition_type_id=?,create_user=?,create_date=?,modify_user=?,"
        + " modify_date=? where id=?";

    /**
     * Represents the sql to delete review statistics data from average persistence.
     */
    private static final String DELETE_REVIEW_STATISTICS_AVERAGE_SQL = "delete from average_review_statistics where"
        + " id=?";

    /**
     * Represents the sql to delete review statistics data from history persistence.
     */
    private static final String DELETE_REVIEW_STATISTICS_HISTORY_SQL = "delete from history_statistics where id=?";

    /**
     * Represents the number of columns retrieve from database for reviewer statistics data.
     */
    private static final int COLUMN_SIZE = 13;

    /**
     * Represents the database connection name, it is used with DBConnectionFactory to create database connections.
     *
     * <p>
     * It's initialized in constructor, and won't change afterwards, can be null indicating default connection will be
     * used. cannot be empty, it has a getter getConnectionName.
     * </p>
     */
    private final String connectionName;

    /**
     * Represents the DBConnectionFactory instance used to create database connections.
     *
     * <p>
     * It's initialized in constructor, and won't change afterwards. Cannot be null. It has a getter
     * getConnectionFactory.
     * </p>
     */
    private final DBConnectionFactory conFactory;

    /**
     * Represents the cache to store results temporarily for "retrieval" actions. It helps reduce the requests to
     * underlying database, making it more efficient. Cache will hold 4 sorts of data. - A single ReviewerStatistics
     * with key=SINGLE_PREFIX+id - An array of ReviewerStatistics with key=HISTORY_PREFIX+reviewerId - An array of
     * ReviewerStatistics with key=AVERAGE_PREFIX+reviewerId
     *
     * <p>
     * It's initialized in constructor to non-null Cache object, won't change afterwards. It cannot be null. It's used
     * by all public methods of this class.
     * </p>
     */
    private final Cache cache;

    /**
     * Represents the ID generator for ReviewerStatistics entity.
     *
     * <p>
     * It's initialized in constructor to non-null, won't change afterwards. It's used in create method.
     * </p>
     */
    private IDGenerator reviewerStatisticsIDGenerator;

    /**
     * Creates an instance of this class with specified configuration.
     *
     * <p>
     * The following parameters are configured.
     * <ul>
     * <li>DBConnFactoryNamespace: The namespace that contains settings for DB Connection Factory. It is required.</li>
     * <li>ConnectionName: The name of the connection that will be used by DBConnectionFactory to create connection.
     * If missing, default connection will be created. It is required.</li>
     * <li>ReviewerStatisticsIDSequenceName: The sequence name used to create Id generator for project Id. It is
     * required.</li>
     * <li>CacheNamespace: The namespace for the Simple Cache object. It is required.</li>
     * </ul>
     * </p>
     * <p>
     *
     * <pre>
     * Sampĺe configuration:
     * &lt;!-- Namespace for InformixReviewerStatisticsPersistence class --&gt;
     * &lt;Config name="com.topcoder.management.resource.persistence.sql.InformixReviewerStatisticsPersistence"&gt;
     *   &lt;Property name="DBConnFactoryNamespace"&gt;
     *    &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;!-- the connection name to retrieve connection in DB Connection Factory, required --&gt;
     *   &lt;Property name="ConnectionName"&gt;
     *    &lt;Value&gt;dbconnection&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;Property name="ReviewerStatisticsIDSequenceName"&gt;
     *    &lt;Value&gt;review_statistics_id_seq&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;Property name="CacheNamespace"&gt;
     *    &lt;Value&gt;simpleCache&lt;/Value&gt;
     *   &lt;/Property&gt;
     * &lt;/Config&gt;
     * </pre>
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if the input is null or empty string
     * @throws ReviewerStatisticsConfigurationException if any error occurred during configuration
     */
    @SuppressWarnings("deprecation")
    protected AbstractInformixReviewerStatisticsPersistence(String namespace) {
        Helper.assertObjectNotNull(namespace, "namespace");

        // Get the instance of ConfigManager
        ConfigManager cm = ConfigManager.getInstance();

        // Get db connection factory namespace
        String factoryNamespace = getConfigurationParameterValue(cm, namespace,
            CONNECTION_FACTORY_NAMESPACE_PARAMETER, true, getLogger());

        // Try to create a DBConnectionFactoryImpl instance from factoryNamespace
        try {
            conFactory = new DBConnectionFactoryImpl(factoryNamespace);
        } catch (UnknownConnectionException e) {
            throw new ReviewerStatisticsConfigurationException(
                "Unable to create a new instance of DBConnectionFactoryImpl class" + " from namespace ["
                    + factoryNamespace + "].", e);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            throw new ReviewerStatisticsConfigurationException(
                "Unable to create a new instance of DBConnectionFactoryImpl class" + " from namespace ["
                    + factoryNamespace + "].", e);
        }

        // Get the connection name
        connectionName = getConfigurationParameterValue(cm, namespace, CONNECTION_NAME_PARAMETER, true, getLogger());

        // Try to get review statistics id sequence name
        String reviewStatisticsIDSequenceName = getConfigurationParameterValue(cm, namespace,
            REVIEW_STATISTICS_ID_SEQUENCE_NAME_PARAMETER, true, getLogger());

        // Try to get the IDGenerator
        try {
            reviewerStatisticsIDGenerator = IDGeneratorFactory.getIDGenerator(reviewStatisticsIDSequenceName);
        } catch (IDGenerationException e) {
            getLogger().log(Level.ERROR,
                "The reviewApplicationIDGenerator [" + reviewStatisticsIDSequenceName + "] is invalid.");
            throw new ReviewerStatisticsConfigurationException("Unable to create IDGenerator for '"
                + reviewStatisticsIDSequenceName + "'.", e);
        }

        // Try to get cache namespace parameter
        String cacheNamespace = getConfigurationParameterValue(cm, namespace, CACHE_NAMESAPCE_PARAMETER, true,
            getLogger());

        // Create cache instance
        try {
            this.cache = new SimpleCache(cacheNamespace);
        } catch (CacheInstantiationException e) {
            throw createReviewStatisticsConfigurationException(e, cacheNamespace);
        } catch (IllegalArgumentException e) {
            throw createReviewStatisticsConfigurationException(e, cacheNamespace);
        }
    }

    /**
     * Creates a new instance in persistence. Returns the persisted object or null.
     *
     * <p>
     * It handles both HISTORY and AVERAGE statistics.
     * </p>
     *
     * @param reviewerStatistics the reviewer statistics to create
     * @return the created ReviewerStatistics object, with id assigned
     * @throws IllegalArgumentException if argument is null
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics create(ReviewerStatistics reviewerStatistics)
        throws ReviewerStatisticsPersistenceException {
        Helper.assertObjectNotNull(reviewerStatistics, "reviewerStatistics");

        Connection conn = null;

        // newId will contain the new generated Id for the project
        Long newId;

        getLogger().log(Level.INFO,
            new LogMessage(null, null, "creating new reviewer statistics: " + reviewerStatistics.getReviewerId()));

        try {
            // create the connection
            conn = openConnection();

            try {
                // generate id for the project
                newId = new Long(reviewerStatisticsIDGenerator.getNextID());
                getLogger().log(Level.INFO, new LogMessage(newId, null, "generate id for new review statistics"));
            } catch (IDGenerationException e) {
                throw new ReviewerStatisticsPersistenceException("Unable to generate id for the review statistics.",
                    e);
            }

            // insert the average_review_statistics into database
            Object[] queryArgs = new Object[] {newId, reviewerStatistics.getAccuracy(),
                reviewerStatistics.getCoverage(), reviewerStatistics.getTimelineReliability(),
                reviewerStatistics.getTotalEvaluationCoefficient(), reviewerStatistics.getReviewerId(),
                reviewerStatistics.getEligibilityPoints(), reviewerStatistics.getProjectId(),
                reviewerStatistics.getCompetitionTypeId(), reviewerStatistics.getCreationUser(),
                new Timestamp(reviewerStatistics.getCreationTimestamp().getTime()),
                reviewerStatistics.getModificationUser(),
                new Timestamp(reviewerStatistics.getModificationTimestamp().getTime())};

            if (reviewerStatistics.getStatisticsType() == StatisticsType.AVERAGE) {
                Helper.doDMLQuery(conn, CREATE_REVIEW_STATISTICS_AVERAGE_SQL, queryArgs);
                cache.remove(AVERAGE_PREFIX + reviewerStatistics.getReviewerId());
            } else {
                Helper.doDMLQuery(conn, CREATE_REVIEW_STATISTICS_HISTORY_SQL, queryArgs);
                cache.remove(HISTORY_PREFIX + reviewerStatistics.getReviewerId());
            }

            // set the newId when no exception occurred
            reviewerStatistics.setId(newId);
            cache.put(SINGLE_PREFIX + reviewerStatistics.getId(), reviewerStatistics);

            closeConnection(conn);

            return reviewerStatistics;
        } catch (CacheException e) {
            ReviewerStatisticsPersistenceException er = new ReviewerStatisticsPersistenceException(
                "Unable to insert data into cache.", e);
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to create reviewer statistics  in cache, "
                    + reviewerStatistics.getProjectId(), er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        } catch (ReviewerStatisticsPersistenceException e) {
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to create reviewer statistics, "
                    + reviewerStatistics.getProjectId(), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Updates an existing ReviewerStatistic object.
     *
     * <p>
     * It handles both HISTORY and AVERAGE statistics.
     * </p>
     *
     * @param reviewerStatistics the reviewer statistics entity to update
     * @return the updated reviewer statistics entity
     * @throws IllegalArgumentException if argument is null
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics update(ReviewerStatistics reviewerStatistics)
        throws ReviewerStatisticsPersistenceException {
        Helper.assertObjectNotNull(reviewerStatistics, "reviewerStatistics");

        Connection conn = null;

        getLogger().log(Level.INFO,
            new LogMessage(null, null, "creating new reviewer statistics: " + reviewerStatistics.getReviewerId()));

        try {
            // create the connection
            conn = openConnection();

            // insert the average_review_statistics into database
            Object[] queryArgs = new Object[] {reviewerStatistics.getAccuracy(), reviewerStatistics.getCoverage(),
                reviewerStatistics.getTimelineReliability(), reviewerStatistics.getTotalEvaluationCoefficient(),
                reviewerStatistics.getReviewerId(), reviewerStatistics.getEligibilityPoints(),
                reviewerStatistics.getProjectId(), reviewerStatistics.getCompetitionTypeId(),
                reviewerStatistics.getCreationUser(),
                new Timestamp(reviewerStatistics.getCreationTimestamp().getTime()),
                reviewerStatistics.getModificationUser(),
                new Timestamp(reviewerStatistics.getModificationTimestamp().getTime()), reviewerStatistics.getId()};

            // check whether the review statistics id is already in the database
            if (!Helper.checkEntityExists(
                (reviewerStatistics.getStatisticsType() == StatisticsType.AVERAGE) ? "average_review_statistics"
                    : "history_statistics", "id", reviewerStatistics.getId(), conn)) {
                throw new ReviewerStatisticsPersistenceException("The review statistics with the id ["
                    + reviewerStatistics.getId() + "] doesn't exists.");
            }

            if (reviewerStatistics.getStatisticsType() == StatisticsType.AVERAGE) {
                Helper.doDMLQuery(conn, UPDATE_REVIEW_STATISTICS_AVERAGE_SQL, queryArgs);
                cache.remove(AVERAGE_PREFIX + reviewerStatistics.getReviewerId());
            } else {
                Helper.doDMLQuery(conn, UPDATE_REVIEW_STATISTICS_HISTORY_SQL, queryArgs);
                cache.remove(HISTORY_PREFIX + reviewerStatistics.getReviewerId());
            }

            // set the newId when no exception occurred
            cache.put(SINGLE_PREFIX + reviewerStatistics.getId(), reviewerStatistics);

            closeConnection(conn);

            return reviewerStatistics;
        } catch (CacheException e) {
            ReviewerStatisticsPersistenceException er = new ReviewerStatisticsPersistenceException(
                "Unable to insert data into cache.", e);
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to create reviewer statistics  in cache, "
                    + reviewerStatistics.getProjectId(), er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        } catch (ReviewerStatisticsPersistenceException e) {
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to create reviewer statistics, "
                    + reviewerStatistics.getProjectId(), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Retrieve an ReviewerStatistics entity by ID.
     *
     * <p>
     * It handles both HISTORY and AVERAGE statistics.
     * </p>
     *
     * @param id id of the ReviewerStatistics entity to retrieve
     * @return the retrieved ReviewerStatistics
     * @throws IllegalArgumentException if argument is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics retrieve(long id) throws ReviewerStatisticsPersistenceException {
        Helper.assertLongPositive(id, "id");

        getLogger().log(Level.INFO, "get reviewer statistics with the id: " + id);

        Connection conn = null;
        try {
            // Check if review statistics exists in cache
            ReviewerStatistics reviewerStatistics = (ReviewerStatistics) cache.get(SINGLE_PREFIX + id);
            if (reviewerStatistics != null) {
                return reviewerStatistics;
            }

            // create the connection
            conn = openConnection();

            // Execute query
            Object[][] rows = Helper.doQuery(conn, QUERY_SINGLE_REVIEW_STATISTICS_AVERAGE_SQL, new Object[] {id},
                QUERY_SINGLE_REVIEW_STATISTICS_COLUMN_TYPES);

            if (rows.length == 0) {
                rows = Helper.doQuery(conn, QUERY_SINGLE_REVIEW_STATISTICS_HISTORY_SQL, new Object[] {id},
                    QUERY_SINGLE_REVIEW_STATISTICS_COLUMN_TYPES);
            }
            Object[] result = (rows.length == 0) ? null : rows[0];

            closeConnection(conn);

            // Populate reviewer statistics with sql result and add it to cache
            reviewerStatistics = createReviewStatistics(result);

            cache.put(SINGLE_PREFIX + id, reviewerStatistics);

            return reviewerStatistics;
        } catch (CacheException e) {
            ReviewerStatisticsPersistenceException er = new ReviewerStatisticsPersistenceException(
                "Unable to retrieve data from cache.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve reviewer statistics with id: " + id, er));
            throw er;
        } catch (ReviewerStatisticsPersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve reviewer statistics with id: " + id, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Deletes an existing ReviewerStatistics entity.
     *
     * <p>
     * It handles both HISTORY and AVERAGE statistics.
     * </p>
     *
     * @param id id of the ReviewerStatistics entity to delete
     * @return true if entity is deleted, false is entity is missing
     * @throws IllegalArgumentException if argument is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public boolean delete(long id) throws ReviewerStatisticsPersistenceException {
        Helper.assertLongPositive(id, "id");

        getLogger().log(Level.INFO, "delete reviewer statistics with the id: " + id);

        Connection conn = null;
        boolean result = false;
        try {
            // create the connection
            conn = openConnection();

            // check whether the review statistics id is already in the average_review_statistics table
            if (Helper.checkEntityExists("average_review_statistics", "id", id, conn)) {
                Long reviewerId = (Long) Helper.doSingleValueQuery(conn, QUERY_REVIEWER_ID_AVERAGE_SQL,
                    new Object[] {id}, QUERY_REVIEWER_ID_COLUMN_TYPES);

                // Execute query
                Helper.doDMLQuery(conn, DELETE_REVIEW_STATISTICS_AVERAGE_SQL, new Object[] {id});
                cache.remove(AVERAGE_PREFIX + reviewerId);
                result = true;
            }

            // check whether the review statistics id is already in the history_statistics table (if not found in
            // average_review_statistics)
            if (!result && Helper.checkEntityExists("history_statistics", "id", id, conn)) {
                Long reviewerId = (Long) Helper.doSingleValueQuery(conn, QUERY_REVIEWER_ID_HISTORY_SQL,
                    new Object[] {id}, QUERY_REVIEWER_ID_COLUMN_TYPES);

                // Execute query
                Helper.doDMLQuery(conn, DELETE_REVIEW_STATISTICS_HISTORY_SQL, new Object[] {id});
                cache.remove(HISTORY_PREFIX + reviewerId);
                result = true;
            }

            closeConnection(conn);
        } catch (CacheException e) {
            ReviewerStatisticsPersistenceException er = new ReviewerStatisticsPersistenceException(
                "Unable to retrieve data from cache.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve reviewer statistics with id: " + id, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        } catch (ReviewerStatisticsPersistenceException e) {
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve reviewer statistics with id: " + id, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        return result;
    }

    /**
     * Retrieves an array of ReviewStatistics object; the size of the array will be equal to the number of review
     * boards that the reviewer issuer is member of.
     *
     * <p>
     * It returns only HISTORY statistics.
     * </p>
     *
     * @param reviewerId id of the reviewer
     * @return an array of ReviewerStatistics entities, may be empty
     * @throws IllegalArgumentException if argument is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics[] getReviewerStatistics(long reviewerId) throws ReviewerStatisticsPersistenceException {
        Helper.assertLongPositive(reviewerId, "reviewerId");

        getLogger().log(Level.INFO, "get reviewer statistics with the reviewerId: " + reviewerId);

        Connection conn = null;
        try {
            // Check if review statistics exists in cache
            ReviewerStatistics[] reviewerStatistics = (ReviewerStatistics[]) cache.get(HISTORY_PREFIX + reviewerId);
            if (reviewerStatistics != null) {
                return reviewerStatistics;
            }

            // create the connection
            conn = openConnection();

            // Execute query
            Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_STATISTICS_HISTORY_SQL, new Object[] {reviewerId},
                QUERY_REVIEW_STATISTICS_COLUMN_TYPES);

            closeConnection(conn);

            reviewerStatistics = new ReviewerStatistics[rows.length];
            for (int i = 0; i < rows.length; i++) {
                Object[] result = rows[i];
                reviewerStatistics[i] = createReviewStatistics(result);
            }

            if (rows.length > 0) {
                cache.put(HISTORY_PREFIX + reviewerId, reviewerStatistics);
            }

            return reviewerStatistics;
        } catch (CacheException e) {
            ReviewerStatisticsPersistenceException er = new ReviewerStatisticsPersistenceException(
                "Unable to retrieve data from cache.", e);
            getLogger()
                .log(
                    Level.ERROR,
                    new LogMessage(null, null,
                        "Fails to retrieve reviewer statistics with reviewerId: " + reviewerId, er));
            throw er;
        } catch (ReviewerStatisticsPersistenceException e) {
            getLogger()
                .log(
                    Level.ERROR,
                    new LogMessage(null, null,
                        "Fails to retrieve reviewer statistics with reviewerId: " + reviewerId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets the AVERAGES statistics for specified reviewer.
     *
     * @param reviewerId id of the reviewer
     * @return the ReviewerStatistics for specified reviewer, or empty array if not exist
     * @throws IllegalArgumentException if argument is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics[] getReviewerAverageStatistics(long reviewerId)
        throws ReviewerStatisticsPersistenceException {
        Helper.assertLongPositive(reviewerId, "reviewerId");

        getLogger().log(Level.INFO, "get reviewer statistics with the reviewerId: " + reviewerId);

        Connection conn = null;
        try {
            // Check if review statistics exists in cache
            ReviewerStatistics[] reviewerStatistics = (ReviewerStatistics[]) cache.get(AVERAGE_PREFIX + reviewerId);
            if (reviewerStatistics != null) {
                return reviewerStatistics;
            }

            // create the connection
            conn = openConnection();

            // Execute query
            Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_STATISTICS_AVERAGE_SQL, new Object[] {reviewerId},
                QUERY_REVIEW_STATISTICS_COLUMN_TYPES);

            closeConnection(conn);

            reviewerStatistics = new ReviewerStatistics[rows.length];
            for (int i = 0; i < rows.length; i++) {
                Object[] result = rows[i];
                reviewerStatistics[i] = createReviewStatistics(result);
            }

            if (rows.length > 0) {
                cache.put(AVERAGE_PREFIX + reviewerId, reviewerStatistics);
            }

            return reviewerStatistics;
        } catch (CacheException e) {
            ReviewerStatisticsPersistenceException er = new ReviewerStatisticsPersistenceException(
                "Unable to retrieve data from cache.", e);
            getLogger()
                .log(
                    Level.ERROR,
                    new LogMessage(null, null,
                        "Fails to retrieve reviewer statistics with reviewerId: " + reviewerId, er));
            throw er;
        } catch (ReviewerStatisticsPersistenceException e) {
            getLogger()
                .log(
                    Level.ERROR,
                    new LogMessage(null, null,
                        "Fails to retrieve reviewer statistics with reviewerId: " + reviewerId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Retrieves a specific ReviewStatistics for a reviewer and competition type.
     *
     * <p>
     * It returns only AVERAGE statistics.
     * </p>
     *
     * @param reviewerId id of the reviewer
     * @param competitionTypeId id of the competition type
     * @return the ReviewerStatistics entity
     * @throws IllegalArgumentException if any of the arguments is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public ReviewerStatistics getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId)
        throws ReviewerStatisticsPersistenceException {
        Helper.assertLongPositive(reviewerId, "reviewerId");
        Helper.assertLongPositive(competitionTypeId, "competitionTypeId");

        getLogger().log(
            Level.INFO,
            "get reviewer statistics with the reviewerId: " + reviewerId + " and competitionTypeId: "
                + competitionTypeId);

        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // Execute query
            Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_STATISTICS_COMPETITION_DATE_SQL, new Object[] {
                reviewerId, competitionTypeId}, QUERY_REVIEW_STATISTICS_COLUMN_TYPES);

            // close the connection
            closeConnection(conn);

            // parse the results
            Object[] result = (rows.length == 0) ? null : rows[0];
            ReviewerStatistics reviewerStatistics = createReviewStatistics(result);

            return reviewerStatistics;
        } catch (ReviewerStatisticsPersistenceException e) {
            getLogger()
                .log(
                    Level.ERROR,
                    new LogMessage(null, null,
                        "Fails to retrieve reviewer statistics with reviewerId: " + reviewerId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets all statistics for projects in which the two reviewers competed against one another (had secondary
     * reviewer role).
     *
     * <p>
     * It returns only HISTORY statistics.
     * </p>
     *
     * @param firstReviewerId id of the first reviewer
     * @param secondReviewerId id of the second reviewer
     * @param competitionTypeId id of the competition type
     * @return an array of ReviewerStatistics entities
     * @throws IllegalArgumentException if any of the arguments is negative
     * @throws ReviewerStatisticsPersistenceException if any error occurred during operation
     */
    public SideBySideStatistics getSideBySideStatistics(long firstReviewerId, long secondReviewerId,
        int competitionTypeId) throws ReviewerStatisticsPersistenceException {
        Helper.assertLongPositive(firstReviewerId, "firstReviewerId");
        Helper.assertLongPositive(secondReviewerId, "secondReviewerId");
        Helper.assertLongPositive(competitionTypeId, "competitionTypeId");

        getLogger().log(
            Level.INFO,
            "get reviewer statistics with the firstReviewerId: " + firstReviewerId + ", secondReviewerId: "
                + secondReviewerId + " and competitionTypeId: " + competitionTypeId);

        Connection conn = null;
        try {
            SideBySideStatistics sideBySideStatistics = new SideBySideStatistics();

            // create the connection
            conn = openConnection();

            // Execute query
            Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_STATISTICS_SIDEBYSIDE_SQL, new Object[] {
                firstReviewerId, secondReviewerId, competitionTypeId}, QUERY_REVIEW_STATISTICS_SIDEBYSIDE_TYPES);

            // close the connection
            closeConnection(conn);

            List<ReviewerStatistics> firstReviewerStatistics = new ArrayList<ReviewerStatistics>();
            List<ReviewerStatistics> secondReviewerStatistics = new ArrayList<ReviewerStatistics>();
            for (int i = 0; i < rows.length; i++) {
                int columnSize = rows[i].length / 2;
                // parse the results for firstReviewerId
                Object[] result = new Object[columnSize];
                for (int j = 0; j < columnSize; j++) {
                    result[j] = rows[i][j];
                }
                firstReviewerStatistics.add(createReviewStatistics(result));

                // parse the results for secondReviewerId
                for (int j = columnSize; j < 2 * columnSize; j++) {
                    result[j - columnSize] = rows[i][j];
                }
                secondReviewerStatistics.add(createReviewStatistics(result));
            }

            sideBySideStatistics.setSecondReviewerStatistics(firstReviewerStatistics);
            sideBySideStatistics.setFirstReviewerStatistics(secondReviewerStatistics);

            return sideBySideStatistics;
        } catch (ReviewerStatisticsPersistenceException e) {
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve reviewer statistics with firstReviewerId: "
                    + firstReviewerId + ", secondReviewerId: " + secondReviewerId, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets the connection name.
     *
     * @return the name of the connection
     */
    protected String getConnectionName() {
        return connectionName;
    }

    /**
     * Gets the DB connection factory.
     *
     *
     * @return the DB connection factory
     */
    protected DBConnectionFactory getConnectionFactory() {
        return this.conFactory;
    }

    /**
     * <p>
     * Return the getLogger().
     * </p>
     *
     * @return the <code>Log</code> instance used to take the log message
     */
    protected abstract Log getLogger();

    /**
     * <p>
     * Abstract method used to open a database connection.
     * </p>
     *
     * <p>
     * The implementations in subclasses will get a connection and properly prepare it for use, depending on the
     * transaction management strategy used in the subclass.
     * </p>
     *
     * @return the opened database connection
     * @throws ReviewerStatisticsPersistenceException if any error occurred
     */
    protected abstract Connection openConnection() throws ReviewerStatisticsPersistenceException;

    /**
     * <p>
     * Abstract method to close given database connection when no error occurred.
     * </p>
     *
     * <p>
     * The implementations in subclasses will close the given connection. Depending on the transaction management
     * strategy used in the subclass, a transaction on the connection may be committed.
     * </p>
     *
     * @param connection the connection to close
     * @throws IllegalArgumentException if connection is null
     * @throws ReviewerStatisticsPersistenceException if any error occurred
     */
    protected abstract void closeConnection(Connection connection) throws ReviewerStatisticsPersistenceException;

    /**
     * <p>
     * Abstract method to close given database connection when an error has occurred. It might include the whole
     * transaction, depending on implementation.
     * </p>
     *
     * <p>
     * The implementations in subclasses will close the given connection. Depending on the transaction management
     * strategy used in the subclass, a transaction on the connection may be rolled back.
     * </p>
     *
     * @param connection the connection to close
     * @throws IllegalArgumentException if connection is null
     * @throws ReviewerStatisticsPersistenceException if any error occurred
     */
    protected abstract void closeConnectionOnError(Connection connection)
        throws ReviewerStatisticsPersistenceException;

    /**
     * <p>
     * Gets the parameter value from configuration.
     * </p>
     *
     * @param cm the ConfigManager instance
     * @param namespace configuration namespace
     * @param name the parameter name
     * @param required true, if the parameter is require; false, if the parameter is optional
     * @param logger the Log instance
     * @return A String that represents the parameter value
     * @throws IllegalArgumentException if any parameter is null, or namespace or name is empty (trimmed)
     * @throws ReviewerStatisticsConfigurationException if the namespace does not exist, or the value is not specified
     *             when required is true, or the value is empty (trimmed) when not null.
     */
    private static String getConfigurationParameterValue(ConfigManager cm, String namespace, String name,
        boolean required, Log logger) {
        Helper.assertObjectNotNull(cm, "cm");
        Helper.assertStringNotNullNorEmpty(namespace, "namespace");
        Helper.assertStringNotNullNorEmpty(name, "name");

        String value;

        try {
            value = cm.getString(namespace, name);
        } catch (UnknownNamespaceException e) {
            logger.log(Level.FATAL, "Configuration namespace [" + namespace + "] does not exist.");
            throw new ReviewerStatisticsConfigurationException("Configuration namespace [" + namespace
                + "] does not exist.", e);
        }

        if (value == null) {
            if (required) {
                logger.log(Level.FATAL, "Configuration parameter [" + name + "] under namespace [" + namespace
                    + "] is not specified.");
                throw new ReviewerStatisticsConfigurationException("Configuration parameter [" + name
                    + "] under namespace [" + namespace + "] is not specified.");
            }
        } else if (value.trim().length() == 0) {
            logger.log(Level.FATAL, "Configuration parameter [" + name + "] under namespace [" + namespace
                + "] is empty (trimmed).");
            throw new ReviewerStatisticsConfigurationException("Configuration parameter [" + name
                + "] under namespace [" + namespace + "] is empty (trimmed).");
        }

        logger.log(Level.INFO, "Read propery[" + name + "] which is " + (required ? " required " : " optional ")
            + " with value[" + value + "] from namespace [" + namespace + "].");

        return value;
    }

    /**
     * Wrap ReviewerStatisticsConfigurationException around Exception thrown by the SimpleCache class.
     *
     * @param e the Exception to be wrapped
     * @param cacheNamespace the cache namespace used for SimpleCache configuration
     *
     * @return ReviewerStatisticsConfigurationException with wrapped exception
     */
    private ReviewerStatisticsConfigurationException createReviewStatisticsConfigurationException(Throwable e,
        String cacheNamespace) {
        getLogger().log(Level.ERROR, "Unable to create cache with namespace " + cacheNamespace + ".");
        return new ReviewerStatisticsConfigurationException("Unable to create cache with namespace " + cacheNamespace
            + ".", e);
    }

    /**
     * Create review statistics object from sql results.
     *
     * @param result the sql result object
     * @return the create
     * @throws ReviewerStatisticsPersistenceException if data retrieve has incorrect length
     */
    private ReviewerStatistics createReviewStatistics(Object[] result) throws ReviewerStatisticsPersistenceException {
        if (result == null) {
            return null;
        }

        if (result.length != COLUMN_SIZE) {
            throw new ReviewerStatisticsPersistenceException("Retrieved data has invalid length.");
        }

        ReviewerStatistics reviewerStatistics = new ReviewerStatistics();

        int column = 0;
        reviewerStatistics.setId((Long) result[column++]);
        reviewerStatistics.setAccuracy((Double) result[column++]);
        reviewerStatistics.setCoverage((Double) result[column++]);
        reviewerStatistics.setTimelineReliability((Double) result[column++]);
        reviewerStatistics.setTotalEvaluationCoefficient((Double) result[column++]);
        reviewerStatistics.setReviewerId((Long) result[column++]);
        reviewerStatistics.setEligibilityPoints((Double) result[column++]);
        reviewerStatistics.setProjectId((Long) result[column++]);
        reviewerStatistics.setCompetitionTypeId((Integer) result[column++]);
        reviewerStatistics.setCreationUser((String) result[column++]);
        reviewerStatistics.setCreationTimestamp((Date) result[column++]);
        reviewerStatistics.setModificationUser((String) result[column++]);
        reviewerStatistics.setModificationTimestamp((Date) result[column++]);

        return reviewerStatistics;
    }
}
