/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.topcoder.management.project.ReviewApplicationConfigurationException;
import com.topcoder.management.project.ReviewApplicationPersistenceException;

/**
 * Class to hold tests for: UnmanagedTransactionInformixReviewApplicationPersistence.
 *
 * @author pvmagacho, TCSDEVELOPER
 * @version 1.2.1
 * @since 1.2 (Review Process Improvements Project)
 */
public class UnmanagedTransactionInformixReviewApplicationPersistenceUnitTests extends
    AbstractInformixReviewApplicationPersistenceUnitTests {
    /**
     * Namespace to create persistence exception.
     */
    private static final String BAD_PERSISTENCE_NAMESPACE =
        "badPersistence.UnmanagedTransactionInformixReviewApplicationPersistence";

    /**
     * Represents the custom namespace for UnmanagedTransactionInformixReviewApplicationPersistence.
     */
    private static final String CACHE_EXCEPTION_NAMESPACE =
        "cache.UnmanagedTransactionInformixReviewApplicationPersistence";

    /**
     * Represents the namespace for UnmanagedTransactionInformixReviewApplicationPersistence.
     */
    private static final String NAMESPACE = UnmanagedTransactionInformixReviewApplicationPersistence.class.getName();

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = new UnmanagedTransactionInformixReviewApplicationPersistence(NAMESPACE);
        persistenceException = new UnmanagedTransactionInformixReviewApplicationPersistence(CACHE_EXCEPTION_NAMESPACE);
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
     * Accuracy test of <code>UnmanagedTransactionInformixReviewApplicationPersistence()</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewApplicationPersistenceAccuracy1() throws Exception {
        new UnmanagedTransactionInformixReviewApplicationPersistence(NAMESPACE);
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewApplicationPersistence(String namespace)</code>
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
    public void testUnmanagedTransactionInformixReviewApplicationPersistenceFailure1() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewApplicationPersistence(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewApplicationPersistence(String namespace)</code>
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
    public void testUnmanagedTransactionInformixReviewApplicationPersistenceFailure2() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewApplicationPersistence("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewApplicationPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'DBConnFactoryNamespace' property is missed.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewApplicationPersistenceFailure3() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewApplicationPersistence(
                "invalid1.UnmanagedTransactionInformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewApplicationPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'DBConnFactoryNamespace' property is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewApplicationPersistenceFailure4() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewApplicationPersistence(
                "invalid2.UnmanagedTransactionInformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewApplicationPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'ReviewerStatisticsIDSequenceName' property is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewApplicationPersistenceFailure5() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewApplicationPersistence(
                "invalid3.UnmanagedTransactionInformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewApplicationPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'CacheNamespace' property is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewApplicationPersistenceFailure6() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewApplicationPersistence(
                "invalid4.UnmanagedTransactionInformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewApplicationPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'CacheNamespace' property is invalid.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewApplicationPersistenceFailure7() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewApplicationPersistence(
                "invalid5.UnmanagedTransactionInformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>UnmanagedTransactionInformixReviewApplicationPersistence(String namespace)</code>
     * constructor.
     *
     * <p>
     *'CacheNamespace' property is missed.
     * </p>
     *
     * <p>
     * Expect ReviewApplicationConfigurationException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUnmanagedTransactionInformixReviewApplicationPersistenceFailure8() throws Exception {
        try {
            new UnmanagedTransactionInformixReviewApplicationPersistence(
                "invalid6.UnmanagedTransactionInformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>openConnection()</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testOpenConnectionAccuracy() throws Exception {
        persistence = new UnmanagedTransactionInformixReviewApplicationPersistence(NAMESPACE);
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
     * Expect ReviewApplicationPersistenceException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testOpenConnectionFailure() throws Exception {
        try {
            persistence = new UnmanagedTransactionInformixReviewApplicationPersistence(BAD_PERSISTENCE_NAMESPACE);
            persistence.openConnection();
            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>closeConnection(Connection connection)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCloseConnectionAccuracy1() throws Exception {
        persistence = new UnmanagedTransactionInformixReviewApplicationPersistence(NAMESPACE);
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
        persistence = new UnmanagedTransactionInformixReviewApplicationPersistence(NAMESPACE);
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
        persistence = new UnmanagedTransactionInformixReviewApplicationPersistence(NAMESPACE);
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
        persistence = new UnmanagedTransactionInformixReviewApplicationPersistence(NAMESPACE);
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
