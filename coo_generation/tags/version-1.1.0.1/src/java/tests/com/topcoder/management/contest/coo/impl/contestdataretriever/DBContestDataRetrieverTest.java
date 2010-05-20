/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl.contestdataretriever;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.ContestData;
import com.topcoder.management.contest.coo.impl.TestHelper;

/**
 *
 * <p>
 * Unit test case of {@link DBContestDataRetriever}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DBContestDataRetrieverTest extends TestCase {
    /**
     * instance created for testing.
     */
    private DBContestDataRetriever instance;

    /**
     * <p>
     * set up test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {
        TestHelper.executeSqlFile("test_files/clean.sql");
        TestHelper.executeSqlFile("test_files/insert.sql");
        ConfigurationObject config = TestHelper.getConfiguration("test_files/componentManager.xml");
        instance = new DBContestDataRetriever(config);
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
        TestHelper.executeSqlFile("test_files/clean.sql");
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
     * accuracy test for method <code>retrieveContestData()</code>.
     *
     * @throws Exception to JUNIT.
     */
    public void testRetrieveContestData1() throws Exception {

        ContestData data = instance.retrieveContestData(1L);
//        assertEquals("wrong name.", "HBS Singleton Process Manager 1.0.0", data.getComponentName());
//        assertTrue("should contain 'localhost'", data.getSvnPath().contains("localhost"));
//        assertEquals("should contain 'Luca'", "Luca", data.getDesignWinner());
    
    }

    /**
     * accuracy test for method <code>retrieveContestData()</code>.
     *
     * @throws Exception to JUNIT.
     */
    public void testRetrieveContestData2() throws Exception {
    
    	ContestData data = instance.retrieveContestData(1L);
 //       assertEquals("wrong name.", "HBS Singleton Process Manager 1.0.0", data.getComponentName());
 //      assertEquals("should contain 'Luca'", "Luca", data.getDesignWinner());

    }
}
