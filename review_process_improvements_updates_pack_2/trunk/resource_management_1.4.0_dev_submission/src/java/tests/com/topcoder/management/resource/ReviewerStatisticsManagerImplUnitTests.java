/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Unit tests for the class: ReviewerStatisticsManagerImpl.
 *
 * @author pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class ReviewerStatisticsManagerImplUnitTests extends TestCase {
    /**
     * Namespace to create persistence exception.
     */
    private static final String BAD_PERSISTENCE_NAMESPACE = "badPersistence.ReviewerStatisticsManagerImpl";

    /**
     * File contains sql statement to initial database.
     */
    private static final String INIT_DB_SQL = "test_files/InitDB.sql";

    /**
     * File contains sql statement to clear database.
     */
    private static final String CLEAR_DB_SQL = "test_files/ClearDB.sql";

    /**
     * This project is used in the test.
     */
    private ReviewerStatistics reviewerStatistic;

    /**
     * this ReviewerStatisticsManagerImpl is used in the test.
     */
    private ReviewerStatisticsManagerImpl reviewerStatisticsManagerImpl;

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
        reviewerStatistic.setReviewerId(1L);
        reviewerStatistic.setEligibilityPoints(0.7);
        reviewerStatistic.setProjectId(1L);
        reviewerStatistic.setCompetitionTypeId(1);
        reviewerStatistic.setCreationUser("topcoder");
        reviewerStatistic.setCreationTimestamp(new Date(0));
        reviewerStatistic.setModificationUser("topcoder");
        reviewerStatistic.setModificationTimestamp(new Date(0));
        reviewerStatistic.setStatisticsType(StatisticsType.AVERAGE);

        executeSQL(CLEAR_DB_SQL);

        executeSQL(INIT_DB_SQL);

        reviewerStatisticsManagerImpl = new ReviewerStatisticsManagerImpl();
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
     * Accuracy test of <code>ReviewerStatisticsManagerImpl()</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplAccuracy1() throws Exception {
        new ReviewerStatisticsManagerImpl();
    }

    /**
     * Accuracy test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * use 'custom.ReviewerStatisticsManagerImpl' namespace.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplAccuracy2() throws Exception {
        new ReviewerStatisticsManagerImpl(ReviewerStatisticsManagerImpl.DEFAULT_NAMESPACE);
    }

    /**
     * Accuracy test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * use 'custom.ReviewerStatisticsManagerImpl' namespace.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplAccuracy3() throws Exception {
        new ReviewerStatisticsManagerImpl("custom.ReviewerStatisticsManagerImpl");
    }

    /**
     * Failure test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * namespace is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplFailure1() throws Exception {
        try {
            new ReviewerStatisticsManagerImpl(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     * namespace is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplFailure2() throws Exception {
        try {
            new ReviewerStatisticsManagerImpl("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     *'PersistenceClassName' property is missed.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplFailure3() throws Exception {
        try {
            new ReviewerStatisticsManagerImpl("invalid1.ReviewerStatisticsManagerImpl");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     *'PersistenceClassName' is String.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplFailure4() throws Exception {
        try {
            new ReviewerStatisticsManagerImpl("invalid2.ReviewerStatisticsManagerImpl");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     *'PersistenceClassName' is empty.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplFailure5() throws Exception {
        try {
            new ReviewerStatisticsManagerImpl("invalid3.ReviewerStatisticsManagerImpl");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     *'PersistenceClassName' is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplFailure6() throws Exception {
        try {
            new ReviewerStatisticsManagerImpl("invalid4.ReviewerStatisticsManagerImpl");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     *'PersistenceClassName' is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplFailure7() throws Exception {
        try {
            new ReviewerStatisticsManagerImpl("invalid5.ReviewerStatisticsManagerImpl");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     *'PersistenceClassName' is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplFailure8() throws Exception {
        try {
            new ReviewerStatisticsManagerImpl("invalid6.ReviewerStatisticsManagerImpl");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     *'PersistenceClassName' is empty.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplFailure9() throws Exception {
        try {
            new ReviewerStatisticsManagerImpl("invalid7.ReviewerStatisticsManagerImpl");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ReviewerStatisticsManagerImpl(String namespace)</code> constructor.
     *
     * <p>
     *'PersistenceClassName' is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testReviewerStatisticsManagerImplFailure10() throws Exception {
        try {
            new ReviewerStatisticsManagerImpl("unkown_namespace");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>create(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewerStatisticsAccuracy() throws Exception {
        ReviewerStatistics result = reviewerStatisticsManagerImpl.create(reviewerStatistic);
        assertEquals(result, reviewerStatistic);
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
            reviewerStatisticsManagerImpl.create(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>create(ReviewerStatistics reviewerStatistics)</code>
     * method.
     *
     * <p>
     * Expect ReviewerStatisticsManagerException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCreateReviewerStatisticsFailure2() throws Exception {
        try {
            reviewerStatisticsManagerImpl = new ReviewerStatisticsManagerImpl(BAD_PERSISTENCE_NAMESPACE);
            reviewerStatisticsManagerImpl.create(reviewerStatistic);
            fail("Expect ReviewerStatisticsManagerException.");
        } catch (ReviewerStatisticsManagerException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>update(ReviewerStatistics reviewerStatistics)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewerStatisticsAccuracy() throws Exception {
        ReviewerStatistics result = reviewerStatisticsManagerImpl.create(reviewerStatistic);

        long id = result.getId();
        assertEquals(1, result.getProjectId());

        // Update review application
        reviewerStatistic.setProjectId(2);
        result = reviewerStatisticsManagerImpl.update(reviewerStatistic);

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
            reviewerStatisticsManagerImpl.update(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateReviewerStatistics(ReviewerStatistics reviewerStatistics)</code>
     * method.
     *
     * <p>
     * Expect ReviewerStatisticsManagerException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewerStatisticsFailure2() throws Exception {
        try {
            reviewerStatisticsManagerImpl = new ReviewerStatisticsManagerImpl(BAD_PERSISTENCE_NAMESPACE);
            reviewerStatisticsManagerImpl.update(reviewerStatistic);
            fail("Expect ReviewerStatisticsManagerException.");
        } catch (ReviewerStatisticsManagerException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>retrieve(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsAccuracy() throws Exception {
        ReviewerStatistics result = reviewerStatisticsManagerImpl.create(reviewerStatistic);

        long id = result.getId();

        // Delete review application
        result = reviewerStatisticsManagerImpl.retrieve(id);
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
            reviewerStatisticsManagerImpl.retrieve(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>retrieve(long id)</code>
     * method.
     *
     * <p>
     * Expect ReviewerStatisticsManagerException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testRetrieveReviewerStatisticsFailure2() throws Exception {
        try {
            reviewerStatisticsManagerImpl = new ReviewerStatisticsManagerImpl(BAD_PERSISTENCE_NAMESPACE);
            reviewerStatisticsManagerImpl.retrieve(1L);
            fail("Expect ReviewerStatisticsManagerException.");
        } catch (ReviewerStatisticsManagerException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>delete(long id)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewerStatisticsAccuracy() throws Exception {
        ReviewerStatistics result = reviewerStatisticsManagerImpl.create(reviewerStatistic);

        long id = result.getId();

        // Delete review application
        assertTrue(reviewerStatisticsManagerImpl.delete(id));
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
            reviewerStatisticsManagerImpl.delete(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>delete(long id)</code>
     * method.
     *
     * <p>
     * Expect ReviewerStatisticsManagerException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testDeleteReviewerStatisticsFailure2() throws Exception {
        try {
            reviewerStatisticsManagerImpl = new ReviewerStatisticsManagerImpl(BAD_PERSISTENCE_NAMESPACE);
            reviewerStatisticsManagerImpl.delete(1L);
            fail("Expect ReviewerStatisticsManagerException.");
        } catch (ReviewerStatisticsManagerException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getReviewerStatistics(long reviewerId)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsAccuracy() throws Exception {
        ReviewerStatistics[] reviewerStatistics = reviewerStatisticsManagerImpl.getReviewerStatistics(1L);
        assertEquals(2, reviewerStatistics.length);
        assertEquals(1, reviewerStatistics[0].getReviewerId());
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
            reviewerStatisticsManagerImpl.getReviewerStatistics(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getReviewerStatistics(long reviewerId)</code>
     * method.
     *
     * <p>
     * Expect ReviewerStatisticsManagerException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsFailure2() throws Exception {
        try {
            reviewerStatisticsManagerImpl = new ReviewerStatisticsManagerImpl(BAD_PERSISTENCE_NAMESPACE);
            reviewerStatisticsManagerImpl.getReviewerStatistics(1L);
            fail("Expect ReviewerStatisticsManagerException.");
        } catch (ReviewerStatisticsManagerException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getReviewerAverageStatistics(long reviewerId)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerAverageStatisticsAccuracy() throws Exception {
        ReviewerStatistics[] reviewerStatistics = reviewerStatisticsManagerImpl.getReviewerAverageStatistics(1L);
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
            reviewerStatisticsManagerImpl.getReviewerAverageStatistics(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getReviewerAverageStatistics(long reviewerId)</code>
     * method.
     *
     * <p>
     * Expect ReviewerStatisticsManagerException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerAverageStatisticsFailure2() throws Exception {
        try {
            reviewerStatisticsManagerImpl = new ReviewerStatisticsManagerImpl(BAD_PERSISTENCE_NAMESPACE);
            reviewerStatisticsManagerImpl.getReviewerAverageStatistics(1L);
            fail("Expect ReviewerStatisticsManagerException.");
        } catch (ReviewerStatisticsManagerException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getReviewerStatisticsByCompetitionType(long reviewerId, int competitionTypeId)</code>
     * method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsByCompetitionTypeAccuracy() throws Exception {
        reviewerStatistic = reviewerStatisticsManagerImpl.getReviewerStatisticsByCompetitionType(1L, 1);
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
            reviewerStatisticsManagerImpl.getReviewerStatisticsByCompetitionType(0, 1);
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
            reviewerStatisticsManagerImpl.getReviewerStatisticsByCompetitionType(1L, 0);
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
     * Expect ReviewerStatisticsManagerException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetReviewerStatisticsByCompetitionTypeFailure3() throws Exception {
        try {
            reviewerStatisticsManagerImpl = new ReviewerStatisticsManagerImpl(BAD_PERSISTENCE_NAMESPACE);
            reviewerStatisticsManagerImpl.getReviewerStatisticsByCompetitionType(1L, 1);
            fail("Expect ReviewerStatisticsManagerException.");
        } catch (ReviewerStatisticsManagerException e) {
            // expect
        }
    }

    /**
     * Accuracy test of
     * <code>getSideBySideStatistics(long firstReviewerId, long secondReviewerId, int competitionTypeId)</code>
     * method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSideBySideStatisticsAccuracy() throws Exception {
        SideBySideStatistics sideBySideStatistics = reviewerStatisticsManagerImpl.getSideBySideStatistics(1L, 2L, 1);
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
            reviewerStatisticsManagerImpl.getSideBySideStatistics(0, 2L, 1);
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
            reviewerStatisticsManagerImpl.getSideBySideStatistics(1L, 0, 1);
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
            reviewerStatisticsManagerImpl.getSideBySideStatistics(1L, 2L, 0);
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
     * Expect ReviewerStatisticsManagerException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testGetSideBySideStatisticsFailure4() throws Exception {
        try {
            reviewerStatisticsManagerImpl = new ReviewerStatisticsManagerImpl(BAD_PERSISTENCE_NAMESPACE);
            reviewerStatisticsManagerImpl.getSideBySideStatistics(1L, 2L, 1);
            fail("Expect ReviewerStatisticsManagerException.");
        } catch (ReviewerStatisticsManagerException e) {
            // expect
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
}
