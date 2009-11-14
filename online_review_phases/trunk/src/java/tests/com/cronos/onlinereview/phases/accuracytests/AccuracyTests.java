/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all accuracy test cases.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Gathers all accuracy tests together and return.
     *
     * @return all tests in one suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ApprovalPhaseHandlerAccTestsV11.class);
        suite.addTestSuite(FinalReviewPhaseHandlerAccTestsV11.class);
        suite.addTestSuite(PostMortemPhaseHandlerAccTests.class);
        suite.addTestSuite(RegistrationPhaseHandlerAccTestsV11.class);
        suite.addTestSuite(ScreeningPhaseHandlerAccTestsV11.class);
        suite.addTestSuite(SubmissionPhaseHandlerAccTestsV11.class);
        return suite;
    }
}
