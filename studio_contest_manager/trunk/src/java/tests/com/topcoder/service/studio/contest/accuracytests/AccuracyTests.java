/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all the accuracy test cases.
 * </p>
 *
 * @author Topcoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Aggregates tests of the accuracy tests.
     *
     * @return the suite of all accuracy tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // suite.addTestSuite(TestSocketDocumentContentManagerAccuracy.class);
        // suite.addTestSuite(TestContestManagerBeanAccuracy.class);
        suite.addTestSuite(TestContestManagerBean2Accuracy.class);

        return suite;
    }
}
