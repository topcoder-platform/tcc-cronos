/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.sql.Connection;
import java.sql.SQLException;

import com.topcoder.management.resource.ReviewerStatisticsConfigurationException;
import com.topcoder.management.resource.ReviewerStatisticsPersistenceException;

/**
 * Unit tests for the class: UnmanagedTransactionInformixReviewerStatisticsPersistence.
 *
 * @author pvmagacho
 * @version 1.4
 * @since 1.4
 */
public class UnmanagedTransactionInformixReviewerStatisticsPersistenceUnitTests extends
    AbstractInformixReviewerStatisticsPersistenceUnitTests {
    /**
     * Namespace to create persistence exception.
     */
    private static final String BAD_PERSISTENCE_NAMESPACE =
        "badPersistence.UnmanagedTransactionInformixReviewerStatisticsPersistence";

    /**
     * Represents the custom namespace for InformixReviewerStatisticsPersistence.
     */
    private static final String CACHE_EXCEPTION_NAMESPACE =
        "cache.UnmanagedTransactionInformixReviewerStatisticsPersistence";

    /**
     * Represents the namespace for UnmanagedTransactionInformixReviewerStatisticsPersistence.
     */
    private static final String NAMESPACE = UnmanagedTransactionInformixReviewerStatisticsPersistence.class.getName();

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = new UnmanagedTransactionInformixReviewerStatisticsPersistence(NAMESPACE);
        persistenceException = new UnmanagedTransactionInformixReviewerStatisticsPersistence(
            CACHE_EXCEPTION_NAMESPACE);
        useClass = persistence.getClass().getSuperclass();
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Accuracy test of <code>UnmanagedTransactionInformixReviewerStatisticsPersistence()</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewerStatisticsPersistenceAccuracy1() throws Exception {
        new UnmanagedTransactionInformixReviewerStatisticsPersistence(NAMESPACE);
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewerStatisticsPersistence(String namespace)</code>
     * constructor.
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
    public void testUnmanagedTransactionInformixReviewerStatisticsPersistenceFailure1() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewerStatisticsPersistence(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewerStatisticsPersistence(String namespace)</code>
     * constructor.
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
    public void testUnmanagedTransactionInformixReviewerStatisticsPersistenceFailure2() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewerStatisticsPersistence("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewerStatisticsPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'DBConnFactoryNamespace' property is missed.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewerStatisticsPersistenceFailure3() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewerStatisticsPersistence(
                "invalid1.UnmanagedTransactionInformixReviewerStatisticsPersistence");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewerStatisticsPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'DBConnFactoryNamespace' property is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewerStatisticsPersistenceFailure4() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewerStatisticsPersistence(
                "invalid2.UnmanagedTransactionInformixReviewerStatisticsPersistence");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewerStatisticsPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'ReviewerStatisticsIDSequenceName' property is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewerStatisticsPersistenceFailure5() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewerStatisticsPersistence(
                "invalid3.UnmanagedTransactionInformixReviewerStatisticsPersistence");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewerStatisticsPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'CacheNamespace' property is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewerStatisticsPersistenceFailure6() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewerStatisticsPersistence(
                "invalid4.UnmanagedTransactionInformixReviewerStatisticsPersistence");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewerStatisticsPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'CacheNamespace' property is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewerStatisticsPersistenceFailure7() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewerStatisticsPersistence(
                "invalid5.UnmanagedTransactionInformixReviewerStatisticsPersistence");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewerStatisticsPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'CacheNamespace' property is missed.
     * </p>
     *
     * <p>
     * Expect ReviewerStatisticsConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewerStatisticsPersistenceFailure8() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewerStatisticsPersistence(
                "invalid6.UnmanagedTransactionInformixReviewerStatisticsPersistence");
            fail("Expect ReviewerStatisticsConfigurationException.");
        } catch (ReviewerStatisticsConfigurationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>openConnection()</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testOpenConnectionAccuracy() throws Exception {
        persistence = new UnmanagedTransactionInformixReviewerStatisticsPersistence(NAMESPACE);
        Connection connection = persistence.openConnection();
        assertNotNull(connection);
        try {
            connection.close();
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * Failure test of <code>openConnection()</code> method.
     *
     * <p>
     * Expect ReviewerStatisticsPersistenceException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testOpenConnectionFailure() throws Exception {
        try {
            persistence = new UnmanagedTransactionInformixReviewerStatisticsPersistence(BAD_PERSISTENCE_NAMESPACE);
            persistence.openConnection();
            fail("Expect ReviewerStatisticsPersistenceException.");
        } catch (ReviewerStatisticsPersistenceException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>closeConnection(Connection connection)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCloseConnectionAccuracy1() throws Exception {
        persistence = new UnmanagedTransactionInformixReviewerStatisticsPersistence(NAMESPACE);
        Connection connection = persistence.openConnection();
        assertNotNull(connection);

        persistence.closeConnection(connection);
    }

    /**
     * Accuracy test of <code>closeConnection(Connection connection)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCloseConnectionAccuracy2() throws Exception {
        persistence = new InformixReviewerStatisticsPersistence(NAMESPACE);
        Connection connection = persistence.openConnection();
        assertNotNull(connection);
        try {
            connection.close();
        } catch (SQLException e) {
            // ignore
        }

        persistence.closeConnection(connection);
    }

    /**
     * Failure test of <code>closeConnection(Connection connection)</code> method.
     *
     * <p>
     * connection is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCloseConnectionFailure1() throws Exception {
        try {
            persistence.closeConnection(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>closeConnectionOnError(Connection connection)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCloseConnectionOnErrorAccuracy1() throws Exception {
        persistence = new InformixReviewerStatisticsPersistence(NAMESPACE);
        Connection connection = persistence.openConnection();
        assertNotNull(connection);

        persistence.closeConnectionOnError(connection);
    }

    /**
     * Accuracy test of <code>closeConnectionOnError(Connection connection)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCloseConnectionOnErrorAccuracy2() throws Exception {
        persistence = new InformixReviewerStatisticsPersistence(NAMESPACE);
        Connection connection = persistence.openConnection();
        assertNotNull(connection);
        try {
            connection.close();
        } catch (SQLException e) {
            // ignore
        }

        persistence.closeConnectionOnError(connection);
    }

    /**
     * Failure test of <code>closeConnectionOnError(Connection connection)</code> method.
     *
     * <p>
     * connection is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCloseConnectionOnErrorFailure1() throws Exception {
        try {
            persistence.closeConnectionOnError(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
