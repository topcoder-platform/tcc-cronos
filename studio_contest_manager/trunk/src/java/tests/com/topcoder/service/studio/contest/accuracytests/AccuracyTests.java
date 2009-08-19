/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
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
 * @author KLW, chenhong, myxgyy
 * @version 1.3
 */
public class AccuracyTests extends TestCase {
    /**
     * Aggregates tests of the accuracy tests.
     *
     * @return the suite of all accuracy tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestContestManagerBeanAccuracy.class);
        suite.addTestSuite(TestContestManagerBeanAccuracy1.class);

        return suite;
    }
}
