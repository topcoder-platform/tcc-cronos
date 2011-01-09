/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.topcoder.management.project.ReviewApplicationConfigurationException;
import com.topcoder.management.project.ReviewApplicationPersistenceException;

/**
 * Class to hold tests for: InformixReviewApplicationPersistence.
 *
 * @author pvmagacho
 * @version 1.2
 * @since 1.2
 */
public class InformixReviewApplicationPersistenceUnitTests extends
    AbstractInformixReviewApplicationPersistenceUnitTests {
    /**
     * Namespace to create persistence exception.
     */
    private static final String BAD_PERSISTENCE_NAMESPACE = "badPersistence.InformixReviewApplicationPersistence";

    /**
     * Represents the custom namespace for InformixReviewApplicationPersistence.
     */
    private static final String CACHE_EXCEPTION_NAMESPACE = "cache.InformixReviewApplicationPersistence";

    /**
     * Represents the namespace for InformixReviewApplicationPersistence.
     */
    private static final String NAMESPACE = InformixReviewApplicationPersistence.class.getName();

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = new InformixReviewApplicationPersistence(NAMESPACE);
        persistenceException = new InformixReviewApplicationPersistence(CACHE_EXCEPTION_NAMESPACE);
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
     * Accuracy test of <code>InformixReviewApplicationPersistence()</code> constructor.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testInformixReviewApplicationPersistenceAccuracy1() throws Exception {
        new InformixReviewApplicationPersistence(NAMESPACE);
    }

    /**
     * Failure test of <code>InformixReviewApplicationPersistence(String namespace)</code> constructor.
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
    public void testInformixReviewApplicationPersistenceFailure1() throws Exception {
        try {
            new InformixReviewApplicationPersistence(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>InformixReviewApplicationPersistence(String namespace)</code> constructor.
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
    public void testInformixReviewApplicationPersistenceFailure2() throws Exception {
        try {
            new InformixReviewApplicationPersistence("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>InformixReviewApplicationPersistence(String namespace)</code> constructor.
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
    public void testInformixReviewApplicationPersistenceFailure3() throws Exception {
        try {
            new InformixReviewApplicationPersistence("invalid1.InformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>InformixReviewApplicationPersistence(String namespace)</code> constructor.
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
    public void testInformixReviewApplicationPersistenceFailure4() throws Exception {
        try {
            new InformixReviewApplicationPersistence("invalid2.InformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>InformixReviewApplicationPersistence(String namespace)</code> constructor.
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
    public void testInformixReviewApplicationPersistenceFailure5() throws Exception {
        try {
            new InformixReviewApplicationPersistence("invalid3.InformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>InformixReviewApplicationPersistence(String namespace)</code> constructor.
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
    public void testInformixReviewApplicationPersistenceFailure6() throws Exception {
        try {
            new InformixReviewApplicationPersistence("invalid4.InformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>InformixReviewApplicationPersistence(String namespace)</code> constructor.
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
    public void testInformixReviewApplicationPersistenceFailure7() throws Exception {
        try {
            new InformixReviewApplicationPersistence("invalid5.InformixReviewApplicationPersistence");
            fail("Expect ReviewApplicationConfigurationException.");
        } catch (ReviewApplicationConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>InformixReviewApplicationPersistence(String namespace)</code> constructor.
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
    public void testInformixReviewApplicationPersistenceFailure8() throws Exception {
        try {
            new InformixReviewApplicationPersistence("invalid6.InformixReviewApplicationPersistence");
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
        persistence = new InformixReviewApplicationPersistence(NAMESPACE);
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
            persistence = new InformixReviewApplicationPersistence(BAD_PERSISTENCE_NAMESPACE);
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
        persistence = new InformixReviewApplicationPersistence(NAMESPACE);
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
        persistence = new InformixReviewApplicationPersistence(NAMESPACE);
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
     * Failure test of <code>closeConnection(Connection connection)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCloseConnectionFailure2() throws Exception {
        try {
            persistence = new InformixReviewApplicationPersistence(NAMESPACE);
            Connection connection = persistence.openConnection();

            connection.setAutoCommit(true);
            persistence.closeConnection(connection);
            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>closeConnectionOnError(Connection connection)</code> method.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCloseConnectionOnErrorAccuracy1() throws Exception {
        persistence = new InformixReviewApplicationPersistence(NAMESPACE);
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
        persistence = new InformixReviewApplicationPersistence(NAMESPACE);
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

    /**
     * Failure test of <code>closeConnectionOnError(Connection connection)</code> method.
     *
     * <p>
     * Expect ReviewApplicationPersistenceException.
     * </p>
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testCloseConnectionOnErrorFailure2() throws Exception {
        try {
            persistence = new InformixReviewApplicationPersistence(NAMESPACE);
            Connection connection = persistence.openConnection();

            connection.setAutoCommit(true);
            persistence.closeConnectionOnError(connection);
            fail("Expect ReviewApplicationPersistenceException.");
        } catch (ReviewApplicationPersistenceException e) {
            // expect
        }
    }
}
