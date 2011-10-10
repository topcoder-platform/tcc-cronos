/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.management.project.ReviewApplicationConfigurationException;
import com.topcoder.management.project.ReviewApplicationPersistence;
import com.topcoder.management.project.ReviewApplicationPersistenceException;
import com.topcoder.management.project.persistence.Helper.DataType;
import com.topcoder.management.project.persistence.logging.LogMessage;
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
 * This class is the base class for informix review application persistence implementations. It implements CRUD
 * operations on ReviewApplication entities. DBConnectionFactory is used to create database connections.
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
 * @author moonli, pvmagacho, TCSDEVELOPER
 * @version 1.2.1
 * @since 1.2 (Review Process Improvements Project)
 */
public abstract class AbstractInformixReviewApplicationPersistence implements ReviewApplicationPersistence {
    /**
     * Represents the cache key prefix for single ReviewApplication entity. Value of the key in cache is a single
     * ReviewApplication instance. E.g. if id of the ReviewApplication is 1, then cache key for this instance is
     * "single_1".
     */
    public static final String SINGLE_PREFIX = "single_";

    /**
     * Represents the cache key prefix for primary review applications for a project. Value of the key is an array of
     * ReviewApplication objects. For example, if the project id is 100, then the cache key is "primary_100"
     */
    public static final String PRIMARY_PREFIX = "primary_";

    /**
     * Represents the cache key prefix for secondary review applications for a project. Value of the key is an array
     * of ReviewApplication objects. For example, if the project id is 11, then the cache key is "secondary_11"
     */
    public static final String SECONDARY_PREFIX = "secondary_";

    /**
     * Represents the cache key prefix for all review applications for a project. Value of the key is an array of
     * ReviewApplication objects. For example, if the project id is 10, then the cache key is "all_10"
     */
    public static final String ALL_PREFIX = "all_";

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
     * Represents the name of review application id sequence name parameter in configuration.
     */
    private static final String REVIEW_APPLICATION_ID_SEQUENCE_NAME_PARAMETER = "ReviewApplicationIDSequenceName";

