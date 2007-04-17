/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.client.ClientFilterFactory;
import com.topcoder.timetracker.client.db.InformixClientDAO;
import com.topcoder.timetracker.client.depth.ClientIDOnlyDepth;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>
 * Accuracy Unit test cases for ClientIDOnlyDepth.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ClientIDOnlyDepthAccuracyTests extends TestCase {
    /**
     * <p>
     * ClientIDOnlyDepth instance for testing.
     * </p>
     */
    private ClientIDOnlyDepth instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        instance = new ClientIDOnlyDepth();
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
        return new TestSuite(ClientIDOnlyDepthAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor ClientIDOnlyDepth#ClientIDOnlyDepth() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create ClientIDOnlyDepth instance.", instance);
    }

    /**
     * <p>
     * Tests ClientIDOnlyDepth#buildClient(CustomResultSet) for accuracy.
     * </p>
     * @throws Exception to JUnit
     */
    public void testBuildClient() throws Exception {
        InformixClientDAO dao = new InformixClientDAO();
        CustomResultSet result = dao.searchClient(ClientFilterFactory.createActiveFilter(true), instance);

        assertNotNull("Failed to build client.", instance.buildResult(result));
    }

}