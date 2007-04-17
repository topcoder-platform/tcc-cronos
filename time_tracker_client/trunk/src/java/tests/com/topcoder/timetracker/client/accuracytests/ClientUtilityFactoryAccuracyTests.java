/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.ejb.ClientUtilityFactory;

/**
 * <p>
 * Accuracy Unit test cases for ClientUtilityFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ClientUtilityFactoryAccuracyTests extends TestCase {
    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ClientUtilityFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ClientUtilityFactory#getClientUtility() for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetClientUtility() throws Exception {
        assertNotNull("Failed to get client utility.", ClientUtilityFactory.getClientUtility());
    }

}