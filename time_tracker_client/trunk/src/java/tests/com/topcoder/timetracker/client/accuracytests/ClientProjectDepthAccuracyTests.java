/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.ClientFilterFactory;
import com.topcoder.timetracker.client.db.InformixClientDAO;
import com.topcoder.timetracker.client.depth.ClientProjectDepth;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>
 * Accuracy Unit test cases for ClientProjectDepth.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ClientProjectDepthAccuracyTests extends TestCase {
    /**
     * <p>
     * ClientProjectDepth instance for testing.
     * </p>
     */
    private ClientProjectDepth instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        instance = new ClientProjectDepth();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        instance = null;

        AccuracyTestHelper.tearDownDataBase();
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
        return new TestSuite(ClientProjectDepthAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor ClientProjectDepth#ClientProjectDepth() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create ClientProjectDepth instance.", instance);
    }

    /**
     * <p>
     * Tests ClientProjectDepth#buildClient(CustomResultSet) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testBuildClient() throws Exception {
        InformixClientDAO dao = new InformixClientDAO();
        CustomResultSet result = dao.searchClient(ClientFilterFactory.createActiveFilter(true), instance);

        assertNotNull("Failed to build client.", instance.buildResult(result));
    }

}