/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.ClientFilterFactory;
import com.topcoder.timetracker.client.db.InformixClientDAO;
import com.topcoder.timetracker.client.depth.ClientOnlyDepth;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>
 * Accuracy Unit test cases for ClientOnlyDepth.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ClientOnlyDepthAccuracyTests extends TestCase {
    /**
     * <p>
     * ClientOnlyDepth instance for testing.
     * </p>
     */
    private ClientOnlyDepth instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        instance = new ClientOnlyDepth();
        AccuracyTestHelper.setUpDatabase();
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
        return new TestSuite(ClientOnlyDepthAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor ClientOnlyDepth#ClientOnlyDepth() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create ClientOnlyDepth instance.", instance);
    }

    /**
     * <p>
     * Tests ClientOnlyDepth#buildClient(CustomResultSet) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testBuildClient() throws Exception {
        InformixClientDAO dao = new InformixClientDAO();
        CustomResultSet result = dao.searchClient(ClientFilterFactory.createActiveFilter(true), instance);

        assertNotNull("Failed to build client.", instance.buildResult(result));
    }

}