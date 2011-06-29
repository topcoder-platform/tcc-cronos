/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.stresstests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 *
 * @author extra
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Returns a test suite containing all stress test cases.
     * </p>
     *
     * @return a test suite containing all stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(new JUnit4TestAdapter(SearchContestsManagerImplStressTests.class));
        // suite.addTest(new JUnit4TestAdapter(UpcomingContestsManagerImplStressTests.class));
        suite.addTest(new JUnit4TestAdapter(ReviewOpportunitiesManagerImplStressTests.class));

        return suite;
    }
}
