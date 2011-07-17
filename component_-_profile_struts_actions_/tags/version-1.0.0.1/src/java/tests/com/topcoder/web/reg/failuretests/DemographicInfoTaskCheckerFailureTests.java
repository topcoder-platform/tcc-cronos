/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.reg.actions.profile.DemographicInfoTaskChecker;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for DemographicInfoTaskChecker.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class DemographicInfoTaskCheckerFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DemographicInfoTaskCheckerFailureTests.class);
    }

    /**
     * <p>
     * Tests DemographicInfoTaskChecker#getTaskReport(User) for failure.
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     */
    public void testGetTaskReport_NullUser() {
        DemographicInfoTaskChecker instance = new DemographicInfoTaskChecker();
        try {
            instance.getTaskReport(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}