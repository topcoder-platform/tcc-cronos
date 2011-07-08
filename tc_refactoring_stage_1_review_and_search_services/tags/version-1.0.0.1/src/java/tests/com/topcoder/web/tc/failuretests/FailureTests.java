/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests;

import com.topcoder.web.tc.failuretests.action.ReviewOpportunitiesManagerActionFailureTest;
import com.topcoder.web.tc.failuretests.action.SearchContestsManagerActionFailureTest;
import com.topcoder.web.tc.failuretests.action.UpcomingContestsManagerActionFailureTest;
import com.topcoder.web.tc.failuretests.implement.ReviewOpportunitiesManagerImplFailureTest;
import com.topcoder.web.tc.failuretests.implement.SearchContestsManagerImplFailureTest;
import com.topcoder.web.tc.failuretests.implement.UpcomingContestsManagerImplFailureTest;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * <p>
     * Returns a test suite containing all unit test cases.
     * </p>
     *
     * @return a test suite containing all unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(new JUnit4TestAdapter(UpcomingContestsManagerImplFailureTest.class));
        suite.addTest(new JUnit4TestAdapter(SearchContestsManagerImplFailureTest.class));
        suite.addTest(new JUnit4TestAdapter(ReviewOpportunitiesManagerImplFailureTest.class));

        suite.addTest(new JUnit4TestAdapter(ReviewOpportunitiesManagerActionFailureTest.class));
        suite.addTest(new JUnit4TestAdapter(SearchContestsManagerActionFailureTest.class));
        suite.addTest(new JUnit4TestAdapter(UpcomingContestsManagerActionFailureTest.class));

        return suite;
    }
}