/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.failuretests;

import java.math.BigDecimal;
import java.sql.Connection;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.AuthorizationNotFoundException;
import com.topcoder.payments.amazonfps.model.Authorization;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistenceException;
import com.topcoder.payments.amazonfps.persistence.db.DatabaseAuthorizationPersistence;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for DatabaseAuthorizationPersistence.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class DatabaseAuthorizationPersistenceFailureTests extends TestCase {

    /**
     * <p>
     * Represent the DatabaseAuthorizationPersistence instance for testing.
     * </p>
     */
    private DatabaseAuthorizationPersistence instance;

    /**
     * <p>
     * Represent the ConfigurationObject instance for testing.
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * Represent the Authorization instance for testing.
     * </p>
     */
    private Authorization authorization;

    /**
     * <p>
     * Represent the Connection instance for testing.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        connection = FailureTestHelper.getConnection();
        FailureTestHelper.clearDB(connection);
        FailureTestHelper.loadDB(connection);

        instance = new DatabaseAuthorizationPersistence();
        configuration = FailureTestHelper.getConfig("com.topcoder.payments.amazonfps.AmazonPaymentManagerImpl").getChild(
            "authorizationPersistenceConfig");
        authorization = new Authorization();
        authorization.setId(8);
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId("tokenId");
        authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(10));
        authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(100));
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        try {
            FailureTestHelper.clearDB(connection);
        } finally {
            FailureTestHelper.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DatabaseAuthorizationPersistenceFailureTests.class);
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#getAuthorization(long) for failure.
     * It tests the case when authorizationId is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAuthorization_ZeroAuthorizationId() throws Exception {
        instance.configure(configuration);
        try {
            instance.getAuthorization(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#getAuthorization(long) for failure.
     * It tests the case when authorizationIdGenerator is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAuthorization_NullAuthorizationIdGenerator() throws Exception {
        try {
            instance.getAuthorization(1);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#getAuthorization(long) for failure.
     * Expects AuthorizationPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAuthorization_AuthorizationPersistenceException() throws Exception {
        configuration.setPropertyValue("connectionName", "invalid");
        instance.configure(configuration);
        try {
            instance.getAuthorization(1);
            fail("AuthorizationPersistenceException expected.");
        } catch (AuthorizationPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#createAuthorization(Authorization) for failure.
     * It tests the case that when authorization is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAuthorization_NullAuthorization() throws Exception {
        instance.configure(configuration);
        try {
            instance.createAuthorization(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#createAuthorization(Authorization) for failure.
     * It tests the case that when authorizationIdGenerator is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAuthorization_NullAuthorizationIdGenerator() throws Exception {
        try {
            instance.createAuthorization(authorization);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#createAuthorization(Authorization) for failure.
     * Expects AuthorizationPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCreateAuthorization_AuthorizationPersistenceException() throws Exception {
        configuration.setPropertyValue("connectionName", "invalid");
        instance.configure(configuration);
        try {
            instance.createAuthorization(authorization);
            fail("AuthorizationPersistenceException expected.");
        } catch (AuthorizationPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#updateAuthorization(Authorization) for failure.
     * It tests the case that when authorization is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAuthorization_NullAuthorization() throws Exception {
        try {
            instance.updateAuthorization(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#updateAuthorization(Authorization) for failure.
     * It tests the case that when authorization.getId() is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAuthorization_ZeroAuthorizationId() throws Exception {
        instance.configure(configuration);
        authorization.setId(0);
        try {
            instance.updateAuthorization(authorization);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#updateAuthorization(Authorization) for failure.
     * Expects AuthorizationNotFoundException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAuthorization_AuthorizationNotFoundException() throws Exception {
        instance.configure(configuration);
        try {
            instance.updateAuthorization(authorization);
            fail("AuthorizationNotFoundException expected.");
        } catch (AuthorizationNotFoundException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#updateAuthorization(Authorization) for failure.
     * Expects AuthorizationPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateAuthorization_AuthorizationPersistenceException() throws Exception {
        configuration.setPropertyValue("connectionName", "invalid");
        instance.configure(configuration);
        try {
            instance.updateAuthorization(authorization);
            fail("AuthorizationPersistenceException expected.");
        } catch (AuthorizationPersistenceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#configure(ConfigurationObject) for failure.
     * It tests the case that when configuration is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConfigure_NullConfiguration() throws Exception {
        try {
            instance.configure(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseAuthorizationPersistence#configure(ConfigurationObject) for failure.
     * It tests the case that when authorizationIdGeneratorName is null and expects
     * AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConfigure_NullauthorizationIdGeneratorName() throws Exception {
        try {
            instance.configure(new DefaultConfigurationObject("test"));
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

}