/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsPersistenceException;
import com.topcoder.management.resource.SideBySideStatistics;
import com.topcoder.management.resource.StatisticsType;
import com.topcoder.management.resource.TestsHelper;
import com.topcoder.util.cache.CacheException;
import com.topcoder.util.cache.SimpleCache;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Class to hold tests for: AbstractInformixReviewerStatisticsPersistence.
 *
 * @author pvmagacho
 * @version 1.4
 * @since 1.4
 */
public abstract class AbstractInformixReviewerStatisticsPersistenceUnitTests extends TestCase {
    /**
     * File contains sql statement to initial database.
     */
    private static final String INIT_DB_SQL = "test_files/InitDB.sql";

    /**
     * File contains sql statement to clear database.
     */
    private static final String CLEAR_DB_SQL = "test_files/ClearDB.sql";

    /**
     * Class used to retrieve instance variable.
     */
    protected Class<?> useClass;

    /**
     * AbstractInformixReviewerStatisticsPersistence instance used in test.
     */
    protected AbstractInformixReviewerStatisticsPersistence persistence;

    /**
     * AbstractInformixReviewerStatisticsPersistence instance used in test.
     */
    protected AbstractInformixReviewerStatisticsPersistence persistenceException;

    /**
     * This project is used in the test.
     */
    private ReviewerStatistics reviewerStatistic;

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config.xml
        ConfigManager.getInstance().add("config.xml");

        // initialize the instance that is used in the test.
        reviewerStatistic = new ReviewerStatistics();
        reviewerStatistic.setAccuracy(0.7);
        reviewerStatistic.setCoverage(0.7);
        reviewerStatistic.setTimelineReliability(0.7);
        reviewerStatistic.setTotalEvaluationCoefficient(0.7);
        reviewerStatistic.setReviewerId(4L);
        reviewerStatistic.setEligibilityPoints(0.7);
        reviewerStatistic.setProjectId(1L);
        reviewerStatistic.setCompetitionTypeId(1);
        reviewerStatistic.setCreationUser("topcoder");
        reviewerStatistic.setCreationTimestamp(new Date());
        reviewerStatistic.setModificationUser("topcoder");
        reviewerStatistic.setModificationTimestamp(new Date());
        reviewerStatistic.setStatisticsType(StatisticsType.AVERAGE);

        executeSQL(CLEAR_DB_SQL);

        executeSQL(INIT_DB_SQL);
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        executeSQL(CLEAR_DB_SQL);

