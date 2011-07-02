/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     *
     * @return All test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(CompleteProfileActionAccuracyTests.suite());
        suite.addTest(CompleteProfileActionConfigurationExceptionAccuracyTests.suite());
        suite.addTest(CompleteProfileActionExceptionAccuracyTests.suite());

        return suite;
    }

}
