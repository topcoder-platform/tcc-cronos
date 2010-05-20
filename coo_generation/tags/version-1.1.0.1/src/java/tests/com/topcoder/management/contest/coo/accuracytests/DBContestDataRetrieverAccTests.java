/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.ContestData;
import com.topcoder.management.contest.coo.impl.contestdataretriever.DBContestDataRetriever;

/**
 * <p>
 * Accuracy test case for {@link DBContestDataRetriever} class.
 * </p>
 * 
 * @author myxgyy
 * @version 1.0
 */
public class DBContestDataRetrieverAccTests extends BaseTestCase {
    /** Target. */
    private DBContestDataRetriever instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception
     *             to Junit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigurationObject config = BaseTestCase
                .getConfigurationObject(BaseTestCase.ACCURACY + "Config.xml");

        instance = new DBContestDataRetriever(config);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Accuracy test case for method <code>retrieveContestData(long)</code>.
     * 
     * @throws Exception
     *             to JUNIT.
     */
    public void testRetrieveContestDataAccuracy1() throws Exception {
        ContestData data = instance.retrieveContestData(1L);
        assertEquals("HBS Singleton Process Manager 1.0.0", data
                .getComponentName());
        assertTrue("should contain 'http'", data.getSvnPath().contains("http"));
        assertEquals("sparemax", data.getDevelopmentWinner());
        assertEquals("EveningSun", data.getDevelopmentSecondPlace());
        assertTrue(data.getDevelopmentReviewers().contains("bbxiong"));
        assertTrue(data.getDevelopmentReviewers().contains("Veloerien"));
        assertTrue(data.getDevelopmentReviewers().contains("FireIce"));
    }
}