    /**
     * Represents the sql statement to query single review application.
     */
    private static final String QUERY_SINGLE_REVIEW_APPLICATION_SQL = "select reviewer_id, project_id, "
        + "application_date, is_primary from review_applications where id=?";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query single
     * review application.
     */
    private static final DataType[] QUERY_SINGLE_REVIEW_APPLICATION_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.LONG_TYPE, Helper.DATE_TYPE, Helper.BOOLEAN_TYPE};

    /**
     * Represents the sql statement to retrieve primary/secondary review application.
     */
    private static final String QUERY_REVIEW_APPLICATION_SQL = "select id, reviewer_id, application_date from "
        + "review_applications where project_id=? and is_primary=";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query
     * primary/secondary review application.
     */
    private static final DataType[] QUERY_REVIEW_APPLICATION_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.LONG_TYPE, Helper.DATE_TYPE};

    /**
     * Represents the sql statement to retrieve all review application.
     */
    private static final String QUERY_ALL_REVIEW_APPLICATION_SQL = "select id, reviewer_id, application_date, "
        + "is_primary from review_applications where project_id=?";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query all
     * review application.
     */
    private static final DataType[] QUERY_ALL_REVIEW_APPLICATION_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.LONG_TYPE, Helper.DATE_TYPE, Helper.BOOLEAN_TYPE};

    /**
     * Represents the sql statement to retrieve project id for review application.
     */
    private static final String QUERY_PROJECT_ID_SQL = "select project_id from review_applications where id=?";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query
     * project id from review application.
     */
    private static final DataType QUERY_PROJECT_ID_COLUMN_TYPES = Helper.LONG_TYPE;

    /**
     * Represents the sql statement to create review application.
     */
    private static final String CREATE_REVIEW_APPLICATION_SQL = "INSERT INTO review_applications (id, reviewer_id, "
        + "project_id, application_date, is_primary) VALUES (?, ?, ?, ?, ?)";

    /**
     * Represents the sql statement to update review application.
     */
    private static final String UPDATE_REVIEW_APPLICATION_SQL = "update review_applications set reviewer_id=?, "
        + "project_id=?, application_date=?, is_primary=? where id=?";

    /**
     * Represents the sql statement to delete review application.
     */
    private static final String DELETE_REVIEW_APPLICATION_SQL = "delete from review_applications where id=?";

    /**
     * Represents the database connection name, it is used with DBConnectionFactory to create database connections.
     *
     * It's initialized in ctor, and won't change afterwards, can be null indicating default connection will be used.
     * cannot be empty, it has a getter getConnectionName.
     */
    private final String connectionName;

    /**
     * Represents the DBConnectionFactory instance used to create database connections.
     *
     * It's initialized in ctor, and won't change afterwards. Cannot be null. It has a getter getConnectionFactory.
     */
    private final DBConnectionFactory conFactory;

    /**
     * Represents the cache to store results temporarily for "retrieval" actions. It helps reduce the requests to
     * underlying database, making it more efficient. Cache will hold 4 sorts of data. - A single ReviewApplication
     * entity with key=SINGLE_PREFIX+id - An array of ReviewApplication entities with key=PRIMARY_PREFIX+projectId -
     * An array of ReviewApplication entities with key=SECONDARY_PREFIX+projectId - An array of ReviewApplication
     * entities with key=ALL_PREFIX+projectId
     *
     * It's initialized in ctor to non-null Cache object, won't change afterwards. It cannot be null. It's used by all
     * public methods of this class.
     */
    private final Cache cache;

    /**
     * Represents the IDGenerator used to generate new ID for ReviewApplication entity during "create" operation.
     *
     * It's initialized to non-null object in ctor, won't change afterwards. It's used in create method of this class.
     */
    private final IDGenerator reviewApplicationIDGenerator;

    /**
     * Creates an instance of this class with configuration in specified namespace.
     *
     * <p>
     * The following parameters are configured.
     * <ul>
     * <li>DBConnFactoryNamespace: The namespace that contains settings for DB Connection Factory. It is required.</li>
     * <li>ConnectionName: The name of the connection that will be used by DBConnectionFactory to create connection.
     * If missing, default connection will be created. It is optional.</li>
     * <li>ReviewApplicationIDSequenceName: The sequence name used to create Id generator for project Id. If missing
     * default value (review_applications_id_seq) is used. It is optional.</li>
     * <li>CacheNamespace: The namespace for the Simple Cache object. It is required.</li>
     * </ul>
     * </p>
     * <p>
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if the input is null or empty string
     * @throws ReviewApplicationConfigurationException if any error occurred during configuration
     */
    @SuppressWarnings("deprecation")
    protected AbstractInformixReviewApplicationPersistence(String namespace) {
        Helper.assertStringNotNullNorEmpty(namespace, "namespace");

        // Get the instance of ConfigManager
        ConfigManager cm = ConfigManager.getInstance();

        // Get db connection factory namespace
        String factoryNamespace = getConfigurationParameterValue(cm, namespace,
            CONNECTION_FACTORY_NAMESPACE_PARAMETER, true, getLogger());

        // Try to create a DBConnectionFactoryImpl instance from factoryNamespace
        try {
            conFactory = new DBConnectionFactoryImpl(factoryNamespace);
        } catch (UnknownConnectionException e) {
            throw new ReviewApplicationConfigurationException(
                "Unable to create a new instance of DBConnectionFactoryImpl class" + " from namespace ["
                    + factoryNamespace + "].", e);
        } catch (ConfigurationException e) {
            throw new ReviewApplicationConfigurationException(
                "Unable to create a new instance of DBConnectionFactoryImpl class" + " from namespace ["
                    + factoryNamespace + "].", e);
        }

        // Get the connection name
        connectionName = getConfigurationParameterValue(cm, namespace, CONNECTION_NAME_PARAMETER, true, getLogger());

        // Try to get review application id sequence name
        String reviewApplicationIDSequenceName = getConfigurationParameterValue(cm, namespace,
            REVIEW_APPLICATION_ID_SEQUENCE_NAME_PARAMETER, true, getLogger());

        // Try to get the IDGenerator
        try {
            reviewApplicationIDGenerator = IDGeneratorFactory.getIDGenerator(reviewApplicationIDSequenceName);
        } catch (IDGenerationException e) {
            getLogger().log(Level.ERROR,
                "The reviewApplicationIDGenerator [" + reviewApplicationIDSequenceName + "] is invalid.");
            throw new ReviewApplicationConfigurationException("Unable to create IDGenerator for '"
                + reviewApplicationIDSequenceName + "'.", e);
        }

        // Try to get cache namespace parameter
        String cacheNamespace = getConfigurationParameterValue(cm, namespace, CACHE_NAMESAPCE_PARAMETER, true,
            getLogger());

        // Create cache instance
        try {
            this.cache = new SimpleCache(cacheNamespace);
        } catch (CacheInstantiationException e) {
            throw createReviewApplicationConfigurationException(e, cacheNamespace);
        } catch (IllegalArgumentException e) {
            throw createReviewApplicationConfigurationException(e, cacheNamespace);
        }
    }

    /**
     * Create a new review application in persistence.
     *
     * @param reviewApplication the new review application to create
     * @return the created review application
     * @throws IllegalArgumentException if reviewApplication is null or reviewApplication.projectId or
     *             reviewApplication.reviewerId is not positive
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication create(ReviewApplication reviewApplication) throws ReviewApplicationPersistenceException {
        Helper.assertObjectNotNull(reviewApplication, "reviewApplication");
        Helper.assertLongPositive(reviewApplication.getReviewerId(), "reviewerId");
        Helper.assertLongPositive(reviewApplication.getProjectId(), "projectId");

        Connection conn = null;

        // newId will contain the new generated Id for the project
        Long newId;

        getLogger().log(Level.INFO,
            new LogMessage(null, null, "creating new review application: " + reviewApplication.getReviewerId()));

        try {
            // create the connection
            conn = openConnection();

            try {
                // generate id for the project
                newId = new Long(reviewApplicationIDGenerator.getNextID());
                getLogger().log(Level.INFO, new LogMessage(newId, null, "generate id for new review application"));
            } catch (IDGenerationException e) {
                throw new ReviewApplicationPersistenceException("Unable to generate id for the review application.",
                    e);
            }

            // insert the project into database
            Object[] queryArgs = new Object[] {newId, reviewApplication.getReviewerId(),
                reviewApplication.getProjectId(), new Timestamp(reviewApplication.getApplicationDate().getTime()),
                reviewApplication.isAcceptPrimary()};
            Helper.doDMLQuery(conn, CREATE_REVIEW_APPLICATION_SQL, queryArgs);

            closeConnection(conn);

            // set the newId when no exception occurred
            reviewApplication.setId(newId);

            removeCache(cache, reviewApplication.getProjectId());

            cache.put(SINGLE_PREFIX + reviewApplication.getId(), reviewApplication);
        } catch (CacheException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to insert data into cache.", e);
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to create review application  in cache, "
                    + reviewApplication.getProjectId(), er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        } catch (PersistenceException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to insert data from database.", e);
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to create review application, " + reviewApplication.getProjectId(),
                    er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        }

        return reviewApplication;
    }

    /**
     * Updates existing review application.
     *
     * @param reviewApplication the review application to update
     * @return the updated review application
     * @throws IllegalArgumentException if reviewApplication is null or reviewApplication.projectId or
     *             reviewApplication.reviewerId is not positive
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication update(ReviewApplication reviewApplication) throws ReviewApplicationPersistenceException {
        Helper.assertObjectNotNull(reviewApplication, "reviewApplication");
        Helper.assertLongPositive(reviewApplication.getReviewerId(), "reviewerId");
        Helper.assertLongPositive(reviewApplication.getProjectId(), "projectId");

        Connection conn = null;

        getLogger()
            .log(
                Level.INFO,
                new LogMessage(reviewApplication.getId(), null, "updating project: "
                    + reviewApplication.getReviewerId()));
        try {
            // create the connection
            conn = openConnection();

            // check whether the review application id is already in the database
            if (!Helper.checkEntityExists("review_applications", "id", reviewApplication.getId(), conn)) {
                throw new ReviewApplicationPersistenceException("The review application with the id ["
                    + reviewApplication.getId() + "] doesn't exists.");
            }

            // insert the project into database
            Object[] queryArgs = new Object[] {reviewApplication.getReviewerId(), reviewApplication.getProjectId(),
                new Timestamp(reviewApplication.getApplicationDate().getTime()), reviewApplication.isAcceptPrimary(),
                reviewApplication.getId()};
            Helper.doDMLQuery(conn, UPDATE_REVIEW_APPLICATION_SQL, queryArgs);

            closeConnection(conn);

            removeCache(cache, reviewApplication.getProjectId());

            cache.put(SINGLE_PREFIX + reviewApplication.getId(), reviewApplication);
        } catch (CacheException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to update data from cache.", e);
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to update review application, " + reviewApplication.getProjectId(),
                    er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        } catch (PersistenceException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to update data from database.", e);
            getLogger().log(
                Level.ERROR,
                new LogMessage(null, null, "Fails to update review application, " + reviewApplication.getProjectId(),
                    er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        }

        return reviewApplication;
    }

    /**
     * Retrieve the review application by id.
     *
     * @param id id of the review application to retrieve
     * @return the retrieved review application, or null if not found
     * @throws IllegalArgumentException if id is not positive
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication retrieve(long id) throws ReviewApplicationPersistenceException {
        Helper.assertLongPositive(id, "id");

        getLogger().log(Level.INFO, "get review application with the id: " + id);

        Connection conn = null;
        try {
            // Check if review application exists in cache
            ReviewApplication reviewApplication = (ReviewApplication) cache.get(SINGLE_PREFIX + id);
            if (reviewApplication != null) {
                return reviewApplication;
            }

            // create the connection
            conn = openConnection();

            // Execute query
            Object[][] rows = Helper.doQuery(conn, QUERY_SINGLE_REVIEW_APPLICATION_SQL, new Object[] {id},
                QUERY_SINGLE_REVIEW_APPLICATION_COLUMN_TYPES);

            closeConnection(conn);

            if (rows.length == 0) {
                return null;
            }

            Object[] result = rows[0];
            reviewApplication = new ReviewApplication();
            reviewApplication.setId(id);
            int i = 0;
            reviewApplication.setReviewerId((Long) result[i++]);
            reviewApplication.setProjectId((Long) result[i++]);
            reviewApplication.setApplicationDate((Date) result[i++]);
            reviewApplication.setAcceptPrimary((Boolean) result[i++]);

            cache.put(SINGLE_PREFIX + id, reviewApplication);

            return reviewApplication;
        } catch (CacheException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to retrieve data from cache.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve review application with id: " + id, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        } catch (PersistenceException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to retrieve data from database.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve review application with id: " + id, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        }
    }

    /**
     * Deletes the review application by ID.
     *
     * @param id id of the review application to delete
     * @return true if review application is deleted, otherwise false (id doesn't exist in persistence)
     * @throws IllegalArgumentException if id is not positive
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public boolean delete(long id) throws ReviewApplicationPersistenceException {
        Helper.assertLongPositive(id, "id");

        getLogger().log(Level.INFO, "delete review application with the id: " + id);

        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // check whether the review application id is already in the database
            if (!Helper.checkEntityExists("review_applications", "id", id, conn)) {
                return false;
            }

            // Get project id from review_applications table
            Long projectId = (Long) Helper.doSingleValueQuery(conn, QUERY_PROJECT_ID_SQL, new Object[] {id},
                QUERY_PROJECT_ID_COLUMN_TYPES);

            // Execute query
            Helper.doDMLQuery(conn, DELETE_REVIEW_APPLICATION_SQL, new Object[] {id});

            closeConnection(conn);

            removeCache(cache, projectId);
        } catch (CacheException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to delete data from cache.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to delete review application with id: " + id, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        } catch (PersistenceException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to delete data from database.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to delete review application with id: " + id, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        }

        return true;
    }

    /**
     * Gets primary review applications for specified project.
     *
     * @param projectId id of the project
     * @return a ReviewApplication array, won't be null, may be empty
     * @throws IllegalArgumentException if projectId is not positive
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication[] getPrimaryApplications(long projectId) throws ReviewApplicationPersistenceException {
        Helper.assertLongPositive(projectId, "projectId");

        getLogger().log(Level.INFO, "retrieve primary review application with the projectId: " + projectId);

        Connection conn = null;
        try {
            // Check if review application exists in cache
            ReviewApplication[] reviewApplications = (ReviewApplication[]) cache.get(PRIMARY_PREFIX + projectId);
            if (reviewApplications != null) {
                return reviewApplications;
            }

            // create the connection
            conn = openConnection();

            // Execute query
            Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_APPLICATION_SQL + "'t'", new Object[] {projectId},
                QUERY_REVIEW_APPLICATION_COLUMN_TYPES);

            closeConnection(conn);

            reviewApplications = new ReviewApplication[rows.length];
            for (int i = 0; i < rows.length; i++) {
                Object[] result = rows[i];
                reviewApplications[i] = createReviewApplication(result, projectId, true);
            }

            cache.put(PRIMARY_PREFIX + projectId, reviewApplications);

            return reviewApplications;
        } catch (CacheException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to retrieve data from cache.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve review application with project id: " + projectId, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        } catch (PersistenceException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to retrieve data from database.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve review application with project id: " + projectId, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        }
    }

    /**
     * Gets secondary review applications for specified project.
     *
     * @param projectId id of the project
     * @return a ReviewApplication array, won't be null, may be empty
     * @throws IllegalArgumentException if projectId is not positive
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication[] getSecondaryApplications(long projectId) throws ReviewApplicationPersistenceException {
        Helper.assertLongPositive(projectId, "projectId");

        getLogger().log(Level.INFO, "retrieve secondary review application with the projectId: " + projectId);

        Connection conn = null;
        try {
            // Check if review application exists in cache
            ReviewApplication[] reviewApplications = (ReviewApplication[]) cache.get(SECONDARY_PREFIX + projectId);
            if (reviewApplications != null) {
                return reviewApplications;
            }

            // create the connection
            conn = openConnection();

            // Execute query
            Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_APPLICATION_SQL + "'f'", new Object[] {projectId},
                QUERY_REVIEW_APPLICATION_COLUMN_TYPES);

            closeConnection(conn);

            reviewApplications = new ReviewApplication[rows.length];
            for (int i = 0; i < rows.length; i++) {
                Object[] result = rows[i];
                reviewApplications[i] = createReviewApplication(result, projectId, false);
            }

            cache.put(SECONDARY_PREFIX + projectId, reviewApplications);

            return reviewApplications;
        } catch (CacheException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to retrieve data from cache.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve review application with project id: " + projectId, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        } catch (PersistenceException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to retrieve data from database.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve review application with project id: " + projectId, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        }
    }

    /**
     * Gets all review applications for specified project.
     *
     * @param projectId id of the project
     * @return a ReviewApplication array, won't be null, may be empty
     * @throws IllegalArgumentException if projectId is not positive
     * @throws ReviewApplicationPersistenceException if any other error occurred during operation
     */
    public ReviewApplication[] getAllApplications(long projectId) throws ReviewApplicationPersistenceException {
        Helper.assertLongPositive(projectId, "projectId");

        getLogger().log(Level.INFO, "retrieve all review applications with the projectId: " + projectId);

        Connection conn = null;
        try {
            // Check if review application exists in cache
            ReviewApplication[] reviewApplications = (ReviewApplication[]) cache.get(ALL_PREFIX + projectId);
            if (reviewApplications != null) {
                return reviewApplications;
            }

            // create the connection
            conn = openConnection();

            // Execute query
            Object[][] rows = Helper.doQuery(conn, QUERY_ALL_REVIEW_APPLICATION_SQL, new Object[] {projectId},
                QUERY_ALL_REVIEW_APPLICATION_COLUMN_TYPES);

            closeConnection(conn);

            reviewApplications = new ReviewApplication[rows.length];
            for (int i = 0; i < rows.length; i++) {
                Object[] result = rows[i];
                reviewApplications[i] = createReviewApplication(result, projectId, (Boolean) result[result.length - 1]);
            }

            cache.put(ALL_PREFIX + projectId, reviewApplications);

            return reviewApplications;
        } catch (CacheException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to retrieve data from cache.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve review application with project id: " + projectId, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        } catch (PersistenceException e) {
            ReviewApplicationPersistenceException er = new ReviewApplicationPersistenceException(
                "Unable to retrieve data from database.", e);
            getLogger().log(Level.ERROR,
                new LogMessage(null, null, "Fails to retrieve review application with project id: " + projectId, er));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw er;
        }
    }

    /**
     * Create review application from sql results.
     *
     * @param result the sql result object.
     * @param projectId the review application project id.
     * @param isPrimary true if is primary reviewer
     * @return the created review application object
     */
    private ReviewApplication createReviewApplication(Object[] result, long projectId, Boolean isPrimary) {
        ReviewApplication reviewApplication = new ReviewApplication();
        reviewApplication.setId((Long) result[0]);
        reviewApplication.setReviewerId((Long) result[1]);
        reviewApplication.setProjectId(projectId);
        reviewApplication.setApplicationDate((Date) result[2]);
        reviewApplication.setAcceptPrimary(isPrimary);

        return reviewApplication;
    }

    /**
     * Gets the connection name.
     *
     * @return the connection name
     */
    protected String getConnectionName() {
        return connectionName;
    }

    /**
     * Gets the DB connection factory.
     *
     * @return the DB connection factory
     */
    protected DBConnectionFactory getConnectionFactory() {
        return conFactory;
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
     * @throws ReviewApplicationPersistenceException if any error occurred
     */
    protected abstract Connection openConnection() throws ReviewApplicationPersistenceException;

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
     * @throws ReviewApplicationPersistenceException if any error occurred
     */
    protected abstract void closeConnection(Connection connection) throws ReviewApplicationPersistenceException;

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
     * @throws ReviewApplicationPersistenceException if any error occurred
     */
    protected abstract void closeConnectionOnError(Connection connection)
        throws ReviewApplicationPersistenceException;

    /**
     * Wrap ReviewApplicationConfigurationException around Exception thrown by the SimpleCache class.
     *
     * @param e the Exception to be wrapped
     * @param cacheNamespace the cache namespace used for SimpleCache configuration
     *
     * @return ReviewApplicationConfigurationException with wrapped exception
     */
    private ReviewApplicationConfigurationException createReviewApplicationConfigurationException(Throwable e,
        String cacheNamespace) {
        getLogger().log(Level.ERROR, "Unable to create cache with namespace " + cacheNamespace + ".");
        return new ReviewApplicationConfigurationException("Unable to create cache with namespace " + cacheNamespace
            + ".", e);
    }

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
     * @throws ReviewApplicationConfigurationException if the namespace does not exist, or the value is not specified
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
            throw new ReviewApplicationConfigurationException("Configuration namespace [" + namespace
                + "] does not exist.", e);
        }

        if (value == null || value.trim().length() == 0) {
            logger.log(Level.FATAL, "Configuration parameter [" + name + "] under namespace [" + namespace
                + "] is not specified.");
            throw new ReviewApplicationConfigurationException("Configuration parameter [" + name
                + "] under namespace [" + namespace + "] is not specified.");
        }

        logger.log(Level.INFO, "Read propery[" + name + "] which is " + (required ? " required " : " optional ")
            + " with value[" + value + "] from namespace [" + namespace + "].");

        return value;
    }

    /**
     * Remove entry from cache based on the review application project's id.
     *
     * @param localCache the cache instance
     * @param projectId the review application project's id
     */
    private static void removeCache(Cache localCache, long projectId) {
        localCache.remove(PRIMARY_PREFIX + projectId);
        localCache.remove(SECONDARY_PREFIX + projectId);
        localCache.remove(ALL_PREFIX + projectId);
    }
}
