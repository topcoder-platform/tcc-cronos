/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.failuretests;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.persistence.db.BaseDatabasePersistence;
import com.topcoder.payments.amazonfps.persistence.db.DatabasePaymentPersistence;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseDatabasePersistence.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class BaseDatabasePersistenceFailureTests extends TestCase {

    /**
     * <p>
     * Represent the BaseDatabasePersistence instance for testing.
     * </p>
     */
    private BaseDatabasePersistence instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new DatabasePaymentPersistence();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BaseDatabasePersistenceFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseDatabasePersistence#configure(ConfigurationObject) for failure.
     * It tests the case that when configuration is null and expects IllegalArgumentException.
     * </p>
     */
    public void testConfigure_NullConfiguration() {
        try {
            instance.configure(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseDatabasePersistence#configure(ConfigurationObject) for failure.
     * It tests the case that when require property is not exist and
     * expects AmazonFlexiblePaymentSystemComponentConfigurationException.
     * </p>
     */
    public void testConfigure_NotExistProperty() {
        try {
            instance.configure(new DefaultConfigurationObject("test"));
            fail("AmazonFlexiblePaymentSystemComponentConfigurationException expected.");
        } catch (AmazonFlexiblePaymentSystemComponentConfigurationException e) {
            //good
        }
    }

}