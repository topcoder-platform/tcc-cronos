/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.reg.actions.profile.DefaultProfileCompletenessRetriever;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for DefaultProfileCompletenessRetriever.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class DefaultProfileCompletenessRetrieverFailureTests extends TestCase {

    /**
     * <p>
     * The DefaultProfileCompletenessRetriever instance for testing.
     * </p>
     */
    private DefaultProfileCompletenessRetriever instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new DefaultProfileCompletenessRetriever();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DefaultProfileCompletenessRetrieverFailureTests.class);
    }

    /**
     * <p>
     * Tests DefaultProfileCompletenessRetriever#getProfileCompletenessReport(User) for failure.
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetProfileCompletenessReport_NullUser() throws Exception {
        try {
            instance.getProfileCompletenessReport(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}