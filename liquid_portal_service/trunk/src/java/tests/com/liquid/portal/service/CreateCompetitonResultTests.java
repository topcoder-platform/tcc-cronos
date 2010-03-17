/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.TestCase;

import com.topcoder.service.project.Competition;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>
 * UnitTest cases of the <code>CreateCompetitonResult</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CreateCompetitonResultTests extends TestCase {
    /**
     * <p>
     * Represents the instance of <code>CreateCompetitonResult</code>.
     * </p>
     */
    private CreateCompetitonResult result;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        result = new CreateCompetitonResult();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

    }

    /**
     * <p>
     * Accuracy test case for <code>getCompetition()</code> and
     * <code>setCompetition(Competition)</code>.
     * </p>
     */
    public void testSetGetCompetition() {
        Competition competition = new SoftwareCompetition();
        result.setCompetition(competition);
        assertEquals(competition, result.getCompetition());
    }
}