        // remove all namespace.
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator<String> iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace(iter.next());
        }
    }

    /**
     * Accuracy test of <code>create(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewerStatisticsAverageAccuracy() throws Exception {
        reviewerStatistic.setStatisticsType(StatisticsType.AVERAGE);
        ReviewerStatistics result = persistence.create(reviewerStatistic);

        assertEquals(result, reviewerStatistic);

        SimpleCache cache = (SimpleCache) TestsHelper.getField(persistence, "cache", useClass);
        assertNull(cache.get(AbstractInformixReviewerStatisticsPersistence.AVERAGE_PREFIX
            + reviewerStatistic.getReviewerId()));
        assertEquals(result, (ReviewerStatistics) cache
            .get(AbstractInformixReviewerStatisticsPersistence.SINGLE_PREFIX + reviewerStatistic.getId()));
    }

    /**
     * Accuracy test of <code>create(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewerStatisticsHistoryAccuracy() throws Exception {
        reviewerStatistic.setStatisticsType(StatisticsType.HISTORY);
        ReviewerStatistics result = persistence.create(reviewerStatistic);

        assertEquals(result, reviewerStatistic);

        SimpleCache cache = (SimpleCache) TestsHelper.getField(persistence, "cache", useClass);
        assertNull(cache.get(AbstractInformixReviewerStatisticsPersistence.HISTORY_PREFIX
            + reviewerStatistic.getReviewerId()));
        assertEquals(result, (ReviewerStatistics) cache
            .get(AbstractInformixReviewerStatisticsPersistence.SINGLE_PREFIX + reviewerStatistic.getId()));
    }

    /**
     * Failure test of <code>create(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * <p>
     * reviewerStatistics is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewerStatisticsFailure1() throws Exception {
        try {
            persistence.create(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>create(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * <p>
     * reviewerStatistics projectId is invalid (equal to Long.MAX_VALUE).
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewerStatisticsFailure2() throws Exception {
        try {
            reviewerStatistic.setProjectId(Long.MAX_VALUE);
            persistence.create(reviewerStatistic);
            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>create(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewerStatisticsFailure3() throws Exception {
        try {
            persistenceException.create(reviewerStatistic);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Accuracy test of <code>update(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewerStatisticsAverageAccuracy() throws Exception {
        reviewerStatistic.setStatisticsType(StatisticsType.AVERAGE);
        ReviewerStatistics result = persistence.create(reviewerStatistic);

        long id = result.getId();
        assertEquals(1, result.getProjectId());

        // Update review application
        reviewerStatistic.setProjectId(2);
        result = persistence.update(reviewerStatistic);

        assertEquals(2, result.getProjectId());
        assertEquals(id, result.getId());
    }

    /**
     * Accuracy test of <code>update(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewerStatisticsHistoryAccuracy() throws Exception {
        reviewerStatistic.setStatisticsType(StatisticsType.HISTORY);
        ReviewerStatistics result = persistence.create(reviewerStatistic);

        long id = result.getId();
        assertEquals(1, result.getProjectId());

        // Update review application
        reviewerStatistic.setProjectId(2);
        result = persistence.update(reviewerStatistic);

        assertEquals(2, result.getProjectId());
        assertEquals(id, result.getId());
    }

    /**
     * Failure test of <code>update(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * <p>
     * project is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewerStatisticsFailure1() throws Exception {
        try {
            persistence.update(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>update(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewerStatisticsFailure2() throws Exception {
        try {
            reviewerStatistic.setId(1L);
            persistenceException.update(reviewerStatistic);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Failure test of <code>update(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * <p>
     * id doesn't exist in persistence.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewerStatisticsFailure3() throws Exception {
        try {
            // Update review application
            reviewerStatistic.setId(999);
            persistence.update(reviewerStatistic);
            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>update(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * <p>
     * reviewerStatistics projectId is invalid (equal to Long.MAX_VALUE).
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewerStatisticsFailure4() throws Exception {
        try {
            // Update review application
            reviewerStatistic.setId(1L);
            reviewerStatistic.setProjectId(Long.MAX_VALUE);
            persistence.update(reviewerStatistic);
            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>retrieve(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsAccuracy1() throws Exception {
        // Retrieve review application
        ReviewerStatistics result = persistence.retrieve(1L);
        assertSame(1L, result.getId());
    }

    /**
     * Accuracy test of <code>retrieve(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsAccuracy2() throws Exception {
        // Retrieve review application
        ReviewerStatistics result = persistence.retrieve(5L);
        assertSame(5L, result.getId());
    }

    /**
     * Accuracy test of <code>retrieve(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsAccuracy3() throws Exception {
        // Retrieve review application
        ReviewerStatistics result = persistence.retrieve(6L);
        assertNull(result);
    }

    /**
     * Accuracy test of <code>retrieve(long id)</code> method. Use cache.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsAccuracy4() throws Exception {
        ReviewerStatistics result = persistence.create(reviewerStatistic);

        long id = result.getId();

        // Retrieve review application
        result = persistence.retrieve(id);
        assertSame(result, reviewerStatistic);
    }

    /**
     * Failure test of <code>retrieve(long id)</code> method.
     *
     * <p>
     * id <= 0.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsFailure1() throws Exception {
        try {
            persistence.retrieve(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>retrieve(long id)</code> method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsFailure2() throws Exception {
        try {
            persistenceException.retrieve(1L);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Failure test of <code>retrieve(long id)</code> method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (SQLException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsFailure3() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE average_review_statistics;");
            statement.execute("DROP TABLE history_statistics;");

            persistence.retrieve(1L);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof SQLException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    executeCreate(statement);
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    /**
     * Accuracy test of <code>delete(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewerStatisticsAverageAccuracy() throws Exception {
        reviewerStatistic.setStatisticsType(StatisticsType.AVERAGE);

        ReviewerStatistics result = persistence.create(reviewerStatistic);
        persistence.getReviewerAverageStatistics(reviewerStatistic.getReviewerId());

        long id = result.getId();
        SimpleCache cache = (SimpleCache) TestsHelper.getField(persistence, "cache", useClass);
        assertNotNull(cache.get(AbstractInformixReviewerStatisticsPersistence.AVERAGE_PREFIX
            + reviewerStatistic.getReviewerId()));

        // Delete review application
        assertTrue(persistence.delete(id));

        assertNull(cache.get(AbstractInformixReviewerStatisticsPersistence.AVERAGE_PREFIX
            + reviewerStatistic.getReviewerId()));
    }

    /**
     * Accuracy test of <code>delete(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewerStatisticsHistoryAccuracy() throws Exception {
        reviewerStatistic.setStatisticsType(StatisticsType.HISTORY);

        ReviewerStatistics result = persistence.create(reviewerStatistic);
        persistence.getReviewerStatistics(reviewerStatistic.getReviewerId());

        long id = result.getId();
        SimpleCache cache = (SimpleCache) TestsHelper.getField(persistence, "cache", useClass);
        assertNotNull(cache.get(AbstractInformixReviewerStatisticsPersistence.HISTORY_PREFIX
            + reviewerStatistic.getReviewerId()));

        // Delete review application
        assertTrue(persistence.delete(id));

        assertNull(cache.get(AbstractInformixReviewerStatisticsPersistence.HISTORY_PREFIX
            + reviewerStatistic.getReviewerId()));
    }

    /**
     * Accuracy test of <code>delete(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewerStatisticsAccuracy() throws Exception {
        // Delete review application
        assertFalse(persistence.delete(6L));
    }

    /**
     * Failure test of <code>delete(long id)</code> method.
     *
     * <p>
     * id <= 0.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewerStatisticsFailure1() throws Exception {
        try {
            persistence.delete(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>delete(long id)</code> method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (SQLException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewApplicationFailure2() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE average_review_statistics;");
            statement.execute("DROP TABLE history_statistics;");

            persistence.delete(1L);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof SQLException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    executeCreate(statement);
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    /**
     * Accuracy test of <code>getReviewerStatistics(long reviewerId)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsAccuracy1() throws Exception {
        ReviewerStatistics[] reviewerStatistics = persistence.getReviewerStatistics(1L);
        assertEquals(2, reviewerStatistics.length);
        assertEquals(1, reviewerStatistics[0].getReviewerId());
    }

    /**
     * Accuracy test of <code>getReviewerStatistics(long reviewerId)</code> method. Gets from cache.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsAccuracy2() throws Exception {
        ReviewerStatistics[] reviewerStatistics = persistence.getReviewerStatistics(1L);
        assertEquals(2, reviewerStatistics.length);
        assertEquals(1, reviewerStatistics[0].getReviewerId());

        reviewerStatistics = persistence.getReviewerStatistics(1L);
        assertEquals(2, reviewerStatistics.length);
        assertEquals(1, reviewerStatistics[0].getProjectId());
    }

    /**
     * Failure test of <code>getReviewerStatistics(long reviewerId)</code> method.
     *
     * <p>
     * id <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsFailure1() throws Exception {
        try {
            persistence.getReviewerStatistics(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getReviewerStatistics(long reviewerId)</code> method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsFailure2() throws Exception {
        try {
            persistenceException.getReviewerStatistics(1L);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Failure test of <code>getReviewerStatistics(long reviewerId)</code> method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (SQLException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsFailure3() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE average_review_statistics;");
            statement.execute("DROP TABLE history_statistics;");

            persistence.getReviewerStatistics(1L);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof SQLException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    executeCreate(statement);
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    /**
     * Accuracy test of <code>getReviewerAverageStatistics(long reviewerId)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerAverageStatisticsAccuracy1() throws Exception {
        ReviewerStatistics[] reviewerStatistics = persistence.getReviewerAverageStatistics(1L);
        assertEquals(2, reviewerStatistics.length);
        assertEquals(1, reviewerStatistics[0].getProjectId());
        assertEquals(2, reviewerStatistics[1].getProjectId());
    }

    /**
     * Accuracy test of <code>getReviewerAverageStatistics(long reviewerId)</code> method. Gets from cache.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerAverageStatisticsAccuracy2() throws Exception {
        ReviewerStatistics[] reviewerStatistics = persistence.getReviewerAverageStatistics(1L);
        assertEquals(2, reviewerStatistics.length);
        assertEquals(1, reviewerStatistics[0].getProjectId());
        assertEquals(2, reviewerStatistics[1].getProjectId());

        reviewerStatistics = persistence.getReviewerAverageStatistics(1L);
        assertEquals(2, reviewerStatistics.length);
        assertEquals(1, reviewerStatistics[0].getProjectId());
        assertEquals(2, reviewerStatistics[1].getProjectId());
    }

    /**
     * Failure test of <code>getReviewerAverageStatistics(long reviewerId)</code> method.
     *
     * <p>
     * id <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerAverageStatisticsFailure1() throws Exception {
        try {
            persistence.getReviewerAverageStatistics(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getReviewerAverageStatistics(long reviewerId)</code> method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (CacheException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerAverageStatisticsFailure2() throws Exception {
        try {
            persistenceException.getReviewerAverageStatistics(1L);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof CacheException);
        }
    }

    /**
     * Failure test of <code>getReviewerAverageStatistics(long reviewerId)</code> method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (SQLException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerAverageStatisticsFailure3() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE average_review_statistics;");
            statement.execute("DROP TABLE history_statistics;");

            persistence.getReviewerAverageStatistics(1L);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof SQLException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    executeCreate(statement);
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    /**
     * Accuracy test of <code>getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId)</code>
     * method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsByCompetitionTypeAccuracy1() throws Exception {
        reviewerStatistic = persistence.getReviewerStatisticsByCompetitionType(1L, 1);
    }

    /**
     * Accuracy test of <code>getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId)</code>
     * method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsByCompetitionTypeAccuracy2() throws Exception {
        assertNull(persistence.getReviewerStatisticsByCompetitionType(1L, 3));
    }

    /**
     * Failure test of <code>getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId)</code>
     * method.
     *
     * <p>
     * reviewerId <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsByCompetitionTypeFailure1() throws Exception {
        try {
            persistence.getReviewerStatisticsByCompetitionType(0, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId)</code>
     * method.
     *
     * <p>
     * competitionTypeId <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsByCompetitionTypeFailure2() throws Exception {
        try {
            persistence.getReviewerStatisticsByCompetitionType(1L, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId)</code>
     * method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (SQLException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsByCompetitionTypeFailure3() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE average_review_statistics;");
            statement.execute("DROP TABLE history_statistics;");

            persistence.getReviewerStatisticsByCompetitionType(1L, 1);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof SQLException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    executeCreate(statement);
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    /**
     * Accuracy test of
     * <code>getSideBySideStatistics(long firstReviewerId, long secondReviewerId, int competitionTypeId)</code>
     * method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSideBySideStatisticsAccuracy1() throws Exception {
        SideBySideStatistics sideBySideStatistics = persistence.getSideBySideStatistics(1L, 2L, 1);
        List<ReviewerStatistics> firstReviewerStatistics = sideBySideStatistics.getFirstReviewerStatistics();
        assertEquals(2, firstReviewerStatistics.size());
        assertEquals(0.9, firstReviewerStatistics.get(0).getAccuracy());
        assertEquals(0.8, firstReviewerStatistics.get(1).getAccuracy());

        List<ReviewerStatistics> secondReviewerStatistics = sideBySideStatistics.getSecondReviewerStatistics();
        assertEquals(2, secondReviewerStatistics.size());
        assertEquals(0.7, secondReviewerStatistics.get(0).getAccuracy());
        assertEquals(0.6, secondReviewerStatistics.get(1).getAccuracy());
    }

    /**
     * Accuracy test of
     * <code>getSideBySideStatistics(long firstReviewerId, long secondReviewerId, int competitionTypeId)</code>
     * method. Gets from cache.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSideBySideStatisticsAccuracy3() throws Exception {
        SideBySideStatistics sideBySideStatistics = persistence.getSideBySideStatistics(1L, 2L, 1);
        List<ReviewerStatistics> firstReviewerStatistics = sideBySideStatistics.getFirstReviewerStatistics();
        assertEquals(2, firstReviewerStatistics.size());
        assertEquals(0.9, firstReviewerStatistics.get(0).getAccuracy());
        assertEquals(0.8, firstReviewerStatistics.get(1).getAccuracy());

        List<ReviewerStatistics> secondReviewerStatistics = sideBySideStatistics.getSecondReviewerStatistics();
        assertEquals(2, secondReviewerStatistics.size());
        assertEquals(0.7, secondReviewerStatistics.get(0).getAccuracy());
        assertEquals(0.6, secondReviewerStatistics.get(1).getAccuracy());

        sideBySideStatistics = persistence.getSideBySideStatistics(1L, 2L, 1);
        firstReviewerStatistics = sideBySideStatistics.getFirstReviewerStatistics();
        assertEquals(2, firstReviewerStatistics.size());
        assertEquals(0.9, firstReviewerStatistics.get(0).getAccuracy());
        assertEquals(0.8, firstReviewerStatistics.get(1).getAccuracy());

        secondReviewerStatistics = sideBySideStatistics.getSecondReviewerStatistics();
        assertEquals(2, secondReviewerStatistics.size());
        assertEquals(0.7, secondReviewerStatistics.get(0).getAccuracy());
        assertEquals(0.6, secondReviewerStatistics.get(1).getAccuracy());
    }

    /**
     * Failure test of
     * <code>getSideBySideStatistics(long firstReviewerId, long secondReviewerId, int competitionTypeId)</code>
     * method.
     *
     * <p>
     * firstReviewerId <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSideBySideStatisticsFailure1() throws Exception {
        try {
            persistence.getSideBySideStatistics(0, 2L, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>getSideBySideStatistics(long firstReviewerId, long secondReviewerId, int competitionTypeId)</code>
     * method.
     *
     * <p>
     * secondReviewerId <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSideBySideStatisticsFailure2() throws Exception {
        try {
            persistence.getSideBySideStatistics(1L, 0, 1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>getSideBySideStatistics(long firstReviewerId, long secondReviewerId, int competitionTypeId)</code>
     * method.
     *
     * <p>
     * competitionTypeId <= 0
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSideBySideStatisticsFailure3() throws Exception {
        try {
            persistence.getSideBySideStatistics(1L, 2L, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>getSideBySideStatistics(long firstReviewerId, long secondReviewerId, int competitionTypeId)</code>
     * method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException. (SQLException)
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSideBySideStatisticsFailure4() throws Exception {
        Statement statement = null;
        Connection connection = null;
        try {
            // get db connection
            connection = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
                .createConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE average_review_statistics;");
            statement.execute("DROP TABLE history_statistics;");

            persistence.getSideBySideStatistics(1L, 2L, 1);

            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
            assertTrue(e.getCause() instanceof SQLException);
        } finally {
            try {
                if ((statement != null && connection != null) && !connection.isClosed()) {
                    executeCreate(statement);
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    /**
     * Executes a sql batch contains in a file.
     *
     * @param file the file contains the sql statements.
     *
     * @throws Exception pass to JUnit.
     */
    private void executeSQL(String file) throws Exception {
        // get db connection
        Connection connection = new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").createConnection();
        Statement statement = connection.createStatement();

        // get sql statements and add to statement
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                statement.addBatch(line);
            }
        }

        statement.executeBatch();
    }

    /**
     * Execute create table sql statement.
     *
     * @param statement the statement to execute.
     * @throws SQLException if error occurs during sql execution.
     */
    private void executeCreate(Statement statement) throws SQLException {
        statement
            .execute("CREATE TABLE average_review_statistics(id DECIMAL(10,0) NOT NULL,"
                + " accuracy FLOAT NOT NULL,coverage FLOAT NOT NULL,timeline_reliability FLOAT NOT NULL,"
                + " total_evaluation_coefficient FLOAT NOT NULL,reviewer_id INTEGER NOT NULL,"
                + " eligibility_points FLOAT NOT NULL,project_id DECIMAL(10,0) NOT NULL,competition_type_id INTEGER NOT NULL,"
                + " create_user VARCHAR(64) NOT NULL,create_date DATETIME YEAR TO FRACTION(3) NOT NULL,"
                + " modify_user VARCHAR(64) NOT NULL,modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,"
                + " PRIMARY KEY(id, competition_type_id),FOREIGN KEY(project_id) REFERENCES project(project_id),"
                + " FOREIGN KEY(reviewer_id) REFERENCES rboard_user(user_id), FOREIGN KEY(competition_type_id)"
                + " REFERENCES project_type_lu(project_type_id));");
        statement
            .execute("CREATE TABLE history_statistics(id DECIMAL(10,0) NOT NULL,"
                + " accuracy FLOAT NOT NULL,coverage FLOAT NOT NULL,timeline_reliability FLOAT NOT NULL,"
                + " total_evaluation_coefficient FLOAT NOT NULL,reviewer_id INTEGER NOT NULL,"
                + " eligibility_points FLOAT NOT NULL,project_id DECIMAL(10,0) NOT NULL,competition_type_id INTEGER NOT NULL,"
                + " create_user VARCHAR(64) NOT NULL,create_date DATETIME YEAR TO FRACTION(3) NOT NULL,"
                + " modify_user VARCHAR(64) NOT NULL,modify_date DATETIME YEAR TO FRACTION(3) NOT NULL,"
                + " PRIMARY KEY(id, competition_type_id),FOREIGN KEY(project_id) REFERENCES project(project_id),"
                + " FOREIGN KEY(reviewer_id) REFERENCES rboard_user(user_id), FOREIGN KEY(competition_type_id)"
                + " REFERENCES project_type_lu(project_type_id));");
    }
}
