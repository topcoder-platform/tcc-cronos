/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
/**
 *
 * <p>
 * Unit test case of {@link BaseDBConnector}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseDBConnectorTest extends TestCase {
    /**
     * instance created for testing.
     */
    private BaseDBConnector instance;

    /**
     * <p>
     * set up test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {

        ConfigurationObject config = TestHelper.getConfiguration("test_files/componentManager.xml");

        instance = new BaseDBConnector(config) {
        };
    }

    /**
     * <p>
     * tear down test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * test constructor.
     *
     * @throws Exception to JUNIT.
     */
    public void testCtor() throws Exception {
        assertNotNull("fail to create instance.", instance);
    }

    /**
     * test <code>getConnectionName()</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void testConnNameGetter() throws Exception {
        assertEquals("should be informix_connection", "informix_connection", instance.getConnectionName());
    }

    /**
     * test <code>getConnectionName()</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void testConnFac() throws Exception {
        assertNotNull("should not be null", instance.getDbConnectionFactory());
    }

    /**
     * test <code>getConnectionName()</code> method.
     *
     * @throws Exception to JUNIT.
     */
    public void testLoggerGetter() throws Exception {
        assertNotNull("should not be null", instance.getLogger());
    }
}
