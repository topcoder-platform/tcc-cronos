/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.topcoder.management.resource.persistence.ReviewerStatisticsPersistence;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * This class is the default implementation of ReviewerStatisticsManager interface. It holds and instance of
 * ReviewerStatisticsPersistence and delegates all job to ReviewerStatisticsPersistence instance. It provides CRUD
 * operations of ReviewerStatistics entity.
 * </p>
 *
 * <p>
 * The default configuration namespace for this class is:
 * &quot;com.topcoder.management.resource.ReviewerStatisticsManagerImpl&quot;. It can accept a custom namespace as
 * well.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: it's immutable and thread safe.
 * </p>
 *
 * <p>
 * <b>Sample usage</b>:
 * </p>
 *
 * <pre>
 * // create an instance of ReviewerStatisticsManagerImpl
 * ReviewerStatisticsManagerImpl manager = new ReviewerStatisticsManagerImpl(ReviewerStatisticsManagerImpl.class
 *     .getName());
 *
 * ReviewerStatistics rs = new ReviewerStatistics();
 * // set properties of ReviewerStatistics
 * rs.setReviewerId(1);
 * rs.setProjectId(1);
 * rs.setCompetitionTypeId(1);
 * rs.setAccuracy(0.65);
 * rs.setModificationUser(&quot;topcoder&quot;);
 * rs.setModificationTimestamp(new Date());
 * rs.setCreationUser(&quot;topcoder&quot;);
 * rs.setCreationTimestamp(new Date());
 * // create an instance of ReviewerStatistics
 * rs = manager.create(rs);
 * // rs has new ID provided by review_application_id_seq + 1
 *
 * // create another instance
 * ReviewerStatistics anotherRS = new ReviewerStatistics();
 * anotherRS.setReviewerId(2);
 * anotherRS.setProjectId(1);
 * anotherRS.setCompetitionTypeId(1);
 * anotherRS.setAccuracy(0.8);
 * anotherRS.setModificationUser(&quot;topcoder&quot;);
 * anotherRS.setModificationTimestamp(new Date());
 * anotherRS.setCreationUser(&quot;topcoder&quot;);
 * anotherRS.setCreationTimestamp(new Date());
 * anotherRS = manager.create(anotherRS);
 * // anotherRS has new ID equal to rs ID plus 1
 *
 * // update ReviewerStatistics
 * rs.setAccuracy(0.85);
 * rs = manager.update(rs);
 * // accuracy is set to 0.85
 *
 * // retrieve ReviewerStatistics
 * ReviewerStatistics rs2 = manager.retrieve(rs.getId());
 * // rs2 has the same content as rs
 *
 * // delete rs
 * manager.delete(rs.getId());
 * // rs should be deleted
 *
 * // create an instance of ReviewerStatistics
 * rs = manager.create(rs);
 *
 * // get reviewer statistics by competition type
 * ReviewerStatistics rs3 = manager.getReviewerStatisticsByCompetitionType(1, 1);
 * // rs3 should be the same as rs, its reviewer id is 1 and competition type id is 1
 *
 * // get Side By Side Statistics
 * SideBySideStatistics rs4 = manager.getSideBySideStatistics(1, 2, 1);
 * // an array with 1 elements is returned, one is for reviewer with
 * // id=1, the other is for reviewer with id=2
 *
 * // get Reviewer Average Statistics
 * ReviewerStatistics[] rs5 = manager.getReviewerAverageStatistics(2);
 * // an array with 1 element is returned, average statistics for
 * // reviewer 2, with competition type=1
 *
 * // get Reviewer history Statistics
 * ReviewerStatistics[] rs6 = manager.getReviewerStatistics(2);
 * // an array with 1 elements is returned, all elements have
 * // reviewer id=2 and competition type id=1
 * </pre>
 *
 * @author moonli, pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class ReviewerStatisticsManagerImpl implements ReviewerStatisticsManager {
    /**
     * Represents the default configuration namespace for this class.
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.management.resource.ReviewerStatisticsManagerImpl";

    /**
     * Represents the persistence class property name.
     */
    private static final String PERSISTENCE_CLASS = "PersistenceClassName";

    /**
     * Represents the persistence namespace property name.
     */
    private static final String PERSISTENCE_NAMESPACE = "PersistenceNamespace";

    /**
     * Represents the persistence handle that used to manage ReviewerStatistics entities in persistence. It's
     * initialized in constructor, won't change afterwards, cannot be null. It's used in all methods of this class.
     */
    private final ReviewerStatisticsPersistence persistence;

    /**
     * Create an instance of ReviewerStatisticsManagerImpl class.
     *
     * <pre>
     * Sample usage for configuration file:
     *
     * &lt;Config name="com.topcoder.management.project.ApplicationsManagerImpl"&gt;
     *  &lt;Property name="PersistenceClassName"&gt;
     *   &lt;Value&gt;com.topcoder.management.resource.persistence.sql.InformixReviewerStatisticsPersistence&lt;/Value&gt;
     *  &lt;/Property&gt;
     *  &lt;Property name="PersistenceNamespace"&gt;
     *   &lt;Value&gt;com.topcoder.management.resource.persistence.sql.InformixReviewerStatisticsPersistence&lt;/Value&gt;
     *  &lt;/Property&gt;
     * &lt;/Config&gt;
     * </pre>
     *
     * @throws ReviewerStatisticsConfigurationException if any error occurred when configuring this class
     */
    public ReviewerStatisticsManagerImpl() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * Create an instance of ReviewerStatisticsManagerImpl class.
     *
     * <pre>
     * Sample usage for configuration file:
     *
     * &lt;Config name="com.topcoder.management.project.ApplicationsManagerImpl"&gt;
     *   &lt;Property name="PersistenceClassName"&gt;
     *     &lt;Value&gt;com.topcoder.management.resource.persistence.sql.InformixReviewerStatisticsPersistence&lt;/Value&gt;
     *   &lt;/Property&gt;
     *   &lt;Property name="PersistenceNamespace"&gt;
     *     &lt;Value&gt;com.topcoder.management.resource.persistence.sql.InformixReviewerStatisticsPersistence&lt;/Value&gt;
     *   &lt;/Property&gt;
     * &lt;/Config&gt;
     * </pre>
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if namespace is null or empty
     * @throws ReviewerStatisticsConfigurationException if any error occurred when configuring this class
     */
    public ReviewerStatisticsManagerImpl(String namespace) {
        Helper.checkStringNotNullOrEmpty(namespace, "namespace");

        // get config manager instance.
        ConfigManager cm = ConfigManager.getInstance();
        try {
            // get PersistenceClass property.
            String persistenceClass = cm.getString(namespace, PERSISTENCE_CLASS);
            // assert perisitenceClass not null/empty.
            Helper.checkStringNotNullOrEmpty(persistenceClass, "persistenceClass");

            // get PersistenceNamespace property.
            String persistenceNamespace = cm.getString(namespace, PERSISTENCE_NAMESPACE);

            // if PersistenceNamespace property is not exist, use
            // persistenceClass instead.
            if (persistenceNamespace == null) {
                persistenceNamespace = persistenceClass;
            }
            // assert perisitenceNamespace not empty.
            Helper.checkStringNotNullOrEmpty(persistenceNamespace, "persistenceNamespace");

            // create persistence and validator
            persistence = (ReviewerStatisticsPersistence) createObject(persistenceClass, persistenceNamespace);
        } catch (IllegalArgumentException iae) {
            throw new ReviewerStatisticsConfigurationException("some property is missed", iae);
        } catch (UnknownNamespaceException une) {
            throw new ReviewerStatisticsConfigurationException(namespace + " namespace is unknown.", une);
        } catch (ClassCastException cce) {
            throw new ReviewerStatisticsConfigurationException("error occurs", cce);
        }
    }

    /**
     * This private method is used to create object via reflection.
     *
     * @param className className to use
     * @param namespace namespace to use
     * @throws ReviewerStatisticsConfigurationException if any error occurs
     * @return the object created.
     */
    private static Object createObject(String className, String namespace) {
        try {
            // get constructor
            Constructor<?> constructor = Class.forName(className).getConstructor(new Class[] {String.class});
            // create object
            return constructor.newInstance(new Object[] {namespace});
        } catch (ClassNotFoundException cnfe) {
            throw new ReviewerStatisticsConfigurationException(
                "error occurs when trying to create object via reflection.", cnfe);
        } catch (NoSuchMethodException nsme) {
            throw new ReviewerStatisticsConfigurationException(
                "error occurs when trying to create object via reflection.", nsme);
        } catch (InstantiationException ie) {
            throw new ReviewerStatisticsConfigurationException(
                "error occurs when trying to create object via reflection.", ie);
        } catch (IllegalAccessException iae) {
            throw new ReviewerStatisticsConfigurationException(
                "error occurs when trying to create object via reflection.", iae);
        } catch (InvocationTargetException ite) {
            throw new ReviewerStatisticsConfigurationException(
                "error occurs when trying to create object via reflection.", ite);
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
     * @throws ReviewerStatisticsManagerException if any error occurred during operation
     */
    public ReviewerStatistics create(ReviewerStatistics reviewerStatistics) throws ReviewerStatisticsManagerException {
        return persistence.create(reviewerStatistics);
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
     * @throws ReviewerStatisticsManagerException if any error occurred during operation
     */
    public ReviewerStatistics update(ReviewerStatistics reviewerStatistics) throws ReviewerStatisticsManagerException {
        return persistence.update(reviewerStatistics);
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
     * @throws ReviewerStatisticsManagerException if any error occurred during operation
     */
    public ReviewerStatistics retrieve(long id) throws ReviewerStatisticsManagerException {
        return persistence.retrieve(id);
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
     * @throws ReviewerStatisticsManagerException if any error occurred during operation
     */
    public boolean delete(long id) throws ReviewerStatisticsManagerException {
        return persistence.delete(id);
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
     * @throws ReviewerStatisticsManagerException if any error occurred during operation
     */
    public ReviewerStatistics getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId)
        throws ReviewerStatisticsManagerException {
        return persistence.getReviewerStatisticsByCompetitionType(reviewerId, competitionTypeId);
    }

    /**
     * Gets the AVERAGES statistics for specified reviewer.
     *
     * @param reviewerId id of the reviewer
     * @return the ReviewerStatistics for specified reviewer, or empty array if not exist
     * @throws IllegalArgumentException if argument is negative
     * @throws ReviewerStatisticsManagerException if any error occurred during operation
     */
    public ReviewerStatistics[] getReviewerAverageStatistics(long reviewerId)
        throws ReviewerStatisticsManagerException {
        return persistence.getReviewerAverageStatistics(reviewerId);
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
     * @throws ReviewerStatisticsManagerException if any error occurred during operation
     */
    public ReviewerStatistics[] getReviewerStatistics(long reviewerId) throws ReviewerStatisticsManagerException {
        return persistence.getReviewerStatistics(reviewerId);
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
     * @throws ReviewerStatisticsManagerException if any error occurred during operation
     */
    public SideBySideStatistics getSideBySideStatistics(long firstReviewerId, long secondReviewerId,
        int competitionTypeId) throws ReviewerStatisticsManagerException {
        return persistence.getSideBySideStatistics(firstReviewerId, secondReviewerId, competitionTypeId);
    }
}
